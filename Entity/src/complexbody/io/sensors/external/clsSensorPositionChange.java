/**
 * clsSensorPositionChange.java: BW - bw.body.io.sensors.ext
 * 
 * @author zeilinger
 * 30.07.2009, 10:59:42
 */
package complexbody.io.sensors.external;

import java.util.ArrayList;

import physics2D.physicalObject.clsCollidingObject;
import properties.clsProperties;
import sim.physics2D.util.Double2D;
import tools.clsPose;

import complexbody.io.clsBaseIO;
import complexbody.io.clsExternalIO;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.07.2009, 10:59:42
 * 
 */
public class clsSensorPositionChange extends clsSensorExt{

	private clsPose moLastPos;
	private clsPose moDiv;
	private clsEntity moEntity;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 30.07.2009, 11:00:03
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	public clsSensorPositionChange(String poPrefix, clsProperties poProp,
			clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		moEntity = ((clsExternalIO)poBaseIO).moEntity;
		moLastPos = new clsPose(moEntity.getPose());
		moDiv = new clsPose(0,0,0);
		applyProperties(poPrefix, poProp);
		// TODO (zeilinger) - Auto-generated constructor stub
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
										 poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
	
		//HZ -- initialise sensor engine - defines the maximum sensor range
		assignSensorData(oOffset, nRange, nFieldOfView);	
	}		
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:00:01
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		clsPose oCurrentPose = new clsPose(moEntity.getPose());
		moDiv = new clsPose(oCurrentPose);
		moDiv.subtract(moLastPos);
		moLastPos.setPose(oCurrentPose);
	}
	
	public clsPose getPositionChange() {return moDiv;}
	
	// FIXME (horvath) - returns last position
	public clsPose getLastPosition() {return moLastPos;}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:00:01
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<clsCollidingObject> peObjInAreaList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:00:01
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:00:01
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_POSITIONCHANGE;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:00:01
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Position Change";
	}

}
