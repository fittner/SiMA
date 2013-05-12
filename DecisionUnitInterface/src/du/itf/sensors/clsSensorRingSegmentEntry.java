package du.itf.sensors;


import java.util.ArrayList;

import du.enums.eEntityType;
import du.enums.eShapeType;

import bfg.tools.shapes.clsPolarcoordinate;
import bfg.utils.enums.eCount;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:56:28
 * 
 */
public abstract class clsSensorRingSegmentEntry extends clsSensorExtern {
	protected clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	protected eEntityType mnEntityType = eEntityType.UNDEFINED;
	protected eShapeType mnShapeType = eShapeType.UNDEFINED;
	protected String moEntityId = "";
	protected eCount mnNumEntitiesPresent = eCount.UNDEFINED; 
	
	public clsPolarcoordinate getPolarcoordinate() {
		return moPolarcoordinate;
	}
	public void setPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
		moPolarcoordinate = poPolarcoordinate;
	}
	
	public eEntityType getEntityType() {
		return mnEntityType;
	}
	public void setEntityType(eEntityType pnEntityType) {
		mnEntityType = pnEntityType;
	}
	
	public eShapeType getShapeType() {
		return mnShapeType;
	}
	public void setShapeType(eShapeType pnShapeType) {
		mnShapeType = pnShapeType;
	}
	
	public String getEntityId() {
		return moEntityId;
	}
	public void setEntityId(String pnEntityId) {
		moEntityId = pnEntityId;
	}
	
	public eCount getNumEntitiesPresent() {
		return mnNumEntitiesPresent;
	}
	public void setNumEntitiesPresent(eCount pnNumEntitiesPresent) {
		mnNumEntitiesPresent = pnNumEntitiesPresent;
	}
		 
	public clsSensorRingSegmentEntry() {
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
	 * @see decisionunit.itf.sensors.clsDataBase#logXML()
	 */
	@Override
	public String logXML() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
}
