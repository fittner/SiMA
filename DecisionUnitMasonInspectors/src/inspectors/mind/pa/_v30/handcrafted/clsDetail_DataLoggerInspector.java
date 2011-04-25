/**
 * clsCSV_DataLogger.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.handcrafted
 * 
 * @author deutsch
 * 23.04.2011, 17:06:40
 */
package inspectors.mind.pa._v30.handcrafted;


import java.awt.BorderLayout;
import pa._v30.logger.clsDataLogger;
import panels.TextOutputPanel;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 17:06:40
 * 
 */
public class clsDetail_DataLoggerInspector extends Inspector {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 17:08:19
	 */
	private static final long serialVersionUID = -2533270967364006767L;
	private clsDataLogger moDL;
	private TextOutputPanel moTextPane;
	
	public clsDetail_DataLoggerInspector(clsDataLogger poDL) {
		super();
		moDL = poDL;
		
        setLayout(new BorderLayout());
        moTextPane = new TextOutputPanel(getText());
    	
    	try {
			add(moTextPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

	private String getText() {
		return moDL.getDescription();
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
				moTextPane.setText(getText());
			}
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

}