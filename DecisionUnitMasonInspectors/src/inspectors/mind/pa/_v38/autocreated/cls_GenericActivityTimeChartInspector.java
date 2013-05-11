/**
 * clsE31_ChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38
 * 
 * @author deutsch
 * 15.04.2011, 18:54:16
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 18:54:16
 * 
 */
public class cls_GenericActivityTimeChartInspector extends cls_AbstractTimeChartInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 18:54:35
	 */
	private static final long serialVersionUID = -644854858917480906L;
	protected XYSeries moLowerLimit;
	protected XYSeries moUpperLimit;
	private double mrLower;
	private double mrUpper;
	
	private final static int mnDefaultOffset = 2;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 18:54:19
	 *
	 * @param poTimingContainer
	 */
	public cls_GenericActivityTimeChartInspector(itfInspectorGenericActivityTimeChart poObject) {
		super(poObject, poObject.getTimeChartAxis(), poObject.getTimeChartTitle(), mnDefaultOffset);
		//nothing to do
	}
	
	public cls_GenericActivityTimeChartInspector(itfInspectorGenericActivityTimeChart poObject, int pnHistoryLength, int pnWidth, int pnHeight) {
		super(poObject, poObject.getTimeChartAxis(), poObject.getTimeChartTitle(), mnDefaultOffset, pnHistoryLength, pnWidth, pnHeight);
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
        
        plot.getRenderer().setSeriesPaint(moValueHistory.size(), Color.white); //lower limit line     
        plot.getRenderer().setSeriesPaint(moValueHistory.size()+1, Color.white); //upper limit line
        
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
    	mrLower = -0.1;
    	moLowerLimit = new XYSeries("");
    	moLowerLimit.setMaximumItemCount(mnHistoryLength);
    	moLowerLimit.add(mnCurrentTime, mrLower);
    	poDataset.addSeries(moLowerLimit);  
    	
    	mrUpper = 0.1;
    	mrUpper = mrUpper + 2*moValueHistory.size() - 1;
    	moUpperLimit = new XYSeries("");
    	moUpperLimit.setMaximumItemCount(mnHistoryLength);
    	moUpperLimit.add(mnCurrentTime, mrUpper);
    	poDataset.addSeries(moUpperLimit);     	
    }
    
    @Override
    protected XYSeriesCollection createDataset() {
    	XYSeriesCollection poDataset = super.createDataset();
		
		addLimitLines(poDataset);
		
		return poDataset;
    }    
    
    private void updateLimitLines() {
		moLowerLimit.add(mnCurrentTime, mrLower);
		moUpperLimit.add(mnCurrentTime, mrUpper);
    }	
    
    @Override
	protected void updateDataset() {
    	super.updateDataset();
		updateLimitLines();
    }
   
}
