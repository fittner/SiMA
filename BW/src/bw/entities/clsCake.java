/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfGetFlesh;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.factories.clsRegisterEntity;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.*;
import enums.eEntityType;

/**
 * DOCUMENT (tobias) - insert description 
 * 
 * @author tobias
 * Jul 24, 2009, 10:15:27 PM
 * 
 */
public class clsCake extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable {
	public static final String P_BODY = "body";
	
	private clsMeatBody moBody;
		
	public clsCake(String poPrefix, clsBWProperties poProp)
    {
		super(poPrefix, poProp);		
		applyProperties(poPrefix, poProp);
    } 
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
		String pre = clsBWProperties.addDot(poPrefix);
		
		moBody = new clsMeatBody(pre+P_BODY, poProp);
		
		setVariableWeight(getFlesh().getWeight());
	}	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
			
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/cake.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 15.0 );
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 2 );
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 5.0);
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 15);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);		
		
		return oProp;
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
		if (getFlesh().getTotallyConsumed()) {
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
		setVariableWeight(getFlesh().getWeight());
		
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