/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfGetFlesh;
import bw.factories.clsRegisterEntity;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.*;
import enums.eEntityType;
import ARSsim.physics2D.util.clsPose;

/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsCake extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable {
	private static double mrDefaultRadius = 10.0;
	private static String moImagePath = sim.clsBWMain.msArsPath + "/src/resources/images/cake.gif";
	private static Color moDefaultColor = Color.pink;
	
	private static double mrOwnMass = 1.0; //mass without any flesh left ...
	
	private boolean mnShapeUpdated;
	
	private clsMeatBody moBody;

	public clsCake(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigMap poConfig)
    {
//		super(pnId, poPose, poStartingVelocity, new ARSsim.physics2D.shape.clsCircleImage(prRadius, clsStone.moDefaultColor, clsStone.moImagePath), prRadius * clsStone.mrDefaultRadiusToMassConversion);
		//todo muchitsch ... hier wird eine default shape �bergeben, nicht null, sonst krachts
		super(pnId, poPose, poStartingVelocity, null, clsCake.mrOwnMass, clsCake.getFinalConfig(poConfig));
		
		applyConfig();
		
		mnShapeUpdated = false;
		
		moBody = new clsMeatBody(this, (clsConfigMap)moConfig.get(eConfigEntries.BODY));
		setMass(mrOwnMass + getFlesh().getWeight());
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(clsCake.mrDefaultRadius, moDefaultColor , moImagePath), getMass());
    } 
	
	private void applyConfig() {
		//TODO add ...
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
		meEntityType = eEntityType.CAKE;
		
	}


	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
		if (getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
			mnShapeUpdated = true;
			setShape(new sim.physics2D.shape.Circle(clsCake.mrDefaultRadius, Color.gray), getFlesh().getWeight());
			
			//TODO langr: wohin damit
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 19:26:16
	 * 
	 * @see bw.entities.clsEntity#execution()
	 */
	@Override
	public void execution() {
		// no executions
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 19:26:16
	 * 
	 * @see bw.entities.clsEntity#processing()
	 */
	@Override
	public void processing() {
		// no processing
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 19:26:16
	 * 
	 * @see bw.entities.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// no sensing
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.05.2009, 18:16:27
	 * 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	public clsFlesh getFlesh() {
		return this.moBody.getFlesh();
	}
	
	/*
	 * Interface Eatable
	 */
	public double tryEat() {
		return 0;
	}
	public clsFood Eat(double prBiteSize) {
		//withdraw from the flesh the food corresponding the bite size in weight
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		
		//update the Mason Physics2D Mass to the new weight
		setMass(mrOwnMass + getFlesh().getWeight());
		
		//return the chunk of food
		return oFood;
	}
	
	/*
	 * Interface Carryable
	 */
	public clsMobile getCarryableEntity() {
		return this;	
	}
	public void setCarriedBindingState(eBindingState pBindingState) {
		//handle binding-state implications 
	}
	
}