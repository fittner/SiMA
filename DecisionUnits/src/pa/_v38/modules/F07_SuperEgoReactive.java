/**
 * F7_SuperEgoReactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author gelbard
 * 02.05.2011, 15:47:53
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.modules.eProcessType;
import pa._v38.modules.ePsychicInstances;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_11_send;
import pa._v38.interfaces.modules.I5_12_receive;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_13_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Checks incoming drives and perceptions according to internalized rules.
 * If one internalized rule fires, a forbidden drive or perception is detected.
 * The forbidden drive or perception is added to the list of forbidden drives or the list of forbidden perceptions, respectively. 
 * The list with forbidden drives is sent to "F06: Defense mechanisms for drives".
 * The list with forbidden perceptions is sent to "F19: Defense mechanisms for perseption".
 * F06 or F19 (Ego) can decide now to defend the forbidden drives or not.
 * 
 * moSuperEgoStrength is a personality parameter which determines the strength of the Super-Ego.
 * Some Super-Ego rules are only affective if the moSuperEgoStrength is above a certain value.
 * 
 * @author zeilinger, gelbard
 * 07.05.2012, 15:47:53
 * 
 */
public class F07_SuperEgoReactive extends clsModuleBase
	implements I5_12_receive, I5_10_receive, I5_11_send, I5_13_send{

	public static final String P_MODULENUMBER = "7";
	private static final int threshold_psychicEnergy = 10;
	double moSuperEgoStrength; // personality parameter to adjust the strength of Super-Ego
	
	// Das muss erst noch von Isabella implementiert werden. Ist jetzt einmal nur vorbereitet.
	private static final int consumed_psychicEnergyPerInteration = 1;
	
	//AW 20110522: New inputs
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Input;
	
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Output;
	
	@SuppressWarnings("unused")
	private Object moMergedPrimaryInformation;
	private ArrayList<clsDriveMeshOLD> moDrives;
	private ArrayList<String> moForbiddenDrives;
	private ArrayList<clsPair<eContentType, String>> moForbiddenPerceptions;
	
	private DT3_PsychicEnergyStorage moDT3_PsychicEnergyStorage = new DT3_PsychicEnergyStorage();
	
	private ArrayList<clsEmotion> moEmotions_Input;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:49:49
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F07_SuperEgoReactive(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		moForbiddenDrives = new ArrayList<String>(); //TD 2011/07/20 - added initialization of member field
		moForbiddenPerceptions = new ArrayList<clsPair<eContentType,String>>(); //TD 2011/07/20 - added initialization of member field
		
		applyProperties(poPrefix, poProp); 
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		oProp.setProperty(pre+"superego", 0.5);	// this parameter adjusts the strength of the Super-Ego
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
		moSuperEgoStrength = poProp.getPropertyDouble(pre + "superego");
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		//text += toText.valueToTEXT("moAssociatedMemories_Input", moAssociatedMemories_Input);	
		//text += toText.valueToTEXT("moAssociatedMemories_Output", moAssociatedMemories_Output);
		text += toText.valueToTEXT("moDrives", moDrives);		
		text += toText.valueToTEXT("moForbiddenDrives", moForbiddenDrives);		
		text += toText.valueToTEXT("moForbiddenPerceptions", moForbiddenPerceptions);		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh) {
		try {
			//moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		//moAssociatedMemories_Input = (ArrayList<clsPrimaryDataStructureContainer>) deepCopy(poAssociatedMemories);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_12_receive#receive_I5_12(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_12(ArrayList<clsDriveMeshOLD> poDrives,  ArrayList<clsEmotion> poEmotions) {
		
		moDrives = (ArrayList<clsDriveMeshOLD>) deepCopy(poDrives); 
		moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//AW 20110522: Input from perception
		try {
			//moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.cloneGraph();
			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		//AW 20110522: Input from associated memories
		//moAssociatedMemories_Output = moAssociatedMemories_Input;
		
		
		// if there is enough psychic energy
		if (moDT3_PsychicEnergyStorage.send_D3_1(mnModuleNumber) > threshold_psychicEnergy
				/* for test purposes only: */ || true)
			checkInternalizedRules();	 // check perceptions and drives, and apply internalized rules	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:12
	 * 
	 * Super-Ego checks perception and drives
	 * 
	 */
	private void checkInternalizedRules() {
		// ToDo FG: These are just samples for internalized rules.
		//          All the internalized rules must be stored in an (XML-)file and processed one after another
		
		// If no Super-Ego rules fires, the list with forbidden drives must be empty
		moForbiddenDrives.clear();
		
		// If no Super-Ego rules fires, the list with forbidden perceptions must be empty
		moForbiddenPerceptions.clear();
		
		// sample rule for repression of drives
		if (moSuperEgoStrength >= 0.5)
			if (searchInDM ("NOURISH") &&
				searchInTPM (eContentType.ENTITY, "ARSIN") &&
				searchInTPM (eContentType.ENTITY, "CAKE"))
				// If all the conditions above are true then Super-Ego can fire.
				// An internalized rule was detected to be true.
				// So the Super-Ego conflicts now with Ego and Super-Ego requests from Ego to activate defense.
				
				
				// The following drive was found by Super-Ego as inappropriate or forbidden.
				// Therefore the Super-Ego marks the drive as forbidden and sends the mark to the Ego.
				if (!moForbiddenDrives.contains("NOURISH")) // no duplicate entries
					moForbiddenDrives.add("NOURISH");
		
		// sample rule for denial of perceptions
		if (moSuperEgoStrength >= 0.5)
			if (searchInDM ("NOURISH") &&
				searchInTP (eContentType.COLOR, "Farbe eine feindlichen ARSin") &&
				searchInTPM (eContentType.ENTITY, "ARSIN") &&
				searchInTPM (eContentType.ENTITY, "CAKE"))
				// If all the conditions above are true then Super-Ego can fire.
				// An internalized rule was detected to be true.
				// So the Super-Ego conflicts now with Ego and Super-Ego requests from Ego to activate defense.
			
				
				// The following perception was found by Super-Ego as inappropriate or forbidden.
				// Therefore the Super-Ego marks the perception as forbidden and sends the mark to the Ego.
				if (!moForbiddenPerceptions.contains(new clsPair<String, String> ("ENTITY", "CAKE")))
					moForbiddenPerceptions.add(new clsPair<eContentType, String> (eContentType.ENTITY, "CAKE"));

		// sample rule for conversion of aggressive drive energy into anxiety
		if (moSuperEgoStrength >= 0.8)
			if (searchInDM ("BITE", 0.20))
				if (!moForbiddenDrives.contains("BITE"))
					moForbiddenDrives.add("BITE");
		
/*		
		// sample rules to test repression
		if (searchInDM ("NOURISH")) {
			if (!moForbiddenDrives.contains("NOURISH"))
				moForbiddenDrives.add("NOURISH");
		}

		if (searchInDM ("AGGRESSIVE_GENITAL")) {
			if (!moForbiddenDrives.contains("AGGRESSIVE_GENITAL"))
				moForbiddenDrives.add("AGGRESSIVE_GENITAL");
		}
*/

		
/*		
		// sample rule to recognize cake
		if (searchInTPM ("ENTITY", "CAKE")) {
			// The following perception was found by Super-Ego as inappropriate or forbidden.
			// Therefore the Super-Ego marks the perception as forbidden and sends the mark to the Ego.
			if (!moForbiddenPerceptions.contains(new clsPair<String, String> ("ENTITY", "CAKE")))
				moForbiddenPerceptions.add(new clsPair<String, String> ("ENTITY", "CAKE"));
		}
*/		
		/*
		// sample rule to test denial
		if (searchInTI("IMAGE", "A3TOP")) {
			if (!moForbiddenPerceptions.contains(new clsPair<String, String> ("IMAGE", "A3TOP"))) // no duplicate entries
				moForbiddenPerceptions.add(new clsPair<String, String> ("IMAGE", "A3TOP"));
		}
		*/		
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:48
	 * 
	 * searches in the input-perception for example for a color red
	 * 
	 */
	private boolean searchInTP (eContentType oContentType, String oContent) {
		// search in perceptions
		// Todo FG: Die Frage ist generell: "Suche ich in perceptions oder in associated memories???"
		
		//Get a list of all attribute associations in a 
		//ArrayList<clsAssociationAttribute> oAttributeAss = clsDataStructureTools.getTPAssociations(moPerceptionalMesh_OUT, oContentType, oContent, 2, true, 1);
		
		//Association attribute are delivered here
		ArrayList<clsPair<eContentType, String>> oContentTypeAndContentList = new ArrayList<clsPair<eContentType, String>>();
		oContentTypeAndContentList.add(new clsPair<eContentType, String>(oContentType, oContent));
		ArrayList<clsDataStructurePA> oAttributeAss = clsMeshTools.getDataStructureInTPM(moPerceptionalMesh_OUT, eDataType.TP, oContentTypeAndContentList, true, 1);
		if (oAttributeAss.isEmpty()==false) {
			return true;
		}
		

		return false;
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like a ARSIN
	 * 
	 */
	private boolean searchInTPM (eContentType oContentType, String oContent) {
		// search in perceptions
		
		//Get all TPM (in format DataStructurePA), which fulfill the filter contenttype and content
		ArrayList<clsPair<eContentType, String>> oContentTypeAndContentList = new ArrayList<clsPair<eContentType, String>>();
		oContentTypeAndContentList.add(new clsPair<eContentType, String>(oContentType, oContent));
		ArrayList<clsDataStructurePA> oTPMList = clsMeshTools.getDataStructureInTPM(moPerceptionalMesh_OUT, eDataType.TPM, oContentTypeAndContentList, true, 1);
		
		if (oTPMList.isEmpty()==false) {
			return true;
		}
		

		return false;
	}

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 07.09.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like a ARSIN
	 * 
	 */
	@SuppressWarnings("unused")
	private boolean searchInTI (String oContentType, String oContent) {
		// search in perceptions
		
		ArrayList<clsThingPresentationMesh> oImages = clsMeshTools.getAllTPMImages(moPerceptionalMesh_OUT, 2);	//Parameter 2=2 means, search the current TPM + one level of external structures
		ArrayList<clsThingPresentationMesh> oFilteredImages = clsMeshTools.filterTPMList(oImages, null, oContent, true);
		
		if (oFilteredImages.isEmpty()==false) {
			return true;
		}
		
		return false;
	}

	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:50
	 * 
	 * searches in the input-DriveMesh for example for NOURISH
	 * 
	 */
	private boolean searchInDM (String oContent) {		
		// search in drives
		for(clsDriveMeshOLD oDrives : moDrives){
			// check DriveMesh
			// oDrives.b.getMoContent() = for example "NOURISH"
			// oDrives.b.getMoContentType() =  for example "LIFE"
			if (oDrives.getMoContent().equalsIgnoreCase(oContent)){
				return true;
			}
		}
		return false;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.06.2012, 17:06:50
	 * 
	 * searches in the input-DriveMesh for example for NOURISH
	 * and check for a certain quota of affect
	 * 
	 */
	private boolean searchInDM (String oContent, double oQuotaOfAffect) {		
		// search in drives
		for(clsDriveMeshOLD oDrives : moDrives){
			// check DriveMesh
			// oDrives.b.getMoContent() = for example "NOURISH"
			// oDrives.b.getMoContentType() =  for example "LIFE"
			if (oDrives.getMoContent().equalsIgnoreCase(oContent) &&
				oDrives.getPleasure() >= oQuotaOfAffect){
				return true;
			}
		}
		return false;
	}	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_13(moForbiddenDrives, moDrives); 
		send_I5_11(moForbiddenPerceptions, moPerceptionalMesh_OUT, moEmotions_Input); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		moDescription = "Based on internalized rules, Super-Ego checks incoming perceptions and drives. If the internalized rules are violated Super-Ego requests from F06 and F19 to activate the defense mechanisms.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_13_send#send_I5_13(java.util.ArrayList)
	 */
	@Override
	public void send_I5_13(ArrayList<String> poForbiddenDrives, ArrayList<clsDriveMeshOLD> poData) {
		((I5_13_receive)moModuleList.get(6)).receive_I5_13(poForbiddenDrives, poData);
		
		putInterfaceData(I5_13_send.class, poForbiddenDrives, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_11_send#send_I5_11(java.util.ArrayList)
	 */
	@Override
	public void send_I5_11(ArrayList<clsPair<eContentType, String>> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsEmotion> poEmotions) {
		((I5_11_receive)moModuleList.get(19)).receive_I5_11(poForbiddenPerceptions, poPerceptionalMesh, poEmotions);
		
		putInterfaceData(I5_13_send.class, poForbiddenPerceptions, poPerceptionalMesh);
	}
}
