/**
 * clsActionWakeUp.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny D�nz
 * 28.08.2009, 13:49:07
 */

package decisionunit.itf.actions;

/**
 * WakeUp Command (Reverses the effects of the sleep command)
 * No parameters
 * 
 * @author Benny D�nz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionWakeUp implements itfActionCommand {

	public clsActionWakeUp() {
	}
	
	public String getLog() {
		return "<WakeUp>" + "</WakeUp>"; 
	}

}
