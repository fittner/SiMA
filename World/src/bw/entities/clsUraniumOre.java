/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import statictools.clsGetARSPath;

import config.clsProperties;
import du.enums.eEntityType;
import bw.utils.enums.eBindingState;
import bw.body.io.actuators.actionProxies.itfAPCarryable;

/**
 * 
 * This class represents uranium ore - an utility for the Fungus-Eater. It's a radioactivity source and can be carried.
 * 
 * TODO (horvath) - implement radioactivity
 * 
 * @author horvath
 * 08.07.2009, 11:25:46
 * 
 */
public class clsUraniumOre extends clsInanimate implements itfAPCarryable {
	public static final String CONFIG_FILE_NAME= "uraniumore.default.properties";
	public static final String P_RADIATION_INTENSITY = "radiation_intensity";
	
	public double mrRadiationIntensity;
	
	public clsUraniumOre(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}
    
    private void applyProperties(String poPrefix, clsProperties poProp){
    	mrRadiationIntensity = poProp.getPropertyDouble(poPrefix +P_RADIATION_INTENSITY);
	}	
    
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll(clsInanimate.getDefaultProperties(pre) );
		
		clsProperties oPropFile = clsProperties.readProperties(clsGetARSPath.getEntityConfigPath(), CONFIG_FILE_NAME);
		oPropFile.addPrefix(poPrefix);
		oProp.putAll(oPropFile);
		
/*	the old hardcoded properties; now they are in uraniumore.default.properties
 * 		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 5.0);
		oProp.setProperty(pre+P_RADIATION_INTENSITY, 1.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, "4.0");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, Color.green);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/uranium.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
	*/	
		return oProp;
	}	
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.URANIUM;
		
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
	 * 25.02.2009, 17:34:14
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
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// nothing to do
		
	}
	
	/*
	 * Interface Carryable
	 */
	@Override
	public clsMobile getCarryableEntity() {
		return this;	
	}
	@Override
	public void setCarriedBindingState(eBindingState pBindingState) {
		//handle binding-state implications 
	}

}

