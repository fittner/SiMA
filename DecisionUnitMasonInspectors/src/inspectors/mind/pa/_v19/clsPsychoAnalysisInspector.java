/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 12.08.2009, 22:50:56
 */
package inspectors.mind.pa._v19;

import inspectors.clsInspectorUtils;

import java.awt.BorderLayout;
import pa._v19.clsProcessor;
import java.lang.reflect.Field;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import pa.clsPsychoAnalysis;
import pa.memory.clsMemory;
import pa.modules._v19.G00_PsychicApparatus;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 12.08.2009, 22:50:56
 * 
 */
public class clsPsychoAnalysisInspector extends Inspector implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsPsychoAnalysis moPA;
	JTree moModuleTree;
	JScrollPane moContentPane;
	TabbedInspector moContent = new TabbedInspector();
	JSplitPane moSplitPane;

	private LocationWrapper moWrapper;
	private GUIState moGuiState;
	
    public clsPsychoAnalysisInspector(Inspector originalInspector,
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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Psychic Apparatus");
		//grab the top element of the top-down design 
		G00_PsychicApparatus oPsyApp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
		//build a tree with all members that start either with moC for clsModuleContainer or moE for clsModuleBase
		getTree( oPsyApp, root );
		addKnowledge(oPsyApp, root);

		moModuleTree = new JTree(root);
		moModuleTree.addTreeSelectionListener(this);
		JScrollPane oTreeScroll = new JScrollPane(moModuleTree);
		moContentPane = new JScrollPane(moContent);
		
		
		moSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				oTreeScroll, moContentPane);
		moSplitPane.setResizeWeight(0.5);
		moSplitPane.setOneTouchExpandable(true);
		moSplitPane.setContinuousLayout(true);
		moSplitPane.setDividerLocation(200);
		
		oBox1.add(moSplitPane);
		
        setLayout(new BorderLayout());
        add(oBox1, BorderLayout.CENTER);
    }
    
	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 13.08.2009, 00:52:09
	 *
	 * @param psyApp
	 * @param poParentTreeNode
	 */
	private void getTree(Object poPAModule,
			DefaultMutableTreeNode poParentTreeNode) {
		
		Field[] oFields = poPAModule.getClass().getDeclaredFields(); //get members of class
		for(Field oField : oFields) { //for each member
			if(oField.getType().getSuperclass().getName().equals("pa.modules.clsModuleContainer")) { //case clsModuleContainer (C00-C16)
				//create a new tree-element with the name of the public member variable without mo-prefix 
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(oField.getName().substring(2));  
				
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
				poParentTreeNode.add(child);
			}
			else if(oField.getType().getSuperclass().getName().equals("pa.modules.clsModuleBase")) { //case clsMuduleBase (E01-E32)
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(oField.getName().substring(2));
				poParentTreeNode.add(child);
			}
		}
	}

	private void addKnowledge(G00_PsychicApparatus poPAModule,
			DefaultMutableTreeNode poParentTreeNode) {
		
		DefaultMutableTreeNode oMemoryNode = new DefaultMutableTreeNode("Knowledge Base");
		
		clsMemory oMemory = poPAModule.getMemoryForInspector();
		
		Field[] oFields = oMemory.getClass().getFields();
		for(Field oField : oFields) {
			
			if(oField.getName().startsWith("mo")) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(oField.getName().substring(2));
				oMemoryNode.add(child);
			}
		}
		poParentTreeNode.add(oMemoryNode);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
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
	 * @author langr
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
		//if (node.isLeaf()) {
			moContentPane.remove(moContent);
			moContent = clsInspectorMappingPA.getPAInspector( moOriginalInspector, moWrapper, moGuiState, 
											((clsProcessor)moPA.getProcessor()).getPsychicApparatus(), nodeInfo.toString(), moModuleTree);
			moContentPane.add(moContent);
			moContentPane.setViewportView(moContent);
			moContentPane.repaint();
		//}
	}
}
