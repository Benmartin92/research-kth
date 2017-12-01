#!/bin/bash

for (( N = 5; N < 20; N++ )); do
	java -jar test.jar $N 4
done


for (( C = 4; C < 20; C++ )); do
	java -jar test.jar 20 $C
done


for file in ./coloration-*.txt
do
	correct=$(./test-coloring ${file} 2>&1)
	# echo ${correct}
	if [[ $correct == *"NOT CORRECT"* ]]; then
  		echo "wrong"
		ext=${file:12} 

		mkdir wrong
		filesToMove=`find . -maxdepth 1 -type f -name "*${ext}"`
		echo ${filesToMove}
		for line in $filesToMove ; do
  			mv $line wrong
  		done
	fi
done