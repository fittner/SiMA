/**
 * clsE03InspectorInput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 06.10.2009, 18:36:01
 */
package inspectors.mind.pa;

import java.awt.BorderLayout;
import java.util.HashMap;

import pa.modules.E03_GenerationOfDrives;
import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorIntType;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 06.10.2009, 18:36:01
 * 
 */
public class clsE03InspectorInput extends Inspector {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private E03_GenerationOfDrives moGenDrive;
	HTMLBrowser moHTMLPane;
	
    public clsE03InspectorInput(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E03_GenerationOfDrives poGenDrive)
    {
		moOriginalInspector = originalInspector;
		moGenDrive= poGenDrive;
		
		HashMap<eSensorIntType, clsDataBase> oHomeo = poGenDrive.moHomeostasisSymbols;
		
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
		HashMap<eSensorIntType, clsDataBase> oHomeo = moGenDrive.moHomeostasisSymbols;
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
