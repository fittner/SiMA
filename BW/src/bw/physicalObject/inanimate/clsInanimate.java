/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate;

import java.awt.Color;

import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import ARSsim.robot2D.clsMotionPlatform;
import bw.clsEntity;
import bw.body.clsAgentBody;
import bw.physicalObject.inanimate.mobile.clsMobile;


/**
 * Inanimates represent dead objects (cannot grow, move, ...)
 * 
 * @author langr
 * 
 */
public abstract class clsInanimate extends clsMobile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8003531210919357291L;
	private int mnId;
	
	public int getId() {	return mnId;	}
	public void setId(int pnId) {		this.mnId = pnId;	}
	

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsInanimate(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId) {
		super(poStartingPosition, poStartingVelocity, new sim.physics2D.shape.Circle(10, Color.ORANGE), 300);
		
		//MobileObject2D oMobile = getMobile();
	}
	
	
//	public clsInanimate(Double2D poPos, double pnRadius, double pnMass)
//    {
//		super();
//		initDefault(poPos, pnRadius, pnMass);
//    } 
	
	/**
	 * TODO (langr) - standard inanimate deinition
	 *
	 */
	private void initDefault(Double2D poPos, double pnRadius, double pnMass)
	{
		MobileObject2D oMobile = getMobile();
		
		oMobile.setPose(poPos, new Angle(0));
		oMobile.setVelocity( new Double2D() );
		oMobile.setCoefficientOfFriction(.5);
		oMobile.setCoefficientOfStaticFriction(0);
		oMobile.setCoefficientOfRestitution(1);
		oMobile.setShape(new ARSsim.physics2D.shape.clsCircleImage(pnRadius, Color.darkGray), pnMass);
	}
	
}
