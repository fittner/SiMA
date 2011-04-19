/**
 * itfInspectorGenericTimeChart.java: DecisionUnits - pa.interfaces._v30
 * 
 * @author deutsch
 * 19.04.2011, 10:32:39
 */
package pa.interfaces._v30;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 10:32:39
 * 
 */
public interface itfInspectorGenericTimeChart extends itfInspectorTimeChart {
	public String getTimeChartAxis();
	public String getTimeChartTitle();
	public double getTimeChartUpperLimit();
	public double getTimeChartLowerLimit();

}
