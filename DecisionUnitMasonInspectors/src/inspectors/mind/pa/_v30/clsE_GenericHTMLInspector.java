/**
 * clsE_GenericInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 14.04.2011, 14:57:39
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;
import pa.modules._v30.clsModuleBase;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

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
	
	public Inspector moInspector;
	HTMLBrowser moHTMLPane;
	
    public clsE_GenericHTMLInspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsModuleBase poModule)
    {
    	moInspector  = originalInspector;
    	moModule = poModule;
    	
    	setTitle();
    	updateContent();
    	
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(getHTML());
		add(moHTMLPane, BorderLayout.CENTER);
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
		moHTMLPane.setText(getHTML());
	}    
}
