/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;
import bw.entities.tools.clsShapeCreator;
import bw.utils.config.clsBWProperties;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPose;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsStationary extends clsEntity {
	public static final String P_DEF_RESTITUTION = "def_restitution";
		
	private double mrDefaultRestitution; 			 //0.5 

	
	public clsStationary(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsEntity.getDefaultProperties(pre) );
		oProp.setProperty(pre+clsPose.P_POS_X, 0.0);
		oProp.setProperty(pre+clsPose.P_POS_Y, 0.0);
		oProp.setProperty(pre+clsPose.P_POS_ANGLE, 0.0);
				
		oProp.setProperty(pre+P_STRUCTURALWEIGHT , java.lang.Double.MAX_VALUE);
		oProp.setProperty(pre+P_DEF_RESTITUTION , 1.0);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		mrDefaultRestitution = poProp.getPropertyDouble(pre+P_DEF_RESTITUTION);
		
		double oPosX = poProp.getPropertyDouble(pre+clsPose.P_POS_X);
		double oPosY = poProp.getPropertyDouble(pre+clsPose.P_POS_Y);
		double oPosAngle = poProp.getPropertyDouble(pre+clsPose.P_POS_ANGLE);
		
		Shape oShape = clsShapeCreator.createShape(pre+P_SHAPE, poProp); //depends on the config
		initPhysicalObject2D(new clsPose(oPosX, oPosY, oPosAngle), new Double2D(0.0,0.0), oShape, getTotalWeight());
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
		//int i = 0; 
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
