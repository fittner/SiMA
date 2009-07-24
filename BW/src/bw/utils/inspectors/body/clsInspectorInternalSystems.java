/**
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import org.jfree.chart.renderer.category.BarRenderer;

import org.jfree.data.category.DefaultCategoryDataset;
import bw.body.internalSystems.clsInternalSystem;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
 * 
 */
public class clsInspectorInternalSystems extends Inspector{

	/**
	 * TODO (muchitsch) - insert description 
	 * 
	 * @author muchitsch
	 * Jul 24, 2009, 12:34:41 PM
	 */
	private static final long serialVersionUID = 1L;
	public sim.portrayal.Inspector moOriginalInspector;
	private clsInternalSystem moInternalSystem;
	
	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDatasetHealth;
	

	
	
	public clsInspectorInternalSystems(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsInternalSystem poInternalSystem)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moInternalSystem= poInternalSystem;

    	initChart();
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
    }
	
	private void initChart() {
		moDatasetHealth = new DefaultCategoryDataset();


		moDatasetHealth.addValue(moInternalSystem.getHealthSystem().getHealth().getContent(), "Health", "Health");


//		for(Map.Entry<eNutritions, clsNutritionLevel> oNut : moStomachSystem.getList().entrySet() ) {
//			moDataset.addValue( 4, "", "Nutrition "+oNut.getKey().toString()); //oNut.getValue().getContent()
//			moDatasetLowerLimits.addValue(1, "Lower Bound", "Nutrition "+oNut.getKey().toString());
//			moDatasetUpperLimits.addValue(4, "Upper Bound", "Nutrition "+oNut.getKey().toString());
//		}
		
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Internal Systems",     // chart title
                "IntSystems",               // domain axis label
                "",                  // range axis label
                moDatasetHealth,                  // data
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
        
        
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
        moChartPanel = new ChartPanel(oChartPanel);
        moChartPanel.setFillZoomRectangle(true);
        //chartPanel.setMouseWheelEnabled(true);
        moChartPanel.setPreferredSize(new Dimension(500, 270));
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * Jul 24, 2009, 12:33:23 PM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO Auto-generated method stub
		
	}
	

}
