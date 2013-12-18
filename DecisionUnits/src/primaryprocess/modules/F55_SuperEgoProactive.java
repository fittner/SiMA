/**
 * F55_SuperEgoProactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:48
 */
package primaryprocess.modules;

import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.storage.DT3_PsychicEnergyStorage;
import modules.interfaces.I5_12_receive;
import modules.interfaces.I5_12_send;
import modules.interfaces.I5_14_send;
import modules.interfaces.I5_21_receive;
import modules.interfaces.I5_4_receive;
import modules.interfaces.I5_5_receive;
import modules.interfaces.I5_5_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import config.clsProperties;
import du.enums.pa.eDriveComponent;

/**
 * Super-Ego pro-active watches internalized rules like "Do a good deed every day" or "Be always friendly" 
 * 
 * @author gelbard
 * 14.09.2011, 15:47:48
 * 
 */
public class F55_SuperEgoProactive extends clsModuleBase
		implements I5_4_receive, I5_5_send, I5_12_send, I5_14_send, I5_21_receive, itfInspectorGenericDynamicTimeChart{

	public static final String P_MODULENUMBER = "55";
	private ArrayList<clsDriveMesh> moDrives_Input;
	private ArrayList<clsDriveMesh> moDrives_Output;	
	private ArrayList<clsEmotion> moEmotions_Input;
	public int ReducedPsychicEnergy;
	public int PsychicEnergy_IN;
	private int step_count = 0;
	
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData;
	
	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:50:18
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F55_SuperEgoProactive(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
		this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
		

		applyProperties(poPrefix, poProp); 
		
		moDriveChartData = new HashMap<String, Double>();
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
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		text += toText.valueToTEXT("moDrives_Input", moDrives_Input);	
		text += toText.valueToTEXT("moDrives_Output", moDrives_Output);		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_4_receive#receive_I5_4(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_4(
			ArrayList<clsDriveMesh> poDrives) {

		moDrives_Input = (ArrayList<clsDriveMesh>) deepCopy(poDrives); 
	}

	/* (non-Javadoc)
	 *
	 * @author hinterleitner
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_4_receive#receive_I5_4(java.util.ArrayList)
	 */
	
	public void receive_D3_2(int ReducedPsychicEnergy)
	{
		receive_D3_2(ReducedPsychicEnergy); 
		PsychicEnergy_IN = ReducedPsychicEnergy;  
		
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moDrives_Output = moDrives_Input;
		
		// check drives and apply pro-active internalizes rules
		//checkInternalizedRules();
		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber, 2, 10);
		
		//write chart Data
		for (clsDriveMesh oDriveMeshEntry : moDrives_Output)
		{
			String oaKey = oDriveMeshEntry.getChartShortString();
			if ( !moDriveChartData.containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moDriveChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
			
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
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
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 14:47:14
	 * 
	 * Super-Ego pro-active checks drives and applies pro-active internalizes rules
	 * like "Be always friendly!" 
	 */
	@SuppressWarnings("unused")
	private void checkInternalizedRules() {
		
		// pro-active Super-Ego rule "Always be friendly to other people!"
		alwaysBeFriendly();
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 28.07.2012, 12:08:14
	 * 
	 * This function represents the pro-active Super-Ego rule "Always be friendly to other people!" 
	 * The functionality is: subtract 0.1 quota of affect from each aggressive drives and add 0.1 quota of affect to each libidinous drives
	 *
	 */
	private void alwaysBeFriendly () {
		// Is the sum of quota of affects after "alwaysBeFriendly" same as before?
		double oDifferenceOfQuotaOfAffect = 0.0;

		// count iteration steps of simulation
		step_count++;
		
		if (step_count >= 10)
		{
			step_count = 0;
			
			// internalized pro-active rule: Always be friendly!
			// shift quota of affect from aggressive drives to libidinous drives
			// (ATTENTION: sum of quota of affect may be changed if 
			// number of libidinous drives > 2 * number of aggressive drives
			// or 
			// number of aggressive drives > 2 * number of libidinous drives)
			for(clsDriveMesh oDrives : moDrives_Input){
				// check DriveMesh
				if (oDrives.getDriveComponent() == eDriveComponent.AGGRESSIVE &&
					oDrives.getQuotaOfAffect() >= 0.1){
					
					oDrives.setQuotaOfAffect(oDrives.getQuotaOfAffect() - 0.1);
					oDifferenceOfQuotaOfAffect -= 0.1;
				}
				else if (oDrives.getDriveComponent() == eDriveComponent.LIBIDINOUS &&
						 oDrives.getQuotaOfAffect() <= 0.9){
						 
						oDrives.setQuotaOfAffect(oDrives.getQuotaOfAffect() + 0.1);
						oDifferenceOfQuotaOfAffect += 0.1;
					 }
			}
			
			
			// Did we add more quota of affect than we subtracted?
			if (oDifferenceOfQuotaOfAffect > 0) {
				for(clsDriveMesh oDrives : moDrives_Input) {
					// check DriveMesh
					if (oDrives.getDriveComponent() == eDriveComponent.AGGRESSIVE &&
						oDrives.getQuotaOfAffect() >= 0.1) {
						
						oDrives.setQuotaOfAffect(oDrives.getQuotaOfAffect() - 0.1);
						oDifferenceOfQuotaOfAffect -= 0.1;
					}
					
					// If the difference is 0 we are done.
					if (oDifferenceOfQuotaOfAffect == 0) break;
				}
			}
			
			// Did we subtract more quota of affect than we added?
			else if (oDifferenceOfQuotaOfAffect < 0) {
				for(clsDriveMesh oDrives : moDrives_Input) {
					// check DriveMesh
					if (oDrives.getDriveComponent() == eDriveComponent.LIBIDINOUS &&
							 oDrives.getQuotaOfAffect() <= 0.9) {
						
							 oDrives.setQuotaOfAffect(oDrives.getQuotaOfAffect() + 0.1);
							 oDifferenceOfQuotaOfAffect += 0.1;
					}
					
					// If the difference is 0 we are done.
					if (oDifferenceOfQuotaOfAffect == 0) break;
				}
			}
			
		}	

	}
	

	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_5(new ArrayList<clsDriveMesh>());
		send_I5_12(moDrives_Output, moEmotions_Input);
		send_I5_14(new ArrayList<clsDriveMesh>());
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
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
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
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
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		moDescription = "Checks periodically and proactiv internalized rules.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_12_send#send_I5_12(java.util.ArrayList)
	 */
	@Override
	public void send_I5_12(
			ArrayList<clsDriveMesh> poDrives, ArrayList<clsEmotion> poEmotions) {
		
		((I5_12_receive)moModuleList.get(7)).receive_I5_12(poDrives, poEmotions);
		
		putInterfaceData(I5_12_send.class, poDrives, poEmotions);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_5_send#send_I5_5(java.util.ArrayList)
	 */
	@Override
	public void send_I5_5(
			ArrayList<clsDriveMesh> poData) {
		
		((I5_5_receive)moModuleList.get(6)).receive_I5_5(poData);
		
		putInterfaceData(I5_5_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:40:54
	 * 
	 * @see pa._v38.interfaces.modules.I5_14_send#send_I5_14(java.util.ArrayList)
	 */
	@Override
	public void send_I5_14(
			ArrayList<clsDriveMesh> poData) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:46:08
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_receive#receive_I5_21(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_21(ArrayList<clsEmotion> poEmotions) {
		moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions); 
		
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		// TODO (herret) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		// TODO (herret) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		// TODO (herret) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		// TODO (herret) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moDriveChartData.values());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moDriveChartData.keySet());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 6, 2012 11:11:40 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;
		
	}	
}
