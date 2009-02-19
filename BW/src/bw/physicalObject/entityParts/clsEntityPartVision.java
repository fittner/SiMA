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
import bw.clsEntity;
import bw.sim.clsBWMain;

import sim.engine.*;
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

public class clsEntityPartVision extends MobileObject2D implements Steppable{

	private static final long serialVersionUID = 1L;
	private final static double MASS = 0.0001;
	private final static double FRICTION = 0;
	private final static double RESTITUTION = 0;
	
	private double mnRadius;
		
	private Bag meCollidingObj;
	private Bag meVisionObj;
	private Paint moColor;
	private CircleBorder moShape;
			
	/**
	 * @param poEntity
	 * @param pnRad
	 */
	public clsEntityPartVision(clsEntity poEntity,  double pnRad) {    	
	 mnRadius = pnRad; 

	 meCollidingObj = new Bag();
	 meVisionObj = new Bag();
	 moColor = Color.yellow;
	 moShape = new CircleBorder(mnRadius, moColor);
	 
		try
		{
			 this.setShape(moShape, MASS); 
			 this.setCoefficientOfFriction(FRICTION);
			 this.setCoefficientOfRestitution(RESTITUTION);
		}catch( Exception ex )
		{
			System.out.println(ex.getMessage());
		}
	}
	
		
	/* (non-Javadoc)
	 * @see sim.physics2D.physicalObject.PhysicalObject2D#handleCollision(sim.physics2D.physicalObject.PhysicalObject2D, sim.physics2D.util.Double2D)
	 */
	public int handleCollision(PhysicalObject2D other, Double2D colPoint){
		meCollidingObj.add(other);
		return 0; // Vis collision
	}
	
	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	public void step(SimState state){
		  ((clsBWMain)state).moGameGridField.setObjectLocation(this, new sim.util.Double2D(this.getPosition().x, this.getPosition().y));
	      meCollidingObj.clear(); 
	      meVisionObj.clear();
	}
	    
	//--------------------------------------------------------------------------------------------------
	// local set and get methods
	//-------------------------------------------------------------------------------------------------
	
	
	/**
	 * @return the mnRadius
	 */
	public double getMnRadius() {
		return mnRadius;
	}


	/**
	 * @param mnRadius the mnRadius to set
	 */
	public void setMnRadius(double mnRadius) {
		this.mnRadius = mnRadius;
	}


	/**
	 * @return the meCollidingObj
	 */
	public Bag getMeCollidingObj() {
		return meCollidingObj;
	}


	/**
	 * @param meCollidingObj the meCollidingObj to set
	 */
	public void setMeCollidingObj(Bag meCollidingObj) {
		this.meCollidingObj = meCollidingObj;
	}


	/**
	 * @return the meVisionObj
	 */
	public Bag getMeVisionObj() {
		return meVisionObj;
	}


	/**
	 * @param meVisionObj the meVisionObj to set
	 */
	public void setMeVisionObj(Bag meVisionObj) {
		this.meVisionObj = meVisionObj;
	}


	/**
	 * @return the moColor
	 */
	public Paint getMoColor() {
		return moColor;
	}


	/**
	 * @param moColor the moColor to set
	 */
	public void setMoColor(Paint moColor) {
		this.moColor = moColor;
	}


	/**
	 * @return the moShape
	 */
	public CircleBorder getMoShape() {
		return moShape;
	}


	/**
	 * @param moShape the moShape to set
	 */
	public void setMoShape(CircleBorder moShape) {
		this.moShape = moShape;
	}


	
	//--------------------------------------------------------------------------------------------------
	// Methods from Mobile2D which have to be overwritten
	//-------------------------------------------------------------------------------------------------
	      
	/** Calculates and adds the static and dynamic friction forces on the object
	 * based on the coefficients of friction. 
	 */
	/* (non-Javadoc)
	 * @see sim.physics2D.physicalObject.MobileObject2D#addFrictionForce()
	 */
	public void addFrictionForce()
	{        }
	
	/* (non-Javadoc)
	 * @see sim.portrayal.SimplePortrayal2D#hitObject(java.lang.Object, sim.portrayal.DrawInfo2D)
	 */
	public boolean hitObject(Object object, DrawInfo2D range)   {
		//TODO Clemens, hier gehört mehr rein als nur true!
    	return true; // (insert location algorithm and intersection here)
    }
	
	//--------------------------------------------------------------------------------------------------
	// Methods from PhysicalObject2D which have to be overwritten
	//-------------------------------------------------------------------------------------------------
	
	/** receives all objects, the physical object is colliding with - objects 
	 * which are moving away from the   
	*/     
	/* (non-Javadoc)
	 * @see sim.physics2D.physicalObject.PhysicalObject2D#addContact(sim.physics2D.physicalObject.PhysicalObject2D, sim.physics2D.util.Double2D)
	 */
	public void addContact(PhysicalObject2D other, Double2D colPoint){
		meVisionObj.add(other);		
	}	
}

