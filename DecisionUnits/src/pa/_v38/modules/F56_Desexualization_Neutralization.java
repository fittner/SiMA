/**
 * F56_Desexualization_Neutralization.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:42
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_4_receive;
import pa._v38.interfaces.modules.I5_4_send;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.storage.clsBlockedContentStorage;
import pa._v38.tools.clsPair;
//import pa._v38.tools.toText;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - This function reduces the affect values of drives by spliting them according to the attached modules. It controls the amount of the neutralized drive energy and generates lust 
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:42
 * 
 */
public class F56_Desexualization_Neutralization extends clsModuleBase
		implements I5_3_receive, I5_4_send{

	public static final String P_MODULENUMBER = "56";
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_IN;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_OUT;
	private clsBlockedContentStorage moBlockedContentStorage;
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:54:40
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F56_Desexualization_Neutralization(String poPrefix,
			clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsBlockedContentStorage poBlockedContentStorage)
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
	 * 02.05.2011, 15:54:37
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		return null;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:28:25
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_receive#receive_I5_3(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_3(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives) {
		
		moDrives_IN = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poDrives); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:54:37
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moDrives_OUT = moDrives_IN;
		moDrives_IN.get(mnTest);
		//System.out.println(moBlockedContentStorage);
		//System.out.println(moAttachedRepressed_Output);
		//System.out.println(mnTest);
		
	//	ArrayList<clsPrimaryDataStructureContainer> oContainerList = new ArrayList<clsPrimaryDataStructureContainer>(); 
		
	//	moBlockedContentStorage.receive_D2_4(oContainerList);
	//	moAttachedRepressed_Output = moBlockedContentStorage.receive_D2_3(poData);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:54:37
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
	 * 02.05.2011, 15:54:37
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
	 * 02.05.2011, 15:54:37
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_4(moDrives_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:54:37
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
	 * 02.05.2011, 15:54:37
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
	 * 02.05.2011, 15:54:37
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
	 * 02.05.2011, 15:54:37
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This function reduces the affect values of drives by spliting them according to the attached modules. It controls the amount of the neutralized drive energy and generates lust";
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:28:25
	 * 
	 * @see pa._v38.interfaces.modules.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I5_4(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives) {
		
		((I5_4_receive)moModuleList.get(55)).receive_I5_4(poDrives);
		
		putInterfaceData(I5_4_send.class, poDrives);
	}
}
