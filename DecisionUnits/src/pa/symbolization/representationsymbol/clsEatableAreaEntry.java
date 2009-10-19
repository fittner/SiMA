/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package pa.symbolization.representationsymbol;

import enums.eEntityType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsEatableAreaEntry extends decisionunit.itf.sensors.clsEatableAreaEntry implements itfGetMeshAttributeName, itfGetSymInterface, itfSymEatableAreaEntry {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.10.2009, 19:51:02
	 *
	 * @param pnEntityType
	 */
	public clsEatableAreaEntry(eEntityType pnEntityType) {
		super(pnEntityType);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:51:09
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetMeshAttributeName#getMeshAttributeName()
	 */
	@Override
	public String getMeshAttributeName() {
		return "mnEntityType";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:51:09
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymEatableAreaEntry";
	}

	
}
