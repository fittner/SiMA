/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import java.util.HashMap;
import java.util.Iterator;

import utils.exceptions.exContentColumnMaxContentExceeded;
import utils.exceptions.exContentColumnMinContentUnderrun;
import utils.exceptions.exSlowMessengerAlreadyExists;
import utils.exceptions.exSlowMessengerDoesNotExist;
import utils.exceptions.exValueNotWithinRange;

import config.clsProperties;
import du.enums.eSlowMessenger;
import body.itfStepUpdateInternalState;
import body.utils.clsDecayColumn;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSlowMessengerSystem implements itfStepUpdateInternalState {
	public static final String P_NUMSLOWMESSENGERS = "numslowmessengers";
	public static final String P_SLOWMESSENGERTYPE = "slowmessengertype";
	
	private HashMap<eSlowMessenger, clsDecayColumn> moSlowMessengerContainer;
	 
	public clsSlowMessengerSystem(String poPrefix, clsProperties poProp) {
		moSlowMessengerContainer = new HashMap<eSlowMessenger, clsDecayColumn>();
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_NUMSLOWMESSENGERS, 2);
		
		oProp.setProperty(pre+"0."+P_SLOWMESSENGERTYPE, eSlowMessenger.ADREANLIN.toString());
		oProp.putAll( clsDecayColumn.getDefaultProperties(pre+"0") );
		oProp.setProperty(pre+"0."+clsDecayColumn.P_MAXCONTENT, 1.0);

		oProp.setProperty(pre+"1."+P_SLOWMESSENGERTYPE, eSlowMessenger.BLOODSUGAR.toString());
		oProp.putAll( clsDecayColumn.getDefaultProperties(pre+"1") );
		oProp.setProperty(pre+"1."+clsDecayColumn.P_MAXCONTENT, 1.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
		
        int num = poProp.getPropertyInt(pre+P_NUMSLOWMESSENGERS);
        for (int i=0; i<num; i++) {
        	try {
				clsDecayColumn oDC = new clsDecayColumn(pre+i+".", poProp);
				String oSM = poProp.getPropertyString(pre+i+"."+P_SLOWMESSENGERTYPE);
				eSlowMessenger nSM = eSlowMessenger.valueOf(oSM);
				moSlowMessengerContainer.put(nSM, oDC);
				
			} catch (exValueNotWithinRange e) {				
				e.printStackTrace();
			}
        }
	}	
	
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws exSlowMessengerAlreadyExists
	 * @throws exContentColumnMaxContentExceeded
	 * @throws exContentColumnMinContentUnderrun
	 * @throws exValueNotWithinRange
	 */
	public clsDecayColumn addSlowMessenger(eSlowMessenger poMessengerId, double prDefaultContent, double prDefaultMaxContent, double prDefaultIncreaseRate, double prDefaultDecayRate) throws exSlowMessengerAlreadyExists, exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun, exValueNotWithinRange {
		if (moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new utils.exceptions.exSlowMessengerAlreadyExists(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = new clsDecayColumn(prDefaultContent, prDefaultMaxContent, prDefaultIncreaseRate, prDefaultDecayRate);
		
		moSlowMessengerContainer.put(poMessengerId, oSlowMessenger);
		
		return oSlowMessenger;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public HashMap<eSlowMessenger, clsDecayColumn> getSlowMessengers() {
		return new HashMap<eSlowMessenger, clsDecayColumn>(moSlowMessengerContainer);
	}
	

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 */
	public boolean existsSlowMessenger(eSlowMessenger poMessengerId) {
		return moSlowMessengerContainer.containsKey(poMessengerId);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws exSlowMessengerDoesNotExist
	 */
	public double getMessengerValue(eSlowMessenger poMessengerId) throws exSlowMessengerDoesNotExist {
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new utils.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}		
		
		return moSlowMessengerContainer.get(poMessengerId).getContent();
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @param prAmount
	 * @throws exSlowMessengerDoesNotExist
	 * @throws exValueNotWithinRange
	 */
	public void inject(eSlowMessenger poMessengerId, double prAmount) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {		
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new utils.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(poMessengerId);
		
		oSlowMessenger.inject(prAmount);
	}
	
	public void setInjectionValue(eSlowMessenger poMessengerId, double prValue) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new utils.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(poMessengerId);
		
		oSlowMessenger.setInjectionValue(prValue);		
	}
	
	public void setValue(eSlowMessenger poMessengerId, double prValue) throws exSlowMessengerDoesNotExist, exValueNotWithinRange, exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun {
		//TD 2011/04/29 - for init purposes. 
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new utils.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(poMessengerId);
		
		oSlowMessenger.setContent(prValue);			
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void stepUpdateInternalState() {
		Iterator<eSlowMessenger> i = moSlowMessengerContainer.keySet().iterator();
		
		while(i.hasNext()) {
			clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(i.next());
			oSlowMessenger.update();
		}
	}
}
