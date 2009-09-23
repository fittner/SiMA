/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package decisionunit.itf.sensors;

import java.util.Formatter;

import enums.eEntityType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsEatableAreaEntries extends clsSensorRingSegmentEntries {
	
	public eEntityType mnTypeOfFirstEntity = eEntityType.UNDEFINED;
	
		
	public clsEatableAreaEntries() {
		moClassName = "SegmentEntry";
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.09.2009, 13:18:25
	 * 
	 * @see decisionunit.itf.sensors.clsSensorRingSegmentEntries#logHTML()
	 */
	@Override
	public String logHTML() {
		String oResult = "";
		Formatter oDoubleFormatter = new Formatter();

		
		//mnEntityType
		oResult += "x:"+oDoubleFormatter.format("%.2f",moPolarcoordinate.getVector().mrX);
		//TODO (zeilinger): use the formatter in a better way!!!
		oDoubleFormatter = new Formatter();
		oResult += " y:"+oDoubleFormatter.format("%.2f",moPolarcoordinate.getVector().mrY);
		oDoubleFormatter = new Formatter();
		oResult += " deg:"+oDoubleFormatter.format("%.5f",moPolarcoordinate.moAzimuth.getDegree())+" ";
		oDoubleFormatter = new Formatter();
		oResult += mnTypeOfFirstEntity.toString();
		oResult += " - ID="+moEntityId+" : ";

		return oResult;	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.09.2009, 13:18:25
	 * 
	 * @see decisionunit.itf.sensors.clsSensorRingSegmentEntries#logXML(int)
	 */
	@Override
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
	
		logEntry += clsDataBase.addXMLTag("Polarcoordinate", moPolarcoordinate.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityType", mnTypeOfFirstEntity.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityId", new Integer(moEntityId).toString());
		
		logEntry += "</Entry>";

		return logEntry;	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.09.2009, 13:18:25
	 * 
	 * @see decisionunit.itf.sensors.clsSensorRingSegmentEntries#toString()
	 */
	@Override
	public String toString() {
		String oResult = "";
		oResult += moClassName+": type "+mnTypeOfFirstEntity+" | id "+moEntityId+" | direction "+moPolarcoordinate;
		return oResult;
	}

}
