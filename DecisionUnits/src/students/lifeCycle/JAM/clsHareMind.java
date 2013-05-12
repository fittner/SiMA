package students.lifeCycle.JAM;

import config.clsProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eDecisionType;

public class clsHareMind extends clsBaseDecisionUnit {

	@Override
	public void process() {
		//  (langr) - Auto-generated method stub
	}

	
	public clsHareMind(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsBaseDecisionUnit.getDefaultProperties(poPrefix) );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);

	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:57:15
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.HARE_JAM;
		
	}
}
