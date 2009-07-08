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
import bw.utils.container.clsConfigMap;
import bw.utils.container.clsConfigDouble;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsDecayColumn;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSlowMessengerSystem implements itfStepUpdateInternalState {
    private clsConfigMap moConfig;
    
	private HashMap<Integer, clsDecayColumn> moSlowMessengerContainer;

	private double mrDefaultContent;
	private double mrDefaultMaxContent;
	private double mrDefaultIncreaseRate;
	private double mrDefaultDecayRate;
	 
	/**
	 * 
	 */
	public clsSlowMessengerSystem(clsConfigMap poConfig) {
		moSlowMessengerContainer = new HashMap<Integer, clsDecayColumn>();
		
		moConfig = getFinalConfig(poConfig);
		applyConfig();		
	}
	
	private void applyConfig() {
		
		mrDefaultContent = ((clsConfigDouble)moConfig.get(eConfigEntries.CONTENT)).get();
		mrDefaultMaxContent = ((clsConfigDouble)moConfig.get(eConfigEntries.MAXCONTENT)).get();
		mrDefaultIncreaseRate = ((clsConfigDouble)moConfig.get(eConfigEntries.INCREASERATE)).get();
		mrDefaultDecayRate = ((clsConfigDouble)moConfig.get(eConfigEntries.DECAYRATE)).get();
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigDouble(0.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.INCREASERATE, new clsConfigDouble(0.1f));
		oDefault.add(eConfigEntries.DECAYRATE, new clsConfigDouble(0.01f));
		
		return oDefault;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnMessengerId
	 * @return
	 * @throws exSlowMessengerAlreadyExists
	 * @throws exContentColumnMaxContentExceeded
	 * @throws exContentColumnMinContentUnderrun
	 * @throws exValueNotWithinRange
	 */
	public clsDecayColumn addSlowMessenger(int pnMessengerId) throws exSlowMessengerAlreadyExists, exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun, exValueNotWithinRange {
		return addSlowMessenger(new Integer(pnMessengerId));
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
	public clsDecayColumn addSlowMessenger(Integer poMessengerId) throws exSlowMessengerAlreadyExists, exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun, exValueNotWithinRange {
		if (moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.exSlowMessengerAlreadyExists(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = new clsDecayColumn(mrDefaultContent, mrDefaultMaxContent, mrDefaultIncreaseRate, mrDefaultDecayRate);
		
		moSlowMessengerContainer.put(poMessengerId, oSlowMessenger);
		
		return oSlowMessenger;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public HashMap<Integer, clsDecayColumn> getSlowMessengers() {
		return new HashMap<Integer, clsDecayColumn>(moSlowMessengerContainer);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnMessengerId
	 * @return
	 */
	public boolean existsSlowMessenger(int pnMessengerId) {
		return existsSlowMessenger(new Integer(pnMessengerId));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 */
	public boolean existsSlowMessenger(Integer poMessengerId) {
		return moSlowMessengerContainer.containsKey(poMessengerId);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnMessengerId
	 * @return
	 * @throws exSlowMessengerDoesNotExist
	 */
	public double getMessengerValue(int pnMessengerId) throws exSlowMessengerDoesNotExist {
		return getMessengerValue(new Integer(pnMessengerId));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws exSlowMessengerDoesNotExist
	 */
	public double getMessengerValue(Integer poMessengerId) throws exSlowMessengerDoesNotExist {
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.exSlowMessengerDoesNotExist(poMessengerId);
		}		
		
		return moSlowMessengerContainer.get(poMessengerId).getContent();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnMessengerId
	 * @param prAmount
	 * @throws exSlowMessengerDoesNotExist
	 * @throws exValueNotWithinRange
	 */
	public void inject(int pnMessengerId, double prAmount) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {		
		inject(new Integer(pnMessengerId), prAmount);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @param prAmount
	 * @throws exSlowMessengerDoesNotExist
	 * @throws exValueNotWithinRange
	 */
	public void inject(Integer poMessengerId, double prAmount) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {		
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
		Iterator<Integer> i = moSlowMessengerContainer.keySet().iterator();
		
		while(i.hasNext()) {
			clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(i.next());
			oSlowMessenger.update();
		}
	}
}
