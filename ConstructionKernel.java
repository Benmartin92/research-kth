import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graphs;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

public class ConstructionKernel {
	private DirectedAcyclicGraph<String, DefaultEdge> directedAcyGraph;
	private Map<String, Boolean> marked;
	
	public ConstructionKernel(DirectedAcyclicGraph<String, DefaultEdge> directedAcyGraph){
		super();
		this.directedAcyGraph = directedAcyGraph;
		this.marked = new HashMap<>();
		for(String p : this.directedAcyGraph.vertexSet()){
			this.marked.put(p, false);
		}
	}
	
	
	public Set<String> returnKernel(){
		TopologicalOrderIterator<String, DefaultEdge> iteratorT = new TopologicalOrderIterator<>(this.directedAcyGraph);
		Set<String> kernel = new HashSet<>();

		while(iteratorT.hasNext()){
			String next = iteratorT.next();
			if(!this.marked.get(next)){
				kernel.add(next);
				//mark all neighbors
				for(String p : Graphs.neighborListOf(this.directedAcyGraph, next)){
					this.marked.put(p, true);
				}
			}
		}
		return kernel;
		
	}
}
