/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.animate;

import java.awt.*;

import sim.physics2D.constraint.*;

import sim.engine.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.*;
import sim.portrayal.DrawInfo2D;
import sim.util.Bag;
import ARSsim.motionplatform.clsMotionPlatform;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.physicalObject.entityParts.clsBotHands;
import bw.physicalObject.inanimate.mobile.clsCan;
import bw.sim.*;
import bw.utils.enums.eEntityType;

/* Mason test robot implementation. 
* 
* @author langr
* 
*/
public class clsBot //extends clsAnimate implements Steppable, ForceGenerator
    {
/*	private clsMotionPlatform moMotion;
	
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
    

	public int getBotState() {
		return botState;
	}
	public Object domBotState() { return new String[] { "HAVECAN", "APPROACHINGCAN", "RELEASINGCAN", "RETURNINGHOME", "SEARCHING" }; }

	public void setId(int mnId) {
		this.mnId = mnId;
	}

	public clsBot(Double2D pos, Double2D vel, int pnId)
        {
		super(pos, vel, pnId);
		
		MobileObject2D oMobile = getMobile();
		
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
                
        botState = SEARCHING;
                
        objCE = ConstraintEngine.getInstance();
        } 
 
     public void addForceEntity()
        {
        
    	clsMobileObject2D oMobile = getMobile();  
    	
        switch (botState)
            {
            case HAVECAN:
            	if( mnId%2==0 )
            	{
	            	if (oMobile.getPosition().y <= 40)
	                    {
	                    if (oMobile.getVelocity().length() > 0.01 || oMobile.getAngularVelocity() > 0.01)
	                    	moMotion.stop();
	                    else
	                        {
	                        clsMobileObject2D oCanMobile = null;
	                        oCanMobile = currentCan.getMobile();

	                        objCE.unRegisterForceConstraint(pj);                            
	                        botState = RELEASINGCAN;
	                        objCE.removeNoCollisions(oMobile, oCanMobile);
	                        objCE.removeNoCollisions(e1, oCanMobile);
	                        objCE.removeNoCollisions(e2, oCanMobile);
	                        currentCan.visible = true;
	                        }
	                    }
	                else
	                	moMotion.goTo(new Double2D(oMobile.getPosition().x, 40));
            	}
            	else
            	{
            	if (oMobile.getPosition().y >= 160)
                {
	                if (oMobile.getVelocity().length() > 0.01 || oMobile.getAngularVelocity() > 0.01)
	                	moMotion.stop();
	                else
	                    {
                        clsMobileObject2D oCanMobile = null;
                        oCanMobile = currentCan.getMobile();
                        
	                    objCE.unRegisterForceConstraint(pj);                            
	                    botState = RELEASINGCAN;
	                    objCE.removeNoCollisions(oMobile, oCanMobile);
	                    objCE.removeNoCollisions(e1, oCanMobile);
	                    objCE.removeNoCollisions(e2, oCanMobile);
	                    currentCan.visible = true;
	                    }
	                }
		            else
		            	moMotion.goTo(new Double2D(oMobile.getPosition().x, 160));
            	}
                break;
            case RELEASINGCAN:
                // back out of can home
                if (oMobile.getPosition().subtract(currentCan.getMobile().getPosition()).length() <= 30)
                	moMotion.backup();
                else
                    botState = SEARCHING;
                break;
            case APPROACHINGCAN:
                if (currentCan.visible)
                	moMotion.goTo(currentCan.getMobile().getPosition());
                else
                    botState = SEARCHING;
                break;
            case RETURNINGHOME:
                if (oMobile.getPosition().subtract(botHome).length() <= 30)
                    {
                    if (oMobile.getOrientation().radians != 0)
                    	moMotion.faceTowards(new Angle(0));
                    else
                    	moMotion.stop();
                    }
                else
                	moMotion.goTo(botHome);
                break;  
            }
        }
        
   public int handleCollision(PhysicalObject2D other, Double2D colPoint)
        {
    	clsMobileObject2D oMobile = getMobile();  
        Double2D globalPointPos = oMobile.getPosition().add(colPoint);
        Double2D localPointPos = moMotion.localFromGlobal(globalPointPos);
        Angle colAngle = moMotion.getAngle(localPointPos);
                
        // Make sure the object is a can and that it is (roughly) between
        // the effectors
        if (((clsMobileObject2D)other).isEntityType(eEntityType.CAN) && botState == APPROACHINGCAN  && other == currentCan.getMobile()
            && (colAngle.radians < Math.PI / 8 || colAngle.radians > (Math.PI * 2 - Math.PI / 8)))
            {
            // Create a fixed joint directly at the center of the can
            pj = new PinJoint(other.getPosition(), oMobile, other);
            objCE.registerForceConstraint(pj);
                        
            botState = HAVECAN;
                        
            objCE.setNoCollisions(oMobile, other);
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


	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BOT;
	}


	@Override
	public void addForce() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void thinking() {
		
       Double2D position = getMobile().getPosition();
        clsBWMain simRobots = (clsBWMain)state;
        simRobots.moGameGridField.setObjectLocation(this.getMobile(), new sim.util.Double2D(position.x, position.y));
        
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

	@Override
	public void execution() {
		// TODO Auto-generated method stub
		
	}
*/
}
