/**
 * E45_LibidoDischarge.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I2_16_receive;
import pa.interfaces.receive._v30.I2_8_receive;
import pa.interfaces.send._v30.I2_16_send;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 * 
 */
public class E45_LibidoDischarge extends clsModuleBase implements I2_8_receive, I2_16_send {
	public static final String P_MODULENUMBER = "45";
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:30:00
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E45_LibidoDischarge(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
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
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_16(new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>());

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:31:25
	 * 
	 * @see pa.interfaces.receive._v30.I2_8_receive#receive_I2_8(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_8(
			ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:32:47
	 * 
	 * @see pa.interfaces.send._v30.I2_16_send#send_I2_16(java.util.ArrayList)
	 */
	@Override
	public void send_I2_16(
			ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I2_16_receive)moModuleList.get(18)).receive_I2_16(poMergedPrimaryInformation);
		putInterfaceData(I2_16_send.class, poMergedPrimaryInformation);
	}

}
