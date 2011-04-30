/**
 * clsInspectorPositionLogger.java: BW - bw.utils.inspectors.entity
 * 
 * @author deutsch
 * 30.04.2011, 14:16:23
 */
package bw.utils.inspectors.entity;

import java.awt.BorderLayout;
import bw.entities.logger.clsPositionLogger;
import panels.TextOutputPanel;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 30.04.2011, 14:16:23
 * 
 */
public class clsInspectorPositionLoggerCSV extends Inspector {
	private TextOutputPanel moText;
	private clsPositionLogger moPositionLogger;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 30.04.2011, 14:16:41
	 */
	private static final long serialVersionUID = 8002791087184023730L;
	
	public clsInspectorPositionLoggerCSV(clsPositionLogger poPL) {
		moPositionLogger = poPL;
		
	    moText = new TextOutputPanel(getContent());
		
		setLayout(new BorderLayout());
    	
    	try {
    		add(moText, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}
	
	private String getContent() {
		return moPositionLogger.toCSV();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 30.04.2011, 14:16:36
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		try {
			if (isVisible()) {
				moText.setText(getContent());
			}
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}

}
