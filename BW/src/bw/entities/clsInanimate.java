/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import bw.utils.config.clsBWProperties;



/**
 * Inanimates represent dead objects (cannot grow, move, ...)
 * 
 * @author langr
 * 
 */
public abstract class clsInanimate extends clsMobile {

	public static final String P_ID = "entity_ID";
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 25.02.2009, 17:27:06
	 *
	 * @param pos
	 * @param vel
	 * @param circle
	 * @param pnId
	 */
	
	public clsInanimate(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp);
	}


	public static clsBWProperties getDefaultProperties(String poPrefix) {
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsMobile.getDefaultProperties(poPrefix));
	
		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsBWProperties poProp) {		
	
		
	}	

}
