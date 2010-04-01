/**
 * clsE03InspectorDriveDefinitions.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 28.09.2009, 19:10:55
 */
package inspectors.mind.pa;

import java.awt.BorderLayout;
import java.util.ArrayList;

import pa.loader.clsTemplateDrive;
import pa.modules.E03_GenerationOfDrives;
import pa.tools.clsPair;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 19:10:55
 * 
 */
public class clsE03InspectorDriveDefinitions extends Inspector {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private E03_GenerationOfDrives moGenDrive;
	HTMLBrowser moHTMLPane;
	
    public clsE03InspectorDriveDefinitions(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E03_GenerationOfDrives poGenDrive)
    {
		moOriginalInspector = originalInspector;
		moGenDrive= poGenDrive;
		
		ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>> oDriveList = moGenDrive.getDriveDefinition();
		
        String contentData = "<html><head></head><body><p>";
		for( clsPair<clsTemplateDrive, clsTemplateDrive> oDrvPair : oDriveList ) {
			if(oDrvPair != null) {
				contentData += oDrvPair.a.logHTML();
				contentData += oDrvPair.b.logHTML();
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
		//this information is static during runtime...
	}
}
