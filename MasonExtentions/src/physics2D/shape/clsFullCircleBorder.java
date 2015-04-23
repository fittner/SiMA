package physics2D.shape;

import interfaces.itfEntity;

import java.awt.Graphics2D;
import java.awt.Paint;

import physics2D.physicalObject.sensors.clsEntitySensorEngine;
import sim.portrayal.DrawInfo2D;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 04.03.2013, 10:53:47
 * 
 */
public class clsFullCircleBorder extends clsCircleBorder
    {

	public clsFullCircleBorder(double radius, double angle, Paint paint) {
		super(radius, angle, paint);
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
        
        itfEntity oTempEntity = ((clsEntitySensorEngine)object).getEntity();
        double currentAngle = oTempEntity.getPose().getAngle().radians;
 
        // recalculate the Bubble-Angle World to the Java Angle world by: JavaAngle = 360-BubbleAngle
        int start_arc = (int) Math.toDegrees(currentAngle);
        
        // draw centered on the origin
        graphics.drawArc(x,y,w, h, start_arc, 360); 
    }
	
}