package TestApps.Physics2D.BubbleRace;

import java.awt.*;
import sim.engine.*;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.*;

import bw.world.surface.*;

/**
 * This class was adopted from the Bot class from the physics engine
 * @author kohlhauser
 *
 */
public class Bubble extends FrictionRobot implements Steppable, ForceGenerator
{	
	
	public Effector e1;
	public Effector e2;
	
	BubbleRace bubbleRace;
	
	//test
	SimState state;
	boolean finished = false;
	//test
	
	public Bubble(Double2D pos, Double2D vel)
	{
		// vary the mass with the size
		setPose(pos, new Angle(0));
		setVelocity(vel);
		setShape(new sim.physics2D.shape.Circle(10, Color.gray), 300);
		setCoefficientOfFriction(.2);
		setCoefficientOfRestitution(1);
		faceTowards(new Angle(Math.PI + Angle.halfPI));
	}
        
    public void step(SimState state)
	{
		Double2D position = this.getPosition();
		bubbleRace = (BubbleRace)state;
		bubbleRace.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
		//test
		this.state = state;
		//test
	}
        
    public void addForce()
	{
    	//the agent goes to the coordinates (x|20) and then stops.
    	if (getPosition().y > 40)
    	{
    		goTo(new Double2D(getPosition().x, 20));
    	}
    	else
    	{
    		//test
    		if (getPosition().y < 40 && finished == false)
    		{
    			System.out.println("Tick " + state.schedule.getSteps());
        		finished = true;
    		}
    		//test
    		if (getVelocity().length() > 0.01 || getAngularVelocity() > 0.01)
    			stop();
    	}
	}
}