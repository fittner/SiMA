/**
 * clsCSV_DataLogger.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.handcrafted
 * 
 * @author deutsch
 * 23.04.2011, 17:06:40
 */
package inspectors.mind.pa._v30.handcrafted;

import java.awt.BorderLayout;

import pa._v30.logger.clsDataLogger;
import sim.portrayal.Inspector;
import sim.util.gui.HTMLBrowser;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 17:06:40
 * 
 */
@Deprecated
public class clsHTML_DataLoggerInspector extends Inspector {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 17:08:19
	 */
	private static final long serialVersionUID = -2533270967364006767L;
	private clsDataLogger moDL;
	private HTMLBrowser moHTMLPane;
	
	public clsHTML_DataLoggerInspector(clsDataLogger poDL) {
		super();
		moDL = poDL;
		
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(getHTML());
    	
    	try {
			add(moHTMLPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

	private String getHTML() {
		return moDL.toHTML();
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 17:07:18
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		try {
			if (isVisible()) {
				moHTMLPane.setText(getHTML());
			}
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

}
