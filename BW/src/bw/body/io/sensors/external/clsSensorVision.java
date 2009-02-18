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
	}
	
	private void calcViewObj()
	{
		double nOrientation;  
		PhysicalObject2D oPhObj;  
		meViewObj.clear(); 
		
		Iterator itr = meCollidingObj.iterator();
		while(itr.hasNext())
		{
			oPhObj = (PhysicalObject2D)itr.next(); 
			nOrientation = this.getRelPos(oPhObj.getPosition());
			
			if(this.getInView(nOrientation))
			{
				this.setViewObj(oPhObj); 
			}
		}
	}
	
	public double getRelPos(Double2D poPos)
	{
		double nOrientation;
		nOrientation = Math.atan((poPos.y - moVisionArea.getPosition().y)/
	             				 (poPos.x - moVisionArea.getPosition().x));
		return nOrientation; 
	}
	
	public boolean getInView(double pnOrientation)
	{
		if(pnOrientation <= moVisionArea.getOrientation().radians + mnViewDegree/2 ||
				pnOrientation >= moVisionArea.getOrientation().radians + mnViewDegree/2)
		{
			return true;  
		}
		return false; 
	}
	public void setViewObj(PhysicalObject2D pPhObj)
	{
		meViewObj.add(pPhObj);
	}
	
	public void setVel(Double2D poVel)
	{
		moVel=poVel; 
	}
		
	public Bag getViewObj()
	{
		meCollidingObj = moVisionArea.getCollidingObj();
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
	
	public void setMeCollidingObj(Bag peCollidingObj)
	{
		meCollidingObj = peCollidingObj; 
	}
}
