/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities.abstractEntities;

import java.awt.Color;

import complexbody.io.clsExternalIO;
import complexbody.io.sensors.external.clsSensorEatableArea;
import complexbody.io.sensors.external.clsSensorEngine;
import complexbody.io.sensors.external.clsSensorVision;

import physics2D.physicalObject.clsCollidingObject;
import physics2D.physicalObject.clsMobileObject2D;
import physics2D.physicalObject.clsStationaryObject2D;

import config.clsProperties;
import du.enums.eEntityType;
import du.enums.eSensorExtType;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;
import registration.clsRegisterEntity;
//import bw.utils.inspectors.entity.clsInspectorFungusBase;
import sim.engine.SimState;
import sim.physics2D.physicalObject.PhysicalObject2D;
import statictools.clsGetARSPath;
import tools.clsPose;
import tools.eImagePositioning;

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
	public static String P_SENSOR = "sensor";
	
	private int mnStoredOre;	// stored ore counter
	private clsSensorEatableArea moSensorEatable;	// 'eatability' sensor
	
	
    public clsBase(String poPrefix, clsProperties poProp, int uid) {
    	super(poPrefix, poProp, uid);
    	
		mnStoredOre = 0;

		applyProperties(poPrefix, poProp);
    }
    
    
    private void applyProperties(String poPrefix, clsProperties poProp){		
		String pre = clsProperties.addDot(poPrefix);
		
    	// null - Stationary objects don't have a body, therefore can't have an instance of clsBaseIO 
    	moSensorEatable = new clsSensorEatableArea(pre+P_SENSOR, poProp, null);		
	}	
    
    public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll(clsStationary.getDefaultProperties(pre) );

		
		/*
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, 2 * Math.PI );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_MAX_DISTANCE, 25.0 );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 0.0 );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_OFFSET_X , 0.0 );
		oProp.setProperty(pre+P_SENSOR+"."+clsSensorVision.P_SENSOR_OFFSET_Y , 0.0 );
	*/


  		String tmp_pre = pre+P_SENSOR+".";
		
		oProp.putAll( clsSensorEngine.getDefaultProperties(tmp_pre+clsExternalIO.P_SENSORENGINE) );
		oProp.setProperty(tmp_pre+clsExternalIO.P_SENSORRANGE, 0.0); // Default - changed later on
		
		oProp.putAll( clsSensorVision.getDefaultProperties( tmp_pre) );
		oProp.setProperty(tmp_pre+clsExternalIO.P_SENSORACTIVE, true);
		oProp.setProperty(tmp_pre+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION.name());
		oProp.setProperty(tmp_pre+clsExternalIO.P_SENSORRANGE, oProp.getProperty(tmp_pre+clsExternalIO.P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE));
		oProp.setProperty(tmp_pre+clsSensorVision.P_SENSOR_MAX_DISTANCE, oProp.getProperty(tmp_pre+clsExternalIO.P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE));

		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 17);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.white);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "base.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		
		return oProp;
	}	
	@Override
	public void registerEntity(){
		clsRegisterEntity.registerEntity(this);
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
						registration.clsRegisterEntity.unRegisterPhysicalObject2D(oColObj.moCollider);
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
			oResult = (clsEntity) ((clsMobileObject2D) poObject).getEntity();
		} else if (poObject instanceof clsStationaryObject2D) {
			oResult = (clsEntity) ((clsStationaryObject2D) poObject).getEntity();
		}	
		
		return oResult;
	}
	
	public int getMnStoredOre() {
		return mnStoredOre;
	}


	/* (non-Javadoc)
	 *
	 * @since Dec 11, 2012 4:22:38 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.inspector.TabbedInspector, sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector(new clsInspectorFungusBase(poSuperInspector, poWrapper, poState, (clsBase)poEntity), "FungusBase default");
		
	}*/
	
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsEntityFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
		double x = this.getPose().getPosition().x;
		double y = this.getPose().getPosition().y;
		double angle = this.getPose().getAngle().radians;
		double weight = this.getVariableWeight();
		
		//set position
		oNewEntity.setPose(new clsPose(x-(poDistance/2), y, angle));
		this.setPose(new clsPose(x+(poDistance/2), y, angle));
		//set weight
		oNewEntity.setVariableWeight(weight*poSplitFactor);
		this.setVariableWeight(weight*(1-poSplitFactor));
		
		return oNewEntity;

	}
	
}

