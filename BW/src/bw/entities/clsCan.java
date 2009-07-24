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
import bw.utils.enums.eShapeType;
import enums.eEntityType;

public class clsCan extends clsInanimate {
		
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
		oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.blue);
		oProp.setProperty(pre+P_MASS, 80.0);
		oProp.setProperty(pre+P_SHAPE_RADIUS, 2.0);
		oProp.setProperty(pre+P_SHAPE_TYPE,  eShapeType.SHAPE_CIRCLE.name());
		
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

