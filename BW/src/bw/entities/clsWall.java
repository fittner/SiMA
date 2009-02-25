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

import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.motionplatform.clsBrainAction;


/**
 * Mason representative (physics+renderOnScreen) for a wall.  
 * 
 * FIXME: CLEMENS - REIMPLEMENT PLEASE!!!!
 * 
 * @author langr
 * 
 */
public class clsWall extends clsStationary
    {
    public double radius;
    
    public clsWall(Double2D poPosition, int width, int height, int pnId)
    {
		//super(pnId);
		
		// doing the initialize stuff myself so i can set the shape myself
		
		clsStationaryObject2D oStat = getStationary();
		moPhysicalObject2D = oStat;
		
		oStat.setPose(poPosition, new Angle(0));
		//oStat.setVelocity(new Double2D(0,0));
		
		setPosition( new sim.util.Double2D(poPosition.x, poPosition.y) );
		
		 //override the default
//        java.net.URL oImageURL = getClass().getResource("images/rock1.jpg");
//        String nImagePath = "S:/ARS/PA/BWv1/BW/src/resources/images/rock1.jpg";
		
		setShape((new sim.physics2D.shape.Rectangle(width, height, Color.white)), 1000);
		
		//setCoefficients(.2, 0, 1); //default coefficients
		
		oStat.finalizeSetup();
    } 
    
    
    
//    public clsWall(Double2D pos, int width, int height)
//        {
//    	
//    	super()
//    	
//        setCoefficientOfRestitution(1);
//        this.setPose(pos, new Angle(0));
//        this.setShape(new sim.physics2D.shape.Rectangle(width, height, Color.white));
//        
//       
//        }

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:37
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
	 * 25.02.2009, 17:37:37
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}
}