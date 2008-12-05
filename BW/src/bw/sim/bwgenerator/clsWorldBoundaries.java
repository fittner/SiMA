/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.bwgenerator;

import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.*;
import bw.body.physicalObject.stationary.*;

/**
 * Helper class to load the boundaries of the playground (walls for collisionhandler) 
 * 
 * @author langr
 * 
 */
public class clsWorldBoundaries {

	public static void loadWorldBoundaries(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE){
		Double2D pos;
		clsWallPhysics wall;
		
        // HORIZ
        pos = new Double2D(100,0);
        wall = new clsWallPhysics(pos, 193, 6);
        poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);
 
        pos = new Double2D(100,200);
        wall = new clsWallPhysics(pos, 193, 6);
        poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);
                
        // VERT
        pos = new Double2D(0,100);
        wall = new clsWallPhysics(pos, 6, 200);
        poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);
        
        pos = new Double2D(200,100);
        wall = new clsWallPhysics(pos, 6, 200);
        poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);		
	}
	
}
