/**
 * clsMemory.java: DecisionUnits - pa.memory
 
 * @author langr
 * 11.08.2009, 11:17:10
 */
package pa.memory;

import java.util.ArrayList;

import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsThingPresentationSingle;
import pa.interfaces.itfPrimaryProcessAssociation;
import pa.interfaces.itfPrimaryProcessRetrieval;
import pa.interfaces.itfPrimaryProcessStorage;
import pa.interfaces.itfSecondaryProcessAssociation;
import pa.interfaces.itfSecondaryProcessRetrieval;
import pa.interfaces.itfSecondaryProcessStorage;
import pa.loader.clsContextLoader;
import config.clsBWProperties;
import enums.eEntityType;

//import pa.datatypes.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:17:10
 * 
 */
public class clsMemory implements 
	itfPrimaryProcessAssociation, itfPrimaryProcessRetrieval, itfPrimaryProcessStorage, 		//PrimaryProcess access interfaces
	itfSecondaryProcessAssociation, itfSecondaryProcessRetrieval, itfSecondaryProcessStorage	//SecondaryProcess access interfaces
	{
	
	public static final String P_REPRESSEDCONTENTSSTORAGE = "repressedcontentsstorage";
	public static final String P_CURRENTCONTEXT = "currentcontext";
	
	public clsRepressedContentsStore moRepressedContentsStore;
	public clsCurrentContextStorage moCurrentContextStorage;
	
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 11.08.2009, 11:21:16
	 *
	 */
	public clsMemory(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
    }
    
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		String pre = clsBWProperties.addDot(poPrefix);
    	 
		moRepressedContentsStore = new clsRepressedContentsStore(pre+P_REPRESSEDCONTENTSSTORAGE, poProp);	
		moCurrentContextStorage  = new clsCurrentContextStorage(pre+P_CURRENTCONTEXT, poProp);
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
    	String pre = clsBWProperties.addDot(poPrefix);
    	
    	clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsRepressedContentsStore.getDefaultProperties(pre+P_REPRESSEDCONTENTSSTORAGE) );
		oProp.putAll(clsCurrentContextStorage.getDefaultProperties(pre+P_CURRENTCONTEXT) );		
		
		return oProp;
    }
    
    
    /**
     * DOCUMENT (zeilinger) - 
     * 
     * This method receives the type of an entity which is received by the agent's sensor. It forwards it to create
     * Context and gets a List of the context which is associated to the entity. Currently a group ID has to be 
     * forwarded to createContext - PSY_10. However this is hard coded now and should be change when we know 
     * hoe to deal with different agents - will they get the same default memory which changes during runtime
     * or do we need variable default parameters for every agent.  
     *
     * @author zeilinger
     * 08.10.2009, 21:16:48
     *
     * @param poEntityType
     * @return
     */
    public static ArrayList<clsAssociationContext<clsThingPresentationSingle>> getAssociatedContext(eEntityType poEntityType){
    	//FIXME: HZ - change PSY_10 to a generic variable or get rid of it. It depends on the use of group IDs within the 
    	//			  XML paths.
    	ArrayList<clsAssociationContext<clsThingPresentationSingle>> oAssociatedContextList = clsContextLoader.createContext(poEntityType.toString()); 
    	return oAssociatedContextList; 
    }
}
