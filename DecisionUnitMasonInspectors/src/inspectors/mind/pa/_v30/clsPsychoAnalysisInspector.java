/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 12.08.2009, 22:50:56
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;
import pa._v30.clsProcessor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

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
import pa.modules._v30.clsModuleBase;
import pa.modules._v30.clsPsychicApparatus;

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
		clsPsychicApparatus oPsyApp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
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
	private void getTree(clsPsychicApparatus poPA,
			DefaultMutableTreeNode poParentTreeNode) {
		
		DefaultMutableTreeNode group = new DefaultMutableTreeNode("Function Modules");
		poParentTreeNode.add(group);
		
		ArrayList<String> oChilds = new ArrayList<String>();
		
        for ( Map.Entry<Integer, clsModuleBase> module : poPA.moModules.entrySet() )	{
        	String oName = module.getValue().getClass().getSimpleName();
        	oChilds.add(oName);
        }
        Collections.sort(oChilds);
        for (String oName:oChilds) {
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(oName);
        	group.add(child);
        }
        
	}

	private void addKnowledge(clsPsychicApparatus poPAModule,
			DefaultMutableTreeNode poParentTreeNode) {
		
		DefaultMutableTreeNode oMemoryNode = new DefaultMutableTreeNode("Knowledge Base");
		
		clsMemory oMemory = poPAModule.moMemory;
		
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
			moContent = clsInspectorPATabFactory.createInspectorModules( ((clsProcessor)moPA.getProcessor()).getPsychicApparatus(), nodeInfo.toString(), moModuleTree);
			moContentPane.add(moContent);
			moContentPane.setViewportView(moContent);
			moContentPane.repaint();
		//}
	}
}
