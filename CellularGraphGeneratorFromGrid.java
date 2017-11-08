import java.util.HashMap;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.Pseudograph;

public class CellularGraphGeneratorFromGrid {
	private GridUndirectedGraphGenerator<String, DefaultEdge> generatorGrid;
	private UndirectedGraph <String, DefaultEdge> finalGraph;
	int rows;
	int cols;

	
	public CellularGraphGeneratorFromGrid(int rows, int cols) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.generatorGrid = new GridUndirectedGraphGenerator<>(this.rows,this.cols);
		this.finalGraph = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
		
	}
	
	private int next(int previous){
		if (previous != 1)
			return previous-1;
		else
			return 3;
	}
	
	
	/**
	 * Genrate an Undirected Cellular Graph based on a grid
	 * @return
	 */
	public UndirectedGraph <String, DefaultEdge> generateFinalGraph(){
		
		Map<String, String> resultMap = new HashMap<>();
		this.generatorGrid.generateUndirectedGraph(this.finalGraph, new StringVertexFactory(), resultMap);
		int N = this.cols; // N
		int M = this.rows;
		int j = 1;
		int i = 1;
		int next = 1;
		//for(int i=1; i<N*N-2*N;i=i+3){
		while(i < N*M+1){
			boolean build = true;
			boolean newLine = false;
			
			
			//Test build is possible
			
			// last column
			if (i+2 > N*j){
				build = false;
				newLine = true;
			}
			
			//last row
			if (i + 2*N > N*(M-1)+i%N){
				build = false;
			}
			
			// up right coin should be taken care of by the while condition
			
			if (build){
				this.finalGraph.removeVertex(Integer.toString(i+1+N));
				this.finalGraph.addEdge(Integer.toString(i+1), Integer.toString(i+N+2));
				this.finalGraph.addEdge(Integer.toString(i+N), Integer.toString(i+2*N+1));
				
				
				// first row + normal
				if (i < N ){
					this.finalGraph.removeVertex(Integer.toString(i+2));
				}
				// last column + normal
				if (i+2 == N*j){
					this.finalGraph.removeVertex(Integer.toString(i+2));
				}
				// first column + normal
				if (i == N*(j-1)+1){
					this.finalGraph.removeVertex(Integer.toString(i+2*N));
				}
				
				// first column + end of the column
//				if (i+4*N >= N*(N-1)+1){
//					this.finalGraph.removeVertex(Integer.toString(N*(N-1)+1));
//					this.finalGraph.removeVertex(Integer.toString(N*(N-1)+2));
//					if (i==N*(N-1)+1-4*N){
//						this.finalGraph.removeVertex(Integer.toString(N*(N-1)+1-N));
//					}
//				}
				
				// last row + normal
				if (i + 2*N == N*(M-1)+i%N){
					this.finalGraph.removeVertex(Integer.toString(i + 2*N));
				}
			
			}
			else{
				// first row + end of the row
				if (i <= N){
					
					if(i==N){
						this.finalGraph.removeVertex(Integer.toString(2*N));
						this.finalGraph.removeVertex(Integer.toString(N));
					}
					else if (i==N-1){
						this.finalGraph.removeVertex(Integer.toString(N-1));
						this.finalGraph.removeVertex(Integer.toString(N));
					}
				}
				
				// last row + end of the row
				if (i == N*M-2*N-1){
					this.finalGraph.removeVertex(Integer.toString(N*N));
					this.finalGraph.removeVertex(Integer.toString(N*N-N));
					this.finalGraph.removeVertex(Integer.toString(N*N-1));
				}
				else if (i == N*M-2*N){
					this.finalGraph.removeVertex(Integer.toString(N*N));
				}
				
				//first column + end of the column
				
				if (i == N*(M-1)+1){
					this.finalGraph.removeVertex(Integer.toString(N*(N-1)+1));
					this.finalGraph.removeVertex(Integer.toString(N*(N-1)+2));
				}
				else if(i == N*(M-2)+1){
					this.finalGraph.removeVertex(Integer.toString(N*(N-2)+1));
					this.finalGraph.removeVertex(Integer.toString(N*(N-1)+1));
				}
				
			}
			
			
			if (newLine || i+2 == N*j){
				i= N*j + next(next);
				next = next(next);
				j = j+1;
			}
			else{
				i = i+3;
			}
			
			

//			// first row + coin Down Right + down left
//			if(i < N){
//				if (i+1 !=N){
//					
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));
//					
//					this.finalGraph.removeVertex(Integer.toString(i+2));
//					this.finalGraph.addEdge(Integer.toString(i+1), Integer.toString(i+N+2));
//					this.finalGraph.addEdge(Integer.toString(i+N), Integer.toString(i+2*N+1));
//					
//					if (i==1){
//						this.finalGraph.removeVertex(Integer.toString(i+2*N));
//						
//						//if N=4 we have to take are of the up left coin
//						if(i+3*N==N*(N-1)+1){
//							this.finalGraph.removeVertex(Integer.toString(i+3*N));
//							this.finalGraph.removeVertex(Integer.toString(i+3*N+1));
//						}
//						
//					}
//					//not else if because case N=4
//					if(i+3==N){
//						this.finalGraph.removeVertex(Integer.toString(i+3));
//						this.finalGraph.removeVertex(Integer.toString(i+3+N));
//					}
//				}
//				else {
//					this.finalGraph.removeVertex(Integer.toString(i+1));
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));
//					this.finalGraph.removeVertex(Integer.toString(i));
//				}
//			}
//			//last row (or almost) + coin UpRight
//			else if(i > N*(N-3) && i <= N*(N-2)){
//				if(i+1+2*N !=N*N){
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));
//					
//					this.finalGraph.removeVertex(Integer.toString(i+2*N));
//					this.finalGraph.addEdge(Integer.toString(i+1), Integer.toString(i+N+2));
//					this.finalGraph.addEdge(Integer.toString(i+N), Integer.toString(i+2*N+1));
//					
//					if(i+3+2*N == N*N){
//						this.finalGraph.removeVertex(Integer.toString(N*N));
//					}
//				}
//				else{
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));
//					this.finalGraph.removeVertex(Integer.toString(i+2*N));
//					this.finalGraph.removeVertex(Integer.toString(N*N));
//				}
//			}
//			//first column + coin Up Left
//			else if (i % N == 1){
//				
//				this.finalGraph.removeVertex(Integer.toString(i+1+N));
//				this.finalGraph.removeVertex(Integer.toString(i+2*N));
//				this.finalGraph.addEdge(Integer.toString(i+1), Integer.toString(i+N+2));
//				this.finalGraph.addEdge(Integer.toString(i+N), Integer.toString(i+2*N+1));
//				
//				
//				if(i+3*N==N*(N-1)+1){
//					this.finalGraph.removeVertex(Integer.toString(i+3*N));
//					this.finalGraph.removeVertex(Integer.toString(i+3*N+1));
//				}
//				else if (i+4*N == N*(N-1)+1){
//					this.finalGraph.removeVertex(Integer.toString(i+3*N));
//					this.finalGraph.removeVertex(Integer.toString(i+4*N));
//				}
//					
//			}
//			//last column (or almost)
//			else if( (i+3) >= N*j){
//				if (i+1 == N*j){
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));	
//				}
//				else if (i != N*j){
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));
//					
//					this.finalGraph.addEdge(Integer.toString(i+1), Integer.toString(i+N+2));
//					this.finalGraph.addEdge(Integer.toString(i+N), Integer.toString(i+2*N+1));
//				}
//				if ( (i+2) >= N*j){
//					j=j+1;
//				}
//			}
//			else{
//				if(i!=N*j){
//					this.finalGraph.removeVertex(Integer.toString(i+1+N));
//					this.finalGraph.addEdge(Integer.toString(i+1), Integer.toString(i+N+2));
//					this.finalGraph.addEdge(Integer.toString(i+N), Integer.toString(i+2*N+1));
//				}
//			}
		}
		return this.finalGraph;
	}
	
	
}
