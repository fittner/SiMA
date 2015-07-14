/**
 * CHANGELOG
 *
 * 10.07.2015 Kollmann - File created
 *
 */
package inspector.interfaces;

import java.util.ArrayList;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 10.07.2015, 11:36:49
 * 
 */
public interface itfInspectorMultipleBarChart {
    /**
     * Returns the title of the bar chart for the given Label.
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public String getBarChartTitle(String poLabel);
    /**
     * Returns an array that contains the current set of data for the given Label. The main Array List has to have the same number of entries as the array returned by getAreaChartAreaCaptions. And the sub Array Lists has to have the same number of entries a the array returned by getAreaChartColumnCaptions
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public ArrayList<ArrayList<Double>> getBarChartData(String poLabel);
    
    /**
     * Returns an array that contains the labels of all Categories for the given Label. Has to have the same number of entries as the main Array List return by getAreaChartData.
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public ArrayList<String> getBarChartCategoryCaptions(String poLabel);
    
    /**
     * Returns an array that contains the labels for all Columns for the given Label. Has to have the same number of entries as the sub ArrayList retruned by getAreaChartData.
     *
     * @since 30.08.2012 10:00
     *
     * @return
     */
    public ArrayList<String> getBarChartColumnCaptions(String poLabel);
}
