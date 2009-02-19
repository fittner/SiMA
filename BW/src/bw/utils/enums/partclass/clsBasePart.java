/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums.partclass;

import bw.factories.clsSingletonUniqueIdGenerator;
import bw.utils.enums.eBodyParts;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBasePart {
	protected eBodyParts mePartId;
	protected String moName;
	
	public clsBasePart() {
		setName();
		setPartId();
	}

	protected abstract void setPartId();
	
	public eBodyParts getPartId() {
		return mePartId;
	}
	
	/**
	 * @param moName the moName to set
	 */
	protected abstract void setName();

	/**
	 * @return the moName
	 */
	public String getName() {
		return moName;
	}

}
