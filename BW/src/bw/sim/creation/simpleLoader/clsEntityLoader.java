/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import org.w3c.dom.NodeList;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsStone;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.sim.creation.clsLoader;
import bw.utils.container.clsConfigContainer;


/**
 * The object loader handles the registration of new animate & inanimate objects in the physics engine 
 * 
 * @author muchitsch
 * 
 */
public class clsEntityLoader {
	
	/**
	 * Use this method to create all inanimate objects in the world
	 *
	 * @param poFieldEnvironment
	 * @param poObjPE
	 * @param poSimState
	 */
	public static void loadInanimate(int pnNumCans, int pnNumStones, int pnNumFood){
		//load inanimate entitieshere
		loadCans(pnNumCans);
		loadStones(pnNumStones);
		loadFood(pnNumFood);

	}
	
	public static void loadStones(int pnNumStones){
		for (int i = 0; i < pnNumStones; i++)
        {
			clsPose oStartPose = clsLoader.generateRandomPose();
			
			//FIXME warum ist der Radius random? wenn ein Bild drüber ist, dann muss der Radius dem Bild entsprechen!
			//double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
			double rRadius =  15.0 ;
	        
		    clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius, new clsConfigContainer());
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
	
	public static void loadCans(int pnNumCans){
        for (int i = 0; i < pnNumCans; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
	        clsCan oCan = new clsCan(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigContainer());
	        
	        clsRegisterEntity.registerEntity(oCan);
        }
 	}
	
	 public static void loadFood(int pnNumFood) 
     {
        for (int i = 0; i < pnNumFood; i++)
        {		 
    	  	clsPose oStartPose = clsLoader.generateRandomPose();
    	    clsCake oCake = new clsCake(1, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigContainer());
    		clsRegisterEntity.registerEntity(oCake);
        }
     }

}
