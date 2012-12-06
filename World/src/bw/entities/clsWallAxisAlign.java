/**
 * @author langr
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
 * Mason representative (physics+renderOnScreen) for a wall.  
 * 
 * FIXME: CLEMENS - REIMPLEMENT PLEASE!!!!
 * 
 * @author langr
 * 
 */
public class clsWallAxisAlign extends clsStationary  {	
    public static final String CONFIG_FILE_NAME="wall_axis.default.properties";
	public clsWallAxisAlign(String poPrefix, clsProperties poProp, int uid) {
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
		
		
/*	the old hardcodedproperties; now they are in wall_axis.default.properties
 * 		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);

		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_WIDTH, 10);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_LENGTH, 10);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.black);
 */			
		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.WALL;
	}
}