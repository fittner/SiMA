/**
 * @author langr
 * 01.04.2009, 15:44:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package inspectors;

import pa.clsPsychoAnalysis;
import decisionunit.clsBaseDecisionUnit;
import du.itf.itfDecisionUnit;
import inspectors.mind.clsDumbBrainInspector;
import inspectors.mind.clsRemoteControlInspector;
import inspectors.mind.clsReactiveInspector;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import simple.dumbmind.clsDumbMindA;
import simple.remotecontrol.clsRemoteControl;
import simple.reactive.clsReactive;

/**
 * assigns the required decision inspector class to the object extending the clsEntity-class 
 * (mapping type of instance to mason inspector class)
 * 
 * @author langr
 * 01.04.2009, 15:44:50
 * 
 */
public class clsInspectorMappingDecision {

	/**
	 * Insert the clsEntity.Object and get the corresponding customized inspector-class
	 * This method is called from clsMobileObject2D, from the override-method getInspector
	 * 
	 * TODO langr: If you want to assign the Inspectors of our clsEntity-Instances during 
	 * the creation phase, you have to think about a possibility to get the super.Inspector
	 * (the standard mason-Inspector of the mason-object MobileObject2D) in this phase. 
	 *
	 * @author langr
	 * 01.04.2009, 15:50:45
	 *
	 * @param poSuperInspector - standard mason inspector - you still might use it in your customized inspector (see clsDumbbrainInspector)
	 * @param poWrapper - the Location-Wrapper that holds the selected Portrayal2D-object as well 
	 * @param poState - simulation environment to register the controls
	 * @param poEntity - the agent-object to distinguish between the classes... 
	 * @return - the new inspector with the defined look and feel
	 */
	@SuppressWarnings("deprecation")
	public static TabbedInspector getInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, itfDecisionUnit poItfDU) {
		clsBaseDecisionUnit poDU = (clsBaseDecisionUnit) poItfDU;
		
    	TabbedInspector oRetVal = new TabbedInspector();
    	
    	
    	
    	
    	
    	
        //extend this if-statement with your new clsEntity-classes or inspectors
        if( poDU instanceof clsDumbMindA) {
        	oRetVal.addInspector( new clsDumbBrainInspector(poSuperInspector, poWrapper, poState, (clsDumbMindA)poDU), "Brain Insp.");
        	
        } else if( poDU instanceof clsRemoteControl) {
        	oRetVal.addInspector( new clsRemoteControlInspector(poSuperInspector, poWrapper, poState, (clsRemoteControl) poDU), "Brain Insp.");
        	
        } else if (poDU instanceof clsPsychoAnalysis ) {
        	
//        	if(clsPsychoAnalysis.getModelVersion().equals("v19")) {
//           		//oRetVal.addInspector( new inspectors.mind.pa._v19.clsPsychoAnalysisInspector(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "PA-Decision");
//            	//oRetVal.addInspector( new inspectors.mind.pa._v19.clsMemoryInspectorTab(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "PA-Memory 2.0"); //shows a tab with the memory inspector, mapping for the tree etc see clsInspectorMappingPA
//         	} 
//        	else if (clsPsychoAnalysis.getModelVersion().equals("v30")){
//        		//oRetVal.addInspector( new inspectors.mind.pa._v30.clsInspectorTab_Modules((clsPsychoAnalysis) poDU), "PA-Modules");
//            	oRetVal.addInspector( new inspectors.mind.pa._v30.clsInspectorTab_Memory(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "PA-Memory"); //shows a tab with the memory inspector, mapping for the tree etc see clsInspectorMappingPA
//        		oRetVal.addInspector( new inspectors.mind.pa._v30.clsInspectorTab_DataLogger((clsPsychoAnalysis) poDU), "Data Logger");
//        		
//        		//TODO CM these two should also work in V38, but I get a exeption, DEBUG! 28.06.2011
//                oRetVal.addInspector( new clsInspectorSensorData(poDU), "Sensors");
//                oRetVal.addInspector( new clsInspectorActionCommands(poDU), "Actions");
//        	}
//        	else 
        	if (clsPsychoAnalysis.getModelVersion().equals("v38")){
        		oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_Modules((clsPsychoAnalysis) poDU), "PA-Modules");
            	oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_Memory(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "PA-Memory"); //shows a tab with the memory inspector, mapping for the tree etc see clsInspectorMappingPA
        		oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_DataLogger((clsPsychoAnalysis) poDU), "Data Logger");
        		oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_ARSinOverview(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "ARSin Overview");
        		oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_PlanningOverview(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "Planning Overview");
        		oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_PersonalityParameter((clsPsychoAnalysis) poDU), "Personality Parameters");
        		//oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_ThoughtsIntention(), "Thoughts Intention - Overview");
        	}
        	else {
        		//FIXME (muchitsch) - activate inspectors
        	}
        	
        } else if( poDU instanceof clsReactive) {
        	oRetVal.addInspector( new clsReactiveInspector(poSuperInspector, poWrapper, poState, (clsReactive) poDU), "Reactive DU Insp.");
        }
        
        //oRetVal.addInspector( new clsInspectorSensorData(poDU), "Sensors");
        //oRetVal.addInspector( new clsInspectorActionCommands(poDU), "Actions");        
        
    	//add standard inspector if nothing happened
    	if(oRetVal.inspectors.size() == 0)  {
    		oRetVal.addInspector(poSuperInspector, "Values");
    	}
        
	    return oRetVal;
	}

}
