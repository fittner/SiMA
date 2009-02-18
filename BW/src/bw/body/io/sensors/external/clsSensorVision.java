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
 * TODO (zeilinger) - This class defines the Vision object   
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
		
	public clsSensorVision(Double2D poPos, Double2D poVel, PhysicsEngine2D poPE)
	{
		mnViewRange = Math.PI;
		meCollidingObj = new Bag();
		meViewObj = new Bag(); 
		moVel = poVel;
		moVisionArea = new clsAnimateVision(poPos, moVel);
		moVisionArea.loadVision(poPE, moAnimate); 
		
		this.setCollidingObj(); 
	}
	
	private void calcViewObj()
	{
		double nDeg;  
		PhysicalObject2D oPhObj;  
		
		Iterator itr = meCollidingObj.iterator();
		meViewObj.clear(); 
		
		while(itr.hasNext())
		{
			oPhObj = (PhysicalObject2D)itr.next(); 
			nDeg = Math.atan(((oPhObj.getPosition()).y - moVisionArea.getPosition().y)/
					             ((oPhObj.getPosition()).x - moVisionArea.getPosition().x));
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
