/**
 * CHANGELOG
 *
 * 08.10.2013 herret - File created
 *
 */
package entities.abstractEntities;

import properties.clsProperties;
import complexbody.internalSystems.clsFlesh;

import registration.clsRegisterEntity;
import body.clsBaseBody;
import body.clsMeatBody;
import body.itfget.itfGetBody;
import body.itfget.itfGetFlesh;


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
