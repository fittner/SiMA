/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.tools.ElementNotFoundException;
import logger.clsLogger;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:05:23
 * 
 */
public abstract class clsCodelet {
	protected clsWordPresentationMeshPossibleGoal moGoal;	//Goal to be processed
	protected clsEnvironmentalImageMemory moEnvironmentalImageMemory;
	protected clsWordPresentationMesh moEnvironmentalImage;	//Current environmental image
	protected clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory;	//Current STM, in order to get the previous actions
	protected clsCodeletHandler moCodeletHandler;		//The codelethandler, in order to execute other codelets within this one
    protected DT1_PsychicIntensityBuffer moPsychicIntensityBuffer; //Buffer of Psychic Intensity (the drive generation is based an these values)
	protected String moCodeletName = "";
	protected String moCodeletDescription = "";
	
	protected static Logger log = clsLogger.getLog("DecisionPreparation");
	
	protected ArrayList<clsConditionGroup> moPreconditionGroupList = new ArrayList<clsConditionGroup>();
	protected ArrayList<clsConditionGroup> moPostConditionGroupList = new ArrayList<clsConditionGroup>();
	
	
	
	public clsCodelet (clsCodeletHandler poCodeletHandler) {
		moCodeletHandler=poCodeletHandler;
		
		moEnvironmentalImageMemory = moCodeletHandler.getMoEnvironmentalImageMemory();
		moEnvironmentalImage=moCodeletHandler.getEnvironmentalImage();
		moShortTermMemory=moCodeletHandler.getShortTermMemory();
		
		moGoal=clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
		
		this.setPreconditions();
		this.setPostConditions();
		this.moCodeletName = this.getClass().getSimpleName();
		this.setDescription();
		
		//Register this codelet in the list
		this.register();
	}
	
	public void startCodelet() throws ElementNotFoundException {
		removeTriggerCondition();
		this.processGoal();
		
		//moGoal=clsMeshTools.getNullObjectWPM();
	}
	
	protected abstract void processGoal();
	
	protected abstract void setPreconditions();
	
	protected abstract void setPostConditions();
	
	protected abstract void setDescription();
	
	protected abstract void removeTriggerCondition() throws ElementNotFoundException;
	
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
	
	public double checkMatchingPreconditions(clsWordPresentationMeshPossibleGoal poGoal) {
		clsConditionGroup oGroupFromGoal = new clsConditionGroup(poGoal.getCondition());
		
		return checkMatchingPreconditions(oGroupFromGoal);
	}
	
	public void assignGoal(clsWordPresentationMeshPossibleGoal poGoal) {
		this.moGoal=poGoal;
	}
	
	public void clearGoal() {
		this.moGoal=clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
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
	
	/**
	 * This is an extended description of a codelet
	 * 
	 * (wendt)
	 *
	 * @since 27.12.2012 12:03:07
	 *
	 * @return
	 */
	public String toStringExtended() {
		String oResult = "";
		
		oResult += "==================================================\n";
		
		oResult+="Name: " + this.moCodeletName + "\n";
		oResult+="Description: " + this.moCodeletDescription + "\n";
		oResult+="Preconditions: \n";
		for (clsConditionGroup oC :moPreconditionGroupList) {
			oResult += oC.toString() + "\n";
		}
		
		//if (moPostConditionGroupList.isEmpty()==false) {
			oResult+= "Postconditions: \n";
			for (clsConditionGroup oC :moPostConditionGroupList) {
				oResult += oC.toString() + "\n";
			}
		//}
		
		oResult+="CodeletType: " + this.getClass().getSuperclass().getSimpleName().toString() + "\n";
		
		return oResult;
	}
	

	
	
}
