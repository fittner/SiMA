/**
 * clsE_SimpleInterfaceDataInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 19.04.2011, 12:17:43
 */
package inspectors.mind.pa._v30.autocreated;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInterfaceInterfaceData;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 12:17:43
 * 
 */
public class clsE_SimpleInterfaceDataInspector extends
		cls_GenericHTMLInspector {

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
	public clsE_SimpleInterfaceDataInspector(itfInterfaceInterfaceData poModule, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) {
		super(poModule);
		moRecv = ((itfInterfaceInterfaceData)moObject).getInterfacesRecv();
		moSend = ((itfInterfaceInterfaceData)moObject).getInterfacesSend();
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
		moTitle = moObject.getClass().getSimpleName() + " - Interface Data";
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
			for (Iterator<eInterfaces> it = moRecv.iterator();it.hasNext();) {
				eInterfaces eRcv = it.next();
				moContent += "<h3>"+eRcv+"</h3>";
				moContent += "<ul>";
				try {
					for (Iterator<Object> it2 = moInterfaceData.get(eRcv).iterator(); it2.hasNext();) {
						Object data = it2.next();
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
			for (Iterator<eInterfaces> it = moSend.iterator();it.hasNext();) {
				eInterfaces eSnd = it.next();
				moContent += "<h3>"+eSnd+"</h3>";
				moContent += "<ul>";
				try {
					for (Iterator<Object> it2 = moInterfaceData.get(eSnd).iterator(); it2.hasNext();) {
						Object data = it2.next();
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
