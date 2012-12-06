/**
 * @author deutsch
 * Jul 24, 2009, 10:50:57 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import statictools.clsGetARSPath;

import config.clsProperties;
import du.enums.eEntityType;


/**
 * DOCUMENT a simple stationary rectangle for multiple purpose, has NO image
 * 
 * @author muchitsch
 * Jul 24, 2011, 10:50:57 PM
 * 
 */
public class clsRectangleStationary extends clsStationary {
	public static final String CONFIG_FILE_NAME="rectangle_stationary.default.properties";
	public clsRectangleStationary(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
    	applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsStationary.getDefaultProperties(pre) );
		
		clsProperties oPropFile = clsProperties.readProperties(clsGetARSPath.getEntityConfigPath(), CONFIG_FILE_NAME);
		oPropFile.addPrefix(poPrefix);
		oProp.putAll(oPropFile);
		
/*	the old hardcoded properties : now they are in rectange_stationary.default.properties	
 * 		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_WIDTH, 30);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_LENGTH, 30);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.YELLOW);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
*/	
	
		return oProp;
	}		
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @since 30.11.2011 13:52:30
	 * 
	 * @see bw.entities.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.RECTANGLE_STATIONARY;
	}
}
