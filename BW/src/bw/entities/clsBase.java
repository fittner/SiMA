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
import statictools.clsGetARSPath;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
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
public class clsBase extends clsStationary {
	
	public static final String P_ENTITY_RANGE = "range"; 
	public static final String P_ENTITY_ANGLE_VIEW = "angle_view"; 
	public static final String P_ENTITY_OFFSET = "offset"; 
	public static final String P_IMAGE_PATH = "image_path";
			
	private int mnStoredOre;	// stored ore counter
	private clsSensorEatableArea moSensorEatable;	// 'eatability' sensor
	
	
    public clsBase(String poPrefix, clsBWProperties poProp) {
    	super(poPrefix, poProp);
    	applyProperties(poPrefix, poProp);
    	
    	// null - Stationary objects don't have a body, therefore can't have an instance of clsBaseIO 
		moSensorEatable = new clsSensorEatableArea(poPrefix, poProp, null,this);		
		
		mnStoredOre = 0;
    }
    
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		//TODO
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(poPrefix) );
		oProp.setProperty(pre+P_ENTITY_COLOR_B, Color.gray.getBlue());
		oProp.setProperty(pre+P_ENTITY_COLOR_B, Color.gray.getBlue());
		oProp.setProperty(pre+P_ENTITY_COLOR_G, Color.gray.getGreen());
		oProp.setProperty(pre+P_SHAPE_TYPE,  eShapeType.SHAPE_CIRCLE.name());
				
		oProp.setProperty(pre+P_ENTITY_ANGLE_VIEW, 1.99 * Math.PI);
		oProp.setProperty(pre+P_ENTITY_RANGE, 50.0);
		oProp.setProperty(pre+P_SHAPE_RADIUS, 50.0);
		oProp.setProperty(pre+P_ENTITY_OFFSET, 0.0);
		oProp.setProperty(pre+P_IMAGE_PATH, clsGetARSPath.getArsPath()+ "/src/resources/images/spacestation.gif");
		
		return oProp;
	}	

			
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.UNDEFINED;
		
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
		Iterator<Integer> i = moSensorEatable.getViewObj().keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			// check if the entity is uranium
			if (getEntityType(moSensorEatable.getViewObj().get(oKey)) == eEntityType.URANIUM) {
				// check if the entity is registered - 'exists'
				if(getEntity(moSensorEatable.getViewObj().get(oKey)).isRegistered()){
					// check if the entity is not carried by any bubble
					if(((clsUraniumOre)getEntity(moSensorEatable.getViewObj().get(oKey))).getHolders() == 0){
						// 'eat' the entity
						getEntity(moSensorEatable.getViewObj().get(oKey)).setRegistered(false);
						bw.factories.clsRegisterEntity.unRegisterPhysicalObject2D(moSensorEatable.getViewObj().get(oKey));
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

