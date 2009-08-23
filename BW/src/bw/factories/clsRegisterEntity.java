/**
 * @author deutsch
 * 25.02.2009, 15:22:23
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import java.util.TreeMap;

import sim.engine.Schedule;
import sim.physics2D.constraint.PinJoint;
import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.itfget.itfGetSensorEngine;
import bw.entities.clsAnimate;
import bw.entities.clsMobile;
import bw.entities.clsRemoteBot;
import bw.entities.clsStationary;
import bw.entities.clsBase;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.physicalObjects.sensors.clsEntitySensorEngine;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 15:22:23
 * 
 */
public final class clsRegisterEntity {
	
	public final static double defaultScheduleStepWidth = 1.0;

	public static void registerPhysicalObject2D(PhysicalObject2D poPhysicalObject2D) {
		clsSingletonMasonGetter.getPhysicsEngine2D().register(poPhysicalObject2D);
	}
	
	public static void unRegisterPhysicalObject2D(PhysicalObject2D poPhysicalObject2D) {
		clsSingletonMasonGetter.getPhysicsEngine2D().unRegister(poPhysicalObject2D);
		clsSingletonMasonGetter.getFieldEnvironment().allObjects.remove(poPhysicalObject2D);
	}
	
	/**
	 *
	 * This function registers mason's PhysicalObject2D in the
	 * - mason physics engine
	 * - mason framework to call the step-method
	 * 
	 * It is MANDATORY to call this function from outside (a clsMobile-Instance) 
	 *
	 * @author langr
	 * 25.02.2009, 14:22:58
	 */	
	public static void registerMobileObject2D(final clsMobileObject2D poMobileObject2D) {
		registerPhysicalObject2D(poMobileObject2D);
		Schedule s = clsSingletonMasonGetter.getSimState().schedule;

		/* schedule the various steps */
		s.scheduleRepeating(poMobileObject2D.getSteppableBeforeStepping(), 1, defaultScheduleStepWidth);
		s.scheduleRepeating(poMobileObject2D.getSteppableSensing(), 2, defaultScheduleStepWidth);
		s.scheduleRepeating(poMobileObject2D.getSteppableUpdateInternalState(), 3, defaultScheduleStepWidth);
		s.scheduleRepeating(poMobileObject2D.getSteppableProcessing(), 4, defaultScheduleStepWidth);
		s.scheduleRepeating(poMobileObject2D.getSteppableExecution(), 5, defaultScheduleStepWidth);
		s.scheduleRepeating(poMobileObject2D.getSteppableAfterStepping(), 6, defaultScheduleStepWidth);
	}
	
	public static void registerStationaryObject2D(clsStationaryObject2D poStationaryObject2D) {
		registerPhysicalObject2D(poStationaryObject2D);		
		Schedule s = clsSingletonMasonGetter.getSimState().schedule;
		s.scheduleRepeating(poStationaryObject2D.getSteppableSensing(), 2, defaultScheduleStepWidth); 
		s.scheduleRepeating(poStationaryObject2D.getSteppableProcessing(), 4, defaultScheduleStepWidth);
	}
	
	public static void registerEntity(clsMobile poEntity) {
		registerMobileObject2D((clsMobileObject2D)poEntity.getMobileObject2D());
		poEntity.setRegistered(true);
	}
	
	public static void registerEntity(clsStationary poEntity) {
		registerStationaryObject2D(poEntity.getStationaryObject2D());
		poEntity.setRegistered(true);		
	}
	
	/**
	 * 
	 * Registers Base (stationary) object and it's eatable area
	 * 
	 * TODO (horvath) - generalize -> create a general stationary object with eatable area
	 *
	 * @author horvath
	 * 13.07.2009, 14:46:52
	 *
	 * @param poEntity
	 */
	public static void registerEntity(clsBase poEntity) {
		registerStationaryObject2D(poEntity.getStationaryObject2D());
		poEntity.setRegistered(true);		
					
		registerPhysicalObject2D(poEntity.getEatableAreaVision() );
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(poEntity.getEatableAreaVision(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poEntity.getEatableAreaVision(), 6, defaultScheduleStepWidth);
	}

	public static void registerBotHands(clsBotHands poBotHand) {
		clsSingletonMasonGetter.getPhysicsEngine2D().register(poBotHand);
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation( poBotHand, new sim.util.Double2D(poBotHand.getPosition().getX(), poBotHand.getPosition().getY()) );
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poBotHand, 6, defaultScheduleStepWidth);
	}
	
	public static void registerEntity(clsRemoteBot poEntity) {
		registerMobileObject2D(poEntity.getMobileObject2D());
		
		//ZEILINGER -- integrate SensorEngine - actually only for remoteBot
    	registerSensorEngine(((itfGetSensorEngine)poEntity).getSensorEngineAreas()); 
			
//		registerPhysicalObject2D(poEntity.getVision() );
//		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(poEntity.getVision(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
//		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poEntity.getVision(), 6, defaultScheduleStepWidth);
//		
//		registerPhysicalObject2D(poEntity.getRadiation() );
//		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(poEntity.getRadiation(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
//		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poEntity.getRadiation(), 6, defaultScheduleStepWidth);
		
//		registerPhysicalObject2D(poEntity.getEatableArea() );
//     	clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(poEntity.getEatableArea(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
//		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poEntity.getEatableArea(), 6, defaultScheduleStepWidth);
//	
		registerBotHands(poEntity.getBotHand1());
		registerBotHands(poEntity.getBotHand2());
	
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getMobileObject2D(), poEntity.getBotHand1());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getMobileObject2D(), poEntity.getBotHand2());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getMobileObject2D());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getBotHand1());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getBotHand2());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getEatableArea());
//		
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getRadiation(),poEntity.getMobileObject2D());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getRadiation(),poEntity.getBotHand1());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getRadiation(),poEntity.getBotHand2());
//		
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getEatableArea(),poEntity.getMobileObject2D());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getEatableArea(),poEntity.getBotHand1());
//		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getEatableArea(),poEntity.getBotHand2());
//		
        PinJoint oPJ1 = new PinJoint(poEntity.getBotHand1().getPosition(), poEntity.getBotHand1(), poEntity.getMobileObject2D());
        PinJoint oPJ2 = new PinJoint(poEntity.getBotHand2().getPosition(), poEntity.getBotHand2(), poEntity.getMobileObject2D());
           
        clsSingletonMasonGetter.getPhysicsEngine2D().register(oPJ1);
        clsSingletonMasonGetter.getPhysicsEngine2D().register(oPJ2);
   
		poEntity.setRegistered(true);
	}
	
	public static void registerEntity(clsAnimate poEntity) {
		registerMobileObject2D(poEntity.getMobileObject2D());
		
		//ZEILINGER -- integrate SensorEngine - actually only for remoteBot
 //		if (poEntity instanceof itfGetVision) {
//			registerPhysicalObject2D(((itfGetVision)poEntity).getVision() );
//			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(((itfGetVision)poEntity).getVision(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
//			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(((itfGetVision)poEntity).getVision(), 6, defaultScheduleStepWidth);
//		}
		
		if (poEntity instanceof itfGetSensorEngine) {
			registerSensorEngine(((itfGetSensorEngine)poEntity).getSensorEngineAreas());
		}
		
//		if (poEntity instanceof itfGetRadiation) {
//			registerPhysicalObject2D(((itfGetRadiation)poEntity).getRadiation() );
//			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(((itfGetRadiation)poEntity).getRadiation(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
//			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(((itfGetRadiation)poEntity).getRadiation(), 6, defaultScheduleStepWidth);
//		}
		
//		if (poEntity instanceof itfGetEatableArea) {
//			registerPhysicalObject2D(((itfGetEatableArea)poEntity).getEatableArea() );
//			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(((itfGetEatableArea)poEntity).getEatableArea(), new sim.util.Double2D( poEntity.getPosition().x+10.0, poEntity.getPosition().y));
//			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(((itfGetEatableArea)poEntity).getEatableArea(), 6, defaultScheduleStepWidth);
//		}
		
		poEntity.setRegistered(true);
	}
	
	//ZEILINGER -- integrate Sensor Engine
	public static void registerSensorEngine(TreeMap<Double, clsEntitySensorEngine> peEntities){
			
		for(clsEntitySensorEngine entity : peEntities.values()){	
			registerPhysicalObject2D(entity);
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(entity, new sim.util.Double2D(entity.getPosition().x, entity.getPosition().y));
			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(entity, 6, defaultScheduleStepWidth);
		}
	}

}
