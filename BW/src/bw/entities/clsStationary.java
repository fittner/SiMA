/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.shape.Shape;
import bw.utils.container.clsConfigMap;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPose;



/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsStationary extends clsEntity {
	private static double mrDefaultStationaryWeight = 9999.0;
	private double mrDefaultRestitution = 0.5; //0.5 

	public clsStationary(int pnId, clsPose poPose, Shape poShape, clsConfigMap poConfig) {
		super(pnId, clsStationary.getFinalConfig(poConfig));
		
		applyConfig();
		
		initPhysicalObject2D(poPose, null, poShape, mrDefaultStationaryWeight);
	}
	
	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}
	
	@Override
	protected void initPhysicalObject2D(clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double prMass) {
		moPhysicalObject2D = new clsStationaryObject2D(this);
		
		setPose(poPose);
		setShape(poShape, prMass);
		setCoefficients(Double.NaN, Double.NaN, mrDefaultRestitution);  // First two coeffs ignored for stationary objects, use NaN
	}
	
	public clsStationaryObject2D getStationaryObject2D() {
		return (clsStationaryObject2D)moPhysicalObject2D;
	}
		
	@Override
	public sim.physics2D.util.Double2D getPosition() {
		return getStationaryObject2D().getPosition();
	}	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// DO NOTHING - stationary objects are inanimate per design ...
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		int i = 0; 
		// DO NOTHING - stationary objects are inanimate per design ...	
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:37
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		// DO NOTHING - stationary objects are inanimate per design ...		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:37
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}	
	
}
