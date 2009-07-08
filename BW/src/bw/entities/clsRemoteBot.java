/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import ARSsim.physics2D.util.clsPose;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetVision;
import enums.eEntityType;
import sim.display.clsKeyListener;
import sim.physics2D.util.Angle;
import simple.remotecontrol.clsRemoteControl;
import statictools.clsSingletonUniqueIdGenerator;

/**
 * Sample implementation of a clsAnimate, having sensors and actuators 
 * (therefore the cycle sensing and executing implemented). The thinking
 * is in this instance out-sourced to the human user directing the entity 
 * across defined keys on her/his keyboard
 * 
 * @author langr
 * 
 */
public class clsRemoteBot extends clsAnimate implements itfGetVision, itfGetEatableArea  {
    private clsBotHands moBotHand1;
    private clsBotHands moBotHand2;
       
	private static double mrDefaultWeight = 100.0f;
	private static double mrDefaultRadius = 10.0f;
	private static Color moDefaultColor = Color.CYAN;
	private static Color moDefaultHandColor = Color.gray;
	
	private int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();

//	private Array moCapturedKeys;

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 26.02.2009, 11:29:23
	 *
	 * @param pnId
	 * @param poStartingPose
	 * @param poStartingVelocity
	 */
	public clsRemoteBot(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poStartingPose, poStartingVelocity, new sim.physics2D.shape.Circle(clsRemoteBot.mrDefaultRadius, clsRemoteBot.moDefaultColor), clsRemoteBot.mrDefaultWeight, clsRemoteBot.getFinalConfig(poConfig));
		
		applyConfig();
		
		addBotHands();
		setDecisionUnit(new clsRemoteControl());		
	}
	
	public clsBaseBody createBody() {
		return  new clsComplexBody(this, (clsConfigMap)moConfig.get(eConfigEntries.BODY));
	}	
	
	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}	
	
	private clsBotHands addHand(double offsetX, double offsetY) {
        double x = getPosition().x;
        double y = getPosition().y;
        sim.physics2D.util.Double2D oPos;
                    
        oPos = new sim.physics2D.util.Double2D(x + offsetX, y + offsetY);
        
        clsBotHands oHand = new clsBotHands(oPos, new sim.physics2D.util.Double2D(0, 0), 1, moDefaultHandColor);
        
        return oHand;
	}
	
	
	
	/**
	 * To be called AFTER clsRemoteBot has been initialized and registered to mason 
	 * 
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:38:59
	 *
	 */
	private void addBotHands() {
		//FIXME hands are only added correctly if - and only if - direction of bot is 0 ...
		Angle oDirection = new Angle(getMobileObject2D().getOrientation().radians); //TODO add getDirection to clsEntity
		getMobileObject2D().setPose(getPosition(), new Angle(0));
		
		moBotHand1 = addHand(12, 6);
		moBotHand2 = addHand(12, -6);

		getMobileObject2D().setPose(getPosition(), oDirection);
	}
	
	public clsBotHands getBotHand1() {
		return moBotHand1;
	}
	public clsBotHands getBotHand2() {
		return moBotHand2;
	}
	
	public clsEntityPartVision getVision()
	{
		return ((clsSensorVision)moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.VISION)).getMoVisionArea(); 
	}
	
	public clsEntityPartVision getEatableAreaVision()
	{
		return ((clsSensorEatableArea)moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.EATABLE_AREA)).getMoVisionArea(); 
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.REMOTEBOT;
	}
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {

		((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		moBody.getBrain().stepProcessing();
	}

	/**
	 * @author deutsch
	 * 08.07.2009, 15:05:13
	 * 
	 * @return the mnUniqueId
	 */
	public int getUniqueId() {
		return mnUniqueId;
	}
	
}