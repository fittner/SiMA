/**
 * CHANGELOG
 *
 * Nov 6, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

import pa._v38.interfaces.modules.eInterfaces;

/**
 * If this interface is implemented by a module, an inspector tab that displays the interfaces returned by getGraphInterfaces() is added.
 * 
 * @author herret
 * Nov 6, 2012, 10:32:05 AM
 * 
 */
public interface itfGraphInterface {

	/**
	 * Returns the interfaces that should be displayed in the graph.
	 *
	 * @since 06.11.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<eInterfaces> getGraphInterfaces();
}
