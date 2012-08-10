/**
 * KB02_InternalPerceptionMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:49:28
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;
import java.util.HashSet;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

import pa._v38.memorymgmt.datatypes.itfInternalAssociatedDataStructure;	
import pa._v38.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v38.memorymgmt.informationrepresentation.enums.eDataStructureMatch;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:49:28
 * 
 */
public class KB02_InternalPerceptionMgmt extends clsInformationRepresentationModuleBase{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:59:15
	 *
	 * @param poInformationRepresentationModulesContainer
	 * @param poSearchSpaceHandler 
	 */
	public KB02_InternalPerceptionMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, 
											clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		super(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod);
	
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:06:58
	 * 
	 * @see pa.informationrepresentation.ARSi10.modules.clsInformationRepresentationModuleBase#listSearch(java.lang.String, pa.informationrepresentation.datatypes.clsDataStructureContainer)
	 */
	@Override
	public ArrayList<clsPair<Double,clsDataStructureContainer>> listSearch(int poReturnType,clsDataStructurePA poDataStructureUnknown) {

		ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
		ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsPair<Double,clsDataStructurePA>>();
//		System.out.println(moSearchSpaceHandler.toString());
		if(poDataStructureUnknown.getMoDS_ID() > -1 ){	//If the data structure already has an ID, no matching is necessary and it has found itself
			oMatchedDataStructures.add(new clsPair<Double, clsDataStructurePA>(1.0,poDataStructureUnknown)); 
		}
		else{
			oMatchedDataStructures = compareElements(poDataStructureUnknown); 
		}
				
		for(clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures){
			clsDataStructureContainer oDataStructureContainer = getDataContainer(poReturnType, (clsPhysicalRepresentation)oPatternElement.b);	//Get container from a certain data value
			oDataStructureContainerList.add(new clsPair<Double, clsDataStructureContainer>(oPatternElement.a, oDataStructureContainer));
		}
		return oDataStructureContainerList;
	}
	
	

	/**
	 * Start the method to compare the input container with all other images in the storage
	 *
	 * @since 30.06.2011 23:03:27
	 *
	 * @param poReturnType
	 * @param poDataStructureUnknown
	 * @return
	 * 
	 * A listsearch, which uses the whole container to search the memory with.
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructureContainer>> listSearchContainer(int poReturnType,clsDataStructureContainer poDataContainerInput, double prThreshold) {
		//In this function, the container content is compared with saved template images, which are the data structure in generated containers
		//ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 	//Result
		
		/* Parameters:
		 * - Boolean Strong match: Default=true. If one compared TP belonging to a TI is false, the whole match is = 0
		 * - boolean Compare drives: Default=false. Compare DMs with each other
		 * - String DataType: Default: MEMORY. TIs with this type are searched for
		 * - Boolean AddItself: Default=true: If the structure itself is found in the memory, it adds itself to the result
		 * - double Threshold: Default=0.0 Association threshold for adding the image as a match
		 */
		
		//Steps
		ArrayList<clsPair<Double, clsDataStructureContainer>> oMatchedDataStructures = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		//2b. Set the Content type of oDS	
		oMatchedDataStructures = compareElementsContainer(poDataContainerInput, prThreshold);	//Get a List of all matching structures in the memory
	
		return oMatchedDataStructures;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 05.12.2011 16:35:26
	 * 
	 * @see pa._v38.memorymgmt.informationrepresentation.modules.clsInformationRepresentationModuleBase#listSearchMesh(int, pa._v38.memorymgmt.datatypes.clsDataStructurePA, double, int)
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructurePA>> listSearchMesh(int poReturnType, clsDataStructurePA poDataStructureUnknown, double prThreshold, int pnLevel) {
		//In this function, the container content is compared with saved template images, which are the data structure in generated containers
		//ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 	//Result
		
		/* Parameters:
		 * - Boolean Strong match: Default=true. If one compared TP belonging to a TI is false, the whole match is = 0
		 * - boolean Compare drives: Default=false. Compare DMs with each other
		 * - String DataType: Default: MEMORY. TIs with this type are searched for
		 * - Boolean AddItself: Default=true: If the structure itself is found in the memory, it adds itself to the result
		 * - double Threshold: Default=0.0 Association threshold for adding the image as a match
		 */
		
		//Steps
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		//2b. Set the Content type of oDS	
		oMatchedDataStructures = compareElementsMesh(poDataStructureUnknown, prThreshold, pnLevel);	//Get a List of all matching structures in the memory
	
		return oMatchedDataStructures;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 12.07.2010, 12:58:02
	 *
	 * @param poReturnType
	 * @param oPatternElement
	 * @return
	 */
	private clsDataStructureContainer getDataContainer(int poReturnType, clsPhysicalRepresentation poDataStructure) {
		
			clsPrimaryDataStructureContainer oDataStructureContainer = new clsPrimaryDataStructureContainer(null, null);
			oDataStructureContainer.setMoDataStructure(poDataStructure); 
			oDataStructureContainer.setMoAssociatedDataStructures(readOutSearchSpace(poReturnType, poDataStructure)); 
			
		return oDataStructureContainer;
	}
	
	/*@Override
	public clsDataStructureContainer getDataCompleteStructureContainer(clsDataStructurePA poInput) {
		return clsDataStructureComparison.getCompleteContainer(poInput, moSearchSpaceHandler);
	}*/

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 12.07.2010, 13:52:24
	 *
	 * @param poReturnType
	 * @param poDataStructure
	 * @return
	 */
	private ArrayList<clsAssociation> readOutSearchSpace(int poReturnType, clsPhysicalRepresentation poDataStructure) {
		return moSearchSpaceHandler.readOutSearchSpace(poReturnType, poDataStructure);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.07.2010, 07:12:23
	 *
	 * @param moDataStructure
	 * @return
	 */
	private ArrayList<clsPair<Double,clsDataStructurePA>> compareElements(clsDataStructurePA poDataStructureUnknown) {
		return clsDataStructureComparison.compareDataStructures(poDataStructureUnknown, moSearchSpaceHandler.returnSearchSpace());
	}
	
	/**
	 * Search for the whole content of a container in the memory and compare the found structures with the input structure
	 *
	 * @since 08.07.2011 11:53:35
	 *
	 * @param poContainerUnknown
	 * @return
	 */
	private ArrayList<clsPair<Double, clsDataStructureContainer>> compareElementsContainer(clsDataStructureContainer poContainerUnknown, double prThreshold) {
		ArrayList<clsPair<Double, clsDataStructureContainer>> oRetVal = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		
		oRetVal = clsDataStructureComparison.compareDataStructuresContainer(moSearchSpaceHandler, poContainerUnknown, prThreshold);	
		
		return oRetVal;
	}
	
	/**
	 * Search for the whole content of a container in the memory and compare the found structures with the input structure
	 *
	 * @since 08.07.2011 11:53:35
	 *
	 * @param poContainerUnknown
	 * @return
	 */
	private ArrayList<clsPair<Double, clsDataStructurePA>> compareElementsMesh(clsDataStructurePA poUnknown, double prThreshold, int pnLevel) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		oRetVal = clsDataStructureComparison.compareDataStructuresMesh(moSearchSpaceHandler, poUnknown, prThreshold, pnLevel);	
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 12.08.2012, 13:52:24
	 *
	 * @param poReturnType
	 * @param poDataStructureUnknown
	 * @return
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructureContainer>> graphSearch(
			int poReturnType, clsDataStructurePA poDataStructureUnknown) {
		
		double rMatchScore = 0;
		
		ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
		
		// list for expanded nodes
		
		// list for unexpanded nodes

		HashSet<clsDataStructurePA> oSearchFringe = new HashSet<clsDataStructurePA>();
		
		ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsPair<Double,clsDataStructurePA>>();
				
		clsPair<Double, clsDataStructurePA> oBestMatch = null;
		
		// of poDataStructureUnknown (not internal assoc objects!)
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		if(poDataStructureUnknown.getMoDS_ID() > -1 ){	//If the data structure already has an ID, no matching is necessary and it has found itself
			oMatchedDataStructures.add(new clsPair<Double, clsDataStructurePA>(1.0,poDataStructureUnknown)); 
		}
		else if (poDataStructureUnknown  instanceof itfInternalAssociatedDataStructure) {

			// For each unknowDS (e.g. UnknownTPM): take its internal associated data structures (e.g.TPs) and get all returntype of unknownDs (e.g. TPMs that are associated with this TP - if returntype is TPM) 
			
			// 1. get internal associated data structures of UnknowDS (e.g. UnknownTPM)
			for( clsAssociation oIntAssOfUnknownDS: ((itfInternalAssociatedDataStructure) poDataStructureUnknown).getMoInternalAssociatedContent()) {
				// 2. get matching data structures from knowledgebase
				oMatchedDataStructures = compareElements(oIntAssOfUnknownDS.getMoAssociationElementB());
				
				if(oMatchedDataStructures.isEmpty()) continue;
				
				// 3. get best match of internal ass. data structures (e.g TPs) as starting fringe for graph search
				oBestMatch =  getBestMatch(oMatchedDataStructures);
				
				// 4. get associated returntype (e.g. TPMs of TP)
				clsDataStructureContainer oDataStructureContainer = getDataContainer(poReturnType, (clsPhysicalRepresentation)oBestMatch.b);	//Get container from a certain data value
				oDataStructureContainerList.add(new clsPair<Double, clsDataStructureContainer>(oBestMatch.a, oDataStructureContainer));

			}
			
			// 5. fill initial searchFringe. use a set (searchFringe) because different TPs may have the same TPM associated
			for(clsPair<Double, clsDataStructureContainer> oAssReturnObjects: oDataStructureContainerList) {
				for(clsAssociation oAssReturnObject: oAssReturnObjects.b.getMoAssociatedDataStructures()) {
					oSearchFringe.add(oAssReturnObject.getMoAssociationElementA());
				}
			}
			
			
			// 6. start search			
			if(oSearchFringe.isEmpty() == true) {
				// return failure
			}
			
			// 7. Goal test (=similarity check)
			for (clsDataStructurePA oFringeObject: oSearchFringe) {
				rMatchScore = oFringeObject.compareTo(poDataStructureUnknown);
				
				if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
					oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(rMatchScore, oFringeObject));
				}
			}
			 
		}
		
		
		return null;
	}

	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @since Aug 9, 2012 2:50:19 PM
	 *
	 * @param oMatchedDataStructures
	 * @return
	 */
	private clsPair<Double, clsDataStructurePA> getBestMatch(
			ArrayList<clsPair<Double, clsDataStructurePA>> poMatchedDataStructures) {
		
		clsPair<Double, clsDataStructurePA> oReturnValue = null;
		double rMaxScore = 0;
		
		for(clsPair<Double, clsDataStructurePA> oPatternElement : poMatchedDataStructures){
			if(oPatternElement.a > rMaxScore) {
				rMaxScore = oPatternElement.a;
				oReturnValue = new clsPair<Double, clsDataStructurePA>(rMaxScore,oPatternElement.b);
			}
		}
		
		return oReturnValue;
	}

}
