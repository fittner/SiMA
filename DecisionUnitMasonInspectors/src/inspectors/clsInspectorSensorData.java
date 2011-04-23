/**
 * clsInspectorSensorData.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author langr
 * 03.08.2009, 13:34:23
 */
package inspectors;

import inspectors.mind.pa._v30.autocreated.TextOutputPanel;

import java.awt.BorderLayout;
import decisionunit.clsBaseDecisionUnit;
import sim.portrayal.Inspector;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 03.08.2009, 13:34:23
 * 
 */
public class clsInspectorSensorData extends Inspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 17.09.2009, 09:56:49
	 */
	private static final long serialVersionUID = 205969537561647102L;
	
	private clsBaseDecisionUnit moDU;
	TextOutputPanel moTextPanel;
	
	public clsInspectorSensorData( clsBaseDecisionUnit poDU)
	{
		moDU = poDU;
		
        String contentData = moDU.getSensorData().logText();
        
        setLayout(new BorderLayout());
    	moTextPanel = new TextOutputPanel(contentData);
		add(moTextPanel, BorderLayout.CENTER);
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 03.08.2009, 13:35:21
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
        String contentData = "";
        contentData+=moDU.getSensorData().logText();
    	moTextPanel.setText(contentData);
	}

}
