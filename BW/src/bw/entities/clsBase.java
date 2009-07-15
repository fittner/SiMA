/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.Iterator;

import sim.engine.SimState;
import sim.physics2D.physicalObject.PhysicalObject2D;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPose;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import enums.eEntityType;
//import bw.factories.clsRegisterEntity;

/**
 * 
 * Stores uranium ore collected by the Fungus-Eater. Has infinite capacity.
 * 
 * TODO (horvath) - insert description 
 * 
 * @author horvath
 * 08.07.2009, 14:52:00
 * 
 */
public class clsBase extends clsStationary{
	private static Color moDefaultColor = Color.gray;
	private static double mrDefaultRadius = 20.0f;
	private static String moImagePath = sim.clsBWMain.msArsPath + "/src/resources/images/spacestation.gif";
	private int mnStoredOre;	// stored ore counter
	
	private clsSensorEatableArea moSensorEatable;	// 'eatability' sensor
	private clsConfigMap oConfig;					// 'eatability' sensor configuration
    
    public clsBase(int pnId, clsPose poPose, clsConfigMap poConfig) {
    	super(pnId, poPose, new ARSsim.physics2D.shape.clsCircleImage(clsBase.mrDefaultRadius, moDefaultColor , moImagePath), clsBase.getFinalConfig(poConfig));
		
		applyConfig();
		
		oConfig = poConfig;
		
		oConfig.add(eConfigEntries.ANGLE, new clsConfigDouble((float) 1.99 * Math.PI));
		oConfig.add(eConfigEntries.RANGE, new clsConfigDouble(20.0f));
		oConfig.add(eConfigEntries.OFFSET, new clsConfigDouble(0.0f));
		
		// null - Stationary objects don't have a body, therefore can't have an instance of clsBaseIO 
		moSensorEatable = new clsSensorEatableArea(this, null, oConfig);		
		
		mnStoredOre = 0;
    }
    
    
  

	private void applyConfig() {
		//TODO add ...
		
		
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.CAN;
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		moSensorEatable.updateSensorData();
	}
	
	public void step(SimState state){
		//int i = 0; 
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
	
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		// TODO Auto-generated method stub
		Iterator<Integer> i = moSensorEatable.getViewObj().keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			if (getEntityType(moSensorEatable.getViewObj().get(oKey)) == eEntityType.URANIUM) {
				if(getEntity(moSensorEatable.getViewObj().get(oKey)).isRegistered()){
					getEntity(moSensorEatable.getViewObj().get(oKey)).setRegistered(false);
					bw.factories.clsRegisterEntity.unRegisterPhysicalObject2D(moSensorEatable.getViewObj().get(oKey));
					mnStoredOre++;
				}
			}
		}		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		
		// TODO Auto-generated method stub
		
	}
	
	
	public clsEntityPartVision getEatableAreaVision()
	{
		return ((clsSensorEatableArea)moSensorEatable).getMoVisionArea();
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
	
	public int getMnStoredOre() {
		return mnStoredOre;
	}
	
}

