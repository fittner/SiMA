/**
 * clsE_DescriptionInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 */
package inspectors.mind.pa._v30.autocreated;

import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInterfaceDescription;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 * 
 */
public class cls_DescriptionInspector extends cls_GenericHTMLInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 18:07:16
	 */
	private static final long serialVersionUID = 9138148242374689745L;

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
	public cls_DescriptionInspector(itfInterfaceDescription poObject) {
		super(poObject);
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
		moTitle = moObject.getClass().getSimpleName() + " - Description";
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
		moContent += "<p>"+((itfInterfaceDescription)moObject).getDescription()+"</p>";
		moContent += "<h2>Incoming Interfaces</h2>";
		moContent += "<ul>";
		for (eInterfaces eI:((itfInterfaceDescription)moObject).getInterfacesRecv()) {
			moContent += "<li><b>"+eI+"</b>: "+eI.getDescription()+"</li>";
		}
		moContent += "</ul>";
		
		moContent += "<h2>Outgoing Interfaces</h2>";
		moContent += "<ul>";
		for (eInterfaces eI:((itfInterfaceDescription)moObject).getInterfacesSend()) {
			moContent += "<li><b>"+eI+"</b>: "+eI.getDescription()+"</li>";
		}
		moContent += "</ul>";
	}

}
