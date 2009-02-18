/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.Iterator;

import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
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
	private double mnViewRange; 
	private Double2D moVel;
	private clsAnimate moAnimate;
	private clsAnimateVision moVisionArea;
	private Bag meCollidingObj;
	private Bag meViewObj;
		
	public clsSensorVision(Double2D poPos, Double2D poVel, PhysicsEngine2D poPE, clsAnimate poAnimate)
	{
		mnViewRange = Math.PI;
		meCollidingObj = new Bag();
		meViewObj = new Bag(); 
		moVel = poVel;
		moAnimate = poAnimate; 
		moVisionArea = new clsAnimateVision(poPos, moVel);
		moVisionArea.loadVision(poPE, moAnimate); 
		
		this.setCollidingObj(); 
	}
	
	private void calcViewObj()
	{
		Double2D oPosObj;
		double nDeg;  
		PhysicalObject2D oPhObj;  
		
		Iterator itr = meCollidingObj.iterator();
		meViewObj.clear(); 
		
		while(itr.hasNext())
		{
			oPhObj = (PhysicalObject2D)itr.next(); 
			oPosObj = (oPhObj).getPosition();
			nDeg = Math.atan((oPosObj.y - moVisionArea.getPosition().y)/
					         (oPosObj.x - moVisionArea.getPosition().x));
			if(nDeg <= moVisionArea.getOrientation().radians + mnViewRange/2 ||
			   nDeg >= moVisionArea.getOrientation().radians + mnViewRange/2)
			{
				meViewObj.add(oPhObj); 
			}
		}
	}
	
	private void setCollidingObj()
	{
		meCollidingObj = moVisionArea.getCollidingObj(); 
	}
	
	public Bag getViewObj()
	{
		this.setCollidingObj(); 
		this.calcViewObj(); 
		return meViewObj; 
	}
	
	public Bag getCollidingObj()
	{
		return meCollidingObj; 
	}
	
	public clsAnimateVision getVisionObj()
	{
		return moVisionArea; 
	}
}
