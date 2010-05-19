/**
 * clsTemplateImageStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 24.10.2009, 12:47:19
 */
package pa.memory;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsSecondaryInformation;
import pa.loader.templateimage.clsTemplateImageLoader;
import pa.loader.templateimage.clsTemplateSecondaryInfo;
import pa.loader.templateimage.clsTemplateSecondaryMesh;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 24.10.2009, 12:47:19
 * 
 */
@Deprecated
public class clsTemplateImageStorage {
	
	//these PrimaryInfos implement the itfTemplateComparable!!!!
	public ArrayList<clsSecondaryInformation> moTemplateImages;
	
	public clsTemplateImageStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moTemplateImages = clsTemplateImageLoader.createTemplateImageList("1", "PSY_10");
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