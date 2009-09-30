package symbolization.representationsysmbol;


import java.util.ArrayList;

import decisionunit.itf.sensors.clsSensorExtern;

import enums.eEntityType;
import enums.eShapeType;
import bfg.utils.enums.eCount;

public abstract class clsSensorRingSegmentEntries extends clsSensorExtern{
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public eShapeType mnShapeType = eShapeType.UNDEFINED;
	public eCount mnNumEntitiesPresent = eCount.UNDEFINED; 
		
	public clsSensorRingSegmentEntries() {
	}
	
	public abstract String logXML(int pnId); 
	
	@Override
	public abstract String toString(); 
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 14:22:09
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#getDataObjects()
	 */
	@Override
	public ArrayList<clsSensorExtern> getDataObjects() {
		ArrayList<clsSensorExtern> oRetVal = new ArrayList<clsSensorExtern>();
		oRetVal.add(this);
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 14:22:09
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#getMeshAttributeName()
	 */
	@Override
	public String getMeshAttributeName() {
		// TODO (zeilinger) - Auto-generated method stub
		return "mnEntityType";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 14:22:09
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#isContainer()
	 */
	@Override
	public boolean isContainer() {
		// TODO (zeilinger) - Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 14:22:09
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#logXML()
	 */
	@Override
	public String logXML() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
}
