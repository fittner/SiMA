package creation.simplePropertyLoader;


/**
 * CHANGELOG
 *
 * Mar 27, 2012 schaat - File created
 *
 */



import config.clsProperties;
import creation.clsLoader;
import creation.eLoader;
import decisionunit.clsDecisionUnitFactory;
import du.enums.eDecisionType;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import bw.entities.clsARSIN;
import bw.entities.clsEntity;
import statictools.clsUniqueIdGenerator;

/**
 * Loads the properties and and creates an ARSINI for external use. No dependencies to MASON or other simulation code.
 * This file is derived from  clsSimplePropertyLoader
 * 
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Mar 27, 2012, 12:08:20 PM
 * 
 * 
 */
public class clsExternalARSINLoader extends clsLoader {
	/** Contains the defaults for the entity type, if not provided, the default values are extracted in runtime from the classes. */
	public static final String P_DEFAULTSENTITY  = "defaultsentity";
	/** Contains the defaults for the decision unit type, if not provided, the default values are extracted in runtime from the classes. */
	public static final String P_DEFAULTSDECISIONUNIT  = "defaultsdecisionunit";

	/** This is the version number of the simpleproperty loader. */
	public static final int mnVersion = 3;
	/** This loader can handle old property files down to version number ... */
	public static final int mnDownCompatibility = 3; // can read 3 and newer
	
	/**
	 * Prepares the MASON simulation environment for the to be loaded entities. 
	 *
	 * @since 20.06.2011 19:51:24
	 *
	 * @param poSimState
	 * @param poProperties
	 */
	public clsExternalARSINLoader(clsProperties poProperties) {
		super(poProperties);
		applyProperties(getPrefix(), getProperties());
		
    }

	
    /**
     * Applies the provided properties and creates all instances of various member variables.
     *
     * @since 20.06.2011 19:51:45
     *
     * @param poPrefix
     * @param poProp
     */
    private void applyProperties(String poPrefix, clsProperties poProp){		
    	String pre = clsProperties.addDot(poPrefix);
    	
    	
    	boolean usedefaults = true; 
    	
    	if (usedefaults) {
    		clsProperties oP = new clsProperties();

    		oP.putAll( getEntityDefaults(pre+P_DEFAULTSENTITY) );
    		oP.putAll( getDecisionUnitDefaults(pre+P_DEFAULTSDECISIONUNIT) );
	    	oP.putAll(poProp);

	    	poProp.clear();
	    	poProp.putAll(oP);
    	}	
	}	
	
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     *
     * @since 06.07.2011 13:01:59
     *
     * @param poPrefix
     * @return
     */
    private static clsProperties getEntityDefaults(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsARSIN.getDefaultProperties		(pre+eEntityType.ARSIN.name()) );
		
		return oProp;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	    
    private static clsProperties getDecisionUnitDefaults(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( simple.dumbmind.clsDumbMindA.getDefaultProperties				(pre+eDecisionType.DUMB_MIND_A.name()) );
		oProp.putAll( simple.reactive.clsReactive.getDefaultProperties				(pre+eDecisionType.FUNGUS_EATER.name()) );
		oProp.putAll( simple.remotecontrol.clsRemoteControl.getDefaultProperties	(pre+eDecisionType.REMOTE.name()) );
		oProp.putAll( students.lifeCycle.JADEX.clsHareMind.getDefaultProperties		(pre+eDecisionType.HARE_JADEX.name()) );
		oProp.putAll( students.lifeCycle.JAM.clsHareMind.getDefaultProperties		(pre+eDecisionType.HARE_JAM.name()) );
		oProp.putAll( students.lifeCycle.IfThenElse.clsHareMind.getDefaultProperties(pre+eDecisionType.HARE_IFTHENELSE.name()) );
		oProp.putAll( students.lifeCycle.JADEX.clsLynxMind.getDefaultProperties		(pre+eDecisionType.LYNX_JADEX.name()) );
		oProp.putAll( students.lifeCycle.JAM.clsLynxMind.getDefaultProperties		(pre+eDecisionType.LYNX_JAM.name()) );
		oProp.putAll( students.lifeCycle.IfThenElse.clsLynxMind.getDefaultProperties(pre+eDecisionType.LYNX_IFTHENELSE.name()) );
		oProp.putAll( pa.clsPsychoAnalysis.getDefaultProperties						(pre+eDecisionType.PA.name()) );
		oProp.putAll( testbrains.clsActionlessTestPA.getDefaultProperties			(pre+eDecisionType.ActionlessTestPA.name()) );
		
		return oProp;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	    
    public static clsProperties getDefaultProperties(String poPrefix) {
    	return getDefaultProperties(poPrefix, true, true);
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	    
    public static clsProperties getDefaultProperties(String poPrefix, boolean pnAddDefaultEntities, boolean pnAddDefaultDecisionUnits) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsLoader.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_LOADER_TYPE, eLoader.SIMPLE_PROPERTY_LOADER.name());
		oProp.setProperty(pre+P_LOADER_VERSION, mnVersion);
		oProp.setProperty(pre+P_TITLE, "default simple property loader");
		
		if (pnAddDefaultEntities) {
			oProp.putAll( getEntityDefaults(pre+P_DEFAULTSENTITY) );
		}	
		if (pnAddDefaultDecisionUnits) {
			oProp.putAll( getDecisionUnitDefaults(pre+P_DEFAULTSDECISIONUNIT) );
		}
		
		return oProp;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	
    private clsProperties getEntityProperties(eEntityType pnType) {
    	String pre = clsProperties.addDot( getPrefix() );    	
    	String oKey = pre+P_DEFAULTSENTITY+"."+pnType.name();
    	clsProperties oResult = getProperties().getSubset(oKey);
    	return oResult;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	
    private clsProperties getDecisionUnitProperties(eDecisionType pnType) {
    	String pre = clsProperties.addDot( getPrefix() );    	
    	String oKey = pre+P_DEFAULTSDECISIONUNIT+"."+pnType.name();
    	clsProperties oResult = getProperties().getSubset(oKey);
    	return oResult;
    }    
    
    
    /**
     * Creates a single entity according to the current property entry. If a decision unit is defined, it is created too.
     *
     * @since 20.06.2011 19:54:06
     *
     * @param poPropEntity
     * @param poPropDecisionUnit
     * @param pnEntityType
     * @param pnDecisionType
     * @param uid
     */
    private clsEntity createEntity(clsProperties poPropEntity, clsProperties poPropDecisionUnit, 
    		eEntityType pnEntityType, eDecisionType pnDecisionType, int uid) {
    	String pre = clsProperties.addDot("");
    	
    	itfDecisionUnit oDU = null;
    	if (pnDecisionType != eDecisionType.NONE) {
    			oDU = clsDecisionUnitFactory.createDecisionUnit_static(pnDecisionType, pre, poPropDecisionUnit, uid);
    	}
    	
    	clsEntity oEntity = null;
    	
    	switch(pnEntityType) {
    		case ARSIN:
    			oEntity = new clsARSIN(oDU, pre, poPropEntity, uid);    				
    			break;
			default:
				throw new java.lang.IllegalArgumentException("eEntityType."+pnEntityType.toString());    	
    	}
    	return oEntity;
		
    }
    
    /**
     * Creates a entity. The property file is read and parsed. For the entity type and the decision
     * unit type the default configuration is loaded. If overwrite or remove entries are present in the config, the 
     * merging or removing of entries is processed.
     *
     * @since 20.06.2011 19:54:44
     *
     * @param poPrefix
     * @param poProp
     */
    private clsEntity createArsin(String poPrefix, clsProperties poProp) {
    	String pre = clsProperties.addDot(poPrefix);
    	
    	//get enttity type
    	eEntityType nEntityType = eEntityType.ARSIN;
    	
    	//get decision unit type
    	eDecisionType nDecisionType = eDecisionType.PA;

    		
    	int uid = clsUniqueIdGenerator.getUniqueId();
    		
    	//get entity default properties
    	clsProperties oEntityProperties = getEntityProperties(nEntityType);
    	oEntityProperties.put( clsEntity.P_ID, nEntityType.name()+"_"+nDecisionType.name()+"_"+" (#"+uid+")" );    		
    	   		
    		
    	//get default decision unit properties (if existing)
    	clsProperties oDecisionUnitProperties = getDecisionUnitProperties(nDecisionType);
    		 		    		
    		
    	//create entity wiht resulting configuration for entity and decision unit type
    	
    	return createEntity(oEntityProperties, oDecisionUnitProperties, nEntityType, nDecisionType, uid);
    }
    
	/**
	 * Executes the load and entity creation. 
	 *
	 * @author deutsch
	 * Jul 25, 2009, 11:56:16 AM
	 * 
	 * @see sim.creation.clsLoader#loadObjects()
	 */

	public clsEntity getARSINI() {
		String pre = clsProperties.addDot( getPrefix() );
		
		System.out.print(" done;");
		
		return createArsin(pre, getProperties() );


		
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * Jul 26, 2009, 3:24:30 PM
	 * 
	 * @see sim.creation.clsLoader#checkVersionCompatibility()
	 */
	@Override
	protected void checkVersionCompatibility(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot( poPrefix );
		int nLoaderVersion = poProp.getPropertyInt(pre+P_LOADER_VERSION);
		
		if (nLoaderVersion > mnVersion) {
			throw new java.lang.NullPointerException("loader is to old to read given file. loader version:"+mnVersion+"; file version:"+nLoaderVersion);
		} else if (nLoaderVersion < mnDownCompatibility) {
			throw new java.lang.NullPointerException("file is to old to be read by this loader. loader version:"+mnVersion+"; file version:"+nLoaderVersion);
		}
	}

	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * Jul 26, 2009, 3:36:41 PM
	 * 
	 * @see sim.creation.clsLoader#verifyLoaderType(java.lang.String, bw.utils.config.clsProperties)
	 */
	@Override
	protected void verifyLoaderType(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot( poPrefix );
		eLoader nLoader = eLoader.valueOf(poProp.getPropertyString(pre+P_LOADER_TYPE));
		
		if (nLoader != eLoader.SIMPLE_PROPERTY_LOADER) {
			throw new java.lang.NullPointerException("wrong loader used. loader type:"+eLoader.SIMPLE_PROPERTY_LOADER+"; file created by loader"+poProp.getPropertyString(pre+P_LOADER_TYPE));
		}		
	}


	/* (non-Javadoc)
	 *
	 * @since Mar 27, 2012 5:43:26 PM
	 * 
	 * @see creation.clsLoader#loadObjects()
	 */
	@Override
	public void loadObjects() {
		// TODO (schaat) - Auto-generated method stub
		
	}
}
