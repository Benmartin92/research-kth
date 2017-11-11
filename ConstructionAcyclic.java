import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.Pseudograph;

public class ConstructionAcyclic {
	private UndirectedGraph<String, DefaultEdge> undirectedGraph;
	private Map<String, Integer> functionF1;
	private Map<String, Integer> functionF2;
	
	
	/**
	 * Constructor
	 * @param directedGraph
	 * @param functionF
	 */
	
	public ConstructionAcyclic(UndirectedGraph<String, DefaultEdge> undirectedGraph) {
		super();
		this.undirectedGraph = undirectedGraph;
		this.functionF1 = new HashMap<String, Integer>(this.undirectedGraph.vertexSet().size());
		this.functionF2 = new HashMap<String, Integer>(this.undirectedGraph.vertexSet().size());
		
		for (String p : this.undirectedGraph.vertexSet()){
			this.functionF1.put(p, 0);
			this.functionF2.put(p, 0);
		}
		
	}
	
	/**
	 * Transform an undirected cellular graph into a directed acyclic graph
	 * @return
	 */
	public DirectedAcyclicGraph<String, DefaultEdge> returnH(){
	//public DirectedGraph<String, DefaultEdge> returnH(){
		int i=0;
		
		//Copy the undirectedGraph
		UndirectedGraph<String, DefaultEdge> undirectedGraphCopy = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
		for (String p : this.undirectedGraph.vertexSet()){
			undirectedGraphCopy.addVertex(p);
		}
		
		for(DefaultEdge e : this.undirectedGraph.edgeSet()){
			undirectedGraphCopy.addEdge(this.undirectedGraph.getEdgeSource(e), this.undirectedGraph.getEdgeTarget(e));
		}
		
		// Compute function F
//		for (String p : undirectedGraphCopy.vertexSet()){
//			i=i+1;
//			int degree = this.undirectedGraph.degreeOf(p);
//			if(degree <= 3){
//				this.functionF1.put(p, i);
//				this.undirectedGraph.removeVertex(p);
//			}
//		}
		
		// Compute function F
		for (int j=0; j < this.undirectedGraph.vertexSet().size(); j++){
			for (DefaultEdge e : undirectedGraph.edgeSet()){
				int deg1= this.undirectedGraph.degreeOf(this.undirectedGraph.getEdgeSource(e));
				int deg2=this.undirectedGraph.degreeOf(this.undirectedGraph.getEdgeTarget(e));
				String s=this.undirectedGraph.getEdgeSource(e);
				String t=this.undirectedGraph.getEdgeTarget(e);
				if (deg1 <= 3 && undirectedGraphCopy.containsVertex(s)){
					this.functionF2.put(s, j);
					undirectedGraphCopy.removeVertex(s);
					break;
				}
				else if (deg2 <= 3 && undirectedGraphCopy.containsVertex(t)){
					this.functionF2.put(t, j);
					undirectedGraphCopy.removeVertex(t);
					break;
				}
			}
		}
		
		
		
		
		
		// Initialize the directed acyclic graph to be returned
		DirectedAcyclicGraph<String, DefaultEdge> directedGraphH = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		//DirectedGraph<String, DefaultEdge> directedGraphH = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		for(String p : this.undirectedGraph.vertexSet()){
			directedGraphH.addVertex(p);
		}
		
		// the directed acyclic graph edges
		int m=0;
		System.out.println("size: "+this.undirectedGraph.edgeSet().size());
		for(DefaultEdge e : this.undirectedGraph.edgeSet()){
			String u = this.undirectedGraph.getEdgeSource(e);
			String v = this.undirectedGraph.getEdgeTarget(e);
			
			if(functionF2.get(u) < functionF2.get(v)){
				System.out.println(u+","+v);
				directedGraphH.addEdge(u, v);
			}
			else{
				System.out.println(v+","+u);
				directedGraphH.addEdge(v, u);
			}
			m=m+1;
			System.out.println(m+"\n");
		}
		return directedGraphH;
	}
	
	
	
}
