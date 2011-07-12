/**
 * F49_PrimalRepressionForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:30
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.modules.ePsychicInstances;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_2_receive;
import pa._v38.interfaces.modules.I5_2_send;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:30
 * 
 */
public class F49_PrimalRepressionForDrives extends clsModuleBase 
			implements I5_1_receive, I5_2_send{

	public static final String P_MODULENUMBER = "49";
	@SuppressWarnings("unused")
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moInput;
	
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moOutput;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:51:29
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F49_PrimalRepressionForDrives(String poPrefix,
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
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moInput", moInput);	
		text += toText.valueToTEXT("moOutput", moOutput);		
				
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:12:55
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_1(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		
		moInput = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poData); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		//moOutput =  deepCopy(moInput); 
		moOutput = new ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_2(moOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
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
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		//FIXME CM english descriptiopn
		moDescription = "Anhängen von urverdrängten Inhalte an die erinnerten Triebinhalte, wenn einen Assoziation möglich ist => 'Erinnerte Triebinhalte mit assoziierten Urverdrängungen'";
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:12:55
	 * 
	 * @see pa._v38.interfaces.modules.I5_2_send#send_I5_2(java.util.ArrayList)
	 */
	@Override
	public void send_I5_2(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		
		((I5_2_receive)moModuleList.get(54)).receive_I5_2(poData);
		
		putInterfaceData(I5_2_send.class, poData);
	}
}
