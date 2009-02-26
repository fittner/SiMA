package ReinformentAgent;
import java.awt.*;

import sim.physics2D.constraint.*;
import sim.engine.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.*;
import sim.util.Bag;

public class Bot extends sim.robot2D.Robot implements Steppable, ForceGenerator{
	Can currentCan;
    private PinJoint pj;
    private Double2D canHome;
    private double canHomeRadius = 20;
    private ConstraintEngine objCE;
  	private Double2D botHome;
  	PhysicalObject2D colObj;    
    private double normalForce;
    public Effector e1;
    public Effector e2;
 // **************************learning parameters start*******************************   
    // learning parameters
    public double epsilon,alpha,gamma,lambda;
    // Q table
    int numState,numAction;
    public double [][][] qEST;
    // Frequency table
    public double [][][] elig;
    private int state_rows,state_cols,actions;
    
    
 // **************************learning parameters end********************************* 
 
 // **************************Scenario modeling parameters start*********************************    
    //Bot possible states    
    private int botState;
    private final int HAVECAN = 1;
    private final int APPROACHINGCAN = 2;
    private final int APPROACHINGHOME = 3;
    private final int SEARCHINGCAN = 4;
    private final int SEARCHINGHOME = 5;
    private final int HAVEHOME = 6;
    
    //Bot possible actions
    public String [] botAction ; 
    private final int MOVEFORWARD = 1;
    private final int BACKUP = 2;
    private final int FACETOWARDNORTH = 3;
    private final int FACETOWARDSOUTH = 4;
    private final int FACETOWARDEAST = 5;
    private final int FACETOWARDWEST = 6;
    private final int GOTOGETCAN = 7;
    private final int GOTOGETHOME = 8;
    private final int PICKCAN = 9;
    private final int RELEASINGCAN = 10;
    
    
    //Bot possible directions
    private final int NORTH = 1;
    private final int SOUTH = 2;
    private final int EAST = 3;
    private final int WEST = 4;
    
    //Bot possible state actions arrays
    public int [] HAVECAN_p_action;
    public int [] APPROACHINGCAN_p_action;
    public int [] APPROACHINGHOME_p_action;
    public int [] SEARCHINGCAN_p_action;
    public int [] SEARCHINGHOME_p_action;
    public int [] HAVEHOME_p_action;
   
    //Bot possible state state transition arrays
    public int [] HAVECAN_p_state;
    public int [] APPROACHINGCAN_p_state;
    public int [] APPROACHINGHOME_p_state;
    public int [] SEARCHINGCAN_p_state;
    public int [] SEARCHINGHOME_p_state;
    public int [] HAVEHOME_p_state;
 
// **************************Scenario modeling parameters end*************************************
 
// **********************Scenario modeling parameters intialization function start****************
    public void inil_Para_Scenario(){
    	
    	//Bot possible state action arrays initalization
    	HAVECAN_p_action=new int [1];
    	APPROACHINGCAN_p_action=new int[1];
    	APPROACHINGHOME_p_action=new int[1];
    	SEARCHINGCAN_p_action=new int[6];
    	SEARCHINGHOME_p_state=new int[6];
    	HAVEHOME_p_action=new int[1];
    	
    	//Bot possible state state arrays initalization
    	HAVECAN_p_state=new int[1];
    	APPROACHINGCAN_p_state=new int[1];
    	APPROACHINGHOME_p_state=new int[1];
    	SEARCHINGCAN_p_state=new int[1];
    	SEARCHINGHOME_p_state=new int[1];
    	HAVEHOME_p_state=new int[1];
    }
// **********************Scenario modeling parameters intialization function end*******************   

// **********************learning parameters intialization function start**************************
    public void inil_para_learning(){
    	state_rows=6;
    	state_cols=1;
    	qEST=new double[state_rows][state_cols][actions];
    }
    
    
    
// **********************learning parameters intialization function start**************************
    
    
    public Bot(Double2D pos, Double2D vel)
    {
    // vary the mass with the size
    this.setPose(pos, new Angle(0));
    this.setVelocity(vel);
    this.setShape(new sim.physics2D.shape.Circle(5, Color.gray), 300);
    this.setCoefficientOfFriction(.2);
    this.setCoefficientOfStaticFriction(0);
    this.setCoefficientOfRestitution(1);
          
    this.normalForce = this.getMass();
            
    currentCan = null;
            
    canHome = new Double2D(10, 10);
    botHome = pos;
            
    botState = SEARCHINGFOOD;
            
    objCE = ConstraintEngine.getInstance();
    }
    
    public void step(SimState state)
    {
    Double2D position = this.getPosition();
    Robots simRobots = (Robots)state;
    simRobots.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
 	
  
    
    // Find a can
    if (botState == SEARCHINGFOOD)
        {
        Bag objs = simRobots.fieldEnvironment.allObjects;
        objs.shuffle(state.random);
        for (int i = 0; i < objs.numObjs; i++)
            {
            if (objs.objs[i] instanceof Can)
                {
                currentCan = (Can)objs.objs[i];
                if (currentCan.getPosition().y > 50 && currentCan.visible)
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
            
    switch (botState)
        {
        case HAVECAN:
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
            break;
        case RELEASINGCAN:
            // back out of can home
            if (this.getPosition().subtract(currentCan.getPosition()).length() <= 30)
                backup();
            else
                botState = SEARCHINGFOOD;
            break;
        case APPROACHINGCAN:
            if (currentCan.visible)
                this.goTo(currentCan.getPosition());
            else
                botState = SEARCHINGFOOD;
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
    if (other instanceof Can && botState == APPROACHINGCAN
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
    {
    	
    	return 1; // regular collision
    }
    
    
    }  


     }
    
    

