/**
 * CHANGELOG
 *
 * Sep 4, 2012 herret - File created
 *
 */
package mind.autocreated;

import inspector.interfaces.itfInspectorAreaChart;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;


import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 *  
 * 
 * @author herret
 * Sep 4, 2012, 9:35:36 AM
 * 
 */
public class cls_AreaChartInspector extends cls_AbstractChartInspector {

	
	private static final long serialVersionUID = 8474252951041507982L;
	/* (non-Javadoc)
	 *
	 * @since Sep 4, 2012 9:35:37 AM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	protected itfInspectorAreaChart moContainer;
	protected DefaultCategoryDataset moDataset;
	
	public cls_AreaChartInspector(itfInspectorAreaChart poObject){
		super(poObject.getAreaChartTitle());
		moContainer = poObject;
		
		moChartPanel=createPanel();
		add(moChartPanel);
	}
	
  @Override
	protected ChartPanel initChart() {

		  
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
	    	chart.setBackgroundPaint(Color.white); // background of the outside-panel
	  
	    	
	    	
	    	ChartPanel oChartPanel = new ChartPanel(chart);
	        moChartPanel = oChartPanel;
	        oChartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
	        chart.setTitle(moChartName);
	        CategoryPlot plot =chart.getCategoryPlot();
	        NumberAxis iAxis = new NumberAxis();
	        iAxis.setAutoRange(false);
	        iAxis.setRange(0.0,1.0);
	        plot.setRangeAxis(iAxis);
	        
	        plot.setBackgroundPaint(Color.white);
	        
	        plot.setForegroundAlpha(0.5f);

	    	return oChartPanel;
		}

	  
	    protected DefaultCategoryDataset createDataset() {
	    	
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
	    
	    
	    
	    @Override
		protected void updateDataset(){
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getAreaChartData();
	    	//column or row size of given data != column or row size of actual dataset
	    	//->clear dataset and crate an new on out of given data
	    	if(moDataset.getColumnCount()!=iContainer.get(0).size() || moDataset.getRowCount()!=iContainer.size()){
	    		moDataset.clear();
	    		DefaultCategoryDataset iDataset = createDataset();
	    		for (int i= 0; i<iDataset.getRowCount();i++){
	    			for (int j=0; j<iDataset.getColumnCount();j++){
	    				moDataset.addValue(iDataset.getValue(i, j), iDataset.getRowKey(i), iDataset.getColumnKey(j));
	    			}
	    		}
	    	}
	    	else{  
			   for(int i =0 ;i< iContainer.size();i++){
		    		for(int j=0;j<iContainer.get(0).size();j++){
		    			if(!moDataset.getValue(moContainer.getAreaChartAreaCaptions().get(i), moContainer.getAreaChartColumnCaptions().get(j)).equals(iContainer.get(i).get(j))){
		    				moDataset.setValue(iContainer.get(i).get(j), moContainer.getAreaChartAreaCaptions().get(i), moContainer.getAreaChartColumnCaptions().get(j));
		    			}
		    			//poDataset.addValue(iContainer.get(i).get(j), moContainer.getAreaChartAreaCaptions().get(i), moContainer.getAreaChartColumnCaptions().get(j));
		    		}
		    		
			   }
	    	}
	    }

}
