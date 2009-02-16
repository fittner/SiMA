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

import bw.physicalObject.entityParts.*;
import bw.body.physicalObject.mobile.*;
import bw.physicalObject.animate.clsBot;
import bw.physicalObject.entityParts.clsBotVision;
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
	private clsBot moRobot;
	private clsBotVision moVisionArea;
	private Bag meCollidingObj;
	private Bag mePerceiveObj;
	
	public clsSensorVision(Double2D poPos, Double2D poVel, PhysicsEngine2D poPE, clsBot poRobot)
	{
		meCollidingObj = new Bag();
		mePerceiveObj = new Bag();
		
		moPos = poPos; 
		moVel = poVel;
		moRobot = poRobot; 
		
		moVisionArea = new clsBotVision(moPos, moVel);
		moVisionArea.loadVision(poPE, moRobot); 
		
		this.setCollidingObj(); 
	}
	
	public clsBotVision getVisionObj()
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
