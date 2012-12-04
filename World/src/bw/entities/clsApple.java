/**
 * @author schaat
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;

import config.clsProperties;
import du.enums.eEntityType;
import bw.body.clsBaseBody;
import bw.body.clsMeatBody;
import bw.body.attributes.clsAttributes;
import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfGetBody;
import bw.body.itfget.itfIsConsumeable;
import bw.body.itfget.itfGetFlesh;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.factories.clsRegisterEntity;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.*;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Oct 03, 2012, 10:15:27 PM
 * 
 */
public class clsApple extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable, itfGetBody, itfIsConsumeable {
	private boolean mnDestroyed = false;
	
	public clsApple(String poPrefix, clsProperties poProp, int uid)
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
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 6.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.red);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/Apple2.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 150.0 );
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 5 );
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 500.0);
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.CARBOHYDRATE.name());
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 500.0);
		oProp.setProperty(pre+P_BODY+"."+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_BODY+"."+"2."+clsFlesh.P_NUTRITIONFRACTION, 100.0);
		oProp.setProperty(pre+P_BODY+"."+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.PROTEIN.name());
		oProp.setProperty(pre+P_BODY+"."+"3."+clsFlesh.P_NUTRITIONFRACTION, 500.0);
		oProp.setProperty(pre+P_BODY+"."+"4."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.name());
		oProp.setProperty(pre+P_BODY+"."+"4."+clsFlesh.P_NUTRITIONFRACTION, 200.0);				
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 150);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);		
		oProp.putAll( clsAttributes.getDefaultProperties(pre+P_BODY+"."+clsBaseBody.P_ATTRIBUTES) );
		
		return oProp;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.APPLE;
		
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
		if (getFlesh().getTotallyConsumed() && !mnDestroyed) {
			mnDestroyed = true;
			clsEventLogger.add(new Event(this, getId(), eEvent.CONSUMED, ""));
			clsEventLogger.add(new Event(this, getId(), eEvent.DESTROY, ""));
			//This command removes the APPLE from the playground
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
	@Override
	public clsFlesh getFlesh() {
		return ((clsMeatBody)moBody).getFlesh();
	}
	
	/*
	 * Interface Eatable
	 */
	@Override
	public double tryEat() {
		return 0;
	}
	@Override
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
	@Override
	public clsMobile getCarryableEntity() {
		return this;	
	}
	@Override
	public void setCarriedBindingState(eBindingState pBindingState) {
		//handle binding-state implications 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 17:25:18
	 * 
	 * @see bw.body.itfget.itfGetBody#getBody()
	 */
	@Override
	public clsBaseBody getBody() {
		return moBody;
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