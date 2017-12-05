#!/bin/bash
#global.sh


#new_Files=`find ./DATA -type f -name "matrix-4-4-2017-12-04T22*"`

for file in ./DATA/matrix-*.txt
#for file in $new_Files
do
	./ipl.sh ${file}
done

