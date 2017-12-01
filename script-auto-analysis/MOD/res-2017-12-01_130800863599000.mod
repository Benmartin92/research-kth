/* PARAMETERS */

/* number of nodes */
param n, integer, > 0;
/* number of colors */
param c, integer, > 0;
/* node indices */
set nodes_index := 1..n;
/* color indices */
set colors_index := 1..c;
/* this specifies the list of available colors for each node, i.e.,
   color_lists[i,j] = 1 if and only if v_i has the color c_j
                      0 otherwise
*/
param color_lists{nodes_index, colors_index} binary;
/* the adjacency matrix of the input graph */
param adj_matrix{nodes_index, nodes_index} binary;

/* VARIABLES */

var coloring{nodes_index, colors_index} binary; /* 1 if node v_i is assigned color c_k and 0 otherwise */

/* CONSTRAINTS */

/*
  coloring[i,j] * color_lists[i,j] = 1 if and only if v_i has been colored with c_j and v_i has the color c_j
  This merges constraint (2) and (3) (see [1] A. Optimal Solution using Integer Liner Programming).
*/
s.t. using_one_of_the_colors_from_the_lists{i in nodes_index}:
    sum{j in colors_index} coloring[i,j] * color_lists[i,j] = 1;
/*
  if v_i and v_j are neighbors then coloring[i,k] + coloring[j,k] <= 1 for all colors k
*/
s.t. neighbors_have_different_color{k in colors_index, i in nodes_index, j in nodes_index}:
    adj_matrix[i,j] * (coloring[i,k] + coloring[j,k]) <= 1;

solve;

for {i in colors_index} {
    for {j in nodes_index} {
        printf (if coloring[j,i] == 1 then "%d " else ""), j;
    }
    printf "\n";
}

data;
param n := 16;

param c := 4;

param adj_matrix
: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 :=
1 0 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 
2 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 
3 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 
4 0 0 0 0 1 0 1 1 0 0 0 0 0 0 0 0 
5 0 0 0 0 0 1 0 1 1 0 0 0 0 0 0 0 
6 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 
7 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 0 
8 0 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 
9 0 0 0 0 0 0 0 0 0 1 0 0 1 1 0 0 
10 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 
11 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 
12 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1 
13 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 
14 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
15 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
16 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ;

param color_lists
: 1 2 3 4 :=
1 1 1 1 1 
2 1 1 1 1 
3 1 1 1 1 
4 1 1 1 1 
5 1 1 1 1 
6 1 1 1 1 
7 1 1 1 1 
8 1 1 1 1 
9 1 1 1 1 
10 1 1 1 1 
11 1 1 1 1 
12 1 1 1 1 
13 1 1 1 1 
14 1 1 1 1 
15 1 1 1 1 
16 1 1 1 1 ;

end;