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
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
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
	
	/** Weight for the matching for the content */
	private static double mrMatchValContentFactor = 0.2; //Max 1.0
	/** Weight for the matching for the intrinsic values */
	private static double mrMatchValInstrinsicFactor = 0.2; //Max 1.0
	/** Weight for the matching for the extrinsic values */
	private static double mrMatchValExtrinsicFactor = 0.6; //Max 1.0
	/** If a match has the value here, it is newly ordered by the second search */
	private static double mrBestMatchThreshold = 1.0; //Max 1.0
	/** This factor says how much can be added to 1.0 as a max association strength */
	private static double mrAssociationMaxValue = 1.2;	//Max unlimited
	
	
	
	
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
	
	/**
	 * Get matches for one input datastructure container. A list with the activated containers and their match 
	 * factors are returned
	 *
	 * @since 14.07.2011 16:06:16
	 *
	 * @param poSearchSpaceHandler
	 * @param poContainerUnknown
	 * @return
	 */
	public static ArrayList<clsPair<Double, clsDataStructureContainer>> compareDataStructuresContainer(
			clsSearchSpaceHandler poSearchSpaceHandler,
			clsDataStructureContainer poContainerUnknown, double prThreshold) {
		ArrayList<clsPair<Double, clsDataStructureContainer>> oRetVal = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		ArrayList<clsPair<Double, clsDataStructureContainer>> oPreliminaryRetVal = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		
		clsSearchSpaceBase poSearchSpace = poSearchSpaceHandler.returnSearchSpace();
		HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poContainerUnknown.getMoDataStructure().getMoDataStructureType());	//Nehme nur nach Typ Image oder TI
		//Get Searchspace for a certain datatype
		HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> oMapWithType = oMap.get(poContainerUnknown.getMoDataStructure().getMoContentType());
		
		//For each template image in the storage compare with the input image
		//1. First search to get all matches
		for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oMapWithType.entrySet()){
			clsDataStructurePA oCompareElement = oEntry.getValue().a;
	
			clsDataStructureContainer oCompareContainer = getCompleteContainer(oCompareElement, poSearchSpaceHandler);
			double oMatch = compareTIContainer((clsPrimaryDataStructureContainer)oCompareContainer, (clsPrimaryDataStructureContainer)poContainerUnknown, true); //Strong matching deactivated
		
			if (oMatch < prThreshold)
				continue;
			// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
			int i = 0;
			while ((i + 1 < oPreliminaryRetVal.size()) && oMatch < oPreliminaryRetVal.get(i).a) {
				i++;
			}
			
			//Set moInstanceID for all structures in the container
			//FIXME AW: NOTE: No instanceIDs are allowed to be set here. InstanceIDs must be set "ausserhalb" from the memory
			//clsDataStructureTools.createInstanceFromType(oCompareContainer);
			//Add results
			oPreliminaryRetVal.add(i, new clsPair<Double, clsDataStructureContainer>(oMatch, oCompareContainer));
		}
		//2. Second search, where the best matches are newly ordered. This newly ordered list is given back as a result
		oRetVal.addAll(compareBestResults(oPreliminaryRetVal, poContainerUnknown, mrBestMatchThreshold, mrAssociationMaxValue));
		
		//3. Sort the list
		//TODO AW: Sort the output list
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 03.08.2011 14:27:59
	 *
	 * @param poInputList
	 * @param poContainerUnknown
	 * @param prBestResultsThreshold
	 * @param prCorrectFactor
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructureContainer>> compareBestResults(ArrayList<clsPair<Double, clsDataStructureContainer>> poInputList, 
			clsDataStructureContainer poContainerUnknown, double prBestResultsThreshold, double prAssociationMaxValue) {
		ArrayList<clsPair<Double, clsDataStructureContainer>> oRetVal = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		
		double rMaxMatchValue = 0.0;
		
		ArrayList<clsPair<Double, clsDataStructureContainer>> oBestResults = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		ArrayList<clsPair<Double, clsDataStructureContainer>> oOtherResults = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		
		for (clsPair<Double, clsDataStructureContainer> oPair : poInputList) {
			//1. Search for all values, which are >= prBestResultsThreshold
			if (oPair.a >= prBestResultsThreshold) {
				//2. Compare these values with the unknown image once again and return the match. This time the comparison is in the other way, i. e. the template image is searched in the input image
				double oMatch = compareTIContainer((clsPrimaryDataStructureContainer)poContainerUnknown, (clsPrimaryDataStructureContainer)oPair.b, false);	//Strong matching deactivated
				//Sort the list
				int i = 0;
				while ((i < oBestResults.size()) && (oMatch < oBestResults.get(i).a)) {
					i++;
				}
				//Add the new container, sorted
 				oBestResults.add(i, new clsPair<Double, clsDataStructureContainer>(oMatch, oPair.b));
			} else {
				oOtherResults.add(oPair);
			}
		}
		
		//3. Norm the values to the max match value
		if (oBestResults.size()>1) {
			//If there is only one 1.0 match nothing has to be done
			//3a. Get the first value with the highest match if there are any matches
			rMaxMatchValue = oBestResults.get(0).a;
			
			for (clsPair<Double, clsDataStructureContainer> oBestPair : oBestResults) {
				//3b. Calculate the new Matchvalue acc. formula
				double rNewMatchValue = calculateBestMatchValue(oBestPair.a, prBestResultsThreshold, prAssociationMaxValue, rMaxMatchValue);
				//3c. Set the new match value
				oBestPair.a = rNewMatchValue;
			}
			//4. Merge the lists
			oRetVal.addAll(oBestResults);
			oRetVal.addAll(oOtherResults);
		} else {
			//4b. Take the input list
			oRetVal.addAll(poInputList);
		}
	
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 03.08.2011 14:23:32
	 *
	 * @param prBestMatchValue
	 * @param prBestResultsThreshold
	 * @param prCorrectFactor
	 * @return
	 */
	private static double calculateBestMatchValue(double prInverseTemplateMatch, double prBestResultsThreshold, double prAssociationMaxValue, double prMaxMatchValue) {
		//prMatchValueOfFullMatch Current match with inverse template comparison 
		//The threshold does not play any role in the calculation the prMaxMatchValue there to norm the matching for all found parts
		//return (1-prCorrectFactor) * prBestMatchValue/prMaxMatchValue + prCorrectFactor;
		return (prBestResultsThreshold+((prAssociationMaxValue-prBestResultsThreshold)*(prInverseTemplateMatch/prMaxMatchValue)));
	}
	
	
	/**
	 * Get a whole container from a data structure including all associated structures
	 * AW
	 * @since 20.07.2011 02:00:14
	 *
	 * @param poInput
	 * @param poSearchSpaceHandler
	 * @return
	 */
	public static clsDataStructureContainer getCompleteContainer(clsDataStructurePA poInput, clsSearchSpaceHandler poSearchSpaceHandler) {
		//Readoutsearchspace searches everything with a certain moDSID
		//Everything shall be returned
		//A special case of the searchspace was used
		
		//Create Container for the DataStructure
		clsDataStructureContainer oCompareContainer = null;
		
		//Check if that data structure can be found in the database, else return null
		if (poInput.getMoDS_ID()>0) {
			if (poInput instanceof clsPrimaryDataStructure) {
				ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
				oAssList.addAll(poSearchSpaceHandler.readOutSearchSpace((clsPhysicalRepresentation)poInput));
				
				oCompareContainer = new clsPrimaryDataStructureContainer((clsPrimaryDataStructure)poInput, oAssList);
				//Add associations from intrinsic structures
				//TI, TPM
				if (poInput instanceof clsTemplateImage) {
					for (clsAssociation oAss: ((clsTemplateImage)poInput).getMoAssociatedContent()) {
						//Recursive function
						clsDataStructureContainer oSubContainer = getCompleteContainer(oAss.getLeafElement(), poSearchSpaceHandler);
						if (oSubContainer!=null) {
							oCompareContainer.getMoAssociatedDataStructures().addAll(oSubContainer.getMoAssociatedDataStructures());
						}
					}
				} else if (poInput instanceof clsThingPresentationMesh) {
					//Do nothing, because Thing Presentations shall not be treated
				}
			} else {
				oCompareContainer = new clsSecondaryDataStructureContainer((clsSecondaryDataStructure) poInput, poSearchSpaceHandler.readOutSearchSpace(poInput));
			}
		}
		
		//Remove duplicate structures
		if (oCompareContainer!=null) {
			oCompareContainer.setMoAssociatedDataStructures(removeNonBelongingStructures(oCompareContainer));
		}
		//Set moInstanceID for all structures in the container
		//clsDataStructureTools.createInstanceFromType(oCompareContainer);
		
		return oCompareContainer;
	}
	
	/**
	 * Remove duplicates in an arraylist and also non belongen associations of the containers.
	 * AW
	 *
	 * @since 19.07.2011 21:22:12
	 *
	 * @param poInput
	 * @return
	 */
	private static <E extends clsDataStructureContainer> ArrayList<clsAssociation> removeNonBelongingStructures(E poInput) {
		ArrayList<clsAssociation> oRetVal = new ArrayList<clsAssociation>();
		
		ArrayList<clsAssociation> oAssList = poInput.getMoAssociatedDataStructures();
		for (int i=0; i<oAssList.size();i++) {
			int iNumberOfMatches = 0;
			boolean bStructureFound = false;
			
			//Remove duplicates
			for (int j=i; j<oAssList.size();j++) {
				if (oAssList.get(i) == oAssList.get(j)) {
					iNumberOfMatches++;
				}
			}
			
			//Check the roots of the structures
			clsDataStructurePA oDS = poInput.getMoDataStructure();
			//If the DS is a primary data structure, the instance ID have to be checked (all images have an instanceID), because
			//there will be duplicates
			if (oDS instanceof clsPrimaryDataStructure) {
				//Check directed associations. Directed associations are the following:
				if ((oAssList.get(i) instanceof clsAssociationPrimary)==false) {
					if (oDS.getMoDSInstance_ID() == oAssList.get(i).getRootElement().getMoDSInstance_ID()) {
						bStructureFound = true;
					} else if (oDS instanceof clsTemplateImage) {
						//For each intrinsic data structures, the one element
						for (clsAssociation oIntrinsicAss : ((clsTemplateImage)oDS).getMoAssociatedContent()) {
							if (oIntrinsicAss.getLeafElement().getMoDSInstance_ID() == oAssList.get(i).getRootElement().getMoDSInstance_ID()) {
								bStructureFound = true;
								break;
							}
						}
					}
				//Check not directed associations
				} else {
					if (((clsAssociationPrimary)oAssList.get(i)).containsInstanceID(oDS) != null) {
						bStructureFound = true;
					} else if (oDS instanceof clsTemplateImage) {
						//For each intrinsic data structures, the one element
						for (clsAssociation oIntrinsicAss : ((clsTemplateImage)oDS).getMoAssociatedContent()) {
							if (((clsAssociationPrimary)oAssList.get(i)).containsInstanceID(oIntrinsicAss.getLeafElement()) != null) {
								bStructureFound = true;
								break;
							}
						}
					}
				}
				
			//In secondary data structure, there are no duplicate WP...yet.
			//TODO AW: All structure shall have an instance ID too.
			} else if (oDS instanceof clsSecondaryDataStructure) {
				if ((oDS.getMoDS_ID() == oAssList.get(i).getRootElement().getMoDS_ID()) || 
						(oDS.getMoDS_ID() == oAssList.get(i).getLeafElement().getMoDS_ID())) {
					bStructureFound = true;
				}
			}
			
			
			if ((iNumberOfMatches<2) && (bStructureFound==true)) {
				oRetVal.add(oAssList.get(i));
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Start the compareTIContainerInclDM and return only the data structures and not the matched DM associations
	 *
	 * @since 14.07.2011 16:16:47
	 *
	 * @param poBlockedContent
	 * @param poPerceivedContent
	 * @return
	 */
	private static double compareTIContainer(clsPrimaryDataStructureContainer poBlockedContent,
			clsPrimaryDataStructureContainer poPerceivedContent, boolean pbStrongMatch) {
		double rRetVal = 0;
		clsPair<Double, ArrayList<clsAssociationDriveMesh>> oPair = compareTIContainerInclDM(poBlockedContent, poPerceivedContent, pbStrongMatch);
		rRetVal = oPair.a;
		
		return rRetVal;
		
	}
	
	/**
	 * Calculates the match between two containers containing TemplateImages.<br>
	 * <br>
	 * Returns the quality of the match and a list that contains associations
	 * between any DMs in the blockedContent and the matching items in the
	 * perceivedContent. The second part of the result is done here because it is
	 * way more efficient to create those associations "on the fly" while
	 * comparing the items than to later extract the DMs from a matching content
	 * and find their correct "targets" in the perception - which would
	 * essentially require a second match algorithm.<br>
	 * <br>
	 * - Quality of the match is the sum of the quality of the matches of the items
	 * in the (blocked) TI divided by the total number of items.<br>
	 * - Quality of the match of ThingPresentationMeshes is determined by comparing
	 * the moContent (20%), intrinsic properties (moAssociatedData of the items)
	 * (40%) and extrinsic properties (moAssociatedDataStructures in the
	 * container) (40%).<br>
	 * - Quality of the match of ThingPresentations is either 1 or 0.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 22.06.2011, 20:08:11
	 *
	 * @param poBlockedContent		- the item from the blockedContentStorage for
	 * which the match quality should be calculated.
	 * @param poPerceivedContent	- the perception to compare the
	 * <i>blockedContent</i> to
	 * @return									- a clsPair with A the quality of the match 
	 * (double) and B = a list of Associations between DMs in <i>blockedContent</i>
	 * and their matching "partners" in the perception 
	 * (ArrayList&lt;clsAssociationDriveMesh&gt;)
	 * 
	 * @see DT2_BlockedContentStorage#getAssocAttributeMatch(ArrayList, ArrayList, double)
	 * @see DT2_BlockedContentStorage#createNewDMAssociations(clsPrimaryDataStructure, ArrayList)
	 */
	
	public static clsPair<Double, ArrayList<clsAssociationDriveMesh>> compareTIContainerInclDM(
			clsPrimaryDataStructureContainer poBlockedContent,
			clsPrimaryDataStructureContainer poPerceivedContent, boolean pbStrongMatch) {

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
								getAssocAttributeMatch(poBlockedContent.getMoAssociatedDataStructures(blockedTPM), poPerceivedContent.getMoAssociatedDataStructures(perceivedTPM), matchValContent);	
						}
						// combine values to calculate overall match of a TPM. Weights are arbitrary!
						
						double matchValCombined = matchFactorCalculation(matchValContent, matchValIntrinsic, matchValExtrinsic, pbStrongMatch);//((0.2 * matchValContent) + (0.2 * matchValIntrinsic) + (0.6 * matchValExtrinsic));
						
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
							//If a perfect match is made, then break, as no more comparison is necessary
							if (bestMatchValue==1.0) {
								break;
							}
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
	
	/**
	 * Formula for calculating the match between two images.
	 * Option: Strong or weak match: If strong match is chosen, the extrinsic values like position must have 1.0 match.
	 * If weak match, the position is only weighted together with the other parameters
	 * (wendt)
	 *
	 * @since 22.07.2011 10:27:56
	 *
	 * @param rMatchValContent
	 * @param rMatchValIntrinsic
	 * @param rMatchValExtrinsic
	 * @param blStrongMatch
	 * @return
	 */
	private static double matchFactorCalculation(double rMatchValContent, double rMatchValIntrinsic, double rMatchValExtrinsic, boolean blStrongMatch) {
		double rRetVal = 0.0;
		
		if (blStrongMatch==false) {
			rRetVal = ((mrMatchValContentFactor * rMatchValContent) + (mrMatchValInstrinsicFactor * rMatchValIntrinsic) + (mrMatchValExtrinsicFactor * rMatchValExtrinsic));
		} else {
			if (rMatchValExtrinsic==1.0) {
				rRetVal = ((mrMatchValContentFactor * rMatchValContent) + (mrMatchValInstrinsicFactor * rMatchValIntrinsic))/(mrMatchValContentFactor + mrMatchValInstrinsicFactor);
			} else {
				rRetVal = 0.0;
			}
			
		}
		
		return rRetVal;		
	}
	
	/**
	 * Creates new AssociationDriveMeshes from the inputs.<br>
	 * <br>
	 * A list of new AssociationDriveMeshes with the argument newRoot as
	 * rootElement and the DMs found in oldAssociations as leafElements. 
	 *
	 * @author Zottl Marcus (e0226304),
	 * 28.06.2011, 20:08:58
	 *
	 * @param poNewRoot					- the root element for the new associations
	 * @param poOldAssociations -	the old associations from which the DMs are taken
	 * @return								- a list of new associations between newRoot and the
	 * DMs found in oldAssociations
	 */
	private static ArrayList<clsAssociationDriveMesh> createNewDMAssociations(
			clsPrimaryDataStructure poNewRoot,
			ArrayList<clsAssociation> poOldAssociations) {
		ArrayList<clsAssociationDriveMesh> oReturnlist = new ArrayList<clsAssociationDriveMesh>();
		
		for (clsAssociation entry : poOldAssociations) {
			if (entry instanceof clsAssociationDriveMesh) {
				clsAssociationDriveMesh oldAssDM = (clsAssociationDriveMesh)entry;
				clsAssociationDriveMesh newAssDM = 
					new clsAssociationDriveMesh(
							new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, "ASSOCIATIONDM"),
							oldAssDM.getDM(),
							poNewRoot);
				oReturnlist.add(newAssDM);
			}
		}
		return oReturnlist;
	}
	
	/**
	 * Calculates the match between two lists of properties (of two items).<br>
	 * <br>
	 * The quality of the match is the number of matching properties divided by
	 * the total number of properties.<br>
	 * If there are no blocked associations, then the result depends on whether
	 * the roots of the associations match.<br> 
	 * Example: a cake with no specified attributes matches any other cake,
	 * therefore the result is a full match, but a stone without properties does
	 * NOT match any cake, therefore the match is zero.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 22.06.2011, 23:41:36
	 *
	 * @param poBlockedAssocs		- list of associated properties of the blocked
	 * content item.
	 * @param poPerceivedAssocs	- list of associated properties of the perceived
	 * content item.
	 * @param poRootMatch				- a number indicating whether the root elements of
	 * the associations are considered a match (1.0) or not (0.0).
	 * @return									- the quality of the match between the to lists of
	 * properties.
	 */
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
				//A comparison will only take place, if the search data structure instanceID is 0, in order to compare the unknown structures with a type
				//if (oCompareElement.getMoDSInstance_ID()==0) {
					rMatchScore = oCompareElement.compareTo(poDS_Unknown);
					
					if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
						int nInsert = sortList(oDS_List, rMatchScore); 
						oDS_List.add(nInsert,new clsPair<Double, clsDataStructurePA>(rMatchScore, oCompareElement));
					}
				//}
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
				//InstanceID has to be 0, in the search part, in order to compare the structure
				//if (oSearchSpaceElement.getMoDSInstance_ID()==0) {
					rMatchScore = oSearchSpaceElement.compareTo(poDataStructureUnknown);
					
					if(rMatchScore > eDataStructureMatch.THRESHOLDMATCH.getMatchFactor()){
						oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(rMatchScore, oSearchSpaceElement));
					}
				//}
			}
		}
				
		return oMatchingDataStructureList;
	}
	
	/**
	 * DOCUMENT Compare 2 images regarding datatype and contenttype. If the perceived objects of the "FromImage" are found in the "ToImage", then the 
	 * associated data structures (datatype and contenttype) are assigned the found structures. Only a complete type match is allowed for object matching,
	 * i. e. the MoDS_ID is compared. The purpose of the function is to find corresponding objects in the "ToImage", where e. g. drive meshes can be attached.
	 * This is used in F45. 
	 * 
	 * TODO AW: At the moment, this function is only implemented for drive meshes.
	 *
	 * @since 16.07.2011 10:28:21
	 *
	 * @param poFromImage
	 * @param poToImage
	 * @param poDataType
	 * @param poContentType
	 * @return
	 */
	public static ArrayList<clsPair<clsDataStructurePA, clsAssociation>> getSpecificAssociatedContent(clsPrimaryDataStructureContainer poFromImage, clsPrimaryDataStructureContainer poToImage, eDataType poDataType, String poContentType) {
		ArrayList<clsPair<clsDataStructurePA, clsAssociation>> oRetVal = new ArrayList<clsPair<clsDataStructurePA, clsAssociation>>();
		clsPair<clsDataStructurePA, clsAssociation> oMatch = null;
		//Get the data structure, which could also have DMs
		//Only DM and TP can be copied
		
		if ((poFromImage != null) && (poToImage != null)) {	//Catch the problem if the data structure would be null
			//Get Target structures
			if ((poToImage.getMoDataStructure() instanceof clsTemplateImage)==false) {
				try {
					throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: Only data structures consisting of clsTemplateImage canbbe used.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			clsTemplateImage oToImageObject = (clsTemplateImage)poToImage.getMoDataStructure();
			//TODO AW: Only Template Images, which contain TPMs are concerned, expand to other data types and nested template images
			
			//For each DM or TP in the associated structures in the SourceContainer
			for (clsAssociation oAssInFromImage : poFromImage.getMoAssociatedDataStructures()) {	//The association in the source file. The root element shall be found in that target file
				if (poDataType == eDataType.DM) {
					if (oAssInFromImage instanceof clsAssociationDriveMesh) {
						if (poContentType != null) {	//Add only that content type of that structure type
							if (oAssInFromImage.getLeafElement().getMoContentType() == poContentType) {
								oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
							}
						} else {	//Add all
							oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
						}
					}
				} else if (poDataType == eDataType.TP) {
					if (oAssInFromImage instanceof clsAssociationAttribute) {
						if (poContentType != null) {	//Add only that content type of that structure type
							if (oAssInFromImage.getLeafElement().getMoContentType().toString() == "poContentType") {
								oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
							}
						} else {	//Add all
							oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
						}
					}
				} else {
					try {
						throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: A not allowed datatype was used");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (oMatch != null) {
					oRetVal.add(oMatch);
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * The root element of the association is searched in all intrinsic (de: innewohnenede, definierende) data structures in the
	 * target image and the match is returned as a pair of the data structure of the target image and the association itself.
	 * 
	 * This function is a part of "getSpecificAssociatedContent"
	 *
	 * @since 16.07.2011 10:31:28
	 *
	 * @param poSourceAssociation
	 * @param poTargetDataStructure
	 * @return
	 */
	private static clsPair<clsDataStructurePA, clsAssociation> getMatchInDataStructure (clsAssociation poSourceAssociation, clsTemplateImage poTargetDataStructure) {
		clsPair<clsDataStructurePA, clsAssociation> oRetVal = null;
		
		//Get root element
		clsDataStructurePA oCompareRootElement = poSourceAssociation.getRootElement();
		//Find the root element in the target image. Only an exact match is count
		//1. Check if the root element is the same as the data structure in the target container
		if ((oCompareRootElement.getMoDS_ID() == poTargetDataStructure.getMoDS_ID() && (oCompareRootElement.getMoDS_ID() > 0))) {
			oRetVal = new clsPair<clsDataStructurePA, clsAssociation>(poTargetDataStructure, poSourceAssociation);
		} else {
			//2. Check if the root element can be found in the associated data structures
			for (clsAssociation oAssToImage : poTargetDataStructure.getMoAssociatedContent()) {
				if ((oCompareRootElement.getMoDS_ID() == oAssToImage.getLeafElement().getMoDS_ID() && (oCompareRootElement.getMoDS_ID() > 0))) {
					oRetVal = new clsPair<clsDataStructurePA, clsAssociation>(oAssToImage.getLeafElement(), poSourceAssociation);
					break;
				}
			}
		}
		
		return oRetVal;
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
