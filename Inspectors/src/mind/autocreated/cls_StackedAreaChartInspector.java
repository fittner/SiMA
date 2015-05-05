/**
 * CHANGELOG
 *
 * 19.02.2015 Kollmann - File created
 *
 */
package mind.autocreated;

import inspector.interfaces.itfInspectorStackedAreaChart;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import singeltons.clsSimState;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 19.02.2015, 11:15:53
 * 
 */
public class cls_StackedAreaChartInspector extends cls_AbstractChartInspector {
	
	protected String moYAxisCaption = "";
	protected itfInspectorStackedAreaChart moContent = null;
	protected DefaultCategoryDataset moDataset = null;
	protected String moLabel = "";
	
	/**
	 * DOCUMENT (Kollmann) - insert description 
	 *
	 * @since 19.02.2015 11:29:48
	 *
	 * @param poTimingContainer
	 * @param poYAxisCaption
	 * @param poChartName
	 * @param pnOffset
	 */
	public cls_StackedAreaChartInspector(
			itfInspectorStackedAreaChart poTimingContainer, String poYAxisCaption,
			String poChartName, String poLabel) {
		super(poChartName);
		
		moLabel = poLabel;

		moContent = poTimingContainer;
		moChartName = poChartName;
		moYAxisCaption = poYAxisCaption;
		moDataset = (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset("", "", new double[][] {});
		moChartPanel=create();
    	
    	add(moChartPanel);
	}

		/* (non-Javadoc)
	 *
	 * @since 19.02.2015 14:21:08
	 * 
	 * @see mind.autocreated.cls_AbstractChartInspector#initChart()
	 */
	@Override
	protected ChartPanel initChart() {
		final JFreeChart chart = ChartFactory.createStackedAreaChart(
	            moContent.getTitle(moLabel),// chart title
	            "Steps",                	// domain axis label
	            moYAxisCaption,             // range axis label
	            moDataset,					// data
	            PlotOrientation.VERTICAL,  	// orientation
	            true,                      	// include legend
	            true,
	            false
	        );

	        chart.setBackgroundPaint(Color.white);

	        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        plot.setForegroundAlpha(0.5f);
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        final CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setLowerMargin(0.0);
	        domainAxis.setUpperMargin(0.0);
	        domainAxis.setTickLabelsVisible(false);
	        
	        // change the auto tick unit selection to integer units only...
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setRange(0.0, 1.0);
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

	        final CategoryItemRenderer renderer = plot.getRenderer();
	        renderer.setBaseItemLabelsVisible(true);
	        
	        ChartPanel oChartPanel = new ChartPanel(chart);
	        oChartPanel.setFillZoomRectangle(true);
	        oChartPanel.setPreferredSize(new Dimension(600, 400));
	        
	        return oChartPanel;
	}


	/* (non-Javadoc)
	 *
	 * @since 19.02.2015 14:21:08
	 * 
	 * @see mind.autocreated.cls_AbstractChartInspector#updateDataset()
	 */
	@Override
	protected void updateDataset() {
		ArrayList<Double> oData = moContent.getData(moLabel);
		ArrayList<String> oCaptions = moContent.getCategoryCaptions(moLabel);
		
		if(oData.size() != oCaptions.size()) {
			throw new RuntimeException("Dataset inconsistent");
		}
		
		for(int i = 0; i < oData.size(); ++i) {
			moDataset.addValue(oData.get(i), oCaptions.get(i), Long.toString(clsSimState.getSteps()));
		}
	}
	
	private ChartPanel create()  {
    	ChartPanel poChartPanel = createPanel();
    	return poChartPanel;
    }
    
    private void recreate() {
    	removeAll();
    	create();
    }
}
