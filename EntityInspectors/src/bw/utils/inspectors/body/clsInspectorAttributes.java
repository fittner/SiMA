/**
 * clsInspectorAttributes.java: BW - bw.utils.inspectors.body
 * 
 * @author deutsch
 * 17.09.2009, 09:54:56
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import entities.enums.eBodyAttributes;

import body.attributes.clsAttributes;
import body.attributes.clsBaseAttribute;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 17.09.2009, 09:54:57
 * 
 */
public class clsInspectorAttributes extends Inspector  {

	/**
	 * @author deutsch
	 * 17.09.2009, 10:00:18
	 */
	private static final long serialVersionUID = -4921447755303330168L;
	
	public Inspector moOriginalInspector;
	private Set<Entry<eBodyAttributes, clsBaseAttribute>> moAttributes; //reference
	
	HTMLBrowser moHTMLPane;
	
	public clsInspectorAttributes(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsAttributes poAttributes)
	{
		moOriginalInspector = originalInspector;
		moAttributes = poAttributes.getAttributeList().entrySet();
		
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
		String oContent = "";
		
		for (Map.Entry<eBodyAttributes, clsBaseAttribute> entry:moAttributes) {
			oContent += entry.getValue().toString() + "<br/>";
		}
		
		return oContent;
	}
}
