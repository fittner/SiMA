package interfaces;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/*
 * NOTE: always set format before setting the target (setting the target will immediately write the files header
 * 
 * Usage example:
 *      moLogger.setFormat("$(First), $(Second), $(Third)");
		HashMap<String, String> data = new HashMap<>();
		data.put("First", "1");
		data.put("Second", "2");
		data.put("Third", "3");
		try {
			File file = new File("test.csv");
			controller.moLogger.setTarget(file.toURI());
			controller.moLogger.write(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 */

public interface itfAnalysisLogger {
	public void setFormat(String oRegExFormat);
	public void setTarget(URI oTarget) throws IOException;
	public void write(Map<String, String> oValues) throws IOException;
}
