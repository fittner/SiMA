/**
 * clsInspectorFastMessengers.java: BW - bw.utils.inspectors.body
 * 
 * @author deutsch
 * 17.09.2009, 10:49:45
 */
package bw.utils.inspectors.body;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import bfg.tools.clsMutableInteger;
import bfg.tools.clsMutableDouble;
import bw.body.internalSystems.clsFastMessengerEntry;
import bw.body.internalSystems.clsFastMessengerKeyTuple;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 17.09.2009, 10:49:45
 * 
 */
public class clsInspectorFastMessengers  extends Inspector{
	/**
	 * DOCUMENT (tobias) - insert description 
	 * 
	 * @author tobias
	 * Jul 31, 2009, 10:41:09 AM
	 */
	private static final long serialVersionUID = 2381501531626669313L;
	
	public sim.portrayal.Inspector moOriginalInspector;
	
	private ArrayList<clsFastMessengerKeyTuple> moFromToMapping; //reference
	private HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>> moTargetList;		 //reference
	
	private TreeMap<Long, ArrayList<clsFastMessengerEntry>> moHistory;
	
	private HashMap<clsFastMessengerKeyTuple, clsMutableInteger> moCountShort;
	private HashMap<clsFastMessengerKeyTuple, clsMutableInteger> moCountMedium;
	private HashMap<clsFastMessengerKeyTuple, clsMutableInteger> moCountLong;
	private HashMap<clsFastMessengerKeyTuple, clsMutableDouble> moActive;
	
	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDatasetActive;
	private DefaultCategoryDataset moDatasetShort;
	private DefaultCategoryDataset moDatasetMedium;
	private DefaultCategoryDataset moDatasetLong;
	
	private static final int mnShortPeriod = 20;
	private static final int mnMediumPeriod = 100;
	private static final int mnLongPeriod = 200;
	
	private static final int mnShortUpdateInterval = 5;	
	private static final int mnMediumUpdateInterval = 10;
	private static final int mnLongUpdateInterval = 20;
    
    public clsInspectorFastMessengers(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsFastMessengerSystem poFastMessengerSystem)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	
    	moFromToMapping = poFastMessengerSystem.getFromToMapping();
    	moTargetList = poFastMessengerSystem.getTargetList();
    	
    	moHistory = new TreeMap<Long, ArrayList<clsFastMessengerEntry>>();
    	moCountShort = new HashMap<clsFastMessengerKeyTuple, clsMutableInteger>();
    	moCountMedium = new HashMap<clsFastMessengerKeyTuple, clsMutableInteger>();
    	moCountLong = new HashMap<clsFastMessengerKeyTuple, clsMutableInteger>();
    	moActive = new HashMap<clsFastMessengerKeyTuple, clsMutableDouble>();
    	
    	updateHistory();
    	initChart();
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
    }

    private long getStep() {
    	return clsSingletonMasonGetter.getSimState().schedule.getSteps();
    }
    
    private ArrayList<clsFastMessengerEntry> getCurrentEntries() {
    	ArrayList<clsFastMessengerEntry> oResult = new ArrayList<clsFastMessengerEntry>();
    	
    	//HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>>
    	for (Map.Entry<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>> target:moTargetList.entrySet()) {
    		for (Map.Entry<eBodyParts, clsFastMessengerEntry> source:target.getValue().entrySet()) {
    			oResult.add(source.getValue());
    		}
    	}
    	
    	return oResult;
    }
    
    private void purgeOldEntries(long currentstep, long maxage) {
    	long minstep = currentstep -  maxage;
    	
    	while (moHistory.size()>0 && moHistory.firstKey().longValue() < minstep) {
    		moHistory.remove(moHistory.firstKey());
    	}
    }
    
    private void updateActive(ArrayList<clsFastMessengerEntry> currentEntries)  {
    	//decrease all former entries
    	for (Map.Entry<clsFastMessengerKeyTuple, clsMutableDouble> entry:moActive.entrySet()) {
    		clsMutableDouble oValue = entry.getValue();
    		if (oValue.doubleValue()>0) {
    			oValue.add(-0.1);
    			if (oValue.doubleValue()<0) {
    				oValue.set(0);
    			}
    		}
    	}
    	
    	//set all current active entries to 1
    	for (clsFastMessengerEntry entry:currentEntries) {
    		clsMutableDouble oValue = moActive.get(entry.getFromTo());
    		if (oValue == null) {
    			moActive.put(entry.getFromTo(), new clsMutableDouble(entry.getIntensity()));
    		} else {
    			oValue.set(entry.getIntensity());
    		}
    	}
    }
    
    private void updateHistory(long step, boolean updateshort, boolean updatemedium, boolean updatelong) {
    	ArrayList<clsFastMessengerEntry> currentEntries = getCurrentEntries();
    	
    	updateActive(currentEntries);
    	
    	moHistory.put(step, currentEntries);
    	purgeOldEntries(step, mnLongPeriod);
    	
    	if (updateshort) {resetCount(moCountShort);}
    	if (updatemedium) {resetCount(moCountMedium);}
    	if (updatelong) {resetCount(moCountLong);}
    	
    	long nMinLongKey = step - mnLongPeriod;
    	long nMinMediumKey = step - mnMediumPeriod;
    	long nMinShortKey = step - mnShortPeriod;
    	
    	Long oCurrentKey = step+1;
    	while ( (oCurrentKey = moHistory.lowerKey(oCurrentKey)) != null) {
    		ArrayList<clsFastMessengerEntry> stepEntries = moHistory.get(oCurrentKey);
    		
    		boolean nShort = updateshort && oCurrentKey.longValue() >= nMinShortKey;
    		boolean nMedium = updatemedium && oCurrentKey.longValue() >= nMinMediumKey;
    		boolean nLong = updatelong && oCurrentKey.longValue() >= nMinLongKey;
    		
    		if (!(nShort || nMedium || nLong)) {
    			break;
    		}
    		
    		for (clsFastMessengerEntry entry:stepEntries) {
    			clsFastMessengerKeyTuple key = entry.getFromTo();
    			if (nShort) {
    				updateCount(moCountShort, key);
    			}
    			if (nMedium) {
    				updateCount(moCountMedium, key);
    			}
    			if (nLong) {
    				updateCount(moCountLong, key);
    			}
    		}
    	}
    }
    
    private void resetCount(HashMap<clsFastMessengerKeyTuple, clsMutableInteger> poCount) {
    	poCount.clear();
    	for (clsFastMessengerKeyTuple key:moFromToMapping) {
    		poCount.put(key, new clsMutableInteger(0));
    	}
    }
    private void updateCount(HashMap<clsFastMessengerKeyTuple, clsMutableInteger> poCount, clsFastMessengerKeyTuple key) {
		clsMutableInteger temp = poCount.get(key);
		temp.inc();
    }
    
    private void updateHistory() {
    	long step =  getStep();
    	boolean updateshort = false;
    	boolean updatemedium = false;
    	boolean updatelong = false;
    	if (step % mnShortUpdateInterval == 0) {
    		updateshort = true;
    	}
    	if (step % mnMediumUpdateInterval == 0) {
    		updatemedium = true;
    	}
    	if (step % mnLongUpdateInterval == 0) {
    		updatelong = true;
    	}
    	updateHistory(step, updateshort, updatemedium, updatelong);
    }
    
    private void fillDataSets() {
		String oShortPeriod = new Integer(mnShortPeriod).toString();
		String oMediumPeriod = new Integer(mnMediumPeriod).toString();
		String oLongPeriod = new Integer(mnLongPeriod).toString();

		for(clsFastMessengerKeyTuple oKey : moFromToMapping ) {
			String oKeyName = oKey.toString();
			
			double rActive = 0;
			try {
				rActive = moActive.get(oKey).doubleValue();
			} catch (java.lang.NullPointerException e) {
				//do nothing
			}
			double rShort = 0;
			try {
				rShort = (double)(moCountShort.get(oKey).intValue()) / (double)mnShortPeriod;
			} catch (java.lang.NullPointerException e) {
				//do nothing
			}
			double rMedium = 0;
			try {
				rMedium = (double)(moCountMedium.get(oKey).intValue()) / (double)mnMediumPeriod;
			} catch (java.lang.NullPointerException e) {
				//do nothing
			}			
			double rLong = 0;
			try {
				rLong = (double)(moCountLong.get(oKey).intValue()) / (double)mnLongPeriod;
			} catch (java.lang.NullPointerException e) {
				//do nothing
			}	
			
			moDatasetActive.addValue( rActive, "Active", oKeyName); 
			moDatasetShort.addValue( rShort, oShortPeriod, oKeyName); 
			moDatasetMedium.addValue( rMedium, oMediumPeriod, oKeyName); 
			moDatasetLong.addValue( rLong, oLongPeriod, oKeyName); 
		}    	
    }
    
	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 21.07.2009, 17:25:04
	 *
	 */
	private void initChart() {
		
		moDatasetActive = new DefaultCategoryDataset();
		moDatasetShort = new DefaultCategoryDataset();
		moDatasetMedium = new DefaultCategoryDataset();
		moDatasetLong = new DefaultCategoryDataset();
		
		fillDataSets();

				
        JFreeChart oChartPanel = ChartFactory.createBarChart(
                "Fast Messenger System",     // chart title
                "Fast Messages",               // domain axis label
                "",                  // range axis label
                moDatasetActive,                  // data
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
        rangeAxis.setUpperBound(5.0); //set the max value for the energy/nutrition

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
        plot.setDataset(1, moDatasetShort);
        plot.setRenderer(1, renderer2);
        
        //adds the upper limits to the chart
        CategoryItemRenderer renderer3 = new LevelRenderer();
        renderer3.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer3.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setDataset(2, moDatasetMedium);
        plot.setRenderer(2, renderer3);
        
        //adds the max value to the chart
        CategoryItemRenderer renderer4 = new LevelRenderer();
        renderer4.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer4.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setDataset(3, moDatasetLong);
        plot.setRenderer(3, renderer4);        
        
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
		updateHistory();

		fillDataSets();
		
		this.repaint();
	}

}
