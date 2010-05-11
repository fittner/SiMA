/**
 * clsStaticLoaderHelper.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 17.10.2009, 18:06:51
 */
package pa.loader;

import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import du.enums.pa.eContext;


import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;
import pa.datatypes.clsAffectMemory;
import pa.datatypes.clsDriveContentCategories;
import pa.datatypes.clsPrimaryInformation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 18:06:51
 * 
 */
public class clsStaticLoaderHelper {

	public static clsPrimaryInformation loadPrimaryInformation(Node primaryNode) {
		
		clsPrimaryInformation oRetVal = new clsPrimaryInformation();
		NamedNodeMap oAtrib = primaryNode.getAttributes();
		
		clsDriveContentCategories oRatio = new clsDriveContentCategories(
				Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"oral")),
				Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"anal")),
				Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"phallic")),
				Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"genital"))
				);
		
		eContext eCurrentCont = eContext.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"context") );
		oRetVal.moTP.moDriveContentCategory.put(eCurrentCont, oRatio);
		
		oRetVal.moAffect = new clsAffectMemory(
				Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"affect_memory")));
		
		return oRetVal;
	}
	
	public static clsPrimaryInformation loadPrimaryInformationObject(Node primaryNode, String poDCCList, String poDCCEntry) {
		
		clsPrimaryInformation oRetVal = new clsPrimaryInformation();
		NamedNodeMap oAtrib = primaryNode.getAttributes();
		
		oRetVal.moAffect = new clsAffectMemory(
				Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"affect_memory")));
		
		//get ContentCathegoryList-Node (its only only)
		Node oDCCListNode = clsXMLAbstractImageReader.getNextNodeElementByName(primaryNode, poDCCList);
		
	    Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getNodeElementByName( oDCCListNode, poDCCEntry, 1, oNodes);

		for(Node oCatNode : oNodes)                       
		{
			oAtrib = oCatNode.getAttributes();
			clsDriveContentCategories oRatio = new clsDriveContentCategories(
					Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"oral")),
					Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"anal")),
					Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"phallic")),
					Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"genital"))
					);
			
			eContext eCurrentCont = eContext.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"context") );
			oRetVal.moTP.moDriveContentCategory.put(eCurrentCont, oRatio);
		}
		
		return oRetVal;
	}
}
