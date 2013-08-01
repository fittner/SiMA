/**
 * @author deutsch
 * 12.05.2009, 19:37:43
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;

import config.clsProperties;

import sim.physics2D.shape.Shape;
import statictools.clsGetARSPath;

import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import entities.factory.clsEntityFactory;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsDigestiveSystem;
import bw.entities.base.clsAnimal;
import bw.entities.base.clsAnimate;
import bw.entities.base.clsEntity;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:37:43
 * 
 */
public class clsLynx extends clsAnimal {
	public static final String P_SHAPE_ALIVE		= "shape_alive";
	public static final String P_SHAPE_DEAD 		= "shape_dead";	

	//private Shape moAlive; //reactivate in case of resurrection
	private Shape moDead2D;
	
	public clsLynx(itfDecisionUnit poDU, String poPrefix, clsProperties poProp, int uid) {
		super(poDU, poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		//moAlive = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_ALIVE, poProp); //reactivate in case of resurrection
		moDead2D = clsShape2DCreator.createShape(pre+P_SHAPE+"."+P_SHAPE_DEAD, poProp);		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.putAll( clsAnimal.getDefaultProperties(pre) );

		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//FIXME (deutsch) - .4. is not guaranteed - has to be changed!
		oProp.setProperty(pre+"body.externalio.sensors.4.offset", 11);
		oProp.setProperty(pre+"body.externalio.sensors.4.sensor_range", 2);
		oProp.setProperty(pre+"body.externalio.sensors.2.sensor_range", 45.0);
		oProp.setProperty(pre+"body.externalio.sensors.3.sensor_range", 45.0);		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 600.0);
		

		//change stomach to desired values
		String stomach_pre = pre+clsAnimate.P_BODY+"."+clsComplexBody.P_INTERNAL+"."+clsInternalSystem.P_STOMACH+".";
		oProp.removeKeysStartingWith(stomach_pre);

		int i = 0;
		
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONTYPE, eNutritions.FAT.toString());
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONEFFICIENCY, 2);
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONMETABOLISMFACTOR, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 10);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 10);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0.5);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 5);		
		i++;
		
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONTYPE, eNutritions.PROTEIN.toString());
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONEFFICIENCY, 2);
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONMETABOLISMFACTOR, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 10);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 10);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0.5);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 5);
		i++;		

		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONTYPE, eNutritions.WATER.toString());
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONEFFICIENCY, 0);
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONMETABOLISMFACTOR, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 10);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 10);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0.5);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 5);
		i++;
		
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONEFFICIENCY, 0);
		oProp.setProperty(stomach_pre+i+"."+clsDigestiveSystem.P_NUTRITIONMETABOLISMFACTOR, 0);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(stomach_pre+i+".") );	
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_MAXCONTENT, 6);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_UPPERBOUND, 5);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_LOWERBOUND, 0);
		oProp.setProperty(stomach_pre+i+"."+clsNutritionLevel.P_CONTENT, 0);
		i++;
		
		oProp.setProperty(stomach_pre+clsDigestiveSystem.P_NUMNUTRITIONS, i);
		
		//change flesh to desired values
		String flesh_pre = pre+clsAnimate.P_BODY+"."+clsComplexBody.P_INTERNAL+"."+clsInternalSystem.P_FLESH+".";
		oProp.removeKeysStartingWith(flesh_pre);		
		
		oProp.setProperty(flesh_pre+clsFlesh.P_WEIGHT, 50.0 );
		oProp.setProperty(flesh_pre+clsFlesh.P_NUMNUTRITIONS, 4 );
		oProp.setProperty(flesh_pre+"0."+clsFlesh.P_NUTRITIONTYPE, eNutritions.PROTEIN.name());
		oProp.setProperty(flesh_pre+"0."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(flesh_pre+"1."+clsFlesh.P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(flesh_pre+"1."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(flesh_pre+"2."+clsFlesh.P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(flesh_pre+"2."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(flesh_pre+"3."+clsFlesh.P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(flesh_pre+"3."+clsFlesh.P_NUTRITIONFRACTION, 1.0);
		
		//set shape
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPE_ALIVE);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_RADIUS, 5.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_COLOR, Color.yellow);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "luchs.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_ALIVE+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_RADIUS, 5.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_COLOR, Color.BLACK);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "luchs_grey.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPE_DEAD+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		return oProp;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.LYNX;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 27.07.2009, 14:58    adapted by zeilinger from 
	 * 						clsComplexBody to clsMeatBody 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	public clsFlesh getFlesh() {
		clsFlesh oResult = ((clsComplexBody)moBody).getInternalSystem().getFlesh();
		return oResult;
	}	
	
	private void updateShape() {
		if (!isAlive()) {
			set2DShape(moDead2D, getTotalWeight());
			((clsComplexBody)moBody).getIntraBodySystem().getColorSystem().setNormColor();
		}		
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

//	    ((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		super.processing();
		updateShape();		
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		super.updateInternalState();
		if (((clsComplexBody)moBody).getInternalSystem().getHealthSystem().getHealth().getContent() < 0.0001) {
			setAlive(false);
		}
		updateShape();
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
}
