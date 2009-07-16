/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObjects.sensors;

import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;

import ARSsim.physics2D.shape.clsCircleBorder;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.entities.clsStationary;
import bw.factories.clsSingletonMasonGetter;

/**
 *  This class defines the the physical object for the radiation sensor. It 
 *  extends the Mobile2D object.
 *  clsSensorRadiation defines the functionalities of the radiation 
 *  sensor, while clsAnimateRadiation defines the physical object. 
 * 
 * @author horvath
 * 
 */

public class clsEntityPartRadiation extends MobileObject2D implements Steppable{

	private static final long serialVersionUID = 1L;
	private final static double MASS = 0.0001;
	//private final static double FRICTION = 0;		// EH - make warning free
	//private final static double RESTITUTION = 0;	// EH - make warning free
	
	private double mnRadius;
	
	private double mnRadiusOffsetRadiationArea = 0;
		
	private HashMap<Integer, PhysicalObject2D> meFilteredObj;
	private HashMap<Integer, PhysicalObject2D> meUnFilteredObj;
	private HashMap<Integer, PhysicalObject2D> meRadiationObj; 
	private HashMap<Integer, Double2D> meCollisionPoint; 
	private Paint moColor;
	private clsCircleBorder moShape;
	private clsEntity moEntity;
			
	/**
	 * @param poEntity
	 * @param pnRad
	 * @param pnRadiusOffsetRadiationArea 
	 */
	public clsEntityPartRadiation(clsEntity poEntity,  double pnRad, double pnRadiusOffsetRadiationArea) {    
	 mnRadius = pnRad;
	 mnRadiusOffsetRadiationArea = pnRadiusOffsetRadiationArea;

	 meFilteredObj = new HashMap<Integer, PhysicalObject2D>();
	 meUnFilteredObj = new HashMap<Integer, PhysicalObject2D>();
	 meRadiationObj = new HashMap<Integer, PhysicalObject2D>(); 
	 meCollisionPoint = new HashMap<Integer, Double2D>(); 
	 moColor = Color.yellow;
	 moShape = new clsCircleBorder(mnRadius, moColor);
	 moEntity = poEntity; 
	 
		try
		{
			 this.setShape(moShape, MASS); 
			 /*this.setCoefficientOfFriction(FRICTION);
			 this.setCoefficientOfRestitution(RESTITUTION);
			 */
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
			
	/* (non-Javadoc)
	 * @see sim.physics2D.physicalObject.PhysicalObject2D#handleCollision(sim.physics2D.physicalObject.PhysicalObject2D, sim.physics2D.util.Double2D)
	 */
	@Override
	public int handleCollision(PhysicalObject2D other, sim.physics2D.util.Double2D colPoint){
		meFilteredObj.put(other.getIndex(), other);
		return 0; // Rad collision
	}
		
	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	public void step(SimState state){
		 sim.physics2D.util.Double2D oEntityPos = null;
		 Angle oEntityOrientation = null;
		 
		 // In case the entity isn't Mobile, but Stationary with eatable area (Base), treat it as an instance of the Stationary class
		 if(moEntity instanceof clsMobile){
			 oEntityPos = ((clsMobile)moEntity).getMobileObject2D().getPosition();
			 oEntityOrientation = ((clsMobile)moEntity).getMobileObject2D().getOrientation();
		 }
		 if(moEntity instanceof clsStationary){
			 oEntityPos = ((clsStationary)moEntity).getStationaryObject2D().getPosition();
			 oEntityOrientation = ((clsStationary)moEntity).getStationaryObject2D().getOrientation();
		 }
		 
		 //if we have a offset radius, recalculate position
		 if(mnRadiusOffsetRadiationArea != 0)
		 {
			 oEntityPos = new Double2D(oEntityPos.x + mnRadiusOffsetRadiationArea * Math.cos(oEntityOrientation.radians), oEntityPos.y + mnRadiusOffsetRadiationArea * Math.sin(oEntityOrientation.radians) );
		 }
		 
	     this.setPose(oEntityPos, oEntityOrientation);
	     clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, new sim.util.Double2D(oEntityPos.x, oEntityPos.y));
		 this.clearLists(); 
	}
	
	/**
	 * TODO (horvath) - insert description
	 *
	 * @author horvath
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
	public HashMap<Integer, PhysicalObject2D> getMeFilteredObj() {
		return meFilteredObj;
	}


	/**
	 * @param meCollidingObj the meCollidingObj to set
	 */
	public void setMeFilteredObj(HashMap<Integer, PhysicalObject2D> peFilteredObj) {
		this.meFilteredObj = peFilteredObj;
	}


	/**
	 * @return the meRadiationObj
	 */
	public HashMap<Integer, PhysicalObject2D> getMeUnFilteredObj() {
		return meUnFilteredObj;
	}


	/**
	 * @param meRadiationObj the meRadiationObj to set
	 */
	public void setMeUnFilteredObj(HashMap<Integer, PhysicalObject2D> peUnFilteredObj) {
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
	 * @author horvath
	 * 20.02.2009, 12:53:18
	 * 
	 * @return the meRadiationObj
	 */
	public HashMap<Integer, PhysicalObject2D> getmeRadiationObj() {
		return meRadiationObj;
	}


	/**
	 * @author horvath
	 * 20.02.2009, 12:53:18
	 * 
	 * @param meRadiationObj the meRadiationObj to set
	 */
	public void setMeRadiationObj(HashMap<Integer, PhysicalObject2D> peRadiationObj) {
		this.meRadiationObj = peRadiationObj;
	}
	
    
	/**
	 *
	 * @author horvath
	 * 16.07.2009, 12:27:04
	 *
	 * @return
	 */
	public HashMap<Integer, Double2D> getMeCollisionPoint() {
		return meCollisionPoint;
	}

	/**
	 *
	 * @author horvath
	 * 16.07.2009, 12:26:40
	 *
	 * @param meCollisionPoint the meCollisionPoint to set
	 */
	public void setMeCollisionPoint(HashMap<Integer, Double2D> meCollisionPoint) {
		this.meCollisionPoint = meCollisionPoint;
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
	@Override
	public void addFrictionForce()
	{        }
	
	/* (non-Javadoc)
	 * @see sim.portrayal.SimplePortrayal2D#hitObject(java.lang.Object, sim.portrayal.DrawInfo2D)
	 */
	@Override
	public boolean hitObject(Object object, DrawInfo2D range)   {
		//TODO Clemens, hier geh�rt mehr rein als nur true!
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
	@Override
	public void addContact(PhysicalObject2D other, sim.physics2D.util.Double2D colPoint){
		//FIXME colPoint not used
		if (colPoint != null)
		{
			meUnFilteredObj.put(other.getIndex(), other);
			meCollisionPoint.put(other.getIndex(), colPoint);
		}				
	}	
}

