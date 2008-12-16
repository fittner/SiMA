/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.stationary;

import java.awt.Color;

import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

/**
 * Mason representative (physics+renderOnScreen) for a stone.  
 * 
 * @author langr
 * 
 */
public class clsStonePhysics extends sim.physics2D.physicalObject.StationaryObject2D {

	//FIXME löschen nur für magictuesday
	public clsStonePhysics(Double2D pos, int width, int height)
    {
    this.setCoefficientOfRestitution(1);
    this.setPose(pos, new Angle(0));
    this.setShape(new sim.physics2D.shape.Rectangle(width, height, Color.cyan));
    } 
}
