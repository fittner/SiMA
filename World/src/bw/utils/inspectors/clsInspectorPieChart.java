/**
 * CHANGELOG
 *
 * 05.04.2013 Jordakieva - File created
 *
 */
package bw.utils.inspectors;

import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

import sim.portrayal.Inspector;

/**
 * DOCUMENT (Jordakieva) - insert description 
 * 
 * @author Jordakieva
 * 05.04.2013, 11:10:07
 * 
 */
public class clsInspectorPieChart extends Inspector {
	private PieDataset moPieDataset;
	private ChartPanel moChart;

	public clsInspectorPieChart (PieDataset poPieDataset) {
		
		moPieDataset = poPieDataset;
		moChart = new ChartPanel (createChart());
		
		setLayout (new BorderLayout());
		
		add (moChart, BorderLayout.CENTER);
	}
	
	private JFreeChart createChart () {
		
		JFreeChart oChart = ChartFactory.createPieChart("Inventory Weight Capacity", moPieDataset, true, false, false);
		
		return oChart;
	}

	@Override
	public void updateInspector() {
		
		moChart.repaint();
	}

}
