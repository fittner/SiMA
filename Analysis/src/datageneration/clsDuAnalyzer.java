package datageneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logger.clsLogger;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eEmotionType;

import org.slf4j.Logger;

import secondaryprocess.modules.F29_EvaluationOfImaginaryActions;
import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import control.interfaces.clsBaseDecisionUnit;
import control.interfaces.itfDuAnalysis;

public class clsDuAnalyzer implements itfDuAnalysis {
	protected static final Logger log = clsLogger.getLog("analysis.analyzer.du");
	Map<String, String> moCurrentStepFactors = new HashMap<>();
	List<Map<String, String>> moFactors = new ArrayList<>();
	List<String> moActions = new ArrayList<>();
	clsAnalyzer moParent = null;
	int mnEntityGroupId;
	
	clsDuAnalyzer(clsAnalyzer poParent, int pnEntityGroupId) {
		this.moParent = poParent;
		this.mnEntityGroupId = pnEntityGroupId;
	}
	
	public String getId() {
		return "Agent_" + Integer.toString(mnEntityGroupId);
	}
	
	protected boolean isDecided() {
		if(moActions.size() < 1) {
			return false;
		}
		
		switch(eAction.valueOf(moActions.get(moActions.size() - 1))) {
		case BEAT:
		case EAT:
		case DIVIDE:
		case MOVE_BACKWARD:
//		case PERFORM_BASIC_ACT_ANALYSIS: // this is just for testing - to end the simulation runs a lot quicker
			return true;
		default:
			return false;
		}
	}
	
	protected void nextStep() {
		moFactors.add(moCurrentStepFactors);
		moCurrentStepFactors = new HashMap<>();
	}
	
	@Override
	public void putFactor(String oFactorId, String oFactorValue) {
		moCurrentStepFactors.put(notNull(oFactorId, "Factor id provided to putFactor of clsDuAnalyzer " + this + " must not be null"),
				notNull(oFactorValue, "Factor value provided to putFactor of clsDuAnalyzer " + this + " must not be null"));
	}
	
	public void putFactor(String oFactorId, clsEmotion poEmotion) {
		putFactor(oFactorId + "_INTENSITY", Double.toString(poEmotion.getEmotionIntensity()));
		putFactor(oFactorId + "_SOURCEPLEASURE", Double.toString(poEmotion.getSourcePleasure()));
		putFactor(oFactorId + "_SOURCEUNPLEASURE", Double.toString(poEmotion.getSourceUnpleasure()));
		putFactor(oFactorId + "_SOURCEAGGR", Double.toString(poEmotion.getSourceAggr()));
		putFactor(oFactorId + "_SOURCELIBID", Double.toString(poEmotion.getSourceLibid()));
	}
	
	@Override
	public void putAction(String oActionValue) {
		moActions.add(notNull(oActionValue, "Action provided to putAction method of clsDuAnalyzer " + this + " must not be null"));
		nextStep();
		if(isDecided()) {
			moParent.setDecided(mnEntityGroupId);
		}
	}
	
	public List<String> getActions() {
		return moActions;
	}
	
	public String getAction() {
		return moActions.get(moActions.size() - 1);
	}
	
	protected Map<String, String> finalizeFactors() {
		//finalize_F71_emotionValues_Factors();
		finalizeGoals();
		finalizeF63();
		
		return moCurrentStepFactors;
	}

	protected Map<String, String> extractInitAndFinalByName(Map<String, String> poFirstMap, Map<String, String> poLastMap, String poPrefix) {
		Map<String, String> oMap = new HashMap<>();
		
		//check for consistency
		if(poFirstMap.keySet().equals(poLastMap.keySet())) {
			for(String oKey : poFirstMap.keySet()) {
				if(oKey.startsWith(poPrefix)) {
					oMap.put(oKey + "_INIT", poFirstMap.get(oKey));
					oMap.put(oKey + "_FINAL", poLastMap.get(oKey));
				}
			}
		} else {
			log.error("Attempting to combine initial and final values from incompatible maps");
			log.error("Initial values: {}\nLast values: {}", poFirstMap.keySet(), poLastMap.keySet());
		}
		
		return oMap;
	}
	
	protected void finalizeF63() {
		moCurrentStepFactors.putAll(extractInitAndFinalByName(getFirstDataMap(), getLastDataMap(), "DRIVEIMPACT"));
		moCurrentStepFactors.putAll(extractInitAndFinalByName(getFirstDataMap(), getLastDataMap(), "PERCEPTIONIMPACT_FROMDRIVES"));
		moCurrentStepFactors.putAll(extractInitAndFinalByName(getFirstDataMap(), getLastDataMap(), "PERCEPTIONIMPACT_FROMEXPERIENCE"));
		moCurrentStepFactors.putAll(extractInitAndFinalByName(getFirstDataMap(), getLastDataMap(), "PERCEPTIONIMPACT_FROMBODYSTATES"));
		moCurrentStepFactors.putAll(extractInitAndFinalByName(getFirstDataMap(), getLastDataMap(), "MEMORIZEDVALUATIONIMPACT"));
		moCurrentStepFactors.putAll(extractInitAndFinalByName(getFirstDataMap(), getLastDataMap(), "BASICEMOTION"));
	}
	
	protected Map<String, String> getFirstDataMap() {
		return moFactors.get(0);
	}
	
protected Map<String, String> getLastDataMap() {
		return moFactors.get(moFactors.size() - 1);
	}
	
	protected void finalize_F71_emotionValues_Factors() {
		//check for consistency
		if(moInitialValues.keySet().equals(moLastValues.keySet())) {
			for(eEmotionType oKey : moInitialValues.keySet()) {
				putFactor(oKey.toString() + "_INIT", Double.toString(moInitialValues.get(oKey)));
				putFactor(oKey.toString() + "_FINAL", Double.toString(moLastValues.get(oKey)));
			}
		} else {
			log.error("Initial value and last value map contains different keys:");
			log.error("Initial values: {}\nLast values: {}", moInitialValues.keySet(), moLastValues.keySet());
		}
	}
	
	protected void finalizeGoals() {
		moCurrentStepFactors.putAll(moFactors.get(moFactors.size() - 1));
	}
	
	public Map<String, String> getFactors() {
		Map<String, String> finalizedFactors = finalizeFactors(); 
		
		return finalizedFactors;
	}
	
	protected <T> T notNull(T value, String message) {
        if(value == null) {
            throw new IllegalArgumentException(message);
        }
        
        return value;
    }

	private Map<eEmotionType, Double> moInitialValues = new HashMap<>();
    private Map<eEmotionType, Double> moLastValues = new HashMap<>();
    
	@Override
	public void put_F71_emotionValues(List<clsEmotion> poEmotions) {
		for(clsEmotion oEmotion : poEmotions) {
            if(moInitialValues.containsKey(oEmotion.getContent())) {
                moLastValues.put(oEmotion.getContent(), oEmotion.getEmotionIntensity());
            } else {
                moInitialValues.put(oEmotion.getContent(), oEmotion.getEmotionIntensity());
            }
        }
	}
	
	@Override
	public void putFinalGoals(ArrayList<clsWordPresentationMeshPossibleGoal> poGoals) {
		String oIdentifier = new String();
		String oGoalBaseKeyString = new String();
		int rGoalCount = 0;
		double rTempDriveDemandImportance = 0;
        double rTempFeelingMatchImportance = 0;
        double rTempFeelingExpectationImportance = 0;
        double rTempImportanceSum = 0;
		
		for(clsWordPresentationMeshPossibleGoal oGoal : poGoals) {
			oGoalBaseKeyString = "GOAL" + Integer.toString(rGoalCount++);
			//Store the goal id
			oIdentifier = oGoal.getSupportiveDataStructure().getContent() + ":" + oGoal.getGoalContentIdentifier();
			putFactor(oGoalBaseKeyString + "_ID", oIdentifier);
			
			//Store the goal total importance
			putFactor(oGoalBaseKeyString + "_IMPORTANCE", Double.toString(oGoal.getTotalImportance()));
			
			//Drive demand and feelings importance are combined via non-proportional aggregation, therefore we divide the
            //PP impact factor, according to the relation between drive demand impact and feelings impact
            rTempImportanceSum = oGoal.getDriveDemandImportance() +  oGoal.getFeelingsMatchImportance() + oGoal.getFeelingsExcpactationImportance();
            rTempDriveDemandImportance = oGoal.getPPImportance() * (oGoal.getDriveDemandImportance() / rTempImportanceSum);
            rTempFeelingMatchImportance = oGoal.getPPImportance() * (oGoal.getFeelingsMatchImportance() / rTempImportanceSum);
            rTempFeelingExpectationImportance = oGoal.getPPImportance() * (oGoal.getFeelingsExcpactationImportance() / rTempImportanceSum);
			
			//Store the goals drive demand importance
			putFactor(oGoalBaseKeyString + "_IMPORTANCE_DRIVE", Double.toString(rTempDriveDemandImportance));
			
			//Store the goals feelings match importance
			putFactor(oGoalBaseKeyString + "_IMPORTANCE_FEELINGMATCH", Double.toString(rTempFeelingMatchImportance));
			
			//Store the goals feelings expectation match importance			
			putFactor(oGoalBaseKeyString + "_IMPORTANCE_FEELINGEXPECTATION", Double.toString(rTempFeelingExpectationImportance));
			
			//Store the goals effort impact importance
			putFactor(oGoalBaseKeyString + "_IMPORTANCE_EFFORT", Double.toString(oGoal.getEffortImpactImportance()));
		}
	}

	@Override
	public void put_F63_emotionContributors(clsEmotion poFromDrives, clsEmotion poFromPerceptionDrive,
			clsEmotion poFromPerceptionExperiences, clsEmotion poFromPerceptionBodystates,
			clsEmotion poFromMemorizedValuations) {

		putFactor("DRIVEIMPACT", poFromDrives);
		putFactor("PERCEPTIONIMPACT_FROMDRIVES", poFromPerceptionDrive);
		putFactor("PERCEPTIONIMPACT_FROMEXPERIENCE", poFromPerceptionExperiences);
		putFactor("PERCEPTIONIMPACT_FROMBODYSTATES", poFromPerceptionBodystates);
		putFactor("MEMORIZEDVALUATIONIMPACT", poFromMemorizedValuations);
	}

	@Override
	public void put_F63_basicEmotion(clsEmotion poBasicEmotion) {
		putFactor("BASICEMOTION", poBasicEmotion);
	}
}
