#!/bin/bash


if [ $# -eq 0 ] # s'il n'y a pas de paramètres
   then echo "Enter the path of the coloration file"
   		read coloration # on saisis la valeur
else
    coloration=$1 # path récupère le contenue de $1, le premier paramètre
    echo ${coloration};
fi

nbrColor=`echo ${coloration} | awk -F - '{print $3}'`
N=`echo ${coloration} | awk -F - '{print $2}'`

ext=${coloration:17}


listColorToVFinal=`echo "./ILP/listColorToVFinal${ext}_ILP.txt"`


result=$(./compare-coloration ${coloration} ${listColorToVFinal} 2>&1 | grep "Result" | awk '{print $2}')
echo ${result}

if [[ $result == "SAME" ]] || [[ $result == "DIFFERENT" ]]; then
	if [[ $nbrColor -eq 4 ]]; then
		echo "${result}; ${N}" >> colorComparisonN.txt
	fi

	if [[ $N -eq 40 ]]; then
		echo "${result}; ${nbrColor}" >> colorComparisonNbrColor.txt
	fi
fi
