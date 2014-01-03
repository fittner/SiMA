package externalmessager;

import java.util.ArrayList;

public interface DatapointClientInterface {
	public String getCodeletName();
	public void updateSubscribedValues(ArrayList<Datapoint<?>> datapointList) throws Exception;
	
}
