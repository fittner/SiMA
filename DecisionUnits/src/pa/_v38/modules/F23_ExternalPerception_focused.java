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
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_6_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.tools.clsAffectTools;
import pa._v38.tools.toText;

/**
 * The task of this module is to focus the external perception on ``important'' things. Thus, the word presentations originating from perception are ordered according to their importance to existing drive wishes. This could mean for example that an object is qualified to satisfy a bodily need. The resulting listthe package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importanceis forwarded by the interface {I2.12} to {E24} and {E25}. These two modules are part of reality check. 
 * 
 * TODO (kohlhauser) - freie energie irgendwie einarbeiten
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 * 
 */
public class F23_ExternalPerception_focused extends clsModuleBase implements I6_1_receive, I6_3_receive, I6_6_send {
	public static final String P_MODULENUMBER = "23";
	
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:35 */
	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
	//AW 20110602 New input of the module
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:37 */
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_IN;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:39 */
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList; 
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:40 */
	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:56:18 */
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_OUT;
	
	/** As soon as DT3 is implemented, replace this variable and value */
	private double mrAvailableFocusEnergy = 7.8;
	
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
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
		text += toText.listToTEXT("moDriveList", moDriveList);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		text += toText.listToTEXT("moAssociatedMemoriesSecondary_OUT", moAssociatedMemoriesSecondary_OUT);
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
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_1(clsDataStructureContainerPair poPerception, ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		try {
			moEnvironmentalPerception_IN = (clsDataStructureContainerPair)poPerception.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		//AW 20110602 Added Associtated memories
		moAssociatedMemoriesSecondary_IN = (ArrayList<clsDataStructureContainer>)this.deepCopy(poAssociatedMemoriesSecondary);
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
	public void receive_I6_3(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);
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
		
		//moEnvironmentalPerception_OUT = new ArrayList<clsDataStructureContainer>();
		
		//if (moEnvironmentalPerception_IN.getSecondaryComponent() != null) {
		moEnvironmentalPerception_OUT = focusPerception(moEnvironmentalPerception_IN);
		//}
		
		
		/*
		1. E23 Äußere Wahrnehmung (fokussiert)
		1.1 Modulbeschreibung
		Die Wahrnehmung verfügt über freie Energie, mit der sie im aufmerksamen Zustand unterschiedliche Elemente überbesetzt[1], d.h. fokussiert.
		1.2 Ausgänge
		I2.12
		Wort- und Sachvorstellungen, vorbewusste und bewusste Inhalte der äußeren aufmerksamen Wahrnehmung, werden einerseits zu E24, andererseits zu E25 transportiert.
		mein kommentar dazu: 
		grundsätzlich ist f23 eine funktion der wahrnehmung, d.h. es geht um wahrnehmungsinhalte (mehr oder weniger besetzt), die mit einer wortvorstellung verbunden sind. diese werden nun mit zusätzlicher besetzungsenergie (= quantifizierbare affektbeträge – ev. auch extrahiert aus einer bereits vorhandenen emotion oder gefühl) „aufgetankt“ – ja nach fokussierung.
		diese zusätzliche besetzungsenergie kommt aus:
		•	desexualisierter triebenergie (f56)
		•	f8 – triebe
		und (das ist bislang noch wenig bedacht – um muss zum jetzigen stand der modellbildungnicht unbedingt berücksichtig werden)
		•	aus der besetzung der wortvorstellungen, mit denen der inhalt verbunden ist. (d.h. wenn mir das wort, das ich verwende, viel bedeutet, dann wird auch der inhalt mit viel bedeuten. beispiel: unser theo lernt gerade mit begeisterung sprache. die letzten tage hat er das wort „runterfallen“ entdeckt und war so stolz darauf, dass er es so 1000x am tag wiederholt hat. er wendet es auch für alle möglichen inhalte an (etwa: hüpfen auf der couch, stiegenhaus,…). d.h. für theo ist die wortvorstellung „runterfallen“ hochbesetzt, und somit werden auch inhalte, die mit „runterfallen“ verbunden sind besetzt, indem die besetzungsenergie übergeht. etwa wenn ich ihm sage: der bleistift kann vom tisch runterfallen – so kriegt dieser akt für ihn im moment eine hohe aufmerksamkeit.)
		•	
		zu f8-trieb e ist zu bemerkten, dann nicht die triebe per se die fokussierung beeinflussen, sondern deren stärke. somit stimmt es schon, dass nur ein affektbetrag hier dazukommt, der aus der triebweitergabe I6.3 zu extrahieren ist. der wird wohl jene wahrnehmungsinhalte über-besetzen, die mit dem trieb(objekt), das im primärvorgang über die abwehr usw. gebildet wurde, am engsten verwandt (assoziiiert) sind.
		zu deiner ursprungsfrage:
		@Klaus: Was entscheidet welche und wie viele Inhalte durch F23 Focused Perception kommt? 
		da können schon viele reinkommen (=vorbewusst werden)
		Mein Vorschlag wäre eine Liste zu erstellen mit den angehängten Trieben („triebe“ ist hier falsch verwendet – „affekte“ stimmt schon im weitesten sinne)
		(Affekte werden sie im Simulator genannt habe ich gesehen) sortiert von sehr hoch bis sehr niedrig (diesen Wert kommt vom Affektbetrag in F21 habe ich gesehen).
		ok – wenngleich jetzt auch noch ein wert von f56 dazukommt…

		Dann x obersten (Höchsten Affekt) Objekten weiterleiten. Kann man so machen oder wie würdest du vorschlagen, dass man macht?
		auch hier könnte man f56 wirken lassen: je höher die freie desex. triebenergie ist, desto mehr weiterleiten. 
		aber mir persölich würde folgende varriante am besten gefallen: einen besetzungsschwellwert einführen. wird dieser überschritten, so geht der inhalt weiter und muss bearbeitet werden. so lässt man auch überforderungen zu, wie sie nun mal in extremsituationen beim menschen auftreten. (siehe film: „modern times“ von charly chaplin)
		*/
		
		
		moAssociatedMemoriesSecondary_OUT = (ArrayList<clsDataStructureContainer>)deepCopy(moAssociatedMemoriesSecondary_IN);
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
	private clsDataStructureContainerPair focusPerception(clsDataStructureContainerPair poPerception) {
		clsDataStructureContainerPair oRetVal = null;
		
		try {
			ArrayList<clsSecondaryDataStructureContainer> oDriveGoals = clsAffectTools.getWPMDriveGoals(poPerception.getSecondaryComponent());
			ArrayList<clsSecondaryDataStructureContainer> oSortedDriveGoals  = clsAffectTools.sortDriveDemands(oDriveGoals);
			
			//Select perception, which passes the filter
			//1. Only drives with AFFECT >= MEDIUM (2) will pass and
			//2. Only the first x objects, which are defined by the desexual energy
			//Precondition: The list must be sorted
			int nNumberOfAllowedObjects = (int)mrAvailableFocusEnergy;	//FIXME AW: What is the desexualalized energy and how many objects/unit are used.
			int nCurrentObjectNumber = 0;
			ArrayList<clsSecondaryDataStructureContainer> oFilteredGoals = new ArrayList<clsSecondaryDataStructureContainer>();
			for (int i=0; i<oSortedDriveGoals.size();i++) {
				int nDriveIntensity = clsAffectTools.getDriveIntensityAsInt(((clsSecondaryDataStructure)oSortedDriveGoals.get(i).getMoDataStructure()).getMoContent());
				
				if (nCurrentObjectNumber<=nNumberOfAllowedObjects && (nDriveIntensity >= mnAffectThresold)) {
					oFilteredGoals.add(oSortedDriveGoals.get(i));
				} else {
					//If one of the creiteria is not fulfilled, then break
					break;
				}
				
				nCurrentObjectNumber++;
			}
			
			//Filter the PI according to the drive list
			clsSecondaryDataStructureContainer oFilteredImages = filterImageElements(poPerception.getSecondaryComponent(), oFilteredGoals);
			oRetVal = new clsDataStructureContainerPair(oFilteredImages, poPerception.getPrimaryComponent());
			
		
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		

				
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
	private clsSecondaryDataStructureContainer filterImageElements(clsSecondaryDataStructureContainer poImage, ArrayList<clsSecondaryDataStructureContainer> poGoalList) {
		clsSecondaryDataStructureContainer oRetVal = null;
		
		//Clone Input container
		try {
			oRetVal = (clsSecondaryDataStructureContainer) poImage.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Clean Drive goal list, in order only to have one object for each goal
		
		
		//Remove all objects, which are not found in the drive list
		if (oRetVal.getMoDataStructure() instanceof clsWordPresentationMesh) {
			ArrayList<clsAssociation> oOldInternalAssList = ((clsWordPresentationMesh)oRetVal.getMoDataStructure()).getMoAssociatedContent();
			ArrayList<clsAssociation> oNewInternalAssList = new ArrayList<clsAssociation>();
			ArrayList<clsAssociation> oNewContainerAssList = new ArrayList<clsAssociation>();
			//For each association in the old list
			for (clsAssociation oAss : oOldInternalAssList) {
				//For each associated data structure in the new list, The root element shall be picked here and not the leaf as the object is a "part of" the PI
				clsSecondaryDataStructure oInternalDataStructure = (clsSecondaryDataStructure) oAss.getRootElement();
				for (clsSecondaryDataStructureContainer oContainer : poGoalList) {
					//Go through the associated content for a drive
					boolean bObjectFound = false;
					for (clsAssociation oDriveAss : oContainer.getMoAssociatedDataStructures()) {
						//Get the right type
						if (oDriveAss instanceof clsAssociationSecondary) {
							//In this case the leaf element shall be the search data structure, but for safe both are tested
							if (oDriveAss.getLeafElement().getMoDSInstance_ID() == oInternalDataStructure.getMoDSInstance_ID() ||
									oDriveAss.getRootElement().getMoDSInstance_ID() == oInternalDataStructure.getMoDSInstance_ID()) {
								//If the element is found in the drive goals, add it to the new list
								oNewInternalAssList.add(oAss);
								//Add the associated data structures of the element
								oNewContainerAssList.addAll(oRetVal.getAnyAssociatedDataStructures(oAss.getRootElement()));
								//This element shall only be added once, therefore break afterwards
								bObjectFound = true;
								break;
							}
						}
					}
					
					if (bObjectFound==true) {
						break;
					}

				}
				

			}
			//Add all secondary associations from the image structure
			oNewContainerAssList.addAll(poImage.getAnyAssociatedDataStructures(poImage.getMoDataStructure()));
			
			//Replace the old associated intrinsic content 
			((clsWordPresentationMesh)oRetVal.getMoDataStructure()).setMoAssociatedContent(oNewInternalAssList);
			//Replace the old associated content of the container
			oRetVal.setMoAssociatedDataStructures(oNewContainerAssList);
		}
		
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
		send_I6_6(moEnvironmentalPerception_OUT, moDriveList, moAssociatedMemoriesSecondary_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I6_6(clsDataStructureContainerPair poFocusedPerception,
			   				ArrayList<clsSecondaryDataStructureContainer> poDriveList,
			   				ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary_OUT) {
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
