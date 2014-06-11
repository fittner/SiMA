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
import java.util.Random;
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
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import du.enums.pa.eDriveComponent;

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

	// values from drive-track . to get a better output in the inspectors a hashmap is used instead of separate variables
	HashMap<String, Double> oDrivesExtractedValues = new HashMap<String, Double>();

	// personality parameter, perceiving a drive object sould trigger less emotions than the bodily needs
	private double mrPerceptionPleasureImpactFactor;
	private double mrPerceptionUnpleasureImpactFactor;
	
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
		
		clsThingPresentationMesh x = moPerceptions_IN;
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
		
		double rRelativeSystemPleasure = 0.0; 
		double rRelativeSystemUnpleasure = 0.0;
		
		double rRelativeSystemLibid = 0.0;
		double rRelativeSystemAggr = 0.0;

		
		
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
		 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emtion is based on) to influence the emotion generation in F63
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
		
		
		// Normalize to be able to decide which basic category prevails/dominates
		double rSumValuesPlUnPl = rSystemUnpleasure + rSystemPleasure;
		double rSumValuesLibidAggr =  rSystemAggr +rSystemLibid;		
		rRelativeSystemPleasure = rSystemPleasure/rSumValuesPlUnPl; 
		rRelativeSystemUnpleasure = rSystemUnpleasure/rSumValuesPlUnPl;
		rRelativeSystemLibid = rSystemLibid/rSumValuesLibidAggr;
		rRelativeSystemAggr = rSystemAggr/rSumValuesLibidAggr;
		

		/*
		 * Generate Emotions
		 * if unpleasure prevails --> only generate unpleasure-based  emotions (always-> anxiety. if agg prevails -> ANGER. if libid prevails -> mourning. if no one prevails -> both)
		 * if pleasure prevails --> only generate pleasure-based emotions (always->> PLEASURE. if agg prevails ->ELATION if libid prevails -> SATURATION)
		 * 
		 * the intensity of generated emotions is dependent on the relative amount of the basic category (Pleasuer, aggr, ... = from which the emotion is derived), particularly relative to the amount of pleasure+unpleasure
		 * E.g. As Grief is based on aggr. unpleasure, its intensity is derived from the amount of aggr. unpleasure relative to the total amount of the ground truth (pleasure+unpleasure) 
		 * 
		 * Aggr and libid components/categories are just another form of unpleasure. This is considered in the further
		 * procedure to avoid duplicating the ground truth(=the values emotions are based on).
		 * 
		 * variable "rGrade": To avoid "leaps" in emotion-intensity, a threshold-range is considered and a gradual change of intensity (e.g. if the domination of pleasure changes very fast (e.g. in every step), the intensity of the according emotions should not "jump" up and down   
		*/
		
		
		// just generate Unpleasure--based Emotions
		if(rRelativeSystemUnpleasure > mrRelativeThreshold){
			
			mrGrade = (rRelativeSystemUnpleasure-mrRelativeThreshold) / mrThresholdRange; 
			if(mrGrade > 1) mrGrade = 1;
					
			generateEmotion(eEmotionType.ANXIETY, rSystemUnpleasure*mrGrade, 0, rSystemUnpleasure, 0, 0);
			generateEmotion(eEmotionType.JOY, rSystemPleasure*(1-mrGrade), rSystemPleasure, 0, 0, 0);
			
			if(rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ANGER, rSystemAggr*mrGrade, 0, rSystemUnpleasure, 0, rSystemAggr);
			}
			else if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.MOURNING, rSystemLibid*mrGrade, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
			else {
				generateEmotion(eEmotionType.ANGER, rSystemAggr*mrGrade, 0, rSystemUnpleasure, 0, rSystemAggr);
				generateEmotion(eEmotionType.MOURNING,  rSystemLibid*mrGrade, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
		}
		// just generate Pleasure-based Emotions
		else if (rRelativeSystemPleasure > mrRelativeThreshold) {
			
			mrGrade = (rRelativeSystemPleasure-mrRelativeThreshold) / mrThresholdRange; 
			if(mrGrade > 1) mrGrade = 1;
			
			generateEmotion(eEmotionType.ANXIETY, rSystemUnpleasure*(1-mrGrade), 0, rSystemUnpleasure, 0, 0);
			generateEmotion(eEmotionType.JOY, rSystemPleasure*mrGrade, rSystemPleasure, 0, 0, 0);
			if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid*mrGrade, rSystemPleasure, 0, rSystemLibid, 0);
			}
			else if (rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ELATION, rSystemAggr*mrGrade, rSystemPleasure, 0, 0, rSystemAggr);
			}
			else {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid*mrGrade, rSystemPleasure, 0, rSystemLibid, 0);
				generateEmotion(eEmotionType.ELATION, rSystemAggr*mrGrade, rSystemPleasure, 0, 0, rSystemAggr);
			} 
		}
		// generate both
		else {
			// pleasure-based emotions		    
		    mrGrade = (mrRelativeThreshold - rRelativeSystemPleasure) / mrThresholdRange; 
            if(mrGrade > 1) mrGrade = 1;
            		    
			generateEmotion(eEmotionType.JOY, rSystemPleasure*mrGrade, rSystemPleasure, 0, 0, 0);
			if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid*mrGrade, rSystemPleasure, 0, rSystemLibid, 0);
			}
			else if (rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ELATION, rSystemAggr*mrGrade, rSystemPleasure, 0, 0, rSystemAggr);
			}
			else {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid*mrGrade, rSystemPleasure, 0, rSystemLibid, 0);
				generateEmotion(eEmotionType.ELATION, rSystemAggr*mrGrade, rSystemPleasure, 0, 0, rSystemAggr);
			}
			
			//unpleasure-based emotions			
			mrGrade = (mrRelativeThreshold - rRelativeSystemUnpleasure) / mrThresholdRange; 
            if(mrGrade > 1) mrGrade = 1;
            
			generateEmotion(eEmotionType.ANXIETY, rSystemUnpleasure*mrGrade, 0, rSystemUnpleasure, 0, 0);
			if(rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ANGER, rSystemAggr*mrGrade, 0, rSystemUnpleasure, 0, rSystemAggr);
			}
			else if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.MOURNING, rSystemLibid*mrGrade, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
			else {
				generateEmotion(eEmotionType.ANGER, rSystemAggr*mrGrade, 0, rSystemUnpleasure, 0, rSystemAggr);
				generateEmotion(eEmotionType.MOURNING,  rSystemLibid*mrGrade, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
		}
		
	    Random randomGenerator = new Random();
          
	    double rRequestedPsychicIntensity = randomGenerator.nextFloat();
	                
	    double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	            
	    double rConsumedPsychicIntensity = rReceivedPsychicEnergy*(randomGenerator.nextFloat());
	            
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
		return rBaseValue + (1 - rBaseValue) * rAddValue;
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
				
		double rMaxQoAPerception = 1;
		double rMaxQoAPerceptionAggr = 1;
		double rMaxQoAPerceptionLibid = 1;
		
		double rInfluencePerception = 0;
		double rAssociationWeight = 0;
		
		clsEmotion oEmotionFromPerception = null;
		clsDriveMesh oDM = null;
		clsThingPresentationMesh oRI = null;
		
		// use  assoc. emotions from PI's RIs for emotion-generation		
		for (clsAssociation oPIExtAss : moPerceptions_IN.getExternalAssociatedContent()){
			
			if(oPIExtAss.getContentType() == eContentType.ASSOCIATIONPRI){
				if(oPIExtAss.getAssociationElementB().getContentType() == eContentType.RI) {
					oRI = (clsThingPresentationMesh)oPIExtAss.getAssociationElementB();
					
					for (clsAssociation oRIAss: oRI.getExternalAssociatedContent()) {
						if (oRIAss.getContentType() == eContentType.ASSOCIATIONEMOTION) {
							oEmotionFromPerception = (clsEmotion) oRIAss.getAssociationElementA();
							// the more similar the memorized image is, the more influence the associated emotion has on emotion-generation
							rAssociationWeight = oPIExtAss.getMrWeight();
							rPerceptionPleasure = nonProportionalAggregation(rPerceptionPleasure, mrPerceptionPleasureImpactFactor*rAssociationWeight*oEmotionFromPerception.getSourcePleasure()); 
							rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, mrPerceptionUnpleasureImpactFactor*rAssociationWeight*oEmotionFromPerception.getSourceUnpleasure());
							rPerceptionLibid = nonProportionalAggregation(rPerceptionLibid, mrPerceptionUnpleasureImpactFactor*rAssociationWeight*oEmotionFromPerception.getSourceLibid());
							rPerceptionAggr = nonProportionalAggregation(rPerceptionAggr, mrPerceptionUnpleasureImpactFactor*rAssociationWeight*oEmotionFromPerception.getSourceAggr());
				
						}
					}
				}
			}
			
		}
		
		
		// use QoA of the PI's entities  for emotion-generation
		for(clsAssociation oPIINtAss: moPerceptions_IN.getInternalAssociatedContent()) {
			if(oPIINtAss.getContentType() == eContentType.PARTOFASSOCIATION){
				
				for (clsAssociation oEntityAss: ((clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getExternalAssociatedContent()) {
					// exclude empty spaces (they are currently associated with drives). just use entities. (this is not nice, but due to the use of emptySpaces-objekte in ars necessary)
					if ( oEntityAss.getContentType() == eContentType.ASSOCIATIONDM && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")   ) {
																		
						oDM = (clsDriveMesh)oEntityAss.getAssociationElementA();
						
						if(oDM.getContentType() == eContentType.LIBIDO) {
							rPerceptionPleasure += mrPerceptionPleasureImpactFactor*oDM.getQuotaOfAffect();
							
						}
						else {
							
							// influence of perceived objects is dependent on agent's actual drives. e.g. if the agent is hungry and it perceives a schnitzel, 
							// the perception influences emotion-generation more than in the case of an agent without hunger  
							for (clsDriveMesh oActualDM: moDrives_IN) {
								if (oDM.compareTo(oActualDM) >= 0.75) {
									rInfluencePerception = oDM.getQuotaOfAffect()/oActualDM.getQuotaOfAffect();
									if(rInfluencePerception>1) rInfluencePerception = 1;
								}
							}
							
						    //rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, oDM.getQuotaOfAffect());
							if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
								rPerceptionLibid = nonProportionalAggregation(rPerceptionLibid, mrPerceptionUnpleasureImpactFactor*rInfluencePerception*oDM.getQuotaOfAffect());
							} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
								rPerceptionAggr = nonProportionalAggregation(rPerceptionAggr, mrPerceptionUnpleasureImpactFactor*rInfluencePerception*oDM.getQuotaOfAffect());
							}
							
							rPerceptionPleasure = nonProportionalAggregation(rPerceptionPleasure, mrPerceptionPleasureImpactFactor*rInfluencePerception*oDM.getQuotaOfAffect());
						}
						
					}
				}
			}		
			
			
	}
		
        rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, rPerceptionLibid+rPerceptionAggr);
		
		 
		HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();
		oPerceptionExtractedValues.put("rPerceptionPleasure", rPerceptionPleasure);
//		oPerceptionExtractedValues.put("rPerceptionUnpleasure", (rPerceptionLibid+rPerceptionAggr));
		oPerceptionExtractedValues.put("rPerceptionUnpleasure", rPerceptionUnpleasure);
		oPerceptionExtractedValues.put("rPerceptionLibid", rPerceptionLibid);
		oPerceptionExtractedValues.put("rPerceptionAggr", rPerceptionAggr);
		
		return oPerceptionExtractedValues;
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 03.07.2012, 15:48:45
	 * 
	 */
	private void generateEmotion(eEmotionType poEmotionType, double prEmotionIntensity, double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr) {
		moEmotions_OUT.add(clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, poEmotionType, prEmotionIntensity),  prSourcePleasure,  prSourceUnpleasure,  prSourceLibid,  prSourceAggr));
	}
	
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
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		//EMOTIONS
		ArrayList<Double> oAnger =new ArrayList<Double>();
		Double oAngerQoA= 0.0;
		for(int i=0; i<moEmotions_OUT.size();i++){
			if(moEmotions_OUT.get(i).getContent().equals(eEmotionType.ANGER)){
				oAngerQoA = moEmotions_OUT.get(i).getEmotionIntensity();

			}
		}
		oAnger.add(oAngerQoA);
		oResult.add(oAnger);
		
		
		ArrayList<Double> oFear =new ArrayList<Double>();
		Double oFearQoA= 0.0;
		for(int i=0; i<moEmotions_OUT.size();i++){
			if(moEmotions_OUT.get(i).getContent().equals(eEmotionType.ANXIETY)){
				oFearQoA = moEmotions_OUT.get(i).getEmotionIntensity();

			}
		}
		oFear.add(oFearQoA);
		oResult.add(oFear);
		
		ArrayList<Double> oGrief =new ArrayList<Double>();
		Double oGriefQoA= 0.0;
		for(int i=0; i<moEmotions_OUT.size();i++){
			if(moEmotions_OUT.get(i).getContent().equals(eEmotionType.MOURNING)){
				oGriefQoA = moEmotions_OUT.get(i).getEmotionIntensity();

			}
		}
		oGrief.add(oGriefQoA);
		oResult.add(oGrief);
		
		ArrayList<Double> oLoveSa =new ArrayList<Double>();
		Double oLoveSaQoA= 0.0;
		for(int i=0; i<moEmotions_OUT.size();i++){
			if(moEmotions_OUT.get(i).getContent().equals(eEmotionType.SATURATION)){
				oLoveSaQoA = moEmotions_OUT.get(i).getEmotionIntensity();

			}
		}
		oLoveSa.add(oLoveSaQoA);
		oResult.add(oLoveSa);
		
		ArrayList<Double> oLoveEx =new ArrayList<Double>();
		Double oLoveExQoA= 0.0;
		for(int i=0; i<moEmotions_OUT.size();i++){
			if(moEmotions_OUT.get(i).getContent().equals(eEmotionType.ELATION)){
				oLoveExQoA = moEmotions_OUT.get(i).getEmotionIntensity();

			}
		}
		oLoveEx.add(oLoveExQoA);
		oResult.add(oLoveEx);
		
		ArrayList<Double> oPleasure =new ArrayList<Double>();
		Double oPleasureQoA= 0.0;
		for(int i=0; i<moEmotions_OUT.size();i++){
			if(moEmotions_OUT.get(i).getContent().equals(eEmotionType.JOY)){
				oPleasureQoA = moEmotions_OUT.get(i).getEmotionIntensity();

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
