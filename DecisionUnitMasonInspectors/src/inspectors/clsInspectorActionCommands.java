/**
 * clsInspectorActionCommands.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 */
package inspectors;

import java.awt.BorderLayout;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;
import decisionunit.clsBaseDecisionUnit;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 * 
 */
public class clsInspectorActionCommands  extends Inspector {
    
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 03.08.2009, 14:32:39
	 */
	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsBaseDecisionUnit moDU;
//	private Controller moConsole; // TD - warning free
//	private JLabel moCaption; // TD - warning free
	
	HTMLBrowser moHTMLPane;
	
	public clsInspectorActionCommands(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsBaseDecisionUnit poDU)
	{
		moOriginalInspector = originalInspector;
		moDU= poDU;
		//final SimState state=guiState.state;
//		moConsole=guiState.controller; // TD - warning free
		
//		moCaption = new JLabel("Layers of Brooks Subsumption Architecture"); // TD - warning free
        // creating the checkbox to sitch on/off the AI intelligence-levels.
		
        String contentData = "<html><head></head><body><p>";
        contentData+=moDU.getSensorData().logHTML();
        contentData+="</p></body></html>";
        
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(contentData);
		add(moHTMLPane, BorderLayout.WEST);
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
        String contentData = "<html><head><tr.font face='Courier'></head><body>";
        contentData+=moDU.getSensorData().logHTML();
        contentData+="</body></html>";
        moHTMLPane.setText(contentData);
	}

}
