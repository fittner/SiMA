/**
 * clsE_GenericInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 14.04.2011, 14:57:39
 */
package statictools.eventlogger;

import java.awt.BorderLayout;

import sim.portrayal.Inspector;
import sim.util.gui.HTMLBrowser;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.04.2011, 14:57:39
 * 
 */
public class clsEventLoggerInspector extends Inspector {
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 22.04.2011, 23:32:57
	 */
	private static final long serialVersionUID = -7402285636384610376L;
	
    public clsEventLoggerInspector()    {
    	setVolatile(true);
        setLayout(new BorderLayout());
    	
    	try {
    		add(new HTMLBrowser(getHTML()), BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		
    }	
   
    private String getHTML() {
    	return clsEventLogger.toHtml();
    }
    
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		try {
			if (clsEventLogger.isHtmlDirty()) {
				removeAll();
				add(new HTMLBrowser(getHTML()), BorderLayout.CENTER);
			}
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}    
}
