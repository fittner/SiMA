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
public class clsObjectLoader {
	
	public static void loadInanimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D moPos;
		
		// für Demo Steine anzeigen die Physikalisch sind
//        moPos = new Double2D(175,100);
//        clsStone stone = new clsStone( 2 , new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
//        clsInvisibleWallPhysics wall1 = new clsInvisibleWallPhysics(moPos, 20, 20);
//        //poFieldEnvironment.setObjectLocation(wall1, new sim.util.Double2D(pos.x, pos.y));
//        poObjPE.register(wall1);	
//        
//        moPos = new Double2D(155,50);
//        stone = new clsStone( 1 , new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
//        wall1 = new clsInvisibleWallPhysics(moPos, 10, 10);
//        poObjPE.register(wall1);
//        
//        moPos = new Double2D(95,60);
//        stone = new clsStone( 1 , new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
//        wall1 = new clsInvisibleWallPhysics(moPos, 10, 10);
//        poObjPE.register(wall1);
//        
//        moPos = new Double2D(185,180);
//        stone = new clsStone( 1 , new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
//        wall1 = new clsInvisibleWallPhysics(moPos, 10, 10);
//        poObjPE.register(wall1);
//        
//        moPos = new Double2D(32,170);
//        stone = new clsStone( 4 , new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
//        wall1 = new clsInvisibleWallPhysics(moPos, 50, 30);
//        poObjPE.register(wall1);
        
        //stones v2.0
      moPos = new Double2D(50,50);
      clsStone pstone = new clsStone(moPos, 30, 20);
      poFieldEnvironment.setObjectLocation(pstone, new sim.util.Double2D(moPos.x, moPos.y));
      poObjPE.register(pstone);
      poSimState.schedule.scheduleRepeating(pstone);

	}
	
	
	public static void loadAnimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D mPos;
	}

}
