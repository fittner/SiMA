/**
 * @author deutsch
 * 08.07.2009, 10:33:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.Random;
import config.clsProperties;
import du.enums.eEntityType;
import sim.physics2D.shape.Shape;
import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.itfget.itfIsConsumeable;
import bw.body.itfget.itfGetFlesh;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.07.2009, 10:33:20
 * 
 */
public class clsCarrot extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable, itfIsConsumeable {
	public static final String P_SHAPE_FRESH 		= "shape_fresh";
	public static final String P_SHAPE_DEAD 		= "shape_dead";
	public static final String P_REGROW_STEPS_MIN 	= "regrow_steps_min";
	public static final String P_REGROW_STEPS_MAX 	= "regrow_steps_max";

	private Shape moFresh2D;
	private Shape moDead2D;
	
	private boolean mnShapeUpdated;
		
	private double mrInitialFleshWeight; //mass of the consumeable part of the carrot - set on creation of the flesh and after each regrow period  
	private int mnRegrowRate;
	private int mnStepsUntilRegrow;
	
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
	public clsCarrot(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);

		mnShapeUpdated = false;
		
		applyProperties(poPrefix, poProp);
		
		
		Random generator = new Random();
		mnRegrowRate = poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MIN)+
											 Math.round(generator.nextFloat()*
											(poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MAX)-
											 poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MIN)));
		mnStepsUntilRegrow = mnRegrowRate;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp){
		String pre = clsProperties.addDot(poPrefix);
		
		moFresh2D = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_FRESH, poProp);
		moDead2D = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_DEAD, poProp);
		
		mrInitialFleshWeight = poProp.getPropertyDouble(pre+P_BODY+"."+clsFlesh.P_WEIGHT);
				
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
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 5000);

		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPE_FRESH);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_FRESH+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_FRESH+"."+clsShape2DCreator.P_RADIUS, "5");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_FRESH+"."+clsShape2DCreator.P_COLOR, Color.orange);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_FRESH+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/carrot_clipart.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_FRESH+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_RADIUS, "5");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_COLOR, Color.gray);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/carrot_grayscale.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());

		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 5.0 );
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 5);
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.CARBOHYDRATE.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 5.0);
		oProp.setProperty(pre+P_BODY+"."+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.name());
		oProp.setProperty(pre+P_BODY+"."+"2."+clsFlesh.P_NUTRITIONFRACTION, 1.0);	
		oProp.setProperty(pre+P_BODY+"."+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.VITAMIN.name());
		oProp.setProperty(pre+P_BODY+"."+"3."+clsFlesh.P_NUTRITIONFRACTION, 2.0);	
		oProp.setProperty(pre+P_BODY+"."+"4."+clsFlesh.P_NUTRITIONTYPE, eNutritions.MINERAL.name());
		oProp.setProperty(pre+P_BODY+"."+"4."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 150);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);		
		
		oProp.setProperty(pre+P_REGROW_STEPS_MIN, 250);
		oProp.setProperty(pre+P_REGROW_STEPS_MAX, 1000);
		
		return oProp;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType =  eEntityType.CARROT;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	@Override
	public clsFlesh getFlesh() {
		return ((clsMeatBody)moBody).getFlesh();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/carrot_clipart.jpg");

	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#Eat(float)
	 */
	@Override
	public clsFood Eat(double prBiteSize) {
		//withdraw from the flesh the food corresponding the bite size in weight
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		
		if (mnShapeUpdated && getFlesh().getTotallyConsumed()) {
			mnShapeUpdated = false;
		}
		
		setVariableWeight(getFlesh().getWeight());
		
		//return the chunk of food
		return oFood;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#tryEat()
	 */
	@Override
	public double tryEat() {
		if (getFlesh().getTotallyConsumed()) {
			return 1;
		} else {
			return 0;
		}
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
		if (getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to no_food_left
			// update shape to the gray carrot
			clsEventLogger.add(new Event(this, getId(), eEvent.CONSUMED, ""));
			mnShapeUpdated = true;
			set2DShape(moDead2D, getTotalWeight());		
		} else if (!getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to food_available
			// update shape to the orange carrot
			clsEventLogger.add(new Event(this, getId(), eEvent.RESPAWN, ""));
			mnShapeUpdated = true;
			set2DShape(moFresh2D, getTotalWeight());
		}		
	}
	
	private void regrowIt() {
		if (getFlesh().getTotallyConsumed()) {
			mnStepsUntilRegrow--;
			if (mnStepsUntilRegrow <= 0) {
				try {
					getFlesh().setWeight(mrInitialFleshWeight);
					mnShapeUpdated = false;
					mnStepsUntilRegrow = mnRegrowRate;
				} catch (exFoodWeightBelowZero e) {
					e.printStackTrace();
				}
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
		regrowIt();
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 11:36:06
	 * 
	 * @see bw.body.itfget.itfGetConsumeable#isConsumable()
	 */
	@Override
	public boolean isConsumable() {
		return getFlesh().getTotallyConsumed();
	}	

}
