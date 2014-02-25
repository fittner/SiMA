/**
 * CHANGELOG
 *
 * 25.02.2014 herret - File created
 *
 */
package complexbody.io.sensors.external;

import java.util.ArrayList;

import bfg.utils.enums.eSide;

import complexbody.io.clsBaseIO;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsMobile;
import entities.enums.eBodyParts;
import entities.systems.clsInventory;

import physics2D.physicalObject.clsCollidingObject;
import properties.clsProperties;
import sim.physics2D.util.Double2D;
import tools.clsPolarcoordinate;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 25.02.2014, 08:57:06
 * 
 */
public class clsSensorCarriedItems extends clsSensorExt {

	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since 25.02.2014 08:57:10
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	
	private clsMobile moEntity;
	public clsSensorCarriedItems(String poPrefix, clsProperties poProp,
			clsBaseIO poBaseIO, clsEntity poEntity) {
		super(poPrefix, poProp, poBaseIO);

		assignSensorData(new Double2D(0.0,0.0),0.0, 0.0);
		moEntity = (clsMobile) poEntity;
	}

	/* (non-Javadoc)
	 *
	 * @since 25.02.2014 08:57:06
	 * 
	 * @see complexbody.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {

		
	}

	/* (non-Javadoc)
	 *
	 * @since 25.02.2014 08:57:06
	 * 
	 * @see complexbody.io.sensors.external.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		ArrayList<clsCollidingObject> moCollidingObjects = new ArrayList<clsCollidingObject>();
		
		clsInventory oInventory = moEntity.getInventory();
		clsMobile oCarriedEntity = oInventory.getCarriedEntity();
		if(oCarriedEntity !=null){
			clsCollidingObject oCollidingObject = new clsCollidingObject(oCarriedEntity.getMobileObject2D(),new clsPolarcoordinate(0.0,0.0),eSide.CENTER);
			moCollidingObjects.add(oCollidingObject);
		}
		moSensorData.setMeDetectedObjectList(0.0, moCollidingObjects);

	}

	/* (non-Javadoc)
	 *
	 * @since 25.02.2014 08:57:06
	 * 
	 * @see complexbody.io.sensors.external.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<clsCollidingObject> peObjInAreaList) {
		// not used

	}

	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION;
	}
	
	@Override
	protected void setName() {
		moName = "ext. Sensor Vision";
	}

}
