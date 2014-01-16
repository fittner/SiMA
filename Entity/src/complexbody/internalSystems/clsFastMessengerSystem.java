/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import properties.clsProperties;

import entities.enums.eBodyParts;
import body.itfStepUpdateInternalState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerSystem implements itfStepUpdateInternalState {
	public static final String P_EXPIRETIME = "expiretime";
	
	private ArrayList<clsFastMessengerKeyTuple> moFromToMapping;
	private HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>> moTargetList;

	private int mnDefaultExpireTime;

	public clsFastMessengerSystem(String poPrefix, clsProperties poProp) {
		moTargetList = new HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>>();
		moFromToMapping = new ArrayList<clsFastMessengerKeyTuple>();
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_EXPIRETIME, 3);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
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
		if (!moFromToMapping.contains(poMessage.getFromTo())) {
			moFromToMapping.add(poMessage.getFromTo());
		}
		
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
	@Override
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
	
	public clsFastMessengerEntry getEntry(eBodyParts peSource, eBodyParts peTarget) {
		clsFastMessengerEntry oResult = null;
		
		oResult = (moTargetList.get(peTarget)).get(peSource);
		
		return oResult;
	}
	
	public ArrayList<clsFastMessengerKeyTuple> getFromToMapping() {
		return moFromToMapping;
	}
	
	public HashMap<eBodyParts, HashMap<eBodyParts, clsFastMessengerEntry>> getTargetList() {
		return moTargetList;
	}
}
