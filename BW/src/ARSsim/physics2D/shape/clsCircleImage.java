package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.portrayal.DrawInfo2D;
//import sim.portrayal.*;
//import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Extension of the Physics Engine circle, showing a image instead
 * 
 * @author muchitsch
 *  
 *///
public class clsCircleImage extends Circle
    {
	
    
	double mrRadius; 
	BufferedImage moImage = null;
	private boolean mbShowSimple = false; //can be used later to hide images for speed
		

	
	/**
	 * creates a circular physical object with the given range and displays a image instead.
	 * Users need to know what radius the image has!
	 * 
	 * @param prRadius
	 * @param poPaint
	 * @param psImageFilePath
	 */
	public clsCircleImage(double prRadius, Paint poPaint , String psImageFilePath)
    {
		super(prRadius, poPaint);
		this.mrRadius = prRadius; 
       
    	File oFile = new File( psImageFilePath ); 

	   	try
	   	{
	   		moImage = ImageIO.read( oFile );
	   	} catch (IOException e)
	   	{
	   		e.printStackTrace();
	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
	   	}
    }

   
    
    /** Display the circle + image */
	@Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double fWidthArc =  info.draw.width * mrRadius * 2;
        final double fHeightArc =  info.draw.height  * mrRadius * 2;
        
        //int nImageWidth = moImage.getWidth();
        //int nImageHeight = moImage.getHeight();

        //TODO - (muchitsch): delete this line, because there is no scaling necessary!
        //double fScale = clsSingletonMasonGetter.getDisplay2D().getScale(); // 2 = zoomed in 1x, 0.5 = zoomed out 1x TODO performance issue?
        
        graphics.setPaint(paint);

        final int nxArc = (int)(info.draw.x - fWidthArc / 2.0 );
        final int nyArc = (int)(info.draw.y - fHeightArc / 2.0 );
        final int nwArc = (int)(fWidthArc);
        final int nhArc = (int)(fHeightArc);

        //displays the physical circle
        graphics.fillOval(nxArc, nyArc, nwArc, nhArc); //fillOval(x,y,w,h); //scale automatic by mason

        if (!mbShowSimple)
	        {
		        int nScaledWidth = (int) (fWidthArc); // * fScale /2 ... here the with of the arc should be used
		        int nScaledHeight = (int) (fHeightArc);  // * fScale /2 ... no scaling necessary (roland)
		   	
		        graphics.drawImage(moImage, nxArc , nyArc, nScaledWidth, nScaledHeight, null );
	        }
        }
   
    }