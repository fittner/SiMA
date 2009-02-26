package TestApps.src.BumpTest;
import java.awt.Color;
import java.util.HashMap;

import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.constraint.PinJoint;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.SimpleInspector;
import sim.util.Bag;
public class AntBot extends sim.robot2D.Robot implements Steppable, ForceGenerator{
	Can currentCan;
	BotHome currentHome;
	private PinJoint pj;
	private Double2D canHome;
	private double canHomeRadius = 20;
	private ConstraintEngine objCE;
	private Double2D botHome;
	PhysicalObject2D colObj;
	private double normalForce;
	double visionDist = 10;
	private int botState;
	private boolean moIsBumped;
	private final int HAVECAN = 1;
	private final int APPROACHINGCAN = 2;
	private final int RELEASINGCAN = 3;
	private final int RETURNINGHOME = 4;
	private final int SEARCHING = 5;
	private final int FORWARDING = 6;
	private final int SEARCHINGHOME=7;
	private final int GETTINGHOME = 8;
	public Double2D position;
	Bag objs;
	int randomDir;
	public Effector e1;
	public Effector e2;
	HashMap<Integer, Integer>  internalEnergyTable =  new HashMap<Integer, Integer>();
	HashMap<String, Integer>  EnergyConsumptionTable =  new HashMap<String, Integer>();
	


	// public Pheromones p;
	
	// PhysicsEngine2D objPE;
	// Sensors
	private clsSensorBump moSensorBump;

		
	 public final static double[] Dir = new double[/* 8 */] {
			0 * (/* Strict */Math.PI / 180), 45 * (/* Strict */Math.PI / 180),
			90 * (/* Strict */Math.PI / 180), 135 * (/* Strict */Math.PI / 180),
			180 * (/* Strict */Math.PI / 180),
			225 * (/* Strict */Math.PI / 180),
			270 * (/* Strict */Math.PI / 180),
			315 * (/* Strict */Math.PI / 180) };

	public AntBot(Double2D pos, Double2D vel) {
		// vary the mass with the size
		this.setPose(pos, new Angle(0));
		this.setVelocity(vel);
		this.setShape(new sim.physics2D.shape.Circle(5, Color.gray), 300);
		this.setCoefficientOfFriction(.2);
		this.setCoefficientOfStaticFriction(0);
		this.setCoefficientOfRestitution(1);
		moIsBumped = false;
		this.normalForce = this.getMass();
		
		//Intialization of HashMap
		internalEnergyTable.put(1, 500);
		internalEnergyTable.put(2, 300);
		internalEnergyTable.put(3, 200);
		
		//Just for JTreeTableTest
		
		
		
		currentCan = null;
		currentHome=null;

		canHome = new Double2D(10, 10);
		botHome =  new Double2D(12, 12);

		botState = SEARCHING;

		objCE = ConstraintEngine.getInstance();

		moSensorBump = new clsSensorBump();

		// objPE = new PhysicsEngine2D();
		objs = null;
		randomDir=0;
		position=new Double2D(0,0);


	}

	public void step(SimState state) {

		position = this.getPosition();
		Robots simRobots = (Robots) state;
		simRobots.fieldEnvironment.setObjectLocation(this,
				new sim.util.Double2D(position.x, position.y));
		
		System.out.println(simRobots.fieldEnvironment.hashCode());
		
		
		moSensorBump.resetSensor();
		randomDir=simRobots.random.nextInt(8);
		// Find a can
		if (botState == SEARCHING) {
			simRobots.SpeadingPheromones(simRobots.fieldEnvironment,
					simRobots.pID + 1, position);
			objs = simRobots.fieldEnvironment.getObjectsWithinDistance(
					new sim.util.Double2D(position.x, position.y), visionDist);
			canVisibilityTest();
		}
		
		
//		Searching home
		
		if(botState==SEARCHINGHOME){
			simRobots.SpeadingHomePheromones(simRobots.fieldEnvironment,
					 position);
			objs = simRobots.fieldEnvironment.getObjectsWithinDistance(
					new sim.util.Double2D(position.x, position.y), visionDist);
			homeVisibilityTest();
			
		}
		
		
		//Consumption
		int val1=internalEnergyTable.get(1);
		internalEnergyTable.put(1, val1-1);
		int val2=internalEnergyTable.get(2);
		internalEnergyTable.put(2, val2-1);
		int val3=internalEnergyTable.get(3);
		internalEnergyTable.put(3, val3-1);
		
		//Energy Consumption for the test
		
		
		
	}

	
	void canVisibilityTest(){
		
		

		// TODO Auto-generated catch block

		if (objs == null) {

			botState = FORWARDING;
			currentCan = null;
		}

		else {

			for (int i = 0; i < objs.numObjs; i++) {
				if (objs.objs[i] instanceof Can) {
					currentCan = (Can) objs.objs[i];
					if (currentCan.visible) {
						botState = APPROACHINGCAN;
						break;
					} else
						currentCan = null; // can is already home or has
											// been picked
					// up by another bot
				}
			}

		}

		if (currentCan == null) {
			botState = FORWARDING;
			objs=null;
		}
	}
	
	void homeVisibilityTest(){
		if (objs == null) {

			botState = RETURNINGHOME;
			currentHome = null;
		}
		else {

			for (int i = 0; i < objs.numObjs; i++) {
				if (objs.objs[i] instanceof BotHome) {
					currentHome = (BotHome) objs.objs[i];
					if (currentHome.homeVisible) {
						botState = GETTINGHOME;
						break;
					} else
						currentHome = null; // can is already home or has
											// been picked
					// up by another bot
				}
			}

		}
		if (currentHome == null) {
			botState = GETTINGHOME;
			objs=null;
		}
		
	}
	
	
	
	public void addForce() {

		switch (botState) {

		case HAVECAN:
			if ((this.getPosition().x== botHome.getX())&&(this.getPosition().y== botHome.getY())) {
				if (this.getVelocity().length() > 0.01
						|| this.getAngularVelocity() > 0.01)
					this.stop();
				else {
					objCE.unRegisterForceConstraint(pj);
					botState = RELEASINGCAN;
					objCE.removeNoCollisions(this, currentCan);
					objCE.removeNoCollisions(e1, currentCan);
					objCE.removeNoCollisions(e2, currentCan);
					currentCan.visible = true;
				}
			} else
				botState=GETTINGHOME;
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
				this.faceTowards(new Angle(Dir[randomDir]));
				this.moveForward(5.0);
				botState = SEARCHINGHOME;
			
			break;

		case FORWARDING:
			
			if (moSensorBump.isBumped() == true) {
				this.backup();
				this.stop();
				this.faceTowards(new Angle(Dir[randomDir]));
				botState = SEARCHING;
			} else{
				this.faceTowards(new Angle(Dir[randomDir]));
				this.moveForward(5.0);
				botState = SEARCHING;
			}
			break;
		
		case GETTINGHOME:
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

	public int handleCollision(PhysicalObject2D other, Double2D colPoint) {

		Double2D globalPointPos = this.getPosition().add(colPoint);
		Double2D localPointPos = this.localFromGlobal(globalPointPos);
		Angle colAngle = this.getAngle(localPointPos);
		// Make sure the object is a can and that it is (roughly) between
		// the effectors
		
		if (other instanceof Can
				&& botState == APPROACHINGCAN
				&& (colAngle.radians < Math.PI / 8 || colAngle.radians > (Math.PI * 2 - Math.PI / 8))) {
			// Create a fixed joint directly at the center of the can
			pj = new PinJoint(other.getPosition(), this, other);
			objCE.registerForceConstraint(pj);

			botState = HAVECAN;

			objCE.setNoCollisions(this, other);
			objCE.setNoCollisions(e1, other);
			objCE.setNoCollisions(e2, other);

			currentCan.visible = false;
			return 2; // sticky collision
		} else {

			return 1; // regular collision
		}

	}
	
	Double2D getX(){
		return position;
	}
	
    public Inspector getInspector(LocationWrapper wrapper, GUIState state){
		//Override to get constantly updating inspectors = volatile
    	
        if (wrapper == null) return null;
        SimpleInspector oSimp = new SimpleInspector(wrapper.getObject(), state, "Properties");
    	
    	clsNewInspector oInsp = new clsNewInspector(oSimp, wrapper, state); //(SimplePortrayal2D)this.getInspector()
	    return oInsp;
	   
    }
	
	public boolean hitObject(Object object, DrawInfo2D range)
	    {
 		//TODO Clemens, hier gehört mehr rein als nur true!
	    	return true; // (insert location algorithm and intersection here)
	    } 
	public int getHashMapValue(int key){
		return internalEnergyTable.get(key);
	}
	
	public int getHashMapValue1(String key){
		return EnergyConsumptionTable.get(key);
	}

}
