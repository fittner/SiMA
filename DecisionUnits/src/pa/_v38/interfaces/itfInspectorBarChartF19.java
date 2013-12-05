/**
 * CHANGELOG
 *
 * 12.04.2013 Lotfi - File created
 *
 */
package pa._v38.interfaces;

import java.util.HashMap;

/**
 * DOCUMENT (Lotfi) - insert description 
 * 
 * @author Lotfi
 * *
 * 12.04.2013, 14:54:02
 * ChartBar for F19
 */
public interface itfInspectorBarChartF19 {
	public String getBarChartTitle();
	/**
	 * Returns an array that contains the current set of data. The main Array List has to have the same number of entries as the array returned by getAreaChartAreaCaptions. And the sub Array Lists has to have the same number of entries a the array returned by getAreaChartColumnCaptions
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	//public ArrayList<ArrayList<Double>> getBarChartData();
	public HashMap<String, Double> getBarChartData();

}
