/**
 * E44_PrimalRepressionForSexualDrives.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:20:48
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I2_18_receive;
import pa.interfaces.receive._v30.I2_19_receive;
import pa.interfaces.send._v30.I2_19_send;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:20:48
 * 
 */
public class E44_PrimalRepressionForSexualDrives extends clsModuleBase implements I2_18_receive, I2_19_send {
	public static final String P_MODULENUMBER = "44";
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:57:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E44_PrimalRepressionForSexualDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += "n/a";
		
		return html;
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
	 * 03.03.2011, 15:20:48
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
	 * 03.03.2011, 15:20:48
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
	 * 03.03.2011, 15:20:48
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
	 * 03.03.2011, 15:20:48
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_19(new ArrayList<clsDriveMesh>());

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:51:07
	 * 
	 * @see pa.interfaces.send._v30.I2_19_send#send_I2_19(java.util.List)
	 */
	@Override
	public void send_I2_19(List<clsDriveMesh> poData) {
		((I2_19_receive)moModuleList.get(6)).receive_I2_19(poData);
		((I2_19_receive)moModuleList.get(7)).receive_I2_19(poData);
		((I2_19_receive)moModuleList.get(9)).receive_I2_19(poData);
		
		putInterfaceData(I2_19_send.class, poData);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:51:07
	 * 
	 * @see pa.interfaces.receive._v30.I2_18_receive#receive_I2_18(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_18(
			ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This function categorizes the thing presentations according to the four primary drives. The result of it is that thing presentations have an additional value which can be used for further memory lookup to find similar entries.";
	}	
}
