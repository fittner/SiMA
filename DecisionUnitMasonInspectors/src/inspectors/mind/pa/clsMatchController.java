/**
 * clsMatchController.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 04.11.2009, 18:54:38
 */
package inspectors.mind.pa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
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

import pa.interfaces.itfTimeChartInformationContainer;
import pa.tools.clsPair;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 04.11.2009, 18:54:38
 * 
 */
public class clsMatchController extends Inspector{

	private static final long serialVersionUID = 7987322176593478683L;

	private itfTimeChartInformationContainer moTimeingContainer;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private ChartPanel moChartPanel;
	private XYSeriesCollection moDataset;
	private HashMap<String, XYSeries> moSeries;
	private ArrayList<String> moFirstOrder;
	
	private long moCurrentTime;
	private int mnHistoryLength;
	
	private TextField moRichText;
    
    public clsMatchController(sim.portrayal.Inspector originalInspector,
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
    	
    	moRichText = new TextField();

    	initChart(poChartName);
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	add(moChartPanel);
    	add(moRichText);
    }

	private void initChart(String poChartName) {
		
		moDataset = new XYSeriesCollection();
		
		ArrayList<clsPair<String, Double>> oTimingValues = moTimeingContainer.getTimeChartData();
		
		moSeries = new HashMap<String, XYSeries>(oTimingValues.size());
		int nOffeset=0;
		String oInputOrder = "";
		for (clsPair<String, Double> c : oTimingValues) {
			XYSeries oTemp = new XYSeries(c.a);
			oTemp.setMaximumItemCount(mnHistoryLength);
			oTemp.add(moCurrentTime, c.b+nOffeset);
			moSeries.put(c.a, oTemp);
			moDataset.addSeries(oTemp);
			moFirstOrder.add(c.a);
			
			oInputOrder += "\n" + nOffeset + ": " + c.a;
			
			nOffeset+=2;
		}

		moRichText.setText(oInputOrder);
		
        JFreeChart oChartPanel = ChartFactory.createXYStepChart(
                poChartName,     // chart title
                "Steps",               // domain axis label
                "Probes",                  // range axis label
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
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
   
        
     // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        moChartPanel = new ChartPanel(oChartPanel);
        moChartPanel.setFillZoomRectangle(true);
        //chartPanel.setMouseWheelEnabled(true);
        moChartPanel.setPreferredSize(new Dimension(800, 800));
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
		for (clsPair<String, Double> oProbe : oTimingData) {
			
			moSeries.get(oProbe.a).add(moCurrentTime, oProbe.b + nOffeset );
			nOffeset+=2;
		}
		this.repaint();
		
	}
}