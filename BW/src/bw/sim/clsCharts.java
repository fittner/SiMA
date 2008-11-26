/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * TODO insert Description
 * 
 * @author muchitsch
 * 
 */



public class clsCharts{
	
	clsMain moModel;
  
	clsCharts (clsMain poModel){
		moModel = poModel;
	}
	
	
	JFreeChart createTestChart() {
	       JFreeChart chart = ChartFactory.createXYLineChart(
	               "Trading and Population over Time",
	               "Time",
	               "Level",
	               	moModel.agents_series_coll,
	                PlotOrientation.VERTICAL,
	                true,
	                true,
	                false);
	       		moModel.moTestChart = chart;
	            NumberAxis rangeAxis1 = new NumberAxis("Time");
	            rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	            org.jfree.chart.axis.NumberAxis domainAxis = new NumberAxis("Bins");
	            XYPlot plot = chart.getXYPlot();
	            ValueAxis xAxis = plot.getDomainAxis();
	            XYItemRenderer renderer = plot.getRenderer();
	            renderer.setSeriesPaint(0, Color.BLUE);
	            //TODO plot.setDataset(1, moModel.trade_coll);           
	            XYItemRenderer rend2 = new StandardXYItemRenderer();
	            //if (rend2 != null)
	            rend2.setSeriesPaint(1, Color.BLACK);
	            plot.setRenderer(1, rend2);
	            return chart;
	    }


}
