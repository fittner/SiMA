/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.ArrayList;

import ARSsim.physics2D.util.clsPose;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.utils.container.clsConfigMap;
import enums.eEntityType;
import sim.physics2D.util.Double2D;


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
public class clsAnimal extends clsAnimate{
	private static double mrDefaultWeight = 300.0f;
	private static double mrDefaultRadius = 10.0f;
	private static Color moDefaultColor = Color.BLUE;

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsAnimal(int pnId, clsPose poPose, Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poPose, poStartingVelocity, new sim.physics2D.shape.Circle(clsAnimal.mrDefaultRadius, clsAnimal.moDefaultColor), clsAnimal.mrDefaultWeight, poConfig);
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		meEntityType = eEntityType.ANIMAL;
		
	}
	


	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:31:48
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.04.2009, 16:53:08
	 * 
	 * @see bw.entities.clsEntity#processing()
	 */
	@Override
	public void processing() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 17:47:23
	 * 
	 * @see bw.entities.clsEntity#getDefaultConfig()
	 */
	@Override
	protected clsConfigMap getDefaultConfig() {
		// TODO Auto-generated method stub
		clsConfigMap oDefault = super.getDefaultConfig();
	
		
		return oDefault;
	}
	
	
}
