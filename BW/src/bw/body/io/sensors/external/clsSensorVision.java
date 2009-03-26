/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
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
	private HashMap<Integer, PhysicalObject2D> moCollidingObj;
	private HashMap<Integer, PhysicalObject2D> moViewObj;
	private HashMap<Integer, Double2D> moCollisionPoint;
		
	/**
	 * @param poEntity
	 * @param poBaseIO
	 */
	public clsSensorVision(clsEntity poEntity, clsBaseIO poBaseIO)	{
		super(poBaseIO);
		mnViewRad = Math.PI;
		mnVisRange = 50; 
		
		moCollidingObj = new HashMap<Integer, PhysicalObject2D>();
		moViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		moCollisionPoint = new HashMap<Integer, Double2D>();
		moVisionArea = new clsEntityPartVision(poEntity, mnVisRange, 0);
		this.regVisionObj(poEntity, 0); //0 = no offset = vision centered on object
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
	 * @param pnRadiusOffsetVisionArea
	 * @param poVisionOrientation
	 */
	public clsSensorVision(clsEntity poEntity, clsBaseIO poBaseIO, double pnViewDegree, double pnVisRange, double pnRadiusOffsetVisionArea, Angle poVisionOrientation)	{
		super(poBaseIO);
		mnViewRad = pnViewDegree;
		mnVisRange = pnVisRange; 
		
		moCollidingObj = new HashMap<Integer, PhysicalObject2D>();
		moViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		moCollisionPoint = new HashMap<Integer, Double2D>(); 
		moVisionArea = new clsEntityPartVision(poEntity, mnVisRange, pnRadiusOffsetVisionArea);
		
	    this.regVisionObj(poEntity, pnRadiusOffsetVisionArea);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param poEntity
	 */
	private void regVisionObj(clsEntity poEntity, double pnRadiusOffsetVisionArea)	{
		Angle oEntityOrientation = ((clsMobile)poEntity).getMobileObject2D().getOrientation(); 
		regVisionObjWithParams(poEntity, pnRadiusOffsetVisionArea, oEntityOrientation);
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
		
		Double2D oEntityPos = ((clsMobile)poEntity).getMobileObject2D().getPosition(); 
		
		//if we have a offset, change the center point. this is only for initializing, see step of entitypartvision for more
		if(pnRadiusOffsetVisionArea != 0)
			//oEntityPos = oEntityPos.add(pnRadiusOffsetVisionArea);
		
		try
		{
			moVisionArea.setPose(oEntityPos, poVisionOrientation);
		
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
				
		moCollidingObj = moVisionArea.getMeUnFilteredObj();
		moCollisionPoint = moVisionArea.getMeCollisionPoint(); 
	
		if(moCollidingObj.size()>0)
		 {
			moViewObj.clear(); 
			try{			
				Iterator<PhysicalObject2D> itr = moCollidingObj.values().iterator(); 			
				while(itr.hasNext())
				{
					oPhObj = itr.next(); 
					nOrientation = this.getRelPos(moCollisionPoint.get(oPhObj.getIndex()));
					
					if(!moViewObj.containsKey(oPhObj.getIndex()) && this.getInView(nOrientation)){
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
		this.calcViewObj();
		moVisionArea.setMeVisionObj(moViewObj);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param pPhObj
	 */
	public void addViewObj(PhysicalObject2D pPhObj)	{
		moViewObj.put(pPhObj.getIndex(),pPhObj);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param peCollidingObj
	 */
	public void setMeCollidingObj(HashMap<Integer, PhysicalObject2D> peCollidingObj)	{
		moCollidingObj = peCollidingObj; 
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
}
