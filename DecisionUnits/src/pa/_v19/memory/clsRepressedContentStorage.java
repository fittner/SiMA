/**
 * RepressedContentsStore.java: DecisionUnits - pa.memory
 * 
 * @author deutsch
 * 07.10.2009, 12:39:50
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pa._v19.datatypes.clsDriveContentCategories;
import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.loader.clsRepressedContentLoader;
import pa._v19.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v19.memorymgmt.datatypes.clsAssociation;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsThingPresentation;
import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsPair;
import pa._v19.tools.clsTripple;
import config.clsProperties;
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
	public ArrayList<clsDriveMesh> moRepressedContentCONVERTED;
	
	public clsRepressedContentStorage(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moRepressedContent = clsRepressedContentLoader.createRepressedList("1", "PSY_10");
		
		//HZ 16.08.2010: this method is defined in order to convert old data structures to new ones 
		convertRepressedContent(); 
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

		clsPrimaryInformation oRetVal = new clsPrimaryInformation();
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

	/**
	 *
	 * @author zeilinger
	 * 16.08.2010, 20:10:47
	 *
	 */
	private void convertRepressedContent() {
		moRepressedContentCONVERTED = new ArrayList<clsDriveMesh>(); 
		
		for(clsPrimaryInformation oEntry : moRepressedContent){
			for(Map.Entry<eContext, clsDriveContentCategories> oContentCat : oEntry.moTP.moDriveContentCategory.entrySet()){
				clsThingPresentation oTP = null; 
				clsDriveMesh oDM = null; 
								
				oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>(oContentCat.getKey().name(), oContentCat.getKey().name())); 
				oDM = clsDataStructureGenerator.generateDM(new clsTripple<String, ArrayList<clsThingPresentation>, Object>(oContentCat.getKey().toString(), 
																		   new ArrayList<clsThingPresentation>(Arrays.asList(oTP)),
																		   oEntry.moTP.moContent.toString()));
				oDM.setPleasure(oEntry.moAffect.getValue()); 
				oDM.setAnal(oContentCat.getValue().getAnal()); 
				oDM.setOral(oContentCat.getValue().getOral());
				oDM.setGenital(oContentCat.getValue().getGenital());
				oDM.setPhallic(oContentCat.getValue().getPhallic());
				moRepressedContentCONVERTED.add(oDM);
			}
		}
	}
	/**
	 *
	 * @author zeilinger
	 * 16.08.2010, 19:30:51
	 *
	 * @param oInput
	 * @return
	 */
	public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput) {
		clsThingPresentation oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("NULL", "NULL")); 
		clsTripple <String, ArrayList<clsThingPresentation>, Object> oContent = 
			new clsTripple<String, ArrayList<clsThingPresentation>, Object>("REPRESSED", new ArrayList<clsThingPresentation>(Arrays.asList(oTP)), "DEFAULT"); 
		
		clsDriveMesh oRetVal = (clsDriveMesh) clsDataStructureGenerator.generateDataStructure(eDataType.DM, oContent);
		double rHighestMatch = 0.0;

		for( clsDriveMesh oDMRepressedContent : moRepressedContentCONVERTED ) {
			for(clsAssociation oAssociation : poInput.getMoAssociatedDataStructures()){
				//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
				// do not have a data structure ID => in a later version when E16 will be placed in front 
				// of E15, the patterns already have an ID. 
				if(oAssociation instanceof clsAssociationDriveMesh){
					clsDriveMesh oData = ((clsAssociationDriveMesh)oAssociation).getDM(); 
					
					if(oDMRepressedContent.getMoContentType().equals(oData.getMoContentType())){
						double rMatchValue = oDMRepressedContent.matchCathegories(oData); 
							
						if(rMatchValue > rHighestMatch) {
								rHighestMatch = rMatchValue;
								oRetVal = oDMRepressedContent;
						}
					}
					if(rHighestMatch >= 1) { break;	} //do the doublebreak to abort search --> first come first serve
				}
				}
			}

		return oRetVal;
	}
}
