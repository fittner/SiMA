/**
 * clsAttributeAntenna.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 15:11:49
 */
package body.attributes;

import properties.clsProperties;
import du.enums.eAntennaPositions;
import entities.enums.eBodyAttributes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 15:11:49
 * 
 */
public class clsAttributeAntenna extends clsBaseAttribute {
	public static final String P_ANTENNAPOSITION = "position";
	
	protected eAntennaPositions mnPosition;
	
    public clsAttributeAntenna(String poPrefix, clsProperties poProp, eBodyAttributes mnAntennaLeftRight) {
    	super(poPrefix, poProp);
    	
    	if (mnAntennaLeftRight != eBodyAttributes.ANTENNA_LEFT && mnAntennaLeftRight != eBodyAttributes.ANTENNA_RIGHT) {
    		throw new java.lang.IllegalArgumentException("Only eBodyAttributes.ANTENNA_LEFT or eBodyAttributes.ANTENNA_RIGHT as third param.");
    	}
    	mnBodyAttribute = mnAntennaLeftRight;
    	
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mnPosition = eAntennaPositions.valueOf( poProp.getPropertyString(pre+P_ANTENNAPOSITION) );
	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_ANTENNAPOSITION, eAntennaPositions.INTERMEDIATE.name() );
		
		return oProp;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 14:58:13
	 * 
	 * @see bw.body.attributes.clsBaseAttribute#setBodyAttribute()
	 */
	@Override
	protected void setBodyAttribute() {
		mnBodyAttribute = eBodyAttributes.UNDEFINED;
	}	
		
	public eAntennaPositions getPosition() {
		return mnPosition;
	}
	
	public void setPosition(eAntennaPositions pnPosition) {
		mnPosition = pnPosition;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": "+mnPosition.name();
		
		return result;
	}
}