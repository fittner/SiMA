/**
 * clsDL_CSVGenericInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.autocreated
 * 
 * @author deutsch
 * 23.04.2011, 17:17:01
 */
package inspectors.mind.pa._v30.autocreated;

import java.awt.BorderLayout;

import pa._v30.datalogger.clsDLEntry_Abstract;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 17:17:01
 * 
 */
public class clsDL_CSVGenericInspector extends Inspector {
	private clsDLEntry_Abstract moDL; 
	private TextOutputPanel moTextPane;
	
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
		super();
		
		moDL = poObject;
		
        setLayout(new BorderLayout());
        moTextPane = new TextOutputPanel(getCSV());
    	
    	try {
			add(moTextPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

	private String getCSV() {
		return moDL.toCSV();
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 22:15:06
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		try {
			if (isVisible()) {
				moTextPane.setText(getCSV());
			}
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

}
