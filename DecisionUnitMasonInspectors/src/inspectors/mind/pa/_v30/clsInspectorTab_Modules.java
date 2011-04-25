/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 12.08.2009, 22:50:56
 */
package inspectors.mind.pa._v30;

import inspectors.mind.pa._v30.autocreated.clsE_SimpleInterfaceDataInspector;
import inspectors.mind.pa._v30.autocreated.clsI_SimpleInterfaceDataInspector;
import inspectors.mind.pa._v30.autocreated.cls_DescriptionInspector;
import inspectors.mind.pa._v30.autocreated.cls_GenericActivityTimeChartInspector;
import inspectors.mind.pa._v30.autocreated.cls_GenericDynamicTimeChartInspector;
import inspectors.mind.pa._v30.autocreated.cls_GenericTimeChartInspector;
import inspectors.mind.pa._v30.autocreated.cls_StateInspector;
import inspectors.mind.pa._v30.functionalmodel.clsPAInspectorFunctional;
import inspectors.mind.pa._v30.graph.clsDriveInspector;
import inspectors.mind.pa._v30.graph.clsMeshInterface;
import inspectors.mind.pa._v30.handcrafted.clsE26DecisionCalculation;
import inspectors.mind.pa._v30.handcrafted.clsI_AllInterfaceData;
import java.awt.BorderLayout;
import java.awt.Dimension;
import pa._v30.clsProcessor;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorDrives;
import pa._v30.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.interfaces.itfInspectorInternalState;
import pa._v30.interfaces.itfInterfaceDescription;
import pa._v30.interfaces.itfInterfaceInterfaceData;
import pa._v30.modules.clsModuleBase;
import pa._v30.modules.clsPsychicApparatus;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import pa.clsPsychoAnalysis;
import sim.portrayal.Inspector;
import sim.portrayal.inspector.TabbedInspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 12.08.2009, 22:50:56
 * 
 */
public class clsInspectorTab_Modules extends Inspector implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsPsychoAnalysis moPA;
	JTree moModuleTree;
	JScrollPane moContentPane;
	TabbedInspector moContent = new TabbedInspector();
	JSplitPane moSplitPane;

    public clsInspectorTab_Modules(clsPsychoAnalysis poPA) {
		moPA= poPA;
		
		Box oBox1 = new Box(BoxLayout.PAGE_AXIS);
		
		//set root tree manually
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Psychic Apparatus");
		//grab the top element of the top-down design 
		clsPsychicApparatus oPsyApp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
		//build a tree with all members that start either with moC for clsModuleContainer or moE for clsModuleBase
		addModulesToTree( oPsyApp, root );
		addInterfacesToTree( oPsyApp, root);

		moModuleTree = new JTree(root);
		moModuleTree.addTreeSelectionListener(this);
		JScrollPane oTreeScroll = new JScrollPane(moModuleTree);
		
		moContentPane = new JScrollPane(moContent);
	
		moSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, oTreeScroll, moContentPane);
		moSplitPane.setOneTouchExpandable(true);
		moSplitPane.setContinuousLayout(true);
		moSplitPane.setDividerLocation(150);
		
		oBox1.add(moSplitPane);
		
        setLayout(new BorderLayout());
        add(oBox1, BorderLayout.CENTER);
        createTabs("Psychic Apparatus");
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
	private void addModulesToTree(clsPsychicApparatus poPA,
			DefaultMutableTreeNode poParentTreeNode) {
		ArrayList<String> oChilds = new ArrayList<String>();
		DefaultMutableTreeNode oParent = new DefaultMutableTreeNode("Modules");
		poParentTreeNode.add(oParent);
		
        for ( Map.Entry<Integer, clsModuleBase> module : poPA.moModules.entrySet() )	{
        	String oName = module.getValue().getClass().getSimpleName();
        	oChilds.add(oName);
        }
        Collections.sort(oChilds);
        for (String oName:oChilds) {
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(oName);
        	oParent.add(child);
        }
	}
	
	private void  addInterfacesToTree(clsPsychicApparatus poPA,
			DefaultMutableTreeNode poParentTreeNode) {
		ArrayList<String> oChilds = new ArrayList<String>();
		DefaultMutableTreeNode oParent = new DefaultMutableTreeNode("Interfaces");
		poParentTreeNode.add(oParent);
		
        for ( Entry<eInterfaces, ArrayList<Object>> e : poPA.moInterfaceData.entrySet() )	{
        	String oName = e.getKey().toString();
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

	private void createTabs(String nodeInfo) {
		moContentPane.remove(moContent);
		
		moContent = createInspectorModules( ((clsProcessor)moPA.getProcessor()).getPsychicApparatus(), nodeInfo, moModuleTree);
		moContent.setPreferredSize( new Dimension(300,300) );
		
		moContentPane.add(moContent);
		moContentPane.setViewportView(moContent);
		moContentPane.repaint();		
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
		createTabs(nodeInfo.toString());
	}
	
	/**
	 * In the valueChanged() method of a inspector panel this function is called and creates a inspector (which is a JPanel in the long term) 
	 * according to the pressed tree or button or whatever 'poModulName' is given. PLEASE reuse Inspectors! as they only tell how to show some 
	 * Information sometimes. So you can fill them with different Informations to show! cm
	 *
	 * @author langr
	 * 13.08.2009, 01:32:58
	 *
	 * @param moPA
	 * @param poModuleName
	 */
	private TabbedInspector createInspectorModules(clsPsychicApparatus moPA, String poModuleName, JTree poLeftMenu) {
		TabbedInspector oRetVal = null;
		
		if (poModuleName.charAt(0) == 'E') {
			oRetVal = createModules(moPA, poModuleName);
			
		} else if (poModuleName.charAt(0) == 'I' || poModuleName.charAt(0) == 'D') {
			oRetVal = createInterfaces(moPA, poModuleName);
			
		} else if(poModuleName.equals("Psychic Apparatus")) {
			oRetVal = new TabbedInspector(); 
			
			oRetVal.addInspector( new clsPAInspectorFunctional(poLeftMenu, true, moPA), "FM Compact");
			oRetVal.addInspector( new clsPAInspectorFunctional(poLeftMenu, false, moPA), "Functional Model");
			oRetVal.addInspector( new clsI_AllInterfaceData(moPA.moInterfaceData), "Interface Data");
		} else {
			oRetVal = new TabbedInspector();
		}
		
		return oRetVal;
	}
	
	private TabbedInspector createInterfaces(clsPsychicApparatus moPA, String poModuleName) {
		TabbedInspector oRetVal = new TabbedInspector();
		
		try {
			eInterfaces eI = eInterfaces.valueOf(poModuleName);
			oRetVal.addInspector(
					new clsI_SimpleInterfaceDataInspector(eI, moPA.moInterfaceData, moPA.moInterfaces_Recv_Send),
					"Simple");
			oRetVal.addInspector( new clsMeshInterface(moPA, eI), "Graph");
		} catch (java.lang.IllegalArgumentException e) {
			//do nothing
		}
		
		return oRetVal;
	}
	
	private void addAutocreatedInspectors(TabbedInspector poTI, clsPsychicApparatus poPA, String poModuleName) {
		try {
			String oName = "mo"+poModuleName;
			Field oField = poPA.getClass().getField(oName);
 			clsModuleBase oModule = (clsModuleBase) oField.get( poPA );
			
			if (oModule instanceof itfInspectorInternalState) {
				poTI.addInspector( 
						new cls_StateInspector(oModule), 
						"State");
			}
			
			if (oModule instanceof itfInterfaceDescription) {
				poTI.addInspector( 
						new cls_DescriptionInspector(oModule), 
						"Desc");				
			}
			
			if (oModule instanceof itfInspectorGenericDynamicTimeChart) {
				poTI.addInspector(
						new cls_GenericDynamicTimeChartInspector((itfInspectorGenericDynamicTimeChart) oModule),	
						"Time Chart");				
			} else if (oModule instanceof itfInspectorGenericTimeChart) { //else if is important! itfInspectorGenericDynamicTimeChart is derived from itfInspectorGenericTimeChart
				poTI.addInspector(
						new cls_GenericTimeChartInspector((itfInspectorGenericTimeChart) oModule),	
						"Time Chart");
			}
			
			if (oModule instanceof itfInspectorGenericActivityTimeChart) {
				poTI.addInspector(
						new cls_GenericActivityTimeChartInspector((itfInspectorGenericActivityTimeChart) oModule),	
						"Activity Chart");				
			}
			
			if (oModule instanceof itfInspectorDrives) {
				poTI.addInspector(
						new clsDriveInspector((itfInspectorDrives) oModule),	
						"Current Drives (Graph)");				
			}
			
			if (oModule instanceof itfInterfaceInterfaceData) {
				poTI.addInspector(
						new clsE_SimpleInterfaceDataInspector(oModule, poPA.moInterfaceData),
						"Interface Data");
				
				//iterating through all receive and send interfaces and creates a graphical inspector tab for each of them
				ArrayList<eInterfaces> oRecv = oModule.getInterfacesRecv();
				ArrayList<eInterfaces> oSend = oModule.getInterfacesSend();
				
				for (eInterfaces eRcv:oRecv) {
					poTI.addInspector( new clsMeshInterface(poPA, eRcv), "rcv "+eRcv.toString());
				}
				
				for (eInterfaces eSnd:oSend) {
					poTI.addInspector( new clsMeshInterface(poPA, eSnd), "snd "+eSnd.toString());
				}
			
			}
		} catch (java.lang.NoSuchFieldException e) {
			// do nothing
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}		
	}
	
	private TabbedInspector createModules(clsPsychicApparatus poPA, String poModuleName) {
		TabbedInspector oRetVal = new TabbedInspector();
	
		addAutocreatedInspectors(oRetVal, poPA, poModuleName);
		addHandCraftedInspectors(oRetVal, poPA, poModuleName);
		
		return oRetVal;
	}
	
	private void addHandCraftedInspectors(TabbedInspector poTI, clsPsychicApparatus poPA, String poModuleName) {		
		if(poModuleName.equals("E01_SensorsMetabolism")) {
		} else if(poModuleName.equals("E02_NeurosymbolizationOfNeeds")) {
		} else if(poModuleName.equals("E03_GenerationOfSelfPreservationDrives")) {
		} else if(poModuleName.equals("E04_FusionOfSelfPreservationDrives")) {
		} else if(poModuleName.equals("E05_AccumulationOfAffectsForSelfPreservationDrives")) {
//			poTI.addInspector( new clsDriveInspector(poPA.moE05_AccumulationOfAffectsForSelfPreservationDrives), "Current Drives (Graph)");
		} else if(poModuleName.equals("E06_DefenseMechanismsForDrives")) {
		} else if(poModuleName.equals("E07_InternalizedRulesHandler")) {
		} else if(poModuleName.equals("E08_ConversionToSecondaryProcessForDriveWishes")) {
		} else if(poModuleName.equals("E09_KnowledgeAboutReality_unconscious")) {
		} else if(poModuleName.equals("E10_SensorsEnvironment")) {
		} else if(poModuleName.equals("E11_NeuroSymbolizationEnvironment")) {
		} else if(poModuleName.equals("E12_SensorsBody")) {
		} else if(poModuleName.equals("E13_NeuroSymbolizationBody")) {
		} else if(poModuleName.equals("E14_ExternalPerception")) {
		} else if(poModuleName.equals("E18_CompositionOfAffectsForPerception")) {
		} else if(poModuleName.equals("E19_DefenseMechanismsForPerception")) {
		} else if(poModuleName.equals("E20_InnerPerception_Affects")) {
		} else if(poModuleName.equals("E21_ConversionToSecondaryProcessForPerception")) {
		} else if(poModuleName.equals("E22_SocialRulesSelection")) {
		} else if(poModuleName.equals("E23_ExternalPerception_focused")) {
		} else if(poModuleName.equals("E24_RealityCheck_1")) {
		} else if(poModuleName.equals("E25_KnowledgeAboutReality_1")) {
		} else if(poModuleName.equals("E26_DecisionMaking")) {
			poTI.addInspector( new clsE26DecisionCalculation(poPA.moE26_DecisionMaking), "Decision Calculation");
		} else if(poModuleName.equals("E27_GenerationOfImaginaryActions")) {
		} else if(poModuleName.equals("E28_KnowledgeBase_StoredScenarios")) {
		} else if(poModuleName.equals("E29_EvaluationOfImaginaryActions")) {
		} else if(poModuleName.equals("E30_MotilityControl")) {
		} else if(poModuleName.equals("E31_NeuroDeSymbolizationActionCommands")) {
		} else if(poModuleName.equals("E32_Actuators")) {
		} else if(poModuleName.equals("E33_RealityCheck_2")) {
		} else if(poModuleName.equals("E34_KnowledgeAboutReality_2")) {
		} else if(poModuleName.equals("E35_EmersionOfRepressedContent")) {
		} else if(poModuleName.equals("E36_RepressionHandler")) {
		} else if(poModuleName.equals("E37_PrimalRepressionForPerception")) {
		} else if(poModuleName.equals("E38_PrimalRepressionForSelfPreservationDrives")) {
		} else if(poModuleName.equals("E39_SeekingSystem_LibidoSource")) {
		} else if(poModuleName.equals("E40_NeurosymbolizationOfLibido")) {
		} else if(poModuleName.equals("E41_Libidostasis")) {			
		} else if(poModuleName.equals("E42_AccumulationOfAffectsForSexualDrives")) {
		} else if(poModuleName.equals("E43_SeparationIntoPartialSexualDrives")) {
		} else if(poModuleName.equals("E44_PrimalRepressionForSexualDrives")) {
		} else if(poModuleName.equals("E45_LibidoDischarge")) {
		} else if(poModuleName.equals("E46_FusionWithMemoryTraces")) {
		} else if(poModuleName.equals("E47_ConversionToPrimaryProcess")) {
		} 
	}	
}