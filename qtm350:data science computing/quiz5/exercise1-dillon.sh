#/bin/bash

# This program prints the number of unique permissions in a directory
# This program is for Linux (stat command doesn't exist on Mac)
# Usage: ./exercise-dillon $directory

function get_stats() {
        cd $1
        for file in *;
                do stat -c %a ${file};
        done
}

get_stats | uniq -u | wc -l 
