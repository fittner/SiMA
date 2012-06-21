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
import java.util.Map;

import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D3_1_receive;
import pa._v38.interfaces.modules.D3_1_send;
import pa._v38.interfaces.modules.D3_2_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.tools.toText;

/**
 * Storage module for delivering psychic energy to F55, F7, F21, F20, F8, F23.
 * Module F56 sends freed psychic energy that is taken from the drives from {I5.3}.
 * A special storage containing this psychic energy is necessary in order to distribute it.
 * The stored data is simply the amount of energy available to each connected module.
 * The modules are connected to this special type of storage with {D3_1_receive},
 * {D3_1_send} and {D3_2_receive} interfaces.
 * 
 * @author Marcus Zottl (e0226304)
 * @since 12.10.2011
 */
public class DT3_PsychicEnergyStorage 
implements itfInspectorInternalState, itfInterfaceDescription, D3_1_receive, D3_1_send, D3_2_receive {

	private Hashtable<Integer, Double> moPsychicEnergyBuffer;
	
	private Hashtable<Integer, Double> moStepDifferenceBuffer;

	private double mrInitialEnergy = 0.5;
	private double mrFreePsychicEnergy = 0;
	
	
	public DT3_PsychicEnergyStorage() {
		initializeBuffers();
	}

	/**
	 * Initialize the buffers for free psychic energy.
	 * 
	 * @author Marcus Zottl (e0226304)
	 * @since 12.10.2011 16:03:02
	 */
	private void initializeBuffers() {
		moPsychicEnergyBuffer = new Hashtable<Integer, Double>();
		// initially no module has available free psychic energy
		double initial = mrInitialEnergy/moPsychicEnergyBuffer.size();
		moPsychicEnergyBuffer.put(7, initial);
		moPsychicEnergyBuffer.put(8, initial);
		moPsychicEnergyBuffer.put(20, initial);
		moPsychicEnergyBuffer.put(21, initial);
		moPsychicEnergyBuffer.put(23, initial);
		moPsychicEnergyBuffer.put(55, initial);
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
		"{D3_1_send} and {D3_2_receive} interfaces.";
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D3_1, eInterfaces.D3_2) );
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
		text += toText.mapToTEXT("moPsychicEnergyBuffer", moPsychicEnergyBuffer);
		
		return text;
	}


	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_send#send_D3_1(int)
	 */
	@Override
	public double send_D3_1(int pnModuleNr) {
		double freeEnergy = 0.0;
		// send energy to module. energy in module-buffer is not reduced here, but in receive_D3_2()
		if (moPsychicEnergyBuffer.containsKey(pnModuleNr)) {
			freeEnergy = moPsychicEnergyBuffer.get(pnModuleNr);
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
		mrFreePsychicEnergy = prFreePsychicEnergy;
	}


	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:55:37
	 * 
	 * @see pa._v38.interfaces.modules.D3_2_receive#receive_D3_2(double, int)
	 */
	@Override
	public void receive_D3_2(double prConsumedPsychicEnergy, int pnModuleNr) {
		double newEnergyLevel, oldEnergyLevel = 0.0;
		// reduce buffer of module according to consumedpsychicenergy
		if (moPsychicEnergyBuffer.containsKey(pnModuleNr)) {
			oldEnergyLevel = moPsychicEnergyBuffer.get(pnModuleNr);
			newEnergyLevel = oldEnergyLevel - prConsumedPsychicEnergy;
			if (newEnergyLevel < 0) {
				newEnergyLevel = 0.0;
			}
			
			// Update of the StepDifferenceBuffer (ration of consumed energy = relative energy consumption)
			moStepDifferenceBuffer.put(pnModuleNr, prConsumedPsychicEnergy/oldEnergyLevel);
			
			moPsychicEnergyBuffer.put(pnModuleNr, newEnergyLevel);			
		}
		
	}
	
	public void divideFreePsychicEnergy () {
	
		// divides received energy uniformly
		for (Map.Entry<Integer, Double> entry: moPsychicEnergyBuffer.entrySet()) {
			entry.setValue(entry.getValue() + (mrFreePsychicEnergy / moPsychicEnergyBuffer.size()));
		}
	
	}

}