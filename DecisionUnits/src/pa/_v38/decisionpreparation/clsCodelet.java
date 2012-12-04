/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.storage.clsEnvironmentalImageMemory;
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
	protected clsEnvironmentalImageMemory moEnvironmentalImageMemory;
	protected clsWordPresentationMesh moEnvironmentalImage;	//Current environmental image
	protected clsShortTermMemory moShortTermMemory;	//Current STM, in order to get the previous actions
	protected clsCodeletHandler moCodeletHandler;		//The codelethandler, in order to execute other codelets within this one
	protected String moCodeletName = "";
	
	protected ArrayList<clsConditionGroup> moPreconditionGroupList = new ArrayList<clsConditionGroup>();
	protected ArrayList<clsConditionGroup> moPostConditionGroupList = new ArrayList<clsConditionGroup>();
	
	
	
	public clsCodelet (clsCodeletHandler poCodeletHandler) {
		moCodeletHandler=poCodeletHandler;
		
		moEnvironmentalImageMemory = moCodeletHandler.getMoEnvironmentalImageMemory();
		moEnvironmentalImage=moCodeletHandler.getEnvironmentalImage();
		moShortTermMemory=moCodeletHandler.getShortTermMemory();
		
		moGoal=clsMeshTools.getNullObjectWPM();
		
		this.setPreconditions();
		this.setPostConditions();
		this.moCodeletName = this.getClass().getSimpleName();
		
		//Register this codelet in the list
		this.register();
	}
	
	public void startCodelet() {
		removeTriggerCondition();
		this.processGoal();
		
		//moGoal=clsMeshTools.getNullObjectWPM();
	}
	
	protected abstract void processGoal();
	
	protected abstract void setPreconditions();
	
	protected abstract void setPostConditions();
	
	protected abstract void removeTriggerCondition();
	
	private double checkMatchingPreconditions(clsConditionGroup poForeignConditionGroup) {
		//double rResult = 0.0;
		
		double rBestMatch = 0.0;
		
		for (clsConditionGroup oGroup : this.moPreconditionGroupList) {
			double rCurrentMatch = oGroup.checkConditionGroupMatch(poForeignConditionGroup);
			if (rCurrentMatch>=1.0 && rBestMatch<rCurrentMatch) {
				rBestMatch=rCurrentMatch;
				//bResult=true;
				//break;
			}
		}
		
		return rBestMatch;
	}
	
	public double checkMatchingPreconditions(clsWordPresentationMesh poGoal) {
		clsConditionGroup oGroupFromGoal = new clsConditionGroup(clsGoalTools.getCondition(poGoal));
		
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
		
		if (moPostConditionGroupList.isEmpty()==false) {
			oResult+= ", Postconditions: ";
			for (clsConditionGroup oC :moPostConditionGroupList) {
				oResult += oC.toString() + "; ";
			}
		}
		
		if (this.moGoal.isNullObject()==false) {
			oResult+="; Goal: " + this.moGoal.toString();
		}
		
		
		return oResult;
	}
	

	
	
}
