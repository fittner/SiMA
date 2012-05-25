/**
 * E23_ExternalPerception_focused.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.interfaces.modules.I6_12_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_6_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.storage.clsGoalMemory;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsAffectTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsPair;
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
public class F23_ExternalPerception_focused extends clsModuleBase implements I6_12_receive, I6_3_receive, I6_6_send {
	public static final String P_MODULENUMBER = "23";
	
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	
	
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:35 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
//	//AW 20110602 New input of the module
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:37 */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_IN;
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:39 */
	ArrayList<clsWordPresentationMesh> moDriveGoalList_IN; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:40 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:56:18 */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_OUT;
	
	/** As soon as DT3 is implemented, replace this variable and value */
	private double mrAvailableFocusEnergy = 5;
	
	/** Threshold for letting through drive goals */
	private int mnAffectThresold = 1;	//Everything with an affect >= MEDIUM is passed through
	
	
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
	public F23_ExternalPerception_focused(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);		
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
		text += toText.valueToTEXT("mnAffectThresold", mnAffectThresold);
		
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
		
		//ArrayList<clsWordPresentationMesh> oGoalList = new ArrayList<clsWordPresentationMesh>();
		
		clsGoalMemory moGoalMemory = new clsGoalMemory(60, 4);
		
		ArrayList<clsWordPresentationMesh> oGoalList = new ArrayList<clsWordPresentationMesh>();
		
		//=== Extract all goals from perception, drives, memories and plans ===//
		
		//Extract all possible goals in the perception
		oGoalList.addAll(extractPossibleGoalsForPerception(moPerceptionalMesh_IN));
		
		//Extract all possible goals from the images (memories)
		oGoalList.addAll(extractPossibleGoalsFromActs(moAssociatedMemories_IN));
		
		//Extract possible goals from plans
		
		
		//=== Process drive list ===//
		//Enhance the Drive list with goals from emotions
		//moDriveGoalList_IN.addAll(extractEmergentGoalsFromEmotions(oGoalList));
		
		//Sort the goals
		ArrayList<clsWordPresentationMesh> oSortedGoalList = clsAffectTools.sortGoals(oGoalList, moDriveGoalList_IN, mnAffectThresold);
		
		//=== Filter the perception === //
		int nNumberOfAllowedObjects = (int)mrAvailableFocusEnergy;	//FIXME AW: What is the desexualalized energy and how many objects/unit are used.
		moPerceptionalMesh_OUT = focusPerception(moPerceptionalMesh_IN, oSortedGoalList, nNumberOfAllowedObjects);
				
		//TODO AW: Memories are not focused at all, only prioritized!!! Here is a concept necessary
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
		
		
		/*
		1. E23 aeuﬂere Wahrnehmung (fokussiert)
		1.1 Modulbeschreibung
		Die Wahrnehmung verfuegt ueber freie Energie, mit der sie im aufmerksamen Zustand unterschiedliche Elemente ueberbesetzt[1], d.h. fokussiert.
		1.2 Ausgaenge
		I2.12
		Wort- und Sachvorstellungen, vorbewusste und bewusste Inhalte der aeuﬂeren aufmerksamen Wahrnehmung, werden einerseits zu E24, andererseits zu E25 transportiert.
		mein kommentar dazu: 
		grundsaetzlich ist f23 eine funktion der wahrnehmung, d.h. es geht um wahrnehmungsinhalte (mehr oder weniger besetzt), die mit einer wortvorstellung verbunden sind. diese werden nun mit zusaetzlicher besetzungsenergie (= quantifizierbare affektbetraege ñ ev. auch extrahiert aus einer bereits vorhandenen emotion oder gefuehl) Ñaufgetanktì ñ ja nach fokussierung.
		diese zusaetzliche besetzungsenergie kommt aus:
		ï	desexualisierter triebenergie (f56)
		ï	f8 ñ triebe
		und (das ist bislang noch wenig bedacht ñ um muss zum jetzigen stand der modellbildungnicht unbedingt beruecksichtig werden)
		ï	aus der besetzung der wortvorstellungen, mit denen der inhalt verbunden ist. (d.h. wenn mir das wort, das ich verwende, viel bedeutet, dann wird auch der inhalt mit viel bedeuten. beispiel: unser theo lernt gerade mit begeisterung sprache. die letzten tage hat er das wort Ñrunterfallenì entdeckt und war so stolz darauf, dass er es so 1000x am tag wiederholt hat. er wendet es auch fuer alle moeglichen inhalte an (etwa: huepfen auf der couch, stiegenhaus,Ö). d.h. fuer theo ist die wortvorstellung Ñrunterfallenì hochbesetzt, und somit werden auch inhalte, die mit Ñrunterfallenì verbunden sind besetzt, indem die besetzungsenergie uebergeht. etwa wenn ich ihm sage: der bleistift kann vom tisch runterfallen ñ so kriegt dieser akt fuer ihn im moment eine hohe aufmerksamkeit.)
		ï	
		zu f8-trieb e ist zu bemerkten, dann nicht die triebe per se die fokussierung beeinflussen, sondern deren staerke. somit stimmt es schon, dass nur ein affektbetrag hier dazukommt, der aus der triebweitergabe I6.3 zu extrahieren ist. der wird wohl jene wahrnehmungsinhalte ueber-besetzen, die mit dem trieb(objekt), das im primaervorgang ueber die abwehr usw. gebildet wurde, am engsten verwandt (assoziiiert) sind.
		zu deiner ursprungsfrage:
		@Klaus: Was entscheidet welche und wie viele Inhalte durch F23 Focused Perception kommt? 
		da koennen schon viele reinkommen (=vorbewusst werden)
		Mein Vorschlag waere eine Liste zu erstellen mit den angehaengten Trieben (Ñtriebeì ist hier falsch verwendet ñ Ñaffekteì stimmt schon im weitesten sinne)
		(Affekte werden sie im Simulator genannt habe ich gesehen) sortiert von sehr hoch bis sehr niedrig (diesen Wert kommt vom Affektbetrag in F21 habe ich gesehen).
		ok ñ wenngleich jetzt auch noch ein wert von f56 dazukommtÖ

		Dann x obersten (Hoechsten Affekt) Objekten weiterleiten. Kann man so machen oder wie wuerdest du vorschlagen, dass man macht?
		auch hier koennte man f56 wirken lassen: je hoeher die freie desex. triebenergie ist, desto mehr weiterleiten. 
		aber mir persoelich wuerde folgende varriante am besten gefallen: einen besetzungsschwellwert einfuehren. wird dieser ueberschritten, so geht der inhalt weiter und muss bearbeitet werden. so laesst man auch ueberforderungen zu, wie sie nun mal in extremsituationen beim menschen auftreten. (siehe film: Ñmodern timesì von charly chaplin)
		*/
		
		//=========================================================//
		//TODO AW: In Focus of attention, the most relevant memories can be selected, i. e. the analysis of the current moment, intention and expectation
		//could be done here. It is also a possibility to use the complete perception for this task.
		
		//=========================================================//
		//moAssociatedMemoriesSecondary_OUT = (ArrayList<clsDataStructureContainerPair>)deepCopy(moAssociatedMemoriesSecondary_IN);
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
			if (clsGoalTools.getGoalContent(oGoal).equals(eContent.UNKNOWN_GOAL.toString())) {
				oRetVal.add(oGoal);
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Extract all possible goals for perception (Emotions and drives)
	 * 
	 * (wendt)
	 *
	 * @since 25.05.2012 18:43:47
	 *
	 * @param moPerceptionalMesh_IN
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> extractPossibleGoalsForPerception(clsWordPresentationMesh moPerceptionalMesh_IN) {
		//TODO AW: Add emotions here
		
		return clsGoalTools.extractPossibleGoals(moPerceptionalMesh_IN);
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
			//Get the intention
			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oAct);
			if (oIntention!=null) {
				oRetVal.addAll(clsGoalTools.extractPossibleGoals(oIntention));
			} 
			
			if (oRetVal.isEmpty()==true) {
				//The intention does not exist. If the agent has a drive goal without a found object in the memory or in
				//in the perception, it shall search its activated memory first
				//Here, a special goal is created. With the empty Intention in as goal object, this shall be processed by the phantasy 
				oRetVal.add(clsGoalTools.createGoal(eContent.UNKNOWN_GOAL.toString(), eAffectLevel.INSIGNIFICANT, oIntention, oAct));
				
			}
			
		}
		
		return oRetVal;
	}
	
	private ArrayList<clsWordPresentationMesh> extractPossibleGoalsFromPlans(ArrayList<clsWordPresentationMesh> moActList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
	
		//Get their supportive structures and ALL objects in this supportive structure shall be kept if found in the perception
		//Add importance of 5 for these goals
		
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
	private clsWordPresentationMesh focusPerception(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poPossibleGoals, int  pnNumberOfAllowedObjects) {
		clsWordPresentationMesh oRetVal = null;
		
		ArrayList<clsWordPresentationMesh> oFilteredGoalList = clsAffectTools.filterGoals(poPossibleGoals, pnNumberOfAllowedObjects);
		
		//Filter the PI according to the drive list
		oRetVal = filterImageElements(poPerception, oFilteredGoalList);
		//oRetVal = new clsDataStructureContainerPair(oFilteredImages, poPerception.getPrimaryComponent());
			
		return oRetVal;
	}
	
	private ArrayList<clsWordPresentationMesh> focusMemories(ArrayList<clsWordPresentationMesh> poActMesh) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		
		
		return oRetVal;
	}
	
	/**
	 * Filter a PI for elements, which are associted with the drive goals in a list
	 * (wendt)
	 *
	 * @since 15.08.2011 22:30:44
	 *
	 * @param poImage
	 * @param poGoalList
	 * @return
	 */
	private clsWordPresentationMesh filterImageElements(clsWordPresentationMesh poImage, ArrayList<clsWordPresentationMesh> poGoalList) {
		clsWordPresentationMesh oRetVal = null;
		
		//Create a copy without clone
		oRetVal = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(poImage.getMoContentType(), poImage.getMoContent()), new ArrayList<clsAssociation>());
		
		//Add all objects from the perception, which exist in the goallist
		for (clsWordPresentationMesh oGoal : poGoalList) {
			//Add all objects to the list if they don't exist yet, add them
			boolean bFound = false;
			for (clsAssociation oAss : oRetVal.getAssociatedContent()) {
				if (oAss.getLeafElement().equals(clsGoalTools.getGoalObject(oGoal))) {
					bFound = true;
					break;
				}
			}
			
			if (bFound==false) {
				clsMeshTools.createAssociationSecondary(oRetVal, 1, clsGoalTools.getGoalObject(oGoal), 0, 1.0, eContentType.ASSOCIATIONSECONDARY.toString(), ePredicate.PARTOF.toString(), false);
			}
		}
		
		//Add a new association to the original PI, which contains all perception
		clsMeshTools.createAssociationSecondary(oRetVal, 2, poImage, 2, 1.0, eContentType.ASSOCIATIONSECONDARY.toString(), ePredicate.PARTOF.toString(), false);
		
//		//Remove all objects, which are not found in the drive list
//		if (oRetVal.getMoDataStructure() instanceof clsWordPresentationMesh) {
//			ArrayList<clsAssociation> oOldInternalAssList = ((clsWordPresentationMesh)oRetVal.getMoDataStructure()).getAssociatedContent();
//			ArrayList<clsAssociation> oNewInternalAssList = new ArrayList<clsAssociation>();
//			ArrayList<clsAssociation> oNewContainerAssList = new ArrayList<clsAssociation>();
//			//For each association in the old list
//			for (clsAssociation oAss : oOldInternalAssList) {
//				//For each associated data structure in the new list, The root element shall be picked here and not the leaf as the object is a "part of" the PI
//				clsSecondaryDataStructure oInternalDataStructure = (clsSecondaryDataStructure) oAss.getRootElement();
//				for (clsSecondaryDataStructureContainer oContainer : poGoalList) {
//					//Go through the associated content for a drive
//					boolean bObjectFound = false;
//					for (clsAssociation oDriveAss : oContainer.getMoAssociatedDataStructures()) {
//						//Get the object types
//						
//						//Get the right type
//						if (oDriveAss instanceof clsAssociationSecondary) {
//							//In this case the leaf element shall be the search data structure, but for safe both are tested
//							if (oDriveAss.getLeafElement().getMoDSInstance_ID() == oInternalDataStructure.getMoDSInstance_ID() ||
//									oDriveAss.getRootElement().getMoDSInstance_ID() == oInternalDataStructure.getMoDSInstance_ID()) {
//								//If the element is found in the drive goals, add it to the new list
//								oNewInternalAssList.add(oAss);
//								//Add the associated data structures of the element
//								oNewContainerAssList.addAll(oRetVal.getAnyAssociatedDataStructures(oAss.getRootElement()));
//								//This element shall only be added once, therefore break afterwards
//								bObjectFound = true;
//								break;
//							}
//						}
//					}
//					
//					if (bObjectFound==true) {
//						break;
//					}
//
//				}
//				
//
//			}
//			//Add all secondary associations from the image structure
//			oNewContainerAssList.addAll(poImage.getAnyAssociatedDataStructures(poImage.getMoDataStructure()));
//			
//			//Replace the old associated intrinsic content 
//			((clsWordPresentationMesh)oRetVal.getMoDataStructure()).setMoAssociatedContent(oNewInternalAssList);
//			//Replace the old associated content of the container
//			oRetVal.setMoAssociatedDataStructures(oNewContainerAssList);
//		}
		
		return oRetVal;
	}
	
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
		send_I6_6(moPerceptionalMesh_OUT, moDriveGoalList_IN, moAssociatedMemories_OUT);
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
			ArrayList<clsWordPresentationMesh> poDriveList,
			   				ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary_OUT) {
		((I6_6_receive)moModuleList.get(51)).receive_I6_6(poFocusedPerception, poDriveList, poAssociatedMemoriesSecondary_OUT);
		
		putInterfaceData(I6_6_send.class, poFocusedPerception, poDriveList, poAssociatedMemoriesSecondary_OUT);
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
