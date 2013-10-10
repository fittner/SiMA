/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eCondition;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:11:04
 * 
 */
public class clsConditionGroup {
	private ArrayList<eCondition> moConditionList = new ArrayList<eCondition>();

	public clsConditionGroup() {
		
	}
	
	public clsConditionGroup(ArrayList<eCondition> poConditionList) {
		moConditionList=poConditionList;
	}
	
	public clsConditionGroup(eCondition ... poDecisionTask) {
		for (int i=0; i<poDecisionTask.length;i++) {
			moConditionList.add(poDecisionTask[i]);
		}
	}
	
	
	/**
	 * @since 22.09.2012 17:44:07
	 * 
	 * @return the moConditionList
	 */
	public ArrayList<eCondition> getConditionList() {
		return moConditionList;
	}

	/**
	 * @since 22.09.2012 17:44:07
	 * 
	 * @param moConditionList the moConditionList to set
	 */
	public void setConditionList(ArrayList<eCondition> moConditionList) {
		this.moConditionList = moConditionList;
	}
	
	public boolean checkIfConditionExists(eCondition poCondition) {
		boolean bResult = false;
		
		if (moConditionList.contains(poCondition)==true) {
			bResult=true;
		}
		
		return bResult;
	}
	
	public void addCondition(eCondition poCondition) {
		if (checkIfConditionExists(poCondition)==false) {
			moConditionList.add(poCondition);
		}
	}
	
	public void removeCondition(eCondition poCondition) {
		if (checkIfConditionExists(poCondition)==true) {
			moConditionList.remove(poCondition);
		}
	}
	
	public double checkConditionGroupMatch(clsConditionGroup poGroup) {
		double rPreliminaryResult = 0.0;
		double rResult = 0.0;
		
		double rMatchCount = 0.0;
		double rNumberOfRecords = this.moConditionList.size();
		for (eCondition oForeignCondition : poGroup.getConditionList()) {
			if (this.moConditionList.contains(oForeignCondition)==true) {
				rMatchCount++;
			}
		}
		
		rPreliminaryResult = rMatchCount/rNumberOfRecords;	//If 1.0, then all of them were matched
		
		//If there is a perfect match, check how specialized this match is
		if (rPreliminaryResult==1.0) {
			double rSpecializedMatchCount = 0.0;
			double rNumberOfRecordsForeignList = poGroup.getConditionList().size();
			
			for (eCondition oThisCondition : this.moConditionList) {
				if (poGroup.getConditionList().contains(oThisCondition)==true) {
					rSpecializedMatchCount++;
				}
			}
			
			rResult = rPreliminaryResult + rSpecializedMatchCount/rNumberOfRecordsForeignList;
	
		} else {
			rResult = rPreliminaryResult;
		}
		
		return rResult;
	}
	
	@Override
	public String toString() {
		String oResult = "";
		
		oResult+=moConditionList.toString();
		
		return oResult;
	}
}
