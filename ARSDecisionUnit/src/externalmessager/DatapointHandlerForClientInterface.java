package externalmessager;

import java.util.ArrayList;

public interface DatapointHandlerForClientInterface {
	public void updateSubscribedValues(ArrayList<Datapoint<?>> datapointList);
	public void updateSubscribedValue(Datapoint<?> datapoint);
}
