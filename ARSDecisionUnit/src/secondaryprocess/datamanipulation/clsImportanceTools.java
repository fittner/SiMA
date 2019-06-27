/**
 * CHANGELOG
 *
 * 25.06.2011 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eGoalType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.ePredicate;
import memorymgmt.enums.eRadius;

import org.slf4j.Logger;

import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsAssociationSecondary;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsPair;


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
	//private static final ArrayList<String> moPossibleDriveGoals = new ArrayList<String>(Arrays.asList("LIBIDINOUSSTAMINA", "AGGRESSIVESTAMINA", "LIBIDINOUSSTOMACH", "AGGRESSIVESTOMACH", "LIBIDINOUSRECTUM", "AGGRESSIVERECTUM", "LIBIDINOUSLIBIDO"));	//SLEEP first, as if there is no sleep, the agent cannot do anything
	/** A list of possible affects sorted in the order of importance */
	private static final String _Delimiter01 = ":"; 
	private static final String _Delimiter02 = "||";
	private static final String _Delimiter03 = "|";
	
    // FIXME Kollmann: change impact increase values from hardcoded to parameter
    private static double mrMomentIncrease = 2.0;
    private static double mrLastIncrease = 1.0;
    private static double mrBaseIncrease = 0.5;
	
	private static final double rDriveEmotionValueRelation = 0.5;
	
	private static final Logger log = clsLogger.getLog("clsImportanceTools");
	
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
			rTotalAffect += java.lang.Math.abs(((clsEmotion)oEmotionAss.getLeafElement()).getEmotionIntensity());
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
	 * (schaat)
	 *
	 * @since 18.05.2013 20:58:25
	 *
	 * @param prQoA
	 * @param prEmbodimentActivation
	 * @return
	 */
	public static double convertDMIntensityToImportance(double prQoA, double prEmbodimentActivation, double reinforcementFactor) {
	    return prQoA + ( (1-prQoA)* reinforcementFactor * prQoA * prEmbodimentActivation);
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
	public static ArrayList<clsWordPresentationMeshPossibleGoal> getSelectableGoalsFromWPM(clsWordPresentationMesh poImage, eGoalType poGoalType, clsWordPresentationMesh poSupportiveDataStructure, boolean pbKeepDuplicates) throws Exception {
		ArrayList<clsWordPresentationMeshPossibleGoal> oRetVal = new ArrayList<clsWordPresentationMeshPossibleGoal>();
		ArrayList<clsWordPresentationMeshPossibleGoal> oPrelResult = new ArrayList<clsWordPresentationMeshPossibleGoal> ();
		
		if (poGoalType.equals(eGoalType.PERCEPTIONALDRIVE)) {
		    oPrelResult = getAllDriveWishAssociationsInImage(poImage, 1);
		} else if (poGoalType.equals(eGoalType.MEMORYDRIVE)) {
		    oPrelResult = getAllPossibleGoalsFromImage(poImage, 1);
		} else {
		    log.warn("Unknonwn goal type {}.\nCan not get selectable goals from WPM {}", poGoalType, poImage);
		}
		
		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
		for (clsWordPresentationMeshPossibleGoal originalSelectableGoal : oPrelResult) {
			clsWordPresentationMeshPossibleGoal copyOfSelectableGoal = clsGoalManipulationTools.createSelectableGoal(originalSelectableGoal.getGoalName(), originalSelectableGoal.getGoalSource(), originalSelectableGoal.getPotentialDriveFulfillmentImportance(), originalSelectableGoal.getGoalObject()); 
			
			
			if (poSupportiveDataStructure.isNullObject()==true) {
			    if (poGoalType.equals(eGoalType.PERCEPTIONALDRIVE)) {
			        copyOfSelectableGoal.setSupportiveDataStructure(clsMeshTools.createImageFromEntity(copyOfSelectableGoal.getGoalObject(), eContentType.PERCEPTIONSUPPORT));
			    } else {
			        throw new Exception("Cannot create supportive data structure");
			    }
			    
			} else {
			    //Spezialfall f. Memorygoald abgleich mit Intention->action
			    copyOfSelectableGoal.setSupportiveDataStructure(poSupportiveDataStructure);
			}
			
			if (!poGoalType.equals(eGoalType.PERCEPTIONALDRIVE)) {
			    copyOfSelectableGoal.addFeelings(clsGoalManipulationTools.getFeelingsFromImage(poImage));
			}
			
			//Check if the drive and the intensity already exists in the list
			if (pbKeepDuplicates==false) {
				boolean bFound = false;
				for (clsWordPresentationMeshGoal oGoalTriple : oRetVal) {
					if (copyOfSelectableGoal.getGoalName() == oGoalTriple.getGoalName() && 
					        copyOfSelectableGoal.getTotalImportance() == oGoalTriple.getTotalImportance() && 
					                copyOfSelectableGoal.getGoalObject().getContent().equals(oGoalTriple.getGoalObject().getContent())) {
						bFound = true;
						break;
					}
				}
				
				if (bFound==false) {
					oRetVal.add(copyOfSelectableGoal);
				}
			} else {
				oRetVal.add(copyOfSelectableGoal);
			}
		}
		
		return oRetVal;
	}
	
//	/**
//	 * Extract possible drive goals from a word presentation mesh. If the option keep duplicates is activates, duplicate goals
//	 * with different instance ids of the objects are kept.
//	 * 
//	 * The supportive data structure has to be provided here
//	 * 
//	 * (wendt)
//	 *
//	 * @since 29.07.2011 14:16:29
//	 *
//	 * @param poWPInput
//	 * @param poContainer
//	 * @return
//	 * @throws Exception 
//	 */
//	public static ArrayList<clsWordPresentationMeshGoal> getDriveGoalsFromWPM(clsWordPresentationMesh poImage, eGoalType poGoalType, clsWordPresentationMesh poSupportiveDataStructure, boolean pbKeepDuplicates) {
//		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
//		
//		ArrayList<clsDataStructurePA> oPrelResult = getAllDriveWishAssociationsInImage(poImage, 1);
//		
//		//Get feelings from WPM
//		ArrayList<clsWordPresentationMeshFeeling> oFeelingsList = clsGoalTools.getFeelingsFromImage(poImage);
//		
//		//Convert the result into a drive goal, which is a triple of the drive, the intensity and the drive object
//		for (clsDataStructurePA oDSPA : oPrelResult) {
//			clsAssociationSecondary oAssSec = (clsAssociationSecondary) oDSPA;
//			
//			String oDriveContent = ((clsWordPresentationMeshGoal)oAssSec.getLeafElement()).getGoalContentIdentifier(); //.clsImportanceTools.getDriveType(((clsSecondaryDataStructure)oAssSec.getLeafElement()).getMoContent());
//            
//            //Get the intensity
//            double oImportance = ((clsWordPresentationMeshGoal)oAssSec.getLeafElement()).getTotalImportance();
//			
//			//Get the drive object
//			clsWordPresentationMesh oGoalObject = (clsWordPresentationMesh) oAssSec.getRootElement();
//			
//			clsWordPresentationMeshGoal oGoal = clsGoalTools.createGoal(oDriveContent, poGoalType, oImportance, eAction.NULLOBJECT, oFeelingsList, oGoalObject, poSupportiveDataStructure);
//
//			//Check if the drive and the intensity already exists in the list
//			if (pbKeepDuplicates==false) {
//				boolean bFound = false;
//				for (clsWordPresentationMeshGoal oGoalTriple : oRetVal) {
//					if (oGoal.getGoalName() == oGoalTriple.getGoalName() && 
//					        oGoal.getTotalImportance() == oGoalTriple.getTotalImportance() && 
//							oGoal.getGoalObject().getMoContent().equals(oGoalTriple.getGoalObject().getMoContent())) {
//						bFound = true;
//						break;
//					}
//				}
//				
//				if (bFound==false) {
//					oRetVal.add(oGoal);
//				}
//			} else {
//				oRetVal.add(oGoal);
//			}
//		}
//		
//		return oRetVal;
//	}
	
	
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
	private static ArrayList<clsWordPresentationMeshPossibleGoal> getAllDriveWishAssociationsInImage(clsWordPresentationMesh poImage, int pnLevel) {
	    ArrayList<clsDataStructurePA> oAffects = new ArrayList<clsDataStructurePA>();
	    clsDataStructurePA oPossGoal = null;
		ArrayList<clsWordPresentationMeshPossibleGoal> oPrelResult = new ArrayList<clsWordPresentationMeshPossibleGoal>();
		//Get a list of associationsecondary, where the root element is the drive object and the leafelement the affect
		ArrayList<clsPair<eContentType, String>> oContentTypeAndContent = new ArrayList<clsPair<eContentType, String>>();
		oContentTypeAndContent.add(new clsPair<eContentType, String>(eContentType.AFFECT, ""));
		
		//Get all WPMs
		ArrayList<clsWordPresentationMesh> oAllWPM = clsMeshTools.getAllWPMObjects(poImage, pnLevel);
		for (clsWordPresentationMesh oWPM : oAllWPM) {
		    oAffects = clsMeshTools.searchDataStructureOverAssociation(oWPM, ePredicate.HASAFFECT, 0, true, false);
		    for(clsDataStructurePA oEntry : oAffects) {
		        if(oEntry instanceof clsAssociationSecondary) {
                    oPossGoal = ((clsAssociationSecondary)oEntry).getLeafElement();
                    if(oPossGoal instanceof clsWordPresentationMeshPossibleGoal) {
                        oPrelResult.add((clsWordPresentationMeshPossibleGoal) oPossGoal);
                    }
		        }
		    }
		    
		}
		
		return oPrelResult;
	}
	
	private static ArrayList<clsWordPresentationMeshPossibleGoal> getAllPossibleGoalsFromImage(clsWordPresentationMesh poImage, int pnLevel) {
	    ArrayList<clsDataStructurePA> oAffects = new ArrayList<clsDataStructurePA>();
	    ArrayList<clsDataStructurePA> oFeelings = new ArrayList<clsDataStructurePA>();
        ArrayList<clsWordPresentationMeshPossibleGoal> oPrelResult = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        clsDataStructurePA oPossGoal = null;
        
        //Get a list of associationsecondary, where the root element is the drive object and the leaf element the affect
        ArrayList<clsPair<eContentType, String>> oContentTypeAndContent = new ArrayList<clsPair<eContentType, String>>();
        oContentTypeAndContent.add(new clsPair<eContentType, String>(eContentType.AFFECT, ""));
        
        //Get all WPMs
        ArrayList<clsWordPresentationMesh> oAllWPM = clsMeshTools.getAllWPMObjects(poImage, pnLevel);
        
        for (clsWordPresentationMesh oWPM : oAllWPM) {
            if(oWPM.getDS_ID() != poImage.getDS_ID()) { //the image itself is always returned by getAllWPMObjects
                oAffects = clsMeshTools.searchDataStructureOverAssociation(oWPM, ePredicate.HASAFFECT, 0, true, false);
                if(!oAffects.isEmpty()) {
                    for(clsDataStructurePA oEntry : oAffects) {
                        if(oEntry instanceof clsAssociationSecondary) {
                            oPossGoal = ((clsAssociationSecondary)oEntry).getLeafElement();
                            if(oPossGoal instanceof clsWordPresentationMeshPossibleGoal) {
                                oPrelResult.add((clsWordPresentationMeshPossibleGoal) oPossGoal);
                            }
                        } else {
                            log.warn("Image {} had an affect predicate in an association that is not of type clsAssociationSecondary", poImage);
                        }
                    }
                }
            }
        }
        
        if(oPrelResult.isEmpty()) {
            //The image had no WPM Objects that where associated with drive meshes - this could still be a feasable goal, for example a feeling motivated goal like FLEE
            String oGoalName = "NO_DRIVE";
            clsWordPresentationMeshPossibleGoal oGoal = clsGoalManipulationTools.createSelectableGoal(oGoalName, eGoalType.MEMORYDRIVE, clsWordPresentationMesh.getNullObject());
            oPrelResult.add(oGoal);
        }
        
        return oPrelResult;
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
			nResult+=-0.71;
		} else if (poCondition.equals(eCondition.IS_PERCEPTIONAL_SOURCE)) {
			nResult+= 0.0; 
		} else if (poCondition.equals(eCondition.IS_MEMORY_SOURCE)) {
			nResult-= 0.0;
		} else if (poCondition.equals(eCondition.IS_CONTEXT_SOURCE)) {
            nResult+= 0.0;
		} else if (poCondition.equals(eCondition.GOAL_NOT_REACHABLE)) {
			nResult+=-10.00;
		} else if (poCondition.equals(eCondition.IS_CONTINUED_GOAL)) {
			nResult+= 0.01;
		} else if (poCondition.equals(eCondition.IS_CONTINUED_PLANGOAL)) {
            nResult+= 0.00;
		} else if (poCondition.equals(eCondition.ACT_MATCH_TOO_LOW)) {
			nResult+=-10.00;
		} else if (poCondition.equals(eCondition.GOAL_COMPLETED)) {
			nResult+=-2.00;
		} else if (poCondition.equals(eCondition.OBSTACLE_SOLVING)) {
			nResult+= 1.0;
		} else if (poCondition.equals(eCondition.INSUFFICIENT_PIMATCH_INFO)) {
		    nResult+= 0.0;
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
			nResult += -0.002;
		} else if (rActConfidence<0.5) {
			nResult += -0.005;
		}
		
		
		return nResult;
	}
	
	
	
	public static double getEffortValueOfSpeechActConfidence(clsWordPresentationMesh poIntention) {
        double nResult = 0;
        
        double rActConfidence = clsActTools.getActConfidenceLevel(poIntention);
        
        if (rActConfidence==1.0) {
            nResult += 0;
        } else if (rActConfidence<1.0 && rActConfidence>=0.5) {
            nResult += -0.02;
            //How to change rActConfidence 
        } else if (rActConfidence<0.5) {
            nResult += 0.20;
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
    public static double getConsequencesOfFeelingsOnGoalAsImportance(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        double rResult = 0;
        //Get Feeling affect
        ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poGoal.getFeelings();
            
        for (clsWordPresentationMeshFeeling oF : oFeelingList) {
            double nAffectFromFeeling = oF.getIntensity();
                
            rResult += nAffectFromFeeling;
        }
        
        return rResult;
    }
    
//    /**
//     * DOCUMENT (Kollmann) - Calculates an importance value for a certain goal, if it contains a specified action. There are three cases to consider:
//     *                          - The specified action is equal to the associated action of the current moment
//     *                          - The specified action is equal to the last associated action of the current act
//     *                          - The specified action is associated somewhere else within the act
//     *
//     * @since 24.09.2013 11:42:21
//     *
//     * @param poGoal - Goal to check (if this is not associated with an act -> the function will only return 0)
//     * @param poAction - Action to check against
//     * @return
//     */
//    public static double getImpactOfAim(clsWordPresentationMeshGoal poPossibleGoal, clsWordPresentationMeshGoal poDriveGoal)
//    {
//        throw new RuntimeException("clsImportanceTools::getImpactOfAim is unfinished and should not be used yet");
//        
//        double rResult = 0;
//        
//        log.debug("static double clsImportanceTools::getImpactOfAim(" + poPossibleGoal + ", " + poDriveGoal + ")");
//        
////        clsWordPresentationMesh oActionMesh = poDriveGoal.getgetAssociatedAimAction();
//        clsWordPresentationMesh oActionMesh = null;
//        
//        //get the supportive data structure for the goal
//        clsWordPresentationMesh oSuppDataStructure = poPossibleGoal.getSupportiveDataStructure();
//        
//        if(oSuppDataStructure != null && !oSuppDataStructure.isNullObject()) {
//            log.debug("Supportive data structure " + oSuppDataStructure + " has type " + poPossibleGoal.getSupportiveDataStructureType());
//            
//            //if goal has no intention -> do not evaluate anything else, because poGoal is not an act
//            clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oSuppDataStructure);
//            if(oIntention != null && !oIntention.isNullObject()) {
//                log.debug("Goal " + poPossibleGoal + " has intention " + oIntention);
//                
//                //get the current moment in the act (if there is one) for later comparison
//                clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oSuppDataStructure);
//                
//                //itterate over all sub images and check if they have an associated action
//                ArrayList<clsWordPresentationMesh> oSubImages = clsActTools.getAllSubImages(oIntention);
//
//                for(clsWordPresentationMesh oImage : oSubImages) {
//                    log.debug("Intention " + oIntention + " has image " + oImage);
//                    //extract the images action (if any)
//                    eAction oAction = clsActTools.getRecommendedAction(oImage);
//                    
//                    //check NONE and NULLOBJECT, just in case the usage changes somehow - in both cases there should be no impact increment
//                    if(oAction != eAction.NONE && oAction != eAction.NULLOBJECT) {
//                        log.debug("Image " + oImage + " has action " + oAction);
//                        
//                        eAction oLookupAction = eAction.valueOf(oActionMesh.getMoContent());
//                        
//                        //if the images action fits, start increasing the importance value
//                        if(oLookupAction == oAction) {
//                            rResult += mrBaseIncrease; // this is the base increase for a 'normal' occurance, if it is a special occurance, increase the value later on
//                            
//                            //now check how if the image where the action was found is either the current moment or the last image (before post-condition)
//                            if(oImage == oMoment) {
//                                log.debug("Image " + oImage + " is current moment");
//                                rResult += (mrMomentIncrease - mrBaseIncrease); 
//                            }
//                            
//                            //if the image is the last image
//                            if(clsActTools.isLastImage(oImage)) {
//                                log.debug("Image " + oImage + " is last image");
//                                rResult += (mrMomentIncrease - mrLastIncrease);
//                            } else {
//                                //if it is not the last image, check if it is the second to last image and the last image has no action
//                                clsWordPresentationMesh oNextImage = clsActTools.getNextImage(oImage);
//                                if(clsActTools.isLastImage(oNextImage) == true) {
//                                    eAction oTempAction = clsActTools.getRecommendedAction(oNextImage);
//                                    if(oTempAction == eAction.NULLOBJECT || oTempAction == eAction.NONE) {
//                                        //in this case, the last image is just a post-condition so thread the second to last image like the last image
//                                        log.debug("Image " + oImage + " is last image before post-condition");
//                                        rResult += (mrMomentIncrease - mrLastIncrease);
//                                    }
//                                }
//                            }
//                            log.info("Action match for possible goal " + oSuppDataStructure.getMoContent() + " and drive goal " + poDriveGoal.getGoalName() + " with action " + oLookupAction + ". Increasing importance by " + rResult);
//                            //log.info("Possible goal " + oSuppDataStructure.getMoContent() + " has importance increase by " + rResult + " due to action match for " + oLookupAction + " in " + oImage.getMoContent());
//                        }
//                    }
//                }
//            }
//        }
//        
//        log.debug("clsImportanceTools::getImpactOfAim(...) -> " + rResult);
//        
//        return rResult;
//    }

}


