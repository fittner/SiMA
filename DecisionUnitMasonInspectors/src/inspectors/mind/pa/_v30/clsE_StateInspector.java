/**
 * clsE_StateInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 14.04.2011, 18:11:46
 */
package inspectors.mind.pa._v30;

import pa.modules._v30.clsModuleBase;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.04.2011, 18:11:46
 * 
 */
public class clsE_StateInspector extends clsE_GenericInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 14.04.2011, 18:11:56
	 */
	private static final long serialVersionUID = -1727640620970155658L;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 14.04.2011, 18:11:52
	 *
	 * @param originalInspector
	 * @param wrapper
	 * @param guiState
	 * @param poModule
	 */
	public clsE_StateInspector(Inspector originalInspector,
			LocationWrapper wrapper, GUIState guiState, clsModuleBase poModule) {
		super(originalInspector, wrapper, guiState, poModule);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 18:11:46
	 * 
	 * @see inspectors.mind.pa._v30.clsE_GenericInspector#setTitle()
	 */
	@Override
	protected void setTitle() {
		moTitle = moModule.getClass().getSimpleName();

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 18:11:46
	 * 
	 * @see inspectors.mind.pa._v30.clsE_GenericInspector#updateContent()
	 */
	@Override
	protected void updateContent() {
		moContent = moModule.stateToHTML();
	}

}
