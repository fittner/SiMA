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

import bw.utils.enums.partclass.clsBasePart;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerSystem {
	private HashMap<clsBasePart, ArrayList<clsBasePart>> moSourceTargetMappings;
	private HashMap<clsBasePart, ArrayList<clsBasePart>> moTargetSourceMappings;
	
	private ArrayList<clsFastMessengerEntry> moMessages;
	
	private HashMap<clsBasePart, clsFastMessengerEntry> moSourceList;
	private HashMap<clsBasePart, clsFastMessengerEntry> moTargetList;
	
	/**
	 * 
	 */
	public clsFastMessengerSystem() {
		moSourceTargetMappings = new HashMap<clsBasePart, ArrayList<clsBasePart>>();
		moTargetSourceMappings = new HashMap<clsBasePart, ArrayList<clsBasePart>>();		
		moMessages = new ArrayList<clsFastMessengerEntry>();
		moSourceList = new HashMap<clsBasePart, clsFastMessengerEntry>();
		moTargetList = new HashMap<clsBasePart, clsFastMessengerEntry>();
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
		moSourceList.clear();
		moTargetList.clear();
	}

	public ArrayList<clsBasePart> getTargets(clsBasePart poSource) {
		return copyArray(moSourceTargetMappings.get(poSource));
	}
	
	private ArrayList<clsBasePart> copyArray(ArrayList<clsBasePart> poArray) {
		ArrayList<clsBasePart> oResult = null;
		
		Iterator<clsBasePart> i = poArray.iterator();
		while (i.hasNext()) {
			clsBasePart oEntry = i.next();
			if (oEntry != null) {
		//		clsBasePart oCopy = new clsBasePart(oEntry);
				oResult.add(oEntry);
			}
		}
		
		
		
		return oResult;		
	}
}
