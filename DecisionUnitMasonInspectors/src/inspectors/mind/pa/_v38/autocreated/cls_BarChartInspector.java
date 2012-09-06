/**
 * CHANGELOG
 *
 * Sep 5, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import pa._v38.interfaces.itfInspectorBarChart;

import sim.portrayal.Inspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 5, 2012, 11:23:21 AM
 * 
 */
public class cls_BarChartInspector extends Inspector {

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:23:21 AM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	private itfInspectorBarChart moContainer;
	private String moChartName;
	private DefaultCategoryDataset moDataset;
	private boolean created =false;
	
	public cls_BarChartInspector(itfInspectorBarChart poObject){
		this.moContainer = poObject;
		this.moChartName = poObject.getBarChartTitle();
		
		createPanel();
	}
	
	@Override
	public void updateInspector() {
		updateDataset(moDataset);
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
	        JFreeChart chart = ChartFactory.createBarChart(
	        		moContainer.getBarChartTitle(), 
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
	        oChartPanel.setPreferredSize(new java.awt.Dimension(720, 400));
	        chart.setTitle(moChartName);
	        CategoryPlot plot =chart.getCategoryPlot();
	        NumberAxis iAxis = new NumberAxis();
	        iAxis.setAutoRange(false);
	        iAxis.setRange(0.0,1.0);
	        plot.setRangeAxis(iAxis);
	        plot.getDomainAxis().setTickLabelFont(new Font("new",1,8));
			
	        
	        plot.setBackgroundPaint(Color.white);
	       

	    	return oChartPanel;
		}

	  
	    private DefaultCategoryDataset createDataset() {
	    	
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getBarChartData();
		    double[][] data = new double[iContainer.size()][iContainer.get(0).size()];
	    	
	    	
		   for(int i =0 ;i< iContainer.size();i++){
	    		for(int j=0;j<iContainer.get(0).size();j++){
	    			data[i][j]=iContainer.get(i).get(j);
	    		}
	    		
		   }
		   String[] AreaCaption = new String[moContainer.getBarChartCategoryCaptions().size()];
		   for(int i = 0; i<moContainer.getBarChartCategoryCaptions().size();i++){
			   AreaCaption [i]= moContainer.getBarChartCategoryCaptions().get(i);
		   }
		   String[] ColumnCaption = new String[moContainer.getBarChartColumnCaptions().size()];
		   for(int i = 0; i<moContainer.getBarChartColumnCaptions().size();i++){
			   ColumnCaption [i]= moContainer.getBarChartColumnCaptions().get(i);
		   }
	        return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(AreaCaption, ColumnCaption, data);
	        
	    }
	    
	    private DefaultCategoryDataset updateDataset(DefaultCategoryDataset poDataset){
	    	//poDataset.clear();
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getBarChartData();
	    	if(poDataset.getColumnCount()!=iContainer.get(0).size() || poDataset.getRowCount()!=iContainer.size()){
	    		poDataset.clear();
	    		DefaultCategoryDataset iDataset = createDataset();
	    		for (int i= 0; i<iDataset.getRowCount();i++){
	    			for (int j=0; j<iDataset.getColumnCount();j++){
	    				poDataset.addValue(iDataset.getValue(i, j), iDataset.getRowKey(i), iDataset.getColumnKey(j));
	    			}
	    		}
	    	}else{   	

			   for(int i =0 ;i< iContainer.size();i++){
		    		for(int j=0;j<iContainer.get(0).size();j++){
		    			if(!poDataset.getValue(moContainer.getBarChartCategoryCaptions().get(i), moContainer.getBarChartColumnCaptions().get(j)).equals(iContainer.get(i).get(j))){
		    				poDataset.setValue(iContainer.get(i).get(j), moContainer.getBarChartCategoryCaptions().get(i), moContainer.getBarChartColumnCaptions().get(j));
		    			}
		    			//poDataset.addValue(iContainer.get(i).get(j), moContainer.getBarChartCategoryCaptions().get(i), moContainer.getBarChartColumnCaptions().get(j));
		    		}
		    		
			   }
	    	}
	    	return poDataset;
	    }

}

