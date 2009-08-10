package ARSsim.physics2D.shape;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import bw.entities.tools.eImagePositioning;

import sim.physics2D.shape.Polygon;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import sim.util.matrix.DenseMatrix;



/**
 * Extension of the Physics Engine circle, showing a image instead
 * remember: positioning in mason is on the center point of the polygon
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
		private boolean mbShowSimple = false; //can be used for testing, no image is rendered
		private eImagePositioning mImagePositioning = eImagePositioning.STRETCHING;
		
	
	/**
	 * creates a rectangular physical object with the given range and displays a image above.
	 * The image is resized to the wanted rectangular so use a image with close dimensions.
	 * For alpha use a transparent gif
	 * remember: positioning in mason is on the center point of the polygon
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
	   		throw new NullPointerException("Image URL could not be loaded, file not found in path:"+psImageFilePath);
	   	}
    }


	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * Aug 9, 2009, 5:26:29 PM
	 * 
	 * @see sim.physics2D.shape.Polygon#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
	 */
	@Override
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) 
	{
		graphics.setPaint(graphics.getPaint());

        // set the scaling DenseMatrix
        scale.vals[0][0] = info.draw.width;
        scale.vals[1][1] = info.draw.height;
                
        DenseMatrix scaledMat = scale.times(this.vertices);
        DenseMatrix rottedMat = Polygon.rotationTranslationMatrix2D(getOrientation().radians, new Double2D(info.draw.x, info.draw.y)).times(scaledMat);
        graphics.fillPolygon(Polygon.getRow(0, rottedMat),Polygon.getRow(1, rottedMat), this.vertices.n);
        
        if (!mbShowSimple)
        {
        	switch(mImagePositioning)
    		{
    			case CENTER:
    			{
    				drawImageCentered(graphics, info);
     				break;
    			}
    		
    			case STRETCHING:
    			{
    				drawImageStreched(graphics, info);
    				break;
    			}
    		
    			default:throw new java.lang.NullPointerException("Image positioning type not yet implemented: "+mImagePositioning.toString());
    		}
        }
	}
		
	private void drawImageCentered(Graphics2D graphics, DrawInfo2D info)
	{
		int oScale = 1; //not really used, maybe later, leave at 1 for now
		final int iw = moImage.getWidth(null);
        final int ih = moImage.getHeight(null);
        double width;
        double height;
        
        /* if the image is taller than it is wide, then the width of the image will be info.draw.width * scale and the height
   		   will stay in proportion; else the height of the image will be info.draw.height * scale and thewidth will stay in proportion.
         */
        
        if (ih > iw)
            {
            width = info.draw.width*oScale;
            height = (ih*width)/iw;  // ih/iw = height / width
            }
        else
            {
            height = info.draw.height * oScale;
            width = (iw * height)/ih;  // iw/ih = width/height
            }

        final int x = (int)(info.draw.x - width / 2.0);
        final int y = (int)(info.draw.y - height / 2.0);
        final int w = (int)(width);
        final int h = (int)(height);

        // draw centered on the origin
        graphics.drawImage(moImage,x,y,w,h,null);
	}
	
	private void drawImageStreched(Graphics2D graphics, DrawInfo2D info)
	{
		int oScale = 6; //FIXME Clemens, richtige image größe berechnen lassen
		final int iw = (int)mrWidth*2; 
        final int ih = (int)mrHeight*2; 
        double width;
        double height;
        
        
        if (ih > iw)
            {
            width = info.draw.width*oScale;
            height = (ih*width)/iw;  // ih/iw = height / width
            }
        else
            {
            height = info.draw.height * oScale;
            width = (iw * height)/ih;  // iw/ih = width/height
            }
            
        final int x = (int)(info.draw.x - width / 2.0);
        final int y = (int)(info.draw.y - height / 2.0);
        final int w = (int)(width);
        final int h = (int)(height);

        // draw centered on the origin
        graphics.drawImage(moImage,x,y,w,h,null);
	}
	
	
}