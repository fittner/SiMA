/**
 * CHANGELOG
 *
 * 16.05.2012 muchitsch - File created
 *
 */
package complexbody.io.sensors.datatypes;

import complexbody.io.sensors.datatypes.enums.eEntityType;

/**
 * Only used for Unreal external vision entries, do not tinker the real vision!
 * 
 * @author muchitsch
 * 16.05.2012, 15:24:39
 * 
 */
public class clsUnrealSensorValueVision {
	
	/**
	 * DOCUMENT (muchitsch) - insert description 
	 *
	 * @since 16.05.2012 16:01:47
	 *
	 * @param angle
	 * @param radius
	 * @param iD
	 * @param type
	 */
	public clsUnrealSensorValueVision(double angle, double radius, String iD,
			eEntityType type) {
		super();
		this.angle = angle;
		this.radius = radius;
		ID = iD;
		this.type = type;
	}
	
	public clsUnrealSensorValueVision(){
		super();
		//empty CTOR
	}


	private double angle = 0;
	private double radius = 0;
	private String ID = "";
	private eEntityType type;
	

	@Override
	public String toString() {
		return ID+" A:"+angle+" R:"+radius;
	}
	
	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @return the type
	 */
	public eEntityType getType() {
		return type;
	}


	/**
	 * @since 16.05.2012 15:30:02
	 * 
	 * @param type the type to set
	 */
	public void setType(eEntityType type) {
		this.type = type;
	}
}
