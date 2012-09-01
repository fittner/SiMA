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
import pa._v38.memorymgmt.datatypes.clsAssociationEmotion;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eGoalType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.06.2011, 09:29:24
 * 
 */
public class clsImportanceTools {
	
	//Added by AW, in order to be able to add drive goals from perception and memories
	/** The list of possible drives, sorted regarding importance */
	private static ArrayList<String> moPossibleDriveGoals = new ArrayList<String>(Arrays.asList("DMNourishAggr", "SLEEP", "RELAX", "NOURISH", "BITE", "REPRESS", "DEPOSIT"));	//SLEEP first, as if there is no sleep, the agent cannot do anything
	/** A list of possible affects sorted in the order of importance */
	private static ArrayList<Integer> moAffectSortOrder = new ArrayList<Integer>(Arrays.asList(-3,3,-2, 2, -1, 1,0));	//FIXME AW: Possibly use another solution for sorting
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	
	

	/**
	 * Calculate the average emotion for an image. No filtering used
	 * 
	 * (wendt)
	 *
	 * @since 31.08.2012 12:53:40
	 *
	 * @param poImage
	 * @return
	 */
	public static double calculateAverageImageEmotionalImportance(clsThingPresentationMesh poImage) {
		double rTotalAffect = 0;
		
		ArrayList<clsAssociationEmotion> oEmotionList = clsMeshTools.getAllEmotionsInImage(poImage);
		
		for (clsAssociationEmotion oEmotionAss : oEmotionList) {
			rTotalAffect += java.lang.Math.abs(((clsDriveMesh)oEmotionAss.getLeafElement()).getQuotaOfAffect());
		}
		
		return rTotalAffect/oEmotionList.size();
	}
	
	/**
	 * The average affect of the mesh is calculated for one level (i. e. one image only).
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 20.07.2011 13:58:37
	 *
	 * @param poImage
	 * @return
	 */
	public static double calculateAverageImageAffect(clsThingPresentationMesh poImage, ArrayList<clsDriveMesh> poDMFilterList) {
		double rTotalAffect = 0;
		
		ArrayList<eContentType> oDMContentType = new ArrayList<eContentType>();
		//Get all contenttypes from the DM
		for (clsDriveMesh oDM : poDMFilterList) {
			oDMContentType.add(oDM.getMoContentType());
		}
		
		ArrayList<clsAssociationDriveMesh> oDMList = clsMeshTools.getSelectedDMInImage(poImage, oDMContentType);
		
		for (clsAssociationDriveMesh oAssDMList : oDMList) {
			rTotalAffect += java.lang.Math.abs(((clsDriveMesh)oAssDMList.getLeafElement()).getQuotaOfAffect());
		}
		
		return rTotalAffect/oDMList.size();
	}
	
//	/**
//	 * Get the max Affect from an image
//	 *
//	 * @since 31.08.2011 07:50:44
//	 *
//	 * @param poImage
//	 * @return
//	 */
//
//	public static int calculateMaxAffectSecondary(clsWordPresentationMesh poImage) {
//		//Precondition: In the TI, the affect values are used
//		
//		int rThisAffect = 0;
//		int rMaxAffect = 0;
//		
//		try {
//			//Get all DriveGoals
//			ArrayList<clsWordPresentationMesh> oDriveGoals = getWPMDriveGoals(poImage, eGoalType.NULLOBJECT, false);
//			for (clsWordPresentationMesh oGoal : oDriveGoals) {
//				//Get the drive intensity
//				rThisAffect = clsGoalTools.getAffectLevel(oGoal).mnAffectLevel;
//								
//				//Get the max value
//				if (rThisAffect>rMaxAffect) {
//					rMaxAffect = rThisAffect;
//				}	
//			}
//		} catch (Exception e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return rMaxAffect ;
//	}
	
	/**
	 * Extract possible drive goals from a word presentation mesh. If the option keep duplicates is activates, duplicate goals
	 * with different instance ids of the objects are kept.
	 * 
	 * In this case, the goal object is the supportive data structure!!!
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
	public static ArrayList<clsWordPresentationMesh> getDriveGoalsFromWPM(clsWordPresentationMesh poImage, eGoalType poGoalType, boolean pbKeepDuplicates) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		ArrayList<clsDataStructurePA> oPrelResult = getAllDriveWishAssociationsInImage(poImage);
		
		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
		for (clsDataStructurePA oDSPA : oPrelResult) {
			clsAssociationSecondary oAssSec = (clsAssociationSecondary) oDSPA;
			
			//Get the drive
			String oDriveContent = clsImportanceTools.getDriveType(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the intensity
			eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the drive object
			clsWordPresentationMesh oGoalObject = (clsWordPresentationMesh) oAssSec.getRootElement();
			
			clsWordPresentationMesh oGoal = clsGoalTools.createGoal(oDriveContent, poGoalType, oAffectLevel, oGoalObject, clsMeshTools.createImageFromEntity(oGoalObject, eContentType.PERCEPTIONSUPPORT));

			//Check if the drive and the intensity already exists in the list
			if (pbKeepDuplicates==false) {
				boolean bFound = false;
				for (clsWordPresentationMesh oGoalTriple : oRetVal) {
					if (clsGoalTools.getGoalName(oGoal) == clsGoalTools.getGoalName(oGoalTriple) && 
							clsGoalTools.getAffectLevel(oGoal) == clsGoalTools.getAffectLevel(oGoalTriple) && 
							clsGoalTools.getGoalObject(oGoal).getMoContent().equals(clsGoalTools.getGoalObject(oGoalTriple).getMoContent())) {
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
		
		return oRetVal;
	}
	
	/**
	 * Extract possible drive goals from a word presentation mesh. If the option keep duplicates is activates, duplicate goals
	 * with different instance ids of the objects are kept.
	 * 
	 * The supportive data structure has to be provided here
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
	public static ArrayList<clsWordPresentationMesh> getDriveGoalsFromWPM(clsWordPresentationMesh poImage, eGoalType poGoalType, clsWordPresentationMesh poSupportiveDataStructure, boolean pbKeepDuplicates) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		ArrayList<clsDataStructurePA> oPrelResult = getAllDriveWishAssociationsInImage(poImage);
		
		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
		for (clsDataStructurePA oDSPA : oPrelResult) {
			clsAssociationSecondary oAssSec = (clsAssociationSecondary) oDSPA;
			
			//Get the drive
			String oDriveContent = clsImportanceTools.getDriveType(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the intensity
			eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the drive object
			clsWordPresentationMesh oGoalObject = (clsWordPresentationMesh) oAssSec.getRootElement();
			
			clsWordPresentationMesh oGoal = clsGoalTools.createGoal(oDriveContent, poGoalType, oAffectLevel, oGoalObject, poSupportiveDataStructure);

			//Check if the drive and the intensity already exists in the list
			if (pbKeepDuplicates==false) {
				boolean bFound = false;
				for (clsWordPresentationMesh oGoalTriple : oRetVal) {
					if (clsGoalTools.getGoalName(oGoal) == clsGoalTools.getGoalName(oGoalTriple) && 
							clsGoalTools.getAffectLevel(oGoal) == clsGoalTools.getAffectLevel(oGoalTriple) && 
							clsGoalTools.getGoalObject(oGoal).getMoContent().equals(clsGoalTools.getGoalObject(oGoalTriple).getMoContent())) {
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
		
		return oRetVal;
	}
	
	/**
	 * Get all Drivewishes from an image in the SP
	 * 
	 * (wendt)
	 *
	 * @since 24.07.2012 22:39:55
	 *
	 * @param poImage
	 * @return
	 */
	private static ArrayList<clsDataStructurePA> getAllDriveWishAssociationsInImage(clsWordPresentationMesh poImage) {
		ArrayList<clsDataStructurePA> oPrelResult = new ArrayList<clsDataStructurePA>();
		//Get a list of associationsecondary, where the root element is the drive object and the leafelement the affect
		ArrayList<clsPair<eContentType, String>> oContentTypeAndContent = new ArrayList<clsPair<eContentType, String>>();
		oContentTypeAndContent.add(new clsPair<eContentType, String>(eContentType.AFFECT, ""));
		oPrelResult = clsMeshTools.getDataStructureInWPM(poImage, eDataType.WP, oContentTypeAndContent, false, 1);
		
		return oPrelResult;
	}
	
	
	/**
	 * Sort the input list of the drive demands according to max pleasure
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 05.08.2011 22:16:51
	 *
	 * @param poDriveDemandsList
	 */
	public static ArrayList<clsWordPresentationMesh> sortGoals(ArrayList<clsWordPresentationMesh> poDriveDemandsList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		//If the list is empty return
		if (poDriveDemandsList.isEmpty()) {
			return oRetVal; //nothing to do. either list is empty, or it consists of one lement only
		}
		
		//Set list of drives in the order of drive priority, FIXME KD: Which drives have priority and how is that changed if they have the same affect
		//FIXME CM: What drives do exist????
		//ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
		
		//TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>> oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
		
		ArrayList<clsTriple<Integer, Integer, clsWordPresentationMesh>> oNewList = new ArrayList<clsTriple<Integer, Integer, clsWordPresentationMesh>>();
		
		//Go through the original list
		for (int i=0; i<poDriveDemandsList.size();i++) {	//Go through each element in the list
			//The the content of each drive
			clsWordPresentationMesh oGoal = poDriveDemandsList.get(i);
			//Get the content of the datatype in the container
			//String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent();
			
			//Recognize if the string is a drive demand or a drive goal. If it is a drive demand, then do nothing, return the drive demand. If it is a drive goal, 
			//convert to drive demand
			
			//Sort first for affect
			int nAffectValue = clsGoalTools.getAffectLevel(oGoal).mnAffectLevel; //getDriveIntensityAsInt(oContent);
			//Sort the affects for priority according to the order in the list in this class
			int nAffectSortOrder = (moAffectSortOrder.size() - moAffectSortOrder.indexOf(nAffectValue)-1) * 10;
			//Important note: Sorting is made by setting the most significant value (*10), adding them and after that to sort.
			//Sort then for drive according to the order in the list 
			String oDriveType = clsGoalTools.getGoalName(oGoal); //getDriveType(oContent);
			int nDriveIndex = moPossibleDriveGoals.size() - moPossibleDriveGoals.indexOf(oDriveType)-1;	//The higher the better
			
			int nIndex = 0;
			//Increase index if the list is not empty
			while((oNewList.isEmpty()==false) && 
					(nIndex<oNewList.size()) &&
					(oNewList.get(nIndex).a + oNewList.get(nIndex).b > nAffectSortOrder + nDriveIndex)) {
				nIndex++;
			}
			
			oNewList.add(nIndex, new clsTriple<Integer, Integer, clsWordPresentationMesh>(nAffectSortOrder, nDriveIndex, oGoal));
		}
		
		//Add results to the new list
		for (int i=0; i<oNewList.size();i++) {
			oRetVal.add(i, oNewList.get(i).c);
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
	
	public static void setImportance(clsWordPresentation poImportanceWP, int pnImportance) {
		poImportanceWP.setMoContent(String.valueOf(pnImportance));
	}
	
	public static void addAbsoulteImportance(clsWordPresentation poImportanceWP, int pnAbsoluteImportance) {
		int nOriginalImportance = getImportance(poImportanceWP);
		
		if (nOriginalImportance<0) {
			nOriginalImportance =- pnAbsoluteImportance;
		} else {
			nOriginalImportance =+ pnAbsoluteImportance;
		}
		
		setImportance(poImportanceWP, nOriginalImportance);
	}
	
	public static int getImportance(clsWordPresentation poImportanceWP) {
		return Integer.valueOf(poImportanceWP.getMoContent());
	}
	
	public static int getAbsoluteImportanceFromAffectLevel(clsWordPresentation poImportanceWP) {
		return Math.abs(convertAffectLevelToImportance(getAffectLevel(poImportanceWP)));
	}
	
	public static eAffectLevel getAffectLevel(clsWordPresentation poWP) {
		return eAffectLevel.valueOf(poWP.getMoContent());
	}
	
	public static int convertAffectLevelToImportance(eAffectLevel poAffectLevel) {
		int nResult = 0;
		
		if (Math.abs(poAffectLevel.mnAffectLevel)==3) {
			nResult =  80;
		} else if (Math.abs(poAffectLevel.mnAffectLevel)==2) {
			nResult =  40;
		} else if (Math.abs(poAffectLevel.mnAffectLevel)==1) {
			nResult =  10;
		}
		
		if (poAffectLevel.mnAffectLevel<0) {
			nResult = nResult * (-1);
		}
		
		return nResult;
	}
	
	/**
	 * Sort and filter any list with rates in integer format.
	 * 
	 * This sort is used to sort images, which are rated for importance. The biggest value first
	 * 
	 * (wendt)
	 *
	 * @since 09.07.2012 15:02:22
	 * 
	 * @param pnNumberOfAllowedObjects: -1 for no filtering, any positive value for filtering
	 * @param poInput
	 * @return
	 */
	public static <E extends Object> ArrayList<clsPair<Integer, E>> sortAndFilterRatedStructures(ArrayList<clsPair<Integer, E>> poInput, int pnNumberOfAllowedObjects) {
		ArrayList<clsPair<Integer, E>>oResult = new ArrayList<clsPair<Integer, E>>();
		
		for (clsPair<Integer, E> oP : poInput) {
			int nIndex = 0;
			//Increase index if the list is not empty
			while((oResult.isEmpty()==false) && 
					(nIndex<oResult.size()) &&
					(oResult.get(nIndex).a > oP.a)) {
				nIndex++;
			}
			
			oResult.add(nIndex, oP);
			
			if (pnNumberOfAllowedObjects!=-1 && oResult.size()==pnNumberOfAllowedObjects) {
				break;
			}
		}
		
		return oResult;
	}

	
}


