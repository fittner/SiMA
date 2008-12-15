/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.Double2D;
import sim.util.*;

import bw.body.physicalObject.effector.*;
import bw.body.physicalObject.mobile.*;
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
	private Bag meCollidingObj = new Bag();
	private Bag mePerceiveObj = new Bag();
	
	public clsSensorVision(Double2D poPos, Double2D poVel, PhysicsEngine2D poPE, clsBot poRobot)
	{
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
