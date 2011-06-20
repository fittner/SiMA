/**
 * clsE26DecisionCalculation.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author andi
 * 17.09.2009, 16:48:04
 */
package inspectors.mind.pa._v19;

import java.awt.BorderLayout;
//import java.awt.GradientPaint;
//import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.plot.SpiderWebPlot;

import pa._v19.modules.E26_DecisionMaking;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

import java.awt.Color;


/**
 * 
 * 
 * @author andi
 * 17.09.2009, 16:48:04
 * 
 */
@Deprecated
public class clsE26DecisionCalculation extends Inspector {

	private static final long serialVersionUID = 2391511521426669313L;
	
	public Inspector moOriginalInspector;
//	private E26_DecisionMaking moE26;  //  - add real descion-making values 
	
	private ChartPanel moChartPanel;
//	private DefaultCategoryDataset moDatasetNormValue;
	
	// dummy values for chart-demo
	private int iDummy;
	private double dDummyValue;

	
    public clsE26DecisionCalculation(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E26_DecisionMaking poNeuroNeeds)
    {
		super();
		
		moOriginalInspector = originalInspector;
//		moE26= poNeuroNeeds; //  - add real descion-making values
		
		initBrainChart();
		
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
    	
    	// init demo-dummys
    	iDummy = 0;
    	dDummyValue = 0.1;
		
    }
    
    /**
     * 
     *  initializes the plot
     *
     * @author andi
     * 24.09.2009, 13:14:41
     *
     */
    private void initBrainChart() {
    	// get the norm-dataset
    	final CategoryDataset dataset = createNormDataset();

        final SpiderWebPlot spiderwebplot = new SpiderWebPlot((CategoryDataset)dataset);
        //spiderwebplot.setStartAngle(45D);
        spiderwebplot.setStartAngle(0);
        spiderwebplot.setInteriorGap(0.40000000000000002D);
        
        // create the chart and pack it onto the panel
        final JFreeChart chart = new JFreeChart("Brain Information", TextTitle.DEFAULT_FONT, spiderwebplot, false);        
    	chart.setBackgroundPaint(Color.LIGHT_GRAY); // background of the outside-panel
        moChartPanel = new ChartPanel(chart);
        moChartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        
    }
    
    /**
     * 
     * returns the norm-dataset (value 1 for every axis) for the decision calculation spiderplot-graphic
     *
     * @author andi
     * 24.09.2009, 13:13:25
     *
     * @return
     */
    private CategoryDataset createNormDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        
        result.addValue(1, "normvalue", "Psy 1");
        result.addValue(1, "normvalue", "Psy 2");
        result.addValue(1, "normvalue", "Psy 3");
        result.addValue(1, "normvalue", "Psy 4");
        result.addValue(1, "normvalue", "Psy 5");
        
        return result;
    }
    
	
	/* (non-Javadoc)
	 *
	 * @author andi
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {

		// update the chart with a dummy-value
		iDummy++;
		if (iDummy > 100) {
			iDummy = 1;
			dDummyValue += 0.5;
		}
		if (dDummyValue > 2) dDummyValue = 0.1;

		// always display the norm-values
        DefaultCategoryDataset result = (DefaultCategoryDataset) createNormDataset();
        
        result.addValue(dDummyValue, "current", "Psy 1");
        result.addValue(dDummyValue, "current", "Psy 2");
        result.addValue(dDummyValue, "current", "Psy 3");
        result.addValue(dDummyValue, "current", "Psy 4");
        result.addValue(dDummyValue, "current", "Psy 5");
		
//        SpiderWebPlot spiderwebplot = (SpiderWebPlot) moChartPanel.getChart().getPlot();
//        spiderwebplot.setDataset(result);

        ((SpiderWebPlot) moChartPanel.getChart().getPlot()).setDataset(result);
        this.repaint();
	}
}
