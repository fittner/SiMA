/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObjects.bodyparts;

import java.awt.Paint;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import bw.factories.clsSingletonMasonGetter;

/* Mason test implementation of Effectors. 
* 
* @author langr
* 
*/
public class clsBotHands extends MobileObject2D implements Steppable
    {
    /**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 26.02.2009, 12:36:11
	 */
	private static final long serialVersionUID = 4933590799776946075L;

	// public double radius;
    public clsBotHands(sim.physics2D.util.Double2D pos, sim.physics2D.util.Double2D vel, double radius, Paint paint)
        {
        this.setVelocity(vel);
        this.setPose(pos, new Angle(0));

        this.setShape(new sim.physics2D.shape.Circle(radius, paint), 0.1);

        this.setCoefficientOfFriction(0);
        this.setCoefficientOfRestitution(1);
        }
 
    public void step(SimState state)
        {
    	sim.physics2D.util.Double2D position = this.getPosition();
        clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
        }
    }