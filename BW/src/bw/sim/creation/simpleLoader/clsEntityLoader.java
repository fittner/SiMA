/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsCan;
import bw.entities.clsStone;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.sim.creation.clsLoader;


/**
 * The object loader handles the registration of new animate & inanimate objects in the physics engine 
 * 
 * @author muchitsch
 * 
 */
public class clsEntityLoader {
	
	/**
	 * Use this method to create all inanimate objects in the world
	 *
	 * @param poFieldEnvironment
	 * @param poObjPE
	 * @param poSimState
	 */
	public static void loadInanimate(int pnNumCans, int pnNumStones){
		//load inanimate entitieshere
		loadCans(pnNumCans);
		loadStones(pnNumStones);

	}
	
	public static void loadStones(int pnNumStones){
		for (int i = 0; i < pnNumStones; i++)
        {
			clsPose oStartPose = clsLoader.generateRandomPose();
			double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
	        
		    clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius);
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
	
	public static void loadCans(int pnNumCans){
        for (int i = 0; i < pnNumCans; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
	        clsCan oCan = new clsCan(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
	        
	        clsRegisterEntity.registerEntity(oCan);
        }
		
	}

}
