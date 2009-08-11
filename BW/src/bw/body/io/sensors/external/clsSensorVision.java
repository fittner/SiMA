/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.HashMap;

import config.clsBWProperties;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

import ARSsim.physics2D.util.clsPolarcoordinate;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.entities.clsStationary;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.enums.eBodyParts;
import bw.utils.sensors.clsSensorDataCalculation;

/**
 * TODO (zeilinger) - This class defines the Vision object which is tagged to an animate 
 *                    object. clsSensorVision defines the functionalities of the vision 
 *                    sensor, while clsAnimateVision defines the physical object.     
 * 
 * @author zeilinger
 * 
 */
public class clsSensorVision extends clsSensorExt {
	public static final String P_SENSOR_ANGLE = "sensor_angle";
	public static final String P_SENSOR_RANGE = "sensor_range";
	public static final String P_SENSOR_OFFSET = "offset";	
	
	protected double mnViewRad;
	protected double mnVisRange;
	protected double mnVisOffset;
	
	protected clsEntity moEntity;
	
	protected clsEntityPartVision moVisionArea;
	private HashMap<Integer, PhysicalObject2D> moCollidingObj;
	private HashMap<Integer, Double2D> moCollisionPoint;
	private HashMap<Integer, PhysicalObject2D> moViewObj;
	private HashMap<Integer, clsPolarcoordinate> moViewObjDir;
	private clsSensorDataCalculation moCalculationObj; 
	
	/**
	 * @param poEntity
	 * @param poBaseIO
	 */
	public clsSensorVision(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsEntity poEntity)	{
		super(poPrefix, poProp);
			
		moCollidingObj = new HashMap<Integer, PhysicalObject2D>();
		moViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		moCollisionPoint = new HashMap<Integer, Double2D>();
		moViewObjDir = new HashMap<Integer, clsPolarcoordinate>();
		
		moEntity = poEntity;
		 
		applyProperties(poPrefix, poProp);

		moVisionArea = new clsEntityPartVision(moEntity, mnVisRange, mnVisOffset);
		moCalculationObj = new clsSensorDataCalculation(); 
		this.regVisionObj(moEntity, mnVisOffset); //0 = no offset = vision centered on object
	}	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_SENSOR_ANGLE, (5*Math.PI/3) );
		oProp.setProperty(pre+P_SENSOR_RANGE, 60.0 );
		oProp.setProperty(pre+P_SENSOR_OFFSET, 0.0 );		
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
					
		mnViewRad = poProp.getPropertyDouble(pre+P_SENSOR_ANGLE);
		mnVisRange = poProp.getPropertyDouble(pre+P_SENSOR_RANGE);
		mnVisOffset = poProp.getPropertyDouble(pre+P_SENSOR_OFFSET);
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @param poEntity
	 */
	private void regVisionObj(clsEntity poEntity, double pnRadiusOffsetVisionArea)	{
		Angle oEntityOrientation;
		if(poEntity instanceof clsMobile){
			oEntityOrientation = ((clsMobile)poEntity).getMobileObject2D().getOrientation(); 
			regVisionObjWithParams(poEntity, pnRadiusOffsetVisionArea, oEntityOrientation);
		}
		if(poEntity instanceof clsStationary){
			oEntityOrientation = ((clsStationary)poEntity).getStationaryObject2D().getOrientation(); 
			regVisionObjWithParams(poEntity, pnRadiusOffsetVisionArea, oEntityOrientation);
		}		
    }
		
	/**
	 * Extension of the default method, with parameters
	 *
	 * @author muchitsch
	 * 25.02.2009, 13:36:24
	 *
	 * @param poEntity
	 * @param pnRadiusOffsetVisionArea - how much is the center of the vision shiftet? NULL if no offset
	 * @param poVisionOrientation
	 */
	private void regVisionObjWithParams(clsEntity poEntity, double pnRadiusOffsetVisionArea, Angle poVisionOrientation)	{
		Double2D oEntityPos = null;
		
		if(poEntity instanceof clsMobile){
			oEntityPos = ((clsMobile)poEntity).getMobileObject2D().getPosition(); 
		}
		if(poEntity instanceof clsStationary){
			oEntityPos = ((clsStationary)poEntity).getStationaryObject2D().getPosition(); 
		}
			
		//if we have a offset, change the center point. this is only for initializing, see step of entitypartvision for more
		if(pnRadiusOffsetVisionArea != 0)
			//oEntityPos = oEntityPos.add(pnRadiusOffsetVisionArea);
		
		try
		{
			moVisionArea.setPose(oEntityPos, poVisionOrientation);
		
		}
		catch( Exception ex )
		{
			System.out.println("regVisionObjWithParams:"+ex.getMessage());
		}
	}
	


	/**
	 * TODO (zeilinger) - calculated which are within the entity vision field  
	 *
	 */
	private void calcViewObj(){
				
		moCollidingObj = moVisionArea.getMeUnFilteredObj();
		moCollisionPoint = moVisionArea.getMeCollisionPoint(); 
	
		if(moCollidingObj.size()>0) {
			moViewObj.clear(); 

			for(PhysicalObject2D oPhysicalObject : moCollidingObj.values()){
					clsPolarcoordinate oRel = moCalculationObj
											 .getRelativeCollisionPosition(moCollisionPoint
											 .get(oPhysicalObject.getIndex())); 
					
					if(moCalculationObj.checkIfObjectInView(oRel.moAzimuth.radians, 
												moVisionArea.getOrientation().radians, mnViewRad)){
						addViewObj(oPhysicalObject); 
						oRel.rotateBy(moVisionArea.getOrientation(), true);
						addViewObjDir(oRel, oPhysicalObject.getIndex());
					}
			     }
		 }
	}
		
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 23.04.2009, 17:27:07
	 *
	 * @param rel
	 * @param index
	 */
	private void addViewObjDir(clsPolarcoordinate rel, int index) {
		moViewObjDir.put(index,rel);
		
	}
	
	/* (non-Javadoc)
	 * Updates the sensor data values by fetching the info from the physics engine entity 
	 */
	public void updateSensorData() {
		calcViewObj();
		moVisionArea.setMeVisionObj(moViewObj);
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @param pPhObj
	 */
	public void addViewObj(PhysicalObject2D pPhObj)	{
		moViewObj.put(pPhObj.getIndex(),pPhObj);
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @param peCollidingObj
	 */
	public void setMeCollidingObj(HashMap<Integer, PhysicalObject2D> peCollidingObj)	{
		moCollidingObj = peCollidingObj; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @param pnVisRange
	 */
	public void setVisionRange(double pnVisRange)	{
		mnVisRange = pnVisRange; 
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO (zeilinger) - Auto-generated method stub
		mePartId = eBodyParts.SENSOR_EXT_VISION;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		// TODO (zeilinger) - Auto-generated method stub
		moName = "ext. Sensor Vision";
	}
	
	
	/**
	 * @return the mnVisRange
	 */
	public double getMnVisRange() {
		return mnVisRange;
	}
	
	public clsEntityPartVision getMoVisionArea()
	{
		return moVisionArea; 
	}
	

	/**
	 * @param mnVisRange the mnVisRange to set
	 */
	public void setMnVisRange(double pnVisRange) {
		this.mnVisRange = pnVisRange;
	}

	/**
	 * @return the meViewObj
	 */
	public HashMap<Integer, PhysicalObject2D> getViewObj() {
		return moViewObj;
	}
	
	/**
	 * @return the moViewObjDir
	 */
	public HashMap<Integer, clsPolarcoordinate> getViewObjDir() {
		return moViewObjDir;
	}	
	
	
}
