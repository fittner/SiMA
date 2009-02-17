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
 * Extension of the Physics Engine circle, showing a image instead
 * 
 * @author muchitsch
 *  
 */
public class clsCircleImage extends Circle
    {
    
	double mrRadius; 
	BufferedImage moImage = null;
		
	public clsCircleImage(double prRadius, Paint poPaint )
    {
		super(prRadius, poPaint);
		this.mrRadius = prRadius; 

		
        String nImagePath = "S:/ARS/PA/BWv1/BW/src/resources/images/rock1.jpg";
    	double nScale = 5;
    	File oFile = new File( nImagePath ); 
   	
    	//BufferedImage moImage = null;
	   	try
	   	{
	   		moImage = ImageIO.read( oFile );
	   	} catch (IOException e)
	   	{
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
	   	}
    }
        
    
    /** Display the circle */
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double width = info.draw.width * mrRadius * 2;
        final double height = info.draw.height  * mrRadius * 2;

        graphics.setPaint(paint);

        final int x = (int)(info.draw.x - width / 2.0);
        final int y = (int)(info.draw.y - height / 2.0);
        final int w = (int)(width);
        final int h = (int)(height);

        graphics.fillOval(x,y,w,h);

	   	BufferedImageOp op = null;
        graphics.drawImage(moImage, op, x, y);
        }
   
    }