/**
 * CHANGELOG
 *
 * Oct 31, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;


/**
 * Implement this interface to display a graph with the return objects by getGraphData() within an inspector tab.
 * 
 * @author herret
 * Oct 31, 2012, 10:19:21 AM
 * 
 */
public interface itfGraphData {
	/**
	 * A list of data that should be displayed within the graph
	 *
	 * @since 12.07.2011 11:30:19
	 *
	 * @return
	 */
	public ArrayList<Object> getGraphData();
	
}
