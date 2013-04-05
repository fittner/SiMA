/**
 * CHANGELOG
 *
 * 05.04.2013 Jordakieva - File created
 *
 */
package bw.utils.inspectors;

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

	public clsInspectorPieChart (PieDataset poPieDataset) {
		moPieDataset = poPieDataset;
		
	}
	/* (non-Javadoc)
	 *
	 * @since 05.04.2013 11:10:08
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO (Jordakieva) - Auto-generated method stub
		
//		PiePlot oPlot = new PiePlot (moPieDataset);
//		JFreeChart oChart = new JFreeChart ("Inventory Weight Usage", oPlot);


	}

}
