package interfaces;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/* Usage example:
 * try {
		moDataManipulator.prepareConfig("sim_run_1");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	... some prep code ...
	
	try {
		for(double i = 0; i < 1.0; i += 0.01) {
			moDataManipulator.put("text://personality/analysis_personality_adam.properties/F26.INITIAL_REQUEST_INTENSITY.value", Double.toString(i));
		}
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 */

public interface itfDataManipulation {
	public void prepareConfig(String configName) throws IOException;
	public void put(String oUniqueResourceIdentifier, String oValue)  throws URISyntaxException;
	public File getScenarioFile();
}
