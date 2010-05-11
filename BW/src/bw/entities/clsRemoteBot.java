/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import config.clsBWProperties;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.utils.enums.eBodyAttributes;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eShapeType;
import bw.body.clsComplexBody;
import bw.body.itfGetBrain;
import bw.body.attributes.clsAttributeAntenna;
import bw.body.attributes.clsAttributeEye;
import bw.body.attributes.clsAttributeHand;
import bw.body.attributes.clsAttributes;
import bw.body.brainsocket.clsBrainSocket;
import bw.body.io.clsExternalIO;
import bw.body.itfget.itfGetBotHand;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetSensorEngine;
import bw.entities.tools.clsShapeCreator;
import sim.display.clsKeyListener;
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

public class clsRemoteBot extends clsAnimate implements itfGetSensorEngine, itfGetRadiation, itfGetBotHand  {
	public static final String P_HANDSIZE = "handsize";
	public static final String P_HANDOFFSETX = "handoffsetx";
	public static final String P_HANDOFFSETY = "handoffsety";
	
    private clsBotHands moBotHand1;
    private clsBotHands moBotHand2;
	
    public clsRemoteBot(itfDecisionUnit poDU, String poPrefix, clsBWProperties poProp) {
		super(poDU, poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		addBotHands( pre, poProp ); 
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) ); 
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//add correct default sensor values (three range vision)
		oProp.removeKeysStartingWith(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS);
		oProp.putAll( clsExternalIO.getDefaultSensorProperties(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS, true));
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.CYAN);

		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
		oProp.setProperty(pre+P_HANDSIZE, 1.0);
		oProp.setProperty(pre+P_HANDOFFSETX, 12.0);
		oProp.setProperty(pre+P_HANDOFFSETY, 6.0);		
				
		
		//add attributes
		String att_pre = pre+P_BODY+"."+clsComplexBody.P_ATTRIBUTES+".";
		int num = oProp.getPropertyInt(att_pre+clsAttributes.P_NUMATTRIBUTES);
		
		oProp.putAll( clsAttributeAntenna.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.ANTENNA_LEFT.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;

		oProp.putAll( clsAttributeAntenna.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.ANTENNA_RIGHT.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;

		oProp.putAll( clsAttributeEye.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.EYE.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;
		
		oProp.putAll( clsAttributeHand.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.HAND_LEFT.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;
		
		oProp.putAll( clsAttributeHand.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.HAND_RIGHT.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;
		
		oProp.setProperty(att_pre+clsAttributes.P_NUMATTRIBUTES, num);	
		
		return oProp;
	}
	

	
	private clsBotHands addHand(double offsetX, double offsetY, double radius, Color poColor) {
        double x = getPosition().x;
        double y = getPosition().y;
        sim.physics2D.util.Double2D oPos;
                    
        oPos = new sim.physics2D.util.Double2D(x + offsetX, y + offsetY);
        
        clsBotHands oHand = new clsBotHands(oPos, new sim.physics2D.util.Double2D(0, 0), radius, poColor);
        
        return oHand;
	}
	
	/**
	 * To be called AFTER clsRemoteBot has been initialized and registered to mason 
	 * 
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:38:59
	 *
	 */
	private void addBotHands(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		//FIXME hands are only added correctly if - and only if - direction of bot is 0 ...
		//Angle oDirection = new Angle(getMobileObject2D().getOrientation().radians); //TODO add getDirection to clsEntity
		getMobileObject2D().setPose(getPosition(), new Angle(0));

		Color oColor = poProp.getPropertyColor(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR); 
		double offsetX = poProp.getPropertyDouble(pre+P_HANDOFFSETX); 
		double offsetY = poProp.getPropertyDouble(pre+P_HANDOFFSETY); 
		double radius = poProp.getPropertyDouble(pre+P_HANDSIZE); 

		moBotHand1 = addHand(offsetX, offsetY, radius, oColor);
		moBotHand2 = addHand(offsetX, -offsetY, radius, oColor);

	}
	
	public clsBotHands getBotHandLeft() {
		return moBotHand1;
	}
	public clsBotHands getBotHandRight() {
		return moBotHand2;
	}
	
	@Override
	public void processing() {
		if (moBody instanceof itfGetBrain) {
			clsBrainSocket oSocket = ((itfGetBrain)moBody).getBrain();
			oSocket.setKeyPressed(clsKeyListener.getKeyPressed());
			 ((itfGetBrain)moBody).getBrain().stepProcessing();
		}
	}
		
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.REMOTEBOT;
	}	
}