package students.lifeCycle.JAM;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;

public class clsHareMind extends clsBaseDecisionUnit {

	@Override
	public void process() {
		// TODO (langr) - Auto-generated method stub
	}

	
	public clsHareMind(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsBaseDecisionUnit.getDefaultProperties(poPrefix) );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);

	}
}
