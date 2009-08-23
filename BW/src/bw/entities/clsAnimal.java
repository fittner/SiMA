/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;

import du.utils.enums.eDecisionType;


import bw.body.itfget.itfGetSensorEngine;
import bw.body.itfget.itfGetRadiation;
import bw.entities.tools.clsShapeCreator;
import bw.utils.enums.eShapeType;
import enums.eEntityType;

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

public class clsAnimal extends clsAnimate implements itfGetRadiation, itfGetSensorEngine{

	private boolean mnAlive;
	
	public clsAnimal(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp );
		applyProperties(poPrefix, poProp);
		setAlive(true);
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		//add additional fields here
	}
	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );

		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.DUMB_MIND_A.name());
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, "10.0");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.blue);
		
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
		this.mnAlive = mnAlive;
	}

	/**
	 * @author deutsch
	 * 08.07.2009, 15:05:52
	 * 
	 * @return the mnAlive
	 */
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

}
