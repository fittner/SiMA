/**
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LevelRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import bw.body.internalSystems.clsStomachSystem;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsNutritionLevel;

import sim.display.GUIState;

import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * Inspector for testing purpose to switch on/off intelligence-levels in 'AI brain'  
 * 
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 */
public class clsInspectorFillLevel extends Inspector{

	/**
	 * @author deutsch
	 * Jul 31, 2009, 10:41:09 AM
	 */
	private static final long serialVersionUID = 2381501531626669313L;
	
	public sim.portrayal.Inspector moOriginalInspector;
	private clsStomachSystem moStomachSystem;
	
	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDataset;
	private DefaultCategoryDataset moDatasetUpperBounds;
	private DefaultCategoryDataset moDatasetLowerBounds;
	private DefaultCategoryDataset moDatasetMaxValue;
    
    public clsInspectorFillLevel(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsStomachSystem poStomachSystem)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moStomachSystem= poStomachSystem;

    	initNutritionChart();
	
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
	private void initNutritionChart() {
		
		moDataset = new DefaultCategoryDataset();
		moDatasetLowerBounds = new DefaultCategoryDataset();
		moDatasetUpperBounds = new DefaultCategoryDataset();
		moDatasetMaxValue = new DefaultCategoryDataset();

		moDataset.addValue(moStomachSystem.getEnergy(), "Energy", "Energy");
		moDatasetLowerBounds.addValue(0, "Lower Bound", "Energy");
		moDatasetUpperBounds.addValue(5, "Upper Bound", "Energy");
		moDatasetMaxValue.addValue(5, "Max Value", "Energy");

		for(Map.Entry<eNutritions, clsNutritionLevel> oNut : moStomachSystem.getList().entrySet() ) {
			moDataset.addValue( oNut.getValue().getContent(), "Nutrition", oNut.getKey().toString()); 
			moDatasetLowerBounds.addValue( oNut.getValue().getLowerBound(), "Lower Bound", oNut.getKey().toString());
			moDatasetUpperBounds.addValue( oNut.getValue().getUpperBound(), "Upper Bound", oNut.getKey().toString());
			moDatasetMaxValue.addValue( oNut.getValue().getMaxContent(), "Max Value", oNut.getKey().toString());
		}
		
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Stomach Fill Level",     // chart title
                "Nutritions",               // domain axis label
                "",                  // range axis label
                moDataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
            );
        
        oChartPanel.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) oChartPanel.getPlot();

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperBound(5.0); //set the max value for the energy/nutrition

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64));
        renderer.setSeriesPaint(0, gp0);
 
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 6.0));
        
        //adds the lower limits to the chart
        CategoryItemRenderer renderer2 = new LevelRenderer();
        renderer2.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer2.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setDataset(1, moDatasetLowerBounds);
        plot.setRenderer(1, renderer2);
        
        //adds the upper limits to the chart
        CategoryItemRenderer renderer3 = new LevelRenderer();
        renderer3.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer3.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setDataset(2, moDatasetUpperBounds);
        plot.setRenderer(2, renderer3);
        
        //adds the max value to the chart
        CategoryItemRenderer renderer4 = new LevelRenderer();
        renderer4.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer4.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setDataset(3, moDatasetMaxValue);
        plot.setRenderer(3, renderer4);        
        
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
		moDataset.addValue(moStomachSystem.getEnergy(), "Energy", "Energy");
		moDatasetLowerBounds.addValue(0, "Lower Bound", "Energy");
		moDatasetUpperBounds.addValue(5, "Upper Bound", "Energy");
	
		for(Map.Entry<eNutritions, clsNutritionLevel> oNut : moStomachSystem.getList().entrySet() ) {
			moDataset.addValue( oNut.getValue().getContent(), "Nutrition", oNut.getKey().toString()); 
			moDatasetLowerBounds.addValue( oNut.getValue().getLowerBound(), "Lower Bound", oNut.getKey().toString());
			moDatasetUpperBounds.addValue( oNut.getValue().getUpperBound(), "Upper Bound", oNut.getKey().toString());
		}
		
		moChartPanel.getChart().getCategoryPlot().setDataset(moDataset);
		
		this.repaint();
	}
	
}
