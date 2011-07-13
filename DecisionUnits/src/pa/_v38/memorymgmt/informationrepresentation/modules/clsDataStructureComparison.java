/**
 * clsDataStructureComparison.java: DecisionUnits - pa._v38.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v38.memorymgmt.informationrepresentation.enums.eDataStructureMatch;
import pa._v38.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 * 
 */
public abstract class clsDataStructureComparison {
	public static ArrayList<clsPair<Double,clsDataStructurePA>> compareDataStructures
									(clsDataStructurePA poDS_Unknown, clsSearchSpaceBase poSearchSpace){

		return getMatchingDataStructures(poSearchSpace, poDS_Unknown);
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
													clsDataStructurePA poDS_Unknown) {
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double,clsDataStructurePA>>(); 
		HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poDS_Unknown.getMoDataStructureType());
		
		if(oMap.containsKey(poDS_Unknown.getMoContentType())){	//If the input content type already exists in the memory
			oRetVal = getDataStructureByContentType(oMap.get(poDS_Unknown.getMoContentType()), poDS_Unknown); 
		}
		else{
			oRetVal = getDataStructureByDataStructureType(oMap, poDS_Unknown); 
		}
		
		return oRetVal; 
	}
	
	public static ArrayList<clsTripple<Double, clsDataStructureContainer, ArrayList<clsAssociationDriveMesh>>> compareDataStructures(
			clsSearchSpaceHandler poSearchSpaceHandler,
			clsDataStructureContainer poContainerUnknown) {
		ArrayList<clsTripple<Double, clsDataStructureContainer, ArrayList<clsAssociationDriveMesh>>> oRetVal = new ArrayList<clsTripple<Double, clsDataStructureContainer, ArrayList<clsAssociationDriveMesh>>>();
		
		double oThreshold = 0.5;
		
		clsSearchSpaceBase poSearchSpace = poSearchSpaceHandler.returnSearchSpace();
		HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poContainerUnknown.getMoDataStructure().getMoDataStructureType());	//Nehme nur nach Typ Image oder TI
		//Get Searchspace for a certain datatype
		HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> oMapWithType = oMap.get(poContainerUnknown.getMoDataStructure().getMoContentType());
		
		//For each template image in the storage compare with the input image
		for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oMapWithType.entrySet()){
			clsDataStructurePA oCompareElement = oEntry.getValue().a;
			//Create a container,
			//FIXME: Only the primary Datastructurecontainer is used  //External Associations of FIXME AW: are NOT inside the containers
			ArrayList<Integer> iReturnTypes = new ArrayList<Integer>();
			iReturnTypes.add(eDataType.DM.nBinaryValue);
			iReturnTypes.add(eDataType.TP.nBinaryValue);
			clsDataStructureContainer oCompareContainer = getCompleteContainer((clsTemplateImage)oCompareElement, poSearchSpaceHandler, iReturnTypes);
			clsPair<Double, ArrayList<clsAssociationDriveMesh>> oMatch = compareTIContainer((clsPrimaryDataStructureContainer)oCompareContainer, (clsPrimaryDataStructureContainer)poContainerUnknown);
		
			if (oMatch.a < oThreshold)
				continue;
			// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
			int i = 0;
			while ((i + 1 < oRetVal.size()) && oMatch.a < oRetVal.get(i).a) {
				i++;
			}
			//Add results
			oRetVal.add(i, new clsTripple<Double, clsDataStructureContainer, ArrayList<clsAssociationDriveMesh>>(oMatch.a, oCompareContainer, oMatch.b));
		}
			
		return oRetVal;
	}
	
	private static clsDataStructureContainer getCompleteContainer(clsTemplateImage poInput, clsSearchSpaceHandler poSearchSpaceHandler, ArrayList<Integer> iReturnTypes) {
		//Readoutsearchspace searches everything with a certain moDSID
		
		//Create Container for the TI
		clsDataStructureContainer oCompareContainer = new clsPrimaryDataStructureContainer(poInput, poSearchSpaceHandler.readOutSearchSpace(0, (clsPhysicalRepresentation)poInput));
		
		//Add all associations from the intrinsic associated elements
		ArrayList<clsAssociation> oAssList = oCompareContainer.getMoAssociatedDataStructures();
		for (clsAssociation oAss: poInput.getMoAssociatedContent()) {
			for (Integer iType : iReturnTypes) {
				oAssList.addAll(poSearchSpaceHandler.readOutSearchSpace(iType, ((clsPhysicalRepresentation)oAss.getLeafElement()), true));
			}	
		}
		
		oCompareContainer.setMoAssociatedDataStructures(oAssList);
		return oCompareContainer;
	}
	
	private static clsPair<Double, ArrayList<clsAssociationDriveMesh>> compareTIContainer(
			clsPrimaryDataStructureContainer poBlockedContent,
			clsPrimaryDataStructureContainer poPerceivedContent) {

		clsTemplateImage oBlockedTI = (clsTemplateImage) poBlockedContent.getMoDataStructure();
		clsTemplateImage oPerceivedTI = (clsTemplateImage) poPerceivedContent.getMoDataStructure();
		double oMatchValueTI = 0.0;
		int oElemCountTI = oBlockedTI.getMoAssociatedContent().size();
		double oMatchSumElements = 0.0;
		ArrayList<clsAssociationDriveMesh> oNewDriveMeshAssociations = new ArrayList<clsAssociationDriveMesh>();
		
		// for each element of the blockedContent, find the !best! matching element
		// in the perceivedContent
		for (clsAssociation blockedTIContent : oBlockedTI.getMoAssociatedContent()) {
			double bestMatchValue = 0.0;
			ArrayList<clsAssociationDriveMesh> bestMatchDriveMeshAssociations = new ArrayList<clsAssociationDriveMesh>();
			for (clsAssociation perceivedTIContent : oPerceivedTI.getMoAssociatedContent()) {
				if ((blockedTIContent.getLeafElement() instanceof clsThingPresentationMesh) &&
						(perceivedTIContent.getLeafElement() instanceof clsThingPresentationMesh)) {
					clsThingPresentationMesh blockedTPM = (clsThingPresentationMesh)blockedTIContent.getLeafElement();
					clsThingPresentationMesh perceivedTPM = (clsThingPresentationMesh)perceivedTIContent.getLeafElement();
					if (perceivedTPM.getMoContentType() == blockedTPM.getMoContentType()) {
						double matchValContent = 0.0;
						// first see if the contents match
						if (perceivedTPM.getMoContent() == blockedTPM.getMoContent()) {
							matchValContent = 1.0;
						}
						// now check how well the intrinsic properties match
						double matchValIntrinsic = 
							getAssocAttributeMatch(blockedTPM.getMoAssociatedContent(), perceivedTPM.getMoAssociatedContent(), matchValContent);
						// only check the extrinsic properties if the content matches!
						double matchValExtrinsic = 0.0;
						if (matchValContent == 1.0){
							matchValExtrinsic = 
								getAssocAttributeMatch(
										poBlockedContent.getMoAssociatedDataStructures(blockedTPM), 
										poPerceivedContent.getMoAssociatedDataStructures(perceivedTPM), matchValContent);	
						}
						// combine values to calculate overall match of a TPM. Weights are arbitrary!
						double matchValCombined = ((0.2 * matchValContent) + (0.4 * matchValIntrinsic) + (0.4 * matchValExtrinsic));
						// store best element match so far, because we need to find the highest matching element
						if (matchValCombined > bestMatchValue) {
							bestMatchValue = matchValCombined;
							/* In case the overall matchValueTI is 1 we need to associate all
							 * DMs from the blockedContent with the perceivedContent. Since
							 * the DMs are not associated with the whole TI but with one of 
							 * its elements, we need to create new associations between the 
							 * DMs and the matching elements in the perceivedContent. If we do
							 * not want to find the correct new roots those DMs in an
							 * additional matching algorithm, we have to acquire them now! 
							 */
							bestMatchDriveMeshAssociations = 
								createNewDMAssociations(perceivedTPM, 
										poBlockedContent.getMoAssociatedDataStructures(blockedTPM));
						}
					}
				}
				else if ((blockedTIContent.getLeafElement() instanceof clsThingPresentation) &&
						(perceivedTIContent.getLeafElement() instanceof clsThingPresentation)) {
					clsThingPresentation blockedTP = (clsThingPresentation)blockedTIContent.getLeafElement();
					clsThingPresentation perceivedTP = (clsThingPresentation)perceivedTIContent.getLeafElement();

					if((perceivedTP.getMoContentType() == blockedTP.getMoContentType()) &&
							(perceivedTP.getMoContent() == blockedTP.getMoContent())) {
						// we have found a match! No need to check anything else since TPs don't have any associatedContents
						bestMatchValue = 1;
						break; // since a TP match is simply 1 or 0 we just take the first match we find
					}
				}
				else if ((blockedTIContent.getLeafElement() instanceof clsTemplateImage) &&
						(perceivedTIContent.getLeafElement() instanceof clsTemplateImage)) {
					//TODO matching of TIs nested inside a TI if this ever becomes necessary
				}
			}
			// best matchValue is added to the sum of matchValues and related
			// associations are added to the result
			oMatchSumElements += bestMatchValue;
			oNewDriveMeshAssociations.addAll(bestMatchDriveMeshAssociations);
		}
		// matchValue of two TIs is the average matchValue of their contents/elements or zero if the TI is empty (should not happen)
		if (oElemCountTI != 0) {
			oMatchValueTI = oMatchSumElements / oElemCountTI;
		}
		else {
			oMatchValueTI = 0;
		}
		return new clsPair<Double, ArrayList<clsAssociationDriveMesh>>(oMatchValueTI, oNewDriveMeshAssociations);
	}
	
	private static ArrayList<clsAssociationDriveMesh> createNewDMAssociations(
			clsPrimaryDataStructure poNewRoot,
			ArrayList<clsAssociation> poOldAssociations) {
		ArrayList<clsAssociationDriveMesh> oReturnlist = new ArrayList<clsAssociationDriveMesh>();
		
		for (clsAssociation entry : poOldAssociations) {
			if (entry instanceof clsAssociationDriveMesh) {
				clsAssociationDriveMesh oldAssDM = (clsAssociationDriveMesh)entry;
				clsAssociationDriveMesh newAssDM = 
					new clsAssociationDriveMesh(
							new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, "ASSOCIATIONDM"),
							oldAssDM.getDM(),
							poNewRoot);
				oReturnlist.add(newAssDM);
			}
		}
		return oReturnlist;
	}
	
	
	private static double getAssocAttributeMatch(
			ArrayList<clsAssociation> poBlockedAssocs,
			ArrayList<clsAssociation> poPerceivedAssocs,
			double poRootMatch) {
		double oMatchFactor;
		int oAssocCount = 0;
		int oAssocMatches = 0;
		for(clsAssociation blockedAssocEntry : poBlockedAssocs) {
			if(blockedAssocEntry.getLeafElement() instanceof clsThingPresentation) {
			oAssocCount++; // we only count associated TPs, not DMs!
			for(clsAssociation perceivedAssocEntry : poPerceivedAssocs) {
					if(perceivedAssocEntry.getLeafElement() instanceof clsThingPresentation) {
						clsThingPresentation blockedTP = (clsThingPresentation)blockedAssocEntry.getLeafElement();
						clsThingPresentation perceivedTP = (clsThingPresentation)perceivedAssocEntry.getLeafElement();

						if((perceivedTP.getMoContentType() == blockedTP.getMoContentType()) &&
								(perceivedTP.getMoContent().toString() == blockedTP.getMoContent().toString())) {
							oAssocMatches++; // we have found a match!
							break; // leave inner loop, because there can't be more than one match: that would lead to matchFactor greater 1.
						}
					}
				}
				// matching ONLY calculated for TPs for now, DMs are simply ignored.
			}
		}
		// (Association)matchFactor of two DataStructures is the number of matching Associations divided by the total number of
		// Associations of the blocked DataStructure
		if(oAssocCount > 0)
			oMatchFactor = ((double) oAssocMatches) / oAssocCount;
		else
			/* if there are no (blocked) associations, then the result depends on whether the roots of the associations match. 
			 * Example: a cake with no specified attributes matches any other cake, therefore the result is a full match,
			 * but a stone without properties does NOT match any cake, therefore the match is zero.
			 *  
			 */
			oMatchFactor = poRootMatch;
		return oMatchFactor;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 14:59:49
	 *
	 * @param poMap
	 * @param poDataStructureUnknown
	 * @param poDataStructureContentType 
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByContentType(
			HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> poMap,
			clsDataStructurePA poDS_Unknown) {
		
			double rMatchScore = 0.0; 
			ArrayList<clsPair<Double, clsDataStructurePA>> oDS_List = new ArrayList<clsPair<Double, clsDataStructurePA>>();
			
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poMap.entrySet()){
					clsDataStructurePA oCompareElement = oEntry.getValue().a; 
					rMatchScore = oCompareElement.compareTo(poDS_Unknown);
					
					if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
						int nInsert = sortList(oDS_List, rMatchScore); 
						oDS_List.add(nInsert,new clsPair<Double, clsDataStructurePA>(rMatchScore, oCompareElement));
					}
				}
		
			return oDS_List;
	}
	
//	/**
//	 * DOCUMENT (zeilinger) - insert description
//	 *
//	 * @author zeilinger
//	 * 18.08.2010, 14:59:44
//	 *
//	 * @param poSearchSpace
//	 * @param poDataStructureUnknown
//	 * @return
//	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByDataStructureType(
			HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> poMap,
			clsDataStructurePA poDataStructureUnknown) {

		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();

		for(Map.Entry<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oTableEntry : poMap.entrySet()){
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oTableEntry.getValue().entrySet()){
					clsDataStructurePA oSearchSpaceElement = oEntry.getValue().a; 
					rMatchScore = oSearchSpaceElement.compareTo(poDataStructureUnknown);
						
					if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
						oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(rMatchScore, oSearchSpaceElement));
					}
	
			}
		}
				
		return oMatchingDataStructureList;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.07.2010, 16:36:54
	 * @param rMatchScore 
	 *
	 * @param matchingDataStructureList
	 * @return
	 */
	private static int sortList(ArrayList<clsPair<Double, clsDataStructurePA>> poDSList, double rMS) {
		int oRetVal = 0; 
		
		for(clsPair<Double, clsDataStructurePA> oEntry : poDSList){
				
				if(rMS > oEntry.a){
					oRetVal = poDSList.indexOf(oEntry); 
					break; 
				}
				else if (rMS == oEntry.a){
					oRetVal = poDSList.indexOf(oEntry); 
					break;
				}
				else {
					oRetVal = poDSList.size(); 
				}
		}
	
		return oRetVal;
	}
	
}
