/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate.mobile;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import bw.physicalObject.inanimate.clsInanimate;
import bw.sim.clsBWMain;
import bw.utils.enums.eEntityType;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.robot2D.clsBrainAction;

/**
 * Mason representative (physics+renderOnScreen) for a stone.  
 * 
 * @author muchitsch
 * 
 */
public class clsStone extends clsInanimate {

	public boolean visible;
	
	
	
	public clsStone(Double2D poPosition, Double2D poStartingVelocity, double pnRadius, double pnMass, int pnId)
    {
		super(pnId);
		visible = true;
		
		
		// doing the initialize stuff myself
		
		clsMobileObject2D oMobile = getMobile();
		moPhysicalObject2D = oMobile;
		
		oMobile.setPose(poPosition, new Angle(0));
		oMobile.setVelocity(poStartingVelocity);
		
		setPosition( new sim.util.Double2D(poPosition.x, poPosition.y) );
		
		 //override the default
        java.net.URL oImageURL = getClass().getResource("images/rock1.jpg");
        String nImagePath = "S:/ARS/PA/BWv1/BW/src/resources/images/rock1.jpg";
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(pnRadius, Color.darkGray, nImagePath), pnMass);
		setCoefficients(.5, 0, 1); //default coefficients
		
		oMobile.finalizeSetup();
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



	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}

}