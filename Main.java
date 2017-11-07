/**
 * 
 */
//import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.JGraphXAdapter;
/**
 * @author Marine
 *
 */
public class Main extends JApplet{
	private static final long serialVersionUID = 3256444702936019250L;
    //private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Main applet = new Main();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Display Cellular Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
     
	}
	
	public void init(){
		
		
		UndirectedGraph <String, DefaultEdge> undirectedGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		//CellularGraphGenerator generatorCell = new CellularGraphGenerator(5);
		CellularGraphGeneratorFromGrid generatorCellGrid = new CellularGraphGeneratorFromGrid(20,20);
		//undirectedGraph = generatorCell.generateFinalGraph();
		undirectedGraph = generatorCellGrid.generateFinalGraph();
		
		ConstructionAcyclic constructor = new ConstructionAcyclic(undirectedGraph);
		
		
		
		
//		jgxAdapter = new JGraphXAdapter<>(undirectedGraph);
//		getContentPane().add(new mxGraphComponent(jgxAdapter));
//		
//        resize(DEFAULT_SIZE);
//        
//        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
//        layout.execute(jgxAdapter.getDefaultParent());
        
		//Acyclic Graph Construction
        DirectedAcyclicGraph<String, DefaultEdge> directedGraphH = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		directedGraphH = constructor.returnH();
		
		
		
		//DISPLAY
		jgxAdapter = new JGraphXAdapter<>(directedGraphH);
		getContentPane().add(new mxGraphComponent(jgxAdapter));
		
        resize(DEFAULT_SIZE);
        
        HashMap<String,com.mxgraph.model.mxICell> vertexToCellMap = jgxAdapter.getVertexToCellMap();
        
        //END (for now) DISPLAY
        
        
        
//        //Construction Kernel
//        ConstructionKernel constructorKernel = new ConstructionKernel(directedGraphH);
//		Set<String> kernelSet = constructorKernel.returnKernel();
//		
//		for (String p : kernelSet){
//			System.out.print(p + " ");
//		}
//		System.out.println("\nEND INITIAL KERNEL\n");
		
		
		//4-List coloring of a cellular graph
		
		int nbrColor;
		boolean randnbrColor = false;
		if (randnbrColor){
			int min = 0;
			int max = 10;
			nbrColor = min + (int)(Math.random() * ((max - min) + 1));
		}
		else
			nbrColor = 4;
		
		boolean randLengthSetofColors = false;
		int[] lengthSetofColors = new int[directedGraphH.vertexSet().size()];
		if (randLengthSetofColors){
			int min = 4;
			int max = 10;
			for (int i1=0; i1<directedGraphH.vertexSet().size();i1++){
				lengthSetofColors[i1] = min + (int)(Math.random() * ((max - min) + 1));
			}
		}
		else{
			for (int i1=0; i1<directedGraphH.vertexSet().size();i1++){
				lengthSetofColors[i1] = 4;
			}
		}
		
		
		
		FourListColoringCellularGraph fourListColoringAlgo = new FourListColoringCellularGraph(nbrColor,lengthSetofColors);
		
		List<List<String>> listColorToVertexFinal = fourListColoringAlgo.computeColoring(directedGraphH);
		for (int k=0;k<listColorToVertexFinal.size();k++){
			System.out.println("Color"+k);
			for(String s : listColorToVertexFinal.get(k)){
				System.out.print(s+" ");
			}
			System.out.println(" ");
		}
		

		// Display NEXT
        
		for (int k=0; k < listColorToVertexFinal.size(); k++){
			Object[] vertexCellArray = new Object[listColorToVertexFinal.get(k).size()];
	        int i =0;
	        for (String p : listColorToVertexFinal.get(k)) {
	        	//if (i <directedGraphH.vertexSet().size()/2)
	        		vertexCellArray[i] = (Object)(vertexToCellMap.get(p));
	          i=i+1;
	        }
	        //Map<String,Object> vertexCellStyle = jgxAdapter.getCellStyle(vertexCellArray[0]);
	        //System.out.println(vertexCellStyle);
	        
	        // Create random color
	        Random random = new Random();
	        int nextInt = random.nextInt(256*256*256);
	        String colorHex = String.format("#%06X", nextInt);
	        System.out.println("fillColor="+colorHex);
	        jgxAdapter.setCellStyle("fillColor="+colorHex, vertexCellArray);
	        
	        //jgxAdapter.setCellStyle("fillColor=#00CC00", vertexCellArray);
		}
        
                
        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
        //END DISPLAY
        
        
        
		
        
        
	}
        
}
