/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.HashMap;
import java.util.Iterator;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.factories.clsSingletonMasonGetter;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.enums.eBodyParts;

/**
 * TODO (zeilinger) - This class defines the Vision object which is tagged to an animate 
 *                    object. clsSensorVision defines the functionalities of the vision 
 *                    sensor, while clsAnimateVision defines the physical object.     
 * 
 * @author zeilinger
 * 
 */
public class clsSensorVision extends clsSensorExt
{
	private double mnViewRad;
	private double mnVisRange; 
	private clsEntityPartVision moVisionArea;
	private HashMap<Integer, PhysicalObject2D> meCollidingObj;
	private HashMap<Integer, PhysicalObject2D> meViewObj;
	private HashMap<Integer, Double2D> meCollisionPoint;
		
	/**
	 * @param poEntity
	 * @param poBaseIO
	 */
	public clsSensorVision(clsEntity poEntity, clsBaseIO poBaseIO)	{
		super(poBaseIO);
		mnViewRad = Math.PI;
		mnVisRange = 50; 
		
		meCollidingObj = new HashMap<Integer, PhysicalObject2D>();
		meViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		meCollisionPoint = new HashMap<Integer, Double2D>(); 
		moVisionArea = new clsEntityPartVision(poEntity, mnVisRange);
		this.regVisionObj(poEntity);
	}
	
	/**
	 * special constructor with all the parameters for the vision area
	 * 
	 * @author muchitsch
	 * 26.02.2009, 11:21:50
	 *
	 * @param poEntity
	 * @param poBaseIO
	 * @param pnViewDegree
	 * @param pnVisRange
	 * @param poOffsetVisionArea
	 * @param poVisionOrientation
	 */
	public clsSensorVision(clsEntity poEntity, clsBaseIO poBaseIO, double pnViewDegree, double pnVisRange, Double2D poOffsetVisionArea, Angle poVisionOrientation)	{
		super(poBaseIO);
		mnViewRad = pnViewDegree;
		mnVisRange = pnVisRange; 
		
		meCollidingObj = new HashMap<Integer, PhysicalObject2D>();
		meViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		meCollisionPoint = new HashMap<Integer, Double2D>(); 
		moVisionArea = new clsEntityPartVision(poEntity, mnVisRange);
		
		//moVisionArea.setCenterOffset(poOffsetVisionArea);
		//this.regVisionObjWithParams(poEntity, poOffsetVisionArea, poVisionOrientation);
		this.regVisionObj(poEntity);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param poEntity
	 */
	private void regVisionObj(clsEntity poEntity)	{
		Double2D oEntityPos = ((clsMobile)poEntity).getMobileObject2D().getPosition(); 
		Angle oEntityOrientation = ((clsMobile)poEntity).getMobileObject2D().getOrientation(); 
		
		PhysicsEngine2D oPhyEn2D = clsSingletonMasonGetter.getPhysicsEngine2D();
		Continuous2D oFieldEnvironment = clsSingletonMasonGetter.getFieldEnvironment();
		SimState oSimState = clsSingletonMasonGetter.getSimState();
		
		try
		{
			moVisionArea.setPose(oEntityPos, oEntityOrientation);
			oPhyEn2D.register(moVisionArea);
			oPhyEn2D.setNoCollisions(moVisionArea,((clsMobile)poEntity).getMobileObject2D());
			oFieldEnvironment.setObjectLocation(moVisionArea, new sim.util.Double2D(oEntityPos.x, oEntityPos.y));
	        oSimState.schedule.scheduleRepeating(moVisionArea);
		}
		catch( Exception ex )
		{
			System.out.println(ex.getMessage());
		}
    }

	/**
	 * TODO (zeilinger) - calculated which are within the entity vision field  
	 *
	 */
	private void calcViewObj(){
		double nOrientation;  
		PhysicalObject2D oPhObj;  
				
		meCollidingObj = moVisionArea.getMeUnFilteredObj();
		meCollisionPoint = moVisionArea.getMeCollisionPoint(); 
	
		if(meCollidingObj.size()>0)
		 {
			meViewObj.clear(); 
			try{			
				Iterator<PhysicalObject2D> itr = meCollidingObj.values().iterator(); 			
				while(itr.hasNext())
				{
					oPhObj = itr.next(); 
					nOrientation = this.getRelPos(meCollisionPoint.get(oPhObj.getIndex()));
					
					if(!meViewObj.containsKey(oPhObj.getIndex()) && this.getInView(nOrientation)){
						this.addViewObj(oPhObj); 
					}
			     }
			}catch(Exception ex)
			{System.out.println(ex.getMessage());}
		 }
	}
	
	/**
	 * TODO (zeilinger) - returns the angle of the relative position
	 * to the perceived objectn
	 *
	 * @param poPos
	 * @return nOrientation 
	 */
	public double getRelPos(Double2D poColPos)
	{   
		double nOrientation;
		
		nOrientation = Math.atan2(poColPos.y, poColPos.x);
		
		if(nOrientation < 0)
			nOrientation = 2*Math.PI+nOrientation; 
		
		return nOrientation; 
	}
	
	/**
	 * TODO (zeilinger) - Tests if an object is within an agent's field of
	 * view
	 *
	 * @param pnOrientation
	 * @return boolean
	 */
	public boolean getInView(double pnOrientation)
	{
		double nEntityOrientation = moVisionArea.getOrientation().radians;
		double nMinBorder; 
		double nMaxBorder; 
		
		nEntityOrientation = this.normRad(nEntityOrientation);
		nMinBorder = nEntityOrientation -  mnViewRad/2; 
		nMaxBorder = nEntityOrientation +  mnViewRad/2; 
		
		if(nMaxBorder>2*Math.PI)
			nMaxBorder-=2*Math.PI; 
		if(nMinBorder<0)
			nMinBorder+=2*Math.PI; 
		
		if(nMaxBorder > nMinBorder && 
				pnOrientation <= nMaxBorder &&
				pnOrientation >= nMinBorder)
		{
			return true;  
		}
		else if (nMaxBorder < nMinBorder && 
				(pnOrientation <= nMaxBorder || 
				pnOrientation >= nMinBorder))
		{
			
			return true; 
		}
		return false; 
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.02.2009, 16:22:39
	 *
	 * @param pnOrientation
	 * @return
	 */
	public double normRad(double pnOrientation)
	{
		double newVal =  pnOrientation; 
		double twoPI = 2* Math.PI; 
		
		while(newVal > twoPI)
	            newVal -= twoPI;
	                
	    while(newVal < 0)
	            newVal += twoPI;
	  return newVal;  
	}
	
	/* (non-Javadoc)
	 * Updates the sensor data values by fetching the info from the physics engine entity 
	 */
	public void updateSensorData() {
		//TODO: HZ --> update meViewObj + meCollidingObj
		this.calcViewObj();
		moVisionArea.setMeVisionObj(meViewObj);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param pPhObj
	 */
	public void addViewObj(PhysicalObject2D pPhObj)	{
		meViewObj.put(pPhObj.getIndex(),pPhObj);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param peCollidingObj
	 */
	public void setMeCollidingObj(HashMap<Integer, PhysicalObject2D> peCollidingObj)	{
		meCollidingObj = peCollidingObj; 
	}
	
	/**
	 * TODO (zeilinger) - insert description
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
		// TODO Auto-generated method stub
		mePartId = eBodyParts.SENSOR_EXT_VISION;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		// TODO Auto-generated method stub
		moName = "ext. Sensor Vision";
	}
	
	
	/**
	 * @return the mnVisRange
	 */
	public double getMnVisRange() {
		return mnVisRange;
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
	public HashMap<Integer, PhysicalObject2D> getMeViewObj() {
		return meViewObj;
	}

	
}
