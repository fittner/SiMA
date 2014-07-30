/**
 * @author langr
 * 01.04.2009, 15:44:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package utils;

import control.clsPsychoAnalysis;
import control.interfaces.clsBaseDecisionUnit;
import control.interfaces.itfDecisionUnit;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * assigns the required decision inspector class to the object extending the clsEntity-class 
 * (mapping type of instance to mason inspector class)
 * 
 * @author langr
 * 01.04.2009, 15:44:50
 * 
 */
public class clsInspectorMappingDecisionUnit {

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
	 * @throws Exception 
	 */
	public static TabbedInspector getInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, itfDecisionUnit poItfDU) throws Exception {
		clsBaseDecisionUnit poDU = (clsBaseDecisionUnit) poItfDU;
		
    	TabbedInspector oRetVal = new TabbedInspector();
    	
        //extend this if-statement with your new clsEntity-classes or inspectors
        if (poDU instanceof clsPsychoAnalysis ) {
        	if (clsPsychoAnalysis.getModelVersion().equals("v38")){
        		oRetVal.addInspector( new mind.clsInspectorTab_Modules((clsPsychoAnalysis) poDU), "PA-Modules");
            	oRetVal.addInspector( new mind.clsInspectorTab_Memory(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "PA-Memory"); //shows a tab with the memory inspector, mapping for the tree etc see clsInspectorMappingPA
        		oRetVal.addInspector( new mind.clsInspectorTab_ARSinOverview(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "ARSin Overview");
        		oRetVal.addInspector( new mind.clsInspectorTab_PlanningOverview(poSuperInspector, poWrapper, poState, (clsPsychoAnalysis) poDU), "Planning Overview");
        		oRetVal.addInspector( new mind.clsInspectorTab_PersonalityParameter((clsPsychoAnalysis) poDU), "Personality Parameters");
        		//oRetVal.addInspector( new inspectors.mind.pa._v38.clsInspectorTab_ThoughtsIntention(), "Thoughts Intention - Overview");
        	} else {
        		throw new IllegalArgumentException("No Inspectors available for psychoanalytic decision unit version " + clsPsychoAnalysis.getModelVersion().toString() + " passed as poItfDU.");
        	}
        } else {
        	throw new IllegalArgumentException("Passed decision unit interface is not compatible with any available inspectors");
        }
        
    	//add standard inspector if nothing happened
    	if(oRetVal.inspectors.size() == 0)  {
    		oRetVal.addInspector(poSuperInspector, "Values");
    	}
        
	    return oRetVal;
	}

}
