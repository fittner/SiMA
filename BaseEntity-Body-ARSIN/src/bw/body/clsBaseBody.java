/**
 * @author deutsch
 * 05.05.2009, 16:37:09
 *
 * temp
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.attributes.clsAttributes;
import bw.entities.base.clsEntity;
import bw.utils.enums.eBodyType;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 */
public abstract class clsBaseBody implements 	itfStepSensing, itfStepUpdateInternalState, 
												itfStepProcessing, itfStepExecution {

	
	public static final String P_ATTRIBUTES     = "attributes";
    protected clsAttributes  moAttributes;
    protected eBodyType meBodyType;
    protected double moBodyIntegrity;
	
	
	

	public clsBaseBody(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		setBodyType();
		applyProperties(poPrefix, poProp, poEntity);	
	}

	private void applyProperties(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		String pre = clsProperties.addDot(poPrefix);

		moAttributes    = new clsAttributes(pre+P_ATTRIBUTES, poProp, poEntity);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsAttributes.getDefaultProperties(pre+P_ATTRIBUTES) );
			
		return oProp;
	}	

	public clsAttributes getAttributes() {
		   return moAttributes;
	}
	
	public eBodyType getBodyType() {
		return meBodyType;
	}
	
	/**
	 * /Describes if the body is full/half there etc, 0.5 = 50%
	 *
	 * @since 29.11.2012 12:46:22
	 *
	 * @return
	 */
	public double getBodyIntegrity() {
		return moBodyIntegrity;
	}

	public void setBodyIntegrity(double poBodyIntegrity) {
		this.moBodyIntegrity = poBodyIntegrity;
	}
	
	protected abstract void setBodyType();
	
}
