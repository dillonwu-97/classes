#!/bin/bash

FILES=./temp_res/*
write_to=./test_files.txt

# delete file if it exists
rm -f $write_to

for f in $FILES
do
	if [[ $f != *"["*"]"* ]]
	then 
		echo $f >> $write_to
	fi
done

cat $write_to | ./a.out

while read i; do
	echo "---------------------------------  NEW FILE  ---------------------------------------------------"
	wc -l "./temp_res/[output]-${i:11}" "./my-${i:11}"
	diff "./temp_res/[output]-${i:11}" "./my-${i:11}"
done <$write_to

