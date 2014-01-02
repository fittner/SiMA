/**
 * CHANGELOG
 *
 * 20.09.2012 muchitsch - File created
 *
 */
package du.itf.sensors;



/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 20.09.2012, 11:41:59
 * 
 */
public class clsInspectorPerceptionItem {
	
	public String moContent;
	public String moContentType;
	
	public String moPosition;
	public String moDistance;
	public double moExactX;
	public double moExactY;
	public double moSensorArousal;
	

	public String getContentType() {
		return moContentType;
	}
	
	public String getContent() {
		return moContent;
	}
}
