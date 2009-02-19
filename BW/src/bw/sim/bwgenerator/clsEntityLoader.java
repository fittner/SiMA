/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.bwgenerator;

import bw.physicalObject.inanimate.mobile.clsStone;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.Double2D;

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
	public static void loadInanimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D oPos;
		
		//load inanimate here

        
       //load 1 stone	
      oPos = new Double2D(50,50);
      clsStone pstone = new clsStone(oPos, 17, 20);
      poFieldEnvironment.setObjectLocation(pstone.getMobile(), new sim.util.Double2D(oPos.x, oPos.y));
      poObjPE.register(pstone.getMobile());
      poSimState.schedule.scheduleRepeating(pstone.getMobile());
      
	}
	
	
	/**
	 * Use this method to create all animate objects in the world
	 *
	 * @param poFieldEnvironment
	 * @param poObjPE
	 * @param poSimState
	 */
	public static void loadAnimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D oPos;
		
		//load animate here
	}

}
