/**
 * clsMatchController.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 04.11.2009, 18:54:38
 */
package inspectors.mind.pa._v19;

import java.awt.BasicStroke;
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

import pa.interfaces._v19.itfTimeChartInformationContainer;
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
public class clsTimingDiagramInspector extends Inspector{

	private static final long serialVersionUID = 7987322176593478683L;

	private itfTimeChartInformationContainer moTimeingContainer;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private ChartPanel moChartPanel;
	private XYSeriesCollection moDataset;
	private HashMap<String, XYSeries> moSeries;
	private ArrayList<String> moFirstOrder;
	
	private int moCurrentTime;
	private int mnHistoryLength;
    
    public clsTimingDiagramInspector(sim.portrayal.Inspector originalInspector,
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

    private ArrayList<clsPair<String, Double>> convert(ArrayList<String> poCaptions, ArrayList<Double> poValues) {
    	ArrayList<clsPair<String, Double>> oResult = new ArrayList<clsPair<String,Double>>();
    	
    	for (int i=0; i<poCaptions.size(); i++) {
    		clsPair<String, Double> oPair = new clsPair<String, Double>(poCaptions.get(i), poValues.get(i));
    		oResult.add(oPair);
    	}
    	
    	return oResult;
    }
    
	private void initChart(String poChartName) {
		
		moDataset = new XYSeriesCollection();
		
		ArrayList<clsPair<String, Double>> oTimingValues = moTimeingContainer.getTimeChartData();
		
		moSeries = new HashMap<String, XYSeries>(oTimingValues.size());
		int nOffeset=0;
		String oInputOrder = "";
		
		XYSeries oTemp1 = new XYSeries("");
		oTemp1.setMaximumItemCount(mnHistoryLength);
		oTemp1.add(moCurrentTime, -1);
		moSeries.put("", oTemp1);
		moDataset.addSeries(oTemp1);
			
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
		
        JFreeChart oChartPanel = ChartFactory.createXYLineChart(
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
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        
        plot.getRenderer().setSeriesPaint(0, Color.white); 
        plot.getRenderer().setSeriesPaint(1, Color.red);
        plot.getRenderer().setSeriesPaint(2, Color.orange);
        plot.getRenderer().setSeriesPaint(3, new Color(204, 51, 102));
        plot.getRenderer().setSeriesPaint(4, new Color(153, 51, 0));
        plot.getRenderer().setSeriesPaint(5, new Color(255, 153, 0));
        
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
      
        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        moChartPanel = new ChartPanel(oChartPanel);
        moChartPanel.setFillZoomRectangle(true);
        //chartPanel.setMouseWheelEnabled(true);
        moChartPanel.setPreferredSize(new Dimension(600, 600));
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
		
		moSeries.get("").add(moCurrentTime, -1 );
		int nOffset=0;
		
		for (clsPair<String, Double> oProbe : oTimingData) {
			moSeries.get(oProbe.a).add(moCurrentTime, oProbe.b + nOffset );
			nOffset+=2;
		}
		
		this.repaint();
		
	}
}