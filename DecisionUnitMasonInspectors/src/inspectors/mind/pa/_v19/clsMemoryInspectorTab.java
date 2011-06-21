/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 20.07.2010, 14:50:56
 */
package inspectors.mind.pa._v19;

import inspectors.clsInspectorUtils;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


import pa.clsPsychoAnalysis;

import pa._v19.clsProcessor;
import pa._v19.modules.G00_PsychicApparatus;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * (muchitsch) - Inspector instertion point to show the actual state of the semantic memory 
 * 
 * @author muchitsch
 * 03.08.2010, 13:58:44
 * 
 */
@Deprecated
public class clsMemoryInspectorTab extends Inspector implements TreeSelectionListener {

	/**
	 * (muchitsch) - a inspector for the memory. it displays the memory information on a tab in the inspectors.
	 * different filters etc to come
	 * 
	 * @author muchitsch
	 * 03.08.2010, 13:58:44
	 */
	private static final long serialVersionUID = 2184733182638314059L;
	public Inspector moOriginalInspector;
	private clsPsychoAnalysis moPA;
	JTree moModuleTree;
	/**
	 * right panel. shows the inspector selected in the tree of the left panel
	 */
	JScrollPane moContentPane;
	TabbedInspector moContent = new TabbedInspector();
	JSplitPane moSplitPane;

	private LocationWrapper moWrapper;
	private GUIState moGuiState;
	
    public clsMemoryInspectorTab(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsPsychoAnalysis poPA)
    {
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moGuiState = guiState;
		moPA= poPA;
		
		Box oBox1 = new Box(BoxLayout.PAGE_AXIS);
		
		//set root tree manually
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Memory");
		
		DefaultMutableTreeNode oRootChild = new DefaultMutableTreeNode("Modules");
		root.add(oRootChild);
		
		//grab the top element of the top-down design 
		G00_PsychicApparatus oPsyApp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
		//build a tree with all members that start either with moC for clsModuleContainer or moE for clsModuleBase
		getTree( oPsyApp, oRootChild );
		
		//create tree for all memory types
		addAllMemoryTree(oPsyApp, root);

		moModuleTree = new JTree(root);
		moModuleTree.addTreeSelectionListener(this);
		JScrollPane oTreeScroll = new JScrollPane(moModuleTree);
		moContentPane = new JScrollPane(moContent);
		
		
		moSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				oTreeScroll, moContentPane);
		moSplitPane.setResizeWeight(0);
		moSplitPane.setOneTouchExpandable(true);
		moSplitPane.setContinuousLayout(true);
		moSplitPane.setDividerLocation(150);
		
		oBox1.add(moSplitPane);
		
        setLayout(new BorderLayout());
        add(oBox1, BorderLayout.CENTER);
    }
    
	/**
	 * (muchitsch) - creates a tree for all modules and displays the memory within this modules. some my not have a connection to memory
	 *
	 * @author muchitsch
	 * 13.08.2010, 00:52:09
	 *
	 * @param psyApp
	 * @param poParentTreeNode
	 */
	private void getTree(Object poPAModule,
			DefaultMutableTreeNode poParentTreeNode) {

		

		Field[] oFields = poPAModule.getClass().getDeclaredFields(); //get members of class
		for(Field oField : oFields) { //for each member
			if(oField.getType().getSuperclass().getName().equals("pa.modules.clsModuleContainer")) { //case clsModuleContainer (C00-C16)
				//create a new tree-element with the name of the public member variable without mo-prefix + MEM, to avoid interference with rools tree

				DefaultMutableTreeNode child = null; 
				
				//ich will nur die erste Ebene beim Memory! meine Implementierung
				// Liste Memory im Tree anders aufbauen & sortieren
				
				if( 	(oField.getName().substring(2).startsWith("G01")) || 
						(oField.getName().substring(2).startsWith("G02")) || 
						(oField.getName().substring(2).startsWith("G03")) ||
						(oField.getName().substring(2).startsWith("G04")) )
				{
					
					child = new DefaultMutableTreeNode(oField.getName().substring(2) + "MEM");
				}
				else
				{
					child = poParentTreeNode;
				}
				  
				
				//get the content of the member (=the instance of the container module) and get the tree entries for it 
				Object o = null;
				try {
				o = oField.get( poPAModule );
				getTree(o, child);
				}
				catch(Exception e){
					
					System.out.println( clsInspectorUtils.getCustomStackTrace(e) );
				}
				
				//add the filled treenode for the current clsModuleContainer
				if(!child.isNodeAncestor(poParentTreeNode))
					poParentTreeNode.add(child);
				
			}
			else if(oField.getType().getSuperclass().getName().equals("pa.modules.clsModuleBase")) { //case clsMuduleBase (E01-E32)
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(oField.getName().substring(2) + "MEM"); //"MEM" is needed to differentiate the tree nodes from RooL's in the inspector mapping
				poParentTreeNode.add(child);
			}
		}
	}

	private void addAllMemoryTree(G00_PsychicApparatus poPAModule,
			DefaultMutableTreeNode poParentTreeNode) {
		
		DefaultMutableTreeNode oMemorRootNode = new DefaultMutableTreeNode("All Memory");
		
		MutableTreeNode oNodeTPM = new DefaultMutableTreeNode("TPM");
	    MutableTreeNode cNodeTP = new DefaultMutableTreeNode("TP");
	    oMemorRootNode.insert(oNodeTPM, 0);
	    oMemorRootNode.insert(cNodeTP, 1);
		
		/*clsMemory oMemory = poPAModule.getMemoryForInspector();
		
		Field[] oFields = oMemory.getClass().getFields();
		for(Field oField : oFields) {
			
			if(oField.getName().startsWith("mo")) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(oField.getName().substring(2));
				oMemoryNode.add(child);
			}
		}*/
		poParentTreeNode.add(oMemorRootNode);
	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 12.08.2009, 22:51:29
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		for(Object oInsp : moContent.inspectors) {
			if( oInsp instanceof Inspector ) {
				((Inspector) oInsp).updateInspector();
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 13.08.2009, 01:25:02
	 * 
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)moModuleTree.getLastSelectedPathComponent();

		if (node == null)
		//Nothing is selected.	
		return;
		
		Object nodeInfo = node.getUserObject();
		
		moContentPane.remove(moContent);
		moContent = clsInspectorMappingPA.getPAInspector( moOriginalInspector, moWrapper, moGuiState, 
										((clsProcessor)moPA.getProcessor()).getPsychicApparatus(), nodeInfo.toString(), moModuleTree);
		moContentPane.add(moContent);
		moContentPane.setViewportView(moContent);
		moContentPane.repaint();
		
	}
}
