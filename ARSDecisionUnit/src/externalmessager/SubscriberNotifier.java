/**
 * CHANGELOG
 *
 * 03.01.2014 wendt - File created
 *
 */
package externalmessager;

import logger.clsLogger;

import org.slf4j.Logger;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.01.2014, 13:44:51
 * 
 */
public class SubscriberNotifier extends Thread {
    private final DatapointHandler dphandler;
    private final long startThreadTime = System.currentTimeMillis();
    private final Datapoint<?> datapoint;
    
    private static final Logger log = clsLogger.getLog("Timing");
    
    public SubscriberNotifier(DatapointHandler dphandler, Datapoint<?> dp) {
        this.dphandler = dphandler;
        datapoint = dp;
    }

    @Override
    public void run() {
        dphandler.notifySubscriber(datapoint);
       
        int threadDuration = (int) (System.currentTimeMillis() - this.startThreadTime);
        log.trace("Subscriber notification duration {}", threadDuration);
    }
}
