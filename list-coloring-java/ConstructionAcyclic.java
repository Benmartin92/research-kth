import java.util.HashMap;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.Pseudograph;

public class ConstructionAcyclic {
	private UndirectedGraph<String, DefaultEdge> undirectedGraph;
	private Map<String, Integer> functionF1;
	
	
	/**
	 * Constructor
	 * @param directedGraph
	 * @param functionF
	 */
	
	public ConstructionAcyclic(UndirectedGraph<String, DefaultEdge> undirectedGraph) {
		super();
		this.undirectedGraph = undirectedGraph;
		this.functionF1 = new HashMap<String, Integer>(this.undirectedGraph.vertexSet().size());
		
		for (String p : this.undirectedGraph.vertexSet()){
			this.functionF1.put(p, 0);
		}
		
	}
	
	/**
	 * Transform an undirected cellular graph into a directed acyclic graph
	 * @return
	 */
	public DirectedAcyclicGraph<String, DefaultEdge> returnH(){
	//public DirectedGraph<String, DefaultEdge> returnH(){
		
		//Copy the undirectedGraph
		UndirectedGraph<String, DefaultEdge> undirectedGraphCopy = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
		for (String p : this.undirectedGraph.vertexSet()){
			undirectedGraphCopy.addVertex(p);
		}
		
		for(DefaultEdge e : this.undirectedGraph.edgeSet()){
			undirectedGraphCopy.addEdge(this.undirectedGraph.getEdgeSource(e), this.undirectedGraph.getEdgeTarget(e));
		}
		
		
		//We have to make sure that there are no "double" edges
		for (String s1 : undirectedGraphCopy.vertexSet()){
			for (String s2 : undirectedGraphCopy.vertexSet()){
				if (undirectedGraphCopy.containsEdge(s1, s2) && undirectedGraphCopy.containsEdge(s2, s1)){
					undirectedGraphCopy.removeEdge(s1, s2);
				}
			}
		}
		
		// Compute function F
		for (int j=0; j < this.undirectedGraph.vertexSet().size(); j++){
			for (String s : undirectedGraphCopy.vertexSet()){
				int deg1 = undirectedGraphCopy.degreeOf(s);
				if (deg1 <= 3){
					this.functionF1.put(s, j);
					undirectedGraphCopy.removeVertex(s);
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
		
		for(DefaultEdge e : this.undirectedGraph.edgeSet()){
			String u = this.undirectedGraph.getEdgeSource(e);
			String v = this.undirectedGraph.getEdgeTarget(e);
			int fU = functionF1.get(u);
			int fV = functionF1.get(v);
			if(fU < fV){
				//System.out.println(u+","+v);
				directedGraphH.addEdge(u, v);
			}
			else{
				//System.out.println(v+","+u);
				directedGraphH.addEdge(v, u);
			}
			m=m+1;
		}
		return directedGraphH;
	}
	
	
	
}
