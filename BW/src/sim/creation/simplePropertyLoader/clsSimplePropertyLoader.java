/**
 * @author tobias
 * Jul 25, 2009, 11:54:40 AM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simplePropertyLoader;

import java.awt.Color;
import java.util.List;
import du.utils.enums.eDecisionType;
import enums.eEntityType;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsComplexBody;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.internalSystems.clsInternalSystem;
import bw.entities.clsAnimate;
import bw.entities.clsBase;
import bw.entities.clsBubble;
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
import bw.factories.clsPropertiesGetter;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import sim.creation.clsLoader;
import sim.creation.eLoader;
import sim.engine.SimState;

/**
 * DOCUMENT (tobias) - insert description 
 * 
 * @author tobias
 * Jul 25, 2009, 11:54:40 AM
 * 
 */
public class clsSimplePropertyLoader extends clsLoader {
	public static final String P_WORLDBOUNDARYWALLS = "worldboundarywalls";
	public static final String P_REMOVEDEFAULTS = "removedefaults";	
	public static final String P_OVERWRITEDEFAULTS = "overwritedefaults";
	public static final String P_ENTITYGROUPS = "entitygroups";
	public static final String P_NUMENTITYGROUPS = "numentitygroups";
	public static final String P_NUMENTITES = "numentities";
	public static final String P_ENTITYGROUPTYPE = "entitygrouptype";
	public static final String P_POSITIONTYPE = "positiontype";
	public static final String P_POSITIONS = "positions";

	
	public static final String P_ENTITYDEFAULTS  = "entitydefaults";

	private int numentitygroups;
	
	public static final int mnVersion = 2;
	public static final int mnDownCompatibility = -1; // can read any old version
	
	public clsSimplePropertyLoader(SimState poSimState, clsBWProperties poProperties) {
		super(poSimState, poProperties);
		applyProperties(getPrefix(), getProperties());
    }

	
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
    	String pre = clsBWProperties.addDot(poPrefix);
    	
    	numentitygroups = poProp.getPropertyInt(pre+P_ENTITYGROUPS+"."+P_NUMENTITYGROUPS);
    	
    	if (!poProp.existsPrefix(pre+P_ENTITYDEFAULTS)) {
    		poProp.putAll( getEntityDefaults(pre+P_ENTITYDEFAULTS) );
    	}
	}	
	
    private static clsBWProperties getEntityDefaults(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsBubble.getDefaultProperties	(pre+eEntityType.BUBBLE.name()) );
		oProp.putAll( clsRemoteBot.getDefaultProperties	(pre+eEntityType.REMOTEBOT.name()) );
		oProp.putAll( clsPlant.getDefaultProperties		(pre+eEntityType.PLANT.name()) );
		oProp.putAll( clsHare.getDefaultProperties		(pre+eEntityType.HARE.name()) );		
		oProp.putAll( clsLynx.getDefaultProperties		(pre+eEntityType.LYNX.name()) );
		oProp.putAll( clsBase.getDefaultProperties		(pre+eEntityType.BASE.name()) );
		oProp.putAll( clsCan.getDefaultProperties		(pre+eEntityType.CAN.name()) );
		oProp.putAll( clsCake.getDefaultProperties		(pre+eEntityType.CAKE.name()) );
		oProp.putAll( clsStone.getDefaultProperties		(pre+eEntityType.STONE.name()) );
		oProp.putAll( clsFungus.getDefaultProperties	(pre+eEntityType.FUNGUS.name()) );
		oProp.putAll( clsUraniumOre.getDefaultProperties(pre+eEntityType.URANIUM.name()) );
		oProp.putAll( clsCarrot.getDefaultProperties	(pre+eEntityType.CARROT.name()) );
		
		return oProp;
    }
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
    	return getDefaultProperties(poPrefix, false);
    }
    
    public static clsBWProperties getDefaultProperties(String poPrefix, boolean pnAddDefaultEntities) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsLoader.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_LOADER_TYPE, eLoader.SIMPLE_PROPERTY_LOADER.name());
		oProp.setProperty(pre+P_LOADER_VERSION, mnVersion);
		oProp.setProperty(pre+P_TITLE, "default simple property loader");
		
		if (pnAddDefaultEntities) {
			oProp.putAll( getEntityDefaults(pre+P_ENTITYDEFAULTS) );
		}

		int i=0;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.BUBBLE.name());
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
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+clsComplexBody.P_INTERNAL+"."+
														clsInternalSystem.P_FLESH+"."+clsFlesh.P_WEIGHT, 15);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsShapeCreator.P_COLOR, Color.RED);
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.REMOTEBOT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.LIST.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_X, 50);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_Y, 50);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_ANGLE, 0);
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.PLANT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_REMOVEDEFAULTS, "shape, body");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_RADIUS, "1.5");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.orange);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/carrot_clipart.png");
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY_TYPE, eBodyType.MEAT.toString());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+clsMeatBody.P_REGROWRATE, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 100);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+clsFlesh.P_WEIGHT, 50 );
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 4 );
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.PROTEIN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 0.5);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.VITAMIN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 0.5);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"2."+clsFlesh.P_NUTRITIONFRACTION, 4);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_BODY+"."+"3."+clsFlesh.P_NUTRITIONFRACTION, 8);		
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.HARE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_DECISION_TYPE, eDecisionType.HARE_JADEX.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.LYNX.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_OVERWRITEDEFAULTS+"."+clsAnimate.P_DECISION_TYPE, eDecisionType.LYNX_JAM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.BASE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());		
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.CAN.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.CAKE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.STONE.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.FUNGUS.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());	

		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.URANIUM.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		
		i++;
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_ENTITYGROUPTYPE, eEntityType.CARROT.name());
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());	
		
		oProp.setProperty(pre+P_ENTITYGROUPS+"."+P_NUMENTITYGROUPS, i+1);
		
		oProp.setProperty(pre+P_WORLDBOUNDARYWALLS, new Boolean(true).toString());

		return oProp;
    }
    
    private clsBWProperties getEntityProperties(eEntityType pnType) {
    	String pre = clsBWProperties.addDot( getPrefix() );    	
    	String oKey = pre+P_ENTITYDEFAULTS+"."+pnType.name();
    	clsBWProperties oResult = getProperties().getSubset(oKey);
    	return oResult;
    	
    }
    
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
    
    private void createEntity(String poPrefix, clsBWProperties poProp, eEntityType pnType) {
    	String pre = clsBWProperties.addDot(poPrefix);

    	clsEntity oEntity = null;
    	
    	switch(pnType) {
    		case BUBBLE:
    			oEntity = new clsBubble(pre, poProp);
    			clsRegisterEntity.registerEntity((clsBubble)oEntity);		
    			break;
    		case REMOTEBOT:
    			oEntity = new clsRemoteBot(pre, poProp);
    			clsRegisterEntity.registerEntity((clsRemoteBot)oEntity);		
    			break;
    		case PLANT:
    			oEntity = new clsPlant(pre, poProp);
    			clsRegisterEntity.registerEntity((clsPlant)oEntity);		
    			break;
    		case HARE:
    			oEntity = new clsHare(pre, poProp);
    			clsRegisterEntity.registerEntity((clsHare)oEntity);		
    			break;
    		case LYNX:
    			oEntity = new clsLynx(pre, poProp);
    			clsRegisterEntity.registerEntity((clsLynx)oEntity);		
    			break;
    		case BASE:
    			oEntity = new clsBase(pre, poProp);
    			clsRegisterEntity.registerEntity((clsBase)oEntity);		
    			break;
    		case CAN:
    			oEntity = new clsCan(pre, poProp);
    			clsRegisterEntity.registerEntity((clsCan)oEntity);		
    			break;
    		case CAKE:
    			oEntity = new clsCake(pre, poProp);
    			clsRegisterEntity.registerEntity((clsCake)oEntity);		
    			break;
    		case STONE:
    			oEntity = new clsStone(pre, poProp);
    			clsRegisterEntity.registerEntity((clsStone)oEntity);		
    			break;
    		case FUNGUS:
    			oEntity = new clsFungus(pre, poProp);
    			clsRegisterEntity.registerEntity((clsFungus)oEntity);		
    			break;
    		case URANIUM:
    			oEntity = new clsUraniumOre(pre, poProp);
    			clsRegisterEntity.registerEntity((clsUraniumOre)oEntity);		
    			break;    			
    		case CARROT:
    			oEntity = new clsCarrot(pre, poProp);
    			clsRegisterEntity.registerEntity((clsCarrot)oEntity);		
    			break;     			
			default:
				throw new java.lang.IllegalArgumentException("eEntityType."+pnType.toString());    	
    	}
    	
		
    }
    
    private void createEntityGroup(String poPrefix, clsBWProperties poProp) {
    	String pre = clsBWProperties.addDot(poPrefix);
    	eEntityType nType = eEntityType.valueOf(poProp.getPropertyString(pre+P_ENTITYGROUPTYPE));
    	clsBWProperties oOverwrite = poProp.getSubset(pre+P_OVERWRITEDEFAULTS);
    	List<String> oRemove = poProp.getPropertyList(pre+P_REMOVEDEFAULTS);
    	
    	int num = poProp.getPropertyInt(pre+P_NUMENTITES);
    	for (int i=0; i<num; i++) {
    		clsBWProperties oEntityProperties = getEntityProperties(nType);
    		oEntityProperties.put( clsEntity.P_ID, nType.name()+"_"+i );
    		
    		for (String oRemoveKey:oRemove) {
    			oEntityProperties.removeKeysStartingWith(oRemoveKey);
    		}
    		
    		oEntityProperties.putAll( oOverwrite );
    		oEntityProperties.putAll( getPosition(pre, poProp, "", i) );    		
    		createEntity("", oEntityProperties, nType);
    	}
    }
    
	/* (non-Javadoc)
	 *
	 * @author tobias
	 * Jul 25, 2009, 11:56:16 AM
	 * 
	 * @see sim.creation.clsLoader#loadObjects()
	 */
	@Override
	public void loadObjects() {
		String pre = clsBWProperties.addDot( getPrefix() );
		
		for (int i=0;i<numentitygroups; i++) {
			createEntityGroup(pre+P_ENTITYGROUPS+"."+i, getProperties() );
		}	
		
		if (getProperties().getPropertyBoolean(pre+P_WORLDBOUNDARYWALLS)) {
			generateWorldBoundaries();
		}
	}

	private void generateWorldBoundaries() {
		clsWallAxisAlign oWall = null;
		clsBWProperties oProp = null;
		
		double rWidth = clsSingletonMasonGetter.getFieldEnvironment().getWidth();
		double rHeight = clsSingletonMasonGetter.getFieldEnvironment().getHeight();
		double rWallThickness = 6;
		
		// add horizontal walls
		oProp = clsWallHorizontal.getDefaultProperties("");
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_WIDTH, rWidth);
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_HEIGHT, rWallThickness);
		oProp.setProperty(clsPose.P_POS_X, rWidth/2);
		
		if(clsPropertiesGetter.drawImages())
			oProp.setProperty(clsPose.P_POS_X, 0);
		
		oProp.setProperty(clsPose.P_POS_Y, 0);
		// TODO remove image as long scaling is not implemented ...
		//oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "");
		
		oWall = new clsWallHorizontal("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		oProp.setProperty(clsPose.P_POS_Y, rHeight);		

		oWall = new clsWallHorizontal("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		// add vertical walls
		oProp = clsWallVertical.getDefaultProperties("");
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_WIDTH, rWallThickness);
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_HEIGHT, rHeight);
		oProp.setProperty(clsPose.P_POS_X, 0);
		oProp.setProperty(clsPose.P_POS_Y, rHeight/2);
		
		if(clsPropertiesGetter.drawImages())
			oProp.setProperty(clsPose.P_POS_Y, 0);
		// TODO remove image as long scaling is not implemented ...
		//oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "");
		
		oWall = new clsWallVertical("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		oProp.setProperty(clsPose.P_POS_X, rWidth);		

		oWall = new clsWallVertical("", oProp);
		clsRegisterEntity.registerEntity(oWall);
	}

	/* (non-Javadoc)
	 *
	 * @author tobias
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
	 * @author tobias
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
