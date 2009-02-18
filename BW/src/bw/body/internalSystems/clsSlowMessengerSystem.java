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

import bw.body.itfStep;
import bw.exceptions.ContentColumnMaxContentExceeded;
import bw.exceptions.ContentColumnMinContentUnderrun;
import bw.exceptions.SlowMessengerAlreadyExists;
import bw.exceptions.SlowMessengerDoesNotExist;
import bw.exceptions.ValueNotWithinRange;
import bw.utils.tools.clsDecayColumn;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSlowMessengerSystem implements itfStep {
	private HashMap<Integer, clsDecayColumn> moSlowMessengerContainer;

	private float mrDefaultContent = 0.0f;
	private float mrDefaultMaxContent = 1.0f;
	private float mrDefaultIncreaseRate = 0.1f;
	private float mrDefaultDecayRate = 0.01f;
	 
	/**
	 * 
	 */
	public clsSlowMessengerSystem() {
		super();
		
		moSlowMessengerContainer = new HashMap<Integer, clsDecayColumn>();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnMessengerId
	 * @return
	 * @throws SlowMessengerAlreadyExists
	 * @throws ContentColumnMaxContentExceeded
	 * @throws ContentColumnMinContentUnderrun
	 * @throws ValueNotWithinRange
	 */
	public clsDecayColumn addSlowMessenger(int pnMessengerId) throws SlowMessengerAlreadyExists, ContentColumnMaxContentExceeded, ContentColumnMinContentUnderrun, ValueNotWithinRange {
		return addSlowMessenger(new Integer(pnMessengerId));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws SlowMessengerAlreadyExists
	 * @throws ContentColumnMaxContentExceeded
	 * @throws ContentColumnMinContentUnderrun
	 * @throws ValueNotWithinRange
	 */
	public clsDecayColumn addSlowMessenger(Integer poMessengerId) throws SlowMessengerAlreadyExists, ContentColumnMaxContentExceeded, ContentColumnMinContentUnderrun, ValueNotWithinRange {
		if (moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.SlowMessengerAlreadyExists(poMessengerId);
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
	 * @throws SlowMessengerDoesNotExist
	 */
	public float getMessengerValue(int pnMessengerId) throws SlowMessengerDoesNotExist {
		return getMessengerValue(new Integer(pnMessengerId));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @return
	 * @throws SlowMessengerDoesNotExist
	 */
	public float getMessengerValue(Integer poMessengerId) throws SlowMessengerDoesNotExist {
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.SlowMessengerDoesNotExist(poMessengerId);
		}		
		
		return moSlowMessengerContainer.get(poMessengerId).getContent();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnMessengerId
	 * @param prAmount
	 * @throws SlowMessengerDoesNotExist
	 * @throws ValueNotWithinRange
	 */
	public void inject(int pnMessengerId, float prAmount) throws SlowMessengerDoesNotExist, ValueNotWithinRange {		
		inject(new Integer(pnMessengerId), prAmount);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poMessengerId
	 * @param prAmount
	 * @throws SlowMessengerDoesNotExist
	 * @throws ValueNotWithinRange
	 */
	public void inject(Integer poMessengerId, float prAmount) throws SlowMessengerDoesNotExist, ValueNotWithinRange {		
		if (!moSlowMessengerContainer.containsKey(poMessengerId)) {
			throw new bw.exceptions.SlowMessengerDoesNotExist(poMessengerId);
		}
		
		clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(poMessengerId);
		
		oSlowMessenger.inject(prAmount);
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void step() {
		Iterator<Integer> i = moSlowMessengerContainer.keySet().iterator();
		
		while(i.hasNext()) {
			clsDecayColumn oSlowMessenger = moSlowMessengerContainer.get(i.next());
			oSlowMessenger.update();
		}
	}
}
