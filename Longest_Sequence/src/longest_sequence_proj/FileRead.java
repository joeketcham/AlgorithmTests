package longest_sequence_proj;


import java.io.*;
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
	
    /**
	 * Constructor sets up the GUI  
	 *  
	 */
	 public FileRead() {
	     super(new BorderLayout());
	
	     
	     log = new JTextArea(5,20);
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
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console		  
			  
				  
				  log.append(Process_String.execute(strLine));
				  log.append("\n");
				  
			  //JOptionPane.showMessageDialog(null, PS.execute(strLine));			  
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){
			    	//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  
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
}