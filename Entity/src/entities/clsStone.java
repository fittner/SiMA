/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities;

import java.awt.Color;

import statictools.clsGetARSPath;
import tools.clsPose;
import tools.eImagePositioning;


import config.clsProperties;
import du.enums.eEntityType;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsInanimate;
import entities.abstractEntities.clsUnorganic;
import entities.enums.eShapeType;
import entities.factory.clsEntityFactory;
import entities.tools.clsShape2DCreator;



/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsStone extends clsUnorganic {
	public static final String P_RADIUS_TO_MASS_CONVERSION = "conversion";
	public static final String CONFIG_FILE_NAME="stone.default.properties";
		
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
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "rock1.png");
		oProp.setProperty(pre+P_SHAPE+".0."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "rock2.png");
		oProp.setProperty(pre+P_SHAPE+".1."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "rock3.png");
		oProp.setProperty(pre+P_SHAPE+".2."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_RADIUS, "8.0");
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_COLOR, Color.DARK_GRAY);
		oProp.setProperty(pre+P_SHAPE+".3."+clsShape2DCreator.P_IMAGE_PATH, clsGetARSPath.getRelativImagePath() + "rock4.png");
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

	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor){
		clsEntity oNewEntity = clsEntityFactory.createEntity(poPrperties, this.getEntityType(), null, this.uid);
		double x = this.getPose().getPosition().x;
		double y = this.getPose().getPosition().y;
		double angle = this.getPose().getAngle().radians;
		double weight = this.getVariableWeight();
		
		//set position
		oNewEntity.setPose(new clsPose(x-(poDistance/2), y, angle));
		this.setPose(new clsPose(x+(poDistance/2), y, angle));
		//set weight
		oNewEntity.setVariableWeight(weight*poSplitFactor);
		this.setVariableWeight(weight*(1-poSplitFactor));
		
		return oNewEntity;

	}



}