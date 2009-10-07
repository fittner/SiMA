/**
 * clsMemory.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 11.08.2009, 11:17:10
 */
package pa.memory;

import pa.interfaces.itfPrimaryProcessAssociation;
import pa.interfaces.itfPrimaryProcessRetrieval;
import pa.interfaces.itfPrimaryProcessStorage;
import pa.interfaces.itfSecondaryProcessAssociation;
import pa.interfaces.itfSecondaryProcessRetrieval;
import pa.interfaces.itfSecondaryProcessStorage;
import config.clsBWProperties;

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
	
}
