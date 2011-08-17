/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I5_7_receive;
import pa._v38.interfaces.modules.I5_8_receive;
import pa._v38.interfaces.modules.I5_8_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.storage.DT2_BlockedContentStorage;

/**
 * Emersion of blocked content. The inputs of the perception and associated memories are compared with
 * content of the repressed content storage. The content of the repressed content storage is activated
 * if there is a match. The blocked content storage contains both drive meshes and images 
 * 
 * @author wendt
 * 07.10.2009, 11:16:58
 * 
 */
public class F35_EmersionOfBlockedContent extends clsModuleBaseKB implements I5_7_receive, I5_8_send {
	public static final String P_MODULENUMBER = "35";
	
	/** The blocked content storage, which is an Arraylist for data structures */
	private DT2_BlockedContentStorage moBlockedContentStorage;
	
	/** Input perceived image (type template image) */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	/** Input associated activated memories */
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	/** Output perceived image (type template image) */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	/** Output associated activated memories */
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;

	/**
	 * Get the blocked content storage. Apply properties from the config files.  
	 *
	 * @since 25.07.2011 11:05:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @param poBlockedContentStorage
	 * @throws Exception
	 */
	public F35_EmersionOfBlockedContent(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler, DT2_BlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		moBlockedContentStorage = poBlockedContentStorage;

		applyProperties(poPrefix, poProp);
	}
	

	/* (non-Javadoc)
	 *
	 * @since 25.07.2011 11:05:44
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moBlockedContentStorage", moBlockedContentStorage);
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		text += toText.valueToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
		return text;
	}	

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		//mrContextSensitivity = poProp.getPropertyDouble(pre+P_CONTEXT_SENSTITIVITY);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() {
		//Make a deepcopy of the input parameter, else the difference cannot be correctly displayed in statetotext
		moEnvironmentalPerception_OUT = (clsPrimaryDataStructureContainer)moEnvironmentalPerception_IN.clone();
		moAssociatedMemories_OUT = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(moAssociatedMemories_IN);
		/* MZ 2011/07/05: everything that is done with the input is now happening
		 * inside enrichWithBlockedContent. This was done so that in the future
		 * the items in moAssociatedMemories_IN can also be processed in the same
		 * manner.
		 */
		enrichWithBlockedContent(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);
	}

	/**
	 * looks for matching blocked content to enrich the
	 * perception.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 26.06.2011, 17:18:03
	 *
	 * @param poPerception - the perception that is to be processed.
	 * 
	 * @see F35_EmersionOfBlockedContent#assignDriveMeshes(clsPrimaryDataStructureContainer)
	 * @see F35_EmersionOfBlockedContent#adaptCategories(clsPrimaryDataStructureContainer)
	 * @see F35_EmersionOfBlockedContent#matchBlockedContent(clsPrimaryDataStructureContainer)
	 */
	private void enrichWithBlockedContent(clsPrimaryDataStructureContainer poPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		//Send inputs to the memeory
		moBlockedContentStorage.receive_D2_4(poPerception, poAssociatedMemories);
		clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> oGetVal;
		oGetVal = moBlockedContentStorage.send_D2_4();
		//modify inputs
		poPerception = oGetVal.a;
		poAssociatedMemories = oGetVal.b;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_8(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:52:35
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (wendt) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:52:35
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (wendt) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:24:31
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:25:17
	 * 
	 * @see pa.interfaces.send._v38.I2_8_send#send_I2_8(java.util.ArrayList)
	 */
	/*public void send_D2_4(clsPrimaryDataStructureContainer poData, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		((D2_4_receive)moModuleList.get(D2_4)).receive_D2_4(poData, poAssociatedMemories);
	}*/
	
	
	@Override
	public void send_I5_8(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		((I5_8_receive)moModuleList.get(45)).receive_I5_8(poMergedPrimaryInformation, poAssociatedMemories);
	
		putInterfaceData(I5_8_send.class, poMergedPrimaryInformation, poAssociatedMemories);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:25:17
	 * 
	 * @see pa.interfaces.receive._v38.I2_14_receive#receive_I2_14(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_7(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)poEnvironmentalTP.clone();
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "It is responsible for changing repressed contents such that they are more likely to pass the defense mechanisms. This is done by searching for fitting incoming primary process data structures. If one is found, the repressed content is attached to it. All incoming images are forwarded to next modules, some of them with additional information attached.";
	}
	
	
}
