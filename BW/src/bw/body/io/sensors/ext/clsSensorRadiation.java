/**
 * @author zeilinger
 * 29.07.2009, 10:22:26
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.clsBWProperties;
import enums.eEntityType;

import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.entities.clsEntity;

//import bw.entities.clsUraniumOre;
import bw.utils.enums.eBodyParts;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 29.07.2009, 10:22:26
 * 
 */
public class clsSensorRadiation extends clsSensorExt {
	private double mrRadiation;
		
	/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 29.07.2009, 10:22:52
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	public clsSensorRadiation(String poPrefix, clsBWProperties poProp,
			clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
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
	 * 29.07.2009, 10:22:42
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<clsCollidingObject> peObjInAreaList) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 29.07.2009, 10:22:42
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList);
		//computeDataDeliveredToDU();
		computeRadiation(); 
	}
	
    public void computeRadiation(){
		double rDistance;
		// clear radiation information before new value calculation
		mrRadiation = 0;
		HashMap<Double, ArrayList<clsCollidingObject>> eDetectedObjectList = moSensorData.getMeDetectedObject();
		
		for(ArrayList<clsCollidingObject> element : eDetectedObjectList.values())
		{
			Iterator <clsCollidingObject> itr = element.iterator(); 
			while(itr.hasNext()){
				clsCollidingObject oCollidingObject = itr.next();  
				clsEntity oEntity = getEntity(oCollidingObject.moCollider); 
				
				if(oEntity.getEntityType() == eEntityType.URANIUM && oEntity.isRegistered()){
//FIXME  this calculation has to be adapted to the actual Radiation calculation
//					rDistance = getDistance(getEntity(getViewObj().get(oKey)).getPosition());
				// calculate radiation contribution of the selected uranium ore 
//					mrRadiation = mrRadiation + ((clsUraniumOre)getEntity(getViewObj().get(oKey))).mrRadiationIntensity / Math.pow(rDistance, 2);	
					
//					rDistance = getDistance(oCollidingObject.mrColPoint);
//					mrRadiation = mrRadiation + ((clsUraniumOre)oEntity).mrRadiationIntensity / Math.pow(rDistance, 2);
//
////	throw new java.lang.NoSuchMethodError();	
//// FIXME (horvath) - getId is unsafe and does not guarantee a unique value! please change code. TD
////					rDistance = getDistance(oEntity.getId());
//
//					//throw new java.lang.NoSuchMethodError();
////					rDistance = getDistance(oEntity.getUniqueId());
//
//					// calculate radiation contribution of the selected uranium ore 
//					mrRadiation = mrRadiation + ((clsUraniumOre)oEntity).mrRadiationIntensity / Math.pow(rDistance, 2);
				}
			}
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
	private double getDistance(Double2D poCollisionPoint){
		double rDistance = poCollisionPoint.length(); 
		
		return rDistance;
	}
	
	// returns measured radiation intensity
	public double getMrRadiation(){
		return mrRadiation;		
	}
		
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 29.07.2009, 10:22:42
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_RADIATION;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 29.07.2009, 10:22:42
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Radiation";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 29.07.2009, 10:22:42
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub
	}
}
