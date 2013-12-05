/**
 * itfInspectorGenericDynamicTimeChart.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 12:51:52
 */
package pa._v38.interfaces;

/**
 * A specialization of the inspector interface itfInspectorGenericTimeChart: it reacts to changes in the number of columns to be displayed. Usefull for example 
 * if not all values are present at each time.
 * 
 * @author deutsch
 * 23.04.2011, 12:51:52
 * 
 */
public interface itfInspectorGenericDynamicTimeChart extends
		itfInspectorGenericTimeChart {
	/**
	 * Returns true if the internal state of the module has been changed in respect of the number of columns/lines in the time chart. 
	 *
	 * @since 12.07.2011 11:46:47
	 *
	 * @return
	 */
	public boolean chartColumnsChanged();
	
	/**
	 * This method is called by the inspector to inform the module that the chart has been updated to the new set of columnes/lines. 
	 *
	 * @since 12.07.2011 11:46:51
	 *
	 */
	public void chartColumnsUpdated();
}
