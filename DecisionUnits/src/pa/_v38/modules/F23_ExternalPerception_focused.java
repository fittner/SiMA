/**
 * E23_ExternalPerception_focused.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.interfaces.modules.I6_12_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_6_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsSecondarySpatialTools;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * The task of this module is to focus the external perception on ``important'' things. Thus, the word presentations originating from perception are ordered according to their importance to existing drive wishes. This could mean for example that an object is qualified to satisfy a bodily need. The resulting listthe package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importanceis forwarded by the interface {I2.12} to {E24} and {E25}. These two modules are part of reality check. 
 * 
 * TODO (kohlhauser) - consider free energy
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 * 
 */
public class F23_ExternalPerception_focused extends clsModuleBaseKB implements I6_12_receive, I6_3_receive, I6_6_send {
	public static final String P_MODULENUMBER = "23";
	
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	
	private ArrayList<clsWordPresentationMesh> moReachableGoalList_OUT;
	
	
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:35 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
//	//AW 20110602 New input of the module
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:37 */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_IN;
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:39 */
	private ArrayList<clsWordPresentationMesh> moDriveGoalList_IN; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:40 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:56:18 */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_OUT;
	
	private clsShortTermMemory moShortTimeMemory;
	
	/** As soon as DT3 is implemented, replace this variable and value */
	private double mrAvailableFocusEnergy = 5;
	
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F23_ExternalPerception_focused(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsShortTermMemory poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage) throws Exception {
		
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		applyProperties(poPrefix, poProp);	
		
		//Get short time memory
		moShortTimeMemory = poShortTimeMemory;		
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
		//text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		//text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
		text += toText.listToTEXT("moDriveList", moDriveGoalList_IN);
		//text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		//text += toText.listToTEXT("moAssociatedMemoriesSecondary_OUT", moAssociatedMemoriesSecondary_OUT);
		text += toText.valueToTEXT("mrAvailableFocusEnergy", mrAvailableFocusEnergy);
		
		return text;
	}	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	
	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 14:11:16
	 * 
	 * @see pa._v38.interfaces.modules.I6_12_receive#receive_I6_12(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_12(clsWordPresentationMesh poPerception,
			ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
		try {
			moPerceptionalMesh_IN = (clsWordPresentationMesh) poPerception.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		//AW 20110602 Added Associtated memories
		moAssociatedMemories_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poAssociatedMemoriesSecondary);
		
	}	

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMesh> poDriveList) {
		moDriveGoalList_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		//=== Extract all goals from perception and memories ===//
		moReachableGoalList_OUT = new ArrayList<clsWordPresentationMesh>(); 
		
		//Extract all possible goals in the perception
		moReachableGoalList_OUT.addAll(clsGoalTools.extractPossibleGoalsFromPerception(moPerceptionalMesh_IN));
		
		//Extract all possible goals from the images (memories)
		moReachableGoalList_OUT.addAll(extractPossibleGoalsFromActs(moAssociatedMemories_IN));
		
		//--- Select Goals for Perception ---//
		ArrayList<clsPair<Integer,clsWordPresentationMesh>> oFocusOnGoalList = new ArrayList<clsPair<Integer,clsWordPresentationMesh>>();
		
		//Extract the goals with the strongest emotions from the perceptions
		oFocusOnGoalList.addAll(extractStrongestPerceptiveGoals(moReachableGoalList_OUT));
		
		//--- Process actions ---//
		clsWordPresentationMesh oAction = extractPlannedActionFromSTM();
		
		//Extract the goal from the planning
		oFocusOnGoalList.addAll(extractFilterEntitiesFromAction(moPerceptionalMesh_IN, oAction));
		
		//System.out.println(moPerceptionalMesh_IN);
		
		//=== Filter the perception === //
		int nNumberOfAllowedObjects = (int)mrAvailableFocusEnergy;	//FIXME AW: What is the desexualalized energy and how many objects/unit are used.
		moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
		
		//Remove all non focused objects
		focusPerception(moPerceptionalMesh_OUT, oFocusOnGoalList, nNumberOfAllowedObjects);
		
		System.out.println("====================================\nF23: Focused perception:" + moPerceptionalMesh_OUT  + "\n==============================================");
		System.out.println("Focuslist : " + oFocusOnGoalList.toString());
		
		//TODO AW: Memories are not focused at all, only prioritized!!! Here is a concept necessary
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
		
		
		//Everything in the short term memory is conscious. Here, the current mental situation is added to the STM
		moShortTimeMemory.saveToShortTimeMemory(clsMentalSituationTools.createMentalSituation());
		
		
		//=========================================================//
		//TODO AW: In Focus of attention, the most relevant memories can be selected, i. e. the analysis of the current moment, intention and expectation
		//could be done here. It is also a possibility to use the complete perception for this task.
		
		//=========================================================//
		//moAssociatedMemoriesSecondary_OUT = (ArrayList<clsDataStructureContainerPair>)deepCopy(moAssociatedMemoriesSecondary_IN);
	}
	
	/**
	 * Extract goals from the perception, which are emotions and are high negative in order to be 
	 * able to react faster.
	 * 
	 * (wendt)
	 *
	 * @since 24.06.2012 09:24:38
	 *
	 * @param poReachableGoalList
	 * @return
	 */
	private ArrayList<clsPair<Integer, clsWordPresentationMesh>> extractStrongestPerceptiveGoals(ArrayList<clsWordPresentationMesh> poReachableGoalList) {
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oRetVal = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		for (clsWordPresentationMesh oReachableGoal : poReachableGoalList) {
			
			//React on goals in the perception, which are emotion and are HIGH NEGATIVE
			if (clsGoalTools.getSupportiveDataStructureType(oReachableGoal) == eContentType.PI && 
					clsGoalTools.getGoalType(oReachableGoal) == eGoalType.PERCEPTIONALEMOTION &&
					clsGoalTools.getAffectLevel(oReachableGoal) == eAffectLevel.HIGHNEGATIVE) {
				oRetVal.add(new clsPair<Integer, clsWordPresentationMesh>(clsImportanceTools.convertAffectLevelToImportance(clsGoalTools.getAffectLevel(oReachableGoal)), oReachableGoal));
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Extract the last applicable planned goal.
	 * 
	 * (wendt)
	 *
	 * @since 24.06.2012 09:25:22
	 *
	 * @return
	 */
	private clsWordPresentationMesh extractPlannedActionFromSTM() {
		clsWordPresentationMesh oRetVal = null;
		
		clsWordPresentationMesh oLastMentalImage = this.moShortTimeMemory.findPreviousSingleMemory();
		
		if (oLastMentalImage!=null) {
			oRetVal = clsMentalSituationTools.getAction(oLastMentalImage);
		}
		
		return oRetVal;
	}
	
	private ArrayList<clsPair<Integer, clsWordPresentationMesh>> extractFilterEntitiesFromAction(clsWordPresentationMesh poPerceivedImage, clsWordPresentationMesh poActionWPM) {
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oResult  = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		if (poActionWPM.isNullObject()==false) {
			//Extract action
			eAction oAction = eAction.valueOf(poActionWPM.getMoContent());
			
			if (oAction.equals(eAction.FOCUS_MOVE_FORWARD)) {
				//Set focus area in front of the agent, i. e. 
				//1. all entities in CENTER NEAR have the highest priority, add 1000 points. Only a new high negative can overrule this
				//2. all entities in MIDDLE_LEFT NEAR and MIDDLE_RIGHT NEAR have the second highest priority 100 
				//3. all entities in CENTER MEDIUM and CENTER FAR have high priority 
				
				//No supportive data structure is needed
				
				//Extract entities from the single fields and assign them
				oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.NEAR, ePhiPosition.CENTER, 80));
				oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.NEAR, ePhiPosition.MIDDLE_LEFT, 60));
				oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.NEAR, ePhiPosition.MIDDLE_RIGHT, 60));
				oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.MEDIUM, ePhiPosition.CENTER, 40));
				oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.FAR, ePhiPosition.CENTER, 20));
				
				
			} else if (oAction.equals(eAction.FOCUS_TURN_LEFT)) {
				//Set focus area left of the agent
				//1. MIDDLE_LEFT NEAR
				//2. LEFT NEAR
				//3. MIDDLE_LEFT MEDIUM
				//4. LEFT MEDIUM
				
				//No supportive data structure is needed
				
			
			} else if (oAction.equals(eAction.FOCUS_ON)) {
				//All entities in an image get the highest priority
				
				//Use the supportive data structure
				clsWordPresentationMesh oFocusImage = clsActionTools.getSupportiveDataStructure(poActionWPM);
				
				//If there is a supportive data structure
				try {
					if (oFocusImage.getMoContent().equals(eContentType.NULLOBJECT)==true) {
						throw new Exception ("F23: Focused action was chosen but no supportive data structure exists");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//Get all found entities in the perceived image, which matches the entities in the supportive image
				oResult.addAll(getPerceivedImageEntitiesFromImage(poPerceivedImage ,oFocusImage,70));		//Why 70? because 80 is very high importance. 	
			}
		}
		
		
		return oResult;
	}
	
	private static ArrayList<clsPair<Integer, clsWordPresentationMesh>> getPerceivedImageEntitiesFromImage(clsWordPresentationMesh poPerceivedImage, clsWordPresentationMesh poSupportiveImage, int pnImportance) {
		//The instances from the perception are added
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oResult = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		//Get all positions from the supportive image
		ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oSupportiveImageEntityList = clsSecondarySpatialTools.getEntityPositionsInImage(poSupportiveImage);
		
		//Get all positions from the perceived image
		ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oPerceivedImageEntityList = clsSecondarySpatialTools.getEntityPositionsInImage(poPerceivedImage);		
		
		ListIterator<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oPerceivedImageIterator = oPerceivedImageEntityList.listIterator();
		//Find these entities in the perception
		for (clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oSupportiveImageEntity : oSupportiveImageEntityList) {
			
			while (oPerceivedImageIterator.hasNext()) {
				clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPerceivedEntity = oPerceivedImageIterator.next();
				
				if (oSupportiveImageEntity.a.getMoDS_ID() == oPerceivedEntity.a.getMoDS_ID()) {
					//Add to result list
					oResult.add(new clsPair<Integer, clsWordPresentationMesh>(pnImportance, oPerceivedEntity.a));
					
					//Delete from this list
					oPerceivedImageIterator.remove();
				}
			}	
		}
		
		return oResult;
	}
	
	/**
	 * Check all extracted goals for goals, which can emerge from emotions
	 * 
	 * (wendt)
	 *
	 * @since 25.05.2012 20:47:31
	 *
	 * @param poGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> extractEmergentGoalsFromEmotions(ArrayList<clsWordPresentationMesh> poGoalList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsWordPresentationMesh oGoal : poGoalList) {
			if (clsGoalTools.getGoalName(oGoal).equals(eContent.UNKNOWN_GOAL.toString())) {
				oRetVal.add(oGoal);
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Extract all possible goal from acts from their descriptions
	 * 
	 * (wendt)
	 *
	 * @since 25.05.2012 18:52:53
	 *
	 * @param moActList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> extractPossibleGoalsFromActs(ArrayList<clsWordPresentationMesh> moActList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
	
		for (clsWordPresentationMesh oAct : moActList) {
			oRetVal.addAll(clsGoalTools.extractPossibleGoalsFromAct(oAct));
		}
		
		return oRetVal;
	}
	
	
	/**
	 * All drives within the perceived images are extracted and sorted. The drive goal list
	 * and the psychic energy decides how many elements of the PI are passed.
	 * (wendt)
	 *
	 * @since 07.08.2011 23:05:42
	 *
	 * @param poPerceptionSeondary
	 * @return
	 */
	private void focusPerception(clsWordPresentationMesh poPerception, ArrayList<clsPair<Integer, clsWordPresentationMesh>> poPrioritizedGoalList, int  pnNumberOfAllowedObjects) {
		
//		//If there is no consciously selected goal or a very strong emotional component on an entity, then focus on the strongest reachable goals.
//		if (poPrioritizedGoalList.isEmpty()) {
//			//Filter all entities in the perception for these goals.
//			ArrayList<clsWordPresentationMesh> oFilteredGoalList = clsGoalTools.filterGoals(poReachableGoalList, pnNumberOfAllowedObjects);
//					
//			//Filter the PI according to the goal object list
//			filterImageElementsBasedOnReachableGoals(poPerception, oFilteredGoalList);
//		} else {
//			
//		}
		
		//Sort incoming special goals
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oPrioritizedGoalListSorted = clsImportanceTools.sortAndFilterRatedStructures(poPrioritizedGoalList, pnNumberOfAllowedObjects);
		
		//Focus only the select
		filterImageElementsBasedOnPrioritizedGoals(poPerception, oPrioritizedGoalListSorted);
		
	}
	
	
	/**
	 * Filter a PI for elements, which are associted with the goals in a list. 
	 * Remove all entities, which are not selected 
	 * 
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 15.08.2011 22:30:44
	 *
	 * @param poImage - The image, which shall be filtered
	 * @param poGoalList  - All objects, which shall be kept in the perceived image, are put here. 
	 * @return
	 */
	private void filterImageElementsBasedOnPrioritizedGoals(clsWordPresentationMesh poImage, ArrayList<clsPair<Integer, clsWordPresentationMesh>> poEntityList) {
		//clsWordPresentationMesh oRetVal = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(poImage.getMoContentType(), poImage.getMoContent()), new ArrayList<clsAssociation>());
		
		ArrayList<clsWordPresentationMesh> oEntitiesToKeepInPI = new ArrayList<clsWordPresentationMesh>();
		
		//2 cases: entities from PI, entities from complete images
		for (clsPair<Integer, clsWordPresentationMesh> oEntity : poEntityList) {
			//Check if the entity already exists and add it if not
			if (oEntitiesToKeepInPI.contains(oEntity.b)==false) {		
				oEntitiesToKeepInPI.add(oEntity.b);
			}
		}
		
		//Add the self to the image
		oEntitiesToKeepInPI.add(clsMeshTools.getSELF(poImage));
		
		//Remove all other entities
		removeNonFocusedEntities(poImage, oEntitiesToKeepInPI);
	}
		
	//	private void filterImageElementsBasedOnReachableGoals(clsWordPresentationMesh poImage, ArrayList<clsWordPresentationMesh> poGoalList) {
//		//clsWordPresentationMesh oRetVal = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(poImage.getMoContentType(), poImage.getMoContent()), new ArrayList<clsAssociation>());
//		
//		ArrayList<clsWordPresentationMesh> oEntitiesToKeepInPI = new ArrayList<clsWordPresentationMesh>();
//		
//		//2 cases: entities from PI, entities from complete images
//		for (clsWordPresentationMesh oGoal : poGoalList) {
//			if (clsGoalTools.getSupportDataStructureType(oGoal)!=null) {
//				if (clsGoalTools.getSupportDataStructureType(oGoal) == eContentType.PI) {		//If PI
//					//If it is a PI, then the goal data structure is within the current PI
//					
//					clsWordPresentationMesh oGoalObject = clsGoalTools.getGoalObject(oGoal);
//					
//					//Check if the entity already exists and add it if not
//					if (oEntitiesToKeepInPI.contains(oGoalObject)==false) {		
//						oEntitiesToKeepInPI.add(oGoalObject);
//					}
//				}
//			}
//		}
//		
//		//Add the self to the image
//		oEntitiesToKeepInPI.add(clsMeshTools.getSELF(poImage));
//		
//		//Remove all other entities
//		removeNonFocusedEntities(poImage, oEntitiesToKeepInPI);
//	}
	
	
	
	/**
	 * Removes all entities from the image, which are not in the input list
	 * 
	 * (wendt)
	 *
	 * @since 09.07.2012 14:43:03
	 *
	 * @param poImage
	 * @param poEntitiesToKeepInPI
	 */
	private void removeNonFocusedEntities(clsWordPresentationMesh poImage, ArrayList<clsWordPresentationMesh> poEntitiesToKeepInPI) {
		//Remove all entities from the PI, which are not part of the input list
		ArrayList<clsWordPresentationMesh> oRemoveEntities  = clsMeshTools.getOtherInternalImageAssociations(poImage, poEntitiesToKeepInPI);
		for (clsWordPresentationMesh oE : oRemoveEntities) {
			clsMeshTools.removeAssociationInObject(poImage, oE);		//Use this instead as it ONLY removes the association of the entity with the PI
			clsMeshTools.removeAssociationInObject(oE, poImage);
			//clsMeshTools.deleteObjectInMesh(oE);
			//Remove for the primary process too, i. e remove all associationtime in the external associations
			clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(oE);
			clsMeshTools.removeAllTemporaryAssociationsTPM(oTPM);
		}
	}
	
//	/**
//	 * Add the SELF from the source image to the target image
//	 * 
//	 * (wendt)
//	 *
//	 * @since 01.06.2012 12:30:32
//	 *
//	 * @param poTargetImage
//	 * @param poSourceImage
//	 */
//	private void addSELFtoImage(clsWordPresentationMesh poTargetImage, clsWordPresentationMesh poSourceImage) {
//		clsWordPresentationMesh oSELFTargetImage = clsMeshTools.getSELF(poTargetImage);
//		
//		if (oSELFTargetImage == null) {	//SELF not found in the image
//			clsWordPresentationMesh oSELFSourceImage = clsMeshTools.getSELF(poSourceImage);
//			if (oSELFSourceImage!=null) {
//				clsMeshTools.createAssociationSecondary(poTargetImage, 1, oSELFSourceImage, 0, 1.0, eContentType.ASSOCIATIONSECONDARY.toString(), ePredicate.PARTOF.toString(), false);
//			}
//		}
//	}
	
//	private ArrayList<clsSecondaryDataStructureContainer> cleanDriveGoals(ArrayList<clsSecondaryDataStructureContainer> poInputList) {
//		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//		
//		for (clsSecondaryDataStructureContainer oOriginContainer : poInputList) {
//			
//			clsSecondaryDataStructure oInternalDataStructure = (clsSecondaryDataStructure) oOriginContainer.getMoAssociatedDataStructures().getRootElement();
//			//Check if it exists in the list
//			boolean bExists = false;
//			
//			for (clsSecondaryDataStructureContainer oResultContainer : oRetVal) {
//				if (oOriginContainer.equals(oResultContainer)) {
//					bExists = true;
//					break;
//				}
//			}
//			
//			if (bExists==false) {
//				oRetVal.add(oOriginContainer);
//			}
//		}
//		
//		return oRetVal;
//	}
	
//	private clsSecondaryDataStructure getObjectFromDriveGoal(clsSecondaryDataStructureContainer poDriveGoal, clsSecondaryDataStructure poSearchedAssociatedDS) {
//		clsSecondaryDataStructure oRetVal = null;
//		
//		for (clsAssociation oDriveAss : poDriveGoal.getMoAssociatedDataStructures()) {
//			//Get the right type
//			if (oDriveAss instanceof clsAssociationSecondary) {
//				//In this case the leaf element shall be the search data structure, but for safe both are tested
//				if (oDriveAss.getLeafElement().getMoDSInstance_ID() == poSearchedAssociatedDS.getMoDSInstance_ID() ||
//						oDriveAss.getRootElement().getMoDSInstance_ID() == poSearchedAssociatedDS.getMoDSInstance_ID()) {
//					//If the element is found in the drive goals, add it to the new list
//					oNewInternalAssList.add(oAss);
//					//Add the associated data structures of the element
//					oNewContainerAssList.addAll(oRetVal.getMoAssociatedDataStructures(oAss));
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
	
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_6(moPerceptionalMesh_OUT, moReachableGoalList_OUT, moAssociatedMemories_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I6_6(clsWordPresentationMesh poFocusedPerception,
			ArrayList<clsWordPresentationMesh> poReachableGoalList,
			   				ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary_OUT) {
		((I6_6_receive)moModuleList.get(51)).receive_I6_6(poFocusedPerception, poReachableGoalList, poAssociatedMemoriesSecondary_OUT);
		
		putInterfaceData(I6_6_send.class, poFocusedPerception, poReachableGoalList, poAssociatedMemoriesSecondary_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:50:13
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The task of this module is to focus the external perception on ``important'' things. Thus, the word presentations originating from perception are ordered according to their importance to existing drive wishes. This could mean for example that an object is qualified to satisfy a bodily need. The resulting listthe package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importanceis forwarded by the interface {I2.12} to {E24} and {E25}. These two modules are part of reality check.";
	}


	
}
