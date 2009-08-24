/**
 * @author zeilinger
 * 15.07.2009, 14:42:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObjects.sensors;

import java.awt.Color;
import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;

import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.shape.clsCircleBorder;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.factories.clsSingletonProperties;
import bw.factories.clsSingletonMasonGetter;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.07.2009, 14:42:25
 * 
 */


	/**
	 *  This class defines the the physical object for the vision sensor. It 
	 *  extends the Mobile2D object.
	 *  clsSensorVision defines the functionalities of the vision 
	 *  sensor, while clsAnimateVision defines the physical object. 
	 * 
	 * @author zeilinger
	 * 
	 */

	public class clsEntitySensorEngine extends MobileObject2D implements Steppable{

		private static final long serialVersionUID = 1L;
		private final static double MASS = 0.0001;
	
		private ArrayList <clsCollidingObject>  meDetectedObjList;
		private clsCircleBorder moShape;
		private clsEntity moHostEntity;
				
		/**
		 * @param poEntity
		 * @param pnRad
		 * @param pnRadiusOffsetVisionArea 
		 */
		public clsEntitySensorEngine(clsEntity poHostEntity, double pnRadius) {    
		   meDetectedObjList = new ArrayList <clsCollidingObject>(); 
		   moHostEntity = poHostEntity; 
		   setShape(pnRadius); 
		 }
	
		public void step(SimState state){
			setOrientationAndPosition();
			clearList();
		}
		
		public void setShape(double pnRadius){
			setDrawOptions(pnRadius);
			registerShape(); 
		}
		
		public void setDrawOptions(double pnRadius){
			 double pnAngle = 0.001;
			 Color oColor = Color.RED;
			 
			 if(clsSingletonProperties.drawSensors()){
				 pnAngle = 2*Math.PI; 
			 }
			
			 moShape = new clsCircleBorder(pnRadius, pnAngle, oColor);		
		}
		
		public void registerShape(){
			 try	{
				 setShape(moShape, MASS); 
			}catch(Exception ex){System.out.println(ex.getMessage());}
		}
			
		public ArrayList <clsCollidingObject> requestDetectedObjList(){
			return meDetectedObjList; 
		}
		
		private void setOrientationAndPosition(){
			clsMobileObject2D oMobileObj = ((clsMobile)moHostEntity).getMobileObject2D();
			this.setPose(oMobileObj.getPosition(), oMobileObj.getOrientation()); 
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, 
										new sim.util.Double2D(oMobileObj.getPosition().x,oMobileObj.getPosition().y));
		}
			
		private void clearList(){
			meDetectedObjList.clear(); 
		}
		//--------------------------------------------------------------------------------------------------
		// Methods from PhysicalObject2D which have to be overwritten
		//-------------------------------------------------------------------------------------------------
		
		/** receives all objects, the physical object is colliding with - objects 
		 * which are moving away from the   
		*/     
		
		
		@Override
		public int handleCollision(PhysicalObject2D other, sim.physics2D.util.Double2D colPoint){
			return 0; // Sensor Area Collision - avoids forwarding calculated impulses to the object
		}
		
		@Override
		public void addContact(PhysicalObject2D poCollidingObj,
									 Double2D poCollisionPoint){
			//FIXME colPoint not used
			
			if (poCollisionPoint != null && poCollisionPoint.length()>= 0){
				meDetectedObjList.add(new clsCollidingObject(poCollidingObj, poCollisionPoint));
			}				
		}

		//--------------------------------------------------------------------------------------------------
		// Methods from Mobile2D which have to be overwritten
		//-------------------------------------------------------------------------------------------------
		/** Calculates and adds the static and dynamic friction forces on the object
		 * based on the coefficients of friction. 
		*/
			
		@Override
		public void addFrictionForce()
		{        }
		
		@Override
		public boolean hitObject(Object object, DrawInfo2D range)   {
			//TODO Clemens, hier geh�rt mehr rein als nur true!
	    	return true; // (insert location algorithm and intersection here)
	    }		
}

