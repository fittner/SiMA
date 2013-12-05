/**
 * CHANGELOG
 *
 * Aug 30, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

/**
 * If an module implements this interface, a spider web chart inspector tab is added that displays the provided data.
 * 
 * @author herret
 * Aug 30, 2012, 9:30:47 AM
 * 
 */
public interface itfInspectorSpiderWebChart {
	/**
	 * Returns the title of the spieder chart
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public String getSpiderWebChartTitle();
	
	/**
	 * Returns an array that contains the current set of data. Has to have the same number of entries as the array returned by getSpiderChartCaptions.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<Double> getSpiderChartData();
	
	/**
	 * Returns an array that contains the labels for all lines. Has to have the same number of entries as the array returned by getSpiderChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getSpiderChartCaptions();
	
	/**
	 * Returns the angle of the first axis
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public double getSpiderChartStartingAngle();
	
	
}
