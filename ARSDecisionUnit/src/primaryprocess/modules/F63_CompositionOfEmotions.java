/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author schaat
 * 07.06.2012, 15:47:11
 */
package primaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorCombinedTimeChart;
import inspector.interfaces.itfInspectorGenericTimeChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import org.slf4j.Logger;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import memorymgmt.storage.DT4_PleasureStorage;
import modules.interfaces.I5_10_receive;
import modules.interfaces.I5_21_receive;
import modules.interfaces.I5_21_send;
import modules.interfaces.I5_3_receive;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * (see document "Compositions of Emotions")
 * F63 generate Emotions based on  Pleasure and  Unpleasure. Six basic emotions exists: ANGER, Anxiety, Mourning, Pleasure, Elation and Saturation. They are not mutual exclusive. 
 * Dependent on the dominance of the basic categories Pleasure, Unpleasure and the aggressive and libidinous drive parts, the corresponding emotions are generated.
 * Dominance of Unpleasure +  Dominance of aggr. Drivecomponents --> ANGER
 * Dominance of Unpleasure +  Dominance of libid. Drivecomponents --> Mourning
 * Dominance of Unpleasure --> Anxiety
 * Dominance of Pleasure --> Pleasure
 * Dominance of Pleasure + Dominance of aggr. Drivecomponents -->  Elation
 * Dominance of Pleasure +  Dominance of libid. Drivecomponents --> Saturation
 * 
 * 
 * @author schaat
 * 07.06.2012, 15:47:11
 */
public class F63_CompositionOfEmotions extends clsModuleBase 
					implements  itfInspectorGenericTimeChart, I5_3_receive, I5_10_receive, I5_21_send,itfInspectorCombinedTimeChart {

	//Statics for the module
	public static final String P_MODULENUMBER = "63";
	
	private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
	private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
	        
	private double mrModuleStrength;
	private double mrInitialRequestIntensity;

	public static final String P_REALATIV_THRESHOLD = "REALATIV_THRESHOLD";
	public static final String P_THRESHOLD_RANGE = "THRESHOLD_RANGE";
	public static final String P_PERCEPTION_PLEASURE_IMPACT_FACTOR = "PERCEPTION_PLEASURE_IMPACT_FACTOR";
	public static final String P_PERCEPTION_UNPLEASURE_IMPACT_FACTOR = "PERCEPTION_UNPLEASURE_IMPACT_FACTOR";
    public static final String P_EMOTIONRECOGNITION_IMPACT_FACTOR = "EMOTIONRECOGNITION_IMPACT_FACTOR"; //koller

	
	//Private members for send and recieve
	private ArrayList<clsEmotion> moEmotions_OUT; 
	private ArrayList<clsDriveMesh> moDrives_IN;
	private clsThingPresentationMesh moPerceptions_IN;
	
	
	// threshold to determine in which case domination of a emotion occurs
	private double mrRelativeThreshold;
	private double mrThresholdRange;
	
	DT4_PleasureStorage moPleasureStorage;
	
	// values from perception-track (triggered emotions). to get a better output in the inspectors and a capsulated function-call a hashmap is used instead of separate variables
	HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();
	
	// values from emotions assoziated to memorized scenes (only if the scenes have been activated in F46).
	// to get a better output in the inspectors and a capsulated function-call a hashmap is used instead of separate variables
    HashMap<String, Double> oMemoryExtractedValues = new HashMap<String, Double>();

	// values from drive-track . to get a better output in the inspectors a hashmap is used instead of separate variables
	HashMap<String, Double> oDrivesExtractedValues = new HashMap<String, Double>();

	// personality parameter, perceiving a drive object sould trigger less emotions than the bodily needs
	private double mrPerceptionPleasureImpactFactor;
	private double mrPerceptionUnpleasureImpactFactor;
	
	// koller 
    private double mrEmotionrecognitionImpactFactor;
	
	private clsWordPresentationMesh moWordingToContext;
    
	double mrGrade = 0;
	
	
	private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	private final Logger log = clsLogger.getLog("F" + P_MODULENUMBER);
	
	public F63_CompositionOfEmotions(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage,
			DT3_PsychicIntensityStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F55", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F55", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
		
		applyProperties(poPrefix, poProp);	
		
		mrRelativeThreshold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_REALATIV_THRESHOLD).getParameterDouble();
		mrThresholdRange = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_THRESHOLD_RANGE).getParameterDouble();
		mrPerceptionPleasureImpactFactor =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_PLEASURE_IMPACT_FACTOR).getParameterDouble();
		mrPerceptionUnpleasureImpactFactor =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_UNPLEASURE_IMPACT_FACTOR).getParameterDouble();
		moPleasureStorage = poPleasureStorage;
		
		//koller
        mrEmotionrecognitionImpactFactor =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_IMPACT_FACTOR).getParameterDouble();
        
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
	}

	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 02.07.2012, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("mrGrade", mrGrade);
		text += toText.valueToTEXT("rDriveLibid", oDrivesExtractedValues.get("rDriveLibid"));
		text += toText.valueToTEXT("rDriveAggr", oDrivesExtractedValues.get("rDriveAggr"));
		text += toText.valueToTEXT("rDrivePleasure",  oDrivesExtractedValues.get("rDrivePleasure"));
		text += toText.valueToTEXT("rDriveUnpleasure", oDrivesExtractedValues.get("rDriveUnpleasure"));
				
		text += toText.valueToTEXT("rPerceptionUnpleasure", oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
		text += toText.valueToTEXT("rPerceptionPleasure", oPerceptionExtractedValues.get("rPerceptionPleasure"));
		text += toText.valueToTEXT("rPerceptionLibid", oPerceptionExtractedValues.get("rPerceptionLibid"));
		text += toText.valueToTEXT("rPerceptionAggr", oPerceptionExtractedValues.get("rPerceptionAggr"));
		
		text += toText.listToTEXT("moEmotions_OUT", moEmotions_OUT);
		
		text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getExternalAssociatedContent());
		
		//text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getMoInternalAssociatedContent());
		
		return text;
	}
	


	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 02.07.2012, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moEmotions_OUT = new ArrayList<clsEmotion>() ;
		
		
		// four basic categories ("Grundkategorien", see document "Compositions of Emotions")
		double rSystemPleasure = 0.0; 
		double rSystemUnpleasure = 0.0;
		double rSystemLibid = 0.0;
		double rSystemAggr = 0.0;
		
		// values from drive-track
		double rDrivePleasure = 0.0; 
		double rDriveLibid = 0.0;
		double rDriveAggr = 0.0;
		
		//1. Get Unpleasure from all drives, and the aggr. and libid parts
		for (clsDriveMesh oDM: moDrives_IN) {
			if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
				rDriveLibid = nonProportionalAggregation(rDriveLibid, oDM.getQuotaOfAffect());  
				
			} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
				rDriveAggr = nonProportionalAggregation(rDriveAggr, oDM.getQuotaOfAffect());
				
			}
		}
		
		//get Pleasure
		rDrivePleasure =  moPleasureStorage.send_D4_1();
		
		// TEMPORARY
		oDrivesExtractedValues.put("rDrivePleasure", rDrivePleasure);
		oDrivesExtractedValues.put("rDriveUnpleasure", rDriveLibid+rDriveAggr);
		oDrivesExtractedValues.put("rDriveLibid", rDriveLibid);
		oDrivesExtractedValues.put("rDriveAggr", rDriveAggr);
		
		/* emotions triggered by perception (from memory) influence emotion-generation
		 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (un-pleasure etc the emotion is based on) to influence the emotion generation in F63
		 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
		 * 
		 */
		oPerceptionExtractedValues = getEmotionValuesFromPerception();
		
		// aggregate values from drive- and perception track
		// normalize grundkategorien
		// (if agent sees many objects the perception has more influence, otherwise drives have more influence on emotions)
		rSystemUnpleasure = nonProportionalAggregation(rDriveLibid+rDriveAggr, oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
		rSystemPleasure = nonProportionalAggregation(rDrivePleasure, oPerceptionExtractedValues.get("rPerceptionPleasure"));
		rSystemLibid = nonProportionalAggregation(rDriveLibid, oPerceptionExtractedValues.get("rPerceptionLibid"));
		rSystemAggr = nonProportionalAggregation(rDriveAggr, oPerceptionExtractedValues.get("rPerceptionAggr"));
		
		/* emotions associated to images (from memory) have a direct influence on emotions, if the scenes have been activated (happens in F46)
         * 
         */
        oMemoryExtractedValues = getEmotionValuesFromMemory();
        
        // aggregate values from drive- and perception track
        // normalize grundkategorien
        // (if agent sees many objects the perception has more influence, otherwise drives have more influence on emotions)
        rSystemUnpleasure = nonProportionalAggregation(rSystemUnpleasure, oMemoryExtractedValues.get("rPerceptionUnpleasure"));
        rSystemPleasure = nonProportionalAggregation(rSystemPleasure, oMemoryExtractedValues.get("rPerceptionPleasure"));
        rSystemLibid = nonProportionalAggregation(rSystemLibid, oMemoryExtractedValues.get("rPerceptionLibid"));
        rSystemAggr = nonProportionalAggregation(rSystemAggr, oMemoryExtractedValues.get("rPerceptionAggr"));
        
        if(oMemoryExtractedValues.get("rPerceptionUnpleasure") < 0.1) {
            log.debug("Unpleasure missing");
        }
        
        //create the base emotion state
        clsEmotion oBaseEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0, rSystemPleasure, rSystemUnpleasure, rSystemLibid, rSystemAggr);
        oBaseEmotion.setRelativeThreshold(mrRelativeThreshold);
        oBaseEmotion.setThresholdRange(mrThresholdRange);
        moEmotions_OUT.add(oBaseEmotion);
        
	    double rRequestedPsychicIntensity = 0.0;
	                
	    double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	            
	    double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
	            
	    moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
	
	    log.debug("Current emotions: ");
        
        for(clsEmotion oEmotion: moEmotions_OUT) {
            log.debug(oEmotion.toString());
        }
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 18.12.2012, 15:48:45
	 * 
	 */
	private double nonProportionalAggregation (double rBaseValue, double rAddValue) {
	    /*if(rBaseValue < 0 || rBaseValue > 1) {
            throw new RuntimeException("Attempting aggregation a base value not between 0 and 1");
        }*/
	    
	    rBaseValue = rBaseValue + (1 - rBaseValue) * rAddValue;
	    
	    /*if(rBaseValue < 0 || rBaseValue > 1) {
	        throw new RuntimeException("Aggregation changed base value to value outside of 0 - 1");
	    }*/
	    
	    return rBaseValue;
	}
	
	
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 05.07.2012, 15:48:45
	 * emotions triggered by perception (by - to PI- similar RIs from memory) influence emotion-generation
	 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emotion is based on) to influence the emotion generation in F63
	 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
	 * ZK: use memorized drives (from entities) AND memorized emotions (from images) from perception track for emotion-generation
	 */
	private HashMap<String, Double> getEmotionValuesFromPerception() {
		// values from perception-track (triggered emotions)
		double rPerceptionPleasure = 0.0; 
		double rPerceptionUnpleasure = 0.0;
		double rPerceptionLibid = 0.0;
		double rPerceptionAggr = 0.0;
				
		double rInfluencePerception = 0;
		
		double rInfluencePerceivedObjects = 0.3;
		
		clsDriveMesh oDM = null;
		//clsEmotion oExperiencedEmotion = null;
		
		// use QoA of the PI's entities  for emotion-generation
		for(clsAssociation oPIINtAss: moPerceptions_IN.getInternalAssociatedContent()) {
			if(oPIINtAss.getContentType() == eContentType.PARTOFASSOCIATION){
				
				for (clsAssociation oEntityAss: ((clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getExternalAssociatedContent()) {
					// exclude empty spaces (they are currently associated with drives). just use entities. (this is not nice, but due to the use of emptySpaces-objekte in ars necessary)
					if ( oEntityAss.getContentType() == eContentType.ASSOCIATIONDM && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")   ) {
																		
						oDM = (clsDriveMesh)oEntityAss.getAssociationElementA();
						
						if(oDM.getContentType() == eContentType.LIBIDO) {
							//rPerceptionPleasure += mrPerceptionPleasureImpactFactor*oDM.getQuotaOfAffect();
							// Change by Kollmann - should be calculated as any other emotion value, by nonProportionalAggregation with PerceptionInfluence
							rPerceptionPleasure = nonProportionalAggregation(rPerceptionPleasure, rInfluencePerceivedObjects*oDM.getQuotaOfAffect());
						}
						else {
							
							// influence of perceived objects is dependent on agent's actual drives. e.g. if the agent is hungry and it perceives a schnitzel, 
							// the perception influences emotion-generation more than in the case of an agent without hunger  
//							for (clsDriveMesh oActualDM: moDrives_IN) {
//								if (oDM.compareTo(oActualDM) >= 0.75) {
//									rInfluencePerception = oDM.getQuotaOfAffect() * oActualDM.getQuotaOfAffect();
//									if(rInfluencePerception>1) rInfluencePerception = 1;
//								}
//							}
							
							List<clsDriveMesh> oMatchingDMs = clsDriveMesh.filterListByDM(moDrives_IN, oDM, clsDriveMesh.StructureComparator);
							if(oMatchingDMs.size() > 1) {
							    log.error("DM " + oDM + " has more than one simmilar DMs in the drives list.");
							    rInfluencePerception = 0;
							}
							
							if(oMatchingDMs.size() > 0) {
							    rInfluencePerception = oMatchingDMs.get(0).getQuotaOfAffect() * oDM.getQuotaOfAffect();
							} else {
							    log.warn("DM " + oDM + " has no matching DMs in drive list.");
							    rInfluencePerception = 0;
							}
							
						    //rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, oDM.getQuotaOfAffect());
							if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
								rPerceptionLibid = nonProportionalAggregation(rPerceptionLibid, rInfluencePerceivedObjects*rInfluencePerception*oDM.getQuotaOfAffect());
							} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
								rPerceptionAggr = nonProportionalAggregation(rPerceptionAggr, rInfluencePerceivedObjects*rInfluencePerception*oDM.getQuotaOfAffect());
							}
							
							rPerceptionPleasure = nonProportionalAggregation(rPerceptionPleasure, rInfluencePerceivedObjects*rInfluencePerception*oDM.getQuotaOfAffect());
						}
						
					}
					
					//koller Emotionsübertragung. Hier wirken sich die Emotionen wahrgenommener Bodystates anderer Agenten auf die eigenen Affektbeträge aus.

                    if ( oEntityAss.getContentType() == eContentType.ASSOCIATIONATTRIBUTE && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")   ) {
                        if(oEntityAss.getAssociationElementA().getContentType() == eContentType.ENTITY){
                            
                            clsThingPresentationMesh oTPMA = (clsThingPresentationMesh)oEntityAss.getAssociationElementA();
                            if(!(oTPMA.getContent().equals("SELF"))){ //koller wenn der bodystate am TPM Self angehängt ist, wird er ignoriert. Es kann duch Entfernen dieses ifs wieder Einfluss bekommen. 
                                if(oEntityAss.getAssociationElementB().getContentType() == eContentType.ENTITY){
                            
                                    clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oEntityAss.getAssociationElementB();
                                    
                                    if(oTPM.getContent().equals("Bodystate")){
                                        for(clsAssociation oAssoc : oTPM.getInternalAssociatedContent()){
                                            if(oAssoc.getAssociationElementB().getContentType() == eContentType.BASICEMOTION){
                                                clsEmotion oEmotionFromBodystate = (clsEmotion)oAssoc.getAssociationElementB();
                                                //koller EmotionRecognitionFactor * PerceptionPleasureImpactFactor * SourcePleasure/Unpleasure/Aggr/Libid * EmotionIntensity     
                                                rPerceptionPleasure = nonProportionalAggregation(rPerceptionPleasure, mrEmotionrecognitionImpactFactor*mrPerceptionPleasureImpactFactor*oEmotionFromBodystate.getSourcePleasure()*oEmotionFromBodystate.getEmotionIntensity()); 
                                                rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, mrEmotionrecognitionImpactFactor*mrPerceptionUnpleasureImpactFactor*oEmotionFromBodystate.getSourceUnpleasure()*oEmotionFromBodystate.getEmotionIntensity());
                                                rPerceptionLibid = nonProportionalAggregation(rPerceptionLibid, mrEmotionrecognitionImpactFactor*mrPerceptionUnpleasureImpactFactor*oEmotionFromBodystate.getSourceLibid()*oEmotionFromBodystate.getEmotionIntensity());
                                                rPerceptionAggr = nonProportionalAggregation(rPerceptionAggr, mrEmotionrecognitionImpactFactor*mrPerceptionUnpleasureImpactFactor*oEmotionFromBodystate.getSourceAggr()*oEmotionFromBodystate.getEmotionIntensity()); 
                                            }
                                        }
                                    }
                                }
                            }
                        }  
                    }//end koller  
                    
                    //Experienced emotion for entities
                    /*if(oEntityAss.getContentType().equals(eContentType.ASSOCIATIONEMOTION) && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")) {
                        //check if the association really connects an emotion with a TPM
                        if(oEntityAss.getAssociationElementA() instanceof clsEmotion && oEntityAss.getAssociationElementB() instanceof clsThingPresentationMesh) {
                            oExperiencedEmotion = (clsEmotion) oEntityAss.getAssociationElementA();
                            
                            rPerceptionPleasure = nonProportionalAggregation(rPerceptionPleasure, oExperiencedEmotion.getSourcePleasure()); 
                            rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, oExperiencedEmotion.getSourceUnpleasure());
                            rPerceptionLibid = nonProportionalAggregation(rPerceptionLibid, oExperiencedEmotion.getSourceLibid());
                            rPerceptionAggr = nonProportionalAggregation(rPerceptionAggr, oExperiencedEmotion.getSourceAggr());
                        } else {
                            log.warn("Found ASSOCIATIONEMOTION that does NOT connect clsEmotion to clsThingPresentationMesh:");
                            log.warn(oEntityAss.toString());
                        }
                    }*/
				}
			}
        }
		
        rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, rPerceptionLibid+rPerceptionAggr);
		 
		HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();
		oPerceptionExtractedValues.put("rPerceptionPleasure", rPerceptionPleasure);
		oPerceptionExtractedValues.put("rPerceptionUnpleasure", rPerceptionUnpleasure);
		oPerceptionExtractedValues.put("rPerceptionLibid", rPerceptionLibid);
		oPerceptionExtractedValues.put("rPerceptionAggr", rPerceptionAggr);
		
		return oPerceptionExtractedValues;
	}
	
	/* (non-Javadoc)
    *
    * @author schaat
    * 05.07.2012, 15:48:45
    * emotions triggered by perception (by - to PI- similar RIs from memory) influence emotion-generation
    * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emotion is based on) to influence the emotion generation in F63
    * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
    * ZK: use memorized drives (from entities) AND memorized emotions (from images) from perception track for emotion-generation
    */
   private HashMap<String, Double> getEmotionValuesFromMemory() {
       double rMemoryPleasure = 0.0; 
       double rMemoryUnpleasure = 0.0;
       double rMemoryLibid = 0.0;
       double rMemoryAggr = 0.0;
               
       double rAssociationWeight = 0;
       
       double rInfluenceRememberedImages = 0.75;
       
       clsEmotion oEmotionFromMemory = null;
       clsThingPresentationMesh oRI = null;
       
       // use  assoc. emotions from PI's RIs for emotion-generation        
       for (clsAssociation oPIExtAss : moPerceptions_IN.getExternalAssociatedContent()){
           if(oPIExtAss.getContentType() == eContentType.ASSOCIATIONPRI){
               if(oPIExtAss.getAssociationElementB().getContentType() == eContentType.RI) {
                   oRI = (clsThingPresentationMesh)oPIExtAss.getAssociationElementB();
                   
                   for (clsAssociation oRIAss: oRI.getExternalAssociatedContent()) {
                       if (oRIAss.getContentType() == eContentType.ASSOCIATIONEMOTION && oRIAss instanceof clsAssociationEmotion) {
                           oEmotionFromMemory = ((clsAssociationEmotion)oRIAss).getEmotion();
                           
                           //kollmann: to allow control over which memories influence the state in which do not (neccessray for reflected feelings evaluation, for example)
                           //          we only consider emotions with content type MEMORIZEDEMOTION
                           
                           if(oEmotionFromMemory.getContentType().equals(eContentType.MEMORIZEDEMOTION)) {
                               // the more similar the memorized image is, the more influence the associated emotion has on emotion-generation
                               rAssociationWeight = oPIExtAss.getMrWeight();
                               rMemoryPleasure = nonProportionalAggregation(rMemoryPleasure, rInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourcePleasure()); 
                               rMemoryUnpleasure = nonProportionalAggregation(rMemoryUnpleasure, rInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourceUnpleasure());
                               rMemoryLibid = nonProportionalAggregation(rMemoryLibid, rInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourceLibid());
                               rMemoryAggr = nonProportionalAggregation(rMemoryAggr, rInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourceAggr());
                           }
                       }
                   }
               }
           }
           
       }
       
       HashMap<String, Double> oMemoryExtractedValues = new HashMap<String, Double>();
       oMemoryExtractedValues.put("rPerceptionPleasure", rMemoryPleasure);
       oMemoryExtractedValues.put("rPerceptionUnpleasure", rMemoryUnpleasure);
       oMemoryExtractedValues.put("rPerceptionLibid", rMemoryLibid);
       oMemoryExtractedValues.put("rPerceptionAggr", rMemoryAggr);
       
       return oMemoryExtractedValues;
   }
	
//	/* (non-Javadoc)
//	 *
//	 * @author schaat
//	 * 03.07.2012, 15:48:45
//	 * 
//	 */
//	private void generateEmotion(eEmotionType poEmotionType, double prEmotionIntensity, double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr) {
//		moEmotions_OUT.add(clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, poEmotionType, prEmotionIntensity),  prSourcePleasure,  prSourceUnpleasure,  prSourceLibid,  prSourceAggr));
//	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_21(moEmotions_OUT, moWordingToContext);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "F63: F63 generate Emotions based on  Pleasure and  Unpleasure from the drive- and perception track. ";
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:23:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(pa._v38.memorymgmt.datatypes.clsThingPresentationMesh)
	 */
	@Override
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {
	    moWordingToContext = moWordingToContext2;
	    try {
			moPerceptions_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:23:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_receive#receive_I5_3(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
    @Override
	public void receive_I5_3(
			ArrayList<clsDriveMesh> poDrives) {
		moDrives_IN = (ArrayList<clsDriveMesh>) deepCopy(poDrives); 
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:33:59
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_send#send_I5_21(java.util.ArrayList)
	 */
	@Override
	public void send_I5_21(ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
		((I5_21_receive)moModuleList.get(55)).receive_I5_21(poEmotions, moWordingToContext2);
		
	}

	
	/*************************************************************/
	/***                   TIME CHART METHODS                  ***/
	/*************************************************************/
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChart#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		
		oValues.add(oDrivesExtractedValues.get("rDrivePleasure"));
		oValues.add(oDrivesExtractedValues.get("rDriveUnpleasure"));
		
		
		oValues.add(oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
		
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChart#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		return new ArrayList<String>(Arrays.asList("rDrivePleasure","rDriveUnpleasure", "rPerceptionUnpleasure"));
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Composition of Emotions";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 20;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.5;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartAxis()
	 */
	@Override
	public String getCombinedTimeChartAxis() {
		return "";
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getCombinedTimeChartData() {
	    ArrayList<clsEmotion> oEmotions = moEmotions_OUT.get(0).generateExtendedEmotions();
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		
		//EMOTIONS
		ArrayList<Double> oAnger =new ArrayList<Double>();
		Double oAngerQoA= 0.0;
		for(int i=0; i<oEmotions.size();i++){
			if(oEmotions.get(i).getContent().equals(eEmotionType.ANGER)){
				oAngerQoA = oEmotions.get(i).getEmotionIntensity();

			}
		}
		oAnger.add(oAngerQoA);
		oResult.add(oAnger);
		
		
		ArrayList<Double> oFear =new ArrayList<Double>();
		Double oFearQoA= 0.0;
		for(int i=0; i<oEmotions.size();i++){
			if(oEmotions.get(i).getContent().equals(eEmotionType.ANXIETY)){
				oFearQoA = oEmotions.get(i).getEmotionIntensity();

			}
		}
		oFear.add(oFearQoA);
		oResult.add(oFear);
		
		ArrayList<Double> oGrief =new ArrayList<Double>();
		Double oGriefQoA= 0.0;
		for(int i=0; i<oEmotions.size();i++){
			if(oEmotions.get(i).getContent().equals(eEmotionType.MOURNING)){
				oGriefQoA = oEmotions.get(i).getEmotionIntensity();

			}
		}
		oGrief.add(oGriefQoA);
		oResult.add(oGrief);
		
		ArrayList<Double> oLoveSa =new ArrayList<Double>();
		Double oLoveSaQoA= 0.0;
		for(int i=0; i<oEmotions.size();i++){
			if(oEmotions.get(i).getContent().equals(eEmotionType.SATURATION)){
				oLoveSaQoA = oEmotions.get(i).getEmotionIntensity();

			}
		}
		oLoveSa.add(oLoveSaQoA);
		oResult.add(oLoveSa);
		
		ArrayList<Double> oLoveEx =new ArrayList<Double>();
		Double oLoveExQoA= 0.0;
		for(int i=0; i<oEmotions.size();i++){
			if(oEmotions.get(i).getContent().equals(eEmotionType.ELATION)){
				oLoveExQoA = oEmotions.get(i).getEmotionIntensity();

			}
		}
		oLoveEx.add(oLoveExQoA);
		oResult.add(oLoveEx);
		
		ArrayList<Double> oPleasure =new ArrayList<Double>();
		Double oPleasureQoA= 0.0;
		for(int i=0; i<oEmotions.size();i++){
			if(oEmotions.get(i).getContent().equals(eEmotionType.JOY)){
				oPleasureQoA = oEmotions.get(i).getEmotionIntensity();

			}
		}
		oPleasure.add(oPleasureQoA);
		oResult.add(oPleasure);
		
		//Chart Drive
		ArrayList<Double> oDrive =new ArrayList<Double>();
		oDrive.add(oDrivesExtractedValues.get("rDriveAggr"));
		oDrive.add(oDrivesExtractedValues.get("rDriveLibid"));
		oDrive.add(oDrivesExtractedValues.get("rDriveUnpleasure"));
		oDrive.add(oDrivesExtractedValues.get("rDrivePleasure"));
		oResult.add(oDrive);
		
			
		//Chart Perception
		ArrayList<Double> oPerception =new ArrayList<Double>();
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionAggr"));
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionLibid"));
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionPleasure"));
		oResult.add(oPerception);

        //Chart Memory
        ArrayList<Double> oMemory =new ArrayList<Double>();
        oMemory.add(oMemoryExtractedValues.get("rPerceptionAggr"));
        oMemory.add(oMemoryExtractedValues.get("rPerceptionLibid"));
        oMemory.add(oMemoryExtractedValues.get("rPerceptionUnpleasure"));
        oMemory.add(oMemoryExtractedValues.get("rPerceptionPleasure"));
        oResult.add(oMemory);
		
		return oResult;
		}


	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getChartTitles()
	 */
	@Override
	public ArrayList<String> getChartTitles() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add(eEmotionType.ANGER.toString());
		oResult.add(eEmotionType.ANXIETY.toString());
		oResult.add(eEmotionType.MOURNING.toString());
		
		oResult.add(eEmotionType.SATURATION.toString());
		oResult.add(eEmotionType.ELATION.toString());
		oResult.add(eEmotionType.JOY.toString());
		
		
		oResult.add("DRIVES");
		oResult.add("PERCEPTION");
		oResult.add("MEMORY");
        
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getValueCaptions()
	 */
	@Override
	public ArrayList<ArrayList<String>> getValueCaptions() {
		ArrayList<ArrayList<String>> oResult = new ArrayList<ArrayList<String>>();
		
		//Emotions
		
		//ChartAnger
		ArrayList<String> chartAnger = new ArrayList<String>();
		chartAnger.add("Emotion "+eEmotionType.ANGER.toString());
		oResult.add(chartAnger);
		
		//ChartFear
		ArrayList<String> chartFear = new ArrayList<String>();
		chartFear.add("Emotion "+eEmotionType.ANXIETY.toString());
		oResult.add(chartFear);
		
		//ChartGrief
		ArrayList<String> chartGrief = new ArrayList<String>();
		chartGrief.add("Emotion "+eEmotionType.MOURNING.toString());
		oResult.add(chartGrief);	
		
		//ChartLoveSaturation
		ArrayList<String> chartLoveSaturation = new ArrayList<String>();
		chartLoveSaturation.add("Emotion "+eEmotionType.SATURATION.toString());
		oResult.add(chartLoveSaturation);
		
		//ChartLoveexhilaration
		ArrayList<String> chartLoveExhilaration = new ArrayList<String>();
		chartLoveExhilaration.add("Emotion "+eEmotionType.ELATION.toString());
		oResult.add(chartLoveExhilaration);	
		
		//ChartPleasure
		ArrayList<String> chartPleasure= new ArrayList<String>();
		chartPleasure.add("Emotion "+eEmotionType.JOY.toString());
		oResult.add(chartPleasure);	
		
		
		//ChartDrive
		ArrayList<String> chartDrive = new ArrayList<String>();
		chartDrive.add("Aggr");
		chartDrive.add("Libid");
		chartDrive.add("Unpleasure");
		chartDrive.add("Pleasure");
		oResult.add(chartDrive);
		
		
		//ChartPerception
		ArrayList<String> chartPerception = new ArrayList<String>();
		chartPerception.add("Aggr");
		chartPerception.add("Libid");
		chartPerception.add("Unpleasure");
		chartPerception.add("Pleasure");
		oResult.add(chartPerception);
		
		//ChartPerception
        ArrayList<String> chartMemory = new ArrayList<String>();
        chartMemory.add("Aggr");
        chartMemory.add("Libid");
        chartMemory.add("Unpleasure");
        chartMemory.add("Pleasure");
        oResult.add(chartMemory);
        
		return oResult;
	}	
    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }

	


}
