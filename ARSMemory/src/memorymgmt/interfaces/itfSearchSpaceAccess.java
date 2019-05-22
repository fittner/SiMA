/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package memorymgmt.interfaces;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.02.2013, 14:36:43
 * 
 */
public interface itfSearchSpaceAccess {
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 31.01.2014 12:00:29
	 *
	 * @param poSearchPatternList
	 * @return
	 */
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> searchEntity(ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternList);
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 31.01.2014 12:00:29
	 *
	 * @param poSearchPatternList
	 * @return
	 */
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> searchEntityWrite(ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternList, double weight, double learning);
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 31.01.2014 12:00:32
	 *
	 * @param poSearchPattern
	 * @param prThreshold
	 * @param pnLevel
	 * @return
	 */
	
	//delacruz: return clsTriple and not clsPair
	//public ArrayList<clsTriple<Double, Double, clsDataStructurePA>> searchMesh(clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int pnLevel);
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int pnLevel);
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 31.01.2014 12:00:34
	 *
	 * @param poPattern
	 * @param poSearchContentType
	 * @param prThreshold
	 * @param pnLevel
	 * @return
	 */
	//delacruz: return clsTriple and not clsPair
	
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
	//public ArrayList<clsTriple<Double,Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 31.01.2014 12:00:37
	 *
	 * @param poInput
	 * @param pnLevel
	 * @return
	 */
	public clsDataStructurePA getCompleteMesh(clsDataStructurePA poInput, int pnLevel);
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 31.01.2014 12:00:40
	 *
	 * @param poInput
	 * @param pnLevel
	 */
	public void complementMesh(clsDataStructurePA poInput, int pnLevel);
}
