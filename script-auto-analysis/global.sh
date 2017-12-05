#!/bin/bash
#global.sh


# new_Files=`find ./DATA -type f -name "matrix-40-4-*"`

# # for file in ./DATA/matrix-*.txt
# for file in $new_Files
# do
# 	./ipl.sh ${file}
# done

for file in ./DATA/coloration-*.txt
#for file in $new_Files
do
	bash compareColoration.sh ${file}
done

