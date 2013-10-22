/**
 * clsSmartExcrements.java: BW - bw.entities
 * 
 * @author deutsch
 * 06.08.2009, 14:12:46
 */
package bw.entities.base;

import java.awt.Color;

import statictools.clsGetARSPath;

import config.clsProperties;
import du.enums.eEntityType;
import ARSsim.physics2D.util.clsPose;
import bw.ARSIN.factory.clsARSINFactory;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 06.08.2009, 14:12:46
 * 
 */
public class clsSmartExcrement extends clsOrganic {

	public clsSmartExcrement(String poPrefix, clsProperties poProp, int uid, double prWeight)
    {
		super(poPrefix, poProp, uid);		
		applyProperties(poPrefix, poProp);
		
		try {
			getFlesh().setWeight(prWeight);
		} catch (exFoodWeightBelowZero e) {
			// nothing to do
		}
		setVariableWeight(getFlesh().getWeight());
    } 
	
	public clsSmartExcrement(String poPrefix, clsProperties poProp, int uid)
    {
		super(poPrefix, poProp, uid);		
		applyProperties(poPrefix, poProp);
		
    } 
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
//		String pre = clsProperties.addDot(poPrefix);
		
		setVariableWeight(getFlesh().getWeight());
	}	
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
			
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		
		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		oProp.putAll( clsMeatBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.MEAT.toString());
		
		
 		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 0.0001);

		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 2);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(165, 42, 42));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "smartexcrement.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 1 );
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 1 );
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, Double.MAX_VALUE);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);		
	 
		return oProp;
	}
	



	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 06.08.2009, 14:12:46
	 * 
	 * @see bw.entities.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.SMARTEXCREMENT;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 06.08.2009, 14:12:46
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// nothing to do

	}
	
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsARSINFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
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
