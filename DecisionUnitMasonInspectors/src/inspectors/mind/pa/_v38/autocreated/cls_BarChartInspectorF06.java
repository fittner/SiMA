/**
 * CHANGELOG
 *
 * 19.12.2012 Lotfi - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import inspector.interfaces.itfInspectorBarChartF06;

import java.awt.Color;
import java.awt.Font;


//import java.util.ArrayList;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

//import org.jfree.ui.RefineryUtilities;
//import org.jfree.data.general.DatasetUtilities;



/**
 * DOCUMENT (Lotfi) - insert description 
 * 
 * @author Lotfi
 * 19.12.2012, 23:22:33
 * 25.06.2013
 * *
 * 
 */
public class cls_BarChartInspectorF06 extends cls_AbstractChartInspector {

	/** DOCUMENT (herret) - insert description; @since Oct 23, 2012 10:51:49 AM */
	private static final long serialVersionUID = -6851896833850214331L;
	private itfInspectorBarChartF06 moContainer;
	private DefaultCategoryDataset moDataset;

	public cls_BarChartInspectorF06(itfInspectorBarChartF06 poObject){
		super(poObject.getBarChartTitle());
		moContainer = poObject;
		moDataset = createDataset();
		
		moChartPanel = createPanel();
		add(moChartPanel);

	}
	
	@Override
	protected ChartPanel initChart() {
	    	DefaultCategoryDataset dataset = createDataset();
	        moDataset = dataset;
	        // create the chart and pack it onto the panel
	        JFreeChart chart = ChartFactory.createBarChart(
	        		moContainer.getBarChartTitle(), 
	        		"Defense", 
	        		"Forbidden Drives", 
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
	        //NumberAxis iAxis = new NumberAxis();
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//	        rangeAxis.setLowerMargin(0);
//	        rangeAxis.setUpperMargin(0);
	        
	        //iAxis.setAutoRange(false);
	        //iAxis.setRange(0.0,1.0);
	       // iAxis.setRange(0.0,100000);
	        //plot.setRangeAxis(iAxis);
	        final CategoryAxis domainAxis = plot.getDomainAxis();
	        plot.getDomainAxis().setTickLabelFont(new Font("new",1,8));
	        domainAxis.setCategoryLabelPositions(
	                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	            );
			
	        
	        plot.setBackgroundPaint(Color.white);
	       

	    	return oChartPanel;
	    	
		}
	/*
	 * moTimeChartData.put("PassForbidenEmotion", ChartBarPassForbidenEmotion);
		moTimeChartData.put("PassForbidenPerception", ChartBarPassForbidenPerception);
		moTimeChartData.put("Denial", ChartBarDenial);
		moTimeChartData.put("Idealization", ChartBarIdealization);
		moTimeChartData.put("Depreciation", ChartBarDepreciation);
		moTimeChartData.put("ReversalOfAffect", ChartBarReversalOfAffect);
	 * 
	 * 
	 * 
	 */
	
	 private DefaultCategoryDataset createDataset() {
		 
		 DefaultCategoryDataset dataF06 = new DefaultCategoryDataset();
		 dataF06.setValue(moContainer.getBarChartData().get("Displacement"), "Displacement", "Displacement");
		 dataF06.setValue(moContainer.getBarChartData().get("Repression"),"Repression","Repression");
		 dataF06.setValue(moContainer.getBarChartData().get("Sublimation"),"Sublimation","Sublimation");
		 dataF06.setValue(moContainer.getBarChartData().get("PassForbiddenDrives"),"PassForbiddenDrives","PassForbiddenDrives");
		 dataF06.setValue(moContainer.getBarChartData().get("ReactionFormation"),"ReactionFormation","ReactionFormation");
		 dataF06.setValue(moContainer.getBarChartData().get("ReversalOfAffect"),"ReversalOfAffect","ReversalOfAffect");
		 dataF06.setValue(moContainer.getBarChartData().get("Turning_Against_Self"),"Turning_Against_Self","Turning_Against_Self");
		 dataF06.setValue(moContainer.getBarChartData().get("Projection"),"Projection","Projection");
		
		 
		 
		 
		 return dataF06;
	 }

	/* (non-Javadoc)
	 *
	 * @since 31.12.2012 19:00:42
	 * 
	 * @see inspectors.mind.pa._v38.autocreated.cls_AbstractChartInspector#updateDataset()
	 */
	@Override
	protected void updateDataset() {
		// TODO (Lotfi) - Auto-generated method stub
		moDataset.setValue(moContainer.getBarChartData().get("Displacement"),"Displacement","Displacement");
		moDataset.setValue(moContainer.getBarChartData().get("Repression"),"Repression","Repression");
		moDataset.setValue(moContainer.getBarChartData().get("Sublimation"),"Sublimation","Sublimation");
		moDataset.setValue(moContainer.getBarChartData().get("PassForbiddenDrives"),"PassForbiddenDrives","PassForbiddenDrives");
		moDataset.setValue(moContainer.getBarChartData().get("ReactionFormation"),"ReactionFormation","ReactionFormation");
		moDataset.setValue(moContainer.getBarChartData().get("ReversalOfAffect"),"ReversalOfAffect","ReversalOfAffect");
		moDataset.setValue(moContainer.getBarChartData().get("Turning_Against_Self"),"Turning_Against_Self","Turning_Against_Self");
		moDataset.setValue(moContainer.getBarChartData().get("Projection"),"Projection","Projection");
		
	}

	  
	  /*  private DefaultCategoryDataset createDataset() {
	    	
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getBarChartData();
		    double[][] data = new double[iContainer.size()][iContainer.get(0).size()];
	    	
	    	
		   for(int i =0 ;i< iContainer.size();i++){
	    		for(int j=0;j<iContainer.get(0).size();j++){
	    			data[i][j]=iContainer.get(i).get(j);
	    		}
	    		
		   }
		   String[] AreaCaption = new String[moContainer.getBarChartCategoryCaptions1().size()];
		   for(int i = 0; i<moContainer.getBarChartCategoryCaptions1().size();i++){
			   AreaCaption [i]= moContainer.getBarChartCategoryCaptions1().get(i);
		   }
		   String[] ColumnCaption = new String[moContainer.getBarChartColumnCaptions().size()];
		   for(int i = 0; i<moContainer.getBarChartColumnCaptions().size();i++){
			   ColumnCaption [i]= moContainer.getBarChartColumnCaptions().get(i);
		   }
	        return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(AreaCaption, ColumnCaption, data);
	        
	    }
	   
	    @Override
		protected void updateDataset(){
	    	//poDataset.clear();
	    	ArrayList<ArrayList<Double>> iContainer = moContainer.getBarChartData();
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
		    			if(!moDataset.getValue(moContainer.getBarChartCategoryCaptions1().get(i), moContainer.getBarChartColumnCaptions().get(j)).equals(iContainer.get(i).get(j))){
		    				moDataset.setValue(iContainer.get(i).get(j), moContainer.getBarChartCategoryCaptions1().get(i), moContainer.getBarChartColumnCaptions().get(j));
		    			}
		    			//poDataset.addValue(iContainer.get(i).get(j), moContainer.getBarChartCategoryCaptions().get(i), moContainer.getBarChartColumnCaptions().get(j));
		    		}
		    		
			   }
	    	}
	    }*/

}


