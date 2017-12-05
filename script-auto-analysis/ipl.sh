#!/bin/bash

if [ $# -eq 0 ] # s'il n'y a pas de paramètres
   then echo "Enter the path of the matrix file"
   		read matrix # on saisis la valeur
else
    matrix=$1 # path récupère le contenue de $1, le premier paramètre
    echo ${matrix};
fi


actual_date=$(gdate +%Y-%m-%d_%H%M%S%N)
#echo ${actual_date}

# mkdir ./MOD
# mkdir ./ILP

cat 1.txt ${matrix} > ./MOD/res-${actual_date}.mod

nbrColor=`echo ${matrix} | awk -F - '{print $3}'`
N=`echo ${matrix} | awk -F - '{print $2}'`

ext=${matrix:13}

#echo ${N}
#echo ${nbrColor} 


#Java
timeInNano=$(cat ./DATA/listColorToVFinal${ext} | grep "Took" | awk '{print $2}')
#echo ${timeInNano} 


#ILP
glpsol --cuts --pcost -m ./MOD/res-${actual_date}.mod > temp.txt


timeInSec=$(cat temp.txt | grep "Time used:" | awk '{print $3}')
#echo ${timeInSec}

if [[ $nbrColor -eq 4 ]]; then
	echo "${timeInSec}; ${N}" >> timeN_ILP.txt
	echo "${timeInNano}; ${N}" >> timeN.txt
fi

if [[ $N -eq 40 ]]; then
	echo "${timeInSec}; ${nbrColor}"
	echo "${timeInNano}; ${nbrColor}"
	echo "${timeInSec}; ${nbrColor}" >> timeColor40_ILP.txt
	echo "${timeInNano}; ${nbrColor}" >> timeColor40.txt
fi


nbrLines=$(wc -l temp.txt| awk '{print $1}')

lig1=$(($nbrLines-$nbrColor))
lig2=$((${nbrLines}-1))
# echo ${lig1}
# echo ${lig2}
sed -n ${lig1},${lig2}p temp.txt > ./ILP/listColorToVFinal${ext}_ILP.txt

rm temp.txt
rm ./MOD/res-${actual_date}.mod