/**
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.generate.RingGraphGenerator;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.ext.JGraphXAdapter;
/**
 * @author Marine
 *
 */
public class Main extends JApplet{
	private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
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
		CellularGraphGenerator generatorCell = new CellularGraphGenerator(5);
		undirectedGraph = generatorCell.generateFinalGraph();
		
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
		
		
        
        
	}
        
}
