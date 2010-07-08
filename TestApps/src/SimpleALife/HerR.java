package SimpleALife;

import java.util.ArrayList;
import java.awt.*;
import SimpleALife.PlaG;
import SimpleALife.SimLifeArena;
import sim.engine.*;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;
import sim.util.Bag;

public class HerR extends TestApps.Physics2D.BubbleRace.FrictionRobot implements Steppable, ForceGenerator
{
	private static final long serialVersionUID = 1L;

	private Double2D home, position, endTargetPosition, subTargetPosition, tempTarget;
	private int mass, size;
	private double maxVelocity;
	private Bag allObjs;
	private CarT belongedCarT;				// that the HerR knows to which CarT he belongs
	private ArrayList<CarT> observerCarT;	// that the HerR knows which CarT has "an eye" on him
	private double thinkAhead;				// how long he run away in one direction before he calc a new target
	private PlaG currentPlaG;
	private int tempWait;
	private int steps = 0;

	private int status, oldStatus = 0, observed;
	private final int NORMAL = 1;
	private final int ISTARGET = 2;
	private final int APPROACHINGPLAG = 3;
	private final int RETURNINGHOME = 4;
	private final int SEARCHING = 5;
	private final int EAT = 6;
	private final int WALKAROUND = 7;
	private final int RUNAWAY = 8;

	public boolean visible;
	public double currentMass;

	public HerR(Double2D pos, Double2D vel)
	{
		this.size = 8;
		this.mass = 30;			// vary the mass with the size (!! exception when it's to small (eg.2))
		this.maxVelocity = 3.0;
		this.thinkAhead = 50.0;

		this.setPose(pos, new Angle(0));
		this.setVelocity(vel);
		this.setShape(new sim.physics2D.shape.Circle(size, Color.blue), mass);
		this.setCoefficientOfFriction(.3);
		this.setCoefficientOfRestitution(0.35);		// (!! exception when it's to small)

		this.home = pos;
		this.status = NORMAL;
		this.currentPlaG = null;
		this.observed =  0;
		this.observerCarT = new ArrayList<CarT>();
		this.visible = true;
		this.currentMass = mass;
		this.subTargetPosition = null;
		this.tempWait = 0;
	}

	// function for "thinking" states
	@Override
	public void step(SimState state)
	{
		steps++;

		position = this.getPosition();
//		SimLifeArena simLifeArena = (SimLifeArena)state;
		SimLifeArena.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));

		// place for things that should react on every status change
		if (oldStatus != status)
    	{
    		oldStatus = status;
    		allObjs = SimLifeArena.fieldEnvironment.allObjects;

    		// if the HerR state changes calculate a new target position instantly
        	subTargetPosition = null;
    	}

		switch (status)
		{
			case NORMAL:
				// now only searching for PlaG
				status = SEARCHING;
				break;
			case SEARCHING:
				// Find PlaG
				currentPlaG = null;
				PlaG plaGHit = null;
				double dist = SimLifeArena.maxDist;
				double newDist;

				for (int i = 0; i < allObjs.numObjs; i++)
				{
					if (allObjs.objs[i] instanceof PlaG)
					{
						plaGHit = (PlaG)allObjs.objs[i];
						if (plaGHit.currentMass > 0)
						{
							// Find the nearest PlaG
							newDist = position.subtract(plaGHit.getPosition()).length();
							if (newDist < dist)
							{
								dist = newDist;
								currentPlaG = plaGHit;
							}
						}
					}
				}

				if  (currentPlaG != null)
				{
//					System.out.println("HerR "+this.index+" find PlaG "+currentPlaG.index +" "+currentPlaG.getPosition()+" in dist "+dist +" - see "+allObjs.numObjs+" objects");	// debug

					status = APPROACHINGPLAG;
				}
				else	// all PlaGs are already eaten
					status = WALKAROUND;
				break;
		}
	}

	@Override
	public int handleCollision(PhysicalObject2D other, Double2D colPoint)
	{
		subTargetPosition = null;	// to instantly calculate a new target after COLLISION

//		if (belongedCarT != null)
//			System.out.println("HerR "+this.index+" collide with "+other.getClass()+" "+other.getIndex()+" (CarT "+belongedCarT.index+")");	// debug
//		else
//			System.out.println("HerR "+this.index+" collide with "+other.getClass()+" "+other.getIndex());	// debug

		// the CarT should instantly calculate a new target after COLLISION (HerR+CarT with Other)
		if (belongedCarT != null)
			belongedCarT.resetSubTargetPosition();

		switch (status)
		{
			case EAT:	// if he gets bounced out of the PlaG, go back in
				status = SEARCHING;
				break;
		}

		return 1; // regular collision
	}

	// function for "doing" states
	@Override
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
			case APPROACHINGPLAG:
				if (currentPlaG.currentMass > 0)
				{
					if (this.getPosition().subtract(currentPlaG.getPosition()).length() >= 40)
						this.goToCollisionFree(currentPlaG.getPosition());
					else
						status = EAT;
				}
				else
					status = SEARCHING;
				break;
			case EAT:
				if (currentPlaG.currentMass > 0)
					currentPlaG.currentMass -= 0.001;
				else
				{
//					System.out.println("HerR "+this.index+" eat  PlaG "+currentPlaG.index+" "+this.getPosition());	// debug

					if (tempWait < steps)
						tempWait = steps + 1 + (int)(Math.random() * 300);
					else if (tempWait == steps)
						status = SEARCHING;
				}
				break;
			case WALKAROUND:
				if (tempTarget == null)
				{
					double x = Math.max(Math.min(Math.random() * SimLifeArena.arenaWidth, SimLifeArena.arenaWidth*0.9), SimLifeArena.arenaWidth*0.1);
					double y = Math.max(Math.min(Math.random() * SimLifeArena.arenaHeight, SimLifeArena.arenaHeight*0.9), SimLifeArena.arenaHeight*0.1);
					tempTarget = new Double2D(x, y);
				}

				if (this.getPosition().subtract(tempTarget).length() <= 10)
				{
					tempTarget = null;
					status = SEARCHING;
				}
				else
					goToCollisionFree(tempTarget);
				break;
			case RETURNINGHOME:
				if (this.getPosition().subtract(home).length() <= 30)
				{
					if (this.getVelocity().length() > 0.01)
						stop();
					else
						status = SEARCHING;
				}
				else
					this.goToCollisionFree(home);
				break;
			case ISTARGET:
//				stop();  // freeze from fear ;)
				tempTarget = null;
				status = RUNAWAY;
				break;
			case RUNAWAY:
				if (tempTarget == null)
				{
					double dist = SimLifeArena.maxDist;
					double newDist;
					Double2D observerPos = null;

					// find the nearest observing CarT
					if (!observerCarT.isEmpty())
					{
						for (int i = 0; i < observerCarT.size(); i++)
						{
							newDist = position.subtract(observerCarT.get(i).getPosition()).length();
							if (newDist < dist)
							{
								dist = newDist;
								observerPos = observerCarT.get(i).getPosition();
							}
						}
					}
					else  // the HerR is observed but it can't find the observer
					{
						observerPos = new Double2D(SimLifeArena.arenaHeight/2, SimLifeArena.arenaWidth/2);  // virtual observer pos.
						System.out.println("HerR"+this.index+", can't get observer Object");  // debug
					}

					// run approximately in the opposite direction
					double alpha = Math.random()*2.8 - 1.4;	// +-1.4rad (= ~80�)
					tempTarget = SimLifeMath.limitToBorders(position.subtract(observerPos).setLength(thinkAhead).rotate(alpha).add(position), this.size);
				}

				if (this.getPosition().subtract(tempTarget).length() <= thinkAhead/10)
					tempTarget = null;
				else
					goToCollisionFree(tempTarget);
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
//				System.out.println("HerR"+this.index+", Pos "+this.position+",s subT1 "+subTargetPosition1+", subT2 "+subTargetPosition2);	// debug

				subTargetPosition1 = subTargetPosition2;
				subTargetPosition2 = SimLifeMath.getSubTargetPos(position, subTargetPosition1, allObjs);
			}

			// limit the subTargetPosition to the arena borders
			subTargetPosition = SimLifeMath.limitToBorders(subTargetPosition2, this.size);

//			System.out.println("HerR "+this.index+", State "+status+", Pos "+this.position+", EndT "+endTargetPosition+", newEndT "+subTargetPosition);	// debug
    	}

    	// now got "collision free" to the found sub target point,
    	this.goTo(subTargetPosition);
    }

    public void resetSubTargetPosition()
    {
    	subTargetPosition = null;
    }

	public void setBelongedCarT(int index)
	{
		belongedCarT = getCarTFromIndex(index);
	}

	public void unSetBelongedCarT()
	{
		belongedCarT = null;
	}

	public void setObserver(int index)
	{
		CarT carTHit = getCarTFromIndex(index);
		if (carTHit != null)
			observerCarT.add(carTHit);
	}

	public void unSetObserver(int index)
	{
		CarT carTHit = getCarTFromIndex(index);
		if (carTHit != null)
			observerCarT.remove(carTHit);
	}

	// if a CarT has this HerR choosed as his target, it get's marked as observed
	public void setObservation(boolean obs)
	{
		if (obs)
			observed++;
		else if (!obs && observed > 0)
			observed--;

		if (observed > 0)
		{
			// TODO: if in the future there are other funktions that can change the status the ISTARGET could get overwrite.
			//		 there for a (eq. "isTarget") variable should be set to reset the status if necessary.
			status = ISTARGET;
			this.setShape(new sim.physics2D.shape.Circle(size, Color.red), mass);
		}
		else
		{
			status = NORMAL;
			this.setShape(new sim.physics2D.shape.Circle(size, Color.blue), mass);
		}
	}

	private CarT getCarTFromIndex(int index)
	{
		CarT carTHit = null;
		for (int i = 0; i < allObjs.numObjs; i++)
		{
			if (allObjs.objs[i] instanceof CarT)
			{
				carTHit = (CarT)allObjs.objs[i];
				if (carTHit.getIndex() == index)
					break;
				else
					carTHit = null;
			}
		}
		return carTHit;
	}

	public Double2D getHome ()
	{
		return home;
	}
}