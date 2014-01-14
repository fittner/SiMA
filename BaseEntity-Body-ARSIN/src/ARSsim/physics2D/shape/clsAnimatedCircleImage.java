/**
 * CHANGELOG
 *
 * 25.09.2013 herret - File created
 *
 */
package ARSsim.physics2D.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import sim.physics2D.util.Angle;
import sim.portrayal.DrawInfo2D;

import bw.factories.clsSingletonImageFactory;
import bw.factories.clsSingletonProperties;
import bw.factories.eImages;
import bw.factories.eStrings;
import du.enums.eFacialExpression;
import du.enums.eSpeechExpression;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 25.09.2013, 13:28:51
 * 
 */
public class clsAnimatedCircleImage extends clsCircleImage {

	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since 25.09.2013 13:29:29
	 *
	 * @param prRadius
	 * @param poPaint
	 * @param psImageFilePath
	 * @param pbShowOrientation
	 */
	
	BufferedImage moCarriedItem =null;
	eImages moOverlayImage = eImages.NONE;
	eStrings moOverlayString = eStrings.Nourish;
	eFacialExpression moFacialExpressionOverlayImage = eFacialExpression.NONE;
	eSpeechExpression moSpeechExpressionOverlayImage = eSpeechExpression.NONE;
	eSpeechExpression moThoughtExpressionOverlayImage = eSpeechExpression.NONE;
	
	double moLifeValue =1.0;
	boolean mbShowOrientation = false; //show orientation marker if you want

	
	
	
	
	public clsAnimatedCircleImage(double prRadius, Paint poPaint,
			String psImageFilePath, boolean pbShowOrientation) {
		
		
		super(prRadius, poPaint, psImageFilePath);
		this.mbShowOrientation = pbShowOrientation;	
		
		
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
						graphics.drawImage(oImageOverlay, nxArc+30, nyArc+55, 60, 60, null ); 
			        }
			        // show life indicator
			        if(clsSingletonProperties.showLifeIndicator()==true) {
			        	

			        	double negLifeValue = 1-moLifeValue;
			        	
			        	int width = 5;
			        	int height = 60;
			        	WritableRaster raster = (new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB)).copyData(null);
			        	
			        	int fillSize = (int) ((int) height * moLifeValue);
			        	int [] paint = new int[4];
			        	paint[0]=0;
			        	paint[1]=0;
			        	paint[2]=0;
			        	paint[3]=255;
			        	
			        	int [] no_paint = new int[4];
			        	no_paint[0]=200;
			        	no_paint[1]=200;
			        	no_paint[2]=200;
			        	no_paint[3]=255;
			        	
			        	for(int y=1; y<=height;y++){//y
			        		for(int x=0;x<width;x++){//x
			        			if(y > (fillSize)) raster.setPixel(x, height-y, no_paint);
			        			else raster.setPixel(x, height-y, paint);
			        		}
			        	}

			        	BufferedImage lifeIndicator = new BufferedImage(ColorModel.getRGBdefault(),raster,true,null);
			        	lifeIndicator.getGraphics();
			        	graphics.drawImage(lifeIndicator, nxArc-10, nyArc, width, height, null );
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
							graphics.drawImage(oImageOverlay, nxArc-30, nyArc+55, 60, 60, null ); 	
							//graphics.drawImage(oImageOverlay, nxArc+30, nyArc+55, 60, 60, null ); 
			        	}
			        }
			        
			        
			      //display a Thought expressionoverlay Icon
			        if(clsSingletonProperties.showSpeechQuestionOverlay()) {
			        	if(moThoughtExpressionOverlayImage != null && moThoughtExpressionOverlayImage != eSpeechExpression.NONE){
			        		
				        	eImages oThoughtExpressionImage = eImages.valueOf(moThoughtExpressionOverlayImage.getEImagesString());
				        	
				    	   	BufferedImage oImageOverlay = null;
				    
				    	   	try {
				    	   		oImageOverlay = clsSingletonImageFactory.getImage(oThoughtExpressionImage);
				    	   	} catch (IOException e) {
				    	   		e.printStackTrace();
				    	   		throw new NullPointerException("Image URL could not be loaded, file not found in file");
				    	   	}
				        	
							oImageOverlay.getGraphics();
							graphics.drawImage(oImageOverlay, nxArc+30, nyArc-55, 60, 60, null ); 
			        	}
			        }
			        
				      //display a Speech expressionoverlay Icon
			        if(clsSingletonProperties.showSpeechAnswerOverlay()) {
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
							graphics.drawImage(oImageOverlay, nxArc-30, nyArc-30, 60, 60, null ); 
			        	}
			        }
			        
			        //display carriedItem
			        	if(moCarriedItem != null){
			        		
				        	
						    moCarriedItem.getGraphics();
							graphics.drawImage(moCarriedItem, nxArc, nyArc+20, 20, 20, null ); 
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

	/**
	 * @author hinterleitner
	 * 04.05.2011, 10:11:50
	 * 
	 * @param moOverlay the moOverlay to set
	 */
	public void setThoughtExpressionOverlayImage(eSpeechExpression moOverlay) {
		this.moThoughtExpressionOverlayImage = moOverlay;
	}
	
	public void setCarriedItem(BufferedImage poImage){
		moCarriedItem = poImage;
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
	
	public void setLifeValue(double value){
		moLifeValue = value;
	}



}
