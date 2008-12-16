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

import javax.imageio.ImageIO;
import sim.util.Double2D;
import sim.field.continuous.Continuous2D;


/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 
 */
public class clsStone extends clsImagePortrayal{


	public final int STONE_S = 1;
    private final int STONE_M = 2;
    private final int STONE_L = 3;
    private final int STONE_XL = 4;
    
    double mnStoneType = 0;

	/**
	 * @param image
	 */
	public clsStone(int pnStoneType, Double2D poPosImage, Continuous2D poFieldEnvironment) {
			super();
	
			mnStoneType = pnStoneType;
			
			double nScale = 0;
			String nImagePath = null;
			
			switch (pnStoneType)
            {
            case STONE_S:
            	nImagePath = "/images/rock1.jpg";
            	nScale = 10;
            	break;
            case STONE_M:
            	nImagePath = "C:/temp/rock2.jpg";
            	nScale = 20;
            	break;
            case STONE_L:
            	nImagePath = "C:/temp/rock3.jpg";
            	nScale = 25;
            	break;
            case STONE_XL:
            	nImagePath = "C:/temp/rock4.jpg";
            	nScale = 30;
            	break;
            default:
            	nImagePath = "c:/temp/rock1.jpg";
        		nScale = 10;
        		break;
            }
			
			
			super.PlaceImage(nImagePath, nScale, poPosImage, poFieldEnvironment);
			
			
		// TODO Auto-generated constructor stub
	}

}
