/**
 * E42_AccumulationOfAffectsForSexualDrives.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:20:18
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces.receive._v30.I2_17_receive;
import pa.interfaces.receive._v30.I2_18_receive;
import pa.interfaces.send._v30.I2_18_send;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:20:18
 * 
 */
public class E42_AccumulationOfAffectsForSexualDrives extends clsModuleBase implements 
							I2_17_receive, I2_18_send {
	public static final String P_MODULENUMBER = "42";
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:55:15
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E42_AccumulationOfAffectsForSexualDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList)
			throws Exception {
		super(poPrefix, poProp, poModuleList);
		applyProperties(poPrefix, poProp);	
	}
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_18(new ArrayList<clsPair<clsPair<clsDriveMesh,clsDriveDemand>,clsPair<clsDriveMesh,clsDriveDemand>>>());

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:50:26
	 * 
	 * @see pa.interfaces.send._v30.I2_18_send#receive_I2_18(java.util.ArrayList)
	 */
	@Override
	public void send_I2_18(
			ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		((I2_18_receive)moModuleList.get(44)).receive_I2_18(poDriveCandidate);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:50:26
	 * 
	 * @see pa.interfaces.receive._v30.I2_17_receive#receive_I2_17(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_17(
			ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		// TODO (deutsch) - Auto-generated method stub
		
	}


}
