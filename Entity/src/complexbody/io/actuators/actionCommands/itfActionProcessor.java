package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eCallPriority;

/**
 * ActionProcessor is the public interface of the ActionProcessor
 * 
 * @author Benny Doenz
 * 15.04.2009, 18:40:19
 * 
 */
public interface itfActionProcessor {

	public void inhibitCommand(Class<?> poCommand, int pnDuration);	 
	public void call(clsActionCommand poCommand, eCallPriority pePriority);
	public void call(clsActionCommand poCommand);
	
	public String logXML();
	public String logText();

}
