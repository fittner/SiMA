/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;


import java.awt.Color;

import statictools.clsGetARSPath;


import ARSsim.physics2D.util.clsPose;
import bw.entities.base.clsAnimate;
import bw.entities.base.clsEntity;
import bw.entities.base.clsInanimate;
import bw.entities.base.clsOrganic;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;

import config.clsProperties;
import du.enums.eEntityType;
import entities.factory.clsEntityFactory;
import bw.utils.enums.eShapeType;

/**
 * An instance of the mobile object clsAnimate that can:
 * - grow
 * - be harvest
 * - withdrawn
 * - be eaten  
 * 
 * @author langr
 * 
 */
public class clsPlant extends clsOrganic {
		
	public clsPlant(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
    }
    
    private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}	
    
    public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsAnimate.getDefaultProperties(poPrefix) );
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 300.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 10);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(0x33FF33));	//Green
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "plant01.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());

		return oProp;
	}	
  	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType =  eEntityType.PLANT;
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// nothing to do
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// nothing to do
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:33
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		// nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:33
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
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