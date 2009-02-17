/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.Double2D;
import sim.util.*;

import bw.physicalObject.entityParts.clsAnimateVision;
import bw.physicalObject.animate.clsAnimate;
/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public class clsSensorVision extends clsSensorExt
{
	private Double2D moPos; 
	private Double2D moVel;
	private clsAnimate moAnimate;
	private clsAnimateVision moVisionArea;
	private Bag meCollidingObj;
		
	public clsSensorVision(Double2D poPos, Double2D poVel, PhysicsEngine2D poPE, clsAnimate poAnimate)
	{
		meCollidingObj = new Bag();
				
		moPos = poPos; 
		moVel = poVel;
		moAnimate = poAnimate; 
		
		moVisionArea = new clsAnimateVision(moPos, moVel);
		moVisionArea.loadVision(poPE, moAnimate); 
		
		this.setCollidingObj(); 
	}
	
	public clsAnimateVision getVisionObj()
	{
		return moVisionArea; 
	}
	
	public void setCollidingObj()
	{
		meCollidingObj = moVisionArea.getCollidingObj(); 
	}
	
	public Bag getPerceiveObj()
	{
		this.calcPerceiveObj(); 
		return meCollidingObj; 
	}
	
	private void calcPerceiveObj()
	{
		//toDo
	}
}
