/**
 * clsI_SimpleInterfaceDataInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 19.04.2011, 18:12:40
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.tools.clsPair;
import pa._v30.interfaces.eInterfaces;
import sim.portrayal.Inspector;
import sim.util.gui.HTMLBrowser;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 18:12:40
 * 
 */
public class clsI_SimpleInterfaceDataInspector extends Inspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.04.2011, 18:19:39
	 */
	private static final long serialVersionUID = 8604854877413797459L;
	private SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
	private HashMap<eInterfaces, clsPair<ArrayList<Integer>, ArrayList<Integer>>> moInterfaces_Recv_Send;
	private eInterfaces mnInterface;
	protected String moTitle;
	protected String moContent;
	private String moStaticContent;
	HTMLBrowser moHTMLPane;
	
    public clsI_SimpleInterfaceDataInspector(eInterfaces pnInterface, 
    		SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
    		HashMap<eInterfaces, clsPair<ArrayList<Integer>, ArrayList<Integer>>> poInterfaces_Recv_Send)
    {
    	moInterfaceData = poInterfaceData;
    	moInterfaces_Recv_Send = poInterfaces_Recv_Send;
    	mnInterface = pnInterface;
    	
    	setTitle();
    	setStaticContent();
    	updateContent();
    	
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(getHTML());
    	
    	try {
			add(moHTMLPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
    }	
    
	protected void setTitle() {
		moTitle = "Interface Data - "+mnInterface;
	}
	
	private void setStaticContent() {
		moStaticContent = "";
		
		moStaticContent += "<h2>Description</h2>";
		moStaticContent += "<p>"+mnInterface.getDescription()+"</p>";
		moStaticContent += "<p><b>Receive from modules</b>: ";
		for (Integer oI:moInterfaces_Recv_Send.get(mnInterface).b) {
			moStaticContent += "E"+oI+", ";
		}
		moStaticContent += "</p>";
		moStaticContent += "<p><b>Send to modules</b>: ";
		for (Integer oI:moInterfaces_Recv_Send.get(mnInterface).a) {
			moStaticContent += "E"+oI+", ";
		}
		moStaticContent += "</p>";			
	}
	
    protected void updateContent() {
    	moContent  = moStaticContent;
		
		moContent += "<h2>Data</h2>";
		moContent += "<ul>";

		for (Object data:moInterfaceData.get(mnInterface)) {
			if (data == null) {
				moContent += "<li><i>n/a</i></li>";
			} else {
				moContent += "<li>"+data+"</li>";
			}
		}
		moContent += "</ul>";
    }
    
    private String getHTML() {
    	String html;
    	
    	html  = "<html><head></head><body>";
    	html += "<h1>"+moTitle+"</h1>";	
    	html += moContent;
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
