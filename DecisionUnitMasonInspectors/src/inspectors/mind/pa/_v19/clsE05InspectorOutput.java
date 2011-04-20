/**
 * clsE05InspectorOutput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.10.2009, 20:56:53
 */
package inspectors.mind.pa._v19;

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

import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.modules._v19.E05_GenerationOfAffectsForDrives;

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
@Deprecated
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
	//private DefaultCategoryDataset moDatasetUpperBounds; //never used!
	//private DefaultCategoryDataset moDatasetLowerBounds; //never used!
	//private DefaultCategoryDataset moDatasetMaxValue; //never used!
    
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

		for(clsDriveMesh oDriveTP : moE05AffectsForDrives.moDriveList) {
				clsDriveMesh oDriveMeshLife = oDriveTP; 
				clsDriveMesh oDriveMeshDeath = moE05AffectsForDrives.moDriveList.get(moE05AffectsForDrives.moDriveList.indexOf(oDriveTP)+1);
				moDataset.addValue( oDriveMeshLife.getPleasure(), "Drive (Live)", oDriveMeshLife.getMoContent());
				moDataset.addValue( oDriveMeshDeath.getPleasure(), "Drive (Death)", oDriveMeshDeath.getMoContent());
				
				if(moE05AffectsForDrives.moDriveList.indexOf(oDriveTP)+1 == moE05AffectsForDrives.moDriveList.size()-1){
					break;
				}
		}
		
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Current Drives and Affects",     // chart title
                "Drives",               // domain axis label
                "Affect",                  // range axis label
                moDataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
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
        //renderer.setDrawBarOutline(false);
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(150, 150, 150),
                0.0f, 0.0f, new Color(64, 64, 64));
        renderer.setSeriesPaint(0, gp0);
        
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(255, 100, 100),
                0.0f, 0.0f, new Color(150, 64, 64));
        renderer.setSeriesPaint(1, gp1);
        
        renderer.setSeriesBarWidth(0, 0.5);
        renderer.setSeriesBarWidth(1, 0.5);
        
//        // disable bar outlines...
//        LayeredBarRenderer renderer2 = new LayeredBarRenderer();
//        //renderer2.setDrawBarOutline(false);
//        renderer2.setSeriesBarWidth(0, 0.1);
//        // set up gradient paints for series...
//        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(255, 100, 100),
//                0.0f, 0.0f, new Color(150, 64, 64));
//        renderer2.setSeriesPaint(1, gp1);
//        plot.setRenderer(1,renderer2);
        
        //45deg. nomination
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
	 * @author langr
	 * 25.03.2009, 09:52:36
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		
		moDataset = new DefaultCategoryDataset();

		
		for(clsDriveMesh oDriveTP : moE05AffectsForDrives.moDriveList) {
			clsDriveMesh oDriveMeshLife = oDriveTP; 
			clsDriveMesh oDriveMeshDeath = moE05AffectsForDrives.moDriveList.get(moE05AffectsForDrives.moDriveList.indexOf(oDriveTP)+1);

			moDataset.addValue( oDriveMeshLife.getPleasure(), "Drive (Live)", oDriveMeshLife.getMoContent());
			moDataset.addValue( oDriveMeshDeath.getPleasure(), "Drive (Death)", oDriveMeshDeath.getMoContent());
			
			if(moE05AffectsForDrives.moDriveList.indexOf(oDriveTP)+1 == moE05AffectsForDrives.moDriveList.size()-1){
				break;
			}
		}
		
		moChartPanel.getChart().getCategoryPlot().setDataset(moDataset);
		
		this.repaint();
	}
	
}