/**
 * @author Benny D�nz
 * 21.06.2009, 13:13:07
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import complexbody.internalSystems.clsFastMessengerSystem;
import complexbody.io.actuators.clsActionExecutor;

import singeltons.eImages;

import body.clsComplexBody;
import body.itfget.itfGetBody;
import du.enums.eSensorExtType;
import du.itf.actions.*;
import entities.abstractEntities.clsEntity;
import entities.actionProxies.*;
import entities.enums.eBodyParts;

/**
 * Action Executor for attack/bite
 * Proxy itfAPAttackableBite
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prForceScalingFactor = Scales the force applied to the force felt by the entity to be killed (default = 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorAttackBite extends clsActionExecutor{

	static double srStaminaBase = 4f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Force) ; 			
	static double srStaminaScalingFactor = 0; //0.001f;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;
	
	private double mrForceScalingFactor;

	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_FORCECALINGFACTOR = "forcescalingfactor";

	public clsExecutorAttackBite(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionEat.class);

		applyProperties(poPrefix,poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		oProp.setProperty(pre+P_FORCECALINGFACTOR, 3f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
		mrForceScalingFactor=poProp.getPropertyFloat(pre+P_FORCECALINGFACTOR);
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_ATTACKBITE;
	}
	@Override
	protected void setName() {
		moName="Attack/Bite executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		clsActionAttackBite oCommand =(clsActionAttackBite) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,oCommand.getForce()) ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionAttackBite oCommand =(clsActionAttackBite) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Is something in range
		itfAPAttackableBite oOpponent = (itfAPAttackableBite) findEntityInRange(moEntity, oBody, moRangeSensor ,itfAPAttackableBite.class) ;

		if (oOpponent==null) {
			//Nothing in range then send fast Messenger
			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
			oFastMessengerSystem.addMessage(mePartId, eBodyParts.BRAIN, 1);
			return false;
		} 

		//Check if biting is ok
		double rDamage = oOpponent.tryBite(oCommand.getForce()*mrForceScalingFactor);
		if (rDamage>0) {
			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
			return false;
		}
		moEntity.setOverlayImage(eImages.Overlay_Action_AttackBite);
		//Bite!
		oOpponent.bite(oCommand.getForce()*mrForceScalingFactor);
		
        clsAction oAction = new clsAction(1);
        oAction.setActionName("ATTACK_BITE");
        oAction.attachEntity((clsEntity)oOpponent);
        moEntity.addAction(oAction);
		
		return true;
	}	

}
