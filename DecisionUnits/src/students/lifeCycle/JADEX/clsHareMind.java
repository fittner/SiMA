package students.lifeCycle.JADEX;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eDecisionType;

public class clsHareMind extends clsBaseDecisionUnit {

	@Override
	public void process() {
		// TODO (langr) - Auto-generated method stub
	}
	
	public clsHareMind(String poPrefix, clsBWProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:55:49
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.HARE_JADEX;
		
	}
}
