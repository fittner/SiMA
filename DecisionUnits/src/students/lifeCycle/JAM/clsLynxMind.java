package students.lifeCycle.JAM;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;

public class clsLynxMind extends clsBaseDecisionUnit  {
	public clsLynxMind(String poPrefix, clsBWProperties poProp) {
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
	
	@Override
	public void process() {
		// TODO (deutsch) - Auto-generated method stub
	}

}
