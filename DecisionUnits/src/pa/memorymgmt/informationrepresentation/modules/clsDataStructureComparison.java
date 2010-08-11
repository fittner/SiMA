/**
 * clsDataStructureComparison.java: DecisionUnits - pa.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;

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
		
		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();
				
		for(clsDataStructurePA oSearchSpaceElement : poSearchSpace.returnSearchSpaceTable(poDataStructureUnknown.moDataStructureType).keySet()){
			//System.out.println(" Unknown data structure " + poDataStructureUnknown.toString() + " \n search space element" + oSearchSpaceElement.toString());
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
	 * 02.07.2010, 16:36:54
	 *
	 * @param matchingDataStructureList
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> sortList(ArrayList<clsPair<Double, clsDataStructurePA>> poMatchingDataStructureList) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oSortedList = new ArrayList<clsPair<Double, clsDataStructurePA>>(); 
		
		for(clsPair<Double, clsDataStructurePA> oUnsortedElement : poMatchingDataStructureList ){
			if(oSortedList.size()==0){oSortedList.add(oUnsortedElement);}
			else{
				for(clsPair<Double, clsDataStructurePA> oElement : oSortedList){
					if(oElement.a <= oUnsortedElement.a){
						oSortedList.add(poMatchingDataStructureList.lastIndexOf(oElement), oUnsortedElement);
						break; 
					}
				}
				if(!oSortedList.contains(oUnsortedElement)){oSortedList.add(oUnsortedElement);} 
			}
		}
		return oSortedList;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 12.07.2010, 21:23:09
	 *
	 * @param oClassName
	 * @param oMethodPrefix
	 * @param eDataStructureType
	 * @return
	 */
//	not required actually - private static Method getMethod(String poClassName, String poMethodPrefix,
//			eDataType peDataStructureType) {
//		
//		Class<?> oC = null;
//		//TODO HZ: Fix the representation of the try-catch statements 
//		try {
//			oC = Class.forName(poClassName);
//		} catch (ClassNotFoundException e) {
//			// TODO (zeilinger) - Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			return oC.getDeclaredMethod (poMethodPrefix + peDataStructureType.name());
//		} catch (SecurityException e) {
//			// TODO (zeilinger) - Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO (zeilinger) - Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		throw new UnknownError("Unknown error occured in " + poClassName); 
//	}
}
