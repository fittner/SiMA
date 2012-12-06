/**
 * @author deutsch
 * Jul 24, 2009, 11:00:37 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import statictools.clsGetARSPath;

import config.clsProperties;



/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * Jul 24, 2009, 11:00:37 PM
 * 
 */
public class clsWallVertical extends clsWallAxisAlign {
	public static final String CONFIG_FILE_NAME ="wall_ver.default.properties";
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * Jul 24, 2009, 10:51:04 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsWallVertical(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
    	applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsWallAxisAlign.getDefaultProperties(pre) );
		
		clsProperties oPropFile = clsProperties.readProperties(clsGetARSPath.getEntityConfigPath(), CONFIG_FILE_NAME);
		oPropFile.addPrefix(poPrefix);
		oProp.putAll(oPropFile);

		
		
/* the old hardcoded properties; now they are in wall_ver.default.properties
 * 		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);

		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_WIDTH, 6);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_LENGTH, 200);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.black);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/wall2.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
	*/	
		return oProp;
	}		
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}
}
