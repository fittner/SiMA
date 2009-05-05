/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;


import decisionunit.clsBaseDecisionUnit;
import sim.physics2D.shape.Shape;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsAgentBody;
import bw.body.motionplatform.clsBrainActionContainer;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile{

	public clsAgentBody moAgentBody; // the instance of a body
	protected clsBrainActionContainer moActionList;
	
	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsAnimate(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double poMass) {
		super(pnId, poPose, poStartingVelocity, poShape, poMass);
		
		moAgentBody = new clsAgentBody(this);
		moActionList = new clsBrainActionContainer();
	}
	
	
	/**
	 * @author langr
	 * 20.02.2009, 11:40:14
	 * 
	 * @return the moAgentBody
	 */
	public clsAgentBody getAgentBody() {
		return moAgentBody;
	}
	
	public void setDecisionUnit(clsBaseDecisionUnit poDecisionUnit) {
		moAgentBody.getBrain().setDecisionUnit(poDecisionUnit);
	}
	
	public void sensing() {
		getAgentBody().stepSensing();
		
	}
	
	public void execution() {
		getAgentBody().stepExecution(moActionList);
	}
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:33:53
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		getAgentBody().stepUpdateInternalState();
		
	}	

}
