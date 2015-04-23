/**
 * itfInterfaceDescription.java: DecisionUnits - pa.interfaces._v38
 * 
 * @author deutsch
 * 19.04.2011, 10:24:52
 */
package inspector.interfaces;

import java.util.ArrayList;

import modules.interfaces.eInterfaces;

/**
 * If this interface is implemented by a module, an inspector tab that displays the description of the module and the incoming and outgoing interfaces 
 * is added.
 * 
 * @author deutsch
 * 19.04.2011, 10:24:52
 * 
 */
public interface itfInterfaceDescription {
	/**
	 * A text-based description of the module is expected as return value of this method.
	 *
	 * @since 12.07.2011 11:29:34
	 *
	 * @return
	 */
	public String getDescription();
	
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
