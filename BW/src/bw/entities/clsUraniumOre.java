/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import statictools.clsGetARSPath;

import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBindingState;
import bw.utils.enums.eShapeType;
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
	
	public static final String P_RADIATION_INTENSITY = "radiation_intensity";
    public static final String P_IMAGE_PATH = "image_path";
	
	public double mrRadiationIntensity;
	
	public clsUraniumOre(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
		
		setShape(new ARSsim.physics2D.shape.
				clsCircleImage(poProp.getPropertyDouble(poPrefix + P_SHAPE_RADIUS),
							   poProp.getPropertyColor(poPrefix+P_ENTITY_COLOR_RGB),
									     poProp.getPropertyString(poPrefix +P_IMAGE_PATH)),
							   poProp.getPropertyDouble(poPrefix +P_MASS)); 
	}
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		//TODO
    	mrRadiationIntensity = poProp.getPropertyDouble(poPrefix +P_RADIATION_INTENSITY);
	}	
    
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.green);
		oProp.setProperty(pre+P_SHAPE_TYPE, eShapeType.SHAPE_CIRCLE.name());
		oProp.setProperty(pre+P_RADIATION_INTENSITY, 0.0);
		oProp.setProperty(pre+P_MASS, 30.0);
		oProp.setProperty(pre+P_SHAPE_RADIUS, 4.0);
		oProp.setProperty(pre+P_IMAGE_PATH, clsGetARSPath.getArsPath()+ "/BW/src/resources/images/Uranium.png");
		
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

