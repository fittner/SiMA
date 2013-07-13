/**
 * clsSpeech.java: BW - bw.entities
 * 
 * @author MW
 * 
 */
package bw.entities;

import java.awt.Color;
import java.util.ArrayList;

import sim.physics2D.shape.Shape;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.util.clsPolarcoordinate;
import ARSsim.physics2D.util.clsPose;
import bw.body.interBodyWorldSystems.clsCreateSpeech;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.enums.eShapeType;
import config.clsProperties;
import du.enums.eEntityType;
import du.itf.tools.clsAbstractSpeech;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW 
 * 
 * 27.02.2013, 12:43:12
 */

public class clsSpeech extends clsPhysical {
	public static final String P_ALIVETIME = "alivetime";
	public static final String P_ECHO = "echo";
	public static final String P_SHAPENAME = "speech";
	public static final String P_PLACEMENTDISTANCE = "placement_distance";
	public static final String P_PLACEMENTDIRECTION = "placement_direction";
	
	private clsAbstractSpeech moAbstractSpeech;
	private clsCreateSpeech moCreateSpeech; 
	private int miEcho;
	private Shape moReceive1;
	private Shape moReceive2;
	private double mdMass;
	private double mdPlacementDistance;
	private double mdPlacementDirection;
	
	int miAliveTime;
	
	public clsSpeech(String poPrefix, clsProperties poProp, int uid, clsAbstractSpeech oAbstractSpeech, clsCreateSpeech oCreateSpeech, int iEcho) {
		super(poPrefix, poProp, uid);
		
		moAbstractSpeech = oAbstractSpeech;
		moCreateSpeech = oCreateSpeech;
		miEcho=iEcho;
		applyProperties(poPrefix, poProp);
	}

	private void applyProperties(String poPrefix, clsProperties poProp){		
		String pre = clsProperties.addDot(poPrefix);
		miAliveTime = poProp.getPropertyInt(pre+P_ALIVETIME);	
		moReceive1 = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1", poProp);
		moReceive2 = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2", poProp);
		mdMass = poProp.getPropertyDouble(pre+P_STRUCTURALWEIGHT);
		miEcho = poProp.getPropertyInt(pre+P_ECHO);
		mdPlacementDirection = poProp.getPropertyDouble(pre+P_PLACEMENTDIRECTION);
		mdPlacementDistance = poProp.getPropertyDouble(pre+P_PLACEMENTDISTANCE);
		if (this.moAbstractSpeech != null){
			Double test = new Double(0.0);
			if (this.moAbstractSpeech.getMood().compareTo(test.toString()) > 0){
				poProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(0, 255, 0));
				Shape mShape = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPENAME, poProp);
				this.set2DShape(mShape, mdMass);
			}
			else if (this.moAbstractSpeech.getMood().compareTo(test.toString()) < 0){
				poProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(255, 0, 0));
				Shape mShape = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPENAME, poProp);
				this.set2DShape(mShape, mdMass);
			}
		}
	}	
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 0.0001);
		oProp.setProperty(pre+P_ECHO, 1);
		oProp.setProperty(pre+P_ALIVETIME, 1);
		oProp.setProperty(pre+P_PLACEMENTDISTANCE, 12);
		oProp.setProperty(pre+P_PLACEMENTDIRECTION, Math.PI/2);
		oProp.putAll(clsPhysical.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 5);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(255, 255, 255, 0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/notenschlüssel.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());	
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1."+clsShape2DCreator.P_RADIUS, 5);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1."+clsShape2DCreator.P_COLOR, new Color(255, 255, 255, 0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());	
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.1."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/ear1.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2."+clsShape2DCreator.P_RADIUS, 5);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2."+clsShape2DCreator.P_COLOR, new Color(255, 255, 255, 0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());	
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+".recieve.2."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/ear2.png");

		
		return oProp;
	}
	
	public void setAbstractSpeech(clsAbstractSpeech oAbstractSpeech) {
		moAbstractSpeech = oAbstractSpeech;
	}
	
	public clsAbstractSpeech getAbstractSpeech() {
		return moAbstractSpeech;
	}
	
	private clsPose getPose(clsCollidingObject oCollidingObject) {
		clsPose oPose =  oCollidingObject.moEntity.getPose();
		
		double rDir = oPose.getAngle().radians+mdPlacementDirection;
		double rLength = mdPlacementDistance;
		clsPolarcoordinate oP = new clsPolarcoordinate(rLength, rDir);
		oPose.setPosition( oPose.getPosition().add( oP.toDouble2D() ) );
		
		return oPose;
	}
	
	@Override
	public void updateInternalState() {
		
		miAliveTime--;
		if (miEcho > 0){	
			//forward the speech Object to all ARSINs
			miEcho--;
			ArrayList<clsCollidingObject> oCollidingObjects = moEntitySensorEngine.requestDetectedObjList();
			
			for (clsCollidingObject oCollidingObject : oCollidingObjects){
				if (oCollidingObject != null && oCollidingObject.moEntity != null && oCollidingObject.moEntity.meEntityType == eEntityType.ARSIN) {
					clsSpeech oSpeech = moCreateSpeech.getSpeech(moAbstractSpeech.changeProceedingsToS3(), miEcho);
					
					if (miEcho == 0){
						oSpeech.setPose(getPose(oCollidingObject));
						Double test = new Double(0.0);
						if (this.moAbstractSpeech.getMood().compareTo(test.toString()) > 0)
							oSpeech.set2DShape(moReceive2, mdMass);
						else if (this.moAbstractSpeech.getMood().compareTo(test.toString()) < 0)
							oSpeech.set2DShape(moReceive1, mdMass);
						else
							oSpeech.set2DShape(moReceive2, mdMass);
					} else {
						oSpeech.addSensor();
					}
					oSpeech.miEcho = miEcho;
					
					clsRegisterEntity.registerEntity(oSpeech);
				}
			}
		}
		
		if (miAliveTime <= 0){
			super.unregisterPhysical();
			this.setRegistered(false);
			clsRegisterEntity.unRegisterPhysicalObject2D(this.getMobileObject2D());
			clsSingletonMasonGetter.getFieldEnvironment().allObjects.remove(this.getMobileObject2D());
		}
	}

	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.SPEECH;
	}
	
	public clsAbstractSpeech getSpeech(){
		return moAbstractSpeech;
	}
}