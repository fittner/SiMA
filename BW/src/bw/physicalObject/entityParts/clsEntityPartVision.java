/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.entityParts;

import java.awt.*;
import java.util.HashMap;

import ARSsim.physics2D.shape.clsCircleBorder;
import bw.clsEntity;
import bw.physicalObject.inanimate.mobile.clsMobile;
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
		
	private Bag meFilteredObj;
	private Bag meUnFilteredObj;
	private HashMap<Integer, PhysicalObject2D> meVisionObj; 
	private Paint moColor;
	private clsCircleBorder moShape;
	private clsEntity moEntity;
			
	/**
	 * @param poEntity
	 * @param pnRad
	 */
	public clsEntityPartVision(clsEntity poEntity,  double pnRad) {    	
	 mnRadius = pnRad; 

	 meFilteredObj = new Bag();
	 meUnFilteredObj = new Bag();
	 meVisionObj = new HashMap<Integer, PhysicalObject2D>(); 
	 moColor = Color.yellow;
	 moShape = new clsCircleBorder(mnRadius, moColor);
	 moEntity = poEntity; 
	 
		try
		{
			 this.setShape(moShape, MASS); 
			 this.setCoefficientOfFriction(FRICTION);
			 this.setCoefficientOfRestitution(RESTITUTION);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
			
	/* (non-Javadoc)
	 * @see sim.physics2D.physicalObject.PhysicalObject2D#handleCollision(sim.physics2D.physicalObject.PhysicalObject2D, sim.physics2D.util.Double2D)
	 */
	public int handleCollision(PhysicalObject2D other, Double2D colPoint){
		meFilteredObj.add(other);
		return 0; // Vis collision
	}
	
	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	public void step(SimState state){
		 Double2D oEntityPos = ((clsMobile)moEntity).getMobile().getPosition();
		 Angle oEntityOrientation = ((clsMobile)moEntity).getMobile().getOrientation();
		 
	     this.setPose(oEntityPos, oEntityOrientation);
		  ((clsBWMain)state).moGameGridField.setObjectLocation(this, new sim.util.Double2D(oEntityPos.x, oEntityPos.y));
		 this.clearLists(); 
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.02.2009, 12:13:53
	 *
	 */
	public void clearLists()
	{
		 meFilteredObj.clear(); 
	     meUnFilteredObj.clear();
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
	public void setMnRadius(double pnRadius) {
		this.mnRadius = pnRadius;
	}


	/**
	 * @return the meCollidingObj
	 */
	public Bag getMeFilteredObj() {
		return meFilteredObj;
	}


	/**
	 * @param meCollidingObj the meCollidingObj to set
	 */
	public void setMeFilteredObj(Bag peFilteredObj) {
		this.meFilteredObj = peFilteredObj;
	}


	/**
	 * @return the meVisionObj
	 */
	public Bag getMeUnFilteredObj() {
		return meUnFilteredObj;
	}


	/**
	 * @param meVisionObj the meVisionObj to set
	 */
	public void setMeUnFilteredObj(Bag peUnFilteredObj) {
		this.meUnFilteredObj = peUnFilteredObj;
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
	public void setMoColor(Paint poColor) {
		this.moColor = poColor;
	}


	/**
	 * @return the moShape
	 */
	public clsCircleBorder getMoShape() {
		return moShape;
	}


	/**
	 * @param moShape the moShape to set
	 */
	public void setMoShape(clsCircleBorder poShape) {
		this.moShape = poShape;
	}
	/**
	 * @author zeilinger
	 * 20.02.2009, 12:53:18
	 * 
	 * @return the meVisionObj
	 */
	public HashMap<Integer, PhysicalObject2D> getMeVisionObj() {
		return meVisionObj;
	}


	/**
	 * @author zeilinger
	 * 20.02.2009, 12:53:18
	 * 
	 * @param meVisionObj the meVisionObj to set
	 */
	public void setMeVisionObj(HashMap<Integer, PhysicalObject2D> peVisionObj) {
		this.meVisionObj = peVisionObj;
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
		meUnFilteredObj.add(other);		
	}	
}

