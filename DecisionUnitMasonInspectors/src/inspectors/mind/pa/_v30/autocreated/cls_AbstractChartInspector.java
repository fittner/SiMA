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
import pa._v30.tools.clsPair;
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

	protected final int mnHistoryLength;
	protected final int mnWidth;
	protected final int mnHeight;
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
    	mnHistoryLength = 200;
    	mnWidth = 600;
    	mnHeight = 400;
    	moTimeingContainer= poTimingContainer;
    	mnCurrentTime = clsSimState.getSteps();
    	mnOffset = pnOffset;
    	
    	moChartName = poChartName;
    	moYAxisCaption = poYAxisCaption;

    	create();
    }
    
    public cls_AbstractChartInspector(
    		itfInspectorTimeChartBase poTimingContainer,
            String poYAxisCaption,
            String poChartName,
            int pnOffset, int pnHistoryLength, int pnWidth, int pnHeight)
    {
    	mnHistoryLength = pnHistoryLength;
    	mnWidth = pnWidth;
    	mnHeight = pnHeight;
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
    	ArrayList<clsPair <Long, ArrayList<Double>> > oData = ((itfInterfaceTimeChartHistory)moTimeingContainer).getTimeChartHistory();
    	
    	for (int i=0; i<moValueHistory.size();i++) {
    		XYSeries oSeries = moValueHistory.get(i);
    		oSeries.clear();
    	}
    	
    	int start = oData.size() - mnHistoryLength;
    	if (start < 0) {
    		start = 0;
    	}
    	
    	for (int pos=start; pos<oData.size(); pos++) {
			clsPair <Long, ArrayList<Double>> oLine = oData.get(pos);
    		long x = oLine.a;
    		ArrayList<Double> ys = oLine.b;
    		int i=0;
    		int nOffset = 0;
        	for (int j=0; j<moValueHistory.size();j++) {
        		XYSeries oS = moValueHistory.get(j);
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
			//FIXME (Deutsch): due to some unknown reason, sometimes oTimingData.size is different to moValueHistory.size. workaround for the time being: recreate chart.
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
