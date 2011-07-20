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
import pa._v38.interfaces.modules.D3_2_send;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_4_receive;
import pa._v38.interfaces.modules.I5_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.storage.clsBlockedContentStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - This function reduces the affect values of drives by spliting them according to the attached modules. It controls the amount of the neutralized drive energy and generates lust 
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:42
 * 
 */
public class F56_Desexualization_Neutralization extends clsModuleBase
		implements I5_3_receive, I5_4_send {

	public static final String P_MODULENUMBER = "56";
	
	int ReducedPsychicEnergy;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_IN;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_OUT;
	

	
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
		text += toText.valueToTEXT("moDrives_IN", moDrives_IN);	
		text += toText.valueToTEXT("moDrives_OUT", moDrives_OUT);		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:28:25
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_receive#receive_I5_3(java.util.ArrayList)
	 */
	
	@Override
	public void receive_I5_3(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives) {
		
		moDrives_IN = new ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>(); 
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
		
		reducedAffectValues();
	
	}

	
	/**
	 * DOCUMENT (hinterleitner) - Affect values are reduced according to the modules that need psychic energy.
	 * Psychic energy is calculated based on the hash code, in order not to take pseudo values. 
	 * As there can be nagative values in the hash code there is maultiplication with the factor (-1), because psychic energy can 
	 * not be negative. Finaly the affect values are divided based on the number of modules.
	 *
	 * @since 17.07.2011 17:10:36
	 *
	 * @param moDrives_OUT2
	 */
	private void reducedAffectValues() {


	     for(int index = 0; index < moDrives_IN.size(); index++){
				
	     } int i=0;
				System.out.println(moDrives_IN.indexOf(i));
			i++;
		 }
	     
	       // aus all den drive meshes die reinkommen 
	     //pleasure = affect value siehe Heimo Diss
	     //search for all DM . Zahlenwert
	     
	     //in double 
	     //auf eins normalisieren


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
		send_D3_2(); //schicke Energie zu D3_2

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
		mnProcessType = eProcessType.PRIMARY;
		
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
		mnPsychicInstances = ePsychicInstances.EGO;
		
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


	/**
	 * DOCUMENT (hinterleitner) - Reduced Psychic energy is send to the clsPsychicEnergyStorage, where the interfaces are implemented  
	 * @return 
	 *
	 * @since 17.07.2011 17:26:35
	 *
	 */
	public int send_D3_2() {
	
		putInterfaceData(D3_2_send.class, ReducedPsychicEnergy);
		
		return ReducedPsychicEnergy; 
		//putInterfaceData(I5_4_send.class);
		// TODO (hinterleitner) - Auto-generated method stub
		
	}



}
