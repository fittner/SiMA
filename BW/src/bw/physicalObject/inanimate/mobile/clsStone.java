/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate.mobile;

import java.awt.Color;

import bw.sim.clsBWMain;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

/**
 * Mason representative (physics+renderOnScreen) for a stone.  
 * 
 * @author muchitsch
 * 
 */
public class clsStone extends sim.physics2D.physicalObject.MobileObject2D implements Steppable{

	public boolean visible;
	
	
	public clsStone(Double2D poPos, double pnRadius, double pnMass)
    {
		this.setPose(poPos, new Angle(0));
        this.setVelocity( new Double2D() );
        this.setCoefficientOfFriction(.5);
        this.setCoefficientOfStaticFriction(0);
        this.setCoefficientOfRestitution(1);
        visible = true;

	    //this.setShape(new sim.physics2D.shape.Circle(pnRadius, Color.darkGray), pnMass);
	    
	    //show a image? getting very slow! but maybe we need it sometimes
	    this.setShape(new ARSsim.physics2D.shape.clsCircleImage(pnRadius, Color.darkGray), pnMass);
    } 
	
	 public void step(SimState state)
     {
	     Double2D position = this.getPosition();
	     clsBWMain oMainSim = (clsBWMain)state;
	     oMainSim.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
     }
}