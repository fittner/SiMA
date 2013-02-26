/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.02.2013, 14:35:51
 * 
 */
public interface itfModuleMemoryAccess {
	public <E> ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntity (eDataType poDataType, ArrayList<E> poPattern);
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
	public void executePsychicSpreadActivation(clsThingPresentationMesh poInput, double prPsychicEnergyIn);
	public clsDataStructurePA searchCompleteMesh(clsDataStructurePA poInput, int pnLevel);
	public clsAssociationWordPresentation getSecondaryDataStructure(clsPrimaryDataStructure poDataStructure, double prThreshold);
}
