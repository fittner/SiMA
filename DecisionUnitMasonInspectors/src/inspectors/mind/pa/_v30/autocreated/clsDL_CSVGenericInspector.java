/**
 * clsDL_CSVGenericInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.autocreated
 * 
 * @author deutsch
 * 23.04.2011, 17:17:01
 */
package inspectors.mind.pa._v30.autocreated;

import pa._v30.datalogger.clsDLEntry_Abstract;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 17:17:01
 * 
 */
public class clsDL_CSVGenericInspector extends cls_GenericHTMLInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 17:17:08
	 */
	private static final long serialVersionUID = -6031223024023472087L;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 17:17:04
	 *
	 * @param poObject
	 */
	public clsDL_CSVGenericInspector(clsDLEntry_Abstract poObject) {
		super(poObject);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 17:17:01
	 * 
	 * @see inspectors.mind.pa._v30.autocreated.cls_GenericHTMLInspector#setTitle()
	 */
	@Override
	protected void setTitle() {
		moTitle = ((clsDLEntry_Abstract)moObject).getName()+" - History";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 17:17:01
	 * 
	 * @see inspectors.mind.pa._v30.autocreated.cls_GenericHTMLInspector#updateContent()
	 */
	@Override
	protected void updateContent() {
		moContent  = ((clsDLEntry_Abstract)moObject).toHTML_TABLE();
	}

}
