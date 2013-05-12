/**
 * CHANGELOG
 *
 * Aug 30, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.Color;
import java.util.ArrayList;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;


import pa._v38.interfaces.itfInspectorSpiderWebChart;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Aug 30, 2012, 9:10:21 AM
 * 
 */
public class cls_SpiderWebChartInspector extends cls_AbstractChartInspector {

	
	private static final long serialVersionUID = 7406481565814924483L;

	private itfInspectorSpiderWebChart moContainer;
	private DefaultCategoryDataset moDataset;
	
	
	public cls_SpiderWebChartInspector(itfInspectorSpiderWebChart poObject){
		super(poObject.getSpiderWebChartTitle());
		moContainer = poObject;
		moDataset=createDataset();
		
		moChartPanel= createPanel();
		add(moChartPanel);
		
	}
 
    
    @Override
	protected ChartPanel initChart() {
    	
    	moDataset = createDataset();

        SpiderWebPlot spiderWebPlot = new SpiderWebPlot(moDataset);

        spiderWebPlot.setBackgroundPaint(new Color(255, 255, 255));
        spiderWebPlot.setSeriesOutlinePaint(new Color(255, 255, 255));
        
        spiderWebPlot.setStartAngle(moContainer.getSpiderChartStartingAngle());
        spiderWebPlot.setInteriorGap(0.3);
        
        // create the chart and pack it onto the panel
        JFreeChart chart = new JFreeChart(moChartName, TextTitle.DEFAULT_FONT, spiderWebPlot, false);        
    	chart.setBackgroundPaint(Color.LIGHT_GRAY); // background of the outside-panel
    	ChartPanel oChartPanel = new ChartPanel(chart);
        moChartPanel = oChartPanel;
        oChartPanel.setPreferredSize(new java.awt.Dimension(700, 700));
        
    	return oChartPanel;
	}
    
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<String> oCaptions = moContainer.getSpiderChartCaptions();
		
		for (int i=0; i< oCaptions.size();i++){
			result.addValue(moContainer.getSpiderChartData().get(i), "normvalue", oCaptions.get(i));
		}

        
        return result;
    }

	@Override
	protected void updateDataset() {
		((SpiderWebPlot)moChartPanel.getChart().getPlot()).setDataset(createDataset());	
	}
    

}
