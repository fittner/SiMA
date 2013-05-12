/**
 * itfInspectorTimeChartBase.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 11:13:54
 */
package pa._v38.interfaces;

import java.util.ArrayList;

/**
 * Abstract inspector interface that defines the four basic methods that are needed for an auto-created timechart inspector tab. The x axis is always time. The y axis, 
 * the title and the labels for the individual lines can be configured.
 * 
 * @author deutsch
 * 23.04.2011, 11:13:54
 * 
 */
public abstract interface itfInspectorTimeChartBase {
	/**
	 * Returns the label for the y-axis.
	 *
	 * @since 12.07.2011 11:39:24
	 *
	 * @return
	 */
	public String getTimeChartAxis();
	
	/**
	 * Returns the label for the chart.
	 *
	 * @since 12.07.2011 11:40:51
	 *
	 * @return
	 */
	public String getTimeChartTitle();	
	
	/**
	 * Returns an array that contains the current set of data. Has to have the same number of entries as the array returned by getTimeChartCaptions.
	 *
	 * @since 12.07.2011 11:41:25
	 *
	 * @return
	 */
	public ArrayList<Double> getTimeChartData();
	
	/**
	 * Returns an array that contains the labels for all lines. Has to have the same number of entries as the array returned by getTimeChartData.
	 *
	 * @since 12.07.2011 11:42:16
	 *
	 * @return
	 */
	public ArrayList<String> getTimeChartCaptions();
}
