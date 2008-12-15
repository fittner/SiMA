package ARSsim.robot2D;

import sim.physics2D.physicalObject.MobileObject2D;
import sim.robot2D.Robot;
import sim.physics2D.util.*;

public class clsRobot extends Robot
    {
       
    public Double2D localFromGlobal(Double2D globalCoordinate)
        {
        return super.localFromGlobal(globalCoordinate);
        }
        
    public Double2D globalFromLocal(Double2D localCoordinate)
        {
        return super.globalFromLocal(localCoordinate);
        }
        
    /** Gives the angle of the vector (i.e. vector (1, 1) gives PI / 4)
     */
    public Angle getAngle(Double2D vector)
        {
        return super.getAngle(vector);
        }
        
    public void faceTowards(Angle globalAngle)
        {
    		super.faceTowards(globalAngle);
        }
        
    public void moveForward(double speed)
        {
    	super.moveForward(speed);
        }
        
    public void goTo(Double2D globalDestination)
        {
    		super.goTo(globalDestination);
        }
        
    public void stop()
        {
    	super.stop();
        }
        
    public void backup()
        {
    	super.backup();
        }
    }
