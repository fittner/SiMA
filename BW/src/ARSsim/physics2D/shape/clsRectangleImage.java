package ARSsim.physics2D.shape;

import sim.portrayal.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Extension of the Physics Engine circle, showing a image instead
 * 
 * @author muchitsch
 *  
 */
public class clsRectangleImage extends sim.physics2D.shape.Rectangle
    {
	
    
	double mrWidth;
	double mrHeight;
	
	BufferedImage moImage = null;
	public boolean mbShowSimple = false;
		
	
	/**
	 * creates a rectangular physical object with the given range and displays a image instead.
	 * Users need to know what radius the image has!
	 * 
	 * @param prRadius
	 * @param poPaint
	 * @param psImageFilePath
	 */
	public clsRectangleImage(double prWidth, double prHeight, Paint poDefautColor , String psImageFilePath)
    {
		super(prWidth, prHeight, poDefautColor);
		this.mrWidth = prWidth; 
		this.mrWidth = prHeight;
       
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
	
	
	
	/**
	 * creates a circular physical object with the given range and displays a image instead.
	 * Users need to know what radius the image has!
	 * 
	 * @param prRadius
	 * @param poDefautColor
	 * @param poImageURL
	 * @throws IOException
	 */
	public clsRectangleImage(double prWidth, double prHeight, Paint poDefautColor, java.net.URL poImageURL  ) 
    {
		super(prWidth, prHeight, poDefautColor);
		this.mrWidth = prWidth; 
		this.mrWidth = prHeight;
		
		if(poImageURL == null)
			throw new NullPointerException("Image URL could not be loaded, file not found in ressource");
		
	
		try {
			moImage = ImageIO.read(poImageURL);
		} catch (IOException e) {
			e.printStackTrace();
			throw new NullPointerException("Image URL could not be loaded, file not found in file");
		}
		
    }
	 
    /** Display the circle + image */
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double width = info.draw.width * mrWidth;
        final double height = info.draw.height * mrWidth;
        
        int imageWidth = moImage.getWidth();
        int imageHeight = moImage.getHeight();
        

        graphics.setPaint(paint);

//        final int x = (int)info.draw.x ;
//        final int y = (int)info.draw.y ;
        final int x = (int)(info.draw.x) ;
        final int y = (int)(info.draw.y);
        final int w = (int)( info.draw.width);
        final int h = (int)( info.draw.width);

        //displays the physical circle
        graphics.fillRect(x, y, w, h);

//        if (!mbShowSimple)
//	        {
//			   	BufferedImageOp op = null;
//		        graphics.drawImage(moImage, op, x , y );
//	        }
        }
   
    }