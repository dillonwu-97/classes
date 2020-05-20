// are these all integers?
typedef struct free_frame {
	int inuse;
	int proc_num;
	int page_num;
	int seg_num;
	int track_num;
	int sect_num;
} free_frame;

// information about i/o associated with each request and sector
typedef struct dinfo {
	int op; // operation for the track
	char* add; // address for diskinput; should be in the format &disk_input[tn][0]
	char* v_add; // virtual address 
	int tn; // terminal number
} dinfo;

