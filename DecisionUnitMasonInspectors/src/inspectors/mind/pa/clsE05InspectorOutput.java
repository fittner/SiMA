/**
 * clsE05InspectorOutput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.10.2009, 20:56:53
 */
package inspectors.mind.pa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentationSingle;
import pa.modules.E05_GenerationOfAffectsForDrives;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.10.2009, 20:56:53
 * 
 */
public class clsE05InspectorOutput extends Inspector{

	/**
	 * DOCUMENT (tobias) - insert description 
	 * 
	 * @author tobias
	 * Jul 31, 2009, 10:41:09 AM
	 */
	private static final long serialVersionUID = 2381501531626669313L;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private E05_GenerationOfAffectsForDrives moE05AffectsForDrives;
	
	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDataset;
	private DefaultCategoryDataset moDatasetUpperBounds;
	private DefaultCategoryDataset moDatasetLowerBounds;
	private DefaultCategoryDataset moDatasetMaxValue;
    
    public clsE05InspectorOutput(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E05_GenerationOfAffectsForDrives poE05AffectsForDrives)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moE05AffectsForDrives= poE05AffectsForDrives;

    	initChart();
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	add(moChartPanel);
    }

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 21.07.2009, 17:25:04
	 *
	 */
	private void initChart() {
		
		moDataset = new DefaultCategoryDataset();

		for(clsPrimaryInformation oDriveTP : moE05AffectsForDrives.moDriveList ) {
			moDataset.addValue( oDriveTP.moAffect.moValue.get(), "Drive", ((clsThingPresentationSingle)oDriveTP.moTP).moContent.toString()); 
		}
		
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Current Drives and Affects",     // chart title
                "Drives",               // domain axis label
                "Affect",                  // range axis label
                moDataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                false,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
            );
        
        oChartPanel.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) oChartPanel.getPlot();
        plot.setRenderer(new LayeredBarRenderer());
        plot.setBackgroundPaint(Color.white);

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperBound(1.5); //set the max value for the energy/nutrition

        // disable bar outlines...
        LayeredBarRenderer renderer = (LayeredBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setSeriesBarWidth(0, 0.4);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(150, 150, 150),
                0.0f, 0.0f, new Color(64, 64, 64));
        renderer.setSeriesPaint(0, gp0);
 
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 6.0));
        
//        //adds the lower limits to the chart
//        CategoryItemRenderer renderer2 = new LevelRenderer();
//        renderer2.setSeriesStroke(0, new BasicStroke(2.0f));
//        renderer2.setSeriesStroke(1, new BasicStroke(2.0f));
//        plot.setDataset(1, moDatasetLowerBounds);
//        plot.setRenderer(1, renderer2);
//        
//        //adds the upper limits to the chart
//        CategoryItemRenderer renderer3 = new LevelRenderer();
//        renderer3.setSeriesStroke(0, new BasicStroke(2.0f));
//        renderer3.setSeriesStroke(1, new BasicStroke(2.0f));
//        plot.setDataset(2, moDatasetUpperBounds);
//        plot.setRenderer(2, renderer3);
//        
//        //adds the max value to the chart
//        CategoryItemRenderer renderer4 = new LevelRenderer();
//        renderer4.setSeriesStroke(0, new BasicStroke(2.0f));
//        renderer4.setSeriesStroke(1, new BasicStroke(2.0f));
//        plot.setDataset(3, moDatasetMaxValue);
//        plot.setRenderer(3, renderer4);        
        
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
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
		
		moDataset = new DefaultCategoryDataset();

		for(clsPrimaryInformation oPrimInfo : moE05AffectsForDrives.moDriveList ) {
			moDataset.addValue( oPrimInfo.moAffect.moValue.get(), "Drive", ((clsThingPresentationSingle)oPrimInfo.moTP).moContent.toString()); 
		}
		
		moChartPanel.getChart().getCategoryPlot().setDataset(moDataset);
		
		this.repaint();
	}
	
}