/**
 * clsTemplatePlanStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 25.10.2009, 16:05:39
 */
package pa.memory;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsSecondaryInformation;
import pa.loader.plan.clsPlanAction;
import pa.loader.plan.clsPlanBaseMesh;
import pa.loader.plan.clsPlanLoader;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 16:05:39
 * 
 */
public class clsTemplatePlanStorage {

	public ArrayList<clsSecondaryInformation> moTemplatePlans;

	public clsTemplatePlanStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moTemplatePlans = clsPlanLoader.createTemplatePlanList("1", "PSY_10");
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
	public ArrayList<clsPlanAction> getReognitionUpdate(
			HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateImageResult) {

		ArrayList<clsPlanAction>  oRetVal = new ArrayList<clsPlanAction> ();
		
		for(clsSecondaryInformation oPlan : moTemplatePlans) {
			oRetVal.addAll(((clsPlanBaseMesh)oPlan).process(poTemplateImageResult) );
		}
		return oRetVal;
	}
}