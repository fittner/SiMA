package pa._v38.memorymgmt.longtermmemory.psychicspreadactivation;

import java.util.ArrayList;
import java.util.HashMap;

import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;

public interface PsychicSpreadingActivationInterface {
	public void startSpreadActivation(clsThingPresentationMesh primaryInput, ArrayList<clsThingPresentationMesh> secondaryInput, double recognizedImageMultiplyFactor, 
			clsThingPresentationMesh sourceImage, double prPsychicEnergyIn, int pnMaximumDirectActivationValue, boolean useDirectActivation, 
			ArrayList<clsDriveMesh> drivesForFilteringList, HashMap<Integer, clsThingPresentationMesh> alreadyActivatedImages);
}
