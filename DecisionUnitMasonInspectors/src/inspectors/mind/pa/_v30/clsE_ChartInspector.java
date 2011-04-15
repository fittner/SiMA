/**
 * clsE_GenericChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 */
package inspectors.mind.pa._v30;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import pa.interfaces.itfTimeChartInformationContainer;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 * 
 */
public class clsE_ChartInspector extends Inspector {
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 17:53:49
	 */
	private static final long serialVersionUID = 3125332229433964204L;

	private itfTimeChartInformationContainer moTimeingContainer;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private ChartPanel moChartPanel;
	private XYSeriesCollection moDataset;
	private ArrayList<XYSeries> moValueHistory;
	
	private XYSeries moUpperLimit;
	private XYSeries moLowerLimit;
	
	private long moCurrentTime;
	private final int mnHistoryLength = 200;
	
	private double mrLower; 
	private double mrUpper;
	
	private String moXAxisCaption;
	private String moYAxisCaption;

    public clsE_ChartInspector(
    		sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            itfTimeChartInformationContainer poTimingContainer,
            String poYAxisCaption,
            double prLowerLimit,
            double prUpperLimit,
            String poChartName)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moTimeingContainer= poTimingContainer;
    	moCurrentTime = 0;
    	mrLower = prLowerLimit;
    	mrUpper = prUpperLimit;
    	moXAxisCaption = "Steps";
    	moYAxisCaption = poYAxisCaption;
    	
    	initChart(poChartName);
		setLayout(new FlowLayout(FlowLayout.LEFT));
    	add(moChartPanel);
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
    
    private void updateLimitLines() {
		moLowerLimit.add(moCurrentTime, mrLower );
		moUpperLimit.add(moCurrentTime, mrUpper );    	
    }
    
    private void initChart(String poChartName) {
		moDataset = new XYSeriesCollection();
		
		addLimitLines(moDataset);
		
		ArrayList<String> oCaptions = moTimeingContainer.getTimeChartCaptions();
		ArrayList<Double> oValues = moTimeingContainer.getTimeChartData();
		
		moValueHistory = new ArrayList<XYSeries>(oValues.size());

		for (int i=0; i<oValues.size(); i++) {
			XYSeries oTemp = new XYSeries( oCaptions.get(i) );
			oTemp.setMaximumItemCount( mnHistoryLength );
			oTemp.add(moCurrentTime, oValues.get(i) );
			moValueHistory.add(oTemp);
			moDataset.addSeries(oTemp);			
		}
		
        JFreeChart oChartPanel = ChartFactory.createXYLineChart(
                poChartName,     // chart title
                moXAxisCaption,               // domain axis label
                moYAxisCaption,                  // range axis label
                moDataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
            );
        
     // set the background color for the chart...
        oChartPanel.setBackgroundPaint(Color.white);
   
     // get a reference to the plot for further customisation...
        XYPlot plot = (XYPlot) oChartPanel.getPlot();
        plot.getRenderer().setSeriesPaint(0, Color.white); 
        plot.getRenderer().setSeriesPaint(1, Color.white); 
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setBackgroundPaint(Color.white);
                
     // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        moChartPanel = new ChartPanel(oChartPanel);
        moChartPanel.setPreferredSize(new Dimension(400, 600));
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 09:52:36
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		moCurrentTime += 1;
		updateLimitLines();

		ArrayList<Double> oTimingData = moTimeingContainer.getTimeChartData();
		
		for (int i=0; i<oTimingData.size(); i++) {
			((XYSeries)moValueHistory.get(i)).add(moCurrentTime, oTimingData.get(i));
		}
		this.repaint();
		
	}    
    	
}
