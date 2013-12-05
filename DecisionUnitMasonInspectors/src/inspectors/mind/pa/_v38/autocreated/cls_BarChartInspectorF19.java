/**
 * CHANGELOG
 *
 * 12.04.2013 Lotfi - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


import pa._v38.interfaces.itfInspectorBarChartF19;

/**
 * DOCUMENT (Lotfi) - insert description 
 * 
 * @author Lotfi
 * 12.04.2013, 14:56:49
 * 
 */
public class cls_BarChartInspectorF19 extends cls_AbstractChartInspector {
	/** DOCUMENT (herret) - insert description; @since Oct 23, 2012 10:51:49 AM */
	private static final long serialVersionUID = -6851896833850214331L;
	private itfInspectorBarChartF19 moContainer;
	private DefaultCategoryDataset moDataset;

	public cls_BarChartInspectorF19(itfInspectorBarChartF19 poObject){
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
	        		"Forbidden Perceptions or Emotions", 
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
		 
		 DefaultCategoryDataset dataF19 = new DefaultCategoryDataset();
		 
		 dataF19.setValue(moContainer.getBarChartData().get("PassForbiddenEmotions"),"PassForbiddenEmotions","PassForbiddenEmotions");
		 dataF19.setValue(moContainer.getBarChartData().get("ReversalOfAffect"),"ReversalOfAffect","ReversalOfAffect");
		 dataF19.setValue(moContainer.getBarChartData().get("PassForbiddenPerceptions"),"PassForbiddenPerceptions","PassForbiddenPerceptions");
		 dataF19.setValue(moContainer.getBarChartData().get("Denial"),"Denial","Denial");
		 dataF19.setValue(moContainer.getBarChartData().get("Idealization"),"Idealization","Idealization");
		 dataF19.setValue(moContainer.getBarChartData().get("Depreciation"),"Depreciation","Depreciation");
		 
		 
		 
		 
		 return dataF19;
	 }

	/* (non-Javadoc)
	 * *
	 *
	 * @since 31.12.2012 19:00:42
	 * 
	 * @see inspectors.mind.pa._v38.autocreated.cls_AbstractChartInspector#updateDataset()
	 * 
	 * 
	 */
	@Override
	protected void updateDataset() {
		// TODO (Lotfi) - Auto-generated method stub
		 moDataset.setValue(moContainer.getBarChartData().get("PassForbiddenEmotions"),"PassForbiddenEmotions","PassForbiddenEmotions");
		 moDataset.setValue(moContainer.getBarChartData().get("ReversalOfAffect"),"ReversalOfAffect","ReversalOfAffect");		 
		 moDataset.setValue(moContainer.getBarChartData().get("PassForbiddenPerceptions"),"PassForbiddenPerceptions","PassForbiddenPerceptions");
		 moDataset.setValue(moContainer.getBarChartData().get("Denial"),"Denial","Denial");
		 moDataset.setValue(moContainer.getBarChartData().get("Idealization"),"Idealization","Idealization");
		 moDataset.setValue(moContainer.getBarChartData().get("Depreciation"),"Depreciation","Depreciation");
		 	
	}

	  
	

}
