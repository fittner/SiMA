/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;

import complexbody.io.sensors.datatypes.enums.eEntityType;

import properties.clsProperties;

import tools.clsPose;




import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsInanimate;
import entities.abstractEntities.clsUnorganic;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;

public class clsCan extends clsUnorganic {
	public static final String CONFIG_FILE_NAME = "can.default.properties";
	public clsCan(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid); 
		
		applyProperties(poPrefix, poProp);
    }

	
    private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}	
	
    public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
				
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 80.0);

		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 2);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(0x0000CD));
	 
		return oProp;
    }
    
    /* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.CAN;
		
	}

	@Override
	public void updateInternalState() {
		// nothing to do
	}

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