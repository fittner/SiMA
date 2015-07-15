/**
 * CHANGELOG
 *
 * Oct 10, 2012 herret - File created
 *
 */
package mind.autocreated;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.jfree.chart.ChartPanel;

import sim.portrayal.Inspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Oct 10, 2012, 2:32:44 PM
 * 
 */
public abstract class cls_AbstractChartInspector extends Inspector {
	
	private static final long serialVersionUID = 1688668508314593129L;
	protected String moChartName;
	protected ChartPanel moChartPanel;
	
	protected double moHeightRelation = 3.0;
	protected double moWidthRelation = 2.0;
	
	
	public cls_AbstractChartInspector(String poChartName){
		moChartName =poChartName;	
		ComponentListener compList = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				double maxWidth= getSize().getHeight()*moHeightRelation/moWidthRelation;
				double maxHeight= getSize().getWidth()*moWidthRelation/moHeightRelation;
				Dimension iDimension = getSize();
				if(iDimension.getHeight() >= maxHeight){
					iDimension.setSize(maxHeight*moHeightRelation/moWidthRelation, maxHeight);
				}
				else if (iDimension.getWidth() >= maxWidth){
					iDimension.setSize(maxWidth, maxWidth*moWidthRelation/moHeightRelation);
				}
				moChartPanel.setPreferredSize(iDimension);
			}
	      };
		addComponentListener(compList);
	}
	
	protected abstract ChartPanel initChart();
	protected abstract void updateDataset();
	
	protected ChartPanel createPanel() {
    	ChartPanel oChartPanel = initChart() ;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		return oChartPanel;
	}
	
	@Override
	public void updateInspector() {
		updateDataset();
		moChartPanel.repaint();
	}
}
