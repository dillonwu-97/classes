#/bin/bash

# This program prints the number of unique permissions in a directory
# This program is for Linux (stat command doesn't exist on Mac)
# Usage: ./exercise-dillon $directory

#!/bin/bash
echo $(( $(ls -l $1 | cut -d ' ' -f1 | sort | uniq | wc -l)-1 ))
