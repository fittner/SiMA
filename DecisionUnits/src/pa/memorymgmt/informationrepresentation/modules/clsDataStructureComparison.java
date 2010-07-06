/**
 * clsDataStructureComparison.java: DecisionUnits - pa.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;
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
		Method method = null;
		Class<?> oC = null;
		
		ArrayList <clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>(); 
		eDataType eDataStructureType = poDataStructureSearchPattern.oDataStructureType; 
		
		//TODO HZ: Fix the representation of the try-catch statements 
		try {
			oC = Class.forName("clsDataStructureComparison");
		} catch (ClassNotFoundException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		try {
			method = oC.getDeclaredMethod ("compare" + eDataStructureType.name());
		} catch (SecurityException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		//Proof if search Pattern is already part of the search space
		if(controlDataStructureExistence(poSearchSpace, poDataStructureSearchPattern, oMatchingDataStructureList)){ return oMatchingDataStructureList;} 
		//In case search pattern is not known in search space => best matching elements are 
		//searched and returned. A filter mechanism is not defined up to now - HZ 02.07.2010
		getMatchingDataStructures(poSearchSpace, poDataStructureSearchPattern, oMatchingDataStructureList, method); 
		
		return sortList(oMatchingDataStructureList); 
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
	private static boolean controlDataStructureExistence(
			clsSearchSpaceBase poSearchSpace,
			clsDataStructurePA poDataStructureSearchPattern,
			ArrayList<clsPair<Double, clsDataStructurePA>> poMatchingDataStructureList) {
		// TODO (zeilinger) - Auto-generated method stub
		for(clsDataStructurePA oSearchSpaceElement:poSearchSpace.returnSearchSpaceTable(poDataStructureSearchPattern.oDataStructureType).keySet()){
			if(oSearchSpaceElement.oDataStructureID.equals(poDataStructureSearchPattern)) {
				/*If the ID of the data structure already occurs in the search space, it is a 100% match
				/and will be returned*/ 
				poMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(1.0, oSearchSpaceElement));
				return true; 
			}
		}
		return false; 
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
	private static void getMatchingDataStructures(
			clsSearchSpaceBase poSearchSpace,
			clsDataStructurePA poDataStructureSearchPattern,
			ArrayList<clsPair<Double, clsDataStructurePA>> poMatchingDataStructureList, 
			Method poMethod) {

		for(clsDataStructurePA oSearchSpaceElement:poSearchSpace.returnSearchSpaceTable(poDataStructureSearchPattern.oDataStructureType).keySet()){
			try {
				    clsDataStructurePA oMatchingDataStructure = (clsDataStructurePA)poMethod.invoke(poDataStructureSearchPattern, oSearchSpaceElement);
					poMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(0.0, oMatchingDataStructure));
				}
			catch (IllegalArgumentException e) {
				// TODO (zeilinger) - Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO (zeilinger) - Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO (zeilinger) - Auto-generated catch block
				e.printStackTrace();
			} 
		}
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

	static clsPair<Double, clsDataStructurePA> compareTPM(clsDataStructurePA poDataStructureSearchPattern,clsDataStructurePA poSearchSpaceTable){
		//Hier der Vergleich 		
		return null; 
	}
	
	static clsDataStructurePA compareTP(){
		//Hier der Vergleich
		return null; 
	}
	
	static clsDataStructurePA compareTI(){
		//Hier der Vergleich
		return null; 
	}
}
