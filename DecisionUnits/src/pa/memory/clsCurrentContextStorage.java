/**
 * clsCurrentContextStorage.java: DecisionUnits - pa.memory
 * 
 * @author deutsch
 * 07.10.2009, 15:41:12
 */
package pa.memory;

import java.util.HashMap;
import java.util.Map;
import bfg.tools.clsMutableDouble;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsThingPresentationSingle;
import pa.datatypes.clsWordPresentation;
import config.clsBWProperties;
import enums.pa.eContext;

/**
 * As part of the clsMemory, this class contains the currently predominant context (always a secondary information).
 * However, it is distinguished between two types of access: primary-, and secondary process access 
 * (realized with the implemented interfaces).
 * 
 * @author deutsch
 * 07.10.2009, 15:41:12
 * 
 */
public class clsCurrentContextStorage implements itfContextAccessPrimary, itfContentAccessSecondary{
	private HashMap<clsSecondaryInformation, clsMutableDouble> moStorage; 
	
	//helper to identify secondary information by either TP or WP
	private HashMap<clsThingPresentationSingle, clsSecondaryInformation> moStorageHelperPrim;
	private HashMap<clsWordPresentation, clsSecondaryInformation> moStorageHelperSec;
	
	private void putValues(clsSecondaryInformation poSecInfo,
			clsMutableDouble oValue) {
		moStorage.put(poSecInfo, oValue);
		moStorageHelperPrim.put(poSecInfo.moTP, poSecInfo);
		moStorageHelperSec.put(poSecInfo.moWP, poSecInfo);
	}
	
	public clsCurrentContextStorage(String poPrefix, clsBWProperties poProp) {
		moStorage = new HashMap<clsSecondaryInformation, clsMutableDouble>();
		applyProperties(poPrefix, poProp);
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
    
    public void setContextRatio(clsSecondaryInformation poSecInfo, double prRatio) {
    	if (0>prRatio || prRatio>1) {
    		throw new java.lang.IllegalArgumentException("0<="+prRatio+"<=1");
    	}
    	
    	if (!poSecInfo.moTP.meContentType.equals(eContext.class.getName())) {
    		throw new java.lang.IllegalArgumentException("TP not of type "+eContext.class.getName());
    	}
    	
    	clsMutableDouble oValue = moStorage.get(poSecInfo);
    	
    	if (oValue != null) {
    		oValue.set(prRatio);
    	} else {
    		oValue = new clsMutableDouble(prRatio);
    		putValues(poSecInfo, oValue);
    	}
    }

    public clsMutableDouble getContextRatioPrim(clsThingPresentationSingle poTPContext) {
     	if (!poTPContext.meContentType.equals(eContext.class.getName())) {
    		throw new java.lang.IllegalArgumentException("TP not of type "+eContext.class.getName());
    	}    	
    	clsMutableDouble oResult = moStorage.get(moStorageHelperPrim.get(poTPContext));
    	
    	if (oResult == null) {
    		oResult = new clsMutableDouble(0);
    	}
    	
    	return oResult;
    }
    
    public HashMap<clsSecondaryInformation, clsMutableDouble> getContextRatios() {
    	return moStorage;
    }
    
    /**
     * getContextRatios ABOVE a certain Threashold
     *
     * @author langr
     * 17.10.2009, 10:22:34
     *
     * @param prThreshold - request for values ABOVE the threshold
     * @return every context-representing thing presentation above the given threshold
     */
    public HashMap<clsSecondaryInformation, clsMutableDouble> getContextRatiosSec(double prThreshold) {
    	if (prThreshold == 0) {
    		return getContextRatios();
    	} else {
    		HashMap<clsSecondaryInformation, clsMutableDouble> oResult = new HashMap<clsSecondaryInformation, clsMutableDouble>();
        	for (Map.Entry<clsSecondaryInformation, clsMutableDouble> oEntry:moStorage.entrySet()) {
        		if (oEntry.getValue().doubleValue()>=prThreshold) {
        			oResult.put(oEntry.getKey(), oEntry.getValue());
        		} 
        	}
        	return oResult;
    	}
    }
    
    public HashMap<clsPrimaryInformation, clsMutableDouble> getContextRatiosPrim(double prThreshold) {
    
    	HashMap<clsPrimaryInformation, clsMutableDouble> oRetVal = new HashMap<clsPrimaryInformation, clsMutableDouble>();
    	
    	HashMap<clsSecondaryInformation, clsMutableDouble> oSecMap = getContextRatiosSec(prThreshold);
    	for(Map.Entry<clsSecondaryInformation, clsMutableDouble> oSecMapEntry : oSecMap.entrySet()) {
    		oRetVal.put((clsPrimaryInformation)oSecMapEntry.getKey(), oSecMapEntry.getValue());
    	}
    	return oRetVal;
    }
    
    public void resetAllRatios() {
    	for (Map.Entry<clsSecondaryInformation, clsMutableDouble> oEntry:moStorage.entrySet()) {
    		oEntry.getValue().set(0);
    	}
    }
}
