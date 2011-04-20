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
import sim.portrayal.Inspector;
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
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 17.09.2009, 09:56:49
	 */
	private static final long serialVersionUID = 205969537561647102L;
	
	private clsBaseDecisionUnit moDU;
	HTMLBrowser moHTMLPane;
	
	public clsInspectorSensorData( clsBaseDecisionUnit poDU)
	{
		moDU= poDU;
		
        String contentData = "<html><head></head><body><p>";
        contentData+=moDU.getSensorData().logHTML();
        contentData+="</p></body></html>";
        
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(contentData);
		add(moHTMLPane, BorderLayout.CENTER);
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
