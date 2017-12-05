/**
 * 
 */
//import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;

import junit.framework.Test;

import org.jgrapht.DirectedGraph;
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
    private static int testN;
    private static int testNbrColors;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Program Arguments:");
        for (String arg : args) {
            System.out.println("\t" + arg);
        }
        //System.out.println(args[0]);
        testN = Integer.parseInt(args[0]);
        testNbrColors = Integer.parseInt(args[1]);
        
        if(testNbrColors < 4){
        	System.err.println( "The number of colors available should be higher than 4.");
        	return;
        }
        

        Main applet = new Main();
        

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Display Cellular Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        applet.init();
        
        return;
        
         
     
	}
	
	public void init(){
		
		//int N=testN;//rows
		int N=5;//rows
		int M=N;//columns
		
		UndirectedGraph <String, DefaultEdge> undirectedGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		CellularGraphGeneratorFromGrid generatorCellGrid = new CellularGraphGeneratorFromGrid(N,M);
		undirectedGraph = generatorCellGrid.generateFinalGraph();
		
		long startTime1 = System.nanoTime();
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
		
        long breakTime1 = System.nanoTime();
		
		
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
		
        long startTime2 = System.nanoTime();
        
		int nbrColor;
		boolean randnbrColor = false;
		if (randnbrColor){
			int min = 4;
			int max = 10;
			nbrColor = min + (int)(Math.random() * ((max - min) + 1));
			System.out.println(nbrColor);
		}
		else{
			nbrColor = 4;
			//nbrColor = testNbrColors;
		}
		
		long breakTime2 = System.nanoTime();
		
		boolean randLengthSetofColors = false; // PURPOSE OF PROJECT
		int[] lengthSetofColors = new int[directedGraphH.vertexSet().size()];
		if (randLengthSetofColors){
			int min = 4;
			int max = nbrColor+1;
			for (int i1=0; i1<directedGraphH.vertexSet().size();i1++){
				lengthSetofColors[i1] = min + (int)(Math.random() * ((max - min) + 1));
			}
		}
		else{
			for (int i1=0; i1<directedGraphH.vertexSet().size();i1++){
				lengthSetofColors[i1] = 4;
			}
		}
		
		
		 /*FileWriter write;
			try {
				int nbrVertex = directedGraphH.vertexSet().size();
		        int[][] matrix = new int [nbrVertex][nbrVertex];
		        
		        DirectedAcyclicGraph<String, DefaultEdge> directedGraphH2 = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		        DirectedAcyclicGraph<String, DefaultEdge> directedGraphH3 = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
				
				for (String p : directedGraphH.vertexSet()){
						directedGraphH2.addVertex(p);
						directedGraphH3.addVertex(p);
				}
				
				for(DefaultEdge e : directedGraphH.edgeSet()){
					directedGraphH2.addEdge(directedGraphH.getEdgeSource(e), directedGraphH.getEdgeTarget(e));
					directedGraphH3.addEdge(directedGraphH.getEdgeSource(e), directedGraphH.getEdgeTarget(e));
				}
				
				int i=0;
		        for (String s : directedGraphH3.vertexSet()){
		        	int j=0;
					for (String t : directedGraphH2.vertexSet()){
						if (directedGraphH.containsEdge(s,t)){
							matrix[i][j] = 1;
						}
						else
							matrix[i][j] = 0;
						j=j+1;
					}
					i=i+1;
				}

		        String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		        
		        FourListColoringCellularGraph fourListColoringAlgo = new FourListColoringCellularGraph(nbrColor,lengthSetofColors, directedGraphH);
		        fourListColoringAlgo.generateSetOfColors(directedGraphH.vertexSet());
		        List<List<String>> list = fourListColoringAlgo.getListVertexToColorInitial();

		        int [][] tabColors = new int [nbrVertex][nbrColor]; // nbr vertex | nbr colors
		        for (int i1=0; i1<nbrVertex; i1++){
					for (int j=1; j<nbrColor+1; j++){
						if (list.get(i1).contains(Integer.toString(j))){
							tabColors[i1][j-1]=1;
						}
						else{
							tabColors[i1][j-1]=0;
						}
					}
				}
	
	
				write = new FileWriter("matrix" + "-" + N + "-" + nbrColor + ".txt",true);
				write.write("\nparam n := "+nbrVertex+";\n\n");
				write.write("param c := "+nbrColor+";\n\n");
				
				
				write.write("param adj_matrix\n: ");
				for (int j =1; j<nbrVertex+1; j++){
					write.write(j + " ");
				}
				write.write(":=\n");
				for (int i1 =0; i1<nbrVertex; i1++){
					write.write(i1+1 + " ");
					for (int j =0; j<nbrVertex; j++){
						write.write(matrix[i1][j] + " ");
					}
					if (i1 != nbrVertex-1)
						write.write("\n");
					else 
						write.write(";\n");
				}
				write.write("\n");
				
				write.write("param color_lists\n: ");
				for (int j =1; j<nbrColor+1; j++){
					write.write(j + " ");
				}
				write.write(":=\n");
				for (int i1=0; i1<nbrVertex; i1++){
					write.write(i1+1 + " ");
					for (int j=0; j<nbrColor; j++){
						write.write(tabColors[i1][j] + " ");
					}
					if (i1 != nbrVertex-1)
						write.write("\n");
					else 
						write.write(";\n");
				}
				write.write("\nend;");
				
				
				write.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		
		
		long startTime3 = System.nanoTime();
		FourListColoringCellularGraph fourListColoringAlgo = new FourListColoringCellularGraph(nbrColor,lengthSetofColors, directedGraphH);
		
		List<List<String>> listColorToVertexFinal = fourListColoringAlgo.computeColoring();
		
		long breakTime3 = System.nanoTime();
		
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
        
		
        int nbrVertex = directedGraphH.vertexSet().size();
        int[][] matrix = new int [nbrVertex][nbrVertex];
        
        DirectedAcyclicGraph<String, DefaultEdge> directedGraphH2 = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedAcyclicGraph<String, DefaultEdge> directedGraphH3 = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
		
		for (String p : directedGraphH.vertexSet()){
				directedGraphH2.addVertex(p);
				directedGraphH3.addVertex(p);
		}
		
		for(DefaultEdge e : directedGraphH.edgeSet()){
			directedGraphH2.addEdge(directedGraphH.getEdgeSource(e), directedGraphH.getEdgeTarget(e));
			directedGraphH3.addEdge(directedGraphH.getEdgeSource(e), directedGraphH.getEdgeTarget(e));
		}
        
		int i=0;
        for (String s : directedGraphH3.vertexSet()){
        	int j=0;
			for (String t : directedGraphH2.vertexSet()){
				if (directedGraphH.containsEdge(s,t)){
					matrix[i][j] = 1;
					matrix[j][i] = 1;
				}
				else if (matrix[j][i]!=1){
					matrix[i][j] = 0;
					matrix[j][i] = 0;
				}
				j=j+1;
			}
			i=i+1;
		}

        String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        
        List<List<String>> list = fourListColoringAlgo.getListVertexToColorInitial();
        int [][] tabColors = new int [nbrVertex][nbrColor]; // nbr vertex | nbr colors
        for (int i1=0; i1<nbrVertex; i1++){
			for (int j=1; j<nbrColor+1; j++){
				if (list.get(i1).contains(Integer.toString(j))){
					tabColors[i1][j-1]=1;
				}
				else{
					tabColors[i1][j-1]=0;
				}
			}
		}
        
        
        FileWriter write;
		try {
			
			
			write = new FileWriter("matrix" + "-" + N + "-" + nbrColor + "-"+timeLog+".txt",true);
			write.write("\nparam n := "+nbrVertex+";\n\n");
			write.write("param c := "+nbrColor+";\n\n");
			
			
			write.write("param adj_matrix\n: ");
			for (int j =1; j<nbrVertex+1; j++){
				write.write(j + " ");
			}
			write.write(":=\n");
			for (int i1 =0; i1<nbrVertex; i1++){
				write.write(i1+1 + " ");
				for (int j =0; j<nbrVertex; j++){
					write.write(matrix[i1][j] + " ");
					System.out.print(matrix[i1][j] + " ");
				}
				if (i1 != nbrVertex-1)
					write.write("\n");
				else 
					write.write(";\n");
				System.out.print(";\n");
			}
			write.write("\n");
			
			write.write("param color_lists\n: ");
			for (int j =1; j<nbrColor+1; j++){
				write.write(j + " ");
			}
			write.write(":=\n");
			for (int i1=0; i1<nbrVertex; i1++){
				write.write(i1+1 + " ");
				for (int j=0; j<nbrColor; j++){
					write.write(tabColors[i1][j] + " ");
				}
				if (i1 != nbrVertex-1)
					write.write("\n");
				else 
					write.write(";\n");
			}
			write.write("\nend;");
			
			
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// Lets rescale the vertex number.
		int tab[] = new int[N*M+1];
		int p=1;
		for(String s: directedGraphH.vertexSet()){
			tab[Integer.parseInt(s)]=p;
			p=p+1;
		}
		FileWriter write2;
		
		try {
			write2 = new FileWriter("coloration" + "-" + N + "-" + nbrColor + "-"+timeLog+".txt",true);
			write2.write(nbrVertex+"\n");
			for (int i1 =0; i1<nbrVertex; i1++){
				for (int j =0; j<nbrVertex; j++){
					write2.write(matrix[i1][j] + " ");
				}
				write2.write("\n");
			}
			
			for (int k=0;k<listColorToVertexFinal.size();k++){
				System.out.println("Color"+k);
				for(String s : listColorToVertexFinal.get(k)){
					write2.write(Integer.toString(tab[Integer.parseInt(s)])+" ");
					System.out.print(Integer.toString(tab[Integer.parseInt(s)])+" ");
				}
				write2.write("\n");
				System.out.println(" ");
			}
			write2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileWriter write3;
		
		try {
			write3 = new FileWriter("listColorToVFinal" + "-" + N + "-" + nbrColor + "-"+timeLog+".txt",true);
			for (int k=0;k<listColorToVertexFinal.size();k++){
				System.out.println("Color"+k);
				for(String s : listColorToVertexFinal.get(k)){
					write3.write(Integer.toString(tab[Integer.parseInt(s)])+" ");
					System.out.print(Integer.toString(tab[Integer.parseInt(s)])+" ");
				}
				write3.write("\n");
				System.out.println(" ");
			}
			write3.write("Took "+(breakTime1 - startTime1 + breakTime2 - startTime2 + breakTime3 - startTime3) + " ns");
			write3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        System.out.println("Took "+(breakTime1 - startTime1 + breakTime2 - startTime2 + breakTime3 - startTime3) + " ns");
        
        
	}
        
}
