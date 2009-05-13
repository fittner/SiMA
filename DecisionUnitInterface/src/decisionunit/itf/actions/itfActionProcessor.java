package decisionunit.itf.actions;

import enums.eCallPriority;

/**
 * ActionProcessor is the public interface of the ActionProcessor
 * 
 * @author Benny Dönz
 * 15.04.2009, 18:40:19
 * 
 */
public interface itfActionProcessor {

	public void inhibitCommand(Class poCommand, int pnDuration);	 
	public void call(itfActionCommand poCommand, eCallPriority pePriority);
	public void call(clsActionSequence poSequence, eCallPriority pePriority);
	public void call(itfActionCommand poCommand, eCallPriority pePriority, int pnDuration);

}
