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

import bw.body.itfStep;
import bw.utils.enums.partclass.clsBasePart;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerSystem implements itfStep {
	private HashMap<clsBasePart, ArrayList<clsBasePart>> moSourceTargetMappings;
	private HashMap<clsBasePart, ArrayList<clsBasePart>> moTargetSourceMappings;
	
	private ArrayList<clsFastMessengerEntry> moMessages;
	
	private HashMap<clsBasePart, ArrayList<clsFastMessengerEntry>> moTargetList;
	
	/**
	 * 
	 */
	public clsFastMessengerSystem() {
		moSourceTargetMappings = new HashMap<clsBasePart, ArrayList<clsBasePart>>();
		moTargetSourceMappings = new HashMap<clsBasePart, ArrayList<clsBasePart>>();		
		moMessages = new ArrayList<clsFastMessengerEntry>();
		moTargetList = new HashMap<clsBasePart, ArrayList<clsFastMessengerEntry>>();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 */
	public void addMapping(clsBasePart poSource, clsBasePart poTarget) {
		addSourceMapping(poSource, poTarget);
		addTargetMapping(poSource, poTarget);
	}
	
	/**
	 * TODO (deutsch) - insert description
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
	 * TODO (deutsch) - insert description
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
	 * TODO (deutsch) - insert description
	 *
	 */
	public void clear() {
		moMessages.clear();
		moTargetList.clear();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poSource
	 * @return
	 */
	public ArrayList<clsBasePart> getTargets(clsBasePart poSource) {
		return copyArray(moSourceTargetMappings.get(poSource));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<clsBasePart> getSources(clsBasePart poTarget) {
		return copyArray(moTargetSourceMappings.get(poTarget));
	}
	
	/**
	 * TODO (deutsch) - insert description
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
	 * TODO (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<clsFastMessengerEntry> getMessagesForTarget(clsBasePart poTarget) {
		return  moTargetList.get(poTarget);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public void addMessage(clsBasePart poSource, clsBasePart poTarget, float prIntensity) {
		addMessage(new clsFastMessengerEntry(poSource, poTarget, prIntensity));
	}
	
	/**
	 * TODO (deutsch) - insert description
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
	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	
}
