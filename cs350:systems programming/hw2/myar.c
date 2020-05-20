/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */
#include <stdlib.h>
#include <stdio.h>
#include <ar.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/types.h>
#include <dirent.h>
#include <utime.h>



struct meta {
	char name[16]; //room for null
	int mode;
	int size;
	int uid;
	int gid;
	time_t mtime; //a time_t is a long
};

//Binary to ascii conversion function
void change (unsigned int number, char* array) {
	int index = 0;
	char c;
	int temp = number;
	while (temp / 10 != 0) {
		index+=1;
		temp = temp / 10;
	}
	for (int i = number; i > 0;  i= i /10) {
		c = '0' + (i%10);
		array[index] = c;
		index -= 1;
	}
}

//clean the string of null bytes and pad
void clean (char* str, int size) {
	int temp = strlen(str);
	int pad = size - temp;
	if (pad == 0) { exit(0); };
	for(int i = temp; i < size; i++) {
		str[i] = ' ';
		//printf("%d\n", i);
		//printf("letter %c\n", str[i]);
	}

}

//stat structures have binary fields
//ar_hdr fields are printable ascii which means need to convert binary -> printable ascii
//used in adding members to the archive
int fill_ar_hdr (char *filename, struct ar_hdr *hdr) {
	struct stat test;
	stat(filename, &test);
    strncpy(hdr->ar_name, filename, sizeof(hdr->ar_name));
    clean(hdr->ar_name, sizeof(hdr->ar_name));
    //filling in uid 

   	sprintf(hdr->ar_uid, "%-u", test.st_uid);
    clean(hdr->ar_uid, sizeof(hdr->ar_uid));

    //filling in gid
    //unsigned int gid = test.st_gid;
    //change(gid, hdr->ar_gid);
    sprintf(hdr->ar_gid, "%-u", test.st_gid);
    clean(hdr->ar_gid, sizeof(hdr->ar_gid));


    // filling in mode
    //unsigned int mode = test.st_mode;
    //change(mode, hdr->ar_mode);
    sprintf(hdr->ar_mode, "%-hu", test.st_mode);
    clean(hdr->ar_mode, sizeof(hdr->ar_mode));

    //filling in size
   	sprintf(hdr->ar_size, "%-ld", test.st_size);
    clean(hdr->ar_size, sizeof(hdr->ar_size));

   	//filling fmag
   	sprintf(hdr->ar_fmag, "%-s", ARFMAG);
   	clean(hdr->ar_size, sizeof(hdr->ar_size));
   	//printf("ooba %s\n", ARFMAG);
   	//printf("ooba %s\n", "booba");
   	//strncpy(hdr->ar_fmag,"", sizeof(hdr->ar_fmag));

   	//filling in time
   	sprintf(hdr->ar_date, "%-ld", test.st_mtime);
    clean(hdr->ar_date, sizeof(hdr->ar_date));

	return 0;

}


//string to int
int strToInt (char* number, int size){
	int result = 0;
	for (int i =0; i < size; i++) {
		if (number[i] == ' ') {
			break;
		}
		result = result * 10 + (number[i] - '0');
		//printf("%d\n", result);
	}
	return result;
}





// converts fields in ar_hdr into binary for the meta structure
// used when extracting members from the archive
int fill_meta (struct ar_hdr hdr, struct meta *meta) {
	//filling in name
	strncpy(meta->name,hdr.ar_name, sizeof(hdr.ar_name));
	//printf("meta name %s\n", meta->name); //works ok

	//gid
	int gid = strToInt(hdr.ar_gid, 6);
	int gid2 = atoi(hdr.ar_gid);
	meta->gid = gid;
	//printf("meta gid %d\n", gid);

	//uid
	int uid = strToInt(hdr.ar_uid, 6);
	meta->uid = uid;

	//filling in mode
	int mode = strToInt(hdr.ar_mode, 8);
	meta->mode = mode;

	//filling in size
	int size = strToInt(hdr.ar_size, 10);
	meta->size = size;

	//mtime
	int t = (strToInt(hdr.ar_date, 12));
	meta->mtime = (long)t;
	//printf("meta mtime %ld\n", meta->mtime);
	return 0;
}



//adding members to archive
void append (char* afile, char*name) {
	//initialize
	int fd_afile;
	if (access(afile, F_OK)==-1) {
		fd_afile = open(afile, O_RDWR | O_CREAT, 0666);
		write(fd_afile, ARMAG, 8);

	}

	//this is basically append
    fd_afile = open(afile, O_RDWR | O_APPEND);
    lseek(*afile, 0, SEEK_END);
    struct ar_hdr head2;
    int name_hdr = fill_ar_hdr(name, &head2); // CLEAN each before writing into file
    write(fd_afile, &head2, sizeof(struct ar_hdr));    
    
    //writing in actual information of the file
    int namer = open(name, O_RDONLY);
    struct stat test;
	stat(name, &test);
    int size = (int)test.st_blksize; //upper bound?
	char buffer[size];
	int try = read(namer, &buffer, size); // read into namer size bytes 
	//printf("buffer is %s\n", buffer);
	//printf("size is %d\n", size);
	//printf("tr is %d\n", try);
	write(fd_afile, &buffer, try);


    //where = lseek(fd, 0, SEEK_END);
    //printf("The lseek is: %lld\n", where);

	close(fd_afile);
	//free(&fd_afile);


}

void appendAll (char *afile) {
	DIR *dp; 
	struct dirent *point;
	dp = opendir("./");
	while ((point = readdir(dp))) {
		//printf("%s", "true");
		if(point->d_type==DT_REG) {
			//printf("%s", "true");
			append(afile, point->d_name);
		}
	}


}


void contents (int fd_afile) {
	int pos = 8;
	struct ar_hdr head;
	int size;
	lseek(fd_afile, pos, SEEK_SET);
	while (read(fd_afile, &head, sizeof(struct ar_hdr)) == sizeof(struct ar_hdr)) {
	    printf("%.*s\n", 15, head.ar_name);
	    size = strtol(head.ar_size, NULL, 10);
	    lseek(fd_afile, size, SEEK_CUR);
	}
}


int mycmp (char* one, char* two) {
	int test = 1;
	//printf("asfasd%c\n", one[10]);
	for (int i = 0; i < 15; i++) {
		if (one[i] == '\0') {
			break;
		}
		if (one[i] != two[i]) {
			//printf("%d\n", i);
			test = 0;
			break;
		}
		
		//printf("one: %c\n tw:%c\n", one[i], two[i]);
	}
	//printf("%d\n", test);
	return test;
}


//clean the string of null bytes and pad
void fuckC (char* str, int size) {
	int temp = strlen(str);
	int pad = size - temp;
	if (pad == 0) { exit(0); };
	for(int i = temp; i < size; i++) {
		str[i] = ' ';
		//printf("%d\n", i);
		//printf("letter %c\n", str[i]);
	}

}

void FUCKC( char * str, char * name, int size) {
	for (int i = 0; i < size; i++) {
		if (str[i] == ' ' || str[i] == '\0' || str[i] == '\n') {
			break;
		}
		name[i] = str[i];
		
	}
}


void extract (int fd_afile, char*name) {
	// why is it reading everything into the first buffer?
	//if name is not in archive, make new file
	//printf("%s\n", name);
	struct ar_hdr ret;
	char store[16];
	int pos = 8;
    //ignoring !<arch>
	lseek(fd_afile, pos, SEEK_SET);
    int ext; //for storing new fd
    int size; //size of info
	
	while (read(fd_afile, &ret, sizeof(struct ar_hdr)) == sizeof(struct ar_hdr)){
	    //printf("clean: %s\n", "hi");
	    //printf("why %d\n", mycmp(name, ret.ar_name));
	    
	    //printf("name: %s\n", ret.ar_name);
	    memcpy(store, ret.ar_name, 15);
	    store[15] = '\0'; //ending
	    //printf("name: %s\n", store);
	    //printf("test %d\n", strcmp(name, store));

	  	if (mycmp(name, store) == 1) {
	  		
	  		//printf("Looking at ret%s\n", ret.ar_name);
	  		//printf("Looking at ret%s\n", ret.ar_gid);
	  		//printf("hi");
	  		//printf("%lld", lseek(fd_afile,0, SEEK_CUR));
	  		//printf("hello %s\n", "hi");
	  		break;
	  	}

	  	size = strtol(ret.ar_size, NULL, 10);
	    lseek(fd_afile, size, SEEK_CUR);
    }
    

    // filling in meta information from ret

    // I FUCKING HATE C
    //printf("%s\n", "FUCK C");


    struct meta met;
    fill_meta(ret, &met);
    //printf("met mode: %d\n", met.mode);
    ext = creat(name, met.mode);
    //printf("ext : %d\n", ext);
    //printf("name: |%s|\n", name);
    
    char temp[met.size];
    read(fd_afile, temp, met.size);
    //printf("into temp: %s\n", temp);
    write(ext,temp,met.size);

    //setting the time
    struct utimbuf time;
    time.actime = met.mtime;
    time.modtime = met.mtime;
    utime(name,&time);



}


//sscanf, sprintf, are allowed, and open
int main (int argc, char *argv[]) {

    //printf("%s\n", "moving on to understanding how to build the rest");
   	//initialize
    char* key = argv[1];
    char key1 = key[0];
    char* afile = argv[2];
    char* name = argv[3];
    int fd_afile;
    fd_afile = open(afile, O_RDONLY);
    
    //code something so that the flags do something
    if (*key == 'q') {
    	//printf("%s\n", "you used flag q");
    	//append (fd_afile, name);
    	append(afile, name);
    } else if (key1 == 'x') {
    	//printf("%s\n", "you used the flag xo" );
    	extract (fd_afile, name);
    } else if (*key == 't') {
    	//printf("%s\n", "flag t" );
    	contents (fd_afile);
    } else if (*key == 'A') {
    	//printf("%s\n", "flag A");
    	appendAll(afile);
    	// append all regular files in current directory
    }
    
    //reading into a buffer and then printing the buffer? 
    
    close(fd_afile);

    //need a while loop now and need to increment 7 by size of 

}

