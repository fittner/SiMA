package pa._v38.memorymgmt.longtermmemory.psychicspreadactivation;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;

public interface PsychicSpreadingActivationInterface {
	public void startSpreadActivation(clsThingPresentationMesh poImage, double prPsychicEnergyIn, int pnMaximumDirectActivationValue, boolean useDirectActivation, 
			ArrayList<clsDriveMesh> poDrivesForFilteringList, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> preferredImages,
			ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages);
}
