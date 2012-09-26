/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:05:23
 * 
 */
public abstract class clsCodelet {
	protected clsWordPresentationMesh moGoal;	//Goal to be processed
	protected clsWordPresentationMesh moEnvironmentalImage;	//Current environmental image
	protected clsShortTermMemory moShortTermMemory;	//Current STM, in order to get the previous actions
	protected clsCodeletHandler moCodeletHandler;		//The codelethandler, in order to execute other codelets within this one
	protected String moCodeletName;
	
	protected ArrayList<clsConditionGroup> moPreconditionGroupList = new ArrayList<clsConditionGroup>();
	protected ArrayList<clsConditionGroup> moPostConditionGroupList = new ArrayList<clsConditionGroup>();
	
	
	
	public clsCodelet (clsWordPresentationMesh poEnvironmentalImage, clsShortTermMemory poShortTermMemory, clsCodeletHandler poCodeletHandler) {
		moEnvironmentalImage=poEnvironmentalImage;
		moShortTermMemory=poShortTermMemory;
		moCodeletHandler=poCodeletHandler;
		
		moGoal=clsMeshTools.getNullObjectWPM();
		
		this.setPreconditions();
		this.setPostConditions();
		this.setName();
		
		//Register this codelet in the list
		this.register();
	}
	
	public void startCodelet() {
		
		this.processGoal();
		
		//moGoal=clsMeshTools.getNullObjectWPM();
	}
	
	protected abstract void processGoal();
	
	protected abstract void setPreconditions();
	
	protected abstract void setPostConditions();
	
	protected abstract void setName();
	
	private boolean checkMatchingPreconditions(clsConditionGroup poForeignConditionGroup) {
		boolean bResult = false;
		
		for (clsConditionGroup oGroup : this.moPreconditionGroupList) {
			if (oGroup.checkConditionGroupMatch(poForeignConditionGroup)>=1.0) {
				bResult=true;
				break;
			}
		}
		
		return bResult;
	}
	
	public boolean checkMatchingPreconditions(clsWordPresentationMesh poGoal) {
		clsConditionGroup oGroupFromGoal = new clsConditionGroup(clsGoalTools.getTaskStatus(poGoal));
		
		return checkMatchingPreconditions(oGroupFromGoal);
	}
	
	public void assignGoal(clsWordPresentationMesh poGoal) {
		this.moGoal=poGoal;
	}
	
	public void clearGoal() {
		this.moGoal=clsMeshTools.getNullObjectWPM();
	}
	
	public void register() {
		this.moCodeletHandler.addToCodeletList(this);
	}
	
	@Override
	public String toString() {
		String oResult = "";
		
		oResult+=this.moCodeletName + ", Preconditions: ";
		for (clsConditionGroup oC :moPreconditionGroupList) {
			oResult += oC.toString() + "; ";
		}
		
		oResult+=this.moCodeletName + ", Postconditions: ";
		for (clsConditionGroup oC :moPostConditionGroupList) {
			oResult += oC.toString() + "; ";
		}
		
		oResult+="\nGoal: " + this.moGoal.toString();
		
		return oResult;
	}
	
	
}
