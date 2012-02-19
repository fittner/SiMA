/**
 * CHANGELOG
 *
 * 25.06.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.06.2011, 09:29:24
 * 
 */
public class clsAffectTools {
	
	//Added by AW, in order to be able to add drive goals from perception and memories
	/** The list of possible drives, sorted regarding importance */
	private static ArrayList<String> moPossibleDriveGoals = new ArrayList<String>(Arrays.asList("SLEEP", "RELAX", "NOURISH", "BITE", "REPRESS", "DEPOSIT"));	//SLEEP first, as if there is no sleep, the agent cannot do anything
	/** A list of possible affects sorted in the order of importance */
	private static ArrayList<Integer> moAffectSortOrder = new ArrayList<Integer>(Arrays.asList(-3,3,-2, 2, -1, 1,0));	//FIXME AW: Possibly use another solution for sorting
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	
	

	/**
	 * The total affect of the mesh is calculated for one level.
	 * 
	 * !!!!!!!!!!!! THIS IS A SPECIALIZED FUNCTION, WHICH IS USED TO CALCULATE THE TOTAL AFFECT OF ONE LEVEL OF THE PI OR RI !!!!!!!!!!!!!!!!!
	 * 
	 * (wendt)
	 *
	 * @since 20.07.2011 13:58:37
	 *
	 * @param poImage
	 * @return
	 */
	public static double calculateAbsoluteAffect(clsThingPresentationMesh poImage, ArrayList<clsDriveMesh> poDMList) {
		double rAbsoluteAffect;
		
		rAbsoluteAffect = 0;
		
		ArrayList<clsPair<String, String>> oDMConentType = new ArrayList<clsPair<String, String>>();
		//Get all contenttypes from the DM
		for (clsDriveMesh oDM : poDMList) {
			oDMConentType.add(new clsPair<String, String>(oDM.getMoContentType(), null));
		}
		
		ArrayList<clsAssociationDriveMesh> oDMList = clsDataStructureTools.getSelectedDMInImage(poImage, oDMConentType);
		
		for (clsAssociationDriveMesh oAssDMList : oDMList) {
			rAbsoluteAffect += java.lang.Math.abs(((clsDriveMesh)oAssDMList.getLeafElement()).getPleasure());
		}
		
		return rAbsoluteAffect;
	}
	
	/**
	 * Get the max Affect from an image
	 *
	 * @since 31.08.2011 07:50:44
	 *
	 * @param poImage
	 * @return
	 */

	public static int calculateMaxAffectSecondary(clsWordPresentationMesh poImage) {
		//Precondition: In the TI, the affect values are used
		
		int rThisAffect = 0;
		int rMaxAffect = 0;
		
		try {
			//Get all DriveGoals
			ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oDriveGoals = getWPMDriveGoals(poImage, false);
			for (clsTriple<String, eAffectLevel, clsWordPresentationMesh> oGoal : oDriveGoals) {
				//Get the drive intensity
				rThisAffect = eAffectLevel.valueOf(oGoal.b.toString()).mnAffectLevel;
								
				//Get the max value
				if (rThisAffect>rMaxAffect) {
					rMaxAffect = rThisAffect;
				}	
			}
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		return rMaxAffect ;
	}
	
//	/**
//	 * Get all possible goals from the intention of the perception act.
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 29.07.2011 10:03:27
//	 *
//	 * @param poPredictionInput
//	 * @return
//	 */
//	public static ArrayList<clsSecondaryDataStructureContainer> getDriveGoalsFromPrediction(clsPrediction poPredictionInput) {
//		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//		
//		//Format WP: BITE||ENTITY:CARROT|LOCATION:FARLEFT|NOURISH:HIGH|BITE:HIGH|
//		//Format these drive goals: BITE||IMAGE:A1TOP|ENTITY:CARROT|NOURISH:HIGH|BITE:HIGH|
//		
//		//Get intention
//		clsDataStructureContainerPair oIntention = poPredictionInput.getIntention();
//		if (oIntention.getSecondaryComponent()==null) {
//			return null;
//		}
//		clsSecondaryDataStructureContainer oIntentionSecondary = oIntention.getSecondaryComponent();
//		clsSecondaryDataStructure oIntentionBasicDS = (clsSecondaryDataStructure) oIntentionSecondary.getMoDataStructure();
//		
//		//Get either from the word presentation or from the word presentation mesh
//		if (oIntentionBasicDS instanceof clsSecondaryDataStructure) {
//			//If no Mesh
//			if (oIntentionBasicDS instanceof clsWordPresentation) {
//				try {
//					oRetVal = getDriveGoals((clsWordPresentation)oIntentionBasicDS, oIntentionSecondary);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else if (oIntentionBasicDS instanceof clsWordPresentationMesh) {
//				try {
//					oRetVal = getWPMDriveGoals(oIntentionSecondary, false);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * Extract possible drive goals from a word presentation mesh. If the option keep duplicates is activates, duplicate goals
	 * with different instance ids of the objects are kept.
	 * 
	 * (wendt)
	 *
	 * @since 29.07.2011 14:16:29
	 *
	 * @param poWPInput
	 * @param poContainer
	 * @return
	 * @throws Exception 
	 */
	public static ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> getWPMDriveGoals(clsWordPresentationMesh poImage, boolean pbKeepDuplicates) {
		ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oRetVal = new ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>>();
		ArrayList<clsDataStructurePA> oPrelResult = new ArrayList<clsDataStructurePA>();
		
		//Get a list of associationsecondary, where the root element is the drive object and the leafelement the affect
		ArrayList<clsPair<String, String>> oContentTypeAndContent = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContent.add(new clsPair<String, String>(eContentType.AFFECT.toString(), ""));
		oPrelResult = clsDataStructureTools.getDataStructureInWPM(poImage, eDataType.WP, oContentTypeAndContent, false, 1);
		
		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
		for (clsDataStructurePA oDSPA : oPrelResult) {
			clsAssociationSecondary oAssSec = (clsAssociationSecondary) oDSPA;
			
			//Get the drive
			String oDriveContent = clsAffectTools.getDriveType(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the intensity
			eAffectLevel oAffectLevel = clsAffectTools.getDriveIntensityAsAffectLevel(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the drive object
			clsWordPresentationMesh oGoalObject = (clsWordPresentationMesh) oAssSec.getRootElement();
			
			//Create the goal
			clsTriple<String, eAffectLevel, clsWordPresentationMesh> oGoal = new clsTriple<String, eAffectLevel, clsWordPresentationMesh>(oDriveContent, oAffectLevel, oGoalObject);
			//Check if the drive and the intensity already exists in the list
			if (pbKeepDuplicates==false) {
				boolean bFound = false;
				for (clsTriple<String, eAffectLevel, clsWordPresentationMesh> oTriple : oRetVal) {
					if (oGoal.a == oTriple.a && oGoal.b == oTriple.b && oGoal.c.getMoContent().equals(oTriple.c.getMoContent())) {
						bFound = true;
						break;
					}
				}
				
				if (bFound==false) {
					oRetVal.add(oGoal);
				}
			} else {
				oRetVal.add(oGoal);
			}
		}
		
		
//		if (poContainer.getMoDataStructure() instanceof clsWordPresentationMesh) {
//			//Get possible drive goals from the base structure
//			try {
//				oRetVal.addAll(getDriveGoals((clsSecondaryDataStructure) poContainer.getMoDataStructure(), poContainer));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//Go through the sub elements
//			for (clsAssociation oAss : ((clsWordPresentationMesh)poContainer.getMoDataStructure()).getAssociatedContent()) {
//				clsSecondaryDataStructure oSubDS = (clsSecondaryDataStructure) oAss.getRootElement();	//Get root from the association secondary
//				
//				try {
//					ArrayList<clsSecondaryDataStructureContainer> oReceivedGoals = getDriveGoals(oSubDS, poContainer);
//					//Check if this goal already exists and if it does not, then add the new goal
//					for (clsSecondaryDataStructureContainer oContainer : oReceivedGoals) {
//						boolean blFoundGoal = false;
//						//If the setting keep duplicates is false, then the comparison is made, else the goal is added
//						if (pbKeepDuplicates==false) {
//							for (clsSecondaryDataStructureContainer oRetValContainer : oRetVal) {
//								if (((clsWordPresentation)oRetValContainer.getMoDataStructure()).getMoContent().equals(((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent())) {
//									blFoundGoal = true;
//									break;
//								}
//							}
//						}
//						
//						if (blFoundGoal == false) {
//							oRetVal.add(oContainer);
//						}	
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//		} else {
//			throw new Exception("clsAffectTools, getWPMDriveGoals: This datatype is an unallowed input. Only WPM allowed"); 
//		}
		
		return oRetVal;
	}
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 29.07.2011 14:16:25
//	 *
//	 * @param poWPInput
//	 * @param poContainer
//	 * @return
//	 * @throws Exception 
//	 */
//	private static ArrayList<clsSecondaryDataStructureContainer> getDriveGoals(clsSecondaryDataStructure poInput, clsSecondaryDataStructureContainer poContainer) throws Exception {
//		
//		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//
//		String oExternalContent = "";
//		if (poInput instanceof clsWordPresentation) {
//			oExternalContent = ((clsWordPresentation)poInput).getMoContent();
//		} else if (poInput instanceof clsWordPresentationMesh) {
//			oExternalContent = ((clsWordPresentationMesh)poInput).getMoContent();
//		}
//		else {
//			throw new Exception("clsAffectTools: getDriveGoals: This datatype is an unallowed input");
//		}
//		
//		//Go through all drive goals in the list
//		for (String oDriveGoal : moPossibleDriveGoals) {
//			//Set the input 
//			
//			//Check if the word presentation contains any of the possible drive goals
//			if(oExternalContent.contains(oDriveGoal)) {
//			//Get the base expression
//				String oBaseContent = "";
//				if (poContainer.getMoDataStructure() instanceof clsSecondaryDataStructure) {
//					oBaseContent = ((clsSecondaryDataStructure)poContainer.getMoDataStructure()).getMoContent();
//				}
//				//} else if (poContainer.getMoDataStructure() instanceof clsWordPresentationMesh) {
//				//	oBaseContent = ((clsWordPresentationMesh)poContainer.getMoDataStructure()).getMoContent();
//				//}
//				else {
//					throw new Exception("clsAffectTools, getDriveGoals: This datatype is an unallowed input");
//				}
//			
//				String oBaseContentType = poContainer.getMoDataStructure().getMoContentType();
//			
//				//String oGoalContent = oDriveGoal + _Delimiter02 + oExternalContent;
//				
//				//Format WP: BITE||ENTITY:CARROT|LOCATION:FARLEFT|NOURISH:HIGH|BITE:HIGH|
//				//Format these drive goals: BITE||IMAGE:A1TOP|ENTITY:CARROT|NOURISH:HIGH|BITE:HIGH|
//				
//				String oGoalContent = createSubcontents(oBaseContent, oBaseContentType, oExternalContent, oDriveGoal);
//				
//				//Define data structure for the goal
//				clsWordPresentation oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent)); 
//				//Create an association with that word presentation of that goal
//				clsAssociationSecondary oGoalObjectAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("ASSOCIATIONSEC", oGoal, poInput, ePredicate.HASASSOCIATION.toString(), 1.0);
//				ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
//				oAssociatedDS.add(oGoalObjectAss);
//				//Create the container
//				oRetVal.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
//				
//				//Set Instance ID here
//				for (clsSecondaryDataStructureContainer oDS : oRetVal) {
//					oDS.getMoDataStructure().setMoDSInstance_ID(oDS.getMoDataStructure().hashCode());
//					//clsDataStructureTools.createInstanceFromType(oDS);
//				}
//				
//			}
//		}
//		return oRetVal;
//	}
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 30.07.2011 14:45:39
//	 *
//	 * @param poBaseContent
//	 * @param poBaseContentType
//	 * @param poSubContent
//	 * @param poDriveGoal
//	 * @return
//	 */
//	private static String createSubcontents(String poBaseContent, String poBaseContentType, String poSubContent, String poDriveGoal) {
//		String oRetVal = "";
//		
//		//String oEntityString = "ENTITY";
//		//ENTITY:STONE|LOCATION:FARLEFT|DEPOSIT:LOWPOSITIVE|REPRESS:POSITIVE|
//		
//		String[] oSubContentSplit = poSubContent.split("\\" + _Delimiter03);
//		//Get the object
//		String oDriveObject = oSubContentSplit[0];
//		//Get the object position
//		String oObjectLocation = oSubContentSplit[1];
//		//Get the drive and intensity
//		String oDrive = "";
//		for (String oS : oSubContentSplit) {
//			if (oS.contains(poDriveGoal + ":")) {
//				oDrive = oS; 
//				break;
//			}
//		}
//		
//		if (oDrive.equals("")==true) {
//			try {
//				throw new Exception("Error in clsAffectTools:createSubcontents: A drive must be found");
//			} catch (Exception e) {
//				// TODO (wendt) - Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		//If the content itself is the base content, then nothing has to be done, else the base content must be adapted to an image
//		String oNewBaseContent = "";
//		if (poBaseContent!=poSubContent) {	//i.e. it is a WP
//			oNewBaseContent = poBaseContentType + _Delimiter01  + poBaseContent;
//		}
//
//		//Format these drive goals: BITE||IMAGE:A1TOP|ENTITY:CARROT|BITE:HIGH|
//		//oRetVal = poDriveGoal + _Delimiter02 + oNewBaseContent + _Delimiter03 + poSubContent;
//		oRetVal = poDriveGoal + _Delimiter02 + oNewBaseContent + _Delimiter03 + oDriveObject + _Delimiter03 + oDrive + _Delimiter03;
//		
//		return oRetVal;
//		
//	}
	
	/**
	 * Sort the input list of the drive demands according to max pleasure
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 05.08.2011 22:16:51
	 *
	 * @param poDriveDemandsList
	 */
	public static ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> sortDriveDemands(ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> poDriveDemandsList) {
		ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oRetVal = new ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>>();
		
		//If the list is empty return
		if (poDriveDemandsList.size()<=1) {
			return oRetVal; //nothing to do. either list is empty, or it consists of one lement only
		}
		
		//Set list of drives in the order of drive priority, FIXME KD: Which drives have priority and how is that changed if they have the same affect
		//FIXME CM: What drives do exist????
		//ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
		
		//TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>> oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
		
		ArrayList<clsTriple<Integer, Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>>> oNewList = new ArrayList<clsTriple<Integer, Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>>>();
		
		//Go through the original list
		for (int i=0; i<poDriveDemandsList.size();i++) {	//Go through each element in the list
			//The the content of each drive
			clsTriple<String, eAffectLevel, clsWordPresentationMesh> oGoal = poDriveDemandsList.get(i);
			//Get the content of the datatype in the container
			//String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent();
			
			//Recognize if the string is a drive demand or a drive goal. If it is a drive demand, then do nothing, return the drive demand. If it is a drive goal, 
			//convert to drive demand
			
			//Sort first for affect
			int nAffectValue = eAffectLevel.valueOf(oGoal.b.toString()).mnAffectLevel; //getDriveIntensityAsInt(oContent);
			//Sort the affects for priority according to the order in the list in this class
			int nAffectSortOrder = (moAffectSortOrder.size() - moAffectSortOrder.indexOf(nAffectValue)-1) * 10;
			//Important note: Sorting is made by setting the most significant value (*10), adding them and after that to sort.
			//Sort then for drive according to the order in the list 
			String oDriveType = oGoal.a; //getDriveType(oContent);
			int nDriveIndex = moPossibleDriveGoals.size() - moPossibleDriveGoals.indexOf(oDriveType)-1;	//The higher the better
			
			int nIndex = 0;
			//Increase index if the list is not empty
			while((oNewList.isEmpty()==false) && 
					(nIndex<oNewList.size()) &&
					(oNewList.get(nIndex).a + oNewList.get(nIndex).b > nAffectSortOrder + nDriveIndex)) {
				nIndex++;
			}
			
			oNewList.add(nIndex, new clsTriple<Integer, Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>>(nAffectSortOrder, nDriveIndex, oGoal));
		}
		
		//Add results to the new list
		for (int i=0; i<oNewList.size();i++) {
			oRetVal.add(i, oNewList.get(i).c);
		}
		
		return oRetVal;
	}
	
	/**
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 18:04:40
	 *
	 * @param poSortedPossibleGoalList
	 * @param poSortedFilterList
	 * @param pnNumberOfGoalsToPass
	 * @return
	 */
	public static ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> filterGoals(
			ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> poSortedPossibleGoalList,
			ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> poSortedFilterList, 
			int pnNumberOfGoalsToPass,
			int pnAffectLevelThreshold) {
		
		ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oRetVal = new ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>>();
		
		int nAddedGoals = 0;
		
		//1. Go through the list of drives, which are used as filter
		for (int i=0; i<poSortedFilterList.size();i++) {
			clsTriple<String, eAffectLevel, clsWordPresentationMesh> oDriveGoal = poSortedFilterList.get(i);
			
			ArrayList<clsPair<Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>>> oResultGoalList = new ArrayList<clsPair<Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>>>();
			
			//2. Find those potential goals, which could fullfill the goal from the drive
			for (clsTriple<String, eAffectLevel, clsWordPresentationMesh> oPossibleGoal : poSortedPossibleGoalList) {
				
				//3. If the content is equal
				if ((oDriveGoal.a.equals(oPossibleGoal.a)) && (oDriveGoal.c.getMoContent().equals(oPossibleGoal.c.getMoContent()))) {
					//Sort goals: 1: For drive intensity 2: for PI or RI (Remembered Image)
					
					//Get the level of affect
					int nCurrentAffectLevel = oPossibleGoal.b.mnAffectLevel;
					
					//Create an artificial sort order number
					//The absolute level is taken, as unpleasure counts as much as pleasure
					if (nCurrentAffectLevel>=pnAffectLevelThreshold) {
						//Set sortorder for this image. A PI is taken earlier than a RI
						int nCurrentPISortOrder = 0;
						//get the top image
						clsWordPresentationMesh oTopImage = clsDataStructureTools.getHigherLevelImage(oPossibleGoal.c);
						if (oTopImage==null) {
							try {
								throw new Exception("Error in clsAffectTools: All objects must be associated with images.");
							} catch (Exception e) {
								// TODO (wendt) - Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (oTopImage.getMoContentType().contains(eContentType.PI.toString())==true) {
							nCurrentPISortOrder = 1;
						}
						
						int nTotalCurrentAffectLevel = Math.abs(nCurrentAffectLevel * 10 + nCurrentPISortOrder);
						
						int nIndex = 0;
						//Increase index if the list is not empty
						while((oResultGoalList.isEmpty()==false) && 
								(nIndex<oResultGoalList.size()) &&
								(oResultGoalList.get(nIndex).a > nTotalCurrentAffectLevel)) {
							nIndex++;
						}
						
						oResultGoalList.add(nIndex, new clsPair<Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>>(nTotalCurrentAffectLevel, oPossibleGoal));
					}
				}
			}
			
			//Add all goals to this list
			for (clsPair<Integer, clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oReachableGoal : oResultGoalList) {
				if (nAddedGoals<pnNumberOfGoalsToPass) {
					oRetVal.add(oReachableGoal.b);
					nAddedGoals++;
				} else {
					break;
				}

			}
			
			//If there are no goals, take special actions
			if (oRetVal.size()>=pnNumberOfGoalsToPass) {
				break;
			}
		}	
		
		return oRetVal;
	}
	
	/**
	 * Get drive intensity or affect of a drive as an integer
	 * (wendt)
	 *
	 * @since 05.08.2011 22:30:45
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static int getDriveIntensityAsInt(String poDriveContent) {
		int nIntensity =  0;
		
		eAffectLevel oLevel = getDriveIntensityAsAffectLevel(poDriveContent);
		
		//If it is a drive demand, oDriveIntensity != "", else search for the correct intensity in the string
		if (oLevel!=null) {
			nIntensity = eAffectLevel.valueOf(oLevel.toString()).mnAffectLevel;
		}
		
		return nIntensity;
	}
	
	/**
	 * Get drive intensity or affect of a drive as an integer
	 * (wendt)
	 *
	 * @since 05.08.2011 22:30:45
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static int getDriveIntensityAsInt(eAffectLevel oAffectLevel) {
		int nIntensity =  0;
		
		//eAffectLevel oLevel = getDriveIntensityAsAffectLevel(poDriveContent);
		
		//If it is a drive demand, oDriveIntensity != "", else search for the correct intensity in the string
		if (oAffectLevel!=null) {
			nIntensity = eAffectLevel.valueOf(oAffectLevel.toString()).mnAffectLevel;
		}
		
		return nIntensity;
	}
	
	/**
	 * Get drive intensity or affect of a drive as an Affect level
	 * (wendt)
	 *
	 * @since 13.09.2011 09:35:53
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static eAffectLevel getDriveIntensityAsAffectLevel(String poDriveContent) {
		eAffectLevel oRetVal = null;
		
		String oDrive = poDriveContent.split("\\" + _Delimiter03)[0];
		String[] oDriveSplit = oDrive.split(_Delimiter01);
		String oDriveIntensity = "";
		//If it is a drive demand, then this string is > 1
		if (oDriveSplit.length > 1) {
			oDriveIntensity = oDriveSplit[1];
		} else {
		//else it is a goal
			//Find oDrive
			String[] oSplitParts = poDriveContent.split("\\" + _Delimiter03);
			for (String oE : oSplitParts) {
				if (oE.contains(oDrive + _Delimiter01)) {
					oDriveIntensity = oE.substring(oE.indexOf(_Delimiter01)+1);
					break;
				}
			}
			
		}
		
		oRetVal = eAffectLevel.valueOf(oDriveIntensity);
		
		return oRetVal;
	}
	
	/**
	 * Extract the type of drive like NOURISH, BITE etc... from an input string of drive content
	 * (wendt) - insert description
	 *
	 * @since 05.08.2011 22:33:54
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static String getDriveType(String poDriveContent) {
		String oDrive = poDriveContent.split("\\" + _Delimiter03)[0];
		String oDriveType = oDrive.split(_Delimiter01)[0];

		return oDriveType;
	}
	
//	/**
//	 * Extract the Drive object from an input string of a drive content
//	 * (wendt) - insert description
//	 *
//	 * @since 05.08.2011 22:33:52
//	 *
//	 * @param poDriveContent
//	 * @return
//	 */
//	public static String getDriveObjectType(String poDriveContent) {
//		//The drive content can be either a goal or drive demand
//		String oDriveObject = "";
//		
//		String oDrive = poDriveContent.split("\\" + _Delimiter03)[0];
//		String[] oDriveSplit = oDrive.split(_Delimiter01);
//		
//		//String[] oDrive = poDriveContent.split("\\" + _Delimiter03);
//		if (oDriveSplit.length > 1) {
//			oDriveObject = poDriveContent.split("\\" + _Delimiter03)[1];
//		} else {
//			String[] oDriveGoalSplit = poDriveContent.split("\\" + _Delimiter03);
//			if (oDriveGoalSplit.length>3) {
//				oDriveObject = oDriveGoalSplit[3];
//			}
//		}
//		//String oDriveContentType = oDrive.split(_Delimiter01)[1];
//		//String oDriveObject = oDrive[3];
//		
//		return oDriveObject;
//	}
	
//	/**
//	 * Get the characteristics of affect, which is drive type, affect level and drive object. The input must be a goal
//	 * (wendt)
//	 *
//	 * @since 15.09.2011 10:51:58
//	 *
//	 * @param poContent
//	 * @return
//	 */
//	public static clsTriple<String, eAffectLevel, String> getAffectCharacteristics(String poGoalContent) {
//		clsTriple<String, eAffectLevel, String> oRetVal = null;
//		
//		if (poGoalContent.equals("")==false) {
//			//Get drive type
//			String oReduceDriveType = clsAffectTools.getDriveType(poGoalContent);
//			//Get drive intensity
//			eAffectLevel oReduceDriveIntensity = clsAffectTools.getDriveIntensityAsAffectLevel(poGoalContent);
//			//Get drive object
//			String oReduceDriveObject = clsAffectTools.getDriveObjectType(poGoalContent);
//			
//			if ((oReduceDriveType.equals("") == false) && (oReduceDriveObject.equals("") == false) && (oReduceDriveIntensity != null)) {
//				oRetVal = new clsTriple<String, eAffectLevel, String>(oReduceDriveType, oReduceDriveIntensity, oReduceDriveObject);
//			}
//		}
//		
//		return oRetVal;
//	}
	
//	/**
//	 * Convert a drive goal to a drive demand
//	 * Input Strings
//	 * (wendt)
//	 *
//	 * @since 17.09.2011 08:17:28
//	 *
//	 * @param poDriveGoal
//	 * @return
//	 */
//	public static String convertDriveGoalToDriveDemand(String poDriveGoal) {
//		String oRetVal = "";
//		
//		clsTriple<String, eAffectLevel, String> oDriveContent = getAffectCharacteristics(poDriveGoal);
//		
//		if (oDriveContent.equals("")==false) {
//			oRetVal = oDriveContent.a + _Delimiter01 + oDriveContent.b.toString() + _Delimiter03 + oDriveContent.c;
//		}
//		
//		return oRetVal;
//		
//	}
	
//	/**
//	 * Replace an affect intensity in a goal
//	 * (wendt)
//	 *
//	 * @since 15.09.2011 11:06:21
//	 *
//	 * @param poReplaceGoalString
//	 * @param poIntensity
//	 * @return
//	 */
//	public static String replaceAffectIntensity(String poReplaceGoalString, eAffectLevel poIntensity) {
//		String oRetVal = "";
//		
//		clsTriple<String, eAffectLevel, String> oSourceDrive = getAffectCharacteristics(poReplaceGoalString);
//		if (oSourceDrive!=null) {
//			String oNewContent = poReplaceGoalString.replace(oSourceDrive.a + ":" + oSourceDrive.b.toString(), oSourceDrive.a + ":" + poIntensity.toString());
//			oRetVal += oNewContent;
//		}
//		
//		return oRetVal;
//	}

	
}


