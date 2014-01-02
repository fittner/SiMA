/**
 * CHANGELOG
 *
 * 31.08.2011 wendt - File created
 *
 */
package memorymgmt.shorttermmemory;

import inspector.interfaces.itfGraphData;
import inspector.interfaces.itfInspectorInternalState;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;

/**
 * This is a short time memory, which is used in the secondary process. 
 * 
 * @author wendt
 * 31.08.2011, 07:12:10
 * 
 */
public class clsShortTermMemory<T extends clsWordPresentationMesh> implements itfGraphData,itfInspectorInternalState {
	/** The variable for the short time memory */
	protected ArrayList<clsPair<Integer, T>> moShortTimeMemory;
	
	/** A value for how long content is saved in the short time memory */
	protected int mnMaxTimeValue; // = 60;
	/** Number of objects, which can be saved in the short time memory */
	protected int mnMaxMemorySize; // = 7;

	protected clsPair<Integer, T> moNullMemoryObjectWPM;
	
	/**
	 * Constructor, Init short time Memory with an empty arraylist
	 * 
	 * The values for the memory are set in clsPsychicApparatus
	 * 
	 * @author (wendt)
	 *
	 * @since 31.08.2011 07:15:02
	 *
	 */
	public clsShortTermMemory(int pnMaxTimeValue, int pnMaxMemorySize, T nullobject) {
		moShortTimeMemory = new ArrayList<clsPair<Integer, T>>();
		mnMaxTimeValue = pnMaxTimeValue;
		mnMaxMemorySize = pnMaxMemorySize;
		
		moNullMemoryObjectWPM = new clsPair<Integer, T>(-1, nullobject);
		
	}
	
	/**
	 * @since 16.11.2011 10:43:20
	 * 
	 * @return the moShortTimeMemory
	 */
	public ArrayList<clsPair<Integer, T>> getMoShortTimeMemory() {
		return moShortTimeMemory;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 31.08.2011 07:29:39
	 *
	 */
	public void updateTimeSteps() {
		ArrayList<clsPair<Integer, T>> oRemoveObjects = new ArrayList<clsPair<Integer, T>>();
		for (clsPair<Integer, T> oSingleMemory : moShortTimeMemory) {
			//Update the step count
			oSingleMemory.a++;
			//Remove memories, which are too old and haven´t been transferred to the long time memory
			if (oSingleMemory.a>mnMaxTimeValue) {
				oRemoveObjects.add(oSingleMemory);
			}
		}
		//Remove the overdue objects
		for (clsPair<Integer, T> oSingleRemoveObject : oRemoveObjects) {
			removeMemory(oSingleRemoveObject);
		}
	}
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 31.08.2011 07:31:20
	 *
	 * @param poRemoveMemory
	 */
	protected void removeMemory(clsPair<Integer, T> poRemoveMemory) {
		moShortTimeMemory.remove(poRemoveMemory);
	}
	
	/**
	 * Remove a memory by knowing the hashcode of the WPM, which shall be removed
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 21:16:59
	 *
	 * @param poRemoveMemory
	 */
	protected void removeMemory(T poRemoveMemory) {
		clsPair<Integer, T> oRemoveMemoryEntry = this.findMemory(poRemoveMemory);
		
		moShortTimeMemory.remove(oRemoveMemoryEntry);
	}
	
	/**
	 * Add new memory to the end of the list
	 * (wendt)
	 *
	 * @since 31.08.2011 08:48:01
	 *
	 * @param poRemoveMemory
	 */
	private void addMemory(clsPair<Integer, T> poAddMemory) {
		moShortTimeMemory.add(poAddMemory);
	}
	
	/**
	 * Save new structure to the short time memory if it does not exist already. Forced save is disable here
	 * (wendt)
	 *
	 * @since 31.08.2011 07:19:42
	 *
	 * @param poInput
	 */
	public void saveToShortTimeMemory(T poInput) {
		saveToShortTimeMemory(poInput, false);
	}
	
	/**
	 * Save a data structure to the short time memory. Options: Forced save, which saves the structure in any case or save only if the 
	 * data structure does not exist
	 * (wendt)
	 *
	 * @since 19.08.2011 14:32:56
	 *
	 * @param poInput
	 */
	public void saveToShortTimeMemory(T poInput, boolean forceSave) {
		//save only if this moID is not already saved
		
		//Check if this memory already exists
		clsPair<Integer, T> oFoundMemory  = findMemory(poInput);
		//Memory found and forced save true
		if ((oFoundMemory!=null) && (forceSave==true)) {
			//Here, the memory is replaced by the new memory, which may have some changed values
			//try {
				removeMemory(oFoundMemory);
				oFoundMemory.a = 0;
				oFoundMemory.b = poInput;
				T oAddPair = (T) poInput;
				addMemory(new clsPair<Integer, T>(0, oAddPair));
				
				
			//} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
			//	e.printStackTrace();
			//}
		//If there is free space in the short time memory, add the new memory
		} else if (moShortTimeMemory.size()<mnMaxMemorySize) {
			//try {
				T oAddPair = (T) poInput;
				addMemory(new clsPair<Integer, T>(0, oAddPair));
			//} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
			//	e.printStackTrace();
			//}
		//If there is no space in the short time memory, delete the oldest one
		} else if (moShortTimeMemory.size()>=mnMaxMemorySize) {
			clsPair<Integer, T> oObsoluteMemory = getMostObsoleteMemory();
			removeMemory(oObsoluteMemory);
			//try {
				T oAddPair = (T) poInput;
				addMemory(new clsPair<Integer, T>(0, oAddPair));
			//} catch (CloneNotSupportedException e) {
			//	// TODO (wendt) - Auto-generated catch block
			//	e.printStackTrace();
			//}
		}
		
	}

	/**
	 * Find a memory in the short time memory and return the whole data structure incl. step
	 * 
	 * Compare method used: java std object comparison
	 * 
	 * (wendt)
	 *
	 * @since 31.08.2011 08:09:45
	 *
	 * @param oToBeFound
	 * @return
	 */
	public clsPair<Integer, T> findMemory(T oToBeFound) {
		clsPair<Integer, T> oRetVal = this.moNullMemoryObjectWPM;
			
		for (clsPair<Integer, T> oMemory : moShortTimeMemory) {
			if (oToBeFound.equals(oMemory.b)) {
				oRetVal = oMemory;
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the memory from a certain step;
	 * 
	 * (wendt)
	 *
	 * @since 04.07.2012 11:18:36
	 *
	 * @param pnStep
	 * @return
	 */
	public ArrayList<clsPair<Integer, T>> findMemory(int pnStep) {
		
		ArrayList<clsPair<Integer, T>> oResult = new ArrayList<clsPair<Integer, T>>();
		
		for (clsPair<Integer, T> oMemory : moShortTimeMemory) {
			if (oMemory.a == pnStep) {
				oResult.add(oMemory);
				break;
			}
		}
		return oResult;
	}
	
	private T findSingleMemoryFromStep(int pnStep) {
		T oResult = moNullMemoryObjectWPM.b;
	    
		
		
		
		ArrayList<clsPair<Integer, T>> oMemories = findMemory(pnStep);
		if (oMemories.isEmpty()==false) {
			oResult = oMemories.get(0).b;
		}
		
		return oResult;	
	}
	
	public T findPreviousSingleMemory() {
		return findSingleMemoryFromStep(1);
	}
	
	public T findCurrentSingleMemory() {
		return findSingleMemoryFromStep(0);
	}
	
	/**
	 * Get the most obsolete memory. There are 2 criteria: 1) The oldest memory. If there are more than one memories
	 * with the same time step, then the memory with the lowest affect is selected
	 * (wendt)
	 *
	 * @since 31.08.2011 07:55:14
	 *
	 * @return
	 */
	private clsPair<Integer, T> getMostObsoleteMemory() {
		clsPair<Integer, T> oRetVal = this.moNullMemoryObjectWPM;	//This value is only null, if the memory is empty
		
		//Variables, which are used to get the oldest memory. If there are several memories, which are the oldest ones,
		//then, the memory with the lowest total affect value is selected
		
		if (moShortTimeMemory.isEmpty()==false) {
			oRetVal = moShortTimeMemory.get(0);
		}
		
		for (clsPair<Integer, T> oMemory : moShortTimeMemory) {
						
			if (oMemory.a>oRetVal.a) {
				oRetVal = oMemory;
			} 
		}
		
		return oRetVal;
	}

	/**
	 * Get the most recent saved memory from the STM
	 * 
	 * (wendt)
	 *
	 * @since 04.07.2012 09:52:51
	 *
	 * @return
	 */
	public clsPair<Integer, T> getNewestMemory() {
		clsPair<Integer, T> oRetVal = this.moNullMemoryObjectWPM;	//This value is only null, if the memory is empty
		
		if (moShortTimeMemory.isEmpty()==false) {
			oRetVal = moShortTimeMemory.get(0);
		}
		
		for (clsPair<Integer, T> oMemory : moShortTimeMemory) {
						
			if (oMemory.a<oRetVal.a) {
				oRetVal = oMemory;
			} 
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all WPMs from the STM, i. e. all mental images
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 16:31:15
	 *
	 * @return
	 */
	public ArrayList<T> getAllWPMFromSTM() {
		ArrayList<T> oResult = new ArrayList<T>();
		
		for (clsPair<Integer, T> oPair : this.moShortTimeMemory) {
			oResult.add(oPair.b);
		}
		
		return oResult;
		
	}
	
	
	
	@Override
	public String toString(){
		if (moShortTimeMemory.isEmpty()==false) {
			return moShortTimeMemory.toString();// + //"|" + moShortTimeMemory.get(0).b.toString();
		} else {
			return "empty";
		}
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2012 10:59:07 AM
	 * 
	 * @see pa._v38.interfaces.itfGraphData#getGraphData()
	 */
	@Override
	public ArrayList<Object> getGraphData() {
		ArrayList<Object> oRetVal = new ArrayList<Object>();
		for(clsPair<Integer, T> oClsPair :moShortTimeMemory){
			oRetVal.add(oClsPair.b);
		}
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2012 11:26:51 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		return moShortTimeMemory.toString();
	}

}
