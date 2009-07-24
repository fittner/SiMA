/**
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import bw.body.internalSystems.clsInternalSystem;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * Jul 24, 2009, 12:32:39 PM
 * 
 */
public class clsInspectorInternalSystems extends Inspector{

	/**
	 * TODO (muchitsch) - insert description 
	 * 
	 * @author muchitsch
	 * Jul 24, 2009, 12:34:41 PM
	 */
	private static final long serialVersionUID = 1L;
	public sim.portrayal.Inspector moOriginalInspector;
	private clsInternalSystem moInternalSystem;
	
	private ChartPanel moChartPanel;
	private DefaultCategoryDataset moDataset;
	private DefaultCategoryDataset moDatasetUpperLimits;
	private DefaultCategoryDataset moDatasetLowerLimits;

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * Jul 24, 2009, 12:33:23 PM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO Auto-generated method stub
		
	}
	
	public clsInspectorInternalSystems(sim.portrayal.Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsInternalSystem poInternalSystem)
    {
    	super();
    	moOriginalInspector = originalInspector;
    	moInternalSystem= poInternalSystem;

    	initChart();
	
    	// set up our inspector: keep the properties inspector around too
    	setLayout(new BorderLayout());
    	add(moChartPanel, BorderLayout.NORTH);
    }
	
	private void initChart() {
		
	}
	

}
