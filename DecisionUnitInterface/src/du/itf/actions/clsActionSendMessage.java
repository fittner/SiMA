package du.itf.actions;


/**
 * SendMessage command
 * Parameter SendMessage = Message to show on (NAO) simulator
 * 
 * @author Clemens Muchitsch
 * 15.02.2011, 16:31:13
 * 
 */
public class clsActionSendMessage extends clsActionCommand {

	private String msMessage = "run baby run";

	//Default Message is "run baby run"
	public clsActionSendMessage(String psMessage) {
		msMessage=psMessage;
	}
	
	public String getMessage() {
		return msMessage;
	}
	
	public void setMessagee(String psMessgage) {
		msMessage=psMessgage;
	}
	
	@Override
	public String getLog() {
		return "<SendMessage>" + msMessage + "</SendMessage>"; 
	}

}
