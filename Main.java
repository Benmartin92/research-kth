/**
 * 
 */
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.*;
import org.jgrapht.generate.CompleteBipartiteGraphGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
/**
 * @author Marine
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// generator graph
		CompleteBipartiteGraphGenerator generator = new CompleteBipartiteGraphGenerator(1, 2);
		
		DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		
		//generator.generateGraph(directedGraph, arg1, arg2);
		
		//construct acyclic 3 bounded orientation

	}

}
