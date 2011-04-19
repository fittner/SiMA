/**
 * clsE02InspectorInput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:46:21
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;
import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

import pa.modules._v30.E02_NeurosymbolizationOfNeeds;
import sim.portrayal.Inspector;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 01:46:21
 * 
 */
public class clsE02InspectorInput extends Inspector {

	private static final long serialVersionUID = 3331975073925689043L;
	private E02_NeurosymbolizationOfNeeds moE02;
	HTMLBrowser moHTMLPane;
	
    public clsE02InspectorInput(E02_NeurosymbolizationOfNeeds poNeuroNeeds)
    {
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
