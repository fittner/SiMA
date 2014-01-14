/**
 * CHANGELOG
 *
 * 08.10.2013 herret - File created
 *
 */
package bw.entities.base;

import registration.clsRegisterEntity;
import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;
import bw.body.clsBaseBody;
import bw.body.clsMeatBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfGetBody;
import bw.body.itfget.itfGetFlesh;

import config.clsProperties;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 08.10.2013, 13:02:39
 * 
 */
public abstract class  clsOrganic extends clsInanimate implements itfGetFlesh, itfGetBody{

	private boolean mnDestroyed = false;
	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since 08.10.2013 13:02:46
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param uid
	 */
	public clsOrganic(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
		setVariableWeight(getFlesh().getWeight());

	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.05.2009, 18:16:27
	 * 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	@Override
	public clsFlesh getFlesh() {
		return ((clsMeatBody)moBody).getFlesh();
	}
	
	@Override
	protected clsBaseBody createBody(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsBaseBody oRetVal = new clsMeatBody(pre+P_BODY, poProp, this);
	
		return oRetVal;	
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
				
		if (getFlesh().getTotallyConsumed() && !mnDestroyed) {
			mnDestroyed = true;
			clsEventLogger.add(new Event(this, getId(), eEvent.CONSUMED, ""));
			clsEventLogger.add(new Event(this, getId(), eEvent.DESTROY, ""));
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}




	/* (non-Javadoc)
	 *
	 * @since 08.10.2013 13:02:40
	 * 
	 * @see bw.entities.base.clsEntity#dublicate(config.clsProperties, double, double)
	 */
	@Override
	public clsEntity dublicate(clsProperties poPrperties, double poDistance,
			double poSplitFactor) {
		// TODO (herret) - Auto-generated method stub
		return null;
	}

}
