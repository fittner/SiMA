/**
 * CHANGELOG
 *
 * 09.10.2013 herret - File created
 *
 */
package bw.entities.base;

import bw.body.clsBaseBody;
import config.clsProperties;
import bw.body.clsSimpleBody;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 09.10.2013, 11:23:42
 * 
 */
public abstract class  clsUnorganic extends clsInanimate{



	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since 09.10.2013 11:25:11
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param uid
	 */
	public clsUnorganic(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix,poProp);
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp){		


	}

	/* (non-Javadoc)
	 *
	 * @since 09.10.2013 11:24:35
	 * 
	 * @see bw.entities.base.clsEntity#createBody(java.lang.String, config.clsProperties)
	 */
	@Override
	protected clsBaseBody createBody(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		clsBaseBody oRetVal = new clsSimpleBody(pre+P_BODY, poProp, this);
		return oRetVal;
	}





}
