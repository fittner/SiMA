/**
 * CHANGELOG
 * 
 * 2011/06/20 TD - added some javadoc
 */
package loader;

import java.awt.Color;




import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.interfaces.itfSearchSpaceAccess;
import communication.layer1.implementations.clsLayer1ProcedureCall;
import complexbody.internalSystems.clsFlesh;
import complexbody.internalSystems.clsInternalSystem;
import complexbody.io.sensors.datatypes.enums.eEntityType;
import pa._v38.memorymgmt.longtermmemory.clsLongTermMemoryHandler;
import pa._v38.memorymgmt.searchspace.clsSearchSpaceManager;
import properties.clsProperties;
import control.clsProcessor;
import control.clsPsychoAnalysis;
import control.factory.clsARSDecisionUnitFactory;
import control.interfaces.itfDecisionUnit;
import du.enums.eDecisionType;
import entities.clsWallAxisAlign;
import entities.clsWallHorizontal;
import entities.clsWallVertical;
import entities.abstractEntities.clsAnimate;
import entities.abstractEntities.clsEntity;
import entities.enums.eBodyType;
import entities.enums.eNutritions;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;
import entity.clsInspectorEntity;
import base.clsCommunicationFactory;
import base.clsCommunicationInterface;
import body.clsBaseBody;
import body.clsComplexBody;
import body.clsMeatBody;
import registration.clsRegisterEntity;
import sim.engine.SimState;
import singeltons.clsSingletonMasonGetter;
import tools.clsPose;
import tools.eImagePositioning;
import utils.clsGetARSPath;
import utils.clsUniqueIdGenerator;
import utils.ePositionType;

/**
 * Creates the world and all of its entities according to a simple property file. This file contains basic world settings
 * like width and hight. Further a list of different entity groups is provided. Each group creates entities according to 
 * the default values specified for them and at either a predefined position or at a random position. For an example of 
 * this property file, take a look at @see clsSimplePropertyLoader#getDefaultProperties(java.lang.String,%20boolean,%20boolean). 
 * This method creates a small world populated with various entities. 
 * 
 * @author deutsch
 * Jul 25, 2009, 11:54:40 AM
 * 
 */
public class clsSimplePropertyLoader extends clsLoader {
	/** create walls at the world boundaries (defined by height and width) */
	public static final String P_WORLDBOUNDARYWALLS = "worldboundarywalls";
	/** remove a subtree of the default parameters of the entity defaults */
	public static final String P_REMOVEENTITYDEFAULTS = "removeentitydefaults";	
	/** overwrite the values in a subtree of the entity parameters with the following values. */
	public static final String P_OVERWRITEENTITYDEFAULTS = "overwriteentitydefaults";
	/** remove a subtree of the default parameters of the decision unit defaults */
	public static final String P_REMOVEDECISIONUNITDEFAULTS = "removedecisionunitdefaults";	
	/** overwrite the values in a subtree of the decision unit parameters with the following values. */
	public static final String P_OVERWRITEDECISIONUNITDEFAULTS = "overwritedecisionunitdefaults";
	/** prefix for the entitygroups. to be followed by a number. */
	public static final String P_ENTITYGROUPS = "entitygroups";
	/** total number of entity groups in this setup. */
	public static final String P_NUMENTITYGROUPS = "numentitygroups";
	/** number of entities in this entity group. */
	public static final String P_NUMENTITES = "numentities";
	/** entity type for all entities within this group. */
	public static final String P_GROUPENTITYTYPE = "groupentitytype";
	/** if applicable, the type of the decision unit for all entities in this group. */
	public static final String P_GROUPDECISIONUNITTYPE = "groupdecisionunittype";
	/** Which positioning method to be used. see ePositionType */
	public static final String P_POSITIONTYPE = "positiontype";
	/** In case of positiontype list, the list if given with this prefix. */
	public static final String P_POSITIONS = "positions";
	/** Contains the defaults for the entity type, if not provided, the default values are extracted in runtime from the classes. */
	public static final String P_DEFAULTSENTITY  = "defaultsentity";
	/** Contains the defaults for the decision unit type, if not provided, the default values are extracted in runtime from the classes. */
	public static final String P_DEFAULTSDECISIONUNIT  = "defaultsdecisionunit";
	/** If usedefaults is set to true, the entity/decision unit defaults are used. otherwise ALL params have to be provided by overwritedecisionunitdefaults and overwriteentitydefaults. */
	public static final String P_USEDEFAULTS = "usedefaults";
	/** prefix to the properties file for all entries related to the knowledgebase; @since 12.07.2011 10:58:32 */
	public static final String P_KNOWLEDGEBASE = "knowledgebase";
	
	private int numentitygroups;
	
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
	public clsSimplePropertyLoader(SimState poSimState, clsProperties poProperties) {
		super(poSimState, poProperties);
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
    	
    	numentitygroups = poProp.getPropertyInt(pre+P_ENTITYGROUPS+"."+P_NUMENTITYGROUPS);
    	
    	boolean usedefaults = poProp.getPropertyBoolean(pre+P_USEDEFAULTS); 
    	
    	if (usedefaults) {
    		clsProperties oP = new clsProperties();

    		oP.putAll( clsEntityFactory.getEntityDefaults(pre+P_DEFAULTSENTITY) );
    		oP.putAll( getDecisionUnitDefaults(pre+P_DEFAULTSDECISIONUNIT) );
    		oP.putAll( getMemoryDefaults(pre+P_KNOWLEDGEBASE) );
	    	oP.putAll(poProp);

	    	poProp.clear();
	    	poProp.putAll(oP);
    	}	
	}	 
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	    
    private static clsProperties getDecisionUnitDefaults(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( control.clsPsychoAnalysis.getDefaultProperties						(pre+eDecisionType.PA.name()) );
		oProp.putAll( _MOVEOUTOFPROJECTtestbrains.clsActionlessTestPA.getDefaultProperties			(pre+eDecisionType.ActionlessTestPA.name()) );
		  
		return oProp;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */	    
    public static clsProperties getDefaultProperties(String poPrefix) {
    	return getDefaultProperties(poPrefix, false, false);
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
		oProp.setProperty(pre+P_USEDEFAULTS, true);
		
		if (pnAddDefaultEntities) {
			oProp.putAll( clsEntityFactory.getEntityDefaults(pre+P_DEFAULTSENTITY) );
		}	
		if (pnAddDefaultDecisionUnits) {
			oProp.putAll( getDecisionUnitDefaults(pre+P_DEFAULTSDECISIONUNIT) );
			oProp.putAll(getMemoryDefaults(pre+P_KNOWLEDGEBASE));
		}
		

		int i=0;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.ARSIN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPDECISIONUNITTYPE, eDecisionType.PA.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 3);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.LIST.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_X, 10);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_Y, 10);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_ANGLE, 0);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"1."+clsPose.P_POS_X, 50);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"1."+clsPose.P_POS_Y, 20);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"1."+clsPose.P_POS_ANGLE, Math.PI);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"2."+clsPose.P_POS_X, 100);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"2."+clsPose.P_POS_Y, 100);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"2."+clsPose.P_POS_ANGLE, Math.PI*2/3);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+clsComplexBody.P_INTERNAL+"."+
														clsInternalSystem.P_FLESH+"."+clsFlesh.P_WEIGHT, 15);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsShape2DCreator.P_COLOR, Color.RED);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+
				clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_LIBIDOSTREAM, 0.1);
		
		
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+
				clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_KNOWLEDGEABASE+"."+
				"source_name", "/ARSMemory/config/_v38/bw/pa.memory/AGENT_BASIC/BASIC.pprj");
		
		//oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+
		//		clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_KNOWLEDGEABASE+"."+
		//		clsKnowledgeBaseHandler.P_SOURCE_NAME, "/ARSMemory/config/_v38/bw/pa.memory/AGENT_BASIC/BASIC.pprj");
		//oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+ clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_PSYCHICAPPARATUS+"."+clsPsychicApparatus.P_MINIMALMODEL, false);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+ clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_PSYCHICAPPARATUS+".", false);
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.REMOTEBOT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPDECISIONUNITTYPE, eDecisionType.ActionlessTestPA.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.LIST.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_X, 50);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_Y, 50);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_ANGLE, 0);
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.PLANT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_REMOVEENTITYDEFAULTS, "shape, body");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, clsEntity.P_SHAPENAME);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, "1.5");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.orange);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "carrot_clipart.png");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY_TYPE, eBodyType.MEAT.toString());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+clsMeatBody.P_REGROWRATE, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 100);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+clsFlesh.P_WEIGHT, 50 );
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 4 );
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.PROTEIN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 0.5);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.VITAMIN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 0.5);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"2."+clsFlesh.P_NUTRITIONFRACTION, 4);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsAnimate.P_BODY+"."+"3."+clsFlesh.P_NUTRITIONFRACTION, 8);		
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.HARE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.LYNX.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.BASE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());		
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.CAN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.CAKE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.STONE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.FUNGUS.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());	

		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.URANIUM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.CARROT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.TOILET.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());	
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.LAMP.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.APPLEGREEN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
	
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.SPROUT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.RECTANGLE_STATIONARY.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		//oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+P_NUMENTITYGROUPS, i+1);
		
		oProp.setProperty(pre+P_WORLDBOUNDARYWALLS, new Boolean(true).toString());

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
    
    private static clsProperties getMemoryDefaults(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsSearchSpaceManager.getDefaultProperties(poPrefix));
		
		return oProp;
    }
    
    
    /**
     * Creates and sets the position in clsProperties style.
     *
     * @since 20.06.2011 19:53:18
     *
     * @param poPrefix
     * @param poProp
     * @param poPositionPrefix
     * @param pnNumber
     * @return
     */
    private clsProperties getPosition(String poPrefix, clsProperties poProp, String poPositionPrefix, int pnNumber) {
    	String pre = clsProperties.addDot(poPrefix)+P_POSITIONS+".";
    	poPositionPrefix = clsProperties.addDot(poPositionPrefix);
    	
    	ePositionType nPosType = ePositionType.valueOf( poProp.getPropertyString(pre+P_POSITIONTYPE) );
    	
    	clsProperties oPos = null;
    	
    	switch(nPosType) {
    		case RANDOM:
    			  oPos = generateRandomPose(poPositionPrefix, clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE);
    			break;
    		case LIST:
    			  oPos = poProp.getSubset(pre+pnNumber);
    			  oPos.addPrefix(poPositionPrefix);
    			break;
    		default:
    			throw new java.lang.IllegalArgumentException("ePositionType."+nPosType.toString());
    	}
    	
    	return oPos;
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
    private void createEntity(clsProperties poPropEntity, clsProperties poPropDecisionUnit, clsProperties poPropMemory,
    		eEntityType pnEntityType, eDecisionType pnDecisionType, int uid) {
    	String pre = clsProperties.addDot("");
    	
    	itfDecisionUnit oDU = null;
    	if (pnDecisionType != eDecisionType.NONE) {
    	
    		// distinguish between ARS and Alternative DecisionUnit
    		if(pnDecisionType == eDecisionType.PA || pnDecisionType == eDecisionType.ActionlessTestPA) {
    			itfSearchSpaceAccess oSearchSpace = new clsSearchSpaceManager("",poPropMemory);
    			itfModuleMemoryAccess oMemory = new clsLongTermMemoryHandler(oSearchSpace);
    			oDU = clsARSDecisionUnitFactory.createDecisionUnit_static(pnDecisionType, pre, poPropDecisionUnit, uid, oMemory);
    		} else {
    			throw new IllegalArgumentException("The provided decission unit is not supported.");
    		}
    	}
       	clsEntity temp;

    	temp = clsEntityFactory.createEntity(poPropEntity, pnEntityType, oDU, uid);

    	//link Body and DU
		clsBaseBody oBodyTemp = temp.getBody();
		if((pnDecisionType == eDecisionType.PA || pnDecisionType == eDecisionType.ActionlessTestPA) && oBodyTemp instanceof clsComplexBody) {
			//create the body side
			clsComplexBody oBody = (clsComplexBody) oBodyTemp;
			clsCommunicationInterface oBodyDUData = clsCommunicationFactory.createNonBlockingInterface();
			clsCommunicationInterface oBodyDUControl = clsCommunicationFactory.createBlockingInterface();
			
			//set Interfaces at Body
			oBody.getBrain().setDUControlInterface(oBodyDUControl);
			oBody.getBrain().setDUDataInterface(oBodyDUData);
			
			clsCommunicationInterface oDUBodyData = clsCommunicationFactory.createNonBlockingInterface();
			clsCommunicationInterface oDUBodyControl = clsCommunicationFactory.createBlockingInterface();

			//set Communication Interfaces at DU
			oDU.setControlInterface(oDUBodyControl);
			oDU.setBodyDataInterface(oDUBodyData);
			
			//link the Interfaces
			((clsLayer1ProcedureCall) oDUBodyData.getLayer1()).setCommunicationPartner((clsLayer1ProcedureCall) oBodyDUData.getLayer1());
			((clsLayer1ProcedureCall) oBodyDUData.getLayer1()).setCommunicationPartner((clsLayer1ProcedureCall) oDUBodyData.getLayer1());
			
			((clsLayer1ProcedureCall) oDUBodyControl.getLayer1()).setCommunicationPartner((clsLayer1ProcedureCall) oBodyDUControl.getLayer1());
			((clsLayer1ProcedureCall) oBodyDUControl.getLayer1()).setCommunicationPartner((clsLayer1ProcedureCall) oDUBodyControl.getLayer1());

			//Register the agent at analysis
//			clsAnalyzer.getInstance().registerActiveAgent(temp);
		}
    	
    	
    	

    	temp.setMasonInspectorFactory(new clsInspectorEntity(null,null,null,temp));
    	
    }
    
    /**
     * Creates all entities within a single entity group. The property file is read and parsed. For the entity type and the decision
     * unit type the default configuration is loaded. If overwrite or remove entries are present in the config, the 
     * merging or removing of entries is processed.
     *
     * @since 20.06.2011 19:54:44
     *
     * @param poPrefix
     * @param poProp
     */
    private void createEntityGroup(String poPrefix, clsProperties poProp, int pnGroupId) {
    	String pre = clsProperties.addDot(poPrefix);
    	
    	clsProperties oPropMemory = poProp.getSubset(P_KNOWLEDGEBASE);
    	clsProperties oOverwriteMemoryDefaults = poProp.getSubset(pre+P_OVERWRITEDECISIONUNITDEFAULTS+"."+P_KNOWLEDGEBASE);
    	oPropMemory.putAll(oOverwriteMemoryDefaults);
    	//get enttity type
    	eEntityType nEntityType = eEntityType.valueOf(poProp.getPropertyString(pre+P_GROUPENTITYTYPE));
    	
    	//get decision unit type
    	eDecisionType nDecisionType = eDecisionType.NONE;
    	try {
    		nDecisionType = eDecisionType.valueOf(poProp.getPropertyString(pre+P_GROUPDECISIONUNITTYPE));
    	} catch (java.lang.NullPointerException e) {
    		// do nothing
    	}
    		
    	//get overwritedefault subtrees from teh property file
    	clsProperties oOverwriteEntityDefaults = poProp.getSubset(pre+P_OVERWRITEENTITYDEFAULTS);
    	clsProperties oOverwriteDecisionUnitDefaults = poProp.getSubset(pre+P_OVERWRITEDECISIONUNITDEFAULTS);

    	clsProperties oRemoveEntityDefaults = poProp.getSubset(pre+P_REMOVEENTITYDEFAULTS);
    	
    	clsProperties oRemoveDecisionUnitDefaults = poProp.getSubset(pre + P_REMOVEDECISIONUNITDEFAULTS);
    	
    	//merge default values with overwrite values and remove selected values for each entity in this entity group.
    	int num = poProp.getPropertyInt(pre+P_NUMENTITES);
    	for (int i=0; i<num; i++) {
    		System.out.print(".");
    		
    		//get unique id for this entity (used for entity and for decision unit)
    		//int uid = clsUniqueIdGenerator.getUniqueId();
    		//Kollmann: lets use the group id as unique identifier
    		int uid = pnGroupId;
    		
    		//get entity default properties
    		clsProperties oEntityProperties = getEntityProperties(nEntityType);
    		oEntityProperties.put( clsEntity.P_ID, nEntityType.name()+"_"+nDecisionType.name()+"_"+i+" (#"+uid+")" );    		
    		//remove entity keys
    		if (oRemoveEntityDefaults.size() != 0) {
	    		for (Object oRemoveKey:oRemoveEntityDefaults.keySet()) {
	    			oEntityProperties.removeKeysStartingWith(oRemoveKey.toString());
	    		}
    		}

    		//overwrite entity values
    		oEntityProperties.putAll( oOverwriteEntityDefaults );
    		oEntityProperties.putAll( getPosition(pre, poProp, "", i) );    		
    		
    		//get default decision unit properties (if existing)
    		clsProperties oDecisionUnitProperties = getDecisionUnitProperties(nDecisionType);
    		//remove decision unit keys
    		if (oRemoveDecisionUnitDefaults.size()!=0) {
	    		for (Object oRemoveKey:oRemoveDecisionUnitDefaults.keySet()) {
	    			oDecisionUnitProperties.removeKeysStartingWith(oRemoveKey.toString());
	    		}
    		}
    		//overwrite decision unit values
    		oDecisionUnitProperties.putAll( oOverwriteDecisionUnitDefaults );
    		
    		//create entity with resulting configuration for entity and decision unit type
    		createEntity(oEntityProperties, oDecisionUnitProperties,oPropMemory, nEntityType, nDecisionType, uid);
    	}
    }
    
	/**
	 * Executes the load and entity creation. First all entites are created; afterwards the world boundary walls.
	 *
	 * @author deutsch
	 * Jul 25, 2009, 11:56:16 AM
	 * 
	 * @see sim.creation.clsLoader#loadObjects()
	 */
	@Override
	public void loadObjects() {
		String pre = clsProperties.addDot( getPrefix() );
		
		for (int i=0;i<numentitygroups; i++) {
			createEntityGroup(pre+P_ENTITYGROUPS+"."+i, getProperties(), i);
		}	
		if (getProperties().getPropertyBoolean(pre+P_WORLDBOUNDARYWALLS)) {
			generateWorldBoundaries();
		}
		
	}

	/**
	 * Creates a brick wall around the FieldEnvironment. Remember positioning of shapes is in the center point of the polygon!
	 *
	 * @author muchitsch
	 * Aug 9, 2009, 12:35:19 PM
	 *
	 */
	private void generateWorldBoundaries() {
		clsWallAxisAlign oWall = null;
		clsProperties oProp = null;
		
		double rWidth = clsSingletonMasonGetter.getFieldEnvironment().getWidth();
		double rHeight = clsSingletonMasonGetter.getFieldEnvironment().getHeight();
		double rWallThickness = 6;
		
		// add horizontal walls
		oProp = clsWallHorizontal.getDefaultProperties("");
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_WIDTH, rWidth);
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_LENGTH, rWallThickness);
		oProp.setProperty(clsPose.P_POS_X, rWidth/2);
		oProp.setProperty(clsPose.P_POS_Y, rWallThickness/2);

		// TODO remove image as long scaling is not implemented ...
		//oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "");
		
		oWall = new clsWallHorizontal("", oProp, clsUniqueIdGenerator.getUniqueId());
		clsRegisterEntity.registerEntity(oWall);
		
		oProp.setProperty(clsPose.P_POS_Y, rHeight-(rWallThickness/2) );		

		oWall = new clsWallHorizontal("", oProp, clsUniqueIdGenerator.getUniqueId());
		clsRegisterEntity.registerEntity(oWall);
		
		// add vertical walls
		oProp = clsWallVertical.getDefaultProperties("");
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_WIDTH, rWallThickness);
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShape2DCreator.P_LENGTH, rHeight);
		oProp.setProperty(clsPose.P_POS_X, rWallThickness/2);
		oProp.setProperty(clsPose.P_POS_Y, rHeight/2);
		
		// TODO remove image as long scaling is not implemented ...
		//oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "");
		
		oWall = new clsWallVertical("", oProp, clsUniqueIdGenerator.getUniqueId());
		clsRegisterEntity.registerEntity(oWall);
		
		oProp.setProperty(clsPose.P_POS_X, rWidth-(rWallThickness/2));		

		oWall = new clsWallVertical("", oProp, clsUniqueIdGenerator.getUniqueId());
		clsRegisterEntity.registerEntity(oWall);
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
}
