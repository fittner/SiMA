/**
 * @author zeilinger
 * 14.07.2009, 08:01:45
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;
import bw.body.io.sensors.ext.clsSensorExt;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 14.07.2009, 08:01:45
 * 
 */
public class clsSensorVisionNEW extends clsSensorExt {
	public static final String P_ANGLE = "angle";
	public static final String P_RANGE = "range";
	public static final String P_OFFSET = "offset";	
	
	private Double mnRange;
	private Double2D moPosition; 
	
	public clsSensorVisionNEW(String poPrefix, clsBWProperties poProp, clsEntity poEntity, clsBaseIO poBaseIO, clsSensorEngine poSensorEngine) {
		super(poPrefix, poProp, poBaseIO, poSensorEngine);
		moPosition = poEntity.getPosition(); 
		applyProperties(poPrefix, poProp);
	}
	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_ANGLE, Math.PI );
		oProp.setProperty(pre+P_RANGE, 60 );
		oProp.setProperty(pre+P_OFFSET, 0 );		
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		//mnViewRad = poProp.getPropertyDouble(pre+P_ANGLE);
		mnRange = poProp.getPropertyDouble(pre+P_RANGE);
		//mnVisOffset = poProp.getPropertyDouble(pre+P_OFFSET);
		
		//HZ -- initialise sensor engine - defines the maximum sensor range
		assignSensorData((clsSensorExt)this,moPosition, mnRange);			
	}
	
	
		
//	public ArrayList<PhysicalObject2D> getSensorData(){
//		/*has to be implemented - return SensorData to Decision Unit*/
//		return null; 
//	}
    
	@Override
	public void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj) {
		// TODO Auto-generated method stub
		
		//System.out.println("Range " + pnRange + "  " + peObj.size());
		
		moSensorData.setMeDetectedObject(pnRange, peObj); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.07.2009, 12:48:42
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.util.ArrayList)
	 * Integrate Sensor Engine - new Sensor Ext
	 */
	
	
	@Override
	protected void setBodyPartId() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void setName() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.07.2009, 23:26:09
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub
	}
}
