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
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import simple.dumbmind.clsDumbMindA;

/**
 * assigns the required inspector class to the object extending the clsEntity-class 
 * (mapping type of instance to mason inspector class)
 * 
 * @author langr
 * 01.04.2009, 15:44:50
 * 
 */
public class clsInspectorMapping {

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
	public static Inspector getInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsBaseDecisionUnit poDU)
	{
    	Inspector oRetVal = null;
    	
        if (poDU == null) return poSuperInspector; //return standard mason inspector when decision unit is null
        
        //extend this if-statement with your new clsEntity-classes or inspectors
        if( poDU instanceof clsDumbMindA) {
        	oRetVal = new clsDumbBrainInspector(poSuperInspector, poWrapper, poState, (clsDumbMindA) poDU);
        }
//        else if (poEntity instanceof clsMyNewClass ) {
//        	oRetVal = new clsMyNewInspector(poSuperInspector, poWrapper, poState);
//       }
        else	{
        	oRetVal = poSuperInspector;  	
        }
	    return oRetVal;
	}
}
