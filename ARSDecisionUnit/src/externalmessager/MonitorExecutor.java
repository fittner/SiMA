/**
 * CHANGELOG
 *
 * 02.01.2014 wendt - File created
 *
 */
package externalmessager;

import java.util.ArrayList;


/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.01.2014, 16:48:22
 * 
 */
public class MonitorExecutor implements MonitorExecutorForModuleBaseInterface {
    
    private ArrayList<TimeCollectorInterface> registeredClientList = new ArrayList<TimeCollectorInterface>();
    private final DatapointHandlerForClientInterface datapointServer;
    private boolean isActivated = true;
    
    private static MonitorExecutor monitor = null;
    
    public static void init(DatapointHandlerForClientInterface datapointServer) {
        if (monitor==null) {
            monitor = new MonitorExecutor(datapointServer);
        }
    }
    
    public static MonitorExecutor getMonitor() {
        if (monitor==null) {
            throw new NullPointerException("The monitorExecutor has to be initialized first");
        }
        
        return monitor;
    }

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 03.01.2014 11:21:35
     *
     * @param datapointServer
     */
    private MonitorExecutor(DatapointHandlerForClientInterface datapointServer) {
        this.datapointServer = datapointServer;
    }
    
    
    /* (non-Javadoc)
     *
     * @since 03.01.2014 10:42:34
     * 
     * @see base.modules.DebugExecutorForModuleBaseInterface#notify(java.lang.String)
     */
    @Override
    public synchronized void notifyProbes(String datapointName) {
        for (TimeCollectorInterface tc : registeredClientList) {
            tc.notifyProcessor(datapointName);
        }
    }

    /* (non-Javadoc)
     *
     * @since 03.01.2014 11:20:33
     * 
     * @see externalmessager.MonitorExecutorForModuleBaseInterface#createProbe(java.lang.String)
     */
    @Override
    public synchronized void createProbe(String datapointName) {
        TimeCollectorInterface timeCollector = new TimeCollector(datapointName, this.datapointServer);
        this.registeredClientList.add(timeCollector);
        timeCollector.startTimeCollector();
    }
    
    @Override
    public String toString() {
        return this.registeredClientList.toString();
    }

    /* (non-Javadoc)
     *
     * @since 03.01.2014 13:27:38
     * 
     * @see externalmessager.MonitorExecutorForModuleBaseInterface#writeToCodelet(java.lang.String, int)
     */
    @Override
    public void writeToCodelet(String datapointName, int value) {
        this.datapointServer.updateSubscribedValue(new Datapoint<Integer>(datapointName, value));
        
    }

    /* (non-Javadoc)
     *
     * @since 03.01.2014 14:02:04
     * 
     * @see externalmessager.MonitorExecutorForModuleBaseInterface#isActivated()
     */
    @Override
    public boolean isActivated() {
        return this.isActivated;
    }

    /* (non-Javadoc)
     *
     * @since 03.01.2014 14:02:04
     * 
     * @see externalmessager.MonitorExecutorForModuleBaseInterface#setActivated(boolean)
     */
    @Override
    public void setActivated(boolean activate) {
        this.isActivated = activate;
        
    }

}
