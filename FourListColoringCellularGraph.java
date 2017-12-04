import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

public class FourListColoringCellularGraph {
	int nbrColor;
	int[] lengthSetofColors;
	List<List<String>> listVertexToColor;
	List<List<String>> listVertexToColorFIXED;
	
	List<List<String>> listColorToVertex;
	List<List<String>> listColorToVertexFinal;
	DirectedAcyclicGraph<String, DefaultEdge> directedGraphH;
	
	public FourListColoringCellularGraph(int nbrColor,	int[] lengthSetofColors, DirectedAcyclicGraph<String, DefaultEdge> directedGraphH){
		this.nbrColor=nbrColor;
		this.lengthSetofColors=lengthSetofColors;
		this.listVertexToColor = new ArrayList<List<String>>();
		this.listVertexToColorFIXED = new ArrayList<List<String>>();
		this.listColorToVertex = new ArrayList<List<String>>();
		this.listColorToVertexFinal = new ArrayList<List<String>>();
		this.directedGraphH = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		
		for (String p : directedGraphH.vertexSet()){
				this.directedGraphH.addVertex(p);
		}
		
		for(DefaultEdge e : directedGraphH.edgeSet()){
			this.directedGraphH.addEdge(directedGraphH.getEdgeSource(e), directedGraphH.getEdgeTarget(e));
		}
	}
	
	public void generateSetOfColors(Set<String> vertexSet){
		int minColor=1;
		int maxColor=this.nbrColor;
		
		for (int j=0; j<this.nbrColor;j++){
			List<String> listTemp = new ArrayList<String>();
			listColorToVertex.add(listTemp);
			listColorToVertexFinal.add(listTemp);
		}
		
		
		int i=0;
		int k=1;
		for (String p : vertexSet){
			
			List<String> listTemp = new ArrayList<String>();
			List<String> listTempFixed = new ArrayList<String>();
			
			if (k != Integer.parseInt(p)){
				while(k != Integer.parseInt(p)){
					this.listVertexToColor.add(listTemp);
					this.listVertexToColorFIXED.add(listTempFixed);
					k++;
				}
			}
			
//			List<Integer> possibleColors = new ArrayList<Integer>();
//			for (int j=0; j<lengthSetofColors[i];j++){
//				possibleColors.add(j+1);
//			}
			for (int j=0; j<lengthSetofColors[i];j++){
				int colorToBeAdded = minColor + (int)(Math.random() * ((maxColor - minColor) + 1));
				while(listTemp.contains(Integer.toString(colorToBeAdded))){
					colorToBeAdded = minColor + (int)(Math.random() * ((maxColor - minColor) + 1));
					//this loop is finite because lengthSetofColors[i].size() is defined by the number of colors available.
				}
				
				/*int index = (int)(Math.random() * (possibleColors.size() + 1));
				
				int colorToBeAdded = possibleColors.remove(index);*/
				
				//FOR NOW :
				//int colorToBeAdded = j+1;
				listTemp.add(Integer.toString(colorToBeAdded));
				listTempFixed.add(Integer.toString(colorToBeAdded));
				listColorToVertex.get(colorToBeAdded-1).add(p);
				
			}
			this.listVertexToColor.add(listTemp);
			this.listVertexToColorFIXED.add(listTempFixed);
			i=i+1;
			k++;
		}
		
		//Saving listVertexToColor in listVertexToColorFIXED
		
	}
	
	
	public List<List<String>> computeColoring(){
		generateSetOfColors(directedGraphH.vertexSet());
		
				
		//for (int i=0; i<directedGraphH.vertexSet().size();i++){
		int i=0;
		while (directedGraphH.vertexSet().size() !=0){
			for (String s : this.directedGraphH.vertexSet()){
				i=Integer.parseInt(s)-1;
				break;
			}
			int colorAlpha;
			if (!this.listVertexToColor.get(i).isEmpty())
				colorAlpha = Integer.parseInt(this.listVertexToColor.get(i).get(0)); // we assume that there is always a possible color.
			else{
				colorAlpha =-1;
				System.out.println("This error is never supposed to be called. Vertex considered: "+i);
			}
				
			
				
			Set<String> subVertexSet = new HashSet<String>();

			for (int j =0; j<this.listColorToVertex.get(colorAlpha-1).size(); j++){
				subVertexSet.add(this.listColorToVertex.get(colorAlpha-1).get(j));
			}
			// DirectedSubgraph<String, DefaultEdge> subGraphD = new DirectedSubgraph<String, DefaultEdge>(directedGraphH, subVertexSet);
			// We have a DirectedSubgraph but we want to extract the kernel ==> change in construction Kernel --> apparently not possible from Directed Subgraph --> copy in a new DirectedAcyclicGraph
			DirectedAcyclicGraph<String, DefaultEdge> subGraphD = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
			
			//Copy the directedGraph partially
			for (String p : directedGraphH.vertexSet()){
				if (subVertexSet.contains(p)){
					subGraphD.addVertex(p);
				}
			}
			
			for(DefaultEdge e : directedGraphH.edgeSet()){
				if (subVertexSet.contains(directedGraphH.getEdgeSource(e)) && subVertexSet.contains(directedGraphH.getEdgeTarget(e))){
					subGraphD.addEdge(directedGraphH.getEdgeSource(e), directedGraphH.getEdgeTarget(e));
				}
			}
			
			
			ConstructionKernel constructorKernel = new ConstructionKernel(subGraphD);
			Set<String> kernelSet = constructorKernel.returnKernel();
			
//			for (String p : kernelSet){
//				System.out.print(p + " ");
//			}
//			System.out.println("\nEND color KERNEL");
			
			for(String s:kernelSet){
				this.listColorToVertexFinal.get(colorAlpha-1).add(s);
				directedGraphH.removeVertex(s);
				for (int k=0; k<this.listColorToVertex.size();k++){
					this.listColorToVertex.get(k).remove(s);
				}
			}
			for (int k=0; k<directedGraphH.vertexSet().size();k++){
				this.listVertexToColor.get(k).remove(Integer.toString(colorAlpha));
			}
			//System.out.println("Nbr of nodes not colored "+directedGraphH.vertexSet().size());
			//i=i+1;
		}
		
		return this.listColorToVertexFinal;
	
	}

	public List<List<String>> getListVertexToColorInitial(){
		return this.listVertexToColorFIXED;
	}
}
