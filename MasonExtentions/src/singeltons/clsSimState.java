/**
 * clsSimState.java: DecisionUnitInterface - statictools
 * 
 * @author deutsch
 * 23.04.2011, 12:40:28
 */
package singeltons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import sim.engine.SimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 12:40:28
 * 
 */
public class clsSimState {
    private static clsSimState instance = null;
    private SimState moSimState = null;
    private String moSimStartTimestamp;
    
    
    private clsSimState() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        moSimStartTimestamp = dateFormat.format(date);
    }
    
    private static clsSimState getInstance() {
       if (instance == null) {
            instance = new clsSimState();
       }
       return instance;
    }

	public static SimState getSimState() {
		return clsSimState.getInstance().moSimState;
	}
	
	public static void setSimState(SimState poSimState) {
		clsSimState.getInstance().moSimState = poSimState;
	}	
	
	public static String getSimStartTimestamp() {
		//FIXME (Deutsch): what happens if the simulation is stopped and restarted with the command console? the whole java application is not terminated. thus, the singletons should be left untouched -> for the second run, the same set of filenames are going to be genrated. possible solution: in project Sim the class BWMain ... maybe add there a recreated timestamp funciton call 
		return clsSimState.getInstance().moSimStartTimestamp;
	}
	
	public static long getSteps() {
		return clsSimState.getInstance().moSimState.schedule.getSteps();
	}
}
