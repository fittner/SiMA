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

import statictools.clsGetARSPath;
import enums.eEntityType;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.itfget.itfGetFlesh;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.07.2009, 10:33:20
 * 
 */
public class clsCarrot extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable {
		
	public static final String P_IMAGE_PATH_FRESH = "image_path_fresh";
	public static final String P_IMAGE_PATH_DEAD = "image_path_dead";
	
	public static final String P_FAT = "nutrition_fat";
	public static final String P_WATER = "nutrition_water";
	
	public static final String P_CONTENT= "flesh_content";
	public static final String P_MAXCONTENT= "flesh_max_content";
	public static final String P_INCREASERATE = "flesh_increaserate";
	
	public static final String P_REGROW_STEPS_MIN = "regrow_steps_min";
	public static final String P_REGROW_STEPS_MAX = "regrow_steps_max";
	
	
	private double mrDefaultRadius; 
	private Color  moDefaultColor;  
	private double mrOwnMass; 
	private String moImagePathFresh;
	private String moImagePathDead;
	private double mrInitialFleshMass; //mass of the consumeable part of the carrot - set on creation of the flesh and after each regrow period  
	
	private boolean mnShapeUpdated;
		
	private int mnRegrowRate;
	private int mnStepsUntilRegrow;
	
	private clsMeatBody moBody;

	/**
	 * TODO (deutsch) - insert description 
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
		
		applyProperties(poPrefix, poProp);
		
		mnShapeUpdated = false;
		
		Random generator = new Random();
		mnRegrowRate = poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MIN)+
											 Math.round(generator.nextFloat()*
											(poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MAX)-
											 poProp.getPropertyInt(poPrefix + P_REGROW_STEPS_MIN)));
		mnStepsUntilRegrow = mnRegrowRate;
		
		moBody = new clsMeatBody(poPrefix, poProp);
		setMass(mrOwnMass + getFlesh().getWeight());
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(mrDefaultRadius, moDefaultColor , moImagePathFresh), getMass());
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
		//TODO
		mrOwnMass = poProp.getPropertyDouble(poPrefix + P_MASS); 
		mrInitialFleshMass = poProp.getPropertyDouble(poPrefix +P_CONTENT);
		mrDefaultRadius = poProp.getPropertyDouble(poPrefix +P_SHAPE_RADIUS);
		moDefaultColor =  poProp.getPropertyColor(poPrefix +P_ENTITY_COLOR_RGB);
		moImagePathFresh = poProp.getPropertyString(poPrefix +P_IMAGE_PATH_FRESH);
		moImagePathDead = poProp.getPropertyString(poPrefix +P_IMAGE_PATH_DEAD);
		
	}	
		
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(poPrefix) );
		oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.orange);
		oProp.setProperty(pre+P_MASS, 1.0);
		oProp.setProperty(pre+P_SHAPE_RADIUS, 5.0);
		oProp.setProperty(pre+P_SHAPE_TYPE, eShapeType.SHAPE_CIRCLE.name());
		oProp.setProperty(pre+P_IMAGE_PATH_FRESH, clsGetARSPath.getArsPath()+ "/src/resources/images/carrot_clipart.jpg");
		oProp.setProperty(pre+P_IMAGE_PATH_DEAD, clsGetARSPath.getArsPath()+ "/src/resources/images/carrot_grayscale.jpg");
		
		oProp.setProperty(pre+P_FAT, 5.0);
		oProp.setProperty(pre+P_WATER, 1.0);

		oProp.setProperty(pre+P_CONTENT, 15.0);
		oProp.setProperty(pre+P_MAXCONTENT, 15.0);
		oProp.setProperty(pre+P_INCREASERATE, 0.0);
	
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
		// TODO Auto-generated method stub
		return this.moBody.getFlesh();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:46:55
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#Eat(float)
	 */
	public clsFood Eat(double prBiteSize) {
		//withdraw from the flesh the food corresponding the bite size in weight
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		
		//update the Mason Physics2D Mass to the new weight
		setMass(mrOwnMass + getFlesh().getWeight());
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	private void updateShape() {
		if (getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to no_food_left
			// update shape to the gray carrot
			mnShapeUpdated = true;
			setShape(new ARSsim.physics2D.shape.clsCircleImage(mrDefaultRadius, Color.gray , moImagePathDead), getMass());			
		} else if (!getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to food_available
			// update shape to the orange carrot
			mnShapeUpdated = true;
			setShape(new ARSsim.physics2D.shape.clsCircleImage(mrDefaultRadius, moDefaultColor , moImagePathFresh), getMass());
		}		
	}
	
	private void regrowIt() {
		if (getFlesh().getTotallyConsumed()) {
			mnStepsUntilRegrow--;
			if (mnStepsUntilRegrow <= 0) {
				try {
					getFlesh().setWeight(mrInitialFleshMass);
					mnShapeUpdated = false;
					mnStepsUntilRegrow = mnRegrowRate;
				} catch (exFoodWeightBelowZero e) {
					// TODO Auto-generated catch block
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
