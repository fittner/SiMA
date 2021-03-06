/**
 * clsPsychoAnalysis.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 12.08.2009, 10:18:56
 */
package pa;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.08.2009, 10:18:56
 * 
 */
public class clsPsychoAnalysis extends clsBaseDecisionUnit {
	public static final String P_PROCESSOR = "processor";
	public static final String P_VERSION = "version";
	
	private clsProcessor moProcessor;
	
	public clsPsychoAnalysis(String poPrefix, clsBWProperties poProp) {
		super();
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
		oProp.setProperty( pre+P_VERSION , 1);
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moProcessor = new clsProcessor(pre+P_PROCESSOR, poProp);

	}
		
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 10:18:56
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#process()
	 */
	@Override
	public void process() {
		moProcessor.applySensorData( getSensorData() );
		moProcessor.step();
		moProcessor.getActionCommands( getActionProcessor() );

	}

}
