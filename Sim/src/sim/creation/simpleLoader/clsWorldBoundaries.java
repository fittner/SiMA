/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;


import config.clsBWProperties;
import ARSsim.physics2D.util.clsPose;

import bw.entities.clsWallAxisAlign;
import bw.entities.clsWallHorizontal;
import bw.entities.clsWallVertical;
import bw.entities.tools.clsShapeCreator;
import bw.factories.clsRegisterEntity;


/**
 * Helper class to load the boundaries of the playground (walls for collisionhandler) 
 * FIXME we dont use this one any more right??? cm
 * @author langr
 * @deprecated
 */
public class clsWorldBoundaries {

	public static void loadWorldBoundaries(){
		clsWallAxisAlign oWall = null;
		clsBWProperties oProp = null;
		
		// add horizontal walls
		oProp = clsWallHorizontal.getDefaultProperties("");
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_WIDTH, 193);
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_HEIGHT, 6);
		oProp.setProperty(clsPose.P_POS_X, 100);
		oProp.setProperty(clsPose.P_POS_Y, 0);
		//remove image as long scaling is not implemented ...
		oProp.setProperty(clsWallHorizontal.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "");
		
		oWall = new clsWallHorizontal("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		oProp.setProperty(clsPose.P_POS_Y, 200);		

		oWall = new clsWallHorizontal("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		// add vertical walls
		oProp = clsWallVertical.getDefaultProperties("");
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_WIDTH, 6);
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_HEIGHT, 193);
		oProp.setProperty(clsPose.P_POS_X, 0);
		oProp.setProperty(clsPose.P_POS_Y, 100);
		//remove image as long scaling is not implemented ...
		oProp.setProperty(clsWallVertical.P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "/src/resources/images/wall1.png");
		
		oWall = new clsWallVertical("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		oProp.setProperty(clsPose.P_POS_X, 200);		

		oWall = new clsWallVertical("", oProp);
		clsRegisterEntity.registerEntity(oWall);
		
		// temporarily add images for the walls here - should be done in the wall class instead
		// TODO remove this code !
//		clsImagePortrayal.PlaceImage(
//				clsGetARSPath.getArsPath() + "/BW/src/resources/images/wall1.jpg",	8, 
//				new sim.util.Double2D(100, 0), 	clsSingletonMasonGetter.getFieldEnvironment());	
//		clsImagePortrayal.PlaceImage(
//				clsGetARSPath.getArsPath() + "/BW/src/resources/images/wall1.jpg",	8, 
//				new sim.util.Double2D(100, 200), 	clsSingletonMasonGetter.getFieldEnvironment());	
//		
//		clsImagePortrayal.PlaceImage(
//				clsGetARSPath.getArsPath() + "/BW/src/resources/images/wall2.jpg",	8, 
//				new sim.util.Double2D(0, 100), 	clsSingletonMasonGetter.getFieldEnvironment());	
//		clsImagePortrayal.PlaceImage(
//				clsGetARSPath.getArsPath() + "/BW/src/resources/images/wall2.jpg",	8, 
//				new sim.util.Double2D(200, 100), 	clsSingletonMasonGetter.getFieldEnvironment());	
	}
}