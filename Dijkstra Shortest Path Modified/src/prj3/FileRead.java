package prj3;


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * This is the front end GUI which contains a single 
 * open file button and text box for outputting 
 * the results of the application. 
 * 
 * 
 * @author Joseph Ketcham
 * @author Michael Eby
 * @author Nironjali Karunadasa
 * @version 1.0
 *
 */

public class FileRead extends JPanel
implements ActionListener  
{
	static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
	
    private List<Vertex> nodes;
    private List<Edge> edges;
    private List<Fuel> fuelStations;
    private int startNode;
	private int destinationNode;
    
    /**
	 * Constructor sets up the GUI  
	 *  
	 */
	 public FileRead() {
	     super(new BorderLayout());
	
	     
		 
	     log = new JTextArea(30,70);
	     log.setMargin(new Insets(5,5,5,5));
	     log.setEditable(false);
	     JScrollPane logScrollPane = new JScrollPane(log);
	     fc = new JFileChooser();
	     openButton = new JButton("Open a File..." );
	     openButton.addActionListener(this);
	     JPanel buttonPanel = new JPanel(); //use FlowLayout
	     buttonPanel.add(openButton);
	     add(buttonPanel, BorderLayout.PAGE_START);
	     add(logScrollPane, BorderLayout.CENTER);
	 }
    
    /**
	 * Kicks off the application launching the GUI 
	 * 
	 * @param args
	 * 
	 */    
	 public static void main(String args[])
	  {
		 SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	             //Turn off metal's use of bold fonts
	             UIManager.put("swing.boldMetal", Boolean.FALSE);
	             createAndShowGUI();
	         }
	     }); 
	  }
 
 	/**
	 * Open file method takes a file name and attempts to open it and
	 * Pass the data to the Process_String class for processing
	 * 
	 * @param inname	File with long unsorted string of integers separated by commas
	 * 
	 *  
	 */
	 public void openfile(String inname){
		 log.setText("");
		 System.out.println(inname);
		 
		 try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(inname);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  
			  nodes = new ArrayList<Vertex>();
			  edges = new ArrayList<Edge>();
			  fuelStations = new ArrayList<Fuel>();
			  int range = 0;
			  int dte = 0;
			  boolean flag_range = false;
			  boolean flag_fuel = false;
			  boolean flag_edges = false;
			  boolean flag_points = false;
			  int tmpVertex = 0;
			  int counter = 0;
			  int maxVertex = 0;
			  
			  int countNodes = 0;
			  //Read File Line By Line
			  StringTokenizer st; //= new StringTokenizer("this is a test");
			  
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console	
				  
				//only runs if the comment line for starting range exists and is complete
				  if (flag_range == true && strLine.trim().equals("") == false 
						  && strLine.trim().substring(0,2).equals("//") == false) { 
					  flag_range = false;
					  st = new StringTokenizer(strLine, ",");
					  if (st.countTokens() != 2) { 
						  log.append("Bad file input data, should have two params after starting range comment line 1");
						  break;
					  }
					  
					  range = Integer.parseInt(st.nextToken()) + 1;
					  
					  dte = Integer.parseInt(st.nextToken());
					  if (range > dte) {
						  log.append("Warning, range is greater than max fuel capacity \n\n");
						  
					  }
					  log.append("Range and DTE Loaded Successfully \n");
				  }
				  if (strLine.trim().equals("//starting range, dte at fuel ups, this is the max fill, does not add in addition to remaining fuel")){ flag_range = true; }
				  
				  
				//only runs if the comment line for fuel exists and is complete
				  if (flag_fuel == true && strLine.trim().equals("") == false
						  && strLine.trim().substring(0,2).equals("//") == false) { 
					  flag_fuel = false;
					  st = new StringTokenizer(strLine, ",");
					  while (st.hasMoreTokens()){
						  int tmpStation = Integer.parseInt(st.nextToken()) - 1;
						  addFuel("Node_" + Integer.toString(tmpStation));
					  }
					  log.append("Fuel Stations Loaded Successfully \n");
				  }
				  if (strLine.trim().equals("//fuel stations")){ flag_fuel = true; }
				  
				//only runs if the comment line for start and destination exists and is complete
				  if (flag_points == true && strLine.trim().equals("") == false
						  && strLine.trim().substring(0,2).equals("//") == false) { 
					  flag_points = false;
					  st = new StringTokenizer(strLine, ",");
					  if (st.countTokens() != 3){
						log.append("Bad file input data, expected thee params: Start point, end point, number of nodes in graph");
					  }
					  startNode = Integer.parseInt(st.nextToken()) - 1;
					  destinationNode = Integer.parseInt(st.nextToken()) - 1;
					  countNodes = Integer.parseInt(st.nextToken()) - 1;
					  
					  for (int i = 0; i <= countNodes; i++) {
					        Vertex location = new Vertex("Node_" + i, "Node_" + i);
					        nodes.add(location);
					      }
					  log.append("Start Point, Destination, and Number of Nodes Loaded Successfully \n");
				  }
				  if (strLine.trim().equals("//Start point, destination, number of nodes")){ flag_points = true; }
				  
				//only runs if the comment line for edges exists and is complete
				  if (flag_edges == true && strLine.trim().equals("") == false
						  && strLine.trim().substring(0,2).equals("//") == false) { 
					  
					  st = new StringTokenizer(strLine, ",");
					  
					  if (st.countTokens() != 3 || countNodes == 0) { 
						  log.append("Bad file input data, should have three params for each node: Start, Destination, Distance \n");
						  log.append("Error could have also been caused by setting number of nodes to 0, check source file");
						  break;
					  }
					  
					  addLane("Edge_" + counter++, (Integer.parseInt(st.nextToken().trim()) - 1), 
							  (Integer.parseInt(st.nextToken().trim()) - 1), Integer.parseInt(st.nextToken().trim()));
					  log.append("Edge " + counter + " Loaded Successfully \n");
				  }
				  if (strLine.trim().equals("//start node, destination node, distance")){ flag_edges = true; }
				  
				  //log.append(Process_String.execute(strLine));
				  //log.append("\n");
				  
			  //JOptionPane.showMessageDialog(null, PS.execute(strLine));			  
			  }
			  
			  //Close the input stream
			  in.close();
			  
			  Graph graph = new Graph(nodes, edges, fuelStations);
			  DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
			  dijkstra.execute(nodes.get(startNode), range, dte);
			  LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destinationNode));
			  log.append("\n The following is the shortest path node traversal with fuel constraints: \n");
			  for (Vertex vertex : path) {
			      //System.out.println("Test 2: " + vertex);
				  int tmpDest;
				  String jkktest = vertex.toString();
				  int jkklength = vertex.toString().length();
				  if(vertex.toString().length() > 6) {
					  tmpDest = jkklength;					  
				  } else {tmpDest = 6;}
				  
				  log.append(vertex.toString().substring(0,5) + (Integer.parseInt(vertex.toString().substring(5,tmpDest)) + 1 ) + "\n");
			    }
			    }catch (Exception e){
			    	//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  	if (e.getMessage() == null) {
			  		log.append("Destination path unreachable");
			  	}
			    }
	 }
 
 
 	/**
	 * Event listener for when the button is clicked on the GUI
	 * 
	 * @param e	actionEvent to be handled for processing
	 * 
	 *  
	 */
	 public void actionPerformed(ActionEvent e) {
	
	     //Handle open button action.
	     if (e.getSource() == openButton) {
	         int returnVal = fc.showOpenDialog(FileRead.this);
	
	         if (returnVal == JFileChooser.APPROVE_OPTION) {
	             File file = fc.getSelectedFile();
	         openfile(file.getPath());
	         } else {
	        	  log.append("Open command cancelled by user." + newline);
	         }
	        
	     }
	 }

	 /** Returns an ImageIcon, or null if the path was invalid. */
	 protected static ImageIcon createImageIcon(String path) {
	     java.net.URL imgURL = FileRead.class.getResource(path);
	     if (imgURL != null) {
	         return new ImageIcon(imgURL);
	     } else {
	         System.err.println("Couldn't find file: " + path);
	         return null;
	     }
	 }
	
	 /**
	  * Create the GUI and show it.  For thread safety,
	  * this method should be invoked from the
	  * event dispatch thread.
	  */
	 private static void createAndShowGUI() {
	     //Create and set up the window.
	     JFrame frame = new JFrame("FileChooser");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	     //Add content to the window.
	     frame.add(new FileRead());
	
	     //Display the window.
	     frame.pack();
	     frame.setVisible(true);
	 }
	 
	 private void addLane(String laneId, int sourceLocNo, int destLocNo,
		      int duration) {
		    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		    edges.add(lane);
		  }
		  
	  private void addFuel(String stationId) {
		    Fuel fuel = new Fuel(stationId); 
		    
		    fuelStations.add(fuel);
		  }
}