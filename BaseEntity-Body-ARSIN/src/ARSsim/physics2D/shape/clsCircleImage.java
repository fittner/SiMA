package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.portrayal.DrawInfo2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.WritableRaster;
import java.awt.geom.AffineTransform;
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
public class clsCircleImage extends Circle implements itfImageShape
    {
	    
	double mrRadius; 
	BufferedImage moImage = null;
	BufferedImage moOriginal = null;
	//BufferedImage moImageOverlay = null;
	
	protected boolean mbShowSimple = false; //can be used for testing, no image is rendered
	double fMinImageSize = 15;  //minimal Image size to be shown
	Paint moPaint = null;
	AffineTransform transform = new AffineTransform(); 
	


	/**
	 * creates a circular physical object with the given range and displays a image above.
	 * image is resized to fit the rectangle around the circle (outer bounds)
	 * remember: positioning in mason is on the center point of the polygon
	 * 
	 * @param prRadius
	 * @param poPaint
	 * @param psImageFilePath
	 */
	public clsCircleImage(double prRadius, Paint poPaint , String psImageFilePath)
    {
		super(prRadius, poPaint);
		this.mrRadius = prRadius; 
		this.moPaint = poPaint;

      
    	File oFile = new File( psImageFilePath ); 

	   	try
	   	{
	   		moImage = ImageIO.read( oFile );
	   		moOriginal = moImage;
	   	} catch (IOException e)
	   	{
	   		e.printStackTrace();
	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
	   	}
	   	
    }

   
    
    /* (non-Javadoc)
	 *
	 * @since 14.09.2011 11:52:46
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String retval = "Circle color=" + moPaint.toString() + ", radius=" + mrRadius;
		return retval;
	}



	/** Display the circle + image */
	@Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double fWidthArc =  info.draw.width * mrRadius * 2;
        final double fHeightArc =  info.draw.height  * mrRadius * 2;
       
        graphics.setPaint(paint);

        final int nxArc = (int)(info.draw.x - fWidthArc / 2.0 );
        final int nyArc = (int)(info.draw.y - fHeightArc / 2.0 );
        final int nwArc = (int)(fWidthArc);
        final int nhArc = (int)(fHeightArc);
        

        //displays the physical circle
        graphics.fillOval(nxArc, nyArc, nwArc, nhArc); //fillOval(x,y,w,h); //scale automatic by mason
        

        if (!mbShowSimple)
	        {
	        	if(!(fWidthArc < fMinImageSize || fHeightArc < fMinImageSize)) //dont show images if scale to small -> perfomance
	        	{
			        int nScaledWidth = (int) (fWidthArc  ); //here the with of the arc should be used
			        int nScaledHeight = (int) (fHeightArc );
	
			        //AffineTransform affe = AffineTransform.getRotateInstance(getOrientation().radians);
			        moImage.getGraphics();
			        //imgGra.rotate(getOrientation().radians);
			        
			        graphics.drawImage(moImage, nxArc , nyArc, nScaledWidth, nScaledHeight, null );
			        

	        	}
	        }
        }
	

	
	public BufferedImage getImage(){
		   return moImage;
	   }





	/* (non-Javadoc)
	 *
	 * @since 25.09.2013 08:13:58
	 * 
	 * @see ARSsim.physics2D.shape.itfImageShape#setDisplaySize(double)
	 */
	@Override
	public void setDisplaySize(double poSize) {
		
		if(poSize >1.0)poSize =1.0;
		if(poSize<0.0)poSize=0.0;
		WritableRaster raster = moOriginal.copyData(null);
		Graphics image= moImage.getGraphics();
		
		//double sizeInverted = 1.0 - poSize;
		int height = moImage.getHeight();
		int width = moImage.getWidth();
	//	Color oColor = new Color(0,0,0,0);

		//image.setColor(oColor);

		int rectHeigh = (int) ((1.0-poSize)*height);
		//image.fillRect(0, 0, width, rectHeigh);
		//int[] pixel1 = new int [4];
		//int[] pixel = raster.getPixel(100, 100, pixel1);
		
		int[] newPixel = new int[4];
		for(int i=0;i<newPixel.length;i++){
			newPixel[i] =0;
		}

		for(int i=0; i<width;i++){
			for(int k=0;k<rectHeigh;k++){
				raster.setPixel(i, k, newPixel);
			}
		}
		
		moImage = new BufferedImage(moOriginal.getColorModel(),raster,moOriginal.getColorModel().isAlphaPremultiplied(),null);
	}

   
    }