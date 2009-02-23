/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.bwgenerator;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.entities.clsCan;
import bw.entities.clsStone;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.Double2D;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
	public static void loadInanimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		
		
		//load inanimate entitieshere
		
		//loadCans(poFieldEnvironment, poObjPE, poSimState, 3);
		
		//loadStones(poFieldEnvironment, poObjPE, poSimState, 2);

	}
	
	
	/**
	 * Use this method to create all animate objects in the world
	 *
	 * @param poFieldEnvironment
	 * @param poObjPE
	 * @param poSimState
	 */
	public static void loadAnimate(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		Double2D oPos;
		
		
		
		//load animate here
	}
	
	public static void loadStones(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumStones){
		
		Double2D oPos;
		double xMax = poFieldEnvironment.getHeight();
		double yMax = poFieldEnvironment.getWidth();
		
		for (int i = 0; i < pnNumStones; i++)
        {
			double x = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 10), 10);
	        double y = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 10), 60);
	                    
	        oPos = new Double2D(x, y);
	        
		    clsStone pstone = new clsStone(oPos, new Double2D(0, 0), 17, 20, i);
        }
	}
	
	
public static void loadCans(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumCans){
		
		clsCan can = null;
		Double2D pos;
		double xMin = 0;
		double xMax = poFieldEnvironment.getHeight();
		double yMin = 0;
		double yMax = poFieldEnvironment.getWidth();
		 
        for (int i = 0; i < pnNumCans; i++)
        {
	        double x = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 10), 10);
	        double y = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 10), 60);
	                    
	        pos = new Double2D(x, y);
	                    
	        can = new clsCan(pos, new Double2D(0, 0), i);
	        
	        clsMobileObject2D oMobile = can.getMobile();
//	        clsMobileObject2D oMobile = new clsMobileObject2D(null);
//	    	oMobile.setPose(pos, new Angle(0));
//	    	oMobile.setVelocity(new Double2D(0, 0));
//	    	oMobile.setShape(new sim.physics2D.shape.Circle(2, Color.blue), 80);
//	    	oMobile.setCoefficientOfFriction(.5);
//	    	oMobile.setCoefficientOfStaticFriction(0);
//	    	oMobile.setCoefficientOfRestitution(1);	        
        
//	        poFieldEnvironment.setObjectLocation(oMobile, new sim.util.Double2D(pos.x, pos.y));
//	        poObjPE.register(oMobile);
//	        poSimState.schedule.scheduleRepeating(oMobile);
        }
		
	}

}
