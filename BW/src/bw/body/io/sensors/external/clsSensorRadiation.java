/**
 * @author horvath
 * 20.07.2009, 14:43:49
 */
package bw.body.io.sensors.external;

import java.util.Iterator;

import config.clsBWProperties;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import enums.eEntityType;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyParts;
import bw. entities.clsUraniumOre;

/**
 * 
 * (horvath) - radiation sensor ; based on the Eatable Area Sensor 
 * 
 * @author horvath
 * 20.07.2009, 14:43:49
 *
 */
@Deprecated
public class clsSensorRadiation extends clsSensorVision {
		
	private double mrRadiation;
	
	/*
	 * @param poEntity 
	 *
	 * @param poBaseIO
	 * @param 
	 */
	public clsSensorRadiation(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsEntity poEntity)	{
		super(poPrefix, poProp, poBaseIO, poEntity);	
		
		mrRadiation = 0;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsSensorVision.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_SENSOR_ANGLE, (2.0 *  Math.PI) );
		oProp.setProperty(pre+P_SENSOR_RANGE, 80 );
		oProp.setProperty(pre+P_SENSOR_OFFSET, 0 );		
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		// nothing to do
	}	


	
	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_RADIATION;

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Radiation";

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		double rDistance;
		
		// clear radiation information before new value calculation
		mrRadiation = 0;
		
		
		// done by vision base
		super.updateSensorData();
		Iterator<Integer> i = getViewObj().keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			if (getEntityType(getViewObj().get(oKey)) == eEntityType.URANIUM) {
				if(getEntity(getViewObj().get(oKey)).isRegistered()){
					
					// calculate distance between uranium ore and the sensor 
					rDistance = getDistance(getEntity(getViewObj().get(oKey)).getPosition());
					
					// calculate radiation contribution of the selected uranium ore 
					mrRadiation = mrRadiation + ((clsUraniumOre)getEntity(getViewObj().get(oKey))).mrRadiationIntensity / Math.pow(rDistance, 2);					
				}
			}
		}
		super.moVisionArea.setMrIntensity(mrRadiation);
	}
	
	
	private  enums.eEntityType getEntityType(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		
		if (oEntity != null) {
		  return getEntity(poObject).getEntityType();
		} else {
			return enums.eEntityType.UNDEFINED;
		}
	}
	
	private clsEntity getEntity(PhysicalObject2D poObject) {
		clsEntity oResult = null;
		
		if (poObject instanceof clsMobileObject2D) {
			oResult = ((clsMobileObject2D) poObject).getEntity();
		} else if (poObject instanceof clsStationaryObject2D) {
			oResult = ((clsStationaryObject2D) poObject).getEntity();
		}	
		
		return oResult;
	}
	
	
	// returns distance of the sensor (bubble) from an entity 
	private double getDistance(Double2D poEntity){
		
		double rDistance = Math.pow(Math.abs(poEntity.x - moEntity.getPosition().x),2) 
						+ Math.pow(Math.abs(poEntity.y - moEntity.getPosition().y),2);
		rDistance = Math.sqrt(rDistance);
		
		return rDistance;
	}
	
	
	// returns measured radiation intensity
	public double getMrRadiation(){
		return mrRadiation;		
	}

}
