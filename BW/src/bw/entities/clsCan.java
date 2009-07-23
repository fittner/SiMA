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

public class clsCan extends clsInanimate {
	public static final String P_ID = "id";
	public static final String P_ENTIY_COLOR_B = "colorB";
	public static final String P_ENTIY_COLOR_G = "colorG";
	public static final String P_ENTIY_COLOR_R = "colorR";
	
	public static final String P_DEFAULT_MASS = "mass"; 
	public static final String P_MOBILE_SHAPE_RADIUS = "radius"; 
	public static final String P_MOBILE_SHAPE_TYPE = "shape_type";  
		
	
//	private static double mrDefaultWeight = 80.0f;
//	private static double mrDefaultRadius = 2.0f;
//	private static Color moDefaultColor = Color.blue;	
    
    public clsCan(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp); 
		
		applyProperties(poPrefix, poProp);
    }

	
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		//TODO
	}	
	
public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(poPrefix) );
		oProp.setProperty(pre+P_ENTIY_COLOR_B, Color.blue.getBlue());
		oProp.setProperty(pre+P_ENTIY_COLOR_G, Color.blue.getGreen());
		oProp.setProperty(pre+P_ENTIY_COLOR_R, Color.blue.getRed());
		oProp.setProperty(pre+P_DEFAULT_MASS, 80.0);
		oProp.setProperty(pre+P_MOBILE_SHAPE_RADIUS, 2.0);
		oProp.setProperty(pre+P_MOBILE_SHAPE_TYPE, "SHAPE_CIRCLE");
		
		return oProp;
}
    
    /* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.CAN;
		
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

}

