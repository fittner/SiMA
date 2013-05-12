/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;


import java.awt.Color;


import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;

import config.clsProperties;
import du.enums.eEntityType;
import bw.utils.enums.eShapeType;

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
public class clsPlant extends clsInanimate {
		
	public clsPlant(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
    }
    
    private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
	}	
    
    public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsAnimate.getDefaultProperties(poPrefix) );
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 300.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 10);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.ORANGE);
		
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/plant01.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
		
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