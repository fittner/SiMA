/**
 * clsObjectSemanticsStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 17.10.2009, 20:29:25
 */
package pa.memory;

import java.util.HashMap;

import pa.datatypes.clsPrimaryInformation;
import pa.loader.clsObjectSemanticsLoader;
import config.clsBWProperties;
import enums.eEntityType;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 20:29:25
 * 
 */
public class clsObjectSemanticsStorage {
	
	public HashMap<eEntityType, clsPrimaryInformation> moRepressedContent;
	
	public clsObjectSemanticsStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moRepressedContent = clsObjectSemanticsLoader.createSemanticsList("1", "PSY_10");
    }
    
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
//		String pre = clsBWProperties.addDot(poPrefix);
    	 
    	//moVariable = new clsClass(pre+P_KEY, poProp, null,this);		
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
//    	String pre = clsBWProperties.addDot(poPrefix);
    	
    	clsBWProperties oProp = new clsBWProperties();
		
		//oProp.putAll(clsOtherClass.getDefaultProperties(pre) );
		//oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_ANGLE, 1.99 * Math.PI );
		
		return oProp;
    }
	
}
