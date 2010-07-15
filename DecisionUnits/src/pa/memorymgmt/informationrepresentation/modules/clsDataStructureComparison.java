/**
 * clsDataStructureComparison.java: DecisionUnits - pa.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.lang.reflect.Method;
import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;
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
	public static ArrayList<clsPair<Double,clsDataStructurePA>> compareDataStructures(clsDataStructurePA poDataStructureSearchPattern, clsSearchSpaceBase poSearchSpace){
		String oClassName = "clsDataStructureComparison"; 
		String oMethodPrefix = "compare"; 
		Method method = null;
				
		ArrayList <clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>(); 
		eDataType eDataStructureType = poDataStructureSearchPattern.oDataStructureType; 
		method = getMethod(oClassName, oMethodPrefix, eDataStructureType); 
		
		//Proof if search Pattern is already part of the search space
		oMatchingDataStructureList = controlDataStructureExistence(poSearchSpace, poDataStructureSearchPattern);
		//In case search pattern is not known in search space => best matching elements are 
		//searched and returned. A filter mechanism is not defined up to now - HZ 02.07.2010
		if(oMatchingDataStructureList.size()<1){
			oMatchingDataStructureList = getMatchingDataStructures(poSearchSpace, poDataStructureSearchPattern, method);
			oMatchingDataStructureList = sortList(oMatchingDataStructureList); 
		} 
		return oMatchingDataStructureList; 
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
	private static Method getMethod(String poClassName, String poMethodPrefix,
			eDataType peDataStructureType) {
		
		Class<?> oC = null;
		//TODO HZ: Fix the representation of the try-catch statements 
		try {
			oC = Class.forName(poClassName);
		} catch (ClassNotFoundException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return oC.getDeclaredMethod (poMethodPrefix + peDataStructureType.name());
		} catch (SecurityException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new UnknownError("Unknown error occured in " + poClassName); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.07.2010, 20:10:24
	 *
	 * @param poSearchSpace
	 * @param poDataStructureSearchPattern
	 * @param matchingDataStructureList
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> controlDataStructureExistence(
			clsSearchSpaceBase poSearchSpace,
			clsDataStructurePA poDataStructureSearchPattern) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>> (); 
		// TODO (zeilinger) - Auto-generated method stub
		for(clsDataStructurePA oSearchSpaceElement:poSearchSpace.returnSearchSpaceTable(poDataStructureSearchPattern.oDataStructureType).keySet()){
			if(oSearchSpaceElement.oDataStructureID.equals(poDataStructureSearchPattern.oDataStructureID)) {
				/*If the ID of the data structure already occurs in the search space, it is a perfect match
				and will be returned
				FIXME HZ change the score Parameter*/ 
				oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(eDataStructureMatch.IDENTITYMATCH.getMatchFactor(), oSearchSpaceElement));
			}
		}
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
			clsDataStructurePA poDataStructureSearchPattern,
			Method poMethod) {
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		double rMatchScore = 0.0; 
		
		for(clsDataStructurePA oSearchSpaceElement:poSearchSpace.returnSearchSpaceTable(poDataStructureSearchPattern.oDataStructureType).keySet()){
			   rMatchScore = oSearchSpaceElement.compareTo(poDataStructureSearchPattern);//(Integer)poMethod.invoke(poDataStructureSearchPattern, oSearchSpaceElement);
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
			for(clsPair<Double, clsDataStructurePA> oElement : oSortedList){
				if(oElement.a <= oUnsortedElement.a || oElement == null){
					oSortedList.add(poMatchingDataStructureList.lastIndexOf(oElement), oUnsortedElement);
					break; 
				}
			}
		}
		return oSortedList;
	}
}
