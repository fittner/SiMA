/**
 * F54_EmersionOfBlockedDriveContent.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:36
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_2_receive;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_3_send;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:36
 * 
 */
public class F54_EmersionOfBlockedDriveContent extends clsModuleBase
			implements I5_2_receive, I5_3_send{

	public static final String P_MODULENUMBER = "54";
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moInput;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:51:07
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F54_EmersionOfBlockedDriveContent(String poPrefix,
			clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

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
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:15:07
	 * 
	 * @see pa._v38.interfaces.modules.I5_2_receive#receive_I5_2(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_2(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		
		moInput = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poData); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moDrives = moInput; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_3(moDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:15:07
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_send#send_I5_3(java.util.ArrayList)
	 */
	@Override
	public void send_I5_3(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives) {
		
		((I5_3_receive)moModuleList.get(56)).receive_I5_3(poDrives);
		
		putInterfaceData(I5_3_send.class, poDrives);
	}
}
