package datageneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logger.clsLogger;
import memorymgmt.enums.eAction;

import org.slf4j.Logger;

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
	
	public List<Map<String, String>> getFactors() {
		return moFactors;
	}
	
	protected <T> T notNull(T value, String message) {
        if(value == null) {
            throw new IllegalArgumentException(message);
        }
        
        return value;
    }
}
