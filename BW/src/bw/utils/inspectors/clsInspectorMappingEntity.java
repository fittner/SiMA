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
import bw.body.clsSimpleBody;
//import bw.entities.clsBubble;

import bw.entities.clsBubble;
import bw.entities.clsFungusEater;
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
import bw.utils.inspectors.body.clsInspectorFastMessengers;
import bw.utils.inspectors.body.clsInspectorFillLevel;
import bw.utils.inspectors.body.clsInspectorAttributes;
import bw.utils.inspectors.body.clsInspectorFlesh;
import bw.utils.inspectors.body.clsInspectorInternalEnergyConsumption;
import bw.utils.inspectors.body.clsInspectorInternalSystems;
import bw.utils.inspectors.body.clsInspectorSlowMessengers;
import bw.utils.inspectors.entity.clsInspectorARSin;
import bw.utils.inspectors.entity.clsInspectorFungusEater;
import bw.utils.inspectors.entity.clsInspectorBasic;
import bw.utils.inspectors.entity.clsInspectorFungus;
import bw.utils.inspectors.entity.clsInspectorRemoteBot;
import bw.utils.inspectors.entity.clsInspectorFungusBase;
import bw.utils.inspectors.entity.clsInspectorSensor;
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

    	//change the default inspector to the one you created for the entity if you want more inspected
    	if( poEntity instanceof clsMobile )
    	{
	    	if( poEntity instanceof clsBubble) {
	    		oRetVal.addInspector( new clsInspectorARSin(poSuperInspector, poWrapper, poState, (clsBubble)poEntity), "Bubble");
	    	}
	    	else if( poEntity instanceof clsFungusEater) {
	    		oRetVal.addInspector( new clsInspectorFungusEater(poSuperInspector, poWrapper, poState, (clsFungusEater)poEntity), "Fungus Eater");
	    	}
	    	else if( poEntity instanceof clsCake) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Cake");
	    	}
	    	else if( poEntity instanceof clsCan) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Can");	    		
	    	}
	    	else if( poEntity instanceof clsCarrot) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Carrot");
	    	}
	    	else if( poEntity instanceof clsFungus) {
	    		oRetVal.addInspector( new clsInspectorFungus(poSuperInspector, poWrapper, poState, (clsFungus)poEntity), "Fungus");
	    	}
	    	else if( poEntity instanceof clsHare) {
	    		oRetVal.addInspector(new clsInspectorSensor(poSuperInspector, poWrapper,poState,poEntity), "Hare");
	    		//oRetVal.addInspector( new clsInspectorDefault(poSuperInspector, poWrapper, poState, poEntity), "Hare");
	    	}
	    	else if( poEntity instanceof clsLynx) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Lynx");
	    	}
	    	else if( poEntity instanceof clsPlant) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Plant");	    		
	    	}
	    	else if( poEntity instanceof clsRemoteBot) {
	    		oRetVal.addInspector(new clsInspectorSensor(poSuperInspector, poWrapper,poState,(clsRemoteBot)poEntity), "RemoteBot Sensors");
	    		oRetVal.addInspector( new clsInspectorRemoteBot(poSuperInspector, poWrapper, poState, (clsRemoteBot)poEntity), "RemoteBot");
	    	}
	    	else if( poEntity instanceof clsStone) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Stone");
	    	}
	    	else if( poEntity instanceof clsUraniumOre) {
	    		oRetVal.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "UraniumOre");
	    	}	    	
    	}
    	else if ( poEntity instanceof clsStationary )
    	{
    		// int i = 1; //unused int - removed by TD
    		if( poEntity instanceof clsBase) {
	    		oRetVal.addInspector(new clsInspectorFungusBase(poSuperInspector, poWrapper, poState, (clsBase)poEntity), "FungusBase default");
	    	}
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
    		oRetVal.addInspector( new clsInspectorFillLevel(poSuperInspector, poWrapper, poState, ((clsComplexBody)poBody).getInternalSystem().getStomachSystem()), "Stomach");
    		oRetVal.addInspector( new clsInspectorInternalSystems(poSuperInspector, poWrapper, poState, ((clsComplexBody)poBody).getInternalSystem()), "Internal");
    		oRetVal.addInspector( new clsInspectorSlowMessengers(poSuperInspector, poWrapper, poState, ((clsComplexBody)poBody).getInternalSystem().getSlowMessengerSystem()), "Slow Messengers");
    		oRetVal.addInspector( new clsInspectorFastMessengers(poSuperInspector, poWrapper, poState, ((clsComplexBody)poBody).getInternalSystem().getFastMessengerSystem()), "Fast Messengers");    		
    		oRetVal.addInspector( new clsInspectorAttributes(poSuperInspector, poWrapper, poState, poBody.getAttributes()), "Attributes");    		
    		oRetVal.addInspector( new clsInspectorFlesh(poSuperInspector, poWrapper, poState, ((clsComplexBody) poBody).getInternalSystem().getFlesh()), "Flesh");    		
    		oRetVal.addInspector( new clsInspectorInternalEnergyConsumption(poSuperInspector, poWrapper, poState, ((clsComplexBody) poBody).getInternalEnergyConsumption()), "Int.Energy Cons.");
    	} else if( poBody instanceof clsMeatBody ) {
    		oRetVal.addInspector( new clsInspectorAttributes(poSuperInspector, poWrapper, poState, poBody.getAttributes()), "Attributes");    		
    		oRetVal.addInspector( new clsInspectorFlesh(poSuperInspector, poWrapper, poState, ((clsMeatBody) poBody).getFlesh()), "Flesh");
    	} else if (poBody instanceof clsSimpleBody) {
    		oRetVal.addInspector( new clsInspectorAttributes(poSuperInspector, poWrapper, poState, poBody.getAttributes()), "Attributes");    		
    	} else {
    		throw new java.lang.Error("unkown body type "+poBody.getClass().getName());
    	}
    	

    	//add standard body inspector if nothing happened
    	if(oRetVal.inspectors.size() == 0)  {
    		oRetVal.addInspector(poSuperInspector, "Values");
    	}
    	
	    return oRetVal;
	}
}
