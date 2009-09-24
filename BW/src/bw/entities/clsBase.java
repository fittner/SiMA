/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;
import sim.engine.SimState;
import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.io.sensors.ext.clsSensorEatableArea;
import bw.body.io.sensors.ext.clsSensorVision;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eShapeType;
import enums.eEntityType;

/**
 * 
 * Stores uranium ore collected by the Fungus-Eater. Has infinite capacity.
 * 
 * DOCUMENT (horvath) - insert description 
 * 
 * @author horvath
 * 08.07.2009, 14:52:00
 * 
 */
public class clsBase extends clsStationary {
	public static final String P_SENSOR = "sensor";
	
	private int mnStoredOre;	// stored ore counter
	private clsSensorEatableArea moSensorEatable;	// 'eatability' sensor
	
	
    public clsBase(String poPrefix, clsBWProperties poProp) {
    	super(poPrefix, poProp);
    	
		mnStoredOre = 0;

		applyProperties(poPrefix, poProp);
    }
    
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		String pre = clsBWProperties.addDot(poPrefix);
		
    	// null - Stationary objects don't have a body, therefore can't have an instance of clsBaseIO 
    	moSensorEatable = new clsSensorEatableArea(pre+P_SENSOR, poProp, null);		
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsStationary.getDefaultProperties(pre) );

		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, 2 * Math.PI );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_MAX_DISTANCE, 25.0 );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 0.0 );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_OFFSET_X , 0.0 );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_OFFSET_Y , 0.0 );
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, 17);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.white);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/base.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		
		return oProp;
	}	

			
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BASE;
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO (horvath) - Auto-generated method stub
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
	
		// TODO (horvath) - Auto-generated method stub
		
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
		
		for(Double element : moSensorEatable.moSensorData.getMeDetectedObject().keySet()){
			for(clsCollidingObject oColObj : moSensorEatable.moSensorData.getMeDetectedObject().get(element)){
				clsEntity oEntityObj = getEntity(oColObj.moCollider); 
				
				if(oEntityObj.isRegistered() && oEntityObj.meEntityType == eEntityType.URANIUM){
					if(((clsUraniumOre)oEntityObj).getHolders() == 0){
						oEntityObj.setRegistered(false); 
						bw.factories.clsRegisterEntity.unRegisterPhysicalObject2D(oColObj.moCollider);
						mnStoredOre++;
					}
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
		
		// TODO (horvath) - Auto-generated method stub
		
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

