/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.bwgenerator;

import bw.physicalObject.inanimate.mobile.clsStone;
import bw.physicalObject.inanimate.stationary.clsInvisibleWallPhysics;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.Double2D;

/**
 * The object loader handles the registration of new objects in the physics engine 
 * 
 * @author muchitsch
 * 
 */
public class clsEntityLoader {
	
	public static void loadInanimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D moPos;
		

        
        //load 1 stone	
      moPos = new Double2D(50,50);
      clsStone pstone = new clsStone(moPos, 30, 20);
      poFieldEnvironment.setObjectLocation(pstone.getMobile(), new sim.util.Double2D(moPos.x, moPos.y));
      poObjPE.register(pstone.getMobile());
      poSimState.schedule.scheduleRepeating(pstone.getMobile());
      
	}
	
	
	public static void loadAnimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D mPos;
	}

}
