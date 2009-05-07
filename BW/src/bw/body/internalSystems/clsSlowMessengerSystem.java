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
import bw.utils.container.clsConfigFloat;
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

	private float mrDefaultContent;
	private float mrDefaultMaxContent;
	private float mrDefaultIncreaseRate;
	private float mrDefaultDecayRate;
	 
	/**
	 * 
	 */
	public clsSlowMessengerSystem(clsConfigMap poConfig) {
		moSlowMessengerContainer = new HashMap<Integer, clsDecayColumn>();
		
		applyConfig(poConfig);		
	}
	
	private void applyConfig(clsConfigMap poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);	
		
		mrDefaultContent = ((clsConfigFloat)moConfig.get(eConfigEntries.CONTENT)).get();
		mrDefaultMaxContent = ((clsConfigFloat)moConfig.get(eConfigEntries.MAXCONTENT)).get();
		mrDefaultIncreaseRate = ((clsConfigFloat)moConfig.get(eConfigEntries.INCREASERATE)).get();
		mrDefaultDecayRate = ((clsConfigFloat)moConfig.get(eConfigEntries.DECAYRATE)).get();
	}

	private clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigFloat(0.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigFloat(1.0f));
		oDefault.add(eConfigEntries.INCREASERATE, new clsConfigFloat(0.1f));
		oDefault.add(eConfigEntries.DECAYRATE, new clsConfigFloat(0.01f));
		
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
	public float getMessengerValue(int pnMessengerId) throws exSlowMessengerDoesNotExist {
		return getMessengerValue(new Integer(pnMessengerId));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws exSlowMessengerDoesNotExist
	 */
	public float getMessengerValue(Integer poMessengerId) throws exSlowMessengerDoesNotExist {
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
	public void inject(int pnMessengerId, float prAmount) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {		
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
	public void inject(Integer poMessengerId, float prAmount) throws exSlowMessengerDoesNotExist, exValueNotWithinRange {		
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
