/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import statictools.clsGetARSPath;

import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
import enums.eEntityType;

/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsStone extends clsInanimate {
		
	public static final String P_IMAGE_PATH = "image_path";
	public static final String P_RADIUS_TO_MASS_CONVERSION = "conversion";
		
	public clsStone(String poPrefix, clsBWProperties poProp)
    {
//		super(pnId, poPose, poStartingVelocity, new ARSsim.physics2D.shape.clsCircleImage(prRadius, clsStone.moDefaultColor, clsStone.moImagePath), prRadius * clsStone.mrDefaultRadiusToMassConversion);
		//todo muchitsch ... hier wird eine default shape �bergeben, nicht null, sonst krachts
		super(poPrefix, poProp); 
		//super(pnId, poPose, poStartingVelocity, null, prRadius * clsStone.mrDefaultRadiusToMassConversion, clsStone.getFinalConfig(poConfig));
		applyProperties(poPrefix, poProp);
		
		double rMass = poProp.getPropertyDouble(poPrefix+ P_SHAPE_RADIUS)*
						poProp.getPropertyDouble(poPrefix+ P_RADIUS_TO_MASS_CONVERSION);  
			
		setShape(new ARSsim.physics2D.shape.clsCircleImage(poProp.getPropertyDouble(poPrefix+ P_SHAPE_RADIUS), 
									                       poProp.getPropertyColor(poPrefix+P_ENTITY_COLOR_RGB), 
															  poProp.getPropertyString(poPrefix +P_IMAGE_PATH)),
															  rMass);
    } 
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
			//TODO
		}	
		
		public static clsBWProperties getDefaultProperties(String poPrefix) {
			String pre = clsBWProperties.addDot(poPrefix);

			clsBWProperties oProp = new clsBWProperties();

			oProp.putAll(clsInanimate.getDefaultProperties(pre) );
			oProp.setProperty(pre+P_RADIUS_TO_MASS_CONVERSION , 10.0);
			oProp.setProperty(pre+P_SHAPE_TYPE,  eShapeType.CIRCLE.name());
			oProp.setProperty(pre+P_SHAPE_RADIUS, 1.0);
			oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.DARK_GRAY);
			oProp.setProperty(pre+P_IMAGE_PATH, clsGetARSPath.getArsPath()+ "/BW/src/resources/images/rock1.jpg");
		   			
			return oProp;
		}	
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.STONE;
		
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
	 * 25.02.2009, 17:37:10
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
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}



}