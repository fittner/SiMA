/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author schaat
 * 07.06.2012, 15:47:11
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.modules.eImplementationStage;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_21_send;
import pa._v38.interfaces.modules.I5_21_receive;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.storage.DT4_PleasureStorage;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;
import du.enums.pa.eDriveComponent;

/**
 * (see document "Compositions of Emotions")
 * F63 generate Emotions based on  Pleasure and  Unpleasure. Four basic emotions exists: ANGER, FEAR, GRIEF and LOVE. They are not mutual exclusive. 
 * Dependent on the dominance of the basic categories Pleasure, Unpleasure and the aggressive and libidinous drive parts, the corresponding emotions are generated.
 * Dominance of Unpleasure +  Dominance of aggr. Drivecomponents --> ANGER
 * Dominance of Unpleasure +  Dominance of libid. Drivecomponents --> GRIEF
 * Dominance of Unpleasure --> FEAR
 * Dominance of Pleasure +  Dominance of libid. Drivecomponents --> LOVE
 * 
 * 
 * @author schaat
 * 07.06.2012, 15:47:11
 */
public class F63_CompositionOfEmotions extends clsModuleBase 
					implements  itfInspectorGenericTimeChart, I5_3_receive, I5_10_receive, I5_21_send {

	//Statics for the module
	public static final String P_MODULENUMBER = "63";

	//Private members for send and recieve
	private ArrayList<clsEmotion> moEmotions_OUT; 
	private ArrayList<clsDriveMesh> moDrives_IN;
	private clsThingPresentationMesh moPerceptions_IN;
	
	
	// threshold to determine in which case domination of a emotion occurs
	private double mrRelativeThreshold = 0.667;
	
	DT4_PleasureStorage moPleasureStorage;
	
	// values from perception-track (triggered emotions). to get a better output in the inspectors and a capsulated function-call a hashmap is used instead of separate variables
	HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();

	// values from drive-track . to get a better output in the inspectors a hashmap is used instead of separate variables
	HashMap<String, Double> oDrivesExtractedValues = new HashMap<String, Double>();

	// perceiving a drive object sould trigger less emotions than the bodily needs
	private double mrPerceptionImpactFactor = 0.4;
	
	
	public F63_CompositionOfEmotions(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp);	
		
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
		
		text += toText.listToTEXT("moEmotions_OUT", moEmotions_OUT);			
		text += toText.valueToTEXT("rDriveUnpleasure", oDrivesExtractedValues.get("rDriveUnpleasure"));

		text += toText.valueToTEXT("rDriveLibid", oDrivesExtractedValues.get("rDriveLibid"));
		text += toText.valueToTEXT("rDriveAggr", oDrivesExtractedValues.get("rDriveAggr"));
		
		text += toText.valueToTEXT("rDrivePleasure",  oDrivesExtractedValues.get("rDrivePleasure"));
		
		
		text += toText.valueToTEXT("rPerceptionUnpleasure", oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
		
		text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getExternalMoAssociatedContent());
		
				
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
		
		// rSystemXY = values from whole system (ARSIN), drives = values from drive track, perception = values from perception track

		// four basic categories ("Grundkategorien", see document "Compositions of Emotions")
		double rSystemPleasure = 0.0; 
		double rSystemUnpleasure = 0.0;
		double rSystemLibid = 0.0;
		double rSystemAggr = 0.0;
		
		// values from drive-track
		double rDrivePleasure = 0.0; 
		double rDriveUnpleasure = 0.0;
		double rDriveLibid = 0.0;
		double rDriveAggr = 0.0;
		
			
		double rRelativeSystemPleasure = 0.0; 
		double rRelativeSystemUnpleasure = 0.0;
		double rRelativeSystemLibid = 0.0;
		double rRelativeSystemAggr = 0.0;
		
		// since a drive may have a QoA between 0 and 1, the number of drives reflect the maximal score of aggregated driveQoA
		// this value is needed to calculate the emotion's intensity (which is relative to the (global) maximal possible score of emotion-intensity)
		double rMaxQoADrives = moDrives_IN.size();
		double rMaxQoADrivesAggr = 0;
		double rMaxQoADrivesLibid = 0;
		
		double rMaxQoASystem = 0;
		double rMaxQoASystemAggr = 0;
		double rMaxQoASystemLibid = 0;
		
		//1. Get Unpleasure from all drives, and the aggr. and libid parts
		for (clsDriveMesh oDM: moDrives_IN) {
			rDriveUnpleasure += oDM.getQuotaOfAffect();
			if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
				rDriveLibid += oDM.getQuotaOfAffect();
				rMaxQoADrivesLibid++;
			} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
				rDriveAggr += oDM.getQuotaOfAffect();
				rMaxQoADrivesAggr++;
			}
		}
		
		//get Pleasure
		rDrivePleasure =  moPleasureStorage.send_D4_1();
		
		// TEMPORARY
		oDrivesExtractedValues.put("rDrivePleasure", rDrivePleasure);
		oDrivesExtractedValues.put("rDriveUnpleasure", rDriveUnpleasure);
		oDrivesExtractedValues.put("rDriveLibid", rDriveLibid);
		oDrivesExtractedValues.put("rDriveAggr", rDriveAggr);
		oDrivesExtractedValues.put("rMaxQoADrives", rMaxQoADrives);
		oDrivesExtractedValues.put("rMaxQoADrivesAggr", rMaxQoADrivesAggr);
		oDrivesExtractedValues.put("rMaxQoADrivesLibid", rMaxQoADrivesLibid);
		
		/* emotions triggered by perception (from memory) influence emotion-generation
		 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emtion is based on) to influence the emotion generation in F63
		 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
		 * 
		 */
		
		oPerceptionExtractedValues = getEmotionValuesFromPerception();
				
		
		// aggregate values from drive- and perception track
		// TODO: problem: values from perception are much higher than values from drive-track. --> impact of perception on the generation of emotion is relatively high (relative to impact of drive-track)
		// normalize grundkategorien
		// or is  it a problem? (if agent sees many objects the perception has more influence, otherwise drives have moire influence on emotions)
		rSystemUnpleasure = rDriveUnpleasure + oPerceptionExtractedValues.get("rPerceptionUnpleasure");
		rSystemPleasure = rDrivePleasure + oPerceptionExtractedValues.get("rPerceptionPleasure");
		rSystemLibid = rDriveLibid +oPerceptionExtractedValues.get("rPerceptionLibid");
		rSystemAggr = rDriveAggr + oPerceptionExtractedValues.get("rPerceptionAggr");
		
		rMaxQoASystem = rMaxQoADrives + oPerceptionExtractedValues.get("rMaxQoAPerception");
		rMaxQoASystemLibid = rMaxQoADrivesLibid + oPerceptionExtractedValues.get("rMaxQoAPerceptionLibid");
		rMaxQoASystemAggr = rMaxQoADrivesAggr + oPerceptionExtractedValues.get("rMaxQoAPerceptionAggr");
		
		// Normalize to be able to decide which basic category prevails/dominates
		double orSumValuesPlUnPl = rSystemUnpleasure + rSystemPleasure;
		double orSumValuesLibidAggr =  rSystemAggr +rSystemLibid;		
		rRelativeSystemPleasure = rSystemPleasure/orSumValuesPlUnPl; 
		rRelativeSystemUnpleasure = rSystemUnpleasure/orSumValuesPlUnPl;
		rRelativeSystemLibid = rSystemLibid/orSumValuesLibidAggr;
		rRelativeSystemAggr = rSystemAggr/orSumValuesLibidAggr;

		/*
		 * Generate Emotions
		 * if unpleasure prevails --> only generate unpleasure-based  emotions (always-> FEAR. if agg prevails -> ANGER. if libid prevails -> GRIEF. if no one prevails -> both)
		 * if pleasure prevails --> only generate pleasure-based emotions (always->> PLEASURE. if agg prevails ->ELATION if libid prevails -> SATURATION)
		 * 
		 * the intensity of generated emotions is dependent on the relative amount of the basic category (Pleasuer, aggr, ... = from which the emotion is derived), particularly relative to the amount of pleasure+unpleasure
		 * E.g. As Grief is based on aggr. unpleasure, its intensity is derived from the amount of aggr. unpleasure relative to the total amount of the ground truth (pleasure+unpleasure) 
		 * 
		 * Aggr and libid components/categories are just another form of unpleasure. This is considered in the further
		 * procedure to avoid duplicating the ground truth(=the values emotions are based on).
		*/
		
		// just generate Unpleasure--based Emotions
		if(rRelativeSystemUnpleasure > mrRelativeThreshold){			
			generateEmotion(eEmotionType.FEAR, rSystemUnpleasure/rMaxQoASystem, 0, rSystemUnpleasure, 0, 0);
			if(rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ANGER, rSystemAggr/rMaxQoASystemAggr, 0, rSystemUnpleasure, 0, rSystemAggr);
			}
			else if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.GRIEF, rSystemLibid/rMaxQoASystemLibid, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
			else {
				generateEmotion(eEmotionType.ANGER, rSystemAggr/rMaxQoASystemAggr, 0, rSystemUnpleasure, 0, rSystemAggr);
				generateEmotion(eEmotionType.GRIEF,  rSystemLibid/rMaxQoASystemLibid, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
		}
		// just generate Pleasure-based Emotions
		else if (rRelativeSystemPleasure > mrRelativeThreshold) {
			generateEmotion(eEmotionType.PLEASURE, rSystemPleasure/rMaxQoASystem, rSystemPleasure, 0, 0, 0);
			if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid/rMaxQoASystemLibid, rSystemPleasure, 0, rSystemLibid, 0);
			}
			else if (rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ELATION, rSystemAggr/rMaxQoASystemAggr, rSystemPleasure, 0, 0, rSystemAggr);
			}
			else {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid/rMaxQoASystemLibid, rSystemPleasure, 0, rSystemLibid, 0);
				generateEmotion(eEmotionType.ELATION, rSystemAggr/rMaxQoASystemAggr, rSystemPleasure, 0, 0, rSystemAggr);
			} 
		}
		// generate both
		else {
			
			// pleasure-based emotions
			generateEmotion(eEmotionType.PLEASURE, rSystemPleasure/rMaxQoASystem, rSystemPleasure, 0, 0, 0);
			if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid/rMaxQoASystemLibid, rSystemPleasure, 0, rSystemLibid, 0);
			}
			else if (rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ELATION, rSystemAggr/rMaxQoASystemAggr, rSystemPleasure, 0, 0, rSystemAggr);
			}
			else {
				generateEmotion(eEmotionType.SATURATION,  rSystemLibid/rMaxQoASystemLibid, rSystemPleasure, 0, rSystemLibid, 0);
				generateEmotion(eEmotionType.ELATION, rSystemAggr/rMaxQoASystemAggr, rSystemPleasure, 0, 0, rSystemAggr);
			}
			
			//unpleasure-based emotions
			generateEmotion(eEmotionType.FEAR, rSystemUnpleasure/rMaxQoASystem, 0, rSystemUnpleasure, 0, 0);
			if(rRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(eEmotionType.ANGER, rSystemAggr/rMaxQoASystemAggr, 0, rSystemUnpleasure, 0, rSystemAggr);
			}
			else if (rRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(eEmotionType.GRIEF, rSystemLibid/rMaxQoASystemLibid, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
			else {
				generateEmotion(eEmotionType.ANGER, rSystemAggr/rMaxQoASystemAggr, 0, rSystemUnpleasure, 0, rSystemAggr);
				generateEmotion(eEmotionType.GRIEF,  rSystemLibid/rMaxQoASystemLibid, 0, rSystemUnpleasure, rSystemLibid, 0);
			}
		}
		
		
		
		
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
				
		double rMaxQoAPerception = 0;
		double rMaxQoAPerceptionAggr = 0;
		double rMaxQoAPerceptionLibid = 0;
		
		clsEmotion oEmotionFromPerception = null;
		clsDriveMesh oDM = null;
		clsThingPresentationMesh oRI = null;
		
		// use  assoc. emotions from PI's RIs for emotion-generation		
		for (clsAssociation oPIExtAss : moPerceptions_IN.getExternalMoAssociatedContent()){
			
			if(oPIExtAss.getMoContentType() == eContentType.PIASSOCIATION){
				if(oPIExtAss.getMoAssociationElementB().getMoContentType() == eContentType.RI) {
					oRI = (clsThingPresentationMesh)oPIExtAss.getMoAssociationElementB();
					
					for (clsAssociation oRIAss: oRI.getExternalMoAssociatedContent()) {
						if (oRIAss.getMoContentType() == eContentType.ASSOCIATIONEMOTION) {
							oEmotionFromPerception = (clsEmotion) oRIAss.getMoAssociationElementA();
							rPerceptionPleasure += oEmotionFromPerception.getMrSourcePleasure(); 
							rPerceptionUnpleasure += oEmotionFromPerception.getMrSourceUnpleasure();
							rPerceptionLibid += oEmotionFromPerception.getMrSourceLibid();
							rPerceptionAggr += oEmotionFromPerception.getMrSourceAggr();
							
							rMaxQoAPerception += rPerceptionUnpleasure;
							rMaxQoAPerceptionAggr += rPerceptionAggr;
							rMaxQoAPerceptionLibid += rPerceptionLibid;
						}
					}
				}
			}
			
		}
		
		
		// use QoA of the PI's entities  for emotion-generation
		for(clsAssociation oPIINtAss: moPerceptions_IN.getMoInternalAssociatedContent()) {
			if(oPIINtAss.getMoContentType() == eContentType.PARTOFASSOCIATION){
				
				for (clsAssociation oEntityAss: ((clsThingPresentationMesh)oPIINtAss.getMoAssociationElementB()).getExternalMoAssociatedContent()) {
					// exclude empty spaces (they are currently associated with the deposit drive). just use entities
					if (((clsThingPresentationMesh)oPIINtAss.getMoAssociationElementB()).getMoContentType() == eContentType.ENTITY && oEntityAss.getMoContentType() == eContentType.ASSOCIATIONDM) {
						// TODO: what about pleasure (libidoDischarge-DM)?
												
						oDM = (clsDriveMesh)oEntityAss.getMoAssociationElementA();
					
						rPerceptionUnpleasure += oDM.getQuotaOfAffect();
						if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
							rPerceptionLibid += oDM.getQuotaOfAffect();
							rMaxQoAPerceptionLibid++;
						} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
							rPerceptionAggr += oDM.getQuotaOfAffect();
							rMaxQoAPerceptionAggr++;
						}
						rMaxQoAPerception++;
						
						// TODO: A cake is associated with multiple DMs of the same kind. this should not be the case. delete "break", after this problem is solved
						break;
					}
				}
			}
			
			
			
	}
		
		//// TEMPORARY
		// TODO perception trigger to much emotion
		HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();
		oPerceptionExtractedValues.put("rPerceptionPleasure", mrPerceptionImpactFactor*rPerceptionPleasure);
		oPerceptionExtractedValues.put("rPerceptionUnpleasure", mrPerceptionImpactFactor*rPerceptionUnpleasure);
		oPerceptionExtractedValues.put("rPerceptionLibid", mrPerceptionImpactFactor*rPerceptionLibid);
		oPerceptionExtractedValues.put("rPerceptionAggr", mrPerceptionImpactFactor*rPerceptionAggr);
		oPerceptionExtractedValues.put("rMaxQoAPerception", mrPerceptionImpactFactor*rMaxQoAPerception);
		oPerceptionExtractedValues.put("rMaxQoAPerceptionAggr", mrPerceptionImpactFactor*rMaxQoAPerceptionAggr);
		oPerceptionExtractedValues.put("rMaxQoAPerceptionLibid", mrPerceptionImpactFactor*rMaxQoAPerceptionLibid);
		
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
		send_I5_21(moEmotions_OUT);
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
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh) {
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
	public void send_I5_21(ArrayList<clsEmotion> poEmotions) {
		((I5_21_receive)moModuleList.get(55)).receive_I5_21(poEmotions);
		
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

	


}
