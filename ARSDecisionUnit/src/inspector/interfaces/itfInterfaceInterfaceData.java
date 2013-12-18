/**
 * itfInterfaceInterface.java: DecisionUnits - pa.interfaces._v38
 * 
 * @author deutsch
 * 19.04.2011, 11:13:35
 */
package inspector.interfaces;

import java.util.ArrayList;

import modules.interfaces.eInterfaces;

/**
 * If this interface is implemented by a module, an inspector tab that displays the incoming and outgoing data in a auto-layouted graph is added.
 * 
 * @author deutsch
 * 19.04.2011, 11:13:35
 * 
 */
public interface itfInterfaceInterfaceData {
	/**
	 * A list of all incoming interfaces of the module is expected as return value of this method. 
	 *
	 * @since 12.07.2011 11:30:19
	 *
	 * @return
	 */
	public ArrayList<eInterfaces> getInterfacesRecv();
	
	/**
	 * A list of all outgoing interfaces of the module is expected as return value of this method.
	 *
	 * @since 12.07.2011 11:30:54
	 *
	 * @return
	 */
	public ArrayList<eInterfaces> getInterfacesSend();
}
