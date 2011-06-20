/**
 * E5_GenerationOfAffectsForDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import pa._v30.tools.clsDriveValueSplitter;
import pa._v30.tools.clsPair;
import pa._v30.tools.eDriveValueSplitter;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.interfaces.modules.I1_4_receive;
import pa._v30.interfaces.modules.I2_15_receive;
import pa._v30.interfaces.modules.I2_15_send;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import config.clsBWProperties;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 * 
 */
public class E05_AccumulationOfAffectsForSelfPreservationDrives extends clsModuleBase implements 
						I1_4_receive, I2_15_send, itfInspectorGenericTimeChart {
	public static final String P_MODULENUMBER = "05";
	public static final String P_SPLITFACTORLABEL = "label";
	public static final String P_SPLITFACTORVALUE = "value";
	public static final String P_NUM_SPLIFACTOR = "num";
	
	private ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> moDriveCandidate;
	private ArrayList<clsDriveMesh> moDriveList; 
	private HashMap<String, Double> moSplitterFactor;	
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:58:36
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E05_AccumulationOfAffectsForSelfPreservationDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);					
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moDriveCandidate", moDriveCandidate);
		text += toText.listToTEXT("moDriveList", moDriveList);
		text += toText.mapToTEXT("moSplitterFactor", moSplitterFactor);
		
		return text;
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		int i=0;
		
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
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moSplitterFactor = new HashMap<String, Double>();
		
		int num = poProp.getPropertyInt(pre+P_NUM_SPLIFACTOR);
		for (int i=0; i<num; i++) {
			String oKey = poProp.getProperty(pre+i+"."+P_SPLITFACTORLABEL);
			Double oValue = poProp.getPropertyDouble(pre+i+"."+P_SPLITFACTORVALUE);
			moSplitterFactor.put(oKey, oValue);
		}		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_4(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		
		moDriveCandidate = (ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>>) deepCopy(poDriveCandidate); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:56
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moDriveList = new ArrayList<clsDriveMesh>(); 
		
		for(clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>> oEntry : moDriveCandidate){
			double rFactor = 0.5;
			try {
				for (Map.Entry<String, Double> oSF:moSplitterFactor.entrySet()) {
					if (oEntry.a.a.toString().contains(oSF.getKey())) {
						rFactor = oSF.getValue();
					}
				}
			} catch (java.lang.Exception e) {
				//do nothing
			}			
			//RL:
			//for a constant increase of the affect values, the following function is implemented:
			//1.: life-instinct increases faster than death-instinct
			//2.: life-instinct reaches maximum (death-instinct at 50%) and decreases
			//3.: death-instinct reaches maximum (--> should result in deatch)
			clsPair<Double, Double> oSplitResult = clsDriveValueSplitter.calc(oEntry.a.b.getTension(), oEntry.b.b.getTension(), 
					eDriveValueSplitter.ADVANCED, rFactor); 
			
			double oLifeAffect  = oSplitResult.a;
			double oDeathAffect = oSplitResult.b;
			oEntry.a.a.setPleasure(oLifeAffect); 
			oEntry.b.a.setPleasure(oDeathAffect); 
			moDriveList.add(oEntry.a.a); 
			moDriveList.add(oEntry.b.a); 
		}
	}
		
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:56
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_15(moDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:16
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:16
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:58:42
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
	 * 03.03.2011, 15:59:28
	 * 
	 * @see pa.interfaces.send._v30.I2_15_send#send_I2_15(java.util.ArrayList)
	 */
	@Override
	public void send_I2_15(ArrayList<clsDriveMesh> poDriveList) {
		((I2_15_receive)moModuleList.get(38)).receive_I2_15(poDriveList);
		putInterfaceData(I2_15_send.class, poDriveList);
	}
	
	public ArrayList<clsDriveMesh> getDriveList() {
		return moDriveList;
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
		moDescription = "Analogous to E42, E5 attaches quota of affects to the memory traces containing the drive contents. The difference is that the neurosymbols representing the drive tensions have been forwarded from {E2} through {E3} and {E4}. Thus, they are transferred into psychic processable form in this module. ";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 17:41:33
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		
		for( clsDriveMesh oDM : moDriveList) {
			oCaptions.add(oDM.getMoContent());
		}
		
		return oCaptions;
	}	
	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.11.2010, 23:00:57
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oTimingValues = new ArrayList<Double>();
		
		for( clsDriveMesh oDM : moDriveList) {
			oTimingValues.add(oDM.getPleasure());
		}
		return oTimingValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:31:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorTimeChart#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Self-Preservation Drives: Drive-Quota of Affect Chart";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:31:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:31:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:34:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Quota of Affects";
	}	
}



	
