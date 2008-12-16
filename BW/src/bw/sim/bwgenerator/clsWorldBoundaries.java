/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.bwgenerator;

import java.awt.Color;

import sim.engine.SimState;
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

	public static void loadWorldBoundaries(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D pos;
		clsWallPhysics wall;
		
        // HORIZ
        pos = new Double2D(100,0);
        wall = new clsWallPhysics(pos, 193, 6);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);
        clsImagePortrayal imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
 
        pos = new Double2D(100,200);
        wall = new clsWallPhysics(pos, 193, 6);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
                
        // VERT
        pos = new Double2D(0,100);
        wall = new clsWallPhysics(pos, 6, 200);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        
        pos = new Double2D(200,100);
        wall = new clsWallPhysics(pos, 6, 200);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(wall);	
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        
        //testing tha stones
        //ohne setobjectLocation wir nichts angezeigt!
        pos = new Double2D(170,50);
        clsStone stone = new clsStone( 1 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        clsStonePhysics invwall = new clsStonePhysics(pos, 10, 10);
        //poFieldEnvironment.setObjectLocation(invwall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(invwall);	
        
        pos = new Double2D(100,80);
        stone = new clsStone( 1 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        invwall = new clsStonePhysics(pos, 10, 10);
        //poFieldEnvironment.setObjectLocation(invwall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(invwall);	
        
        pos = new Double2D(100,160);
        stone = new clsStone( 2 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        invwall = new clsStonePhysics(pos, 20, 20);
        //poFieldEnvironment.setObjectLocation(invwall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(invwall);		
        
        pos = new Double2D(25,110);
        stone = new clsStone( 3 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        invwall = new clsStonePhysics(pos, 25, 25);
        //poFieldEnvironment.setObjectLocation(invwall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(invwall);	
                
        pos = new Double2D(32,20);
        stone = new clsStone( 4 , new sim.util.Double2D(pos.x, pos.y), poFieldEnvironment);
        invwall = new clsStonePhysics(pos, 50, 30);
        //poFieldEnvironment.setObjectLocation(invwall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(invwall);	
        
        //stones v2.0
        pos = new Double2D(50,50);
        clsStonePhysics pstone = new clsStonePhysics(pos, 10, 20);
        poFieldEnvironment.setObjectLocation(pstone, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(pstone);
        poSimState.schedule.scheduleRepeating(pstone);
        
        
	}
	
}
