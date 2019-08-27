/**
 * clsE_ChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38
 * 
 * @author deutsch
 * 15.04.2011, 19:01:42
 */
package mind.autocreated;

import inspector.interfaces.itfInspectorGenericTimeChart;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.04.2011, 19:01:42
 * 
 */
public class cls_GenericTimeChartInspector extends cls_AbstractTimeChartInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 19:01:50
	 */
	private static final long serialVersionUID = -2224815255678859538L;
	private final double mrLower; 
	private final double mrUpper;
	protected XYSeries moUpperLimit;
	protected XYSeries moLowerLimit;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 15.04.2011, 19:01:47
	 *
	 * @param oModule
	 * @param poYAxisCaption
	 * @param prLowerLimit
	 * @param prUpperLimit
	 * @param poChartName
	 */
	public cls_GenericTimeChartInspector(itfInspectorGenericTimeChart poObject) {
		super(poObject, poObject.getTimeChartAxis(), poObject.getTimeChartTitle(), 0);
		
		mrLower = poObject.getTimeChartLowerLimit();
		mrUpper = poObject.getTimeChartUpperLimit();
	}
	
	public cls_GenericTimeChartInspector(itfInspectorGenericTimeChart poObject, int pnHistoryLength, int pnWidth, int pnHeight) {
		super(poObject, poObject.getTimeChartAxis(), poObject.getTimeChartTitle(), 0, pnHistoryLength, pnWidth, pnHeight);
		
		mrLower = poObject.getTimeChartLowerLimit();
		mrUpper = poObject.getTimeChartUpperLimit();
	}
	
	
    private void addLimitLines(XYSeriesCollection poDataset) {
    	//tweak to have a nice static upper and lower limit 
    	//moUpperLimit = new XYSeries("");
    	//moUpperLimit.setMaximumItemCount(mnHistoryLength);
    	//moUpperLimit.add(mnCurrentTime, mrUpper);
    	//poDataset.addSeries(moUpperLimit);    
		
    	moLowerLimit = new XYSeries("");
    	moLowerLimit.setMaximumItemCount(mnHistoryLength);
    	moLowerLimit.add(mnCurrentTime, mrLower);
    	//poDataset.addSeries(moLowerLimit);  
    }
    
    @Override
    protected XYSeriesCollection createDataset() {
    	XYSeriesCollection poDataset = super.createDataset();
		
    	addLimitLines(poDataset);
	
		return poDataset;
    }    
    
    protected void updateLimitLines() {
		moLowerLimit.add(mnCurrentTime, mrLower );
		//moUpperLimit.add(mnCurrentTime, mrUpper );    	
    }	
    
    @Override
	protected void updateDataset() {
    	super.updateDataset();
		updateLimitLines();
    }

    @Override
    protected void customizePlot(XYPlot plot) {
        super.customizePlot(plot);
        
        plot.getRenderer().setSeriesPaint(moValueHistory.size(), Color.white);
        plot.getRenderer().setSeriesPaint(moValueHistory.size()+1, Color.white);        
    }
    
    @Override
	protected ArrayList<Color> getColorList() {
    	ArrayList<Color> oColors = new ArrayList<Color>();
    	
    	oColors.add(Color.red);
    	oColors.add(Color.black);
    	oColors.add(Color.orange);
    	oColors.add(Color.blue);
    	oColors.add(Color.cyan);
    	oColors.add(Color.green);
    	oColors.add(Color.magenta);
    	oColors.add(Color.pink);
    	oColors.add(Color.darkGray);
    	oColors.add(new Color(204, 51, 102));
    	oColors.add(new Color(153, 51, 0));
    	oColors.add(new Color(255, 153, 0));
    	oColors.add(Color.yellow);
    	oColors.add(new Color(210, 55, 44));
    	oColors.add(new Color(90, 153, 5));
    	oColors.add(new Color(255, 103, 5));
    	oColors.add(new Color(68, 5, 255));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
    	oColors.add(new Color(0, 0, 0));
        
        return oColors;
    }
}
