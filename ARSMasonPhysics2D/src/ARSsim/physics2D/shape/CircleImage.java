package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.portrayal.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author muchitsch
 *  IMPORTANT: DO NOT USE THIS CLASS! major performance issue! TODO Clemens: rewrite!
 */
public class CircleImage extends Circle
    {
    
	double radius; 
		
	public CircleImage(double radius, Paint paint)
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
        //graphics.drawArc(x,y,w, h, 0,360);
        
        String nImagePath = "S:/ARS/PA/BWv1/BW/src/resources/images/ball.jpg";
    	 double nScale = 5;
    	File oFile = new File( nImagePath ); 
   	
	   	BufferedImage oImage = null;
	   	try
	   	{
	   		oImage = ImageIO.read( oFile );
	   	} catch (IOException e)
	   	{
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
	   	}
	   	BufferedImageOp op = null;
        graphics.drawImage(oImage, op, x, y);
        }
   
    }