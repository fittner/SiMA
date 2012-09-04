/**
 * CHANGELOG
 *
 * Aug 30, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Aug 30, 2012, 3:41:59 PM
 * 
 */
public interface itfInspectorAreaChart {
	/**
	 * Returns the title of the area chart
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public String getAreaChartTitle();
	
	/**
	 * Returns an array that contains the current set of data. The main Array List has to have the same number of entries as the array returned by getAreaChartAreaCaptions. And the sub Array Lists has to have the same number of entries a the array returned by getAreaChartColumnCaptions
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getAreaChartData();
	
	/**
	 * Returns an array that contains the labels of all Areas.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getAreaChartAreaCaptions();
	
	/**
	 * Returns an array that contains the labels for all Columns
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getAreaChartColumnCaptions();
}
