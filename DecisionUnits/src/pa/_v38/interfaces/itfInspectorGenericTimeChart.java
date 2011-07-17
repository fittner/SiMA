/**
 * itfInspectorGenericTimeChart.java: DecisionUnits - pa.interfaces._v38
 * 
 * @author deutsch
 * 19.04.2011, 10:32:39
 */
package pa._v38.interfaces;

/**
 * If an module implements this interface, a time chart inspector tab is added that displays the provided data.
 * 
 * @author deutsch
 * 19.04.2011, 10:32:39
 * 
 */
public interface itfInspectorGenericTimeChart extends itfInspectorTimeChartBase {
	/**
	 * Returns the defined maximum value for the displayed values.
	 *
	 * @since 12.07.2011 11:44:39
	 *
	 * @return
	 */
	public double getTimeChartUpperLimit();
	
	/**
	 * Returns the defined minimum value for the displayed values.
	 *
	 * @since 12.07.2011 11:44:42
	 *
	 * @return
	 */
	public double getTimeChartLowerLimit();

}
