/**
 * CHANGELOG
 *
 * Aug 30, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


import pa._v38.interfaces.itfInspectorSpiderWebChart;

import sim.portrayal.Inspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Aug 30, 2012, 9:10:21 AM
 * 
 */
public class cls_SpiderWebChartInspector extends Inspector {

	
	private static final long serialVersionUID = 7406481565814924483L;

	private String moChartName;
	private itfInspectorSpiderWebChart moContainer;
	private ChartPanel moChartPanel;
	/* (non-Javadoc)
	 *
	 * @since Aug 30, 2012 9:10:21 AM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	
	public cls_SpiderWebChartInspector(itfInspectorSpiderWebChart poObject){

		moChartName = poObject.getSpiderWebChartTitle();
		moContainer = poObject;
		
		create();
		
	}
	@Override
	public void updateInspector() {

		updateData();
	}
	
	private void updateData(){
		CategoryDataset dataset = createDataset();
        ((SpiderWebPlot) moChartPanel.getChart().getPlot()).setDataset(dataset);
        this.repaint();
	}
	
    private void create()  {
    	createPanel();
    }
    
	
    protected void createPanel() {
    	ChartPanel oChartPanel = initChart(moChartName) ;
    	oChartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
    	add(oChartPanel);
    	
		setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    
    private ChartPanel initChart(String poChartName) {
    	
    	CategoryDataset dataset = createDataset();

        SpiderWebPlot spiderWebPlot = new SpiderWebPlot((CategoryDataset)dataset);

        spiderWebPlot.setBackgroundPaint(new Color(255, 255, 255));
        spiderWebPlot.setSeriesOutlinePaint(new Color(255, 255, 255));
        
        spiderWebPlot.setStartAngle(moContainer.getSpiderChartStartingAngle());
        spiderWebPlot.setInteriorGap(0.3);
        
        // create the chart and pack it onto the panel
        JFreeChart chart = new JFreeChart(poChartName, TextTitle.DEFAULT_FONT, spiderWebPlot, false);        
    	chart.setBackgroundPaint(Color.LIGHT_GRAY); // background of the outside-panel
    	ChartPanel oChartPanel = new ChartPanel(chart);
        moChartPanel = oChartPanel;
        oChartPanel.setPreferredSize(new java.awt.Dimension(700, 700));
        
    	return oChartPanel;
	}
    
    private CategoryDataset createDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<String> oCaptions = moContainer.getSpiderChartCaptions();
		
		for (int i=0; i< oCaptions.size();i++){
			result.addValue(moContainer.getSpiderChartData().get(i), "normvalue", oCaptions.get(i));
		}

        
        return result;
    }
    

}
