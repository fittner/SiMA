/**
 * CHANGELOG
 *
 * Sep 11, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

import pa._v38.interfaces.modules.eInterfaces;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 11, 2012, 2:47:14 PM
 * 
 */
public interface itfGraphCompareInterfaces {
	/**
	 * A list of all incoming interfaces that should be compared with outgoing interfaces of the module is expected as return value of this method. 
	 *
	 * @since 12.07.2011 11:30:19
	 *
	 * @return
	 */
	public ArrayList<eInterfaces> getCompareInterfacesRecv();
	
	/**
	 * A list of all outgoing interfaces hat should be compared with incoming  interfaces of the module is expected as return value of this method.
	 *
	 * @since 12.07.2011 11:30:54
	 *
	 * @return
	 */
	public ArrayList<eInterfaces> getCompareInterfacesSend();
}
