/**
 * clsE03InspectorOutput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 06.10.2009, 18:36:23
 */
package inspectors.mind.pa;

import pa.modules.E03_GenerationOfDrives;
import sim.display.GUIState;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;
import sim.portrayal.Inspector;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 06.10.2009, 18:36:23
 * 
 */
public class clsE03InspectorOutput extends Inspector {

	private static final long serialVersionUID = 2188636113077293223L;
	public Inspector moOriginalInspector;
	//private E03_GenerationOfDrives moGenDrive; //never used!
	HTMLBrowser moHTMLPane;
	
    public clsE03InspectorOutput(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E03_GenerationOfDrives poGenDrive)
    {
		moOriginalInspector = originalInspector;
		//moGenDrive= poGenDrive; //never used!
		
		//HashMap<eSensorIntType, clsDataBase> oHomeo = moE02.getHomeostasisData();
//		
//        String contentData = "<html><head></head><body><p>test homeo input";
//		for( clsDataBase oDataBase : oHomeo.values() ) {
//			if(oDataBase != null) {
//				contentData += oDataBase.logHTML();
//			}
//		}
//        contentData+="</p></body></html>";
//        
//        setLayout(new BorderLayout());
//    	moHTMLPane = new HTMLBrowser(contentData);
//		add(moHTMLPane, BorderLayout.CENTER);
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
//		HashMap<eSensorIntType, clsDataBase> oHomeo = moE02.getHomeostasisData();
//        String contentData = "<html><head></head><body><p>test homeo input";
//		for( clsDataBase oDataBase : oHomeo.values() ) {
//			if(oDataBase != null) {
//				contentData += oDataBase.logHTML();
//			}
//		}
//        contentData+="</p></body></html>";
//		moHTMLPane.setText(contentData);
	}
}