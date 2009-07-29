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

import bw.factories.clsSingletonMasonGetter;

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
	 * @param prRadius
	 * @param poPaint
	 */
//	public clsCircleImage(double prRadius, Paint poPaint )
//    {
//		super(prRadius, poPaint);
//		this.mrRadius = prRadius; 
//
//		
//        String nImagePath = clsGetARSPath.getArsPath() + "/src/resources/images/rock1.jpg";
//    	double nScale = 5;
//    	File oFile = new File( nImagePath ); 
//   	
//    	//BufferedImage moImage = null;
//	   	try
//	   	{
//	   		moImage = ImageIO.read( oFile );
//	   	} catch (IOException e)
//	   	{
//	   		e.printStackTrace();
//	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
//	   	}
//    }
	
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


	// TD - removed. we will not need to import images from urls ... fileaccess is sufficient. (removed to clarify this)
	///**
	// * creates a circular physical object with the given range and displays a image instead.
	// * Users need to know what radius the image has!
	// * 
	// * @param prRadius
	// * @param poDefautColor
	// * @param poImageURL
	// * @throws IOException
	// */
	//	public clsCircleImage(double prRadius, Paint poDefautColor, java.net.URL poImageURL  ) 
	//    {
	//		super(prRadius, poDefautColor);
	//	
	//	if(poImageURL == null)
	//		throw new NullPointerException("Image URL could not be loaded, file not found in ressource");
	//	
	//	this.mrRadius = prRadius; 
	//	
	//	try {
	//		moImage = ImageIO.read(poImageURL);
	//	} catch (IOException e) {
	//		e.printStackTrace();
	//		throw new NullPointerException("Image URL could not be loaded, file not found in file");
	//	}
	//	
	//}
        
    
    /** Display the circle + image */
	@Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double fWidthArc = info.draw.width * mrRadius * 2;
        final double fHeightArc = info.draw.height  * mrRadius * 2;
        
        int nImageWidth = moImage.getWidth();
        int nImageHeight = moImage.getHeight();
        
        double fScale = clsSingletonMasonGetter.getDisplay2D().getScale(); // 2 = zoomed in 1x, 0.5 = zoomed out 1x TODO performance issue?

        graphics.setPaint(paint);

        final int nxArc = (int)(info.draw.x - fWidthArc / 2.0);
        final int nyArc = (int)(info.draw.y - fHeightArc / 2.0);
        final int nwArc = (int)(fWidthArc);
        final int nhArc = (int)(fHeightArc);

        //displays the physical circle
        graphics.fillArc(nxArc, nyArc, nwArc, nhArc, 0, 360); //fillOval(x,y,w,h); //scale automatic by mason

        if (!mbShowSimple)
	        {
		        int nScaledWidth = (int) (nImageWidth * fScale);
		        int nScaledHeight = (int) (nImageHeight * fScale);
		   	
		        graphics.drawImage(moImage, nxArc , nyArc, nScaledWidth, nScaledHeight, null );
	        }
        }
   
    }