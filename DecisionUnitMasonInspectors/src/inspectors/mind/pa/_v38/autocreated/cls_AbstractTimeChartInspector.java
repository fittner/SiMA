/**
 * clsE_GenericChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.Color;
import java.awt.Dimension;
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
import pa._v38.interfaces.itfInspectorTimeChartBase;
import pa._v38.interfaces.itfInterfaceTimeChartHistory;
import pa._v38.tools.clsPair;
import statictools.clsExceptionUtils;
import statictools.clsSimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 17:25:08
 * 
 */
public abstract class cls_AbstractTimeChartInspector extends cls_AbstractChartInspector {
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 17:53:49
	 */
	private static final long serialVersionUID = 3125332229433964204L;

	protected final int mnHistoryLength;
	protected int mnWidth;
	protected int mnHeight;
	protected int mnOffset;
	protected XYSeriesCollection moDataset;
	
	protected itfInspectorTimeChartBase moTimeingContainer;
	protected ArrayList<XYSeries> moValueHistory;
	protected long mnCurrentTime;

	private String moYAxisCaption;


	public cls_AbstractTimeChartInspector(
    		itfInspectorTimeChartBase poTimingContainer,
            String poYAxisCaption,
            String poChartName,
            int pnOffset)
    {
		super(poChartName);
    	mnHistoryLength = 200;
    	mnWidth = 600;
    	mnHeight = 400;
    	moTimeingContainer= poTimingContainer;
    	mnCurrentTime = clsSimState.getSteps();
    	mnOffset = pnOffset;
    	
    	moYAxisCaption = poYAxisCaption;

    	moDataset=createDataset();
    	moChartPanel=create();
    	add(moChartPanel);
    	
    }
    

	public cls_AbstractTimeChartInspector(
    		itfInspectorTimeChartBase poTimingContainer,
            String poYAxisCaption,
            String poChartName,
            int pnOffset, int pnHistoryLength, int pnWidth, int pnHeight)
    {
		super(poChartName);
    	mnHistoryLength = pnHistoryLength;
    	mnWidth = pnWidth;
    	mnHeight = pnHeight;
    	moTimeingContainer= poTimingContainer;
    	mnCurrentTime = clsSimState.getSteps();
    	mnOffset = pnOffset;
    	
    	moYAxisCaption = poYAxisCaption;

    	moDataset=createDataset();
    	moChartPanel=create();
    	add(moChartPanel);
    }
	
    /**
	 * @since Oct 10, 2012 10:42:26 AM
	 * 
	 * @param showRangeLabel the showRangeLabel to set
	 */
	public void setShowRangeLabel(boolean showRangeLabel) {
		((XYPlot) moChartPanel.getChart().getPlot()).getRangeAxis().setTickLabelsVisible(showRangeLabel);
	}

    private ChartPanel create()  {
    	ChartPanel poChartPanel =createPanel();
    	
    	if (moTimeingContainer instanceof itfInterfaceTimeChartHistory) {
    		fetchDataFromHistory();
    	}
    	return poChartPanel;
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
    
 //   private ChartPanel initChart(String poChartName, XYSeriesCollection poDataset, 
   // 		String poXAxisCaption, String poYAxisCaption, int pnWidth, int pnHeight) {
    @Override
	protected ChartPanel initChart(){	
        JFreeChart oChartPanel = ChartFactory.createXYLineChart(
                moChartName,     // chart title
                "Steps",               // domain axis label
                moYAxisCaption,                  // range axis label
                moDataset,                  // data
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
        poChartPanel.setPreferredSize(new Dimension(mnWidth, mnHeight));
        

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
		
		updateDataset();
		
		this.repaint();
	}    
	
	@Override
	protected void updateDataset() {
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
    	oColors.add(Color.darkGray);
    	oColors.add(new Color(210, 55, 44));
    	oColors.add(new Color(90, 153, 5));
    	oColors.add(new Color(255, 103, 5));
    	oColors.add(new Color(68, 5, 255));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
        
        return oColors;
    }

	public void setChartSize(Dimension x){
    	moChartPanel.setPreferredSize(x);
    }
    
    
    
    
}
