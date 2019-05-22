/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package memorymgmt.interfaces;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
//delacruz: add clsTriple data structure
//import base.datatypes.helpstructures.clsTriple;

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
	public void writeQoA(clsDriveMesh DM);

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
	//delacruz: change type to clsTriple
	//public ArrayList<clsTriple<Double, Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
	public ArrayList<clsPair<Double,clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel);
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
	public void executePsychicSpreadActivation(clsThingPresentationMesh poInput, ArrayList<clsDriveMesh> poDriveMeshFilterList, double prPsychicEnergyIn, int maxNumberOfDirectActivations, boolean useDirectActivation, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> preferredImages);
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
    /**
     * DOCUMENT - insert description
     *
     * @author noName
     * @since 01.08.2018 11:21:55
     *
     * @param poDataType
     * @param poPattern
     * @param weight
     * @return
     */
	public <E> ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntityWrite(eDataType poDataType, ArrayList<E> poPattern, double weight, double learning);

	public int getID();
}
