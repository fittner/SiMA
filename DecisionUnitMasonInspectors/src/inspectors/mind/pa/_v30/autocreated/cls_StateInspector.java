/**
 * clsE_StateInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 14.04.2011, 18:11:46
 */
package inspectors.mind.pa._v30.autocreated;

import pa._v30.interfaces.itfInspectorInternalState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.04.2011, 18:11:46
 * 
 */
public class cls_StateInspector extends cls_GenericHTMLInspector {

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
	 * @param poObject
	 */
	public cls_StateInspector(itfInspectorInternalState poObject) {
		super(poObject);
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
		moTitle = moObject.getClass().getSimpleName()+" - Internal State of Module";

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
		moContent = ((itfInspectorInternalState)moObject).stateToHTML();
	}

}
