/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import java.awt.Color;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.*;
import ARSsim.portrayal.simple.clsImagePortrayal;
import bw.entities.clsStone;
import bw.entities.clsWall;


/**
 * Helper class to load the boundaries of the playground (walls for collisionhandler) 
 * 
 * @author langr
 * 
 */
public class clsWorldBoundaries {

	public static void loadWorldBoundaries(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D moPos;
		clsWall moWall;
		
        // HORIZ
        moPos = new Double2D(100,0);
        
        moWall = new clsWall(moPos, 193, 6, 1);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        //poObjPE.register(moWall);
        clsImagePortrayal imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
 
         	
        moPos = new Double2D(100,200);
        moWall = new clsWall(moPos, 193, 6, 2);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        //poObjPE.register(moWall);
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
                
        // VERT
        moPos = new Double2D(0,100);
        moWall = new clsWall(moPos, 6, 200, 3);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        //poObjPE.register(moWall);
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
        
        moPos = new Double2D(200,100);
        moWall = new clsWall(moPos, 6, 200, 4);
        //poFieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        //poObjPE.register(moWall);	
        imgPortrayal = new clsImagePortrayal();
        imgPortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(moPos.x, moPos.y), poFieldEnvironment);
	}
	
}
