/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import bw.entities.tools.clsShapeCreator;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
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

	
	public clsPlant(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
    }
    
    private void applyProperties(String poPrefix, clsBWProperties poProp){		
		// nothing to do
	}	
    
    public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsAnimate.getDefaultProperties(poPrefix) );

		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 300.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_RADIUS, 10);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.ORANGE);
		
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
		// nothing to do
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// nothing to do
		
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
		// nothing to do
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
		// nothing to do
	}
}