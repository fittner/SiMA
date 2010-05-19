/**
 * clsTemplateScenarioStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 25.10.2009, 13:26:03
 */
package pa.memory;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;

import pa.datatypes.clsSecondaryInformation;
import pa.loader.scenario.clsScenarioBaseMesh;
import pa.loader.scenario.clsScenarioLoader;
import pa.tools.clsPair;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 13:26:03
 * 
 */
@Deprecated
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


	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 15:13:40
	 *
	 * @param moTemplateImageResult
	 * @return
	 */
	public HashMap<String, clsPair<clsSecondaryInformation, Double>> getReognitionUpdate(
			HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateImageResult) {

		HashMap<String, clsPair<clsSecondaryInformation, Double>> oRetVal = new HashMap<String, clsPair<clsSecondaryInformation,Double>>();
		
		for(clsSecondaryInformation oScenario : moTemplateScenarios) {
			double rProgress = ((clsScenarioBaseMesh)oScenario).process(poTemplateImageResult);
			if(rProgress > 0) {
				oRetVal.put(oScenario.moWP.moContent.toString(), new clsPair<clsSecondaryInformation, Double>(oScenario, rProgress));
			}
		}
		return oRetVal;
	}
}
