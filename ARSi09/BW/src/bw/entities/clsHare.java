/**
 * @author deutsch
 * 12.05.2009, 19:30:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;
import sim.physics2D.shape.Shape;
import du.utils.enums.eDecisionType;
import enums.eEntityType;
//import sim.display.clsKeyListener;
//import simple.remotecontrol.clsRemoteControl;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsStomachSystem;
import bw.body.io.actuators.actionProxies.itfAPAttackableBite;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.io.actuators.actionProxies.itfAPAttackableLightning;
import bw.body.itfget.itfIsConsumeable;
import bw.body.itfget.itfGetFlesh;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;
import bw.utils.tools.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:30:22
 * 
 */
public class clsHare extends clsAnimal implements itfGetFlesh, itfAPEatable, itfAPAttackableLightning, itfAPAttackableBite, itfIsConsumeable {
	public static final String P_SHAPE_ALIVE		= "shape_alive";
	public static final String P_SHAPE_DEAD 		= "shape_dead";
	public static final String P_SHAPE_DEADANDEATEN 		= "shape_deadandeaten";
	
	//private Shape moAlive; //reactivate in case of resurrection
	private Shape moDead;
	private Shape moDeadAndEaten;
	
	public clsHare(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp );
		applyProperties(poPrefix, poProp);
		updateShape();
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		//moAlive = clsShapeCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_ALIVE, poProp); //reactivate in case of resurrection
		moDead = clsShapeCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_DEAD, poProp);		
		moDeadAndEaten = clsShapeCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_DEADANDEATEN, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimal.getDefaultProperties(pre) );
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.HARE_IFTHENELSE.name());

		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1000.0);		
		
		//FIXME (deutsch) - .4. is not guaranteed - has to be changed!
		oProp.setProperty(pre+"body.externalio.sensors.4.offset", 8);
		oProp.setProperty(pre+"body.externalio.sensors.4.sensor_range", 2);
		oProp.setProperty(pre+"body.externalio.sensors.2.sensor_range", 30.0);
		oProp.setProperty(pre+"body.externalio.sensors.3.sensor_range", 30.0);

		//change stomach to desired values
		String stomach_pre = pre+clsAnimate.P_BODY+"."+clsComplexBody.P_INTERNAL+"."+clsInternalSystem.P_STOMACH+".";
		oProp.removeKeysStartingWith(stomach_pre);

		int i = 0;
		
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONTYPE, eNutritions.FAT.toString());
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONMETABOLISMFACTOR, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0.5);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 3);
		i++;

		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONTYPE, eNutritions.WATER.toString());
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONEFFICIENCY, 0);
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONMETABOLISMFACTOR, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0.5);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 3);
		i++;
		
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONEFFICIENCY, 0);
		oProp.setProperty(stomach_pre+i+"."+clsStomachSystem.P_NUTRITIONMETABOLISMFACTOR, 0);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );	
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 0);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0);
		
		i++;
		
		oProp.setProperty(stomach_pre+clsStomachSystem.P_NUMNUTRITIONS, i);
		
		//change flesh to desired values
		String flesh_pre = pre+clsAnimate.P_BODY+"."+clsComplexBody.P_INTERNAL+"."+clsInternalSystem.P_FLESH+".";
		oProp.removeKeysStartingWith(flesh_pre);		
		
		oProp.setProperty(flesh_pre+clsFlesh.P_WEIGHT, 5.0 );
		oProp.setProperty(flesh_pre+clsFlesh.P_NUMNUTRITIONS, 4 );
		oProp.setProperty(flesh_pre+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.PROTEIN.name());
		oProp.setProperty(flesh_pre+"0."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(flesh_pre+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(flesh_pre+"1."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(flesh_pre+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(flesh_pre+"2."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(flesh_pre+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(flesh_pre+"3."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		
		//set shape
		oProp.removeKeysStartingWith(pre+P_SHAPE);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPE_ALIVE);
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShapeCreator.P_RADIUS, 2.5);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShapeCreator.P_COLOR, Color.LIGHT_GRAY);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/hase_grey.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShapeCreator.P_RADIUS, 2.5);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShapeCreator.P_COLOR, Color.RED);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/hase_red.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
				
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEADANDEATEN+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEADANDEATEN+"."+clsShapeCreator.P_RADIUS, 2.5);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEADANDEATEN+"."+clsShapeCreator.P_COLOR, Color.BLACK);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEADANDEATEN+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/hase.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEADANDEATEN+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		return oProp;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.HARE;
	}
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 27.07.2009, 14:58    adapted by zeilinger from 
	 * 						clsComplexBody to clsMeatBody 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	public clsFlesh getFlesh() {
		clsFlesh oResult = ((clsComplexBody)moBody).getInternalSystem().getFlesh();
		return oResult;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#Eat(float)
	 */
	public clsFood Eat(double prBiteSize) {
		clsFood oResult = getFlesh().withdraw(prBiteSize);
		updateShape();
		return oResult;
	}
	



	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:17:57
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKillable#kill(float)
	 */
	public void bite(double pfForce) {
		setAlive(false);
		updateShape();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @author Benny D�nz
	 * 30.08.2009, 14:00:14
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKillable#kill(double)
	 */
	public void attackLightning(double pfForce) {
	}



	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:17:57
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKillable#tryKill(float)
	 */
	public double tryBite(double pfForce) {
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#tryEat()
	 */
	public double tryEat() {
		if (!isAlive()){
  		  return 0;
		} else {
		  return 1.0f;
		}
	}
	
	private void updateShape() {
		if ( !isAlive() && getFlesh().getTotallyConsumed() ) {
			//This command removes the cake from the playground
			//clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
			setShape(moDeadAndEaten, getTotalWeight());
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		} else if (!isAlive()) {
			setShape(moDead, getTotalWeight());
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		}		
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
//	    ((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		super.processing();
		updateShape();
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
		super.updateInternalState();
		if (((clsComplexBody)moBody).getInternalSystem().getHealthSystem().getHealth().getContent() < 0.0001) {
			setAlive(false);
		}
		updateShape();
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