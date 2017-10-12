import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.generate.LinearGraphGenerator;
import org.jgrapht.generate.RingGraphGenerator;
import org.jgrapht.graph.GraphUnion;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.UndirectedGraphUnion;

public class CellularGraphGenerator {
	private int nbrEdge;
	private int nbrCells;
	private RingGraphGenerator<String, DefaultEdge> generator;
	private UndirectedGraph <String, DefaultEdge> finalGraph;
	private List<DefaultEdge> availableEdge;
	private int nbrVertex;
	
	
	
	
	public CellularGraphGenerator(int nbrCells) {
		super();
		this.nbrCells = nbrCells;
		this.nbrEdge = 6;
		this.nbrVertex = this.nbrEdge;
		this.generator = new RingGraphGenerator<>(this.nbrEdge);
		
		this.availableEdge = new ArrayList<>();
		
		this.finalGraph = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
		
	} 
	
	private void addCell(){
		///choose randomly the edge to be attached
		int max = this.availableEdge.size();
		Random rand = new Random();
		int rdmNbr = rand.nextInt(max);
		
		DefaultEdge edge = this.availableEdge.get(rdmNbr);
		// Case if only one Border
		if (this.finalGraph.edgesOf(this.finalGraph.getEdgeSource(edge)).size() < 3 && this.finalGraph.edgesOf(this.finalGraph.getEdgeTarget(edge)).size() < 3){
			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+1));
			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+2));
			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+3));
			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+4));
			this.finalGraph.addEdge(this.finalGraph.getEdgeSource(edge),Integer.toString(this.nbrVertex+1));
			this.finalGraph.addEdge(Integer.toString(this.nbrVertex+1),Integer.toString(this.nbrVertex+2));
			this.finalGraph.addEdge(Integer.toString(this.nbrVertex+2),Integer.toString(this.nbrVertex+3));
			this.finalGraph.addEdge(Integer.toString(this.nbrVertex+3),Integer.toString(this.nbrVertex+4));
			this.finalGraph.addEdge(Integer.toString(this.nbrVertex+4),this.finalGraph.getEdgeTarget(edge));
			
			this.availableEdge.remove(this.availableEdge.indexOf(edge));
			this.availableEdge.add(this.finalGraph.getEdge(this.finalGraph.getEdgeSource(edge),Integer.toString(this.nbrVertex+1)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+1),Integer.toString(this.nbrVertex+2)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+2),Integer.toString(this.nbrVertex+3)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+3),Integer.toString(this.nbrVertex+4)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+4),this.finalGraph.getEdgeTarget(edge)));
			
			this.nbrVertex = this.nbrVertex+4;
		}
		
		// TODO: all the other case !! without forgetting update available edges

	}
	
	public UndirectedGraph <String, DefaultEdge> generateFinalGraph(){
		
		//initial value of the final graph (=1 cell)
		Map<String, String> resultMap = new HashMap<>();
		this.generator.generateGraph(this.finalGraph, new StringVertexFactory(), resultMap);
		

		for(DefaultEdge e : this.finalGraph.edgeSet()){
			this.availableEdge.add(e);
		}
		for (int i1=1; i1<this.nbrCells; i1++){
			addCell();
		}
		return this.finalGraph;
	}
	
	
}
