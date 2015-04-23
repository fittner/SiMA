/**
 * CHANGELOG
 *
 * 03.01.2014 wendt - File created
 *
 */
package externalmessager;

import org.slf4j.Logger;

/**
 * This class shall just measure the time of a method or a module 
 * 
 * @author wendt
 * 03.01.2014, 10:44:05
 * 
 */
public class TimeCollector extends Thread implements TimeCollectorInterface {

    private final String conditionString;
    private final DatapointHandlerForClientInterface datapointServer;
    
    private boolean isAlive = true;
    
    private long startTime;
    private long stopTime;
    private boolean hasStarted = false;
    
    private static final Logger log = logger.clsLogger.getLog("Timing");
    
    public TimeCollector(String conditionString, DatapointHandlerForClientInterface datapointServer) {
        this.conditionString = conditionString;
        this.datapointServer = datapointServer;
        this.setName("TimeCollector " + this.conditionString);
    }
    
    /* (non-Javadoc)
     *
     * @since 03.01.2014 10:45:51
     * 
     * @see externalmessager.TimeCollectorInterface#notify(java.lang.String)
     */
    @Override
    public synchronized void notifyProcessor(String datapointName) {
        //Check conditions. If they are true, then notify the cell
        boolean doNotify = this.checkConditions(datapointName);
        log.trace("{}: wake {}, {}, {}", doNotify, this.conditionString, datapointName);
        
        if (doNotify==true) {
            //Awake client
            this.notify();
        }
        
        //else do nothing
    }
    
    private boolean checkConditions(String datapointName) {
        boolean result = false;
        
        if (this.conditionString.equals(datapointName)==true) {
            result=true;
        }
        
        return result;
    }
    
    @Override
    public void run() {
        while (this.isAlive==true) {
            //First action, execute wait
            this.executeWait();
            
            if (this.hasStarted==false) {
                this.startTimer();
            } else {
                this.stopTimer();
                this.sendResult();
            }
        }
        
        log.trace("Shut down time collector {}", this.getName());
       
    }
    
    private synchronized void executeWait() {
        try {
            //Block profile controller
            this.wait();
        } catch (InterruptedException e) {
            //Do nothing
        }
    }
    
    private void startTimer() {
        this.startTime = System.currentTimeMillis();
        this.hasStarted=true;
    }
    
    private void stopTimer() {
        this.stopTime = System.currentTimeMillis();
        this.hasStarted=false;
    }
    
    private void sendResult() {
        //Calculate time
        int delta = (int) (this.stopTime-this.startTime);
        
        //Create datapoint
        Datapoint<Integer> timeForModule = new Datapoint<Integer>(this.conditionString, delta);
        
        //Set new value in the executor
        this.datapointServer.updateSubscribedValue(timeForModule);
    }

    /* (non-Javadoc)
     *
     * @since 03.01.2014 11:10:41
     * 
     * @see externalmessager.TimeCollectorInterface#startTimeCollector()
     */
    @Override
    public void startTimeCollector() {
       super.start(); 
       log.trace("Start timecollector for {} ", this.conditionString);
    }

    /* (non-Javadoc)
     *
     * @since 03.01.2014 11:10:41
     * 
     * @see externalmessager.TimeCollectorInterface#shutDown()
     */
    @Override
    public void shutDown() {
        this.isAlive=false;
        
    }
    
    @Override
    public String toString() {
        return this.getName();
        
    }
    
    
    
}
