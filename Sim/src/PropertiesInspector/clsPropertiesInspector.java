package PropertiesInspector;


import config.clsProperties;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 * @author Matthias Jakubec
 *
 * The class builds a GUI to display the contents of the clsProperties object,
 * which holds the property parameters in the project ARS V38.
 * 
 * It can be called
 * 		.) from within Eclipse via the "main" method.
 * 			In this case it displays the file specified as argument at the program call.
 * 			The program can be called with no, one or two string arguments.
 * 			In the case of no argument, the initially displayed properties object is empty.
 * 			In the case of one argument, this is interpreted as a fully qualified file name of a properties file.
 * 			In the case of one argument, these are interpreted as path name and local file name of a properties file.
 * 		.) by another application using the constructor with an existing clsProperties object as input parameter.
 */
public class clsPropertiesInspector extends JDialog { // To appear at the desktop the class is defined as a child of JFrame (package "javax.swing").

	
	private static final long serialVersionUID = 1L;
	// Fields for the Components of the properties inspector's pane.
	private JTree jtrConfigTree = null;
	private JTextArea jtaTextArea1;
	private JTextField jtfTextField1;
	private JLabel lblLabel1;
	private JTextField jtfTextField2;
	private JLabel lblLabel2;
	private JLabel lblLabelRename;
	private JTextField jtfTextFieldRename;
    private JPanel pnlEditArea;
    private JPanel pnlNodeNew;
    private JPanel pnlNodeNewButtons;
    private JPanel pnlNodeNewEdit;
    private JPanel pnlNodeNewName;
    private JPanel pnlNodeNewValue;
    private JPanel pnlNodeRename;
    private JPanel pnlNodeRenameButtons;
    private JPanel pnlNodeRenameEdit;
    private JButton btnNodeNewAdd;
    private JButton btnNodeNewCancel;
    private JButton btnNodeRename;
    private JButton btnNodeRenameCancel;
	private JMenuBar menuBar;
	private JMenu menuEdit;
	private JMenu menuFile;
	private JMenu menuView;
	private JPopupMenu menuPopup;
	private JMenuItem menuItemExpandSubtree;
	private JMenuItem menuItemCutSubtree;
	private JMenuItem menuItemDeleteSubtree;
	private JMenuItem menuItemEditNodeNew;
	private JMenuItem menuItemEditPaste;
	private JMenuItem menuItemEditPasteRenamed;
	private JMenuItem menuItemEditTreeCopy;
	private JMenuItem menuItemFileOpen;
	private JMenuItem menuItemFileSave;
	private JMenuItem menuItemFileSaveAs;
	private JMenuItem menuItemExpandSubtreePopup;
	private JMenuItem menuItemCutSubtreePopup;
	private JMenuItem menuItemDeleteSubtreePopup;
	private JMenuItem menuItemEditNodeNewPopup;
	private JMenuItem menuItemEditPastePopup;
	private JMenuItem menuItemEditPasteRenamedPopup;
	private JMenuItem menuItemEditTreeCopyPopup;
	private JScrollPane jspScrollPane1, jspScrollPane2;
	private JFileChooser jfcFileChooser;
	private AbstractAction actionExpandSubtree;
	private AbstractAction actionCutSubtree;
	private AbstractAction actionDeleteSubtree;
	private AbstractAction actionNodeNew;
	private AbstractAction actionPaste;
	private AbstractAction actionPasteRenamed;
	private AbstractAction actionTreeCopy;
	private AbstractAction actionNodeNewAdd;
	private AbstractAction actionEditCancel;
	private AbstractAction actionSubtreeAddRenamed;
	private AbstractAction actionOpen;
	private AbstractAction actionSave;
	private AbstractAction actionSaveAs;
	private boolean isPropertiesTreeToBeRestored = false;
	private clsProperties BWProperties = null;
	private String lastPropertyValue = ""; // Stores the value of the currently selected property.
	private String propertyFilename = ""; // Fully qualified name of the loaded property file.
	private DefaultMutableTreeNode lastSelectedNode; // Stores the last selected node to enable writing it after it lost the focus.
	private boolean propertiesModified = false; // Stores, whether the properties have been modified.
	private String [] bufferPropertylabels; // Stores the property labels to be copied.
	private String [] bufferPropertyvalues; // Stores the property values to be copied.
	private Enumeration<TreePath> bufferCurrentExpansion; // Stores the current expansion satus of the properties tree.
	private TreePath bufferCurrentSelection; //Stores the path of the currently selected node of the properties tree.

	
	// Configuration Constants
	private static final char SEPARATOR_FOR_PATH = '\\';
	private static final char SEPERATOR_FOR_TREE_LEVELS = '.';
	private static final int APPLICATION_FRAME_POSITION_HORIZONTAL = 300;
	private static final int APPLICATION_FRAME_POSITION_VERTICAL = 200;
	private static final int HEIGHT_FIXED_FOR_TEXT_AREA1 = 50;
	private static final int HEIGHT_PREFERRED_FOR_TREE_PANE = 600;
	private static final int INDENT_DEFAULT = 10;
	private static final int WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS = 400;
	private static final Object[] OPTIONS_OKAY = {"Okay"};
	private static final Object[] OPTIONS_YES_NO = {"Yes", "No"};
	private static final Object[] OPTIONS_YES_NO_CANCEL = {"Yes", "No", "Cancel"};
	private static final String APPLICATION_FRAME_TITLE = "Properties Inspector";
	private static final String LABEL_FOR_BUTTON_EDIT_CANCEL = "Cancel";
	private static final String LABEL_FOR_BUTTON_NODE_NEW_ADD = "Add";
	private static final String LABEL_FOR_BUTTON_NODE_RENAME = "Proceed";
	private static final String LABEL_FOR_BUTTON_NODE_RENAME_CANCEL = "Cancel";
	private static final String LABEL_FOR_CONFIRMATION_DIALOG = "Confirmation Dialog";
	private static final String LABEL_FOR_DIALOG_NOT_SAVED = "Not saved";
	private static final String LABEL_FOR_DIALOG_REPLACE_VALUE_BY_NODE = "Dialog Replace Value";
	private static final String LABEL_FOR_INFORMATION = "Information window";
	private static final String LABEL_FOR_INPUT_FIELD_NAME = "Name: ";
	private static final String LABEL_FOR_INPUT_FIELD_VALUE = "Value: ";
	private static final String LABEL_FOR_INPUT_FIELD_RENAME = "New label of the root of the to be inserted subtree:";
	private static final String LABEL_FOR_MENU_EDIT = "Edit";
	private static final String LABEL_FOR_MENU_FILE = "File";
	private static final String LABEL_FOR_MENU_VIEW = "View";
	private static final String LABEL_FOR_MENUITEM_EDIT_TREE_CUT = "Cut subtree";
	private static final String LABEL_FOR_MENUITEM_EDIT_TREE_DELETE = "Delete subtree";
	private static final String LABEL_FOR_MENUITEM_EDIT_NODE_NEW = "New node …";
	private static final String LABEL_FOR_MENUITEM_EDIT_PASTE = "Paste";
	private static final String LABEL_FOR_MENUITEM_EDIT_PASTE_RENAMED = "Paste renamed …";
	private static final String LABEL_FOR_MENUITEM_EDIT_TREE_COPY = "Copy subtree";
	private static final String LABEL_FOR_MENUITEM_FILE_OPEN = "Open …";
	private static final String LABEL_FOR_MENUITEM_FILE_SAVE = "Save";
	private static final String LABEL_FOR_MENUITEM_FILE_SAVE_AS = "Save as …";
	private static final String LABEL_FOR_MENUITEM_VIEW_TREE_EXPAND = "Expand subtree";
	private static final String LABEL_FOR_ROOTNODE_DEFAULT = "Properties";
	private static final String LABEL_INFORMATION_COPY_BUFFER_EMPTY = "The copy buffer is empty.\nPlease perform function copy first.";
	private static final String LABEL_INFORMATION_NEW_KEY_EMPTY = "The new key is empty.\nPlease specify a name.";
	private static final String LABEL_INFORMATION_NEW_KEY_EXISTS = "A node with the specified name already exists.\nPlease specify a different name.";
	private static final String LABEL_INFORMATION_NO_NODE_SELECTED = "You didn't select a node in the tree.\nPlease select a node.";
	private static final String LABEL_INFORMATION_ROOT_NODE_EXISTS = "A node with the same name as the root of the to be copied subtree already exists.\nThe subtree cannot be pasted here.\n(Hint: you may use 'Paste renamed …'.)";
	private static final String LABEL_INFORMATION_ROOT_NODE_SELECTED = "The selected node is the root, which is not allowed for this operation.\nPlease select a different node.";
	private static final String PATH_FOR_PROPERTY_FILES_DEFAULT = "S:\\ARSIN_V01";
	private static final String QUESTION_CONFIRMATION_DIALOG_FILE_SAVE = " already exists!\nDo you want to overwrite?";
	private static final String QUESTION_DIALOG_PROPERTIES_NOT_SAVED_PART_1 = "The properties ";
	private static final String QUESTION_DIALOG_PROPERTIES_NOT_SAVED_PART_2 = "in file ";
	private static final String QUESTION_DIALOG_PROPERTIES_NOT_SAVED_PART_3 = "have been modified.\nSave changes?";
	private static final String QUESTION_DIALOG_REPLACE_VALUE_BY_NODE_PART1 = "Current node '";
	private static final String QUESTION_DIALOG_REPLACE_VALUE_BY_NODE_PART2 = "' has a value.\nThe new node or subtree can only be added below of it, if the value gets deleted.\nShould the new node / subtree be added and the value be deleted?";

	public clsPropertiesInspector(JFrame owner, String title, boolean modal, String propertyPathname, String propertyFilenameLocal)
	{
		super(owner, title, modal);
		propertyFilename = propertyPathname + SEPARATOR_FOR_PATH + propertyFilenameLocal;
		initApplication(); // Initiates the application and constructs the layout of the programs pane (window).
	}
	
	/**
	 * @throws HeadlessException
	 * Is used when class is called without the specification of a property file.
	 */
	public clsPropertiesInspector() throws HeadlessException {
	    propertyFilename = ""; // Ensures that an empty string indicates that no property file is specified.
		initApplication(); // Initiates the application and constructs the layout of the programs pane (window).
	}

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called with the fully qualified name of the properties file as parameter.
	 * !!! This deviates from the definition of the constructor for JFrame objects !!!
	 */
	public clsPropertiesInspector(String propertyFilenameFullyQualified) throws HeadlessException {
	    propertyFilename = propertyFilenameFullyQualified;
		initApplication(); // Initiates the application and constructs the layout of the programs pane (window).
	}

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called by another program which provides the path name and local file name of the properties file as parameters.
	 */
	public clsPropertiesInspector(String propertyPathname, String propertyFilenameLocal) throws HeadlessException {
	    propertyFilename = propertyPathname + SEPARATOR_FOR_PATH + propertyFilenameLocal;
		initApplication(); // Initiates the application and constructs the layout of the programs pane (window).
	}

	
	/**
	 * @throws HeadlessException
	 * Is used when class is called by another program which provides the clsProperties object.
	 */
	public clsPropertiesInspector(clsProperties receivedProperties) throws HeadlessException {
	    propertyFilename = ""; // Ensures that an empty string indicates that no property file is specified.
		BWProperties = receivedProperties; // Stores the received clsBWProperties object in the foreseen private field.
		initApplication(); // Initiates the application and constructs the layout of the programs pane (window).
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
				case 1: new clsPropertiesInspector(arguments[0]); break; // The application is called without an initially loaded clsProperties object.
				case 2: new clsPropertiesInspector(arguments[0], arguments[1]); break; // The application is called without an initially loaded clsProperties object.
				default: new clsPropertiesInspector(); // The application is called without an initially loaded clsProperties object.
				}
            }            	
        });
	}

	
	/**
     * This method performs the necessary activities when a new subtree or single node has to be added after a node new or a copy action.
     * If the to be inserted node is result of a copy isACopy has to be true.
     */
	private void addSubtree (Component thisForTheSubobject, boolean isACopy, boolean isRenamed) {
		
		DefaultMutableTreeNode currentNode;
		int returnVal1;
		String currentPath = "";
		
		currentNode = (DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent(); // Finds the node, below which the new subtree has to be added.
		if (currentNode == null) { // No parent node is selected.
			JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog informs that there is no node selected.
				LABEL_INFORMATION_NO_NODE_SELECTED, // Information that no current node is selected.
				LABEL_FOR_INFORMATION, // Label of the dialog window
				JOptionPane.OK_OPTION, // Dialog type "Okay_Option" is indicated.
				JOptionPane.INFORMATION_MESSAGE, // Message type "Information" is indicated.
				null, // No specific icon is set.
				OPTIONS_OKAY, // Only the option "Okay" is available for choose.
				OPTIONS_OKAY[0]); // Option "Okay" is preselected.
		} else { // New node has a parent.
			currentPath = getPath (currentNode);
			if (currentNode.isLeaf()) {
				if (BWProperties.getProperty(currentPath).isEmpty()) { // As the leaf has no value, the new subtree simply can be added below of it.
					addSubtreeParticularly(thisForTheSubobject, currentNode, currentPath, isACopy, isRenamed, true);						
				} else { // The leaf node has a value. The new node can only be added if the value gets deleted.
					returnVal1 = JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog asks, whether the existing value of the leaf should be replaced by the new subtree or whether the value should remain untouched and the new node should be withdrawn.
						QUESTION_DIALOG_REPLACE_VALUE_BY_NODE_PART1 + currentNode.toString() + QUESTION_DIALOG_REPLACE_VALUE_BY_NODE_PART2, // The question, whether the value should be overwritten is composed from 2 text blocks and the label of the current node.
						LABEL_FOR_DIALOG_REPLACE_VALUE_BY_NODE, // Label of the dialog window
						JOptionPane.YES_NO_OPTION, // Dialog type "Option" is indicated.
						JOptionPane.QUESTION_MESSAGE, // Message type "Question" is indicated.
						null, // No specific icon is set.
						OPTIONS_YES_NO, // Two options are available for choose.
						OPTIONS_YES_NO[1]); // Option "No" is preselected.
					if (returnVal1 == 0) { // If the action to add the new subtree has been acknowledged by the user it gets performed.
						addSubtreeParticularly(thisForTheSubobject, currentNode, currentPath, isACopy, isRenamed, true);						
					} else { // The action to add a new node has to be cancelled.
						actionEditCancel.actionPerformed(null);
					}
				}
			} else { // New subtree has to be added below current node as another sibling of its children.
				addSubtreeParticularly(thisForTheSubobject, currentNode, currentPath, isACopy, isRenamed, false);						
			}									
		}
	}

	
	/**
     * This method adds a new subtree below current node and current path. isACopy indicates, whether the new subtree is a copy.
     */
	private void addSubtreeParticularly(Component thisForTheSubobject, DefaultMutableTreeNode currentNode, String currentPath,  boolean isACopy, boolean isRenamed, boolean isForeseenParentALeaf) {
		
		int iOfDot, l1;
		String labelRootOfSubtree, labelRootOfSubtreeOld = "";
		
		if (isACopy) { // If the buffer is empty copy operations cannot be performed.
			if (bufferPropertylabels == null) { // If the buffer is empty an add (= paste) operation cannot be performed.
				JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog informs that the copy buffer is empty.
					LABEL_INFORMATION_COPY_BUFFER_EMPTY, // Information that the copy buffer is empty.
					LABEL_FOR_INFORMATION, // Label of the dialog window
					JOptionPane.OK_OPTION, // Dialog type "Okay_Option" is indicated.
					JOptionPane.INFORMATION_MESSAGE, // Message type "Information" is indicated.
					null, // No specific icon is set.
					OPTIONS_OKAY, // Only the option "Okay" is available for choose.
					OPTIONS_OKAY[0]); // Option "Okay" is preselected.
				return;
			} else { // The new subtree is a copy, add can be performed.
				if (isRenamed) {
					for (l1 = 0; l1 < bufferPropertylabels.length; l1++) { // The buffer is looked through for the first to be pasted node, to get the to be replaced node name.
						if (!bufferPropertylabels[l1].isEmpty()) {
			    			iOfDot = bufferPropertylabels[l1].indexOf(SEPERATOR_FOR_TREE_LEVELS); // If there is a SEPERATOR_FOR_TREE_LEVELS in the label, its position is stored in iOfDot. Otherwise iOfDot is -1.
			    			if (iOfDot < 0) { // There is no dot in the label. The whole label is the name of the root node.
			    				labelRootOfSubtreeOld = bufferPropertylabels[l1];
			    			} else { // There is a dot in the label, so its first part is the name of the root node.
			    				labelRootOfSubtreeOld = bufferPropertylabels[l1].substring(0, iOfDot); // The part of the label preceding the first dot is extracted.
			    			}
							break;
						}
					}
					labelRootOfSubtree = jtfTextFieldRename.getText();
				} else { // If the to be added subtree is a copy and not renamed, the names of all potential sibblings must not be the same as the name of the subtree's root.
					labelRootOfSubtree = "";
					for (l1 = 0; l1 < bufferPropertylabels.length; l1++) { // The buffer is looked through for the first to be pasted node.
						if (!bufferPropertylabels[l1].isEmpty()) {
			    			iOfDot = bufferPropertylabels[l1].indexOf(SEPERATOR_FOR_TREE_LEVELS); // If there is a SEPERATOR_FOR_TREE_LEVELS in the label, its position is stored in iOfDot. Otherwise iOfDot is -1.
			    			if (iOfDot < 0) { // There is no dot in the label. The whole label is the name of the root node.
			    				labelRootOfSubtree = bufferPropertylabels[l1];
			    			} else { // There is a dot in the label, so its first part is the name of the root node.
			    				labelRootOfSubtree = bufferPropertylabels[l1].substring(0, iOfDot); // The part of the label preceding the first dot is extracted.
			    			}
							break;
						}
					}
				}
			}
		} else { // If the new node is not a copy its name is taken from the text field.
			labelRootOfSubtree = jtfTextField1.getText();
		}
		
		if ((bufferPropertylabels != null || !isACopy) && labelRootOfSubtree.isEmpty()) { // The new node label is empty string, which is not allowed, but is only relevant if the action is not copy or the copy buffer is not empty.
			JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog informs that the name field for the key is empty.
				LABEL_INFORMATION_NEW_KEY_EMPTY, // Information that the label of the new key is empty.
				LABEL_FOR_INFORMATION, // Label of the dialog window
				JOptionPane.OK_OPTION, // Dialog type "Okay_Option" is indicated.
				JOptionPane.INFORMATION_MESSAGE, // Message type "Information" is indicated.
				null, // No specific icon is set.
				OPTIONS_OKAY, // Only the option "Okay" is available for choose.
				OPTIONS_OKAY[0]); // Option "Okay" is preselected.
			jtfTextField1.grabFocus(); // The focus will be set into the field for the key label.
		} else if (getLabeledChildOfParentNode (labelRootOfSubtree, currentNode) != null) { // A node with the same label as the new one already exists.
			JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog informs that a node with the name for the new key already exists.
				(isACopy && !isRenamed)? LABEL_INFORMATION_ROOT_NODE_EXISTS: LABEL_INFORMATION_NEW_KEY_EXISTS, // Information that a node with the specified name already exists.
				LABEL_FOR_INFORMATION, // Label of the dialog window
				JOptionPane.OK_OPTION, // Dialog type "Okay_Option" is indicated.
				JOptionPane.INFORMATION_MESSAGE, // Message type "Information" is indicated.
				null, // No specific icon is set.
				OPTIONS_OKAY, // Only the option "Okay" is available for choose.
				OPTIONS_OKAY[0]); // Option "Okay" is preselected.
			jtfTextField1.setText(""); // Clean the name field for the new node.
			jtfTextField1.grabFocus(); // The focus will be set into the field for the key label.
		} else {
			if (isForeseenParentALeaf) {
				BWProperties.remove(currentPath); // The forseen parent as a leaf and has to be deleted, otherwise the new subtree would be created below a new copy of it.
			}
			if (isACopy) {
				if (isRenamed) {  // Function paste renamed.
					for (l1 = 0; l1 < bufferPropertylabels.length; l1++) { // The whole buffer has to be added.
						if (!bufferPropertylabels[l1].isEmpty()) {
							BWProperties.setProperty(currentPath + (currentPath.isEmpty()? "": SEPERATOR_FOR_TREE_LEVELS) + labelRootOfSubtree + bufferPropertylabels[l1].substring(labelRootOfSubtreeOld.length(), bufferPropertylabels[l1].length()), bufferPropertyvalues[l1]); // The new node is added below the current path. If current path is empty, there must not be a level separator.
						}
					}
				} else { // Function paste.
					for (l1 = 0; l1 < bufferPropertylabels.length; l1++) { // The whole buffer has to be added.
						if (!bufferPropertylabels[l1].isEmpty()) {
							BWProperties.setProperty(currentPath + (currentPath.isEmpty()? "": SEPERATOR_FOR_TREE_LEVELS) + bufferPropertylabels[l1], bufferPropertyvalues[l1]); // The new node is added below the current path. If current path is empty, there must not be a level separator.
						}
					}
				}
			} else { // Function new node.
				BWProperties.setProperty(currentPath + (currentPath.isEmpty()? "": SEPERATOR_FOR_TREE_LEVELS) + jtfTextField1.getText(), jtfTextField2.getText()); // The new node is added below the current path. If current path is empty, there must not be a level separator.
			}
			rebuildTree (currentNode); // The tree gets reconstructed from the scratch. 
			propertiesModified = true; // Information that a "save" action is needed.
			actionSave.setEnabled(true); // Switches action "save" on in the menu.
			if (!isACopy || isRenamed) { // Function new node or pasteRenamed only.
				pnlNodeNew.setVisible(false);
				pnlNodeRename.setVisible(false);
				jspScrollPane2.setVisible(true);
				jtaTextArea1.setText("");
		    	jtaTextArea1.setEditable(false); // Inhibits to edit the now empty text area, as the selected node is not a leaf now.
			}
		}
	}

	
	/**
     * This method performs the necessary activities in the case when the properties have not been saved before they will get deselected.
     * The method returns true, if the calling method should continue,
     * it returns false if the calling method should get cancelled.
     */
	private boolean continueIfNotSaved () {
		boolean bContinue = false;
		int returnVal1;

		returnVal1 = JOptionPane.showOptionDialog(this, // If the property file is not saved yet the dialog asks, whether it should be saved.
			QUESTION_DIALOG_PROPERTIES_NOT_SAVED_PART_1 +
			(propertyFilename.isEmpty()? "": QUESTION_DIALOG_PROPERTIES_NOT_SAVED_PART_2 + propertyFilename + " ") +
			QUESTION_DIALOG_PROPERTIES_NOT_SAVED_PART_3, // The dialog text is composed from 3 parts and from the properties file name if available.
			LABEL_FOR_DIALOG_NOT_SAVED, // label for the dialog window
			JOptionPane.YES_NO_CANCEL_OPTION, // Dialog type "Option" is indicated.
			JOptionPane.QUESTION_MESSAGE, // Message type "Question" is indicated.
			null, // No specific icon is set.
			OPTIONS_YES_NO_CANCEL, // Three options are available for choose.
			OPTIONS_YES_NO_CANCEL[0]); // Option "Yes" is preselected.
		switch (returnVal1) {
		case 0: //
			actionSaveAs.actionPerformed(null); // "Yes" selected. The "Save as" dialog gets started. Afterwards the calling method should not continue.
			bContinue = false;
			break;
		case 1:
			bContinue = true; // "No" selected. Properties do not get saved. Nevertheless calling method may continue.
			break;
		case 2: // "Cancel" selected. Calling method should not continue.
			bContinue = false;
			break;
		}
		return bContinue;
	}

	
	/**
     * This method performs the actions copy, cut or delete as they only differ minimally.
     */
	private void copyCutDelete (Component thisForTheSubobject, boolean toCopy, boolean toDelete) { // toCopy has to be true for copy and cut, toDelete has to be true for cut and delete.
		/* Puts the to be copied subtree into the buffer bufferPropertylabels.
		 * If the current node is the root, all properties have to be copied.
		 * In this case the root label is inserted as prefix of the path of every property.
		 * If the current node is different from root node, the entries in bufferPropertylabels of all nodes not to be copied are set to empty string.
		 * From the nodes to be copied the prefix of the path of the current node except the local name of the node is cut.
		 * The resulting array finally contains as many entries as there have been properties in the original properties object.
		 * All entries can be paste (inserted) by just adding all non-empty string lines with the name of the new parent node as prefix.
		 */
		
		DefaultMutableTreeNode currentNode;
		int l1, nPropertyelements, lengthOfPrefix;
		String pathOfCurrentNode;
		String [] propertylabels;

		currentNode = (DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent(); // Finds the node which has to be root of the to be treated subtree.
		if (currentNode == null) { // No node is selected.
			JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog informs that there is no node selected.
				LABEL_INFORMATION_NO_NODE_SELECTED, // Information that no current node is selected.
				LABEL_FOR_INFORMATION, // Label of the dialog window
				JOptionPane.OK_OPTION, // Dialog type "Okay_Option" is indicated.
				JOptionPane.INFORMATION_MESSAGE, // Message type "Information" is indicated.
				null, // No specific icon is set.
				OPTIONS_OKAY, // Only the option "Okay" is available for choose.
				OPTIONS_OKAY[0]); // Option "Okay" is preselected.
		} else { // Node to be copied is selected.
			pathOfCurrentNode = getPath (currentNode); // Stores the path of the current node in local variable.
			lengthOfPrefix = pathOfCurrentNode.length() - jtrConfigTree.getLastSelectedPathComponent().toString().length(); // Computes the length of the prefix, that has to be removed in the labels of the to be copied properties.
			if (pathOfCurrentNode.isEmpty()) { // The selected node is the root.
				JOptionPane.showOptionDialog(thisForTheSubobject, // A dialog informs that the selected node is the root node, which is not permitted.
					LABEL_INFORMATION_ROOT_NODE_SELECTED, // Information that the root node is selected.
					LABEL_FOR_INFORMATION, // Label of the dialog window
					JOptionPane.OK_OPTION, // Dialog type "Okay_Option" is indicated.
					JOptionPane.INFORMATION_MESSAGE, // Message type "Information" is indicated.
					null, // No specific icon is set.
					OPTIONS_OKAY, // Only the option "Okay" is available for choose.
					OPTIONS_OKAY[0]); // Option "Okay" is preselected.
			} else { // The selected node is different from root.
				nPropertyelements = BWProperties.size(); // The number of properties is detected as the number of objects in the properties class.
				propertylabels = BWProperties.stringPropertyNames().toArray(new String[0]); // The keys of the properties are put into the String array propertyLabels.
				if (toCopy) { // Actions for copy and cut.
					bufferPropertylabels = BWProperties.stringPropertyNames().toArray(new String[0]); // The buffers have to be prepared, the keys of the properties are put into the String array bufferPropertyLabels.
					bufferPropertyvalues = new String [bufferPropertylabels.length];				
					for (l1 = 0; l1 < nPropertyelements; l1++) {
						if (propertylabels[l1].startsWith(pathOfCurrentNode)) { // Property has to be copied.
							bufferPropertyvalues[l1] = BWProperties.getProperty(bufferPropertylabels[l1]); // Put value of property into buffer.
							bufferPropertylabels[l1] = bufferPropertylabels[l1].substring(lengthOfPrefix); // Put label of property into buffer.
						} else { // Property remains uncopied.
							bufferPropertyvalues[l1] = ""; // Value buffer remains empty.
							bufferPropertylabels[l1] = ""; // Label buffer remains empty.
						}
					}
				}
				if (toDelete) { // Actions for cut and delete.
					for (l1 = 0; l1 < nPropertyelements; l1++) {
						if (propertylabels[l1].startsWith(pathOfCurrentNode)) { // Property has to be deleted.
							BWProperties.remove(propertylabels[l1]);
						}
					}
					if (currentNode.getSiblingCount() <= 1) { // If root of the deleted subtree (it is not yet deleted in the tree, only in  the properties) has no sibblings (besides itself), a property named with the label of the parent of current node has to be added.
						BWProperties.setProperty(getPath ((DefaultMutableTreeNode) currentNode.getParent()), ""); // The value of the property in current node is empty string after the deletion of its .
					}
					rebuildTree ((DefaultMutableTreeNode) currentNode.getParent()); // The tree gets reconstructed from the scratch.
					propertiesModified = true; // Information that it is needed to save the tree.
					actionSave.setEnabled(true); // Information to activate the "save" menu entry.
				}
			}
		}
	}


	/**
     * This method fetches the property value with the specified key from the clsProperties object displays it in text area 1 and sets this area to editable.
     * If the key is an empty string, the method clears an eventually previously displayed value from text area 1 and blocks the editability of this text area.
     */
	private void displayPropertyvalue (String key) { // The key identifies the property value in the object BWProperties. 

		if (key.isEmpty()) { // No key specified.
			jtaTextArea1.setText(""); // Text area 1 is empty.
    		jtaTextArea1.setEditable(false); // Inhibits to edit the empty text area.
		}
    	else {
			jtaTextArea1.setText(BWProperties.getProperty(key)); // The value identified by the key in object BWProperties is set as the text of text area 1.
 			jtaTextArea1.setEditable(true); // Enables to edit the value in the text area.
    	}
	}

	
    /**
     * This method expands the subtree of jtrConfigTree identified by selectedPath and all its subtrees.
     */
	@SuppressWarnings("unchecked") // As the usage of an Enumeration would cause a warning. Type cast would be  rejected.
	private void expandSubtree (TreePath selectedPath) {
		
		Enumeration<DefaultMutableTreeNode> children;
		TreePath lSelectedPath;
		
		if (selectedPath == null) {
			lSelectedPath = new TreePath (((DefaultMutableTreeNode) (jtrConfigTree.getModel().getRoot())).getPath()); // If no node was selected a path to root has to be created;
		} else {
			lSelectedPath = selectedPath;
		}
		jtrConfigTree.setSelectionPath(lSelectedPath); // Ensures that the selected path is selectedPath resp. if no path was selected now root gets selected.
		jtrConfigTree.expandPath(lSelectedPath); // Expands the path up to selectedPath.
		children = ((DefaultMutableTreeNode) lSelectedPath.getLastPathComponent()).children();
		while  (children.hasMoreElements()) { // Expands the path of all children of the node at selectedPath.
			expandSubtree (new TreePath (children.nextElement().getPath()));
		}

	}

	
    /**
     * If the child nodes of parentNode are sorted alphabetically, this method delivers the index after parentNode's child with the highest lower label in alphabetical order.
     * If no such child exists the method returns 0.
     */
	@SuppressWarnings("unchecked") // As the usage of an Enumeration would cause a warning. Type cast would be  rejected.
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
	@SuppressWarnings("unchecked") // As the usage of an Enumeration would cause a warning. Type cast would be  rejected.
	private DefaultMutableTreeNode getLabeledChildOfParentNode (String  newNodeLabel, DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode localLabeledChildOfParentNode = null, currentChild;
		Enumeration<DefaultMutableTreeNode> eChildren; // enumeration of children of a node
		
		for (eChildren = parentNode.children(); eChildren.hasMoreElements();) { // Runs through the enumeration of the children of parent node.
			currentChild = eChildren.nextElement(); // Reads the current child and steps over to the next child afterwards implicitly.
			if (newNodeLabel.equals(currentChild.getUserObject())) { 
				localLabeledChildOfParentNode = currentChild; // If the current child is labeled by newNodeLabel it is stored as return value and the loop is ended.
				break;
			}
		}
		return localLabeledChildOfParentNode;
	}

	
    /**
     * This reconstructs the path of a node as a string without root node. The node labels get separated by SEPERATOR_FOR_TREE_LEVELS.
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
    private  void initApplication() {
    	
    	int widthOfNodeNewButtons, widthOfNodeNewLabels, widthOfNodeRenameButtons;

    	if(!this.isModal())
    		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Makes the program terminating after closing the window.

    	// Initializes private variables
    	menuBar = new JMenuBar(); // Creates the menu bar of the frame.
    	menuEdit = new JMenu(LABEL_FOR_MENU_EDIT); // Creates the edit menu.
    	menuFile = new JMenu(LABEL_FOR_MENU_FILE); // Creates the file menu.
    	menuView = new JMenu(LABEL_FOR_MENU_VIEW); // Creates the view menu.
    	menuPopup = new JPopupMenu (); // Creates the popup menu.
    	menuItemExpandSubtree = new JMenuItem(LABEL_FOR_MENUITEM_VIEW_TREE_EXPAND); // Creates the menu item to expand a subtree.
    	menuItemCutSubtree = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_TREE_CUT); // Creates the menu item to cut a subtree.
    	menuItemDeleteSubtree = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_TREE_DELETE); // Creates the menu item to delete a subtree.
    	menuItemEditNodeNew = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_NODE_NEW); // Creates the menu item to create new nodes.
    	menuItemEditPaste = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_PASTE); // Creates the menu item to paste a node or subtree.
    	menuItemEditPasteRenamed = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_PASTE_RENAMED); // Creates the menu item to paste a node or subtree with renamed root.
    	menuItemEditTreeCopy = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_TREE_COPY); // Creates the menu item to copy a subtree.
    	menuItemExpandSubtreePopup = new JMenuItem(LABEL_FOR_MENUITEM_VIEW_TREE_EXPAND); // Creates the popup menu item to expand a subtree.
    	menuItemCutSubtreePopup = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_TREE_CUT); // Creates the popup menu item to cut a subtree.
    	menuItemDeleteSubtreePopup = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_TREE_DELETE); // Creates the popup menu item to delete a subtree.
    	menuItemEditNodeNewPopup = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_NODE_NEW); // Creates the popup menu item to create new nodes.
    	menuItemEditPastePopup = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_PASTE); // Creates the popup menu item to paste a node or subtree.
    	menuItemEditPasteRenamedPopup = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_PASTE_RENAMED); // Creates the popup menu item to paste a node or subtree with renamed root.
    	menuItemEditTreeCopyPopup = new JMenuItem(LABEL_FOR_MENUITEM_EDIT_TREE_COPY); // Creates the popup menu item to copy a subtree.
    	menuItemFileOpen = new JMenuItem(LABEL_FOR_MENUITEM_FILE_OPEN); // Creates the menu item to open files.
    	menuItemFileSave = new JMenuItem(LABEL_FOR_MENUITEM_FILE_SAVE); // Creates the menu item to save files.
    	menuItemFileSaveAs = new JMenuItem(LABEL_FOR_MENUITEM_FILE_SAVE_AS); // Creates the menu item to save files with a to be selected name.
    	jfcFileChooser = new JFileChooser(PATH_FOR_PROPERTY_FILES_DEFAULT); // Creates file chooser for the file treatment dialogs initially pointing at the path PATH_FOR_PROPERTY_FILES_DEFAULT.

    	pnlEditArea = new JPanel(); // Creates the panel that will contain the elements of the edit area (text area or new node editing fields).
    	jtaTextArea1 = new JTextArea(); // Creates the text area that will contain the selected configuration value.
    	jtfTextField1 = new JTextField(); // Creates the text field that will contain the name of a new node.
    	jtfTextField2 = new JTextField(); // Creates the text field that will contain the value of a new node.
    	lblLabel1 = new JLabel(LABEL_FOR_INPUT_FIELD_NAME); // Creates the label to request the name of a new node.
    	lblLabel2 = new JLabel(LABEL_FOR_INPUT_FIELD_VALUE); // Creates the label to request the value of a new node.
    	jtfTextFieldRename = new JTextField(); // Creates the text field that will contain the label of the root of the to be displayed subtree.
    	lblLabelRename = new JLabel(LABEL_FOR_INPUT_FIELD_RENAME); // Creates the label to request the label of the root of the to be inserted subtree.
    	pnlNodeNew = new JPanel(); // Creates the panel that will contain the elements to create a new node.
    	pnlNodeNewEdit = new JPanel(); // Creates the panel that will contain the elements to edit name and value of a new node.
    	pnlNodeNewName = new JPanel(); // Creates the panel that will contain the elements to edit the name of a new node.
    	pnlNodeNewValue = new JPanel(); // Creates the panel that will contain the elements to edit the value of a new node.
    	pnlNodeNewButtons = new JPanel(); // Creates the panel that will contain the buttons to create a new node.
    	btnNodeNewAdd = new JButton(LABEL_FOR_BUTTON_NODE_NEW_ADD); // Creates the button to add a new node.
    	btnNodeNewCancel = new JButton(LABEL_FOR_BUTTON_EDIT_CANCEL); // Creates the button to cancel the adding of a new node.
    	pnlNodeRename = new JPanel(); // Creates the panel that will contain the elements to rename node.
    	pnlNodeRenameEdit = new JPanel(); // Creates the panel that will contain the elements to edit the new label of a node.
    	pnlNodeRenameButtons = new JPanel(); // Creates the panel that will contain the buttons to rename a node.
    	btnNodeRename = new JButton(LABEL_FOR_BUTTON_NODE_RENAME); // Creates the button to rename a node.
    	btnNodeRenameCancel = new JButton(LABEL_FOR_BUTTON_NODE_RENAME_CANCEL); // Creates the button to cancel the renaming of a node.
    	jspScrollPane1 = new JScrollPane(); // Creates the pane that will contain the tree.
        jspScrollPane2 = new JScrollPane(); // Creates the pane that will contain the selected configuration value.
         
    	setTitle(APPLICATION_FRAME_TITLE); // Sets the title of the application's frame.
    	setLocation(APPLICATION_FRAME_POSITION_HORIZONTAL, APPLICATION_FRAME_POSITION_VERTICAL); // Sets the position of the application's frame on the screen.
    	menuFile.add(menuItemFileOpen); // Composes the file menu.
    	menuFile.add(menuItemFileSave); // Composes the file menu.
    	menuFile.add(menuItemFileSaveAs); // Composes the file menu.
    	menuBar.add(menuFile); // Composes the menu bar.
    	menuView.add(menuItemExpandSubtree); // Composes the view menu.
    	menuBar.add(menuView); // Composes the menu bar.
    	menuEdit.add(menuItemEditTreeCopy); // Composes the edit menu.
    	menuEdit.add(menuItemCutSubtree); // Composes the edit menu.
    	menuEdit.add(menuItemDeleteSubtree); // Composes the edit menu.
    	menuEdit.add(menuItemEditNodeNew); // Composes the edit menu.
    	menuEdit.add(menuItemEditPaste); // Composes the edit menu.
    	menuEdit.add(menuItemEditPasteRenamed); // Composes the edit menu.
    	menuBar.add(menuEdit); // Composes the menu bar.
    	setJMenuBar(menuBar); // Sets the menu bar for the frame.
    	
    	menuPopup.add(menuItemEditTreeCopyPopup); // Composes the popup menu.
    	menuPopup.add(menuItemCutSubtreePopup); // Composes the popup menu.
    	menuPopup.add(menuItemDeleteSubtreePopup); // Composes the popup menu.
    	menuPopup.add(menuItemEditNodeNewPopup); // Composes the popup menu.
    	menuPopup.add(menuItemEditPastePopup); // Composes the popup menu.
    	menuPopup.add(menuItemEditPasteRenamedPopup); // Composes the popup menu.
    	menuPopup.addSeparator(); // Composes the popup menu.
    	menuPopup.add(menuItemExpandSubtreePopup); // Composes the popup menu.
    	   	    	
    	
		final clsPropertiesInspector thisForEverySubobject = this; // variable to hand over the frame to the definitions of the menu actions
    	
    	actionOpen = new AbstractAction () { // Implements the action to open property files.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				int returnVal1;

				if (propertiesModified) // If properties have been modified a dialog checks, whether they should be saved before going on.
					if (!continueIfNotSaved ())
						return;
				
				returnVal1 = jfcFileChooser.showOpenDialog(thisForEverySubobject); // The open dialog is called as a child of the main frame. It returns, whether a file has been opened or the dialog has been canceled.

				if (returnVal1 == JFileChooser.APPROVE_OPTION) { // A new file has been selected.
					propertyFilename = jfcFileChooser.getSelectedFile().getPath(); // The applications property file name is set to the name of the newly selected file.
					setBWProperties(); // The properties object gets set a new.
				}
    		}
    	};
    	actionOpen.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_FILE_OPEN); // Equips the file open action with the foreseen name.
    	menuItemFileOpen.setAction(actionOpen); // Assigns action file open to the foreseen menu item.
    	   	
    	actionSave = new AbstractAction () { // Implements the action to save property files.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				
				if (propertiesModified) // The properties get saved only if they have been modified.
					savePropertyfile();
			}
    	};
    	actionSave.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_FILE_SAVE); // Equips the file save file action with the foreseen name.
    	menuItemFileSave.setAction(actionSave); // Assigns action file save to the foreseen menu item.
    	   	
    	actionSaveAs = new AbstractAction () { // Implements the action save as for property files.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				int returnVal1;
				String propertyFilenameBackup;

				returnVal1 = jfcFileChooser.showSaveDialog(thisForEverySubobject); // The dialog is called as a child of the main frame. It returns, whether a file has been opened or the dialog has been canceled.

				if (returnVal1 == JFileChooser.APPROVE_OPTION) { // A new file has been selected.
					propertyFilenameBackup = propertyFilename; // Backs up the name of the property file to enable to set it back if the save attempt fails.
					propertyFilename = jfcFileChooser.getSelectedFile().getPath(); // The applications property file name is set to the name of the newly selected file.
					if (savePropertyfile()) { // True if the save action is okay.
						((DefaultMutableTreeNode) (jtrConfigTree.getModel().getRoot())).setUserObject(propertyFilename); // Sets the root node to the new file name.
						jtrConfigTree.repaint(); // Tree has to be repainted, to show the new file name.
					} else
						propertyFilename = propertyFilenameBackup; // If the save action had failed (was cancelled), propertyFilename has to be set back.
				}
			}
    	};
    	actionSaveAs.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_FILE_SAVE_AS); // Equips the file save as action with the foreseen name.
    	menuItemFileSaveAs.setAction(actionSaveAs); // Assigns action file save as to the foreseen menu item.
    	   	
    	if (propertyFilename.isEmpty()) { // If the property file is not specified, action "save" initially has to be disabled.
    		actionSave.setEnabled(false);
    	}
    	
    	actionExpandSubtree = new AbstractAction () { // Implements the action to expand a subtree recursively.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.

				TreePath currentlySelectedPath;
				
				currentlySelectedPath = jtrConfigTree.getSelectionPath(); // The currently selected path has to be remembered and reselected after the expansion.
				expandSubtree (jtrConfigTree.getSelectionPath());
				jtrConfigTree.setSelectionPath(currentlySelectedPath); // Reselects the path that has been selected before the expansion.
				jtrConfigTree.repaint(); // Makes the expansion visible.
			}
    	};
    	actionExpandSubtree.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_VIEW_TREE_EXPAND); // Equips the collapse subtree action with the foreseen name.
    	menuItemExpandSubtree.setAction(actionExpandSubtree); // Assigns action expand subtree to the foreseen menu item.
    	menuItemExpandSubtreePopup.setAction(actionExpandSubtree); // Assigns action expand subtree to the foreseen popup menu item.
    	   	
    	actionCutSubtree = new AbstractAction () { // Implements the action to cut a subtree.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				copyCutDelete (thisForEverySubobject, true, true);
			}
    	};
    	actionCutSubtree.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_EDIT_TREE_CUT); // Equips the cut subtree action with the foreseen name.
    	menuItemCutSubtree.setAction(actionCutSubtree); // Assigns action cut subtree to the foreseen menu item.
    	menuItemCutSubtreePopup.setAction(actionCutSubtree); // Assigns action cut subtree to the foreseen popup menu item.
    	   	
    	actionDeleteSubtree = new AbstractAction () { // Implements the action to delete a subtree.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				copyCutDelete (thisForEverySubobject, false, true);
			}
    	};
    	actionDeleteSubtree.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_EDIT_TREE_DELETE); // Equips the delete subtree action with the foreseen name.
    	menuItemDeleteSubtree.setAction(actionDeleteSubtree); // Assigns action delete subtree to the foreseen menu item.
    	menuItemDeleteSubtreePopup.setAction(actionDeleteSubtree); // Assigns action delete subtree to the foreseen popup menu item.
    	   	
    	actionNodeNew = new AbstractAction () { // Implements the action to create new nodes.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				
				jspScrollPane2.setVisible(false); // The field to input/output node values is replaced by the fields to create a new node.
				pnlNodeRename.setVisible(false);
				pnlNodeNew.setVisible(true);
				jtfTextField1.grabFocus(); // The focus will be set into the field for the key label.
			}
    	};
    	actionNodeNew.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_EDIT_NODE_NEW); // Equips the node new action with the foreseen name.
    	menuItemEditNodeNew.setAction(actionNodeNew); // Assigns action node new to the foreseen menu item.
    	menuItemEditNodeNewPopup.setAction(actionNodeNew); // Assigns action node new to the foreseen popup menu item.
    	   	
    	actionPaste = new AbstractAction () { // Implements the action to paste nodes.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				
				addSubtree (thisForEverySubobject, true, false);
				jtrConfigTree.grabFocus(); // The focus needs to be set to the tree, so that it gets reconstructed and its epansion and selection situation gets rebuilt. (I don't understand why this is done inplicitely after the other abstract actions.

			}
    	};
    	actionPaste.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_EDIT_PASTE); // Equips the paste action with the foreseen name.
    	menuItemEditPaste.setAction(actionPaste); // Assigns action node new to the foreseen menu item.
    	menuItemEditPastePopup.setAction(actionPaste); // Assigns action node new to the foreseen popup menu item.
    	   	
    	actionPasteRenamed = new AbstractAction () { // Implements the action to paste nodes with renamed root.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				
				jspScrollPane2.setVisible(false); // The field to input/output node values is replaced by the fields to create a new node.
				pnlNodeNew.setVisible(false);
				pnlNodeRename.setVisible(true);
				jtfTextFieldRename.grabFocus(); // The focus will be set into the field for the key label.
			}
    	};
    	actionPasteRenamed.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_EDIT_PASTE_RENAMED); // Equips the paste action with the foreseen name.
    	menuItemEditPasteRenamed.setAction(actionPasteRenamed); // Assigns action node new to the foreseen menu item.
    	menuItemEditPasteRenamedPopup.setAction(actionPasteRenamed); // Assigns action node new to the foreseen popup menu item.
    	   	
    	actionTreeCopy = new AbstractAction () { // Implements the action to copy a subtree.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				copyCutDelete (thisForEverySubobject, true, false);
			}
    	};
    	actionTreeCopy.putValue(AbstractAction.NAME, LABEL_FOR_MENUITEM_EDIT_TREE_COPY); // Equips the tree copy action with the foreseen name.
    	menuItemEditTreeCopy.setAction(actionTreeCopy); // Assigns action tree copy to the foreseen menu item.
    	menuItemEditTreeCopyPopup.setAction(actionTreeCopy); // Assigns action tree copy to the foreseen popup menu item.
   	
    	actionNodeNewAdd = new AbstractAction () { // Implements the action to add a new node.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				addSubtree (thisForEverySubobject, false, false);
			}

    	};
    	actionNodeNewAdd.putValue(AbstractAction.NAME, LABEL_FOR_BUTTON_NODE_NEW_ADD); // Equips the node new action with the foreseen name.

    	actionEditCancel = new AbstractAction () { // Implements the action to cancel any edit action.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				
				pnlNodeNew.setVisible(false);
				pnlNodeRename.setVisible(false);
				jspScrollPane2.setVisible(true);
			}
    	};
    	actionEditCancel.putValue(AbstractAction.NAME, LABEL_FOR_BUTTON_EDIT_CANCEL); // Equips the edit cancel action with the foreseen name.

    	actionSubtreeAddRenamed = new AbstractAction () { // Implements the action to add a renamed subtree.
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) { // To be implemented method of AbstractAction.
				addSubtree (thisForEverySubobject, true, true);
			}

    	};
    	actionSubtreeAddRenamed.putValue(AbstractAction.NAME, LABEL_FOR_BUTTON_NODE_RENAME); // Equips the node new action with the foreseen name.
    	
    	jtaTextArea1.setEditable(false); // Inhibits to edit the initially empty text area.
    	
    	jtaTextArea1.addFocusListener(new FocusListener () { // Creates the methods to detect when the text area gets entered or left.
    		
    		@Override
			public void focusGained(FocusEvent event) {
	        	lastSelectedNode = (DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent(); // Stores the selected node to be able to check its value after it lost the focus.
	        	if (lastSelectedNode != null)
	        		if (lastSelectedNode.isLeaf())
	        			lastPropertyValue = BWProperties.getProperty(getPath (lastSelectedNode)); // Stores the value of the currently selected property to be able to check later whether it has changed. This is only to be done if a leaf node was selected.
	        		else
	        			lastPropertyValue = null;
	        	else
	        		lastPropertyValue = null; // If there is no last selected node, there is also no last property value.
    		}
    		
    		@Override
			public void focusLost(FocusEvent event) {
    			if (lastSelectedNode != null)
	        		if (lastSelectedNode.isLeaf())
	        			propertiesModified = propertiesModified || !BWProperties.getProperty(getPath (lastSelectedNode)).equals(lastPropertyValue); // If not already done it is registered when the recently left property has been modified. This is only to be done if a leaf node was selected.
    			if (propertiesModified && !propertyFilename.isEmpty()) // If the properties have been modified and the property file name is known the save action has to get enabled.
    				actionSave.setEnabled(true);
    		}
    		
    	});
    	
    	jtaTextArea1.getDocument().addDocumentListener(new DocumentListener () { // Creates the methods to detect text changes in the text area and to write back the property value to the clsBWProperties Object.

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
    	
    	jspScrollPane1.setViewportView(jtrConfigTree); // The properties tree is put into scroll pane 1.
    	jspScrollPane2.setViewportView(jtaTextArea1); // The text area to display the selected property value is put into scroll pane 2.
		pnlNodeNew.setVisible(false);
		pnlNodeRename.setVisible(false);
		
		widthOfNodeNewLabels = lblLabel1.getMinimumSize().width > lblLabel2.getMinimumSize().width ? lblLabel1.getMinimumSize().width : lblLabel2.getMinimumSize().width; // Computes the width of the label fields in the node new edit panel from the widest one.
		widthOfNodeNewButtons = btnNodeNewCancel.getMinimumSize().width > btnNodeNewAdd.getMinimumSize().width ? btnNodeNewCancel.getMinimumSize().width : btnNodeNewAdd.getMinimumSize().width; // Computes the width of the buttons from the widest one.
		widthOfNodeRenameButtons = btnNodeRenameCancel.getMinimumSize().width > btnNodeRename.getMinimumSize().width ? btnNodeRenameCancel.getMinimumSize().width : btnNodeRename.getMinimumSize().width; // Computes the width of the buttons from the widest one.

		pnlNodeNewName.setLayout(new GroupLayout(pnlNodeNewName)); // Specifies that pnlNodeNewName consists of the text fields for the name (label and input field) of the new node.
		((GroupLayout) pnlNodeNewName.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeNewName.getLayout()).createSequentialGroup()
					.addGap(INDENT_DEFAULT)
					.addComponent(lblLabel1, widthOfNodeNewLabels, widthOfNodeNewLabels, widthOfNodeNewLabels)
					.addComponent(jtfTextField1, GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS - INDENT_DEFAULT - widthOfNodeNewLabels - widthOfNodeNewButtons, Short.MAX_VALUE)
		);
		((GroupLayout) pnlNodeNewName.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeNewName.getLayout()).createParallelGroup()
					.addComponent(lblLabel1)
					.addComponent(jtfTextField1)
		);
		
		pnlNodeNewValue.setLayout(new GroupLayout(pnlNodeNewValue)); // Specifies that pnlNodeNewValue consists of the text fields for the value (label and input field) of the new node.
		((GroupLayout) pnlNodeNewValue.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeNewValue.getLayout()).createSequentialGroup()
					.addGap(INDENT_DEFAULT)
					.addComponent(lblLabel2, widthOfNodeNewLabels, widthOfNodeNewLabels, widthOfNodeNewLabels)
					.addComponent(jtfTextField2, GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS - INDENT_DEFAULT - widthOfNodeNewLabels - widthOfNodeNewButtons, Short.MAX_VALUE)
		);
		((GroupLayout) pnlNodeNewValue.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeNewValue.getLayout()).createParallelGroup()
					.addComponent(lblLabel2)
					.addComponent(jtfTextField2)
		);
		
		pnlNodeNewEdit.setLayout(new GroupLayout(pnlNodeNewEdit)); // Specifies that pnlNodeNewEdit consists of the panels for the name and the value of the new node.
		((GroupLayout) pnlNodeNewEdit.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeNewEdit.getLayout()).createParallelGroup()
					.addComponent(pnlNodeNewName)
					.addComponent(pnlNodeNewValue)
		);
		((GroupLayout) pnlNodeNewEdit.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeNewEdit.getLayout()).createSequentialGroup()
					.addComponent(pnlNodeNewName)
					.addComponent(pnlNodeNewValue)
		);
		
		pnlNodeNewButtons.setLayout(new GroupLayout(pnlNodeNewButtons)); // Specifies that pnlNodeNewButtons consists of the buttons add and cancel.
		((GroupLayout) pnlNodeNewButtons.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeNewButtons.getLayout()).createParallelGroup()
					.addComponent(btnNodeNewAdd, widthOfNodeNewButtons, widthOfNodeNewButtons, widthOfNodeNewButtons)
					.addComponent(btnNodeNewCancel, widthOfNodeNewButtons, widthOfNodeNewButtons, widthOfNodeNewButtons)
		);
		((GroupLayout) pnlNodeNewButtons.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeNewButtons.getLayout()).createSequentialGroup()
					.addComponent(btnNodeNewAdd)
					.addComponent(btnNodeNewCancel)
		);
		btnNodeNewAdd.setAction(actionNodeNewAdd); // Assigns action node new add to the add button.
		btnNodeNewCancel.setAction(actionEditCancel); // Assigns action edit cancel to the cancel button.
		
		pnlNodeNew.setLayout(new GroupLayout(pnlNodeNew)); // Specifies that pnlNodeNew consists of the edit panel and the buttons panel side by side.
		((GroupLayout) pnlNodeNew.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeNew.getLayout()).createSequentialGroup()
					.addComponent(pnlNodeNewEdit)
					.addComponent(pnlNodeNewButtons)
		);
		((GroupLayout) pnlNodeNew.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeNew.getLayout()).createParallelGroup()
					.addComponent(pnlNodeNewEdit)
					.addComponent(pnlNodeNewButtons)
		);
		
		pnlNodeRenameButtons.setLayout(new GroupLayout(pnlNodeRenameButtons)); // Specifies that pnlNodeNewButtons consists of the buttons add and cancel.
		((GroupLayout) pnlNodeRenameButtons.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeRenameButtons.getLayout()).createParallelGroup()
					.addComponent(btnNodeRename, widthOfNodeRenameButtons, widthOfNodeRenameButtons, widthOfNodeRenameButtons)
					.addComponent(btnNodeRenameCancel, widthOfNodeRenameButtons, widthOfNodeRenameButtons, widthOfNodeRenameButtons)
		);
		((GroupLayout) pnlNodeRenameButtons.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeRenameButtons.getLayout()).createSequentialGroup()
					.addComponent(btnNodeRename)
					.addComponent(btnNodeRenameCancel)
		);
		btnNodeRename.setAction(actionSubtreeAddRenamed); // Assigns action node new add to the add button.
		btnNodeRenameCancel.setAction(actionEditCancel); // Assigns action edit cancel to the add button.

		pnlNodeRenameEdit.setLayout(new GroupLayout(pnlNodeRenameEdit)); // Specifies that pnlNodeRenameEdit consists of the label and the text field for the new label.
		((GroupLayout) pnlNodeRenameEdit.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeRenameEdit.getLayout()).createParallelGroup()
					.addComponent(lblLabelRename, GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS - INDENT_DEFAULT - widthOfNodeRenameButtons, Short.MAX_VALUE)
					.addComponent(jtfTextFieldRename, GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS - INDENT_DEFAULT - widthOfNodeRenameButtons, Short.MAX_VALUE)
		);
		((GroupLayout) pnlNodeRenameEdit.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeRenameEdit.getLayout()).createSequentialGroup()
					.addComponent(lblLabelRename, HEIGHT_FIXED_FOR_TEXT_AREA1 / 2, HEIGHT_FIXED_FOR_TEXT_AREA1 / 2, HEIGHT_FIXED_FOR_TEXT_AREA1 / 2)
					.addComponent(jtfTextFieldRename, HEIGHT_FIXED_FOR_TEXT_AREA1 / 2, HEIGHT_FIXED_FOR_TEXT_AREA1 / 2, HEIGHT_FIXED_FOR_TEXT_AREA1 / 2)
		);
		
		pnlNodeRename.setLayout(new GroupLayout(pnlNodeRename)); // Specifies that pnlNodeRename consists of the edit panel and the buttons panel side by side.
		((GroupLayout) pnlNodeRename.getLayout()).setHorizontalGroup(
				((GroupLayout) pnlNodeRename.getLayout()).createSequentialGroup()
					.addComponent(pnlNodeRenameEdit)
					.addComponent(pnlNodeRenameButtons)
		);
		((GroupLayout) pnlNodeRename.getLayout()).setVerticalGroup(
				((GroupLayout) pnlNodeRename.getLayout()).createParallelGroup()
					.addComponent(pnlNodeRenameEdit)
					.addComponent(pnlNodeRenameButtons)
		);
		
		pnlEditArea.setLayout(new GroupLayout(pnlEditArea)); // Specifies that pnlEditArea consists of two alternatives - text area 1 in scroll pane 2 or pnlNodeNew - at the same place. Visibility is toggled according to edit actions.
		((GroupLayout) pnlEditArea.getLayout()).setHorizontalGroup(
			((GroupLayout) pnlEditArea.getLayout()).createParallelGroup()
				.addComponent(jspScrollPane2)
				.addComponent(pnlNodeNew)
				.addComponent(pnlNodeRename)
		);
		((GroupLayout) pnlEditArea.getLayout()).setVerticalGroup(
			((GroupLayout) pnlEditArea.getLayout()).createParallelGroup()
				.addComponent(jspScrollPane2)
				.addComponent(pnlNodeNew)
				.addComponent(pnlNodeRename)
		);
		
        getContentPane().setLayout(new GroupLayout(getContentPane())); // Specifies, that the layout used to build the application window is a GroupLayout.
        ((GroupLayout) getContentPane().getLayout()).setHorizontalGroup( // Specifies the positions of the components of the window.
        		((GroupLayout) getContentPane().getLayout()).createParallelGroup() // The tree and the text area (respectively the scroll panes containing them) should be at the same horizontal position.
        		.addComponent(jspScrollPane1, GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS, Short.MAX_VALUE)
        		.addComponent(pnlEditArea, GroupLayout.DEFAULT_SIZE, WIDTH_PREFERRED_FOR_DISPLAYED_OBJECTS, Short.MAX_VALUE)
        );
        ((GroupLayout) getContentPane().getLayout()).setVerticalGroup( // The text area should be below the tree.
        		((GroupLayout) getContentPane().getLayout()).createSequentialGroup()
        		.addComponent(jspScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, HEIGHT_PREFERRED_FOR_TREE_PANE, Short.MAX_VALUE)
        		.addComponent(pnlEditArea, HEIGHT_FIXED_FOR_TEXT_AREA1, HEIGHT_FIXED_FOR_TEXT_AREA1, HEIGHT_FIXED_FOR_TEXT_AREA1)
        );

    	pack(); // Reshapes the frames pane.

    	setBWProperties(); // Sets the BWProperties object and performs everything that has to be done, when a properties object is set a new.
    	this.setVisible(true); // Sets the frame of the program visible.
    }

	
	/**
     * This method collects the activities to rebuild the tree after a modificication and to save the current expansion and selection situation.
     */
	private void rebuildTree (DefaultMutableTreeNode toBeSelectedNode) {
		
		bufferCurrentSelection = new TreePath (toBeSelectedNode.getPath()); // The parent of the new node(s) is stored as the current selection.
		storeCurrentExpansion (); // The current expansion situation is stored.
		setPropertiesTree (BWProperties); // Creates the tree from the received clsBWProperties object and sets private field foreseen for the tree.
		jspScrollPane1.setViewportView(jtrConfigTree); // Makes the tree the object to be displayed in the foreseen view.
		isPropertiesTreeToBeRestored = true; // Information that the tree  apansion has to be restored when the tree gets the focus next time.
		
	}

	
	/**
     * This method restores the recent expansion situation of the properties tree which is stored in bufferCurrentExpansion.
     */
	private void restoreExpansion () {
		boolean mindNode;
		DefaultMutableTreeNode currentChild;
		Enumeration <?> children;
		int l1;
		Object [] currentPathObjects;
		TreePath toBeSelectedPath = null;
		
		if (bufferCurrentExpansion != null) {
			l1 = 0; // The variable counts the steps done in the buffer of the current selection.
			while (bufferCurrentExpansion.hasMoreElements()) { // The expansions stored in the buffer have to be mapped to expansions in the newly generated tree. All elements in the buffer get treated.
				jtrConfigTree.setSelectionPath(new TreePath (jtrConfigTree.getModel().getRoot())); // The root node of the new tree has to be selected anyway.
				currentPathObjects = bufferCurrentExpansion.nextElement().getPath(); // The element of the buffer is a path, it gets transferred to an array of objects.
				mindNode = false; // If the node is part of the path stored in bufferCurrentSelection it has to be minded as it might have to be selected finally at the end of the restoration.
				for (Object currentPathObject : currentPathObjects) { // All nodes of the currently treated path stored in the buffer have to be treated one after the other.
					if (currentPathObject == bufferCurrentSelection.getPathComponent(l1)) { // If the node in the expansion buffer is also a node in the path of the buffered selection this has to be minded.
						mindNode = true;
						if (l1 < bufferCurrentSelection.getPathCount() - 1) { // The next level of the buffered selection path has to be checked if it exists.
							l1++;
						}
					}
					if (currentPathObject != currentPathObjects[0]) { // If the node taken currently is the root the steps must not be executed as the root is already selected.
						children = ((DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent()).children();
						while (children.hasMoreElements()) {  // The child in the new tree which matches the currend step of the selected branch of the buffer has to be found and selected.
							currentChild = (DefaultMutableTreeNode) children.nextElement();
							if (currentPathObject.toString().compareTo(currentChild.toString()) == 0) { // If the correct node in the new tree is found it has to be selected. The correct node is the one that matches with the relevant node in the buffered path.
								jtrConfigTree.setSelectionPath(jtrConfigTree.getSelectionPath().pathByAddingChild(currentChild));
							}
						}
					}
					jtrConfigTree.expandPath(jtrConfigTree.getSelectionPath()); // The now selected path has to be expanded.
					if (mindNode) { // The now selected path also is the path in the bufferCurrentSelection.
						toBeSelectedPath = jtrConfigTree.getSelectionPath(); // The path (it is mapping the bufferCurrentSelection) has to be saved. It has to be selected and expanded at the end oft the restoration.
					}
				}
			}			
			jtrConfigTree.setSelectionPath(toBeSelectedPath); // The path (it is mapping the bufferCurrentSelection) was saved. It now has to be selected and expanded at the end oft the restoration. 
			jtrConfigTree.expandPath(jtrConfigTree.getSelectionPath()); // Expands the selected subtree.
		} else {
			jtrConfigTree.setSelectionPath(new TreePath (jtrConfigTree.getModel().getRoot())); // If the expansion buffer is empty the root node of the new tree gets selected.
		}
		
		jtrConfigTree.expandPath(jtrConfigTree.getSelectionPath()); // Expands the selected subtree.

	}

	
    /**
     * This method performs the save activities for the property file. It is called by the actions file save and file save as.
     * It returns true, if the saving was successful, false otherwise.
     */
	private boolean savePropertyfile() {
		boolean saveOk = false;
		int iOfBackslash, returnVal1;

		if (!propertyFilename.isEmpty()) { // If the property object has been received from a superior application (property file name is empty).
			if ((new File(propertyFilename)).exists()) // checks whether the file with the specified name already exists.
				returnVal1 = JOptionPane.showOptionDialog(this, // A dialog asks, whether the existing file really should be overwritten.
					propertyFilename + QUESTION_CONFIRMATION_DIALOG_FILE_SAVE, // The question, whether the file should be overwritten is composed from the file name and a text block.
					LABEL_FOR_CONFIRMATION_DIALOG, // Label of the dialog window
					JOptionPane.YES_NO_OPTION, // Dialog type "Option" is indicated.
					JOptionPane.QUESTION_MESSAGE, // Message type "Question" is indicated.
					null, // No specific icon is set.
					OPTIONS_YES_NO, // Two options are available for choose.
					OPTIONS_YES_NO[1]); // Option "No" is preselected.
			else
				returnVal1 = 0; // If the file does not exist there was no confirmation dialog performed but the further behavior is the same as if there had been a dialog with positive answer.
			if (returnVal1 == 0) { // If the save action has been acknowledged by the user it gets performed.
				iOfBackslash = propertyFilename.lastIndexOf(SEPARATOR_FOR_PATH); // The properties get saved in the currently selected properties file.
				clsProperties.writeProperties(BWProperties, propertyFilename.substring (0, iOfBackslash), propertyFilename.substring (iOfBackslash + 1), "Written by Properties Inspector"); // Writes the property object to the property file with the write method of the class clsBWProperties.
				saveOk = true; // Sets the return value;
				propertiesModified = false; // After the save the properties are not modified compared to the saved state.
				actionSave.setEnabled(false); // The file save function only gets enabled again when values get changed.
			} 
		} else // The property file name is not specified therefore the file save as action gets executed.
			actionSaveAs.actionPerformed(null);
		return saveOk;
	}

	
    /**
     * This method sets the BWProteries field with the contents of a fully qualified properties file,
     * and performs everything which has to be done when the properties are set a new.
     */
	private void setBWProperties () {
		int iOfBackslash;
	
    	if (!propertyFilename.isEmpty() || (BWProperties != null)) { // The function can only be executed if either a properties file or a properties object is specified.
    		jtaTextArea1.setText(""); // Cleans the text area. Has to be done before the new BWproperties are loaded, as otherwise the last edited property remains connected with the text area which may lead to undesired run time effects.
    		jtaTextArea1.setEditable(false); // Inhibits to edit the initially empty text area.
    		if (!propertyFilename.isEmpty()) { // If the property file name is specified, the properties object has to be set with its contents. 
    			iOfBackslash = propertyFilename.lastIndexOf(SEPARATOR_FOR_PATH);
    			BWProperties = clsProperties.readProperties(propertyFilename.substring (0, iOfBackslash), propertyFilename.substring (iOfBackslash + 1)); // Reads the given properties file and stores it in the BWProperties object.
    		}
    		setPropertiesTree (BWProperties); // Creates the tree from the received clsBWProperties object and sets private field foreseen for the tree.
    		jspScrollPane1.setViewportView(jtrConfigTree); // Makes the tree the object to be displayed in the foreseen view.
    		propertiesModified = false;
    		actionSave.setEnabled(false);
    	}
	}

    
    /**
     * This method creates a JTree object from a clsProperties object.
     */
	private void setPropertiesTree (clsProperties anyProperties) {
		// Definition of local variables.
		DefaultMutableTreeNode branch = null, currentNode, nextNode, top;
		String firstPartOfLabel, restOfLabel;
		String[] propertyLabels;
		int iOfDot, nPropertyelements;
		
		nPropertyelements = anyProperties.size(); // The number of properties is detected as the size of the object anyProperties.
		propertyLabels = anyProperties.stringPropertyNames().toArray(new String[0]); // The keys of the properties are put into the String array propertyLabels.

		top = new DefaultMutableTreeNode(propertyFilename.isEmpty()? LABEL_FOR_ROOTNODE_DEFAULT: propertyFilename); // A new root node is created from the name of the properties file. If there is no file, the root node is a constant.
    	jtrConfigTree = new JTree(top); // top is hooked in as the root of the local tree.
        
    	for (int l1 = 0; l1 < nPropertyelements; l1++) { // Every property from the object anyProperties is added to the tree.
    		restOfLabel = propertyLabels[l1]; // The label (the key) of the current property is read into restOfLabel.
    		currentNode = top; // The current position is set to top level of the tree.
    		while (true){ // As long as there are dots in the current property label, it consists of more then one tree nodes.
    			iOfDot = restOfLabel.indexOf(SEPERATOR_FOR_TREE_LEVELS); // If there is a SEPERATOR_FOR_TREE_LEVELS in the label, its position is stored in iOfDot. Otherwise iOfDot is -1.
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
	
    	jtrConfigTree.addTreeSelectionListener(new TreeSelectionListener() { // For the new tree a method is created, to react on the selection of nodes.
    	    @Override
			public void valueChanged(TreeSelectionEvent anyEvent) {
    	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent(); // The variable node will hold the last selected node.
    	    	
    	        if (node == null) { // Nothing is selected.
    	        	return;
    	        }

    	    	if (node.isLeaf())
    	        	displayPropertyvalue (getPath (node)); // If the selected node is a leaf, the corresponding property value has to be displayed in the form. The complete path of the node has to be handed over to the method displayPropertyvalue.
    	        else
    	        	displayPropertyvalue (""); // If the selected node is not a leaf an eventually previous display has to be cleaned. This has to be indicated to displayPropertyvalue by handing over an empty string.
    	    }
    	});
    	
    	jtrConfigTree.addFocusListener(new FocusListener () { // Needed to reconstruct the expansion situation after the addition or removal of nodes, as the tree gets rebuilt from scratch when the properties object has been modified.

			@Override
			public void focusGained(FocusEvent arg0) {

				if (isPropertiesTreeToBeRestored) {
					restoreExpansion();
				}
				isPropertiesTreeToBeRestored = false;
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
			}
    		
    	});
    	
    	jtrConfigTree.addMouseListener(new MouseListener () { // To show the popup menu after pressing of the right mouse button.

			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				
				switch (mouseEvent.getModifiers()) {
				case InputEvent.BUTTON3_MASK:
					menuPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
					break;
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO (hias) - Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO (hias) - Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO (hias) - Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO (hias) - Auto-generated method stub
				
			}
    		
    	});
    	
	}

	
	/**
     * This method stores the current expansion situation of the properties tree in bufferCurrentExpansion.
     */
	private void storeCurrentExpansion () {
		
		bufferCurrentExpansion = jtrConfigTree.getExpandedDescendants(new TreePath (jtrConfigTree.getModel().getRoot())); // Stores the expanded descendents of root.
		bufferCurrentSelection = jtrConfigTree.getSelectionPath(); // Stores the path of the currently selected node.
		
	}

	
    /**
     * This method writes the modified value back to the selected node in the clsPropertiesObject.
     */
	private void writebackPropertyvalue (String propertyvalue) {
		DefaultMutableTreeNode node;

		node = (DefaultMutableTreeNode) jtrConfigTree.getLastSelectedPathComponent();

		if (node.isLeaf()) {
			BWProperties.setProperty(getPath (node), propertyvalue); // The given value is set for the property with the path of the recently selected node as key.
		}
	}
}
