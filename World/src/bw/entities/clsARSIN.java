/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.ArrayList;

import javax.media.j3d.TransformGroup;

import sim.physics2D.shape.Shape;
import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;

import config.clsProperties;

import du.enums.eActionKissIntensity;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import du.itf.actions.clsActionCommand;
import bw.body.clsComplexBody;
import bw.body.itfGetExternalIO;
import bw.body.attributes.clsAttributeAntenna;
import bw.body.attributes.clsAttributeEye;
import bw.body.attributes.clsAttributes;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetSensorEngine;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.clsShape3DCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBodyAttributes;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eShapeType;
import bw.body.io.clsExternalIO;
import bw.body.io.actuators.actionProxies.itfAPKissable;

//import tstBw.*;

/**
 * Host of the arsin body and brain
 * 
 * @author langr
 * 
 */
public class clsARSIN extends clsAnimate implements itfGetSensorEngine, itfGetRadiation, itfAPKissable {
	public static final String P_SHAPE_ALIVE		= "shape_alive";
	public static final String P_SHAPE_DEAD 		= "shape_dead";
	public static final String P_ALIVE              = "alive";
	public static final String P_IMMORTAL			= "immortal"; 
	
	private Shape moAlive2D;
	private Shape moDead2D;
	private TransformGroup moAlive3D;
	private TransformGroup moDead3D;
	
	private boolean mnAlive;
	private boolean mnImmortal;
	
	public clsARSIN(itfDecisionUnit poDU, String poPrefix, clsProperties poProp, int uid) {
		super(poDU, poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_ALIVE, true);
		oProp.setProperty(pre+P_IMMORTAL, false);
		
		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsEntity.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//add correct default sensor values (three range vision)
		oProp.removeKeysStartingWith(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS);
		oProp.putAll( clsExternalIO.getDefaultSensorProperties(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS, true));

	
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPE_ALIVE);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_COLOR, new Color(51,255,51));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/arsin_green.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_COLOR, new Color(0,0,0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/arsin_grey.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		

		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
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
		
		oProp.setProperty(att_pre+clsAttributes.P_NUMATTRIBUTES, num);		
		
		return oProp;
	}


	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		moAlive2D = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_ALIVE, poProp); 
		moDead2D = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_DEAD, poProp);		
		moAlive3D = clsShape3DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_ALIVE, poProp); 
		moDead3D = clsShape3DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_DEAD, poProp);		
		
		mnAlive = poProp.getPropertyBoolean(pre+P_ALIVE);
		mnImmortal = poProp.getPropertyBoolean(pre+P_IMMORTAL);
		if (mnImmortal) {
			mnAlive = true;
		}
		
		updateShape();
	}
	
	// TODO: this code should be transferred to the entities inspector class - used only for inspectors
	public double getInternalEnergyConsuptionSUM() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getMergedList().values().toArray();	}
	public Object[] getSensorExternal() {
		if (moBody instanceof itfGetExternalIO) {
			return ((itfGetExternalIO)moBody).getExternalIO().moSensorEngine.getMeRegisteredSensors().values().toArray();
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.ARSIN;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#execution()
	 */
	@Override
	public void execution() {
		if (isAlive()) {
			super.execution();
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#processing()
	 */
	@Override
	public void processing() {
		if (isAlive()) {
			super.processing();
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		if (isAlive()) {
			super.sensing();
		}
	}

	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 10:59:18
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		if (isAlive()) {
			super.updateInternalState();
		}
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @author Benny Doenz
	 * 28.08.2009, 18:16:17
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKissable#tryKiss(enums.eActionKissIntensity)
	 */
	@Override
	public boolean tryKiss(eActionKissIntensity peIntensity) {
		return true;
	}
	
	@Override
	public void kiss(eActionKissIntensity peIntensity) {
		double rIntensity=1;
		if (peIntensity==eActionKissIntensity.MIDDLE) rIntensity=2;
		if (peIntensity==eActionKissIntensity.STRONG) rIntensity=4;

		((clsComplexBody) this.getBody()).getInterBodyWorldSystem().getEffectKiss().kiss(null, rIntensity);
	}
	
	public boolean isAlive() {
		if (mnImmortal) {
			mnAlive = true;
		} else {
			if (mnAlive != ((clsComplexBody)moBody).isAlive()) {
				mnAlive = ((clsComplexBody)moBody).isAlive();
				updateShape();
			}
		}
		
		return mnAlive;
	}	
	
	private void updateShape() {
		 if (!mnAlive) {
			clsEventLogger.add(new Event(this, getId(), eEvent.DEAD, ""));
			set2DShape(moDead2D, getTotalWeight());
			set3DShape(moDead3D);
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		} else {
			clsEventLogger.add(new Event(this, getId(), eEvent.ALIVE, ""));
			set2DShape(moAlive2D, getTotalWeight());
			set3DShape(moAlive3D);
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		}
	}	
	
	/**
	 * Function to extract the current actions from the arsin
	 *
	 *(wendt)
	 *
	 * @since 06.04.2012 16:16:25
	 *
	 * @return
	 */
	public ArrayList<clsActionCommand> getActions(){
		
		return ((clsComplexBody)moBody).getExternalIO().getActionProcessor().getCommandStack();
	}
	
	
	/**
	 * (wendt)
	 *
	 * @since 06.04.2012 16:16:52
	 *
	 */
	public void clearStack(){
		((clsComplexBody)moBody).getExternalIO().getActionProcessor().clear();
	}

}
