/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.portrayal.simple;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import sim.field.continuous.Continuous2D;

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

	
	/**
	 * @param image
	 * @param scale
	 */
	public clsImagePortrayal() {
		super(null);
		// just for extending the base
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
	public static void PlaceImage(String  psImagePath, double pnScale, Double2D poPosImage, Continuous2D poFieldEnvironment){
		
		//TODO Clemens load rel path?
		//URL url = getClass().getClassLoader().getResource("/../resources/images/rock1.jpg");
  
		
		File oFile = new File( psImagePath ); 
		
		BufferedImage oImage = null;
		try
		{
			oImage = ImageIO.read( oFile );
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sim.portrayal.simple.ImagePortrayal2D oImagePort = new sim.portrayal.simple.ImagePortrayal2D(oImage, pnScale);

		poFieldEnvironment.setObjectLocation(oImagePort, new sim.util.Double2D(poPosImage.x, poPosImage.y));
	}

}
