package students.lifeCycle.JAM;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eDecisionType;

public class clsLynxMind extends clsBaseDecisionUnit  {
	public clsLynxMind(String poPrefix, clsBWProperties poProp, int uid) {
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
	
	@Override
	public void process() {
		// TODO (deutsch) - Auto-generated method stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:57:34
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.LYNX_JAM;
		
	}

}
