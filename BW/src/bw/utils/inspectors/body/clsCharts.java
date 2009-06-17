/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;

import java.awt.Color;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.xy.XYSeriesCollection;

import sim.clsBWMain;


/**
 * Class for all JFreeChart definitions. Here all special charting needs are nested.
 * To add another Chart you have to add the chart definition here and also add some code in clsBWMainWithUI.addChartPanel()
 * 
 * @author muchitsch
 * 
 */



public class clsCharts{
	
	private clsBWMain moModel;
  
	public clsCharts (clsBWMain poModel){
		moModel = poModel;
	}
	
	
	/**
	 * Test method for chart generation
	 *
	 * @return JFreeChart
	 */
	public JFreeChart createTestChart() {
				JFreeChart chart =
		            ChartFactory.createXYLineChart(
		            "Agent States",     // the title of the chart
		            "Time step",
		            //"% Population",                                          // the label for the X axis
		            "Agent State",
		            // % Wealththe label for the Y axis
		            new XYSeriesCollection(moModel.getMoTestSeries()),              // the dataset for the chart
		            PlotOrientation.VERTICAL,                             // the orientation of the chart
		            true,                                                 // a flag specifying whether or not a legend is required
		            true,                                                 // a flag specifying whether or not tooltips should be generated
		            false);                                               // a flag specifying whether or not the chart should generate URLs
		
		    XYPlot plot = chart.getXYPlot();
		
		    ValueAxis xAxis = plot.getDomainAxis();
		    xAxis.setFixedDimension(100);
		    //xAxis.setRange(0,100);
		    //yAxis.setRange(0,1);
		   XYItemRenderer renderer = plot.getRenderer();
		   renderer.setSeriesPaint(0, Color.black);
		    //System.out.println("done creating chart");
		    return chart;
    
    
//	       JFreeChart chart = ChartFactory.createXYLineChart(
//	               "Trading and Population over Time",
//	               "Time",
//	               "Level",
//	               	moModel.moAgents_series_coll,
//	                PlotOrientation.VERTICAL,
//	                true,
//	                true,
//	                false);
//	       		moModel.moTestChart = chart;
//	            NumberAxis rangeAxis1 = new NumberAxis("Time");
//	            rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//	            org.jfree.chart.axis.NumberAxis domainAxis = new NumberAxis("Bins");
//	            XYPlot plot = chart.getXYPlot();
//	            ValueAxis xAxis = plot.getDomainAxis();
//	            XYItemRenderer renderer = plot.getRenderer();
//	            renderer.setSeriesPaint(0, Color.BLUE);
//	            //TODO plot.setDataset(1, moModel.trade_coll);           
//	            XYItemRenderer rend2 = new StandardXYItemRenderer();
//	            //if (rend2 != null)
//	            rend2.setSeriesPaint(1, Color.BLACK);
//	            plot.setRenderer(1, rend2);
//	            return chart;
	    }


}
