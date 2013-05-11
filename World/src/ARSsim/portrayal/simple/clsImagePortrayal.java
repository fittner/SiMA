/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.portrayal.simple;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import sim.field.continuous.Continuous2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.simple.ImagePortrayal2D;
import sim.util.Double2D;




/**
 * A special Version of the Mason ImgagePortrayal class, that can load a image 
 * file from a filepath. for more Information see Masons sim.portrayal.simple.ImagePortrayal2D
 * no physics here! just imgae display
 * @author muchitsch
 * 
 */
public class clsImagePortrayal extends ImagePortrayal2D{

	BufferedImage moImage = null;
	double mnMinImageSize = 15;  //minimal Image size to be shown
	double mnScale = 1;
	double mrRadius = 15;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7515804757462033545L;


	/**
	 * @param image
	 * @param scale
	 */
	public clsImagePortrayal(double pnScale, String poImageFilePath) {
		super(null);
		// just for extending the base
		
      
    	File oFile = new File( poImageFilePath ); 

	   	try
	   	{
	   		moImage = ImageIO.read( oFile );
	   	} catch (IOException e)
	   	{
	   		e.printStackTrace();
	   		throw new NullPointerException("Image URL could not be loaded, file not found in path:" +poImageFilePath);
	   	}
	}
	
	@Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
        final double fWidthArc =  info.draw.width * mrRadius * 2;
        final double fHeightArc =  info.draw.height  * mrRadius * 2;
       
        graphics.setPaint(paint);

        final int nxArc = (int)(info.draw.x - fWidthArc / 2.0 );
        final int nyArc = (int)(info.draw.y - fHeightArc / 2.0 );
//        final int nwArc = (int)(fWidthArc);
//        final int nhArc = (int)(fHeightArc);

        //displays the physical circle
        //graphics.fillOval(nxArc, nyArc, nwArc, nhArc); //fillOval(x,y,w,h); //scale automatic by mason
        

       
    	if(!(fWidthArc < mnMinImageSize || fHeightArc < mnMinImageSize)) //dont show images if scale to small -> perfomance
    	{
	        int nScaledWidth = (int) (fWidthArc  ); //here the with of the arc should be used
	        int nScaledHeight = (int) (fHeightArc );

	   	
	        //AffineTransform affe = AffineTransform.getRotateInstance(getOrientation().radians);
	        moImage.getGraphics();
	        //imgGra.rotate(getOrientation().radians);
	        
	        graphics.drawImage(moImage, nxArc , nyArc, nScaledWidth, nScaledHeight, null );
    	}
	}

   
	
	
	/**
	 * Use this function to place a image on the environment.
	 * for more Information see Masons sim.portrayal.simple.ImagePortrayal2D
	 * !catches image loading exceptions, and displays them in console!
	 *
	 * @param psImagePath full absolute filepath
	 * @param pnScale
	 * @param poPosImage
	 * @param poFieldEnvironment
	 */
	public static ImagePortrayal2D PlaceImage(String  psImagePath, double pnScale, double pnX, double pnY, Continuous2D poFieldEnvironment){
		
		//TODO Clemens load rel path?
		//URL url = getClass().getClassLoader().getResource("/../resources/images/rock1.jpg");
  
		Double2D poPosImage = new Double2D(pnX, pnY);
		
		File oFile = new File( psImagePath ); 
		
		BufferedImage oImage = null;
		try
		{
			oImage = ImageIO.read( oFile );
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		sim.portrayal.simple.ImagePortrayal2D oImagePort = new sim.portrayal.simple.ImagePortrayal2D(oImage, pnScale);

		poFieldEnvironment.setObjectLocation(oImagePort, new sim.util.Double2D(poPosImage.x, poPosImage.y));
		
		return oImagePort;
	}
	
	
	public static void RemoveImage(sim.portrayal.simple.ImagePortrayal2D poImagePort, Continuous2D poFieldEnvironment){
		poFieldEnvironment.remove(poImagePort);
	}

}
