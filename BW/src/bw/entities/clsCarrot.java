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
import enums.eEntityType;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.util.clsPose;
import bw.actionresponses.clsMeatResponses;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.itfget.itfGetFlesh;
import bw.exceptions.exFoodAmountBelowZero;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.07.2009, 10:33:20
 * 
 */
public class clsCarrot extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable {
	private static double mrDefaultRadius = 5.0;
	private static String moImagePathFresh = sim.clsBWMain.msArsPath + "/src/resources/images/carrot_clipart.jpg";
	private static String moImagePathDead = sim.clsBWMain.msArsPath + "/src/resources/images/carrot_grayscale.jpg";
	private static Color moDefaultColor = Color.orange;
	
	private static double mrOwnMass = 1.0; //mass without any flesh left ...
	private double mrInitialFleshMass = 0.0; //mass of the consumeable part of the carrot - set on creation of the flesh and after each regrow period  

	private boolean mnShapeUpdated;
		
	private int mnRegrowRate;
	private static int mnRegrowStepsMin = 50;
	private static int mnRegrowStepsMax = 1000;
	
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
	public clsCarrot(int pnId, clsPose poStartingPose,
			Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poStartingPose, poStartingVelocity, null, clsCarrot.mrOwnMass, clsCarrot.getFinalConfig(poConfig));
		
		applyConfig();
		
		mnShapeUpdated = false;
		
		Random generator = new Random();
		mnRegrowRate =  mnRegrowStepsMin + Math.round(generator.nextFloat() * (mnRegrowStepsMax - mnRegrowStepsMin));
		mnStepsUntilRegrow = mnRegrowRate;
		
		moBody = new clsMeatBody(this, (clsConfigMap)moConfig.get(eConfigEntries.BODY));
		setMass(mrOwnMass + getFlesh().getAmount());
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(clsCarrot.mrDefaultRadius, moDefaultColor , moImagePathFresh), getMass());
		
		setEntityActionResponse(new clsMeatResponses(this));
	}
	
	private void applyConfig() {

		// store initial mass of flesh - needed for regrowing
		mrInitialFleshMass = ((clsConfigDouble)((clsConfigMap)((clsConfigMap)moConfig.get(eConfigEntries.BODY)).get(eConfigEntries.INTSYS_FLESH)).get(eConfigEntries.CONTENT)).get();

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();

		clsConfigMap oBody = new clsConfigMap();		

		clsConfigMap oFlesh = new clsConfigMap();		
		clsConfigMap oNutritions = new clsConfigMap();
		
		oNutritions.add(eConfigEntries.FAT, new clsConfigDouble(5.0f));
		oNutritions.add(eConfigEntries.WATER, new clsConfigDouble(1.0f));
		
		oFlesh.add(eConfigEntries.NUTRITIONS, oNutritions);
		oFlesh.add(eConfigEntries.CONTENT, new clsConfigDouble(15.0f));
		oFlesh.add(eConfigEntries.MAXCONTENT, new clsConfigDouble(15.0f));
		oFlesh.add(eConfigEntries.INCREASERATE, new clsConfigDouble(0.00f));
		
		oBody.add(eConfigEntries.INTSYS_FLESH, oFlesh);

		oDefault.add(eConfigEntries.BODY, oBody);		
		
		return oDefault;
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
	@Override
	public clsFood Eat(float prBiteSize) {
		//withdraw from the flesh the food corresponding the bite size in weight
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		
		//update the Mason Physics2D Mass to the new weight
		setMass(mrOwnMass + getFlesh().getAmount());
		
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
	public float tryEat() {
		// TODO Auto-generated method stub
		return 0;
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
			setShape(new ARSsim.physics2D.shape.clsCircleImage(clsCarrot.mrDefaultRadius, Color.gray , moImagePathDead), getMass());			
		} else if (!getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			// state has changed recently to food_available
			// update shape to the orange carrot
			mnShapeUpdated = true;
			setShape(new ARSsim.physics2D.shape.clsCircleImage(clsCarrot.mrDefaultRadius, moDefaultColor , moImagePathFresh), getMass());
		}		
	}
	
	private void regrowIt() {
		if (getFlesh().getTotallyConsumed()) {
			mnStepsUntilRegrow--;
			if (mnStepsUntilRegrow <= 0) {
				try {
					getFlesh().setAmount(mrInitialFleshMass);
					mnShapeUpdated = false;
					mnStepsUntilRegrow = mnRegrowRate;
				} catch (exFoodAmountBelowZero e) {
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
