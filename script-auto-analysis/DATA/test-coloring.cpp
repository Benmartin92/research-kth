//
//    Author: Benjamin Seregi <seregi@kth.se>
//    This program reads an adjacency matrix and the colors assigned to each node
//    in a graph G=(V,E).
//
//    It checks whether the coloring is good, that is, if (u,v) \in E, then
//    c(u) != c(v), where the function c:E->N represents the color c(v) of
//    node v.
//
//    A sample input format for this program is as follows:
//      N
//      *An adjacency matrix*
//      16 78 58 39 19 42 22 61 80 64 44 24 82 67 47 27 9 70 50 30 12 15 1 3 5 72 52 33 75 55 36
//      37 17 79 59 40 20 23 62 43 81 65 45 25 83 7 68 48 28 10 51 31 13 2 4 6 71 73 53 34 76 56
//      26 57 8 38 69 18 49 29 60 11 41 21 32 14 63 74 54 35 66 46 77
//
//    The first integer represents the number of nodes that is followed by an adjacency matrix, that is,
//    an N*N matrix with 0-1 entries.
//    After the adjacency matrix there are n lines. Line i with the integers 16, 78, 58, etc. means that
//    node 16, 78, 58, etc. have been colored with Color i. The integers 16, 78, etc. match the corresponding line in
//    the adjacency matrix.
//
//    The output of the program is simply "CORRECT" if the coloring is good, and "NOT CORRECT" otherwise.
//    How to run it? ./test-coloring <your_input_file>
//
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>

std::vector<int> readColors(int numNodes, std::ifstream& file)
{
    std::string line;
    int color = 1;
    std::vector<int> c(numNodes);
    while (getline(file, line)) {
         std::stringstream ss(line);
         int node;
         while (ss >> node)
            c[node-1] = color;
         color++;
    }
    return c;
}

std::vector<std::vector<int> > readAdjacencyMatrix(int numNodes, std::ifstream& file) {
    std::string line;
    std::vector<std::vector<int> > adjacencyMatrix(numNodes);
    for (int i = 0; i < numNodes; i++)
        adjacencyMatrix[i].resize(numNodes);
    for (int i = 0; i < numNodes; i++) {
        getline(file, line);
        std::stringstream ss(line);
        int connected;
        int j = 0;
        while (ss >> connected) {
            adjacencyMatrix[i][j] = connected;
            j++;
        }
    }
    return adjacencyMatrix;
}

bool testColoring(std::vector<std::vector<int> >& adjacencyMatrix, std::vector<int>& coloring) {
    int numNodes = adjacencyMatrix.size();
    for (int i = 0; i < numNodes; i++) {
        for (int j = 0; j < numNodes; j++) {
            if (adjacencyMatrix[i][j] == 1 && coloring[i] == coloring[j])
                return false;
        }
    }
    return true;
}

int main(int argc, char *argv[]) {
    std::ifstream file(argv[1]);
    if (file.is_open()) {
        std::string line;
        getline(file, line);
        int numNodes = std::stoi(line);
        std::cout << "Reading adjacency matrix..." << std::endl;
        auto adjMatrix = readAdjacencyMatrix(numNodes, file);
        std::cout << "Reading colors..." << std::endl;
        auto coloring = readColors(numNodes, file);
        std::cout << "Test coloring..." << std::endl;
        if (testColoring(adjMatrix, coloring))
            std::cout << "CORRECT" << std::endl;
        else
            std::cout << "NOT CORRECT" << std::endl;
        return 0;
    }
    else {
        std::cout << "Error while opening the file" << std::endl;
        return 1;
    }
}

