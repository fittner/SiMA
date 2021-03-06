/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import general.datamanipulation.GeneralSortingTools;

import java.util.ArrayList;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsPair;
import base.tools.ElementNotFoundException;
import logger.clsLogger;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsActionCodelet;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsConsequenceCodelet;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDecisionCodelet;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsInitCodelet;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:06:06
 * 
 */
public class clsCodeletHandler {
	
    private static Logger log = clsLogger.getLog("DecisionPreparation");
	
	private final clsWordPresentationMesh moEnvironmentalImage;	//Current environmental image
	

	private final clsEnvironmentalImageMemory moEnvironmentalImageMemory;
	

	private final clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory;	//Current STM, in order to get the previous actions

	private ArrayList<clsWordPresentationMeshPossibleGoal> moGoalListFromF51; //= new ArrayList<clsWordPresentationMeshGoal>();
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemoriesFromF51 = new ArrayList<clsWordPresentationMesh>();
	
	private DT1_PsychicIntensityBuffer libidoBuffer;

    private ArrayList<clsActionCodelet> moActionCodeletList = new ArrayList<clsActionCodelet>();
	private ArrayList<clsDecisionCodelet> moDecisionCodeletList = new ArrayList<clsDecisionCodelet>();
	private ArrayList<clsInitCodelet> moInitCodeletList = new ArrayList<clsInitCodelet>();
	private ArrayList<clsConsequenceCodelet> moConsequenceCodeletList = new ArrayList<clsConsequenceCodelet>();
	
	
	public clsCodeletHandler(clsEnvironmentalImageMemory poEnvironmentalImageMemory, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTermMemory, DT1_PsychicIntensityBuffer libidoBuffer) {
		//Get the init references
		moEnvironmentalImageMemory=poEnvironmentalImageMemory;
		
		this.moEnvironmentalImage=moEnvironmentalImageMemory.getEnvironmentalImage();
		this.moShortTermMemory=poShortTermMemory;
		
		this.libidoBuffer = libidoBuffer;
	}

	
	/**
	 * @since 01.10.2012 16:32:05
	 * 
	 * @return the moEnvironmentalImage
	 */
	public clsWordPresentationMesh getEnvironmentalImage() {
		return moEnvironmentalImage;
	}


//	/**
//	 * @since 01.10.2012 16:32:05
//	 * 
//	 * @param moEnvironmentalImage the moEnvironmentalImage to set
//	 */
//	public void setEnvironmentalImage(clsWordPresentationMesh moEnvironmentalImage) {
//		this.moEnvironmentalImage = moEnvironmentalImage;
//	}
	
	/**
	 * @since 01.10.2012 16:32:33
	 * 
	 * @return the moShortTermMemory
	 */
	public clsShortTermMemory<clsWordPresentationMeshMentalSituation> getShortTermMemory() {
		return moShortTermMemory;
	}


//	/**
//	 * @since 01.10.2012 16:32:33
//	 * 
//	 * @param moShortTermMemory the moShortTermMemory to set
//	 */
//	public void setShortTermMemory(clsShortTermMemory moShortTermMemory) {
//		this.moShortTermMemory = moShortTermMemory;
//	}
	
	
	/**
	 * @since 01.10.2012 20:07:37
	 * 
	 * @return the moInitCodeletList
	 */
	public ArrayList<clsInitCodelet> getInitCodeletList() {
		return moInitCodeletList;
	}


	/**
	 * @since 01.10.2012 20:07:37
	 * 
	 * @param moInitCodeletList the moInitCodeletList to set
	 */
	public void setInitCodeletList(ArrayList<clsInitCodelet> moInitCodeletList) {
		this.moInitCodeletList = moInitCodeletList;
	}


	/**
	 * @since 01.10.2012 20:07:41
	 * 
	 * @return the moConsequenceCodeletList
	 */
	public ArrayList<clsConsequenceCodelet> getConsequenceCodeletList() {
		return moConsequenceCodeletList;
	}


	/**
	 * @since 01.10.2012 20:07:41
	 * 
	 * @param moConsequenceCodeletList the moConsequenceCodeletList to set
	 */
	public void setConsequenceCodeletList(ArrayList<clsConsequenceCodelet> moConsequenceCodeletList) {
		this.moConsequenceCodeletList = moConsequenceCodeletList;
	}

	/**
	 * @since 23.09.2012 12:10:38
	 * 
	 * @return the moCodeletList
	 */
	public ArrayList<clsActionCodelet> getActionCodeletList() {
		return moActionCodeletList;
	}
	
	public ArrayList<clsDecisionCodelet> getDecisionCodeletList() {
		return moDecisionCodeletList;
	}
	
	/**
	 * @since 01.10.2012 16:37:16
	 * 
	 * @return the moGoalListFromF51
	 */
	public ArrayList<clsWordPresentationMeshPossibleGoal> getGoalListFromF51() {
		return moGoalListFromF51;
	}


	/**
	 * @since 01.10.2012 16:37:16
	 * 
	 * @param moGoalListFromF51 the moGoalListFromF51 to set
	 */
	public void setGoalListFromF51(ArrayList<clsWordPresentationMeshPossibleGoal> moGoalListFromF51) {
		this.moGoalListFromF51 = moGoalListFromF51;
	}


	/**
	 * @since 23.09.2012 12:10:38
	 * 
	 * @param moCodeletList the moCodeletList to set
	 */
	public void setActionCodeletList(ArrayList<clsActionCodelet> poActionCodeletList) {
		this.moActionCodeletList = poActionCodeletList;
	}
	
	public void setDecisionCodeletList(ArrayList<clsDecisionCodelet> poDecisionCodeletList) {
		this.moDecisionCodeletList = poDecisionCodeletList;
	}
	
	/**
	 * @since 01.10.2012 20:31:58
	 * 
	 * @return the moEnvironmentalImageMemory
	 */
	public clsEnvironmentalImageMemory getMoEnvironmentalImageMemory() {
		return moEnvironmentalImageMemory;
	}

	public DT1_PsychicIntensityBuffer getLibidoBuffer() {
	    return libidoBuffer;
	}
	
	public void setLibidoBuffer(DT1_PsychicIntensityBuffer buffer){
	    libidoBuffer=buffer;
	}
	public void addToCodeletList(clsCodelet poCodelet) {
		String oList = "";
		if (poCodelet instanceof clsActionCodelet) {
			this.moActionCodeletList.add((clsActionCodelet) poCodelet);
			oList = "Action";
		} else if (poCodelet instanceof clsDecisionCodelet) {
			this.moDecisionCodeletList.add((clsDecisionCodelet) poCodelet);
			oList = "Decision";
		} else if (poCodelet instanceof clsConsequenceCodelet) {
			this.moConsequenceCodeletList.add((clsConsequenceCodelet) poCodelet);
			oList = "Consequence";
		} else if (poCodelet instanceof clsInitCodelet) {
			this.moInitCodeletList.add((clsInitCodelet) poCodelet);
			oList = "Init";
		}
		
		log.info(oList + " codelet registered: " + poCodelet.toStringExtended());
		
	}
	
	public void initF51(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalListFromF51) {
		moGoalListFromF51 = poGoalListFromF51;
	}
	
	public void initF52() {
		
	}
	
	public ArrayList<clsCodelet> getMatchingActionCodelets(Object poTheExecutingObject, clsWordPresentationMeshPossibleGoal poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
		ArrayList<clsActionCodelet> oPrelResult = sortAndFilterRatedStructures(poTheExecutingObject, this.moActionCodeletList, poGoal);
		oResult.addAll(oPrelResult);
		
		return oResult;
	}
	
	public ArrayList<clsCodelet> getMatchingDecisionCodelets(Object poTheExecutingObject, clsWordPresentationMeshPossibleGoal poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
		ArrayList<clsDecisionCodelet> oPrelResult = sortAndFilterRatedStructures(poTheExecutingObject, this.moDecisionCodeletList, poGoal);
		oResult.addAll(oPrelResult);
		
		
		return oResult;
	}
	
	public ArrayList<clsCodelet> getMatchingConsequenceCodelets(Object poTheExecutingObject, clsWordPresentationMeshPossibleGoal poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
		ArrayList<clsConsequenceCodelet> oPrelResult = sortAndFilterRatedStructures(poTheExecutingObject, this.moConsequenceCodeletList, poGoal);
		oResult.addAll(oPrelResult);
		
		
		return oResult;
	}
	
	public ArrayList<clsCodelet> getMatchingInitCodelets(Object poTheExecutingObject, clsWordPresentationMeshPossibleGoal poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
		ArrayList<clsInitCodelet> oPrelResult = sortAndFilterRatedStructures(poTheExecutingObject, this.moInitCodeletList, poGoal);
		oResult.addAll(oPrelResult);
		
		
		return oResult;
	}
	
	private static <E extends clsCodelet> ArrayList<E> sortAndFilterRatedStructures(Object poTheExecutingObject, ArrayList<E> poInput, clsWordPresentationMeshPossibleGoal poGoal) {
		ArrayList<E> oResult = new ArrayList<E>();
		
		ArrayList<clsPair<Double, E>> oUnsortList = new ArrayList<clsPair<Double, E>>();
		//ArrayList<clsPair<Double, E>> oSortList = new ArrayList<clsPair<Double, E>>();
		
		for (E oCodelet : poInput) {
			double rMatch = oCodelet.checkMatchingPreconditions(poGoal);
			if (oCodelet.checkMatchingPreconditions(poGoal)>=1.0 && (oCodelet != poTheExecutingObject)) {	//Exclude the executing object if a codelet executes another codelet in the list
				oUnsortList.add(new clsPair<Double, E>(rMatch, oCodelet));
			}
		}
		
		oResult = GeneralSortingTools.sortAndFilterRatedStructures(oUnsortList, -1);
		
//		for (clsPair<Double, E> oSortPair : oSortList) {
//			oResult.add(oSortPair.b);
//		}
		
		return oResult;
		
	}
	
	
	/**
	 * Execute the number of codelets in the codeletlist
	 * 
	 * If the number of codelets are -1, then execute all codelets
	 * 
	 * (wendt)
	 *
	 * @since 01.10.2012 21:20:16
	 *
	 * @param oCodeletList
	 * @param poGoal
	 * @param numberOfCodeletsToExecute
	 */
	public void executeCodeletListOnGoal(ArrayList<clsCodelet> oCodeletList, clsWordPresentationMeshPossibleGoal poGoal, int numberOfCodeletsToExecute) {
		int nInit=0;
		int nMax = oCodeletList.size();
		if (numberOfCodeletsToExecute >= 0) {
			nMax = numberOfCodeletsToExecute;
		}
		
		for (clsCodelet oCodelet : oCodeletList) {
			if (nInit >= nMax) {
				break;
			}
			
			oCodelet.assignGoal(poGoal);
			try {
                oCodelet.startCodelet();
            } catch (ElementNotFoundException e) {
                log.error(e.getMessage());
            }
			log.debug("Executed codelet "  + oCodelet.toString());
			oCodelet.clearGoal();
			
			nInit++;
		}
	}
	
	/**
	 * Execute matching codelets
	 * 
	 * A codelet can execute other codelets within the same codelet handler. Therefore the this pointer of the executing object is passed poTheExecutingObject
	 * 
	 * (wendt)
	 *
	 * @since 30.12.2012 21:02:49
	 *
	 * @param poTheExecutingObject: always this pointer
	 * @param poGoal
	 * @param poCodeletType
	 * @param numberOfCodeletsToExecute: From the matching codeletlist, execute a defined number or all if set (-1)
	 * @param numberOfLoops: Run the matchloop one or more times. If set (-1) then run as long as codelets match
	 */
	public void executeMatchingCodelets(Object poTheExecutingObject, clsWordPresentationMeshPossibleGoal poGoal, eCodeletType poCodeletType, int numberOfCodeletsToExecute, int numberOfLoops) {
		boolean runLoop=false;
		int loopCount = 0;
	    //Execute at least once
		do {
	        ArrayList<clsCodelet> cList = new ArrayList<clsCodelet>();
            String oTypeString = "";
            
            //Get list of matching codelets
            if (poCodeletType.equals(eCodeletType.ACTION)) {
                cList = this.getMatchingActionCodelets(poTheExecutingObject, poGoal);
                oTypeString = "Execute action codelets: ";
            } else if (poCodeletType.equals(eCodeletType.DECISION)) {
                cList = this.getMatchingDecisionCodelets(poTheExecutingObject, poGoal);
                oTypeString = "Execute decision codelets: ";
            } else if (poCodeletType.equals(eCodeletType.CONSEQUENCE)) {
                cList = this.getMatchingConsequenceCodelets(poTheExecutingObject, poGoal);
                oTypeString = "Execute conseqeunce codelets: ";
            } else if (poCodeletType.equals(eCodeletType.INIT)) {
                cList = this.getMatchingInitCodelets(poTheExecutingObject, poGoal);
                oTypeString = "Execute init codelets: ";
            }
            log.trace(oTypeString + cList.toString());
            
            //Execute the list
            this.executeCodeletListOnGoal(cList, poGoal, numberOfCodeletsToExecute);
            
            //Check if loop shall be run again
            loopCount++;
            if (cList.isEmpty()==true) {
                runLoop=false;
            } else {
                //Continue executing if
                //1. maxnumber <0 or
                //2. loopcount<max and max>1
                if ((numberOfLoops>1 && loopCount<numberOfLoops) || (numberOfLoops<0)) {
                    runLoop=true;
                } else {
                    runLoop = false;
                }
            }
            
	    } while (runLoop==true);
	}
	
}
