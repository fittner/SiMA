package SimpleALife;

import java.awt.*;

import SimpleALife.SimLifeArena;
import sim.engine.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;

public class Head extends MobileObject2D implements Steppable
{
    private static final long serialVersionUID = 1L;

    // public double radius;
    public Head(Double2D pos, Double2D vel, double radius, Paint paint)
    {
        this.setVelocity(vel);
        this.setPose(pos, new Angle(0));
        this.setShape(new sim.physics2D.shape.Circle(radius, paint), radius * radius * Math.PI);
        this.setCoefficientOfFriction(0);
        this.setCoefficientOfRestitution(1);
	}

    public void step(SimState state)
    {
        Double2D position = this.getPosition();
		SimLifeArena.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
    }
}
