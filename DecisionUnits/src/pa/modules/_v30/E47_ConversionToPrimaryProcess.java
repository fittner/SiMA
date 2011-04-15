/**
 * E47_ConversionToPrimaryProcess.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:22:59
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I7_3_receive;
import pa.interfaces.receive._v30.I7_7_receive;
import pa.interfaces.send._v30.I7_7_send;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;

import config.clsBWProperties;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:22:59
 * 
 */
public class E47_ConversionToPrimaryProcess extends clsModuleBase implements I7_3_receive, I7_7_send {
	public static final String P_MODULENUMBER = "47";
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:56:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E47_ConversionToPrimaryProcess(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
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
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (KOHLHAUSER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I7_7(new ArrayList<clsPrimaryDataStructureContainer>());

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v30.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
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
	 * 03.03.2011, 16:56:32
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
	 * 03.03.2011, 18:03:53
	 * 
	 * @see pa.interfaces.send._v30.I7_7_send#send_I7_7(java.util.ArrayList)
	 */
	@Override
	public void send_I7_7(
			ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		((I7_7_receive)moModuleList.get(46)).receive_I7_7(poGrantedPerception);
		putInterfaceData(I7_7_send.class, poGrantedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 18:03:53
	 * 
	 * @see pa.interfaces.receive._v30.I7_3_receive#receive_I7_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		
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
		moDescription = "Contents of various action plans can be used to reduce libido tension in E45. Before they can be processed by primary process functions, they have to be converted back again. The preconscious parts of the contents - the word presentations - are removed by this module.";
	}	
}
