/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.*;
import java.util.ArrayList;

import sim.physics2D.util.*;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.body.motionplatform.clsBrainAction;
import bw.utils.enums.eEntityType;

public class clsCan extends clsInanimate
    {
    public boolean visible;
    public clsCan(Double2D pos, Double2D vel, int pnId) {
    	super(pos, vel,new sim.physics2D.shape.Circle(2, Color.blue), 80, pnId);

    	//FIXME... folgendes wird woanders gemacht, weg?
    	clsMobileObject2D oMobile = getMobileObject2D();
    	oMobile.setPose(pos, new Angle(0));
    	oMobile.setVelocity(vel);
    	oMobile.setShape(new sim.physics2D.shape.Circle(2, Color.blue), 80);
    	oMobile.setCoefficientOfFriction(.5);
    	oMobile.setCoefficientOfStaticFriction(0);
    	oMobile.setCoefficientOfRestitution(1);
        visible = true;
    }
    /* 
    public void step(SimState state) {
    	
    	clsMobileObject2D oMobile = getMobile();
        Double2D position = oMobile.getPosition();
        clsBWMain simRobots = (clsBWMain)state;
        simRobots.moGameGridField.setObjectLocation(oMobile, new sim.util.Double2D(position.x, position.y));
        
    }
*/
    
	/* (non-Javadoc)
	 * @see bw.clsEntity#getEntityType()
	 */
	@Override
	public eEntityType getEntityType() {
		return eEntityType.CAN;
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
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}
}

