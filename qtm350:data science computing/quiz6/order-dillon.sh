# This outputs input in sorted order
#!/bin/bash
for x in "$@";do
	echo "$x"
done | sort
