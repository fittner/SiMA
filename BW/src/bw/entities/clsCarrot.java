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

import sim.physics2D.shape.Shape;
import enums.eEntityType;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.itfget.itfGetFlesh;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBindingState;
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
public class clsCarrot extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable {
	public static final String P_BODY 				= "body";
	public static final String P_SHAPE_FRESH 		= "shape_fresh";
	public static final String P_SHAPE_DEAD 		= "shape_dead";
	public static final String P_REGROW_STEPS_MIN 	= "regrow_steps_min";
	public static final String P_REGROW_STEPS_MAX 	= "regrow_steps_max";

	private Shape moFresh;
	private Shape moDead;
	
	private boolean mnShapeUpdated;
		
	private double mrInitialFleshWeight; //mass of the consumeable part of the carrot - set on creation of the flesh and after each regrow period  
	private int mnRegrowRate;
	private int mnStepsUntilRegrow;
	
	private clsMeatBody moBody;

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
	public clsCarrot(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);

		mnShapeUpdated = false;
		
		applyProperties(poPrefix, poProp);
		
		
		Random generator = new Random();
		mnRegrowRate = poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MIN)+
											 Math.round(generator.nextFloat()*
											(poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MAX)-
											 poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MIN)));
		mnStepsUntilRegrow = mnRegrowRate;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){
		String pre = clsBWProperties.addDot(poPrefix);
		
		moFresh = clsShapeCreator.createShape(poPrefix+P_SHAPE_FRESH, poProp);
		moDead = clsShapeCreator.createShape(poPrefix+P_SHAPE_DEAD, poProp);
		
		mrInitialFleshWeight = poProp.getPropertyDouble(pre+P_BODY+"."+clsFlesh.P_WEIGHT);
				
		moBody = new clsMeatBody(poPrefix+P_BODY, poProp);
		
		setVariableWeight(getFlesh().getWeight());		
	}	
		
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_RADIUS, "1.5");
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.orange);

		oProp.setProperty(pre+P_SHAPE_FRESH+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE_FRESH+"."+clsShapeCreator.P_RADIUS, "1.5");
		oProp.setProperty(pre+P_SHAPE_FRESH+"."+clsShapeCreator.P_COLOR, Color.orange);
		oProp.setProperty(pre+P_SHAPE_FRESH+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/carrot_clipart.png");
		oProp.setProperty(pre+P_SHAPE_FRESH+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());

		oProp.setProperty(pre+P_SHAPE_DEAD+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE_DEAD+"."+clsShapeCreator.P_RADIUS, "1.5");
		oProp.setProperty(pre+P_SHAPE_DEAD+"."+clsShapeCreator.P_COLOR, Color.gray);
		oProp.setProperty(pre+P_SHAPE_DEAD+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/carrot_grayscale.png");
		oProp.setProperty(pre+P_SHAPE_DEAD+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());

		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 0.5 );
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 2 );
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 5.0);
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 15);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);		
		
		oProp.setProperty(pre+P_REGROW_STEPS_MIN, 50);
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
	public clsFlesh getFlesh() {
		// TODO (deutsch) - Auto-generated method stub
		return this.moBody.getFlesh();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/carrot_clipart.jpg");

	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#Eat(float)
	 */
	public clsFood Eat(double prBiteSize) {
		//withdraw from the flesh the food corresponding the bite size in weight
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		
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
	public double tryEat() {
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPCarryable#getCarryableEntity()
	 */
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
		// TODO (deutsch) - Auto-generated method stub
		
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
		// TODO (deutsch) - Auto-generated method stub
		
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
		// TODO (deutsch) - Auto-generated method stub
		
	}

	private void updateShape() {
		if (getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to no_food_left
			// update shape to the gray carrot
			mnShapeUpdated = true;
			setShape(moDead, getTotalWeight());			
		} else if (!getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to food_available
			// update shape to the orange carrot
			mnShapeUpdated = true;
			setShape(moFresh, getTotalWeight());
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

}
