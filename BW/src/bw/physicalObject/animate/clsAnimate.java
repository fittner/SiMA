/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.animate;

import java.awt.Color;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import tstBw.body.*;
import bw.clsEntity;
import bw.body.clsAgentBody;
import ARSsim.robot2D.clsMotionPlatform;
import bw.physicalObject.inanimate.mobile.clsMobile;
import bw.sim.clsBWMain;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile implements Steppable{
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
		
		MobileObject2D oMobile = getMobile();
		
		moMotionPlatform = new clsMotionPlatform(oMobile);
		moAgentBody = new clsAgentBody(this);
		
		
		
		oMobile.setPose(poStartingPosition, new Angle(0));
		oMobile.setVelocity(poStartingVelocity);
	    
	    //standard for Animate... override in your class!
		oMobile.setShape(new sim.physics2D.shape.Circle(10, Color.CYAN), 300); //FIXME clemens: replace this with a seperate class, or at least with a mehtod
     
		oMobile.setCoefficientOfFriction(.5);
		oMobile.setCoefficientOfStaticFriction(0);
		oMobile.setCoefficientOfRestitution(1);
	            
		//setPhysicsConstraintEngine(ConstraintEngine.getInstance());
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#step(sim.engine.SimState)
	 */
	public void step(SimState state) {
		// TODO Auto-generated method stub
		
		//minimum to act on physical actions!
	    Double2D position = getMobile().getPosition();
	    clsBWMain simRobots = (clsBWMain)state;
	    simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	    
	    moAgentBody.step();
	}
	
}
