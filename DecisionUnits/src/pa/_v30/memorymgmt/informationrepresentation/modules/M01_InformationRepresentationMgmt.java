/**
 * G01_InformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 */
package pa._v30.memorymgmt.informationrepresentation.modules;

import pa._v30.interfaces.itfInspectorInternalState;
import pa._v30.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v30.tools.toHtml;
/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 * 
 */
public class M01_InformationRepresentationMgmt extends clsInformationRepresentationModuleContainer implements itfInspectorInternalState {
	public KB01_SecondaryDataStructureMgmt moKB01SecondaryDataStructureMgmt; 
	public M02_PrimaryInformationMgmt moM02PrimaryInformationMgmt;
	
	public M01_InformationRepresentationMgmt(clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod){
//		super(null, poSearchSpaceHandler, poSearchMethod);
		super(poSearchSpaceHandler, poSearchMethod);
		moKB01SecondaryDataStructureMgmt = new KB01_SecondaryDataStructureMgmt(this, poSearchSpaceHandler, poSearchMethod); 
		moM02PrimaryInformationMgmt = new M02_PrimaryInformationMgmt(this, poSearchSpaceHandler, poSearchMethod); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 16:35:47
	 * 
	 * @see pa._v30.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html = "";
		
		html += "<h1>M01_InformationRepresentationMgmt</h1>";
		html += toHtml.removeHTMLWrap(moSearchSpaceHandler.stateToHTML());
		html += toHtml.removeHTMLWrap(moKB01SecondaryDataStructureMgmt.stateToHTML());
		html += toHtml.removeHTMLWrap(moM02PrimaryInformationMgmt.stateToHTML());
		
		return html;
	}
	
}
