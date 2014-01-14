/**
 * clsInspectorSlowMessengers.java: BW - bw.utils.inspectors.body
 * 
 * @author deutsch
 * 16.09.2009, 17:24:51
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import du.enums.eSlowMessenger;

import bw.body.internalSystems.clsSlowMessengerSystem;
import bw.exceptions.exSlowMessengerDoesNotExist;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import singeltons.clsSingletonMasonGetter;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 16.09.2009, 17:24:51
 * 
 */
public class clsInspectorSlowMessengers extends Inspector {
	/**
	 * @author deutsch
	 * 16.09.2009, 18:30:38
	 */
	private static final long serialVersionUID = 3447642649370004869L;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private ChartPanel moChartPanel;
	private XYSeriesCollection moDataset;
	private HashMap<eSlowMessenger, XYSeries> moSeries;
	
	private clsSlowMessengerSystem moSlowMessengerSystem; //reference
	private int mnHistoryLength;
	
	public clsInspectorSlowMessengers(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsSlowMessengerSystem poSlowMessengerSystem)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	mnHistoryLength = 400;
    
    	moSlowMessengerSystem = poSlowMessengerSystem;
    	initChart();
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
    }
    
	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 21.07.2009, 17:25:04
	 *
	 */
	private void initChart() {
		
		moDataset = new XYSeriesCollection();
		
		moSeries = new HashMap<eSlowMessenger, XYSeries>(eSlowMessenger.values().length);
		long currentTime = clsSingletonMasonGetter.getSimState().schedule.getSteps();
		
		for (eSlowMessenger c : eSlowMessenger.values()) {
			XYSeries oTemp = new XYSeries(c.name());
			oTemp.setMaximumItemCount(mnHistoryLength);
			try {
				oTemp.add(currentTime, moSlowMessengerSystem.getMessengerValue(c));
				moSeries.put(c, oTemp);
				moDataset.addSeries(oTemp);
			} catch (exSlowMessengerDoesNotExist e) {
				//nothing to do - slow messenger does not exist ...
			}			
		}
		
        JFreeChart oChartPanel = ChartFactory.createXYLineChart(
                "Slow Messengers",     // chart title
                "Steps",               // domain axis label
                "Amount",                  // range axis label
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
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
   
        
     // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        moChartPanel = new ChartPanel(oChartPanel);
        moChartPanel.setFillZoomRectangle(true);
        //chartPanel.setMouseWheelEnabled(true);
        moChartPanel.setPreferredSize(new Dimension(500, 270));
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
		long currentTime = clsSingletonMasonGetter.getSimState().schedule.getSteps();
		
		for (Map.Entry<eSlowMessenger, XYSeries> entry : moSeries.entrySet()) {
				try {
					entry.getValue().add(currentTime, moSlowMessengerSystem.getMessengerValue(entry.getKey()));
				} catch (exSlowMessengerDoesNotExist e) {
					// nothing to do -- maybe remove the entry ...
				}
		}

		this.repaint();
		
	}
	

}
