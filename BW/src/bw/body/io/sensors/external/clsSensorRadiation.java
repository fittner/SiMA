/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

//import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPolarcoordinate;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.entities.clsStationary;
import bw.physicalObjects.sensors.clsEntityPartRadiation;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eConfigEntries;

/**
 * TODO (horvath) - This class defines the Radiation sensor object which is tagged to an animate 
 *                    object. clsSensorRadiation defines the functionalities of the radiation 
 *                    sensor, while clsAnimateRadiation defines the physical object.     
 * 
 * @author horvath
 * 
 */
public class clsSensorRadiation extends clsSensorExt {
	protected double mnViewRad;
	protected double mnRadRange; 
	protected double mnRadOffset;
	
	private clsEntity moEntity;
	
	private clsEntityPartRadiation moRadiationArea;
	private HashMap<Integer, PhysicalObject2D> moCollidingObj;
	private HashMap<Integer, Double2D> moCollisionPoint;
	private HashMap<Integer, PhysicalObject2D> moViewObj;
	private HashMap<Integer, clsPolarcoordinate> moViewObjDir;
		
	/**
	 * @param poEntity
	 * @param poBaseIO
	 */
	public clsSensorRadiation(clsEntity poEntity, clsBaseIO poBaseIO, clsConfigMap poConfig)	{
		super(poBaseIO, clsSensorRadiation.getFinalConfig(poConfig));
			
		moCollidingObj = new HashMap<Integer, PhysicalObject2D>();
		moViewObj = new HashMap<Integer, PhysicalObject2D>(); 
		moCollisionPoint = new HashMap<Integer, Double2D>();
		moViewObjDir = new HashMap<Integer, clsPolarcoordinate>();
		
		moEntity = poEntity;
		 
		applyConfig();

		moRadiationArea = new clsEntityPartRadiation(moEntity, mnRadRange, mnRadOffset);
		this.regRadiationObj(moEntity, mnRadOffset); //0 = no offset = radiation sensing centered on object
	}	
	
	
	private void applyConfig() {	
		mnViewRad = ((clsConfigDouble)moConfig.get(eConfigEntries.ANGLE)).get();
		mnRadRange = ((clsConfigDouble)moConfig.get(eConfigEntries.RANGE)).get();
		mnRadOffset = ((clsConfigDouble)moConfig.get(eConfigEntries.OFFSET)).get();
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ANGLE, new clsConfigDouble(Math.PI));
		oDefault.add(eConfigEntries.RANGE, new clsConfigDouble(75.0));
		oDefault.add(eConfigEntries.OFFSET, new clsConfigDouble(0.0));
		

		return oDefault;
	}
	
	/**
	 * TODO (horvath) - insert description
	 *
	 * @param poEntity
	 */
	private void regRadiationObj(clsEntity poEntity, double pnRadiusOffsetRadiationArea)	{
		Angle oEntityOrientation;
		if(poEntity instanceof clsMobile){
			oEntityOrientation = ((clsMobile)poEntity).getMobileObject2D().getOrientation(); 
			regRadiationObjWithParams(poEntity, pnRadiusOffsetRadiationArea, oEntityOrientation);
		}
		if(poEntity instanceof clsStationary){
			oEntityOrientation = ((clsStationary)poEntity).getStationaryObject2D().getOrientation(); 
			regRadiationObjWithParams(poEntity, pnRadiusOffsetRadiationArea, oEntityOrientation);
		}		
    }
	
	
	
	
	/**
	 * Extension of the default method, with parameters
	 *
	 * @author muchitsch
	 * 25.02.2009, 13:36:24
	 *
	 * @param poEntity
	 * @param pnRadiusOffsetRadiationArea - how much is the center of the radiation shiftet? NULL if no offset
	 * @param poRadiationOrientation
	 */
	private void regRadiationObjWithParams(clsEntity poEntity, double pnRadiusOffsetRadiationnArea, Angle poRadiationOrientation)	{
		Double2D oEntityPos = null;
		
		if(poEntity instanceof clsMobile){
			oEntityPos = ((clsMobile)poEntity).getMobileObject2D().getPosition(); 
		}
		if(poEntity instanceof clsStationary){
			oEntityPos = ((clsStationary)poEntity).getStationaryObject2D().getPosition(); 
		}
			
		//if we have a offset, change the center point. this is only for initializing, see step of entitypartradiation for more
		if(pnRadiusOffsetRadiationnArea != 0)
			//oEntityPos = oEntityPos.add(pnRadiusOffsetRadiationArea);
		
		try
		{
			moRadiationArea.setPose(oEntityPos, poRadiationOrientation);
		
		}
		catch( Exception ex )
		{
			System.out.println("regRadiationObjWithParams:"+ex.getMessage());
		}
	}
	


	/**
	 * TODO (horvath) - calculated which are within the entity radiation sensing field  
	 *
	 */
	private void calcViewObj(){
				
		moCollidingObj = moRadiationArea.getMeUnFilteredObj();
		moCollisionPoint = moRadiationArea.getMeCollisionPoint(); 
	
		if(moCollidingObj.size()>0)
		 {
			moViewObj.clear(); 
//			try{			
				Iterator<PhysicalObject2D> itr = moCollidingObj.values().iterator(); 			
				while(itr.hasNext())
				{
					PhysicalObject2D oPhObj = itr.next();
					clsPolarcoordinate oRel = getRelPos(moCollisionPoint.get(oPhObj.getIndex())); 
					
					if(!moViewObj.containsKey(oPhObj.getIndex()) && getInView(oRel.moAzimuth.radians)){
						addViewObj(oPhObj, oPhObj.getIndex()); 
						oRel.rotateBy(moRadiationArea.getOrientation(), true);
						try {
						addViewObjDir(oRel, oPhObj.getIndex());
						} catch (Exception e) {
							System.out.println(e);
						}
					}
			     }
//			} catch(Exception ex){
//				System.out.println("calcViewObj:"+ex.getMessage());
//			}
		 }
	}
	
	/**
	 * TODO (deutsch) - insert description
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

	/**
	 * TODO (horvath) - returns the angle of the relative position
	 * to the perceived objectn
	 *
	 * @param poPos
	 * @return nOrientation 
	 */
	public clsPolarcoordinate getRelPos(Double2D poColPos)
	{   
		double nOrientation;
		
		nOrientation = Math.atan2(poColPos.y, poColPos.x);
		
		if(nOrientation < 0)
			nOrientation = 2*Math.PI+nOrientation; 
		
		return new clsPolarcoordinate(poColPos.length(), nOrientation); 
	}
	
	/**
	 * TODO (horvath) - Tests if an object is within an agent's field of
	 * view
	 *
	 * @param pnOrientation
	 * @return boolean
	 */
	public boolean getInView(double pnOrientation)
	{
		double nEntityOrientation = moRadiationArea.getOrientation().radians;
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
	 * TODO (horvath) - insert description
	 *
	 * @author horvath
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
		calcViewObj();
		moRadiationArea.setMeRadiationObj(moViewObj);
	}
	
	/**
	 * TODO (horvath) - insert description
	 *
	 * @param pPhObj
	 */
	public void addViewObj(PhysicalObject2D pPhObj, int index)	{
		moViewObj.put(index,pPhObj);
	}
	
	/**
	 * TODO (horvath) - insert description
	 *
	 * @param peCollidingObj
	 */
	public void setMeCollidingObj(HashMap<Integer, PhysicalObject2D> peCollidingObj)	{
		moCollidingObj = peCollidingObj; 
	}
	
	/**
	 * TODO (horvath) - insert description
	 *
	 * @param pnRadRange
	 */
	public void setRadiationRange(double pnRadRange)	{
		mnRadRange = pnRadRange; 
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO Auto-generated method stub
		mePartId = eBodyParts.SENSOR_EXT_RADIATION;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		// TODO Auto-generated method stub
		moName = "ext. Sensor Radiation";
	}
	
	
	/**
	 * @return the mnRadRange
	 */
	public double getmnRadRange() {
		return mnRadRange;
	}
	
	public clsEntityPartRadiation getMoRadiationArea()
	{
		return moRadiationArea; 
	}

	/**
	 * @param mnRadRange the mnRadRange to set
	 */
	public void setMnRadRange(double pnRadRange) {
		this.mnRadRange = pnRadRange;
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
