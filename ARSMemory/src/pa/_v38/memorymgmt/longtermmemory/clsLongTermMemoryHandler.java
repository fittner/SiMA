/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.longtermmemory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.interfaces.itfSearchSpaceAccess;

import org.apache.log4j.Logger;

import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAffect;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.tools.clsDebugTools;
import pa._v38.memorymgmt.framessearchspace.clsOntologyLoader;
import pa._v38.memorymgmt.longtermmemory.psychicspreadactivation.PsychicSpreadingActivation;
import pa._v38.memorymgmt.longtermmemory.psychicspreadactivation.PsychicSpreadingActivationInterface;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.datamanipulation.meshprocessor.MeshProcessor;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.02.2013, 14:40:42
 * 
 */
public class clsLongTermMemoryHandler implements itfModuleMemoryAccess {
	
	private itfSearchSpaceAccess moSearchSpaceMethods;
	private PsychicSpreadingActivationInterface moSpreadActivationHandler;
	
	private Logger log = Logger.getLogger("pa._v38.memorymgmt");
	
	private static final double moConsumeValue = 0.2;
	private static final double mrActivationThreshold = 0.1;
	//private static final int mnMaximumDirectActivationValue = 20;
	
	public clsLongTermMemoryHandler(itfSearchSpaceAccess poSearchSpaceMethods) {
		moSearchSpaceMethods = poSearchSpaceMethods;
		moSpreadActivationHandler = new PsychicSpreadingActivation(moSearchSpaceMethods, moConsumeValue, mrActivationThreshold);
		log.info("Initialize " + this.getClass().getName());
	}
	
	

	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 14:59:22
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#search(pa._v38.memorymgmt.enums.eDataType, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public <E> ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntity(eDataType poDataType, ArrayList<E> poPattern) {
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		createSearchPattern(poDataType, poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult = moSearchSpaceMethods.searchEntity(oSearchPattern);
		
		return poSearchResult;
		//accessKnowledgeBase(poSearchResult, oSearchPattern); 
		//log.debug("Search for pattern: " + oSearchPattern.toString() + ". Result: " + poSearchResult.toString());
		
	}
	
	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 14:59:22
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#search(pa._v38.memorymgmt.enums.eDataType, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public <E> ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntityWrite(eDataType poDataType, ArrayList<E> poPattern, double weight, double learning) {
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		createSearchPattern(poDataType, poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult = moSearchSpaceMethods.searchEntityWrite(oSearchPattern,weight,learning);
		
		return poSearchResult;
		//accessKnowledgeBase(poSearchResult, oSearchPattern); 
		//log.debug("Search for pattern: " + oSearchPattern.toString() + ". Result: " + poSearchResult.toString());
		
	}
	
	public void writeQoA(clsDriveMesh DM)
	{
		clsDriveMesh DMnew;
		
	}
	

	
	/* (non-Javadoc)
	 *
	 * @since 26.02.2013 11:25:29
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#searchExactEntityFromInternalAttributes(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public clsThingPresentationMesh searchExactEntityFromInternalAttributes(String poContent, String poShape, String poColor) {
		clsThingPresentationMesh oRetVal = null;
		
		ArrayList<clsThingPresentation> oPropertyList = new ArrayList<clsThingPresentation>();
		//Shape
		if (poShape != "") {
			clsThingPresentation oShapeTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.ShapeType, poShape));
			oPropertyList.add(oShapeTP);
		}
		
		//Color
		if (poColor != "") {
			clsThingPresentation oColorTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.COLOR, poColor));
			oPropertyList.add(oColorTP);
		}
		
		
		//Create the TPM
		clsThingPresentationMesh oGeneratedTPM = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>
			(eContentType.ENTITY, oPropertyList, poContent));
				
		ArrayList<clsPrimaryDataStructureContainer> oSearchStructure = new ArrayList<clsPrimaryDataStructureContainer>();
		oSearchStructure.add(new clsPrimaryDataStructureContainer(oGeneratedTPM, new ArrayList<clsAssociation>()));
				
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
				
		oSearchResult = searchEntity(eDataType.TPM, oSearchStructure); 
				//If nothing is found, cancel
		clsPrimaryDataStructureContainer oEmptySpaceContainer=null;
		for (ArrayList<clsPair<Double,clsDataStructureContainer>> oE : oSearchResult) {
			for (clsPair<Double,clsDataStructureContainer> oE2 : oE) {
				if (((clsThingPresentationMesh)oE2.b.getMoDataStructure()).getContent().equals(poContent)==true) {
					oEmptySpaceContainer = (clsPrimaryDataStructureContainer) oE2.b;
				}
			}
		}
		
		if (oSearchResult.get(0).isEmpty()==true) {
			
			return oRetVal;
		}
		//Create "Nothing"-objects for each position
		//oEmptySpaceContainer = (clsPrimaryDataStructureContainer) oSearchResult.get(0).get(0).b;
		ArrayList<clsPrimaryDataStructureContainer> oEmptySpaceContainerList = new ArrayList<clsPrimaryDataStructureContainer>();
		oEmptySpaceContainerList.add(oEmptySpaceContainer);
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult2 = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
			
		oSearchResult2 = searchEntity(eDataType.DM, oEmptySpaceContainerList);


		//oEntry: Data structure with a double association weight and an object e. g. CAKE with its associated DM.
		if (oSearchResult2.size()!=oEmptySpaceContainerList.size()) {
			try {
				throw new Exception("F46: addAssociations, Error, different Sizes");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		for (int i=0;i<oSearchResult2.size();i++) {
			ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchPair = oSearchResult2.get(i);
			clsPrimaryDataStructureContainer oPC = oEmptySpaceContainerList.get(i);
			if (oSearchPair.size()>0) {
				oEmptySpaceContainerList.get(i).getMoAssociatedDataStructures().addAll(oSearchPair.get(0).b.getMoAssociatedDataStructures());
			}
				
		}
		
		
		((clsThingPresentationMesh)oEmptySpaceContainer.getMoDataStructure()).setExternalAssociatedContent(oEmptySpaceContainer.getMoAssociatedDataStructures());
		clsThingPresentationMesh oEmptySpaceTPM = null;
		try {
			oEmptySpaceTPM = (clsThingPresentationMesh) ((clsThingPresentationMesh) oEmptySpaceContainer.getMoDataStructure()).clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		oRetVal = oEmptySpaceTPM;
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 14:59:22
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#executePsychicSpreadActivation(pa._v38.memorymgmt.datatypes.clsThingPresentationMesh, double)
	 */
	@Override
	public void executePsychicSpreadActivation(clsThingPresentationMesh primaryInput, ArrayList<clsDriveMesh> poDriveMeshFilterList, double prPsychicEnergyIn, int maxNumberOfDirectActivations, boolean useDirectActivation, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> secondaryInput) {
		//Add the activated image to the already processed list
		HashMap<Integer, clsThingPresentationMesh> oAlreadyActivatedImages = new HashMap<Integer, clsThingPresentationMesh>();
		oAlreadyActivatedImages.put(primaryInput.getDS_ID(), primaryInput);
		log.debug("Psychic Spread Activation input: " + primaryInput + "; Psychic Energy=" + prPsychicEnergyIn);
		moSpreadActivationHandler.startSpreadActivation(primaryInput, secondaryInput, recognizedImageMultiplyFactor, primaryInput, prPsychicEnergyIn, maxNumberOfDirectActivations, useDirectActivation, poDriveMeshFilterList, oAlreadyActivatedImages);
		log.debug("Psychic Spread Activation output: " + primaryInput);
	}

	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 14:59:22
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#getSecondaryDataStructure(pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure, double)
	 */
	@Override
	public clsAssociationWordPresentation getSecondaryDataStructure(clsPrimaryDataStructure poDataStructure, double prThreshold) {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		clsAssociationWordPresentation oRetVal = null; 
		
		//If the input is a TPM, then search for a WPM
		if (poDataStructure instanceof clsThingPresentationMesh) {
			oSearchResult = searchEntity(eDataType.WPM, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)));
			//If the input is a TP or DM, then search for the WP
		} else if (poDataStructure instanceof clsAffect || poDataStructure instanceof clsThingPresentation) {
			oSearchResult = searchEntity(eDataType.WP, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)));
		}
		
		//If something was found
		if(oSearchResult.isEmpty() == false && oSearchResult.get(0).size() > 0 && oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().size() > 0){
			//Get the best match if higher than the threshold
			if (oSearchResult.get(0).get(0).a >= prThreshold) {
				oRetVal = (clsAssociationWordPresentation)oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().get(0);
				if (poDataStructure.getDS_ID()==oRetVal.getRootElement().getDS_ID()) {
					oRetVal.setRootElement(poDataStructure);
				}
			}	
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @since 13.07.2011 13:47:03
	 *
	 * @param <E>
	 * @param poDataType
	 * @param poList
	 * @param poSearchPattern
	 */
	public <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList, ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		for (E oEntry : poList){
			if(oEntry instanceof clsDataStructurePA){
				poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, (clsDataStructurePA)oEntry));
			}
			else if (oEntry instanceof clsPrimaryDataStructureContainer){
				poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, ((clsPrimaryDataStructureContainer)oEntry).getMoDataStructure()));
			}
		}
	}
	

	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 17:35:38
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#searchCompleteMesh(pa._v38.memorymgmt.datatypes.clsDataStructurePA, int)
	 */
	@Override
	public clsDataStructurePA searchCompleteMesh(clsDataStructurePA poInput, int pnLevel) {
		clsDataStructurePA oRetVal = null;
		
		oRetVal = moSearchSpaceMethods.getCompleteMesh(poInput, pnLevel); 
		//moKnowledgeBaseHandler.initMeshRetrieval(poInput, pnLevel);
		
		if (oRetVal==null) {
			try {
				throw new Exception("Error in searchMesh: the returned function for " + poInput + " is null. This always occurs if images of TPM does not have any WPM");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//Move all associations from the found structure to the original structure of the input. This is used in Spreadactivation where the mesh is "growing"
		if (poInput instanceof clsWordPresentationMesh) {
			clsDebugTools.correctErronerusAssociations((clsWordPresentationMesh) oRetVal);
			MeshProcessor merger = new MeshProcessor();
			merger.complementMesh((clsWordPresentationMesh)poInput, (clsWordPresentationMesh)oRetVal, true);
			//clsMeshTools.moveAllAssociations((clsWordPresentationMesh)poInput, (clsWordPresentationMesh)oRetVal);
			
		} else if (poInput instanceof clsThingPresentationMesh) {
			clsMeshTools.moveAllAssociations((clsThingPresentationMesh)poInput, (clsThingPresentationMesh)oRetVal);
		}
		
		return poInput;
	}



	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 17:27:34
	 * 
	 * @see pa._v38.memorymgmt.itfModuleMemoryAccess#searchMesh(pa._v38.memorymgmt.clsDataStructurePA, pa._v38.memorymgmt.eContentType, double, int)
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern, eContentType poSearchContentType, double prThreshold, int pnLevel) {
		return this.moSearchSpaceMethods.searchMesh(poPattern, poSearchContentType, prThreshold, pnLevel);
	}

	/*@Override
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return clsOntologyLoader.DS_ID++;
	}

	
}
