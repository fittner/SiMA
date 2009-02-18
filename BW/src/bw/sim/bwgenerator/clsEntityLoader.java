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
		
		// für Demo Steine anzeigen die Physikalisch sind
//        moPos = new Double2D(175,100);
//        clsStone stone = new clsStone( 2 , new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
//        clsInvisibleWallPhysics wall1 = new clsInvisibleWallPhysics(moPos, 20, 20);
//        //poFieldEnvironment.setObjectLocation(wall1, new sim.util.Double2D(pos.x, pos.y));
//        poObjPE.register(wall1);	
        
        //stones v2.0
/* @TODO - clemens muss da noch ran ...		
      moPos = new Double2D(50,50);
      clsStone pstone = new clsStone(moPos, 30, 20);
      poFieldEnvironment.setObjectLocation(pstone, new sim.util.Double2D(moPos.x, moPos.y));
      poObjPE.register(pstone);
      poSimState.schedule.scheduleRepeating(pstone);
*/      
	}
	
	
	public static void loadAnimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D mPos;
	}

}
