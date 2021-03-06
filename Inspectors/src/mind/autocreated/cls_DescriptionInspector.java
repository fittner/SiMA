/**
 * clsE_DescriptionInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 */
package mind.autocreated;

import inspector.interfaces.itfInterfaceDescription;

import java.util.Iterator;

import modules.interfaces.eInterfaces;
import base.tools.toText;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 * 
 */
public class cls_DescriptionInspector extends cls_GenericTEXTInspector {

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
	 * @see inspectors.mind.pa._v38.clsE_GenericInspector#setTitle()
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
	 * @see inspectors.mind.pa._v38.clsE_GenericInspector#updateContent()
	 */
	@Override
	protected void updateContent() {
		moContent  = toText.h2("Module");
		moContent += toText.p(((itfInterfaceDescription)moObject).getDescription());
		
		moContent += toText.h2("Incoming Interfaces");
		for (Iterator<eInterfaces> it = ((itfInterfaceDescription)moObject).getInterfacesRecv().iterator();it.hasNext();) {
			eInterfaces eI = it.next();
			moContent += toText.li(toText.b(eI.toString())+": "+eI.getDescription());
		}
		moContent += toText.newline;
		
		moContent += toText.h2("Outgoing Interfaces");
		for (Iterator<eInterfaces> it = ((itfInterfaceDescription)moObject).getInterfacesSend().iterator();it.hasNext();) {
			eInterfaces eI = it.next();
			moContent += toText.li(toText.b(eI.toString())+": "+eI.getDescription());
		}
		moContent += toText.newline;
	}

}
