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

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eShapeType;
import bw.utils.inspectors.entity.clsInspectorBasic;

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
		
	
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_WIDTH, 30);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_LENGTH, 30);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.YELLOW);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		

	
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

	/* (non-Javadoc)
	 *
	 * @since Dec 11, 2012 4:22:52 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.inspector.TabbedInspector, sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Rectange Stationary");		
	}
}
