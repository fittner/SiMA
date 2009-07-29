/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import bw.body.itfStepUpdateInternalState;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.partclass.clsBasePart;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerSystem implements itfStepUpdateInternalState {
    // private clsConfigMap moConfig; // EH - make warning free
    
	private HashMap<clsBasePart, ArrayList<clsBasePart>> moSourceTargetMappings;
	private HashMap<clsBasePart, ArrayList<clsBasePart>> moTargetSourceMappings;
	
	private ArrayList<clsFastMessengerEntry> moMessages;
	
	private HashMap<clsBasePart, ArrayList<clsFastMessengerEntry>> moTargetList;


	public clsFastMessengerSystem(String poPrefix, clsBWProperties poProp) {
		moSourceTargetMappings = new HashMap<clsBasePart, ArrayList<clsBasePart>>();
		moTargetSourceMappings = new HashMap<clsBasePart, ArrayList<clsBasePart>>();		
		moMessages = new ArrayList<clsFastMessengerEntry>();
		moTargetList = new HashMap<clsBasePart, ArrayList<clsFastMessengerEntry>>();		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		// no properties
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
        // nothing to do		
	}	
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 */
	public void addMapping(clsBasePart poSource, clsBasePart poTarget) {
		addSourceMapping(poSource, poTarget);
		addTargetMapping(poSource, poTarget);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 */
	private void addSourceMapping(clsBasePart poSource, clsBasePart poTarget) {
		ArrayList<clsBasePart> oList = moSourceTargetMappings.get(poSource);
		
		if (oList == null) {
			oList = new ArrayList<clsBasePart>();
		}
		
		if (!oList.contains(poTarget)) {
			oList.add(poTarget);
		}
		
		moSourceTargetMappings.put(poSource, oList);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 */
	private void addTargetMapping(clsBasePart poSource, clsBasePart poTarget) {
		ArrayList<clsBasePart> oList = moSourceTargetMappings.get(poTarget);
		
		if (oList == null) {
			oList = new ArrayList<clsBasePart>();
		}
		
		if (!oList.contains(poSource)) {
			oList.add(poSource);
		}
		
		moSourceTargetMappings.put(poTarget, oList);		
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 */
	public void clear() {
		moMessages.clear();
		moTargetList.clear();
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @return
	 */
	public ArrayList<clsBasePart> getTargets(clsBasePart poSource) {
		return copyArray(moSourceTargetMappings.get(poSource));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<clsBasePart> getSources(clsBasePart poTarget) {
		return copyArray(moTargetSourceMappings.get(poTarget));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poArray
	 * @return
	 */
	private ArrayList<clsBasePart> copyArray(ArrayList<clsBasePart> poArray) {
		ArrayList<clsBasePart> oResult = new ArrayList<clsBasePart>();
		
		Iterator<clsBasePart> i = poArray.iterator();
		while (i.hasNext()) {
			oResult.add(i.next().clone());
		}
		
		return oResult;		
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<clsFastMessengerEntry> getMessagesForTarget(clsBasePart poTarget) {
		return  moTargetList.get(poTarget);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public void addMessage(clsBasePart poSource, clsBasePart poTarget, double prIntensity) {
		addMessage(new clsFastMessengerEntry(poSource, poTarget, prIntensity));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessage
	 */
	public void addMessage(clsFastMessengerEntry poMessage) {
		moMessages.add(poMessage);
		
		clsBasePart oTarget = poMessage.getTarget();
		ArrayList<clsFastMessengerEntry> oList = moTargetList.get(oTarget);
		
		if (oList == null) {
			oList = new ArrayList<clsFastMessengerEntry>();
			oList.add(poMessage);
			moTargetList.put(oTarget, oList);
		} else {
			oList.add(poMessage);
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 11:51:42
	 * 
	 * @see bw.body.itfStep#step()
	 */
	public void stepUpdateInternalState() {
		// TODO Auto-generated method stub
		
	}

	
}
