package du.itf.actions;

import du.enums.eCallPriority;


/**
 * ActionProcessor is the public interface of the ActionProcessor
 * 
 * @author Benny Doenz
 * 15.04.2009, 18:40:19
 * 
 */
public interface itfInternalActionProcessor {

	public void inhibitCommand(Class<?> poCommand, int pnDuration);	 
	public void call(clsInternalActionCommand poCommand);
	public void call(clsInternalActionCommand poCommand, eCallPriority pePriority);
}
