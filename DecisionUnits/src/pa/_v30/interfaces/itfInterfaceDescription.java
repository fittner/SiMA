/**
 * itfInterfaceDescription.java: DecisionUnits - pa.interfaces._v30
 * 
 * @author deutsch
 * 19.04.2011, 10:24:52
 */
package pa._v30.interfaces;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 10:24:52
 * 
 */
public interface itfInterfaceDescription {
	public String getDescription();
	public ArrayList<eInterfaces> getInterfacesRecv();
	public ArrayList<eInterfaces> getInterfacesSend();
}
