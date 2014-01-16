/**
 * @author schaat
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;


import java.awt.Color;

import properties.clsProperties;

import complexbody.internalSystems.clsFlesh;

import registration.clsRegisterEntity;
import tools.clsPose;
import tools.eImagePositioning;
import utils.clsGetARSPath;

import du.enums.eEntityType;
import entities.abstractEntities.clsAnimate;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsInanimate;
import entities.abstractEntities.clsMobile;
import entities.abstractEntities.clsOrganic;
import entities.actionProxies.*;
import entities.enums.eBindingState;
import entities.enums.eNutritions;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;
import body.clsBaseBody;
import body.clsMeatBody;
import body.attributes.clsAttributes;
import body.itfget.itfGetBody;
import body.itfget.itfGetFlesh;
import body.itfget.itfIsConsumeable;
import body.utils.clsFood;


//import bw.utils.inspectors.entity.clsInspectorBasic;


/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Oct 03, 2012, 10:15:27 PM
 * 
 */
public class clsApple extends clsOrganic implements itfGetFlesh, itfAPEatable, itfAPCarryable, itfGetBody, itfIsConsumeable {
	
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
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 6.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.red);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "Apple2.png");
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
			//This command removes the APPLE from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
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
	 * 23.09.2009, 11:36:06
	 * 
	 * @see bw.body.itfget.itfGetConsumeable#isConsumable()
	 */
	@Override
	public boolean isConsumable() {
		return getFlesh().getTotallyConsumed();
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 11, 2012 4:22:26 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.inspector.TabbedInspector, sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Apple");
		
	}*/
	
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsEntityFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
		double x = this.getPose().getPosition().x;
		double y = this.getPose().getPosition().y;
		double angle = this.getPose().getAngle().radians;
		double weight = this.getVariableWeight();
		
		//set position
		oNewEntity.setPose(new clsPose(x-(poDistance/2), y, angle));
		this.setPose(new clsPose(x+(poDistance/2), y, angle));
		//set weight
		oNewEntity.setVariableWeight(weight*poSplitFactor);
		this.setVariableWeight(weight*(1-poSplitFactor));
		
		return oNewEntity;

	}

	
}