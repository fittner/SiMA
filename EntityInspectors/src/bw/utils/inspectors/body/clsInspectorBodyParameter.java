/**
 * CHANGELOG
 *
 * 08.07.2013 herret - File created
 *
 */
package bw.utils.inspectors.body;

import body.clsComplexBody;
import inspectors.mind.pa._v38.autocreated.clsParameterInspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 08.07.2013, 10:31:06
 * 
 */
public class clsInspectorBodyParameter extends clsParameterInspector {

	/** DOCUMENT (herret) - insert description; @since 08.07.2013 10:35:38 */
	private static final long serialVersionUID = -6536469456940721397L;
	private clsComplexBody moComplexBody;
	
	public clsInspectorBodyParameter(clsComplexBody poComplexBody){
		
		moComplexBody = poComplexBody;
		updateData();
	}
	
	
	/* (non-Javadoc)
	 *
	 * @since 08.07.2013 10:31:06
	 * 
	 * @see inspectors.mind.pa._v38.autocreated.clsParameterInspector#updateData()
	 */
	@Override
	protected void updateData() {
		moPPS = moComplexBody.getPersonalityParameter();

	}

}
