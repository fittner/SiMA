/**
 * clsDataStructureComparison.java: DecisionUnits - pa._v38.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 */
package pa._v38.memorymgmt.framessearchspace.tools;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsAssociationPrimary;
import base.datatypes.clsAssociationPrimaryDM;
import base.datatypes.clsAssociationTime;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsPhysicalRepresentation;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsSecondaryDataStructureContainer;
import base.datatypes.clsTemplateImage;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.itfInternalAssociatedDataStructure;
import base.datatypes.enums.eConnectionType;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import inspector.interfaces.Singleton;
import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.framessearchspace.clsSearchSpaceBase;
import pa._v38.memorymgmt.framessearchspace.clsSearchSpaceHandler;
import primaryprocess.datamanipulation.clsPrimarySpatialTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.clsTester;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.07.2010, 07:20:48
 * 
 */
public abstract class clsDataStructureComparisonTools {
	
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
	
	private static double THRESHOLDMATCH=0.0;
	
	private static Logger log = clsLogger.getLog("Memory");
	
	public static ArrayList<clsPair<Double,clsDataStructurePA>> compareDataStructures
									(clsDataStructurePA poDS_Unknown, clsSearchSpaceBase poSearchSpace){

		return getMatchingDataStructures(poSearchSpace, poDS_Unknown);
	}
	public static ArrayList<clsPair<Double,clsDataStructurePA>> compareDataStructuresWrite
							(clsDataStructurePA poDS_Unknown, clsSearchSpaceBase poSearchSpace, double weight, double learning)
	{

		return getMatchingDataStructuresWrite(poSearchSpace, poDS_Unknown, weight, learning);
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
		HashMap<eContentType, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poDS_Unknown.getMoDataStructureType());
		
		if(oMap.containsKey(poDS_Unknown.getContentType())){	//If the input content type already exists in the memory
			oRetVal = getDataStructureByContentType(oMap.get(poDS_Unknown.getContentType()), poDS_Unknown); 
		}
		else{
			oRetVal = getDataStructureByDataStructureType(oMap, poDS_Unknown); 
		}
		
		return oRetVal; 
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
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getMatchingDataStructuresWrite(
													clsSearchSpaceBase poSearchSpace,
													clsDataStructurePA poDS_Unknown, double weight, double learning) {
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double,clsDataStructurePA>>(); 
		HashMap<eContentType, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poDS_Unknown.getMoDataStructureType());
		String marker;
		String markerDM;
		String markerTPM;
		//EAT
		marker = "([AD%3A";
		//marker += poDS_Unknown.getContentType().toString();
		//marker += "%3A";
		//SATISFACTION
		marker += ((clsThingPresentationMesh)(((clsAssociation)poDS_Unknown).getAssociationElementB())).getContentType();
		marker += "%3A";
		//CAKE
		marker += ((clsDriveMesh)(((clsAssociation)poDS_Unknown).getAssociationElementA())).getActualDriveObject().getContent();
		marker += "%3A";
		//EAT
		marker += ((clsDriveMesh)(((clsAssociation)poDS_Unknown).getAssociationElementA())).getActualDriveAim().getContent();
		marker += "%3A";
		//HIGH,MID,LOW
		marker += ((clsThingPresentationMesh)(((clsAssociation)poDS_Unknown).getAssociationElementB())).getContent();
		marker += "]";
		
		markerDM = "[DM%3A";
		//EAT
		markerDM += ((clsDriveMesh)(((clsAssociation)poDS_Unknown).getAssociationElementA())).getActualDriveAim().getContent();
		markerDM += "%3A";
		//CAKE
		markerDM += ((clsDriveMesh)(((clsAssociation)poDS_Unknown).getAssociationElementA())).getActualDriveObject().getContent();
		markerDM += "%3A";
		markerDM += "]";
		
		markerTPM = "[TPM%3A";
		//marker += poDS_Unknown.getContentType().toString();
		//marker += "%3A";
		//SATISFACTION
		markerTPM += ((clsThingPresentationMesh)(((clsAssociation)poDS_Unknown).getAssociationElementB())).getContentType();
		markerTPM += "%3A";
		//HIGH,MID,LOW
		markerTPM += ((clsThingPresentationMesh)(((clsAssociation)poDS_Unknown).getAssociationElementB())).getContent();
		markerTPM += "])";
		
		HashMap<eContentType, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> oMap2 
		= poSearchSpace.returnSearchSpaceTable().get(((clsAssociation)poDS_Unknown).getAssociationElementA().getMoDataStructureType());
		
		//marker ="([AD%3ASATISFACTION%3ACAKE%3AEAT%3AHIGH]";
		if(oMap.containsKey(poDS_Unknown.getContentType())){	//If the input content type already exists in the memory
			oRetVal = getDataStructureByContentTypeWrite(oMap.get(poDS_Unknown.getContentType()),oMap2.get(((clsDriveMesh)(((clsAssociation)poDS_Unknown).getAssociationElementA())).getContentType()),((clsAssociation)poDS_Unknown).getAssociationElementA(), poDS_Unknown, weight, learning, marker, markerDM,markerTPM); 
		}
		else{
			oRetVal = getDataStructureByDataStructureTypeWrite(oMap, poDS_Unknown, weight, learning); 
		}		

		return oRetVal; 
	}
	
//	/**
//	 * Get matches for one input datastructure container. A list with the activated containers and their match 
//	 * factors are returned
//	 *
//	 * @since 14.07.2011 16:06:16
//	 *
//	 * @param poSearchSpaceHandler
//	 * @param poContainerUnknown
//	 * @return
//	 */
//	public static ArrayList<clsPair<Double, clsDataStructureContainer>> compareDataStructuresContainer(
//			clsSearchSpaceHandler poSearchSpaceHandler,
//			clsDataStructureContainer poContainerUnknown, double prThreshold) {
//		ArrayList<clsPair<Double, clsDataStructureContainer>> oRetVal = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
//		ArrayList<clsPair<Double, clsDataStructureContainer>> oPreliminaryRetVal = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
//		
//		clsSearchSpaceBase poSearchSpace = poSearchSpaceHandler.returnSearchSpace();
//		HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oMap 
//											= poSearchSpace.returnSearchSpaceTable().get(poContainerUnknown.getMoDataStructure().getMoDataStructureType());	//Nehme nur nach Typ Image oder TI
//		//Get Searchspace for a certain datatype
//		HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> oMapWithType = oMap.get(poContainerUnknown.getMoDataStructure().getContentType().toString());
//		
//		//For each template image in the storage compare with the input image
//		//1. First search to get all matches
//		for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oMapWithType.entrySet()){
//			clsDataStructurePA oCompareElement = oEntry.getValue().a;
//	
//			clsDataStructureContainer oCompareContainer = getCompleteContainer(oCompareElement, poSearchSpaceHandler);
//			
//			double oMatch = 0.0; //clsSpatialTools.getImageMatch((clsPrimaryDataStructureContainer)poContainerUnknown, (clsPrimaryDataStructureContainer)oCompareContainer);
//			//double oMatch = compareTIContainer((clsPrimaryDataStructureContainer)oCompareContainer, (clsPrimaryDataStructureContainer)poContainerUnknown, true); //Strong matching deactivated
//		
//			if (oMatch < prThreshold)
//				continue;
//			// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
//			int i = 0;
//			while ((i + 1 < oPreliminaryRetVal.size()) && oMatch < oPreliminaryRetVal.get(i).a) {
//				i++;
//			}
//			
//			//Set moInstanceID for all structures in the container
//			//FIXME AW: NOTE: No instanceIDs are allowed to be set here. InstanceIDs must be set "ausserhalb" from the memory
//			//clsDataStructureTools.createInstanceFromType(oCompareContainer);
//			//Add results
//			oPreliminaryRetVal.add(i, new clsPair<Double, clsDataStructureContainer>(oMatch, oCompareContainer));
//		}
//		//2. Second search, where the best matches are newly ordered. This newly ordered list is given back as a result
//		oRetVal.addAll(compareBestResults(oPreliminaryRetVal, poContainerUnknown, mrBestMatchThreshold, mrAssociationMaxValue));
//		
//		//3. Sort the list
//		//TODO AW: Sort the output list
//		return oRetVal;
//	}
	
	
	/**
	 * Get matches for one input datastructure, which may be an image. A list with the activated containers and their match 
	 * factors are returned
	 * 
	 * Only thing presentation mesh allowed
	 *
	 * @since 14.07.2011 16:06:16
	 *
	 * @param poSearchSpaceHandler
	 * @param poContainerUnknown
	 * @return
	 */
	public static ArrayList<clsPair<Double, clsDataStructurePA>> compareDataStructuresMesh(clsSearchSpaceHandler poSearchSpaceHandler, clsDataStructurePA poDSUnknown, double prThreshold, int pnLevel) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		ArrayList<clsPair<Double, clsDataStructurePA>> oPreliminaryRetVal = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		//Get searchspace
		clsSearchSpaceBase poSearchSpace = poSearchSpaceHandler.returnSearchSpace();
		//Get all objects of a certain type
		HashMap<eContentType, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oMap 
											= poSearchSpace.returnSearchSpaceTable().get(poDSUnknown.getMoDataStructureType());	//Nehme nur nach Typ Image oder TI
		
		//Get Searchspace for a certain datatype
		HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> oMapWithType = oMap.get(poDSUnknown.getContentType());
		
		if(oMapWithType == null) {
			log.error("Could not get searchspace for content type " + poDSUnknown.getContentType().toString());
			return oRetVal;
		}
		
		//Check, which search depth is used. 
		//pnLevel 0: Nothing is done with the image
		//pnLevel 1: Load only indirect associations
		//pnLevel 2: Load the first order of indirect associations to other images
		
		log.trace("Input image, which shall be compared: {}", poDSUnknown);
		
		if (pnLevel>=1) {
			//For each template image in the storage compare with the input image
			
			
			//1. First search to get all matches
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oMapWithType.entrySet()){
				
				Singleton.getInstance().addToPIMatchList();
				
				clsDataStructurePA oCompareElement = oEntry.getValue().a;
				
				if (oCompareElement instanceof clsThingPresentationMesh) {
					//Clone the structure as here often the structure comes directly from the memory
					
					clsThingPresentationMesh oClonedCompareElement = null;
					try {
						//oRetVal = (clsThingPresentationMesh) ((clsThingPresentationMesh) poInput).cloneGraph();
						oClonedCompareElement = (clsThingPresentationMesh) ((clsThingPresentationMesh) oCompareElement).clone();
						Singleton.getInstance().RIList.add(oClonedCompareElement);
					} catch (CloneNotSupportedException e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
					
					//INFO: In the image function, the inverse associations are also created.
					try {
						getCompleteMesh(oClonedCompareElement, poSearchSpaceHandler, pnLevel);
					} catch (Exception e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
					
					//=== Perform system tests ===//
					if (clsTester.getTester().isActivated()) {
						try {
							clsTester.getTester().exeTestAssociationAssignment(oClonedCompareElement);
						} catch (Exception e) {
							log.error("Systemtester has an error in clsDataStructureComparison after getCompleteMesh, clonedCompareElement", e);
						}
					}
					
					double oMatch = clsPrimarySpatialTools.getImageMatch((clsThingPresentationMesh) poDSUnknown, oClonedCompareElement);
					log.debug("Compared image {}, match: {}", oClonedCompareElement, oMatch);
					
					//=== Perform system tests ===//
					if (clsTester.getTester().isActivated()) {
						try {
							clsTester.getTester().exeTestAssociationAssignment(oClonedCompareElement);
						} catch (Exception e) {
							log.error("Systemtester has an error in clsDataStructureComparison after getImageMatch, clonedCompareElement", e);
						}
					}
					
					if (oMatch < prThreshold)
						continue;
					// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
					int i = 0;
					while ((i < oPreliminaryRetVal.size()) && oMatch < oPreliminaryRetVal.get(i).a) {
						i++;
					}
					
					//Set moInstanceID for all structures in the container
					//FIXME AW: NOTE: No instanceIDs are allowed to be set here. InstanceIDs must be set "ausserhalb" from the memory
					//clsDataStructureTools.createInstanceFromType(oCompareContainer);
					//Add results
					oPreliminaryRetVal.add(i, new clsPair<Double, clsDataStructurePA>(oMatch, oClonedCompareElement));
				}
			}
			//2. Second search, where the best matches are newly ordered. This newly ordered list is given back as a result
			
			//FIXME AW. Get different matches, for even better matches
			//oRetVal.addAll(compareBestResults(oPreliminaryRetVal, poDSUnknown, mrBestMatchThreshold, mrAssociationMaxValue));
			oRetVal.addAll(oPreliminaryRetVal);
		}
		
		//3. Sort the list
		//TODO AW: Sort the output list
		
		//=== Perform system tests ===//
		if (clsTester.getTester().isActivated()) {
			try {
				clsTester.getTester().exeTestAssociationAssignment(oRetVal);
			} catch (Exception e) {
				log.error("Systemtester has an error in clsDataStructureComparison", e);
			}
		}
		
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
		
		//Get all best results
		for (clsPair<Double, clsDataStructureContainer> oPair : poInputList) {
			//1. Search for all values, which are >= prBestResultsThreshold
			if (oPair.a >= prBestResultsThreshold) {
				//2. Get the number of found associations in the image for that RI
				double rMatch = (double)countMatchAssociations((clsPrimaryDataStructureContainer)oPair.b);
				//double oMatch = compareTIContainer((clsPrimaryDataStructureContainer)poContainerUnknown, (clsPrimaryDataStructureContainer)oPair.b, false);	//Strong matching deactivated
				
				//Sort the list
				int i = 0;
				while ((i < oBestResults.size()) && (rMatch < oBestResults.get(i).a)) {
					i++;
				}
				//Add the new container, sorted
 				oBestResults.add(i, new clsPair<Double, clsDataStructureContainer>(rMatch, oPair.b));
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
	 * @since 03.08.2011 14:27:59
	 *
	 * @param poInputList
	 * @param poContainerUnknown
	 * @param prBestResultsThreshold
	 * @param prCorrectFactor
	 * @return
	 */
	private static ArrayList<clsPair<Double, clsDataStructurePA>> compareBestResults(ArrayList<clsPair<Double, clsDataStructurePA>> poInputList, 
			clsDataStructurePA poContainerUnknown, double prBestResultsThreshold, double prAssociationMaxValue) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		double rMaxMatchValue = 0.0;
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oBestResults = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		ArrayList<clsPair<Double, clsDataStructurePA>> oOtherResults = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		//Get all best results
		for (clsPair<Double, clsDataStructurePA> oPair : poInputList) {
			//1. Search for all values, which are >= prBestResultsThreshold
			if (oPair.a >= prBestResultsThreshold) {
				//2. Get the number of found associations in the image for that RI
				double rMatch = (double)countMatchAssociations((clsThingPresentationMesh)oPair.b);
				//double oMatch = compareTIContainer((clsPrimaryDataStructureContainer)poContainerUnknown, (clsPrimaryDataStructureContainer)oPair.b, false);	//Strong matching deactivated
				
				//Sort the list
				int i = 0;
				while ((i < oBestResults.size()) && (rMatch < oBestResults.get(i).a)) {
					i++;
				}
				//Add the new container, sorted
 				oBestResults.add(i, new clsPair<Double, clsDataStructurePA>(rMatch, oPair.b));
			} else {
				oOtherResults.add(oPair);
			}
		}
		
		//3. Norm the values to the max match value
		if (oBestResults.size()>1) {
			//If there is only one 1.0 match nothing has to be done
			//3a. Get the first value with the highest match if there are any matches
			rMaxMatchValue = oBestResults.get(0).a;
			
			for (clsPair<Double, clsDataStructurePA> oBestPair : oBestResults) {
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
	 * @since 05.12.2011 17:31:01
	 *
	 * @param poRIContainer
	 * @return
	 */
	private static int countMatchAssociations(clsPrimaryDataStructureContainer poRIContainer) {
		int nRetVal = 0;
		
		for (clsAssociation oAss : poRIContainer.getMoAssociatedDataStructures()) {
			if ((oAss instanceof clsAssociationTime) && (oAss.getContentType().equals(eContentType.MATCHASSOCIATION)==true)) {
				nRetVal++;
			}
		}
		
		return nRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 05.12.2011 17:31:04
	 *
	 * @param poRIContainer
	 * @return
	 */
	private static int countMatchAssociations(clsThingPresentationMesh poRI) {
		int nRetVal = 0;
		
		for (clsAssociation oAss : poRI.getExternalAssociatedContent()) {
			if ((oAss instanceof clsAssociationTime) && (oAss.getContentType().equals(eContentType.MATCHASSOCIATION)==true)) {
				nRetVal++;
			}
		}
		
		return nRetVal;
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
	public static clsThingPresentationMesh getCompleteCompositionStructure(clsThingPresentationMesh poInput, clsSearchSpaceHandler poSearchSpaceHandler) {
		//Readoutsearchspace searches everything with a certain moDSID
		//Everything shall be returned
		//A special case of the searchspace was used
		
		//Create Container for the DataStructure
		
		//clsDataStructureContainer oCompareContainer = null;
		clsThingPresentationMesh oRetVal = null;
		try {
			oRetVal = (clsThingPresentationMesh) poInput.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Check if that data structure can be found in the database, else return null
		if (poInput.getDS_ID()>0) {
			if (poInput instanceof clsPrimaryDataStructure) {
				ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
				oAssList.addAll(poSearchSpaceHandler.readOutSearchSpace((clsPhysicalRepresentation)poInput));
				
				//oCompareContainer = new clsPrimaryDataStructureContainer((clsPrimaryDataStructure)poInput, oAssList);
				oRetVal.setExternalAssociatedContent(oAssList);
				//Add associations from intrinsic structures
				//TI, TPM
//				if (poInput instanceof clsTemplateImage) {
//					for (clsAssociation oAss: ((clsTemplateImage)poInput).getMoAssociatedContent()) {
//						//Recursive function
//						clsDataStructureContainer oSubContainer = getCompleteContainer(oAss.getLeafElement(), poSearchSpaceHandler);
//						if (oSubContainer!=null) {
//							oCompareContainer.getMoAssociatedDataStructures().addAll(oSubContainer.getMoAssociatedDataStructures());
//						}
//					}
				//if (poInput instanceof clsThingPresentationMesh) {
				//	for (clsAssociation oAss: poInput.getMoAssociatedContent()) {
				//		//Recursive function
				//		clsDataStructureContainer oSubContainer = getCompleteContainer(oAss.getLeafElement(), poSearchSpaceHandler);
				//		if (oSubContainer!=null) {
				//			oCompareContainer.getMoAssociatedDataStructures().addAll(oSubContainer.getMoAssociatedDataStructures());
				//		}
				//	}
				//}
			//} else {
			//	oCompareContainer = new clsSecondaryDataStructureContainer((clsSecondaryDataStructure) poInput, poSearchSpaceHandler.readOutSearchSpace(poInput));
			}
		}
		
		//Remove duplicate structures
		//if (oCompareContainer!=null) {
		//	oCompareContainer.setMoAssociatedDataStructures(removeNonBelongingStructures(oCompareContainer));
		//}
		//Set moInstanceID for all structures in the container
		//clsDataStructureTools.createInstanceFromType(oCompareContainer);
		
		return oRetVal;
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
		if (poInput.getDS_ID()>0) {
			if (poInput instanceof clsPrimaryDataStructure) {
				ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
				oAssList.addAll(poSearchSpaceHandler.readOutSearchSpace((clsPhysicalRepresentation)poInput));
				
				oCompareContainer = new clsPrimaryDataStructureContainer((clsPrimaryDataStructure)poInput, oAssList);
				//Add associations from intrinsic structures
				//TI, TPM
				if (poInput instanceof clsTemplateImage) {
					for (clsAssociation oAss: ((clsTemplateImage)poInput).getInternalAssociatedContent()) {
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
	 * TODO (Kollmann): doc
	 * 
	 * @since 20.07.2011 02:00:14
	 *
	 * @param poInput
	 * @param poSearchSpaceHandler
	 * @throws Exception 
	 * @throws CloneNotSupportedException 
	 */
	public static void complementMesh(clsThingPresentationMesh poInput, clsSearchSpaceHandler poSearchSpaceHandler, int pnLevel,
			HashMap<Integer, clsThingPresentationMesh> poVisitedTPMs) throws Exception {
		clsThingPresentationMesh oSubMesh = null;
		
		if(!poVisitedTPMs.containsKey(poInput.getDS_ID())) {
			//complete the current node
			getCompleteMesh(poInput, poSearchSpaceHandler, 1);
			
			//store to avoid loops
			poVisitedTPMs.put(poInput.getDS_ID(), poInput);
			
			if(pnLevel > 0)
			{
				//go through internal associations
				for (clsAssociation oAssociation : poInput.getInternalAssociatedContent()) {
					if (oAssociation.getLeafElement() instanceof clsThingPresentationMesh) {
						oSubMesh = (clsThingPresentationMesh)oAssociation.getLeafElement();
						complementMesh(oSubMesh, poSearchSpaceHandler, pnLevel - 1, poVisitedTPMs);
					}
				}
				
				//go through external associations
				for (clsAssociation oAssociation : poInput.getExternalAssociatedContent()) {
					if (oAssociation.getLeafElement() instanceof clsThingPresentationMesh) {
						oSubMesh = (clsThingPresentationMesh)oAssociation.getLeafElement();
						complementMesh(oSubMesh, poSearchSpaceHandler, pnLevel - 1, poVisitedTPMs);
					}
				}
			}
		} else {
			//this is a debugging else
			log.info("Loop detected in clsDataStructureComparisonTools::complementMesh(...) for element:\n" + poInput.toString());
			//because of the whole clone madness, we merge the current input with the input stored from former instances
		}
	}
	
	/**
	 * Get a whole mesh from a data structure including all associated structures in its associated structures
	 * Only TPM are allowed
	 * 
	 * If the pnLevel=-1, then nothing is done
	 * If the pnLevel=0, then only the drive meshes of the external associated structures and the direct structures of the internal associations are loaded
	 * If the pnLevel=1, then the drive meshes and associations to other images (but no structures of those images) are loaded of the external structures and 
	 * the direct internal associations are loaded with drive meshes and internal structures
	 * 
	 * AW
	 * 
	 * 
	 * @since 20.07.2011 02:00:14
	 *
	 * @param poInput
	 * @param poSearchSpaceHandler
	 * @throws Exception 
	 * @throws CloneNotSupportedException 
	 */
	public static void getCompleteMesh(clsThingPresentationMesh poInput, clsSearchSpaceHandler poSearchSpaceHandler, int pnLevel) throws Exception {
		
		clsThingPresentationMesh oRetVal = poInput;
		
			//Check if that data structure can be found in the database, else return null
		if (oRetVal instanceof clsThingPresentationMesh) {
			if (oRetVal.getDS_ID()>0 && pnLevel >=0) {
				//Get the internal associations
				//Add associations from intrinsic structures
				for (clsAssociation oAss: oRetVal.getInternalAssociatedContent()) {
					//Recursive function
					if (oAss.getLeafElement() instanceof clsThingPresentationMesh) {
						clsThingPresentationMesh oSubMesh = (clsThingPresentationMesh)oAss.getLeafElement();
						//Get the complete mesh for this structure
						//FIXME AW: Shall the structure be copied?
						getCompleteMesh(oSubMesh, poSearchSpaceHandler, pnLevel-1);

						//Get the extended structures from the searched one and add them to the TPM
						((clsThingPresentationMesh)oAss.getLeafElement()).setExternalAssociatedContent(oSubMesh.getExternalAssociatedContent());
						//Add the source association too, i. e. if it is an image. The internal TIME-associations are already there, but not the external 
						//time associations of the subobject. This association is added to the external associations of the subobject
						//FIXME AW: This is a non clean solution. The association time is always added but the original object is NOT copied. Therefore, it shall be
						//          checked, that this association is only copied once.
						if (((clsThingPresentationMesh)oAss.getLeafElement()).getExternalAssociatedContent().contains(oAss)==false) {
							((clsThingPresentationMesh)oAss.getLeafElement()).getExternalAssociatedContent().add(oAss);
						}
					}
				}
				
				//Get the external associations
				ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
				if((((clsThingPresentationMesh)poInput).getContent()).equals("A12_EAT_MEAT_L01_I04"))
				{
					boolean test = true;
				}
				oAssList.addAll(poSearchSpaceHandler.readOutSearchSpace((clsThingPresentationMesh)poInput));
				
				for (clsAssociation oAss : oAssList) {
					//Check if the association already exists
					//FIXME: This is a hack to avoid that multiple Drive Meshes are added to the same structure
					
					boolean bFound = false;
					for (clsAssociation oExternalAss : oRetVal.getExternalAssociatedContent()) {
						if (oAss.getDS_ID()==oExternalAss.getDS_ID()) {
							bFound=true;
							break;
						}
					}
					
					//Also check internal associations
					if(bFound == false) {
	                    for (clsAssociation oInternalAss : oRetVal.getInternalAssociatedContent()) {
	                        if (oAss.getDS_ID()==oInternalAss.getDS_ID()) {
	                            bFound=true;
	                            break;
	                        }
	                    }
					}
					
					if (bFound==false) {
						try {
							clsAssociation oClonedAss = (clsAssociation) oAss.clone();
						
							if (oClonedAss instanceof clsAssociationPrimaryDM) {
								//If pnLevel is at least 1 and this association does not exist in the list
								if (pnLevel>=1 && oRetVal.getExternalAssociatedContent().contains(oClonedAss)==false) {
									//Replace the erroneous associations
									if (oRetVal.getDS_ID()==oClonedAss.getRootElement().getDS_ID()) {
										oClonedAss.setRootElement(oRetVal);
									} else if (oRetVal.getDS_ID()==oClonedAss.getLeafElement().getDS_ID()) {
										oClonedAss.setLeafElement(oRetVal);
									} else {
										throw new Exception("Error: No object in the association can be associated to the source structure.\nTPM: " + oRetVal + "\nAssociation: " + oClonedAss);
									}
									
									oRetVal.getExternalAssociatedContent().add(oClonedAss);
								}
							}
							 // Association primary  should be loaded before the image match because of action image comparision (we are require the action which is associated via primary association)
							else if (oClonedAss instanceof clsAssociationPrimary) {
	                                //If pnLevel is at least 1 and this association does not exist in the list
	                                if (pnLevel>=0 && oRetVal.getExternalAssociatedContent().contains(oClonedAss)==false) {
	                                    //Replace the erroneous associations
	                                    if (oRetVal.getDS_ID()==oClonedAss.getRootElement().getDS_ID()) {
	                                        oClonedAss.setRootElement(oRetVal);
	                                    } else if (oRetVal.getDS_ID()==oClonedAss.getLeafElement().getDS_ID()) {
	                                        oClonedAss.setLeafElement(oRetVal);
	                                    } else {
	                                        throw new Exception("Error: No object in the association can be associated to the source structure.\nTPM: " + oRetVal + "\nAssociation: " + oClonedAss);
	                                    }
	                                    
	                                    oRetVal.getExternalAssociatedContent().add(oClonedAss);
	                                }
	                            }
							else if (oClonedAss instanceof clsAssociationAttribute || 
									oClonedAss instanceof clsAssociationDriveMesh || 
									oClonedAss instanceof clsAssociationEmotion) {
								//If pnLevel is at least 1 and this association does not exist in the list
								if (oRetVal.getExternalAssociatedContent().contains(oClonedAss)==false) {
									//Replace the erroneous associations
									if (oRetVal.getDS_ID()==oClonedAss.getRootElement().getDS_ID()) {
										oClonedAss.setRootElement(oRetVal);
									} else if (oRetVal.getDS_ID()==oClonedAss.getLeafElement().getDS_ID()) {
										oClonedAss.setLeafElement(oRetVal);
									} else {
										throw new Exception("Error: No object in the association can be associated to the source structure.\nTPM: " + oRetVal + "\nAssociation: " + oClonedAss);
									}
									
									//oRetVal.getExternalAssociatedContent().add(oClonedAss);
									oClonedAss.activate(eConnectionType.EXTERNAL, eConnectionType.EXTERNAL);
								}
								
							} else if ((oClonedAss instanceof clsAssociationTime)==false) {
								oRetVal.getExternalAssociatedContent().add(oClonedAss);
							}
							
						} catch (CloneNotSupportedException e) {
							// TODO (wendt) - Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
				}
				
			}
		}
		
		//Complement all associations in the other structures
		clsMeshTools.setInverseAssociations(oRetVal);
		
	}
	
	/**
	 * Get a whole mesh from a data structure including all associated structures in its associated structures
	 * Only WPM are allowed
	 * 
	 * AW
	 * 
	 * 
	 * @since 20.07.2011 02:00:14
	 *
	 * @param poInput
	 * @param poSearchSpaceHandler
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	public static clsWordPresentationMesh getCompleteMesh(clsWordPresentationMesh poInput, clsSearchSpaceHandler poSearchSpaceHandler, int pnLevel) {
		
		clsWordPresentationMesh oFoundWPM = poInput;
		clsWordPresentationMesh oRetVal = null;
		
		//Readoutsearchspace searches everything with a certain moDSID
		//Everything shall be returned
		//A special case of the searchspace was used
			
		//Check if that data structure can be found in the database, else return null
		//pnLevel MUST be at least 1, else no substructures are searched
		if (poInput.getDS_ID()>0 && pnLevel >0) {
			//Clone the structure
			
			ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
			oAssList.addAll(poSearchSpaceHandler.readOutSearchSpace((clsWordPresentationMesh)poInput));
				
			//Define the WPM from one of the found associations
			if (oAssList.isEmpty()==false) {
				for (clsAssociation oAss :oAssList) {
					if (oAss.getRootElement().getDS_ID()==poInput.getDS_ID()) {
						oFoundWPM = (clsWordPresentationMesh) oAss.getRootElement();
					} else if (oAss.getLeafElement().getDS_ID()==poInput.getDS_ID()) {
						oFoundWPM = (clsWordPresentationMesh) oAss.getLeafElement();
					} else {
						try {
							throw new Exception("Error in DataStructureComparison: Only associations are allowed, where the input element is one of the elements.");
						} catch (Exception e) {
							log.error("Error in DataStructureComparison: Only associations are allowed, where the input element is one of the elements.");
						}
					}
					
					break;
				}
			} else {
				oFoundWPM = poInput;
			}
			
			//Add everything except associationWP. This association is already set at the conversion from the TPM.
			ArrayList<clsAssociation> oExtAssList = new ArrayList<clsAssociation>();
			for (clsAssociation oAss : oAssList) {
				if ((oAss instanceof clsAssociationWordPresentation)==false) {
					oExtAssList.add(oAss);
				}
				
			}
			oFoundWPM.setExternalAssociatedContent(oExtAssList);
				
			//Copy the result after correctly adressing of the associations
			try {
				oRetVal = (clsWordPresentationMesh) ((clsWordPresentationMesh) oFoundWPM).clone();
				oFoundWPM.setExternalAssociatedContent(new ArrayList<clsAssociation>());
			} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}

			//Add associations from intrinsic structures
			for (clsAssociation oAss: oRetVal.getInternalAssociatedContent()) {
				//Recursive function
				if (oAss.getLeafElement() instanceof clsWordPresentationMesh) {
					clsWordPresentationMesh oSubMesh = (clsWordPresentationMesh) getCompleteMesh((clsWordPresentationMesh)oAss.getLeafElement(), poSearchSpaceHandler, pnLevel-1);
					if (oSubMesh!=null) {
						//Get the extended structures from the searched one and add them to the WPM
						if (oAss.getLeafElement() instanceof clsWordPresentationMesh) {
							((clsWordPresentationMesh)oAss.getLeafElement()).setExternalAssociatedContent(oSubMesh.getExternalAssociatedContent());
							//Add the source association too, i. e. if it is an image. The internal TIME-associations are already there, but not the external 
							//time associations of the subject. This association is added to the external associations of the subject
							((clsWordPresentationMesh)oAss.getLeafElement()).getExternalAssociatedContent().add(oAss);
						}
					}
				}
			}
		}
		
		if(oRetVal != null) {
			//Complement all associations in the other structures
			clsMeshTools.setInverseAssociations(oRetVal);
		} else {
			log.error("Could not complete WPM {} - possibly because it is not found in memory (did you forget to connect the TPM and WPM in memory?)", poInput.getContent());
			log.error("Stack: ", new Exception());
		}
		
		return oRetVal;
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
					if (oDS.getDSInstance_ID() == oAssList.get(i).getRootElement().getDSInstance_ID()) {
						bStructureFound = true;
					} else if (oDS instanceof clsTemplateImage) {
						//For each intrinsic data structures, the one element
						for (clsAssociation oIntrinsicAss : ((clsTemplateImage)oDS).getInternalAssociatedContent()) {
							if (oIntrinsicAss.getLeafElement().getDSInstance_ID() == oAssList.get(i).getRootElement().getDSInstance_ID()) {
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
						for (clsAssociation oIntrinsicAss : ((clsTemplateImage)oDS).getInternalAssociatedContent()) {
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
				if ((oDS.getDS_ID() == oAssList.get(i).getRootElement().getDS_ID()) || 
						(oDS.getDS_ID() == oAssList.get(i).getLeafElement().getDS_ID())) {
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
			clsThingPresentationMesh poNewRoot,
			ArrayList<clsAssociation> poOldAssociations) {
		ArrayList<clsAssociationDriveMesh> oReturnlist = new ArrayList<clsAssociationDriveMesh>();
		
		for (clsAssociation entry : poOldAssociations) {
			if (entry instanceof clsAssociationDriveMesh) {
				clsAssociationDriveMesh oldAssDM = (clsAssociationDriveMesh)entry;
				clsAssociationDriveMesh newAssDM = 
					new clsAssociationDriveMesh(
							new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM),
							oldAssDM.getDM(),
							poNewRoot,1.0);
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

						if((perceivedTP.getContentType() == blockedTP.getContentType()) &&
								(perceivedTP.getContent().toString() == blockedTP.getContent().toString())) {
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
					
					if(rMatchScore > THRESHOLDMATCH){
						int nInsert = sortList(oDS_List, rMatchScore); 
						oDS_List.add(nInsert,new clsPair<Double, clsDataStructurePA>(rMatchScore, oCompareElement));
					}
				//}
			}
		
			return oDS_List;
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
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByContentTypeWrite(
			HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> poMap,HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> poMap2,
			clsDataStructurePA TPM,
			clsDataStructurePA poDS_Unknown, double weight, double learning, String marker, String markerDM, String markerTPM) {
		
			double rMatchScore = 0.0; 
			ArrayList<clsPair<Double, clsDataStructurePA>> oDS_List = new ArrayList<clsPair<Double, clsDataStructurePA>>();
			
			boolean newElement = true;
			
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poMap.entrySet()){
				clsDataStructurePA oCompareElement = oEntry.getValue().a; 
				//A comparison will only take place, if the search data structure instanceID is 0, in order to compare the unknown structures with a type
				//if (oCompareElement.getMoDSInstance_ID()==0) {
				
					
					rMatchScore = oCompareElement.compareTo(poDS_Unknown);
					
					if(rMatchScore >= 1.0){
						// set weight
						((clsAssociation)oCompareElement).setMrWeight(weight);
						((clsAssociation)oCompareElement).setMrLearning(learning);
						ArrayList<String> lines = new ArrayList<String>();
						String line = null;
						boolean found = false;
						DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
						dfs.setDecimalSeparator('.');
						DecimalFormat f = new DecimalFormat("#0.00", dfs);
						try
				        {
							LocalDateTime now = LocalDateTime.now();
					        DateTimeFormatter df;
					        df = DateTimeFormatter.ofPattern("_yyyyMMdd");     // 31.01.2016 20:07
					        String date = now.format(df);
							
							File f1 = new File("C:/Users/nocks/Dropbox/workspace/ARSIN_V02/ARSMemory/config/_v38/bw/pa.memory/ADAM_EC2SC2_BEAT/ADAM_EC2SC2_BEAT_Learning.pins");
							//File f2 = new File("C:/Users/noName/Dropbox/workspace/ARSIN_V02/ARSMemory/config/_v38/bw/pa.memory/ADAM_EC2SC2_BEAT/ADAM_EC2SC2_BEAT.pins");
							FileReader fr = new FileReader(f1);
					        BufferedReader br = new BufferedReader(fr);
					        while ((line = br.readLine()) != null) {
					            if (line.contains(marker))
					            {
					            	found=true;
					            }
					            if (found)
					            {
					            	if (line.contains("(weight"))
					            	{
					            		line = line.replaceFirst("[(]weight [0-9]*.[0-9]*", "(weight "+f.format(weight));
						            	found = false;
						            	newElement = false;
					            	}
					            }
					            lines.add(line+"\n");
					        }
					        fr.close();
					        br.close();

					        FileWriter fw = new FileWriter(f1);
					        BufferedWriter out = new BufferedWriter(fw);
					        for(String s : lines)
					             out.write(s);
					        out.flush();
					        out.close();
				        }
					   catch (Exception ex) {
					        ex.printStackTrace();
					    }
					}
			}
			if(newElement)
			{
				ArrayList<clsAssociation> Ass = new ArrayList<clsAssociation>();
				poMap.put(poDS_Unknown.getDS_ID(),new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(poDS_Unknown,Ass));
				
				for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poMap2.entrySet())
				{
					clsDataStructurePA oCompareElement = oEntry.getValue().a; 
					//A comparison will only take place, if the search data structure instanceID is 0, in order to compare the unknown structures with a type
					//if (oCompareElement.getMoDSInstance_ID()==0) {
					
						
						rMatchScore = oCompareElement.compareTo(TPM);
						ArrayList<clsAssociation> poInternalAssociatedContent = new ArrayList<clsAssociation> ();
						poInternalAssociatedContent.add((clsAssociation) poDS_Unknown);
						if(rMatchScore >= 1.0){
							// set Assoziation
							oEntry.getValue().b.add((clsAssociation) poDS_Unknown);
							((clsDriveMesh)oEntry.getValue().a).addInternalAssociations(poInternalAssociatedContent);;
						}
				}
				
				// set weight
				ArrayList<String> lines = new ArrayList<String>();
				String line = null;
				DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
				dfs.setDecimalSeparator('.');
				DecimalFormat f = new DecimalFormat("#0.00", dfs);
				try
		        {
					LocalDateTime now = LocalDateTime.now();
			        DateTimeFormatter df;
			        df = DateTimeFormatter.ofPattern("_yyyyMMdd");     // 31.01.2016 20:07
			        String date = now.format(df);
					
					File f1 = new File("C:/Users/nocks/Dropbox/workspace/ARSIN_V02/ARSMemory/config/_v38/bw/pa.memory/ADAM_EC2SC2_BEAT/ADAM_EC2SC2_BEAT_Learning.pins");
					//File f2 = new File("C:/Users/noName/Dropbox/workspace/ARSIN_V02/ARSMemory/config/_v38/bw/pa.memory/ADAM_EC2SC2_BEAT/ADAM_EC2SC2_BEAT.pins");
					FileReader fr = new FileReader(f1);
			        BufferedReader br = new BufferedReader(fr);
			        while ((line = br.readLine()) != null) {
			            lines.add(line+"\n");
			        }
			        fr.close();
			        br.close();
			        
			        /* Add new Element */
			        lines.add("\n");
			        lines.add(marker+" of  ASSOCIATIONDM\n\n");
			        lines.add("\t(element\n");
			        lines.add("\t\t"+markerDM+"\n");
			        lines.add("\t\t"+markerTPM+"\n");
			        lines.add("\t(weight "+f.format(weight) + "))\n");

			        FileWriter fw = new FileWriter(f1);
			        BufferedWriter out = new BufferedWriter(fw);
			        for(String s : lines)
			             out.write(s);
			        out.flush();
			        out.close();
		        }
			   catch (Exception ex) {
			        ex.printStackTrace();
			    }
	        }

		
			return oDS_List;
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
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByContentTypeWrite2(
			HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> poMap,
			clsDataStructurePA poDS_Unknown, clsDataStructurePA poDS_Unknown2)
	{
		
			double rMatchScore = 0.0; 
			ArrayList<clsPair<Double, clsDataStructurePA>> oDS_List = new ArrayList<clsPair<Double, clsDataStructurePA>>();
			
			boolean newElement = true;
			
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : poMap.entrySet())
			{
				clsDataStructurePA oCompareElement = oEntry.getValue().a; 
				//A comparison will only take place, if the search data structure instanceID is 0, in order to compare the unknown structures with a type
				//if (oCompareElement.getMoDSInstance_ID()==0) {
				
					
					rMatchScore = oCompareElement.compareTo(poDS_Unknown);
					
					if(rMatchScore >= 1.0){
						// set Assoziation
						oEntry.getValue().b.add((clsAssociation) poDS_Unknown2);
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
			HashMap<eContentType, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> poMap,
			clsDataStructurePA poDataStructureUnknown) {

		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();

		for(Map.Entry<eContentType, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oTableEntry : poMap.entrySet()){
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oTableEntry.getValue().entrySet()){
				clsDataStructurePA oSearchSpaceElement = oEntry.getValue().a;
				//InstanceID has to be 0, in the search part, in order to compare the structure
				//if (oSearchSpaceElement.getMoDSInstance_ID()==0) {
				if(oSearchSpaceElement instanceof clsDriveMesh)
				{
					if (((clsDriveMesh)oSearchSpaceElement).getActualDriveSource() != null)
					{
						if (((clsDriveMesh)oSearchSpaceElement).getActualDriveSource().getContent().toString().equals("HEALTH"))
						{
							if(poDataStructureUnknown instanceof clsDriveMesh)
							{
								if (((clsDriveMesh)poDataStructureUnknown).getActualDriveSource() != null)
								{
									if (((clsDriveMesh)poDataStructureUnknown).getActualDriveSource().getContent().toString().equals("HEALTH"))
									{
										rMatchScore = 0.0;
									}
								}
							}
						}
					}
				}
					rMatchScore = oSearchSpaceElement.compareTo(poDataStructureUnknown);
					if(rMatchScore > THRESHOLDMATCH){
						oMatchingDataStructureList.add(new clsPair<Double, clsDataStructurePA>(rMatchScore, oSearchSpaceElement));
					}

				//}
			}
		}
				
		return oMatchingDataStructureList;
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
	private static ArrayList<clsPair<Double, clsDataStructurePA>> getDataStructureByDataStructureTypeWrite(
			HashMap<eContentType, HashMap<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>>> poMap,
			clsDataStructurePA poDataStructureUnknown, double weight, double learning) {

		double rMatchScore = 0.0; 
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchingDataStructureList = new ArrayList<clsPair<Double, clsDataStructurePA>>();

		for(Map.Entry<eContentType, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>> oTableEntry : poMap.entrySet()){
			for(Map.Entry<Integer, clsPair<clsDataStructurePA,ArrayList<clsAssociation>>> oEntry : oTableEntry.getValue().entrySet()){
				clsDataStructurePA oSearchSpaceElement = oEntry.getValue().a;
				//InstanceID has to be 0, in the search part, in order to compare the structure
				//if (oSearchSpaceElement.getMoDSInstance_ID()==0) {
					rMatchScore = oSearchSpaceElement.compareTo(poDataStructureUnknown);
					
					if(rMatchScore >= 1.0){
						// set weight
						((clsAssociation)oSearchSpaceElement).setMrWeight(weight);
						((clsAssociation)oSearchSpaceElement).setMrLearning(learning);	
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
	public static ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>> getSpecificAssociatedContent(clsThingPresentationMesh poFromImage, clsThingPresentationMesh poToImage, eDataType poDataType, eContentType poContentType) {
		ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>>();
		clsPair<clsThingPresentationMesh, clsAssociation> oMatch = null;
		//Get the data structure, which could also have DMs
		//Only DM and TP can be copied
		
		if ((poFromImage != null) && (poToImage != null)) {	//Catch the problem if the data structure would be null
			//Get Target structures
			
			//if ((poToImage.getMoDataStructure() instanceof clsTemplateImage)==false) {
			//	try {
			//		throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: Only data structures consisting of clsTemplateImage canbbe used.");
			//	} catch (Exception e) {
			//		e.printStackTrace();
			//	}
			//}
			//clsTemplateImage oToImageObject = (clsTemplateImage)poToImage.getMoDataStructure();
			//TODO AW: Only Template Images, which contain TPMs are concerned, expand to other data types and nested template images
			if (poDataType == eDataType.DM) {
				//Get all compare drive meshes
				ArrayList<clsAssociationDriveMesh> oFromImageDriveMeshes = clsMeshTools.getAllDMInMesh(poFromImage);
			
				//For each DM or TP in the associated structures in the SourceContainer
				for (clsAssociationDriveMesh oFromImageDM : oFromImageDriveMeshes) {	//The association in the source file. The root element shall be found in that target file
				
					//if (oAssInFromImage instanceof clsAssociationDriveMesh) {
					if (poContentType != null) {	//Add only that content type of that structure type
						if (oFromImageDM.getLeafElement().getContentType() == poContentType) {
							oMatch = getMatchInDataStructure(oFromImageDM, poToImage);
						}
					} else {	//Add all
						oMatch = getMatchInDataStructure(oFromImageDM, poToImage);
					}
				}
			
				/*} else if (poDataType == eDataType.TP) {
					if (oAssInFromImage instanceof clsAssociationAttribute) {
						if (poContentType != null) {	//Add only that content type of that structure type
							if (oAssInFromImage.getLeafElement().getMoContentType().toString() == "poContentType") {
								oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
							}
						} else {	//Add all
							oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
						}
					}*/
				//} 
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
	private static clsPair<clsThingPresentationMesh, clsAssociation> getMatchInDataStructure (clsAssociation poSourceAssociation, clsThingPresentationMesh poTargetDataStructure) {
		clsPair<clsThingPresentationMesh, clsAssociation> oRetVal = null;
		
		//Get root element
		clsThingPresentationMesh oCompareRootElement = (clsThingPresentationMesh) poSourceAssociation.getRootElement();
		//Find the root element in the target image. Only an exact match is count
		//1. Check if the root element is the same as the data structure in the target container
		if ((oCompareRootElement.getDS_ID() == poTargetDataStructure.getDS_ID() && (oCompareRootElement.getDS_ID() > 0))) {
			oRetVal = new clsPair<clsThingPresentationMesh, clsAssociation>(poTargetDataStructure, poSourceAssociation);
		} else {
			//2. Check if the root element can be found in the associated data structures
			for (clsAssociation oAssToImage : poTargetDataStructure.getInternalAssociatedContent()) {
				if ((oCompareRootElement.getDS_ID() == oAssToImage.getLeafElement().getDS_ID() && (oCompareRootElement.getDS_ID() > 0))) {
					oRetVal = new clsPair<clsThingPresentationMesh, clsAssociation>((clsThingPresentationMesh) oAssToImage.getLeafElement(), poSourceAssociation);
					break;
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
		if ((oCompareRootElement.getDS_ID() == poTargetDataStructure.getDS_ID() && (oCompareRootElement.getDS_ID() > 0))) {
			oRetVal = new clsPair<clsDataStructurePA, clsAssociation>(poTargetDataStructure, poSourceAssociation);
		} else {
			//2. Check if the root element can be found in the associated data structures
			for (clsAssociation oAssToImage : poTargetDataStructure.getInternalAssociatedContent()) {
				if ((oCompareRootElement.getDS_ID() == oAssToImage.getLeafElement().getDS_ID() && (oCompareRootElement.getDS_ID() > 0))) {
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
