package granov.props;

import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;



/**
 * clsBWPropEditor is the class for displaying the properties 
 * 
 * 
 */

public class clsPropEditor extends JPanel implements TreeSelectionListener, ActionListener, 
														MouseListener, Runnable 
{
	  private JEditorPane valuePane;
	  JScrollPane treeView;
	  private JTree tree;
	  DefaultMutableTreeNode top;
	  File file;
	  String sFileName = "";
	  final JFileChooser fc = new JFileChooser();
	  JButton pbOpenFile, pbSaveFile, pbSave;
	  
	  private static final long serialVersionUID = 200908081130L;
	  
	  //public clsBWPropEditor() {
	  public clsPropEditor(String s) {
		  super(new GridLayout(2,0));
		  
	        //Create the nodes.
	        top = new DefaultMutableTreeNode("Properties - ");

	        //Create a tree that allows one selection at a time.
	        tree = new JTree(top);
	        tree.setEditable(true);
	        tree.getSelectionModel().setSelectionMode
	                (TreeSelectionModel.SINGLE_TREE_SELECTION);

	        //Listen for when the selection changes.
	        tree.addTreeSelectionListener(this);

	        //Create the scroll pane and add the tree to it. 
	        treeView = new JScrollPane(tree);

	        //Create the values viewing pane.
	        valuePane = new JEditorPane();
	        valuePane.setContentType("text/plain");
	        valuePane.setEditable(true);
	        valuePane.addMouseListener(this);
	        JScrollPane valueView = new JScrollPane(valuePane);

	        //Add the scroll panes to a split pane.
	        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	        splitPane.setTopComponent(treeView);
	        splitPane.setBottomComponent(valueView);

	        Dimension minimumSize = new Dimension(100, 150);
	        valueView.setMinimumSize(minimumSize);
	        treeView.setMinimumSize(minimumSize);
	        splitPane.setDividerLocation(200); 
	        splitPane.setPreferredSize(new Dimension(500, 300));

	        pbOpenFile = new JButton("Open *.property file");
	        pbOpenFile.addActionListener(this);
	        pbSaveFile = new JButton("Save *.property file");
	        pbSaveFile.addActionListener(this);
	        pbSave = new JButton("Save property changes");
	        pbSave.addActionListener(this);
	        
	        //Add the split pane to this panel.
	        add(splitPane);
	        add(pbOpenFile); add(pbSave); add(pbSaveFile);

	        if (!s.isEmpty()){ // If the object was created from the program 
	        	DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			
	        	model.setRoot(null);
		 
				// Create the nodes from a file 
	        	treeView.repaint();
				file = new File(s);
				String fp = file.getPath();
				top = new DefaultMutableTreeNode("Properties - " + s);
				sFileName = s;
				model.setRoot(top);
				try {
					FileInputStream in = new FileInputStream(fp);
					 DataInputStream dis = new DataInputStream(in);
				        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
				    String strLine;
				    //Read File Line By Line
				    while ((strLine = br.readLine()) != null)   {
				      	int ei = strLine.lastIndexOf("=");
				    	if(ei>-1 && ei<strLine.length())
				    		createNodes(strLine.substring(0, ei), strLine.substring(ei+1));
				    }
				    //Close the input stream
				    dis.close();
				} catch (Exception ex){//Catch exception if any
				      System.err.println("Error: " + ex.getMessage());
				}
				treeView.repaint();
	        }
	  }
	  
	  /** Required by TreeSelectionListener interface. */
	    @Override
		public void valueChanged(TreeSelectionEvent e) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                           tree.getLastSelectedPathComponent();

	        if (node == null) return;

	        Object nodeInfo = node.getUserObject();
	        if (node.isLeaf()) {
	        	PropInfo prop = (PropInfo)nodeInfo;
	        	displayVal(prop.propValue);
	        }
	    }
	    
	    /** Required by ActionListener interface. */
	    @Override
		public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == pbOpenFile) { // Open the file and create nodes   
	    		int returnVal = fc.showOpenDialog(clsPropEditor.this);
	    		if (returnVal == JFileChooser.APPROVE_OPTION) {
	    			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    			
	    		    model.setRoot(null); //Reset The tree 
	    		 
	    			treeView.repaint();
	    			file = fc.getSelectedFile();
	    			String s = file.getName();
	    			String fp = file.getPath();
	    			top = new DefaultMutableTreeNode("Properties - " + s);
	    			sFileName = s;
	    			// Now create nodes again 
	    			model.setRoot(top);
	    			try {
	    				FileInputStream in = new FileInputStream(fp);
	    				 DataInputStream dis = new DataInputStream(in);
	    			        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	    			    String strLine;
	    			    //Read File Line By Line
	    			    while ((strLine = br.readLine()) != null)   {
	    			      	int ei = strLine.lastIndexOf("=");
	    			    	if(ei>-1 && ei<strLine.length())
	    			    		createNodes(strLine.substring(0, ei), strLine.substring(ei+1));
	    			    }
	    			    //Close the input stream
	    			    dis.close();
	    			    }catch (Exception ex){//Catch exception if any
	    			      System.err.println("Error: " + ex.getMessage());
	    			    }
	    			    treeView.repaint();
	    		}
	    	}
	    	if (e.getSource() == pbSave) { // Save changes made to property/node
	    		 DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	    		 				tree.getLastSelectedPathComponent();

	    		 if (node == null) return;

	    		 Object nodeInfo = node.getUserObject();
	    		 if (node.isLeaf()) {
	    			 PropInfo prop = (PropInfo)nodeInfo;
	    			 String newVal = valuePane.getText();
	    			 prop.propValue = newVal;
	    			 //displayVal(prop.propValue);
	    		}
	    	}
	    	if (e.getSource() == pbSaveFile) {
	    	    File outFile = new File(sFileName+".tmp"); // Create temporary file
	    		try {
					PrintWriter out
					   = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
				
					RandomAccessFile raf = new RandomAccessFile(file, "rw");
					raf.seek(0);
					while (raf.getFilePointer() < raf.length()) { // Read the File line by line 
						String strLine = raf.readLine();
						int ei = strLine.lastIndexOf("=");
						String filePropName = ""; // Property name in file
						// Write to temporary File 
						if(ei>-1 && ei<strLine.length()) // This is a property 
							filePropName = strLine.substring(0, ei);
						else {
							out.println(strLine);
							continue;
						}
						
						for (int i=0; i<top.getLeafCount();i++) { //Run thorough the tree 
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) top.getChildAt(i);
							if (node == null) continue;
							
							Object nodeInfo = node.getUserObject();
							
				    		if (node.isLeaf()) {
				    			 PropInfo prop = (PropInfo)nodeInfo;
				    			 if(prop.toString().equals(filePropName)) { //Found The property 
				    				 String newLine = prop.propName + "=" + prop.propValue;
				    				 out.println(newLine);
				    				 out.flush();
				    			 }
				    		}
						}
					}
					raf.close();
					out.close();
					//Copy and delete temporary File  
					copyFile(outFile, file);
					outFile.delete();
				} catch (FileNotFoundException e1) {
					//  (Boss) - Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					//  (Boss) - Auto-generated catch block
					e2.printStackTrace();
				}
				
	    	}
	    }
	    
	    private void copyFile(File from, File to) throws IOException {
	    	FileReader in = new FileReader(from);
	        FileWriter out = new FileWriter(to);
	        int c;

	        while ((c = in.read()) != -1)
	          out.write(c);

	        in.close();
	        out.close();
	    }


	  private class PropInfo {  //keeps the property
	        public String propName;
	        public String propValue;

	        public PropInfo(String pn, String pv) {
	        	propName = pn;
	        	propValue = pv;
	        }

	        @Override
			public String toString() {
	            return propName;
	        }
	    }

	  private void displayVal(String url) {
		  try {
			  valuePane.setText(url);
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
	    }

	  
	  private void createNodes(String ts, String det) {
	        DefaultMutableTreeNode book = null;

	        book = new DefaultMutableTreeNode(new PropInfo(ts, det));
	        top.add(book);
	  }

	  
	  /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event dispatch thread.
	     */
	    private static void createAndShowGUI(String s) {
	        //Create and set up the window.
	        JFrame frame = new JFrame("clsBWPropEditor");
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        //Add content to the window.
	        frame.add(new clsPropEditor(s));

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	  
	  public static void main(String[] args) {
	        //Schedule a job for the event dispatch thread:
	        //creating and showing this application's GUI.
		  (new Thread(new clsPropEditor(""))).run();
	    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// (Boss) - Auto-generated method stub
		if(e.getComponent() == valuePane){ //If the valuePane was clicked 
			if(e.getClickCount() == 2) { //If it was a doubleclick
				String sVal = valuePane.getText();
				if(sVal.indexOf("@") == 0) { //if the value marks a file to be included
					sFileName = sVal.substring(1);
					//Open the file in new window 
					Thread th = new Thread(new clsPropEditor(sVal.substring(1)));
					th.run();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// (Boss) - Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// (Boss) - Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// (Boss) - Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// (Boss) - Auto-generated method stub
		
	}

	@Override
	public void run() {
		// (Boss) - Auto-generated method stub
		createAndShowGUI(sFileName);
	}


}


