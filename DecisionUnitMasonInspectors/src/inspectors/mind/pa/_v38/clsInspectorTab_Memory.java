/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 20.07.2010, 14:50:56
 */
package inspectors.mind.pa._v38;

import inspectors.mind.pa._v38.autocreated.cls_DescriptionInspector;
import inspectors.mind.pa._v38.autocreated.cls_GenericTimeChartInspector;
import inspectors.mind.pa._v38.autocreated.cls_StateInspector;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import pa.clsPsychoAnalysis;
import pa._v38.clsProcessor;
import pa._v38.modules.clsPsychicApparatus;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * Inspector instertion point to show the actual state of the semantic memory 
 * 
 * @author muchitsch
 * 03.08.2010, 13:58:44
 * 
 */
public class clsInspectorTab_Memory extends Inspector implements TreeSelectionListener {

	/**
	 * A inspector for the memory. it displays the memory information on a tab in the inspectors.
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
	
    public clsInspectorTab_Memory(Inspector originalInspector,
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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Memory 2.0");
						
		root.add(new DefaultMutableTreeNode("KB"));
		root.add(new DefaultMutableTreeNode("TPM"));
		root.add(new DefaultMutableTreeNode("TP"));
		root.add(new DefaultMutableTreeNode("Libido Storage"));
		root.add(new DefaultMutableTreeNode("Blocked Content Storage"));

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
		moContent = createInspectorMemory( moOriginalInspector, moWrapper, moGuiState, 
										((clsProcessor)moPA.getProcessor()).getPsychicApparatus(), nodeInfo.toString(), moModuleTree);
		moContent.setPreferredSize( new Dimension(300,300) );
		
		moContentPane.add(moContent);
		moContentPane.setViewportView(moContent);
		moContentPane.repaint();
		
	}

	private TabbedInspector createInspectorMemory(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, 
				clsPsychicApparatus moPA, String poModuleName, JTree poTree) {

		TabbedInspector oRetVal = new TabbedInspector();

		//special memory tree...
		if (poModuleName.equals("KB")) {
			oRetVal.addInspector(new cls_StateInspector(moPA.moKnowledgeBaseHandler), "State");
		} else if (poModuleName.equals("TPM")) {
			
		} else if (poModuleName.equals("TP")) {
			
		} else if (poModuleName.equals("Libido Storage")) {
			oRetVal.addInspector(new cls_StateInspector(moPA.moLibidoBuffer), "State");
			oRetVal.addInspector(new cls_GenericTimeChartInspector(moPA.moLibidoBuffer), "Time Chart");
			oRetVal.addInspector(new cls_DescriptionInspector(moPA.moLibidoBuffer), "Desc");				
		} else if (poModuleName.equals("Blocked Content Storage")) {
			oRetVal.addInspector(new cls_StateInspector(moPA.moBlockedContentStorage), "State");
			oRetVal.addInspector(new cls_DescriptionInspector(moPA.moBlockedContentStorage), "Desc");
			//TODO (MUCHITSCH): added graphinspector for blocked content storage
		} 
		
		return oRetVal;
	}	
}
