package externalmessager;

public interface DatapointClientSubscriberContainerInterface {
	public void addDatapoint(Datapoint<?> datapoint);
	public void sendDatapointListToCodelet();
	public String getCodeletName();
}
