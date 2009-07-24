/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import du.utils.enums.eDecisionType;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetVision;
import bw.entities.tools.clsShapeCreator;
import enums.eEntityType;
import sim.physics2D.util.Angle;

/**
 * Sample implementation of a clsAnimate, having sensors and actuators 
 * (therefore the cycle sensing and executing implemented). The thinking
 * is in this instance out-sourced to the human user directing the entity 
 * across defined keys on her/his keyboard
 * 
 * @author langr
 * 
 */

public class clsRemoteBot extends clsAnimate implements itfGetVision, itfGetRadiation, itfGetEatableArea  {
    private clsBotHands moBotHand1;
    private clsBotHands moBotHand2;
	
    public clsRemoteBot(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		addBotHands( poProp.getPropertyColor(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR) ); 
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.DUMB_MIND_A.name());
		
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.CYAN);

		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
		return oProp;
	}
	

	
	private clsBotHands addHand(double offsetX, double offsetY, Color poColor) {
        double x = getPosition().x;
        double y = getPosition().y;
        sim.physics2D.util.Double2D oPos;
                    
        oPos = new sim.physics2D.util.Double2D(x + offsetX, y + offsetY);
        
        clsBotHands oHand = new clsBotHands(oPos, new sim.physics2D.util.Double2D(0, 0), 1, poColor);
        
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
	private void addBotHands(Color poColor) {
		//FIXME hands are only added correctly if - and only if - direction of bot is 0 ...
		Angle oDirection = new Angle(getMobileObject2D().getOrientation().radians); //TODO add getDirection to clsEntity
		getMobileObject2D().setPose(getPosition(), new Angle(0));
		
		moBotHand1 = addHand(12, 6, poColor);
		moBotHand2 = addHand(12, -6, poColor);

		getMobileObject2D().setPose(getPosition(), oDirection);
	}
	
	public clsBotHands getBotHand1() {
		return moBotHand1;
	}
	public clsBotHands getBotHand2() {
		return moBotHand2;
	}
		
	//ZEILINGER Has to be Integrated to Animated too
	/*returns the Vision Sensor*/
//	public clsSensorVisionNEW getVision_new()
//	{
//		return (clsSensorVisionNEW)moBody
//					.getExternalIO().moSensorExternal
//					.get(enums.eSensorExtType.VISION_new); 
//	}
//	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.REMOTEBOT;
	}

}