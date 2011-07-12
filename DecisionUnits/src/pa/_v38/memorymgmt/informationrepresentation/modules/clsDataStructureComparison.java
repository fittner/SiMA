/**
 * clsDataStructureComparison.java: DecisionUnits - pa._v38.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.informationrepresentation.enums.eDataStructureMatch;
import pa._v38.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 * 
 */
public abstract class clsDataStructureComparison {
	public static ArrayList<clsPair<Double,clsDataStructurePA>> compareDataStructures
									(clsDataStructurePA poDS_Unknown, clsSearchSpaceBase poSearchSpace){

		return getMatchingDataStructures(poSearchSpace, poDS_Unknown);
	}
	
	public static ArrayList<clsPair<Double,clsDataStructureContainer>> compareDataStructures
		(clsDataStructureContainer poDS_Unknown, clsSearchSpaceBase poSearchSpace){

		return getMatchingDataStructures(poSearchSpace, poDS_Unknown);
	}
		
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.07.2010, 20:04:08
	 *
	 * @param poSearchSpace
	 * @param poDataStructureSearchPattern
	 * @param matchingDataStructureList
	 * @param method 
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getMatchingDataStructures(
													clsSearchSpaceBase poSearchSpace,
													clsDataStructurePA poDS_Unknown) {
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double,clsDataStructurePA>>(); 
		HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poDS_Unknown.getMoDataStructureType());
		
		if(oMap.containsKey(poDS_Unknown.getMoContentType())){	//If the input content type already exists in the memory
			oRetVal = getDataStructureByContentType(oMap.get(poDS_Unknown.getMoContentType()), poDS_Unknown); 
		}
		else{
			oRetVal = getDataStructureByDataStructureType(oMap, poDS_Unknown); 
		}
		
		return oRetVal; 
	}
	
	/**
	 * Compare a whole container with the structures in the memeory, function overloading
	 *
	 * @since 08.07.2011 11:55:44
	 *
	 * @param poSearchSpace
	 * @param poDS_Unknown
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructureContainer>> getMatchingDataStructures(
			clsSearchSpaceBase poSearchSpace,
			clsDataStructureContainer poDS_Unknown) {
		
		ArrayList<clsPair<Double, clsDataStructureContainer>> oRetVal = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
		HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poDS_Unknown.getMoDataStructure().getMoDataStructureType());
		
		double rMatchScore = 0.0;
		
		//FIXME AW: Funktion hier
		/*for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poMap.entrySet()){
			clsDataStructurePA oCompareElement = oEntry.getValue().a; 
			rMatchScore = oCompareElement.compareTo(poDS_Unknown);
		
			if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
				int nInsert = sortList(oDS_List, rMatchScore); 
				oRetVal.add(nInsert, new clsPair<Double, clsDataStructureContainer>(rMatchScore, oCompareElement));
			}
		}*/
		
		
		return oRetVal;
		
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 14:59:49
	 *
	 * @param poMap
	 * @param poDataStructureUnknown
	 * @param poDataStructureContentType 
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByContentType(
			HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> poMap,
			clsDataStructurePA poDS_Unknown) {
		
			double rMatchScore = 0.0; 
			ArrayList<clsPair<Double, clsDataStructurePA>> oDS_List = new ArrayList<clsPair<Double, clsDataStructurePA>>();
			
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poMap.entrySet()){
					clsDataStructurePA oCompareElement = oEntry.getValue().a; 
					rMatchScore = oCompareElement.compareTo(poDS_Unknown);
				
					if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
						int nInsert = sortList(oDS_List, rMatchScore); 
						oDS_List.add(nInsert,new clsPair<Double, clsDataStructurePA>(rMatchScore, oCompareElement));
					}
				}
		
			return oDS_List;
	}
	
//	/**
//	 * DOCUMENT (zeilinger) - insert description
//	 *
//	 * @author zeilinger
//	 * 18.08.2010, 14:59:44
//	 *
//	 * @param poSearchSpace
//	 * @param poDataStructureUnknown
//	 * @return
//	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByDataStructureType(
			HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> poMap,
			clsDataStructurePA poDataStructureUnknown) {

		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();

		for(Map.Entry<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oTableEntry : poMap.entrySet()){
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oTableEntry.getValue().entrySet()){
					clsDataStructurePA oSearchSpaceElement = oEntry.getValue().a; 
					rMatchScore = oSearchSpaceElement.compareTo(poDataStructureUnknown);
	
					if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
						oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(rMatchScore, oSearchSpaceElement));
					}
			}
		}
				
		return oMatchingDataStructureList;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.07.2010, 16:36:54
	 * @param rMatchScore 
	 *
	 * @param matchingDataStructureList
	 * @return
	 */
	private static int sortList(ArrayList<clsPair<Double, clsDataStructurePA>> poDSList, double rMS) {
		int oRetVal = 0; 
		
		for(clsPair<Double, clsDataStructurePA> oEntry : poDSList){
				
				if(rMS > oEntry.a){
					oRetVal = poDSList.indexOf(oEntry); 
					break; 
				}
				else if (rMS == oEntry.a){
					oRetVal = poDSList.indexOf(oEntry); 
					break;
				}
				else {
					oRetVal = poDSList.size(); 
				}
		}
	
		return oRetVal;
	}
}
