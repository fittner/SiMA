/**
 * CHANGELOG
 *
 * 19.11.2012 LuHe - File created
 *
 */
package inspectors.mind.pa._v38;

import inspectors.mind.pa._v38.autocreated.clsParameterInspector;
import pa.clsPsychoAnalysis;
import pa._v38.clsProcessor;
import pa._v38.modules.clsPsychicApparatus;

/**
 * DOCUMENT (LuHe) - insert description 
 * 
 * @author LuHe
 * 19.11.2012, 10:14:21
 * 
 */
public class clsInspectorTab_PersonalityParameter extends clsParameterInspector{

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
