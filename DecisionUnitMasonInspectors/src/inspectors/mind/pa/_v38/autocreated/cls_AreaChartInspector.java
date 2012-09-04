/**
 * CHANGELOG
 *
 * Sep 4, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;


import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.data.general.DatasetUtilities;

import pa._v38.interfaces.itfInspectorAreaChart;

import sim.portrayal.Inspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 4, 2012, 9:35:36 AM
 * 
 */
public class cls_AreaChartInspector extends Inspector {

	
	private static final long serialVersionUID = 8474252951041507982L;
	/* (non-Javadoc)
	 *
	 * @since Sep 4, 2012 9:35:37 AM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	private itfInspectorAreaChart moContainer;
	private String moChartName;
	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDataset;
	
	public cls_AreaChartInspector(itfInspectorAreaChart poObject){
		this.moContainer = poObject;
		this.moChartName =poObject.getAreaChartTitle();
		
		createPanel();
	}
	
	@Override
	public void updateInspector() {
		// TODO (herret) - Auto-generated method stub
		
		updateData();

	}
	
	//to do
	private void updateData(){

		updateDataset(moDataset);
		moChartPanel.repaint();
		//((SpiderWebPlot) moChartPanel.getChart().getPlot()).setDataset(dataset);
        this.repaint();
	}

	   protected void createPanel() {
	    	ChartPanel oChartPanel = initChart(moChartName) ;
	    	add(oChartPanel);
	    	
			setLayout(new FlowLayout(FlowLayout.LEFT));
	    }
 
	  private ChartPanel initChart(String poChartName) {

		  
	    	DefaultCategoryDataset dataset = createDataset();
	        moDataset = dataset;
	        // create the chart and pack it onto the panel
	        JFreeChart chart = ChartFactory.createAreaChart(
	        		moContainer.getAreaChartTitle(), 
	        		"Category", 
	        		"Value", 
	        		dataset, 
	        		PlotOrientation.VERTICAL, 
	        		true, 
	        		true, 
	        		false
	        );
	        //place for optical improvements of the chart
	    	chart.setBackgroundPaint(Color.LIGHT_GRAY); // background of the outside-panel
	    	
	    	
	    	ChartPanel oChartPanel = new ChartPanel(chart);
	        moChartPanel = oChartPanel;
	        oChartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
	        chart.setTitle(moChartName);
	        CategoryPlot plot =chart.getCategoryPlot();
	        NumberAxis iAxis = new NumberAxis();
	        iAxis.setAutoRange(false);
	        iAxis.setRange(0.0,1.0);
	        plot.setRangeAxis(iAxis);
	        
	        plot.setForegroundAlpha(0.5f);

	    	return oChartPanel;
		}

	  
	    private DefaultCategoryDataset createDataset() {
	    	
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getAreaChartData();
		    double[][] data = new double[iContainer.size()][iContainer.get(0).size()];
	    	
	    	
		   for(int i =0 ;i< iContainer.size();i++){
	    		for(int j=0;j<iContainer.get(0).size();j++){
	    			data[i][j]=iContainer.get(i).get(j);
	    		}
	    		
		   }
		   String[] AreaCaption = new String[moContainer.getAreaChartAreaCaptions().size()];
		   for(int i = 0; i<moContainer.getAreaChartAreaCaptions().size();i++){
			   AreaCaption [i]= moContainer.getAreaChartAreaCaptions().get(i);
		   }
		   String[] ColumnCaption = new String[moContainer.getAreaChartColumnCaptions().size()];
		   for(int i = 0; i<moContainer.getAreaChartColumnCaptions().size();i++){
			   ColumnCaption [i]= moContainer.getAreaChartColumnCaptions().get(i);
		   }
	        return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(AreaCaption, ColumnCaption, data);
	        
	    }
	    
	    private DefaultCategoryDataset updateDataset(DefaultCategoryDataset poDataset){
	    	poDataset.clear();
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getAreaChartData();
			   for(int i =0 ;i< iContainer.size();i++){
		    		for(int j=0;j<iContainer.get(0).size();j++){
		    			poDataset.addValue(iContainer.get(i).get(j), moContainer.getAreaChartAreaCaptions().get(i), moContainer.getAreaChartColumnCaptions().get(j));
		    		}
		    		
			   }
	    	return poDataset;
	    }

}
