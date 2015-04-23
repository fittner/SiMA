package physics2D.shape;

import java.awt.Graphics2D;
import java.awt.Paint;

import sim.physics2D.shape.Circle;
import sim.portrayal.DrawInfo2D;

//import bw.entities.base.clsEntity;
//import bw.factories.clsSingletonDisplay2D;
//import bw.physicalObjects.sensors.clsEntitySensorEngine;;

/** The Circle class is used by a circular physical object to store the attributes
 * of its appearance and size
 */
public class clsCircleBorder extends Circle
    {
    
	double radius; 
	int angle; 
		
	public clsCircleBorder(double radius, double angle, Paint paint)
    {
		super(radius, paint);
		this.radius = radius; 
	    this.angle = (int)Math.toDegrees(angle); 
    }
        
    
    /** Display the circle */
	@Override
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
        
        //clsEntity oTempEntity = ((clsEntitySensorEngine)object).getEntity();
        double currentAngle = getOrientation().radians; //use the shapes angle not the superclass!
 
        // recalculate the Bubble-Angle World to the Java Angle world by: JavaAngle = 360-BubbleAngle
        int start_arc = (int) Math.toDegrees(currentAngle);
        start_arc = 360-start_arc-90; // Bubble2Java minus 90deg (to left from center of line of sight)
        
        // draw centered on the origin
        graphics.drawArc(x,y,w, h, start_arc, 180); //the last number is the angle of the arc, aka groesze des Tortenstuecks
        
        
        //added by SK: draw line of sights
        //draw line separating front and back (end point up)
        graphics.drawLine((int) (info.draw.x + radius * info.draw.width * Math.sin(currentAngle)),
        	(int) (info.draw.y - radius * info.draw.height  * Math.cos(currentAngle)),
        	(int) (info.draw.x - radius * info.draw.width * Math.sin(currentAngle)),
        	(int) (info.draw.y + radius * info.draw.height  * Math.cos(currentAngle)));
        //draw middle lines
        //left side of arsin
        graphics.drawLine((int) info.draw.x, 
            	(int) info.draw.y, 
            	(int) (info.draw.x + radius * info.draw.width * Math.sin(currentAngle + 1 * Math.PI/4)), 
            	(int) (info.draw.y - radius * info.draw.height  * Math.cos(currentAngle + 1 * Math.PI/4)));
        //right side of arsin
        graphics.drawLine((int) info.draw.x, 
            	(int) info.draw.y, 
            	(int) (info.draw.x + radius * info.draw.width * Math.sin(currentAngle + 3 * Math.PI/4)), 
            	(int) (info.draw.y - radius * info.draw.height  * Math.cos(currentAngle + 3 * Math.PI/4)));
        //draw center lines
        //left side of arsin
        graphics.drawLine((int) info.draw.x, 
        	(int) info.draw.y, 
        	(int) (info.draw.x + radius * info.draw.width * Math.sin(currentAngle + 17 * Math.PI/36)), 
        	(int) (info.draw.y - radius * info.draw.height  * Math.cos(currentAngle + 17 * Math.PI/36)));
        //right side of arsin
        graphics.drawLine((int) info.draw.x, 
            (int) info.draw.y, 
            (int) (info.draw.x + radius * info.draw.width * Math.sin(currentAngle + 19 * Math.PI/36)), 
            (int) (info.draw.y - radius * info.draw.height  * Math.cos(currentAngle + 19 * Math.PI/36)));
    }
   
	public int getStartAngle(){
		return 360-angle/2; 
	}

	
	
}