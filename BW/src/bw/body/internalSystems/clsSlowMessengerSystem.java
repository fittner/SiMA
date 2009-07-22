/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.HashMap;
import java.util.Iterator;
import bw.body.itfStepUpdateInternalState;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.exceptions.exSlowMessengerAlreadyExists;
import bw.exceptions.exSlowMessengerDoesNotExist;
import bw.exceptions.exValueNotWithinRange;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eSlowMessenger;
import bw.utils.tools.clsDecayColumn;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSlowMessengerSystem implements itfStepUpdateInternalState {
	public static final String P_NUMSLOWMESSENGERS = "numslowmessengers";
	public static final String P_SLOWMESSENGERTYPE = "slowmessengertype";
	
	private HashMap<eSlowMessenger, clsDecayColumn> moSlowMessengerContainer;
	 
	public clsSlowMessengerSystem(String poPrefix, clsBWProperties poProp) {
		moSlowMessengerContainer = new HashMap<eSlowMessenger, clsDecayColumn>();
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_NUMSLOWMESSENGERS, 2);
		
		oProp.setProperty(pre+"0."+P_SLOWMESSENGERTYPE, eSlowMessenger.TESTOSTERON.toString());
		oProp.putAll( clsDecayColumn.getDefaultProperties(pre+"0") );

		oProp.setProperty(pre+"1."+P_SLOWMESSENGERTYPE, eSlowMessenger.BLOODSUGAR.toString());
		oProp.putAll( clsDecayColumn.getDefaultProperties(pre+"1") );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
	    String pre = clsBWProperties.addDot(poPrefix);
		
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
	 * TODO (deutsch) - insert description
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
			throw new bw.exceptions.exSlowMessengerAlreadyExists(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = new clsDecayColumn(prDefaultContent, prDefaultMaxContent, prDefaultIncreaseRate, prDefaultDecayRate);
		
		moSlowMessengerContainer.put(poMessengerId, oSlowMessenger);
		
		return oSlowMessenger;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public HashMap<eSlowMessenger, clsDecayColumn> getSlowMessengers() {
		return new HashMap<eSlowMessenger, clsDecayColumn>(moSlowMessengerContainer);
	}
	

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 */
	public boolean existsSlowMessenger(eSlowMessenger poMessengerId) {
		return moSlowMessengerContainer.containsKey(poMessengerId);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws exSlowMessengerDoesNotExist
	 */
	public double getMessengerValue(eSlowMessenger poMessengerId) throws exSlowMessengerDoesNotExist {
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}		
		
		return moSlowMessengerContainer.get(poMessengerId).getContent();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @param prAmount
	 * @throws exSlowMessengerDoesNotExist
	 * @throws exValueNotWithinRange
	 */
	public void inject(eSlowMessenger poMessengerId, double prAmount) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {		
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(poMessengerId);
		
		oSlowMessenger.inject(prAmount);
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void stepUpdateInternalState() {
		Iterator<eSlowMessenger> i = moSlowMessengerContainer.keySet().iterator();
		
		while(i.hasNext()) {
			clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(i.next());
			oSlowMessenger.update();
		}
	}
}
