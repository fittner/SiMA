/**
 * CHANGELOG
 *
 * 19.12.2012 Lotfi - File created
 *
 */
package pa._v38.interfaces;

//import java.util.ArrayList;
import java.util.HashMap;

/**
 * DOCUMENT (Lotfi) - insert description 
 * 
 * @author Lotfi
 * 19.12.2012, 23:01:48
 * 
 */
public interface itfInspectorBarChartF06 {
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
	/**
	 * Returns an array that contains the labels of all Categories. Has to have the same number of entries as the main Array List return by getAreaChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	//public ArrayList<String> getBarChartCategoryCaptions1();
	
	/**
	 * Returns an array that contains the labels for all Columns. Has to have the same number of entries as the sub ArrayList retruned by getAreaChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	//public ArrayList<String> getBarChartColumnCaptions();

}
