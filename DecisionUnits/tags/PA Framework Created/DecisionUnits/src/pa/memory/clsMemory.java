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
    	 
    	//moVariable = new clsClass(pre+P_KEY, poProp, null,this);		
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
    	String pre = clsBWProperties.addDot(poPrefix);
    	
    	clsBWProperties oProp = new clsBWProperties();
		
		//oProp.putAll(clsOtherClass.getDefaultProperties(pre) );
		//oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_ANGLE, 1.99 * Math.PI );
		
		return oProp;
		
    }
	
}
