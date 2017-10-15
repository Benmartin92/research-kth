import java.util.HashMap;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.Pseudograph;

public class ConstructionAcyclic {
	private UndirectedGraph<String, DefaultEdge> undirectedGraph;
	private Map<String, Integer> functionF;
	
	
	/**
	 * Constructor
	 * @param directedGraph
	 * @param functionF
	 */
	
	public ConstructionAcyclic(UndirectedGraph<String, DefaultEdge> undirectedGraph) {
		super();
		this.undirectedGraph = undirectedGraph;
		this.functionF = new HashMap<String, Integer>(this.undirectedGraph.vertexSet().size());
		
		for (String p : this.undirectedGraph.vertexSet()){
			this.functionF.put(p, 0);
		}
		
	}
	
	/**
	 * Transform an undirected cellular graph into a directed acyclic graph
	 * @return
	 */
	public DirectedAcyclicGraph<String, DefaultEdge> returnH(){
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
		for (String p : undirectedGraphCopy.vertexSet()){
			i=i+1;
			int degree = this.undirectedGraph.degreeOf(p);
			if(degree <= 3){
				this.functionF.put(p, i);
				this.undirectedGraph.removeVertex(p);
			}
		}
		
		// Initialize the directed acyclic graph to be returned
		DirectedAcyclicGraph<String, DefaultEdge> directedGraphH = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		for(String p : undirectedGraphCopy.vertexSet()){
			directedGraphH.addVertex(p);
		}
		
		// the directed acyclic graph edges
		for(DefaultEdge e : undirectedGraphCopy.edgeSet()){
			String u = undirectedGraphCopy.getEdgeSource(e);
			String v = undirectedGraphCopy.getEdgeTarget(e);
			
			if(functionF.get(u) < functionF.get(v)){
				directedGraphH.addEdge(u, v);
			}
			else{
				directedGraphH.addEdge(v, u);
			}
		}
		return directedGraphH;
	}
	
	
	
}
