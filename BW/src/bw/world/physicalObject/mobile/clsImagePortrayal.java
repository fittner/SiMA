/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.world.physicalObject.mobile;

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
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 
 */
public class clsImagePortrayal extends ImagePortrayal2D{

//	double mnScale = 0;
//	Double2D moPosImage = null;
//	String msImagePath = null;
	
	/**
	 * @param image
	 * @param scale
	 */
	public clsImagePortrayal() {
		super(null);
		// just for extending the base
	}
	
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @param psImagePath
	 * @param pnScale
	 * @param poPosImage
	 * @param poFieldEnvironment
	 */
	public void PlaceImage(String  psImagePath, double pnScale, Double2D poPosImage, Continuous2D poFieldEnvironment){
		
		//TODO Clemens loar rel path!
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
