/**
 * CHANGELOG
 * 
 * 2011/07/11 TD - added javadoc comments. code sanitation.
 */
package pa;



import config.clsProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eDecisionType;


/**
 * This class wraps the implementation of the psychoanalytically inspired decision units to be compatible 
 * with the interface specified in decisionunit.clsBaseDecisionUnit. Can distinguish between v19, v30, and v38. The default
 * value is v38 currently. To specify a different version, please change the provided proterites.
 * 
 * @author deutsch
 * 11.07.2011, 16:45:48
 * 
 */
public class clsPsychoAnalysis extends clsBaseDecisionUnit {
	/** prefix for all property entries for this class. @since 11.07.2011 16:50:30 */
	public static final String P_PROCESSOR = "processor"; 
	/** the version of this class. not to be confused with the model version of the selected implementation; @since 11.07.2011 16:50:55 */
	public static final String P_VERSION = "version";
	/** the version of the decsion unit to be used.; @since 11.07.2011 16:51:38 */
	public static final String P_MODELVERSION = "modelversion";
	
	private static final eModelVersion P_MODEL = eModelVersion.v38; 	//AW 20110519 Start with V38 now.
	
	private itfProcessor moProcessor;
	
	/**
	 * Creates an instance of the class with the provided properties and the uid.
	 *
	 * @since 11.07.2011 16:47:28
	 *
	 * @param poPrefix - prefix for the properties
	 * @param poProp - the stored properties
	 * @param uid - the unique id for this agent. the same for the body and the decision unit. eases debugging and logging.
	 */
	public clsPsychoAnalysis(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp, uid);
	}

	/**
	 * Returns the selected model of the psychoanalytically inspired decision unit. 
	 *
	 * @since 11.07.2011 16:48:51
	 *
	 * @return
	 */
	public static String getModelVersion() {
		return P_MODEL.name();
	}

	/**
	 * returns the default properties for this class and the default properties for the default decision unit.
	 *
	 * @since 11.07.2011 16:52:35
	 *
	 * @param poPrefix
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty( pre+P_VERSION , 1);

		
		switch(P_MODEL){
//			case v19:
//				oProp.putAll( pa._v19.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
//				oProp.setProperty( pre+P_MODELVERSION, "v19");
//			break;
//			
//			case v30:
//				oProp.putAll( pa._v30.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
//				oProp.setProperty( pre+P_MODELVERSION, "v30");
//			break; 
			case v38:
				oProp.putAll( pa._v38.clsProcessor.getDefaultProperties(pre+P_PROCESSOR) );
				oProp.setProperty( pre+P_MODELVERSION, "v38");
			break; 
		}
		
		return oProp;
	}	
	
	/**
	 * applies the provided properties and creates an instance of the decision unit. to be more precise, an instance of a class that 
	 * implements the interface pa.itfProcessor.
	 *
	 * @since 11.07.2011 16:53:16
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param uid
	 */
	@SuppressWarnings("deprecation")
	private void applyProperties(String poPrefix, clsProperties poProp, int uid) {
		String pre = clsProperties.addDot(poPrefix);
	 
		String oModelVersion = poProp.getProperty(P_MODELVERSION);
		//double oSuperEgo_strength = poProp.getPropertyDouble(pre + "superego");
		
//		if (oModelVersion.equals(eModelVersion.v19.name())) {	
//			moProcessor = new pa._v19.clsProcessor(pre+P_PROCESSOR, poProp);
//		} else if (oModelVersion.equals(eModelVersion.v30.name())) {
//			moProcessor = new pa._v30.clsProcessor(pre+P_PROCESSOR, poProp, uid);
//		} else {
			moProcessor = new pa._v38.clsProcessor(pre+P_PROCESSOR, poProp, uid);
//		}

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
		moPerceptionInspectorData = moProcessor.getPerceptionInspectorData();
		moProcessor.getInternalActionCommands( getInternalActionProcessor() );
		moProcessor.getActionCommands( getActionProcessor() );

	}

	/**
	 * Returns the processor. Thus, with this method the instance of the decision unit implementation can be accessed.
	 *
	 * @since 11.07.2011 16:54:20
	 *
	 * @return The instance to the processor/decision unit.
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
