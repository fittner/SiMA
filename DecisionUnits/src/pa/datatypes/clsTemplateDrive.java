/**
 * clsTPDrive.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 28.09.2009, 14:32:21
 */
package pa.datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import enums.pa.eContext;
import enums.pa.eDriveContent;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 14:32:21
 * 
 */
public class clsTemplateDrive {

	public int mnId;
	public String moName;
	public String moDescription;
	public eDriveContent meDriveContent;
	
	public ArrayList<clsAffectCandidatePart> moAffectCandidate = new ArrayList<clsAffectCandidatePart>();
	public HashMap<eContext, clsLifeInstinctRatio> moLifeInstinctRatio = new HashMap<eContext, clsLifeInstinctRatio>();
	public ArrayList<clsDriveObject> moDriveObjects = new ArrayList<clsDriveObject>();
	
	public String logHTML() {
		
		String oLogStream = "";
		oLogStream += "<b color='FF0000'>Content: " + meDriveContent.toString() + "</b><br><table width=\"600px\">";
		oLogStream += "<thead><tr align=\"center\"><th width=\"250px\">Affect candidates</th><th width=\"250px\">Life instinct ratio</th><th>Drive objects</th></tr></thead><tr><td>";
		
		oLogStream += "<table>";
		oLogStream += "<thead><tr align=\"center\"><th>SensorType</th><th>ValueType</th><th>Ratio</th></tr></thead>";
		for( clsAffectCandidatePart oAC :moAffectCandidate) {
			oLogStream += "<tr><td>";
			oLogStream += oAC.meSensorType + "</td><td>" + oAC.moValueType + "</td><td>" + oAC.moRatio;
			oLogStream += "</td></tr>";
		}
		oLogStream += "</table></td><td>";

		oLogStream += "<table>";
		oLogStream += "<thead><tr align=\"center\"><th>Context</th><th>Anal</th><th>Genital</th><th>Oral</th><th>Phallic</th></tr></thead>";
		for( Map.Entry<eContext, clsLifeInstinctRatio> oIR : moLifeInstinctRatio.entrySet()) {
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
