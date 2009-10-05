/**
 * clsSensorBump.java: BW - bw.body.io.sensors.ext
 * 
 * @author zeilinger
 * 30.07.2009, 11:29:42
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;


import ARSsim.physics2D.physicalObject.clsCollidingObject;
import config.clsBWProperties;
import bw.body.clsComplexBody;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
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
	private static clsEntity moHostEntity; 

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
		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 10.0);
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
		return mnBumped;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:29:54
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
	 * 30.07.2009, 11:29:54
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		
		moSensorData.setMeDetectedObjectList(pnAreaRange,  peDetectedObjInAreaList); 
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

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 11:29:54
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		//FIXME HZ In this case it is supposed that the bumpsensor is registered for 
		//mnRange 0 only; 
		if(moSensorData.getMeDetectedObject().get(0)!= null) {
			mnBumped = false;
		}
		else {
			mnBumped = true;
		}
		
		if (mnBumped) {
			if ( ((itfGetBody)moHostEntity).getBody() instanceof clsComplexBody) {
				//TODO calculate true force
				double rForce = 1.0;
				((clsComplexBody)((itfGetBody)moHostEntity).getBody()).getInterBodyWorldSystem().getDamageBump().bumped(eBodyParts.SENSOR_EXT_TACTILE_BUMP, rForce);
			}
		}
	}

}
