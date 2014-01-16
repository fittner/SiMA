/**
 * clsAttributeAlive.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 23.09.2009, 11:14:29
 */
package body.attributes;

import properties.clsProperties;
import du.enums.eTriState;
import entities.abstractEntities.clsEntity;
import entities.enums.eBodyAttributes;
import body.itfget.itfIsAlive;
import body.itfget.itfIsConsumeable;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.09.2009, 11:14:29
 * 
 */
public class clsAttributeAlive extends clsBaseAttribute {
	protected clsEntity moEntity;
	
	private boolean mnIsAliveAvailable;
	private boolean mnIsConsumableAvailable;	
	
    public clsAttributeAlive(String poPrefix, clsProperties poProp, clsEntity poEntity) {
    	super(poPrefix, poProp);
 
    	moEntity = poEntity;
    	
    	if (moEntity instanceof itfIsAlive) {
    		mnIsAliveAvailable = true;
    	} else {
    		mnIsAliveAvailable = false;
    	}
    	
    	if (moEntity instanceof itfIsConsumeable) {
    		mnIsConsumableAvailable = true;
    	} else {
    		mnIsConsumableAvailable = false;
    	}    	
    	
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);
		
	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		
		return oProp;
	}
	
	public eTriState isAlive() {
		if (mnIsAliveAvailable) {
			if (((itfIsAlive)moEntity).isAlive()) {
				return eTriState.TRUE;
			} else {
				return eTriState.FALSE;
			}
			
		} else {
			return eTriState.UNDEFINED;
		}
	}
	
	public eTriState isConsumeable() {
		if (mnIsConsumableAvailable) {
			if (((itfIsConsumeable)moEntity).isConsumable()) {
				return eTriState.TRUE;
			} else {
				return eTriState.FALSE;
			}
			
		} else {
			return eTriState.UNDEFINED;
		}
	}	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 11:16:27
	 * 
	 * @see bw.body.attributes.clsBaseAttribute#setBodyAttribute()
	 */
	@Override
	protected void setBodyAttribute() {
		mnBodyAttribute = eBodyAttributes.ALIVE;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = "Alive: "+isAlive().name()+" | Consumeable: "+isConsumeable().name();
		
		return result;
	}	
}
