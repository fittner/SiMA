/**
 * CHANGELOG
 *
 * 02.01.2014 wendt - File created
 *
 */
package externalmessager;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.01.2014, 16:29:43
 * 
 */
public interface MonitorExecutorForModuleBaseInterface {
    public void notifyProbes(String datapointName);
    public void createProbe(String datapointName);
    public void writeToCodelet(String datapointName, int value);
    public boolean isActivated();
    public void setActivated(boolean activate);
}
