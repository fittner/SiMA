/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.mobile;

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
import bw.body.physicalObject.effector.clsBotHands;
import bw.body.physicalObject.stationary.clsCan;
import bw.sim.clsBWMain;

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
    private final int RELEASINGCAN = 3;
    private final int RETURNINGHOME = 4;
    private final int SEARCHING = 5;
        
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
                
        botState = SEARCHING;
                
        objCE = ConstraintEngine.getInstance();
        } 
 
    public void step(SimState state)
        {
        Double2D position = this.getPosition();
        clsBWMain simRobots = (clsBWMain)state;
        simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
        
        // Find a can
        if (botState == SEARCHING)
            {
            Bag objs = simRobots.moGameGridField.allObjects;
            objs.shuffle(state.random);
            for (int i = 0; i < objs.numObjs; i++)
                {
                if (objs.objs[i] instanceof clsCan)
                    {
                    currentCan = (clsCan)objs.objs[i];
                    if ( currentCan.visible) //currentCan.getPosition().y > 50 &&
                        { 
                        botState = APPROACHINGCAN;      
                        break;
                        }
                    else
                        currentCan = null; // can is already home or has been picked
                    // up by another bot
                    }
                }
                        
            if (currentCan == null)
                botState = RETURNINGHOME;
            }
        
        
        }

    public void addForce()
        {
        
    	//add remote control here!
    	
        switch (botState)
            {
            case HAVECAN:
            	if( mnId%2==0 )
            	{
	            	if (this.getPosition().y <= 40)
	                    {
	                    if (this.getVelocity().length() > 0.01 || this.getAngularVelocity() > 0.01)
	                        this.stop();
	                    else
	                        {
	                        objCE.unRegisterForceConstraint(pj);                            
	                        botState = RELEASINGCAN;
	                        objCE.removeNoCollisions(this, currentCan);
	                        objCE.removeNoCollisions(e1, currentCan);
	                        objCE.removeNoCollisions(e2, currentCan);
	                        currentCan.visible = true;
	                        }
	                    }
	                else
	                    this.goTo(new Double2D(this.getPosition().x, 40));
            	}
            	else
            	{
            	if (this.getPosition().y >= 160)
                {
	                if (this.getVelocity().length() > 0.01 || this.getAngularVelocity() > 0.01)
	                    this.stop();
	                else
	                    {
	                    objCE.unRegisterForceConstraint(pj);                            
	                    botState = RELEASINGCAN;
	                    objCE.removeNoCollisions(this, currentCan);
	                    objCE.removeNoCollisions(e1, currentCan);
	                    objCE.removeNoCollisions(e2, currentCan);
	                    currentCan.visible = true;
	                    }
	                }
		            else
		                this.goTo(new Double2D(this.getPosition().x, 160));
            	}
                break;
            case RELEASINGCAN:
                // back out of can home
                if (this.getPosition().subtract(currentCan.getPosition()).length() <= 30)
                    backup();
                else
                    botState = SEARCHING;
                break;
            case APPROACHINGCAN:
                if (currentCan.visible)
                    this.goTo(currentCan.getPosition());
                else
                    botState = SEARCHING;
                break;
            case RETURNINGHOME:
                if (this.getPosition().subtract(botHome).length() <= 30)
                    {
                    if (this.getOrientation().radians != 0)
                        this.faceTowards(new Angle(0));
                    else
                        stop();
                    }
                else
                    this.goTo(botHome);
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
    		//TODO Clemens, hier geh�rt mehr rein als nur true!
	    	return true; // (insert location algorithm and intersection here)
	    } 
    
    }