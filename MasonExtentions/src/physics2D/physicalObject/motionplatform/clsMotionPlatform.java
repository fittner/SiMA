package physics2D.physicalObject.motionplatform;

import physics2D.physicalObject.clsMobileObject2D;
import sim.util.matrix.DenseMatrix;
import sim.physics2D.util.*;

public class clsMotionPlatform
   {
	private clsMobileObject2D moMobile;
	
    private double P_angle;
    private double D_angle;
        
    private double P_pos;
    private double D_pos;
    public clsMotionPlatform(clsMobileObject2D poMobile)
        {
    	moMobile = poMobile;
    	
        P_angle = 10;
        D_angle = 500;
                
        P_pos = .2;
        D_pos = 10;
        

        }
    

        
    public Double2D localFromGlobal(Double2D globalCoordinate)
        {
        // x0 = R'x1 - R'T
        DenseMatrix global = new sim.util.matrix.DenseMatrix(2, 1);
        global.vals[0][0] = globalCoordinate.x;
        global.vals[1][0] = globalCoordinate.y;
                
        DenseMatrix T = new sim.util.matrix.DenseMatrix(2, 1);
        T.vals[0][0] = moMobile.getPosition().x;
        T.vals[1][0] = moMobile.getPosition().y;
                
        double theta = moMobile.getOrientation().radians;
        double[][] arR = {{ Math.cos(theta), -Math.sin(theta) },
                              { Math.sin(theta), Math.cos(theta) }};
                
        DenseMatrix R = new sim.util.matrix.DenseMatrix(arR);
        sim.util.matrix.DenseMatrix local = R.transpose().times(global).minus(R.transpose().times(T));
        return new Double2D(local.vals[0][0], local.vals[1][0]);
        }
        
    public Double2D globalFromLocal(Double2D localCoordinate)
        {
        // x1 = Rx0 + T
        Double2D rotated = localCoordinate.rotate(moMobile.getOrientation().radians);
        return rotated.add(moMobile.getPosition());
        }
        
    /** Gives the angle of the vector (i.e. vector (1, 1) gives PI / 4)
     */
    public Angle getAngle(Double2D vector)
        {
        // Get the angle  
        Angle theta;
        if (vector.x != 0)
            {
            theta = new Angle(Math.atan(vector.y / vector.x));
            if (vector.x < 0 && vector.y >= 0)
                theta = new Angle(Math.PI + theta.radians);
            else if (vector.x < 0 && vector.y < 0)
                theta = theta.add(Math.PI);
            else if (vector.x > 0 && vector.y < 0)
                theta = new Angle(Angle.twoPI + theta.radians);
            // otherwise (positive x,y quadrant) just theta
            }
        else
            theta = new Angle(vector.y > 0 ? Angle.halfPI : Angle.halfPI * 3);
                
        return theta;
        }
        
    
    /**
     * @param relativeAngle
     * function allows the rotation of the given angle relative to the current direction 
     */
    public void faceTowardsRelative(Angle relativeAngle)
    {
    	faceTowards(relativeAngle.add( moMobile.getOrientation() ) );
    }
    
    public void faceTowards(Angle globalAngle)
        {
//        double angularVel = moMobile.getAngularVelocity();
//        double angularError = globalAngle.add(new Angle(-moMobile.getOrientation().radians)).radians;
//        if (angularError >= Math.PI)
//            angularError = -(Angle.twoPI - angularError);
//        double toAdd = P_angle * angularError - D_angle * angularVel;
//        moMobile.addTorque(toAdd);
        
    	moMobile.setPose(moMobile.getPosition(), globalAngle);
    	
        
        }
        
    public void moveForward(double speed)
        {
        if (moMobile.getVelocity().length() < speed - .5)
        	moMobile.addForceComponent((new Double2D(speed, 0)).rotate(moMobile.getOrientation().radians));
        else if (moMobile.getVelocity().length() > speed + .5)
        	moMobile.addForceComponent((new Double2D(-speed, 0)).rotate(moMobile.getOrientation().radians));
                
        }
        
    public void goTo(Double2D globalDestination)
        {
        // First, get the destination in local coordinates
        Double2D localDestination = localFromGlobal(globalDestination);

        Angle localAngle = getAngle(localDestination);
        double angularVel = moMobile.getAngularVelocity();
        double angularError;
                
        // Turn towards the target
        if (localAngle.radians < Math.PI)
            angularError = localAngle.radians;
        else
            angularError = -(Angle.twoPI - localAngle.radians);
                
        double toAdd = P_angle * angularError - D_angle * angularVel;
        moMobile.addTorqueComponent(toAdd);
                
        // approach the target
        if (Math.abs(angularError) < Math.PI / 15)
            {
            if (localDestination.length() < 20)
            	moMobile.addForceComponent((new Double2D(4, 0)).rotate(moMobile.getOrientation().radians));
            else
                {
                double scale = P_pos * localDestination.length() - D_pos * moMobile.getVelocity().length();
                Double2D force = (new Double2D(1, 0)).rotate(moMobile.getOrientation().radians).scalarMult(scale); 
                moMobile.addForceComponent(force);
                }
            }
        else
            {
            // otherwise, hit the breaks
        	moMobile.addForceComponent(moMobile.getVelocity().rotate(Math.PI).scalarMult(10));
            }
        }
        
    public void stop()
        {
        double angularVel = moMobile.getAngularVelocity();
        Double2D vel = moMobile.getVelocity();
                
        moMobile.addForceComponent(vel.rotate(Math.PI).scalarMult(10));
        moMobile.addTorqueComponent(-angularVel * 200);
        }
        
    public void backup()
        {
        Double2D backward = new Double2D(4, 0).rotate(moMobile.getOrientation().add(Math.PI).radians);
        moMobile.addForceComponent(backward);
        }
    }
