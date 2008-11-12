package TestApps.Physics2D.BubbleRace;

import java.awt.Color;
import java.io.File;

import ec.util.MersenneTwisterFast;
import sim.engine.Schedule;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.field.grid.IntGrid2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.constraint.PinJoint;
import sim.physics2D.util.Double2D;

import bfg.world.surface.*;

/**
 * This class was adopted from the physicsTutorial3 class from the physics engine
 * @author kohlhauser
 *
 */
public class BubbleRace extends SimState
{
	public Continuous2D fieldEnvironment;
	public IntGrid2D surfaceGrid;
	public File xmlFile = null;
	
	public BubbleRace(long seed)
	{
		super(new MersenneTwisterFast(seed), new Schedule());
	}
	
    // Resets and starts a simulation
	public void start()
	{
		super.start();
		fieldEnvironment = new Continuous2D(10, 225, 300);
		
        // Add physics specific code here
        // Create and schedule the physics engine
		PhysicsEngine2D objPE = new PhysicsEngine2D();
		schedule.scheduleRepeating(objPE);
		
		Double2D pos;
		Bubble bubble;
		
		//create world from XML-file
	    if (xmlFile != null)
	    {
	    	if (xmlFile.exists())
	    	{
	    		surfaceGrid = SurfaceHandler.getInstance().createWorld(xmlFile);
	    	}
	    	else
	    	{
	    		System.out.println("XML-file does not exist!");
	    		surfaceGrid = SurfaceHandler.getInstance().createWorld(200, 200);	//mine
	    	}
	    }
	    else
	    {
	    	System.out.println("xmlFile not initialised");
	    	surfaceGrid = SurfaceHandler.getInstance().createWorld(200, 200);	//mine
	    }
		
		//Set up the bots
		for (int i = 25; i < 225; i += 75)
		{
			pos = new Double2D(i, 290);
			bubble = new Bubble(pos, new Double2D(0,0));
			objPE.register(bubble);
			fieldEnvironment.setObjectLocation(bubble, new sim.util.Double2D(pos.x, pos.y));
			schedule.scheduleRepeating(bubble);
			
			//create the effectors. They are not used in the race, but indicate the front of the agent
			Effector effector;
			
			pos = new Double2D(i + 12, 290 + 6);
			effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
			objPE.register(effector);
			fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
			schedule.scheduleRepeating(effector);
			bubble.e1 = effector;
			
			objPE.setNoCollisions(bubble, effector);

			PinJoint pj = new PinJoint(pos, effector, bubble);	
			objPE.register(pj);
			
			pos = new Double2D(i + 12, 290 - 6);
			effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
			objPE.register(effector);
			fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
			schedule.scheduleRepeating(effector);
			bubble.e2 = effector;
			
			objPE.setNoCollisions(bubble, effector);
	
			pj = new PinJoint(pos, effector, bubble);
			objPE.register(pj);		
		}
			
	}
}
