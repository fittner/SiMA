/**
 * E38_PrimalRepressionForSelfPreservationDrives.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:21:18
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.receive._v30.I1_5_receive;
import pa.interfaces.receive._v30.I2_15_receive;
import pa.interfaces.send._v30.I1_5_send;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:21:18
 * 
 */
public class E38_PrimalRepressionForSelfPreservationDrives extends	clsModuleBase implements I2_15_receive, I1_5_send {
	public static final String P_MODULENUMBER = "38";
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:44:20
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E38_PrimalRepressionForSelfPreservationDrives(String poPrefix,
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
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
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
	 * 03.03.2011, 15:21:18
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
	 * 03.03.2011, 15:21:18
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
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_5(new ArrayList<clsPrimaryInformation>(), new ArrayList<clsDriveMesh>());

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:59:47
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:00:47
	 * 
	 * @see pa.interfaces.send._v30.I1_5_send#send_I1_5(java.util.List, java.util.List)
	 */
	@Override
	public void send_I1_5(List<clsPrimaryInformation> poData_old,
			List<clsDriveMesh> poData) {
		
		((I1_5_receive)moModuleList.get(6)).receive_I1_5(poData_old, poData);
		((I1_5_receive)moModuleList.get(7)).receive_I1_5(poData_old, poData);
		((I1_5_receive)moModuleList.get(9)).receive_I1_5(poData_old, poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:00:47
	 * 
	 * @see pa.interfaces.receive._v30.I2_15_receive#receive_I2_15(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_15(
			ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
