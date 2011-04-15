/**
 * clsE_DescriptionInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 */
package inspectors.mind.pa._v30;

import pa.interfaces._v30.eInterfaces;
import pa.modules._v30.clsModuleBase;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 * 
 */
public class clsE_DescriptionInspector extends clsE_GenericHTMLInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 15:03:15
	 *
	 * @param originalInspector
	 * @param wrapper
	 * @param guiState
	 * @param poModule
	 */
	public clsE_DescriptionInspector(Inspector originalInspector,
			LocationWrapper wrapper, GUIState guiState, clsModuleBase poModule) {
		super(originalInspector, wrapper, guiState, poModule);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 15:03:10
	 * 
	 * @see inspectors.mind.pa._v30.clsE_GenericInspector#setTitle()
	 */
	@Override
	protected void setTitle() {
		moTitle = moModule.getClass().getSimpleName() + " - Description";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 15:03:10
	 * 
	 * @see inspectors.mind.pa._v30.clsE_GenericInspector#updateContent()
	 */
	@Override
	protected void updateContent() {
		moContent  = "<h2>Module</h2>";
		moContent += "<p>"+moModule.getDescription()+"</p>";
		moContent += "<h2>Incoming Interfaces</h2>";
		moContent += "<ul>";
		for (eInterfaces eI:moModule.getInterfacesRecv()) {
			moContent += "<li><b>"+eI+"</b>: "+eI.getDescription()+"</li>";
		}
		moContent += "</ul>";
		
		moContent += "<h2>Outgoing Interfaces</h2>";
		moContent += "<ul>";
		for (eInterfaces eI:moModule.getInterfacesSend()) {
			moContent += "<li><b>"+eI+"</b>: "+eI.getDescription()+"</li>";
		}
		moContent += "</ul>";
	}

}
