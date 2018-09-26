/**
 * F7_SuperEgoReactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author gelbard
 * 02.05.2011, 15:47:53
 */
package primaryprocess.modules;

import inspector.interfaces.itfGraphInterface;
import inspector.interfaces.itfInspectorForRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_10_receive;
import modules.interfaces.I5_11_receive;
import modules.interfaces.I5_11_send;
import modules.interfaces.I5_12_receive;
import modules.interfaces.I5_13_receive;
import modules.interfaces.I5_13_send;
import modules.interfaces.eInterfaces;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictDrive;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictEmotion;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictPerception;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoRulesCheck;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

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
	implements I5_12_receive, I5_10_receive, I5_11_send, I5_13_send, itfGraphInterface, itfInspectorForRules{
 
	public static final String P_MODULENUMBER = "07";
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    
    //file name of the file with super-ego rules
    private final String oFileName;
    
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
	public static final String P_SUPER_EGO_STRENGTH = "SUPER_EGO_STRENGTH";
	public static final String P_SUPER_EGO_RULES_FILE = "SUPER_EGO_RULES_FILE";
	public static final String P_PSYCHIC_ENERGY_THESHOLD = "PSYCHIC_ENERGY_THESHOLD";
	public static final String P_PSYCHIC_ENERGY_PRIORITY = "PSYCHIC_ENERGY_PRIORITY";
	
	private double threshold_psychicEnergy;
	private int msPriorityPsychicEnergy;
	private double moSuperEgoStrength; // personality parameter to adjust the strength of Super-Ego
	public clsSuperEgoRulesCheck moSuperEgoRulesCheckInstance; 
	
	@SuppressWarnings("unused")
	// Das muss erst noch implementiert werden. Ist jetzt einmal nur vorbereitet.
	private static final int consumed_psychicEnergyPerInteration = 1;
	
	//AW 20110522: New inputs
	private ArrayList<clsDriveMesh> moDrives;	
	private clsThingPresentationMesh moPerceptionalMesh_IN;	
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	private ArrayList<clsEmotion> moEmotions_Input;
	private ArrayList<String> moSuperEgoOutputRules = new ArrayList <String> ();
	
	private ArrayList<clsSuperEgoRulesCheck> moRules;     //die Regeln die das Über-Ich verbietet Ivy
	private ArrayList<clsSuperEgoConflictDrive> moForbiddenDrives;
	private ArrayList<clsSuperEgoConflictPerception> moForbiddenPerceptions;
	private ArrayList<clsSuperEgoConflictEmotion> moForbiddenEmotions; 
	
	private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	//private ArrayList<String> Test= new ArrayList<String>() ;
//	Ivy begin ~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~ //
	//private ArrayList<String> moSuperEgoOutputRules = new ArrayList <String> (); //für die Ausgabe	

	private clsWordPresentationMesh moWordingToContext;
    
	
	/**
	 * DOCUMENT (zeilinger) - insert description Ivy: liest die SuperEgoRules Datei aus
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:49:49
	 *
	 * @param poPrefix
	 * @param poProp
	 * 
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poPsychicEnergyStorage 
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F07_SuperEgoReactive(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT3_PsychicIntensityStorage poPsychicEnergyStorage,
			clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);

        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F07", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F07", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
		
		moForbiddenDrives = new ArrayList<clsSuperEgoConflictDrive>();
		moForbiddenPerceptions = new ArrayList<clsSuperEgoConflictPerception>();
		moForbiddenEmotions = new ArrayList<clsSuperEgoConflictEmotion>();
		
		applyProperties(poPrefix, poProp); 
		
		threshold_psychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_THESHOLD).getParameterDouble();
		msPriorityPsychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_PRIORITY).getParameterInt();
		moSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SUPER_EGO_STRENGTH).getParameterDouble();
	
        oFileName = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_RULES_FILE).getParameter();

        moSuperEgoRulesCheckInstance = new clsSuperEgoRulesCheck();
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
		text += toText.listToTEXT("--------------------------------moSuperEgoRules Activated-----------------------------", moSuperEgoOutputRules);
//		text += toText.listToTEXT("--------------------------------moSuperEgoEmotionsRules---------------------------", moSuperEgoEmotionsRules);
//		text += toText.listToTEXT("--------------------------------moSuperEgoPerceptionsRules------------------------", moSuperEgoPerceptionsRules);
//		text += toText.listToTEXT("--------------------------------Test----------------------------------------", Test);
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
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {
	    
	    moWordingToContext =  moWordingToContext2;
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
	public void receive_I5_12(ArrayList<clsDriveMesh> poDrives,  ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
	    
	    moWordingToContext = moWordingToContext2;
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
		
		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
        double rConsumedPsychicIntensity = 0;
        
        log.debug("neutralized intensity F07: " + Double.toString(rReceivedPsychicEnergy));
        
        if (rReceivedPsychicEnergy > threshold_psychicEnergy) {
           
            moSuperEgoRulesCheckInstance.setCheckingSuperEgoRuleParameters(moPerceptionalMesh_OUT, moEmotions_Input, moDrives);
            
            try {  
                rConsumedPsychicIntensity = moSuperEgoRulesCheckInstance.checkInternalizedRules(rReceivedPsychicEnergy, moSuperEgoStrength, oFileName);
                moSuperEgoOutputRules = moSuperEgoRulesCheckInstance.getSuperEgoOutputRules();
                moForbiddenDrives = moSuperEgoRulesCheckInstance.getForbiddenDrives();
                moForbiddenEmotions = moSuperEgoRulesCheckInstance.getForbiddenEmotions();
                moForbiddenPerceptions = moSuperEgoRulesCheckInstance.getForbiddenPerception();
            }   
            catch (Exception e) {
                // TODO (zhukova) - Auto-generated catch block
                e.printStackTrace();
            }
           // setValues(); -> checkRules
            //rConsumedPsychicIntensity = checkInternalizedRules(rReceivedPsychicEnergy);   // check perceptions and drives, and apply internalized rules
        }
        
        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, mrInitialRequestIntensity, rConsumedPsychicIntensity);		
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
//		try {
//			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		
//		//clear the list of forbidden drives, every turn
//		moForbiddenDrives.clear();
//		
//		if(moSuperEgoStrength >= 0.5) //if super ego is strong enough - 0.5 is an arbitrary value
//		{
//			//simple_rule to deal with eating in BODOs vicinity
//
//			ArrayList<String> oEntities = new ArrayList<String> ();
//
//			oEntities.add("BODO");
//
//			simple_rule(eDriveComponent.LIBIDINOUS,
//				eOrgan.STOMACH,
//				0.0, //FIXME Kollmann: The super-rule for divide should only fire above a certain QoA
//				0.1, // Kollmann: When the stomach is full -> LIB/STOM has a QoA ~ 0.04 
//				oEntities);
//		}
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
//	
//	/**
//	 * DOCUMENT (Kollmann) - alternative call to simple_rule without any perceivable entities
//	 *
//	 * @since 17.09.2013 14:16:37
//	 *
//	 * @param poComponent the drive component (poComponent.LIBIDINOUSE or poComponent.AGGRESIVE)
//	 * @param poOrgan the drive source
//	 * @param pnMinQuota the minimum quota of affect the drive needs to activate the rule
//	 * @param pnMaxQuota the maximum (>=) QoA the drive can have to activate the rule
//	 * @return true if the rule was triggered, false otherwise
//	 */
//	protected boolean simple_rule(eDriveComponent poComponent, eOrgan poOrgan, double pnMinQuota, double pnMaxQuota)
//	{
//	    return simple_rule(poComponent, poOrgan, pnMinQuota, pnMaxQuota, new ArrayList<String>());
//	}
//	
//	/**
//	 * DOCUMENT (Kollmann) - Checks if the rule, defined by the parameters, is present and, if it is,
//	 *                       adds the corresponding eDriveComponent/eOrgan combination to the list of
//	 *                       forbidden drives
//	 *
//	 * @since 17.09.2013 14:18:15
//	 *
//	 * @param poComponent the drive component (poComponent.LIBIDINOUSE or poComponent.AGGRESIVE) 
//	 * @param poOrgan the drive source
//	 * @param pnMinQuota the minimum QoA the drive needs to activate the rule
//	 * @param pnMaxQuota the maximum QoA the drive can have to activate the rule
//	 * @param poPerceivedEntities - a list of perceivable entities - the rule will only trigger if all
//	 *                              these entities are currently perceived
//	 * @return true if the rule was triggered, false otherwise
//	 */
//	protected boolean simple_rule(eDriveComponent poComponent, eOrgan poOrgan, double pnMinQuota, double pnMaxQuota,
//			ArrayList<String> poPerceivedEntities)
//	{
//		boolean rule_triggered = false;
//		clsPair<eDriveComponent, eOrgan> oDrive;
//		
//		clsQuadruppel<String, eDriveComponent, eOrgan, Double> oForbiddenDrive = null;
//		clsTriple<String, clsQuadruppel<String, eDriveComponent, eOrgan, Double>, ArrayList<String>> oDriveRules=null;
//		ArrayList<String> oContentTypeDrives= new ArrayList<String> ();
//				
//		//check for a fitting drive
//		for(clsDriveMesh oDrives : moDrives){
//			if (oDrives.getDriveComponent().equals(poComponent) &&
//				oDrives.getActualDriveSourceAsENUM().equals(poOrgan) &&
//				oDrives.getQuotaOfAffect() >= pnMinQuota &&
//				oDrives.getQuotaOfAffect() <= pnMaxQuota)
//			{		
//				//logging for inspectors
//				oForbiddenDrive = new clsQuadruppel<String,eDriveComponent, eOrgan,Double>(
//						pnMinQuota + " =< " + poComponent.toString() + "/" + poOrgan.toString() + " >= " + pnMaxQuota,
//						poComponent,
//						poOrgan,
//						pnMinQuota);
//				
//				//check if entities are perceived
//				for(String oEntity : poPerceivedEntities)
//				{
//					if(searchInTPM(eContentType.ENTITY, oEntity))
//					{
//						oContentTypeDrives.add("ENTITY=" + oEntity);
//					}
//				}
//	
//				if(oContentTypeDrives.size() > poPerceivedEntities.size()) //should never happen
//				{
//					System.err.println("PROBLEM: found more perceivide entities than specified entities");
//				}else if(oContentTypeDrives.size() == poPerceivedEntities.size())
//				{
//				  //forbid drive, if not yet forbidden
//					oDrive = new clsPair<eDriveComponent, eOrgan>(poComponent, poOrgan);
//					
//					if (!moForbiddenDrives.contains(oDrive)) // no duplicate entries
//					{
//						moForbiddenDrives.add(oDrive);
//						rule_triggered = true;
//					}
//					
//					//log forbidding of drive
//					oDriveRules= new clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>("SuperEgoStrength >= 0.5", oForbiddenDrive, oContentTypeDrives);
//					if(!moSuperEgoDrivesRules.contains(oDriveRules))
//					{
//						// add rule info to inspector 
//						moSuperEgoDrivesRules.add(oDriveRules);
//					}
//				}
//			}
//		}
//		
//		
//		return rule_triggered;
//	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:12
	 * 
	 * Super-Ego checks perception and drives
	 * 
	 */
	

	

    /* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_13(moForbiddenDrives, moDrives, moForbiddenEmotions, moEmotions_Input ); 
		send_I5_11(moForbiddenPerceptions, moPerceptionalMesh_OUT, moForbiddenEmotions, moEmotions_Input, moWordingToContext); 
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
	public void send_I5_13(ArrayList<clsSuperEgoConflictDrive> poForbiddenDrives, ArrayList<clsDriveMesh> poData,ArrayList<clsSuperEgoConflictEmotion> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions) {
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
	public void send_I5_11(ArrayList<clsSuperEgoConflictPerception> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsSuperEgoConflictEmotion> poForbiddenEmotions, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
		((I5_11_receive)moModuleList.get(19)).receive_I5_11(poForbiddenPerceptions, poPerceptionalMesh, poForbiddenEmotions, poEmotions, moWordingToContext2);
		
		putInterfaceData(I5_13_send.class, poForbiddenPerceptions, poPerceptionalMesh, moWordingToContext2);
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

    /* (non-Javadoc)
     *
     * @since 01.11.2013 16:52:14
     * 
     * @see pa._v38.interfaces.itfInspectorForRules#getDriverules()
     */
    @Override
    public ArrayList<clsSuperEgoRulesCheck> getDriverules() {
        if(!moRules.isEmpty())
            return moRules;
         return moSuperEgoRulesCheckInstance.readSuperEgoRules(moSuperEgoStrength, oFileName);
    }
}
