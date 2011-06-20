/**
 * CHANGELOG
 * 
 * 2011/06/20 TD - added some javadoc
 */
package creation.simplePropertyLoader;

import java.awt.Color;
import java.util.List;

import pa.clsPsychoAnalysis;
import pa._v30.clsProcessor;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.modules.clsPsychicApparatus;
import config.clsBWProperties;
import creation.clsLoader;
import creation.eLoader;
import decisionunit.clsDecisionUnitFactory;
import du.enums.eDecisionType;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsComplexBody;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.internalSystems.clsInternalSystem;
import bw.entities.clsAnimate;
import bw.entities.clsBase;
import bw.entities.clsBubble;
import bw.entities.clsFungusEater;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsCarrot;
import bw.entities.clsEntity;
import bw.entities.clsFungus;
import bw.entities.clsHare;
import bw.entities.clsLynx;
import bw.entities.clsPlant;
import bw.entities.clsRemoteBot;
import bw.entities.clsStone;
import bw.entities.clsUraniumOre;
import bw.entities.clsWallAxisAlign;
import bw.entities.clsWallHorizontal;
import bw.entities.clsWallVertical;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import sim.engine.SimState;
import statictools.clsUniqueIdGenerator;

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
//	public static final String P_ENTITY = "entity";
//	public static final String P_DECISIONUNIT = "decisionunit";
	/** If usedefaults is set to true, the entity/decision unit defaults are used. otherwise ALL params have to be provided by overwritedecisionunitdefaults and overwriteentitydefaults. */
	public static final String P_USEDEFAULTS = "usedefaults";

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
	public clsSimplePropertyLoader(SimState poSimState, clsBWProperties poProperties) {
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
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
    	String pre = clsBWProperties.addDot(poPrefix);
    	
    	numentitygroups = poProp.getPropertyInt(pre+P_ENTITYGROUPS+"."+P_NUMENTITYGROUPS);
    	
    	boolean usedefaults = poProp.getPropertyBoolean(pre+P_USEDEFAULTS); 
    	
    	if (usedefaults) {
    		clsBWProperties oP = new clsBWProperties();

    		oP.putAll( getEntityDefaults(pre+P_DEFAULTSENTITY) );
    		oP.putAll( getDecisionUnitDefaults(pre+P_DEFAULTSDECISIONUNIT) );
	    	oP.putAll(poProp);

	    	poProp.clear();
	    	poProp.putAll(oP);
    	}	
	}	
	
	
    /**
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	    
    private static clsBWProperties getEntityDefaults(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsBubble.getDefaultProperties		(pre+eEntityType.BUBBLE.name()) );
		oProp.putAll( clsFungusEater.getDefaultProperties	(pre+eEntityType.FUNGUS_EATER.name()) );
		oProp.putAll( clsRemoteBot.getDefaultProperties		(pre+eEntityType.REMOTEBOT.name()) );
		oProp.putAll( clsPlant.getDefaultProperties			(pre+eEntityType.PLANT.name()) );
		oProp.putAll( clsHare.getDefaultProperties			(pre+eEntityType.HARE.name()) );		
		oProp.putAll( clsLynx.getDefaultProperties			(pre+eEntityType.LYNX.name()) );
		oProp.putAll( clsBase.getDefaultProperties			(pre+eEntityType.BASE.name()) );
		oProp.putAll( clsCan.getDefaultProperties			(pre+eEntityType.CAN.name()) );
		oProp.putAll( clsCake.getDefaultProperties			(pre+eEntityType.CAKE.name()) );
		oProp.putAll( clsStone.getDefaultProperties			(pre+eEntityType.STONE.name()) );
		oProp.putAll( clsFungus.getDefaultProperties		(pre+eEntityType.FUNGUS.name()) );
		oProp.putAll( clsUraniumOre.getDefaultProperties	(pre+eEntityType.URANIUM.name()) );
		oProp.putAll( clsCarrot.getDefaultProperties		(pre+eEntityType.CARROT.name()) );
		
		return oProp;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	    
    private static clsBWProperties getDecisionUnitDefaults(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
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
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
    	return getDefaultProperties(poPrefix, false, false);
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	    
    public static clsBWProperties getDefaultProperties(String poPrefix, boolean pnAddDefaultEntities, boolean pnAddDefaultDecisionUnits) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsLoader.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_LOADER_TYPE, eLoader.SIMPLE_PROPERTY_LOADER.name());
		oProp.setProperty(pre+P_LOADER_VERSION, mnVersion);
		oProp.setProperty(pre+P_TITLE, "default simple property loader");
		oProp.setProperty(pre+P_USEDEFAULTS, true);
		
		if (pnAddDefaultEntities) {
			oProp.putAll( getEntityDefaults(pre+P_DEFAULTSENTITY) );
		}	
		if (pnAddDefaultDecisionUnits) {
			oProp.putAll( getDecisionUnitDefaults(pre+P_DEFAULTSDECISIONUNIT) );
		}
		

		int i=0;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.BUBBLE.name());
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
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsShapeCreator.P_COLOR, Color.RED);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+
				clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_LIBIDOSTREAM, 0.1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+
				clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_KNOWLEDGEABASE+"."+
				clsKnowledgeBaseHandler.P_SOURCE_NAME, "/DecisionUnits/config/_v30/bw/pa.memory/AGENT_BASIC/BASIC.pprj");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDECISIONUNITDEFAULTS+"."+
				clsPsychoAnalysis.P_PROCESSOR+"."+clsProcessor.P_PSYCHICAPPARATUS+"."+clsPsychicApparatus.P_MINIMALMODEL, false);
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.REMOTEBOT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPDECISIONUNITTYPE, eDecisionType.REMOTE.name());
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
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, clsEntity.P_SHAPENAME);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, "1.5");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.orange);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/carrot_clipart.png");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEENTITYDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
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
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPDECISIONUNITTYPE, eDecisionType.HARE_JADEX.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPENTITYTYPE, eEntityType.LYNX.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_GROUPDECISIONUNITTYPE, eDecisionType.LYNX_JAM.name());
		
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
		
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+P_NUMENTITYGROUPS, i+1);
		
		oProp.setProperty(pre+P_WORLDBOUNDARYWALLS, new Boolean(true).toString());

		return oProp;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	
    private clsBWProperties getEntityProperties(eEntityType pnType) {
    	String pre = clsBWProperties.addDot( getPrefix() );    	
    	String oKey = pre+P_DEFAULTSENTITY+"."+pnType.name();
    	clsBWProperties oResult = getProperties().getSubset(oKey);
    	return oResult;
    }
    
	
    /**
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	
    private clsBWProperties getDecisionUnitProperties(eDecisionType pnType) {
    	String pre = clsBWProperties.addDot( getPrefix() );    	
    	String oKey = pre+P_DEFAULTSDECISIONUNIT+"."+pnType.name();
    	clsBWProperties oResult = getProperties().getSubset(oKey);
    	return oResult;
    }    
    
    
    /**
     * Creates and sets the position in clsBWProperties style.
     *
     * @since 20.06.2011 19:53:18
     *
     * @param poPrefix
     * @param poProp
     * @param poPositionPrefix
     * @param pnNumber
     * @return
     */
    private clsBWProperties getPosition(String poPrefix, clsBWProperties poProp, String poPositionPrefix, int pnNumber) {
    	String pre = clsBWProperties.addDot(poPrefix)+P_POSITIONS+".";
    	poPositionPrefix = clsBWProperties.addDot(poPositionPrefix);
    	
    	ePositionType nPosType = ePositionType.valueOf( poProp.getPropertyString(pre+P_POSITIONTYPE) );
    	
    	clsBWProperties oPos = null;
    	
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
    private void createEntity(clsBWProperties poPropEntity, clsBWProperties poPropDecisionUnit, 
    		eEntityType pnEntityType, eDecisionType pnDecisionType, int uid) {
    	String pre = clsBWProperties.addDot("");
    	
    	itfDecisionUnit oDU = null;
    	if (pnDecisionType != eDecisionType.NONE) {
    			oDU = clsDecisionUnitFactory.createDecisionUnit_static(pnDecisionType, pre, poPropDecisionUnit, uid);
    	}
    	
    	clsEntity oEntity = null;
    	
    	switch(pnEntityType) {
    		case BUBBLE:
    			oEntity = new clsBubble(oDU, pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsBubble)oEntity);		
    			break;
    		case FUNGUS_EATER:
    			oEntity = new clsFungusEater(oDU, pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsFungusEater)oEntity);		
    			break;
    		case REMOTEBOT:
    			oEntity = new clsRemoteBot(oDU, pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsRemoteBot)oEntity);		
    			break;
    		case PLANT:
    			oEntity = new clsPlant(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsPlant)oEntity);		
    			break;
    		case HARE:
    			oEntity = new clsHare(oDU, pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsHare)oEntity);		
    			break;
    		case LYNX:
    			oEntity = new clsLynx(oDU, pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsLynx)oEntity);		
    			break;
    		case BASE:
    			oEntity = new clsBase(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsBase)oEntity);		
    			break;
    		case CAN:
    			oEntity = new clsCan(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsCan)oEntity);		
    			break;
    		case CAKE:
    			oEntity = new clsCake(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsCake)oEntity);		
    			break;
    		case STONE:
    			oEntity = new clsStone(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsStone)oEntity);		
    			break;
    		case FUNGUS:
    			oEntity = new clsFungus(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsFungus)oEntity);		
    			break;
    		case URANIUM:
    			oEntity = new clsUraniumOre(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsUraniumOre)oEntity);		
    			break;    			
    		case CARROT:
    			oEntity = new clsCarrot(pre, poPropEntity, uid);
    			clsRegisterEntity.registerEntity((clsCarrot)oEntity);		
    			break;     			
			default:
				throw new java.lang.IllegalArgumentException("eEntityType."+pnEntityType.toString());    	
    	}
    	
		
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
    private void createEntityGroup(String poPrefix, clsBWProperties poProp) {
    	String pre = clsBWProperties.addDot(poPrefix);
    	
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
    	clsBWProperties oOverwriteEntityDefaults = poProp.getSubset(pre+P_OVERWRITEENTITYDEFAULTS);
    	clsBWProperties oOverwriteDecisionUnitDefaults = poProp.getSubset(pre+P_OVERWRITEDECISIONUNITDEFAULTS);

    	//get list for remove specified entity subtrees
    	List<String> oRemoveEntityDefaults = null;
    	try {
    		oRemoveEntityDefaults = poProp.getPropertyList(pre+P_REMOVEENTITYDEFAULTS);
    	} catch (java.lang.NullPointerException e) {
    		// do nothing
    	}

    	//get list for remove specified decisionunit subtrees
    	List<String> oRemoveDecisionUnitDefaults = null;
    	try {
    		oRemoveDecisionUnitDefaults = poProp.getPropertyList(pre+P_REMOVEDECISIONUNITDEFAULTS);
    	} catch (java.lang.NullPointerException e) {
    		// do nothing
    	}
    	
    	//merge default values with overwrite values and remove selected values for each entity in this entity group.
    	int num = poProp.getPropertyInt(pre+P_NUMENTITES);
    	for (int i=0; i<num; i++) {
    		System.out.print(".");
    		
    		//get unique id for this entity (used for entity and for decision unit)
    		int uid = clsUniqueIdGenerator.getUniqueId();
    		
    		//get entity default properties
    		clsBWProperties oEntityProperties = getEntityProperties(nEntityType);
    		oEntityProperties.put( clsEntity.P_ID, nEntityType.name()+"_"+nDecisionType.name()+"_"+i+" (#"+uid+")" );    		
    		//remove entity keys
    		if (oRemoveEntityDefaults != null) {
	    		for (String oRemoveKey:oRemoveEntityDefaults) {
	    			oEntityProperties.removeKeysStartingWith(oRemoveKey);
	    		}
    		}	
    		//overwrite entity values
    		oEntityProperties.putAll( oOverwriteEntityDefaults );
    		oEntityProperties.putAll( getPosition(pre, poProp, "", i) );    		
    		
    		//get default decision unit properties (if existing)
    		clsBWProperties oDecisionUnitProperties = getDecisionUnitProperties(nDecisionType);
    		//remove decision unit keys
    		if (oRemoveDecisionUnitDefaults != null) {
	    		for (String oRemoveKey:oRemoveDecisionUnitDefaults) {
	    			oDecisionUnitProperties.removeKeysStartingWith(oRemoveKey);
	    		}
    		}    		    		
    		//overwrite decision unit values
    		oDecisionUnitProperties.putAll( oOverwriteDecisionUnitDefaults );
    		
    		//create entity wiht resulting configuration for entity and decision unit type
    		createEntity(oEntityProperties, oDecisionUnitProperties, nEntityType, nDecisionType, uid);
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
		String pre = clsBWProperties.addDot( getPrefix() );
		
		//process all entity groups
		System.out.print("Create "+numentitygroups+" groups:");
		for (int i=0;i<numentitygroups; i++) {
			System.out.print(" "+i);
			createEntityGroup(pre+P_ENTITYGROUPS+"."+i, getProperties() );
		}	
		System.out.print(" done;");
		
		//create world boundaries
		System.out.print(" create world boundaries ...");
		if (getProperties().getPropertyBoolean(pre+P_WORLDBOUNDARYWALLS)) {
			generateWorldBoundaries();
		}
		System.out.println(" done.");
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
		clsBWProperties oProp = null;
		
		double rWidth = clsSingletonMasonGetter.getFieldEnvironment().getWidth();
		double rHeight = clsSingletonMasonGetter.getFieldEnvironment().getHeight();
		double rWallThickness = 6;
		
		// add horizontal walls
		oProp = clsWallHorizontal.getDefaultProperties("");
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_WIDTH, rWidth);
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_HEIGHT, rWallThickness);
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
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_WIDTH, rWallThickness);
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsEntity.P_SHAPENAME+"."+clsShapeCreator.P_HEIGHT, rHeight);
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
	protected void checkVersionCompatibility(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot( poPrefix );
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
	 * @see sim.creation.clsLoader#verifyLoaderType(java.lang.String, bw.utils.config.clsBWProperties)
	 */
	@Override
	protected void verifyLoaderType(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot( poPrefix );
		eLoader nLoader = eLoader.valueOf(poProp.getPropertyString(pre+P_LOADER_TYPE));
		
		if (nLoader != eLoader.SIMPLE_PROPERTY_LOADER) {
			throw new java.lang.NullPointerException("wrong loader used. loader type:"+eLoader.SIMPLE_PROPERTY_LOADER+"; file created by loader"+poProp.getPropertyString(pre+P_LOADER_TYPE));
		}		
	}
}
