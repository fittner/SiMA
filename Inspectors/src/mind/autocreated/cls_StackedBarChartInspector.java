/**
 * CHANGELOG
 *
 * Sep 27, 2012 herret - File created
 *
 */
package mind.autocreated;

import inspector.interfaces.itfInspectorStackedBarChart;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 27, 2012, 9:44:49 AM
 * 
 */

public class cls_StackedBarChartInspector extends cls_AbstractChartInspector {

	private static final long serialVersionUID = 5401441807093048593L;
	
	protected itfInspectorStackedBarChart moContainer;
	protected DefaultCategoryDataset moDataset;
	
	public cls_StackedBarChartInspector(itfInspectorStackedBarChart poContainer){
		super(poContainer.getStackedBarChartTitle());
		moContainer = poContainer;
		moDataset = createDataset();
		
		moChartPanel = createPanel();
		add(moChartPanel);
		
	}
	
	
	@Override
	protected ChartPanel initChart(){
        // create the chart and pack it onto the panel
        JFreeChart chart = ChartFactory.createStackedBarChart(
        		moContainer.getStackedBarChartTitle(), 
        		"Category", 
        		"Value", 
        		moDataset, 
        		PlotOrientation.VERTICAL, 
        		true, 
        		true, 
        		false
        );
        //place for optical improvements of the chart
    	chart.setBackgroundPaint(Color.black); // background of the outside-panel
  
    	
    	
    	ChartPanel oChartPanel = new ChartPanel(chart);
        oChartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        chart.setTitle(moChartName);
        
        
        StackedBarRenderer renderer = new StackedBarRenderer(false);
        
        Color color_new;
        //renderer.setSeriesPaint(0, color_new);
        
        for (int i = 0; i < moDataset.getRowCount(); i++){
            switch (i) {
            case 0:
                // red
                color_new = new Color(255, 0, 0);
                break;
            case 1:
                // blue
                color_new = new Color(0, 0, 255);
                break;
            case 2:
                // red
                color_new = new Color(240, 240, 0);
                break;
            case 3:
                // blue
                color_new = new Color(0, 255, 255);
                break;
            case 4:
                // red
                color_new = new Color(0, 255, 0);
                break;
            case 5:
                // blue
                color_new = new Color(255, 0, 255);
                break;
            case 6:
                // red
                color_new = new Color(164, 164, 164);
                break;
            case 7:
                // blue
                color_new = new Color(255, 128, 0);
                break;
            case 8:
                // red
                color_new = new Color(128, 0, 255);
                break;
            case 9:
                // blue
                color_new = new Color(255, 0, 128);
                break;
            default:
                // green
                color_new = new Color(0, 0, 0);
                break;
            }
            renderer.setSeriesPaint(i, color_new);
            renderer.setSeriesFillPaint(0, getForeground());
            renderer.setSeriesFillPaint(0, new Color(255, 0, 0));
            renderer.setSeriesVisible(0, null);
        }
        
        renderer.setShadowVisible(false);
        renderer.setShape(null);
        renderer.setBarPainter(new StandardBarPainter());
        
        CategoryPlot plot =chart.getCategoryPlot();
        chart.getCategoryPlot().setRenderer(renderer);
        NumberAxis iAxis = new NumberAxis();
        iAxis.setAutoRange(true);
        //iAxis.setRange(0.0,1.0);
        plot.setRangeAxis(iAxis);
        
        plot.setBackgroundPaint(Color.lightGray);
        plot.setForegroundAlpha(0.7f);

    	return oChartPanel;
	}
	
	protected DefaultCategoryDataset createDataset(){
		ArrayList<ArrayList<Double>> iContainer = moContainer.getStackedBarChartData();
		double[][] data = new double[iContainer.size()][iContainer.get(0).size()];
		   for(int i =0 ;i< iContainer.size();i++){
	    		for(int j=0;j<iContainer.get(0).size();j++){
	    			data[i][j]=iContainer.get(i).get(j);
	    		}
	    		
		   }
		   String[] AreaCaption = new String[moContainer.getStackedBarChartCategoryCaptions().size()];
		   for(int i = 0; i<moContainer.getStackedBarChartCategoryCaptions().size();i++){
			   AreaCaption [i]= moContainer.getStackedBarChartCategoryCaptions().get(i);
		   }
		   String[] ColumnCaption = new String[moContainer.getStackedBarChartColumnCaptions().size()];
		   for(int i = 0; i<moContainer.getStackedBarChartColumnCaptions().size();i++){
			   ColumnCaption [i]= moContainer.getStackedBarChartColumnCaptions().get(i);
		   }
	        return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(AreaCaption, ColumnCaption, data);
	}
	

	@Override
	protected void updateDataset(){
    	ArrayList<ArrayList<Double>> iContainer = moContainer.getStackedBarChartData();
    	//column or row size of given data != column or row size of actual dataset
    	//->clear dataset and crate an new on out of given data
    	//if(moDataset.getColumnCount()!=iContainer.get(0).size() || moDataset.getRowCount()!=iContainer.size()){
    		moDataset.clear();
    		DefaultCategoryDataset iDataset = createDataset();
    		for (int i= 0; i<iDataset.getRowCount();i++){
    			for (int j=0; j<iDataset.getColumnCount();j++){
    				moDataset.addValue(iDataset.getValue(i, j), iDataset.getRowKey(i), iDataset.getColumnKey(j));
    			}
    		}
    	/*}
    	else{  
		   for(int i =0 ;i< iContainer.size();i++){
	    		for(int j=0;j<iContainer.get(0).size();j++){
	    			if(!moDataset.getValue(moContainer.getStackedBarChartCategoryCaptions().get(i), moContainer.getStackedBarChartColumnCaptions().get(j)).equals(iContainer.get(i).get(j))){
	    				moDataset.setValue(iContainer.get(i).get(j), moContainer.getStackedBarChartCategoryCaptions().get(i), moContainer.getStackedBarChartColumnCaptions().get(j));
	    			}
	    		}
	    		
		   }
    	}*/		
	}

}
