/**
 * clsE_SimpleInterfaceDataInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 19.04.2011, 12:17:43
 */
package inspectors.mind.pa._v30.autocreated;

import java.util.ArrayList;
import java.util.SortedMap;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInterfaceInterfaceData;
import pa._v30.tools.toText;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 12:17:43
 * 
 */
public class clsE_SimpleInterfaceDataInspector extends
		cls_GenericTEXTInspector {

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
	 * @see inspectors.mind.pa._v30.clsE_GenericTEXTInspector#setTitle()
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
	 * @see inspectors.mind.pa._v30.clsE_GenericTEXTInspector#updateContent()
	 */
	@Override
	protected void updateContent() {
		moContent  = toText.h2("Receive");
		if (moRecv != null) {
			for (int i=0; i<moRecv.size(); i++) {
				eInterfaces eRcv = moRecv.get(i);
				moContent += toText.h3(eRcv.toString());

				try {
					for (int j=0; j<moInterfaceData.get(eRcv).size(); j++) {
						Object data = moInterfaceData.get(eRcv).get(j);
						moContent +=  toText.li(data.toString());
					}
				} catch (java.lang.Exception e) {
					moContent += toText.li(toText.i("n/a"));
				}
				moContent += toText.newline;
			}
		}
		
		moContent += toText.h2("Send");
		if (moSend != null) {
			for (int i=0; i<moSend.size(); i++) {
				eInterfaces eSnd = moSend.get(i);	
				moContent += toText.h3(eSnd.toString());

				try {
					for (int j=0; j<moInterfaceData.get(eSnd).size(); j++) {
						Object data = moInterfaceData.get(eSnd).get(j);
						moContent +=  toText.li(data.toString());
					}
				} catch (java.lang.Exception e) {
					moContent += toText.li(toText.i("n/a"));
				}				
				moContent += toText.newline;
			}		
		}
	}
}
