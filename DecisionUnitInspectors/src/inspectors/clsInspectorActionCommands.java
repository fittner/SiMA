/**
 * clsInspectorActionCommands.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 */
package inspectors;

import java.awt.BorderLayout;

import panels.TextOutputPanel;
import sim.portrayal.Inspector;
import statictools.clsSimState;
import decisionunit.clsBaseDecisionUnit;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 * 
 */
public class clsInspectorActionCommands  extends Inspector {
	private static final long serialVersionUID = 7969271764842942368L;
    protected long mnTimeCounter;
	private clsBaseDecisionUnit moDU;
	TextOutputPanel moTextPane;
	public static final String newline = System.getProperty("line.separator");
	
	private String getContent() {
        String contentData = "";
        
        //contentData += moDU.moActionLogger.;
        
        return contentData;
	}
	
	public clsInspectorActionCommands(clsBaseDecisionUnit poDU) {
		moDU= poDU;
       
        setLayout(new BorderLayout());
    	moTextPane = new TextOutputPanel( getContent() );
		add(moTextPane, BorderLayout.CENTER);
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
		mnTimeCounter = clsSimState.getSteps();
        moTextPane.setText(  getContent() );
	}
}
