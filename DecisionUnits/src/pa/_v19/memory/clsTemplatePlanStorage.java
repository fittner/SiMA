/**
 * clsTemplatePlanStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 25.10.2009, 16:05:39
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.loader.plan.clsPlanAction;
import pa._v19.loader.plan.clsPlanBaseMesh;
import pa._v19.loader.plan.clsPlanLoader;
import pa._v19.tools.clsPair;
import config.clsProperties;

/**
 * 
 * @author langr
 * 25.10.2009, 16:05:39
 * 
 */
@Deprecated
public class clsTemplatePlanStorage {

	public ArrayList<clsSecondaryInformation> moTemplatePlans;

	public clsTemplatePlanStorage(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moTemplatePlans = clsPlanLoader.createTemplatePlanList("1", "PSY_10");
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


	/**
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