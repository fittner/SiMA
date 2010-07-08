/**
 * @author deutsch
 * 12.05.2009, 17:11:53
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import config.clsBWProperties;
import bw.body.attributes.clsAttributes;
import bw.body.internalSystems.clsFlesh;
import bw.entities.clsEntity;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 17:11:53
 * 
 */
public class clsMeatBody extends clsBaseBody {
	public static final String P_REGROWRATE = "regrowrate";
	public static final String P_MAXWEIGHT  = "maxweight";
	
	private clsFlesh moFlesh;
	private double mrRegrowRate;
	private double mrMaxWeight;

	public clsMeatBody(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp, poEntity);
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsFlesh.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_REGROWRATE, 0);
		oProp.setProperty(pre+P_MAXWEIGHT, 100);
		
		oProp.putAll( clsAttributes.getDefaultProperties(pre+P_ATTRIBUTES) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		moFlesh = new clsFlesh(pre, poProp);
		mrRegrowRate = poProp.getPropertyDouble(pre+P_REGROWRATE);
		mrMaxWeight = poProp.getPropertyDouble(pre+P_MAXWEIGHT);
	}	
		
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 17:11:53
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
		if (moFlesh.getWeight() < mrMaxWeight) {
			moFlesh.grow(mrRegrowRate);
		}
	}

	/**
	 * @author deutsch
	 * 12.05.2009, 17:53:30
	 * 
	 * @return the moFlesh
	 */
	public clsFlesh getFlesh() {
		return moFlesh;
	}

	/* (non-Javadoc)
	 *
	 * @author tobias
	 * Jul 24, 2009, 6:09:06 PM
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		// nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author tobias
	 * Jul 24, 2009, 6:09:06 PM
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing()
	 */
	@Override
	public void stepProcessing() {
		// nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author tobias
	 * Jul 24, 2009, 6:09:06 PM
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	@Override
	public void stepExecution() {
		// nothing to do
	}

}
