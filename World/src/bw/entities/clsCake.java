/**
 * @author muchitsch
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
import bw.utils.enums.eShapeType;
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
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.*;
import sim.physics2D.shape.Shape;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * Jul 24, 2009, 10:15:27 PM
 * 
 */
public class clsCake extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable, itfGetBody, itfIsConsumeable {
	public static final String CONFIG_FILE_NAME ="cake.default.properties";
	
	private boolean mnDestroyed = false;
	
	public static final String P_SHAPE_75 		= "shape_75";
	public static final String P_SHAPE_50 		= "shape_50";
	public static final String P_SHAPE_25 		= "shape_25";
	
	private Shape moShape75;
	private Shape moShape50;
	private Shape moShape25;
	
	public clsCake(String poPrefix, clsProperties poProp, int uid)
    {
		super(poPrefix, poProp, uid);		
		applyProperties(poPrefix, poProp);
    } 
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
//		String pre = clsProperties.addDot(poPrefix);
		
		moShape75 = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_75, poProp);
		moShape50 = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_50, poProp);
		moShape25 = clsShape2DCreator.createShape(poPrefix+P_SHAPE+"."+P_SHAPE_25, poProp);
		
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
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/schnitzl.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());	
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_75+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_75+"."+clsShape2DCreator.P_RADIUS, 6.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_75+"."+clsShape2DCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_75+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/schnitzl75.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_75+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_50+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_50+"."+clsShape2DCreator.P_RADIUS, 8.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_50+"."+clsShape2DCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_50+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/schnitzl50.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_50+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_25+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_25+"."+clsShape2DCreator.P_RADIUS, 8.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_25+"."+clsShape2DCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_25+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/schnitzl25.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_25+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 150.0 );
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_LIBIDINOUS_STIMULATION, 0.07);
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_AGGRESSIV_STIMULATION, 0.03);
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 8 );
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
		oProp.setProperty(pre+P_BODY+"."+"5."+clsFlesh.P_NUTRITIONTYPE, eNutritions.MINERAL.name());
		oProp.setProperty(pre+P_BODY+"."+"5."+clsFlesh.P_NUTRITIONFRACTION, 500.0);	
		oProp.setProperty(pre+P_BODY+"."+"6."+clsFlesh.P_NUTRITIONTYPE, eNutritions.TRACEELEMENT.name());
		oProp.setProperty(pre+P_BODY+"."+"6."+clsFlesh.P_NUTRITIONFRACTION, 500.0);	
		oProp.setProperty(pre+P_BODY+"."+"7."+clsFlesh.P_NUTRITIONTYPE, eNutritions.VITAMIN.name());
		oProp.setProperty(pre+P_BODY+"."+"7."+clsFlesh.P_NUTRITIONFRACTION, 100.0);	
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
		
		updateShape();
		
		if (getFlesh().getTotallyConsumed() && !mnDestroyed) {
			mnDestroyed = true;
			clsEventLogger.add(new Event(this, getId(), eEvent.CONSUMED, ""));
			clsEventLogger.add(new Event(this, getId(), eEvent.DESTROY, ""));
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}
	
	private void updateShape() {
		
		
		if (this.moBody.getBodyIntegrity() < 0.25) {
			//do nothing, will be eaten soon
		}
		else if (this.moBody.getBodyIntegrity() < 0.50) {
			//25%
			set2DShape(moShape25, getTotalWeight());	
		}
		else if (this.moBody.getBodyIntegrity() < 0.75) {
			//50%
			set2DShape(moShape50, getTotalWeight());	
		}
		else if (this.moBody.getBodyIntegrity() < 1.0) {
			//100-75%
			set2DShape(moShape75, getTotalWeight());	
		}
		else{
			// = 100% do nothing
		}
//			// state has changed recently to no_food_left
//			// update shape to the gray carrot
//			clsEventLogger.add(new Event(this, getId(), eEvent.CONSUMED, ""));
//			mnShapeUpdated = true;
//			set2DShape(moDead2D, getTotalWeight());		
//		} else if (!getFlesh().getTotallyConsumed() && !mnShapeUpdated) {
//			// state has changed recently to food_available
//			// update shape to the orange carrot
//			clsEventLogger.add(new Event(this, getId(), eEvent.RESPAWN, ""));
//			mnShapeUpdated = true;
//			set2DShape(moFresh2D, getTotalWeight());
//		}		
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
