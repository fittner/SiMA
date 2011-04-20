/**
 * clsE05DriveTiming.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 23.12.2009, 10:42:35
 */
package inspectors.mind.pa._v19;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import pa._v19.interfaces.itfTimeChartInformationContainer;
import pa._v19.tools.clsPair;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.12.2009, 10:42:35
 * 
 */
@Deprecated
public class clsE05DriveTiming  extends Inspector{

	private static final long serialVersionUID = 7987322176593478683L;

	private itfTimeChartInformationContainer moTimeingContainer;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private ChartPanel moChartPanel;
	private XYSeriesCollection moDataset;
	private HashMap<String, XYSeries> moSeries;
	private ArrayList<String> moFirstOrder;
	
	private long moCurrentTime;
	private int mnHistoryLength;
    
    public clsE05DriveTiming(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            itfTimeChartInformationContainer poTimeingContainer,
            int pnStepHistory, String poChartName)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moTimeingContainer= poTimeingContainer;
    	mnHistoryLength = pnStepHistory;
    	moCurrentTime = 0;
    	
    	moFirstOrder = new ArrayList<String>(); 

    	initChart(poChartName);
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	add(moChartPanel);
    }
    
    private void initChart(String poChartName) {
		
		moDataset = new XYSeriesCollection();
		
		ArrayList<clsPair<String, Double>> oTimingValues = moTimeingContainer.getTimeChartData();
		
		moSeries = new HashMap<String, XYSeries>(oTimingValues.size());
		int nOffeset=0;
		String oInputOrder = "";
		
		XYSeries oTemp1 = new XYSeries("");
		oTemp1.setMaximumItemCount(mnHistoryLength);
		oTemp1.add(moCurrentTime, -0.1);
		moSeries.put("-0.1", oTemp1);
		moDataset.addSeries(oTemp1);
		
		XYSeries oTemp2 = new XYSeries("");
		oTemp2.setMaximumItemCount(mnHistoryLength);
		oTemp2.add(moCurrentTime, 1.1);
		moSeries.put("1.1", oTemp2);
		moDataset.addSeries(oTemp2);
		
		for (clsPair<String, Double> c : oTimingValues) {
			XYSeries oTemp = new XYSeries(c.a);
			oTemp.setMaximumItemCount(mnHistoryLength);
			oTemp.add(moCurrentTime, c.b+nOffeset);
			moSeries.put(c.a, oTemp);
			moDataset.addSeries(oTemp);
			moFirstOrder.add(c.a);
			
			oInputOrder += "\n" + nOffeset + ": " + c.a;
			
			//nOffeset+=2;
		}
		
        JFreeChart oChartPanel = ChartFactory.createXYLineChart(
                poChartName,     // chart title
                "Steps",               // domain axis label
                "Affects",                  // range axis label
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
       // moChartPanel.setFillZoomRectangle(true);
        //chartPanel.setMouseWheelEnabled(true);
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

		ArrayList<clsPair<String, Double>> oTimingData = moTimeingContainer.getTimeChartData();
		int nOffeset=0;

		moSeries.get("-0.1").add(moCurrentTime, -0.1 );
		moSeries.get("1.1").add(moCurrentTime, 1.1 );
		for (clsPair<String, Double> oProbe : oTimingData) {
			
			moSeries.get(oProbe.a).add(moCurrentTime, oProbe.b + nOffeset );
			//nOffeset+=2;
		}
		this.repaint();
		
	}
}