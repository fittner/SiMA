/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;

import sim.creation.clsLoader;

import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.entities.clsStone;
import bw.entities.clsFungus;
import bw.entities.clsUraniumOre;
import bw.entities.clsBase;
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;



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
			//FIXME warum ist der Radius random? wenn ein Bild dr�ber ist, dann muss der Radius dem Bild entsprechen!
			//double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
			clsBWProperties oProp = clsStone.getDefaultProperties("Stone.");
			oProp.putAll( clsLoader.generateRandomPose("Stone.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );			
			oProp.setProperty("Stone."+clsEntity.P_ID, i);
	        
		    clsStone oStone = new clsStone("Stone.", oProp);
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
	
	public static void loadCans(int pnNumCans){
        for (int i = 0; i < pnNumCans; i++)
        {
        	clsBWProperties oProp = clsCan.getDefaultProperties("Can.");
        	oProp.setProperty("Can."+clsEntity.P_ID, i);
			oProp.putAll( clsLoader.generateRandomPose("Can.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );			
	         

        	clsCan oCan = new clsCan("Can.",oProp); 
	        
	        clsRegisterEntity.registerEntity(oCan);
        }
	}
 	
	 public static void loadFood(int pnNumFood) 
     {
        for (int i = 0; i < pnNumFood; i++)
        {		 
    	    clsBWProperties oProp = clsCake.getDefaultProperties("Cake.");
    	  	oProp.setProperty("Cake."+clsEntity.P_ID, i);
			oProp.putAll( clsLoader.generateRandomPose("Cake.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );			
    	    clsCake oCake = new clsCake("Cake.",oProp);
    		
    	    clsRegisterEntity.registerEntity(oCake);
        }
     }
	 
	 public static void loadFungi(int pnNumFungi) 
     {
        for (int i = 0; i < pnNumFungi; i++)
        {		 
    	  	clsBWProperties oProp = clsFungus.getDefaultProperties("Fungus.");
    	  	oProp.setProperty("Fungus."+clsEntity.P_ID, i);
			oProp.putAll( clsLoader.generateRandomPose("Fungus.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );			
	        
    	    clsFungus oFungus = new clsFungus("Fungus.", oProp); 
    		clsRegisterEntity.registerEntity(oFungus);
        }
     }
	 
	 public static void loadUraniumOre(int pnNumUraniumOre){
	        for (int i = 0; i < pnNumUraniumOre; i++)
	        {
	        	clsBWProperties oProp = clsUraniumOre.getDefaultProperties("Uranium.");
	        	oProp.setProperty("Uranium."+clsEntity.P_ID, i);
				oProp.putAll( clsLoader.generateRandomPose("Uranium.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );			
	        	
		        clsUraniumOre oUraniumOre = new clsUraniumOre("Uranium.", oProp);
		        clsRegisterEntity.registerEntity(oUraniumOre);
	        }
	}
	 
	 public static void loadBases(int pnNumBases) 
     {
        for (int i = 0; i < pnNumBases; i++)
        {		 
    	  	clsBWProperties oProp = clsBase.getDefaultProperties("Base.");
    	  	oProp.setProperty("Base."+clsEntity.P_ID, i);
			oProp.putAll( clsLoader.generateRandomPose("Base.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );			
	   	        
    	    clsBase oBase = new clsBase("Base.", oProp);
    		clsRegisterEntity.registerEntity(oBase);
        }
     }

}
