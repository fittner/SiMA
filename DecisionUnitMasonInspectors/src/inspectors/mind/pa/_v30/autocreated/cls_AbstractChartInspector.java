/**
 * clsE_GenericChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 */
package inspectors.mind.pa._v30.autocreated;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import pa._v30.interfaces.itfInspectorTimeChartBase;
import pa._v30.interfaces.itfInterfaceTimeChartHistory;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;
import statictools.clsSimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 * 
 */
public abstract class cls_AbstractChartInspector extends Inspector {
	
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
	
	protected itfInspectorTimeChartBase moTimeingContainer;
	protected ArrayList<XYSeries> moValueHistory;
	protected long mnCurrentTime;

	private String moChartName;
	private String moYAxisCaption;
	
    public cls_AbstractChartInspector(
    		itfInspectorTimeChartBase poTimingContainer,
            String poYAxisCaption,
            String poChartName,
            int pnOffset)
    {
    	moTimeingContainer= poTimingContainer;
    	mnCurrentTime = clsSimState.getSteps();
    	mnOffset = pnOffset;
    	
    	moChartName = poChartName;
    	moYAxisCaption = poYAxisCaption;
    	
    	create();
    }
    
    private void create()  {
    	createPanel();
    	
    	if (moTimeingContainer instanceof itfInterfaceTimeChartHistory) {
    		fetchDataFromHistory();
    	}
    }
    
    private void recreate() {
    	removeAll();
    	create();
    }
    
    protected void fetchDataFromHistory() {
    	TreeMap<Long, ArrayList<Double>> oData = ((itfInterfaceTimeChartHistory)moTimeingContainer).getTimeChartHistory();
    	
    	for (Iterator<XYSeries> it=moValueHistory.iterator();it.hasNext();) {
    		XYSeries oSeries = it.next();
    		oSeries.clear();
    	}
    	
    	for (Iterator<Map.Entry<Long, ArrayList<Double>>> it = oData.entrySet().iterator();it.hasNext();) {
    		Map.Entry<Long, ArrayList<Double>> oLine = it.next();
    		long x = oLine.getKey();
    		ArrayList<Double> ys = oLine.getValue();
    		int i=0;
    		int nOffset = 0;
    		for (Iterator<XYSeries> it2=moValueHistory.iterator();it2.hasNext();) {
    			XYSeries oS = it2.next();
        		double r = (ys.get(i) + nOffset);
        		oS.add(x, r);
        		nOffset += mnOffset;
        		i++;
        	}
    	}
    	
    }
  
    protected void createPanel() {
    	ChartPanel oChartPanel = initChart(moChartName,  createDataset(), 
    			"Steps", moYAxisCaption, mnWidth, mnHeight);
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
			oTemp.add(mnCurrentTime, oValues.get(i) + nOffset );
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
		mnCurrentTime = clsSimState.getSteps();
		
		updateData();
		
		this.repaint();
	}    
	
	protected void updateData() {
		ArrayList<Double> oTimingData = moTimeingContainer.getTimeChartData();
		
		int nOffset=0;
		
		try {
			for (int i=0; i<oTimingData.size(); i++) {
				((XYSeries)moValueHistory.get(i)).add(mnCurrentTime, oTimingData.get(i) + nOffset);
				nOffset += mnOffset;
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
			System.out.println("cls_AbstractChartInspector.updateData: RESET CHART PANEL!");
			recreate();
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
