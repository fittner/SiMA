package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.portrayal.*;
import java.awt.*;

/** The Circle class is used by a circular physical object to store the attributes
 * of its appearance and size
 */
public class CircleBorder extends Circle
    {
    
	double radius; 
		
	public CircleBorder(double radius, Paint paint)
    {
		super(radius, paint);
		this.radius = radius; 
		//this.paint = paint;
    }
        
    
    /** Display the circle */
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double width = info.draw.width * radius * 2;
        final double height = info.draw.height  * radius * 2;

        graphics.setPaint(paint);
        // we are doing a simple draw, so we ignore the info.clip
        final int x = (int)(info.draw.x - width / 2.0);
        final int y = (int)(info.draw.y - height / 2.0);
        final int w = (int)(width);
        final int h = (int)(height);

        // draw centered on the origin
        graphics.drawArc(x,y,w, h, 0,360);
        }
   
    }