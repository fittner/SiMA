/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBindingState;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import enums.eEntityType;

/**
 * 
 * This class represents uranium ore - an utility for the Fungus-Eater. It's a radioactivity source and can be carried.
 * 
 * TODO (horvath) - implement radioactivity
 * 
 * @author horvath
 * 08.07.2009, 11:25:46
 * 
 */
public class clsUraniumOre extends clsInanimate implements itfAPCarryable {
	
	public static final String P_POS_X = "pos_x";
	public static final String P_POS_Y = "pos_y";
	public static final String P_POS_ANGLE = "pos_angle";
	public static final String P_START_VELOCITY_X = "start_velocity_x";
	public static final String P_START_VELOCITY_Y = "start_velocity_y";
	public static final String P_RADIATION_INTENSITY = "radiation_intensity";
	public static final String P_COLOR_BLUE = "colorB";
	public static final String P_COLOR_GREEN = "colorG";
	public static final String P_COLOR_RED = "colorR";
	
	public static final String P_DEFAULT_WEIGHT = "weight"; 
	public static final String P_DEFAULT_RADIUS = "radius"; 
	public static final String P_ID = "entity_ID";
	public static final String P_IMAGE_PATH = "image_path";
    
    public clsUraniumOre(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp, null);
		applyProperties(poPrefix, poProp);
		
		setShape(new ARSsim.physics2D.shape.
				clsCircleImage(poProp.getPropertyDouble(poPrefix + P_DEFAULT_RADIUS),
							   new Color(poProp.getPropertyInt(poPrefix +P_COLOR_RED),
									     poProp.getPropertyInt(poPrefix +P_COLOR_GREEN),
									     poProp.getPropertyInt(poPrefix +P_COLOR_BLUE)), 
							   poProp.getPropertyString(poPrefix +P_IMAGE_PATH)),
							   poProp.getPropertyDouble(poPrefix +P_DEFAULT_WEIGHT)); 
	}
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		//TODO
	}	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();

		oProp.setProperty(pre+P_POS_X, 0.0);
		oProp.setProperty(pre+P_POS_Y, 0.0);
		oProp.setProperty(pre+P_POS_ANGLE, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_X, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_Y, 0.0);
		
		oProp.setProperty(pre+P_COLOR_BLUE, Color.green.getBlue());
		oProp.setProperty(pre+P_COLOR_GREEN, Color.green.getGreen());
		oProp.setProperty(pre+P_COLOR_RED, Color.green.getRed());
		oProp.setProperty(pre+P_DEFAULT_WEIGHT, 30.0);
		oProp.setProperty(pre+P_DEFAULT_RADIUS, 4.0);
		oProp.setProperty(pre+P_IMAGE_PATH, sim.clsBWMain.msArsPath + "/src/resources/images/Uranium.png");
		
		return oProp;
	}	
	
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.URANIUM;
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
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

