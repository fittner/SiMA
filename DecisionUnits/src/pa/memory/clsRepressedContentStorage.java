/**
 * RepressedContentsStore.java: DecisionUnits - pa.memory
 * 
 * @author deutsch
 * 07.10.2009, 12:39:50
 */
package pa.memory;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.loader.clsRepressedContentLoader;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 12:39:50
 * 
 */
public class clsRepressedContentStorage {
	
	public ArrayList<clsPrimaryInformation> moRepressedContent;
	
	public clsRepressedContentStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moRepressedContent = clsRepressedContentLoader.createRepressedList("1", "PSY_10");
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
