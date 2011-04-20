/**
 * clsPsychoAnalysis.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 12.08.2009, 10:18:56
 */
package pa;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eDecisionType;

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
	public static final String P_MODELVERSION = "modelversion";
	
	private static final boolean mnUseOld = false;
	
	/**
	 * @author muchitsch
	 * 05.04.2011, 16:41:35
	 * 
	 * @return the mnuseold
	 */
	public static boolean isUseOldModel() {
		return mnUseOld;
	}

	private itfProcessor moProcessor;
	
	public clsPsychoAnalysis(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp);
	}

	@SuppressWarnings("deprecation")
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty( pre+P_VERSION , 1);

		if (mnUseOld) {
			oProp.putAll( pa._v19.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
			oProp.setProperty( pre+P_MODELVERSION, "v19");
		} else {
			oProp.putAll( pa._v30.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
			oProp.setProperty( pre+P_MODELVERSION, "v30");
		}
		
		return oProp;
	}	
	
	@SuppressWarnings("deprecation")
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	 
		String oModelVersion = poProp.getProperty(P_MODELVERSION);
		
		if (oModelVersion == "v19") {	
			moProcessor = new pa._v19.clsProcessor(pre+P_PROCESSOR, poProp);
		} else if (oModelVersion == "v30") {
			moProcessor = new pa._v30.clsProcessor(pre+P_PROCESSOR, poProp);
		} else {
			
		}

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

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 13.08.2009, 00:07:30
	 *
	 * @return
	 */
	public itfProcessor getProcessor() {
		return moProcessor;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:53:45
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.PA;
		
	}

}
