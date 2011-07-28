/**
 * clsObjectSemanticsStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 17.10.2009, 20:29:25
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.loader.clsObjectSemanticsLoader;
import config.clsProperties;
import du.enums.eEntityType;

/**
 * 
 * @author langr
 * 17.10.2009, 20:29:25
 * 
 */
@Deprecated
public class clsObjectSemanticsStorage {
	
	public HashMap<eEntityType, clsPrimaryInformation> moObjectSemantics;
	public ArrayList<clsPrimaryInformation> moObjectSemanticsArray;
	
	public clsObjectSemanticsStorage(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moObjectSemantics = clsObjectSemanticsLoader.createSemanticsList("1", "PSY_10");
		
		moObjectSemanticsArray = new ArrayList<clsPrimaryInformation>();
		for(Map.Entry<eEntityType, clsPrimaryInformation> oEntry : moObjectSemantics.entrySet()) {
			moObjectSemanticsArray.add( oEntry.getValue() );
		}
    }
    
    
    private void applyProperties(String poPrefix, clsProperties poProp){		
//		String pre = clsProperties.addDot(poPrefix);
    	 
    	//moVariable = new clsClass(pre+P_KEY, poProp, null,this);		
	}	
    
    public static clsProperties getDefaultProperties(String poPrefix) {
//    	String pre = clsProperties.addDot(poPrefix);
    	
    	clsProperties oProp = new clsProperties();
		
		//oProp.putAll(clsOtherClass.getDefaultProperties(pre) );
		//oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_ANGLE, 1.99 * Math.PI );
		
		return oProp;
    }
	
}
