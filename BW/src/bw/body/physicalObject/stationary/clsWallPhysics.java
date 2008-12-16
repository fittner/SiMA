/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.stationary;

import java.awt.*;

import bw.world.physicalObject.mobile.clsImagePortrayal;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;

/**
 * Mason representative (physics+renderOnScreen) for a wall.  
 * 
 * FIXME: A wall has no body - why is it in the namespace
 * 
 * @author langr
 * 
 */
public class clsWallPhysics extends StationaryObject2D
    {
    public double radius;
    
    public clsWallPhysics(Double2D pos, int width, int height)
        {
        this.setCoefficientOfRestitution(1);
        this.setPose(pos, new Angle(0));
        this.setShape(new sim.physics2D.shape.Rectangle(width, height, Color.white));
        
       
        } 
    }