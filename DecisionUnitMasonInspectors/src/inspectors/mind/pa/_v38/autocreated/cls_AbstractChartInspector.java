/**
 * CHANGELOG
 *
 * Oct 10, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

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
public abstract class cls_AbstractChartInspector extends Inspector  {


	private static final long serialVersionUID = 1688668508314593129L;
	protected String moChartName;
	protected ChartPanel moChartPanel;
	
	
	public cls_AbstractChartInspector(String poChartName){
		moChartName =poChartName;	
		ComponentListener compList = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				moChartPanel.setPreferredSize(getSize());
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
