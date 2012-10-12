/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsProperties;
import du.enums.eEntityType;

import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eShapeType;

/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsStone extends clsInanimate {
	public static final String P_RADIUS_TO_MASS_CONVERSION = "conversion";
		
	public clsStone(String poPrefix, clsProperties poProp, int uid)
    {
		super(poPrefix, poProp, uid); 
		applyProperties(poPrefix, poProp);
    }
	
	private void applyProperties(String poPrefix, clsProperties poProp){	
		String pre = clsProperties.addDot(poPrefix);
		double rMass = poProp.getPropertyDouble(pre+P_SHAPE+".0."+clsShape2DCreator.P_RADIUS)*
			poProp.getPropertyDouble(poPrefix+ P_RADIUS_TO_MASS_CONVERSION);
		setStructuralWeight(rMass);
	}	
		
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_RADIUS_TO_MASS_CONVERSION , 500.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, "0");
		
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/rock1.png");
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/rock2.png");
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/rock3.png");
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/rock4.png");
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());				
	   			
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
		// TODO (muchitsch) - Auto-generated method stub
		
	}
	

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// TODO (muchitsch) - Auto-generated method stub
		
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
		// TODO (muchitsch) - Auto-generated method stub
		
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
		// TODO (muchitsch) - Auto-generated method stub
		
	}



}