# Emory Fall 2019: CS350 Systems Programming 
## HW1
I wrote a program which takes a number n and computes a bitmap of primes less than or equal to n using the Sieve of Eratosthenes, and then prints out the number of primes found. It then reads a pair of integers k and m, and calculates the number of triple primes of the form (n, n+k, n+m) and outputs the largest tuple.

## HW2 
I implemented a modified version of the Linux ar command with '-q', '-x', '-t', and '-A' options. 
'-q' appends named files to the archive
'-x' extracts named files with the meta data
'-t' prints a concise table of contents of the archive
'-A' appends all regular files in the current directory

## HW3
The point of this program is to practice interprocess communication using pipes. 
The program first processes a file from stdin filtering out nonalphabetical words, words shorter than 5 characters, longer than 32 characters, and then converts all words to lower case. Any nonalphabetical characters interlaced between alphabetical characters are ignored.
Second, the program makes an exec call to /bin/sort to sort the words in alphabetical order.
Finally, all duplicates are removed and the remaining words are output to stdout. 

## HW4
The point of this program is to practice interprogram communication using message queues and shared memory. 
There are three programs: compute.c, manage.c, and report.c.
Compute.c computes perfect numbers and and sends the numbers to the "manage" process. More than one instance of compute.c can be run at a given time.
Manage.c updates the shared memory segment with perfect numbers and tracks the active "compute" processes so that it can signal for them to terminate.
Report.c reads from shared memory and outputs the perfect numbers tested, skipped, and found by each "compute.c" process. 
