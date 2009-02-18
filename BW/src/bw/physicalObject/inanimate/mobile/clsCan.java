/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate.mobile;

import java.awt.*;
import sim.engine.*;
import sim.physics2D.util.*;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.physicalObject.eEntityType;
import bw.physicalObject.inanimate.clsInanimate;
import bw.sim.clsBWMain;

public class clsCan extends clsInanimate
    {
    public boolean visible;
    public clsCan(Double2D pos, Double2D vel) {
    	super();

    	clsMobileObject2D oMobile = getMobile();
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

