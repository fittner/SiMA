package students.borer.episodicMemory;

import students.borer.episodicMemory.tempframework.clsDrive;
import students.borer.episodicMemory.tempframework.enumTypeLevelDrive;

/**
 * The class clsElementDrive represents a feature element drive. It represents one drive (clsDrive) and implements the additional functionalities inherited from the abstract class clsFeatureElement $Revision: 768 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-07-19 23:12:25 +0200 (Do, 19 Jul 2007) $: Date of last commit
 */
public class clsElementDrive extends clsFeatureElement {
	private clsDrive moDrive;
	private float mrDeltaToPrevSituation;
	private float mrDeltaToPrevEvent;
	private boolean mnTrigger; // Trigger: set if this Drive caused an Encoding

	/**
	 * The parameter Delta0 of the gaussian matching function
	 */
	public static float MATCH_DELTA0 = 0.13f;

	public clsElementDrive(clsDrive poDrive) {
		super();
		moDrive = poDrive;
		mnTrigger = false;
		mrDeltaToPrevSituation = 0;
		mrDeltaToPrevEvent = 0;
	}
	public clsDrive getDrive() {
		return moDrive;
	}
	private void setSalience() {
		// salience = 0, if |rLevel| < 0.7 ; otherwise linear function until 1 for rLevel = 1
		float rSalience =0;
		float rLevel = Math.abs( moDrive.getInternalValue() );
		if(rLevel > 0.7) {
			rSalience = 2 * rLevel - 1;
		}
		moSalience.set(rSalience);
	}
	/**
	 * Sets the salience and checks, if the encoding of an event on the basis of this drive should be triggered
	 * Currently: trigger encoding on threshold (on change event)
	 */
	@Override
	public boolean triggerEncoding(clsFeatureElement poPrevDrive) {
		clsElementDrive oPrevDrive = (clsElementDrive)poPrevDrive;
		//calculates difference to previous Drive
		mrDeltaToPrevSituation = moDrive.getInternalValue() - oPrevDrive.getDrive().getInternalValue();
		
		setSalience();
		triggerOnThreshold(oPrevDrive); // "trigger on change event"
//		triggerOnPrevSituation(oPrevDrive);
//		triggerOnPrevEvent(oPrevDrive);
		if(mnTrigger) {
//			System.out.println("Encoded DriveType: "+moDrive.getType());
		}
//		decaySalience(poPrevDrive);		
		return mnTrigger;
	}
	private void triggerOnSalience(){
		if(moSalience.get() > 0.5) {
			mnTrigger = true;
		}
	}
	private void triggerOnThreshold(clsElementDrive poPrevDrive) {
		// trigger on change event
		//float rLevel = moDrive.getInternalValue().getValue();
		
		if( moDrive.getLevel() >= enumTypeLevelDrive.TLEVELDRIVE_VERYHIGH) {
			// check if Drive was below the pos. threshold in the previous situation
			if(poPrevDrive.moDrive.getLevel() < enumTypeLevelDrive.TLEVELDRIVE_VERYHIGH) {
				mnTrigger = true;
			}
		}
		else if (moDrive.getLevel() <= enumTypeLevelDrive.TLEVELDRIVE_VERYLOW){
			// check if Drive was above the neg. threshold in the previous situation
			if(poPrevDrive.moDrive.getLevel() > enumTypeLevelDrive.TLEVELDRIVE_VERYLOW) {
				mnTrigger = true;
			}
		}
		if( poPrevDrive.moDrive.getLevel() >= enumTypeLevelDrive.TLEVELDRIVE_VERYHIGH) {
			// check if Drive is below the pos. threshold in the situation
			if(moDrive.getLevel() < enumTypeLevelDrive.TLEVELDRIVE_VERYHIGH) {
				mnTrigger = true;
			}
		}
		else if (poPrevDrive.moDrive.getLevel() <= enumTypeLevelDrive.TLEVELDRIVE_VERYLOW){
			// check if Drive is above the neg. threshold in the situation
			if(moDrive.getLevel() > enumTypeLevelDrive.TLEVELDRIVE_VERYLOW) {
				mnTrigger = true;
			}
		}
	}
	private void triggerOnPrevSituation(clsElementDrive poPrevDrive){
		if(mrDeltaToPrevSituation >= 0.5f) {
			mnTrigger = true;
		}
	}
	private void triggerOnPrevEvent(clsElementDrive poPrevDrive){
		//calculates difference to the previous occurance of this type of Drive that triggered an event
		if(mnTrigger) {
			mrDeltaToPrevEvent = 0; // this Drive caused encoding -> reset delta to previous event
		}
		else {
			mrDeltaToPrevEvent = poPrevDrive.mrDeltaToPrevEvent + mrDeltaToPrevSituation;  // sums up the difference to the last such event
			if(mrDeltaToPrevEvent >= 0.5f) {
				mnTrigger = true;
			}
		}
		
	}
	/**
	 * Calculates the match of this drive to the one inidicated in the parameter on the basis of
	 * a gaussian matching function
	 * @param poDrive The drive of the retrieval cue (the cue element)
	 * @return A clsMatchFeatureElement object representing the match of this element to the cue element
	 */
	@Override
	public clsMatchFeatureElement getMatch(clsFeatureElement poDrive) {
		// compares this Drive with the parameter and returns the match
		clsElementDrive oDriveCue = (clsElementDrive)poDrive;
		float rDiff = Math.abs( moDrive.getInternalValue()- oDriveCue.getDrive().getInternalValue() );
		float rMatch = (float)Math.exp(-0.5 * Math.pow(rDiff/MATCH_DELTA0, 2));
		return new clsMatchFeatureElement(oDriveCue, rMatch);
	}
	@Override
	public boolean checkIfSameType(clsFeatureElement poFeatElem) {
		if(poFeatElem instanceof clsElementDrive) {
			clsElementDrive oDrive = (clsElementDrive)poFeatElem;
			if(moDrive.getType() == oDrive.getDrive().getType()) {
				return true;
			}
		}
		return false;
	}
}
