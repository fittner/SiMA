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

import bw.body.physicalObject.effector.clsBotHands;
import bw.body.physicalObject.mobile.*;
import bw.body.physicalObject.stationary.*;

/**
 * Helper class to load every agent and register to mason-physics2D 
 * @author langr
 * 
 */
public class clsAgentLoader {

	public static void loadAgents(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, double xMin, double xMax, double yMin, double yMax){
		
		//FIXME (langr) for test cases only --> refactor!
		loadCans(poFieldEnvironment, poObjPE, poSimState, 30, xMin, xMax, yMin, yMax);
		loadBots(poFieldEnvironment, poObjPE, poSimState, 2, xMin, xMax, yMin, yMax);
	}
	
	public static void loadCans(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumCans, double xMin, double xMax, double yMin, double yMax){
		
		clsCan can = null;
		Double2D pos;
		 
        for (int i = 0; i < pnNumCans; i++)
        {
        double x = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 10), 10);
        double y = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 10), 60);
                    
        pos = new Double2D(x, y);
                    
        can = new clsCan(pos, new Double2D(0, 0));
        poFieldEnvironment.setObjectLocation(can, new sim.util.Double2D(pos.x, pos.y));
        poObjPE.register(can);
        poSimState.schedule.scheduleRepeating(can);
        }
		
	}
	
	public static void loadBots(Continuous2D poFieldEnvironment, PhysicsEngine2D poObjPE, SimState poSimState, int pnNumBots, double xMin, double xMax, double yMin, double yMax){
		
		   clsBot bot = null;
		   Double2D pos;
		
	       for (int i = 0; i < pnNumBots; i++)
           {
           double x = Math.max(Math.min(poSimState.random.nextDouble() * xMax, xMax - 20), 20);
           double y = Math.max(Math.min(poSimState.random.nextDouble() * yMax, yMax - 20), 50);
                       
           pos = new Double2D(x, y);
           bot = new clsBot(pos, new Double2D(0, 0),i);
           poObjPE.register(bot);
           poFieldEnvironment.setObjectLocation(bot, new sim.util.Double2D(pos.x, pos.y));
           poSimState.schedule.scheduleRepeating(bot);
                       
                       
           //FixedAngle fa = new FixedAngle();
           //NoPerpMotion npm = new NoPerpMotion();
                       
           clsBotHands effector;
                       
           pos = new Double2D(x + 12, y + 6);
           effector = new clsBotHands(pos, new Double2D(0, 0), 1, Color.gray);
           poObjPE.register(effector);
           poFieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
           poSimState.schedule.scheduleRepeating(effector);
           bot.e1 = effector;
                       
           poObjPE.setNoCollisions(bot, effector);
           //objPE.setNoCollisions(bot, landscape);
                       
           //fa.AddPhysicalObject(effector);
           //npm.AddPhysicalObject(bot);
                       
           PinJoint pj = new PinJoint(pos, effector, bot);
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
                       
           poObjPE.setNoCollisions(bot, effector);
                       
           pj = new PinJoint(pos, effector, bot);
           //fa = new FixedAngle();
           //fa.AddPhysicalObject(effector);
           //fa.AddPhysicalObject(bot);
               
           poObjPE.register(pj);
           //objPE.register(fa);
           }
		
	}
}
