/**
 * clsDLEntry_DynamicTimeChart.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 16:09:48
 */
package pa._v30.datalogger;

import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v30.modules.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 16:09:48
 * 
 */
public class clsDLEntry_DynamicTimeChart extends clsDLEntry_Abstract implements itfInspectorGenericDynamicTimeChart {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 16:12:42
	 *
	 * @param poModule
	 */
	public clsDLEntry_DynamicTimeChart(clsModuleBase poModule) {
		super(poModule);
		// TODO (deutsch) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:12:58
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		// TODO (deutsch) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:12:58
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		// TODO (deutsch) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:12:58
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericDynamicTimeChart#chartRowsChanged()
	 */
	@Override
	public boolean chartRowsChanged() {
		// TODO (deutsch) - Auto-generated method stub
		return false;
	}

}
