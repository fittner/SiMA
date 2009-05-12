/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.utils.container.clsConfigFloat;
import bw.utils.container.clsConfigInt;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
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
	private static double mrWeight;
	private static double mrRadius;
	private static Color moColor;
	private static double mrSpeed;

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsAnimal(int pnId, clsPose poPose, Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poPose, poStartingVelocity, new sim.physics2D.shape.Circle(clsAnimal.mrRadius, clsAnimal.moColor), clsAnimal.mrWeight, clsAnimal.getFinalConfig(poConfig));
		applyConfig();
		// TODO Auto-generated constructor stub
	}

	public clsBaseBody createBody() {
		return  new clsComplexBody(this, moConfig);
	}
		
	private void applyConfig() {
		mrWeight = ( (clsConfigFloat)moConfig.get(eConfigEntries.WEIGHT) ).get();
		mrRadius = ( (clsConfigFloat)moConfig.get(eConfigEntries.RADIUS) ).get();
		mrSpeed = ( (clsConfigFloat)moConfig.get(eConfigEntries.SPEED) ).get();
		moColor = new Color( ( (clsConfigInt)moConfig.get(eConfigEntries.COLOR) ).get() );
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.SPEED, new clsConfigFloat(4.0f));
		oDefault.add(eConfigEntries.WEIGHT, new clsConfigFloat(300.0f));
		oDefault.add(eConfigEntries.RADIUS, new clsConfigFloat(10.0f));
		oDefault.add(eConfigEntries.COLOR, new clsConfigInt( Color.BLUE.getRGB() ));

		return oDefault;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		meEntityType = eEntityType.ANIMAL;
	}


}
