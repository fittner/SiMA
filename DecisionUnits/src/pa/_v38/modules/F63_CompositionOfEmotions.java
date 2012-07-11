/**
 * F48_AccumulationOfAffectsForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author muchitsch
 * 02.05.2011, 15:47:11
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.modules.eImplementationStage;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_21_send;
import pa._v38.interfaces.modules.I5_21_receive;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationEmotion;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.clsEmotionType;
import pa._v38.storage.DT4_PleasureStorage;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * (see document "Compositions of Emotions")
 * F63 generate Emotions based on  Pleasure and  Unpleasure. Four basic emotions exists: ANGER, FEAR, GRIEF and LOVE. They are not mutual exclusive. 
 * Dependent on the dominance of the basic categories Pleasure, Unpleasure and its aggressive and libidinous parts, the corresponding emotions are generated.
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
					implements I5_3_receive, I5_10_receive, I5_21_send {

	//Statics for the module
	public static final String P_MODULENUMBER = "63";

	//Private members for send and recieve
	private ArrayList<clsEmotion> moEmotions_OUT; 
	private ArrayList<clsDriveMesh> moDrives_IN;
	private clsThingPresentationMesh moPerceptions_IN;
	
	 // four basic categories ("Grundkategorien", see document "Compositions of Emotions")
	private double mrSystemPleasure = 0.0; 
	private double mrSystemUnpleasure = 0.0;
	private double mrSystemLibid = 0.0;
	private double mrSystemAggr = 0.0;
	
	// values from drive-track
	private double mrDrivePleasure = 0.0; 
	private double mrDriveUnpleasure = 0.0;
	private double mrDriveLibid = 0.0;
	private double mrDriveAggr = 0.0;
	
	// values from perception-track (triggered emotions)
	private double mrPerceptionPleasure = 0.0; 
	private double mrPerceptionUnpleasure = 0.0;
	private double mrPerceptionLibid = 0.0;
	private double mrPerceptionAggr = 0.0;
	
	private double mrRelativeSystemPleasure = 0.0; 
	private double mrRelativeSystemUnpleasure = 0.0;
	private double mrRelativeSystemLibid = 0.0;
	private double mrRelativeSystemAggr = 0.0;
	
	private double mrMaxValue = 0.0;
	private double mrRelativeThreshold = 0.75;
	
	DT4_PleasureStorage moPleasureStorage = null;
	

	public F63_CompositionOfEmotions(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp);	
		
		DT4_PleasureStorage moPleasureStorage = poPleasureStorage;
		
		moEmotions_OUT = new ArrayList<clsEmotion>();
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
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moEmotions_OUT", moEmotions_OUT);	
				
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//1. Get Unpleasure from all drives, and its aggr. and libid parts
		for (clsDriveMesh oDM: moDrives_IN) {
			mrDriveUnpleasure += oDM.getMrQuotaOfAffect();
			if(oDM.getMoContentType().equals("LIFE")) {
				mrDriveLibid += oDM.getMrQuotaOfAffect();
			} else if (oDM.getMoContentType().equals("DEATH")){
				mrDriveAggr += oDM.getMrQuotaOfAffect();
			}			
		}
		
		mrDrivePleasure = 0.7; // TODO: Wenn F48 Lust in DT4 speichert: moPleasureStorage.send_D4_1();
		
		
		/* emotions triggered by perception (from memory) influence emotion-generation
		 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emtion is based on) to influence the emotion generation in F63
		 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
		 * 
		 */
		
		getEmotionValuesFromPerception();
				
		
		//double[] rAllValues={mrSystemUnpleasure,mrSystemUnpleasure,mrSystemAggrUnpleasure, mrSystemLibidUnpleasure};
		
		//mrMaxValue = getMaxValue(rAllValues);

		// aggregate values from drive- and perception track
		mrSystemUnpleasure = mrDriveUnpleasure + mrPerceptionUnpleasure;
		mrSystemPleasure = mrDrivePleasure + mrPerceptionPleasure;
		mrSystemLibid = mrDriveLibid +mrPerceptionLibid;
		mrSystemAggr = mrDriveAggr + mrPerceptionAggr;
		
		// Normalize to be able to decide which basic category prevails/dominates
		double orSumValuesPlUnPl = mrSystemUnpleasure + mrSystemPleasure;
		double orSumValuesLibidAggr =  mrSystemAggr +mrSystemLibid;		
		mrRelativeSystemPleasure = mrSystemPleasure/orSumValuesPlUnPl; 
		mrRelativeSystemUnpleasure = mrSystemUnpleasure/orSumValuesPlUnPl;
		mrRelativeSystemLibid = mrSystemAggr/orSumValuesLibidAggr;
		mrRelativeSystemAggr = mrSystemLibid/orSumValuesLibidAggr;

		/*
		 * if unpleasure prevails --> only generate unpleasure emotions (always-> FEAR. if agg prevails -> ANGER. if libid prevails -> GRIEF. if no one prevails -> both)
		 * if pleasure prevails --> only generate pleasure-emotions (if libid prevails -> LOVE)
		 * 
		 * the intensity of generated emotions is dependent on the relative amount of the basic category (Pleasuer, aggr, ... = from which the emotion is derived), particularly relative to the amount of pleasure+unpleasure
		 * E.g. As Grief is based on aggr. unpleasure, its intensity is derived from the amount of aggr. unpleasure relative to the total amount of the ground truth (pleasure+unpleasure) 
		 * 
		 * Aggr and libid components/categories are just another form of unpleasure. This is considered in the further
		 * procedure to avoid duplicating the ground truth (the values emotions are based on).
		*/
		
		// just generate Unpleasure-Emotions
		if(mrRelativeSystemUnpleasure > mrRelativeThreshold){			
			generateEmotion(clsEmotionType.FEAR, mrRelativeSystemUnpleasure, 0, mrSystemUnpleasure, 0, 0);
			if(mrRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(clsEmotionType.ANGER, mrSystemAggr/orSumValuesPlUnPl, 0, mrSystemUnpleasure, 0, mrSystemAggr);
			}
			else if (mrRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(clsEmotionType.GRIEF, mrSystemLibid/orSumValuesPlUnPl, 0, mrSystemUnpleasure, mrSystemLibid, 0);
			}
			else {
				generateEmotion(clsEmotionType.ANGER, mrSystemAggr/orSumValuesPlUnPl, 0, mrSystemUnpleasure, 0, mrSystemAggr);
				generateEmotion(clsEmotionType.GRIEF,  mrSystemLibid/orSumValuesPlUnPl, 0, mrSystemUnpleasure, mrSystemLibid, 0);
			}
		}
		// just generate Pleasure-Emotions
		else if (mrRelativeSystemUnpleasure > mrRelativeThreshold) {
			if (mrRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(clsEmotionType.LOVE,  mrRelativeSystemPleasure, mrSystemPleasure, 0, mrSystemLibid, 0);
			}
			// else NO EMOTIONS??? 
		}
		// generate both
		else {
			if (mrRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(clsEmotionType.LOVE,  mrRelativeSystemPleasure, mrSystemPleasure, 0, mrSystemLibid, 0);
			}
			generateEmotion(clsEmotionType.FEAR, mrRelativeSystemUnpleasure, 0, mrSystemUnpleasure, 0, 0);
			if(mrRelativeSystemAggr > mrRelativeThreshold) {
				generateEmotion(clsEmotionType.ANGER, mrSystemAggr/orSumValuesPlUnPl, 0, mrSystemUnpleasure, 0, mrSystemAggr);
			}
			else if (mrRelativeSystemLibid > mrRelativeThreshold) {
				generateEmotion(clsEmotionType.GRIEF, mrSystemLibid/orSumValuesPlUnPl, 0, mrSystemUnpleasure, mrSystemLibid, 0);
			}
			else {
				generateEmotion(clsEmotionType.ANGER, mrSystemAggr/orSumValuesPlUnPl, 0, mrSystemUnpleasure, 0, mrSystemAggr);
				generateEmotion(clsEmotionType.GRIEF,  mrSystemLibid/orSumValuesPlUnPl, 0, mrSystemUnpleasure, mrSystemLibid, 0);
			}
		}
		
		
		
		
	}
	
	private double getMaxValue(double[] prAllValues) {
		Arrays.sort(prAllValues);
		return prAllValues[prAllValues.length-1];
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 05.07.2012, 15:48:45
	 *  emotions triggered by perception (from memory) influence emotion-generation
	 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emtion is based on) to influence the emotion generation in F63
	 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
	 * 
	 */
	private void getEmotionValuesFromPerception() {
		ArrayList<clsAssociation> oPIAssociatedContent = new ArrayList<clsAssociation>();
		ArrayList<clsAssociation> oEntityAssociatedContent = new ArrayList<clsAssociation>();
		oPIAssociatedContent = moPerceptions_IN.getMoInternalAssociatedContent();
		clsEmotion oEmotionFromPerception = null;
		for(clsAssociation oPIAss: oPIAssociatedContent) {
			// in an image there just should exist TPMs (Entities)
			try {
				oEntityAssociatedContent = ((clsThingPresentationMesh)oPIAss.getMoAssociationElementB()).getExternalMoAssociatedContent();
			}
			catch (Exception e) {
				System.out.println("No TPM)" + oPIAss.getMoAssociationElementB().toString());
				e.printStackTrace();
			}
			
			for (clsAssociation oEntityAss: oEntityAssociatedContent) {
				if (oEntityAss instanceof clsAssociationEmotion) {
					oEmotionFromPerception = (clsEmotion) oEntityAss.getMoAssociationElementA();
					mrPerceptionPleasure += oEmotionFromPerception.getMrSourcePleasure(); 
					mrPerceptionUnpleasure += oEmotionFromPerception.getMrSourceUnpleasure();
					mrPerceptionLibid += oEmotionFromPerception.getMrSourceLibid();
					mrPerceptionAggr += oEmotionFromPerception.getMrSourceAggr();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 03.07.2012, 15:48:45
	 * 
	 */
	private void generateEmotion(clsEmotionType poEmotionType, double prEmotionIntensity, double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr) {
		moEmotions_OUT.add(clsDataStructureGenerator.generateEMOTION(new clsTriple <String, clsEmotionType, Object>("BASIC_EMOTIONS", poEmotionType, prEmotionIntensity),  prSourcePleasure,  prSourceUnpleasure,  prSourceLibid,  prSourceAggr));
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
		moDescription = "F63: TODO";
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
	
	
	

}
