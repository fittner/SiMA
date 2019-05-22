/**
 * clsSingletonImageFactory.java: BW - bw.factories
 * 
 * @author muchitsch
 * 04.05.2011, 11:34:14
 */
package singeltons;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import logger.clsLogger;
import utils.clsGetARSPath;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 04.05.2011, 11:34:14
 * 
 */
public class clsSingletonImageFactory {
    private static clsSingletonImageFactory instance = null;
    private HashMap<eImages, BufferedImage> moImageBuffer;
    
    private clsSingletonImageFactory() {
    	moImageBuffer = new HashMap<eImages, BufferedImage>();
    }
    
	static private clsSingletonImageFactory instance() {
		if (null == instance) {
			instance = new clsSingletonImageFactory();
		}
		return instance;
	}    
	
	static public BufferedImage getImage(eImages pnImage) throws IOException  {
		BufferedImage oResult = null;
		
		if (!clsSingletonImageFactory.instance().moImageBuffer.containsKey(pnImage)) {
			clsSingletonImageFactory.instance().loadImage(pnImage);		
		}
		
		oResult = clsSingletonImageFactory.instance().moImageBuffer.get(pnImage);
		
		if (oResult == null) {
			throw new IOException("clsSingletonImageFactory.getImage: file '"+clsGetARSPath.getImagePath() + pnImage.getFilename()+"' not found/not loadable.");
		}
		
		return oResult;
	}
	
	
	
	//new method
	static public BufferedImage getString(eStrings pnString) throws IOException  {
		BufferedImage oResult = null;
		
		if (!clsSingletonImageFactory.instance().moImageBuffer.containsKey(pnString)) {
			//clsSingletonImageFactory.instance().loadImage(pnString);		
		}
		
		oResult = clsSingletonImageFactory.instance().moImageBuffer.get(pnString);
		
		if (oResult == null) {
			throw new IOException("clsSingletonImageFactory.getImage: file '"+clsGetARSPath.getImagePath() + pnString.getFilename()+"' not found/not loadable.");
		}
		
		return oResult;
	}
	
	private void loadImage(eImages pnImage){
		String oFilename = clsGetARSPath.getImagePath() + pnImage.getFilename();
		
//		System.out.println("loading image into image buffer "+oFilename);
		clsLogger.getLog("sim").info("loading image {} into image buffer ", oFilename);
		BufferedImage oBI = null;	

		try {
			File oFile = new File( oFilename ); 			
			oBI = ImageIO.read( oFile );
		} catch (IOException e) {
			//System.out.println("clsSingletonImageFactory.loadImage: "+e);
			clsLogger.getLog("sim").info("clsSingletonImageFactory.loadImage: "+e);
		}
		
		moImageBuffer.put(pnImage, oBI);		
	}
	

	
	

}
