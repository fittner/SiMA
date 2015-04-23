/**
 * CHANGELOG
 *
 * 03.01.2014 wendt - File created
 *
 */
package timing;

import logger.clsLogger;

import org.slf4j.Logger;

import externalmessager.DatapointHandlerForCodeletInterface;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.01.2014, 11:52:02
 * 
 */
public class TimingStarter {
    
    private static final Logger   log = clsLogger.getLog("Timing");
    
    public void startDebugInspectors(DatapointHandlerForCodeletInterface codeletHandler) {
        DatapointClientFactory cf = new DatapointClientFactory(codeletHandler);
        
        try {
            for (int i=1; i<70; i++) {
                cf.createCodelet("F" + i);
                log.debug("Created timing client for module {}", "F" + i);
            }
        } catch (Exception e) {
            log.error("Cannot start codelet.", e);
        }
    }
}
