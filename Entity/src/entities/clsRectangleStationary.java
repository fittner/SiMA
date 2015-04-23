/**
 * @author deutsch
 * Jul 24, 2009, 10:50:57 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;

import properties.clsProperties;
import tools.clsPose;
import tools.eImagePositioning;

import complexbody.io.sensors.datatypes.enums.eEntityType;

import entities.abstractEntities.clsStationary;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;
//import sim.display.GUIState;
//import sim.portrayal.Inspector;
//import sim.portrayal.LocationWrapper;
//import sim.portrayal.inspector.TabbedInspector;
//import bw.utils.inspectors.entity.clsInspectorBasic;
import entities.abstractEntities.clsEntity;


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
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Rectange Stationary");		
	}*/
	
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsEntityFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
		double x = this.getPose().getPosition().x;
		double y = this.getPose().getPosition().y;
		double angle = this.getPose().getAngle().radians;
		double weight = this.getVariableWeight();
		
		//set position
		oNewEntity.setPose(new clsPose(x-(poDistance/2), y, angle));
		this.setPose(new clsPose(x+(poDistance/2), y, angle));
		//set weight
		oNewEntity.setVariableWeight(weight*poSplitFactor);
		this.setVariableWeight(weight*(1-poSplitFactor));
		
		return oNewEntity;

	}
}
