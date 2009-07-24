/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import du.utils.enums.eDecisionType;

import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetVision;
import bw.utils.config.clsBWProperties;
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
public class clsAnimal extends clsAnimate implements itfGetVision, itfGetEatableArea{

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
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.DUMB_MIND_A.name());
		
		oProp.setProperty(pre+P_SHAPE_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE_RADIUS, "10.0");
		oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.blue);
		
//		oProp.setProperty(pre+P_MOBILE_SPEED, "4.0" );
//		oProp.setProperty(pre+P_ENTITY_WEIGHT, "300.0" ); //TODO: (creator) is this for the mass???

		return oProp;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
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
