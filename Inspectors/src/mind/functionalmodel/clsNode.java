/**
 * clsModule.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 12:10:26
 */
package mind.functionalmodel;

import java.util.ArrayList;

import base.modules.clsModuleBase;

/**
 * The Node object, If we change again from E->F or alike... change the prefix here too!!!
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
	public int mnCol;
	public int mnRow;
	public boolean mnAdded;
	private ArrayList<clsConnection> moNextModules;
	
	public clsNode(Integer poId, int pnCol, int pnRow, clsModuleBase poModule) {
		moId = poId;
		
		moName = beautifyName(poModule.getClass().getSimpleName().substring(4));
		moDescription = poModule.getDescription();
		mePsychicInstance = ePsychicInstance.valueOf( poModule.getPsychicInstances().toString() );
		meInformationProcessingType = eInformationProcessingType.valueOf( poModule.getProcessType().toString() );
		
		moNextModules = new ArrayList<clsConnection>();
		mnCol = pnCol;
		mnRow = pnRow;
		mnAdded = false;
	}
	
	private String beautifyName(String poName) {
		poName = poName.replace("_", " ");
		String result = "";
		
		for (int i=0; i<poName.length(); i++) {
			char temp = poName.charAt(i);
			if (Character.isUpperCase(temp)) {
				result += " "+temp;
			} else {
				result += temp;
			}
			
		}
		
		return result.trim();
	}
	
	public void setCoords(int pnCol, int pnRow) {
		mnCol = pnCol;
		mnRow = pnRow;
	}
	
	public ArrayList<clsConnection> getNextModules() {
		return moNextModules;
	}
	
	public void addNextModule(clsConnection poConnection) {
		moNextModules.add(poConnection);
	}
	
	public String getName(boolean pnShort) {
		if (pnShort) {
			return "F"+moId;
		} else {
			return "F"+moId+": "+moName;
		}
	}
	
	@Override
	public String toString() {
		return "F"+moId+": "+moName;
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
