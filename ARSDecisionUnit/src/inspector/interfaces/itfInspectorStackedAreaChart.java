/**
 * CHANGELOG
 *
 * 19.02.2015 Kollmann - File created
 *
 */
package inspector.interfaces;

import java.util.ArrayList;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 19.02.2015, 11:49:03
 * 
 */
public interface itfInspectorStackedAreaChart {
    /**
     * Returns the title of the stacked bar chart for a certain label
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public String getTitle(String poLabel);
    
    /**
     * Returns an array that contains the current set of data a certain label
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public ArrayList<Double> getData(String poLabel);
    
    /**
     * Returns an array that contains the labels of all Categories for a certain label.
     * Has to have the same number of entries as the main Array List return by getData.
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public ArrayList<String> getCategoryCaptions(String poLabel);
}
