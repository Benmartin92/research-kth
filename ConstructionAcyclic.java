import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;;

public class ConstructionAcyclic {
	private DirectedGraph<String, DefaultEdge>[] directedGraph;
	private functionF[] functionFTable;
	
	
	/**
	 * Constructor
	 * @param directedGraph
	 * @param functionF
	 */
	
	public ConstructionAcyclic(DirectedGraph<String, DefaultEdge> directedGraph) {
		super();
		for (int i=0; i<directedGraph.vertexSet().size();i++){
			this.directedGraph[i]= directedGraph;
			this.functionFTable[i] = new functionF();
		}
		
	}
	public int functionF(String s){
		for(int i=0; i<this.functionFTable.length;i++){
			if (this.functionFTable[i].isThisVertex(s))
				return i;
			}
		return -1;
	}
	
	public DirectedGraph<String, DefaultEdge> returnH(){
		int i=0;
		for (String p : directedGraph[0].vertexSet()){
			i=i+1;
			int degree = directedGraph[i].outDegreeOf(p);
			if(degree <= 3){
				if(functionF(p)!=-1)
					this.functionFTable[functionF(p)].setValue(i);
				else
					this.functionFTable[i] = new functionF(p, i);

				this.directedGraph[i+1].removeVertex(p);
			}
		}
		DirectedGraph<String, DefaultEdge> directedGraphH = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		for(DefaultEdge e : this.directedGraph[0].edgeSet()){
			String u=this.directedGraph[0].getEdgeSource(e);
			String v=this.directedGraph[0].getEdgeTarget(e);
			if(functionF(u)<functionF(v)){
				directedGraphH.addEdge(u, v);
			}
			else{
				directedGraphH.addEdge(v, u);
			}
		}
		
		return directedGraphH;
	}
	
	
	
}
