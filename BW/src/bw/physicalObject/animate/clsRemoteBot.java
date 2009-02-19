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
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.robot2D.clsMotionPlatform;
import bw.physicalObject.entityParts.clsBotHands;
import bw.physicalObject.inanimate.mobile.clsCan;
import bw.sim.clsBWMain;
import bw.utils.enums.eEntityType;


import sim.display.clsKeyListener;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsRemoteBot extends clsAnimate implements Steppable
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
		super(pos, vel, pnId);
		
		clsMobileObject2D oMobile = getMobile();
		moMotion = new clsMotionPlatform(oMobile);
		
        // vary the mass with the size
    	this.mnId = pnId;
    	oMobile.setPose(pos, new Angle(0));
    	oMobile.setVelocity(vel);
    	oMobile.setShape(new sim.physics2D.shape.Circle(10, Color.gray), 300);
                
        this.normalForce = oMobile.getMass();
                
        currentCan = null;
                
        canHome = new Double2D(50, 50);
        botHome = pos;
                
        botState = APPROACHINGCAN;
                
        objCE = ConstraintEngine.getInstance();
        } 
 
    public void step(SimState state)
        {
        Double2D position = getMobile().getPosition();
        clsBWMain simRobots = (clsBWMain)state;
        simRobots.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
        }

 /*   public void addForceEntity()
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
    		moMotion.faceTowardsRelative(new Angle(-1));
    		break;
    	case 39: //right
    		moMotion.faceTowardsRelative(new Angle(1));
    		break;
    	case 65: //'A'
    		break;
    	case 83: //'S'
            if(botState==HAVECAN)
            {
	    		objCE.unRegisterForceConstraint(pj);                            
	            botState = APPROACHINGCAN;
	            objCE.removeNoCollisions(getMobile(), currentCan.getMobile());
	            objCE.removeNoCollisions(e1, currentCan.getMobile());
	            objCE.removeNoCollisions(e2, currentCan.getMobile());
	            currentCan.visible = true;
            }
    		break;
    	}

        }
*/        
/*    public int handleCollision(PhysicalObject2D other, Double2D colPoint)
        {
        Double2D globalPointPos = getMobile().getPosition().add(colPoint);
        Double2D localPointPos = moMotion.localFromGlobal(globalPointPos);
        Angle colAngle = moMotion.getAngle(localPointPos);
                
        // Make sure the object is a can and that it is (roughly) between
        // the effectors
        if (((clsMobileObject2D)other).getEntity().isEntityType(eEntityType.CAN) && botState == APPROACHINGCAN
            && (colAngle.radians < Math.PI / 8 || colAngle.radians > (Math.PI * 2 - Math.PI / 8)))
            {
            // Create a fixed joint directly at the center of the can
        	pj = new PinJoint(other.getPosition(), getMobile(), other);
            objCE.registerForceConstraint(pj);
                        
            botState = HAVECAN;
                        
            objCE.setNoCollisions(getMobile(), other);
            objCE.setNoCollisions(e1, other);
            objCE.setNoCollisions(e2, other);
                        
            currentCan.visible = false;
            return 2; // sticky collision
            }
        else
            return 1; // regular collision
        }
 */   

    /* (non-Javadoc)
	 * @see bw.clsEntity#getEntityType()
	 */
	@Override
	public eEntityType getEntityType() {
		return eEntityType.REMOTEBOT;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution()
	 */
	@Override
	public void execution() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#thinking()
	 */
	@Override
	public void thinking() {
		// TODO Auto-generated method stub
		
	}
    
    }