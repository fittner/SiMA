/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.effector;

import java.awt.*;
import sim.engine.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;
import bw.sim.*;

/* Mason test implementation of Effectors. 
* 
* @author langr
* 
*/
public class clsBotHands extends MobileObject2D implements Steppable
    {
    // public double radius;
    public clsBotHands(Double2D pos, Double2D vel, double radius, Paint paint)
        {
        this.setVelocity(vel);
        this.setPose(pos, new Angle(0));

        this.setShape(new sim.physics2D.shape.Circle(radius, paint), radius * radius * Math.PI);

        this.setCoefficientOfFriction(0);
        this.setCoefficientOfRestitution(1);
        }
 
    public void step(SimState state)
        {
        Double2D position = this.getPosition();
        clsBWMain simRobots = (clsBWMain)state;
        simRobots.fieldEnvironment.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
        }
    }