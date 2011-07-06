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
import config.clsBWProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfMinimalModelMode;
import pa._v38.interfaces.modules.I5_7_receive;
import pa._v38.interfaces.modules.I5_8_receive;
import pa._v38.interfaces.modules.I5_8_send;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.storage.clsBlockedContentStorage;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class F35_EmersionOfBlockedContent extends clsModuleBaseKB implements itfMinimalModelMode, I5_7_receive, I5_8_send {
	public static final String P_MODULENUMBER = "35";
	public static String P_CONTEXT_SENSTITIVITY = "CONTEXT_SENSITIVITY"; 
	
	private clsBlockedContentStorage moBlockedContentStorage;
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	//AW 20110521: Old Output
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output; 
	
	private double mrContextSensitivity = 0.8;
	private boolean mnMinimalModel;
	
	/**
	 * Minimum match factor a blocked content must achieve to be activated. 
	 * 
	 * @author Marcus Zottl (e0226304)
	 * 28.06.2011, 20:22:42
	 */
	private double mrActivationThreshold = 0.5;
	/**
	 * Limit to adjust the number of activated blocked contents. 
	 * 
	 * @author Marcus Zottl (e0226304)
	 * 28.06.2011, 20:21:28
	 */
	private int mnActivationLimit = 3;

	/**
	 * DOCUMENT (wendt) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:24:23
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F35_EmersionOfBlockedContent(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsBlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		moBlockedContentStorage = poBlockedContentStorage;

		applyProperties(poPrefix, poProp);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.valueToTEXT("moBlockedContentStorage", moBlockedContentStorage);
		text += toText.valueToTEXT("moEnvironmentalTP_Input", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moAttachedRepressed_Output", moAttachedRepressed_Output);
		text += toText.valueToTEXT("mrContextSensitivity", mrContextSensitivity);
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
		return text;
	}	

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_CONTEXT_SENSTITIVITY, 0.8);
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		mrContextSensitivity = poProp.getPropertyDouble(pre+P_CONTEXT_SENSTITIVITY);
		mnMinimalModel = false;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@SuppressWarnings("unused")
	@Override
	protected void process_basic() {
			
		moEnvironmentalPerception_OUT = (clsPrimaryDataStructureContainer)deepCopy(moEnvironmentalPerception_IN);
		moAssociatedMemories_OUT = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(moAssociatedMemories_IN);
		/* MZ 2011/07/05: everything that is done with the input is now happening
		 * inside enrichWithBlockedContent. This was done so that in the future
		 * the items in moAssociatedMemories_IN can also be processed in the same
		 * manner.
		 */
		//enrichWithBlockedContent(moEnvironmentalPerception_OUT);
		
		/* ALT BEGIN */
		
		//FIXME: AW: The output consists of 3 equal pairs of DM and Containers. Why is the same Ref used 3 times?
		//moAttachedRepressed_Output = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		//ArrayList<clsPrimaryDataStructureContainer> oContainerList = new ArrayList<clsPrimaryDataStructureContainer>(); 
	 		
		//oContainerList = clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN); 
			/* Add DM for to object and include them in the TPM through associations. The TPM then contains 
			 * attributeassociations and drivemeshassociations
			 */
		//assignDriveMeshes(oContainerList);
			/* The context of a certain drive or object is loaded, in the case of CAKE, it is NOURISH. If the drive 
			 * NOURISH is found in the objects the categories (anal, oral...) is multiplied with a category factor <= 1
			 * In case of a 100% match, the factor is 1.0.
			 */
		//adaptCathegories(oContainerList);
			/* DM from the Repressed Content are added to the objects in the oContainerList
			 */
		
		/* ALT END */
		
		//moBlockedContentStorage.receive_D2_4(oContainerList);
		//moAttachedRepressed_Output = moBlockedContentStorage.send_D2_4();
			
			//matchRepressedContent(oContainerList);	//This function can be found in the blocked content instead
			
	
		
		//Convert Output to new format
		//moEnvironmentalPerception_OUT = ConvertToTIContainer(moAttachedRepressed_Output);
		//Pass the memories forward. Later, they are enriched repressed content
		//moAssociatedMemories_OUT = moAssociatedMemories_IN;
	}

	/**
	 * Assigns DriveMeshes, adapts the categories of the DriveMeshes according to
	 * the context and then looks for matching blocked content to enrich the
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
	private void enrichWithBlockedContent(clsPrimaryDataStructureContainer poPerception) {
		matchBlockedContent(poPerception);
	}

	/**
	 * Acquires a list of matching items from the blocked content storage and enriches the incoming
	 * perception according to the following rules:
	 * <ul>
	 * <li>If the match contains a TemplateImage and is a full match (match value = 1) then all the
	 * DriveMeshes associated with elements in the TemplateImage are added to the perception by
	 * associating them with their matching "partner"-elements in the perceived TemplateImage.</li>
	 * <li>If the match contains a TemplateImage and is a partial match (match value < 1) then
	 * the whole TemplateImage is added to the moAssociatedMemories.</li>
	 * <li>If the match contains an "independent" DriveMesh, it is added to the perceived TemplateImage
	 * by associating it with the element that it matched with.</li>
	 * </ul>
	 * 
	 * <b>Variables read by this method:</b><br>
	 * {@link #mrActivationThreshold}	- defines a minimum quality for matches<br>
	 * {@link #mnActivationLimit}		- limits the number of considered matches<br> 
	 * <br>
	 * <b>Variables modified by this method:</b><br>
	 * {@link #moAssociatedMemories_OUT}	- partially matching TIs are added to this list<br>
	 * <br>
	 *
	 * @author Zottl Marcus
	 * 22.06.2011, 18:47:30
	 *
	 * @param poPerception - the perceived input with attached DriveMeshes and adapted categories for
	 * which you want to find matches in the blocked content storage. 
	 */
	private void matchBlockedContent(clsPrimaryDataStructureContainer poPerception) {
		ArrayList<clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>> oMatchedContent;
		
		// look up matching content
		oMatchedContent = null; //XXXmoBlockedContentStorage.getMatchesForPerception(poPerception, mrActivationThreshold);
		// now pick the topmost matches and process them accordingly
		int i = 0;
		for (clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>> matchedItem : oMatchedContent) {
			i++;
			if (i > mnActivationLimit) break;
			
			//case 1: the item is a TemplateImage
			if (matchedItem.a.getMoDataStructure() instanceof clsTemplateImage) {
				// case 1a: full match (matchValue = 1)
				if (matchedItem.b == 1) {
					// attach all DMs in result to the input TI
					poPerception.getMoAssociatedDataStructures().addAll(matchedItem.c);
				}
				// case 1b: partial match (matchValue < 1)
				else {
					// add complete result to associated memories
					moAssociatedMemories_OUT.add(matchedItem.a);
				}
				// activated content has to be deleted from the blocked content storage
				//XXXmoBlockedContentStorage.removeBlockedContent(matchedItem.a.getMoDataStructure());
			}
			// case 2: the item is a DriveMesh
			else if (matchedItem.a.getMoDataStructure() instanceof clsDriveMesh) {
				// attach all DMs in result to the input TI
				poPerception.getMoAssociatedDataStructures().addAll(matchedItem.c);
				// activated content has to be deleted from the blocked content storage
				// XXXmoBlockedContentStorage.removeBlockedContent(matchedItem.a.getMoDataStructure());
			}
		}
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
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 08:36:59
	 *
	 * @param undefined
	 * @param poDS
	 * @param oSearchResult
	 */
	@Override
	public <E> void search(
			eDataType poDataType,
			ArrayList<E> poPattern,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		createSearchPattern(poDataType, poPattern, oSearchPattern);
		accessKnowledgeBase(poSearchResult, oSearchPattern); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.03.2011, 19:04:29
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#createSearchPattern(pa._v38.memorymgmt.enums.eDataType, java.lang.Object, java.util.ArrayList)
	 */
	@Override
	public <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList,
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		for (E oEntry : poList){
				if(oEntry instanceof clsDataStructurePA){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, (clsDataStructurePA)oEntry));
				}
				else if (oEntry instanceof clsPrimaryDataStructureContainer){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, ((clsPrimaryDataStructureContainer)oEntry).getMoDataStructure()));
				}
			}
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.03.2011, 22:34:44
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(pa.tools.clsPair)
	 */
	@Override
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult,
									ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
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
	@Override
	/*public void send_I5_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I5_8_receive)moModuleList.get(45)).receive_I5_8(poMergedPrimaryInformation);
		
		putInterfaceData(I5_8_send.class, poMergedPrimaryInformation);
	}*/
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
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)deepCopy(poEnvironmentalTP);
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
	
	@Override
	public void setMinimalModelMode(boolean pnMinial) {
		mnMinimalModel = pnMinial;
	}

	@Override
	public boolean getMinimalModelMode() {
		return mnMinimalModel;
	}
	
}
