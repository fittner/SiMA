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
import sim.util.*;

import bw.clsEntity;
import bw.body.io.clsBaseIO;
import bw.factories.clsSingletonMasonGetter;
import bw.physicalObject.entityParts.clsEntityPartVision;
import bw.physicalObject.inanimate.mobile.clsMobile;
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
	private double mnViewDegree;
	private double mnVisRange; 
	private clsEntityPartVision moVisionArea;
	private Bag meCollidingObj;
	private HashMap<Integer, PhysicalObject2D> meViewObj;
		
	/**
	 * @param poEntity
	 * @param poBaseIO
	 */
	public clsSensorVision(clsEntity poEntity, clsBaseIO poBaseIO)	{
		super(poBaseIO);
		mnViewDegree = Math.PI;
		mnVisRange = 50; 
		
		meCollidingObj = new Bag();
		meViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		moVisionArea = new clsEntityPartVision(poEntity, mnVisRange);
		this.regVisionObj(poEntity);
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param poEntity
	 */
	private void regVisionObj(clsEntity poEntity)	{
		Double2D oEntityPos = ((clsMobile)poEntity).getMobile().getPosition(); 
		Angle oEntityOrientation = ((clsMobile)poEntity).getMobile().getOrientation(); 
		
		PhysicsEngine2D oPhyEn2D = clsSingletonMasonGetter.getPhysicsEngine2D();
		Continuous2D oFieldEnvironment = clsSingletonMasonGetter.getFieldEnvironment();
		SimState oSimState = clsSingletonMasonGetter.getSimState();
		
		try
		{
			moVisionArea.setPose(oEntityPos, oEntityOrientation);
			oPhyEn2D.register(moVisionArea);
			oPhyEn2D.setNoCollisions(moVisionArea,((clsMobile)poEntity).getMobile());
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
	
		if(meCollidingObj.size()>0)
		 {
			meViewObj.clear(); 
			Iterator<PhysicalObject2D> itr = meCollidingObj.iterator();
			
			while(itr.hasNext()){
				oPhObj = (PhysicalObject2D)itr.next(); 
				nOrientation = this.getRelPos(oPhObj.getPosition());
				
				if(!meViewObj.containsKey(oPhObj.getIndex()) && this.getInView(nOrientation)){
					this.addViewObj(oPhObj); 
				}
		     }
		 }
	}
	
	/**
	 * TODO (zeilinger) - returns the angle of the relative position
	 * to the perceived objectn
	 *
	 * @param poPos
	 * @return nOrientation 
	 */
	public double getRelPos(Double2D poPos)
	{
		double nOrientation;
		double nDivX = poPos.x - moVisionArea.getPosition().x;
		double nDivY = poPos.y - moVisionArea.getPosition().y;
		nOrientation = Math.atan2(nDivY, nDivX);
		
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
		
		nMinBorder = nEntityOrientation -  mnViewDegree/2; 
		nMaxBorder = nEntityOrientation +  mnViewDegree/2; 
		
		if(nMaxBorder>2*Math.PI)
			nMaxBorder-=2*Math.PI; 
		if(nMinBorder<0)
			nMinBorder+=2*Math.PI; 
		
		if(pnOrientation <= nMaxBorder ||
				pnOrientation >= nMinBorder)
		{
			return true;  
		}
		return false; 
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
	public void setMeCollidingObj(Bag peCollidingObj)	{
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
	 * @return the mnViewDegree
	 */
	public double getMnViewDegree() {
		return mnViewDegree;
	}

	/**
	 * @param mnViewDegree the mnViewDegree to set
	 */
	public void setMnViewDegree(double pnViewDegree) {
		this.mnViewDegree = pnViewDegree;
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
