/**
 * E19_DefenseMechanismsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 */
package primaryprocess.modules;

import inspector.interfaces.Singleton;
import inspector.interfaces.itfInspectorBarChartF19;
import inspector.interfaces.itfInspectorCombinedTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedMap;

import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictEmotion;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictPerception;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.storage.DT2_BlockedContentStorage;
import modules.interfaces.I5_11_receive;
import modules.interfaces.I5_14_receive;
import modules.interfaces.I5_15_receive;
import modules.interfaces.I5_15_send;
import modules.interfaces.I5_16_receive;
import modules.interfaces.I5_16_send;
import modules.interfaces.I5_22_receive;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAffect;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
//import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
//import pa._v38.tools.clsMeshTools;
import testfunctions.clsTester;

/**
 * F19 defends forbidden perceptions and forbidden emotions.
 * Super-Ego (F7, F55) sends a list with forbidden perceptions and forbidden emotions to F19.
 * F19 decides whether to defend the forbidden perceptions or not.
 * If F19 decided to defend the forbidden perceptions F19 chooses the defense mechanism (denial, projection, depreciation, ...).
 * 
 * Implemented defense mechanisms for perception:
 * - denial (Verdrängung)
 * - idealization (Idealisierung): only positive associations are perceived with an object
 * - depreciation (Entwertung): only negative associations are perceived with an object
 * - not yet implemented: projektive Identifizierung: Adam bringt es Lust, wenn er Bodo beim Essen zuschaut (mit KD besprochen)
 * - not yet implemeted (ist auch nicht im Funktionsmodell vorgesehen, wäre aber leicht zu implementieren): isolation (Isolierung) Isolierung trennt den Affektbetrag vom Wahrnehmungsinhalt
 * 
 * Implemented defense mechanisms for emotions
 * - Reversal of affect: changes for the forbidden emotion into the emotion fear
 * - Reaction formation: changes emotions according to the implemented hash map
 * - Not yet implemented: Turning against the self: anger -> guilt (mit KD besprochen)
 * 
 * @author gelbard AND Lotfi
 * 07.05.2012, 14:35:08
 * 10.02.2013, 17:47:03
 * 
 *
 * **/
public class F19_DefenseMechanismsForPerception extends clsModuleBaseKB implements 
			I5_14_receive, I5_11_receive, I5_15_send, I5_16_send, I5_22_receive, itfInspectorCombinedTimeChart, itfInspectorBarChartF19{
	public static final String P_MODULENUMBER = "19";
	
	public static final String P_INTENSITY_REDUCTION_RATE_SELF_PRESERV = "INTENSITY_REDUCTION_RATE_SELF_PRESERV";
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	//AW 20110522: New inputs
	//private clsPrimaryDataStructureContainer moEnvironmentalPerception_Input;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Input;
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	
	//delacruz 27.4.2019: add type for stateToText print
	private ArrayList<clsPair<Double, clsDataStructurePA>>  oResultSearchMesh;
	
	//private clsPrimaryDataStructureContainer moEnvironmentalPerception_Output;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Output;
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	
	private double moEgoStrength; // personality parameter to adjust the strength of the Ego
	// Perceptions and emotions not "liked" by Super-Ego
	private ArrayList<clsSuperEgoConflictPerception> moForbiddenPerceptions_Input;
	private ArrayList<clsSuperEgoConflictEmotion>    moForbiddenEmotions_Input;
	

	private ArrayList<base.datatypes.clsThingPresentation> moDeniedThingPresentations;
	clsAffect moAffect; // anxiety which is generated while repressing content, also CONFLICT is sent via this affect
	private ArrayList<clsPrimaryDataStructure> moQuotasOfAffect_Output = new ArrayList<clsPrimaryDataStructure>(); // anxiety which is generated while repressing content
    private ArrayList<clsDriveMesh> moInput;
	
	// defense mechanisms must be activated by a psychoanalytic conflict
	// defense_active symbolizes an unpleasure value which is generated by the psychonanlytic conflict
	boolean defense_active = false;
	
	private DT2_BlockedContentStorage moBlockedContentStorage; // only needed here in F19 to initialize the blocked content storage

	private eContentType moBlockedContentType = eContentType.RIREPRESSED;
	
	private ArrayList<clsEmotion> moEmotions_Input; 
	private ArrayList<clsEmotion> moEmotions_Output;
	
	private clsWordPresentationMesh moWordingToContext;
    
	ArrayList<clsThingPresentationMesh> oListWithNegativeObjects = new ArrayList<clsThingPresentationMesh>();
	
	ArrayList<clsThingPresentationMesh> oListWithPositiveObjects = new ArrayList<clsThingPresentationMesh>();
	ArrayList<clsThingPresentationMesh> oListWithPositiveOrNegativeObjects = new ArrayList<clsThingPresentationMesh>();
	ArrayList<clsAssociation> oInternalAssociationsPositiveOrNegativeObject = new ArrayList<clsAssociation>();
	ArrayList<String> Test= new ArrayList<String>();
	ArrayList<String> Test3= new ArrayList<String>();
	
	// For TimeChart and ChartBar
	HashMap<String, Double>  moTimeChartData = new HashMap<String, Double>();
	double denial                         = 0.0;
	double idealization                   = 0.0;
	double depreciation                   = 0.0;
	double reversalOfAffect               = 0.0;
	double reactionFormation              = 0.0;
	double PassForbidenEmotions           = 0.0;
	double PassForbidenPerceptions        = 0.0;
	double ChartBarDenial                 = 0.0;
	double ChartBarIdealization           = 0.0;
	double ChartBarDepreciation           = 0.0;
	double ChartBarReversalOfAffect       = 0.0;
	double ChartBarReactionFormation      = 0.0;
	double ChartBarPassForbidenEmotion    = 0.0;
	double ChartBarPassForbidenPerception = 0.0;
	
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
			itfModuleMemoryAccess poMemory,
			clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory, pnUid);

		//Set current Singleton instance
        Singleton.setCurrentAgent(getAgentIndex());
        
		applyProperties(poPrefix, poProp);
		
	    // for use case 1: the Ego strength is equal to the neutralization rate
        // (normalerweise wird sie über receive_I5_22 empfangen - siehe vorletzte Zeile in diesem File)
        //moEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F56", P_INTENSITY_REDUCTION_RATE_SELF_PRESERV).getParameterDouble();
 		
 		//Get Blocked content storage
		moBlockedContentStorage = poBlockedContentStorage;
		
		//Fill the blocked content storage with initial data from protege
		moBlockedContentStorage.addAll(initialFillRepressedContent());
		//27.4.2019 delacruz: add match to return value
		oResultSearchMesh = initialFillRepressedContentWithPIMatch();
		moTimeChartData =  new HashMap<String, Double>(); 		
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
		// To test the Idealization Or Depreciation
		text += toText.valueToTEXT("/*********************Before IdealizationOrDepreciation*********************/", Test3);
		text += toText.valueToTEXT("/*********************After IdealizationOrDepreciation*********************/", Test);
			 				
		text += toText.listToTEXT("moForbiddenPerceptions_Input", moForbiddenPerceptions_Input);
		text += toText.listToTEXT("moForbiddenEmotions_Input", moForbiddenEmotions_Input);
		text += toText.listToTEXT("moDeniedThingPresentations", moDeniedThingPresentations);
		text += toText.valueToTEXT("moAffect", moAffect);
		text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
		text += toText.listToTEXT("moEmotions_Output", moEmotions_Output);

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
	@Override
	public void receive_I5_11(ArrayList<clsSuperEgoConflictPerception> poForbiddenPerceptions,
			                  clsThingPresentationMesh poPerceptionalMesh,
			                  ArrayList<clsSuperEgoConflictEmotion> poForbiddenEmotions,
			                  ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
	    moWordingToContext = moWordingToContext2;
	    
		// clone perceptions
		try {
			//moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		
		moEmotions_Input             = clone(poEmotions);
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
	 * @author gelbard
	 * 27.08.2012, 17:54:00
	 * 
	 * clones an ArrayList<clsEmotions>
	 */
	private ArrayList<clsEmotion> clone(ArrayList<clsEmotion> oEmotions) {
		// deep clone: oEmotions --> oClonedEmotions
		ArrayList<clsEmotion> oClonedEmotions = new ArrayList<clsEmotion>();

		for (clsEmotion oOneEmotion : oEmotions) {
			try {
				oClonedEmotions.add( (clsEmotion) oOneEmotion.clone(new HashMap<clsDataStructurePA, clsDataStructurePA>()));
			} catch (CloneNotSupportedException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return oClonedEmotions;
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
		
		try {
			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}	
		
		moEmotions_Output = clone(moEmotions_Input);

		
		detect_conflict_and_activate_defense_machanisms();
		moTimeInputChartData();
		//=== Perform system tests ===//
		if (clsTester.getTester().isActivated()) {
			try {
				clsTester.getTester().exeTestAssociationAssignment(moPerceptionalMesh_OUT);
			} catch (Exception e) {
				log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
			}
		}
		
		
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
		
		
		 moTimeInputChartData();
		
		 // empty the list from last step otherwise list only grows
		 moQuotasOfAffect_Output.clear();
		 if (moForbiddenPerceptions_Input.isEmpty()){
			 ResetTimeChartForbidenPerceptionData(); 
		 }
		 if (moForbiddenEmotions_Input.isEmpty()){
			 ResetTimeChartForbidenEmotionData(); 
		 }
		
		 // check for a psychoanalytic conflict
		 // defense mechanisms are delayed by one cycle to produce a situation where conflict exists and no action plans are executed
		 if (!moForbiddenPerceptions_Input.isEmpty() && !defense_active)
		 {
			 // conflicting events exist -> activate conflict -> activate defense mechanisms but do not defend yet. (defense will work in the next cycle)
			 defense_active = true;
			 
			 // send quota of affect 999.9 via I5.17 to produce a "CONFLICT"-signal in F20
			  moAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<eContentType, Object>(eContentType.AFFECT, 999.9)); 
			  moQuotasOfAffect_Output.add(moAffect);
			 
			 return;
		 }
		 else if (moForbiddenPerceptions_Input.isEmpty() &&
				  moForbiddenEmotions_Input.isEmpty())
		 {
			 // no conflicting events -> deactivate defense mechanisms
			 defense_active = false;
			 return;
		 }
		 // Defense mechanisms start to work.
		 
		 /* (non-Javadoc)
		 *
		 * @author Lotfi
		 * 15.03.2013, 13:30:00
		 * 
		 * Ego decides now which defense mechanisms to apply (depending on the Intensity of Emotion ANXIETY)
		 * In F19 ForbidenEmotions and ForbidenPerceptions should be defended 
		 * 		 
		 */ 
//		 selectAndActivateDefenseMechanisms_UC1();

		 selectAndActivateDefenseMechanisms_EC2();

		
		  			 
		 // For time chart
		 moTimeInputChartData();
	}
	
	
	private void selectAndActivateDefenseMechanisms_Lotfi() {
	       if (!moForbiddenPerceptions_Input.isEmpty()){ 
	            
               
	            if(GetEmotionIntensity(eEmotionType.ANXIETY) <= 0.4){
	                 
	                 defenseMechanism_Idealization(moForbiddenPerceptions_Input);
	                 
	                 
	                                 
	            }else if ((GetEmotionIntensity(eEmotionType.ANXIETY) > 0.4) && (GetEmotionIntensity(eEmotionType.ANXIETY) <= 0.9)){
	                    
	                defenseMechanism_Depreciation(moForbiddenPerceptions_Input);
	                
	                
	                
	            }else if(GetEmotionIntensity(eEmotionType.ANXIETY) > 1.5) {
	                
	                defenseMechanism_Denial(moForbiddenPerceptions_Input);
	                
	                

	            }else{
	                
	                // For TimeChart
	                
	                ResetTimeChartDefenseForbidenPerceptionData();
	                
	                
	            }
	            
	        // If no ForbidenPerception Y-Axis of TimeCharts have to be inactive 
	        
	        }
	        
	        
	        
	        
	        // Defense for emotions
	        if(!moForbiddenEmotions_Input.isEmpty()){

	            if((GetEmotionIntensity(eEmotionType.ANXIETY) > 0.1) && (GetEmotionIntensity(eEmotionType.ANXIETY) <= 0.9)){
	                
	                defenseMechanism_ReversalOfAffect (moForbiddenEmotions_Input, moEmotions_Output.get(0));
	                
	                             
	            }else{
	                
	                ResetTimeChartDefenseForbidenEmotionData();
	                                
	            }
	        }
	            
	        // If no ForbidenEmotion Y-Axis of TimeCharts are inactive
	        
	                     
	}
	
	
	/* (non-Javadoc)
    *
    * @author gelbard
    * 12.02.2014, 10:56:00
    * 
    * Version for use case 1 (is working correctly)
    * 
    * This function chooses a defense mechanism depending on the conflict tension and the ego strength
    * and activates the defense mechanism.
    * - Low ego strength means a primitive defense mechanism is selected
    * - High ego strength means an elevated defense mechanism is selected
    *
    */
    private void selectAndActivateDefenseMechanisms_UC1() {
        
           // Defense for perceptions
           if (!moForbiddenPerceptions_Input.isEmpty()){ 
                if (moEgoStrength <= 0.15) {
                    defenseMechanism_Denial(moForbiddenPerceptions_Input);                      
                }
                else
                {
                    // For TimeChart    
                    ResetTimeChartDefenseForbidenPerceptionData();
                }       
            }
            
            // Defense for emotions
            if(!moForbiddenEmotions_Input.isEmpty()){
                if (moEgoStrength <= 0.15) {
                    defenseMechanism_ReactionFormation (moForbiddenEmotions_Input, moEmotions_Output.get(0).generateExtendedEmotions());  
                }
                else
                {   
                    ResetTimeChartDefenseForbidenEmotionData();                 
                }
            }
    }
	
    private void selectAndActivateDefenseMechanisms_EC2() {
        // Defense for perceptions
        if (!moForbiddenPerceptions_Input.isEmpty()){ 
            if (moEgoStrength <= 0.15) {
                defenseMechanism_Denial(moForbiddenPerceptions_Input);                      
            }
            else
            {
                // For TimeChart    
                ResetTimeChartDefenseForbidenPerceptionData();
            }       
        }
        
        // Defense for emotions
        if(!moForbiddenEmotions_Input.isEmpty()){
            if(moEgoStrength < 0.01) {
                ResetTimeChartDefenseForbidenEmotionData();
            } else {
                defenseMechanism_ReversalOfAffect(moForbiddenEmotions_Input, moEmotions_Output.get(0));
                
                /* For now we only use the simple defense mechanism - otherwise we could use something like this:
                 * 
                //the simplest defense mechanism
                if (moEgoStrength <= 0.25) {
                    defenseMechanism_ReversalOfAffect(moForbiddenEmotions_Input, moEmotions_Output.get(0));
                }
                //a mid-level defense mechanism
                if (moEgoStrength >= 0.15 && moEgoStrength <= 0.35) {
                    //not yet
                }
                
                //a high level defense mchanism
                if (moEgoStrength >= 0.3) {
                    //not yet
                }*/
            }
        }
    }
	
	/* (non-Javadoc)
    *
    * @author gelbard
    * 12.02.2014, 10:56:00
    * 
    * This function chooses a defense mechanism depending on the conflict tension and the ego strength
    * and activates the defense mechanism.
    * - Low ego strength means a primitive defense mechanism is selected
    * - High ego strength means an elevated defense mechanism is selected
    *
    */
    private void selectAndActivateDefenseMechanisms() {
        
        double conflictTension = calculateConflictTension(moForbiddenPerceptions_Input);
        
        // conflictTension <= 0.01
        if (conflictTension <= 0.01) {
            // For TimeChart
            ResetTimeChartDefenseForbidenPerceptionData();
        }
        
        // conflictTension <= 0.5
        else if (conflictTension <= 0.5) {
            if (moEgoStrength <= 0.3) {
                         defenseMechanism_Idealization(moForbiddenPerceptions_Input);
            }
            else if (moEgoStrength <= 0.7) {
                         defenseMechanism_Denial(moForbiddenPerceptions_Input);
            }
            else {
                         // ACHTUNG !!!: Projektion ist nur implementiert fuer drives.
                         // Ist aber ganz einfach fuer perceptions:
                         // Einfach das Thing presentation and das andere Objekt als Assoziation dranhängen und beim origianlobjekt löschen.
                         // Genauso funktioniert Projektion fuer drives und fuer emotions  
                         //defenseMechanism_Projection(moForbiddenPerceptions_Input);
            }
        }
        
        // conflictTension > 0.5        
        else {
            if (moEgoStrength <= 0.3) {
                         defenseMechanism_Depreciation(moForbiddenPerceptions_Input);
            }
            else if (moEgoStrength <= 0.7) {
                         // ACHTUNG !!!: Displacement ist nur fuer Triebe implementiert.
                         // Wuerde aber fuer Wahrnehmung auch Sinn machen.
                         // -> Abklaeren, ob Displacement fuer Wahrnehmung im ARS vorgesehen ist.
                         //defenseMechanism_Displacement(moForbiddenPerceptions_Input);
            }
            else {
                         // ACHTUNG !!!: Projektion ist nur implementiert fuer drives.
                         // Ist aber ganz einfach fuer perceptions:
                         // Einfach das Thing presentation and das andere Objekt als Assoziation dranhängen und beim origianlobjekt löschen.
                         // Genauso funktioniert Projektion fuer drives und fuer emotions  
                         //defenseMechanism_Projection(moForbiddenPerceptions_Input);
            }
        }        
            
            
            
        
        
            
        // Defense for emotions
        if(!moForbiddenEmotions_Input.isEmpty()){
            if (moEgoStrength <= 0.15) {
                defenseMechanism_ReactionFormation (moForbiddenEmotions_Input, moEmotions_Output.get(0).generateExtendedEmotions());  
            }
            else
            {   
                ResetTimeChartDefenseForbidenEmotionData();                 
            }
        }
    }
    
    
    /* (non-Javadoc)
    *
    * @author gelbard
    * 19.03.2014, 13:12:00
    * 
    * This function calculates the average conflict tension of the perceptions in the list of forbidden perceptions.
    *
    */
    private double calculateConflictTension(ArrayList<clsSuperEgoConflictPerception> forbiddenPerceptions_Input) {
        double conflictTension = 0.0;
        int sizeOfForbiddenPerceptionsList = forbiddenPerceptions_Input.size();
        
        for(int i=0; i < sizeOfForbiddenPerceptionsList; i++) {
            conflictTension += forbiddenPerceptions_Input.get(i).getConflictTension();
        }
        
        return conflictTension / sizeOfForbiddenPerceptionsList;
    }
	
	
	// get the intensity of the emotions MOURNING, ANXIETY and ANGER 
	private double GetEmotionIntensity(eEmotionType moEmotionType){
			double oEmotionIntensity =0.0;
			for(clsEmotion oOneEmotion : moEmotions_Output.get(0).generateExtendedEmotions()) {
				
					if(searchInEmotions (eEmotionType.MOURNING)){
						if ((moEmotionType == eEmotionType.MOURNING) && (oOneEmotion.getContent() == eEmotionType.MOURNING)){
							oEmotionIntensity = oOneEmotion.getEmotionIntensity();
						}
				    }
					if(searchInEmotions (eEmotionType.ANXIETY)){
						if ((moEmotionType == eEmotionType.ANXIETY)&&(oOneEmotion.getContent() == eEmotionType.ANXIETY)){
							oEmotionIntensity = oOneEmotion.getEmotionIntensity();
						}
					}
				     
				    if(searchInEmotions(eEmotionType.ANGER)){
				    	if ((moEmotionType == eEmotionType.ANGER)&&(oOneEmotion.getContent() == eEmotionType.ANGER)){
				    		 oEmotionIntensity = oOneEmotion.getEmotionIntensity();
				    	}
				    }
		
				
			}
			return oEmotionIntensity;
	}
	// Search of the emotion types if they exist
	private boolean searchInEmotions (eEmotionType oEmotionType) {	
			
		   	for(clsEmotion oOneEmotion : moEmotions_Output.get(0).generateExtendedEmotions()) {
		   		if(oOneEmotion.getContent() == oEmotionType) {
		   			return true;
		   		}
		   	}
		   	
		   	return false;
	}

	// HashMap for TimeChart
	private HashMap<String, Double>  moTimeInputChartData(){
		moTimeChartData.put("TimePassForbidenEmotion", PassForbidenEmotions);
		moTimeChartData.put("TimePassForbidenPerception", PassForbidenPerceptions);
		moTimeChartData.put("TimeDenial", denial);
		moTimeChartData.put("TimeIdealization", idealization);
		moTimeChartData.put("TimeDepreciation", depreciation );
		moTimeChartData.put("TimeReversalOfAffect", reversalOfAffect);
		moTimeChartData.put("PassForbidenEmotions", ChartBarPassForbidenEmotion);
		moTimeChartData.put("PassForbidenPerceptions", ChartBarPassForbidenPerception);
		moTimeChartData.put("Denial", ChartBarDenial);
		moTimeChartData.put("Idealization", ChartBarIdealization);
		moTimeChartData.put("Depreciation", ChartBarDepreciation);
		moTimeChartData.put("ReversalOfAffect", ChartBarReversalOfAffect);
		
		
		
		
		
		return moTimeChartData;
		
	}
	
		
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like a ARSIN
	 * 
	 */
	private void defenseMechanism_Denial (ArrayList<clsSuperEgoConflictPerception> oForbiddenPerceptions) {
		ChartBarDenial ++;
		denial = 1.0;
		
    	// If nothing to deny return immediately (otherwise NullPointerException)
    	if (oForbiddenPerceptions == null) return;
		
		// check list of forbidden perceptions
		for(clsSuperEgoConflictPerception oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			eContentType oContentType = oOneForbiddenPerception.getContentType();
			String oContent     = oOneForbiddenPerception.getContent();
			
			// search in perceptions
			ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_OUT).getInternalAssociatedContent();
			for(clsAssociation oAssociation : oInternalAssociations){
				if (oAssociation.getAssociationElementB() instanceof clsThingPresentationMesh)
					if( ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContentType().equals(oContentType) &&
						((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContent().equals(oContent) ) {
						
						// delete forbidden association from PI (Perception Image)
						oInternalAssociations.remove(oAssociation);
						break; // must leave here otherwise exception, if element of iterator is deleted!
					}
			}
			
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author lotfi
	 * 10.03.2013, 18:26:49
	 * 
	 * Create positive and negative parameters of the forbidden perception
	 * The call of defense mechanism Idealization delete the negative parameters of the forbidden perception
	 * AND 
	 * The call of defense mechanism Depreciation delete the positive parameters of the forbidden perception  
	 * 
	 */
	private ArrayList<clsThingPresentationMesh> CreateListWithPositiveObjects (ArrayList<clsSuperEgoConflictPerception> oForbiddenPerceptions) {
		
		ArrayList<clsAssociation> oListWithPositiveAssociations = new ArrayList<clsAssociation>();

		for(clsSuperEgoConflictPerception oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			
			eContentType oContentType = oOneForbiddenPerception.getContentType();
			String oContent           = oOneForbiddenPerception.getContent();
			
		
						
					clsThingPresentationMesh oObjectDataStructure= 	(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure
																	(eDataType.TPM, new clsTriple<eContentType, Object, Object>
																	(oContentType,new ArrayList<clsThingPresentation>(), oContent)); 
					
					
					clsThingPresentation oColor = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "GREEN"));
					clsThingPresentation oForm = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "FLAT"));
					clsThingPresentation oRace = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "DESERT"));
					clsThingPresentation oTaste = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "SWEET"));
					
					
					clsAssociation oColorAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																		  (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oColor);
	
					oListWithPositiveAssociations.add(oColorAss);
									
					
					clsAssociation oFormAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																		 (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oForm);
					oListWithPositiveAssociations.add(oFormAss);
					
					clsAssociation oRaceAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																		 (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oRace);
					oListWithPositiveAssociations.add(oRaceAss);
					clsAssociation oTasteAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																		  (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oTaste);
					oListWithPositiveAssociations.add(oTasteAss);
					
					oObjectDataStructure= new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1,eDataType.TPM, eContentType.TPM), oListWithPositiveAssociations, oContent); 
					
					
					
					oListWithPositiveObjects.add(oObjectDataStructure);
					//Double pi = oObjectDataStructure.getNumbInternalAssociations();
					//Double pi1 = oObjectDataStructure.getNumbExternalAssociations();
					//Test3.add(String.valueOf(pi));
					//Test3.add(String.valueOf(pi1));
					// Just to test the Root
		/*
					clsDataStructurePA pop = oColorAss.getRootElement();
					clsDataStructurePA pop1 = oFormAss.getRootElement();
					clsDataStructurePA pop2 = oRaceAss.getRootElement();
					clsDataStructurePA pop3 = oTasteAss.getRootElement();
						
					Test2.add(pop);
					Test2.add(pop1);
					Test2.add(pop2);
					Test2.add(pop3);
					
					
					// Just to test the LeafElements
					Test3.add(oColorAss.toString());
					Test3.add(oFormAss.toString());
					Test3.add(oRaceAss.toString());
					Test3.add(oTaste.toString());
					Test3.add("!!"+ oObjectDataStructure.toString());
					
		*/		
			
					
		
		}	
		
				
		return oListWithPositiveObjects;
	}
	// Create list with negative associations
	
	private  ArrayList<clsThingPresentationMesh> CreateListWithNegativeObjects (ArrayList<clsSuperEgoConflictPerception> oForbiddenPerceptions){
		
		ArrayList<clsAssociation> oListWithNegativeAssociations = new ArrayList<clsAssociation>();

		for(clsSuperEgoConflictPerception oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			
			eContentType oContentType = oOneForbiddenPerception.getContentType();
			String oContent           = oOneForbiddenPerception.getContent();
			
		
				clsThingPresentationMesh oObjectDataStructure= 	(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure
																(eDataType.TPM, new clsTriple<eContentType, Object, Object>
																(oContentType,new ArrayList<clsThingPresentation>(), oContent)); 
				
				
				clsThingPresentation oColor = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "Red"));
				clsThingPresentation oForm = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "ROUND"));
				clsThingPresentation oRace = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "MUCHCALORIES"));
				clsThingPresentation oTaste = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ASSOCIATIONTEMP, "BAD"));
				
				
				clsAssociation oColorAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																	  (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oColor);

				oListWithNegativeAssociations.add(oColorAss);
								
				
				clsAssociation oFormAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																	 (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oForm);
				oListWithNegativeAssociations.add(oFormAss);
				
				clsAssociation oRaceAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																	 (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oRace);
				oListWithNegativeAssociations.add(oRaceAss);
				clsAssociation oTasteAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>
																	  (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONTEMP), oObjectDataStructure, oTaste);
				oListWithNegativeAssociations.add(oTasteAss);
				
				oObjectDataStructure= new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1,eDataType.TPM, eContentType.TPM), oListWithNegativeAssociations, oContent); 
				
				
				
				//oObjectDataStructure.assignDataStructure(oColorAss);
//				ArrayList<clsAssociation> pop= oObjectDataStructure.getMoInternalAssociatedContent();
//				Test1 = deepCopy(pop);
				oListWithNegativeObjects.add(oObjectDataStructure);
				//Double pi = oObjectDataStructure.getNumbInternalAssociations();
				//Double pi1 = oObjectDataStructure.getNumbExternalAssociations();
				//Test3.add(String.valueOf(pi));
				//Test3.add(String.valueOf(pi1));
				// Just to test the Root
	/*
				clsDataStructurePA pop = oColorAss.getRootElement();
				clsDataStructurePA pop1 = oFormAss.getRootElement();
				clsDataStructurePA pop2 = oRaceAss.getRootElement();
				clsDataStructurePA pop3 = oTasteAss.getRootElement();
					
				Test2.add(pop);
				Test2.add(pop1);
				Test2.add(pop2);
				Test2.add(pop3);
				
				
				// Just to test the LeafElements
				Test3.add(oColorAss.toString());
				Test3.add(oFormAss.toString());
				Test3.add(oRaceAss.toString());
				Test3.add(oTaste.toString());
				Test3.add("!!"+ oObjectDataStructure.toString());
				
	*/		
				//clsDataStructurePA pop = oColorAss.getLeafElement();
				//Test3.add(pop.toString());
			
			
		}
		
		
				
		return oListWithNegativeObjects;
	}
	// For Test on Simulation -->State
	private void RemoveLast(ArrayList<String> Test){
		for (int i=0;i<Test.size();i++){
			if (i>0){
				Test.remove(0);
			}
			
		}
	}
	
	
	private void defenseMechanism_Idealization (ArrayList<clsSuperEgoConflictPerception> oForbiddenPerceptions) {
		
		idealization = 1.0;
		denial=0.0;
		depreciation =0.0;
		PassForbidenPerceptions=0.0;
		ChartBarIdealization++;

		
		// has to be Called to create the negative Associations 
		CreateListWithNegativeObjects (oForbiddenPerceptions); 
		
		//Just for Test --> State on The Simulator
		for(clsThingPresentationMesh oNegativeObject : CreateListWithNegativeObjects (oForbiddenPerceptions)) {
		Test3.add(" Before Idealization:"+"\n"+oNegativeObject.getInternalAssociatedContent().toString());
		RemoveLast(Test3);
		}
		
		// has to be Called to delete the negative Association
		deleteAssociationsFromPerception (oForbiddenPerceptions, oListWithNegativeObjects);
		
		//Just for Test --> State on The Simulator
		Test.add(" After Idealization:"+"\n"+oInternalAssociationsPositiveOrNegativeObject.toString());
		RemoveLast(Test);
		
	}
	
	private void defenseMechanism_Depreciation (ArrayList<clsSuperEgoConflictPerception> oForbiddenPerceptions) {
		
		depreciation= 1.0;
		denial=0.0;
		idealization=0.0;
		PassForbidenPerceptions=0.0;
		ChartBarDepreciation++;
		
		// has to be Called to create the positive Associations
		CreateListWithPositiveObjects (oForbiddenPerceptions);
		
		//Just for Test --> State on The Simulator
		for(clsThingPresentationMesh oPositiveObject : CreateListWithPositiveObjects (oForbiddenPerceptions)) {
			Test3.add(" Before Depreciation:"+"\n"+oPositiveObject.getInternalAssociatedContent().toString());
			RemoveLast(Test3);
			}

		// has to be Called to delete the positive Association
		deleteAssociationsFromPerception (oForbiddenPerceptions, oListWithPositiveObjects);
		
		//Just for Test --> State on The Simulator
		Test.add(" After Depreciation:"+"\n"+oInternalAssociationsPositiveOrNegativeObject.toString());
		RemoveLast(Test);
	
	}
		
		
	private void deleteAssociationsFromPerception (ArrayList<clsSuperEgoConflictPerception> oForbiddenPerceptions, ArrayList<clsThingPresentationMesh> oListWithPositiveOrNegativeObjects) {
		
		
		boolean found = false;
		clsAssociation oPosiveorNegativeAssociation = null;
//		ArrayList<clsAssociation> oInternalAssociationsPositiveOrNegativeObject = new ArrayList<clsAssociation>();
			

	   	if (oForbiddenPerceptions == null) return;

		// check list of forbidden perceptions
		for(clsSuperEgoConflictPerception oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			eContentType oContentType = oOneForbiddenPerception.getContentType();
			String oContent     = oOneForbiddenPerception.getContent();
			
			ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_OUT).getInternalAssociatedContent();
			//Search in perception
			for(clsAssociation oAssociation : oInternalAssociations){

				if (oAssociation.getAssociationElementB() instanceof clsThingPresentationMesh) 
						
					if( ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContentType().equals(oContentType) &&
						((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContent().equals(oContent)){
						
						
						
						for(clsThingPresentationMesh oPositiveOrNegativeObject : oListWithPositiveOrNegativeObjects) {
						
														
							oInternalAssociationsPositiveOrNegativeObject = ((clsThingPresentationMesh)oPositiveOrNegativeObject).getInternalAssociatedContent();
														
						}
						
					}else{
						
						break;
					}
			}				
			// Delete Associations with positive or negative Properities			
			while(!oInternalAssociationsPositiveOrNegativeObject.isEmpty()){
			
				for(clsAssociation oAssociation1 : oInternalAssociationsPositiveOrNegativeObject){
										
					found = true;
					oPosiveorNegativeAssociation = oAssociation1 ;
					
					break;
				}
				if (found){
							
					oInternalAssociationsPositiveOrNegativeObject.remove(oPosiveorNegativeAssociation );
				}
								
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
	
	 (non-Javadoc)
	 *
	 * @author gelbard
	 * 06.07.2012, 13:23:49
	 * 
	 * Defense mechanism depreciation (perceives only the bad properties of an object and denies the positive properties)
	 * 
	 
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
	
	 (non-Javadoc)
	 *
	 * @author gelbard
	 * 06.07.2012, 12:23:49
	 * 
	 * deletes all positive (or all negative) associations in a TPM
	 * is needed for defense mechanism idealization/depreciation
	 * 
	 
	private void deleteAssociationsFromPerception (ArrayList<clsPair<eContentType, String>> oForbiddenPerceptions, ArrayList<clsThingPresentationMesh> oListWithPositiveOrNegativeObjects) {
		
	   	// If no perception in list to defend return immediately (otherwise NullPointerException)
	   	if (oForbiddenPerceptions == null) return;
		
		// check list of forbidden perceptions
		for(clsPair<eContentType, String> oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			eContentType oContentType = oOneForbiddenPerception.a;
			String oContent     = oOneForbiddenPerception.b;
			
			
			
			
			
			// search in perceptions
			ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_OUT).getMoInternalAssociatedContent();
			
			// search in perceived objects (objects are called associations)
			for(clsAssociation oAssociation : oInternalAssociations){
				if (oAssociation.getMoAssociationElementB() instanceof clsThingPresentationMesh) 
					if( ((clsThingPresentationMesh)oAssociation.getMoAssociationElementB()).getMoContentType().equals(oContentType) &&
						((clsThingPresentationMesh)oAssociation.getMoAssociationElementB()).getMoContent().equals(oContent) ) {
					
						clsThingPresentationMesh oPerceivedObject = (clsThingPresentationMesh) oAssociation.getMoAssociationElementB();
						
						// delete forbidden properties from perceived object
						for(clsThingPresentationMesh oPositiveOrNegativeObject : oListWithPositiveOrNegativeObjects) {
							clsMeshTools.deleteAssociationInObject( oPerceivedObject, oPositiveOrNegativeObject);
							// ---------------------------------------------------TODO FG:    ^^^  das sollte eigentlich ein TP sein
						}
											
					}

			}
			
			
			
			
			
			
		}
	}
	*/
	
    /*(non-Javadoc)
    *
    * @author gelbard
    * 06.07.2013, 13:23:49
    * 
    * Defense mechanism reversal of affect for emotions
    * 
    */
	private void defenseMechanism_ReversalOfAffect(ArrayList<clsSuperEgoConflictEmotion> oForbiddenEmotions_Input, clsEmotion poBaseEmotion) {
	   	// If no emotion in list to defend return immediately (otherwise NullPointerException)
		PassForbidenEmotions=0.0;
		ChartBarReversalOfAffect++;
	   	if (oForbiddenEmotions_Input == null) return;
		
	   	// check list of forbidden emotions
		for(clsSuperEgoConflictEmotion oConflict : oForbiddenEmotions_Input) {
			for(clsEmotion oOneEmotion : poBaseEmotion.generateExtendedEmotions()) {
				if(oOneEmotion.getContent().equals(oConflict.getConflictEmotionType())) {
				    double rReversalStrength = oConflict.getConflictTension();
				    // add the old emotion intensity to the emotion intensity of the emotion FEAR
                    double rTransfer = oOneEmotion.getSourceAggr() * rReversalStrength;
                    
                    // do not transfer above 1.0
					rTransfer = Math.min(rTransfer, 1 - oOneEmotion.getSourceLibid());
					
					/*Kollmann: this stores the reversal rate for visualization. The rate will be calculated based on the
					 *          current aggressive component of the base emotion*/
                    reversalOfAffect = rTransfer / oOneEmotion.getSourceAggr();
                    
                    poBaseEmotion.setSourceAggr(poBaseEmotion.getSourceAggr() - rTransfer);
					poBaseEmotion.setSourceLibid(poBaseEmotion.getSourceLibid() + rTransfer);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	/* @author SUN 01.2014
     * This Function transfer the Source Emotion(s) to other Emotion(s) as the Target, based on a Emotion-to-Emotion Hashmap.
     * If the Target Emotion(s) already exist, the current Emotion Intensity will be controlled, it will be decided, 
     * whether the Source Emotion should be deleted or remained, but with updated Intensity.
     * Otherwise will the new Target Emotion(s) created, and the Source Emotion should be deleted.
     */
    private ArrayList<clsEmotion> defenseMechanism_ReactionFormation(ArrayList<clsSuperEgoConflictEmotion> oForbiddenEmotions_Input, ArrayList<clsEmotion> oEmotions_Output) {
        
        reactionFormation =1.0;
        PassForbidenEmotions=0.0;
        ChartBarReactionFormation++;
        boolean oDMflag=false; //Flag for whether this DM will be activated.
        boolean oNewEmotionFlag=false; //Identified, whether a new Emotion should be created in the Output or not. false means new Emotion should be created
        
        HashMap<eEmotionType,eEmotionType> oEmotionMap=new HashMap<eEmotionType,eEmotionType>(); //HashMap of the Table(Emotion-Emotion) for Reaction Formation
        oEmotionMap.put(eEmotionType.ANGER,eEmotionType.GUILT);
        oEmotionMap.put(eEmotionType.MOURNING,eEmotionType.JOY);
        oEmotionMap.put(eEmotionType.ELATION,eEmotionType.MELANCHOLIA);
        oEmotionMap.put(eEmotionType.JOY,eEmotionType.DISGUST);  
        oEmotionMap.put(eEmotionType.HATE,eEmotionType.LOVE);
        oEmotionMap.put(eEmotionType.LOVE,eEmotionType.HATE);
        
        if (oForbiddenEmotions_Input == null) return oEmotions_Output; // if no Emotion should be defensed, return immediately
        
        ArrayList<eEmotionType> oSuitableEmotions = new ArrayList<eEmotionType>(); //List for the "Forbidden" Emotion Types in Input, which can be transformed with this DM
        for(clsSuperEgoConflictEmotion oConflict : oForbiddenEmotions_Input){ 
            for(Iterator<eEmotionType> i = oEmotionMap.keySet().iterator(); i.hasNext();){
                eEmotionType key = i.next();
                if(oConflict.getConflictEmotionType() == key){ //found a Emotion Type, which can be transformed with this DM.
                    oSuitableEmotions.add(key);  // add it to the List
                    oDMflag=true;  
                }
            }
        }
        if(oDMflag==false) return oEmotions_Output; //If no input Emotion Types can be transformed with this DM, return immediately
        

        for(eEmotionType oOneForbiddenEmotion : oSuitableEmotions) {
            for(clsEmotion oOneEmotion : oEmotions_Output) {
                if(oOneEmotion.getContent() == oOneForbiddenEmotion) { //Original Emotion in output found, which should be transformed.
                    for(clsEmotion oOneEmotion1 : oEmotions_Output) {
                       if(oEmotionMap.get(oOneForbiddenEmotion) == oOneEmotion1.getContent()){ //if the Target Emotion already exists
                           double oNewEmotionIntensity = oOneEmotion1.getEmotionIntensity() + oOneEmotion.getEmotionIntensity();
                           if (oNewEmotionIntensity > 1.0) {                                   // The Target Emotion will be too strong, if the intensity is higher than 1   
                               oOneEmotion1.setEmotionIntensity(1.0);                        // the original emotion can not 100% transformed
                               oOneEmotion .setEmotionIntensity(oNewEmotionIntensity - 1.0); // -> the original emotion has a lower EmotionIntensity but still exists 
                           }
                           else{                                                          //If the Target Emotion will not be too strong
                               oOneEmotion1.setEmotionIntensity(oNewEmotionIntensity);  //The original Emotion can be 100% transformed,
                               oEmotions_Output.remove(oOneEmotion);                      //Delete the original Emotion from the Output
                               return oEmotions_Output; // Friedrich 2014-02-19
                           }
                           oNewEmotionFlag=true;                                           // Set the Flag, let the Program know, it's not necessary to create a new Emotion
                       }
                    }
                    if(oNewEmotionFlag==false){                                   //Target Emotion doesn't exist in the Output
                        eContentType oContentType = eContentType.COMPLEXEMOTION;
                        if(oEmotionMap.get(oOneForbiddenEmotion)==eEmotionType.JOY){ //only "Joy" in Hash-Value belong to Basic Emotion 
                            oContentType=eContentType.BASICEMOTION;
                        }
                        clsEmotion oNewEmotion = clsDataStructureGenerator.generateEMOTION( //Create the new Target Emotion
                                new clsTriple <eContentType, eEmotionType, Object>(oContentType, oEmotionMap.get(oOneForbiddenEmotion), 
                                oOneEmotion.getEmotionIntensity()),
                                oOneEmotion.getSourcePleasure(),
                                oOneEmotion.getSourceUnpleasure(),
                                oOneEmotion.getSourceLibid(),
                                oOneEmotion.getSourceAggr()); 
                        oEmotions_Output.add(oNewEmotion);      //Add the Target Emotion   
                        oEmotions_Output.remove(oOneEmotion);   //Remove the Original Emotion
                        return oEmotions_Output; // Friedrich 2014-02-19
                    }                                  
                }
            }
        }
        return oEmotions_Output;
    }
    
	
	
    /* @author SUN 10.2013
     * This Method will find a random Target(TPM) for the forbidden Emotion,  
     * and build a Association between them. 
     * Parameters are to be defended forbidden Emotions and output Emotions
     * as return value is a list of built Associations
     */ 
    
    private ArrayList<clsAssociation> defenseMechanism_Projection(ArrayList<eEmotionType> oForbiddenEmotions_Input, 
            ArrayList<clsEmotion> oEmotions_Output){       
        //projection =1.0;
        PassForbidenEmotions=0.0;
        //ChartBarProjection++;
        if (oForbiddenEmotions_Input == null) return null;                      // if no Emotion should be defensed, return immediately
        
        ArrayList<clsThingPresentationMesh> oTargetTPM = findEntityInSight();   // get the Target's candidates
       
        int counter= oTargetTPM.size();    // Range for the random figure
        if (counter == 0) return null;     // if no candidate exits, return immediately       
        ArrayList<clsAssociation> oNewAssociation = new ArrayList<clsAssociation>();  // will be used to save the created Associations        
        Random r = new Random();           // for the random choice of Emotion's Target
        int targetNr= r.nextInt(counter);  // number of the random agent, which will be chosen as the target for the forbidden emotion
        
        //followed prompt output are used for software test
        //System.out.println("");
        //System.out.println("##################################################################################################### "); 
        //System.out.println("######### Info for Projection, available Objects in sight: "+oTargetTPM.toString()); 
        //System.out.println("######### Info for Projection, amount of available Objects: "+counter);        
        //System.out.println("######### Info for Projection, Nr. of the chosen Object: "+targetNr); 
        
        for(eEmotionType oOneForbiddenEmotion : oForbiddenEmotions_Input) {           
                    for(clsEmotion oOneEmotion : oEmotions_Output) { 
                        if(oOneEmotion.getContent()==oOneForbiddenEmotion){     //find the forbidden Emotion from Output
                            throw new RuntimeException("Defense mechanism projection tried to create an emotion the wrong way. Use clsDataStructureGenerator.generateASSOCIATIONEMOTION to do it properly.");
//                            try{
//                                clsAssociation oAssociation = new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType>(
//                                        -1, eDataType.ASSOCIATIONEMOTION, eContentType.UNDEFINED),          
//                                        oOneEmotion, oTargetTPM.get(targetNr));     // Build the Association between forbidden 
//                                                                                    //Emotion and the chosen random Target, reference from F14 line 1178
//                                oNewAssociation.add(oAssociation);          // add this Association to the List
//                                ((clsThingPresentationMesh) moPerceptionalMesh_IN).getInternalAssociatedContent().add(oAssociation); 
//                                // add the association to the agent's perception-memory                                
//                                oEmotions_Output.remove(oOneEmotion);              //remove the forbidden Emotions, since it has been projected to others
//                                
//                                //followed prompt output are used for software test  
//                                ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_IN).getInternalAssociatedContent();
//                                for(clsAssociation oAssociation1 : oInternalAssociations){  //look up the ARSIN in Sight, refer to line 698
//                                    if (oAssociation1==oAssociation){
//                                        System.out.println("######### Info for Projection, new Association added to the Perception ");
//                                        }
//                                    }
//                                //System.out.println("######### Info for Projection, added new Association: "+ oAssociation.toString());
//                                //System.out.println("######### Info for Projection, done "); 
//                                //System.out.println("##################################################################################################### ");
//                                //System.out.println("");
//                                
//                                break;                                             // current action done, jump to the next forbidden Emotion
//                            }catch(Exception e){
//                                e.printStackTrace();
//                                System.out.println("Unable to create the Association for Projection!");
//                            }                     
                        }
                    }                                           
        }       
        return oNewAssociation;                  
    }
    
    /* @author SUN 03.2013
     * This Method will be used in defenseMechanism_Projection(). 
     * This Method will check the inputed Perceptions, to find all ENTITYs like ARSIN from Perceptions
     * It provides the Projection's Targets-TPM for defenseMechanism_Projection(). 
     */
    private ArrayList<clsThingPresentationMesh> findEntityInSight(){
        ArrayList<clsThingPresentationMesh> oSelectedTPM = new ArrayList<clsThingPresentationMesh>();
        ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_IN).getInternalAssociatedContent();
        for(clsAssociation oAssociation : oInternalAssociations){  //look up the ARSIN in Sight, refer to line 698
            if (oAssociation.getAssociationElementB() instanceof clsThingPresentationMesh){
                if( ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContentType().equals(eContentType.ENTITY) &&
                    ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContent().equals("BODO") ) { //ARSIN found in Sight
                    oSelectedTPM.add((clsThingPresentationMesh)oAssociation.getAssociationElementB());                //add ARSIN to the List
                }
            }
        }
        return oSelectedTPM;
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
		
		oSearchResult = this.getLongTermMemory().searchMesh(newTPMImage, moBlockedContentType, 0.0, 2);	//Set pnLevel=2, in order to add direct matches
		
		for (clsPair<Double, clsDataStructurePA> oPair : oSearchResult) {
			oRetVal.add((clsThingPresentationMesh) oPair.b);
		}
		
		return oRetVal;
	}

	private ArrayList<clsPair<Double, clsDataStructurePA>> initialFillRepressedContentWithPIMatch() {
	    ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double, clsDataStructurePA>>();
        
        ArrayList<clsPair<Double, clsDataStructurePA>> oSearchResult = new ArrayList<clsPair<Double, clsDataStructurePA>>();
        
        clsThingPresentationMesh newTPMImage = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.TPM, moBlockedContentType), new ArrayList<clsAssociation>(), "EMPTY");
        //clsPrimaryDataStructureContainer oPattern = new clsPrimaryDataStructureContainer(newTI, new ArrayList<clsAssociation>());
        
        oSearchResult = this.getLongTermMemory().searchMesh(newTPMImage, moBlockedContentType, 0.0, 2); //Set pnLevel=2, in order to add direct matches
        
        for (clsPair<Double, clsDataStructurePA> oPair : oSearchResult) {
            oRetVal.add(oPair);
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
		send_I5_15(moPerceptionalMesh_OUT, moEmotions_Output);
		send_I5_16(moQuotasOfAffect_Output, moEmotions_Output, moWordingToContext, moPerceptionalMesh_OUT);
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
	public void send_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2, clsThingPresentationMesh poPerceptionalMesh) {
		((I5_16_receive)moModuleList.get(71)).receive_I5_16(poAffectOnlyList, poEmotions, moWordingToContext2, poPerceptionalMesh);
		putInterfaceData(I5_16_send.class, poAffectOnlyList, moWordingToContext2);
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
	    moTimeInputChartData();
        
        // empty the list from last step otherwise list only grows
        moQuotasOfAffect_Output.clear();
        if (moForbiddenPerceptions_Input.isEmpty()){
            ResetTimeChartForbidenPerceptionData(); 
        }
        if (moForbiddenEmotions_Input.isEmpty()){
            ResetTimeChartForbidenEmotionData(); 
        }
       
        // check for a psychoanalytic conflict
        // defense mechanisms are delayed by one cycle to produce a situation where conflict exists and no action plans are executed
        if (!moForbiddenPerceptions_Input.isEmpty() && !defense_active)
        {
            // conflicting events exist -> activate conflict -> activate defense mechanisms but do not defend yet. (defense will work in the next cycle)
            defense_active = true;
            
            // send quota of affect 999.9 via I5.17 to produce a "CONFLICT"-signal in F20
             moAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<eContentType, Object>(eContentType.AFFECT, 999.9)); 
             moQuotasOfAffect_Output.add(moAffect);
            
            return;
        }
        else if (moForbiddenPerceptions_Input.isEmpty() &&
                 moForbiddenEmotions_Input.isEmpty())
        {
            // no conflicting events -> deactivate defense mechanisms
            defense_active = false;
            return;
        }
        // Defense mechanisms start to work.
                
                    
        // For time chart
        moTimeInputChartData();
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
	
	private void ResetTimeChartDefenseForbidenPerceptionData(){
		
		PassForbidenPerceptions=1.0; // to see for how long no defenses are applied to the forbidden perceptions    
		denial=0.0;
		idealization=0.0;
		depreciation =0.0;
		ChartBarPassForbidenPerception++;
		
	}
	private void ResetTimeChartForbidenPerceptionData(){
		
		PassForbidenPerceptions=0.0; // to see for how long no defenses are applied to the forbidden perceptions    
		denial=0.0;
		idealization=0.0;
		depreciation =0.0;
		
	}
	private void ResetTimeChartDefenseForbidenEmotionData(){
		PassForbidenEmotions=1.0;
		reversalOfAffect =0.0;
		ChartBarPassForbidenEmotion++;
	
	}
	private void ResetTimeChartForbidenEmotionData(){
		PassForbidenEmotions=0.0;
		reversalOfAffect =0.0;
	
	}
	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 20:23:54
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartAxis()
	 */
	@Override
	public String getCombinedTimeChartAxis() {
		// TODO (Lotfi) - Auto-generated method stub
		return "0 to 1";
	}
	

	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 20:23:54
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getCombinedTimeChartData() {
		// TODO (Lotfi) - Auto-generated method stub
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		//GetCombinedTimeDefenseData();
			
		ArrayList<Double> oDenial =new ArrayList<Double>();
		oDenial.add(moTimeChartData.get("TimeDenial"));
		oResult.add(oDenial);
		
		ArrayList<Double> oIdealization =new ArrayList<Double>();
		oIdealization.add(moTimeChartData.get("TimeIdealization"));
		oResult.add(oIdealization);
		
		ArrayList<Double> oDepreciation  =new ArrayList<Double>();
		oDepreciation.add(moTimeChartData.get("TimeDepreciation"));
		oResult.add(oDepreciation);
		
		ArrayList<Double> oReversalOfAffect =new ArrayList<Double>();
		oReversalOfAffect.add(moTimeChartData.get("TimeReversalOfAffect"));
		oResult.add(oReversalOfAffect);
				
		ArrayList<Double> oNoEmotionDefense =new ArrayList<Double>();
		oNoEmotionDefense.add(moTimeChartData.get("TimePassForbidenEmotion"));
		oResult.add(oNoEmotionDefense);
		
		ArrayList<Double> oNoPerceptionDefense =new ArrayList<Double>();
		oNoPerceptionDefense.add(moTimeChartData.get("TimePassForbidenPerception"));
		oResult.add(oNoPerceptionDefense);
			
		return oResult;
	}


	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 20:23:54
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getChartTitles()
	 */
	@Override
	public ArrayList<String> getChartTitles() {
		// TODO (Lotfi) - Auto-generated method stub
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("Denial");
		oResult.add("Idealization");
		oResult.add("Depreciation");
		oResult.add("ReversalOfAffect");
		oResult.add("PassForbiddenEmotions");
		oResult.add("PassForbiddenPerceptions");
		
		
		return oResult;
	}
	

	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 20:23:54
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getValueCaptions()
	 */
	@Override
	public ArrayList<ArrayList<String>> getValueCaptions() {
		// TODO (Lotfi) - Auto-generated method stub
		ArrayList<ArrayList<String>> oResult = new ArrayList<ArrayList<String>>();
		
		
		ArrayList<String> oDenial = new ArrayList<String>();
		oDenial.add("Denial");
		oResult.add(oDenial);
		
		ArrayList<String> oIdealization = new ArrayList<String>();
		oIdealization.add("Idealization");
		oResult.add(oIdealization);
		
		ArrayList<String> oDepreciation = new ArrayList<String>();
		oDepreciation.add("Depreciation");
		oResult.add(oDepreciation);
		
		ArrayList<String> oReversalOfAffect = new ArrayList<String>();
		oReversalOfAffect.add("ReversalOfAffect");
		oResult.add(oReversalOfAffect);

		ArrayList<String> oNoEmotionDefense = new ArrayList<String>();
		oNoEmotionDefense.add("PassForbiddenEmotions");
		oResult.add(oNoEmotionDefense);
		
		ArrayList<String> oNoPerceptionDefense = new ArrayList<String>();
		oNoPerceptionDefense.add("PassForbiddenPerceptions");
		oResult.add(oNoPerceptionDefense);
		
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 12.04.2013 13:41:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChartF06#getBarChartTitle()
	 */
	@Override
	public String getBarChartTitle() {
		// TODO (Lotfi) - Auto-generated method stub
		return "Defense Mechanisms";
	}

	/* (non-Javadoc)
	 *
	 * @since 12.04.2013 13:41:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChartF06#getBarChartData()
	 */
	@Override
	public HashMap<String, Double> getBarChartData() {
		// TODO (Lotfi) - Auto-generated method stub
		return moTimeChartData;
	}

    /* (non-Javadoc)
     *
     * @since 20.02.2014 11:06:01
     * 
     * @see modules.interfaces.I5_22_receive#receive_I5_22(double)
     */
    @Override
    public void receive_I5_22(double neutralisationFactor, double neutralizedIntensity) {
        moEgoStrength = neutralizedIntensity;
    }
}
