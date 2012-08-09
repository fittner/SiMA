/**
 * E19_DefenseMechanismsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.interfaces.modules.I5_14_receive;
import pa._v38.interfaces.modules.I5_15_receive;
import pa._v38.interfaces.modules.I5_15_send;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_16_receive;
import pa._v38.interfaces.modules.I5_16_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * Defends forbidden perceptions. Super-Ego (F7, F55) sends a list with forbidden perceptions to F19. F19 decides whether to defend the forbidden perceptions or not.
 * If F19 decided to defend the forbidden perceptions F19 chooses the defense mechanism (denial, projection, depreciation, ...).
 * 
 * Implemented defense mechanisms for perception:
 * - denial (Verdrängung)
 * - idealization, depreciation (Idealisierung, Entwertung): only positive (negative) associations are perceived with an object
 * 
 * @author gelbard
 * 07.05.2012, 14:35:08
 * 
 */
public class F19_DefenseMechanismsForPerception extends clsModuleBaseKB implements 
			I5_14_receive, I5_11_receive, I5_15_send, I5_16_send{
	public static final String P_MODULENUMBER = "19";
	
	//AW 20110522: New inputs
	//private clsPrimaryDataStructureContainer moEnvironmentalPerception_Input;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Input;
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	
	//private clsPrimaryDataStructureContainer moEnvironmentalPerception_Output;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Output;
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	
	// Perceptions and emotions not "liked" by Super-Ego
	private ArrayList<clsPair<eContentType, String>> moForbiddenPerceptions_Input;
	private ArrayList<eEmotionType>                  moForbiddenEmotions_Input;
	
	//private ArrayList<clsPrimaryDataStructureContainer> moSubjectivePerception_Input; 
	//private ArrayList<clsPrimaryDataStructureContainer> moFilteredPerception_Output; 
	private ArrayList<pa._v38.memorymgmt.datatypes.clsThingPresentation> moDeniedThingPresentations;
	private ArrayList<clsAssociationDriveMesh> moDeniedAffects;
	private ArrayList<clsPrimaryDataStructure> moQuotasOfAffect_Output = new ArrayList<clsPrimaryDataStructure>(); // anxiety which is generated while repressing content
    private ArrayList<clsDriveMesh> moInput;
	
	// defense mechanisms must be activated by a psychoanalytic conflict
	// defense_active symbolizes an unpleasure value which is generated by the psychonanlytic conflict
	boolean defense_active = false;
	
	private DT2_BlockedContentStorage moBlockedContentStorage; // only needed here in F19 to initialize the blocked content storage

	private eContentType moBlockedContentType = eContentType.RIREPRESSED;
	
	private ArrayList<clsEmotion> moEmotions_Input; 
	
	/**
	 * DOCUMENT (GELBARD) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:41:41
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F19_DefenseMechanismsForPerception(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces,
			ArrayList<Object>> poInterfaceData, DT2_BlockedContentStorage poBlockedContentStorage,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		moDeniedAffects = new ArrayList<clsAssociationDriveMesh>();  //TD 2011/07/20 - added initialization of member field
 		applyProperties(poPrefix, poProp);	
 		
 		//Get Blocked content storage
		moBlockedContentStorage = poBlockedContentStorage;
		
		//Fill the blocked content storage with initial data from protege
		moBlockedContentStorage.addAll(initialFillRepressedContent());
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
		String text = "";
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		//text += toText.valueToTEXT("moAssociatedMemories_Input", moAssociatedMemories_Input);
		//text += toText.valueToTEXT("moAssociatedMemories_Output", moAssociatedMemories_Output);
		text += toText.listToTEXT("moForbiddenPerceptions_Input", moForbiddenPerceptions_Input);
		text += toText.listToTEXT("moDeniedThingPresentations", moDeniedThingPresentations);
		text += toText.listToTEXT("moDeniedAffects", moDeniedAffects);
		text += toText.listToTEXT("moQuotasOfAffect_Output", moQuotasOfAffect_Output);

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
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
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
	 * @author gelbard
	 * 11.08.2009, 16:28:09
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_11(ArrayList<clsPair<eContentType, String>> poForbiddenPerceptions,
			                  clsThingPresentationMesh poPerceptionalMesh,
			                  ArrayList<eEmotionType> poForbiddenEmotions,
			                  ArrayList<clsEmotion> poEmotions) {
		try {
			//moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);
		moForbiddenPerceptions_Input = poForbiddenPerceptions;
		moForbiddenEmotions_Input    = poForbiddenEmotions;

	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 03.05.2011, 17:36:10
	 * 
	 * @see pa._v38.interfaces.modules.I5_14_receive#receive_I5_14(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_14(ArrayList<clsDriveMesh> poData) {
		
		moInput = (ArrayList<clsDriveMesh>) deepCopy(poData);
	}		

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moPerceptionalMesh_OUT = moPerceptionalMesh_IN;		
		//moAssociatedMemories_Output      = moAssociatedMemories_Input;
		
		detect_conflict_and_activate_defense_machanisms();
		
	}
	
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 28.03.2012, 17:30:00
	 * 
	 * This function detects a psychoanalytic conflict
	 * and activates the defense mechanisms
	 * and chooses a defense mechanism to resolve the conflict.
	 *
	 */
	private void detect_conflict_and_activate_defense_machanisms() {
		
		 // empty the list from last step otherwise list only grows
		 moQuotasOfAffect_Output.clear();
		
		 // check for a psychoanalytic conflict
		 // defense mechanisms are delayed by one cycle to produce a situation where conflict exists and no action plans are executed
		 if (!moForbiddenPerceptions_Input.isEmpty() && !defense_active)
		 {
			 // conflicting events exist -> activate conflict -> activate defense mechanisms but do not defend yet. (defense will work in the next cycle)
			 defense_active = true;
			 
			 // send quota of affect 999.9 via I5.17 to produce a "CONFLICT"-signal in F20
			 clsAffect oAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>("AFFECT", 999.9)); 
			 moQuotasOfAffect_Output.add(oAffect);
			 
			 return;
		 }
		 else if (moForbiddenPerceptions_Input.isEmpty())
		 {
			 // no conflicting events -> deactivate defense mechanisms
			 defense_active = false;
			 return;
		 }
		 // Defense mechanisms start to work.
		 
		 
		 // Super-Ego requests to defend the drives moForbiddenDrives_Input
		 // Ego decides now which defense mechanisms to apply (depending on the quota of affect of the forbidden drive)
		 
		 // get quota of affect of forbidden drive (for now only one forbidden drive is possible)
		 // TODO: FG many forbidden drives possible
		 // TODO: FG implement the function getQuotaOfAffect(moForbiddenPerceptions_Input); 
		 //double oQoA = getQuotaOfAffect(moForbiddenPerceptions_Input);
		 
		 // select defense mechanism
		 //if (oQoA <= 0.9)
		 defenseMechanism_Denial (moForbiddenPerceptions_Input);
		 //defenseMechanism_ReversalOfAffect (moForbiddenEmotions_Input);

		 // -> if the quota of affect of the forbidden drive is greater than 0.9, the drive can pass the defense (no defense mechanisms is activated)
	}

	
		
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like a ARSIN
	 * 
	 */
	private void defenseMechanism_Denial (ArrayList<clsPair<eContentType, String>> oForbiddenPerceptions) {
		
    	// If nothing to deny return immediately (otherwise NullPointerException)
    	if (oForbiddenPerceptions == null) return;
		
		// check list of forbidden perceptions
		for(clsPair<eContentType, String> oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			eContentType oContentType = oOneForbiddenPerception.a;
			String oContent     = oOneForbiddenPerception.b;
			
			clsDataStructurePA oFoundObject = null;
			
			// search in perceptions
			//Get all images and objects in the mesh
			//ArrayList<clsThingPresentationMesh> oTPMList = clsDataStructureTools.getTPMObjects(moPerceptionalMesh_OUT, oContentType, oContent, true, 1);
			ArrayList<clsPair<eContentType, String>> oContentTypeAndContentList = new ArrayList<clsPair<eContentType, String>>();
			oContentTypeAndContentList.add(new clsPair<eContentType, String>(oContentType, oContent));
			ArrayList<clsDataStructurePA> oTPMList = clsMeshTools.getDataStructureInTPM(moPerceptionalMesh_OUT, eDataType.TPM, oContentTypeAndContentList, true, 1);
			if (oTPMList.isEmpty()==false) {
				oFoundObject = oTPMList.get(0);
			}
				
			// or oContainer can contain a thing-presentation
			// in this case
			// check a TP
		
			//The attribute list is clsAssociationAttribute

			ArrayList<clsDataStructurePA> oAttributeList = clsMeshTools.getDataStructureInTPM(moPerceptionalMesh_OUT, eDataType.TP, oContentTypeAndContentList, true, 1);
			//ArrayList<clsAssociationAttribute> oAttributeList = clsDataStructureTools.getTPAssociations(moPerceptionalMesh_OUT, oContentType, oContent, 0, true, 1);
			if (oAttributeList.isEmpty()==false) {
				oFoundObject = oAttributeList.get(0);
			}	
		
			// if Perception found -->	
			if (oFoundObject!=null) {
				//Delete object from the perception
				
				if (oFoundObject instanceof clsThingPresentationMesh)
				clsMeshTools.deleteObjectInMesh((clsThingPresentationMesh) oFoundObject);
			}
		}
	}

	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 06.07.2012, 12:23:49
	 * 
	 * Defense mechanism idealization (perceives only the good properties of an object and denies the negative properties)
	 * 
	 */
	private void defenseMechanism_Idealization (ArrayList<clsPair<eContentType, String>> oForbiddenPerceptions) {
		ArrayList<clsThingPresentationMesh> oListWithNegativeObjects = new ArrayList<clsThingPresentationMesh>();
		clsThingPresentationMesh oNegativeObject;
		
		oNegativeObject = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<String, Object, Object>("ENTITY", new ArrayList<clsPhysicalRepresentation>(), "RED")); 
		oListWithNegativeObjects.add(oNegativeObject);
		oNegativeObject = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<String, Object, Object>("ENTITY", new ArrayList<clsPhysicalRepresentation>(), "ROUND")); 
		oListWithNegativeObjects.add(oNegativeObject);
		oNegativeObject = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<String, Object, Object>("ENTITY", new ArrayList<clsPhysicalRepresentation>(), "BOESE_SCHWIEGERMUTTER")); 
		oListWithNegativeObjects.add(oNegativeObject);

		deleteAssociationsFromPerception (oForbiddenPerceptions, oListWithNegativeObjects);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 06.07.2012, 13:23:49
	 * 
	 * Defense mechanism depreciation (perceives only the bad properties of an object and denies the positive properties)
	 * 
	 */
	private void defenseMechanism_Depreciation (ArrayList<clsPair<eContentType, String>> oForbiddenPerceptions) {
		ArrayList<clsThingPresentationMesh> oListWithPositiveObjects = new ArrayList<clsThingPresentationMesh>();
		clsThingPresentationMesh oPositiveObject;
		
		oPositiveObject = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<String, Object, Object>("ENTITY", new ArrayList<clsPhysicalRepresentation>(), "GREEN")); 
		oListWithPositiveObjects.add(oPositiveObject);
		oPositiveObject = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<String, Object, Object>("ENTITY", new ArrayList<clsPhysicalRepresentation>(), "FLAT")); 
		oListWithPositiveObjects.add(oPositiveObject);
		oPositiveObject = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<String, Object, Object>("ENTITY", new ArrayList<clsPhysicalRepresentation>(), "SONNE")); 
		oListWithPositiveObjects.add(oPositiveObject);
		deleteAssociationsFromPerception (oForbiddenPerceptions, oListWithPositiveObjects);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 06.07.2012, 12:23:49
	 * 
	 * deletes all positive (or all negative) associations in a TPM
	 * is needed for defense mechanism idealization/depreciation
	 * 
	 */
	private void deleteAssociationsFromPerception (ArrayList<clsPair<eContentType, String>> oForbiddenPerceptions, ArrayList<clsThingPresentationMesh> oListWithPositiveOrNegativeObjects) {
		
	   	// If no perception in list to defend return immediately (otherwise NullPointerException)
	   	if (oForbiddenPerceptions == null) return;
		
		// check list of forbidden perceptions
		for(clsPair<eContentType, String> oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			eContentType oContentType = oOneForbiddenPerception.a;
			String oContent     = oOneForbiddenPerception.b;
			
			clsDataStructurePA oFoundObject = null;
			
			// search in perceptions
			//Get all images and objects in the mesh
			//ArrayList<clsThingPresentationMesh> oTPMList = clsDataStructureTools.getTPMObjects(moPerceptionalMesh_OUT, oContentType, oContent, true, 1);
			ArrayList<clsPair<eContentType, String>> oContentTypeAndContentList = new ArrayList<clsPair<eContentType, String>>();
			oContentTypeAndContentList.add(new clsPair<eContentType, String>(oContentType, oContent));
			ArrayList<clsDataStructurePA> oTPMList = clsMeshTools.getDataStructureInTPM(moPerceptionalMesh_OUT, eDataType.TPM, oContentTypeAndContentList, true, 1);
			if (oTPMList.isEmpty()==false) {
				oFoundObject = oTPMList.get(0);
			}
				
			// or oContainer can contain a thing-presentation
			// in this case
			// check a TP
		
			//The attribute list is clsAssociationAttribute

			ArrayList<clsDataStructurePA> oAttributeList = clsMeshTools.getDataStructureInTPM(moPerceptionalMesh_OUT, eDataType.TP, oContentTypeAndContentList, true, 1);
			//ArrayList<clsAssociationAttribute> oAttributeList = clsDataStructureTools.getTPAssociations(moPerceptionalMesh_OUT, oContentType, oContent, 0, true, 1);
			if (oAttributeList.isEmpty()==false) {
				oFoundObject = oAttributeList.get(0);
			}	
		
			// if Perception found -->	
			if (oFoundObject!=null) {
				// Search all associations of an object and delete all negative (or positive) associations.
				// The negative (or positive) associations are listed in oListWithPositiveOrNegativeObjects
				
				if (oFoundObject instanceof clsThingPresentationMesh)
				//clsMeshTools.deleteObjectInMesh((clsThingPresentationMesh) oFoundObject);
					for(clsThingPresentationMesh oPositiveOrNegativeObject : oListWithPositiveOrNegativeObjects) {
						clsMeshTools.deleteAssociationInObject((clsThingPresentationMesh) oFoundObject, oPositiveOrNegativeObject);
					}
			}
		}
	}

	
	private void defenseMechanism_ReversalOfAffect(ArrayList<eEmotionType> oForbiddenEmotions_Input) {
	   	// If no emotion in list to defend return immediately (otherwise NullPointerException)
	   	if (oForbiddenEmotions_Input == null) return;
		
		// check list of forbidden emotions
		for(eEmotionType oOneForbiddenEmotion : oForbiddenEmotions_Input) {
			for(clsEmotion oOneEmotion : moEmotions_Input) {
				if(oOneEmotion.getMoContent() == oOneForbiddenEmotion) {
					if(moEmotions_Input.contains(eEmotionType.FEAR)) {
						
						// add the old emotion intensity to the emotion intensity of the emotion FEAR
						clsEmotion oEmotionFear = moEmotions_Input.get(oForbiddenEmotions_Input.indexOf(eEmotionType.FEAR));
						oEmotionFear.setMrEmotionIntensity(oEmotionFear.getMrEmotionIntensity() + oOneEmotion.getMrEmotionIntensity());
						// hier muss man noch schauen, ob die EmotionIntensity groesser als 1 ist. Wenn ja -> was tun ???
						
						// remove the old emotion from the input list of emotions
						moEmotions_Input.remove(oOneEmotion);
						break;
					}
					
				}
			}
		}

	}
	
	/**
	 * This function load all images with the content type "IMAGE:REPRESSED" from the knowledgebase. Those images are defined in
	 * Protege
	 * 
	 * (wendt)
	 *
	 * @since 24.10.2011 09:40:43
	 *
	 * @return
	 */
	private ArrayList<clsThingPresentationMesh> initialFillRepressedContent() {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oSearchResult = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		clsThingPresentationMesh newTPMImage = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.TPM, moBlockedContentType), new ArrayList<clsAssociation>(), "EMPTY");
		//clsPrimaryDataStructureContainer oPattern = new clsPrimaryDataStructureContainer(newTI, new ArrayList<clsAssociation>());
		
		searchMesh(newTPMImage, oSearchResult, moBlockedContentType, 0.0, 2);	//Set pnLevel=2, in order to add direct matches
		
		for (clsPair<Double, clsDataStructurePA> oPair : oSearchResult) {
			oRetVal.add((clsThingPresentationMesh) oPair.b);
		}
		
		return oRetVal;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_15(moPerceptionalMesh_OUT, moEmotions_Input);
		send_I5_16(moQuotasOfAffect_Output, moEmotions_Input);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I2_10_send#send_I2_10(java.util.ArrayList)
	 */
	@Override
	public void send_I5_15(clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsEmotion> poEmotions) {
		((I5_15_receive)moModuleList.get(21)).receive_I5_15(poPerceptionalMesh, poEmotions);
		putInterfaceData(I5_15_send.class, poPerceptionalMesh);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I5_16_send#send_I5_16(java.util.ArrayList)
	 */
	@Override
	public void send_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions) {
		((I5_16_receive)moModuleList.get(20)).receive_I5_16(poAffectOnlyList, poEmotions);
		putInterfaceData(I5_16_send.class, poAffectOnlyList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:55
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:55
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:41:48
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
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Analogous to {E6}, {E19} evaluates incoming perceptions if they are allowed to become (pre-)conscious contents. Here, focus is on whether this ``thought'' is allowed or not. This is in opposition to defense mechanisms for drives where the focus is on the acceptability of satisfying a drive demand with a certain object. The same set of mechanisms can be used for {E6} and {E19}. They differ by the available data. {E6} has drive demands, internalized rules, and knowledge about its drives at hand; {E19} has only internalized rules and the perception.";
	}
}
