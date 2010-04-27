/**
 * E33_RealityCheck2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 27.04.2010, 10:18:11
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.interfaces.I7_3;
import pa.interfaces.I7_5;
import pa.interfaces.I7_6;
import pa.loader.plan.clsPlanAction;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 27.04.2010, 10:18:11
 * 
 */
public class E33_RealityCheck2 extends clsModuleBase implements I7_3, I7_5 {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 27.04.2010, 10:24:41
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E33_RealityCheck2(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		// TODO (deutsch) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I7_6)moEnclosingContainer).receive_I7_6(1);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:36:01
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_3(ArrayList<clsPlanAction> poActionCommands) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:44:03
	 * 
	 * @see pa.interfaces.I7_5#receive_I7_5(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_5(int pnData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
