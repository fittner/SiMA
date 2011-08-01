/**
 * @author deutsch
 * Jul 24, 2009, 10:50:57 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsProperties;

import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eShapeType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * Jul 24, 2009, 10:50:57 PM
 * 
 */
public class clsWallHorizontal extends clsWallAxisAlign {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * Jul 24, 2009, 10:51:04 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsWallHorizontal(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
    	applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsStationary.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_WIDTH, 200);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_HEIGHT, 6);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.black);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/World/src/resources/images/wall1.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		return oProp;
	}		
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}
}