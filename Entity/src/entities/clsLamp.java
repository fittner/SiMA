/**
 * @author muchitsch
 * 08.07.2009, 10:33:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;

import properties.clsProperties;

import complexbody.internalSystems.clsFlesh;

import du.enums.eEntityType;
import entities.abstractEntities.clsAnimate;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsInanimate;
import entities.abstractEntities.clsMobile;
import entities.abstractEntities.clsUnorganic;
import entities.actionProxies.itfAPCarryable;
import entities.enums.eBindingState;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;
import body.clsMeatBody;
import sim.physics2D.shape.Shape;
import tools.clsPose;
import tools.eImagePositioning;
import utils.clsGetARSPath;


/**
 * DOCUMENT () - insert description 
 * 
 * @author muchitsch
 * 08.07.2009, 10:33:20
 * 
 */
public class clsLamp extends clsUnorganic implements  itfAPCarryable {
	public static final String CONFIG_FILE_NAME 	= "carrot.default.properties";
	public static final String P_SHAPE_ON 		= "shape_fresh";
	public static final String P_SHAPE_OFF 		= "shape_dead";

	private Shape moLampOff2D;
	private Shape moLampOn2D;
	
	private boolean mnShapeUpdated;
	
	private double mrBrightnessOn = 0.9;
	private double mrBrightnessOff = 0.0;

	private int mnStepsUntilOn = 5; //counter, if >0 then this is the initial wait timer
	private int mnBlinkRate = 10; //in steps

	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 08.07.2009, 10:33:28
	 *
	 * @param pnId
	 * @param poStartingPose
	 * @param poStartingVelocity
	 * @param poConfig
	 */
	public clsLamp(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);

		mnShapeUpdated = false;
		mrVisionBrightness = mrBrightnessOff; //start off
		
		applyProperties(poPrefix, poProp);

	}
	
	private void applyProperties(String poPrefix, clsProperties poProp){
		String pre = clsProperties.addDot(poPrefix);
		
		moLampOn2D = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_ON, poProp);
		moLampOff2D = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_OFF, poProp);

	}	
		
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		
		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		//properties for the body
		oProp.putAll( clsMeatBody.getDefaultProperties(pre+P_BODY) );
		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 5000);

		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPE_OFF);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_RADIUS, "8");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_COLOR, Color.YELLOW);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "lampOn.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_RADIUS, "8");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_COLOR, Color.lightGray);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "lampOff.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());

		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 5.0 );


		return oProp;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType =  eEntityType.LAMP;
	}



	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPCarryable#getCarryableEntity()
	 */
	@Override
	public clsMobile getCarryableEntity() {
		return this;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPCarryable#setCarriedBindingState(bw.utils.enums.eBindingState)
	 */
	@Override
	public void setCarriedBindingState(eBindingState bindingState) {
		//handle binding-state implications 		
	}



	private void updateShape() {
		
		
		if (this.mrVisionBrightness == mrBrightnessOn && !mnShapeUpdated) {
			mnShapeUpdated = true;
			set2DShape(moLampOn2D, getTotalWeight());		
		} else if (this.mrVisionBrightness == mrBrightnessOff && !mnShapeUpdated) {
			mnShapeUpdated = true;
			set2DShape(moLampOff2D, getTotalWeight());
		}		
	}
	
	private void BlinkIt() {
		
		mnStepsUntilOn--;
		
//		if (mnStepsUntilOn <= 0 ) {
//			this.mrVisionBrightness = mrBrightnessOn;
//			mnStepsUntilOn = mnBlinkRate;
//			mnShapeUpdated = false; //marks that the shape has not been updated yet.. will do in next step
//		}
//		else{
//			this.mrVisionBrightness = mrBrightnessOff;
//			//mnStepsUntilOn = mnBlinkRate;
//		}
		
		if (mnStepsUntilOn <= 0 )
		{
			if(this.mrVisionBrightness == mrBrightnessOff) 
			{
				this.mrVisionBrightness = mrBrightnessOn;
				mnStepsUntilOn = mnBlinkRate;
				mnShapeUpdated = false; //marks that the shape has not been updated yet.. will do in next step
			}
			else{
				this.mrVisionBrightness = mrBrightnessOff;
				mnShapeUpdated = false;
				mnStepsUntilOn = mnBlinkRate;
			}
		}
		
		
	}	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		updateShape();
		BlinkIt();
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
