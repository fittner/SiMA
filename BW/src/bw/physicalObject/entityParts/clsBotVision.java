/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.entityParts;

import java.awt.*;

import ARSsim.physics2D.shape.CircleBorder;
import bw.body.io.*; 
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.physicalObject.mobile.*;
import bw.physicalObject.animate.clsBot;
import bw.sim.clsBWMain;

import sim.engine.*;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.constraint.PinJoint;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;
import sim.physics2D.util.Double2D;
import sim.physics2D.shape.*;
import sim.portrayal.DrawInfo2D;
import sim.util.*;
import tstBw.sim.*;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */

public class clsBotVision extends MobileObject2D implements Steppable{

	private final static double MASS = 0.0001;
	private final static double FRICTION = 0;
	private final static double RESTITUTION = 0;
	
	private double mnRadius = 50;
		
	private Bag meCollidingObj;
	//private Bag mePerceiveObj = new Bag();
	private Paint moColor;
	private CircleBorder moShape;
	private ARSsim.robot2D.clsMotionPlatform moTaggedBot;
	
	public clsBotVision(Double2D pos, Double2D vel)
    {    	
	 meCollidingObj = new Bag();
	 moColor = Color.yellow;
	 moShape = new CircleBorder(mnRadius, moColor);
				
	 this.setVelocity(vel);
     this.setPose(pos, new Angle(0));
     this.setShape(moShape, mnRadius * mnRadius * Math.PI);

     this.setMass(MASS); 
     this.setCoefficientOfFriction(FRICTION);
     this.setCoefficientOfRestitution(RESTITUTION);
    }

	public void loadVision(PhysicsEngine2D poPE, ARSsim.robot2D.clsMotionPlatform poRobot)
	{
		moTaggedBot = poRobot; 
		
		PinJoint oPj;	
		oPj = new PinJoint(moTaggedBot.getPosition(), this, moTaggedBot);
		
		poPE.setNoCollisions(this, moTaggedBot);
		poPE.register(this);
		poPE.register(oPj);
	}
	
	public int handleCollision(PhysicalObject2D other, Double2D colPoint)
	{
		if(!meCollidingObj.contains(other))
	   	   meCollidingObj.add(other);
	 
		return 0; // Vis collision
	}
	
	public void step(SimState state)
	{
		  this.setPose(moTaggedBot.getPosition(), moTaggedBot.getOrientation());
		  clsBWMain simRobots = (clsBWMain)state;
	      simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(this.getPosition().x, this.getPosition().y));
	      meCollidingObj.clear(); 
	          
	}
	    
	public Bag getCollidingObj()
	{
		return meCollidingObj; 
	}
	
	//--------------------------------------------------------------------------------------------------
	// local set and get methods
	//-------------------------------------------------------------------------------------------------
	public double getSize()
	{
		return mnRadius;
	}
	
	public void setSize(double pnRadius)
	{
		this.mnRadius = pnRadius;
	}
	
	//--------------------------------------------------------------------------------------------------
	// Methods from Mobile2D which have to be overwritten
	//-------------------------------------------------------------------------------------------------
	      
	/** Calculates and adds the static and dynamic friction forces on the object
	 * based on the coefficients of friction. 
	 */
	public void addFrictionForce()
	{        }
	
	public boolean hitObject(Object object, DrawInfo2D range)
    {
		//TODO Clemens, hier geh�rt mehr rein als nur true!
    	return true; // (insert location algorithm and intersection here)
    }
	
}

