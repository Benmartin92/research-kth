import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.generate.RingGraphGenerator;
import org.jgrapht.graph.Pseudograph;


/**
 * Class that will generate our Cellular Graphs for the tests
 * @author Marine
 *
 */
public class CellularGraphGenerator {
	private int nbrEdge;
	private int nbrCells;
	private RingGraphGenerator<String, DefaultEdge> generator;
	private UndirectedGraph <String, DefaultEdge> finalGraph;
	private List<DefaultEdge> availableEdge;
	private List<List<Integer>> availablePosition;
	private int nbrVertex;
	
	
	
	/**
	 * Constructor of the class CellularGraphGenerator
	 * @param nbrCells
	 */
	public CellularGraphGenerator(int nbrCells) {
		super();
		this.nbrCells = nbrCells;
		this.nbrEdge = 6;
		this.nbrVertex = this.nbrEdge;
		this.generator = new RingGraphGenerator<>(this.nbrEdge);
		
		this.availableEdge = new ArrayList<>();
		
		this.availablePosition = new ArrayList<>();
		
		this.finalGraph = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
		
	} 
	
	/**
	 * Private function called by {@link #generateFinalGraph()}
	 */
	private void addCell(){
		//choose randomly the edge to be attached
		
		int max = this.availableEdge.size();
		Random rand = new Random();
		int rdmNbr = rand.nextInt(max);
		
		DefaultEdge edge = this.availableEdge.get(rdmNbr);
		
		int nbrEdgesSource = this.finalGraph.edgesOf(this.finalGraph.getEdgeSource(edge)).size();
		int nbrEdgesTarget = this.finalGraph.edgesOf(this.finalGraph.getEdgeTarget(edge)).size();
		
		// Case if only one Border
		if (nbrEdgesSource < 3 && nbrEdgesTarget < 3){
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
			//this.availableEdge.add(this.finalGraph.getEdge(this.finalGraph.getEdgeSource(edge),Integer.toString(this.nbrVertex+1)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+1),Integer.toString(this.nbrVertex+2)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+2),Integer.toString(this.nbrVertex+3)));
			this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+3),Integer.toString(this.nbrVertex+4)));
			//this.availableEdge.add(this.finalGraph.getEdge(Integer.toString(this.nbrVertex+4),this.finalGraph.getEdgeTarget(edge)));
			
			this.nbrVertex = this.nbrVertex+4;
		}
		
//		//Case if two common borders/edges
//		if ((nbrEdgesSource == 3 && nbrEdgesTarget < 3) || (nbrEdgesSource < 3 && nbrEdgesTarget == 3)){
//			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+1));
//			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+2));
//			this.finalGraph.addVertex(Integer.toString(this.nbrVertex+3));
//			
//			if (nbrEdgesSource == 3){
//				this.finalGraph.addEdge(this.finalGraph.getEdgeTarget(edge),Integer.toString(this.nbrVertex+1));
//				//this.finalGraph.getEdgeTarget(edge)
//				
//			}
//			else{
//				this.finalGraph.addEdge(this.finalGraph.getEdgeSource(edge),Integer.toString(this.nbrVertex+1));
//			}
//			this.finalGraph.addEdge(Integer.toString(this.nbrVertex+1),Integer.toString(this.nbrVertex+2));
//			this.finalGraph.addEdge(Integer.toString(this.nbrVertex+2),Integer.toString(this.nbrVertex+3));
//			//this.finalGraph.addEdge(Integer.toString(this.nbrVertex+3),Integer.toString());
//			
//		}
		
		
		// TODO: all the other case !! without forgetting update available edges but works for now

	}
	
	/**
	 * Generate an Undirected Graph as a chain
	 * @return
	 */
	public UndirectedGraph <String, DefaultEdge> generateFinalGraph(){
		
		//initial value of the final graph (=1 cell)
		Map<String, String> resultMap = new HashMap<>();
		this.generator.generateGraph(this.finalGraph, new StringVertexFactory(), resultMap);
		

		for(DefaultEdge e : this.finalGraph.edgeSet()){
			this.availableEdge.add(e);
		}
		for(DefaultEdge e : this.finalGraph.edgeSet()){
			List<Integer> temp = new ArrayList<>();
			temp.add(Integer.parseInt(this.finalGraph.getEdgeSource(e)));
			temp.add(Integer.parseInt(this.finalGraph.getEdgeTarget(e)));
			temp.add(1);
			this.availablePosition.add(temp);
		}
		
		for (int i1=1; i1<this.nbrCells; i1++){
			addCell();
		}
		return this.finalGraph;
	}
	
	
	}
