/**
 * clsTemplateScenarioStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 25.10.2009, 13:26:03
 */
package pa.memory;

import java.util.ArrayList;

import config.clsBWProperties;

import pa.datatypes.clsSecondaryInformation;
import pa.loader.scenario.clsScenarioLoader;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 13:26:03
 * 
 */
public class clsTemplateScenarioStorage {

	public ArrayList<clsSecondaryInformation> moTemplateScenarios;

	public clsTemplateScenarioStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moTemplateScenarios = clsScenarioLoader.createTemplateScenarioList("1", "PSY_10");
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
