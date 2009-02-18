/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.entityParts;

import java.awt.*;
import java.util.Iterator;

import ARSsim.physics2D.shape.CircleBorder;
import bw.sim.clsBWMain;
import bw.physicalObject.animate.clsAnimate;

import sim.engine.*;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.constraint.PinJoint;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import sim.util.*;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */

public class clsAnimateVision extends MobileObject2D implements Steppable{

	private final static double MASS = 0.0001;
	private final static double FRICTION = 0;
	private final static double RESTITUTION = 0;
	
	private double mnRadius = 50;
		
	private Bag meCollidingObj;
	private Bag meVisionObj;
	private Paint moColor;
	private CircleBorder moShape;
	private clsAnimate moTaggedAnimate;
	
	public clsAnimateVision(Double2D pos, Double2D vel)
    {    	
	 meCollidingObj = new Bag();
	 meVisionObj = new Bag();
	 moColor = Color.yellow;
	 moShape = new CircleBorder(mnRadius, moColor);
				
	 this.setVelocity(vel);
     this.setPose(pos, new Angle(0));
     this.setShape(moShape, mnRadius * mnRadius * Math.PI);

     this.setMass(MASS); 
     this.setCoefficientOfFriction(FRICTION);
     this.setCoefficientOfRestitution(RESTITUTION);
    }

	public void loadVision(PhysicsEngine2D poPE, clsAnimate poRobot)
	{
		moTaggedAnimate = poRobot; 
		
		PinJoint oPJ;	
		oPJ = new PinJoint(moTaggedAnimate.getMobile().getPosition(), this, moTaggedAnimate.getMobile());
		
		poPE.setNoCollisions(this, moTaggedAnimate.getMobile());
		poPE.register(this);
		poPE.register(oPJ);
	}
	
	public int handleCollision(PhysicalObject2D other, Double2D colPoint)
	{
		return 0; // Vis collision
	}
	
	public void step(SimState state)
	{
		  this.setPose(moTaggedAnimate.getMobile().getPosition(), moTaggedAnimate.getMobile().getOrientation());
		  clsBWMain simRobots = (clsBWMain)state;
	      simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(this.getPosition().x, this.getPosition().y));
	      
	      meCollidingObj.clear();
	}
	    
	//--------------------------------------------------------------------------------------------------
	// local set and get methods
	//-------------------------------------------------------------------------------------------------
	
	public Bag getCollidingObj()
	{
		return meCollidingObj; 
	}
	
	public Bag getVisionObj()
	{
		return meVisionObj; 
	}
	
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
	
	//--------------------------------------------------------------------------------------------------
	// Methods from PhysicalObject2D which have to be overwritten
	//-------------------------------------------------------------------------------------------------
	
	/** receives all objects, the physical object is colliding with - objects 
	 * which are moving away from the   
	*/     
	public void receiveContact(PhysicalObject2D other, Double2D colPoint)
	{
		meCollidingObj.add(other);		
	}	
}

