package externalmessager;

import java.util.ArrayList;

public interface DatapointHandlerForCodeletInterface {
	public void registerCodelet(DatapointClientInterface codelet) throws Exception;
	public void unregisterCodelet(DatapointClientInterface codelet);
	public void subscribeDatapoints(ArrayList<Datapoint<?>> datapoints, DatapointClientInterface codelet);
	public void unsubscribeDatapoints(ArrayList<Datapoint<?>> datapoints, DatapointClientInterface codelet);
	public boolean writeDatapoints(ArrayList<Datapoint<?>> datapoints) throws Exception;
	public ArrayList<Datapoint<?>> readDatapoints(ArrayList<Datapoint<?>> datapoints) throws Exception;
	public void setSharedVariable(String key, Object value);
	public Object getSharedValue(String key);
	
	
	
}
