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

import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_4_receive;
import pa._v38.interfaces.modules.I5_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.storage.DT1_LibidoBuffer;
import pa._v38.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * This function reduces the affect values of drives by spliting them according to the attached modules. 
 * It controls the amount of the neutralized drive energy and generates lust 
 * 
 * @author zeilinger
 * 07.05.2012, 15:47:42
 * 
 */
public class F56_Desexualization_Neutralization extends clsModuleBase
implements I5_3_receive, I5_4_send {

	public static final String P_MODULENUMBER = "56";

	/*
	 * Input/Output of module
	 */
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_IN;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_OUT;
	
	/** Reference to the storage for freed psychic energy, to distribute it to other modules.; @since 12.10.2011 19:28:27 */
	private DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	/** Reference to the libido buffer; @since 12.10.2011 19:35:10 */
	private DT1_LibidoBuffer moLibidoBuffer;
	/** Personality parameter, determines how much drive energy is reduced.; @since 12.10.2011 19:18:39 */
	private double mrEnergyReductionRate = 0.4;

	/**
	 * property key where the selected implementation stage is stored.
	 * @since 12.07.2011 14:54:42
	 * */
	public static String P_PROCESS_IMPLEMENTATION_STAGE = "IMP_STAGE"; 
	public static final String P_SPLITFACTORLABEL = "label";
	public static final String P_SPLITFACTORVALUE = "value";
	public static final String P_NUM_SPLIFACTOR = "num";

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
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage,
			DT1_LibidoBuffer poLibidoBuffer)
	throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moPsychicEnergyStorage = poPsychicEnergyStorage;
		moLibidoBuffer = poLibidoBuffer;
		applyProperties(poPrefix, poProp); 
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
		text += toText.valueToTEXT("moPsychicEnergyStorage", moPsychicEnergyStorage);
		
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
		double reducedEnergy = 0.0;
		// copy input to allow comparison before/after
		moDrives_OUT = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)deepCopy(moDrives_IN);
		
		// take energy from drives attached to the perception
		for (clsPair<clsPhysicalRepresentation, clsDriveMesh> oEntry : moDrives_OUT) {
			// take specified amount of drive energy
			reducedEnergy = 0.0; // initialize for each one, just to be sure.
			reducedEnergy = oEntry.b.getMrPleasure() * mrEnergyReductionRate;
			// update the drive energy 
			oEntry.b.setMrPleasure(oEntry.b.getMrPleasure() * (1 - mrEnergyReductionRate));
			// send free drive energy to DT3 for distribution to other modules
			moPsychicEnergyStorage.receive_D3_1(reducedEnergy);
		}
		
		// also include libido from DT1 (MZ: really? I'm still not sure about this, but IH tells me to do this.)
		reducedEnergy = 0.0;
		reducedEnergy = moLibidoBuffer.send_D1_2() * mrEnergyReductionRate;
		// update libidobuffer
		moLibidoBuffer.receive_D1_3(reducedEnergy);
	// send free drive energy to DT3 for distribution to other modules
		moPsychicEnergyStorage.receive_D3_1(reducedEnergy);
	}

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
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);


		//nothing to do
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

}
