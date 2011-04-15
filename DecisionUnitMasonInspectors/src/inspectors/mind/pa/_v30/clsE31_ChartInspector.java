/**
 * clsE31_ChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 18:54:16
 */
package inspectors.mind.pa._v30;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import pa.interfaces._v30.itfTimeChartInformationContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 18:54:16
 * 
 */
public class clsE31_ChartInspector extends clsE_GenericChartInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 18:54:35
	 */
	private static final long serialVersionUID = -644854858917480906L;
	protected XYSeries moLowerLimit;
	private final double mrLower = -1; 
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 18:54:19
	 *
	 * @param poTimingContainer
	 * @param poYAxisCaption
	 * @param prLowerLimit
	 * @param prUpperLimit
	 * @param poChartName
	 */
	public clsE31_ChartInspector(
			itfTimeChartInformationContainer poTimingContainer,
			String poYAxisCaption, String poChartName) {
		super(poTimingContainer, poYAxisCaption, poChartName, 2);
		//nothing to do
	}

    @Override
	protected void customizePlot(XYPlot plot) {
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        
        
     // set line colors
        ArrayList<Color> oColors = getColorList();
        for (int i=0; i<moValueHistory.size(); i++) {
        	 plot.getRenderer().setSeriesPaint(i, oColors.get(i));
        }  
        
        plot.getRenderer().setSeriesPaint(moValueHistory.size(), Color.white);     
        
        plot.getRenderer().setSeriesStroke(1, new BasicStroke(
                1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 
                1.0f, new float[] {1.0f, 1.0f}, 0.0f));
        plot.getRenderer().setSeriesStroke(2, new BasicStroke(
        		1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 2.0f}, 0.0f)); 
        plot.getRenderer().setSeriesStroke(3, new BasicStroke(
                1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                1.0f, new float[] {3.0f, 3.0f}, 0.0f));
        plot.getRenderer().setSeriesStroke(4, new BasicStroke(
                1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                1.0f, new float[] {4.0f, 4.0f}, 0.0f));
        plot.getRenderer().setSeriesStroke(5, new BasicStroke(
                1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                1.0f, new float[] {5.0f, 5.0f}, 0.0f));    	
    }
    
	
    private void addLimitLines(XYSeriesCollection poDataset) {
    	moLowerLimit = new XYSeries("");
    	moLowerLimit.setMaximumItemCount(mnHistoryLength);
    	moLowerLimit.add(moCurrentTime, mrLower);
    	poDataset.addSeries(moLowerLimit);  
    }
    
    @Override
    protected XYSeriesCollection createDataset() {
    	XYSeriesCollection poDataset = super.createDataset();
		
		addLimitLines(poDataset);
		
		return poDataset;
    }    
    
    private void updateLimitLines() {
		moLowerLimit.add(moCurrentTime, mrLower );
    }	
    
    @Override
	protected void updateData() {
    	super.updateData();
		updateLimitLines();
    }    
}
