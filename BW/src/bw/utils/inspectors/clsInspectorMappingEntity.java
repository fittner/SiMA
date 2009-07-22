/**
 * @author langr
 * 15.07.2009, 14:47:59
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors;

import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.clsMeatBody;
//import bw.entities.clsBubble;
import bw.entities.clsAnimal;
import bw.entities.clsBubble;
import bw.entities.clsBase;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsCarrot;
import bw.entities.clsEntity;
import bw.entities.clsFungus;
import bw.entities.clsHare;
import bw.entities.clsLynx;
import bw.entities.clsMobile;
import bw.entities.clsPlant;
import bw.entities.clsRemoteBot;
import bw.entities.clsStone;
import bw.entities.clsUraniumOre;
//import bw.entities.clsRemoteBot;
import bw.entities.clsStationary;
import bw.utils.inspectors.body.clsFillLevelInspector;
import bw.utils.inspectors.entity.clsInspectorBubble;
import bw.utils.inspectors.entity.clsInspectorFungus;
import bw.utils.inspectors.entity.clsInspectorRemoteBot;
import bw.utils.inspectors.entity.clsInspectorBase;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * assigns the required entity inspector classes to the object extending the clsEntity-class 
 * (mapping type of instance to mason inspector class)
 * 
 * @author langr
 * 15.07.2009, 14:47:59
 * 
 */
public class clsInspectorMappingEntity {

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
	public static TabbedInspector getInspectorEntity(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsEntity poEntity)
	{
    	TabbedInspector oRetVal = new TabbedInspector();

    	if( poEntity instanceof clsMobile )
    	{
	    	if( poEntity instanceof clsAnimal) {
	    		
	    	}
	    	else if( poEntity instanceof clsBubble) {
	    		oRetVal.addInspector( new clsInspectorBubble(poSuperInspector, poWrapper, poState, (clsBubble)poEntity), "Bubble");
	    	}
	    	else if( poEntity instanceof clsCake) {
	    		
	    	}
	    	else if( poEntity instanceof clsCan) {
	    		
	    	}
	    	else if( poEntity instanceof clsCarrot) {
	    		
	    	}
	    	else if( poEntity instanceof clsFungus) {
	    		oRetVal.addInspector( new clsInspectorFungus(poSuperInspector, poWrapper, poState, (clsFungus)poEntity), "Fungus");
	    	}
	    	else if( poEntity instanceof clsHare) {
	    		
	    	}
	    	else if( poEntity instanceof clsLynx) {
	    		
	    	}
	    	else if( poEntity instanceof clsPlant) {
	    		
	    	}
	    	else if( poEntity instanceof clsRemoteBot) {
	    		oRetVal.addInspector( new clsInspectorRemoteBot(poSuperInspector, poWrapper, poState, (clsRemoteBot)poEntity), "RemoteBot");
	    	}
	    	else if( poEntity instanceof clsStone) {
	    		
	    	}
	    	else if( poEntity instanceof clsUraniumOre) {
	    		
	    	}	    	
    	}
    	else if ( poEntity instanceof clsStationary )
    	{
    		int i = 1;
    		if( poEntity instanceof clsBase) {
	    		oRetVal.addInspector( new clsInspectorBase(poSuperInspector, poWrapper, poState, (clsBase)poEntity), "Base default");
	    		
	    	}
//	    	if( poEntity instanceof ) {
//	    		
//	    	}
//	    	else if( poEntity instanceof ) {
//	    		
//	    	}
    	}
    	
    	//add standard inspector if nothing happened
    	if(oRetVal.inspectors.size() == 0)  {
    		oRetVal.addInspector(poSuperInspector, "Values");
    	}

	    return oRetVal;
	}

	public static TabbedInspector getInspectorBody(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsBaseBody poBody)
	{
		TabbedInspector oRetVal = new TabbedInspector();

    	if( poBody instanceof clsComplexBody) {
    		oRetVal.addInspector( new clsFillLevelInspector(poSuperInspector, poWrapper, poState, ((clsComplexBody)poBody).getInternalSystem().getStomachSystem()), "Stomach System");
    	}
    	else if( poBody instanceof clsMeatBody ) {
    	}

    	//add standard inspector if nothing happened
    	if(oRetVal.inspectors.size() == 0)  {
    		oRetVal.addInspector(poSuperInspector, "Values");
    	}
    	
	    return oRetVal;
	}
}
