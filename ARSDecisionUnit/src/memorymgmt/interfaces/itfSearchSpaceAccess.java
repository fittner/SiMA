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

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.02.2013, 14:36:43
 * 
 */
public interface itfSearchSpaceAccess {
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> searchEntity(ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternList);
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int pnLevel);
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
	public clsDataStructurePA getCompleteMesh(clsDataStructurePA poInput, int pnLevel);
	public void complementMesh(clsDataStructurePA poInput, int pnLevel);
}
