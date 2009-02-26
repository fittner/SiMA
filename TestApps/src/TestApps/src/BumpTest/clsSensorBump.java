package TestApps.src.BumpTest;

 import sim.physics2D.physicalObject.PhysicalObject2D;
 import sim.util.Bag;
 import sim.physics2D.physicalObject.*;
 import sim.physics2D.collisionDetection.*;
 
 import sim.physics2D.constraint.*;
 import sim.physics2D.*;
 import sim.engine.*;
 import sim.physics2D.physicalObject.*;
 import sim.physics2D.forceGenerator.ForceGenerator;
 import sim.physics2D.util.*;
 import sim.util.Bag;
 import sim.physics2D.constraint.ConstraintEngine;
//TODO tehseen

 public class clsSensorBump {
	
	private boolean mbBumped;
	CollisionPair moCollidingObject;
	
	clsSensorBump(){
		mbBumped= false;
	}
	
		
	public boolean isBumped() {
		return mbBumped;
	}

	public void setBumped(boolean pbBumped) {
		mbBumped = pbBumped;
	}
	
	public void resetSensor()
	{
		setBumped(false);
	}
	
 }


