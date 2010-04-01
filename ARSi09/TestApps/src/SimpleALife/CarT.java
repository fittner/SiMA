package SimpleALife;

import java.awt.*;
import SimpleALife.HerR;
import SimpleALife.Head;
import SimpleALife.SimLifeArena;
import sim.engine.*;
import sim.physics2D.constraint.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.*;
import sim.util.Bag;

public class CarT extends TestApps.Physics2D.BubbleRace.FrictionRobot implements Steppable, ForceGenerator
{
	private static final long serialVersionUID = 1L;

	private ConstraintEngine objCE;
	private PinJoint pj;

	private Double2D home, position, endTargetPosition, subTargetPosition;
	private int size;
	private double maxVelocity;
	private Bag allObjs;
	private HerR currentHerR;
	private boolean observerStatus;
	private int steps = 0;

	private int status, oldStatus = 0;
	private final int HAVEHERR = 1;
	private final int APPROACHINGHERR = 2;
	private final int RELEASINGHERR = 3;
	private final int RETURNINGHOME = 4;
	private final int SEARCHING = 5;
	private final int EAT = 6;

	public Head head;

	public CarT(Double2D pos, Double2D vel)
	{
		this.size = 15;
		this.mass = 300;		// vary the mass with the size (!! exception when it's to small)
		this.maxVelocity = 3.5;

		this.setPose(pos, new Angle(0));
		this.setVelocity(vel);
		this.setShape(new sim.physics2D.shape.Circle(this.size, Color.gray), mass);
		this.setCoefficientOfFriction(.3);		// "Reibung" (0..perfectly slippery)
		this.setCoefficientOfRestitution(0.35);	// "Elastic" (0..inelastic (slimy), 1..perfectly elastic (flummy)) (!! exception when it's to small)

		this.home = pos;
		this.status = SEARCHING;
		this.currentHerR = null;
		this.observerStatus = false;
		this.subTargetPosition = null;

		this.objCE = ConstraintEngine.getInstance();
	}

	// function for "thinking" states
    public void step(SimState state)
	{
    	steps++;
//    	if (SimLifeMath.modulo(steps, 100) == 0)
//    		System.out.println(" Steps: "+steps+", Velocity: "+this.getVelocity());

		position = this.getPosition();
		//SimLifeArena simLifeArena = (SimLifeArena)state;
		SimLifeArena.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));

		// place for things that should react on every status change
		if (oldStatus != status)
    	{
    		oldStatus = status;

    		// if the CarT state changes calculate a new target position instantly
        	subTargetPosition = null;
    	}

		switch (status)
		{
			// Find an animal
			case SEARCHING:
				if (observerStatus == true)
				{
					if (currentHerR != null)
					{
						currentHerR.setObservation(false);			// unmark previously targeted HerR
						currentHerR.unSetObserver(this.getIndex());
					}
					observerStatus = false;
				}

				currentHerR = null;
				HerR herRHit = null;
//				int visibleHerRs = 0;	// debug
				double dist = SimLifeArena.maxDist;
				double newDist;
				allObjs = SimLifeArena.fieldEnvironment.allObjects;

				for (int i = 0; i < allObjs.numObjs; i++)
				{
					if (allObjs.objs[i] instanceof HerR)
					{
						herRHit = (HerR)allObjs.objs[i];
						if (herRHit.visible)
						{
//							visibleHerRs++;								// debug
							// Find the nearest HerR
							newDist = position.subtract(herRHit.getPosition()).length();
							if (newDist < dist)
							{
								dist = newDist;
								currentHerR = herRHit;
							}
						}
					}
				}

				if  (currentHerR != null)
				{
//					System.out.println("CarT "+this.index+" find HerR "+currentHerR.index +" "+currentHerR.getPosition()+" in dist "+dist +" - see "+allObjs.numObjs+" objects, with "+visibleHerRs+" visible HerR");	// debug

					status = APPROACHINGHERR;
				}
				else	// all animals are already eaten or has been picked up by another CarT
					status = RETURNINGHOME;
				break;
		}
	}

    @Override
    public int handleCollision(PhysicalObject2D other, Double2D colPoint)
	{
		Double2D globalPointPos = this.getPosition().add(colPoint);
		Double2D localPointPos = this.localFromGlobal(globalPointPos);
		Angle colAngle = this.getAngle(localPointPos);

		subTargetPosition = null;	// to instantly calculate a new target after COLLISION
									// TODO: because the CarT can "synergise" with a HerR, the Coll. method in "HerR" should although reset the subTarget

//		System.out.println("CarT "+this.index+" collide with "+other.getClass()+" "+other.index);	// debug

		// Make sure the object is our targeted animal and if is's (roughly) in the "catch area"
		if (other.equals(currentHerR) && status == APPROACHINGHERR
				&& (colAngle.radians < Math.PI / 8 || colAngle.radians > (Math.PI * 2 - Math.PI / 8)))
		{
			// Create a fixed joint directly at the center of the animal
			pj = new PinJoint(other.getPosition(), this, other);
			objCE.registerForceConstraint(pj);

			status = HAVEHERR;

			// deactivate the collision handling
			objCE.setNoCollisions(this, other);
			objCE.setNoCollisions(head, other);

			// hide the can from the other Bots
			currentHerR.visible = false;

			// set this CarT index in the grabbed HerR, that the HerR know his "master" CarT
			currentHerR.setBelongedCarT(this.index);

//			System.out.println("CarT " + this.index + " have HerR " + currentHerR.index + " " + currentHerR.getPosition());	// debug

			return 2; // sticky collision
		}
		else
		{
			backup();

			return 1; // regular collision
		}
	}

    // function for "doing" states
    public void addForce()
	{
    	double actualVelocity = this.getVelocity().length();
		if (actualVelocity > maxVelocity)
		{
			double factor = maxVelocity / actualVelocity;
			this.setVelocity(new Double2D(this.getVelocity().x * factor, this.getVelocity().y * factor));
		}

		switch (status)
		{
			case APPROACHINGHERR:
				if (currentHerR.visible)
				{
					if (observerStatus == false)
					{
						currentHerR.setObservation(true);			// mark targeted HerR
						currentHerR.setObserver(this.getIndex());
						observerStatus = true;
					}

//					Double2D vector = currentHerR.getPosition().subtract(position);
//					double newLength = vector.length() * 1.05;							// to go beyond the target pos
//					Double2D tempTarget = vector.setLength(newLength).add(position);
//					Double2D tempPosition = SimLifeMath.limitToBorders(position, tempTarget, this.size);
//					this.goToCollisionFree(tempPosition);
					this.goToCollisionFree(currentHerR.getPosition());
				}
				else
					status = SEARCHING;
				break;
			case HAVEHERR:
				status = EAT;

				// bring it home
//				if (this.getPosition().y <= 50)
//				{
//					if (this.getVelocity().length() > 0.01 || this.getAngularVelocity() > 0.01)
//						this.stop();
//					else
//						status = EAT;
//				}
//				else
//					this.goToCollisionFree(new Double2D(this.getPosition().x, 20));
				break;
			case RELEASINGHERR:
				// back out of CarT home
				if (this.getPosition().subtract(currentHerR.getPosition()).length() <= 30)
					backup();
				else
					status = SEARCHING;
				break;
			case EAT:
				if (currentHerR.currentMass > 0)
					currentHerR.currentMass -= 0.01;
				else
				{
//					System.out.println("CarT "+this.index+" eat  HerR "+currentHerR.index+" "+this.getPosition());	// debug

					objCE.unRegisterForceConstraint(pj);
					currentHerR.unSetBelongedCarT();

					objCE.removeNoCollisions(this, currentHerR);
					objCE.removeNoCollisions(head, currentHerR);

					SimLifeArena.removeHerR(currentHerR);
					observerStatus = false;
					status = SEARCHING;
				}
				break;
			case RETURNINGHOME:
				if (this.getPosition().subtract(home).length() <= 30)
				{
					if (SimLifeMath.modulo(Math.abs(this.getOrientation().radians), 2*Math.PI) > 0.01)
						this.faceTowards(new Angle(0));
					else
						stop();
				}
				else
					this.goToCollisionFree(home);
				break;
		}
	}

    // this method only works for single standing objects (now only Rocks/Walls),
    // it don't works for overlapping objects (like crossing Rocks/Walls) or objects they are so near that the hole is to small
    private void goToCollisionFree(Double2D endTarget)
    {
    	Double2D subTargetPosition1, subTargetPosition2 = null;

    	// find the next "collision free" sup target if no one is defined (->Start/StateChange/Collision) or it is reached
    	if (subTargetPosition == null || this.getPosition().subtract(subTargetPosition).length() <= 5)
    	{
    		// limit the endTargetPosition to the arena borders
			endTargetPosition = SimLifeMath.limitToBorders(endTarget, this.size);
			// TODO: for robustness .. if endTarPos != endTar -> mark as unreachable and search a new one

			// find the next collision free target for the way to the end target
			subTargetPosition1 = endTargetPosition;
			subTargetPosition2 = SimLifeMath.getSubTargetPos(position, subTargetPosition1, allObjs);

			while (subTargetPosition1.getX() != subTargetPosition2.getX() || subTargetPosition1.getY() != subTargetPosition2.getY())
			{
//				System.out.println("CarT"+this.index+", Pos "+this.position+",s subT1 "+subTargetPosition1+", subT2 "+subTargetPosition2);	// debug

				subTargetPosition1 = subTargetPosition2;
				subTargetPosition2 = SimLifeMath.getSubTargetPos(position, subTargetPosition1, allObjs);
			}

			// limit the subTargetPosition to the arena borders
			subTargetPosition = SimLifeMath.limitToBorders(subTargetPosition2, this.size);

//			System.out.println("CarT "+this.index+", State "+status+", Pos "+this.position+", EndT "+endTargetPosition+", newEndT "+subTargetPosition);	// debug
    	}

    	// now got "collision free" to the found sub target point,
    	this.goTo(subTargetPosition);
    }

    public void resetSubTargetPosition()
    {
    	subTargetPosition = null;
    }
}