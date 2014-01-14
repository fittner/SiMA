/**
 * clsInspectorFlesh.java: BW - bw.utils.inspectors.body
 * 
 * @author deutsch
 * 17.09.2009, 20:09:44
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;

import complexbody.internalSystems.clsFlesh;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 17.09.2009, 20:09:44
 * 
 */
public class clsInspectorFlesh extends Inspector  {

	/**
	 * @author deutsch
	 * 17.09.2009, 10:00:18
	 */
	private static final long serialVersionUID = -4921447755303330168L;
	
	public Inspector moOriginalInspector;
	private clsFlesh moFlesh; //reference
	
	HTMLBrowser moHTMLPane;
	
	public clsInspectorFlesh(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsFlesh poFlesh)
	{
		moOriginalInspector = originalInspector;
		moFlesh = poFlesh;
		
        String contentData = "<html><head></head><body><p>";
        contentData+=generateContent();
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
        contentData+=generateContent();
        contentData+="</body></html>";
    	moHTMLPane.setText(contentData);
	}
	
	private String generateContent() {
		String oContent;
		
		oContent = moFlesh.toString();
		oContent = oContent.replaceAll("\\|", "<br/>");
				
		return oContent;
	}
}
