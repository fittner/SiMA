/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.TreeMap;

import du.utils.enums.eDecisionType;

import bw.physicalObjects.bodyparts.clsBotHands;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
import bw.body.io.sensors.ext.clsSensorEngine;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetSensorEngine;
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

public class clsRemoteBot extends clsAnimate implements itfGetVision, itfGetRadiation, itfGetEatableArea, itfGetSensorEngine  {
	public static final String P_BOT_RADIUS = "bot_radius";
	
	public static final String P_BOT_HAND_COLOR_R = "bot_hand_color_r";
	public static final String P_BOT_HAND_COLOR_G = "bot_hand_color_g";
	public static final String P_BOT_HAND_COLOR_B = "bot_hand_color_b";
	
    private clsBotHands moBotHand1;
    private clsBotHands moBotHand2;
       
	private Color moDefaultHandColor; // = Color.gray;
	
	private int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();


	public clsRemoteBot(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
			applyProperties(poPrefix, poProp);
			
			addBotHands();
		}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, "DU_DUMB_MIND_A");
		
		oProp.setProperty(pre+P_MOBILE_SHAPE_TYPE, eShapeType.SHAPE_CIRCLE.name());
		oProp.setProperty(pre+P_BOT_RADIUS, "10.0");
		oProp.setProperty(pre+P_ENTITY_COLOR_R, Color.CYAN.getRed());
		oProp.setProperty(pre+P_ENTITY_COLOR_G, Color.CYAN.getGreen());
		oProp.setProperty(pre+P_ENTITY_COLOR_B, Color.CYAN.getBlue());
		
		//bot-hand color
		oProp.setProperty(pre+P_BOT_HAND_COLOR_R, Color.gray.getRed());
		oProp.setProperty(pre+P_BOT_HAND_COLOR_G, Color.gray.getGreen());
		oProp.setProperty(pre+P_BOT_HAND_COLOR_B, Color.gray.getBlue());
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moDefaultHandColor = new Color(poProp.getPropertyFloat(P_BOT_HAND_COLOR_R),
	    		    					poProp.getPropertyFloat(P_BOT_HAND_COLOR_G),
	    		    					poProp.getPropertyFloat(P_BOT_HAND_COLOR_B));
		addBotHands(); //in the defined color above....

		moDecisionType = eDecisionType.valueOf( poProp.getPropertyString(pre+P_DECISION_TYPE) );
		//create the defined decision unit...
		setDecisionUnit(moDecisionType);
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
	
	@Override
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
	
	//ZEILINGER - integrate SensorEngine 
	@Override
	public TreeMap <Double,clsEntitySensorEngine> getSensorEngine()
	{
		return ((clsSensorEngine)this.moBody
					.getExternalIO().moSensorEngine).getMeSensorAreas(); 
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