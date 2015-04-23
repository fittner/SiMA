/**
 * CHANGELOG
 *
 * Nov 6, 2012 herret - File created
 *
 */
package inspector.interfaces;

import java.util.ArrayList;

import modules.interfaces.eInterfaces;

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
