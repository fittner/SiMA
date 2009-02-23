package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.portrayal.*;
import sun.java2d.pipe.NullPipe;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bw.exceptions.exException;

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
	public boolean mbShowSimple = false;
		
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
//        String nImagePath = "S:/ARS/PA/BWv1/BW/src/resources/images/rock1.jpg";
//    	double nScale = 5;
//    	File oFile = new File( nImagePath ); 
//   	
//    	//BufferedImage moImage = null;
//	   	try
//	   	{
//	   		moImage = ImageIO.read( oFile );
//	   	} catch (IOException e)
//	   	{
//	   		// TODO Auto-generated catch block
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
	
	
	
	/**
	 * creates a circular physical object with the given range and displays a image instead.
	 * Users need to know what radius the image has!
	 * 
	 * @param prRadius
	 * @param poDefautColor
	 * @param poImageURL
	 * @throws IOException
	 */
	public clsCircleImage(double prRadius, Paint poDefautColor, java.net.URL poImageURL  ) 
    {
		super(prRadius, poDefautColor);
		
		if(poImageURL == null)
			throw new NullPointerException("Image URL could not be loaded, file not found in ressource");
		
		this.mrRadius = prRadius; 
	
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
        final double width = info.draw.width * mrRadius * 2;
        final double height = info.draw.height  * mrRadius * 2;
        
        int imageWidth = moImage.getWidth();
        int imageHeight = moImage.getHeight();
        

        graphics.setPaint(paint);

        final int x = (int)(info.draw.x - width / 2.0);
        final int y = (int)(info.draw.y - height / 2.0);
        final int w = (int)(width);
        final int h = (int)(height);

        //displays the physical circle
        graphics.fillArc(x, y, w, h, 0, 360); //fillOval(x,y,w,h);

        if (!mbShowSimple)
	        {
			   	BufferedImageOp op = null;
		        graphics.drawImage(moImage, op, x , y );
	        }
        }
   
    }