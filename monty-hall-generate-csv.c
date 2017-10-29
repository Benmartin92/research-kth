//
//    Generate data for the Monty Hall Problem
//
//    How to compile it:
//        gcc monty-hall-generate-csv.c -o monty-hall-generate-csv
//    How to use it?
//        ./monty-hall-generate-csv <number-of-samples> <outputfile.csv>
//
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    int numOfSamples = atoi(argv[1]);
    FILE *file = fopen(argv[2], "w");
    if (file == NULL) {
        printf("Error creating a file");
        return 1;
    }
    else {
        fprintf(file, "CarDoor,Selected,S,NS\n");
        for (int i = 0; i < numOfSamples; i++) {
            int carDoor = rand() % 3 + 1;
            int selectedDoor = rand() % 3 + 1;
            if (carDoor == selectedDoor)
                fprintf(file, "d%d,d%d,L,W\n", carDoor, selectedDoor);
            else
                fprintf(file, "d%d,d%d,W,L\n", carDoor, selectedDoor);
        }
        fclose(file);
    }
    return 0;
}

