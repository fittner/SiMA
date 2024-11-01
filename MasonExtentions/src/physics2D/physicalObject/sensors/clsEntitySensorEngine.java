/**
 * @author zeilinger
 * 15.07.2009, 14:42:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package physics2D.physicalObject.sensors;

import interfaces.itfEntity;
import interfaces.itfMobile;


import java.awt.Color;
import java.util.ArrayList;

import physics2D.physicalObject.clsCollidingObject;
import physics2D.physicalObject.clsMobileObject2D;
import physics2D.shape.clsCircleBorder;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
//import sim.physics2D.util.Angle;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import singeltons.clsSingletonMasonGetter;
import singeltons.clsSingletonProperties;
import tools.clsPolarcoordinate;


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
		private itfEntity moHostEntity;
		private double mnDeviationAngle;

		/**
		 * @param poEntity
		 * @param pnRad
		 * @param pnRadiusOffsetVisionArea 
		 */
		public clsEntitySensorEngine(itfEntity poHostEntity, double pnRadius) {    
		   meDetectedObjList = new ArrayList <clsCollidingObject>(); 
		   moHostEntity = poHostEntity; 
		   setShape(pnRadius); 
		   setOrientationAndPosition(); //TD 2011/04/30 - bugfix. sensors are now created at the same position as their host 
		 }
	
		@Override
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
			 Color oColor = Color.BLUE;
			 
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
			//System.out.println("setOrientationAndPosition: " + mnDeviationAngle);
			
			clsMobileObject2D oMobileObj = ((itfMobile)moHostEntity).getMobileObject2D();
			Angle updatedOrientation = oMobileObj.getOrientation().add(mnDeviationAngle);
			this.setPose(oMobileObj.getPosition(), updatedOrientation); 
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, 
										new sim.util.Double2D(oMobileObj.getPosition().x,oMobileObj.getPosition().y));
		}
		
		// same as setOrientationAndPosition() but wit a deviation angle for focused vision
		// deviationAngle is in radians
		public void setFocusedOrientation(double deviationAngle){
			mnDeviationAngle = deviationAngle;
			//System.out.println("@@@ setFocusedOrientation: " + deviationAngle);
//			clsMobileObject2D oMobileObj = ((clsMobile)moHostEntity).getMobileObject2D();
//			Angle updatedOrientation = oMobileObj.getOrientation().add(deviationAngle);
//			this.setPose(oMobileObj.getPosition(), updatedOrientation); 
//			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, 
//										new sim.util.Double2D(oMobileObj.getPosition().x,oMobileObj.getPosition().y));
		}
			
		private void clearList(){
			meDetectedObjList.clear(); 
		}
		
		/**
		 * @author kohlhauser
		 * 18 Nov 2009, 11:51:29
		 * 
		 * @return the moHostEntity
		 */
		public itfEntity getEntity() {
			return moHostEntity;
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
		public void addContact(PhysicalObject2D poCollidingObj,Double2D poCollisionPoint){
			if (poCollisionPoint != null && poCollisionPoint.length()>= 0){
				clsPolarcoordinate oCoord = new clsPolarcoordinate( moHostEntity.getPose().getPosition(), poCollidingObj.getPosition() ); 
				meDetectedObjList.add(new clsCollidingObject(poCollidingObj, oCoord, null ));
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

		/** MW
		 * @since 04.03.2013 10:57:53
		 * 
		 * @return the moShape
		 */
		public clsCircleBorder getMoShape() {
			return moShape;
		}

		/** MW
		 * @since 04.03.2013 10:57:53
		 * 
		 * @param moShape the moShape to set
		 */
		public void setMoShape(clsCircleBorder moShape) {
			this.moShape = moShape;
		}		
}

