// are these all integers?
typedef struct free_frame {
	int inuse;
	int proc_num;
	int page_num;
	int seg_num;
	int track_num;
	int sect_num;
} free_frame;

typedef struct dinfo {
	int op;
	int tn;
	int sector;
	int track;
	char* add;
	int r2;
	int r3;
} dinfo;

typedef struct vsem_info {
	int* add; // saved address for virtual semaphore
	int sem; // actual physical semaphore

} vsem_info;

