/**
 * clsPAInspectorInterfaceData.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 19.04.2011, 14:20:12
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import pa.interfaces._v30.eInterfaces;
import sim.portrayal.Inspector;
import sim.util.gui.HTMLBrowser;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 14:20:12
 * 
 */
public class clsPAInspectorInterfaceData extends Inspector {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.04.2011, 14:20:50
	 */
	private SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
	private static final long serialVersionUID = 1042986825561786694L;
	protected String moTitle;
	protected String moContent;
	HTMLBrowser moHTMLPane;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.04.2011, 14:20:16
	 *
	 * @param poModule
	 */
	public clsPAInspectorInterfaceData(SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) {
		moInterfaceData = poInterfaceData;
    	    	
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

	protected void setTitle() {
		moTitle = "Interface Data";
	}

	protected void updateContent() {
		moContent  = "";
		for (Map.Entry<eInterfaces, ArrayList<Object>> e:moInterfaceData.entrySet()) {
			moContent += "<h2>"+e.getKey()+"</h2>";
			moContent += "<p>"+e.getKey().getDescription()+"</p>";
			moContent += "<ul>";
			for (Object data:e.getValue()) {
				if (data == null) {
					moContent += "<li><i>n/a</i></li>";
				} else {
					moContent += "<li>"+data+"</li>";
				}
			}
			moContent += "</ul>";
		}
	}

}
