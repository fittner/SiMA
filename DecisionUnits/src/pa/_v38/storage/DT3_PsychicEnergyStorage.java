/**
 * CHANGELOG
 *
 * 12.10.2011 zottl					- first proper implementation according to current specification
 * 14.07.2011 deutsch 			- refactored
 * 09.07.2011 hinterleitner - File created
 *
 */
package pa._v38.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D3_1_receive;
import pa._v38.interfaces.modules.D3_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

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
implements itfInspectorInternalState, itfInterfaceDescription, D3_1_receive, D3_1_send {
	// distributed energy for all modules <module number, <energy in buffer, last requested energy, priority of request>> 
	private Hashtable<Integer, clsTriple<Double, Double, Double>> moPsychicEnergyPerModule;
	
	private static int srMaxRoundStorageSize = 10;
	private double[] moPsychicEnergyRoundStorage;
	private int mnOldEnergyIndex = 0;
	private int mnCurrentEnergyIndex = 0;
	
	private double mrFreePsychicEnergy = 0.0;
	
	public DT3_PsychicEnergyStorage() {
		initializeBuffers();
	}

	private void initializeBuffers() {
		moPsychicEnergyRoundStorage = new double[srMaxRoundStorageSize];
		moPsychicEnergyPerModule = new Hashtable<Integer, clsTriple<Double, Double, Double>>();
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
		if(moPsychicEnergyPerModule.containsKey(pnModuleNr)) {
			freeEnergy = moPsychicEnergyPerModule.get(pnModuleNr).a;
			if(freeEnergy > prRequestedPsychicEnergy) {
				moPsychicEnergyPerModule.put(pnModuleNr, new clsTriple<Double,Double,Double>(freeEnergy - prRequestedPsychicEnergy, prRequestedPsychicEnergy, prPriority));
				freeEnergy = prRequestedPsychicEnergy;
			} else {
				moPsychicEnergyPerModule.put(pnModuleNr, new clsTriple<Double,Double,Double>(0.0, prRequestedPsychicEnergy, prPriority));
			}
		} else {
			// first request
			moPsychicEnergyPerModule.put(pnModuleNr, new clsTriple<Double,Double,Double>(0.0, prRequestedPsychicEnergy, prPriority));
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
		for(clsTriple<Double, Double, Double> oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
			rStoredPsychicEnergy += oPsychicEnergyPerModule.a;
			rSumRequestEnergy += oPsychicEnergyPerModule.b;
			rSumPriorityMulRequest += oPsychicEnergyPerModule.b * oPsychicEnergyPerModule.c;
		}
		
		double rConsumedPsychicEnergy = mrFreePsychicEnergy - rStoredPsychicEnergy;
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
			moPsychicEnergyRoundStorage[mnOldEnergyIndex] = 0.;
			mnOldEnergyIndex = (mnOldEnergyIndex+1)%moPsychicEnergyRoundStorage.length;
		}
		
		if(moPsychicEnergyPerModule.size() == 0)
			// no active modules
			return;
		
		if(rSumRequestEnergy <= 0) {
			// no module requested PsychicEnergy
			// distribute received energy uniformly
			double rEnergyForEachModule = mrFreePsychicEnergy / moPsychicEnergyPerModule.size();
			for(clsTriple<Double, Double, Double> oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
				oPsychicEnergyPerModule.a = rEnergyForEachModule;
				oPsychicEnergyPerModule.b = 0.;
				oPsychicEnergyPerModule.c = 0.;
			}
		} else if(mrFreePsychicEnergy >= rSumRequestEnergy) {
			// enough free PsychicEnergy to full fill all requests
			// distribute rest free PsychicEnergy depending on request and priority
			double rRestFreePsychicEnergy = mrFreePsychicEnergy - rSumRequestEnergy;
			for(clsTriple<Double, Double, Double> oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
				oPsychicEnergyPerModule.a = oPsychicEnergyPerModule.b + rRestFreePsychicEnergy * oPsychicEnergyPerModule.b * oPsychicEnergyPerModule.c / rSumPriorityMulRequest;
				oPsychicEnergyPerModule.b = 0.;
				oPsychicEnergyPerModule.c = 0.;
			}
		} else {
			// not enough free PsychicEnergy to full fill all requests
			// distribute PsychicEnergy depending on request and priority
			for(clsTriple<Double, Double, Double> oPsychicEnergyPerModule:moPsychicEnergyPerModule.values()) {
				oPsychicEnergyPerModule.a = mrFreePsychicEnergy * oPsychicEnergyPerModule.b * oPsychicEnergyPerModule.c / rSumPriorityMulRequest;
				oPsychicEnergyPerModule.b = 0.;
				oPsychicEnergyPerModule.c = 0.;
			}
		}
	}
}