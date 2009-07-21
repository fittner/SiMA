/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;

import sim.creation.clsLoader;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsStone;
import bw.entities.clsFungus;
import bw.entities.clsUraniumOre;
import bw.entities.clsBase;
import bw.factories.clsRegisterEntity;
import bw.utils.container.clsConfigMap;


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
	public static void loadInanimate(int pnNumCans, int pnNumStones, int pnNumFood, int pnNumFungi, int pnNumUraniumOre, int pnNumBases){
		//load inanimate entitieshere
		loadCans(pnNumCans);
		loadStones(pnNumStones);
		loadFood(pnNumFood);
		loadFungi(pnNumFungi);
		loadUraniumOre(pnNumUraniumOre);
		loadBases(pnNumBases);
	}
	
	public static void loadStones(int pnNumStones){
		for (int i = 0; i < pnNumStones; i++)
        {
			clsPose oStartPose = clsLoader.generateRandomPose();
			
			//FIXME warum ist der Radius random? wenn ein Bild dr�ber ist, dann muss der Radius dem Bild entsprechen!
			//double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
			double rRadius =  15.0 ;
	        
		    clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius, new clsConfigMap());
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
	
	public static void loadCans(int pnNumCans){
        for (int i = 0; i < pnNumCans; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
	        clsCan oCan = new clsCan(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());
	        
	        clsRegisterEntity.registerEntity(oCan);
        }
 	}
	
	 public static void loadFood(int pnNumFood) 
     {
        for (int i = 0; i < pnNumFood; i++)
        {		 
    	  	clsPose oStartPose = clsLoader.generateRandomPose();
    	    clsCake oCake = new clsCake(1, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());
    		clsRegisterEntity.registerEntity(oCake);
        }
     }
	 
	 public static void loadFungi(int pnNumFungi) 
     {
        for (int i = 0; i < pnNumFungi; i++)
        {		 
    	  	clsPose oStartPose = clsLoader.generateRandomPose();
    	    clsFungus oFungus = new clsFungus(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());
    		clsRegisterEntity.registerEntity(oFungus);
        }
     }
	 
	 public static void loadUraniumOre(int pnNumUraniumOre){
	        for (int i = 0; i < pnNumUraniumOre; i++)
	        {
	        	clsPose oStartPose = clsLoader.generateRandomPose();
		        clsUraniumOre oUraniumOre = new clsUraniumOre(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), 10, new clsConfigMap());
		        
		        clsRegisterEntity.registerEntity(oUraniumOre);
	        }
	}
	 
	 public static void loadBases(int pnNumBases) 
     {
        for (int i = 0; i < pnNumBases; i++)
        {		 
    	  	clsPose oStartPose = clsLoader.generateRandomPose();
    	    clsBase oBase = new clsBase(i, oStartPose, new clsConfigMap());
    		clsRegisterEntity.registerEntity(oBase);
        }
     }

}
