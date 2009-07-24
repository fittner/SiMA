/**
 * @author deutsch
 * 12.05.2009, 18:18:51
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.lifeCycle;

import ARSsim.physics2D.util.clsPose;
import ARSsim.portrayal.simple.clsImagePortrayal;
import bw.entities.clsEntity;
import bw.entities.clsStationary;
import bw.entities.clsWall;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.config.clsBWProperties;


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
		
		
        clsBWProperties oProp = clsWall.getDefaultProperties("Wall.");
        oProp.setProperty("Wall."+clsEntity.P_ID, 1);
        oProp.setProperty("Wall."+clsStationary.P_POS_X, oPose.getPosition().x);
        oProp.setProperty("Wall."+clsStationary.P_POS_Y, oPose.getPosition().y);
        oProp.setProperty("Wall."+clsStationary.P_POS_ANGLE, oPose.getAngle().radians);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_WIDTH, 193);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_HEIGHT, 6);
        
		
        oWall = new clsWall("Wall.", oProp);
        clsRegisterEntity.registerEntity(oWall);
       clsImagePortrayal.PlaceImage(sim.clsBWMain.msArsPath + "/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());


        oPose = new clsPose(100, 200, 0);
        
        oProp = clsWall.getDefaultProperties("Wall.");
        oProp.setProperty("Wall."+clsEntity.P_ID, 2);
        oProp.setProperty("Wall."+clsStationary.P_POS_X, oPose.getPosition().x);
        oProp.setProperty("Wall."+clsStationary.P_POS_Y, oPose.getPosition().y);
        oProp.setProperty("Wall."+clsStationary.P_POS_ANGLE, oPose.getAngle().radians);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_WIDTH, 193);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_HEIGHT, 6);

        oWall = new clsWall("Wall.", oProp);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(sim.clsBWMain.msArsPath + "/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());

        // VERT
        oPose = new clsPose(0, 100, 0);
        oProp = clsWall.getDefaultProperties("Wall.");
        oProp.setProperty("Wall."+clsEntity.P_ID, 2);
        oProp.setProperty("Wall."+clsStationary.P_POS_X, oPose.getPosition().x);
        oProp.setProperty("Wall."+clsStationary.P_POS_Y, oPose.getPosition().y);
        oProp.setProperty("Wall."+clsStationary.P_POS_ANGLE, oPose.getAngle().radians);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_WIDTH, 6);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_HEIGHT, 200);

        oWall = new clsWall("Wall.", oProp);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(sim.clsBWMain.msArsPath + "/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());

        oPose = new clsPose(200, 100, 0);
        oProp = clsWall.getDefaultProperties("Wall.");
        oProp.setProperty("Wall."+clsEntity.P_ID, 2);
        oProp.setProperty("Wall."+clsStationary.P_POS_X, oPose.getPosition().x);
        oProp.setProperty("Wall."+clsStationary.P_POS_Y, oPose.getPosition().y);
        oProp.setProperty("Wall."+clsStationary.P_POS_ANGLE, oPose.getAngle().radians);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_WIDTH, 6);
        oProp.setProperty("Wall."+clsStationary.P_SHAPE_HEIGHT, 200);

        oWall = new clsWall("Wall.", oProp);
        clsRegisterEntity.registerEntity(oWall);
        clsImagePortrayal.PlaceImage(sim.clsBWMain.msArsPath + "/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());	
	}
}
