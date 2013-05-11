package TestApps.Physics2D.BubbleRace;

import java.awt.*;
import sim.engine.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;

/**
 * This class has been copied from the Effector class from the physics package. The only purpose of the Effectors in the race is to indicate the front of the robot.
 * @author kohlhauser
 *
 */
public class Effector extends MobileObject2D implements Steppable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public double radius;
    public Effector(Double2D pos, Double2D vel, double radius, Paint paint)
    {
        setVelocity(vel);
        setPose(pos, new Angle(0));
        setShape(new sim.physics2D.shape.Circle(radius, paint), radius * radius * Math.PI);
        setCoefficientOfFriction(0);
        setCoefficientOfRestitution(1);
    }
 
    @Override
	public void step(SimState state)
    {
        Double2D position = this.getPosition();
        BubbleRace bubbleRace = (BubbleRace)state;
        bubbleRace.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
    }
}