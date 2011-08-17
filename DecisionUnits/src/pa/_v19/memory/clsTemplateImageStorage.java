/**
 * clsTemplateImageStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 24.10.2009, 12:47:19
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.loader.templateimage.clsTemplateImageLoader;
import pa._v19.loader.templateimage.clsTemplateSecondaryInfo;
import pa._v19.loader.templateimage.clsTemplateSecondaryMesh;
import pa._v19.tools.clsPair;
import config.clsProperties;

/**
 * 
 * @author langr
 * 24.10.2009, 12:47:19
 * 
 */
@Deprecated
public class clsTemplateImageStorage {
	
	//these PrimaryInfos implement the itfTemplateComparable!!!!
	public ArrayList<clsSecondaryInformation> moTemplateImages;
	
	public clsTemplateImageStorage(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moTemplateImages = clsTemplateImageLoader.createTemplateImageList("1", "PSY_10");
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
	 * 25.10.2009, 00:11:33
	 *
	 * @param completePerception
	 */
	public HashMap<String, clsPair<clsSecondaryInformation, Double>> compare(ArrayList<clsSecondaryInformation> poCompletePerception) {

		HashMap<String, clsPair<clsSecondaryInformation, Double>> oRetVal = new HashMap<String, clsPair<clsSecondaryInformation, Double>>();
	
		 for(clsSecondaryInformation oTempImage : moTemplateImages) {
			 double match = 0.0;
			 if (oTempImage instanceof clsTemplateSecondaryMesh) {
				 match = ((clsTemplateSecondaryMesh)oTempImage).compareTo(poCompletePerception);
			 } else if(oTempImage instanceof clsTemplateSecondaryInfo) {
				 match = ((clsTemplateSecondaryInfo)oTempImage).compareTo(poCompletePerception);
			 }
			 oRetVal.put(oTempImage.moWP.moContent.toString(), new clsPair<clsSecondaryInformation, Double>(oTempImage,match)); 
		 }
		 
		 return oRetVal;
	}
}