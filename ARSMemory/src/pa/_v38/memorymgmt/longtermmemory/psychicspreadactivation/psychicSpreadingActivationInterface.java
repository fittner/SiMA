package pa._v38.memorymgmt.longtermmemory.psychicspreadactivation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

public interface psychicSpreadingActivationInterface {
	public void startSpreadActivation(clsThingPresentationMesh poImage, double prPsychicEnergyIn, int pnMaximumDirectActivationValue, 
			ArrayList<clsDriveMesh> poDrivesForFilteringList, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> preferredImages,
			ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages);
}
