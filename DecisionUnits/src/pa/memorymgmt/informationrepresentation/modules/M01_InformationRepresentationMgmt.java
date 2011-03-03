/**
 * G01_InformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 */
package pa.memorymgmt.informationrepresentation.modules;

import pa.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 * 
 */
public class M01_InformationRepresentationMgmt extends clsInformationRepresentationModuleContainer{
	public KB01_SecondaryDataStructureMgmt moKB01SecondaryDataStructureMgmt; 
	public M02_PrimaryInformationMgmt moM02PrimaryInformationMgmt;
	
	public M01_InformationRepresentationMgmt(clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod){
//		super(null, poSearchSpaceHandler, poSearchMethod);
		super(poSearchSpaceHandler, poSearchMethod);
		moKB01SecondaryDataStructureMgmt = new KB01_SecondaryDataStructureMgmt(this, poSearchSpaceHandler, poSearchMethod); 
		moM02PrimaryInformationMgmt = new M02_PrimaryInformationMgmt(this, poSearchSpaceHandler, poSearchMethod); 
	}
	
}
