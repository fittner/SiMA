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
import bw.world.physicalObject.mobile.clsImagePortrayal;
import bw.world.physicalObject.mobile.clsStone;

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
        
        //testing tha stones
        pos = new Double2D(170,50);
        clsStone stone = new clsStone( 1 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        // poObjPE.register(stone);	
        
        pos = new Double2D(160,40);
        stone = new clsStone( 1 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        // poObjPE.register(stone);	
        
        pos = new Double2D(100,160);
        stone = new clsStone( 2 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        // poObjPE.register(stone);	
        
        pos = new Double2D(25,110);
        stone = new clsStone( 3 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        // poObjPE.register(stone);	
        
        pos = new Double2D(30,55);
        stone = new clsStone( 4 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        // poObjPE.register(stone);	
        
	}
	
}
