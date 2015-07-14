/**
 * CHANGELOG
 *
 * 10.07.2015 Kollmann - File created
 *
 */
package mind.autocreated;

import inspector.interfaces.itfInspectorMultipleBarChart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 10.07.2015, 11:34:51
 * 
 */
public class cls_MultipleBarChartsInspector extends cls_AbstractChartInspector {
	private itfInspectorMultipleBarChart moContainer;
	private DefaultCategoryDataset moDataset;
	private String moLabel = null;

	public cls_MultipleBarChartsInspector(itfInspectorMultipleBarChart poObject, String poLabel){
		super(poObject.getBarChartTitle(poLabel));
		moLabel = poLabel;
		moContainer = poObject;
		moDataset = createDataset();
		
		moChartPanel = createPanel();
		add(moChartPanel);
		
		//Kollmann change height/width ratio
		ComponentListener compList = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				double maxWidth= getSize().getHeight()*1/1;
				double maxHeight= getSize().getWidth()*1/1;
				Dimension iDimension = getSize();
				if(iDimension.getHeight() >= maxHeight){
					iDimension.setSize(maxHeight*1/1, maxHeight);
				} else if (iDimension.getWidth() >= maxWidth){
					iDimension.setSize(maxWidth, maxWidth*1/1);
				}
				moChartPanel.setPreferredSize(iDimension);
			}};
		addComponentListener(compList);
	}
	
	@Override
	protected ChartPanel initChart() {
    	DefaultCategoryDataset dataset = createDataset();
        moDataset = dataset;
        // create the chart and pack it onto the panel
        JFreeChart chart = ChartFactory.createBarChart(
        		moContainer.getBarChartTitle(moLabel), 
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
		
//        oChartPanel.setMaximumDrawHeight(1000);
//        oChartPanel.setMaximumDrawWidth(1000);
        oChartPanel.setMinimumDrawWidth(100);
        oChartPanel.setMinimumDrawHeight(200);
        
        plot.setBackgroundPaint(Color.white);

//        BarRenderer renderer = (BarRenderer) plot.getRenderer();
//        renderer.setItemMargin(0.5);
//        plot.(offset);

    	return oChartPanel;
	}

  
    private DefaultCategoryDataset createDataset() {
    	
    	ArrayList<ArrayList<Double>> iContainer = moContainer.getBarChartData(moLabel);
	    double[][] data = new double[iContainer.size()][iContainer.get(0).size()];
    	
    	
	   for(int i =0 ;i< iContainer.size();i++){
    		for(int j=0;j<iContainer.get(0).size();j++){
    			data[i][j]=iContainer.get(i).get(j);
    		}
    		
	   }
	   String[] AreaCaption = new String[moContainer.getBarChartCategoryCaptions(moLabel).size()];
	   for(int i = 0; i<moContainer.getBarChartCategoryCaptions(moLabel).size();i++){
		   AreaCaption [i]= moContainer.getBarChartCategoryCaptions(moLabel).get(i);
	   }
	   String[] ColumnCaption = new String[moContainer.getBarChartColumnCaptions(moLabel).size()];
	   for(int i = 0; i<moContainer.getBarChartColumnCaptions(moLabel).size();i++){
		   ColumnCaption [i]= moContainer.getBarChartColumnCaptions(moLabel).get(i);
	   }
        return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(AreaCaption, ColumnCaption, data);
        
    }
    
    @Override
	protected void updateDataset(){
    	//poDataset.clear();
    	ArrayList<ArrayList<Double>> iContainer = moContainer.getBarChartData(moLabel);
    	if(moDataset.getColumnCount()!=iContainer.get(0).size() || moDataset.getRowCount()!=iContainer.size()){
    		moDataset.clear();
    		DefaultCategoryDataset iDataset = createDataset();
    		for (int i= 0; i<iDataset.getRowCount();i++){
    			for (int j=0; j<iDataset.getColumnCount();j++){
    				moDataset.addValue(iDataset.getValue(i, j), iDataset.getRowKey(i), iDataset.getColumnKey(j));
    			}
    		}
    	}else{   	

		   for(int i =0 ;i< iContainer.size();i++){
	    		for(int j=0;j<iContainer.get(0).size();j++){
	    			if(!moDataset.getValue(moContainer.getBarChartCategoryCaptions(moLabel).get(i), moContainer.getBarChartColumnCaptions(moLabel).get(j)).equals(iContainer.get(i).get(j))){
	    				moDataset.setValue(iContainer.get(i).get(j), moContainer.getBarChartCategoryCaptions(moLabel).get(i), moContainer.getBarChartColumnCaptions(moLabel).get(j));
	    			}
	    			//poDataset.addValue(iContainer.get(i).get(j), moContainer.getBarChartCategoryCaptions().get(i), moContainer.getBarChartColumnCaptions().get(j));
	    		}
	    		
		   }
    	}
    }
}
