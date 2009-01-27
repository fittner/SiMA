/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import java.awt.Color;
import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import bw.body.itfStep;
import bw.body.physicalObject.mobile.clsMotionPlatform;
import bw.sim.clsBWMain;
import bw.utils.datatypes.clsMutableFloat;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate implements Steppable, ForceGenerator{

	private int mnId; // ID of the bubble
	private static final long serialVersionUID = -329155160020488088L;
	private clsMotionPlatform moMotion;


	// GetSet...
	public void setId(int pnId) {	this.mnId = pnId;	}
	public int getId() {	return mnId;	} 
	
	public float getInternalEnergyConsuptionSUM() {	return super.moAgentBody.getInternalStates().getInternalEnergyConsumption().getSum();	} 
	
	//just testing (cm)
	public HashMap<Integer, clsMutableFloat> getInternalEnergyConsumption() {	return moAgentBody.getInternalStates().getInternalEnergyConsumption().getList();	} 
	
	/**
	 *  CTOR
	 */
	public clsBubble() {
		super();
	}
	
	public clsBubble(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId)
    {
		super();
		
		this.setId(pnId);
	    this.setPose(poStartingPosition, new Angle(0));
	    this.setVelocity(poStartingVelocity);
	    this.setShape(new sim.physics2D.shape.Circle(10, Color.GREEN), 300); //FIXME clemens: replace this with a seperate class, or at least with a mehtod
         
	    moMotion = new clsMotionPlatform(this);
	    
	    this.setCoefficientOfFriction(.5);
        this.setCoefficientOfStaticFriction(0);
        this.setCoefficientOfRestitution(1);
	    
	            
	    super.setPhysicsConstraintEngine(ConstraintEngine.getInstance());
    } 
	
	public boolean hitObject(Object object, DrawInfo2D range)
    {
		//TODO Clemens, we could add some fancy hittestting here
    	return true; // (insert location algorithm and intersection here)
    }
	
	
	/* (non-Javadoc)
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		// TODO Auto-generated method stub
		
	}
	
	public void step(SimState state)
    {
		//minimum to act on physical actions!
	    Double2D position = this.getPosition();
	    clsBWMain simRobots = (clsBWMain)state;
	    simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	    
	    moAgentBody.step();
	    
	  
    }


}
