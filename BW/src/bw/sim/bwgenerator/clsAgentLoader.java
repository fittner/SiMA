/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.bwgenerator;

import java.awt.*;

import sim.engine.*;
import sim.field.continuous.Continuous2D;
import sim.physics2D.constraint.*;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.*;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.physicalObject.animate.clsBot;
import bw.physicalObject.animate.clsBubble;
import bw.physicalObject.animate.clsRemoteBot;
import bw.physicalObject.entityParts.clsBotHands;
import bw.physicalObject.inanimate.mobile.clsCan;

import bw.physicalObject.animate.*;

/**
 * Helper class to load every agent and register to mason-physics2D 
 * @author langr
 * 
 */
public class clsAgentLoader {

	public static void loadAgents(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, double xMin, double xMax, double yMin, double yMax){
		
		//FIXME (langr) for test cases only --> refactor!
//		loadCans(poFieldEnvironment, poObjPE, poSimState, 3, xMin, xMax, yMin, yMax);
//		loadBots(poFieldEnvironment, poObjPE, poSimState, 2, xMin, xMax, yMin, yMax);
		loadRemoteBots(poFieldEnvironment, poObjPE, poSimState, 2, xMin, xMax, yMin, yMax);
//		loadBubbles(poFieldEnvironment, poObjPE, poSimState, 1, xMin, xMax, yMin, yMax);
	}
	
	public static void loadCans(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumCans, double xMin, double xMax, double yMin, double yMax){
		
		clsCan can = null;
		Double2D pos;
		 
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
        
	        poFieldEnvironment.setObjectLocation(oMobile, new sim.util.Double2D(pos.x, pos.y));
	        poObjPE.register(oMobile);
	        poSimState.schedule.scheduleRepeating(oMobile);
        }
		
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
	public static void loadBubbles(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumAgents, double xMin, double xMax, double yMin, double yMax){
		
		clsBubble bubble = null;
		Double2D startingPosition;
		
		 for (int i = 0; i < pnNumAgents; i++)
         {
			 //create a starting position near center
	         double xStartPos = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 20), 20);
	         double yStartPos = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 20), 50);
	         startingPosition = new Double2D(xStartPos, yStartPos);
	         
	         //create the Bubble and add it to mason and physics list
	         bubble = new clsBubble(startingPosition, new Double2D(0, 0),i);
	         poObjPE.register(bubble.getMobile());
	         poFieldEnvironment.setObjectLocation(bubble.getMobile(), new sim.util.Double2D(startingPosition.x, startingPosition.y));
	         poSimState.schedule.scheduleRepeating(bubble);
         }
	}
	
//	public static void loadBots(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumBots, double xMin, double xMax, double yMin, double yMax){
//		
//		   clsBot bot = null;
//		   Double2D pos;
//		
//	       for (int i = 0; i < pnNumBots; i++)
//           {
//           double x = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 20), 20);
//           double y = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 20), 50);
//                       
//           pos = new Double2D(x, y);
//           bot = new clsBot(pos, new Double2D(0, 0),i);
//           poObjPE.register(bot.getMobile());
//           poFieldEnvironment.setObjectLocation(bot.getMobile(), new sim.util.Double2D(pos.x, pos.y));
//           poSimState.schedule.scheduleRepeating(bot);
//                       
//                       
//           //FixedAngle fa = new FixedAngle();
//           //NoPerpMotion npm = new NoPerpMotion();
//                       
//           clsBotHands effector;
//                       
//           pos = new Double2D(x + 12, y + 6);
//           effector = new clsBotHands(pos, new Double2D(0, 0), 1, Color.gray);
//           poObjPE.register(effector);
//           poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//           poSimState.schedule.scheduleRepeating(effector);
//           bot.e1 = effector;
//                       
//           poObjPE.setNoCollisions(bot.getMobile(), effector);
//           //objPE.setNoCollisions(bot, landscape);
//                       
//           //fa.AddPhysicalObject(effector);
//           //npm.AddPhysicalObject(bot);
//                       
//           PinJoint pj = new PinJoint(pos, effector, bot.getMobile());
//           //fa.AddPhysicalObject(bot);
//               
//           poObjPE.register(pj);
//           //objPE.register(fa);
//           //objPE.register(npm);
//                       
//           pos = new Double2D(x + 12, y - 6);
//           effector = new clsBotHands(pos, new Double2D(0, 0), 1, Color.gray);
//           poObjPE.register(effector);
//           poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//           poSimState.schedule.scheduleRepeating(effector);
//           bot.e2 = effector;
//                       
//           poObjPE.setNoCollisions(bot.getMobile(), effector);
//                       
//           pj = new PinJoint(pos, effector, bot.getMobile());
//           //fa = new FixedAngle();
//           //fa.AddPhysicalObject(effector);
//           //fa.AddPhysicalObject(bot);
//           
//           
//         
//           
//         //VISION AREA Init + Register
//           bw.body.io.sensors.external.clsSensorVision visArea; 
//           visArea = new bw.body.io.sensors.external.clsSensorVision(bot.getMobile().getPosition(),new Double2D(0, 0), poObjPE, (clsAnimate)bot);
//           poFieldEnvironment.setObjectLocation(visArea.getVisionObj(), new sim.util.Double2D(bot.getMobile().getPosition().x, bot.getMobile().getPosition().y));
//           poSimState.schedule.scheduleRepeating(visArea.getVisionObj());           
//               
//           poObjPE.register(pj);
//           //objPE.register(fa);
//           
//           
//         //feet FIXME Clemens nur für magic tuesday!!! gehört in klasse ausgelagert
//			pos = new Double2D(x - 9, y + 6);
//			effector = new clsBotHands(pos, new Double2D(0, 0), 2, Color.gray);
//			poObjPE.register(effector);
//			poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//			poSimState.schedule.scheduleRepeating(effector);
//			//bot.e3 = effector;
//			
//			poObjPE.setNoCollisions(bot.getMobile(), effector);
//
//			pj = new PinJoint(pos, effector, bot.getMobile());	
//			poObjPE.register(pj);
//			
//			pos = new Double2D(x - 9, y - 6);
//			effector = new clsBotHands(pos, new Double2D(0, 0), 2, Color.gray);
//			poObjPE.register(effector);
//			poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//			poSimState.schedule.scheduleRepeating(effector);
//			//bot.e4 = effector;
//			
//			poObjPE.setNoCollisions(bot.getMobile(), effector);
//
//			pj = new PinJoint(pos, effector, bot.getMobile());	
//			poObjPE.register(pj);
//			
//			pos = new Double2D(x, y - 11);
//			effector = new clsBotHands(pos, new Double2D(0, 0), 2, Color.gray);
//			poObjPE.register(effector);
//			poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//			poSimState.schedule.scheduleRepeating(effector);
//			//bot.e5 = effector;
//			
//			poObjPE.setNoCollisions(bot.getMobile(), effector);
//
//			pj = new PinJoint(pos, effector, bot.getMobile());	
//			poObjPE.register(pj);
//			
//			pos = new Double2D(x, y + 11);
//			effector = new clsBotHands(pos, new Double2D(0, 0), 2, Color.gray);
//			poObjPE.register(effector);
//			poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//			poSimState.schedule.scheduleRepeating(effector);
//			//bot.e6 = effector;
//			
//			poObjPE.setNoCollisions(bot.getMobile(), effector);
//
//			pj = new PinJoint(pos, effector, bot.getMobile());	
//			poObjPE.register(pj);
//			//end feet
//           }
//	}
	
	public static void loadRemoteBots(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumBots, double xMin, double xMax, double yMin, double yMax){
		
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
                    
        poObjPE.setNoCollisions(bot.getMobile(), effector);
        //objPE.setNoCollisions(bot, landscape);
                    
        //fa.AddPhysicalObject(effector);
        //npm.AddPhysicalObject(bot);
                    
        PinJoint pj = new PinJoint(pos, effector, bot.getMobile());
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
                    
        poObjPE.setNoCollisions(bot.getMobile(), effector);
                    
        pj = new PinJoint(pos, effector, bot.getMobile());
        //fa = new FixedAngle();
        //fa.AddPhysicalObject(effector);
        //fa.AddPhysicalObject(bot);
        
            
        poObjPE.register(pj);
        //objPE.register(fa);
        }
		
	}	
	
}
