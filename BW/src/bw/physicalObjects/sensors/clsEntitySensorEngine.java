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
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.shape.clsCircleBorder;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.factories.clsSingletonMasonGetter;

/**
 * TODO (zeilinger) - insert description 
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
	
		private HashMap <Integer, sim.physics2D.util.Double2D>  meCollisionPointMap;
		private ArrayList <PhysicalObject2D>  meDetectedObjList;
		private Paint moColor;
		private clsCircleBorder moShape;
		private clsEntity moHostEntity;
				
		/**
		 * @param poEntity
		 * @param pnRad
		 * @param pnRadiusOffsetVisionArea 
		 */
		public clsEntitySensorEngine(clsEntity poHostEntity, double pnRadius) {    
		   meDetectedObjList = new ArrayList <PhysicalObject2D>(); 
		   meCollisionPointMap = new HashMap<Integer, Double2D>(); 
		   moColor = Color.RED;
		   moShape = new clsCircleBorder(pnRadius, moColor);
		   moHostEntity = poHostEntity; 
		   registerShape(); 
		 }
	
		public void step(SimState state){
			setOrientationAndPosition();
			clearList();
		}
		
		public void registerShape(){
			 try	{
				 setShape(moShape, MASS); 
			}catch(Exception ex){System.out.println(ex.getMessage());}
		}
			
		public ArrayList <PhysicalObject2D> requestDetectedObjList(){
			return meDetectedObjList; 
		}
		
		public HashMap <Integer, Double2D> requestCollisionPointMap(){
			return meCollisionPointMap; 
		}
		
		private void setOrientationAndPosition(){
			clsMobileObject2D oMobileObj = ((clsMobile)moHostEntity).getMobileObject2D();
			this.setPose(oMobileObj.getPosition(), oMobileObj.getOrientation()); 
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, 
										new sim.util.Double2D(oMobileObj.getPosition().x,oMobileObj.getPosition().y));
		}
			
		private void clearList(){
			meDetectedObjList.clear(); 
			meCollisionPointMap.clear(); 
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
			if (poCollisionPoint != null){
				meDetectedObjList.add(poCollidingObj);
				meCollisionPointMap.put(poCollidingObj.getIndex(), poCollisionPoint);
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

