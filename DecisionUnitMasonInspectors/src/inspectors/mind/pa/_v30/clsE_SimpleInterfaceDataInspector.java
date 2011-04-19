/**
 * clsE_SimpleInterfaceDataInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 19.04.2011, 12:17:43
 */
package inspectors.mind.pa._v30;

import java.util.ArrayList;
import java.util.SortedMap;
import pa.interfaces._v30.eInterfaces;
import pa.modules._v30.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 12:17:43
 * 
 */
public class clsE_SimpleInterfaceDataInspector extends
		clsE_GenericHTMLInspector {

	private ArrayList<eInterfaces> moRecv;
	private ArrayList<eInterfaces> moSend;	
	private SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.04.2011, 12:17:51
	 */
	private static final long serialVersionUID = -781003084028878854L;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.04.2011, 12:17:47
	 *
	 * @param poModule
	 */
	public clsE_SimpleInterfaceDataInspector(clsModuleBase poModule, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) {
		super(poModule);
		moRecv = moModule.getInterfacesRecv();
		moSend = moModule.getInterfacesSend();
		moInterfaceData = poInterfaceData;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 12:17:43
	 * 
	 * @see inspectors.mind.pa._v30.clsE_GenericHTMLInspector#setTitle()
	 */
	@Override
	protected void setTitle() {
		moTitle = moModule.getClass().getSimpleName() + " - Interface Data";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 12:17:43
	 * 
	 * @see inspectors.mind.pa._v30.clsE_GenericHTMLInspector#updateContent()
	 */
	@Override
	protected void updateContent() {
		moContent  = "<h2>Receive</h2>";
		if (moRecv != null) {
			for (eInterfaces eRcv:moRecv) {
				moContent += "<h3>"+eRcv+"</h3>";
				moContent += "<ul>";
				try {
					for (Object data:moInterfaceData.get(eRcv)) {
						moContent += "<li>"+data+"</li>";
					}
				} catch (java.lang.Exception e) {
					moContent += "<li><i>n/a</li></li>";
				}
				moContent += "</ul>";
			}
		}
		
		moContent += "<h2>Send</h2>";
		if (moSend != null) {
			for (eInterfaces eSnd:moSend) {
				moContent += "<h3>"+eSnd+"</h3>";
				moContent += "<ul>";
				try {
					for (Object data:moInterfaceData.get(eSnd)) {
						moContent += "<li>"+data+"</li>";					
					}
				} catch (java.lang.Exception e) {
					moContent += "<li><i>n/a</li></li>";
				}				
				moContent += "</ul>";
			}		
		}
	}
}
