/**
 * CHANGELOG
 *
 * 12.10.2011 zottl					- first proper implementation according to current specification
 * 14.07.2011 deutsch 			- refactored
 * 09.07.2011 hinterleitner - File created
 *
 */
package memorymgmt.storage;

import inspector.interfaces.itfInspectorGenericTimeChart;
import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInspectorStackedBarChart;
import inspector.interfaces.itfInterfaceDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import properties.personality_parameter.clsPersonalityParameterContainer;

import modules.interfaces.D3_1_receive;
import modules.interfaces.D3_1_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsPsychicEnergyPerModule;
import base.tools.toText;

/**
 * Storage module for delivering psychic energy to F55, F7, F21, F20, F8, F23.
 * Module F56 sends freed psychic energy that is taken from the drives from {I5.3}.
 * A special storage containing this psychic energy is necessary in order to distribute it.
 * The stored data is simply the amount of energy available to each connected module.
 * The modules are connected to this special type of storage with {D3_1_receive} and
 * {D3_1_send} interfaces.
 * 
 * @author Kogelnig Philipp (e0225185)
 * @since 08.10.2012
 */
public class DT3_PsychicEnergyStorage 
implements itfInspectorInternalState, itfInterfaceDescription, itfInspectorGenericTimeChart, D3_1_receive, D3_1_send, itfInspectorStackedBarChart {
	
    private static final String P_PSYCHIC_INTENSITY_CONTAINER_LIMIT ="PSYCHIC_INTENSITY_CONTAINER_LIMIT";
    // distributed energy for all modules <module number, <energy in buffer, last requested energy, priority of request>> 
	private Hashtable<Integer, clsPsychicEnergyPerModule> moPsychicEnergyPerModule;
	
	private static int srMaxRoundStorageSize = 10;
	private double[] moPsychicEnergyRoundStorage;
	private int mnOldEnergyIndex = 0;
	private int mnCurrentEnergyIndex = 0;
	
	private double mrFreePsychicEnergy = 0.0;
	private double mrConsumedPsychicEnergy = 0.0;
	private double mrExpiredPsychicEnergy = 0.0;
	
	public DT3_PsychicEnergyStorage(clsPersonalityParameterContainer poPersonalityParameterContainer) {
		double psychicIntensityContainerLimit =poPersonalityParameterContainer.getPersonalityParameter("DT3", P_PSYCHIC_INTENSITY_CONTAINER_LIMIT).getParameterDouble();
	    initializeBuffers();
	}

	private void initializeBuffers() {
		moPsychicEnergyRoundStorage = new double[srMaxRoundStorageSize];
		moPsychicEnergyPerModule = new Hashtable<Integer, clsPsychicEnergyPerModule>();
	}
	
	/**
	 * Register modules which require psychic energy
	 * 
	 * @since 15.12.2012 15:07:23
	 * 
	 * @param pnModuleNr - The module number of the calling module.
	 */
	public void registerModule(int pnModuleNr) {
		moPsychicEnergyPerModule.put(pnModuleNr, new clsPsychicEnergyPerModule(pnModuleNr,0.0, 0.0, 0, 1.0));
	}

	public ArrayList<itfInspectorGenericTimeChart> getInspectorPsychicEnergyPerModule() {
		ArrayList<itfInspectorGenericTimeChart> ret = new ArrayList<itfInspectorGenericTimeChart>();
		for(int nKey:moPsychicEnergyPerModule.keySet()) {
			ret.add(moPsychicEnergyPerModule.get(nKey));
		}
		return ret;
	}
	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return  "Storage module for delivering psychic energy to F55, F7, F21, F20, F8, F23. " +
		"Module F56 sends freed psychic energy that is taken from the drives from {I5.3}. " +
		"A special storage containing this psychic energy is necessary in order to distribute it. " +
		"The stored data is simply the amount of energy available to each connected module. " +
		"The modules are connected to this special type of storage with {D3_1_receive}," +
		"and {D3_1_send} interfaces.";
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D3_1) );
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D3_1) );
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		
		text += toText.h1("Psychic Energy Storage");
		text += toText.mapToTEXT("moPsychicEnergyPerModule", moPsychicEnergyPerModule);
		
		return text;
	}


	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_send#send_D3_1(int)
	 */
	@Override
	public double send_D3_1(int pnModuleNr,double prRequestedPsychicEnergy, double prPriority) {
		double freeEnergy = 0.0;
		clsPsychicEnergyPerModule oCurrentPsychichEnergiePerModule = moPsychicEnergyPerModule.get(pnModuleNr);
		if(oCurrentPsychichEnergiePerModule != null) {
			freeEnergy = oCurrentPsychichEnergiePerModule.mrAvailablePsychicEnergie;
			if(freeEnergy > prRequestedPsychicEnergy) {
				oCurrentPsychichEnergiePerModule.setValues(freeEnergy - prRequestedPsychicEnergy,prRequestedPsychicEnergy, prRequestedPsychicEnergy, prPriority);
				freeEnergy = prRequestedPsychicEnergy;
			} else {
				oCurrentPsychichEnergiePerModule.setValues(0.0,freeEnergy, prRequestedPsychicEnergy, prPriority);
			}
		} else {
			// first request
			moPsychicEnergyPerModule.put(pnModuleNr, new clsPsychicEnergyPerModule(pnModuleNr,0.0, 0.0, prRequestedPsychicEnergy, prPriority));
		}
		return freeEnergy;
	}


	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_receive#receive_D3_1(double)
	 */
	@Override
	public void receive_D3_1(double prFreePsychicEnergy) {
		moPsychicEnergyRoundStorage[mnCurrentEnergyIndex] += prFreePsychicEnergy;
	}
	
	/**
	 * Distributes the free PsychicEnergy relative to requested amount 
	 * and requested priority
	 * 
	 * Discards energy if it isn't consumed for srMaxRoundStorageSize to simulate
	 * a decay in PsychicEnergy and prevent buffer from overflowing.
	 */
	public void divideFreePsychicEnergy () {
		double rStoredPsychicEnergy = 0.0;
		double rSumRequestEnergy = 0.0;
		double rSumPriorityMulRequest = 0.0;
		for(clsPsychicEnergyPerModule oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
			rStoredPsychicEnergy += oPsychicEnergyPerModule.mrAvailablePsychicEnergie;
			rSumRequestEnergy += oPsychicEnergyPerModule.mrRequestedPsychicEnergie;
			rSumPriorityMulRequest += oPsychicEnergyPerModule.mrRequestedPsychicEnergie * oPsychicEnergyPerModule.mrRequestedPriority;
		}
		
		mrConsumedPsychicEnergy = mrFreePsychicEnergy - rStoredPsychicEnergy;
		double rConsumedPsychicEnergy = mrConsumedPsychicEnergy;
		while(rConsumedPsychicEnergy > 0.) {
			if(moPsychicEnergyRoundStorage[mnOldEnergyIndex] > rConsumedPsychicEnergy) {
				moPsychicEnergyRoundStorage[mnOldEnergyIndex] -= rConsumedPsychicEnergy;
				rConsumedPsychicEnergy = 0.;
			} else {
				rConsumedPsychicEnergy -= moPsychicEnergyRoundStorage[mnOldEnergyIndex];
				moPsychicEnergyRoundStorage[mnOldEnergyIndex] = 0.;
				mnOldEnergyIndex = (mnOldEnergyIndex+1)%moPsychicEnergyRoundStorage.length;
			}
		}
		
		mrFreePsychicEnergy = rStoredPsychicEnergy + moPsychicEnergyRoundStorage[mnCurrentEnergyIndex];
		mnCurrentEnergyIndex = (mnCurrentEnergyIndex+1)%moPsychicEnergyRoundStorage.length;
		if(mnCurrentEnergyIndex == mnOldEnergyIndex) {
			// discard old energy
			mrFreePsychicEnergy -= moPsychicEnergyRoundStorage[mnOldEnergyIndex];
			mrExpiredPsychicEnergy = moPsychicEnergyRoundStorage[mnOldEnergyIndex];
			moPsychicEnergyRoundStorage[mnOldEnergyIndex] = 0.;
			mnOldEnergyIndex = (mnOldEnergyIndex+1)%moPsychicEnergyRoundStorage.length;
		} else {
			mrExpiredPsychicEnergy = 0;		
		}
		
		if(moPsychicEnergyPerModule.size() == 0)
			// no active modules
			return;
		
		if(rSumRequestEnergy <= 0) {
			// no module requested PsychicEnergy
			// distribute received energy uniformly
			double rEnergyForEachModule = mrFreePsychicEnergy / moPsychicEnergyPerModule.size();
			for(clsPsychicEnergyPerModule oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
				oPsychicEnergyPerModule.mrAvailablePsychicEnergie = rEnergyForEachModule;
				oPsychicEnergyPerModule.mrRequestedPsychicEnergie = 0.;
				oPsychicEnergyPerModule.mrRequestedPriority = 0.;
			}
		} else if(mrFreePsychicEnergy >= rSumRequestEnergy) {
			// enough free PsychicEnergy to full fill all requests
			// distribute rest free PsychicEnergy depending on request and priority
			double rRestFreePsychicEnergy = mrFreePsychicEnergy - rSumRequestEnergy;
			for(clsPsychicEnergyPerModule oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
				oPsychicEnergyPerModule.mrAvailablePsychicEnergie = oPsychicEnergyPerModule.mrRequestedPsychicEnergie + rRestFreePsychicEnergy * oPsychicEnergyPerModule.mrRequestedPsychicEnergie * oPsychicEnergyPerModule.mrRequestedPriority / rSumPriorityMulRequest;
				oPsychicEnergyPerModule.mrRequestedPsychicEnergie = 0.;
				oPsychicEnergyPerModule.mrRequestedPriority = 0.;	
			}
		} else {
			// not enough free PsychicEnergy to full fill all requests
			// distribute PsychicEnergy depending on request and priority
			for(clsPsychicEnergyPerModule oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
				oPsychicEnergyPerModule.mrAvailablePsychicEnergie = mrFreePsychicEnergy * oPsychicEnergyPerModule.mrRequestedPsychicEnergie * oPsychicEnergyPerModule.mrRequestedPriority / rSumPriorityMulRequest;
				oPsychicEnergyPerModule.mrRequestedPsychicEnergie = 0.;
				oPsychicEnergyPerModule.mrRequestedPriority = 0.;
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
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
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		oValues.add(mrFreePsychicEnergy);
		oValues.add(mrConsumedPsychicEnergy);
		oValues.add(mrExpiredPsychicEnergy);
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		oCaptions.add("total free PsychichEnergy");
		oCaptions.add("consumed PsychichEnergy");
		oCaptions.add("expired PsychichEnergy");
		return oCaptions;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 20;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.5;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartTitle()
	 */
	@Override
	public String getStackedBarChartTitle() {
		return "Psychic Energy Storage";
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getStackedBarChartData() {
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> received = new ArrayList<Double>();
		ArrayList<Double> remaining = new ArrayList<Double>();
		ArrayList<Double> missing = new ArrayList<Double>();	

		for(clsPsychicEnergyPerModule oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
			received.add(oPsychicEnergyPerModule.mrConsumedPsychicEnergie);
			remaining.add(oPsychicEnergyPerModule.mrAvailablePsychicEnergie);
			if(oPsychicEnergyPerModule.mrConsumedPsychicEnergie < oPsychicEnergyPerModule.mrRequestedPsychicEnergie) {
				missing.add(oPsychicEnergyPerModule.mrRequestedPsychicEnergie - oPsychicEnergyPerModule.mrConsumedPsychicEnergie);	
			} else {
				missing.add(.0);
			}
		}
		
		oResult.add(received);
		oResult.add(remaining);
		oResult.add(missing);	
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartCategoryCaptions()
	 */
	@Override
	public ArrayList<String> getStackedBarChartCategoryCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("received");
		oResult.add("remaining");
		oResult.add("missing");
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartColumnCaptions()
	 */
	@Override
	public ArrayList<String> getStackedBarChartColumnCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		
		for(clsPsychicEnergyPerModule oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
			oResult.add(Integer.toString(oPsychicEnergyPerModule.mnModuleNr));
		}
		return oResult;
	}
}