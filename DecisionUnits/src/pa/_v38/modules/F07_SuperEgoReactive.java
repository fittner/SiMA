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
import pa._v38.interfaces.itfGraphInterface;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_11_send;
import pa._v38.interfaces.modules.I5_12_receive;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_13_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsQuadruppel;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
//import du.enums.pa.eContext;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;

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
	implements I5_12_receive, I5_10_receive, I5_11_send, I5_13_send, itfGraphInterface{

	public static final String P_MODULENUMBER = "07";
	
	public static final String P_SUPER_EGO_STRENGTH = "SUPER_EGO_STRENGTH";
	public static final String P_PSYCHIC_ENERGY_THESHOLD = "PSYCHIC_ENERGY_THESHOLD";
	public static final String P_PSYCHIC_ENERGY_PRIORITY = "PSYCHIC_ENERGY_PRIORITY";
	
	private static int threshold_psychicEnergy;
	private static int msPriorityPsychicEnergy;
	private double moSuperEgoStrength; // personality parameter to adjust the strength of Super-Ego
	
	@SuppressWarnings("unused")
	// Das muss erst noch implementiert werden. Ist jetzt einmal nur vorbereitet.
	private static final int consumed_psychicEnergyPerInteration = 1;
	
	//AW 20110522: New inputs
	private clsThingPresentationMesh moPerceptionalMesh_IN;	
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	
	@SuppressWarnings("unused")
	private Object moMergedPrimaryInformation;
	private ArrayList<clsDriveMesh> moDrives;
	private ArrayList<clsPair<eDriveComponent, eOrgan>> moForbiddenDrives;
	private ArrayList<clsPair<eContentType, String>> moForbiddenPerceptions;
	private ArrayList<eEmotionType>moForbiddenEmotions;
	
	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	private ArrayList<clsEmotion> moEmotions_Input;
	private ArrayList<String> Test= new ArrayList<String>() ;
	private ArrayList<clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>> moSuperEgoDrivesRules =
			new ArrayList<clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>>();
	
	private ArrayList<clsPair<String,String>>  moSuperEgoEmotionsRules =new ArrayList<clsPair<String,String>> ();
	private ArrayList<clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>> moSuperEgoPerceptionsRules =new ArrayList<clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>>();
	
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
	 * @param poPsychicEnergyStorage 
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F07_SuperEgoReactive(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT3_PsychicEnergyStorage poPsychicEnergyStorage , clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
		
		moForbiddenDrives = new ArrayList<clsPair<eDriveComponent, eOrgan>>();
		moForbiddenPerceptions = new ArrayList<clsPair<eContentType,String>>();
		moForbiddenEmotions = new ArrayList<eEmotionType>();
		
		applyProperties(poPrefix, poProp); 
		
		threshold_psychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_THESHOLD).getParameterInt();
		msPriorityPsychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_PRIORITY).getParameterInt();
		moSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SUPER_EGO_STRENGTH).getParameterDouble();

	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * *
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		text += toText.valueToTEXT("moDrives", moDrives);
		text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
		text += toText.listToTEXT("--------------------------------moSuperEgoDrivesRules-----------------------------", moSuperEgoDrivesRules);
		text += toText.listToTEXT("--------------------------------moSuperEgoEmotionsRules---------------------------", moSuperEgoEmotionsRules);
		text += toText.listToTEXT("--------------------------------moSuperEgoPerceptionsRules------------------------", moSuperEgoPerceptionsRules);
		text += toText.listToTEXT("Test", Test);
		text += toText.valueToTEXT("moForbiddenDrives", moForbiddenDrives);		
		text += toText.valueToTEXT("moForbiddenPerceptions", moForbiddenPerceptions);
		text += toText.valueToTEXT("moForbiddenEmotions", moForbiddenEmotions);
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh) {
		try {
			//moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
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
	public void receive_I5_12(ArrayList<clsDriveMesh> poDrives,  ArrayList<clsEmotion> poEmotions) {
		
		moDrives = (ArrayList<clsDriveMesh>) deepCopy(poDrives); 
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
		
		
		
		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber, threshold_psychicEnergy, msPriorityPsychicEnergy);
		// if there is enough psychic energy
	
		

		if (rReceivedPsychicEnergy > threshold_psychicEnergy
				/* for test purposes only: */ || true)
			checkInternalizedRules();	// check perceptions and drives, and apply internalized rules

	}

	/* (non-Javadoc)
	 *
	 * @author kollmann
	 * 17.09.2013, 14:00
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		try {
			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		//clear the list of forbidden drives, every turn
		moForbiddenDrives.clear();
		
		if(moSuperEgoStrength >= 0.5) //if super ego is strong enough - 0.5 is an arbitrary value
		{
			//simple_rule to deal with eating in BODOs vicinity

			ArrayList<String> oEntities = new ArrayList<String> ();

			oEntities.add("BODO");

			simple_rule(eDriveComponent.LIBIDINOUS,
				eOrgan.STOMACH,
				0.0, //FIXME Kollmann: The super-rule for divide should only fire above a certain QoA
				0.1, // Kollmann: When the stomach is full -> LIB/STOM has a QoA ~ 0.04 
				oEntities);
		}
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
	
	
	/**
	 * DOCUMENT (Kollmann) - alternative call to simple_rule without any perceivable entities
	 *
	 * @since 17.09.2013 14:16:37
	 *
	 * @param poComponent the drive component (poComponent.LIBIDINOUSE or poComponent.AGGRESIVE)
	 * @param poOrgan the drive source
	 * @param pnMinQuota the minimum quota of affect the drive needs to activate the rule
	 * @param pnMaxQuota the maximum (>=) QoA the drive can have to activate the rule
	 * @return true if the rule was triggered, false otherwise
	 */
	protected boolean simple_rule(eDriveComponent poComponent, eOrgan poOrgan, double pnMinQuota, double pnMaxQuota)
	{
	    return simple_rule(poComponent, poOrgan, pnMinQuota, pnMaxQuota, new ArrayList<String>());
	}
	
	/**
	 * DOCUMENT (Kollmann) - Checks if the rule, defined by the parameters, is present and, if it is,
	 *                       adds the corresponding eDriveComponent/eOrgan combination to the list of
	 *                       forbidden drives
	 *
	 * @since 17.09.2013 14:18:15
	 *
	 * @param poComponent the drive component (poComponent.LIBIDINOUSE or poComponent.AGGRESIVE) 
	 * @param poOrgan the drive source
	 * @param pnMinQuota the minimum QoA the drive needs to activate the rule
	 * @param pnMaxQuota the maximum QoA the drive can have to activate the rule
	 * @param poPerceivedEntities - a list of perceivable entities - the rule will only trigger if all
	 *                              these entities are currently perceived
	 * @return true if the rule was triggered, false otherwise
	 */
	protected boolean simple_rule(eDriveComponent poComponent, eOrgan poOrgan, double pnMinQuota, double pnMaxQuota,
			ArrayList<String> poPerceivedEntities)
	{
		boolean rule_triggered = false;
		clsPair<eDriveComponent, eOrgan> oDrive;
		
		clsQuadruppel<String, eDriveComponent, eOrgan, Double> oForbiddenDrive = null;
		clsTriple<String, clsQuadruppel<String, eDriveComponent, eOrgan, Double>, ArrayList<String>> oDriveRules=null;
		ArrayList<String> oContentTypeDrives= new ArrayList<String> ();
				
		//check for a fitting drive
		for(clsDriveMesh oDrives : moDrives){
			if (oDrives.getDriveComponent().equals(poComponent) &&
				oDrives.getActualDriveSourceAsENUM().equals(poOrgan) &&
				oDrives.getQuotaOfAffect() >= pnMinQuota &&
				oDrives.getQuotaOfAffect() <= pnMaxQuota)
			{		
				//logging for inspectors
				oForbiddenDrive = new clsQuadruppel<String,eDriveComponent, eOrgan,Double>(
						pnMinQuota + " =< " + poComponent.toString() + "/" + poOrgan.toString() + " >= " + pnMaxQuota,
						poComponent,
						poOrgan,
						pnMinQuota);
				
				//check if entities are perceived
				for(String oEntity : poPerceivedEntities)
				{
					if(searchInTPM(eContentType.ENTITY, oEntity))
					{
						oContentTypeDrives.add("ENTITY=" + oEntity);
					}
				}
	
				if(oContentTypeDrives.size() > poPerceivedEntities.size()) //should never happen
				{
					System.err.println("PROBLEM: found more perceivide entities than specified entities");
				}else if(oContentTypeDrives.size() == poPerceivedEntities.size())
				{
				  //forbid drive, if not yet forbidden
					oDrive = new clsPair<eDriveComponent, eOrgan>(poComponent, poOrgan);
					
					if (!moForbiddenDrives.contains(oDrive)) // no duplicate entries
					{
						moForbiddenDrives.add(oDrive);
						rule_triggered = true;
					}
					
					//log forbidding of drive
					oDriveRules= new clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>("SuperEgoStrength >= 0.5", oForbiddenDrive, oContentTypeDrives);
					if(!moSuperEgoDrivesRules.contains(oDriveRules))
					{
						// add rule info to inspector 
						moSuperEgoDrivesRules.add(oDriveRules);
					}
				}
			}
		}
		
		
		return rule_triggered;
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
		
		// If no Super-Ego rule fires, the lists with forbidden drives, forbidden perceptions, and forbidden emotions must be empty
		moForbiddenDrives     .clear();
		moForbiddenPerceptions.clear();
		moForbiddenEmotions   .clear();
		String oSuperEgoStrength= null;
		
		clsQuadruppel<String, eDriveComponent, eOrgan, Double> oForbiddenDrive = null;
		clsTriple<String, clsQuadruppel<String, eDriveComponent, eOrgan, Double>, ArrayList<String>> oDriveRules=null;
		clsTriple<String, clsQuadruppel<String, eDriveComponent, eOrgan, Double>, ArrayList<String>> oPerceptionRules=null;
		clsPair<String,String> oEmotionRules=null;
		
		
		ArrayList<String> oContentTypeDrives= new ArrayList<String> ();
		ArrayList<String> oContentTypePerceptions= new ArrayList<String> (); 
		// sample rule for repression of drives
		// (eDriveComponent.LIBIDINOUS, eOrgan.STOMACH) means "EAT"
		if (moSuperEgoStrength >= 0.5)
			
			oSuperEgoStrength="SuperEgoStrength >= 0.5";
			
			if (searchInDM (eDriveComponent.LIBIDINOUS, eOrgan.STOMACH, 0.0) &&
				searchInTPM (eContentType.ENTITY, "BODO") &&
				searchInTPM (eContentType.ENTITY, "CAKE")) {
				
				// To view the Rules for drives on the Simulator --> state  
				oForbiddenDrive = new clsQuadruppel<String,eDriveComponent, eOrgan,Double>("Drive Component="+" Hunger",eDriveComponent.LIBIDINOUS, eOrgan.STOMACH, 0.0);
				oContentTypeDrives.add("Entity = BODO");
				oContentTypeDrives.add("CAKE");
				oDriveRules= new clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>(oSuperEgoStrength,oForbiddenDrive,oContentTypeDrives);
							
				// If all the conditions above are true then Super-Ego can fire.
				// That means, an internalized rule was detected to be true.
				// So the Super-Ego conflicts now with Ego. And Super-Ego requests from Ego to activate defense.
				
				
				// The following drive was found by Super-Ego as inappropriate or forbidden.
				// Therefore the Super-Ego marks the drive as forbidden and sends the mark to the Ego.
				clsPair<eDriveComponent, eOrgan> oDrive = new clsPair<eDriveComponent, eOrgan>(eDriveComponent.LIBIDINOUS, eOrgan.STOMACH);
				if (!moForbiddenDrives.contains(oDrive)) // no duplicate entries
					moForbiddenDrives.add(oDrive);
				
				if(!moSuperEgoDrivesRules.contains(oDriveRules)){
					// add Rules for drives on the Simulator --> state 
					moSuperEgoDrivesRules.add(oDriveRules);
					}

			}
		
		// sample rule for denial of perceptions
		// (eDriveComponent.LIBIDINOUS, eOrgan.STOMACH) means "EAT"
		if (moSuperEgoStrength >= 0.5) //0.8
			if (searchInDM (eDriveComponent.LIBIDINOUS, eOrgan.STOMACH, 0.0) &&
				searchInTPM (eContentType.ENTITY, "BODO") &&
				searchInTPM (eContentType.ENTITY, "CAKE"))
				
								
				// If all the conditions above are true then Super-Ego can fire.
				// That means, an internalized rule was detected to be true.
				// So the Super-Ego conflicts now with Ego. And Super-Ego requests from Ego to activate defense.
				
				
				
				// The following perception was found by Super-Ego as inappropriate or forbidden.
				// Therefore the Super-Ego marks the perception as forbidden and sends the mark to the Ego.
				if (!moForbiddenPerceptions.contains(new clsPair<eContentType, String> (eContentType.ENTITY, "CAKE"))){
					moForbiddenPerceptions.add(new clsPair<eContentType, String> (eContentType.ENTITY, "CAKE"));
					
					// To view the Rules for Perceptions on the Simulator --> state
					oForbiddenDrive = new clsQuadruppel<String,eDriveComponent, eOrgan,Double>("Drive Component="+" Hunger",eDriveComponent.LIBIDINOUS, eOrgan.STOMACH, 0.0);
					oContentTypePerceptions.add("EntityOfPerception= CAKE");
					oPerceptionRules= new clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>(oSuperEgoStrength,oForbiddenDrive,oContentTypePerceptions);
				
					
					if(!moSuperEgoPerceptionsRules.contains(oPerceptionRules)){
						// add Rules for Perceptions on the Simulator --> state
						moSuperEgoPerceptionsRules.add(oPerceptionRules);
						}
				}

		// sample rule for conversion of emotion anger into emotion fear (reversal of affect)
		if (moSuperEgoStrength >= 0.5)
			if (searchInEmotions (eEmotionType.ANGER))
				// To view the Rules for Emotions on the Simulator --> state
				oEmotionRules= new clsPair<String,String> ("moSuperEgoStrength >= 0.5","ANGER");
				if (!moForbiddenEmotions.contains(eEmotionType.ANGER))
					moForbiddenEmotions.add(eEmotionType.ANGER);
				
					
				if(!moSuperEgoEmotionsRules.contains(oEmotionRules)){
					// add Rules for Emotions on the Simulator --> state
					moSuperEgoEmotionsRules.add(oEmotionRules);
				}
		
		
		// sample rule for conversion of emotion grief into emotion fear (reversal of affect)
		/*if (moSuperEgoStrength >= 0.5)
			if (searchInEmotions (eEmotionType.GRIEF))
				if (!moForbiddenEmotions.contains(eEmotionType.GRIEF))
					moForbiddenEmotions.add(eEmotionType.GRIEF);
		// to Try 
		if (moSuperEgoStrength >= 0.5)
			if (searchInEmotions (eEmotionType.FEAR))
				if (!moForbiddenEmotions.contains(eEmotionType.FEAR))
					moForbiddenEmotions.add(eEmotionType.FEAR);*/
		
		

		// sample rule for conversion of aggressive drive energy into anxiety
		// (eDriveComponent.AGGRESSIVE, eOrgan.STOMACH) means "BITE"
		// (by repressing the aggressive drive energy, anxiety is produced)

		if (moSuperEgoStrength >= 0.5) //0.8
			if (searchInDM (eDriveComponent.AGGRESSIVE, eOrgan.STOMACH, 0.39)) {

				
				clsPair<eDriveComponent, eOrgan> oDrive = new clsPair<eDriveComponent, eOrgan>(eDriveComponent.AGGRESSIVE, eOrgan.STOMACH);
				oForbiddenDrive = new clsQuadruppel<String,eDriveComponent, eOrgan,Double>("Drive Component="+"Hunger",eDriveComponent.AGGRESSIVE, eOrgan.STOMACH, 0.39);
				oDriveRules= new clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>(oSuperEgoStrength,oForbiddenDrive,null);
					
				if (!moForbiddenDrives.contains(oDrive))
					moForbiddenDrives.add(oDrive);
				if(!moSuperEgoDrivesRules.contains(oDriveRules)){
					moSuperEgoDrivesRules.add(oDriveRules);
					}

			}
		
	
		
		
		// only for test purpose
		//if (moDrives != null)
		//	moForbiddenDrives.add("BITE");

		
/*		
		// sample rule to recognize cake
		if (searchInTPM (eContentType.ENTITY, "CAKE")) {
			// The following perception was found by Super-Ego as inappropriate or forbidden.
			// Therefore the Super-Ego marks the perception as forbidden and sends the mark to the Ego.
			if (!moForbiddenPerceptions.contains(new clsPair<String, String> ("ENTITY", "CAKE")))
				moForbiddenPerceptions.add(new clsPair<String, String> ("ENTITY", "CAKE"));
		}
*/		
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like ARSIN
	 * 
	 */
	private boolean searchInTPM (eContentType oContentType, String oContent) {
		
		// search in perceptions
		ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_OUT).getMoInternalAssociatedContent();
		for(clsAssociation oAssociation : oInternalAssociations){
			if (oAssociation.getMoAssociationElementB() instanceof clsThingPresentationMesh)
				if( ((clsThingPresentationMesh)oAssociation.getMoAssociationElementB()).getMoContentType().equals(oContentType) &&
					((clsThingPresentationMesh)oAssociation.getMoAssociationElementB()).getMoContent().equals(oContent) )
					return true;
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
	private boolean searchInDM (eDriveComponent oDriveComponent, eOrgan oDriveSource, double oQuotaOfAffect) {		
		// search in drives
		
		for(clsDriveMesh oDrives : moDrives){
			// check DriveMesh
			// oDrives.getDriveComponent() = eDriveComponent.LIBIDINOUS or eDriveComponent.AGGRESSIVE
			// oDrives.getActualDriveSourceAsENUM() = for example eOrgan.STOMACH
			if (oDrives.getDriveComponent().equals(oDriveComponent) &&
				oDrives.getActualDriveSourceAsENUM().equals(oDriveSource) &&
				oDrives.getQuotaOfAffect() >= oQuotaOfAffect){
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 10.08.2012, 17:06:50
	 * 
	 * searches emotions for example for eEmotionType.ANGER
	 * 
	 */
	private boolean searchInEmotions (eEmotionType oEmotionType) {	
		
	   	for(clsEmotion oOneEmotion : moEmotions_Input) {
	   		if(oOneEmotion.getMoContent() == oEmotionType) {
	   			return true;
	   		}
	   	}
	   	
	   	return false;
	}
	
	// to Get the last Emotion
	/*private ArrayList<clsEmotion>  GetLastEmotionInput(ArrayList<clsEmotion> moEmotions_Input){
		clsEmotion oLastEmotion = null;
		ArrayList<clsEmotion> LastList_clsEmotion = new ArrayList<clsEmotion>();
		
		//for (clsEmotion oOneEmotion:  moEmotions_Input){
		for (int i =0; i<moEmotions_Input.size();i++){
			
			oLastEmotion = moEmotions_Input.get(moEmotions_Input.size()-1);
			LastList_clsEmotion.add(oLastEmotion);
		}
		
		return LastList_clsEmotion;
	}*/

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_13(moForbiddenDrives, moDrives,moForbiddenEmotions, moEmotions_Input ); 
		send_I5_11(moForbiddenPerceptions, moPerceptionalMesh_OUT, moForbiddenEmotions, moEmotions_Input); 
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
	public void send_I5_13(ArrayList<clsPair<eDriveComponent, eOrgan>> poForbiddenDrives, ArrayList<clsDriveMesh> poData,ArrayList<eEmotionType> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions) {
		((I5_13_receive)moModuleList.get(6)).receive_I5_13(poForbiddenDrives, poData, poEmotions);
		
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
	public void send_I5_11(ArrayList<clsPair<eContentType, String>> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh, ArrayList<eEmotionType> poForbiddenEmotions, ArrayList<clsEmotion> poEmotions) {
		((I5_11_receive)moModuleList.get(19)).receive_I5_11(poForbiddenPerceptions, poPerceptionalMesh, poForbiddenEmotions, poEmotions);
		
		putInterfaceData(I5_13_send.class, poForbiddenPerceptions, poPerceptionalMesh);
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 6, 2012 11:11:19 AM
	 * 
	 * @see pa._v38.interfaces.itfGraphInterface#getGraphInterfaces()
	 */
	@Override
	public ArrayList<eInterfaces> getGraphInterfaces() {
		return this.getInterfaces();
	}
}
