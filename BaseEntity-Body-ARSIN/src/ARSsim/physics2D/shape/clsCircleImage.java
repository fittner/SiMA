package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.portrayal.DrawInfo2D;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import du.enums.eFacialExpression;
import bw.factories.clsSingletonImageFactory;
import bw.factories.clsSingletonProperties;
import bw.factories.eImages;
import bw.factories.eStrings;


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
	eImages moImageName =eImages.NONE;
	//BufferedImage moImageOverlay = null;
	private boolean mbShowSimple = false; //can be used for testing, no image is rendered
	double fMinImageSize = 15;  //minimal Image size to be shown
	eImages moOverlayImage = eImages.NONE;
	eStrings moOverlayString = eStrings.Nourish;
	eFacialExpression moFacialExpressionOverlayImage = eFacialExpression.NONE;
	BufferedImage moCarriedItem = null;
	Paint moPaint = null;


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
	   		moImageName = eImages.Cake;
	   	} catch (IOException e)
	   	{
	   		e.printStackTrace();
	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
	   	}
	   	
    }

   public BufferedImage getImage(){
	   return moImage;
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
			        
			        //display a overlay Icon
			        if(moOverlayImage != eImages.NONE) {
			        	
			    	   	BufferedImage oImageOverlay = null;
			    
			    	   	try {
			    	   		oImageOverlay = clsSingletonImageFactory.getImage(moOverlayImage);
			    	   	} catch (IOException e) {
			    	   		e.printStackTrace();
			    	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
			    	   	}
			        	
						oImageOverlay.getGraphics();
						graphics.drawImage(oImageOverlay, nxArc+30, nyArc-55, 60, 60, null ); 
			        }
			        
			        
			      //display a facial expressionoverlay Icon
			        if(clsSingletonProperties.showFacialExpressionOverlay()) {
			        	if(moFacialExpressionOverlayImage != null && moFacialExpressionOverlayImage != eFacialExpression.NONE){
			        		
				        	eImages oFaciaExpressionImage = eImages.valueOf(moFacialExpressionOverlayImage.getEImagesString());
				        	
				    	   	BufferedImage oImageOverlay = null;
				    
				    	   	try {
				    	   		oImageOverlay = clsSingletonImageFactory.getImage(oFaciaExpressionImage);
				    	   	} catch (IOException e) {
				    	   		e.printStackTrace();
				    	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
				    	   	}
				        	
							oImageOverlay.getGraphics();
							graphics.drawImage(oImageOverlay, nxArc+30, nyArc+55, 60, 60, null ); 
			        	}
			        }
			        
				      //display carried Iteam
			        if(clsSingletonProperties.showCarriedItem()) {
			        	if(moCarriedItem != null){

			        		moCarriedItem.getGraphics();
							graphics.drawImage(moCarriedItem, nxArc-30, nyArc+55,30, 30, null ); 
			        	}
			        }
			      		        
			        
			      //display a overlay String - new method
			        if(moOverlayString != eStrings.Nourish) {
			        	
			    	   	BufferedImage oStringOverlay = null;
			    
			    	   	try {
			    	   		oStringOverlay = clsSingletonImageFactory.getString(moOverlayString);
			    	   	} catch (IOException e) {
			    	   		e.printStackTrace();
			    	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
			    	   	}
			        	
			    	   	oStringOverlay.getGraphics();
						graphics.drawImage(oStringOverlay, nxArc+30, nyArc-55, 60, 60, null ); 
			        }
	
	
	        	}
	        }
        }
	
	/**
	 * @author muchitsch
	 * 04.05.2011, 10:11:50
	 * 
	 * @return the moOverlay
	 */
	public eImages getOverlayImage() {
		return moOverlayImage;
	}



	/**
	 * @author muchitsch
	 * 04.05.2011, 10:11:50
	 * 
	 * @param moOverlay the moOverlay to set
	 */
	public void setOverlayImage(eImages moOverlay) {
		this.moOverlayImage = moOverlay;
	}
	
	public void setCarriedItem(BufferedImage moOverlay) {
		this.moCarriedItem = moOverlay;
	}
	
	public eFacialExpression getFacialExpressionOverlayImage() {
		return moFacialExpressionOverlayImage;
	}



	/**
	 * @author muchitsch
	 * 04.05.2011, 10:11:50
	 * 
	 * @param moOverlay the moOverlay to set
	 */
	public void setFacialExpressionOverlayImage(eFacialExpression moOverlay) {
		this.moFacialExpressionOverlayImage = moOverlay;
	}
   
    }