/**
 * clsE_DescriptionInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 */
package inspectors.mind.pa._v30.autocreated;

import java.util.Iterator;

import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInterfaceDescription;
import pa._v30.tools.toText;

/**
 * 
 * 
 * @author deutsch
 * 15.04.2011, 15:03:10
 * 
 */
public class cls_DescriptionInspector extends cls_GenericTEXTInspector {

	/**
	 * 
	 * 
	 * @author deutsch
	 * 15.04.2011, 18:07:16
	 */
	private static final long serialVersionUID = 9138148242374689745L;

	/**
	 * 
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
