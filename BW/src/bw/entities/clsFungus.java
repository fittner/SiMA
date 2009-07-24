/**
 * @author muchitsch, horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import statictools.clsGetARSPath;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfGetFlesh;
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBindingState;
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import enums.eEntityType;


/**
 * 
 * Fungus - an energy source for the Fungus-Eater
 * 
 * TODO (horvath) - no nutritions, just energy 
 *                - no carryable, immobile (?)
 * 
 * @author horvath
 * 08.07.2009, 14:48:08
 * 
 */
public class clsFungus extends clsInanimate implements itfGetFlesh, itfAPEatable, itfAPCarryable{
	
	public static final String P_BODY = "body";
	public static final String P_IMAGE_PATH = "image_path";
	
	public static final String P_FAT = "nutrition_fat";
	public static final String P_WATER = "nutrition_water";
	
	public static final String P_CONTENT= "flesh_content";
	public static final String P_MAXCONTENT= "flesh_max_content";
	public static final String P_INCREASERATE = "flesh_increaserate";
	
	private double mrCakeWeight;
	private double mrDefaultRadius; 
	private double mrDefaultMass; 
	private boolean mnTotallyConsumed;
	private boolean mnShapeUpdated;
	
	private clsMeatBody moBody;

	public clsFungus(String poPrefix, clsBWProperties poProp)
    {
//		super(pnId, poPose, poStartingVelocity, new ARSsim.physics2D.shape.clsCircleImage(prRadius, clsStone.moDefaultColor, clsStone.moImagePath), prRadius * clsStone.mrDefaultRadiusToMassConversion);
		//todo muchitsch ... hier wird eine default shape �bergeben, nicht null, sonst krachts
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp); 
		
		mnTotallyConsumed = false;
		mnShapeUpdated = false;
		
		moBody = new clsMeatBody(poPrefix+P_BODY, poProp);
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(poProp.getPropertyDouble(poPrefix + P_SHAPE_RADIUS), 
				         poProp.getPropertyColor(poPrefix + P_ENTITY_COLOR_RGB), 
					     poProp.getPropertyString(poPrefix +P_IMAGE_PATH)), 
					     poProp.getPropertyDouble(poPrefix +P_MASS));
    } 
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
			//TODO
			mrCakeWeight =  poProp.getPropertyDouble(poPrefix +P_MASS);
			mrDefaultRadius = poProp.getPropertyDouble(poPrefix +P_SHAPE_RADIUS); 
			mrDefaultMass = poProp.getPropertyDouble(poPrefix +P_MASS);
	}	
		
	public static clsBWProperties getDefaultProperties(String poPrefix) {
			String pre = clsBWProperties.addDot(poPrefix);

			clsBWProperties oProp = new clsBWProperties();
			
			oProp.putAll(clsInanimate.getDefaultProperties(pre) );
			oProp.putAll(clsMeatBody.getDefaultProperties(pre+P_BODY) );
			oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.pink);
			oProp.setProperty(pre+P_MASS, 30.0);
			oProp.setProperty(pre+P_SHAPE_TYPE, "SHAPE_CIRCLE");
			oProp.setProperty(pre+P_SHAPE_RADIUS, 6.0);
			oProp.setProperty(pre+P_IMAGE_PATH, clsGetARSPath.getArsPath()+ "/BW/src/resources/images/fungus.jpg");
			
			oProp.setProperty(pre+"1."+clsFlesh.P_NUTRITIONFRACTION, 5.0);
			oProp.setProperty(pre+"4."+clsFlesh.P_NUTRITIONFRACTION, 1.0);

			oProp.setProperty(pre+P_CONTENT, 15.0);
			oProp.setProperty(pre+P_MAXCONTENT, 15.0);
			oProp.setProperty(pre+P_INCREASERATE, 0.0);
		
			
			return oProp;
	}
	
	public double withdraw(double prAmount) {
		double rWeight = 0.0;
		
		if (prAmount > 0.0) {
			if (mrCakeWeight > prAmount) {
				rWeight = prAmount;
				mrCakeWeight -= prAmount;
			} else {
				rWeight = mrCakeWeight;
				mrCakeWeight = 0.0;
				mnTotallyConsumed = true;
			}
		}

		return rWeight;
	}
	

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.FUNGUS ;
		
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
		// TODO Auto-generated method stub
		
		if (mnTotallyConsumed && !mnShapeUpdated) {
			mnShapeUpdated = true;
			setShape(new sim.physics2D.shape.Circle(mrDefaultRadius, Color.gray), mrDefaultMass);
			
			//TODO langr: wohin damit
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 19:26:16
	 * 
	 * @see bw.entities.clsEntity#execution()
	 */
	@Override
	public void execution() {
		// no executions
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 19:26:16
	 * 
	 * @see bw.entities.clsEntity#processing()
	 */
	@Override
	public void processing() {
		// no processing
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 19:26:16
	 * 
	 * @see bw.entities.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// no sensing
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.05.2009, 18:16:27
	 * 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	public clsFlesh getFlesh() {
		return this.moBody.getFlesh();
	}


	/*
	 * Interface Eatable
	 */
	public double tryEat() {
		return 0;
	}
	public clsFood Eat(double prBiteSize) {
		clsFood oFood = getFlesh().withdraw(prBiteSize);
		return oFood;
	}
	
	/*
	 * Interface Carryable
	 */
	public clsMobile getCarryableEntity() {
		return this;	
	}
	public void setCarriedBindingState(eBindingState pBindingState) {
		//handle binding-state implications 
	}
}