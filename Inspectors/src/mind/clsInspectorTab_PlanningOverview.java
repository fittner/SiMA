/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author perner
 * 29.02.2012, 14:20:56
 */
package mind;


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

import mind.autocreated.cls_StateInspector;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import base.modules.clsPsychicApparatus;
import control.clsProcessor;
import control.clsPsychoAnalysis;

/**
 * Inspector to show an overview of the planning-system of the ARSIN
 * 
 * @author perner 29.02.2012, 11:58:44
 * 
 */
public class clsInspectorTab_PlanningOverview extends Inspector implements
		TreeSelectionListener {

	/**
	 * @author perner 03.08.2010, 13:58:44
	 */
	private static final long serialVersionUID = 2184733132138314059L;
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

	public clsInspectorTab_PlanningOverview(Inspector originalInspector,
			LocationWrapper wrapper, GUIState guiState, clsPsychoAnalysis poPA) {
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moGuiState = guiState;
		moPA = poPA;

		Box oBox1 = new Box(BoxLayout.PAGE_AXIS);

		// set root tree manually
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				"Planning Overview");

		root.add(new DefaultMutableTreeNode("Goals"));
		root.add(new DefaultMutableTreeNode("Plans"));

		moModuleTree = new JTree(root);
		moModuleTree.addTreeSelectionListener(this);
		JScrollPane oTreeScroll = new JScrollPane(moModuleTree);
		moContentPane = new JScrollPane(moContent);

		moSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, oTreeScroll,
				moContentPane);

		moSplitPane.setResizeWeight(0);
		moSplitPane.setOneTouchExpandable(true);
		moSplitPane.setContinuousLayout(true);
		moSplitPane.setDividerLocation(150);

		oBox1.add(moSplitPane);

		setLayout(new BorderLayout());
		add(oBox1, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author perner
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		for (Object oInsp : moContent.inspectors) {
			if (oInsp instanceof Inspector) {
				((Inspector) oInsp).updateInspector();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * handles events from the tree-view
	 * 
	 * @author perner
	 * 
	 * @see
	 * javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event
	 * .TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) moModuleTree
				.getLastSelectedPathComponent();

		if (node == null)
			// Nothing is selected.
			return;

		Object nodeInfo = node.getUserObject();

		moContentPane.remove(moContent);
		moContent = createInspectorMemory(moOriginalInspector, moWrapper,
				moGuiState,
				((clsProcessor) moPA.getProcessor()).getPsychicApparatus(),
				nodeInfo.toString(), moModuleTree);
		moContent.setPreferredSize(new Dimension(300, 300));

		moContentPane.add(moContent);
		moContentPane.setViewportView(moContent);
		moContentPane.repaint();

	}

	
	private TabbedInspector createInspectorMemory(Inspector poSuperInspector,
			LocationWrapper poWrapper, GUIState poState,
			clsPsychicApparatus moPA, String poModuleName, JTree poTree) {

		TabbedInspector oRetVal = new TabbedInspector();

		// special memory tree...
		if (poModuleName.equals("Goals")) {
			oRetVal.addInspector(new cls_StateInspector(
					moPA.moF26_DecisionMaking), "Goals");
		} else if (poModuleName.equals("Plans")) {
			oRetVal.addInspector(new cls_StateInspector(
					moPA.moF52_GenerationOfImaginaryActions), "Plans");
		} 

		return oRetVal;
	}
}
