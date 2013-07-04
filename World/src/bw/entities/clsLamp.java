/**
 * @author muchitsch
 * 08.07.2009, 10:33:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import config.clsProperties;
import du.enums.eEntityType;
import bw.utils.enums.eShapeType;
import sim.physics2D.shape.Shape;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;


/**
 * DOCUMENT () - insert description 
 * 
 * @author muchitsch
 * 08.07.2009, 10:33:20
 * 
 */
public class clsLamp extends clsInanimate implements  itfAPCarryable {
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
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.MEAT.toString());
		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 5000);

		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPE_OFF);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_RADIUS, "8");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_COLOR, Color.YELLOW);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/lampOn.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ON+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_RADIUS, "8");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_COLOR, Color.lightGray);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/lampOff.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_OFF+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());

		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 5.0 );
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 5);
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.CARBOHYDRATE.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 0.0);
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 0.0);
		oProp.setProperty(pre+P_BODY+"."+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.name());
		oProp.setProperty(pre+P_BODY+"."+"2."+clsFlesh.P_NUTRITIONFRACTION, 0.0);	
		oProp.setProperty(pre+P_BODY+"."+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.VITAMIN.name());
		oProp.setProperty(pre+P_BODY+"."+"3."+clsFlesh.P_NUTRITIONFRACTION, 0.0);	
		oProp.setProperty(pre+P_BODY+"."+"4."+clsFlesh.P_NUTRITIONTYPE, eNutritions.MINERAL.name());
		oProp.setProperty(pre+P_BODY+"."+"4."+clsFlesh.P_NUTRITIONFRACTION, 0.0);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 150);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);		
		
//		oProp.setProperty(pre+P_REGROW_STEPS_MIN, 250);
	//	oProp.setProperty(pre+P_REGROW_STEPS_MAX, 1000);

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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#execution()
	 */
	@Override
	public void execution() {
		// nothing to do
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#processing()
	 */
	@Override
	public void processing() {
		// nothing to do
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// nothing to do
		
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
	




}
