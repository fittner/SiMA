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
import java.util.Map;
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
	public static final String P_EXPIRETIME = "expiretime";
	
	private HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>> moTargetList;

	private int mnDefaultExpireTime;

	public clsFastMessengerSystem(String poPrefix, clsBWProperties poProp) {
		moTargetList = new HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>>();		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_EXPIRETIME, 3);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mnDefaultExpireTime = poProp.getPropertyInt(pre+P_EXPIRETIME);		
	}	

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poTarget
	 * @return
	 */
	public ArrayList<clsFastMessengerEntry> getMessagesForTarget(eBodyParts poTarget) {
		HashMap<eBodyParts, clsFastMessengerEntry> oList = moTargetList.get(poTarget);
		ArrayList<clsFastMessengerEntry> oResult = new ArrayList<clsFastMessengerEntry>();
		
		if (oList != null) {
			Iterator<eBodyParts> i = oList.keySet().iterator();
			while (i.hasNext()) {
				oResult.add( oList.get(i.next()) );
			}
		}
		
		return  oResult;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public void addMessage(eBodyParts poSource, eBodyParts poTarget, double prIntensity) {
		addMessage(new clsFastMessengerEntry(poSource, poTarget, prIntensity, mnDefaultExpireTime));
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessage
	 */
	public void addMessage(clsFastMessengerEntry poMessage) {
		eBodyParts oTarget = poMessage.getTarget();
		HashMap<eBodyParts, clsFastMessengerEntry> oList = moTargetList.get(oTarget);
		
		if (oList == null) {
			oList = new HashMap<eBodyParts, clsFastMessengerEntry>();
			moTargetList.put(oTarget, oList);
		}
		
		oList.put(poMessage.getSource(), poMessage);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 11:51:42
	 * 
	 * @see bw.body.itfStep#step()
	 */
	public void stepUpdateInternalState() {
		Iterator<eBodyParts> i = moTargetList.keySet().iterator();
		
		while (i.hasNext()) {
			HashMap<eBodyParts, clsFastMessengerEntry> oEntries = moTargetList.get(i.next());
			ArrayList<eBodyParts> oDeleteCandidates = new ArrayList<eBodyParts>();
			
			// age fast message entries
			for ( Map.Entry<eBodyParts,clsFastMessengerEntry> oEntry:oEntries.entrySet()) {
				oEntry.getValue().decTimer();
				if (oEntry.getValue().timerExpired()) {
					oDeleteCandidates.add(oEntry.getKey());
				}
			}
			
			//remove outdated fast message entries
			for (eBodyParts oDeleteCandidate:oDeleteCandidates) {
				oEntries.remove(oDeleteCandidate);
			}
		}
		
	}

	
}
