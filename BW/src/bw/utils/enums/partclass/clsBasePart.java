/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums.partclass;

import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBasePart implements Cloneable {
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public clsBasePart clone() {
		clsBasePart oResult = null;
		try {
			oResult = (clsBasePart) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return oResult;
	}
	
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param arg0
	 * @return
	 */
	public boolean equals(clsBasePart arg0) {
		return (arg0.mePartId == this.mePartId);
	}

}
