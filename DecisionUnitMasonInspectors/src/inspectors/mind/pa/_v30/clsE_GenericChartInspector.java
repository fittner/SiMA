/**
 * clsE_GenericChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 */
package inspectors.mind.pa._v30;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import pa.interfaces._v30.itfInspectorTimeChart;
import sim.portrayal.Inspector;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 * 
 */
public abstract class clsE_GenericChartInspector extends Inspector {
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 17:53:49
	 */
	private static final long serialVersionUID = 3125332229433964204L;

	protected final static int mnHistoryLength = 200;
	protected final static int mnWidth = 600;
	protected final static int mnHeight = 400;
	protected final int mnOffset;
	
	protected itfInspectorTimeChart moTimeingContainer;
	protected ArrayList<XYSeries> moValueHistory;
	protected long moCurrentTime;

	
    public clsE_GenericChartInspector(
            itfInspectorTimeChart poTimingContainer,
            String poYAxisCaption,
            String poChartName,
            int pnOffset)
    {
    	super();
    	moTimeingContainer= poTimingContainer;
    	moCurrentTime = 0;
    	mnOffset = pnOffset;
    	
    	ChartPanel oChartPanel = initChart(poChartName,  createDataset(), 
    			"Steps", poYAxisCaption, mnWidth, mnHeight);
    	add(oChartPanel);
    	
		setLayout(new FlowLayout(FlowLayout.LEFT));
    }
  
    
    protected XYSeriesCollection createDataset() {
    	XYSeriesCollection poDataset = new XYSeriesCollection();
		
		ArrayList<String> oCaptions = moTimeingContainer.getTimeChartCaptions();
		ArrayList<Double> oValues = moTimeingContainer.getTimeChartData();
		
		moValueHistory = new ArrayList<XYSeries>(oValues.size());
		int nOffset = 0;
		
		for (int i=0; i<oCaptions.size(); i++) {
			XYSeries oTemp = new XYSeries( oCaptions.get(i) );
			oTemp.setMaximumItemCount( mnHistoryLength );
			oTemp.add(moCurrentTime, oValues.get(i) + nOffset );
			moValueHistory.add(oTemp);
			poDataset.addSeries(oTemp);			
			nOffset += mnOffset;
		}    	
		
		return poDataset;
    }
    
    protected void customizePlot(XYPlot plot) {
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setBackgroundPaint(Color.white);
     
     // set line colors
        ArrayList<Color> oColors = getColorList();
        for (int i=0; i<moValueHistory.size(); i++) {
        	 plot.getRenderer().setSeriesPaint(i+2, oColors.get(i));
        }    	
    }
    
    private ChartPanel initChart(String poChartName, XYSeriesCollection poDataset, 
    		String poXAxisCaption, String poYAxisCaption, int pnWidth, int pnHeight) {
    	
        JFreeChart oChartPanel = ChartFactory.createXYLineChart(
                poChartName,     // chart title
                poXAxisCaption,               // domain axis label
                poYAxisCaption,                  // range axis label
                poDataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
            );
        
     // set the background color for the chart...
        oChartPanel.setBackgroundPaint(Color.white);
   
     // get a reference to the plot for further customisation...
        XYPlot plot = (XYPlot) oChartPanel.getPlot();
        customizePlot(plot);
         
     // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        ChartPanel poChartPanel = new ChartPanel(oChartPanel);
        poChartPanel.setFillZoomRectangle(true);
        poChartPanel.setPreferredSize(new Dimension(pnWidth, pnHeight));
        
        return poChartPanel;
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
		moCurrentTime += 1;
		
		updateData();
		
		this.repaint();
	}    
	
	protected void updateData() {
		ArrayList<Double> oTimingData = moTimeingContainer.getTimeChartData();
		
		int nOffset=0;
		
		for (int i=0; i<oTimingData.size(); i++) {
			((XYSeries)moValueHistory.get(i)).add(moCurrentTime, oTimingData.get(i) + nOffset);
			nOffset += mnOffset;
		}
	}
    
    protected ArrayList<Color> getColorList() {
    	ArrayList<Color> oColors = new ArrayList<Color>();
    	
    	oColors.add(Color.black);
    	oColors.add(Color.red);
    	oColors.add(Color.orange);
    	oColors.add(Color.blue);
    	oColors.add(Color.cyan);
    	oColors.add(Color.green);
    	oColors.add(Color.magenta);
    	oColors.add(Color.pink);
    	oColors.add(Color.yellow);
    	oColors.add(new Color(204, 51, 102));
    	oColors.add(new Color(153, 51, 0));
    	oColors.add(new Color(255, 153, 0));
        
        return oColors;
    } 	
}
