/**
 * CHANGELOG
 *
 * 30.04.2015 Kollmann - File created
 *
 */
package mind.autocreated;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import sim.portrayal.Inspector;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 30.04.2015, 17:18:38
 * 
 */
public class cls_CombinedGenericChart extends Inspector {
	private List<Inspector> moInspectors = new ArrayList<>();
	
	public void addInspector(Inspector poInspector) {
		moInspectors.add(poInspector);
		
		double iNumCharts = moInspectors.size();
		int nColumns=(int) Math.ceil(Math.sqrt(iNumCharts));
		int nRows = (int) Math.ceil(iNumCharts/nColumns);
		this.setLayout(new GridLayout(nRows , nColumns));
		add(poInspector);
		
		ComponentListener compList = new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				int iColumns=(int) Math.ceil(Math.sqrt(moInspectors.size()));
				for(Inspector iInspector : moInspectors){
					if(iInspector instanceof cls_GenericTimeChartInspector){
						((cls_GenericTimeChartInspector) iInspector).setChartSize(new Dimension((int)getSize().getWidth()/iColumns,(int)getSize().getHeight()/iColumns));
					}
				}
			}
	      };
		addComponentListener(compList);
	}
	
	@Override
	public void updateInspector() {
		for(Inspector oInspector : moInspectors) {
			oInspector.updateInspector();
		}
	}
}
