/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_8_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;

/**
 * Demands provided by reality, drives, and Superego are merged. The result is evaluated regarding which resulting wish can be used as motive for an action tendency. The list of produced motives is ordered according to their satisability. 
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 * 
 */
/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 31.07.2011, 14:13:58
 * 
 */
public class F26_DecisionMaking extends clsModuleBaseKB implements 
			 I6_2_receive, I6_3_receive, I6_7_receive, I6_8_send {
	public static final String P_MODULENUMBER = "26";
	
	/** Perception IN */
	//private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	//private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	/** List of drive goals IN; @since 07.02.2012 19:10:20 */
	private ArrayList<clsWordPresentationMesh> moReachableGoalList_IN;
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:07 */
	private ArrayList<clsWordPresentationMesh> moDecidedGoalList_OUT;
	
	private ArrayList<clsWordPresentationMesh> moDriveGoalList_IN;
	
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:01 */
	private ArrayList<clsAct> moRuleList; 
	
	private clsShortTermMemory moShortTermMemory;
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:03 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
//	
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:03 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT;
//	
//	//AW 20110602 Added expectations, intentions and the current situation
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:05 */
//	private ArrayList<clsPrediction> moExtractedPrediction_IN;
//
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:05 */
//	private ArrayList<clsPrediction> moExtractedPrediction_OUT;
	
//	/** Associated memories IN */
//	//private ArrayList<clsDataStructureContainerPair> moAssociatedMemories_IN;
//	
//	/** Associated memories OUT */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemories_OUT;
	
	// Anxiety from F20
	private ArrayList<clsPrediction> moAnxiety_Input;
	
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	
	/** Number of goals to pass */
	private int mnNumberOfGoalsToPass = 1;
	
	/** Threshold for letting through drive goals */
	private int mnAffectThresold = 1;	//Everything with an affect >= MEDIUM is passed through
	
	
	private int mnAvoidIntensity = -1;
	
	/**
	 * DOCUMENT (kohlhauser) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:51:33
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F26_DecisionMaking(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsShortTermMemory poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage) throws Exception {
		
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		applyProperties(poPrefix, poProp);	
		
		//Get short time memory
		moShortTermMemory = poShortTimeMemory;
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
		String text ="";
		
		text += toText.listToTEXT("moReachableGoalList_IN", moReachableGoalList_IN);
		//text += toText.listToTEXT("moExtractedPrediction_IN", moExtractedPrediction_IN);
		text += toText.listToTEXT("moRuleList", moRuleList);
		//text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moDriveGoalList_IN", moDriveGoalList_IN);
		//text += toText.listToTEXT("moExtractedPrediction_OUT", moExtractedPrediction_OUT);
		
		text += toText.listToTEXT("moAnxiety_Input", moAnxiety_Input);
		
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
	 * @author kohlhauser
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 * 
	 * this module sends the perception input to module E27, E28 just bypasses the information and sends an additional counter which is not used
	 *  
	 * 
	 */
	@Override
	protected void process_basic() {
		//HZ Up to now it is possible to define the goal by a clsWordPresentation only; it has to be 
		//verified if a clsSecondaryDataStructureContainer is required.
		
		//Get all potential goals
		//ArrayList<clsWordPresentationMesh> oPotentialGoals = extractReachableDriveGoals(moPerceptionalMesh_IN, moExtractedPrediction_IN);
		//Add drivedemands from potential goals, which shall be avoided
		//ArrayList<clsWordPresentationMesh> moExtendedDriveList = moGoalList_IN;
		//moExtendedDriveList.addAll(getAvoidDrives(oPotentialGoals));		//THIS PART IS DONE BY THE EMOTIONS NOW
		
		//From the list of drives, match them with the list of potential goals
		moDecidedGoalList_OUT = processGoals(moReachableGoalList_IN, moDriveGoalList_IN, moRuleList);
		
		//Add the goal to the mental situation
		if (moDecidedGoalList_OUT.isEmpty()==false) {
			addGoalToMentalSituation(moDecidedGoalList_OUT.get(0));
		}
		
		
		
		//Pass PI to Planning
//		try {
//			moPerceptionalMesh_OUT = (clsWordPresentationMesh)moPerceptionalMesh_IN.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//Pass the prediction to the planning
		//moExtractedPrediction_OUT = (ArrayList<clsPrediction>)deepCopy(moExtractedPrediction_IN);
		
		//Pass the associated memories forward
		//moAssociatedMemories_OUT = (ArrayList<clsWordPresentationMesh>)deepCopy(moAssociatedMemories_IN);
	}


	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 * 
	 * by this interface a set of reality information, filtered by E24 (reality check), is received
	 * fills moRealityPerception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_7(ArrayList<clsWordPresentationMesh> poReachableGoalList) {
		moReachableGoalList_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poReachableGoalList); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 * 
	 * TODO cua implement
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_2(ArrayList<clsSecondaryDataStructureContainer> poAnxiety_Input) {
		moAnxiety_Input = (ArrayList<clsPrediction>)deepCopy(poAnxiety_Input);	
		//TODO
		
	}
	
	/* (non-Javadoc)
	 *
	 * @since 04.07.2012 10:02:08
	 * 
	 * @see pa._v38.interfaces.modules.I6_3_receive#receive_I6_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMesh> poDriveList) {
		moDriveGoalList_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poDriveList);
	}
	
	private void addGoalToMentalSituation(clsWordPresentationMesh poGoal) {
		//get the ref of the current mental situation
		clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
		
		clsMentalSituationTools.setGoal(oCurrentMentalSituation, poGoal);
	}

	
	
	private ArrayList<clsWordPresentationMesh> processGoals(
			ArrayList<clsWordPresentationMesh> poPossibleGoalInputs, 
			ArrayList<clsWordPresentationMesh> poDriveList, 
			ArrayList<clsAct> poRuleList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		int nAddedGoals = 0;
		
		//1. Process goals with Superego???

		//2. Sort the goals to get the most important goal first
		//=== Sort and evaluate them === //
		ArrayList<clsWordPresentationMesh> oSortedReachableGoalList = clsGoalTools.sortGoals(moReachableGoalList_IN, moDriveGoalList_IN, mnAffectThresold);
				
		
		
		
		
//		ArrayList<clsWordPresentationMesh> oDriveListSorted = poDriveList; //clsAffectTools.sortDriveDemands(poDriveList);
//		//3. Go through the drive list
//		//The first drive is the one with the highest priority
//		for (int i=0; i<oDriveListSorted.size();i++) {
//			clsWordPresentationMesh oDriveGoal = oDriveListSorted.get(i);
//			
//			String oDriveGoalContent = clsGoalTools.getGoalContent(oDriveGoal);
//			eAffectLevel oDriveGoalAffectLevel = clsGoalTools.getAffectLevel(oDriveGoal);
//			clsWordPresentationMesh oDriveGoalObject = clsGoalTools.getGoalObject(oDriveGoal);
//			//String oDriveGoalContent = ((clsWordPresentation)oDriveGoal.getMoDataStructure()).getMoContent();
//			
//			//Get drive characteristics
//			//ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oDriveCharacteristics = clsAffectTools.getAffectCharacteristics(oDriveGoalContent);
//			
//			//
//			ArrayList<clsPair<Integer, clsWordPresentationMesh>> oReachableGoalList = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
//			
//			for (clsWordPresentationMesh oPossibleGoal : poPossibleGoalInputs) {
//
//				String oPossibleGoalContent = clsGoalTools.getGoalContent(oPossibleGoal);
//				eAffectLevel oPossibleGoalAffectLevel = clsGoalTools.getAffectLevel(oPossibleGoal);
//				clsWordPresentationMesh oPossibleGoalObject = clsGoalTools.getGoalObject(oPossibleGoal);
//				//Get goal content
//				//String oPossibleDriveGoalContent = ((clsWordPresentation)oObjectContianer.getMoDataStructure()).getMoContent();
//				//Get drive characteristics of the possible goals
//				//clsTriple<String, eAffectLevel, String> oReachableGoalCharacteristics = clsAffectTools.getAffectCharacteristics(oPossibleDriveGoalContent);
//				
//				//If the drive and the object is found, add this content to the list from the possible drive goals from memory and perception
//				if ((oDriveGoalContent.equals(oPossibleGoalContent)) && (oDriveGoalObject.getMoContent().equals(oPossibleGoalObject.getMoContent()))) {
//					//Sort goals: 1: For drive intensity 2: for PI or RI (Remembered Image)
//					
//					//Set sortorder for this image. A PI is taken earlier than a RI
//					int nCurrentPISortOrder = 0;
//					//get the top image
//					clsWordPresentationMesh oTopImage = clsMeshTools.getSuperStructure(oPossibleGoalObject);
//					if (oTopImage==null) {
//						try {
//							throw new Exception("Error in F26: All objects must be associated with images.");
//						} catch (Exception e) {
//							// TODO (wendt) - Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					if (oTopImage.getMoContentType().contains(eContentType.PI.toString())==true) {
//						nCurrentPISortOrder = 1;
//					}
//					//Get the level of affect
//					int nCurrentAffectLevel = oPossibleGoalAffectLevel.mnAffectLevel;
//					
//					//Create an artificial sort order number
//					//The absolute level is taken, as unpleasure counts as much as pleasure
//					int nTotalCurrentAffectLevel = Math.abs(nCurrentAffectLevel * 10 + nCurrentPISortOrder);
//					
//					int nIndex = 0;
//					//Increase index if the list is not empty
//					while((oReachableGoalList.isEmpty()==false) && 
//							(nIndex<oReachableGoalList.size()) &&
//							(oReachableGoalList.get(nIndex).a > nTotalCurrentAffectLevel)) {
//						nIndex++;
//					}
//					
//					oReachableGoalList.add(nIndex, new clsPair<Integer, clsWordPresentationMesh>(nTotalCurrentAffectLevel, oPossibleGoal));
//
////					//Sortposition
////					int nSortPosition = oReachableGoalList.size();
////					for (int nSortIndex=0; nSortIndex<oReachableGoalList.size(); nSortIndex++) {
////						//Get target Affectlevel
////						int nAddedAffectLevel = clsAffectTools.getDriveIntensityAsInt(((clsSecondaryDataStructure)oReachableGoalList.get(nSortIndex).getMoDataStructure()).getMoContent());
////						//Get if target is a PI or RI
////						int nAddedPISortOrder = 0;
////						if (((clsSecondaryDataStructure)oReachableGoalList.get(nSortIndex).getMoDataStructure()).getMoContent().contains("PERCEIVEDIMAGE")==true) {
////							nAddedPISortOrder = 1;
////						}
////						
////						//Set the sort position
////						if ((nCurrentAffectLevel>=nAddedAffectLevel) && ((nCurrentPISortOrder >= nAddedPISortOrder))) {
////							nSortPosition = nSortIndex;
////							break;
////						}
////					}					
////					
////					//Add the container according to the sort position
////					oReachableGoalList.add(nSortPosition, oObjectContianer);
//				}
//			}
			
			//Add all goals to this list
			for (clsWordPresentationMesh oReachableGoal : oSortedReachableGoalList) {
				if (nAddedGoals<mnNumberOfGoalsToPass) {
					oRetVal.add(oReachableGoal);
					nAddedGoals++;
				} else {
					break;
				}

			}
//			
//			//If there are no goals, take special actions
//			if (oRetVal.size()>=mnNumberOfGoalsToPass) {
//				//Remove all goals > 3
//				
//				break;	
//				//-3 = HIGHNEGATIVE or +3 HIGHPOSITIVE then, special treatment
//			} else {
//				//If the absolute intensity is equal to HIGHXXX, then ...
//				if ((Math.abs(oDriveGoalAffectLevel.mnAffectLevel)>=1) && (oReachableGoalList.isEmpty()==true)) {
//					//If the drive does have Affect = HIGHPOS or HIGHNEG, if the next drive does exist and also have an affect = VERY HIGH
//					if (i+1<oDriveListSorted.size()) {
//						//if (clsAffectTools.getDriveIntensityAsInt(((clsWordPresentation)oDriveListSorted.get(i+1).getMoDataStructure()).getMoContent())<3) {
//							//If the Drive intensity is very high and the following drives do not have an Affect=HIGHPOS or HIGHNEG, 
//							//then a drive goal must be constructed without an object
//							clsWordPresentationMesh oNecessaryDrive = oDriveGoal;
//							//This container is always != null
//							oRetVal.add(oNecessaryDrive);
//							//break;
//							nAddedGoals++;
//						//}
//					} 
//				}
//			}
//		}
		return oRetVal;
	}
	
	/**
	 * Get all possible reachable goals
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 05.08.2011 22:06:21
	 *
	 * @param poPerception
	 * @param poExtractedPrediction_IN
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> extractReachableDriveGoals(clsWordPresentationMesh poPerception, ArrayList<clsPrediction> poExtractedPrediction_IN) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		//Add Goals from the perception
		oRetVal.addAll(compriseExternalPerception(poPerception));
		
		//Add goals from activated memories
		oRetVal.addAll(comprisePrediction(poExtractedPrediction_IN));
		
		return oRetVal;
	}
	

	
	/*private void sortGoalOutput() throws Exception {
		//TD this function sorts moGoal_Output such that the moset pleasureful goals are listed first. this
		//is a feature-fix. none of the later modules make a selection of the best plan currently. they just
		//select what is first in list.
		
		if (moGoal_Output.size()<=1) {
			return; //nothing to do. either list is empty, or it consists of one lement only
		}
		
		//TD everything in here is a bad hack - problem is, i don't understand clsSecondaryDataStructureContainer ...
		HashMap<String, Double> oValues = new HashMap<String, Double>();
		oValues.put("VERYLOW", 0.0);
		oValues.put("LOW", 0.25);
		oValues.put("MEDIUM", 0.5);
		oValues.put("HIGH", 0.75);
		oValues.put("VERYHIGH", 1.0);
		ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
		
		
		TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer> > oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
		for (int i=0; i<moGoal_Output.size(); i++) {
			
			clsSecondaryDataStructureContainer oSDSC = moGoal_Output.get(i);
			String oDesc = oSDSC.toString();
			String[] oEntries = oDesc.split("\\|");

			double rPleasure = 0;
			int found = 0;
			for (String oE:oEntries) {
				String[] oParts = oE.split(":");
				int len = oParts.length;
				if (len != 2) {
					continue;
				}
				
				if (oKeyWords.contains(oParts[0])) {
					double rTemp = oValues.get(oParts[1]);
					rPleasure += rTemp;
					found++;
				}
			}
			
			if (found != 2 && found != 1) {
				throw new java.lang.Exception("could not find one or two drive demand validations in '"+oDesc+"'. candidates are: "+oKeyWords);
			}
			
			rPleasure /= found;
			
			
			
			ArrayList<clsSecondaryDataStructureContainer> oAL;
			if (oSortedList.containsKey(rPleasure)) {
				oAL = oSortedList.get(rPleasure);
			} else {
				oAL = new ArrayList<clsSecondaryDataStructureContainer>();
				oSortedList.put(rPleasure, oAL);
			}
			oAL.add(oSDSC);
		}
		
	//	badVoodoo(oSortedList); //FIXME (kohlhauser): TD 2011/05/01 - bad voodoo!!!
		
		moGoal_Output.clear();
		NavigableSet<Double> oSLdKS = oSortedList.descendingKeySet();
		Iterator<Double> it = oSLdKS.iterator();
		while (it.hasNext()) {
			Double oKey = it.next();
			ArrayList<clsSecondaryDataStructureContainer> oList = oSortedList.get(oKey);
			for (clsSecondaryDataStructureContainer oTemp:oList) {
				moGoal_Output.add(oTemp);
			}
		}
	}*/
	
	/*private void badVoodoo(TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer> > poSortedList) {
		//FIXME : remove this method!!!
		//TD 2011/05/01 - remove nourish or bit if sleep, repress, deposit, relax is at the same importance level
		//but nothing is visible - very bad voodoo! the problem is that if the agent is very hungry he is doing nothing
		//else any more than search for food!!!!
		
		//check first if nothing is in the reality perception list - precondition for this bad voodoo!!!
		if (moRealityPerception.size() > 0) {
			return; //nothing to do!
		}
		
		ArrayList<clsSecondaryDataStructureContainer> oList = poSortedList.get( poSortedList.lastKey() );
		ArrayList<clsSecondaryDataStructureContainer> oDeleteCandidates = new ArrayList<clsSecondaryDataStructureContainer>();
		
		if (oList.size() > 1) { // if only one entry present, the other drives are not as important!
			for (int i=0; i<oList.size(); i++) {
				clsSecondaryDataStructureContainer oSDSC = oList.get(i);
				clsWordPresentation oWP = (clsWordPresentation)oSDSC.getMoDataStructure();
				String oText = oWP.toString();
				if (oText.contains("GOAL:BITE") || oText.contains("GOAL:NOURISH")) {
					oDeleteCandidates.add(oSDSC);
				}
			}
		}
		
	}*/
	
	
	
	/**
	 * DOCUMENT (kohlhauser) -
	 *
	 * @author kohlhauser
	 * 27.08.2010, 15:28:53
	 * @return 
	 *
	 */
	private ArrayList<clsWordPresentationMesh> compriseExternalPerception(clsWordPresentationMesh poExternalPerception) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		// HZ 2010.08.27: This method selects a goal on the base of input parameters. Up to now
		// these inputs are restricted to I_2.13 and I_1.7. As I_2.13 gives an image about the
		// actual situation and I_1.7 retrieves an ordered list of actual "needs" in the form of 
		// drives. Here it is iterated through the drive list and the need is compared with the 
		// list of focused external perception. In case a match is found between an external perception
		// and a drive (both of highest priority), the goal is formed to decrease the drive
		// by the use of the externally perceived e.g. object. If no match turns up in the 
		// highest priority, the iteration is going on until a match has been found. 
		// However if there is no match between the externally perceived objects and the 
		// received drives, the drive is buffered. In case its priority is the highest one, 
		// the decision can be done, that exactly this drive has to be satisfied even there
		// is no object in the area right now that can be used to do this. Hence the goal 
		// would be to roam around and find an object that can be used to satisfy the drive. 
		
		//new AW 20110807
		//get the secondary structure
		ArrayList<clsWordPresentationMesh> oDriveGoals = new ArrayList<clsWordPresentationMesh>();
		
		oDriveGoals = clsImportanceTools.getWPMDriveGoals(poExternalPerception, false);
		
		oRetVal.addAll(oDriveGoals);
	
		
		//ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
		//String oGoalContent; 
		//clsWordPresentation oGoal = null; 
		
		//FIXME HZ Actually the highest rated drive content is taken => this is sloppy and has to be evaluated in a later version! 
		//AW 20110807: The best goals are assigned later
		//clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		
		//if(oMaxDemand != null){
		//	String oDriveContent = oMaxDemand.a; 
		//	clsSecondaryDataStructureContainer oDriveContainer = oMaxDemand.b; 
					
//		for (clsSecondaryDataStructureContainer oExternalPerception : poExternalPerception ){
//				String oExternalContent = ((clsWordPresentation)oExternalPerception.getMoDataStructure()).getMoContent(); 
//								
//					//TODO HZ: Here the first match is taken and added as goal to the output list; Actually
//					// only one goal is selected!
//					//Attention: the first part of the string (index 0 until the first string sequence "||" ) defines the drive that has to be
//					// satisfied by the object outside; in case there is no adequate object perceived, the variable oContent is defined
//					// only by the first part.
//				if(oExternalContent.contains(oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)))){
//					oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)) + _Delimiter02 + oExternalContent; 
//					oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent)); 
//					oAssociatedDS.addAll(oExternalPerception.getMoAssociatedDataStructures()); 
//					oAssociatedDS.addAll(oDriveContainer.getMoAssociatedDataStructures()); 
//						
//					oRetVal.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
//			}
//		}
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 18.04.2011, 23:12:13
	 *
	 */
//	private void compriseRuleList() {
//		//HZ 16.04.2011 - Here, the evaluation of received SUPER-Ego rules must be incorporated to the goal decision
//		//obsolete for v38
////		for(clsAct oRule : moRuleList){
////			
////			int nUnpleasureIntensity = getUnpleasureIntensity (oRule); 
////			ArrayList<clsSecondaryDataStructureContainer> oObsoleteGoals = getObsoleteGoals (nUnpleasureIntensity, oRule); 
////			ArrayList<clsSecondaryDataStructureContainer> oObsoleteDrives = getObsoleteDrives(oRule); 
////			deleteObsoleteGoals(oObsoleteGoals); 
////			deleteObsoleteDrives(oObsoleteDrives); 
////		}
//	}
	
//	/**
//	 * DOCUMENT (kohlhauser) - insert description
//	 *
//	 * @author kohlhauser
//	 * 25.04.2011, 15:02:31
//	 *
//	 * @param oObsoleteDrives
//	 */
//	private void deleteObsoleteDrives(
//			ArrayList<clsSecondaryDataStructureContainer> poObsoleteDrives) {
//		
//		for(clsSecondaryDataStructureContainer oObsoleteDrive : poObsoleteDrives){
//			moDriveList.remove(oObsoleteDrive); 
//		}
//	}
//
//	/**
//	 * DOCUMENT (kohlhauser) - insert description
//	 *
//	 * @author kohlhauser
//	 * 25.04.2011, 14:54:15
//	 *
//	 * @param oRule
//	 * @return
//	 */
//	private ArrayList<clsSecondaryDataStructureContainer> getObsoleteDrives(clsAct poRule) {
//
//		ArrayList <clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//		String oRuleContent = poRule.getMoContent().substring(0, poRule.getMoContent().indexOf(_Delimiter03));
//		
//		//FIXME (kohlhauser): TD 2011/04/30 bad hack - it seems that a drive demand is removed as soon as there
//		//exists a superego rule for that drive. deposit has been removed regardless of the current drive tension
//		//and the entered unpleasure in the superego rule. 
//		//bugfix: extract unpleasure intesity from string and get nRuleIntensisty. the same is done for each drive
//		//from drivelist. as soon as drive names match AND the drives intensity is larger than the unpleasure of
//		//the superego rule, the drive is NOT added to the obsolete drives list!
//		int pos = poRule.getMoContent().indexOf("CONTENT:UNPLEASURE|INTENSITY:")+"CONTENT:UNPLEASURE|INTENSITY:".length();
//		String oRuleUnpleasure = poRule.getMoContent().substring(pos);
//		oRuleUnpleasure = oRuleUnpleasure.substring(0, oRuleUnpleasure.indexOf("|"));
//		int nRuleIntensity = eAffectLevel.valueOf(oRuleUnpleasure).mnAffectLevel;
//		
//		for(clsSecondaryDataStructureContainer oDrive : moDriveList){
//			String[] oTemp = ((clsWordPresentation)oDrive.getMoDataStructure()).getMoContent().split(":");
//			String oDriveContent = oTemp[0];
//			String oDriveUnpleasure = oTemp[1];
//			int nDriveIntensity = eAffectLevel.valueOf(oDriveUnpleasure).mnAffectLevel;
//						
//			if(oDriveContent.contains(oRuleContent) && nDriveIntensity<=nRuleIntensity){ 
////			if(oDriveContent.contains(oRuleContent)){
//				//TD 2011/04/30: remove drive from list iff the drives instensity is eauql or lower than the punishment of the superego rule
//				oRetVal.add(oDrive); 
//			}
//		}
//		
//		return oRetVal; 
//	}
//
//	/**
//	 * DOCUMENT (kohlhauser) - insert description
//	 *
//	 * @author kohlhauser
//	 * 19.04.2011, 14:40:54
//	 * @param poRule 
//	 *
//	 * @param nUnpleasureIntensity
//	 * @return
//	 */
//	private ArrayList<clsSecondaryDataStructureContainer> getObsoleteGoals(int pnUnpleasureIntensity, clsAct poRule) {
//		ArrayList <clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//		
//		for(clsSecondaryDataStructureContainer oEntry : moGoal_Output){
//			String oGoal = ((clsWordPresentation)oEntry.getMoDataStructure()).getMoContent();
//			String oGoalContent = (String)oGoal.subSequence(0, oGoal.indexOf(_Delimiter02)); 
//			String oRuleContent = poRule.getMoContent().substring(0, poRule.getMoContent().indexOf(_Delimiter03)); 
//						
//			for(clsSecondaryDataStructureContainer oDrive : moDriveList){
//				String oDriveContent = ((clsWordPresentation)oDrive.getMoDataStructure()).getMoContent();
//				String oDriveDemand = oDriveContent.substring(oDriveContent.indexOf(_Delimiter01) + 1); 
//				
//				if(oDriveContent.contains(oGoalContent) && oGoal.contains(oRuleContent)){
//					if(eAffectLevel.valueOf(oDriveDemand).mnAffectLevel - pnUnpleasureIntensity <= 0){
//						oRetVal.add(oEntry); 
//					}
//				}
//			}
//		}
//		
//		return oRetVal; 
//	}
//
//	/**
//	 * DOCUMENT (kohlhauser) - insert description
//	 *
//	 * @author kohlhauser
//	 * 19.04.2011, 14:38:48
//	 *
//	 * @param oRule
//	 * @return
//	 */
//	private int getUnpleasureIntensity(clsAct poRule) {
//		ArrayList <clsSecondaryDataStructure> oContentList = poRule.getMoAssociatedContent(); 
//		int oRetVal = 0; 
//		
//		//Read out the Unpleasure intensity
//		for(clsSecondaryDataStructure oContent : oContentList){
//			if(oContent instanceof clsWordPresentation && ((clsWordPresentation)oContent).getMoContent().contains("UNPLEASURE")){
//				String oUnpleasure = ((clsWordPresentation)oContentList.get(oContentList.indexOf(oContent) + 1)).getMoContent();
//				oRetVal = eAffectLevel.valueOf(oUnpleasure).mnAffectLevel; 
//			}
//		}
//		
//		return oRetVal;
//	}
//
//	/**
//	 * DOCUMENT (kohlhauser) - insert description
//	 *
//	 * @author kohlhauser
//	 * 19.04.2011, 14:44:00
//	 *
//	 * @param oObsoleteGoals
//	 */
//	private void deleteObsoleteGoals(ArrayList<clsSecondaryDataStructureContainer> poObsoleteGoals) {
//		
//		for(clsSecondaryDataStructureContainer oObsoleteGoal : poObsoleteGoals){
//			moGoal_Output.remove(oObsoleteGoal); 
//		}
//	}
	
	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 19.04.2011, 07:17:23
	 *
	 */
	private clsTriple<String, eAffectLevel, clsWordPresentationMesh> compriseDrives(clsTriple<String, eAffectLevel, clsWordPresentationMesh> oDriveContainer) {
	   // In case moGoal_output was not filled, the drive with the highest priority used as output
		clsTriple<String, eAffectLevel, clsWordPresentationMesh> oRetVal = null;
		
		ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
		//clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		
		//if(oMaxDemand != null) { //HZ if-statment should be obsolete in case input parameters are adapted
		//String oDriveContent = ((clsWordPresentation)oDriveContainer.getMoDataStructure()).getMoContent(); 
			//clsSecondaryDataStructureContainer oDriveContainer = oMaxDemand;
			
			//if( moGoal_Output.size() == 0 ){
		//String oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)) + _Delimiter02; 
		//clsWordPresentation oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent));
		//oAssociatedDS.addAll(oDriveContainer.getMoAssociatedDataStructures()); 
		//oRetVal = new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS);
		
		return oRetVal;
	}
	

	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @author kohlhauser
	 * 02.11.2010, 17:04:15
	 * @param moDriveList2
	 * @return
	 */
	/*private clsPair<String, clsSecondaryDataStructureContainer> getDriveMaxDemand() {
		clsPair <String, clsSecondaryDataStructureContainer> oRetVal = null;
		
		TreeMap<Integer, ArrayList< clsPair<String, clsSecondaryDataStructureContainer>>> oSortedDrives = new TreeMap<Integer, ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>>();	
		
		//fill oSortedDrives. the result is the drives grouped by their intensity
		for(clsSecondaryDataStructureContainer oContainer : moDriveList){
			String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent(); 
			clsPair<String, clsSecondaryDataStructureContainer> oVal = new clsPair<String, clsSecondaryDataStructureContainer>(oContent, oContainer);
			
			//String oDriveIntensity = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent().split(_Delimiter01)[1];
			//int nIntensity = eAffectLevel.valueOf(oDriveIntensity).ordinal();
			int nIntensity = getDriveIntensity(((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent());
			
			ArrayList< clsPair<String, clsSecondaryDataStructureContainer> > oList;
			if (oSortedDrives.containsKey(nIntensity)) {
				oList = oSortedDrives.get(nIntensity);
			} else {
				oList = new ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>();
				oSortedDrives.put(nIntensity, oList);
			}
			
			oList.add(oVal);
		}
		
		//select set with highest intensity - treemap is sorted ascending -> the last entry == highest intensity
		ArrayList< clsPair<String, clsSecondaryDataStructureContainer> > oList = new ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>(); 
		
		if(oSortedDrives.size() > 0){
			oList = oSortedDrives.lastEntry().getValue();
		}
		
		if (oList.size() == 1) { //case oList.size() == 0 is dealt with throw statement at the end of the method
			//trivial case
			oRetVal = oList.get(0);
		} else if (oList.size() > 1){
			//priorize sleep. currently, the agent cannot die -> sleeping is more important than eating.
			//TD 2011/05/01 - added deposit and repress
			//FIXME (kohlhauser)- this function is bad voodoo
			ArrayList<String> oPriorityDrives = new ArrayList<String>( Arrays.asList("SLEEP", "RELAX", "DEPOSIT", "REPRESS") );
			
			oRetVal = null;
			for (clsPair<String, clsSecondaryDataStructureContainer> oEntry:oList) {
				String oContent = oEntry.a;

				for (String oP:oPriorityDrives) {
					if (oContent.contains(oP)) {
						oRetVal = oEntry;
						break;
					}
				}
				if (oRetVal != null) {
					break; //can't break through two loops with a single break statement.
				}
			}
			if (oRetVal == null) {
				//no entries matching one of the values of oPriorityDrives could be found -> select first entry of list (as good as random) 
				oRetVal = oList.get(0);
			}
		}
		
		if(oRetVal == null){
			//HZ - should be reactivated in case input parameters are adapted
			//throw new NullPointerException("no drive demand found -- impossible"); 
		}
		
		return oRetVal; 
	}*/
	
	/**
	 * Use expectations as goals. They have the correct acts in their associated memories.
	 * (wendt)
	 *
	 * @since 23.07.2011 14:01:12
	 *
	 * @param poExtractedPrediction_IN
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> comprisePrediction(ArrayList<clsPrediction> poExtractedPrediction_IN) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		//Get the expectations of the acts, and make them to goals
		
		//Get the drive with the higest value
		//clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		
		//find all drive components in the predictions
		for (clsPrediction oPrediction : poExtractedPrediction_IN) {
			//Extract drives from the intention, where they may trigger an action
			ArrayList<clsWordPresentationMesh> oFoundGoals = new ArrayList<clsWordPresentationMesh>(); //FIXME AW: Changed due to new mesh structure clsAffectTools.getDriveGoalsFromPrediction(oPrediction);
			//Add corrective reduce factor
			//FIXME AW: Deactivated due to mesh structure
			//modifyGoalAffectWithCorrectiveFactor(oFoundGoals,  oPrediction.getIntention().getSecondaryComponent());
			
			
			
			//Merge goals for the whole image
			//The expectation shall be set as associated structure. If the expectation is known, the agent can act upon that, else it has to
			// execute the associated action
			//ArrayList<clsSecondaryDataStructureContainer> oImageGoal = mergeImageGoals(oFoundGoals, (clsWordPresentationMesh) oPrediction.getIntention().getSecondaryComponent().getMoDataStructure());
			
			//Add the correct actions for the goals from the expectations
//			for (ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> oGoalContainer : oFoundGoals) {
//				//Add all expectations (ArrayList)
//				//For each expectation, add the associated content of the secondary container. By doing this, the associations with the acts are passed.
//				//Create an association between the drive goal and the intention. This intention is searched in the prediction in generation of plans
//				clsAssociationSecondary oNewIntentionAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("ASSOCIATIONSEC", oGoalContainer.getMoDataStructure(), 
//						oPrediction.getIntention().getSecondaryComponent().getMoDataStructure(), ePredicate.HASINTENTION.toString(), 1.0);
//				oGoalContainer.getMoAssociatedDataStructures().add(oNewIntentionAss);
//				oRetVal.add(oGoalContainer);
//
//			}
			
		}
		return oRetVal;
	}
	
//	/**
//	 * If there is a reduceaffect attached to the intention, it is added to its corresponding goal. The goal itself is modified.
//	 * (wendt)
//	 *
//	 * @since 15.09.2011 11:18:41
//	 *
//	 * @param poGoalContainerList
//	 * @param poIntention
//	 */
//	private void modifyGoalAffectWithCorrectiveFactor(ArrayList<clsSecondaryDataStructureContainer> poGoalContainerList,  clsSecondaryDataStructureContainer poIntention) {
//		//ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//		//Get additional corrective factors
//		ArrayList<clsSecondaryDataStructure> oReduceAffectList = clsPredictionTools.getReduceAffect(poIntention);
//		//Go through all extracted affects
//		for (clsSecondaryDataStructure oReduceWP : oReduceAffectList) {
//			//Get drive characteristics
//			clsTriple<String, eAffectLevel, String> oReduceAffectParts = clsAffectTools.getAffectCharacteristics(oReduceWP.getMoContent());
//			
//			//Go through all drive goals from the intention
//			for (clsSecondaryDataStructureContainer oGoalContainer : poGoalContainerList) {
//				clsWordPresentation oGoalWP = (clsWordPresentation) oGoalContainer.getMoDataStructure();
//				//Get the affact characteristics of the goal
//				clsTriple<String, eAffectLevel, String> oGoalAffectParts = clsAffectTools.getAffectCharacteristics(oGoalWP.getMoContent());
//				//Compare if they are the same goal
//				if ((oGoalAffectParts.a.equals(oReduceAffectParts.a) == true) && (oGoalAffectParts.c.equals(oReduceAffectParts.c) == true)) {
//					//calculate the new affect intensity
//					int nNewAffectIntensity = oReduceAffectParts.b.mnAffectLevel + oGoalAffectParts.b.mnAffectLevel;
//					if (nNewAffectIntensity>3 || nNewAffectIntensity<-3) {
//						try {
//							throw new Exception("Error in F26: ReduceAffect:" + oReduceAffectParts.b.mnAffectLevel + ", GoalAffect: " + oGoalAffectParts.b.mnAffectLevel);
//						} catch (Exception e) {
//							// TODO (wendt) - Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					//Replace the old goal intensity
//					//System.out.print("\n" + oGoalWP.getMoContent());
//					//System.out.print(nNewAffectIntensity);
//					String oNewGoalContent = clsAffectTools.replaceAffectIntensity(oGoalWP.getMoContent(), eAffectLevel.elementAt(nNewAffectIntensity));
//					//Replace the old goal content
//					oGoalWP.setMoContent(oNewGoalContent);
//					
//					break;
//				}
//			}
//		}
//	}
	
	/**
	 * Search the perception or memories for goals with very strong negative affect. These object are converted to drive demands
	 * and put on the to of the priolist. This forces the agent to avoid these objects
	 * (wendt)
	 *
	 * @since 17.09.2011 08:27:41
	 *
	 * @param poPotentialGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> getAvoidDrives(ArrayList<clsWordPresentationMesh> poPotentialGoalList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsWordPresentationMesh oGoal : poPotentialGoalList) {
			String oGoalContent = clsGoalTools.getGoalContent(oGoal); //((clsSecondaryDataStructure)oGoal.getMoDataStructure()).getMoContent();
			int nDriveIntensity = clsImportanceTools.getDriveIntensityAsInt(clsGoalTools.getAffectLevel(oGoal));
			
			if (nDriveIntensity<=mnAvoidIntensity) {
				//String oDriveDemand = clsAffectTools.convertDriveGoalToDriveDemand(oGoalContent);
				//clsWordPresentation oDriveWP = clsDataStructureGenerator.generateWP(new clsPair<String, Object>("DRIVEDEMAND", oDriveDemand));
				//clsSecondaryDataStructureContainer oNewGoalContainer = new clsSecondaryDataStructureContainer(oDriveWP, new ArrayList<clsAssociation>());
				//oRetVal.add(oNewGoalContainer);
				oRetVal.add(oGoal);
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * From all of the goals in the list, they shall all be merged by type, in order to pass a complete content, of what can be found in the intention.
	 * 
	 * 
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 29.07.2011 21:21:35
	 *
	 * @param oFoundGoals
	 * @return
	 */
	/*private ArrayList<clsSecondaryDataStructureContainer> mergeImageGoals(ArrayList<clsSecondaryDataStructureContainer> oFoundGoals, clsWordPresentationMesh poTopImage) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		//Create the ImageBase. This content shall be added to all 
		String oContentBase = poTopImage.getMoContent();
		
		//For each found expression, build the goals
		for (clsSecondaryDataStructureContainer oGoal : oFoundGoals) {
			
		}
		
		return oRetVal;
		
	}*/
	

	

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_8(moDecidedGoalList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_1_send#send_I7_1(java.util.HashMap)
	 */
	@Override
	public void send_I6_8(ArrayList<clsWordPresentationMesh> poDecidedGoalList_OUT) {
		((I6_8_receive)moModuleList.get(52)).receive_I6_8(poDecidedGoalList_OUT);
		
		putInterfaceData(I6_8_send.class, poDecidedGoalList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (kohlhauser) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (kohlhauser) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:51:39
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
	 * 20.04.2011, 08:46:04
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Demands provided by reality, drives, and Superego are merged. The result is evaluated regarding which resulting wish can be used as motive for an action tendency. The list of produced motives is ordered according to their satisability.";
	}




	
}

