/**
 * CHANGELOG
 *
 * Nov 11, 2012 Snorry - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.interfaces.itfInspectorGenericTimeChart;

/**
 * Psychic energy per module to implement access for inspectors.
 * 
 * @author Kogelnig Philipp (e0225185)
 * @since 08.10.2012
 */

public class clsPsychicEnergyPerModule implements itfInspectorGenericTimeChart {

	public int mnModuleNr;
	public double mrAvailablePsychicEnergie = 0;
	public double mrConsumedPsychicEnergie = 0;
	public double mrRequestedPsychicEnergie = 0;
	public double mrRequestedPriority = 0;
	
	public clsPsychicEnergyPerModule(int pnModuleNr, double prAvailablePsychicEnergie, double prConsumedPsychicEnergie, double prRequestedPsychicEnergie, double prRequestedPriority) {
		mnModuleNr = pnModuleNr;
		mrAvailablePsychicEnergie = prAvailablePsychicEnergie;
		mrConsumedPsychicEnergie = prConsumedPsychicEnergie;
		mrRequestedPsychicEnergie = prRequestedPsychicEnergie;
		mrRequestedPriority = prRequestedPriority;	
	}
	
	public void  setValues(double prAvailablePsychicEnergie, double prConsumedPsychicEnergie, double prRequestedPsychicEnergie, double prRequestedPriority) {
		mrAvailablePsychicEnergie = prAvailablePsychicEnergie;
		mrConsumedPsychicEnergie = prConsumedPsychicEnergie;
		mrRequestedPsychicEnergie = prRequestedPsychicEnergie;
		mrRequestedPriority = prRequestedPriority;	
	}
	
	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		// TODO (Snorry) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return Integer.toString(mnModuleNr);
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		oValues.add(mrConsumedPsychicEnergie);
		oValues.add(mrAvailablePsychicEnergie);
		oValues.add(mrRequestedPsychicEnergie);
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oValues = new ArrayList<String>();
		oValues.add("Consumed PsychicEnergie");
		oValues.add("Available PsychicEnergie");
		oValues.add("Requested PsychicEnergie");
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		// TODO (Snorry) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		// TODO (Snorry) - Auto-generated method stub
		return 0;
	}

}
