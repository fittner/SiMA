/**
 * CHANGELOG
 *
 * 19.11.2012 LuHe - File created
 *
 */
package inspectors.mind.pa._v38;

import base.modules.clsPsychicApparatus;
import control.clsProcessor;
import control.clsPsychoAnalysis;
import inspectors.mind.pa._v38.autocreated.clsParameterInspector;

/**
 * DOCUMENT (LuHe) - insert description 
 * 
 * @author LuHe
 * 19.11.2012, 10:14:21
 * 
 */
public class clsInspectorTab_PersonalityParameter extends clsParameterInspector{

	/** DOCUMENT (herret) - insert description; @since 08.07.2013 10:29:02 */
	private static final long serialVersionUID = 9067176975952664407L;
	clsPsychicApparatus moPsyAp;
	
	public clsInspectorTab_PersonalityParameter(clsPsychoAnalysis poPA){
		moPsyAp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
		moPPS=moPsyAp.moPersonalityParameterContainer;
	}

	/* (non-Javadoc)
	 *
	 * @since 04.07.2013 13:09:41
	 * 
	 * @see inspectors.mind.pa._v38.autocreated.clsParameterInspector#updateData()
	 */
	@Override
	protected void updateData() {
		moPPS = moPsyAp.moPersonalityParameterContainer;
		
	}

}
