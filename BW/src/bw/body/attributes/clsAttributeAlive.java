/**
 * clsAttributeAlive.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 23.09.2009, 11:14:29
 */
package bw.body.attributes;

import config.clsBWProperties;
import enums.eTriState;
import bw.body.itfget.itfIsAlive;
import bw.body.itfget.itfIsConsumeable;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyAttributes;

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
	
    public clsAttributeAlive(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
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
    
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);
		
	}	    

	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		
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
