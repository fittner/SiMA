/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import config.clsProperties;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;
import bw.entities.tools.clsShapeCreator;
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

	
	public clsStationary(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.putAll( clsEntity.getDefaultProperties(pre) );
		oProp.setProperty(pre+clsPose.P_POS_X, 0.0);
		oProp.setProperty(pre+clsPose.P_POS_Y, 0.0);
		oProp.setProperty(pre+clsPose.P_POS_ANGLE, 0.0);
				
		oProp.setProperty(pre+P_STRUCTURALWEIGHT , java.lang.Double.MAX_VALUE);
		oProp.setProperty(pre+P_DEF_RESTITUTION , 1.0);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		mrDefaultRestitution = poProp.getPropertyDouble(pre+P_DEF_RESTITUTION);
		
		double oPosX = poProp.getPropertyDouble(pre+clsPose.P_POS_X);
		double oPosY = poProp.getPropertyDouble(pre+clsPose.P_POS_Y);
		double oPosAngle = poProp.getPropertyDouble(pre+clsPose.P_POS_ANGLE);
		
		String oDefaultShape = poProp.getPropertyString(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE);
		Shape oShape = clsShapeCreator.createShape(pre+P_SHAPE+"."+oDefaultShape, poProp);
		initPhysicalObject2D(new clsPose(oPosX, oPosY, oPosAngle), new Double2D(0.0,0.0), oShape, getTotalWeight());
		
		setBody( createBody(pre, poProp) ); // has to be called AFTER the shape has been created. thus, moved to clsMobile and clsStationary.
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
		// TODO (zeilinger) - Auto-generated method stub
		
	}	
	
}
