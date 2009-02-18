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
 * TODO (zeilinger) - This class defines the Vision object which is tagged to an animate 
 *                    object. clsSensorVision defines the functionalities of the vision 
 *                    sensor, while clsAnimateVision defines the physical object.     
 * 
 * @author zeilinger
 * 
 */
public class clsSensorVision extends clsSensorExt
{
	private double mnViewDegree;
	private double mnVisRange; 
	private Double2D moVel;
	private clsAnimate moAnimate;
	private clsAnimateVision moVisionArea;
	private Bag meCollidingObj;
	private Bag meViewObj;
		
	public clsSensorVision(Double2D poPos, Double2D poVel, PhysicsEngine2D poPE)
	{
		mnViewDegree = Math.PI;
		meCollidingObj = new Bag();
		meViewObj = new Bag(); 
		moVel = poVel;
		mnVisRange = 50; 
		
		moVisionArea = new clsAnimateVision(poPos, moVel, mnVisRange);
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
			if(nDeg <= moVisionArea.getOrientation().radians + mnViewDegree/2 ||
			       nDeg >= moVisionArea.getOrientation().radians + mnViewDegree/2)
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
