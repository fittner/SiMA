/**
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
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

import bw.body.clsComplexBody;
import bw.body.internalSystems.clsInternalSystem;
import bw.utils.enums.eNutritions;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * This inspector is used to show the basic information needed for the body: health, energy, stamina, undigestable, stomack tension 
 * 
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
 * 
 */
public class clsInspectorBodyOverview extends Inspector{

	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author muchitsch
	 * Jul 24, 2009, 12:34:41 PM
	 */
	private static final long serialVersionUID = 1L;
	public sim.portrayal.Inspector moOriginalInspector;
	private clsInternalSystem moInternalSystem;
	private clsComplexBody moComplexBody;
	

	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDataset;
	private DefaultCategoryDataset moDatasetUpperLimits;
	private DefaultCategoryDataset moDatasetLowerLimits;
	

	
	
	public clsInspectorBodyOverview(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsComplexBody poComplexBody)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moComplexBody = poComplexBody;
    	moInternalSystem= moComplexBody.getInternalSystem();

    	initChart();
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
    }
	
	private void initChart() {

		//
		AddAndUpdateDatasets();
		
		
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Body Overview",     // chart title
                "Sys",               // domain axis label
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
        rangeAxis.setUpperBound(120.0); //set the max value for the energy/nutrition

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
        plot.setDataset(1, moDatasetLowerLimits);
        plot.setRenderer(1, renderer2);
        
        //adds the upper limits to the chart
        CategoryItemRenderer renderer3 = new LevelRenderer();
        renderer3.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer3.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setDataset(2, moDatasetUpperLimits);
        plot.setRenderer(2, renderer3);
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
		
		AddAndUpdateDatasets();
		
		moChartPanel.getChart().getCategoryPlot().setDataset(moDataset);
		
		this.repaint();
	}
	
	public void AddAndUpdateDatasets() {
		
		moDataset = new DefaultCategoryDataset();
		moDatasetLowerLimits = new DefaultCategoryDataset();
		moDatasetUpperLimits = new DefaultCategoryDataset();
		
		//*** HEALTH ***
		//health value divided by 10 to match with other upper bounds
		double rHealth = moInternalSystem.getHealthSystem().getHealth().getContent()/moInternalSystem.getHealthSystem().getHealth().getMaxContent()*100;
		moDataset.addValue(rHealth, "Health", "Health");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Health");
		moDatasetUpperLimits.addValue(100, "Upper Bound", "Health");
		
		//*** ENERGY ***
		double rEnergy = moInternalSystem.getStomachSystem().getEnergy() / 5 *100; //5 is taken to bnormalize the value to the other
		moDataset.addValue(rEnergy, "Energy", "Energy");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Energy");
		moDatasetUpperLimits.addValue(100, "Upper Bound", "Energy");
		
		//*** STAMINA ***
		double rStamina = moInternalSystem.getStaminaSystem().getStamina().getContent() / moInternalSystem.getStaminaSystem().getStamina().getMaxContent() *100;
		moDataset.addValue(rStamina, "Stamina", "Stamina");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Stamina");
		moDatasetUpperLimits.addValue(100, "Upper Bound", "Stamina");
		
		//*** STOMACH TENSION ***
		double rTension = 0;
		try {
			rTension = moInternalSystem.getStomachSystem().getWeight() / moInternalSystem.getStomachSystem().getMaxWeight() *100;
		} catch (java.lang.ArithmeticException e) {
			//nothing to do
		}
		moDataset.addValue(rTension, "Stomach Tension", "Stomach Tension");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Stomach Tension");
		moDatasetUpperLimits.addValue(100, "Upper Bound", "Stomach Tension");		

		//*** INTESTINE PRESSURE ***
		double rPressure = 0;
		try {
			rPressure = moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.EXCREMENT).getContent() / 
						moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.EXCREMENT).getMaxContent() *100;
		} catch (java.lang.ArithmeticException e) {
			//nothing to do
		}		
		moDataset.addValue(rPressure, "Intestine Pressure", "Intestine Pressure");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Intestine Pressure");
		moDatasetUpperLimits.addValue(100, "Upper Bound", "Intestine Pressure");		
		
		//*** UNDIGESTABLE ***
		double rUndigestableValue = moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.UNDIGESTABLE).getContent() /
		moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.UNDIGESTABLE).getMaxContent() *100;
		moDataset.addValue(rUndigestableValue, "Undigestable", "Undigestable");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Undigestable");
		moDatasetUpperLimits.addValue(100, "Upper Bound", "Undigestable");

	}

}
