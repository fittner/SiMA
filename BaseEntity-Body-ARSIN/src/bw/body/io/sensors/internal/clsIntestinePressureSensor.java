/**
 * clsStomachTension.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 10.08.2009, 15:56:22
 */
package bw.body.io.sensors.internal;

import config.clsProperties;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsDigestiveSystem;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 10.08.2009, 15:56:22
 * 
 */
public class clsIntestinePressureSensor  extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrTension;
	

	public clsIntestinePressureSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		mrTension = 0;
		setEntity(poBody);
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		//nothing to do
	}	
	
	private void setEntity(clsBaseBody poBody) {
		this.moBody = poBody;
	}	
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_INT_INTESTINEPRESSURE;

	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "int. Intestine Pressure Sensor";
	}	
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsDigestiveSystem oStomachSystem = ((clsComplexBody)moBody).getInternalSystem().getStomachSystem();

			try {
				clsNutritionLevel oUndigestable = oStomachSystem.getNutritionLevel(eNutritions.EXCREMENT);
				mrTension = oUndigestable.getContent() / oUndigestable.getMaxContent();
			} catch (java.lang.ArithmeticException e) {
				mrTension = 0; // per definition.
			}
		}
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getTension() {
		return mrTension;
	}		
}
