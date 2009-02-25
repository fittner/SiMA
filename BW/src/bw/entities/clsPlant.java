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

import bw.body.motionplatform.clsBrainAction;
import bw.utils.enums.eEntityType;
import sim.physics2D.util.Double2D;

/**
 * An instance of the mobile object clsAnimate that can:
 * - grow
 * - be harvest
 * - withdrawn
 * - be eaten  
 * 
 * @author langr
 * 
 */
public class clsPlant extends clsAnimate{

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsPlant(Double2D poStartingPosition, Double2D poStartingVelocity,
			int pnId) {
		super(poStartingPosition, poStartingVelocity, new sim.physics2D.shape.Circle(10, Color.CYAN), pnId);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7055381541963741660L;

	/* (non-Javadoc)
	 * @see bw.clsEntity#getEntityType()
	 */
	@Override
	public eEntityType getEntityType() {
		return eEntityType.PLANT;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#thinking()
	 */
	@Override
	public void thinking() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}

}
