/**
 * @author langr
 * 12.05.2009, 17:40:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import java.util.HashMap;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsStomachSystem;
import bw.body.io.clsBaseIO;
import bw.utils.config.clsBWProperties;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsNutritionLevel;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 12.05.2009, 17:40:44
 * 
 */
public class clsStomachSensor extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrEnergy;
	private HashMap<eNutritions, clsMutableDouble> moNutritionContents;

	public clsStomachSensor(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		mrEnergy = 0;
		moNutritionContents = new HashMap<eNutritions, clsMutableDouble>();
		
		setEntity(poBody);
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
	}	
	
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @param poEntity
	 */
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
		mePartId = eBodyParts.SENSOR_INT_STOMACH;

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
		moName = "int. Stomach Sensor";

	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsStomachSystem oStomachSystem = ((clsComplexBody)moBody).getInternalSystem().getStomachSystem();

			mrEnergy = oStomachSystem.getEnergy();
			
			HashMap<eNutritions, clsNutritionLevel> oList = oStomachSystem.getList();
			moNutritionContents.clear();
			
			for(eNutritions nKey:oList.keySet()) {
				clsNutritionLevel oNL = oList.get(nKey);
				moNutritionContents.put(nKey, new clsMutableDouble( oNL.getContent() ) );
			}
		}
		
	}
	
	public HashMap<eNutritions, clsMutableDouble> getNutritionContents() {
		return moNutritionContents;
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getEnergy() {
		return mrEnergy;
	}

}
