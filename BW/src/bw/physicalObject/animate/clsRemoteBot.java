/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.animate;

import java.awt.Color;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.constraint.PinJoint;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import sim.util.Bag;
import bw.body.physicalObject.mobile.clsMotionPlatform;
import bw.physicalObject.entityParts.clsBotHands;
import bw.physicalObject.inanimate.mobile.clsCan;
import bw.sim.clsBWMain;

import sim.display.clsKeyListener;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsRemoteBot extends ARSsim.robot2D.clsRobot implements Steppable, ForceGenerator
    {
	private clsMotionPlatform moMotion;
	
    clsCan currentCan;
    private PinJoint pj;
    private Double2D canHome;
    private double canHomeRadius = 20;
        
    private ConstraintEngine objCE;
    private Double2D botHome;
        
    private double normalForce;
        
    private int botState;
        
    private final int HAVECAN = 1;
    private final int APPROACHINGCAN = 2;
    
    public clsBotHands e1;
    public clsBotHands e2;
    
    public int mnId;
    
    public int getId() {
		return mnId;
	}
    
    /**
	 * @return the botState
	 */
	public int getBotState() {
		return botState;
	}
	public Object domBotState() { return new String[] { "HAVECAN", "APPROACHINGCAN", "RELEASINGCAN", "RETURNINGHOME", "SEARCHING" }; }

	public void setId(int mnId) {
		this.mnId = mnId;
	}

	public clsRemoteBot(Double2D pos, Double2D vel, int pnId)
        {
		moMotion = new clsMotionPlatform(this);
		
        // vary the mass with the size
    	this.mnId = pnId;
        this.setPose(pos, new Angle(0));
        this.setVelocity(vel);
        this.setShape(new sim.physics2D.shape.Circle(10, Color.gray), 300);
                
        this.normalForce = this.getMass();
                
        currentCan = null;
                
        canHome = new Double2D(50, 50);
        botHome = pos;
                
        botState = APPROACHINGCAN;
                
        objCE = ConstraintEngine.getInstance();
        } 
 
    public void step(SimState state)
        {
        Double2D position = this.getPosition();
        clsBWMain simRobots = (clsBWMain)state;
        simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
        
             
        
        }

    public void addForce()
        {
        
    	//add remote control here!
    	
    	switch( clsKeyListener.getKeyPressed() )
    	{
    	case 38: //up
    		moMotion.moveForward(4.00);
    		break;
    	case 40: //down
    		moMotion.backup();
    		break;
    	case 37: //left
    		moMotion.faceTowards(new Angle(-1));
    		break;
    	case 39: //right
    		moMotion.faceTowards(new Angle(1));
    		break;
    	case 65: //'A'
    		break;
    	case 83: //'S'
            if(botState==HAVECAN)
            {
	    		objCE.unRegisterForceConstraint(pj);                            
	            botState = APPROACHINGCAN;
	            objCE.removeNoCollisions(this, currentCan);
	            objCE.removeNoCollisions(e1, currentCan);
	            objCE.removeNoCollisions(e2, currentCan);
	            currentCan.visible = true;
            }
    		break;
    	}

        }
        
    public int handleCollision(PhysicalObject2D other, Double2D colPoint)
        {
        Double2D globalPointPos = this.getPosition().add(colPoint);
        Double2D localPointPos = this.localFromGlobal(globalPointPos);
        Angle colAngle = this.getAngle(localPointPos);
                
        // Make sure the object is a can and that it is (roughly) between
        // the effectors
        if (other instanceof clsCan && botState == APPROACHINGCAN
            && (colAngle.radians < Math.PI / 8 || colAngle.radians > (Math.PI * 2 - Math.PI / 8)))
            {
            // Create a fixed joint directly at the center of the can
        	currentCan = (clsCan)other; 
        	pj = new PinJoint(other.getPosition(), this, other);
            objCE.registerForceConstraint(pj);
                        
            botState = HAVECAN;
                        
            objCE.setNoCollisions(this, other);
            objCE.setNoCollisions(e1, other);
            objCE.setNoCollisions(e2, other);
                        
            currentCan.visible = false;
            return 2; // sticky collision
            }
        else
            return 1; // regular collision
        }
    
    public boolean hitObject(Object object, DrawInfo2D range)
	    {
    		//TODO Clemens, hier gehört mehr rein als nur true!
	    	return true; // (insert location algorithm and intersection here)
	    } 
    
    }