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
import bw.entities.clsEntity;
import bw.utils.enums.eBodyType;
import config.clsBWProperties;

/**
 * 
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
	
	
	public clsBaseBody(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		setBodyType();
		applyProperties(poPrefix, poProp, poEntity);	
	}

	private void applyProperties(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		String pre = clsBWProperties.addDot(poPrefix);

		moAttributes    = new clsAttributes(pre+P_ATTRIBUTES, poProp, poEntity);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsAttributes.getDefaultProperties(pre+P_ATTRIBUTES) );
			
		return oProp;
	}	

	public clsAttributes getAttributes() {
		   return moAttributes;
	}
	
	public eBodyType getBodyType() {
		return meBodyType;
	}
	
	protected abstract void setBodyType();
	
}
