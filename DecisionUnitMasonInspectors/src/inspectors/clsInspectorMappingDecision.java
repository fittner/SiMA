/**
 * @author langr
 * 01.04.2009, 15:44:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package inspectors;

import decisionunit.clsBaseDecisionUnit;
import inspectors.mind.clsDumbBrainInspector;
import inspectors.mind.clsRemoteControlInspector;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import simple.dumbmind.clsDumbMindA;
import simple.remotecontrol.clsRemoteControl;

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
	public static TabbedInspector getInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsBaseDecisionUnit poDU)
	{
    	TabbedInspector oRetVal = new TabbedInspector();
    	
        //extend this if-statement with your new clsEntity-classes or inspectors
        if( poDU instanceof clsDumbMindA) {
        	oRetVal.addInspector( new clsDumbBrainInspector(poSuperInspector, poWrapper, poState, (clsDumbMindA)poDU), "Brain Insp.");
        }
        else if( poDU instanceof clsRemoteControl) {
        	oRetVal.addInspector( new clsRemoteControlInspector(poSuperInspector, poWrapper, poState, (clsRemoteControl) poDU), "Brain Insp.");
        }
//        else if (poEntity instanceof clsMyNewClass ) {
//        	oRetVal.addInspector( new clsMyNewInspector(poSuperInspector, poWrapper, poState), "Name of Inspector-Tab");
//       }
        
        oRetVal.addInspector( new clsInspectorSensorData(poSuperInspector, poWrapper, poState, poDU), "Sensor Information");
        
    	//add standard inspector if nothing happened
    	if(oRetVal.inspectors.size() == 0)  {
    		oRetVal.addInspector(poSuperInspector, "Values");
    	}
        
	    return oRetVal;
	}
}
