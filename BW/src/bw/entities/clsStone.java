/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import bw.utils.config.clsBWProperties;
import enums.eEntityType;

/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsStone extends clsInanimate {
		
	public static final String P_ID = "id";
	public static final String P_ENTIY_COLOR_B = "colorB";
	public static final String P_ENTIY_COLOR_G = "colorG";
	public static final String P_ENTIY_COLOR_R = "colorR";
	
	public static final String P_IMAGE_PATH = "image_path";
	public static final String P_RADIUS_TO_MASS_CONVERSION = "conversion";
	public static final String P_MOBILE_SHAPE_RADIUS = "radius";
	public static final String P_MOBILE_SHAPE_TYPE = "shape_type"; 
	
	
	public clsStone(String poPrefix, clsBWProperties poProp)
    {
//		super(pnId, poPose, poStartingVelocity, new ARSsim.physics2D.shape.clsCircleImage(prRadius, clsStone.moDefaultColor, clsStone.moImagePath), prRadius * clsStone.mrDefaultRadiusToMassConversion);
		//todo muchitsch ... hier wird eine default shape �bergeben, nicht null, sonst krachts
		super(poPrefix, poProp); 
		//super(pnId, poPose, poStartingVelocity, null, prRadius * clsStone.mrDefaultRadiusToMassConversion, clsStone.getFinalConfig(poConfig));
		applyProperties(poPrefix, poProp);
		
		double rMass = poProp.getPropertyDouble(poPrefix+ P_MOBILE_SHAPE_RADIUS)*
						poProp.getPropertyDouble(poPrefix+ P_RADIUS_TO_MASS_CONVERSION);  
			
		setShape(new ARSsim.physics2D.shape.clsCircleImage(poProp.getPropertyDouble(poPrefix+ P_MOBILE_SHAPE_RADIUS), 
									new Color(poProp.getPropertyInt(poPrefix+ P_ENTIY_COLOR_R),
											  poProp.getPropertyInt(poPrefix+ P_ENTIY_COLOR_G), 
											  poProp.getPropertyInt(poPrefix+ P_ENTIY_COLOR_B)), 
											  poProp.getPropertyString(poPrefix +P_IMAGE_PATH)),
											  rMass);
    } 
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
			//TODO
		}	
		
		public static clsBWProperties getDefaultProperties(String poPrefix) {
			String pre = clsBWProperties.addDot(poPrefix);

			clsBWProperties oProp = new clsBWProperties();

			oProp.putAll(clsInanimate.getDefaultProperties(poPrefix) );
			oProp.setProperty(pre+P_RADIUS_TO_MASS_CONVERSION , 10.0);
			oProp.setProperty(pre+P_MOBILE_SHAPE_TYPE, "SHAPE_CIRCLE");
			oProp.setProperty(pre+P_MOBILE_SHAPE_RADIUS, 1.0);
			oProp.setProperty(pre+P_ENTIY_COLOR_B, Color.DARK_GRAY.getBlue());
			oProp.setProperty(pre+P_ENTIY_COLOR_G, Color.DARK_GRAY.getGreen());
			oProp.setProperty(pre+P_ENTIY_COLOR_R, Color.DARK_GRAY.getRed());
		    oProp.setProperty(pre+P_IMAGE_PATH, sim.clsBWMain.msArsPath + "/src/resources/images/rock1.jpg");
		   			
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