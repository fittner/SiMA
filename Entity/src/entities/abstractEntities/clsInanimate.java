/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities.abstractEntities;


import properties.clsProperties;




/**
 * Inanimates represent dead objects (cannot grow, move, ...)
 * 
 * @author langr
 * 
 */
public abstract class clsInanimate extends clsMobile {
 	
	

 	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 25.02.2009, 17:27:06
	 *
	 * @param pos
	 * @param vel
	 * @param circle
	 * @param pnId
	 */
	
	public clsInanimate(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp);
	}


	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsMobile.getDefaultProperties(pre));
		


		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsProperties poProp) {		
	
		
	}	
	
	
	@Override
	public void sensing() {}
	
	@Override
	public void processing() {}

	@Override
	public void execution() {}

	@Override
	public boolean isAlive() {
		return false;
	}

}
