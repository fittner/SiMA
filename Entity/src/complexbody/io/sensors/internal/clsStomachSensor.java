/**
 * @author langr
 * 12.05.2009, 17:40:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.sensors.internal;

import java.util.HashMap;

import properties.clsProperties;
import body.clsBaseBody;
import body.clsComplexBody;
import body.utils.clsNutritionLevel;

import complexbody.internalSystems.clsDigestiveSystem;
import complexbody.io.clsBaseIO;

import datatypes.clsMutableDouble;
import entities.enums.eBodyParts;
import entities.enums.eNutritions;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 12.05.2009, 17:40:44
 * 
 */
public class clsStomachSensor extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private HashMap<eNutritions, clsMutableDouble> moNutritionContents;

	public clsStomachSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		moNutritionContents = new HashMap<eNutritions, clsMutableDouble>();
		
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
	@Override
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsDigestiveSystem oStomachSystem = ((clsComplexBody)moBody).getInternalSystem().getStomachSystem();

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
	
}
