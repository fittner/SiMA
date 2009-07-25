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
import bw.entities.clsBase;
import bw.entities.clsBubble;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsCarrot;
import bw.entities.clsFungus;
import bw.entities.clsHare;
import bw.entities.clsLynx;
import bw.entities.clsPlant;
import bw.entities.clsRemoteBot;
import bw.entities.clsStone;
import bw.entities.clsUraniumOre;
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
	public static final String P_NUMENTITYGROUPS = "numentitygroups";
	public static final String P_NUMENTITES = "numentities";
	public static final String P_ENTITYGROUPTYPE = "entitygrouptype";
	public static final String P_POSITIONTYPE = "positiontype";
	public static final String P_POSITIONS = "positions";
	public static final String P_X = "x";
	public static final String P_Y = "y";
	public static final String P_A = "a";
	
	public static final String P_ENTITYDEFAULTS  = "entitydefaults";

	private int numentitygroups;
	
	public clsSimplePropertyLoader(SimState poSimState, clsBWProperties poProperties) {
		super(poSimState, poProperties);
		applyProperties(moPrefix, moProperties);
    }

	
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
    	String pre = clsBWProperties.addDot(poPrefix);
    	numentitygroups = poProp.getPropertyInt(pre+P_NUMENTITYGROUPS);
	}	
	
    public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsLoader.getDefaultProperties(pre) );
		
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
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+P_X, 10);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+P_Y, 10);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+P_A, 0);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"1."+P_X, 50);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"1."+P_Y, 20);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"1."+P_A, Math.PI);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"2."+P_X, 100);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"2."+P_Y, 100);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"2."+P_A, Math.PI*2/3);
		
		i++;
		oProp.setProperty(pre+i+"."+P_ENTITYGROUPTYPE, eEntityType.REMOTEBOT.name());
		oProp.setProperty(pre+i+"."+P_NUMENTITES, 1);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+P_POSITIONTYPE, ePositionType.LIST.name());
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+P_X, 50);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+P_Y, 50);
		oProp.setProperty(pre+i+"."+P_POSITIONS+"."+"0."+P_A, 0);
		
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
		
		return oProp;
    }
    
    private void getEntityProperties(eEntityType pnType) {
    	// TODO continue
    	
    }
    
    private void createEntityGroup(String poPrefix, clsBWProperties poProp) {
    	// TODO continue
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
		String pre = clsBWProperties.addDot(moPrefix);
		
		for (int i=0;i<numentitygroups; i++) {
			createEntityGroup(pre+i, this.moProperties);
		}	
	}
}
