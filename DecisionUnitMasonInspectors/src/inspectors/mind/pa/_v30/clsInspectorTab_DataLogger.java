/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 20.07.2010, 14:50:56
 */
package inspectors.mind.pa._v30;

import inspectors.mind.pa._v30.autocreated.clsDL_CSVGenericInspector;
import inspectors.mind.pa._v30.autocreated.cls_GenericActivityTimeChartInspector;
import inspectors.mind.pa._v30.autocreated.cls_GenericDynamicTimeChartInspector;
import inspectors.mind.pa._v30.autocreated.cls_GenericTimeChartInspector;
import inspectors.mind.pa._v30.handcrafted.clsCSV_DataLoggerInspector;
import inspectors.mind.pa._v30.handcrafted.clsDetail_DataLoggerInspector;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import pa._v30.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.logger.clsDLEntry_Abstract;
import pa._v30.logger.clsDataLogger;
import pa._v30.modules.clsPsychicApparatus;
import pa.clsPsychoAnalysis;
import pa._v30.clsProcessor;
import sim.portrayal.Inspector;
import sim.portrayal.inspector.TabbedInspector;

/**
 * (muchitsch) - Inspector instertion point to show the actual state of the semantic memory 
 * 
 * @author muchitsch
 * 03.08.2010, 13:58:44
 * 
 */
public class clsInspectorTab_DataLogger extends Inspector implements TreeSelectionListener {

	/**
	 * (muchitsch) - a inspector for the memory. it displays the memory information on a tab in the inspectors.
	 * different filters etc to come
	 * 
	 * @author muchitsch
	 * 03.08.2010, 13:58:44
	 */
	private static final long serialVersionUID = 2184733182638314059L;

	private clsPsychoAnalysis moPA;
	JTree moModuleTree;
	/**
	 * right panel. shows the inspector selected in the tree of the left panel
	 */
	JScrollPane moContentPane;
	TabbedInspector moContent = new TabbedInspector();
	JSplitPane moSplitPane;
	
    public clsInspectorTab_DataLogger(clsPsychoAnalysis poPA)
    {
		moPA= poPA;
		
		Box oBox1 = new Box(BoxLayout.PAGE_AXIS);
		
		clsPsychicApparatus oPsyApp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
		
		//set root tree manually
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Data Logger");
		addLoggerToTree(oPsyApp.moDataLogger, root);
		root.add(new DefaultMutableTreeNode("All"));

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
    
	private void addLoggerToTree(clsDataLogger poDL,
			DefaultMutableTreeNode poParentTreeNode) {
		ArrayList<String> oChilds = new ArrayList<String>();
		DefaultMutableTreeNode oParent = new DefaultMutableTreeNode("Single");
		poParentTreeNode.add(oParent);
		
        for (clsDLEntry_Abstract oDS:poDL.moDataStorage)	{
        	String oName = oDS.getName();
        	oChilds.add(oName);
        }
        Collections.sort(oChilds);
        for (String oName:oChilds) {
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(oName);
        	oParent.add(child);
        }
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
		moContent = createInspectorDataLogger( ((clsProcessor)moPA.getProcessor()).getPsychicApparatus(), nodeInfo.toString(), moModuleTree);
		moContent.setPreferredSize( new Dimension(300,300) );
		
		moContentPane.add(moContent);
		moContentPane.setViewportView(moContent);
		moContentPane.repaint();
		
	}

	private TabbedInspector createInspectorDataLogger(clsPsychicApparatus moPA, String poModuleName, JTree poTree) {

		TabbedInspector oRetVal = new TabbedInspector();

		//special memory tree...
		if (poModuleName.equals("All")) {
			oRetVal.addInspector(new clsDetail_DataLoggerInspector(moPA.moDataLogger), "Description");
			oRetVal.addInspector(new clsCSV_DataLoggerInspector(moPA.moDataLogger), "CSV");
//			oRetVal.addInspector(new clsHTML_DataLoggerInspector(moPA.moDataLogger), "HTML");
		} else {
			clsDLEntry_Abstract oDL = moPA.moDataLogger.getDL(poModuleName);
			if (oDL != null) {
				if (oDL instanceof itfInspectorGenericActivityTimeChart) {
					oRetVal.addInspector(new cls_GenericActivityTimeChartInspector((itfInspectorGenericActivityTimeChart)oDL, 800, 800, 400), "Chart");
				} else if (oDL instanceof itfInspectorGenericDynamicTimeChart) {
					oRetVal.addInspector(new cls_GenericDynamicTimeChartInspector((itfInspectorGenericDynamicTimeChart)oDL, 800, 800, 400), "Chart");					
				} else if (oDL instanceof itfInspectorGenericTimeChart) {
					oRetVal.addInspector(new cls_GenericTimeChartInspector((itfInspectorGenericTimeChart)oDL, 800, 800, 400), "Chart");
				}

				oRetVal.addInspector(new clsDL_CSVGenericInspector(oDL), "CSV");
//				oRetVal.addInspector(new clsDL_HTMLGenericInspector(oDL), "HTML");
			}
		} 
		
		return oRetVal;
	}	
}
