/**
 * clsSimpleBody.java: BW - bw.body
 * 
 * @author deutsch
 * 08.09.2009, 17:14:14
 */
package bw.body;

import bw.body.attributes.clsAttributes;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyType;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 17:14:14
 * 
 */
public class clsSimpleBody extends clsBaseBody {
	
	
	
	public clsSimpleBody(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp, poEntity);	
		applyProperties(poPrefix, poProp, poEntity);	
	}

	private void applyProperties(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		//String pre = clsProperties.addDot(poPrefix);
		
		// nothing to do
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsAttributes.getDefaultProperties(pre+P_ATTRIBUTES) );
			
		return oProp;
	}	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 17:14:14
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		// nothing to do

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 17:14:14
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
		// nothing to do

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 17:14:14
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing()
	 */
	@Override
	public void stepProcessing() {
		// nothing to do

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 17:14:14
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	@Override
	public void stepExecution() {
		// nothing to do

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:42:57
	 * 
	 * @see bw.body.clsBaseBody#setBodyType()
	 */
	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.SIMPLE;
		
	}

}
