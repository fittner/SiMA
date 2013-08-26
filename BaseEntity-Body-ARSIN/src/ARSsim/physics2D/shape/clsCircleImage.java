package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.physics2D.util.Angle;
import sim.portrayal.DrawInfo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import du.enums.eFacialExpression;
import du.enums.eSpeechExpression;
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
	//BufferedImage moImageOverlay = null;
	private boolean mbShowSimple = false; //can be used for testing, no image is rendered
	double fMinImageSize = 15;  //minimal Image size to be shown
	eImages moOverlayImage = eImages.NONE;
	eStrings moOverlayString = eStrings.Nourish;
	eFacialExpression moFacialExpressionOverlayImage = eFacialExpression.NONE;
	eSpeechExpression moSpeechExpressionOverlayImage = eSpeechExpression.NONE;
	Paint moPaint = null;
	AffineTransform transform = new AffineTransform(); 
	boolean mbShowOrientation = false; //show orientation marker if you want
	


	/**
	 * creates a circular physical object with the given range and displays a image above.
	 * image is resized to fit the rectangle around the circle (outer bounds)
	 * remember: positioning in mason is on the center point of the polygon
	 * 
	 * @param prRadius
	 * @param poPaint
	 * @param psImageFilePath
	 */
	public clsCircleImage(double prRadius, Paint poPaint , String psImageFilePath, boolean pbShowOrientation)
    {
		super(prRadius, poPaint);
		this.mrRadius = prRadius; 
		this.moPaint = poPaint;
		this.mbShowOrientation = pbShowOrientation;	
      
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
        
        if(mbShowOrientation)
        	        {
        	        	
        		      //adding orientation
        		        Angle orient = getOrientation();
        		        orient=orient.add(new Angle(-1.571f)); //mason shapes are shifted 90deg, so correct it
        		        double theta = orient.radians;
        		        int scale = 9;
        		        int offset = 0;
        		        double length = (scale * (info.draw.width < info.draw.height ? 
        		                info.draw.width : info.draw.height)) + offset;  // fit in smallest dimension
        		        transform.setToTranslation(info.draw.x, info.draw.y);
        		        transform.rotate(theta);
        	                                           
        		        Shape path = new Line2D.Double(0,0,0,length);
        		        
        			 	Stroke stroke = new BasicStroke(16f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL);
        			 	Color oldColor= graphics.getColor();
        			 	if(moOverlayImage == eImages.Overlay_Action_MoveForward)
        			 	{
        			 		graphics.setColor(Color.DARK_GRAY);
        			 	}
        		    	graphics.setStroke(stroke);
        		    	graphics.draw( transform.createTransformedShape(path) );
        		    	//set old values
        		    	stroke = new BasicStroke(); //set Stroke back to normal
        		    	graphics.setStroke(stroke);
        		    	graphics.setColor(oldColor);
        	        }

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
							graphics.drawImage(oImageOverlay, nxArc-55, nyArc-55, 60, 60, null ); 	
							//graphics.drawImage(oImageOverlay, nxArc+30, nyArc+55, 60, 60, null ); 
			        	}
			        }
			        
			        
			      //display a Speech expressionoverlay Icon
			        if(clsSingletonProperties.showSpeechExpressionOverlay()) {
			        	if(moSpeechExpressionOverlayImage != null && moSpeechExpressionOverlayImage != eSpeechExpression.NONE){
			        		
				        	eImages oSpeechExpressionImage = eImages.valueOf(moSpeechExpressionOverlayImage.getEImagesString());
				        	
				    	   	BufferedImage oImageOverlay = null;
				    
				    	   	try {
				    	   		oImageOverlay = clsSingletonImageFactory.getImage(oSpeechExpressionImage);
				    	   	} catch (IOException e) {
				    	   		e.printStackTrace();
				    	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
				    	   	}
				        	
							oImageOverlay.getGraphics();
							graphics.drawImage(oImageOverlay, nxArc-55, nyArc-55, 60, 60, null ); 
			        	}
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

	/**
	 * @author hinterleitner
	 * 04.05.2011, 10:11:50
	 * 
	 * @param moOverlay the moOverlay to set
	 */
	public void setSpeechExpressionOverlayImage(eSpeechExpression moOverlay) {
		this.moSpeechExpressionOverlayImage = moOverlay;
	}


	
	public BufferedImage getImage(){
		   return moImage;
	   }


	public BufferedImage setCarriedItem(BufferedImage poOverlay) {
		   return moImage;
			  
	}
	
	/**
	 * @since 28.06.2013 16:00:48
	 * 
	 * @return the mbShowOrientation
	 */
	public boolean isShowOrientation() {
		return mbShowOrientation;
	}



	/**
	 * @since 28.06.2013 16:00:48
	 * 
	 * @param mbShowOrientation the mbShowOrientation to set
	 */
	public void setShowOrientation(boolean mbShowOrientation) {
		this.mbShowOrientation = mbShowOrientation;
	}

   
    }