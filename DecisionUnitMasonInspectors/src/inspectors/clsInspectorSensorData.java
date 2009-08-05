/**
 * clsInspectorSensorData.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author langr
 * 03.08.2009, 13:34:23
 */
package inspectors;

import java.awt.BorderLayout;

//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JLabel; // TD - warning free
import decisionunit.clsBaseDecisionUnit;
//import sim.display.Controller; // TD - warning free
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 03.08.2009, 13:34:23
 * 
 */
public class clsInspectorSensorData extends Inspector {
    
	
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
	
	public clsInspectorSensorData(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsBaseDecisionUnit poDU)
	{
		moOriginalInspector = originalInspector;
		moDU= poDU;
		
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
    	//moHTMLPane.setSize(getWidth(), getHeight());
	}

}
