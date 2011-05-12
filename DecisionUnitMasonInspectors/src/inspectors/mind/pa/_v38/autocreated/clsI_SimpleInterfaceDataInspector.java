/**
 * clsI_SimpleInterfaceDataInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38
 * 
 * @author deutsch
 * 19.04.2011, 18:12:40
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.eInterfaces;
import panels.TextOutputPanel;
import sim.portrayal.Inspector;
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
	TextOutputPanel moTEXTPane;
	
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
    	moTEXTPane = new TextOutputPanel(getTEXT());
    	
    	try {
			add(moTEXTPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
    }	
    
	protected void setTitle() {
		moTitle = "Interface Data - "+mnInterface;
	}
	
	private void setStaticContent() {
		moStaticContent = "";
		
		moStaticContent += toText.h2("Description");
		moStaticContent += toText.p(mnInterface.getDescription());
		moStaticContent += toText.b("Receive from modules</b>")+": ";
		
		for (int i=0; i<moInterfaces_Recv_Send.get(mnInterface).b.size(); i++) {
			Integer oI = moInterfaces_Recv_Send.get(mnInterface).b.get(i);
			moStaticContent += "E"+oI+", ";
		}
		moStaticContent += toText.newline;
		moStaticContent += toText.b("Send to modules</b>")+": ";
		for (int i=0; i<moInterfaces_Recv_Send.get(mnInterface).a.size(); i++) {
			Integer oI = moInterfaces_Recv_Send.get(mnInterface).a.get(i);
			moStaticContent += "E"+oI+", ";
		}
		moStaticContent += toText.newline;			
	}
	
    protected void updateContent() {
    	moContent  = moStaticContent;
		
		moContent += toText.h2("Data");

		for (int i=0; i<moInterfaceData.get(mnInterface).size();i++) {
			Object data = moInterfaceData.get(mnInterface).get(i);

			if (data == null) {
				moContent += toText.li(toText.i("n/a"));
			} else {
				moContent += toText.li(data.toString());
			}
		}
		moContent += toText.newline;
    }
    
    private String getTEXT() {
    	String text;
    	
    	text  = "";
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

}
