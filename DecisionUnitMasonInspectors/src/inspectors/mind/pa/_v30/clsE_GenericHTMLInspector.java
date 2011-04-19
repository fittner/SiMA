/**
 * clsE_GenericInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 14.04.2011, 14:57:39
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;
import pa.modules._v30.clsModuleBase;
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
public abstract class clsE_GenericHTMLInspector extends Inspector {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 14.04.2011, 15:02:15
	 */
	private static final long serialVersionUID = -2033800775072753378L;
	protected clsModuleBase moModule;
	protected String moTitle;
	protected String moContent;
	HTMLBrowser moHTMLPane;
	
    public clsE_GenericHTMLInspector(clsModuleBase poModule)
    {
		moModule = poModule;
    	
    	setTitle();
    	updateContent();
    	
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(getHTML());
    	
    	try {
			add(moHTMLPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
    }	
    
    protected abstract void setTitle();
    protected abstract void updateContent();
    
    private String getHTML() {
    	String html;
    	
    	html  = "<html><head></head><body>";
    	html += "<h1>"+moTitle+"</h1>";	
    	html += "<p>"+moContent+"</p>";
    	html += "</body></html>";
    	
    	return html;
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
		updateContent();
		
		try {
			moHTMLPane.setText(getHTML());
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
	}    
}