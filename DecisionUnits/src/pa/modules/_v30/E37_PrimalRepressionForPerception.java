/**
 * E37_PrimalRepressionForPerception.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:22:10
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces.receive._v30.I2_14_receive;
import pa.interfaces.receive._v30.I2_20_receive;
import pa.interfaces.send._v30.I2_14_send;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

import config.clsBWProperties;

/**
 * DOCUMENT (HINTERLEITNER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:22:10
 * 
 */
public class E37_PrimalRepressionForPerception extends clsModuleBase implements I2_20_receive, I2_14_send  {
	public static final String P_MODULENUMBER = "37";
		
	/**
	 * DOCUMENT (HINTERLEITNER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:20:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E37_PrimalRepressionForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList)
			throws Exception {
		super(poPrefix, poProp, poModuleList);
		applyProperties(poPrefix, poProp);	
	}
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

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
	 * 03.03.2011, 15:22:10
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
	 * 03.03.2011, 15:22:10
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
	 * 03.03.2011, 15:22:10
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
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_14(new ArrayList<clsPrimaryDataStructureContainer>());

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.send._v30.I2_14_send#send_I2_14(java.util.ArrayList)
	 */
	@Override
	public void send_I2_14(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		
		((I2_14_receive)moModuleList.get(35)).receive_I2_14(poEnvironmentalTP);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.receive._v30.I2_20_receive#receive_I2_20(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_20(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		// TODO (HINTERLEITNER) - Auto-generated method stub
		
	}

}
