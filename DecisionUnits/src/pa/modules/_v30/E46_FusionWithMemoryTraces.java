/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces.receive._v30.I2_20_receive;
import pa.interfaces.receive._v30.I2_5_receive;
import pa.interfaces.receive._v30.I7_7_receive;
import pa.interfaces.send._v30.I2_20_send;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

import config.clsBWProperties;

/**
 * DOCUMENT (HINTERLEITNER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 * 
 */
public class E46_FusionWithMemoryTraces extends clsModuleBase implements
					I2_5_receive, I7_7_receive, I2_20_send {
	public static final String P_MODULENUMBER = "46";
	
	/**
	 * DOCUMENT (HINTERLEITNER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:16:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E46_FusionWithMemoryTraces(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList) throws Exception {
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
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_20(new ArrayList<clsPrimaryDataStructureContainer>());

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
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
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
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
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.send._v30.I2_20_send#receive_I2_20(java.util.ArrayList)
	 */
	@Override
	public void send_I2_20(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_20_receive)moModuleList.get(37)).receive_I2_20(poEnvironmentalTP);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v30.I7_7_receive#receive_I7_7(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_7(
			ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		// TODO (HINTERLEITNER) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v30.I2_5_receive#receive_I2_5(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_5(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		// TODO (HINTERLEITNER) - Auto-generated method stub
		
	}

}
