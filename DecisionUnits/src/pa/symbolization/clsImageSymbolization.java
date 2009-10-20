/**
 * @author langr
 * 21.04.2009, 11:58:35
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package pa.symbolization;

import java.util.ArrayList;
import java.util.Vector;

import bfg.symbolization.brainimages.clsContainerAbstractImages;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.ruletree.clsRuleCompareResult;

import bfg.tools.xmltools.clsXMLConfiguration;
import decisionunit.itf.sensors.clsSensorData;

/**
 * Implementation for symbolization of sensory data using the Anylogic-based 
 * BFG implementation with the RuleTrees defined in XML.  
 * 
 * @author langr
 * 21.04.2009, 11:58:35
 * @deprecated
 */
public class clsImageSymbolization extends clsSymbolization {

	private clsImageAbstract moTestImage;
	private clsContainerAbstractImages moTestImages;
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 21.04.2009, 11:58:35
	 *
	 */
	public clsImageSymbolization() {
		
		moTestImage = new clsImageAbstract(1, "TEAM_PSY10", "Description");
		clsXMLConfiguration.moConfigurationPath = "S:\\ARS\\PA\\BFG\\xml";
		
		Vector<String> oFilePaths = new Vector<String>(); 
		oFilePaths.add("PSY_10");
		moTestImages = clsImageAbstract.createImageAbstractList(oFilePaths, 1);
	}

	public void generateSymbols(clsSensorData poSensorData)
	{
		ArrayList<clsRuleCompareResult> oMatch = moTestImages.associate(poSensorData, new clsIdentity() );
	}
	
}
