/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;
import java.util.ArrayList;

import org.slf4j.Logger;

import base.datatypes.clsShortTermMemoryMF;
import properties.clsProperties;
import complexbody.io.clsExternalIO;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.sensors.datatypes.enums.eActionKissIntensity;
import complexbody.io.sensors.datatypes.enums.eEntityType;
import body.itfget.itfGetInternalEnergyConsumption;
import control.interfaces.itfDecisionUnit;
import sim.physics2D.shape.Shape;
import singeltons.clsSimState;
import tools.clsPose;
import tools.eImagePositioning;
import utils.clsGetARSPath;


import body.clsComplexBody;
import body.itfGetExternalIO;
import body.attributes.clsAttributeAntenna;
import body.attributes.clsAttributeEye;
import body.attributes.clsAttributes;
import body.itfget.itfGetRadiation;
import body.itfget.itfGetSensorEngine;
import entities.abstractEntities.clsAnimate;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsMobile;
import entities.actionProxies.itfAPAttackableBite;
import entities.actionProxies.itfAPBeatable;
import entities.actionProxies.itfAPKissable;
import entities.actionProxies.itfTransferable;
import entities.enums.eBodyAttributes;
import entities.enums.eBodyType;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;
//import tstBw.*;

/**
 * Host of the arsin body and brain, now worling in v38g
 * 
 * @author langr
 * 
 */
public class clsARSIN extends clsAnimate implements itfGetSensorEngine, itfGetRadiation, itfAPKissable, itfAPBeatable, itfAPAttackableBite, itfTransferable {
	public static final String P_SHAPE_ALIVE		= "shape_alive";
	public static final String P_SHAPE_DEAD 		= "shape_dead";
	public static final String P_ALIVE              = "alive";
	public static final String P_IMMORTAL			= "immortal"; 
	
	
	private Shape moAlive2D;
	private Shape moDead2D;
	private final Logger logtiming = logger.clsLogger.getLog("Timing");
		
	private boolean mnAlive;
	private boolean mnImmortal;
	
	private final int P_DECISION_CYCLUS = 9;
	private int executionCylce =0;
	private boolean executeDU=true;
	
	protected final Logger log;
	
	private int Steps=0;
	

	
	public clsARSIN(itfDecisionUnit poDU, String poPrefix, clsProperties poProp, int uid) {
		super(poDU, poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
		
		 log = logger.clsLogger.getLog("ARSIN");

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
		oProp.putAll( clsExternalIO.getDefaultSensorProperties(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS, true, true));

	
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPE_ALIVE);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_RADIUS, 9.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_COLOR, new Color(51,255,51));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "arsin_green.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_RADIUS, 9.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_COLOR, new Color(0,0,0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "arsin_grey.png");
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
		
		moAlive2D = clsShape2DCreator.createShapeWithOverlays(pre+P_SHAPE+"."+P_SHAPE_ALIVE, poProp, true); 
		moDead2D = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_DEAD, poProp);		
		
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
	
	private boolean sens(){
		boolean exec = false;
		if(executionCylce== 0){
			exec = true;
		}		
		return exec;
	}
	
	private boolean execute(){
		boolean exec = false;
		if(executionCylce == P_DECISION_CYCLUS){
			exec = true;
		}		
		return exec;
	}
	@Override
	public void execution() {
		if(execute()){
			Thread.currentThread().setName("ARSIN #"+uid);
			if (isAlive()) {
				log.trace("Step "+clsSimState.getSteps()+": Execute");
				super.execution();
			}
		}
		if(executionCylce == P_DECISION_CYCLUS){
			executionCylce=0;
			executeDU =true;
		}
		else executionCylce ++;
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
		final Logger logFim = logger.clsLogger.getLog("Fim");
		Thread.currentThread().setName("ARSIN #"+uid);
		clsShortTermMemoryMF.setActualStep(Steps++);
		logFim.info("Step: "+Steps);
		logtiming.info("Timing Step: "+Steps);
		//if (this.)
		if (isAlive()) {
			if(executeDU){
				log.trace("Step "+clsSimState.getSteps()+": Processing");
				super.processing();
				//log.trace("executed");
				//log.trace("Generated Commands" + ((clsComplexBody) moBody).getExternalIO().getActionProcessor().getCommandStack().toString());
			}
			else{
				log.trace("Step "+clsSimState.getSteps()+": Processing empty");
			}
		}
		
		if(((clsComplexBody)moBody).getBrain().getActions().size()>0) {
			executeDU=false;
			//executionCylce=0;
		}

		

		//this.getSensorEngineAreas()
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

		if(sens()){
			Thread.currentThread().setName("ARSIN #"+uid);
			if (isAlive()) {
				log.trace("Step "+clsSimState.getSteps()+": Sensing");
				super.sensing();
			}
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
		if(sens()){
			Thread.currentThread().setName("ARSIN #"+uid);
			if (isAlive()) {
				log.trace("Step "+clsSimState.getSteps()+": Update Internal State");
				super.updateInternalState();
			}
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
	
	@Override
	public boolean isAlive() {
		if (mnImmortal) {
			mnAlive = true;
		} else {
			if (mnAlive != ((clsComplexBody)moBody).isAlive()) {
				mnAlive = ((clsComplexBody)moBody).isAlive();
				
			}
		}
		updateShape();
		return mnAlive;
	}	
	
	private void updateShape() {
		 if (!mnAlive) {
			set2DShape(moDead2D, getTotalWeight());
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		} else {
			set2DShape(moAlive2D, getTotalWeight());
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		}
		 
		 setLifeValue(((clsComplexBody)moBody).getRelativeHealthValue());
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
	
	public void clearInternalActionsStack(){
		((clsComplexBody)moBody).getInternalActionProcessor().clear();
	}
	
	public ArrayList<clsInternalActionCommand> getInternalActions(){
		
		return ((clsComplexBody)moBody).getInternalActionProcessor().getCommandStack();
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 11, 2012 4:14:00 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget, Inspector poSuperInspector,
			LocationWrapper poWrapper, GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorARSin(poSuperInspector, poWrapper, poState, (clsARSIN)poEntity), "ARSIN");
		
	}*/

	/* (non-Javadoc)
	 *
	 * @since 17.07.2013 12:01:17
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPBeatable#beat(double)
	 */
	@Override
	public void beat(double pfForce) {
		double rHurtFactor = 0.5;
		double rBeatThreshold = 0.2;
		
		if(pfForce >= rBeatThreshold){
			if(moBody instanceof clsComplexBody){
				clsComplexBody oBody = (clsComplexBody) moBody;
				oBody.getInternalSystem().getHealthSystem().hurt(pfForce * rHurtFactor* 0.1);
				
				
			}
		}
		else{
			//nothing happens
		}
	}
	
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsEntityFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
		double x = this.getPose().getPosition().x;
		double y = this.getPose().getPosition().y;
		double angle = this.getPose().getAngle().radians;
		double weight = this.getVariableWeight();
		
		//set position
		oNewEntity.setPose(new clsPose(x-(poDistance/2), y, angle));
		this.setPose(new clsPose(x+(poDistance/2), y, angle));
		//set weight
		oNewEntity.setVariableWeight(weight*poSplitFactor);
		this.setVariableWeight(weight*(1-poSplitFactor));
		
		return oNewEntity;

	}

	/* (non-Javadoc)
	 *
	 * @since 16.09.2013 13:44:47
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPAttackableBite#tryBite(double)
	 */
	@Override
	public double tryBite(double pfForce) {
		return 0.0;
	}

	/* (non-Javadoc)
	 *
	 * @since 16.09.2013 13:44:47
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPAttackableBite#bite(double)
	 */
	@Override
	public void bite(double pfForce) {
		double rHurtFactor = 0.5;
		
			if(moBody instanceof clsComplexBody){
				clsComplexBody oBody = (clsComplexBody) moBody;
				oBody.getInternalSystem().getHealthSystem().hurt(pfForce * rHurtFactor);
				
				
			}
		}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2015 7:48:43 PM
	 * 
	 * @see entities.actionProxies.itfTransferable#getEntityToTransfer(double)
	 */
	@Override
	public clsMobile getTransferedEntity() {
		// TODO (zhukova) - Auto-generated method stub
		return this;
	}
		
	

}
