#include <string.h>
#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/vpop.h"
#include "../../h/page.h"
#include "../../h/support.h"
#include "../part1/h/tconst.h"

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
extern int darray[100]; // 0 means dont need to do i/o, 1 means do i/o

// semaphores
int sem_virtualvp; // used to control access to critical sections 
int diskwait[TERMCOUNT]; // all initialized to 0?
int phys_sems[TERMCOUNT]; // array of phys sems?
int virt_sems[TERMCOUNT]; // array of virtual semaphores?

int disk_num; // disk device number
int direction = 1; // positive = going up, negative = going down, starts positive
int latest_track = 0; // the latest track that was done by IOSEEK

// data structure to contain sector info about the track
// contains int sect, int op, char* add
extern dinfo disk_info[100][8];

// associate a virtual semaphore in seg 2 with a physical semaphore
// keeps a list of virtual addresses being used as semaphores and corresponding list of physical addresses
// does P (LOCK) and V (UNLOCK) on physical semaphores
// uses sem_virtualvp to control access to critical sections

// performs v operation on semaphore whose address is in D4
// if T process passes virtual address in D4 that do not refer to seg2, it should be terminated
virtualv(int tn)
{
	
	int i, ps_i; // ps_i stands for physical semaphores index
	int* v_add = (int*)sstat_o[tn].s_r[4]; // should this be a char* or an int*?
	int check = 0;
	// if not in seg2
	if (v_add < SEG2) {
		SYS17(); // terminate
	}
	// if the seg2 value of the semaphore is 0, check to see if there is already a physical semaphore associated with it
	if (*v_add == 0) {
		for (i = 0; i < TERMCOUNT; i++) {
			// this means the virtual address has already been assigned a semaphore
			if (virt_sems[i] == v_add) {
				check = 1;
			}
			// check if char* at i is empty or not
			if (virt_sems[i] == 0) {
				ps_i = i;
			}
		}
		// allocates a physical semaphore
		if (check == 0) {
			phys_sems[ ps_i ] = 0;
		}
		// do a V (UNLOCK)
		vsempop(UNLOCK, phys_sems[ps_i]);
	// if seg value is greater than 0, decrement the value
	} else if (*v_add > 0) {
		*(v_add)-=1;	
	}
	// what about less than 0?
	
}

// performs a P (LOCK) on a semaphore whose virtual address is in D4 at the time of the call
// if T-process passes virtual addresses for D4 that do not refer to segment 2, it should be terminated
virtualp(int tn)
{ 
	
	int i, ps_i; // ps_i stands for physical semaphores index
	int* v_add = (int*)sstat_o[tn].s_r[4]; // should this be a char* or an int*?
	int check = 0;
	// check if virtual address is in segment 2
	if (v_add < SEG2) {
		SYS2();
	}
	// if the seg2 value of the semaphore is 0, check to see if there is already a physical semaphore associated with it
	if (*v_add == 0) {
	// convert virtual to physical address 
		for (i = 0; i < TERMCOUNT; i++) {
			// this means the virtual address has already been assigned a semaphore
			if (virt_sems[i] == v_add) {
				check = 1;
			}
			// check if char* at i is empty or not
			if (virt_sems[i] == 0) {
				ps_i = i;
			}
		}
		// allocates a physical semaphore
		if (check == 0) {
			phys_sems[ ps_i ] = 0;
		}
		// do a LOCK
		vsempop (LOCK, phys_sems[ps_i]);
	// if seg value is greater than 0, decrement the value
	} else if (*v_add > 0) {
		*(v_add)-=1;	
	}
	// what about less than 0?
	
}



// accesses the hard disk by queueing requests on the disk queue
// V (UNLOCK) the semaphore sem_disk after request is queued and block on semaphore diskwait[procnum]
// writing to the disk
diskput(int tn)
{
	char* v_add = (char*)sstat_o[tn].s_r[3]; // contains the virtual address from which disk transfer is to be made
	int track = sstat_o[tn].s_r[4]; // contains disk track number
	int sector = sstat_o[tn].s_r[2]; // contains sector within track to whcih data transfer is made

	disk_info[track][sector].op = IOWRITE;
        disk_info[track][sector].add = &disk_input[tn][0];	
	disk_info[track][sector].v_add = v_add;
	disk_info[track][sector].tn = tn;
	darray[track] = 1; // flip bit
	// unlock sem_disk, block diskwait[procnum]
	twopop (UNLOCK, &sem_disk, LOCK, &diskwait[tn]);	
	// put number of bytes read in D2
	// negative of status register is returned in D2 instead of byte count if operation does not complete successfully	
}

// reading from the disk
diskget(int tn)
{
	char *v_add = (char*)sstat_o[tn].s_r[3];
	int track = sstat_o[tn].s_r[4];
	int sector = sstat_o[tn].s_r[2];
	
	disk_info[track][sector].op = IOREAD;
	disk_info[track][sector].add = &disk_input[tn][0];
	disk_info[track][sector].v_add = v_add;
	disk_info[track][sector].tn = tn;
	darray[track] = 1; // flip bit 

	twopop (UNLOCK, &sem_disk, LOCK, diskwait[tn]);
}

// helper function for the disk daemon
// if the value in the array is 1, then do I/O
int dd_helper( devreg_t* disk, dinfo* d_info, int disk_num, int track) {
	disk->d_track = track;
	disk->d_op = IOSEEK;
	r4 = disk_num;
	SYS8();
	
	int j;	
	// iterate through the array to see if there is a sector that has an i/o queued
	for (j = 0; j < 8; j++) {
		if (d_info->op != -1) {
			if (d_info->op == IOWRITE) {
				// writing data from virtual address to the disk
				memcpy(d_info->add, d_info->v_add, DISKAMOUNT);
			}
			disk->d_sect = j;
			disk->d_badd = d_info->add;
			disk->d_op = d_info->op;
			SYS8();
			if (d_info->op == IOREAD) {
				// if we are doing a read operation, we are reading from the disk
				memcpy(d_info->v_add, d_info->add, DISKAMOUNT);
			}

			// reset dinfo after using it
			d_info->op = -1;
			d_info->v_add = (char*)ENULL;
			d_info->add = (char*)ENULL;
			
			// unlock every time an operation is complete
			twopop(LOCK, &sem_disk, UNLOCK, &diskwait[d_info->tn]);
		}
	}
	
					
}


// looks at the disk requests in the disk queue and uses the elevator algorithm to reduce disk head movement
// is in an infinite loop and does a P (LOCK) on sem_disk at beginning of the loop
// when it finishes the disk I/O, it V (UNLOCK)s the diskwait[procnum]
// idea:
// to minimize head movement, have the queue go through all the requests in a local track before going to a different one
void diskdaemon() {
	// while sem_disk has stuff that needs to be done
	disk_num = 7;
	devreg_t* disk = devregs[disk_num];
	while(sem_disk!= 0) {
		int i, j, tn;
		// if the direction is positive
		if (direction > 0) {
			for (i = latest_track; i < 100; i++) {
				// this means that there is an ioseek that needs to be done here
				if (darray[i] == 1) {
					dd_helper(disk, disk_info[i], disk_num, i);							
				}
				// if sem_disk is 0, change value to 
				if (sem_disk == 0) {
					break;
				}
			}
			// if after all the iterations, we are not at 0, reverse direction 
			if (sem_disk != 0) {
				latest_track = 100;
				direction = -1;
			}
			// when to update latest track?	
		// negative direction
		} else {
			for (i = latest_track; i >=0; i--) {
				if (darray[i] == 1) {
					dd_helper(disk, disk_info[i], disk_num, i);					
				}
				if (sem_disk ==0) {
					break;
				}
			}
			if (sem_disk != 0) {
				latest_track = 0;
				direction = 1;
			}
		}
	
	}
	// once sem_disk reaches 0, lock
	vsempop(LOCK, &sem_disk);
}

