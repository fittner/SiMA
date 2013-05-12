/**
 * clsDLEntry_TimeChart.java: DecisionUnits - pa._v38.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 16:05:33
 */
package pa._v38.logger;

import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.modules.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 16:05:33
 * 
 */
public class clsDLEntry_TimeChart extends clsDLEntry_Abstract implements itfInspectorGenericTimeChart {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 16:05:36
	 *
	 * @param poModule
	 */
	public clsDLEntry_TimeChart(clsModuleBase poModule) {
		super(poModule);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:06:02
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return ((itfInspectorGenericTimeChart)moModule).getTimeChartUpperLimit();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:06:02
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return ((itfInspectorGenericTimeChart)moModule).getTimeChartLowerLimit();
	}

}
