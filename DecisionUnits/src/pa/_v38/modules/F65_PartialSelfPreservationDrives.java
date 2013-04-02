/**
 * CHANGELOG
 *
 * Apr 2, 2013 herret - File created
 *
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I2_2_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I3_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.tools.clsPair;
import config.clsProperties;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Apr 2, 2013, 1:56:16 PM
 * 
 */
public class F65_PartialSelfPreservationDrives extends clsModuleBase implements I2_2_receive, I3_4_send{

	
	public static final String P_MODULENUMBER = "65";
	private ArrayList<clsPair<clsDriveMesh, clsDriveMesh>> moSelfPreservationDrives_OUT;
	
	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since Apr 2, 2013 1:56:22 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F65_PartialSelfPreservationDrives(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		// TODO (herret) - Auto-generated constructor stub
	}

	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}
	
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		// TODO (herret) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_4(moSelfPreservationDrives_OUT);
		

	}


	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:57:27 PM
	 * 
	 * @see pa._v38.interfaces.modules.I3_4_send#send_I3_4(java.util.ArrayList)
	 */
	@Override
	public void send_I3_4(
			ArrayList<clsPair<clsDriveMesh, clsDriveMesh>> poDriveComponents) {
		((I3_4_receive)moModuleList.get(48)).receive_I3_4(poDriveComponents);
		putInterfaceData(I3_4_send.class, poDriveComponents);
		
	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:57:28 PM
	 * 
	 * @see pa._v38.interfaces.modules.I2_2_receive#receive_I2_2(java.util.HashMap)
	 */
	@Override
	public void receive_I2_2(HashMap<String, Double> poHomeostasisSymbols) {
		// TODO (herret) - Auto-generated method stub
		
	}

}
