package interfaces;

import java.net.URISyntaxException;

/* Usage example:
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
	public void put(String oUniqueResourceIdentifier, String oValue)  throws URISyntaxException;
}
