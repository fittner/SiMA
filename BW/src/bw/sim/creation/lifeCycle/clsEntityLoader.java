/**
 * @author deutsch
 * 12.05.2009, 20:13:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.lifeCycle;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsHare;
import bw.entities.clsLynx;
import bw.entities.clsPlant;
import bw.entities.clsStone;
import bw.factories.clsRegisterEntity;
import bw.sim.creation.clsLoader;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 20:13:05
 * 
 */
public class clsEntityLoader {
	public static void loadStones(int pnNum){
		for (int i = 0; i < pnNum; i++)
        {
			clsPose oStartPose = clsLoader.generateRandomPose();
			
			//FIXME warum ist der Radius random? wenn ein Bild dr�ber ist, dann muss der Radius dem Bild entsprechen!
			//double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
			double rRadius =  15.0 ;
	        
		    clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius, new clsConfigMap());
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
	public static void loadPlants(int pnNum){
        for (int i = 0; i < pnNum; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
        	clsPlant oPlant = new clsPlant(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());	        
	        clsRegisterEntity.registerEntity(oPlant);
        }
 	}	
	
	public static void loadHares(int pnNum){
        for (int i = 0; i < pnNum; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
        	clsHare oHare = new clsHare(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());	        
	        clsRegisterEntity.registerEntity(oHare);
        }
 	}	
	
	public static void loadLynx(int pnNum){
        for (int i = 0; i < pnNum; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
        	clsLynx oLynx = new clsLynx(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());	        
	        clsRegisterEntity.registerEntity(oLynx);
        }
 	}		
}
