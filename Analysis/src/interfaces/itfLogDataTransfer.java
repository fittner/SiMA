package interfaces;

import java.io.IOException;
import java.util.Map;

public interface itfLogDataTransfer {
	public void put(Map<String, String> poStepLogEntries) throws IOException;
}
