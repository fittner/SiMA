/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.clsEntity;
import bw.sim.clsBWMain;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsMobileObject2D extends sim.physics2D.physicalObject.MobileObject2D implements Steppable, ForceGenerator{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7732669244848952049L;
	
	private clsEntity moEntity;
	public ArrayList<clsCollidingObject> moCollisionList;
	
	
	public clsMobileObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
		moCollisionList = new ArrayList<clsCollidingObject>();
	}
	
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @return
	 */
	public clsEntity getEntity() {
		// TODO Auto-generated method stub
		return moEntity;
	}
	
	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		resetStepInfo();
		moEntity.sensing();
		moEntity.thinking();
		
		//with these 3 physics work!
		Double2D position = this.getPosition();
	    clsBWMain oMainSim = (clsBWMain)state;
	    oMainSim.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
		
	}
	public void resetStepInfo()
	{
		moCollisionList.clear();
	}
	
	/* (non-Javadoc)
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		// TODO Auto-generated method stub
		moEntity.execution();
	}

    public int handleCollision(PhysicalObject2D other, Double2D colPoint)
    {
    	moCollisionList.add(new clsCollidingObject(other, colPoint));
    	
    	//return 1; // regular collision
    	//return 2; // sticky collision
    	return 0; //happy guessing!
	}

}
