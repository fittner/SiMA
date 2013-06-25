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
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.eRadius;

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
	//AGGRESSIVESTOMACH, LIBIDINOUSSTOMACH, AGGRESSIVESTAMINA, LIBIDINOUSSTAMINA, AGGRESSIVERECTUM, LIBIDINOUSRECTUM, LIBIDINOUSLIBIDO]
	private static final ArrayList<String> moPossibleDriveGoals = new ArrayList<String>(Arrays.asList("LIBIDINOUSSTAMINA", "AGGRESSIVESTAMINA", "LIBIDINOUSSTOMACH", "AGGRESSIVESTOMACH", "LIBIDINOUSRECTUM", "AGGRESSIVERECTUM", "LIBIDINOUSLIBIDO"));	//SLEEP first, as if there is no sleep, the agent cannot do anything
	/** A list of possible affects sorted in the order of importance */
	private static final String _Delimiter01 = ":"; 
	private static final String _Delimiter02 = "||";
	private static final String _Delimiter03 = "|";
	
	private static final double rDriveEmotionValueRelation = 0.5;
	
//	private static int[] mnConversionArray[] = {{-3, -700}, 
//											{-2, -500},
//											{-1, -200},
//											{0, 0},
//											{1, 200},
//											{2, 500},
//											{3, 700},
//										   };

	/**
	 * Calculate the importance of an image based on its emotions, the current drive state and the drive representations contained
	 * in the image.
	 * 
	 * (wendt)
	 *
	 * @since 04.03.2013 08:53:36
	 *
	 * @param poImage
	 * @param poDrivesForFilteringList
	 * @return
	 */
	public static double calculateImageImportance(clsThingPresentationMesh poImage, ArrayList<clsDriveMesh> poDrivesForFilteringList) {
		double rTotalAffect = 0;
		
		double rDriveAffect = calculateAverageImageDriveImportance(poImage, poDrivesForFilteringList);
		double rEmotionAffect = calculateAverageImageEmotionalImportance(poImage); 
		
		rTotalAffect = rDriveEmotionValueRelation * rDriveAffect + (1-rDriveEmotionValueRelation) * rEmotionAffect; 
		
		return rTotalAffect;
		
	}
	
	/**
	 * Calculate the average emotional component and the drive component for an image. Drives filtering used
	 * 
	 * (wendt)
	 *
	 * @since 31.08.2012 12:53:40
	 *
	 * @param poImage
	 * @return
	 */
	private static double calculateAverageImageEmotionalImportance(clsThingPresentationMesh poImage) {
		double rTotalAffect = 0;
		
		ArrayList<clsAssociationEmotion> oEmotionList = clsMeshTools.getAllEmotionsInImage(poImage);
		
		for (clsAssociationEmotion oEmotionAss : oEmotionList) {
			rTotalAffect += java.lang.Math.abs(((clsEmotion)oEmotionAss.getLeafElement()).getMrEmotionIntensity());
		}
		
		double rNormedAffect = 0.0;
		if (oEmotionList.isEmpty()==false) {
			rNormedAffect = rTotalAffect/oEmotionList.size();
		}
		
		return rNormedAffect;
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
	private static double calculateAverageImageDriveImportance(clsThingPresentationMesh poImage, ArrayList<clsDriveMesh> poDMFilterList) {
		double rResult = 0;
		double rTotalAffect = 0;
		
		ArrayList<eContentType> oDMContentType = new ArrayList<eContentType>();
		//Get all contenttypes from the DM
		//for (clsDriveMesh oDM : poDMFilterList) {
		//	if (oDMContentType.contains(oDM.getMoContentType())==false) {
		//oDMContentType.add();
				
		//	}
		//}
		
		ArrayList<clsAssociationDriveMesh> oDMList = clsMeshTools.getSelectedDMInImage(poImage, oDMContentType);
		int nCount= 0;
		
		for (clsAssociationDriveMesh oAssDMList : oDMList) {
			
			//Calculate weighted drives
			clsDriveMesh oDM = containsDriveMeshType(poDMFilterList, oAssDMList.getDM());
			if (oDM!=null) {
				double oDriveAffectLevel = oDM.getQuotaOfAffect();
				rTotalAffect += java.lang.Math.abs(((clsDriveMesh)oAssDMList.getLeafElement()).getQuotaOfAffect() * oDriveAffectLevel);
				nCount++;
			}
			
		}
		
		if (nCount==0) {
			rResult = 0;
		} else {
			rResult = rTotalAffect/nCount;
		}
		
		return rResult;
	}
	
	/**
	 * Check if a list of drive meshes contain a certain drive mesh type
	 * 
	 * (wendt)
	 *
	 * @since 04.03.2013 10:38:35
	 *
	 * @param poFindInList
	 * @param poDMCheckIfExist
	 * @return
	 */
	private static clsDriveMesh containsDriveMeshType(ArrayList<clsDriveMesh> poFindInList, clsDriveMesh poDMCheckIfExist) {
		clsDriveMesh oResult = null;
		
		for (clsDriveMesh oDM : poFindInList) {
			if (poDMCheckIfExist.getDriveIdentifier().equals(oDM.getDriveIdentifier())==true) {
				oResult = oDM;
				break;
			}
		}
		
		return oResult;
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
	 * Convert the Quota of Affect together with the emobodiment activation to importance, which is used in the secondary process
	 * 
	 * (wendt)
	 *
	 * @since 18.05.2013 20:58:25
	 *
	 * @param prQoA
	 * @param prEmbodimentActivation
	 * @return
	 */
	public static double convertDMIntensityToImportance(double prQoA, double prEmbodimentActivation) {
	    return prQoA + ( (1-prQoA) * prEmbodimentActivation);
	}
	
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
	public static ArrayList<clsWordPresentationMeshGoal> getDriveGoalsFromWPM(clsWordPresentationMesh poImage, eGoalType poGoalType, boolean pbKeepDuplicates) {
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		ArrayList<clsDataStructurePA> oPrelResult = getAllDriveWishAssociationsInImage(poImage, 1);
		
		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
		for (clsDataStructurePA oDSPA : oPrelResult) {
			clsAssociationSecondary oAssSec = (clsAssociationSecondary) oDSPA;
			
			//Get the drive
			String oDriveContent = ((clsWordPresentationMeshGoal)oAssSec.getLeafElement()).getGoalContentIdentifier(); //.clsImportanceTools.getDriveType(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the intensity
			double oImportance = ((clsWordPresentationMeshGoal)oAssSec.getLeafElement()).getImportance();
			//eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
			
			//Get the drive object
			clsWordPresentationMesh oGoalObject = (clsWordPresentationMesh) oAssSec.getRootElement();
			
			clsWordPresentationMeshGoal oGoal = clsGoalTools.createGoal(oDriveContent, poGoalType, oImportance, eAction.NULLOBJECT, new ArrayList<clsWordPresentationMeshFeeling>(), oGoalObject, clsMeshTools.createImageFromEntity(oGoalObject, eContentType.PERCEPTIONSUPPORT));

			//Check if the drive and the intensity already exists in the list
			if (pbKeepDuplicates==false) {
				boolean bFound = false;
				for (clsWordPresentationMeshGoal oGoalTriple : oRetVal) {
					if (oGoal.getGoalName() == oGoalTriple.getGoalName() && 
					        oGoal.getImportance() == oGoalTriple.getImportance() && 
					        oGoal.getGoalObject().getMoContent().equals(oGoalTriple.getGoalObject().getMoContent())) {
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
	public static ArrayList<clsWordPresentationMeshGoal> getDriveGoalsFromWPM(clsWordPresentationMesh poImage, eGoalType poGoalType, clsWordPresentationMesh poSupportiveDataStructure, boolean pbKeepDuplicates) {
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		ArrayList<clsDataStructurePA> oPrelResult = getAllDriveWishAssociationsInImage(poImage, 1);
		
		//Get feelings from WPM
		ArrayList<clsWordPresentationMeshFeeling> oFeelingsList = clsGoalTools.getFeelingsFromImage(poImage);
		
		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
		for (clsDataStructurePA oDSPA : oPrelResult) {
			clsAssociationSecondary oAssSec = (clsAssociationSecondary) oDSPA;
			
			String oDriveContent = ((clsWordPresentationMeshGoal)oAssSec.getLeafElement()).getGoalContentIdentifier(); //.clsImportanceTools.getDriveType(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
            
            //Get the intensity
            double oImportance = ((clsWordPresentationMeshGoal)oAssSec.getLeafElement()).getImportance();
			
			//Get the drive object
			clsWordPresentationMesh oGoalObject = (clsWordPresentationMesh) oAssSec.getRootElement();
			
			clsWordPresentationMeshGoal oGoal = clsGoalTools.createGoal(oDriveContent, poGoalType, oImportance, eAction.NULLOBJECT, oFeelingsList, oGoalObject, poSupportiveDataStructure);

			//Check if the drive and the intensity already exists in the list
			if (pbKeepDuplicates==false) {
				boolean bFound = false;
				for (clsWordPresentationMeshGoal oGoalTriple : oRetVal) {
					if (oGoal.getGoalName() == oGoalTriple.getGoalName() && 
					        oGoal.getImportance() == oGoalTriple.getImportance() && 
							oGoal.getGoalObject().getMoContent().equals(oGoalTriple.getGoalObject().getMoContent())) {
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
     * Extract possible feeling goals from an act.
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
    public static ArrayList<clsWordPresentationMeshGoal> getFeelingGoalsFromWPM(clsWordPresentationMesh poIntention, clsWordPresentationMesh poSupportiveDataStructureAsAct) {
        ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
        
        //ArrayList<clsDataStructurePA> oPrelResult = getAllDriveWishAssociationsInImage(poImage, 1);
        
        //Get feelings from WPM
        ArrayList<clsWordPresentationMeshFeeling> oFeelingsList = clsGoalTools.getFeelingsFromImage(poIntention);
        
        for (clsWordPresentationMeshFeeling oFeeling : oFeelingsList) {
            //Goal content
            String oFeelingcontent = oFeeling.getMoContent();
            
            //Goal Importance
            //TODO AW: Check if this is correct. Intensity is here not used at all. 
            double oImportance = Math.abs(oFeeling.getPleasure() + oFeeling.getUnpleasure());
            
            eGoalType oGoalType = eGoalType.MEMORYFEELING;
            
            clsWordPresentationMesh oGoalObject = poIntention;
            
            clsWordPresentationMeshGoal oGoal = clsGoalTools.createGoal(oFeelingcontent, oGoalType, oImportance, eAction.NULLOBJECT, oFeelingsList, oGoalObject, poSupportiveDataStructureAsAct);
            
            oRetVal.add(oGoal);
            
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
	private static ArrayList<clsDataStructurePA> getAllDriveWishAssociationsInImage(clsWordPresentationMesh poImage, int pnLevel) {
		ArrayList<clsDataStructurePA> oPrelResult = new ArrayList<clsDataStructurePA>();
		//Get a list of associationsecondary, where the root element is the drive object and the leafelement the affect
		ArrayList<clsPair<eContentType, String>> oContentTypeAndContent = new ArrayList<clsPair<eContentType, String>>();
		oContentTypeAndContent.add(new clsPair<eContentType, String>(eContentType.AFFECT, ""));
		//Get all WPMs
		ArrayList<clsWordPresentationMesh> oAllWPM = clsMeshTools.getAllWPMObjects(poImage, pnLevel);
		for (clsWordPresentationMesh oWPM : oAllWPM) {
		    oPrelResult.addAll(clsMeshTools.searchDataStructureOverAssociation(oWPM, ePredicate.HASAFFECT, 0, true, false));
		}
		
		
		//oPrelResult = clsMeshTools.getDataStructureInWPM(poImage, eDataType.WPM, oContentTypeAndContent, false, 3);
		
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
	public static ArrayList<clsWordPresentationMeshGoal> sortGoals(ArrayList<clsWordPresentationMeshGoal> poDriveDemandsList) {
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		//If the list is empty return
		if (poDriveDemandsList.isEmpty()) {
			return oRetVal; //nothing to do. either list is empty, or it consists of one lement only
		}
		
		//Set list of drives in the order of drive priority, FIXME KD: Which drives have priority and how is that changed if they have the same affect
		//FIXME CM: What drives do exist????
		//ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
		
		//TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>> oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
		
		ArrayList<clsTriple<Double, Double, clsWordPresentationMeshGoal>> oNewList = new ArrayList<clsTriple<Double, Double, clsWordPresentationMeshGoal>>();
		
		//Go through the original list
		for (int i=0; i<poDriveDemandsList.size();i++) {	//Go through each element in the list
			//The the content of each drive
			clsWordPresentationMeshGoal oDriveGoal = poDriveDemandsList.get(i);
			//Get the content of the datatype in the container
			//String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent();
			
			//Recognize if the string is a drive demand or a drive goal. If it is a drive demand, then do nothing, return the drive demand. If it is a drive goal, 
			//convert to drive demand
			
			//Sort first for affect
			double rAffectLevel = oDriveGoal.getImportance();
			//int nEffortLevel = clsGoalTools.getEffortLevel(oGoal); //getDriveIntensityAsInt(oContent);
			double rTotalAffectValue = rAffectLevel;
			//Sort the affects for priority according to the order in the list in this class
			double rAffectSortOrder = Math.abs(rTotalAffectValue)*10; //Just set the absolute affect as the worst one//(moAffectSortOrder.size() -1 - moAffectSortOrder.indexOf(nTotalAffectValue)) * 10;
			//Important note: Sorting is made by setting the most significant value (*10), adding them and after that to sort.
			//Sort then for drive according to the order in the list 
			String oDriveType = oDriveGoal.getGoalName(); //getDriveType(oContent);
			
			double rDriveIndex = (moPossibleDriveGoals.size() - moPossibleDriveGoals.indexOf(oDriveType)-1)/100;	//The higher the better
			
			int nIndex = 0;
			//Increase index if the list is not empty
			while((oNewList.isEmpty()==false) && 
					(nIndex<oNewList.size()) &&
					(oNewList.get(nIndex).a + oNewList.get(nIndex).b > rAffectSortOrder + rDriveIndex)) {
				nIndex++;
			}
			
			oNewList.add(nIndex, new clsTriple<Double, Double, clsWordPresentationMeshGoal>(rAffectSortOrder, rDriveIndex, oDriveGoal));
		}
		
		//Add results to the new list
		for (int i=0; i<oNewList.size();i++) {
			oRetVal.add(i, oNewList.get(i).c);
		}
		
		return oRetVal;
	}
		
	
//	/**
//	 * Extract the type of drive like NOURISH, BITE etc... from an input string of drive content
//	 * (wendt) - insert description
//	 *
//	 * @since 05.08.2011 22:33:54
//	 *
//	 * @param poDriveContent
//	 * @return
//	 */
//	public static String getDriveType(String poDriveContent) {
//		String oDrive = poDriveContent.split("\\" + _Delimiter03)[0];
//		String oDriveType = oDrive.split(_Delimiter01)[0];
//
//		return oDriveType;
//	}
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 21.09.2012 21:05:35
//	 *
//	 * @param poImportanceWP
//	 * @param pnImportance
//	 */
//	public static void setImportance(clsWordPresentation poImportanceWP, int pnImportance) {
//		poImportanceWP.setMoContent(String.valueOf(pnImportance));
//	}
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 21.09.2012 21:05:34
//	 *
//	 * @param poImportanceWP
//	 * @param pnAbsoluteImportance
//	 */
//	public static void addAbsoulteImportance(clsWordPresentation poImportanceWP, int pnAbsoluteImportance) {
//		int nOriginalImportance = getImportance(poImportanceWP);
//		
//		if (nOriginalImportance<0) {
//			nOriginalImportance =- pnAbsoluteImportance;
//		} else {
//			nOriginalImportance =+ pnAbsoluteImportance;
//		}
//		
//		setImportance(poImportanceWP, nOriginalImportance);
//	}
	
//	public static void addImportance(clsWordPresentationMesh poGoal, int pnImportance) {
//		int nOriginalAffectLevel = clsGoalTools.getAffectLevel(poGoal);
//		int nNewAffectLevel = nOriginalAffectLevel + pnImportance;
//		clsGoalTools.setAffectLevel(poGoal, nNewAffectLevel);
//	}
	
//	public static int getImportance(clsWordPresentation poImportanceWP) {
//		return Integer.valueOf(poImportanceWP.getMoContent());
//	}
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 21.09.2012 21:05:30
//	 *
//	 * @param poImportanceWP
//	 * @return
//	 */
//	public static int getAbsoluteImportanceFromAffectLevel(clsWordPresentation poImportanceWP) {
//		return Math.abs(convertAffectLevelToImportance(getAffectLevel(poImportanceWP)));
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 21.09.2012 21:05:28
//	 *
//	 * @param poWP
//	 * @return
//	 */
//	public static eAffectLevel getAffectLevel(clsWordPresentation poWP) {
//		return eAffectLevel.valueOf(poWP.getMoContent());
//	}
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 21.09.2012 21:05:26
//	 *
//	 * @param poAffectLevel
//	 * @return
//	 */
//	public static int convertAffectLevelToImportance(eAffectLevel poAffectLevel) {
//		int nResult = 0;
//		
//		nResult = poAffectLevel.mnAffectLevel;
////		for (int i=0;i<mnConversionArray.length;i++) {
////			if (poAffectLevel.mnAffectLevel==mnConversionArray[i][0]) {
////				
////				nResult = mnConversionArray[i][1];
////				break;
////			}
////		}
//		
//		return nResult;
//	}
	
	
	
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
	public static <E extends Object> ArrayList<clsPair<Double, E>> sortAndFilterRatedStructures(ArrayList<clsPair<Double, E>> poInput, int pnNumberOfAllowedObjects) {
		ArrayList<clsPair<Double, E>>oResult = new ArrayList<clsPair<Double, E>>();
		
		for (clsPair<Double, E> oP : poInput) {
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
	
	public static <E extends Object> ArrayList<clsPair<Double, E>> sortAndFilterRatedStructuresDouble(ArrayList<clsPair<Double, E>> poInput, int pnNumberOfAllowedObjects) {
		ArrayList<clsPair<Double, E>>oResult = new ArrayList<clsPair<Double, E>>();
		
		for (clsPair<Double, E> oP : poInput) {
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
	
	/**
	 * Get a defined value for increasing the pleasure or unpleasure for a goal
	 * 
	 * (wendt)
	 *
	 * @since 01.10.2012 20:22:41
	 *
	 * @param poCondition
	 * @return
	 */
	public static double getEffortValueOfCondition(eCondition poCondition) {
		double nResult = 0;
		
		if (poCondition.equals(eCondition.IS_DRIVE_SOURCE)) {
			nResult+=-0.40;
		} else if (poCondition.equals(eCondition.IS_PERCEPTIONAL_SOURCE)) {
			nResult+= 0;
		} else if (poCondition.equals(eCondition.IS_MEMORY_SOURCE)) {
			nResult+= -0.10;
		} else if (poCondition.equals(eCondition.GOAL_NOT_REACHABLE)) {
			nResult+=-10.00;
		} else if (poCondition.equals(eCondition.IS_CONTINUED_GOAL)) {
			nResult+=0.1;
		} else if (poCondition.equals(eCondition.ACT_MATCH_TOO_LOW)) {
			nResult+=-10.00;
		} else if (poCondition.equals(eCondition.GOAL_COMPLETED)) {
			nResult+=-2.00;
		} else if (poCondition.equals(eCondition.OBSTACLE_SOLVING)) {
			nResult+=0.20;
		}
		
		return nResult;
	}
	
	public static double getEffortValueOfDistance(ePhiPosition poPosition, eRadius poRadius) {
		double nResult = 0;
		
		if (poRadius.equals(eRadius.NEAR)) {
			nResult += 0;
		} else if (poRadius.equals(eRadius.MEDIUM)) {
			nResult += -0.02;
		} else if (poRadius.equals(eRadius.FAR)) {
			nResult += -0.07;
		}
		
		if (poPosition.equals(ePhiPosition.CENTER)) {
			nResult += 0;
		} else if (poPosition.equals(ePhiPosition.MIDDLE_LEFT) || poPosition.equals(ePhiPosition.MIDDLE_RIGHT)) {
			nResult += -0.01;
		} else if (poPosition.equals(ePhiPosition.RIGHT) || poPosition.equals(ePhiPosition.LEFT)) {
			nResult += -0.02;
		}
		
		return nResult;
	}
	
	public static double getEffortValueOfActConfidence(clsWordPresentationMesh poIntention) {
		double nResult = 0;
		
		double rActConfidence = clsActTools.getActConfidenceLevel(poIntention);
		
		if (rActConfidence==1.0) {
			nResult += 0;
		} else if (rActConfidence<1.0 && rActConfidence>=0.5) {
			nResult += -0.02;
		} else if (rActConfidence<0.5) {
			nResult += -0.10;
		}
		
		
		return nResult;
	}
	
    /**
     * DOCUMENT (wendt) - insert description
     *
     * @since 23.05.2013 13:30:24
     *
     * @param poGoal
     */
    public static double getConsequencesOfFeelingsOnGoalAsImportance(clsWordPresentationMeshGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        double rResult = 0;
        //Get Feeling affect
        ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poGoal.getFeelings();
            
        for (clsWordPresentationMeshFeeling oF : oFeelingList) {
            double nAffectFromFeeling = oF.getIntensity();
                
            rResult += nAffectFromFeeling;
        }
        
        return rResult;
    }

}


