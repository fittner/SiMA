/**
 * RepressedContentsStore.java: DecisionUnits - pa.memory
 * 
 * @author deutsch
 * 07.10.2009, 12:39:50
 */
package pa.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa.datatypes.clsDriveContentCategories;
import pa.datatypes.clsPrimaryInformation;
import pa.loader.clsRepressedContentLoader;
import config.clsBWProperties;
import du.enums.pa.eContext;

/**
 * 
 * 
 * @author deutsch
 * 07.10.2009, 12:39:50
 * 
 */
@Deprecated
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

	/**
	 * searches for the entry that matches best to the DriveContent distribution
	 *
	 * @author langr
	 * 18.10.2009, 01:42:27
	 *
	 * @param input
	 * @return
	 */
	public clsPrimaryInformation getBestMatch(
			HashMap<eContext, clsDriveContentCategories> poDrvContent) {

		clsPrimaryInformation oRetVal = null;
		double rHighestMatch = 0;

		for( clsPrimaryInformation oRep : moRepressedContent ) {
			for( Map.Entry<eContext, clsDriveContentCategories> oDrvContCat : poDrvContent.entrySet() ) {
				
				clsDriveContentCategories oCatRep = oRep.moTP.moDriveContentCategory.get(oDrvContCat.getKey());
				if(oCatRep != null) { //in case, there is an entry
					double match = oCatRep.match( oDrvContCat.getValue() );
					
					if(match > rHighestMatch) {
						rHighestMatch = match;
						oRetVal = oRep;
					}
					if(rHighestMatch >= 1) { break;	} //do the doublebreak to abort search --> first come first serve
				}
			}
			if(rHighestMatch >= 1) { break;	}
		}
		return oRetVal;
	}
	
}
