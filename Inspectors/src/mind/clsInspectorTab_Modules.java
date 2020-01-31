/**
 * clsPsychoAnalysisInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 12.08.2009, 22:50:56
 */
package mind;

//import graph.clsGraph;
import graph.clsGraphCompareInterfaces;
import graph.clsGraphForRules;
import graph.clsGraphForSTM;
import graph.clsGraphInterface;
//import graph.clsGraphWindow;
import graph.clsMeshInterface;
import inspector.interfaces.itfGraphCompareInterfaces;
import inspector.interfaces.itfGraphInterface;
import inspector.interfaces.itfInspectorAdvancedStackedBarChart;
import inspector.interfaces.itfInspectorAreaChart;
import inspector.interfaces.itfInspectorBarChart;
import inspector.interfaces.itfInspectorBarChartF06;
import inspector.interfaces.itfInspectorBarChartF19;
import inspector.interfaces.itfInspectorCombinedTimeChart;
import inspector.interfaces.itfInspectorForRules;
import inspector.interfaces.itfInspectorForSTM;
import inspector.interfaces.itfInspectorGenericActivityTimeChart;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;
import inspector.interfaces.itfInspectorGenericTimeChart;
import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInspectorModificationDrives;
import inspector.interfaces.itfInspectorSpiderWebChart;
import inspector.interfaces.itfInspectorStackedAreaChart;
import inspector.interfaces.itfInspectorStackedBarChart;
import inspector.interfaces.itfInterfaceDescription;
import inspector.interfaces.itfInterfaceInterfaceData;
//import memorymgmt.enums.eContentType;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import primaryprocess.modules.F14_ExternalPerception;
//import base.datahandlertools.clsDataStructureGenerator;
//import base.datatypes.clsThingPresentation;
//import base.datatypes.clsThingPresentationMesh;
//import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsPsychicApparatus;
import control.clsProcessor;
import control.clsPsychoAnalysis;
import mind.autocreated.clsE_SimpleInterfaceDataInspector;
import mind.autocreated.clsI_SimpleInterfaceDataInspector;
import mind.autocreated.cls_AdvancedStackedBarChartInspector;
import mind.autocreated.cls_AreaChartInspector;
import mind.autocreated.cls_BarChartInspector;
import mind.autocreated.cls_BarChartInspectorF06;
import mind.autocreated.cls_BarChartInspectorF19;
import mind.autocreated.cls_CombinedGenericChart;
import mind.autocreated.cls_CombinedTimeChart;
import mind.autocreated.cls_DescriptionInspector;
import mind.autocreated.cls_GenericActivityTimeChartInspector;
import mind.autocreated.cls_GenericDynamicTimeChartInspector;
import mind.autocreated.cls_GenericTimeChartInspector;
import mind.autocreated.cls_MultipleBarChartsInspector;
import mind.autocreated.cls_SpiderWebChartInspector;
import mind.autocreated.cls_StackedAreaChartInspector;
import mind.autocreated.cls_StackedBarChartInspector;
import mind.autocreated.cls_StateInspector;
import mind.functionalmodel.clsPAInspectorFunctional;
import mind.handcrafted.clsF26DecisionCalculation;
import mind.handcrafted.clsI_AllInterfaceData;
import mind.handcrafted.clsInspectorImageDrives;
import modules.interfaces.eInterfaces;
import sim.portrayal.Inspector;
import sim.portrayal.inspector.TabbedInspector;
import utils.clsExceptionUtils;

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
		
		if (poModuleName.charAt(0) == 'F') {
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
	
	public static void addAutocreatedInspectors(TabbedInspector poTI, clsPsychicApparatus poPA, String poModuleName) {
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
			
			if (oModule instanceof itfInspectorSpiderWebChart) { 
				poTI.addInspector(
						new cls_SpiderWebChartInspector((itfInspectorSpiderWebChart) oModule, ""),	
						"Spider Chart");
			}
			
			if (oModule instanceof itfInspectorGenericActivityTimeChart) {
				poTI.addInspector(
						new cls_GenericActivityTimeChartInspector((itfInspectorGenericActivityTimeChart) oModule),	
						"Activity Chart");				
			}
			
			if (oModule instanceof itfInspectorAreaChart) {
				poTI.addInspector(
						new cls_AreaChartInspector((itfInspectorAreaChart) oModule),	
						"AreaChart");				
			}
			
			if (oModule instanceof itfInspectorBarChart) {
				poTI.addInspector(
						new cls_BarChartInspector((itfInspectorBarChart) oModule),	
						"BarChart");				
			}
			if (oModule instanceof itfInspectorStackedBarChart) {
				poTI.addInspector(
						new cls_StackedBarChartInspector((itfInspectorStackedBarChart) oModule),	
						"StackedBarChart");				
			}
			if (oModule instanceof itfInspectorAdvancedStackedBarChart) {
				poTI.addInspector(
						new cls_AdvancedStackedBarChartInspector((itfInspectorAdvancedStackedBarChart) oModule),	
						"StackedBarChart");				
			}
			/*-------------------------BarChart For F06------------------------*/
			if (oModule instanceof itfInspectorBarChartF06) {
				poTI.addInspector(
						new cls_BarChartInspectorF06((itfInspectorBarChartF06) oModule),	
						"BarChartF06");				
			}
			/*------------------------------------------------------------------*/
			/*-------------------------BarChart For F19------------------------*/
			if (oModule instanceof itfInspectorBarChartF19) {
				poTI.addInspector(
						new cls_BarChartInspectorF19((itfInspectorBarChartF19) oModule),	
						"BarChartF19");				
			}
			/*------------------------------------------------------------------*/
			if (oModule instanceof itfInspectorCombinedTimeChart) {
				poTI.addInspector(
						new cls_CombinedTimeChart((itfInspectorCombinedTimeChart) oModule),	
						"Combined Time Chart");				
			}
			
//			if (oModule instanceof itfInspectorDrives) {
//				poTI.addInspector(
//						new clsDriveInspector((itfInspectorDrives) oModule),	
//						"Current Drives (Graph)");				
//			}
			
			if (oModule instanceof itfGraphCompareInterfaces) {
				
				//iterating through all receive and send interfaces and creates a graphical inspector tab for each of them
				ArrayList<eInterfaces> oRecv = ((itfGraphCompareInterfaces )oModule).getCompareInterfacesRecv();
				ArrayList<eInterfaces> oSend = ((itfGraphCompareInterfaces )oModule).getCompareInterfacesSend();
				poTI.addInspector( new clsGraphCompareInterfaces(poPA, oRecv, oSend, true), "Input vs. Output");
			
			}
			
			if (oModule instanceof itfGraphInterface) {
				
				//iterating through all receive and send interfaces and creates a graphical inspector tab for each of them
				ArrayList<eInterfaces> inter = ((itfGraphInterface )oModule).getGraphInterfaces();
				poTI.addInspector( new clsGraphInterface(poPA, inter, true), "Interfaces");
			
			}
			
			if (oModule instanceof itfInterfaceInterfaceData) {
				poTI.addInspector(
						new clsE_SimpleInterfaceDataInspector(oModule, poPA.moInterfaceData),
						"Interface Data");
				
				//iterating through all receive and send interfaces and creates a graphical inspector tab for each of them
				ArrayList<eInterfaces> oRecv = oModule.getInterfacesRecv();
				ArrayList<eInterfaces> oSend = oModule.getInterfacesSend();
				
				for (eInterfaces eRcv:oRecv) {
					//poTI.addInspector( new clsMeshInterface(poPA, eRcv), "rcv "+eRcv.toString());				
					ArrayList<eInterfaces> eRecvList = new ArrayList<eInterfaces>();
					eRecvList.add(eRcv);
					poTI.addInspector( new clsGraphInterface(poPA, eRecvList, false), "rcv "+eRcv.toString());
				}
				
				for (eInterfaces eSnd:oSend) {
					//poTI.addInspector( new clsMeshInterface(poPA, eSnd), "snd "+eSnd.toString());
					ArrayList<eInterfaces> eSndList = new ArrayList<eInterfaces>();
					eSndList.add(eSnd);
					poTI.addInspector( new clsGraphInterface(poPA, eSndList, false), "snd "+eSnd.toString());
				}
			}
			if (oModule instanceof itfInspectorForSTM) {
				poTI.addInspector(
						new clsGraphForSTM (false, (itfInspectorForSTM) oModule),
						"STM");		//erstellt einen neuen Tab mit diesen Namen		
			}
			// Ivy - the rules as a graph
			if (oModule instanceof itfInspectorForRules) {
				poTI.addInspector(
						new clsGraphForRules (true, (itfInspectorForRules) oModule),
						"Rules");		//erstellt einen neuen Tab mit diesen Namen		
			}
			
			if (oModule instanceof itfInspectorModificationDrives) {
				poTI.addInspector(
						new clsInspectorImageDrives((itfInspectorModificationDrives) oModule),
						"pictogram DM-Rules");
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
	
	public static void addHandCraftedInspectors(TabbedInspector poTI, clsPsychicApparatus poPA, String poModuleName) {		
		if(poModuleName.equals("F01_SensorsMetabolism")) {
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
		} else if(poModuleName.equals("F14_ExternalPerception")) {
			//Kollmann: for simplicity use an anonymous subclass of cls_StateInspector als bodystate inspector
			poTI.addInspector(new cls_StateInspector(poPA.moF14_ExternalPerception) {
				/** DOCUMENT (Kollmann) - insert description; @since 20.04.2015 13:21:27 */
				private static final long serialVersionUID = -8296500660267573600L;

				@Override
				protected void updateContent() {
					moContent = ((F14_ExternalPerception)moObject).getBodystatesTextual();
				}
			} //end of anonymous subclass
			,"Bodystates");
//			poTI.addInspector(new clsGraphWindow(true) {
//                /** DOCUMENT (kollmann) - insert description; @since 01.07.2019 12:52:44 */
//                private static final long serialVersionUID = -3714815609755867501L;
//                 @Override
//                 protected void createGraphes() {
//                        moGraphes = new ArrayList<clsGraph>();
//                        moSplitPanes = new ArrayList<JSplitPane>();
//                        moGraphes.add(new clsGraph(mbOrientationVertical));
//                        moGraphes.get(0).setRootNodeName("Short Term Memory (F90)");
//                        updateGraphes();                              
//                 }
//                 @Override
//                 protected void updateInspectorData() {
//                        ArrayList<Object> oMesh = new ArrayList<Object>();
//
//                        clsThingPresentationMesh test = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ACTION, new ArrayList<>(), "Test1"));
//
//                        oMesh.add(test);
//
//                        moGraphes.get(0).setMoMesh(oMesh);
//                }
//           }, "STM");
		} else if(poModuleName.equals("E18_CompositionOfAffectsForPerception")) {
		} else if(poModuleName.equals("E19_DefenseMechanismsForPerception")) {
		} else if(poModuleName.equals("E20_InnerPerception_Affects")) {
		} else if(poModuleName.equals("E21_ConversionToSecondaryProcessForPerception")) {
		} else if(poModuleName.equals("E22_SocialRulesSelection")) {
		} else if(poModuleName.equals("E23_ExternalPerception_focused")) {
		} else if(poModuleName.equals("E24_RealityCheck_1")) {
		} else if(poModuleName.equals("E25_KnowledgeAboutReality_1")) {
		} else if(poModuleName.equals("F26_DecisionMaking")) {
			poTI.addInspector( new clsF26DecisionCalculation(poPA.moF26_DecisionMaking), "Decision Calculation");
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
		} else if(poModuleName.equals("F63_CompositionOfEmotions")) {
			cls_CombinedGenericChart oContainer = new cls_CombinedGenericChart();
			oContainer.addInspector(new cls_StackedAreaChartInspector((itfInspectorStackedAreaChart) poPA.moF63_CompositionOfEmotions,
					"Pleasure", "Pleasure Development", "PLEASURE"));
			oContainer.addInspector(new cls_StackedAreaChartInspector((itfInspectorStackedAreaChart) poPA.moF63_CompositionOfEmotions,
					"Un-pleasure", "Pleasure Development", "UNPLEASURE"));
			oContainer.addInspector(new cls_StackedAreaChartInspector((itfInspectorStackedAreaChart) poPA.moF63_CompositionOfEmotions,
					"Aggressive", "Pleasure Development", "AGGRESSIVE"));
			oContainer.addInspector(new cls_StackedAreaChartInspector((itfInspectorStackedAreaChart) poPA.moF63_CompositionOfEmotions,
					"Libidinous", "Pleasure Development", "LIBIDINOUS"));
			poTI.addInspector(oContainer, "Emotion Development");
			oContainer = new cls_CombinedGenericChart();
			oContainer.addInspector(new cls_MultipleBarChartsInspector(poPA.moF63_CompositionOfEmotions, "Associated Emotion"));
			oContainer.addInspector(new cls_MultipleBarChartsInspector(poPA.moF63_CompositionOfEmotions, "(weighted) Valuation"));
			oContainer.addInspector(new cls_MultipleBarChartsInspector(poPA.moF63_CompositionOfEmotions, "Transfered Emotion"));
			oContainer.setLayout(new BoxLayout(oContainer, BoxLayout.X_AXIS));
			poTI.addInspector(oContainer, "Emotion Transfer");
		}
	}
	
	public void close(){
		for( Object oInsp : moContent.inspectors) {
			if(oInsp instanceof clsPAInspectorFunctional) {
				((clsPAInspectorFunctional) oInsp).closeAllChildWindows();
			}
		}
	}
}
