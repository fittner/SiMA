/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate.mobile;

import java.awt.Color;

import bw.physicalObject.eEntityType;
import bw.physicalObject.inanimate.clsInanimate;
import bw.sim.clsBWMain;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;

/**
 * Mason representative (physics+renderOnScreen) for a stone.  
 * 
 * @author muchitsch
 * 
 */
public class clsStone extends clsInanimate implements Steppable{

	public boolean visible;
	
	
	public clsStone(Double2D poPos, double pnRadius, double pnMass)
    {
		super(poPos, pnRadius, pnMass);
        visible = true;
    } 
	
	 public void step(SimState state)
     {
	     Double2D position = getMobile().getPosition();
	     clsBWMain oMainSim = (clsBWMain)state;
	     oMainSim.moGameGridField.setObjectLocation(getMobile(), new sim.util.Double2D(position.x, position.y));
     }

	/* (non-Javadoc)
	 * @see bw.clsEntity#getEntityType()
	 */
	@Override
	public eEntityType getEntityType() {
		return eEntityType.STONE;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution()
	 */
	@Override
	public void execution() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#thinking()
	 */
	@Override
	public void thinking() {
		// TODO Auto-generated method stub
		
	}
}