/**
 * @author langr
 * 21.04.2009, 11:58:35
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package pa._v38.symbolization;

//import java.util.ArrayList;
//import java.util.Vector;

//import pa._v19.bfg.symbolization.brainimages.clsContainerAbstractImages;
//import pa._v19.bfg.symbolization.brainimages.clsIdentity;
//import pa._v19.bfg.symbolization.brainimages.clsImageAbstract;
//import pa._v19.bfg.symbolization.ruletree.clsRuleCompareResult;
//import pa._v19.bfg.tools.xmltools.clsXMLConfiguration;


import du.itf.sensors.clsSensorData;

/**
 * Implementation for symbolization of sensory data using the Anylogic-based 
 * BFG implementation with the RuleTrees defined in XML.  
 * 
 * @author langr
 * 21.04.2009, 11:58:35
 * @deprecated
 */
public class clsImageSymbolization extends clsSymbolization {

//	@SuppressWarnings("unused")
//	private clsImageAbstract moTestImage;
//	private clsContainerAbstractImages moTestImages;
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 21.04.2009, 11:58:35
	 *
	 */
	public clsImageSymbolization() {
		
//		moTestImage = new clsImageAbstract(1, "TEAM_PSY10", "Description");
//		clsXMLConfiguration.moConfigurationPath = "S:\\ARS\\PA\\BFG\\xml";
//		
//		Vector<String> oFilePaths = new Vector<String>(); 
//		oFilePaths.add("PSY_10");
//		moTestImages = clsImageAbstract.createImageAbstractList(oFilePaths, 1);
	}

	public void generateSymbols(clsSensorData poSensorData)
	{
//		@SuppressWarnings("unused")
//		ArrayList<clsRuleCompareResult> oMatch = moTestImages.associate(poSensorData, new clsIdentity() );
	}
	
}
