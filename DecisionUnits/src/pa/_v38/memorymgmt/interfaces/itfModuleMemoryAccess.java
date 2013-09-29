/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.interfaces;

import java.util.ArrayList;

import datatypes.helpstructures.clsPair;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.02.2013, 14:35:51
 * 
 */
public interface itfModuleMemoryAccess {
	/**
	 * Search for a general single entity or data structure
	 * 
	 * (wendt)
	 *
	 * @since 26.02.2013 11:13:47
	 *
	 * @param poDataType
	 * @param poPattern
	 * @return
	 */
	public <E> ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntity (eDataType poDataType, ArrayList<E> poPattern);
	/**
	 * From the input data like, content, shape and color, the matching TPM is loaded from the memory
	 * 
	 * (wendt)
	 *
	 * @since 26.02.2013 11:24:24
	 *
	 * @param poContent
	 * @param poShape
	 * @param poColor
	 * @return
	 */
	public clsThingPresentationMesh searchExactEntityFromInternalAttributes(String poContent, String poShape, String poColor);
	/**
	 * Search for images according to the input pattern
	 * Usage: Fill libido discharge and repressed content
	 * 
	 * (wendt)
	 *
	 * @since 26.02.2013 11:18:27
	 *
	 * @param poPattern
	 * @param poSearchContentType
	 * @param prThreshold
	 * @param pnLevel
	 * @return
	 */
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
	/**
	 * Search a whole mesh, i. e. for an input image, search for the associated data structures for this mesh
	 * 
	 * (wendt)
	 *
	 * @since 26.02.2013 11:20:13
	 *
	 * @param poInput
	 * @param pnLevel
	 * @return
	 */
	public clsDataStructurePA searchCompleteMesh(clsDataStructurePA poInput, int pnLevel);
	/**
	 * Execute psychic spread activation for an image of the primary process
	 * 
	 * (wendt)
	 *
	 * @since 26.02.2013 11:21:05
	 *
	 * @param poInput
	 * @param prPsychicEnergyIn
	 */
	public void executePsychicSpreadActivation(clsThingPresentationMesh poInput, ArrayList<clsDriveMesh> poDriveMeshFilterList, double prPsychicEnergyIn);
	/**
	 * Get the secondary process data structure from a primary process data structure
	 * 
	 * (wendt)
	 *
	 * @since 26.02.2013 11:21:40
	 *
	 * @param poDataStructure
	 * @param prThreshold
	 * @return
	 */
	public clsAssociationWordPresentation getSecondaryDataStructure(clsPrimaryDataStructure poDataStructure, double prThreshold);
}
