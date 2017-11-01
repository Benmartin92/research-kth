/*
    Author: Benjamin Martin Seregi <seregi@kth.se>

    Implementation of the ILP of the list coloring problem based on the paper of R. Wang, at al.:
    [1] R. Wang, C. Zhou, A. Mazumder, A. Das, H. A. Kierstead, and A. Sen, “Upper and lower bounds of
    choice number for successful channel assignment in cellular networks,” in 2015 IEEE International
    Conference on Communications (ICC), June 2015, pp. 3370–3375.
    Available: https://doi.org/10.1109/ICC.2015.7248845

    Modifications that I made:
        - I merged condition (2) and (3), see below. It means that we have less inequalities.
        - This is intended to use for the list coloring of 1-band buffering (not necessarily cellular) graphs, therefore
          the "2-hops constraint" has been omitted. Instead, we use a 1-hop constraint.

    How to run this? This program requires GLPK (https://www.gnu.org/software/glpk/) which is a free
    linear programming kit. Once you have installed GLPK on your computer, just type:

        glpsol --cuts --pcost -m <name_of_this_file>.mod
*/

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

data;
end;
