/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import java.awt.Color;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import tstBw.body.*;
import bw.body.clsAgentBody;
import bw.body.physicalObject.mobile.clsMotionPlatform;
import bw.sim.clsBWMain;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public class clsAnimate extends clsEntity implements Steppable, ForceGenerator{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1734482865454985954L;
	private int mnId;
	
	public int getId() {	return mnId;	}
	public void setId(int pnId) {		this.mnId = pnId;	}

	public clsAgentBody moAgentBody; // the instance of a body
	private clsMotionPlatform moMotionPlatform; //motion platform for user steering

	
	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsAnimate(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId) {
		super();
		
		moMotionPlatform = new clsMotionPlatform(this);
		moAgentBody = new clsAgentBody(this);
		
	 	this.setPose(poStartingPosition, new Angle(0));
	    this.setVelocity(poStartingVelocity);
	    
	    //standard for Animate... override in your class!
	    this.setShape(new sim.physics2D.shape.Circle(10, Color.CYAN), 300); //FIXME clemens: replace this with a seperate class, or at least with a mehtod
     
	    this.setCoefficientOfFriction(.5);
        this.setCoefficientOfStaticFriction(0);
        this.setCoefficientOfRestitution(1);
	            
	    super.setPhysicsConstraintEngine(ConstraintEngine.getInstance());
	}
	
	

	/* (non-Javadoc)
	 * @see bw.clsEntity#step(sim.engine.SimState)
	 */
	public void step(SimState state) {
		// TODO Auto-generated method stub
		
		//minimum to act on physical actions!
	    Double2D position = this.getPosition();
	    clsBWMain simRobots = (clsBWMain)state;
	    simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	    
	    moAgentBody.step();
	}
	
	
	/* (non-Javadoc)
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see sim.portrayal.SimplePortrayal2D#hitObject(java.lang.Object, sim.portrayal.DrawInfo2D)
	 */
	public boolean hitObject(Object object, DrawInfo2D range)
    {
		//TODO Clemens, we could add some fancy hittestting here
    	return true; // (insert location algorithm and intersection here)
    }
	
}
