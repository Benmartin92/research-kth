/**
 * 
 */
//import java.awt.Color;
import java.awt.Dimension;
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
        
        DirectedAcyclicGraph<String, DefaultEdge> directedGraphH = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		directedGraphH = constructor.returnH();
		
		
		jgxAdapter = new JGraphXAdapter<>(directedGraphH);
		getContentPane().add(new mxGraphComponent(jgxAdapter));
		
        resize(DEFAULT_SIZE);
        
        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
        
        ConstructionKernel constructorKernel = new ConstructionKernel(directedGraphH);
		Set<String> kernelSet = constructorKernel.returnKernel();
		
		for (String p : kernelSet){
			System.out.print(p + " ");
		}
		System.out.println("");
		
		
        
        
	}
        
}
