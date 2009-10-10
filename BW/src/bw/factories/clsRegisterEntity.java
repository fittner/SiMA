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
		registerPhysicalObject2D(poEntity.getStationaryObject2D());		
		Schedule s = clsSingletonMasonGetter.getSimState().schedule;
		s.scheduleRepeating(poEntity.getStationaryObject2D().getSteppableSensing(), 2, defaultScheduleStepWidth); 
		s.scheduleRepeating(poEntity.getStationaryObject2D().getSteppableProcessing(), 4, defaultScheduleStepWidth);
		registerSensorEngine(((itfGetSensorEngine)poEntity).getSensorEngineAreas());
		poEntity.setRegistered(true);
	}

	public static void registerBotHands(clsBotHands poBotHand) {
		clsSingletonMasonGetter.getPhysicsEngine2D().register(poBotHand);
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation( poBotHand, new sim.util.Double2D(poBotHand.getPosition().getX(), poBotHand.getPosition().getY()) );
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poBotHand, 6, defaultScheduleStepWidth);
	}
	
	public static void registerEntity(clsRemoteBot poEntity) {
		registerMobileObject2D(poEntity.getMobileObject2D());
		
	   	registerSensorEngine(((itfGetSensorEngine)poEntity).getSensorEngineAreas()); 
		
	   	registerBotHands(poEntity.getBotHandLeft());
		registerBotHands(poEntity.getBotHandRight());
	
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getMobileObject2D(), poEntity.getBotHandLeft());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getMobileObject2D(), poEntity.getBotHandRight());

        PinJoint oPJ1 = new PinJoint(poEntity.getBotHandLeft().getPosition(), poEntity.getBotHandLeft(), poEntity.getMobileObject2D());
        PinJoint oPJ2 = new PinJoint(poEntity.getBotHandRight().getPosition(), poEntity.getBotHandRight(), poEntity.getMobileObject2D());
           
        clsSingletonMasonGetter.getPhysicsEngine2D().register(oPJ1);
        clsSingletonMasonGetter.getPhysicsEngine2D().register(oPJ2);
   
		poEntity.setRegistered(true);
	}
	
	public static void registerEntity(clsAnimate poEntity) {
		registerMobileObject2D(poEntity.getMobileObject2D());
		
		if (poEntity instanceof itfGetSensorEngine) {
			registerSensorEngine(((itfGetSensorEngine)poEntity).getSensorEngineAreas());
		}

		poEntity.setRegistered(true);
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - register the Sensor Engine
	 *
	 * @author zeilinger
	 * 02.09.2009, 14:06:27
	 *
	 * @param peEntities
	 */
	public static void registerSensorEngine(TreeMap<Double, clsEntitySensorEngine> peEntities){
			
		for(clsEntitySensorEngine entity : peEntities.values()){	
			registerPhysicalObject2D(entity);
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(entity, new sim.util.Double2D(entity.getPosition().x, entity.getPosition().y));
			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(entity, 6, defaultScheduleStepWidth);
		}
	}

}
