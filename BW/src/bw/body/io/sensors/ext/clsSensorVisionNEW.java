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
import java.util.HashMap;

import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.util.clsPolarcoordinate;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.body.io.sensors.external.clsSensorExt;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 14.07.2009, 08:01:45
 * 
 */
public class clsSensorVisionNEW extends clsSensorExt {

	private clsEntity moEntity;
	private clsSensorData moSensorData; 
	private Double mnRange;
		
	public clsSensorVisionNEW(clsEntity poEntity, clsBaseIO poBaseIO, clsConfigMap poConfig, clsSensorEngine poSensorEngine){
		super(poBaseIO, clsSensorVisionNEW.getFinalConfig(poConfig));
		moEntity = poEntity;
		applyConfig();
		moSensorData = new clsSensorData(this, moEntity.getPosition(), mnRange);
		poSensorEngine.registerSensor(moSensorData);
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ANGLE, new clsConfigDouble(Math.PI));
		oDefault.add(eConfigEntries.RANGE, new clsConfigDouble(60.0));
		oDefault.add(eConfigEntries.OFFSET, new clsConfigDouble(0.0));

		return oDefault;
	}
	
	private void applyConfig() {	
		mnRange =(((clsConfigDouble)moConfig.get(eConfigEntries.RANGE)).get());
	}
	
	/**
	 * @return the meViewObj
	 */
	public HashMap<Integer, PhysicalObject2D> getViewObj() {
		return null;
	}
	
	/**
	 * @return the moViewObjDir
	 */
	public HashMap<Integer, clsPolarcoordinate> getViewObjDir() {
		return null;
	}
	
	public void updateSensorData(){
		
	}
	
	public ArrayList<PhysicalObject2D> getSensorData(){
		/*has to be implemented - return SensorData to Decision Unit*/
		return null; 
	}
    
	@Override
	public void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj) {
		// TODO Auto-generated method stub
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
}
