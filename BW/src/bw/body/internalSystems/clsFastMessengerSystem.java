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
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerSystem implements itfStepUpdateInternalState {
    // private clsConfigMap moConfig; // EH - make warning free
    
	private HashMap<eBodyParts, ArrayList<eBodyParts>> moSourceTargetMappings;
	private HashMap<eBodyParts, ArrayList<eBodyParts>> moTargetSourceMappings;
	
	private ArrayList<clsFastMessengerEntry> moMessages;
	
	private HashMap<eBodyParts, ArrayList<clsFastMessengerEntry>> moTargetList;


	public clsFastMessengerSystem(String poPrefix, clsBWProperties poProp) {
		moSourceTargetMappings = new HashMap<eBodyParts, ArrayList<eBodyParts>>();
		moTargetSourceMappings = new HashMap<eBodyParts, ArrayList<eBodyParts>>();		
		moMessages = new ArrayList<clsFastMessengerEntry>();
		moTargetList = new HashMap<eBodyParts, ArrayList<clsFastMessengerEntry>>();		
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
	public void addMapping(eBodyParts poSource, eBodyParts poTarget) {
		addSourceMapping(poSource, poTarget);
		addTargetMapping(poSource, poTarget);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 */
	private void addSourceMapping(eBodyParts poSource, eBodyParts poTarget) {
		ArrayList<eBodyParts> oList = moSourceTargetMappings.get(poSource);
		
		if (oList == null) {
			oList = new ArrayList<eBodyParts>();
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
	private void addTargetMapping(eBodyParts poSource, eBodyParts poTarget) {
		ArrayList<eBodyParts> oList = moSourceTargetMappings.get(poTarget);
		
		if (oList == null) {
			oList = new ArrayList<eBodyParts>();
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
	public ArrayList<eBodyParts> getTargets(eBodyParts poSource) {
		return copyArray(moSourceTargetMappings.get(poSource));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<eBodyParts> getSources(eBodyParts poTarget) {
		return copyArray(moTargetSourceMappings.get(poTarget));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poArray
	 * @return
	 */
	private ArrayList<eBodyParts> copyArray(ArrayList<eBodyParts> poArray) {
		ArrayList<eBodyParts> oResult = new ArrayList<eBodyParts>();
		
		Iterator<eBodyParts> i = poArray.iterator();
		while (i.hasNext()) {
			oResult.add(i.next());
		}
		
		return oResult;		
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<clsFastMessengerEntry> getMessagesForTarget(eBodyParts poTarget) {
		return  moTargetList.get(poTarget);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public void addMessage(eBodyParts poSource, eBodyParts poTarget, double prIntensity) {
		addMessage(new clsFastMessengerEntry(poSource, poTarget, prIntensity));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessage
	 */
	public void addMessage(clsFastMessengerEntry poMessage) {
		moMessages.add(poMessage);
		
		eBodyParts oTarget = poMessage.getTarget();
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
		// TODO (deutsch) Auto-generated method stub
		
	}

	
}
