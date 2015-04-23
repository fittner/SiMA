package timing;

import java.util.ArrayList;

import externalmessager.Datapoint;
import externalmessager.DatapointHandlerForCodeletInterface;

public class CalculateAverageTimeOfModuleClient extends DatapointClient {

    private ArrayList<Integer> history = new ArrayList<Integer>();
    private final static int NUMBEROFENTRIES = 10; 
    private int averageTimeValue = 0;
    private int currentDuration;
    private final String moduleName;
    
	public CalculateAverageTimeOfModuleClient(DatapointHandlerForCodeletInterface handler, String subscribeDatapointName) {
		super(handler, "Codelet: " + subscribeDatapointName);
		super.init(subscribeDatapointName);

		//Execute always
		super.setExecuteOnce(true);
		
		this.moduleName = subscribeDatapointName;
		
	}

	@Override
	protected void executeProgram() {
	    Datapoint<?> dpX = this.getInputDatapoints().get(this.moduleName);
	    if (dpX.getValue().getClass().equals(Integer.class)) {
	        this.currentDuration = (Integer) dpX.getValue();
	        this.history = updateHistory(this.currentDuration, history, NUMBEROFENTRIES);
	        this.averageTimeValue = this.calulateAverage(history);
	        
	        log.info("{} average time={} ms, current time={} ms", this.moduleName, this.currentDuration, this.averageTimeValue);
	        
	    } else {
	        try {
                throw new Exception("Erroneous datatype has been received");
            } catch (Exception e) {
                log.error("Error at the reading of values", e);
            }
	    }
	}

	@Override
	protected void triggerEventAtInputDatapointsChanged() {
		synchronized (this) {
		    this.notify();    //Trigger action execution
		}
	    
	}
	
	private ArrayList<Integer> updateHistory(int newInput, ArrayList<Integer> history, int numberOfEventshistory) {
	    ArrayList<Integer> result = new ArrayList<Integer>();
        
        //double averageValue = 0;
        
        for (int i=0;i<history.size();i++) {
            if (i<numberOfEventshistory) {
                if (i>0 || history.size()<numberOfEventshistory) {
                    result.add(history.get(i));
                }
                
            } else {
                break;
            }
        }
        
        //Add new value
        result.add(newInput);
        
        return result;
    }
	
	private int calulateAverage(ArrayList<Integer> history) {
	    int result = 0;

	    int sum=0;
	    
	    for (int dp : history) {
              sum += dp;
	    }
	    
	    result = sum/history.size();
	    return result;	    
	}

}
