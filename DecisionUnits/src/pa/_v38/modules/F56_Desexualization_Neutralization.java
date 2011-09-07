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

import pa._v30.interfaces.modules.D3_8_send;
import pa._v38.interfaces.modules.D3_2_send;
import pa._v38.interfaces.modules.D3_3_send;
import pa._v38.interfaces.modules.D3_4_send;
import pa._v38.interfaces.modules.D3_5_send;
import pa._v38.interfaces.modules.D3_6_send;
import pa._v38.interfaces.modules.D3_7_send;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_4_receive;
import pa._v38.interfaces.modules.I5_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

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
	
	
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_IN;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_OUT;

	/** property key where the selected implemenation stage is stored.; @since 12.07.2011 14:54:42 */
	public static String P_PROCESS_IMPLEMENTATION_STAGE = "IMP_STAGE"; 
	public static final String P_SPLITFACTORLABEL = "label";
	public static final String P_SPLITFACTORVALUE = "value";
	public static final String P_NUM_SPLIFACTOR = "num";
	private HashMap<String, Double> moSplitterFactor;	
	public int j;
	public int ReducedPsychicEnergy = 0;
	

	
	/**
	 * DOCUMENT (zeilinger) - class 
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
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT2_BlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyPropertiesAndReduce(poPrefix, poProp); 
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
		text += toText.listToTEXT("moDrives_IN", moDrives_IN);	
		text += toText.listToTEXT("moDrives_OUT", moDrives_OUT);	
		text += toText.valueToTEXT("ReducedPsychicEnergy", ReducedPsychicEnergy);	
		
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
		
		moDrives_IN = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)deepCopy(poDrives);
		//moDrives_IN = new ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>(); 
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
		
		//Applyproperties(); 
		
		//getDefaultProperties(moDescription); 
		

	
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
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		// see PhD Deutsch2011 p82 for what this is used for		
		int i=0;
		
		//Konfigurationsparameter
		//Definieren und Auslesen von den Properties
		//applyproperties aus dem 
		
		
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "NOURISH");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "BITE");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "RELAX");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "DEPOSIT");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "REPRESS");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "SLEEP");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;

		oProp.setProperty(pre+P_NUM_SPLIFACTOR, i);
		
		return oProp;
	}	
 
	private void applyPropertiesAndReduce(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moSplitterFactor = new HashMap<String, Double>();
		
		int num = poProp.getPropertyInt(pre+P_NUM_SPLIFACTOR);
		for (j=0; j<num;j++) {
			String oKey = poProp.getProperty(pre+j+"."+P_SPLITFACTORLABEL);
			Double oValue = poProp.getPropertyDouble(pre+j+"."+P_SPLITFACTORVALUE);
			moSplitterFactor.put(oKey, oValue);
			ReducedPsychicEnergy = j+2;
			ReducedPsychicEnergy = ReducedPsychicEnergy / 7; //Divided into per Amount Modules
	
		}		
		
//		//sum up property values
//		for (int i=0; i<num; i++) {
//			Double oValue = poProp.getPropertyDouble(pre+i+"."+P_SPLITFACTORVALUE);
//			ReducedPsychicEnergy = moSplitterFactor.get(oValue);
//		}		
//		
		
	}
	
//	private void reducedAffectValues(String poPrefix, clsProperties poProp) {
//	  
//		String pre = clsProperties.addDot(poPrefix);
//		moSplitterFactor = new HashMap<String, Double>();
//		
//		int num = poProp.getPropertyInt(pre+P_NUM_SPLIFACTOR);
//		for (j=0; j<num;j++) {
//			String oKey = poProp.getProperty(pre+j+"."+P_SPLITFACTORLABEL);
//			Double oValue = poProp.getPropertyDouble(pre+j+"."+P_SPLITFACTORVALUE);
//			moSplitterFactor.put(oKey, oValue);
//			
//
//		}		
		
	
	     
	     //alle drive meshes die reinkommen  durchgehen 
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
	 * DOCUMENT (hinterleitner) - Reduced psychic energy is send to the clsPsychicEnergyStorage, where the interfaces are implemented  
	 * @return 
	 *
	 * @since 17.07.2011 17:26:35
	 *
	 */
	public double send_D3_2() {
	
		putInterfaceData(D3_2_send.class, ReducedPsychicEnergy);
		
		return ReducedPsychicEnergy; 
		//putInterfaceData(I5_4_send.class);
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	
	
	public double send_D3_3() {
		
		putInterfaceData(D3_3_send.class, ReducedPsychicEnergy);
		
		return ReducedPsychicEnergy; 
		//putInterfaceData(I5_4_send.class);
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	
	public double send_D3_4() {
		
		putInterfaceData(D3_4_send.class, ReducedPsychicEnergy);
		
		return ReducedPsychicEnergy; 
		
		// TODO (hinterleitner) - Auto-generated method stub
		
	}

	public double send_D3_5() {
	
	putInterfaceData(D3_5_send.class, ReducedPsychicEnergy);

	return ReducedPsychicEnergy; 
	// TODO (hinterleitner) - Auto-generated method stub
	
}
	
	public double send_D3_6() {
		
		putInterfaceData(D3_6_send.class, ReducedPsychicEnergy);

		return ReducedPsychicEnergy; 
		// TODO (hinterleitner) - Auto-generated method stub
		
	}	

	public double send_D3_7() {
		
		putInterfaceData(D3_7_send.class, ReducedPsychicEnergy);

		return ReducedPsychicEnergy; 
		// TODO (hinterleitner) - Auto-generated method stub
		
	}

	public double send_D3_8() {
		
		putInterfaceData(D3_8_send.class, ReducedPsychicEnergy);

		return ReducedPsychicEnergy; 
		// TODO (hinterleitner) - Auto-generated method stub
		
	}




}
