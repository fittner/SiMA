
package complexbody.io.actuators.actionCommands;

import java.util.ArrayList;

public class clsInternalActionEmotionalStressSweat extends clsInternalActionCommand {

	private ArrayList<Double> moStorageOfEmotionIntensities = null;
	private ArrayList<String> moStorageOfEmotionNames = null;
	
	public clsInternalActionEmotionalStressSweat(ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames) {
		this.moStorageOfEmotionIntensities = poStorageOfEmotionIntensities;
		this.moStorageOfEmotionNames = poStorageOfEmotionNames;
	}
	
	// getter & setter for storage array lists
	public ArrayList<Double> getStorageOfEmotionIntensities() {
		return moStorageOfEmotionIntensities;
	}
	public void setStorageOfEmotionIntensities(ArrayList<Double> moStorageOfEmotionIntensities) {
		this.moStorageOfEmotionIntensities = moStorageOfEmotionIntensities;
	}

	public ArrayList<String> getStorageOfEmotionNames() {
		return moStorageOfEmotionNames;
	}
	public void setStorageOfEmotionNames(ArrayList<String> moStorageOfEmotionNames) {
		this.moStorageOfEmotionNames = moStorageOfEmotionNames;
	}
}