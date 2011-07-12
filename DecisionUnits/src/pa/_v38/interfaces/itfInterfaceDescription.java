/**
 * itfInterfaceDescription.java: DecisionUnits - pa.interfaces._v38
 * 
 * @author deutsch
 * 19.04.2011, 10:24:52
 */
package pa._v38.interfaces;

import java.util.ArrayList;

import pa._v38.interfaces.modules.eInterfaces;

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
