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
import pa.datatypes.clsThingPresentationSingle;
import config.clsBWProperties;
import enums.pa.eContext;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 15:41:12
 * 
 */
public class clsCurrentContextStorage {
	private HashMap<clsThingPresentationSingle, clsMutableDouble> moStorage; 
	
	public clsCurrentContextStorage(String poPrefix, clsBWProperties poProp) {
		moStorage = new HashMap<clsThingPresentationSingle, clsMutableDouble>();
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
    
    public void setContextRatio(clsThingPresentationSingle poTPContext, double prRatio) {
    	if (0>prRatio || prRatio>1) {
    		throw new java.lang.IllegalArgumentException("0<="+prRatio+"<=1");
    	}
    	
    	if (!poTPContext.meContentType.equals(eContext.class.getName())) {
    		throw new java.lang.IllegalArgumentException("TP not of type "+eContext.class.getName());
    	}
    	
    	clsMutableDouble oValue = moStorage.get(poTPContext);
    	
    	if (oValue != null) {
    		oValue.set(prRatio);
    	} else {
    		oValue = new clsMutableDouble(prRatio);
    		moStorage.put(poTPContext, oValue);
    	}
    }
    
    public clsMutableDouble getContextRatio(clsThingPresentationSingle poTPContext) {
     	if (!poTPContext.meContentType.equals(eContext.class.getName())) {
    		throw new java.lang.IllegalArgumentException("TP not of type "+eContext.class.getName());
    	}    	
    	clsMutableDouble oResult = moStorage.get(poTPContext);
    	
    	if (oResult == null) {
    		oResult = new clsMutableDouble(0);
    	}
    	
    	return oResult;
    }
    
    public HashMap<clsThingPresentationSingle, clsMutableDouble> getContextRatios() {
    	return moStorage;
    }
    
    public HashMap<clsThingPresentationSingle, clsMutableDouble> getContextRatios(double prThreshold) {
    	if (prThreshold == 0) {
    		return getContextRatios();
    	} else {
    		HashMap<clsThingPresentationSingle, clsMutableDouble> oResult = new HashMap<clsThingPresentationSingle, clsMutableDouble>();
        	for (Map.Entry<clsThingPresentationSingle, clsMutableDouble> oEntry:moStorage.entrySet()) {
        		if (oEntry.getValue().doubleValue()>=prThreshold) {
        			oResult.put(oEntry.getKey(), oEntry.getValue());
        		} 
        	}
        	return oResult;
    	}
    }
    
    public void resetAllRatios() {
    	for (Map.Entry<clsThingPresentationSingle, clsMutableDouble> oEntry:moStorage.entrySet()) {
    		oEntry.getValue().set(0);
    	}
    }
}
