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
import bw.body.internalSystems.clsInternalSystem;
import bw.utils.enums.eNutritions;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
 * 
 */
public class clsInspectorInternalSystems extends Inspector{

	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author muchitsch
	 * Jul 24, 2009, 12:34:41 PM
	 */
	private static final long serialVersionUID = 1L;
	public sim.portrayal.Inspector moOriginalInspector;
	private clsInternalSystem moInternalSystem;
	

	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDataset;
	private DefaultCategoryDataset moDatasetUpperLimits;
	private DefaultCategoryDataset moDatasetLowerLimits;
	

	
	
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

		moDataset = new DefaultCategoryDataset();
		moDatasetLowerLimits = new DefaultCategoryDataset();
		moDatasetUpperLimits = new DefaultCategoryDataset();
		
		moDataset.addValue(moInternalSystem.getStomachSystem().getEnergy(), "Energy", "Energy");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Energy");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Energy");
		
		double rTension = 0;
		try {
			rTension = moInternalSystem.getStomachSystem().getWeight() / moInternalSystem.getStomachSystem().getMaxWeight();
		} catch (java.lang.ArithmeticException e) {
			//nothing to do
		}
		moDataset.addValue(rTension, "Stomach Tension", "Stomach Tension");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Stomach Tension");
		moDatasetUpperLimits.addValue(1, "Upper Bound", "Stomach Tension");		

		double rPressure = 0;
		try {
			rPressure = moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.UNDIGESTABLE).getContent() / 
						moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.UNDIGESTABLE).getMaxContent();
		} catch (java.lang.ArithmeticException e) {
			//nothing to do
		}		
		moDataset.addValue(rPressure, "Intestine Pressure", "Intestine Pressure");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Intestine Pressure");
		moDatasetUpperLimits.addValue(1, "Upper Bound", "Intestine Pressure");		

		
		//health value divided by 10 to match with other upper bounds
		moDataset.addValue(moInternalSystem.getHealthSystem().getHealth().getContent()/10, "Health", "Health");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Health");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Health");
		
		moDataset.addValue(moInternalSystem.getStaminaSystem().getStamina().getContent(), "Stamina", "Stamina");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Stamina");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Stamina");

//		for(Map.Entry<eNutritions, clsNutritionLevel> oNut : moStomachSystem.getList().entrySet() ) {
//			moDataset.addValue( 4, "", "Nutrition "+oNut.getKey().toString()); //oNut.getValue().getContent()
//			moDatasetLowerLimits.addValue(1, "Lower Bound", "Nutrition "+oNut.getKey().toString());
//			moDatasetUpperLimits.addValue(4, "Upper Bound", "Nutrition "+oNut.getKey().toString());
//		}
		
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Internal Systems",     // chart title
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
        rangeAxis.setUpperBound(12.0); //set the max value for the energy/nutrition

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
		
		moDataset = new DefaultCategoryDataset();
		moDataset.addValue(moInternalSystem.getStomachSystem().getEnergy(), "Energy", "Energy");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Energy");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Energy");

		double rTension = 0;
		try {
			rTension = moInternalSystem.getStomachSystem().getWeight() / moInternalSystem.getStomachSystem().getMaxWeight();
		} catch (java.lang.ArithmeticException e) {
			//nothing to do
		}
		moDataset.addValue(rTension*10, "Stomach Tension", "Stomach Tension");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Stomach Tension");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Stomach Tension");		
		
		double rPressure = 0;
		try {
			rPressure = moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.UNDIGESTABLE).getContent() / 
						moInternalSystem.getStomachSystem().getNutritionLevel(eNutritions.UNDIGESTABLE).getMaxContent();
		} catch (java.lang.ArithmeticException e) {
			//nothing to do
		}		
		moDataset.addValue(rPressure*10, "Intestine Pressure", "Intestine Pressure");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Intestine Pressure");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Intestine Pressure");	
		
		//health value divided by 10 to match with other upper bounds
		moDataset.addValue(moInternalSystem.getHealthSystem().getHealth().getContent()/10, "Health", "Health");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Health");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Health");
		
		moDataset.addValue(moInternalSystem.getStaminaSystem().getStamina().getContent()*10, "Stamina", "Stamina");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Stamina");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Stamina");
		
		moDataset.addValue(moInternalSystem.getTemperatureSystem().getTemperature().getContent()*10, "Temperature", "Temperature");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Temperature");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Temperature");
		
		moDataset.addValue(moInternalSystem.getInternalEnergyConsumption().getSum(), "Int.Eng.Cons", "Int.Eng.Cons");
		moDatasetLowerLimits.addValue(0, "Lower Bound", "Int.Eng.Cons");
		moDatasetUpperLimits.addValue(10, "Upper Bound", "Int.Eng.Cons");
		
		//TODO: (langr) to be adapted when stomach system is ready to use
//		for(Map.Entry<eNutritions, clsNutritionLevel> oNut : moStomachSystem.getList().entrySet() ) {
//			moDataset.addValue( Math.random()*4, "Nutrition", "Nutrition "+oNut.getKey().toString()); //oNut.getValue().getContent()
//			moDatasetLowerLimits.addValue(1, "Lower Bound", "Nutrition "+oNut.getKey().toString());
//			moDatasetUpperLimits.addValue(4, "Upper Bound", "Nutrition "+oNut.getKey().toString());
//		}
		moChartPanel.getChart().getCategoryPlot().setDataset(moDataset);
		
		this.repaint();
	}

}
