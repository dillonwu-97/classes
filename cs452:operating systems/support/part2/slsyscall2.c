#include <string.h>
#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/vpop.h"
#include "../../h/page.h"
#include "../../h/support.h"
#include "../part1/h/tconst.h"

#define TERMCOUNT 5
register int r2 asm("%d2");
register int r3 asm("%d3");
register int r4 asm("%d4");

extern int sem_disk;
extern state_t	pstat_n[TERMCOUNT];
extern state_t mstat_n[TERMCOUNT];
extern state_t sstat_n[TERMCOUNT];
extern state_t pstat_o[TERMCOUNT];
extern state_t mstat_o[TERMCOUNT];
extern state_t sstat_o[TERMCOUNT];
extern devreg_t* devregs[15];
extern void vsempop();
extern void twopop();
extern char disk_input[TERMCOUNT][DISKAMOUNT];
extern dinfo disk_info[TERMCOUNT];
extern vsem_info v_info [TERMCOUNT];
// semaphores
int sem_virtualvp = 1; // used to control access to critical sections 
int diskwait[TERMCOUNT]; // all initialized to 0
int phys_sems[TERMCOUNT]; // array of phys sems
int virt_sems[TERMCOUNT]; // array of virtual semaphores


int disk_num = 7; // disk device number
int direction = 1; // positive = going up, negative = going down, starts positive
int latest_track = 0; // the latest track that was done by IOSEEK
int disk_count = 0; // keeps track of the number of requests remaining

// associate a virtual semaphore in seg 2 with a physical semaphore
// keeps a list of virtual addresses being used as semaphores and corresponding list of physical addresses
// does P (LOCK) and V (UNLOCK) on physical semaphores
// uses sem_virtualvp to control access to critical sections

// performs v operation on semaphore whose address is in D4
// if T process passes virtual address in D4 that do not refer to seg2, it should be terminated
virtualv(int tn)
{
	
	vsempop(LOCK, &sem_virtualvp); // lock so that you can make changes to seg2
	int i, ps_i; // ps_i stands for physical semaphores index
	int* v_add = (int*)sstat_o[tn].s_r[4];
	int check = 0;
	int to_unlock = 0;
	// if not in seg2 
	if (v_add < (int*)(2048 * 512)) {
		SYS17(); // terminate
	}
	// check to see if there is a physical semaphore associated with the address	
	for (i = 0; i < TERMCOUNT; i++) {
		if (v_info[i].add == v_add) {
			check = 1;
			to_unlock = i;
		}
	}
	// if there exists a physical semaphore, V the semaphore; if it is 0, remove it
	if (check == 1) {
		//twopop(UNLOCK, &sem_virtualvp, UNLOCK, &v_info[ to_unlock ]);
		vsempop(UNLOCK, &v_info [to_unlock ]);
		if (v_info[ to_unlock].sem == 0) {
			v_info[ to_unlock ].add = (int*) ENULL;
			v_info[ to_unlock ].sem = 0;
		}
	} else  {
		*(v_add)+=1;	
	}
	vsempop(UNLOCK, &sem_virtualvp);

}

// performs a P (LOCK) on a semaphore whose virtual address is in D4 at the time of the call
// if T-process passes virtual addresses for D4 that do not refer to segment 2, it should be terminated
virtualp(int tn)
{ 

	vsempop(LOCK, &sem_virtualvp);
	int i, ps_i; // ps_i stands for physical semaphores index
	int* v_add = (int*)sstat_o[tn].s_r[4]; // should this be a char* or an int*?
	int check = 0;
	int to_unlock;
	// check if virtual address is in segment 2
	if (v_add < (int*)(2048*512)) {
		SYS17();
	}
	// if the seg2 value of the semaphore is 0, check to see if there is already a physical semaphore associated with it
	if (*v_add == 0) {
		for (i = 0; i < TERMCOUNT; i++) {
			// this means the virtual address has already been assigned a semaphore
			if (virt_sems[i] == *v_add) {
				check = 1;
				to_unlock = i;
			}
		}
		// allocates a physical semaphore
		if (check == 0) {
			// find a free spot and allocate a physical semaphore
			for (i = 0; i < TERMCOUNT; i++) {
				if (v_info[i].add == (int*) ENULL) {
					v_info[i].add = v_add;
					v_info[i].sem = *v_add;	
					to_unlock = i;
				}
			}		
		}
		// do a LOCK
		twopop (UNLOCK, &sem_virtualvp, LOCK, &v_info[to_unlock]);
		//vsempop(LOCK, &v_info[to_unlock]);
	// if seg value is greater than 0, decrement the value
	} else if (*v_add > 0) {
		*(v_add)-=1;
		vsempop(UNLOCK, &sem_virtualvp);	
	}

}



// accesses the hard disk by queueing requests on the disk queue
// V (UNLOCK) the semaphore sem_disk after request is queued and block on semaphore diskwait[procnum]
// writing to the disk
diskput(int tn)
{
	
	char* v_add = (char*)sstat_o[tn].s_r[3]; // contains the virtual address from which disk transfer is to be made
	int track = sstat_o[tn].s_r[4]; // contains disk track number
	int sector = sstat_o[tn].s_r[2]; // contains sector within track to whcih data transfer is made

	devreg_t* disk = devregs[disk_num];
	// write info from virtual address to physical address first
	memcpy(&disk_input[tn][0], v_add, DISKAMOUNT);
		
	disk_info[tn].op = IOWRITE;
	disk_info[tn].add = &disk_input[tn][0];
	disk_info[tn].tn = tn;
	disk_info[tn].track = track;
	disk_info[tn].sector = sector;
	disk_count ++;

	// unlock sem_disk, block diskwait[procnum]
	//vsempop(UNLOCK, &sem_disk);
	//vsempop(LOCK, &diskwait[tn]);
	twopop (UNLOCK, &sem_disk, LOCK, &diskwait[tn]);	
	// put number of bytes read in D2
	// negative of status register is returned in D2 instead of byte count if operation does not complete successfully
	// debug
	//
	int status = disk_info[tn].r3;
	int amnt = disk_info[tn].r2;
	if (status == NORMAL) {
		sstat_o[tn].s_r[2] = DISKAMOUNT;
	} else {
		sstat_o[tn].s_r[2] = -1 * DISKAMOUNT;
	}

}

// reading from the disk
diskget(int tn)
{
	
	devreg_t* disk = devregs[disk_num];
	char *v_add = (char*)sstat_o[tn].s_r[3];
	int track = sstat_o[tn].s_r[4];
	int sector = sstat_o[tn].s_r[2];
	
	disk_info[tn].op = IOREAD;
	disk_info[tn].add = &disk_input[tn][0];
	disk_info[tn].tn = tn;
        disk_info[tn].track = track;
	disk_info[tn].sector = sector;	
	disk_count ++;

	//vsempop(UNLOCK, &sem_disk);
	//vsempop( LOCK, &diskwait[tn]);
	twopop(UNLOCK, &sem_disk, LOCK, &diskwait[tn]);

	memcpy(v_add, &disk_input[tn][0], DISKAMOUNT);
	// number of bytes read
	int status = disk_info[tn].r3;
	int amnt = disk_info[tn].r2;
	if (status == ENDOFINPUT || status == NORMAL) {
		if (amnt > 0) {
			sstat_o[tn].s_r[2] = amnt;
		}
		if (status == ENDOFINPUT || amnt <= 0) {
			sstat_o[tn].s_r[2] = -1 * ENDOFINPUT;
		}
	} else {
	// damedayo
		sstat_o[tn].s_r[2] = -1 * status;
	}	
	
}

// conducts the operation
void operate(devreg_t* disk, dinfo* d_info) {
	disk->d_track = d_info->track;
	disk->d_op = IOSEEK;
	r4 = disk_num;
	SYS8();

	disk->d_sect = d_info->sector;
	disk->d_badd = d_info->add;
	disk->d_op = d_info->op;
	r4 = disk_num;
	SYS8();	
	
	d_info->r3 = r3;
	d_info->r2 = r2;
	disk_count--;
	// save info in disk_info
	vsempop(UNLOCK, &diskwait[d_info->tn]);

}

// looks at the disk requests in the disk queue and uses the elevator algorithm to reduce disk head movement
// is in an infinite loop and does a P (LOCK) on sem_disk at beginning of the loop
// when it finishes the disk I/O, it V (UNLOCK)s the diskwait[procnum]
// idea:
// to minimize head movement, have the queue go through all the requests in a local track before going to a different one
void diskdaemon() {
	// while sem_disk has stuff that needs to be done
	devreg_t* disk = devregs[disk_num];
	int cur_track = 0;
	int cur_sect = 0;
	while(1) {
		if (disk_count == 0) {
			vsempop(LOCK, &sem_disk);
		}
		// debug
		if (disk_info[2].track % 100 == 0) {
			int k;
			k+=1;
		}
		int i, j, tn;
		// if the direction is positive
		if (direction > 0) {
			for (i = 0; i < TERMCOUNT; i++) {
				// check if semaphore is locked and if it is in the right direction
				if (diskwait[i] == -1 && disk_info[i].track >= cur_track && disk_info[i].sector >= cur_sect) {
					operate(disk, &disk_info[i]); // do the waitforio and semaphore operations
					cur_track = disk_info[i].track; // update the track and sector info
					cur_sect = disk_info[i].sector;
				}
			}
			// if after all the iterations, we are not at 0, reverse direction 
			if (disk_count != 0) {
				cur_track = 100;
				cur_sect = 8;
				direction = -1;
			}
		// negative direction
		} else {
			for (i = 0; i < TERMCOUNT; i++) {
				// check if semaphore is locked and if it is in the right direction
				if (diskwait[i] == -1 && disk_info[i].track < cur_track && disk_info[i].sector < cur_sect) {
					operate(disk, &disk_info[i]); // do the waitforio and semaphore operations
					cur_track = disk_info[i].track; // update the track and sector info
					cur_sect = disk_info[i].sector;
				}
			}
			// if after all the iterations, we are not at 0, reverse direction 
			if (disk_count != 0) {
				cur_track = 0;
				cur_sect = 0;
				direction = 1;
			}
		}
	}

}

