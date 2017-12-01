#!/bin/bash
#global.sh

for file in ./DATA/matrix-*.txt
do
	./ipl.sh ${file}
done

