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

import pa._v38.interfaces.itfInspectorBarChart;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_4_receive;
import pa._v38.interfaces.modules.I5_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.toText;
import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import du.enums.pa.ePartialDrive;

/**
 * This function reduces the affect values of drives by spliting them according to the attached modules. 
 * It controls the amount of the neutralized drive energy and generates lust 
 * 
 * @author zeilinger
 * 07.05.2012, 15:47:42
 * 
 */
public class F56_Desexualization_Neutralization extends clsModuleBase
implements I5_3_receive, I5_4_send, itfInspectorBarChart {

	public static final String P_MODULENUMBER = "56";

	public static final String P_ENERGY_REDUCTION_RATE_SEXUAL = "ENERGY_REDUCTION_RATE_SEXUAL";
	public static final String P_ENERGY_REDUCTION_RATE_SELF_PRESERV = "ENERGY_REDUCTION_RATE_SELF_PRESERV";
	
	/*
	 * Input/Output of module
	 */
	private ArrayList<clsDriveMesh> moDrives_IN;
	private ArrayList<clsDriveMesh> moDrives_OUT;
	
	/** Reference to the storage for freed psychic energy, to distribute it to other modules.; @since 12.10.2011 19:28:27 */
	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	/** Personality parameter, determines how much drive energy is reduced.; @since 12.10.2011 19:18:39 */
	private double mrEnergyReductionRateSexual;
	private double mrEnergyReductionRateSelfPreserv;

	/**
	 * property key where the selected implementation stage is stored.
	 * @since 12.07.2011 14:54:42
	 * */
	public static String P_PROCESS_IMPLEMENTATION_STAGE = "IMP_STAGE"; 

	
	private HashMap<String,Double> moChartInputData;
	private HashMap<String,Double> moChartOutputData;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());

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
			DT3_PsychicEnergyStorage poPsychicEnergyStorage , clsPersonalityParameterContainer poPersonalityParameterContainer)
	throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
		
		applyProperties(poPrefix, poProp); 
		mrEnergyReductionRateSexual=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_ENERGY_REDUCTION_RATE_SEXUAL).getParameterDouble();
		mrEnergyReductionRateSelfPreserv=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_ENERGY_REDUCTION_RATE_SELF_PRESERV).getParameterDouble();

		moChartInputData = new HashMap<String,Double>();
		moChartOutputData = new HashMap<String,Double>();
		
		
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
			ArrayList<clsDriveMesh> poDrives) {

		moDrives_IN = (ArrayList<clsDriveMesh>)deepCopy(poDrives);
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
		
		// 1.Reduce drive energy and send it to DT3
		
		double sumReducedEnergy = 0.0;
		// copy input to allow comparison before/after
		moDrives_OUT = (ArrayList<clsDriveMesh>)deepCopy(moDrives_IN);
		
		// take energy from drives attached to the perception
		for (clsDriveMesh oEntry : moDrives_OUT) {
			// take specified amount of drive energy
			
			if (oEntry.getPartialDrive() != ePartialDrive.UNDEFINED) {
				sumReducedEnergy += oEntry.getQuotaOfAffect() * mrEnergyReductionRateSexual;
				// update the drive energy 
				oEntry.setQuotaOfAffect(oEntry.getQuotaOfAffect() * (1 - mrEnergyReductionRateSexual));
			}
			else {
				sumReducedEnergy += oEntry.getQuotaOfAffect() * mrEnergyReductionRateSelfPreserv;
				
				// update the drive energy 
				oEntry.setQuotaOfAffect(oEntry.getQuotaOfAffect() * (1 - mrEnergyReductionRateSelfPreserv));
			}

			
		}
		
		
		// send free drive energy to DT3 for distribution to other modules
		moPsychicEnergyStorage.receive_D3_1(sumReducedEnergy);
		
		
		
		
		
		// 2. Distribute free energy
		
		moPsychicEnergyStorage.divideFreePsychicEnergy();
		
		// also include libido from DT1 (MZ: really? I'm still not sure about this, but IH tells me to do this.)
		/*reducedEnergy = 0.0;
		reducedEnergy = moLibidoBuffer.send_D1_2() * mrEnergyReductionRate;
		// update libidobuffer
		moLibidoBuffer.receive_D1_3(reducedEnergy);*/
		// send free drive energy to DT3 for distribution to other modules
		//moPsychicEnergyStorage.receive_D3_1(reducedEnergy);
		
		
		
		//create chart Data
		for( clsDriveMesh oDriveMeshEntry:moDrives_OUT){
			String oaKey = oDriveMeshEntry.getChartShortString();
			moChartOutputData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
		for( clsDriveMesh oDriveMeshEntry:moDrives_IN){
			String oaKey = oDriveMeshEntry.getChartShortString();
			moChartInputData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
		
	}

	public static clsProperties getDefaultProperties(String poPrefix) {

		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());


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
			ArrayList<clsDriveMesh> poDrives) {

		((I5_4_receive)moModuleList.get(55)).receive_I5_4(poDrives);

		putInterfaceData(I5_4_send.class, poDrives);
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 9:02:17 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartTitle()
	 */
	@Override
	public String getBarChartTitle() {
		return "Drives Inputs and Outputs";
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 9:02:17 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getBarChartData() {
		ArrayList<Double> oInput= new ArrayList<Double>();
		oInput.addAll(moChartInputData.values());
		
		ArrayList<Double> oOutput= new ArrayList<Double>();
		oOutput.addAll(moChartOutputData.values());
		
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		oResult.add(oInput);
		oResult.add(oOutput);
		
		return oResult;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 9:02:17 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartCategoryCaptions()
	 */
	@Override
	public ArrayList<String> getBarChartCategoryCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("input values");
		oResult.add("output values");
		return oResult;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 9:02:17 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartColumnCaptions()
	 */
	@Override
	public ArrayList<String> getBarChartColumnCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moChartOutputData.keySet());

		return oResult;

	}

}
