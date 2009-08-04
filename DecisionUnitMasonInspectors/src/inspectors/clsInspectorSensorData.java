/**
 * clsInspectorSensorData.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author langr
 * 03.08.2009, 13:34:23
 */
package inspectors;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import decisionunit.clsBaseDecisionUnit;

import sim.display.Controller;
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
	private Controller moConsole;
	private JLabel moCaption;
	
	HTMLBrowser moHTMLPane;
	
	public clsInspectorSensorData(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsBaseDecisionUnit poDU)
	{
		moOriginalInspector = originalInspector;
		moDU= poDU;
		//final SimState state=guiState.state;
		moConsole=guiState.controller;
		
		moCaption = new JLabel("Layers of Brooks Subsumption Architecture");
        // creating the checkbox to sitch on/off the AI intelligence-levels.
        Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
        String contentData = "<html><head></head><body><p>";
        contentData+=moDU.getSensorData().logHTML();
        contentData+="</p></body></html>";
        
    	moHTMLPane = new HTMLBrowser(contentData);
        //JScrollPane scrollPane = new JScrollPane(moHTMLPane);
        oBox1.add(moHTMLPane, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(oBox1, BorderLayout.NORTH);
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
