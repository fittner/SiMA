/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities.base;

import java.awt.Color;

import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;
import config.clsProperties;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import ARSsim.physics2D.util.clsPose;
import bw.ARSIN.factory.clsARSINFactory;
import bw.body.itfget.itfIsAlive;
import bw.body.itfget.itfGetSensorEngine;
import bw.body.itfget.itfGetRadiation;
import bw.entities.tools.clsShape2DCreator;
import bw.utils.enums.eShapeType;
//import bw.utils.inspectors.entity.clsInspectorBasic;

/**
 * Preliminary simple moving entities with the 'ability' to be eaten.
 * TODO (langr) - update the following sentence
 * The clsAgentBody shall contain an instance of clsFlesh that can be eaten
 *
 * Other instances of clsAnimals shall be able to eat other agents to act
 * as a threat. Classification into herbivores and carnivores
 * 
 * @author langr
 * 
 */

public abstract class clsAnimal extends clsAnimate implements itfGetRadiation, itfGetSensorEngine, itfIsAlive {
	private boolean mnAlive;
	
	public clsAnimal(itfDecisionUnit poDU, String poPrefix, clsProperties poProp, int uid) {
		super(poDU, poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
		setAlive(true);
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		//add additional fields here
	}
	
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		


  		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);

		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, "10.0");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.blue);
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0 );

		return oProp;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.ANIMAL;
	}

	/**
	 * @author deutsch
	 * 08.07.2009, 15:05:52
	 * 
	 * @param mnAlive the mnAlive to set
	 */
	public void setAlive(boolean mnAlive) {
		if (this.mnAlive != mnAlive) {
			clsEventLogger.add(new Event(this, getId(), eEvent.ALIVE, ""+mnAlive));
		}
		this.mnAlive = mnAlive;
	}

	/**
	 * @author deutsch
	 * 08.07.2009, 15:05:52
	 * 
	 * @return the mnAlive
	 */
	@Override
	public boolean isAlive() {
		return mnAlive;
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
		if (isAlive()) {
			super.execution();
		}
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
		if (isAlive()) {
			super.processing();
		}
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
		if (isAlive()) {
			super.sensing();
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
		if (isAlive()) {
			super.updateInternalState();
		}
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 11, 2012 4:22:13 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.inspector.TabbedInspector, sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity), "Animal");
		
	}*/
	
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsARSINFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
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
