/**
 * @author tobias
 * Jul 25, 2009, 11:54:40 AM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simplePropertyLoader;

import enums.eEntityType;
import ARSsim.physics2D.util.clsPose;
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
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;
import sim.creation.clsLoader;
import sim.engine.SimState;

/**
 * TODO (tobias) - insert description 
 * 
 * @author tobias
 * Jul 25, 2009, 11:54:40 AM
 * 
 */
public class clsSimplePropertyLoader extends clsLoader {
	public static final String P_WORLDBOUNDARYWALLS = "worldboundarywalls";
	public static final String P_NUMENTITYGROUPS = "numentitygroups";
	public static final String P_NUMENTITES = "numentities";
	public static final String P_ENTITYGROUPTYPE = "entitygrouptype";
	public static final String P_POSITIONTYPE = "positiontype";
	public static final String P_POSITIONS = "positions";
	
	public static final String P_ENTITYDEFAULTS  = "entitydefaults";

	private int numentitygroups;
	
	public clsSimplePropertyLoader(SimState poSimState, clsBWProperties poProperties) {
		super(poSimState, poProperties);
		applyProperties(getPrefix(), getProperties());
    }

	
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
    	String pre = clsBWProperties.addDot(poPrefix);
    	numentitygroups = poProp.getPropertyInt(pre+P_NUMENTITYGROUPS);
	}	
	
    public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsLoader.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_TITLE, "default simple property loader");
		
		oProp.putAll( clsBubble.getDefaultProperties	(pre+P_ENTITYDEFAULTS+"."+eEntityType.BUBBLE.name()) );
		oProp.putAll( clsRemoteBot.getDefaultProperties	(pre+P_ENTITYDEFAULTS+"."+eEntityType.REMOTEBOT.name()) );
		oProp.putAll( clsPlant.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.PLANT.name()) );
		oProp.putAll( clsHare.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.HARE.name()) );		
		oProp.putAll( clsLynx.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.LYNX.name()) );
		oProp.putAll( clsBase.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.BASE.name()) );
		oProp.putAll( clsCan.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.CAN.name()) );
		oProp.putAll( clsCake.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.CAKE.name()) );
		oProp.putAll( clsStone.getDefaultProperties		(pre+P_ENTITYDEFAULTS+"."+eEntityType.STONE.name()) );
		oProp.putAll( clsFungus.getDefaultProperties	(pre+P_ENTITYDEFAULTS+"."+eEntityType.FUNGUS.name()) );
		oProp.putAll( clsUraniumOre.getDefaultProperties(pre+P_ENTITYDEFAULTS+"."+eEntityType.URANIUM.name()) );
		oProp.putAll( clsCarrot.getDefaultProperties	(pre+P_ENTITYDEFAULTS+"."+eEntityType.CARROT.name()) );

		int i=0;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.BUBBLE.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 3);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.LIST.name());
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_X, 10);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_Y, 10);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_ANGLE, 0);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"1."+clsPose.P_POS_X, 50);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"1."+clsPose.P_POS_Y, 20);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"1."+clsPose.P_POS_ANGLE, Math.PI);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"2."+clsPose.P_POS_X, 100);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"2."+clsPose.P_POS_Y, 100);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"2."+clsPose.P_POS_ANGLE, Math.PI*2/3);
		
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.REMOTEBOT.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.LIST.name());
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_X, 50);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_Y, 50);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+clsPose.P_POS_ANGLE, 0);
		
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.PLANT.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.HARE.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.LYNX.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.BASE.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());		
		
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.CAN.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.CAKE.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.STONE.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.FUNGUS.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());	

		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.URANIUM.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.CARROT.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.RANDOM.name());	
		
		oProp.setProperty(pre+P_NUMENTITYGROUPS, i+1);
		oProp.setProperty(pre+P_WORLDBOUNDARYWALLS, new Boolean(true).toString());

		return oProp;
    }
    
    private clsBWProperties getEntityProperties(eEntityType pnType) {
    	String pre = clsBWProperties.addDot( getPrefix() );    	
    	String oKey = pnType.name();
    	clsBWProperties oResult = getProperties().getSubset(pre+P_ENTITYDEFAULTS+"."+oKey);
    	return oResult;
    	
    }
    
    private clsBWProperties getPosition(String poPrefix, clsBWProperties poProp, String poPositionPrefix, int pnNumber) {
    	String pre = clsBWProperties.addDot(poPrefix);
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
    	
    	return null;
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
    	
    	
    	int num = poProp.getPropertyInt(pre+P_NUMENTITES);
    	for (int i=0; i<num; i++) {
    		String tmp_pre = pre+i+".";
    		clsBWProperties oEntityProperties = getEntityProperties(nType);
    		oEntityProperties.addPrefix(tmp_pre);
    		oEntityProperties.putAll( getPosition(pre, poProp, tmp_pre, i) );
    		
    		createEntity(tmp_pre, poProp, nType);
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
			createEntityGroup(pre+i, getProperties() );
		}	
	}
}
