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
import ARSsim.portrayal.simple.clsImagePortrayal;
import bw.physicalObject.inanimate.mobile.clsStone;
import bw.physicalObject.inanimate.stationary.*;

/**
 * Helper class to load the boundaries of the playground (walls for collisionhandler) 
 * 
 * @author langr
 * 
 */
public class clsWorldBoundaries {

	public static void loadWorldBoundaries(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D moPos;
		clsWallPhysics moWall;
		
        // HORIZ
        moPos = new Double2D(100,0);
        moWall = new clsWallPhysics(moPos, 193, 6);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(moWall);
        clsImagePortrayal imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
 
        moPos = new Double2D(100,200);
        moWall = new clsWallPhysics(moPos, 193, 6);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(moWall);
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
                
        // VERT
        moPos = new Double2D(0,100);
        moWall = new clsWallPhysics(moPos, 6, 200);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(moWall);
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
        
        moPos = new Double2D(200,100);
        moWall = new clsWallPhysics(moPos, 6, 200);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(moWall);	
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
        
        //testing tha stones
        //ohne setobjectLocation wir nichts angezeigt!
 /*		pos = new Double2D(170,50);
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
        
        clsWallPhysics wall1 = new clsWallPhysics(pos, 20, 20);
        poObjPE.register(wall1);	
        
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
        */
 

        
        
        
        
	}
	
}
