/**
 * @author tobias
 * Jul 24, 2009, 11:00:37 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import bw.entities.tools.clsShapeCreator;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;

/**
 * DOCUMENT (tobias) - insert description 
 * 
 * @author tobias
 * Jul 24, 2009, 11:00:37 PM
 * 
 */
public class clsWallVertical extends clsWallAxisAlign {
	/**
	 * DOCUMENT (tobias) - insert description 
	 * 
	 * @author tobias
	 * Jul 24, 2009, 10:51:04 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsWallVertical(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
    	applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsStationary.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_WIDTH, 1);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_HEIGHT, 10);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.black);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "/src/resources/images/wall2.png");
		
		return oProp;
	}		
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
		// nothing to do
	}
}
