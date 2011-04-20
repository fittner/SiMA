/**
 * clsTPDrive.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 28.09.2009, 14:32:21
 */
package pa._v19.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import du.enums.pa.eContext;
import du.enums.pa.eDriveContent;

import pa._v19.datatypes.clsDriveContentCategories;
import pa._v19.datatypes.clsDriveObject;
import pa._v19.enums.eDriveType;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 14:32:21
 * 
 */
@Deprecated
public class clsTemplateDrive {

	public int mnId;
	public String moName;
	public String moDescription;
	public eDriveType meDriveType;
	public eDriveContent meDriveContent;
	
	public ArrayList<clsAffectCandidateDefinition> moAffectCandidate = new ArrayList<clsAffectCandidateDefinition>();
	public HashMap<eContext, clsDriveContentCategories> moDriveContentRatio = new HashMap<eContext, clsDriveContentCategories>();
	public ArrayList<clsDriveObject> moDriveObjects = new ArrayList<clsDriveObject>();
	
	public String logHTML() {
		
		String moHighlightColor = "00AA00";
		String moDriveType = "(Libido / Life instinct)";
		if(meDriveType == eDriveType.DEATH) {
			moHighlightColor = "888888";
			moDriveType = "(Death instinct)";
		}
		
		String oLogStream = "";
		oLogStream += "<b color='"+moHighlightColor+"'>Content: " + meDriveContent.toString() + " " + moDriveType +"</b><br><table width=\"600px\">";
		oLogStream += "<thead><tr align=\"center\"><th width=\"250px\">Drive sources (Affect candidates)</th><th width=\"250px\">Drive content category</th><th>Drive objects</th></tr></thead><tr><td>";
		
		oLogStream += "<table>";
		oLogStream += "<thead><tr align=\"center\"><th>SensorType</th><th>Ratio</th><th>Max.value</th><th>Inverse</th></tr></thead>";
		for( clsAffectCandidateDefinition oAC :moAffectCandidate) {
			oLogStream += "<tr><td>";
			oLogStream += oAC.moSensorType + "</td><td>" + oAC.mrRatio + "</td><td>" + oAC.mrMaxValue + "</td><td>" + oAC.mnInverse;
			oLogStream += "</td></tr>";
		}
		oLogStream += "</table></td><td>";

		oLogStream += "<table>";
		oLogStream += "<thead><tr align=\"center\"><th>Context</th><th>Anal</th><th>Genital</th><th>Oral</th><th>Phallic</th></tr></thead>";
		for( Map.Entry<eContext, clsDriveContentCategories> oIR : moDriveContentRatio.entrySet()) {
			oLogStream += "<tr><td>";
			oLogStream += oIR.getKey() + "</td><td>" + oIR.getValue().getAnal() + "</td><td>" + oIR.getValue().getGenital() + "</td><td>" + oIR.getValue().getOral() + "</td><td>" + oIR.getValue().getPhallic();
			oLogStream += "</td></tr>";
		}
		oLogStream += "</table></td><td>";

		oLogStream += "<table>";
		oLogStream += "<thead><tr align=\"center\"><th>Context</th><th>Type</th></tr></thead>";
		for( clsDriveObject oDrv :moDriveObjects) {
			oLogStream += "<tr><td>";
			oLogStream += oDrv.meContext + "</td><td>" + oDrv.meType;
			oLogStream += "</td></tr>";
		}
		oLogStream += "</table></td></tr></table>";
		return oLogStream;
	}
}
