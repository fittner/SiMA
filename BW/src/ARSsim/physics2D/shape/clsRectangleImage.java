package ARSsim.physics2D.shape;

import sim.portrayal.*;
import java.awt.*;
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
 */
public class clsRectangleImage extends sim.physics2D.shape.Rectangle
    {
		double mrWidth; //wanted width and height
		double mrHeight;
		private BufferedImage moImage = null; //saves the image
		private Paint moDefaultColor = null; //color of the physical square underneath
		private boolean mbShowSimple = true; //can be used later to hide images for speed
		
	
	/**
	 * creates a rectangular physical object with the given range and displays a image above.
	 * The image is resized to the wanted rectangular so use a image with close dimensions.
	 * For alpha use a transparent gif
	 * 
	 * @param prRadius
	 * @param poPaint
	 * @param psImageFilePath
	 */
	public clsRectangleImage(double prWidth, double prHeight, Paint poDefaultColor , String psImageFilePath)
    {
		super(prWidth, prHeight, poDefaultColor);
		this.mrWidth = prWidth; 
		this.mrHeight = prHeight;
		this.moDefaultColor = poDefaultColor;
       
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
 
    /** Display the rect + image */
	@Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
//        int nImageOriginalWidth = moImage.getWidth();
//        int nImageOriginalHeight = moImage.getHeight();
		double fMasonZoom = 1;
		try
		{
			fMasonZoom = clsSingletonMasonGetter.getDisplay2D().getScale(); // 2 = zoomed in 1x, 0.5 = zoomed out 1x
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
        graphics.setPaint(paint);
        graphics.setColor((Color)moDefaultColor); //do we need this?

        //get the size of the rectangle, resized by mason automaticaly!
        final int nXRect = (int)(info.draw.x);
        final int nYRect = (int)(info.draw.y);
        final int nWRect = (int)(mrWidth);
        final int nHRect = (int)(mrHeight);

        //displays the physical rect
        graphics.fillRect(nXRect, nYRect, nWRect, nHRect);

        if (!mbShowSimple)
	        {
        		//get the new size for the image, need to zoom it ourself
		        int nImageScaledWidth = (int)(nWRect * fMasonZoom);
		        int nImageScaledHeight = (int)(nHRect * fMasonZoom);
		        //draw above the physical object and resize image to physical object
		        graphics.drawImage(moImage, nXRect , nYRect, nImageScaledWidth, nImageScaledHeight, null );
	        }
        }
	
    }