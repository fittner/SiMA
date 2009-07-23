/**
 * @author langr
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
 * An instance of the mobile object clsAnimate that can:
 * - grow
 * - be harvest
 * - withdrawn
 * - be eaten  
 * 
 * @author langr
 * 
 */
public class clsPlant extends clsAnimate {

	public static final String P_DEFAULT_WEIGHT = "weight"; 
	public static final String P_MOBILE_SHAPE_RADIUS = "radius"; 
	public static final String P_MOBILE_SHAPE_TYPE = "shape_type"; 
	public static final String P_ENTIY_COLOR_B = "colorB";
	public static final String P_ENTIY_COLOR_G = "colorG";
	public static final String P_ENTIY_COLOR_R = "colorR";
		
    public clsPlant(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp);

    }
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		//TODO
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsAnimate.getDefaultProperties(poPrefix) );
		oProp.setProperty(pre+P_ENTIY_COLOR_B, Color.ORANGE.getBlue());
		oProp.setProperty(pre+P_ENTIY_COLOR_B, Color.ORANGE.getBlue());
		oProp.setProperty(pre+P_ENTIY_COLOR_G, Color.ORANGE.getGreen());
		oProp.setProperty(pre+P_MOBILE_SHAPE_TYPE, "SHAPE_CIRCLE");
		oProp.setProperty(pre+P_DEFAULT_WEIGHT, 300.0);
		oProp.setProperty(pre+P_MOBILE_SHAPE_RADIUS, 10.0);

		return oProp;
	}	
  	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType =  eEntityType.PLANT;
		
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
	 * 25.02.2009, 17:34:33
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
	 * 25.02.2009, 17:34:33
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}



}
