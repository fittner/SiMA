/**
 * @author deutsch
 * 12.05.2009, 18:18:51
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.lifeCycle;

import ARSsim.physics2D.util.clsPose;
import ARSsim.portrayal.simple.clsImagePortrayal;
import bw.entities.clsWall;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.container.clsConfigMap;


/**
 * Helper class to load the boundaries of the playground (walls for collisionhandler) 
 * 
 * @author langr
 * 
 */
public class clsWorldBoundaries {

	public static void loadWorldBoundaries(){
		double xMax = clsSingletonMasonGetter.getFieldEnvironment().getWidth();
		double yMax = clsSingletonMasonGetter.getFieldEnvironment().getHeight();
		double rThickness = 6;
		
		clsPose oPose;
		clsWall oWall;
		
        // HORIZ
		oPose = new clsPose(xMax/2, 0, 0);
        oWall = new clsWall(1, oPose, xMax - rThickness - 1, rThickness, new clsConfigMap());
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
 
         	
        oPose = new clsPose(xMax/2, yMax, 0);
        oWall = new clsWall(2, oPose, xMax - rThickness - 1, rThickness, new clsConfigMap());
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
                
        // VERT
        oPose = new clsPose(0, yMax/2, 0);
        oWall = new clsWall(3, oPose, rThickness, yMax, new clsConfigMap());
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
        
        oPose = new clsPose(xMax, yMax/2, 0);
        oWall = new clsWall(4, oPose, rThickness, yMax, new clsConfigMap());
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
	}
	
}
