/**
 * clsModule.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 12:10:26
 */
package inspectors.mind.pa.functionalmodel;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 12:10:26
 * 
 */
public class clsNode {
	public final ePsychicInstance mePsychicInstance;
	public final eInformationProcessingType meInformationProcessingType;
	public final String moName;
	public final Integer moId;
	public final String moDescription;
	public final int mnCol;
	public final int mnRow;
	public boolean mnAdded;
	private ArrayList<clsConnection> moNextModules;
	
	public clsNode(Integer poId, String poName, ePsychicInstance pePsychicInstance, eInformationProcessingType peInformationProcessingType, 
					int pnCol, int pnRow, String poDescription) {
		moId = poId;
		moName = poName;
		moDescription = poDescription;
		mePsychicInstance = pePsychicInstance;
		meInformationProcessingType = peInformationProcessingType;
		
		moNextModules = new ArrayList<clsConnection>();
		mnCol = pnCol;
		mnRow = pnRow;
		mnAdded = false;
	}
	
	public ArrayList<clsConnection> getNextModules() {
		return moNextModules;
	}
	
	public void addNextModule(clsConnection poConnection) {
		moNextModules.add(poConnection);
	}
	
	public String getName(boolean pnShort) {
		if (pnShort) {
			return "E"+moId;
		} else {
			return "E"+moId+": "+moName;
		}
	}
	
	@Override
	public String toString() {
		return "E"+moId+": "+moName;
	}
	
	public void resetAdded() {
		if (mnAdded != false) {
			mnAdded = false;
			for (clsConnection oC:moNextModules) {
				oC.moTarget.resetAdded();
			}
		}
	}
}
