/**
 * @author deutsch
 * 12.05.2009, 20:13:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.lifeCycle;

import du.utils.enums.eDecisionType;
import sim.creation.clsLoader;
import ARSsim.physics2D.util.clsPose;
import bw.entities.clsAnimate;
import bw.entities.clsCarrot;
import bw.entities.clsEntity;
import bw.entities.clsHare;
import bw.entities.clsLynx;
import bw.entities.clsStone;
import bw.entities.tools.clsShapeCreator;
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 20:13:05
 * 
 */
public class clsEntityLoader {
	public static void loadStones(int pnNum){
		for (int i = 0; i < pnNum; i++)
        {		
			//FIXME warum ist der Radius random? wenn ein Bild dr�ber ist, dann muss der Radius dem Bild entsprechen!
			//double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;

			clsBWProperties oProp = clsStone.getDefaultProperties("");
			oProp.putAll( clsLoader.generateRandomPose("", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );
			oProp.setProperty(""+clsEntity.P_ID, i);
	        oProp.setProperty(""+clsShapeCreator.P_RADIUS, 10);
	        
		    clsStone oStone = new clsStone("", oProp);
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
	public static void loadCarrots(int pnNum){
        for (int i = 0; i < pnNum; i++)
        {
        	clsBWProperties oProp = clsCarrot.getDefaultProperties("");

        	oProp.putAll( clsLoader.generateRandomPose("", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );
			oProp.setProperty(""+clsEntity.P_ID, i);
	                	
        	clsCarrot oCarrot = new clsCarrot("", oProp);	        
	        clsRegisterEntity.registerEntity(oCarrot);
        }
 	}	
	
	public static void loadHares(int pnNum, eDecisionType peDU){
        for (int i = 0; i < pnNum; i++)
        {
        	clsBWProperties oProp = clsHare.getDefaultProperties("");

        	oProp.putAll( clsLoader.generateRandomPose("", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );
			oProp.setProperty(""+clsEntity.P_ID, i);
			oProp.setProperty(""+clsAnimate.P_DECISION_TYPE, peDU.toString());
	                	
			clsHare oHare = new clsHare("", oProp);
	        clsRegisterEntity.registerEntity(oHare);
        }
 	}	
	
	public static void loadLynx(int pnNum, eDecisionType peDU){
        for (int i = 0; i < pnNum; i++)        
        {
        	clsBWProperties oProp = clsLynx.getDefaultProperties("");

        	oProp.putAll( clsLoader.generateRandomPose("", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );
			oProp.setProperty(""+clsEntity.P_ID, i);
			oProp.setProperty(""+clsAnimate.P_DECISION_TYPE, peDU.toString());
			
			clsLynx oLynx = new clsLynx("", oProp);		        
	        clsRegisterEntity.registerEntity(oLynx);
        }
 	}		
}
