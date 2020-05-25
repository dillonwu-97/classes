#include <string.h>
#include "../../h/support.h"
#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/vpop.h"
#include "../../h/util.h"
#include "../../h/procq.e"
#include "../../h/asl.e"
#include "../../h/main.e"
#include "../../h/trap.e"
#include "../../h/syscall.e"
#include "../../h/slsyscall1.e"
#include "../../h/queues.h"
#include "../../h/page.h"
#include "../../h/support.h"
#include "../../h/slsyscall2.e"

#define MAXFRAMES 10
#define LOWWATER 3
#define HIGHWATER 6
#define TERMCOUNT 5

register int r2 asm("%d2");
register int r3 asm("%d3");
register int r4 asm("%d4");


// external variables
extern int start(), endt0(), startt1(), etext(), startd0(), endd0(), startd1(), edata(),
      startb0(), endb0(), startb1(), end();
extern int end(), sem_mm, p_alive;
extern pd_t termpg_one[TERMCOUNT][32];
extern pd_t termpg_two[32];
extern devreg_t* devregs[15];

// list of free frames
free_frame frameinfo[MAXFRAMES];
// queue to handle page daemon and getfreeframe
int qarray[MAXFRAMES];
int darray[100] = {0}; // 100 since there are 100 tracks 
Queue queue;


// page demon prereq
sd_t pdem_segtable[32];
pd_t pdem_pgtable[1024];

// disk demon prereq
sd_t ddem_segtable[32];
pd_t ddem_pgtable[1024];

// contains information used for vlock and vunlock
vsem_info v_info[TERMCOUNT];

// contains all the free sectors in the swap device; 0 = free, 1 = used
// i.e. the floppy
int free_space[100][8] = {0};

// contains information that is used by disk demon, specifically operation and address 
dinfo disk_info[TERMCOUNT];

// integers and semaphores
int page_wake = 0; // for waking up page daemon
int sem_floppy = 1; // controls access to floppy
int sem_disk = 0; // controls diskdaemon
int current_track_num = 0; // lower 3 bits so 0 - 7
int current_sect_num = 8;  // upper 7 bits so 8 - 1023
int phys_sems[TERMCOUNT]; // array of physical semaphores in segment 2
int dnum; // device number
char disk_input[TERMCOUNT][DISKAMOUNT]; // read input from disk here, diskamount = 512

// provided initialization
int sem_pf=1;
int pf_ctr, pf_start;
int Tsysframe[5];
int Tmmframe[5];
int Scronframe, Spagedframe, Sdiskframe;
int Tsysstack[5];
int Tmmstack[5];
int Scronstack, Spagedstack, Sdiskstack;

void pagedaemon();

// page demon segment 0 pagetable helper
void static pdem_pghelper(int index, int flag) {
	pdem_pgtable[index].pd_p = flag;
	pdem_pgtable[index].pd_r = 1;
	pdem_pgtable[index].pd_frame = index;
}

// disk demon segment 0 pagetable helper
void static ddem_pghelper(int index, int flag) {
	ddem_pgtable[index].pd_p = flag;
	ddem_pgtable[index].pd_r = 1; 
	ddem_pgtable[index].pd_frame = index;
}

// helper function to reset frame info for a particular frame
void reset_fi (int frame_num) {
	// update frameinfo and free_list
	frameinfo[frame_num].inuse = 0;
	frameinfo[frame_num].page_num = -1;
	frameinfo[frame_num].seg_num = -1;
	frameinfo[frame_num].proc_num = -1;
	frameinfo[frame_num].sect_num = -1;
	frameinfo[frame_num].track_num = -1;
}

// this function creates the page demon and disk demon system processes which should run with memory mapping on
// initializes the frameinfo[] array and all the variables and data structures needed to handle the frames
pageinit()
{
	int endframe;

	/* check if you have space for 35 page frames, the system
	has 128K */
	endframe=(int)end / PAGESIZE;
	if (endframe > 220 ) { /* 110 K */
		HALT();
	}

	Tsysframe[0] = endframe + 2;
	Tsysframe[1] = endframe + 3;
	Tsysframe[2] = endframe + 4;
	Tsysframe[3] = endframe + 5;
	Tsysframe[4] = endframe + 6;
	Tmmframe[0]  = endframe + 7;
	Tmmframe[1]  = endframe + 8;
	Tmmframe[2]  = endframe + 9;
	Tmmframe[3]  = endframe + 10;
	Tmmframe[4]  = endframe + 11;
	Scronframe  = endframe + 12;
	Spagedframe = endframe + 13;
	Sdiskframe  = endframe + 14;

	Tsysstack[0] = (endframe + 3)*512 - 2;
	Tsysstack[1] = (endframe + 4)*512 - 2;
	Tsysstack[2] = (endframe + 5)*512 - 2;
	Tsysstack[3] = (endframe + 6)*512 - 2;
	Tsysstack[4] = (endframe + 7)*512 - 2;
	Tmmstack[0]  = (endframe + 8)*512 - 2;
	Tmmstack[1]  = (endframe + 9)*512 - 2;
	Tmmstack[2]  = (endframe + 10)*512 - 2;
	Tmmstack[3]  = (endframe + 11)*512 - 2;
	Tmmstack[4]  = (endframe + 12)*512 - 2;
	Scronstack   = (endframe + 13)*512 - 2;
	Spagedstack  = (endframe + 14)*512 - 2;
	Sdiskstack   = (endframe + 15)*512 - 2;
	pf_start    = (endframe + 17);

	/*
	pf_start = (int)end / PAGESIZE + 1; 
	*/

	int i, j;
	sem_pf = MAXFRAMES;
	pf_ctr = 0;
	// initializing frame info array
	for (i = 0; i < MAXFRAMES; i++) {
		reset_fi(i);
	}

	// initializing info for virtual semaphores
	for (i = 0; i < TERMCOUNT; i++) {
		v_info[i].add = (int*)ENULL;
		v_info[i].sem = 0;
	}
	// initializing disk info
	for (j = 0; j < TERMCOUNT; j++) {
		disk_info[j].op = -1;
		disk_info[j].add = (char*)ENULL;
		disk_info[j].tn = -1;
		disk_info[j].track = -1;
		disk_info[j].sector = -1;
		disk_info[j].r2 = -1;
		disk_info[j].r3 = -1;
	}

	// creating Queue and initializing
	// this queue is for the page daemon	
	queue.capacity = MAXFRAMES;
	queue.size = 0;
	queue.front = 0;
	queue.back = 0;
	queue.array = qarray;

	// testing
//	enqueue(&queue, 10);
//	enqueue(&queue, 20);
//	dequeue(&queue);

	// creating the segment table for page daemon 
	pdem_segtable[0].sd_p = 1;
	pdem_segtable[0].sd_prot = 7;
	pdem_segtable[0].sd_len = 1023;
	pdem_segtable[0].sd_pta = &pdem_pgtable[0];
	

	// creating the segment table for the disk demon
	ddem_segtable[0].sd_p = 1;
	ddem_segtable[0].sd_prot = 7;
	ddem_segtable[0].sd_len = 1023;
	ddem_segtable[0].sd_pta = &ddem_pgtable[0];

	for (i = 1; i < 32; i++) {
		pdem_segtable[i].sd_p = 0;
		ddem_segtable[i].sd_p = 0;
	}
	
	// initializing the page table for segment 0 of the daemons
	for (i = 0; i < 1024; i++) {
		if (i == 2) {
		// segment 0 page 2
			pdem_pghelper(i, 1);
			ddem_pghelper(i, 1);
		// EVT
		} else if (i >= 0x1400/PAGESIZE && i <= 0x1500/PAGESIZE) {
		// device registers
			pdem_pghelper(i, 1);
			ddem_pghelper(i, 1);
		} else if (i >= (int)startt1/PAGESIZE && i<= (int)etext/PAGESIZE) {
		// support level text
			pdem_pghelper(i, 1);
			ddem_pghelper(i, 1);
		} else if (i >= (int)startd1/PAGESIZE && i <= (int)edata/PAGESIZE) {
		// support level data
			pdem_pghelper(i, 1);
			ddem_pghelper(i, 1);
		}  else if (i >= (int)startb1/PAGESIZE && i <= (int)end/PAGESIZE) {
		// support level BSS
			pdem_pghelper(i, 1);
			ddem_pghelper(i, 1);
		} else if (i == Spagedframe) {
			pdem_pghelper(i, 1);
		} else if (i == Sdiskframe) {
			ddem_pghelper(i, 1);	
		} else {
			pdem_pghelper(i, 0);
			ddem_pghelper(i, 0);
		}
	}
	
	state_t paged;
	// starting the page demon
	STST(&paged);
	paged.s_sr.ps_s = 1;
	paged.s_sr.ps_int = 0;
	paged.s_sr.ps_m = 1;
	paged.s_crp = &pdem_segtable[0];
	paged.s_sp = Spagedstack;
	paged.s_pc = (int)pagedaemon;
	r4 = (int)&paged;
	SYS1();

	// starting the disk demon
	state_t diskd;
	STST(&diskd);
	diskd.s_sp = Sdiskstack;
	diskd.s_pc = (int)diskdaemon;
	diskd.s_sr.ps_s = 1;
	diskd.s_sr.ps_int = 7;
	diskd.s_sr.ps_m= 1;
	diskd.s_crp = &ddem_segtable[0];
	r4 = (int)&diskd;
	SYS1();
	
}

// helper function for vpop semop
void vsempop (int op, int* sem) {
	vpop sem_stuff;
	sem_stuff.sem = sem;
	sem_stuff.op = op;
	r3 = 1;
	r4 = (int)&sem_stuff;
	SYS3();
}

// helper function for semop twice
void twopop (int op, int* sem, int op2, int* sem2) {
	vpop sem_two[2];
	sem_two[0].sem = sem;
	sem_two[0].op = op;
	sem_two[1].sem = sem2;
	sem_two[1].op = op2;
	r3 = 2;
	r4 = (int) &sem_two;
	SYS3();
}

// maintains a list of free frames 
// page demon adds frames to the list and this function takes frames off the list
// P (LOCK) sem_pf, uses sem_mm to restrict access to its critical section and it updates the frameinfo[] array
getfreeframe(term,page,seg)
int term, page, seg;
{
	int i;

	vsempop (LOCK, &sem_pf);
	
	int frame_index;
	vsempop (LOCK, &sem_mm);
	int exists = 0; // checks to see if the page already exists for a segment 2 pagein
	if (seg == 2) {
		for (i = 0; i < MAXFRAMES; i++) {
			if (frameinfo[i].inuse == 1 && frameinfo[i].page_num == page && frameinfo[i].seg_num == seg) {
				frame_index = i;
				exists = 1;
			}
		}
	}
	// if the page does not exist find free spot in frameinfo and then put page there
	// i should match the value put into the queue
	// should it also match the pf_ctr value?
	if (exists == 0) {
		for (i = 0; i < MAXFRAMES; i++) {
			if (frameinfo[i].inuse == 0) {
				frameinfo[i].inuse = 1;
				frameinfo[i].proc_num = term;
				frameinfo[i].page_num = page;
				frameinfo[i].seg_num = seg;
				frame_index = i;
				break;
			}
		}
		// i gets updated, and then pf_ctr is incremented
		// i think pf_ctr should not exceed the number of maxfree frames
		// i think it should be this
		i = frame_index + pf_start;
		// instead of this
	//	i = pf_ctr++ + pf_start;

		// i, which is the free frame should get put into a queue
		enqueue(&queue, frame_index);
	}

	// check if the free frames left is at the low and if so, put pagedaemon on RQ
	if (sem_pf <= LOWWATER && page_wake <= 0) {
		vsempop(UNLOCK, &page_wake);
	}
	
	// unlock sem_mm and sem_floppy
	vsempop (UNLOCK, &sem_mm);
	return (i);
} 

// obtains track and sector number that specify the location of page in the swap device from the pd_frame
// copies the page from the swap device to page frame
// updates frameinfo[] and uses sem_floppy to artibrate floppy access
pagein(term,page,seg,pf)
int term, page, seg, pf;
{
	//vsempop(LOCK, &sem_floppy);
	int track, sect, i;
	// segment 1
	
	if (seg == 1) {
	// obtain track and sector number from pd_frame; sect is the lower 3 bits, track is upper 7 bits
		int ts_info = termpg_one[term][page].pd_frame;
		track = ts_info / 8;
		sect = ts_info % 8;
		termpg_one[term][page].pd_frame = pf; // set equal to free_frame 

	// segment 2
	} else {
		int ts_info = termpg_two[page].pd_frame;
		track = ts_info / 8;
		sect = ts_info % 8;
		termpg_two[page].pd_frame = pf;
	}	
	twopop(UNLOCK, &sem_mm, LOCK, &sem_floppy);	
	// do a read from the swap device to the buffer
	dnum = dnum;
	devreg_t* disk = devregs[dnum]; // dnum is the first floppy register
	disk->d_track = track;
	disk->d_op = IOSEEK;
	r4 = dnum;
	SYS8();

	// do a write from the swap device to the buffer
	char* copy_into = (char*)(pf * PAGESIZE); 
	disk->d_sect = sect;
	disk->d_badd = copy_into;
	disk->d_op = IOREAD; // read into buffer from disk
	r4 = dnum;
	SYS8();
	
	twopop(LOCK, &sem_mm, UNLOCK, &sem_floppy);	
	// setting presence bit to 1
	if (seg == 1) {
		termpg_one[term][page].pd_p = 1;
	} else {
		termpg_two[page].pd_p = 1;
	}	

	// update frameinfo[]
	// frame index is the most recently added to the queue, so it should be the value of the tail of the queue
	for (i = 0; i < MAXFRAMES; i++) {
		if (frameinfo[i].page_num == page && frameinfo[i].seg_num == seg && frameinfo[i].proc_num == term) {

			frameinfo[i].track_num = track;
			frameinfo[i].sect_num = sect;
		}
	}
	//vsempop (UNLOCK, &sem_floppy);
}

// releases page frames and sectors of a process that died
// looks at page table and is called by terminate()
putframe(term)
int term;
{
	// find the frameinfo for this particular frame and then reset all the info 
	int i, seg, page, track, sect;
	for (i = 0; i < MAXFRAMES; i++) {
		if (frameinfo[i].proc_num == term) {
			// reset bits for the pages in use
			seg = frameinfo[i].seg_num;
			page = frameinfo[i].page_num;
			if (seg == 1) {
				termpg_one[term][page].pd_frame = -1;
				termpg_one[term][page].pd_r = 0;
				termpg_one[term][page].pd_p = 0;
			}
			// what to do about seg 2? another process might still be using the page
			// reset bits for the track and sect areas in use
			track = frameinfo[i].track_num;
			sect = frameinfo[i].sect_num;
			free_space[track][sect] = 0;
			reset_fi(i);
		}
	}
}

// selects free sector in swap device
// returns an integer that contains the sector number in the lower 3 bits and track number in the next 7 bits
int getfreesector() {
	int free_sector;
	int i, k;
	for (i = 0; i < 100; i++) {
		for (k = 0; k < 8; k++) {
			if (free_space[i][k] == 0) {
				current_sect_num = k;
				current_track_num = i;
				free_space[i][k] = 1;
				free_sector = current_sect_num + current_track_num * 8;
				return free_sector;
			}
		}
	}
	return -1; 
}

// infinite loop that checks the number of available page frames
// if too few frames are available, it selects a used page frame using FIFO or second chance replacement algorithm
// moves the page to the swap device, stores the swap device location in pd_frame, and updates frame_info[] and free list
// it then V's sem_pf
// uses sem_mm to control access to most of its operations like updating page tables and free list
// blocks on pseudoclock if there is no need to page out
// should synchronize pagedaemon and getfreeframe
void pagedaemon() {
	
	while(1) {
		// check the number of available page frames to see if there are too few
		// if enough frames, block
		if (sem_pf >= HIGHWATER) {
			// block on page_wake semaphore if sem_pf is greater
			vsempop(LOCK, &page_wake);
		} else {
			//twopop(LOCK, &sem_mm, LOCK, &sem_floppy);
			// out_frame contains the frame index of the frame that is removed
			vsempop(LOCK, &sem_mm);
			int out_frame = dequeue(&queue);			
			int page_num = frameinfo[out_frame].page_num;
			int tn = frameinfo[out_frame].proc_num;
			int seg_num = frameinfo[out_frame].seg_num;
			int track_num = frameinfo[out_frame].track_num;
			int sect_num = frameinfo[out_frame].sect_num;
			int free_sector, curr_frame;
			char* copy_from;
			// check which segment number
			if (seg_num == 1) {
				// second chance
				if (termpg_one[tn][page_num].pd_r == 1) {
					termpg_one[tn][page_num].pd_r = 0;
					//twopop (UNLOCK, &sem_mm, UNLOCK, &sem_floppy);
					vsempop(UNLOCK, &sem_mm);
					enqueue(&queue, out_frame);
					continue;
				}
				curr_frame = termpg_one[tn][page_num].pd_frame; // get the pd_frame
				termpg_one[tn][page_num].pd_p = 0; // turn off ref and pres bits
			} else {
				// second chance
				if (termpg_two[page_num].pd_r == 1) {
					termpg_two[page_num].pd_r = 0;
					//twopop (UNLOCK, &sem_mm, UNLOCK, &sem_floppy);
					vsempop(UNLOCK, &sem_mm);
					enqueue(&queue, out_frame);
					continue;
				}
				curr_frame = termpg_two[page_num].pd_frame;	
				termpg_two[page_num].pd_p = 0;
			}
			
			reset_fi (out_frame);
			// check to see if page that is going to be moved already occupies a sector
			if (track_num == -1 && sect_num == -1) {
				free_sector = getfreesector(); // contains the new free sector info
				track_num = free_sector / 8;
				sect_num = free_sector % 8;
			} else {
				free_sector = track_num * 8 + sect_num;
			}
			// location to copy data from
			copy_from = (char*)(curr_frame*PAGESIZE);	
			
			// store swap location in pd_frame
			if (seg_num == 1) {
				termpg_one[tn][page_num].pd_frame = free_sector;
				// actually i dont think the reference bit should be turned on
				//termpg_one[tn][page_num].pd_r = 1;
			} else {
				termpg_two[page_num].pd_frame = free_sector;
				//termpg_two[page_num].pd_r = 1;
			}		

			//vsempop(UNLOCK, &sem_mm);
			// turn off ref bit and presence bit for the info
			twopop(UNLOCK, &sem_mm, LOCK, &sem_floppy);
			//vsempop(LOCK, &sem_floppy);
			// move the data from the page to swap device
			
			// disk seek to find the start of where to write the info
			dnum = 11;
			devreg_t* flop = devregs[dnum]; // 11 is the first floppy dev reg
			flop->d_track = track_num;
			flop->d_op = IOSEEK;
			r4 = dnum;
			SYS8();

			// disk write to write the info
			flop->d_sect = sect_num;
			flop->d_badd = copy_from; 
			flop->d_op = IOWRITE;
			r4 = dnum;
			SYS8();
			//vsempop (UNLOCK, &sem_floppy);
			//twopop(UNLOCK, &sem_floppy, LOCK, &sem_mm);
			//vsempop(LOCK, &sem_mm);
			twopop(UNLOCK, &sem_floppy, UNLOCK, &sem_pf);

			

		}
	}
	
}


