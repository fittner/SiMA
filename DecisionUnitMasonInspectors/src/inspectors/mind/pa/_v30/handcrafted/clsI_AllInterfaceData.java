/**
 * clsPAInspectorInterfaceData.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 19.04.2011, 14:20:12
 */
package inspectors.mind.pa._v30.handcrafted;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import pa._v30.interfaces.eInterfaces;
import pa._v30.tools.toText;
import panels.TextOutputPanel;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * 
 * 
 * @author deutsch
 * 19.04.2011, 14:20:12
 * 
 */
public class clsI_AllInterfaceData extends Inspector {
	/**
	 * 
	 * 
	 * @author deutsch
	 * 19.04.2011, 14:20:50
	 */
	private SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
	private static final long serialVersionUID = 1042986825561786694L;
	protected String moTitle;
	protected String moContent;
	TextOutputPanel moTEXTPane;
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 19.04.2011, 14:20:16
	 *
	 * @param poModule
	 */
	public clsI_AllInterfaceData(SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) {
		moInterfaceData = poInterfaceData;
    	    	
    	setTitle();
    	updateContent();
    	
        setLayout(new BorderLayout());
    	moTEXTPane = new TextOutputPanel(getTEXT());
    	
    	try {
			add(moTEXTPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}		
	}
	
    private String getTEXT() {
    	String text="";
    	
    	text += toText.h1(moTitle);	
    	text += toText.p(moContent);
    	
    	return text;
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
			moTEXTPane.setText(getTEXT());
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
			moContent += toText.h2(e.getKey().toString());
			moContent += toText.p(e.getKey().getDescription());

			for (Object data:e.getValue()) {
				if (data == null) {
					moContent += toText.li(toText.i("n/a"));
				} else {
					moContent += toText.li(data.toString());
				}
			}
			moContent += toText.newline;
		}
	}

}
