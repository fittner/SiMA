/**
 * clsDataStructureComparison.java: DecisionUnits - pa.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.informationrepresentation.enums.eDataStructureMatch;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 * 
 */
public abstract class clsDataStructureComparison {
	public static ArrayList<clsPair<Double,clsDataStructurePA>> compareDataStructures
									(clsDataStructurePA poDataStructureUnknown, clsSearchSpaceBase poSearchSpace){
				
		ArrayList <clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList <clsPair<Double, clsDataStructurePA>>(); 

		oMatchingDataStructureList = getMatchingDataStructures(poSearchSpace, poDataStructureUnknown);
		oMatchingDataStructureList = sortList(oMatchingDataStructureList);

		return oMatchingDataStructureList; 
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
			clsDataStructurePA poDataStructureUnknown) {
		
		String oContentType = poDataStructureUnknown.moContentType.toUpperCase(); 
		
		if(poSearchSpace.returnSearchSpaceTable().get(poDataStructureUnknown.moDataStructureType).containsKey(oContentType)){
			return getDataStructureByContentType(poSearchSpace, poDataStructureUnknown, poDataStructureUnknown.moContentType.toUpperCase()); 
		}
		else {
			return getDataStructureByDataStructureType (poSearchSpace, poDataStructureUnknown);
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 14:59:49
	 *
	 * @param poSearchSpace
	 * @param poDataStructureUnknown
	 * @param poDataStructureContentType 
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByContentType(
			clsSearchSpaceBase poSearchSpace,
			clsDataStructurePA poDataStructureUnknown, 
			String poDataStructureContentType) {
		
		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poSearchSpace.returnSearchSpaceTable()
													 .get(poDataStructureUnknown.moDataStructureType)
													 .get(poDataStructureContentType)
													 .entrySet()){
			
				clsDataStructurePA oSearchSpaceElement = oEntry.getValue().a; 
				rMatchScore = oSearchSpaceElement.compareTo(poDataStructureUnknown);
			
				if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
					oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(rMatchScore, oSearchSpaceElement));
				}
			}
		return oMatchingDataStructureList;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 14:59:44
	 *
	 * @param poSearchSpace
	 * @param poDataStructureUnknown
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByDataStructureType(
			clsSearchSpaceBase poSearchSpace,
			clsDataStructurePA poDataStructureUnknown) {

		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();

		for(Map.Entry<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oTableEntry : poSearchSpace
																						.returnSearchSpaceTable()
																						.get(poDataStructureUnknown.moDataStructureType)
																						.entrySet()){
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
	 *
	 * @param matchingDataStructureList
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> sortList(ArrayList<clsPair<Double, clsDataStructurePA>> poMatchingDataStructureList) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oSortedList = new ArrayList<clsPair<Double, clsDataStructurePA>>(); 
		
		for(clsPair<Double, clsDataStructurePA> oUnsortedElement : poMatchingDataStructureList ){
			if(oSortedList.size() == 0){ oSortedList.add(oUnsortedElement); }
			else{
				for(clsPair<Double, clsDataStructurePA> oElement : oSortedList){
					if(oElement.a <= oUnsortedElement.a){
						oSortedList.add(oSortedList.lastIndexOf(oElement), oUnsortedElement);
						break; 
					}
				}
//			 if(!oSortedList.contains(oUnsortedElement)){
//					oSortedList.add(oUnsortedElement);
//			 } 
			}
		}
		return oSortedList;
	}
}
