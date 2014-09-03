/**
 * CHANGELOG
 *
 * 25.09.2013 herret - File created
 *
 */
package physics2D.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;


import physical2d.physicalObject.datatypes.eFacialExpression;
import physical2d.physicalObject.datatypes.eSpeechExpression;


import sim.physics2D.util.Angle;
import sim.portrayal.DrawInfo2D;
import singeltons.clsSingletonImageFactory;
import singeltons.clsSingletonProperties;
import singeltons.eImages;
import singeltons.eStrings;


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

	BufferedImage moShakeLevel = null;
	BufferedImage moCryingLevel = null;
	BufferedImage moSweatLevel = null;
	BufferedImage moStressSweatLevel = null;
	BufferedImage moRedCheeks = null;
	private double mrRedCheeksIntensity = -1;
	Point moLipsCornerLeftEnd = null;
	Point moLipsCornerRightEnd = null;
	Point moUpperLipCenterPosition = null;
	Point moLowerLipCenterPosition = null;
	Point moCenterOfEyeBrowsPosition = null;
	Point moLeftCornerOfEyeBrowsPosition = null;
	Point moRightCornerOfEyeBrowsPosition = null;
	
	
	
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
        // volkan
        // DRAW BODILY EXPRESSIONS
    	if(moShakeLevel != null){ // SHAKE
    		moShakeLevel.getGraphics();
			graphics.drawImage(moShakeLevel, nxArc, nyArc, 60, 60, null );
			moShakeLevel = null;
    	}
    	if(moCryingLevel != null){ // CRY
    		moCryingLevel.getGraphics();
			graphics.drawImage(moCryingLevel, nxArc, nyArc, 60, 60, null );
			moCryingLevel = null;
    	}
    	if(moSweatLevel != null){ // SWEAT
    		moSweatLevel.getGraphics();
			graphics.drawImage(moSweatLevel, nxArc, nyArc, 60, 60, null );
			moSweatLevel = null;
    	}
    	if(moStressSweatLevel != null){ // STRESS SWEAT
    		moStressSweatLevel.getGraphics();
			graphics.drawImage(moStressSweatLevel, nxArc, nyArc, 60, 60, null );
			moStressSweatLevel = null;
    	}
    	if( (moRedCheeks != null) && (mrRedCheeksIntensity >= 0) ){ // RED CHEEKS
    		moRedCheeks.getGraphics();
			graphics.drawImage(moRedCheeks, nxArc, nyArc, 60, 60, null );
			moRedCheeks = null;
    	}

    	// draw mouth
    	BufferedImage mouthImage = null;
    	// draw mouth part 1: upper lip
    	if((moLipsCornerLeftEnd == null) || (moLipsCornerRightEnd == null) || (moUpperLipCenterPosition == null) || (moLowerLipCenterPosition == null)){
    		// ERROR: Not all points are set
    	}
    	else{
    		mouthImage = drawCurveWithThreePoints(mouthImage, moLipsCornerLeftEnd, moUpperLipCenterPosition, moLipsCornerRightEnd);
    	}

    	// draw mouth part 2: lower lip
    	if((moLipsCornerLeftEnd == null) || (moLipsCornerRightEnd == null) || (moUpperLipCenterPosition == null) || (moLowerLipCenterPosition == null)){
    		// ERROR: Not all points are set
    	}
    	else{
    		mouthImage = drawCurveWithThreePoints(mouthImage, moLipsCornerLeftEnd, moLowerLipCenterPosition, moLipsCornerRightEnd);
    	}

    	// draw mouth part 3: fill mouth?

    	// drawImage for mouth
    	if(mouthImage!=null) mouthImage.getGraphics();
		graphics.drawImage(mouthImage, nxArc, nyArc, 60, 60, null );


		// draw Eye Brow
    	BufferedImage eyeBrowImage = null;
    	// draw eye brow:
    	if((moCenterOfEyeBrowsPosition == null) || (moLeftCornerOfEyeBrowsPosition == null) || (moRightCornerOfEyeBrowsPosition == null)){
    		// ERROR: Not all points are set
    	}
    	else{
    		eyeBrowImage = drawCurveWithThreePoints(eyeBrowImage, moLeftCornerOfEyeBrowsPosition, moCenterOfEyeBrowsPosition, moRightCornerOfEyeBrowsPosition);
    	}
    	// drawImage for eye brows
    	if(eyeBrowImage!=null) eyeBrowImage.getGraphics();
		graphics.drawImage(eyeBrowImage, nxArc, nyArc, 60, 60, null );
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

	/**
	 * @since May 23, 2014 9:15:05 PM
	 *
	 * @param poShakeLevel the moShakeLevel to set
	 */
	public void setShakeLevel(BufferedImage poShakeLevel) {
		this.moShakeLevel = poShakeLevel;
	}

	public void setCryLevel(BufferedImage poCryingLevel){
		this.moCryingLevel = poCryingLevel;
	}

	/**
	 * @since May 23, 2014 9:15:46 PM
	 *
	 * @param poSweatLevel the moSweatLevel to set
	 */
	public void setSweatLevel(BufferedImage poSweatLevel) {
		this.moSweatLevel = poSweatLevel;
	}

	/**
	 * @since May 23, 2014 9:15:46 PM
	 *
	 * @param poStressSweatLevel the moStressSweatLevel to set
	 */
	public void setStressSweatLevel(BufferedImage poStressSweatLevel) {
		this.moStressSweatLevel = poStressSweatLevel;
	}

	/**
	 * @since May 24, 2014 11:03:34 PM
	 *
	 * @return the mrRedCheeksIntensity
	 */
	public double getRedCheeksIntensity() {
		return mrRedCheeksIntensity;
	}

	/**
	 * @since May 24, 2014 11:03:34 PM
	 *
	 * @param prRedCheeksIntensity the mrRedCheeksIntensity to set
	 */
	public void setRedCheeksImageAndIntensity(BufferedImage poRedCheeksImage, double prRedCheeksIntensity) {
		this.mrRedCheeksIntensity = prRedCheeksIntensity;
		this.moRedCheeks = changeOpacityOfAnyBufferedImageWithoutChangingTheOriginal(poRedCheeksImage, prRedCheeksIntensity);
	}

	public void setEyeBrowsPoints( double prEyeBrowsCenterVerticalFactor, double prEyeBrowsCornersVerticalFactor ){
		// setting default values for the current image's pixel positioning
		int lowestPositionOfCenterOfEyeBrowX = 93;
		int lowestPositionOfCenterOfEyeBrowY = 80;
		int distanceBetweenCenterAndACorner = 22;
		int maxIncreaseOfEyeBrows = 13;
		int heightDecreaseOfCornersRelativeToCenter = 8;

		moCenterOfEyeBrowsPosition = new Point( lowestPositionOfCenterOfEyeBrowX, (int)(lowestPositionOfCenterOfEyeBrowY - maxIncreaseOfEyeBrows * prEyeBrowsCenterVerticalFactor) );
		moLeftCornerOfEyeBrowsPosition = new Point( lowestPositionOfCenterOfEyeBrowX - distanceBetweenCenterAndACorner, (int)((lowestPositionOfCenterOfEyeBrowY + heightDecreaseOfCornersRelativeToCenter) - maxIncreaseOfEyeBrows * prEyeBrowsCornersVerticalFactor) );
		moRightCornerOfEyeBrowsPosition = new Point( lowestPositionOfCenterOfEyeBrowX + distanceBetweenCenterAndACorner, (int)((lowestPositionOfCenterOfEyeBrowY + heightDecreaseOfCornersRelativeToCenter) - maxIncreaseOfEyeBrows * prEyeBrowsCornersVerticalFactor) );
	}

	public void setMouthPoints(double prLipCornersVerticalFactor, double prLipCornersHorizontalFactor, double prOpenMouthFactor ){

		// setting default values for the current image's pixel positioning
		int defaultLipCenterX = 96;
		int defaultLipCenterY = 170;
		int minDistanceBetweenLipCenterAndLipCorner = 34;
		int maxDistanceFromCenterOfLipCornersUpOrDownState = 20;
		int maxLengthOfStretchiness = 10;
		int maxDistanceOfTheControlPointForAnOpenMouth = 34;

		moUpperLipCenterPosition = new Point( defaultLipCenterX, defaultLipCenterY );

		moLipsCornerLeftEnd = new Point( (int)(moUpperLipCenterPosition.x - (minDistanceBetweenLipCenterAndLipCorner + maxLengthOfStretchiness * prLipCornersHorizontalFactor)), (int)(moUpperLipCenterPosition.y - maxDistanceFromCenterOfLipCornersUpOrDownState * prLipCornersVerticalFactor)  );
		moLipsCornerRightEnd = new Point( (int)(moUpperLipCenterPosition.x + (minDistanceBetweenLipCenterAndLipCorner + maxLengthOfStretchiness * prLipCornersHorizontalFactor)), (int)(moUpperLipCenterPosition.y - maxDistanceFromCenterOfLipCornersUpOrDownState * prLipCornersVerticalFactor)  );
		moLowerLipCenterPosition = new Point( moUpperLipCenterPosition.x, (int)(moUpperLipCenterPosition.y + maxDistanceOfTheControlPointForAnOpenMouth * prOpenMouthFactor) );
	}

	private void placeAThickDot( BufferedImage poImage, Point poDot, int pnDotSize ){

		int r = 0;// red component 0...255
		int g = 0;// green component 0...255
		int b = 0;// blue component 0...255
		int a = 255;// alpha (transparency) component 0...255
		int colorWithARGB = (a << 24) | (r << 16) | (g << 8) | b; // color set to BLACK

		if( (poDot.x < 0) || (poDot.y < 0) || (poDot.x > poImage.getWidth()) || (poDot.y > poImage.getHeight()) ){
			// ERROR: Dot cant be outside of the image!
		}
		else{
			for(int currentX = poDot.x; currentX <= (poDot.x + pnDotSize); currentX++){
				for(int currentY = poDot.y; currentY <= (poDot.y + pnDotSize); currentY++){
					poImage.setRGB(currentX, currentY, colorWithARGB);
				}
			}
		}
	} // end placeAThickDot

	private BufferedImage drawCurveWithThreePoints( BufferedImage poImage, Point poStartPoint, Point poMiddlePoint, Point poEndPoint ) {
		// algorithm taken from web: http://stackoverflow.com/questions/785097/how-do-i-implement-a-bezier-curve-in-c
		if(poImage == null){
			poImage = new BufferedImage(195, 227, BufferedImage.TRANSLUCENT);
		}

		int GreenLineStartX = 0;
		int GreenLineStartY = 0;
		int GreenLineEndX = 0;
		int GreenLineEndY = 0;
		int BlackDotX = 0;
		int BlackDotY = 0;

		for( float i = 0 ; i < 1 ; i += 0.01 )
		{
		    // The Green Line
			GreenLineStartX = getPt( poStartPoint.x , poMiddlePoint.x , i );
			GreenLineStartY = getPt( poStartPoint.y , poMiddlePoint.y , i );
			GreenLineEndX = getPt( poMiddlePoint.x , poEndPoint.x , i );
			GreenLineEndY = getPt( poMiddlePoint.y , poEndPoint.y , i );

		    // The Black Dot
			BlackDotX = getPt( GreenLineStartX , GreenLineEndX , i );
			BlackDotY = getPt( GreenLineStartY , GreenLineEndY , i );

			placeAThickDot(poImage, new Point(BlackDotX, BlackDotY), 3);
		}

		return poImage;
	} // end drawCurveWithThreePoints

	private int getPt( int n1 , int n2 , float perc )
	{
		int diff = n2 - n1;

		return (int)( n1 + ( diff * perc ) );
	} // end getPt

	private BufferedImage changeOpacityOfAnyBufferedImageWithoutChangingTheOriginal( BufferedImage poIncomingImage, double prOpacity ){
		int newWidth = poIncomingImage.getWidth();
		int newHeight = poIncomingImage.getHeight();

		int r, g, b;
		int nTransparencyComponentA = ((int)(255 * prOpacity));

		BufferedImage oTempImage = new BufferedImage( newWidth, newHeight, BufferedImage.TRANSLUCENT );

		for(int x = 0; x < newWidth; x++){
			for(int y = 0; y < newHeight; y++){
				if( (poIncomingImage.getRGB(x, y) & 0xFF000000) == 0 ){ // if its already a pure transparent pixel
					oTempImage.setRGB(x, y, poIncomingImage.getRGB(x, y));
					continue;
				}

				b = poIncomingImage.getRGB(x, y) & 0x000000FF;
				g = poIncomingImage.getRGB(x, y) & 0x0000FF00;
				r = poIncomingImage.getRGB(x, y) & 0x00FF0000;

				oTempImage.setRGB( x, y, ((nTransparencyComponentA << 24) | r | g | b) );
			}
		}

		return oTempImage;
	} // end changeOpacityOfAnyBufferedImageWithoutChangingTheOriginal

}
