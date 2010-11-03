/**
 * RepressedContentsStore.java: DecisionUnits - pa.memory
 * 
 * @author deutsch
 * 07.10.2009, 12:39:50
 */
package pa.memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pa.datatypes.clsDriveContentCategories;
import pa.datatypes.clsPrimaryInformation;
import pa.loader.clsRepressedContentLoader;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;
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
	public ArrayList<clsDriveMesh> moRepressedContentCONVERTED;
	
	public clsRepressedContentStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moRepressedContent = clsRepressedContentLoader.createRepressedList("1", "PSY_10");
		
		//HZ 16.08.2010: this method is defined in order to convert old data structures to new ones 
		convertRepressedContent(); 
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
	 * DOCUMENT (zeilinger) - insert description
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
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 19:30:51
	 *
	 * @param oInput
	 * @return
	 */
	public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput) {
		clsThingPresentation oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("NULL", "NULL")); 
		clsTripple <String, ArrayList<clsThingPresentation>, Object> oContent = new clsTripple<String, ArrayList<clsThingPresentation>, Object>("REPRESSED", new ArrayList<clsThingPresentation>(Arrays.asList(oTP)), "DEFAULT"); 
		
		clsDriveMesh oRetVal = (clsDriveMesh) clsDataStructureGenerator.generateDataStructure(eDataType.DM, oContent);
		double rHighestMatch = 0.0;

		for( clsDriveMesh oDMRepressedContent : moRepressedContentCONVERTED ) {
			for(clsAssociation oAssociation : poInput.moAssociatedDataStructures){
				//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
				// do not have a data structure ID => in a later version when E16 will be placed in front 
				// of E15, the patterns already have an ID. 
				clsDriveMesh oDMInput = ((clsAssociationDriveMesh)oAssociation).getDM(); 
				
				if(oDMRepressedContent.moContentType.equals(oDMInput.moContentType)){
					double rMatchValue = oDMRepressedContent.matchCathegories(oDMInput); 
						
					if(rMatchValue > rHighestMatch) {
							rHighestMatch = rMatchValue;
							oRetVal = oDMRepressedContent;
					}
				}
					if(rHighestMatch >= 1) { break;	} //do the doublebreak to abort search --> first come first serve
				}
			}

		return oRetVal;
	}
}
