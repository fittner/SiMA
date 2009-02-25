/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import java.awt.*;

import sim.engine.*;
import sim.field.continuous.Continuous2D;
import sim.physics2D.constraint.*;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.*;

import bw.entities.clsBubble;
import bw.entities.clsRemoteBot;


import bw.physicalObjects.bodyparts.clsBotHands;

/**
 * Helper class to load every agent and register to mason-physics2D 
 * @author langr
 * 
 */
public class clsAgentLoader {

	public static void loadAgents(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState){
		loadRemoteBots(poFieldEnvironment, poObjPE, poSimState, 2);
		loadBubbles(poFieldEnvironment, poObjPE, poSimState, 1);
	}
	
	
	
	/**
	 * loading of the Bubbles, everything loads here from mason to physics to ARS
	 * 
	 *
	 * @param poFieldEnvironment - is the Continous2D field, the game grid
	 * @param poObjPE - is the physics engine instance where all physical things have to be added
	 * @param poSimState - is the top level instance of mason
	 * @param pnNumAgents - is the number of bubbles to create
	 * @param xMin - are the dimensions of the playground/game grid
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 */
	public static void loadBubbles(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumAgents){
	
		double xMax = poFieldEnvironment.getWidth();
		double yMax = poFieldEnvironment.getHeight();
		
		 for (int i = 0; i < pnNumAgents; i++)
         {
			 //create a starting position near center
	         double xStartPos = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 20), 20);
	         double yStartPos = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 20), 50);
	         Double2D startingPosition = new Double2D(xStartPos, yStartPos);
	         
	         //create the Bubble and add it to mason and physics list
	         @SuppressWarnings("unused")
		  	 clsBubble bubble = new clsBubble(startingPosition, new Double2D(0, 0),i);
//	         poObjPE.register(bubble.getMobile());
//	         poFieldEnvironment.setObjectLocation(bubble.getMobile(), new sim.util.Double2D(startingPosition.x, startingPosition.y));
//	         poSimState.schedule.scheduleRepeating(bubble);
         }
	}
	
	
	public static void loadRemoteBots(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumBots){
		double xMax = poFieldEnvironment.getWidth();
		double yMax = poFieldEnvironment.getHeight();
		
		clsRemoteBot bot = null;
		   Double2D pos;
		
	       for (int i = 0; i < 1; i++)
        {
        double x = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 20), 20);
        double y = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 20), 50);
                    
        pos = new Double2D(x, y);
        bot = new clsRemoteBot(pos, new Double2D(0, 0),i);
       
                    
        //FixedAngle fa = new FixedAngle();
        //NoPerpMotion npm = new NoPerpMotion();
                    
        clsBotHands effector;
                    
        pos = new Double2D(x + 12, y + 6);
        effector = new clsBotHands(pos, new Double2D(0, 0), 1, Color.gray);
        poObjPE.register(effector);
        poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
        poSimState.schedule.scheduleRepeating(effector);
        bot.e1 = effector;
                    
        poObjPE.setNoCollisions(bot.getMobileObject2D(), effector);
        //objPE.setNoCollisions(bot, landscape);
                    
        //fa.AddPhysicalObject(effector);
        //npm.AddPhysicalObject(bot);
                    
        PinJoint pj = new PinJoint(pos, effector, bot.getMobileObject2D());
        //fa.AddPhysicalObject(bot);
            
        poObjPE.register(pj);
        //objPE.register(fa);
        //objPE.register(npm);
                    
        pos = new Double2D(x + 12, y - 6);
        effector = new clsBotHands(pos, new Double2D(0, 0), 1, Color.gray);
        poObjPE.register(effector);
        poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
        poSimState.schedule.scheduleRepeating(effector);
        bot.e2 = effector;
                    
        poObjPE.setNoCollisions(bot.getMobileObject2D(), effector);
                    
        pj = new PinJoint(pos, effector, bot.getMobileObject2D());
        //fa = new FixedAngle();
        //fa.AddPhysicalObject(effector);
        //fa.AddPhysicalObject(bot);
        
            
        poObjPE.register(pj);
        //objPE.register(fa);
        }
		
	}	
	
}
