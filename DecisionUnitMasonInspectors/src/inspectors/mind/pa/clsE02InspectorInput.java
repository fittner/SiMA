/**
 * clsE02InspectorInput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:46:21
 */
package inspectors.mind.pa;

import java.awt.BorderLayout;
import java.util.HashMap;

import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorIntType;

import pa.modules.E02_NeurosymbolizationOfNeeds;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 01:46:21
 * 
 */
public class clsE02InspectorInput extends Inspector {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private E02_NeurosymbolizationOfNeeds moE02;
	HTMLBrowser moHTMLPane;
	
    public clsE02InspectorInput(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E02_NeurosymbolizationOfNeeds poNeuroNeeds)
    {
		moOriginalInspector = originalInspector;
		moE02= poNeuroNeeds;
		
		HashMap<eSensorIntType, clsDataBase> oHomeo = moE02.getHomeostasisData();
		
        String contentData = "<html><head></head><body><p>test homeo input";
		for( clsDataBase oDataBase : oHomeo.values() ) {
			if(oDataBase != null) {
				contentData += oDataBase.logHTML();
			}
		}
        contentData+="</p></body></html>";
        
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(contentData);
		add(moHTMLPane, BorderLayout.CENTER);
    }
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		HashMap<eSensorIntType, clsDataBase> oHomeo = moE02.getHomeostasisData();
        String contentData = "<html><head></head><body><p>test homeo input";
		for( clsDataBase oDataBase : oHomeo.values() ) {
			if(oDataBase != null) {
				contentData += oDataBase.logHTML();
			}
		}
        contentData+="</p></body></html>";
		moHTMLPane.setText(contentData);
	}
}
