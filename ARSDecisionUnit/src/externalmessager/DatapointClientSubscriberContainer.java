package externalmessager;

import java.util.ArrayList;

import org.slf4j.Logger;

public class DatapointClientSubscriberContainer implements DatapointClientSubscriberContainerInterface {
	private final DatapointClientInterface codelet;
	private ArrayList<Datapoint<?>> datapointList = new ArrayList<Datapoint<?>>();
	
	private static final Logger	log	= logger.clsLogger.getLog("Timing");
	
	public DatapointClientSubscriberContainer(DatapointClientInterface codelet) {
		this.codelet = codelet;
	}

	@Override
	public void addDatapoint(Datapoint<?> datapoint) {
		this.datapointList.add(datapoint);
		
	}

	@Override
	public void sendDatapointListToCodelet() {
		try {
			this.codelet.updateSubscribedValues(datapointList);
		} catch (Exception e) {
			log.error("Error updating subscribed values", e);
		}
		//Clear the list as all subscribed values have been added
		this.datapointList.clear();
		
	}

	@Override
	public String getCodeletName() {
		return this.codelet.getCodeletName();
	}

}
