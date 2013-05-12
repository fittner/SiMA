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
import bw.body.itfget.itfGetBody;


import bw.entities.clsARSIN;

import bw.entities.clsEntity;

import bw.utils.inspectors.body.clsInspectorFastMessengers;
import bw.utils.inspectors.body.clsInspectorFillLevel;
import bw.utils.inspectors.body.clsInspectorAttributes;
import bw.utils.inspectors.body.clsInspectorFlesh;
import bw.utils.inspectors.body.clsInspectorInternalEnergyConsumption;
import bw.utils.inspectors.body.clsInspectorInternalSystems;
import bw.utils.inspectors.body.clsInspectorSlowMessengers;
import bw.utils.inspectors.entity.clsInspectorARSin;
import bw.utils.inspectors.entity.clsInspectorARSinDebugActions;
import bw.utils.inspectors.entity.clsInspectorInventory;
import bw.utils.inspectors.entity.clsInspectorPositionLogChart;
import bw.utils.inspectors.entity.clsInspectorPositionLoggerCSV;
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
    	poEntity.addEntityInspector(oRetVal, poSuperInspector, poWrapper, poState, poEntity);
/*    	functionality moved to the entity classes
 * 		if( poEntity instanceof clsMobile )
    	{
	    	if( poEntity instanceof clsARSIN) {
	    		oRetVal.addInspector( new clsInspectorARSin(poSuperInspector, poWrapper, poState, (clsARSIN)poEntity), "ARSIN");
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
 */   	
    	//add position logger inspector
    	oRetVal.addInspector( new clsInspectorPositionLoggerCSV(poEntity.getPositionLogger()), "Pos.CSV");
    	oRetVal.addInspector( new clsInspectorPositionLogChart(poEntity.getPositionLogger()), "Pos.Chart");
    	
    	
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
	
	/**
	 * A inspector that holds the most important information of the arsin. to quick see what is going on
	 *
	 * @since 21.09.2011 10:46:32
	 */
	public static TabbedInspector getInspectorARSINOverview(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsEntity poEntity)
	{
    	TabbedInspector oRetVal = new TabbedInspector();

    	//change the default inspector to the one you created for the entity if you want more inspected

	    	if( poEntity instanceof clsARSIN) {
	    		
	    		//***Body Overview***
	    		//this like work because I can be sure we have a ARSin with a complex body, because there is no arsin without one!
	    		//clsComplexBody oBody = (clsComplexBody)((itfGetBody) poEntity).getBody();
	        	//oRetVal.addInspector( new clsInspectorBodyOverview(poSuperInspector, poWrapper, poState, oBody), "Body Overview");
	        	
	    		//add all information you want to add to the overview inspector here...
	    		oRetVal.addInspector( new clsInspectorARSin(poSuperInspector, poWrapper, poState, (clsARSIN)poEntity), "ARSIN");
	    			    		
	    		//Inventory-Inspector
	    		oRetVal.addInspector(new clsInspectorInventory((clsARSIN) poEntity), "Inventory");
	    			    		
	    	}
	    	else{
	    	
	    		System.out.println("Error: Overview Inspector only works on Entity types of ARSin!!!");
	    	}

    	return oRetVal;
	}
	
	
	/**
	 * A inspector that holds debug actions for the ARSIN
	 *
	 * @since 21.09.2011 10:46:32
	 */
	public static TabbedInspector getInspectorARSINDebugActions(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsEntity poEntity)
	{
    	TabbedInspector oRetVal = new TabbedInspector();
    	
    	oRetVal.setBounds(100,100,100,100);

    	//change the default inspector to the one you created for the entity if you want more inspected

	    	if( poEntity instanceof clsARSIN) {
	    		
	    		//***Debug Actions ***
	    		//this like work because I can be sure we have a ARSin with a complex body, because there is no arsin without one!
	    		clsComplexBody oBody = (clsComplexBody)((itfGetBody) poEntity).getBody();
	        	oRetVal.addInspector( new clsInspectorARSinDebugActions(poSuperInspector, poWrapper, poState, (clsARSIN)poEntity), "Arsin Debug Actions");

	    	}
	    	else{
	    	
	    		System.out.println("Error: DebugAction Inspector only works on Entity types of ARSin!!!");
	    	}

    	return oRetVal;
	}
}

