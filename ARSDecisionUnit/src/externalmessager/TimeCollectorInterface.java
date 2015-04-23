/**
 * CHANGELOG
 *
 * 03.01.2014 wendt - File created
 *
 */
package externalmessager;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.01.2014, 10:44:34
 * 
 */
public interface TimeCollectorInterface {
    public void notifyProcessor(String datapointName);
    public void startTimeCollector();
    public void shutDown();
}
