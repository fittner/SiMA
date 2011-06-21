/**
 * clsCurrentContextStorage.java: DecisionUnits - pa.memory
 * 
 * @author deutsch
 * 07.10.2009, 15:41:12
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import bfg.tools.clsMutableDouble;
import pa._v19.datatypes.clsAffectMemory;
import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsThingPresentationSingle;
import pa._v19.datatypes.clsWordPresentation;
import pa._v19.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsThingPresentation;
import pa._v19.tools.clsPair;
import pa._v19.tools.clsTripple;
import config.clsBWProperties;
import du.enums.pa.eContext;

/**
 * As part of the clsMemory, this class contains the currently predominant context (always a secondary information).
 * However, it is distinguished between two types of access: primary-, and secondary process access 
 * (realized with the implemented interfaces).
 * 
 * @author deutsch
 * 07.10.2009, 15:41:12
 * 
 */
@Deprecated
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
		moStorageHelperPrim = new HashMap<clsThingPresentationSingle, clsSecondaryInformation>();
		moStorageHelperSec = new HashMap<clsWordPresentation, clsSecondaryInformation>();
		applyProperties(poPrefix, poProp);
		
		testInit();
    }
    
    /**
	 *
	 * @author langr
	 * 18.10.2009, 13:38:50
	 *
	 */
	private void testInit() {
		
		clsSecondaryInformation oSecInfo = new clsSecondaryInformation(
				new clsWordPresentation(), 
				new clsThingPresentationSingle("Context", eContext.class.getName(), eContext.NOURISH),
				new clsAffectMemory(0.8) );
		
		this.setContextRatio(oSecInfo, 1.0);
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

    @Override
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

	/**
	 *
	 * @author zeilinger
	 * 16.08.2010, 15:12:45
	 *
	 * @param mrContextSensitivity
	 * @return
	 */
	public HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> getContextRatiosPrimCONVERTED(double prThreshold) {
		HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> oRetVal = null;
		oRetVal = convertPrimInfToPrimCont(this.getContextRatiosPrim(prThreshold)); 
    	return oRetVal;
    }

	/**
	 *
	 * @author zeilinger
	 * 16.08.2010, 15:19:25
	 *
	 * @param contextRatiosPrim
	 * @return
	 */
	private HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> convertPrimInfToPrimCont(HashMap<clsPrimaryInformation, clsMutableDouble> contextRatiosPrim) {
		HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> oRetVal = new HashMap<clsPrimaryDataStructureContainer, clsMutableDouble>();
		
		for(clsPrimaryInformation oKey: contextRatiosPrim.keySet()){
			if(oKey.moAffect != null && oKey.moTP != null){
				ArrayList<clsThingPresentation> oAssociationsDM = new ArrayList<clsThingPresentation>(); 
				String oContentTypeDM = oKey.moTP.moContent.toString();
				clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>(oKey.moTP.moContent.toString(), oKey.moTP.moContent.toString())); 
				oAssociationsDM.add(oTP); 
				
				clsDriveMesh oDriveMesh = clsDataStructureGenerator.generateDM(
								new clsTripple<String, ArrayList<clsThingPresentation>, Object>(oContentTypeDM, oAssociationsDM, oContentTypeDM));
				oDriveMesh.setPleasure(oKey.moAffect.getValue());
    			oRetVal.put(new clsPrimaryDataStructureContainer(oDriveMesh, null), contextRatiosPrim.get(oKey)); 
			}
		}
		
		return oRetVal;
	}
}
