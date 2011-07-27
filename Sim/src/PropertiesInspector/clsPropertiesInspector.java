package PropertiesInspector;


import config.clsBWProperties;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;


/**
 * @author Matthias Jakubec
 *
 * The class builds a GUI to display the contents of the clsBWProperties object,
 * which holds the property parameters in the project ARS V38.
 * 
 * It can be called
 * 		.) from within Eclipse via the "main" method.
 * 			In this case it displays the file specified as argument at the program call.
 * 			The program can be called with no, one or two string arguments.
 * 			In the case of no argument, the initially displayed properties object is empty.
 * 			In the case of one argument, this is interpreted as a fully qualified file name of a properties file.
 * 			In the case of one argument, these are interpreted as path name and local file name of a properties file.
 * 		.) by another application using the constructor with an existing clsBWProperties object as input parameter.
 */
public class clsPropertiesInspector extends JFrame { // To appear at the desktop the class is defined as a child of JFrame (package "javax.swing").

	
	private static final long serialVersionUID = 1L;
	// Fields for the Components of the properties inspector's pane.
	private JTree jtrConfigTree;
	private JTextArea jtaTextArea1;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemOpenFile;
	private JScrollPane jspScrollPane1, jspScrollPane2;
	private JFileChooser jfcFileChooser;
	private clsBWProperties BWProperies;
	private String propertyFilename = ""; // Fully qualified name of the loaded property file.

	
	// Configuration Constants
	private static final char SEPARATOR_FOR_PATH = '\\';
	private static final char SEPERATOR_FOR_TREE_LEVELS = '.';
	private static final int APPLICATION_FRAME_POSITION_HORIZONTAL = 300;
	private static final int APPLICATION_FRAME_POSITION_VERTICAL = 200;
	private static final int HEIGHT_FIXED_FOR_TEXT_AREA1 = 50;
	private static final int HEIGHT_PREFERRED_FOR_TREE_PANE = 600;
	private static final int WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS = 400;
	private static final String APPLICATION_FRAME_TITLE = "Properties Inspector";
	private static final String LABEL_FOR_MENU_FILE = "File";
	private static final String LABEL_FOR_MENUITEM_FILE_OPEN = "Open …";
	private static final String LABEL_FOR_ROOTNODE_DEFAULT = "Properties";
	private static final String PATH_FOR_PROPERTY_FILES_DEFAULT = "S:\\ARSIN_V01";

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called as main program or without the specification of a property file.
	 */
	public clsPropertiesInspector() throws HeadlessException {
		this.setVisible(true); // Sets the frame of the program visible.
	    initComponents(); // Constructs the layout of the programs pane (window).
	}

	
	/**
	 * @param arg0
	 */
	public clsPropertiesInspector(GraphicsConfiguration arg0) {
		super(arg0);
		// Auto-generated constructor stub
	}

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called with the fully qualified name of the properties file as parameter.
	 * !!! This deviates from the definition of the constructor for JFrame objects !!!
	 */
	public clsPropertiesInspector(String arg0) throws HeadlessException {
		this.setVisible(true); // Sets the frame of the program visible.
	    initComponents(); // Constructs the layout of the programs pane (window).
	    propertyFilename = arg0;
		setBWProperties (propertyFilename); // Sets the BWProperties object from the property file.
	}

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called by another program which provides the path name and local file name of the properties file as parameters.
	 */
	public clsPropertiesInspector(String arg0, String arg1) throws HeadlessException {
		this.setVisible(true); // Sets the frame of the program visible.
	    initComponents(); // Constructs the layout of the programs pane (window).
	    propertyFilename = arg0 + SEPARATOR_FOR_PATH + arg1;
		setBWProperties (propertyFilename); // Sets the BWProperties object from the property file.
	}

	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public clsPropertiesInspector(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// Auto-generated constructor stub
	}

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called by another program which provides the clsBWProperties object.
	 */
	public clsPropertiesInspector(clsBWProperties receivedProperties) throws HeadlessException {
		this.setVisible(true); // Sets the frame of the program visible.
	    initComponents(); // Constructs the layout of the programs pane (window).
		BWProperies = receivedProperties; // Stores the received clsBWProperties object in the foreseen private field.
	    jtrConfigTree = setPropertiesTree (BWProperies); // Creates the tree from the received clsBWProperties object and sets private field foreseen for the tree.
	    jspScrollPane1.setViewportView(jtrConfigTree); // Makes the tree the object to be displayed in the foreseen view.
	}

	
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		final String[] arguments = args;

		EventQueue.invokeLater(new Runnable() { // Automatically generated call back object.

			@Override
        	public void run() { // Automatically generated call back method.
				switch (arguments.length) {
				case 1: new clsPropertiesInspector(arguments[0]); break; // The application is called without an initially loaded clsBWProperties object.
				case 2: new clsPropertiesInspector(arguments[0], arguments[1]); break; // The application is called without an initially loaded clsBWProperties object.
				default: new clsPropertiesInspector(); // The application is called without an initially loaded clsBWProperties object.
				}
            }            	
        });
	}

	
    /**
     * This method fetches the property value with the specified key from the clsBWProperties object displays it in text area 1 and sets this area to editable.
     * If the key is an empty string, the method clears an eventually previously displayed value from text area 1 and blocks the editability of this text area.
     */
	private void displayPropertyvalue (String key) { // The key identifies the property value in the object BWProperties. 

		if (key.isEmpty()) { // No key specified.
			jtaTextArea1.setText(""); // Text area 1 is empty.
    		jtaTextArea1.setEditable(false); // Inhibits to edit the empty text area.
		}
    	else {
			jtaTextArea1.setText(BWProperies.getProperty(key)); // The value identified by the key in object BWProperties is set as the text of text area 1.
			jtaTextArea1.setEditable(true); // Enables to edit the value in the text area.
    	}
	}

	
    /**
     * If the child nodes of parentNode are sorted alphabetically, this method delivers the index after parentNode's child with the highest lower label in alphabetical order.
     * If no such child exists the method returns 0.
     */
	@SuppressWarnings("unchecked")
	private int getIndexAfterChildWithHighestLowerLabel (String  newNodeLabel, DefaultMutableTreeNode parentNode) {
		int currentIndex = 0; // The index for the new node. If there are no nodes with lower or equal label, the index will remain 0.
		DefaultMutableTreeNode currentChild;
		Enumeration<DefaultMutableTreeNode> eChildren;

		for (eChildren = parentNode.children() ; eChildren.hasMoreElements() ;) { // Runs through the enumeration of the children of parent node.
			currentChild = eChildren.nextElement(); // Reads the current child and steps over to the next child afterwards implicitly.
			if (newNodeLabel.compareTo((String)currentChild.getUserObject()) >= 0) 
				currentIndex++; // If newNodeLabel is still greater or equal than the label of the current child it has to get a higher index
			else // The label of the most recent child is lower as the new one so the reached index is okay and the loop can be stopped.
				break;
		}
		return currentIndex;
	}

	
    /**
     * This method searches for a child node of parent node with newNodeLabel as its label.
     * It returns the first node with this label if such a node exists, null otherwise.
     */
	@SuppressWarnings("unchecked")
	private DefaultMutableTreeNode getLabeledChildOfParentNode (String  newNodeLabel, DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode localLabeledChildOfParentNode = null, currentChild;
		Enumeration<DefaultMutableTreeNode> eChildren; // enumeration of children of a node

		for (eChildren = parentNode.children() ; eChildren.hasMoreElements() ;) { // Runs through the enumeration of the children of parent node.
			currentChild = eChildren.nextElement(); // Reads the current child and steps over to the next child afterwards implicitly.
			if (newNodeLabel.equals(currentChild.getUserObject())) { 
				localLabeledChildOfParentNode = currentChild; // If the current child is labeled by newNodeLabel it is stored as return value and the loop is ended.
				break;
			}
		}
		return localLabeledChildOfParentNode;
	}

	
    /**
     * This reconstructs the path of a node as a string. The node labels get separated by SEPERATOR_FOR_TREE_LEVELS.
     */
	private String getPath (DefaultMutableTreeNode anyNode) {
		int l1;
		String path = "";
		TreeNode[] pathNodes;
		
		pathNodes = anyNode.getPath();
		if  (anyNode.getLevel() > 0) { // The root node is not used added to the path.
			path = path + pathNodes[1]; // The first added level is level 1.
			for (l1 = 2; l1 <= anyNode.getLevel(); l1++) // Now all the other levels are added preceded by SEPERATOR_FOR_TREE_LEVELS.
				path = path + SEPERATOR_FOR_TREE_LEVELS + pathNodes[l1];
		}
		return path;
	}

	
    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {

    	// Initializes private variables
    	menuBar = new JMenuBar(); // Creates the menu bar of the frame.
    	menuFile = new JMenu(LABEL_FOR_MENU_FILE); // Creates the file menu.
    	menuItemOpenFile = new JMenuItem(LABEL_FOR_MENUITEM_FILE_OPEN); // Creates the menu item to open files.
    	jfcFileChooser = new JFileChooser(PATH_FOR_PROPERTY_FILES_DEFAULT); // Creates file chooser for the file treatment dialogs initially pointing at the path PATH_FOR_PROPERTY_FILES_DEFAULT.

    	jtaTextArea1 = new JTextArea(); // Creates the text area that will contain the configuration values that will contain the tree..
        jspScrollPane1 = new JScrollPane(); // Creates the pane that will contain the tree.
        jspScrollPane2 = new JScrollPane(); // Creates the pane that will contain the selected configuration values.
         
    	setTitle(APPLICATION_FRAME_TITLE); // Sets the title of the application's frame.
    	setLocation(APPLICATION_FRAME_POSITION_HORIZONTAL, APPLICATION_FRAME_POSITION_VERTICAL); // Sets the position of the application's frame on the screen.
    	menuFile.add(menuItemOpenFile); // Composes the file menu.
    	menuBar.add(menuFile); // Composes the menu bar.
    	setJMenuBar(menuBar); // Sets the menu bar for the frame.
    	
    	final clsPropertiesInspector thisForEverySubobject = this; // variable to hand over the frame to the definitions of the menu actions
    	
    	menuItemOpenFile.setAction(new AbstractAction () { // Implements the action to open property files.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				int returnVal;
    			
				returnVal = jfcFileChooser.showOpenDialog(thisForEverySubobject); // The dialog is called as a child of the main frame. It returns, whether a file has been opened or the dialog has been canceled.

				if (returnVal == JFileChooser.APPROVE_OPTION) { // A new file has been selected.
					propertyFilename = jfcFileChooser.getSelectedFile().getPath(); // The applications property file name is set to the name of the newly selected file.
					setBWProperties(propertyFilename);
				}
    		}
    	});

    	menuItemOpenFile.getAction().putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_FILE_OPEN); // Equips the open file action with the foreseen name for the menu.
    	   	
    	jtaTextArea1.setEditable(false); // Inhibits to edit the initially empty text area.
    	
    	jtaTextArea1.getDocument().addDocumentListener(new DocumentListener () { // Creates the methods to detect text changes in the text area and to write back the property value to the clsBWProperies Object.

			@Override
			public void insertUpdate(DocumentEvent event) {
				writebackPropertyvalue(jtaTextArea1.getText());
    		}

			@Override
			public void removeUpdate(DocumentEvent event) {
    	    	writebackPropertyvalue(jtaTextArea1.getText());
    		}

			@Override
			public void changedUpdate(DocumentEvent event) {
    	    	writebackPropertyvalue(jtaTextArea1.getText());
    		}
    	});
    	
    	jspScrollPane1.setViewportView(jtaTextArea1); // The text area to display the selected property value is put into scroll pane 2.
    	jspScrollPane2.setViewportView(jtaTextArea1); // The text area to display the selected property value is put into scroll pane 2.
      
    	GroupLayout layout = new GroupLayout(getContentPane()); // Creates a GroupLayout object.
        getContentPane().setLayout(layout); // Specifies, that the layout used to build the application window has to be the created GroupLayout.
        layout.setHorizontalGroup( // Specifies the positions of the components of the window.
        	layout.createParallelGroup() // The tree and the text area (respectively the scroll panes containing them) should be at the same horizontal position.
        		.addComponent(jspScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS, Short.MAX_VALUE)
        		.addComponent(jspScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS, Short.MAX_VALUE)
        );
        layout.setVerticalGroup( // The text area should be below the tree.
        	layout.createSequentialGroup()
        		.addComponent(jspScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, HEIGHT_PREFERRED_FOR_TREE_PANE, Short.MAX_VALUE)
        		.addComponent(jspScrollPane2, HEIGHT_FIXED_FOR_TEXT_AREA1, HEIGHT_FIXED_FOR_TEXT_AREA1, HEIGHT_FIXED_FOR_TEXT_AREA1)
        );

    	pack(); // Reshapes the frames pane.
    }

	
    /**
     * This method sets the BWProteries field with the contents of a fully qualified properties file,
     * and it cleans text area 1 and sets it to not editable.
     */
	private void setBWProperties (String propertyFileName) {
		int iOfBackslash;
	
    	jtaTextArea1.setText(""); // Cleans the text area.
    	jtaTextArea1.setEditable(false); // Inhibits to edit the initially empty text area.
		iOfBackslash = propertyFilename.lastIndexOf(SEPARATOR_FOR_PATH);
		BWProperies = clsBWProperties.readProperties(propertyFilename.substring (0, iOfBackslash), propertyFilename.substring (iOfBackslash + 1)); // Reads the given properties file and stores it in the clsBWProperties object.
	    jtrConfigTree = setPropertiesTree (BWProperies); // Creates the tree from the received clsBWProperties object and sets private field foreseen for the tree.
	    jspScrollPane1.setViewportView(jtrConfigTree); // Makes the tree the object to be displayed in the foreseen view.
	}

    
    /**
     * This method creates a JTree object from a clsBWProperties object.
     */
	private JTree setPropertiesTree (clsBWProperties anyProperties) {
		// Definition of local variables.
		DefaultMutableTreeNode branch = null, currentNode, nextNode, top;
		final JTree ljtrConfigTree; // local tree object
		String firstPartOfLabel, restOfLabel;
		String[] propertyLabels;
		int iOfDot, nPropertyelements;
		
		nPropertyelements = anyProperties.size(); // The number of properties is detected as the size of the object anyProperties.
		propertyLabels = anyProperties.stringPropertyNames().toArray(new String[0]); // The keys of the properties are put into the String array propertyLabels.

		top = new DefaultMutableTreeNode(propertyFilename.isEmpty()? LABEL_FOR_ROOTNODE_DEFAULT: propertyFilename); // A new root node is created from the name of the properties file. If there is no file, the root node is a constant.
    	ljtrConfigTree = new JTree(top); // top is hooked in as the root of the local tree.
        
    	for (int l1 = 0; l1 < nPropertyelements; l1++) { // Every property from the object anyProperties is added to the tree.
    		restOfLabel = propertyLabels[l1]; // The label (the key) of the current property is read into restOfLabel.
    		currentNode = top; // The current position is set to top level of the tree.
    		while (true){ // As long as there are dots in the current property label, it consists of more then one tree nodes.
    			iOfDot = restOfLabel.indexOf(SEPERATOR_FOR_TREE_LEVELS); // If there is a SEPERATOR_FOR_TREE_LEVELS in the label, its position is stored in iOfDot. Otherwise iOfDot is 0.
    			if (iOfDot < 0) { // There is no dot in the label of the property. The label has to generate a leaf in the tree.
    	        	branch = new DefaultMutableTreeNode(restOfLabel); // The new leaf is created.
    				currentNode.insert(branch, getIndexAfterChildWithHighestLowerLabel (restOfLabel, currentNode)); // The new leaf is inserted on its place in alphabetical order.
    				break; // As the node is a leaf there are no more subtrees to be created. The while loop is exited and the next property can be handled in the for loop.
    			} else { // There is a dot in the label, so its first part has to be handled as a non-leaf node in the tree.
        			firstPartOfLabel = restOfLabel.substring(0, iOfDot); // The part of the (rest) label of the property preceding the first dot is extracted.
        			restOfLabel = restOfLabel.substring(iOfDot + 1); // The reminder of the property label (key) after removing the first part is calculated.
        			nextNode = getLabeledChildOfParentNode (firstPartOfLabel, currentNode); // If firstPartOfLabel already exists as a node, it has to be selected as the branch where the new property has to be hooked in.
        			if (nextNode == null) { // firstPartOfLabel is not already a node of the tree, it has to be created.
        	        	branch = new DefaultMutableTreeNode(firstPartOfLabel); // A new node is created with label firstPartOfLabel.
        				currentNode.insert(branch, getIndexAfterChildWithHighestLowerLabel (firstPartOfLabel, currentNode)); // The new node is inserted on its place in alphabetical order.
        				nextNode = branch; // nextNode is set to be the branch where the new property has to be hooked in.
        			}
    				currentNode = nextNode; // The current position is set to the next node to get forward in the while loop.
    			}
    		}
		}
	
    	ljtrConfigTree.addTreeSelectionListener(new TreeSelectionListener() { // For the new tree a method is created, to react on the selection of nodes.
    	    @Override
			public void valueChanged(TreeSelectionEvent anyEvent) {
    	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) ljtrConfigTree.getLastSelectedPathComponent(); // The variable node will hold the last selected node.

    	    	/* if nothing is selected */ 
    	        if (node == null) return;

    	        if (node.isLeaf()) 
    	        	displayPropertyvalue (getPath (node)); // If the selected node is a leaf, the corresponding property value has to be displayed in the form. The complete path of the node has to be handed over to the method displayPropertyvalue.
    	        else
    	        	displayPropertyvalue (""); // If the selected node is not a leaf an eventually previous display has to be cleaned. This has to be indicated to displayPropertyvalue by handing over an empty string.
    	    }
    	});

 
    	return ljtrConfigTree; // The locally constructed tree gets returned as the result of tree composition.
	}

	
    /**
     * This method writes the modified value back to the selected node in the clsBWPropertiesObject.
     */
	private void writebackPropertyvalue (String propertyvalue) {
		DefaultMutableTreeNode node;

		node = (DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent();
		if (node.isLeaf())
			BWProperies.setProperty(getPath (node), propertyvalue); // The given value is set for the property with the path of the recently selected node as key.
	}
}
