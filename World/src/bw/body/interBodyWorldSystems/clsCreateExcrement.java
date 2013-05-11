/**
 * clsCreateExcrement.java: BW - bw.body.interBodyWorldSystems
 * 
 * @author deutsch
 * 13.08.2009, 10:18:22
 */
package bw.body.interBodyWorldSystems;

import java.util.Random;

import statictools.clsUniqueIdGenerator;

import ARSsim.physics2D.util.clsPolarcoordinate;
import ARSsim.physics2D.util.clsPose;

import bw.body.internalSystems.clsStomachSystem;
import bw.entities.clsEntity;
import bw.entities.clsSmartExcrement;
import bw.exceptions.exNoSuchNutritionType;
import bw.utils.enums.eNutritions;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 13.08.2009, 10:18:22
 * 
 */
public class clsCreateExcrement {
	public static final String P_SMARTEXCREMENTS = "smartexcrements";
	public static final String P_GARBAGENUTRITIONTYPE = "garbagenutritiontype";
	public static final String P_PLACEMENTDISTANCE = "placement_distance";
	public static final String P_PLACEMENTDIRECTION = "placement_direction";
	public static final String P_VARIATIONDISTANCE = "variation_distance";
	public static final String P_VARIATIONDIRECTION = "variation_direction";
	public static final String P_WEIGHT = "weight";
	
	private eNutritions mnGarbageNutritionType;
	private clsStomachSystem moStomachSystem; // reference to existing stomach
	private clsEntity moEntity; // reference to this entity. necessary to determine the position of the sh..t
	private double mrWeight;
	private double mrPlacementDirection;
	private double mrPlacementDistance;
	private double mrVariationDirection;
	private double mrVariationDistance;
	private clsProperties moSmartExcrementProps;


	public clsCreateExcrement(String poPrefix, clsProperties poProp, clsStomachSystem poStomach, clsEntity poEntity) {
		moStomachSystem = poStomach;
		moEntity = poEntity;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_GARBAGENUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+P_WEIGHT, 1);
		oProp.putAll( clsSmartExcrement.getDefaultProperties(pre+P_SMARTEXCREMENTS) );
		oProp.setProperty(pre+P_PLACEMENTDISTANCE, 12);
		oProp.setProperty(pre+P_PLACEMENTDIRECTION, Math.PI);
		oProp.setProperty(pre+P_VARIATIONDISTANCE, 1);
		oProp.setProperty(pre+P_VARIATIONDIRECTION, 0.2);				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		String temp = poProp.getProperty(pre+P_GARBAGENUTRITIONTYPE);
		mnGarbageNutritionType = eNutritions.valueOf(temp);
		mrWeight = poProp.getPropertyDouble(pre+P_WEIGHT);
		moSmartExcrementProps = poProp.getSubset( pre+P_SMARTEXCREMENTS );
		mrPlacementDirection = poProp.getPropertyDouble(pre+P_PLACEMENTDIRECTION);
		mrPlacementDistance = poProp.getPropertyDouble(pre+P_PLACEMENTDISTANCE);
		mrVariationDirection = poProp.getPropertyDouble(pre+P_VARIATIONDIRECTION);
		mrVariationDistance = poProp.getPropertyDouble(pre+P_VARIATIONDISTANCE);		
	}
	
	private clsPose getPose() {
		clsPose oPose =  moEntity.getPose();
		Random oRand = new Random();
		
		double rPDir = mrPlacementDirection + oRand.nextDouble()*2*mrVariationDirection-mrVariationDirection;
		double rPLen = mrPlacementDistance + oRand.nextDouble()*2*mrVariationDistance-mrVariationDistance;;
		
		double rDir = oPose.getAngle().radians+rPDir;
		double rLength = rPLen;
		clsPolarcoordinate oP = new clsPolarcoordinate(rLength, rDir);
		oPose.setPosition( oPose.getPosition().add( oP.toDouble2D() ) );
		
		return oPose;
	}
	
	public clsSmartExcrement getSmartExcrements(double prIntensity) {
		double rExcrementWeight = mrWeight * prIntensity;
				
		try {
			double rFraction = moStomachSystem.withdrawNutrition(eNutritions.EXCREMENT, mrWeight * prIntensity);
			rExcrementWeight *= rFraction;
			
		} catch (exNoSuchNutritionType e) {
			rExcrementWeight = 0.0;
			
		}
		
		//FIXME!!!
		rExcrementWeight = 1.0;
		
		clsSmartExcrement oSh__t = new clsSmartExcrement("", moSmartExcrementProps, 
				clsUniqueIdGenerator.getUniqueId(), rExcrementWeight);
		
		
		oSh__t.setPose( getPose() );
		
		return oSh__t;
	}
}
