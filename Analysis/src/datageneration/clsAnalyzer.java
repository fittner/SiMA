package datageneration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import singeltons.clsSingletonMasonGetter;
import control.interfaces.itfAnalysis;
import control.interfaces.itfDuAnalysis;

public class clsAnalyzer implements itfAnalysis {
    Map<Integer, clsDuAnalyzer> moDus= new HashMap<>();
	clsRemoter moRemote = null;
	
	public clsAnalyzer(clsRemoter poRemote) {
		registerRemote(poRemote);
	}

	public void registerRemote(clsRemoter poRemote) {
		moRemote = notNull(poRemote, "clsRemoter implementation provided to clsAnalyzer must not be null");
	}
	
	protected void analysisFinished() {
		moRemote.stopSiMA();
	}
	
	protected void setDecided(int pnEntityGroupId) {
		analysisFinished();
//		boolean bDecided = true;
//		
//		for(clsDuAnalyzer oDuAnalyzer : moDus.values()) {
//			bDecided = bDecided && oDuAnalyzer.isDecided();
//			if(!bDecided) {
//				break;
//			}
//		}
//		
//		if(bDecided) {
//			analysisFinished();
//		}
	}
	
	@Override
	public void registerDu(int pnEntityGroupId) {
		moDus.put(pnEntityGroupId, new clsDuAnalyzer(this, pnEntityGroupId));
	}
	
	protected <T> T notNull(T value, String message) {
        if(value == null) {
            throw new IllegalArgumentException(message);
        }
        
        return value;
    }
	
	public Collection<clsDuAnalyzer> getAllDuAnalyzers() {
		return moDus.values();
	}
	
	@Override
	public itfDuAnalysis getDu(int pnEntityGroupId) {
		if(!moDus.containsKey(pnEntityGroupId)) {
			throw new IllegalArgumentException("Could not access DU specific analysis for group id " + pnEntityGroupId +".\nAvailable group IDs: " + moDus);
		}
		
		return moDus.get(pnEntityGroupId);
	}
}
