/**
 * clsE_ChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 19:01:42
 */
package inspectors.mind.pa._v30;

import java.awt.Color;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pa.interfaces.itfTimeChartInformationContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 19:01:42
 * 
 */
public class clsE_ChartInspector extends clsE_GenericChartInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 19:01:50
	 */
	private static final long serialVersionUID = -2224815255678859538L;
	private final double mrLower; 
	private final double mrUpper;
	protected XYSeries moUpperLimit;
	protected XYSeries moLowerLimit;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 19:01:47
	 *
	 * @param poTimingContainer
	 * @param poYAxisCaption
	 * @param prLowerLimit
	 * @param prUpperLimit
	 * @param poChartName
	 */
	public clsE_ChartInspector(
			itfTimeChartInformationContainer poTimingContainer,
			String poYAxisCaption, double prLowerLimit, double prUpperLimit,
			String poChartName) {
		super(poTimingContainer, poYAxisCaption, poChartName, 0);
		
		mrLower = prLowerLimit;
		mrUpper = prUpperLimit;
	}
	
    private void addLimitLines(XYSeriesCollection poDataset) {
    	//tweak to have a nice static upper and lower limit 
    	moUpperLimit = new XYSeries("");
    	moUpperLimit.setMaximumItemCount(mnHistoryLength);
    	moUpperLimit.add(moCurrentTime, mrUpper);
    	poDataset.addSeries(moUpperLimit);    
		
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
		moUpperLimit.add(moCurrentTime, mrUpper );    	
    }	
    
    @Override
	protected void updateData() {
    	super.updateData();
		updateLimitLines();
    }

    @Override
    protected void customizePlot(XYPlot plot) {
        super.customizePlot(plot);
        
        plot.getRenderer().setSeriesPaint(moValueHistory.size(), Color.white);
        plot.getRenderer().setSeriesPaint(moValueHistory.size()+1, Color.white);        
    }
}
