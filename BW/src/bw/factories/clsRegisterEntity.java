/**
 * @author deutsch
 * 25.02.2009, 15:22:23
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import sim.physics2D.constraint.PinJoint;
import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetVision;
import bw.entities.clsAnimate;
import bw.entities.clsBubble;
import bw.entities.clsMobile;
import bw.entities.clsRemoteBot;
import bw.entities.clsStationary;
import bw.physicalObjects.bodyparts.clsBotHands;


/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 15:22:23
 * 
 */
public final class clsRegisterEntity {

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
	public static void registerMobileObject2D(clsMobileObject2D poMobileObject2D) {
		registerPhysicalObject2D(poMobileObject2D);
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poMobileObject2D);			
	}
	
	public static void registerStationaryObject2D(clsStationaryObject2D poStationaryObject2D) {
		registerPhysicalObject2D(poStationaryObject2D);		
	}
	
	public static void registerEntity(clsMobile poEntity) {
		registerMobileObject2D((clsMobileObject2D)poEntity.getMobileObject2D());
		poEntity.setRegistered(true);
	}
	
	public static void registerEntity(clsStationary poEntity) {
		registerStationaryObject2D(poEntity.getStationaryObject2D());
		poEntity.setRegistered(true);		
	}

	public static void registerBotHands(clsBotHands poBotHand) {
		clsSingletonMasonGetter.getPhysicsEngine2D().register(poBotHand);
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation( poBotHand, new sim.util.Double2D(poBotHand.getPosition().getX(), poBotHand.getPosition().getY()) );
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poBotHand);
	}
	
	public static void registerEntity(clsRemoteBot poEntity) {
		registerMobileObject2D(poEntity.getMobileObject2D());
		
		registerPhysicalObject2D(poEntity.getVision() );
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(poEntity.getVision(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poEntity.getVision());
		
		registerPhysicalObject2D(poEntity.getEatableAreaVision() );
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(poEntity.getEatableAreaVision(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(poEntity.getEatableAreaVision());
		
		registerBotHands(poEntity.getBotHand1());
		registerBotHands(poEntity.getBotHand2());
		
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getMobileObject2D(), poEntity.getBotHand1());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getMobileObject2D(), poEntity.getBotHand2());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getMobileObject2D());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getBotHand1());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getVision(),poEntity.getBotHand2());
		
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getEatableAreaVision(),poEntity.getMobileObject2D());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getEatableAreaVision(),poEntity.getBotHand1());
		clsSingletonMasonGetter.getPhysicsEngine2D().setNoCollisions(poEntity.getEatableAreaVision(),poEntity.getBotHand2());
		
        PinJoint oPJ1 = new PinJoint(poEntity.getBotHand1().getPosition(), poEntity.getBotHand1(), poEntity.getMobileObject2D());
        PinJoint oPJ2 = new PinJoint(poEntity.getBotHand2().getPosition(), poEntity.getBotHand2(), poEntity.getMobileObject2D());
            
        clsSingletonMasonGetter.getPhysicsEngine2D().register(oPJ1);
        clsSingletonMasonGetter.getPhysicsEngine2D().register(oPJ2);
        
 
		poEntity.setRegistered(true);
	}
	
	public static void registerEntity(clsAnimate poEntity) {
		registerMobileObject2D(poEntity.getMobileObject2D());
		
		if (poEntity instanceof itfGetVision) {
			registerPhysicalObject2D(((itfGetVision)poEntity).getVision() );
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(((itfGetVision)poEntity).getVision(), new sim.util.Double2D(poEntity.getPosition().x, poEntity.getPosition().y));
			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(((itfGetVision)poEntity).getVision());
		}
		
		if (poEntity instanceof itfGetEatableArea) {
			registerPhysicalObject2D(((itfGetEatableArea)poEntity).getEatableArea() );
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(((itfGetEatableArea)poEntity).getEatableArea(), new sim.util.Double2D( (((itfGetEatableArea)poEntity).getEatableArea().getPosition().x+10), ((itfGetEatableArea)poEntity).getEatableArea().getPosition().y));
			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(((itfGetEatableArea)poEntity).getEatableArea());
		}
		
		poEntity.setRegistered(true);
	}

}
