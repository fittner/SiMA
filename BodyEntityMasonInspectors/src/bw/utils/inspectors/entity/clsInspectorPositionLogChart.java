/**
 * clsInspectorPositionLogChart.java: BW - bw.utils.inspectors.entity
 * 
 * @author deutsch
 * 30.04.2011, 14:29:56
 */
package bw.utils.inspectors.entity;

import java.awt.BorderLayout;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import bw.entities.logger.Position;
import bw.entities.logger.clsPositionLogger;
import sim.portrayal.Inspector;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * TODO (deutsch) - add max limits for chart. resizes x and y to current values constantly ... no setrange functionality available .. damn!!! 
 * 
 * @author deutsch
 * 30.04.2011, 14:29:56
 * 
 */
public class clsInspectorPositionLogChart extends Inspector {
	private clsPositionLogger moPositionLogger;
	
	private ChartPanel moChartPanel;
	private JFreeChart moChart;
	
//	private DefaultXYDataset moPositionHistory;
	private final int mnHistoryLength = 500;
	
//	private boolean hasBeenVisible = false;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 30.04.2011, 14:30:11
	 */
	private static final long serialVersionUID = -436016968066510568L;

	public clsInspectorPositionLogChart(clsPositionLogger poPL) {
		moPositionLogger = poPL;
		
		moChart = createChart(getDataSet(), "Position history");
		moChartPanel = new ChartPanel(moChart);
		moChartPanel.setPreferredSize(new java.awt.Dimension(400,400));
		
	     // set the background color for the chart...
		moChart.setBackgroundPaint(Color.white);
   
     // get a reference to the plot for further customisation...
        XYPlot plot = (XYPlot) moChart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
   
        
     // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
	}

	
	JFreeChart createChart(XYDataset dataset, String header) {
		JFreeChart chart = ChartFactory.createXYLineChart(header, "", "",
				dataset, PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}
	
	private DefaultXYDataset getDataSet() {
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		dataset.addSeries("Position history", getData());
		return dataset;
	}
	
	private double[][] getData() {
		int start = 0;
		int end = moPositionLogger.moPositionHistory.size();
		
		if (end-mnHistoryLength > start) {
			start = end-mnHistoryLength;
		}
		
		int len = end - start;
		
		double[] xx = new double[len];
		double[] yy = new double[len];
		
		int j=0;
		for (int i=start; i<end; i++) {
			Position oPos = moPositionLogger.moPositionHistory.get(i);
			xx[j] = oPos.x;
			yy[j] = oPos.y;
					
			j++;
		}
		
		return new double[][]{xx,yy};
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 30.04.2011, 14:30:06
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		if (isVisible()) {
			moChart.getXYPlot().setDataset(getDataSet());
		}
		
	}

}
