/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;
import du.enums.eEntityType;

import bw.entities.tools.clsShapeCreator;
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
		
	public clsStone(String poPrefix, clsBWProperties poProp, String uid)
    {
		super(poPrefix, poProp, uid); 
		applyProperties(poPrefix, poProp);
    }
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){	
		String pre = clsBWProperties.addDot(poPrefix);
		double rMass = poProp.getPropertyDouble(pre+P_SHAPE+".0."+clsShapeCreator.P_RADIUS)*
			poProp.getPropertyDouble(poPrefix+ P_RADIUS_TO_MASS_CONVERSION);
		setStructuralWeight(rMass);
	}	
		
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_RADIUS_TO_MASS_CONVERSION , 500.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, "0");
		
		oProp.setProperty(pre+P_SHAPE+".0."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".0."+clsShapeCreator.P_RADIUS, "15.0");
		oProp.setProperty(pre+P_SHAPE+".0."+clsShapeCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".0."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/rock1.png");
		oProp.setProperty(pre+P_SHAPE+".0."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".1."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".1."+clsShapeCreator.P_RADIUS, "15.0");
		oProp.setProperty(pre+P_SHAPE+".1."+clsShapeCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".1."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/rock2.png");
		oProp.setProperty(pre+P_SHAPE+".1."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".2."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".2."+clsShapeCreator.P_RADIUS, "15.0");
		oProp.setProperty(pre+P_SHAPE+".2."+clsShapeCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".2."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/rock3.png");
		oProp.setProperty(pre+P_SHAPE+".2."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".3."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".3."+clsShapeCreator.P_RADIUS, "15.0");
		oProp.setProperty(pre+P_SHAPE+".3."+clsShapeCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".3."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/rock4.png");
		oProp.setProperty(pre+P_SHAPE+".3."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());				
	   			
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