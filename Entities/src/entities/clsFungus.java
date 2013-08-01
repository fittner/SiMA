/**
 * @author muchitsch, horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;

import statictools.clsGetARSPath;
//import sim.display.GUIState;
//import sim.portrayal.Inspector;
//import sim.portrayal.LocationWrapper;
//import sim.portrayal.inspector.TabbedInspector;
import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;

import config.clsProperties;
import du.enums.eEntityType;
import entities.factory.clsEntityFactory;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfIsConsumeable;
import bw.body.itfget.itfGetFlesh;
import bw.entities.base.clsAnimate;
import bw.entities.base.clsEntity;
import bw.entities.base.clsInanimate;
import bw.entities.base.clsMobile;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.factories.clsRegisterEntity;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
//import bw.utils.inspectors.entity.clsInspectorFungus;
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.io.actuators.actionProxies.itfAPCarryable;


/**
 * 
 * Fungus - an energy source for the Fungus-Eater
 * 
 * TODO (horvath) - no nutritions, just energy 
 *                - no carryable, immobile (?)
 * 
 * @author horvath
 * 08.07.2009, 14:48:08
 * 
 */
public class clsFungus extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable, itfIsConsumeable {
	private boolean mnDestroyed = false;
	
	public clsFungus(String poPrefix, clsProperties poProp, int uid)
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
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 15.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, "6.0");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "fungus.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_WEIGHT, 15.0 );
		oProp.setProperty(pre+P_BODY+"."+clsFlesh.P_NUMNUTRITIONS, 2 );
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(pre+P_BODY+"."+"0."+clsFlesh.P_NUTRITIONFRACTION, 50.0);
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+P_BODY+"."+"1."+clsFlesh.P_NUTRITIONFRACTION, 10.0);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_MAXWEIGHT, 15);
		oProp.setProperty(pre+P_BODY+"."+clsMeatBody.P_REGROWRATE, 0);
		
		return oProp;
	}


	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.FUNGUS ;
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
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		
		setVariableWeight(getFlesh().getWeight());
		
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
	 * @since Dec 11, 2012 4:18:05 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.inspector.TabbedInspector, sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorFungus(poSuperInspector, poWrapper, poState, (clsFungus)poEntity), "Fungus");
		
	}	*/
	
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