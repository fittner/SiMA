package ReinforcedRobot;
import java.awt.*;
import sim.engine.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;

public class clsCan extends MobileObject2D implements Steppable{
	 public boolean visible;
	    public clsCan(Double2D pos, Double2D vel)
	        {
	        // vary the mass with the size
	        this.setPose(pos, new Angle(0));
	        this.setVelocity(vel);
	        this.setShape(new sim.physics2D.shape.Circle(2, Color.blue), 80);
	        this.setCoefficientOfFriction(.5);
	        this.setCoefficientOfStaticFriction(0);
	        this.setCoefficientOfRestitution(1);
	        visible = true;
	        }
	 
	    public void step(SimState state)
	        {
	        Double2D position = this.getPosition();
	        clsRobots simRobots = (clsRobots)state;
	        simRobots.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	        }
}
