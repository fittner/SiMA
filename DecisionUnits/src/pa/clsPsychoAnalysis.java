/**
 * clsPsychoAnalysis.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 12.08.2009, 10:18:56
 */
package pa;

import pa.enums.eModelVersion;
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
	
	//obsolete and replaced by enum eModelVersion
	//private static final boolean mnUseOld = false;
	
	private static final eModelVersion P_MODEL = eModelVersion.v38; 
	
	/**
	 * @author muchitsch
	 * 05.04.2011, 16:41:35
	 * 
	 * @return the mnuseold
	 */
	public static String getModelVersion() {
		return P_MODEL.name();
	}

	private itfProcessor moProcessor;
	
	public clsPsychoAnalysis(String poPrefix, clsBWProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp, uid);
	}

	@SuppressWarnings("deprecation")
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty( pre+P_VERSION , 1);

		
		switch(P_MODEL){
			case v19:
				oProp.putAll( pa._v19.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
				oProp.setProperty( pre+P_MODELVERSION, "v19");
			break;
			
			case v30:
				oProp.putAll( pa._v30.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
				oProp.setProperty( pre+P_MODELVERSION, "v30");
			break; 
			case v38:
				oProp.putAll( pa._v38.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
				oProp.setProperty( pre+P_MODELVERSION, "v38");
			break; 
		}
		
		return oProp;
	}	
	
	@SuppressWarnings("deprecation")
	private void applyProperties(String poPrefix, clsBWProperties poProp, int uid) {
		String pre = clsBWProperties.addDot(poPrefix);
	 
		String oModelVersion = poProp.getProperty(P_MODELVERSION);
		
		if (oModelVersion.equals(eModelVersion.v19.name())) {	
			moProcessor = new pa._v19.clsProcessor(pre+P_PROCESSOR, poProp);
		} else if (oModelVersion.equals(eModelVersion.v30.name())) {
			moProcessor = new pa._v30.clsProcessor(pre+P_PROCESSOR, poProp, uid);
		} else {
			moProcessor = new pa._v38.clsProcessor(pre+P_PROCESSOR, poProp, uid);
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
