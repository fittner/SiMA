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
 *  This class defines the the physical object for the vision sensor. It 
 *  extends the Mobile2D object.
 *  clsSensorVision defines the functionalities of the vision 
 *  sensor, while clsAnimateVision defines the physical object. 
 * 
 * @author zeilinger
 * 
 */

public class clsEntityVision extends MobileObject2D implements Steppable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static double MASS = 0.0001;
	private final static double FRICTION = 0;
	private final static double RESTITUTION = 0;
	
	private double mnRadius;
		
	private Bag meCollidingObj;
	private Bag meVisionObj;
	private Paint moColor;
	private CircleBorder moShape;
		
	public clsEntityVision(Double2D poPos, Double2D poVel, double pnRad)
    {    	
	 meCollidingObj = new Bag();
	 meVisionObj = new Bag();
	 moColor = Color.yellow;
	 mnRadius = pnRad; 
	 moShape = new CircleBorder(mnRadius, moColor);
	 
	 this.setVelocity(poVel);
     this.setPose(poPos, new Angle(0));
     this.setShape(moShape, MASS);

     //this.setMass(MASS); 
     this.setCoefficientOfFriction(FRICTION);
     this.setCoefficientOfRestitution(RESTITUTION);
    }

	
	public int handleCollision(PhysicalObject2D other, Double2D colPoint)
	{
		meCollidingObj.add(other);
		return 0; // Vis collision
	}
	
	public void step(SimState state)
	{
		  ((clsBWMain)state).moGameGridField.setObjectLocation(this, new sim.util.Double2D(this.getPosition().x, this.getPosition().y));
	      meCollidingObj.clear(); 
	      meVisionObj.clear();
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
		//TODO Clemens, hier gehört mehr rein als nur true!
    	return true; // (insert location algorithm and intersection here)
    }
	
	//--------------------------------------------------------------------------------------------------
	// Methods from PhysicalObject2D which have to be overwritten
	//-------------------------------------------------------------------------------------------------
	
	/** receives all objects, the physical object is colliding with - objects 
	 * which are moving away from the   
	*/     
	public void addContact(PhysicalObject2D other, Double2D colPoint)
	{
		meVisionObj.add(other);		
	}	
}

