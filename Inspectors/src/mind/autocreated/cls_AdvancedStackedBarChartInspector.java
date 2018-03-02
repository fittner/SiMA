/**
 * CHANGELOG
 *
 * 02.12.2015 Kollmann - File created
 *
 */
package mind.autocreated;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;

import inspector.interfaces.itfInspectorAdvancedStackedBarChart;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 02.12.2015, 08:05:45
 * 
 */
public class cls_AdvancedStackedBarChartInspector extends cls_StackedBarChartInspector {

	/** DOCUMENT (Kollmann) - insert description; @since 02.12.2015 08:06:13 */
	private static final long serialVersionUID = -4620765010107072601L;
	private List<String> moCategoryIndex = new ArrayList<String>();
	
	/**
	 * DOCUMENT (Kollmann) - insert description 
	 *
	 * @since 02.12.2015 08:05:57
	 *
	 * @param poContainer
	 */
	public cls_AdvancedStackedBarChartInspector(itfInspectorAdvancedStackedBarChart poContainer) {
		super(poContainer);
	}

	/* (non-Javadoc)
	 *
	 * @since 02.12.2015 08:09:14
	 * 
	 * @see mind.autocreated.cls_StackedBarChartInspector#initChart()
	 */
	@Override
	protected ChartPanel initChart() {
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
    	chart.setBackgroundPaint(Color.white); // background of the outside-panel
    	
    	ChartPanel oChartPanel = new ChartPanel(chart);
        oChartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        chart.setTitle(moChartName);
        CategoryPlot plot =chart.getCategoryPlot();
        NumberAxis iAxis = new NumberAxis();
        iAxis.setAutoRange(true);
        plot.setRangeAxis(iAxis);
        plot.setBackgroundPaint(Color.white);
        plot.setForegroundAlpha(0.70f);
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);
        
//        StackedBarRenderer renderer = new StackedBarRenderer(false);
//        StandardCategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
//        renderer.setBaseItemLabelGenerator(generator);
//        ItemLabelPosition pos = new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.TOP_LEFT);
//        renderer.setBasePositiveItemLabelPosition(pos);
//        renderer.setBaseItemLabelsVisible(true);
//        plot.setRenderer(renderer);

        StackedBarRenderer renderer = new StackedBarRenderer(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseNegativeItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.TOP_LEFT));
        
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
        }
        renderer.setShadowVisible(false);
        renderer.setBaseItemLabelFont(new java.awt.Font("Arial", Font.BOLD, 16));
        renderer.setBarPainter(new StandardBarPainter());
      
        
        chart.getCategoryPlot().setRenderer(renderer);
        
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setVerticalAlignment(VerticalAlignment.TOP);
        legend.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        
        LegendTitle legend2 = new LegendTitle(new LegendItemSource() {
			@Override
			public LegendItemCollection getLegendItems() {
				LegendItemCollection collection = new LegendItemCollection();
				
				Shape shape = new Rectangle(10, 10);
				
				for(Object key : moDataset.getColumnKeys()) {
					collection.add(new LegendItem("Category " + key.toString() + " ... " + lookupCategoryLabel(key.toString()), null, null, null, shape, Color.white));
				}
				
				return collection;
			}
		});
        legend2.setPosition(RectangleEdge.BOTTOM);
        BlockFrame f = new BlockBorder(Color.black);
        legend2.setFrame(f);
        
        
        chart.addLegend(legend2);
        
        
        
    	return oChartPanel;
	}

	/**
	 * DOCUMENT (Kollmann) - Create and return a short version of the given caption that can be used as column axis labels
	 *
	 * @since 02.12.2015 08:14:26
	 *
	 * @param poCaption The string that should be indexed
	 * @return The string representing the shortened version of the given caption (the mapping has to be unique)
	 */
	protected String indexCategoryLabel(String poCaption) {
		int index = -1;
		
		if(moCategoryIndex == null) {
			moCategoryIndex = new ArrayList<>();
		}

		index = moCategoryIndex.indexOf(poCaption);
		
		if(index == -1) {
			moCategoryIndex.add(poCaption);
			index = moCategoryIndex.indexOf(poCaption);
		}
		
		return Integer.toString(index);
	}
	
	/**
	 * DOCUMENT (Kollmann) - Lookup the category label for the given index
	 *
	 * @since 02.12.2015 08:19:51
	 *
	 * @param index String of the index returned by the indexCategoryLabel(String) method
	 * @return The full length category label for the given index
	 */
	protected String lookupCategoryLabel(String index) {
		if(moCategoryIndex == null) {
			throw new IllegalArgumentException("Internal error: attempting to access category index for index " + index + " before it has been created");
		}
		
		try {
			int position = Integer.parseInt(index);
			if(position < 0) {
				throw new IllegalArgumentException("Internal error: index provided to category label lookup is < 0");
			}
			
			if(position >= moCategoryIndex.size()) {
				throw new IllegalArgumentException("Internal error: index provided to category label lookup is > index size");
			}
			
			return moCategoryIndex.get(position);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Internal error: index provided to category label lookup can not be parsed into an index position");
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @since 02.12.2015 08:10:02
	 * 
	 * @see mind.autocreated.cls_StackedBarChartInspector#createDataset()
	 */
	@Override
	protected DefaultCategoryDataset createDataset() {
		moCategoryIndex = new ArrayList<>();
		
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
			ColumnCaption [i]= indexCategoryLabel(moContainer.getStackedBarChartColumnCaptions().get(i));
		}
		return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(AreaCaption, ColumnCaption, data);
	}
}
