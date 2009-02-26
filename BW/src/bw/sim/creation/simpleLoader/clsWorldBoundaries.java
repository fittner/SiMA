/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import ARSsim.physics2D.util.clsPose;
import ARSsim.portrayal.simple.clsImagePortrayal;
import bw.entities.clsWall;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;


/**
 * Helper class to load the boundaries of the playground (walls for collisionhandler) 
 * 
 * @author langr
 * 
 */
public class clsWorldBoundaries {

	public static void loadWorldBoundaries(){
		clsPose oPose;
		clsWall oWall;
		
        // HORIZ
		oPose = new clsPose(100, 0, 0);
        oWall = new clsWall(1, oPose, 193, 6);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
 
         	
        oPose = new clsPose(100, 200, 0);
        oWall = new clsWall(2, oPose, 193, 6);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
                
        // VERT
        oPose = new clsPose(0, 100, 0);
        oWall = new clsWall(3, oPose, 6, 200);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
        
        oPose = new clsPose(200, 100, 0);
        oWall = new clsWall(4, oPose, 6, 200);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage("S:/ARS/PA/BWv1/BW/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
	}
	
}
