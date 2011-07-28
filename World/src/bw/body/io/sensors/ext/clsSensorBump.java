/**
 * clsSensorBump.java: BW - bw.body.io.sensors.ext
 * 
 * @author zeilinger
 * 30.07.2009, 11:29:42
 */
package bw.body.io.sensors.ext;

import config.clsBWProperties;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.utils.enums.eBodyParts;

import sim.physics2D.util.Double2D;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.07.2009, 11:29:42
 * 
 */

public class clsSensorBump extends clsSensorRingSegment{
	public static final String P_BUMPED = "bumped"; 
	private boolean mnBumped;
	//private static clsEntity moHostEntity;  //never used!

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 30.07.2009, 11:29:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	public clsSensorBump(String poPrefix, clsBWProperties poProp,
			clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		oProp.setProperty(pre+P_BUMPED, false );
		
		//FIXME HZ - min and max distance should be variable and adapt themselves to the 
		//			 shape size of the entity
		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 0.0);
		oProp.setProperty(pre+P_SENSOR_MAX_DISTANCE, 11.0);
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
													poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
		mnBumped = poProp.getPropertyBoolean(pre+P_BUMPED);
		assignSensorData(oOffset, nRange, nFieldOfView);		
	}
	
	/**
	 * @author langr
	 * 20.02.2009, 13:09:07
	 * 
	 * @param mnBumped the mnBumped to set
	 */
	public void setBumped(boolean mnBumped) {
		this.mnBumped = mnBumped;
	}
	
	/**
	 * @author langr
	 * returns true when collision with other object(s)	 * 
	 * @return the mnBumped
	 */
	public boolean isBumped() {
		
		setBumpStatus();
	
		return mnBumped;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:29:54
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_TACTILE_BUMP;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:29:54
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Bumpsensor";
	}

	
	public void setBumpStatus() {
		//FIXME HZ In this case it is supposed that the bumpsensor is registered for 
		//mnRange 0 only; 
		if(moSensorData.getMeDetectedObject().get(20.0) != null && moSensorData.getMeDetectedObject().get(20.0).size()> 0) {
			mnBumped = true;
		}
		else {
			mnBumped = false;
		}
		
		//if ( ((itfGetBody)moHostEntity).getBody() instanceof clsComplexBody) {
			//TODO calculate true force
//			double rForce = 1.0;
//			((clsComplexBody)((itfGetBody)moHostEntity).getBody()).getInterBodyWorldSystem().getDamageBump().bumped(eBodyParts.SENSOR_EXT_TACTILE_BUMP, rForce);
		//}
	}		
}
