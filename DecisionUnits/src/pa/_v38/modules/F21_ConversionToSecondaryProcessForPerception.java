/**
 * E21_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:38:29
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I5_15_receive;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_1_send;
import pa._v38.interfaces.modules.I6_4_receive;
import pa._v38.interfaces.modules.I6_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.toText;
import secondaryprocess.functionality.conversion.DataStructureConversion;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;
import config.clsProperties;
import datatypes.helpstructures.clsPair;

/**
 * This module does the same as {E8} but with perceptions instead of drive
 * representations. The thing presentations and quota of affects generated by
 * incoming perceived neurosymbols are associated with the most fitting word
 * presentations found in memory.
 * 
 * @author kohlhauser 07.05.2012, 14:38:29
 * 
 */
public class F21_ConversionToSecondaryProcessForPerception extends
		clsModuleBaseKB implements I5_15_receive, I6_1_send, I6_4_send {

	public static final String P_MODULENUMBER = "21";
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	private ArrayList<String> Test = new ArrayList<String>();

	/** Perception IN */
	private clsThingPresentationMesh moPerceptionalMesh_IN;

	/** Perception OUT */
	private clsWordPresentationMesh moPerceptionalMesh_OUT;

	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;

	private clsShortTermMemory moShortTermMemory;

	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;

	private ArrayList<clsEmotion> moEmotions_Input;
	
	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;

	/**
	 * Load up to 98 indirectly associated structures; @since 30.01.2012
	 * 19:57:31
	 */
	private int mnMaxLevel = 100;

	/**
	 * DOCUMENT (KOHLHAUSER) - insert description
	 * 
	 * @author kohlhauser 03.03.2011, 16:44:38
	 * 
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F21_ConversionToSecondaryProcessForPerception(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			itfModuleMemoryAccess poLongTermMemory,
			clsShortTermMemory poShortTermMemory,
			clsShortTermMemory poConceptMemory,
			clsEnvironmentalImageMemory poTempLocalizationStorage,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData,
				poLongTermMemory);

		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
		applyProperties(poPrefix, poProp);
        
		this.moShortTermMemory = poShortTermMemory;
		this.moEnvironmentalImageStorage = poTempLocalizationStorage;	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		// text += toText.valueToTEXT("AdamPerspective:", moConcept);
		text += toText.valueToTEXT("&&&&&&&&&&&&&&&&&&&Test&&&&&&&&&&&&&&&&&&&&&&&&&&",
				Test);
		text += toText.valueToTEXT("moPerceptionalMesh_IN",
				moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT",
				moPerceptionalMesh_OUT);
		text += toText.listToTEXT("moAssociatedMemories_OUT",
				moAssociatedMemories_OUT);
		// text += toText.listToTEXT("moOrderedResult", moOrderedResult);
		// text += toText.mapToTEXT("moTemporaryDM", moTemporaryDM);
//		text += toText.valueToTEXT("moKnowledgeBaseHandler",
//				moKnowledgeBaseHandler);	
		return text;
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE,
				eImplementationStage.BASIC.toString());

		return oProp;
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		// String pre = clsProperties.addDot(poPrefix);

		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 14:39:18 ======= /* (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 14:39:18 >>>>>>>
	 * e61ceb1d2a51a0954a64232c38a3647a95f9a245
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I5_15(clsThingPresentationMesh poPerceptionalMesh,
			ArrayList<clsEmotion> poEmotions) {
		moPerceptionalMesh_IN = poPerceptionalMesh;
		moEmotions_Input = poEmotions;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
	    log.debug("=== module {} start ===", this.getClass().getName());

		// --- Update short term memory ---//
	    ShortTermMemoryFunctionality.createNewMentalSituation(this.moShortTermMemory);

		
		// Search for all images from the primary process in the memory
		// Input: TPM
		// 1. Get all Images of the Mesh
		// 2. Search for WPM for the image
		// 3. Search for WPM for all internal objects in the WPM
		// 4. For each WPM, search for more SP-WPM-Parts and connect them
		// 5. Within the WPM-Structure, the allocation of images to acts is
		// already done. Each image except the PI
		clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>> oWPMConstruct = DataStructureConversion.getWordPresentationsForImages(this.getLongTermMemory(), moPerceptionalMesh_IN);

		log.debug("Perceived Image: " + oWPMConstruct.a);
		log.info("Found Acts:" + oWPMConstruct.b);
		
		// Assign the output to the meshes
		moPerceptionalMesh_OUT = oWPMConstruct.a;
		moAssociatedMemories_OUT = oWPMConstruct.b;
		

		// debug
		// if (moAssociatedMemories_OUT.isEmpty()==false) {
		// ArrayList<clsWordPresentationMesh> poAct =
		// clsMeshTools.getAllWPMImages(clsActDataStructureTools.getIntention(moAssociatedMemories_OUT.get(0)),
		// 6);
		// System.out.print("poAct size" + poAct.size());
		// }

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// HZ: null is a placeholder for the bjects of the type
		// pa._v38.memorymgmt.datatypes
		send_I6_1(moPerceptionalMesh_OUT, moAssociatedMemories_OUT);
		send_I6_4(moPerceptionalMesh_OUT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I2_11_send#send_I2_11(java.util.ArrayList)
	 */
	@Override
	public void send_I6_1(clsWordPresentationMesh poPerception,
			ArrayList<clsWordPresentationMesh> poAssociatedMemories) {
		// AW 20110602: Attention, the associated memeories contain images and
		// not objects like in the perception
		// ((I6_1_receive)moModuleList.get(23)).receive_I6_1(poPerception,
		// poAssociatedMemories);
		((I6_1_receive) moModuleList.get(61)).receive_I6_1(poPerception,
				poAssociatedMemories);
		((I6_1_receive) moModuleList.get(66)).receive_I6_1(poPerception,
                poAssociatedMemories);

		putInterfaceData(I6_1_send.class, poPerception, poAssociatedMemories);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I6_4(clsWordPresentationMesh poPerception) {
		((I6_4_receive) moModuleList.get(20)).receive_I6_4(poPerception);
		putInterfaceData(I6_4_send.class, poPerception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 03.03.2011, 16:44:44
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This module does the same as {E8} but with perceptions instead of drive representations. The thing presentations and quota of affects generated by incoming perceived neurosymbols are associated with the most fitting word presentations found in memory. ";

	}
}
