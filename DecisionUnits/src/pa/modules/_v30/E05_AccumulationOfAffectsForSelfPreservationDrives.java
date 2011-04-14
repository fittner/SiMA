/**
 * E5_GenerationOfAffectsForDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces.itfTimeChartInformationContainer;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I1_4_receive;
import pa.interfaces.receive._v30.I2_15_receive;
import pa.interfaces.send._v30.I2_15_send;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 * 
 */
public class E05_AccumulationOfAffectsForSelfPreservationDrives extends clsModuleBase implements 
						I1_4_receive, I2_15_send, itfTimeChartInformationContainer {
	public static final String P_MODULENUMBER = "05";
	
	private ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> moDriveCandidate;
	private ArrayList<clsDriveMesh> moDriveList; 
	/**
	 * DOCUMENT (deutsch) - insert description 
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
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
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
			//RL:
			//for a constant increase of the affect values, the following function is implemented:
			//1.: life-instinct increases faster than death-instinct
			//2.: life-instinct reaches maximum (death-instinct at 50%) and decreases
			//3.: death-instinct reaches maximum (--> should result in deatch)
			double oLifeAffect  = Math.sin(Math.PI*oEntry.a.b.getTension());
			double oDeathAffect = (2-(Math.cos(Math.PI*oEntry.b.b.getTension())+1))/2;
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
		// TODO (deutsch) - Auto-generated method stub
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
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.11.2010, 23:00:57
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<clsPair<String, Double>> getTimeChartData() {
		ArrayList<clsPair<String, Double>> oTimingValues = new ArrayList<clsPair<String,Double>>();
		
		for( clsDriveMesh oDM : moDriveList) {
			clsDriveMesh oLifeDM = oDM; 
			clsDriveMesh oDeathDM = moDriveList.get(moDriveList.indexOf(oDM)+1); 
			clsPair<String, Double> oLibi = new clsPair<String, Double>(oLifeDM.getMoContent(), oLifeDM.getPleasure());
			clsPair<String, Double> oDeath = new clsPair<String, Double>(oDeathDM.getMoContent(), oDeathDM.getPleasure());
			
			oTimingValues.add(oLibi);
			oTimingValues.add(oDeath);
			
			if(moDriveList.indexOf(oDeathDM) == moDriveList.size()-1){
				break; 
			}
		}
		return oTimingValues;
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
	}
	
	public ArrayList<clsDriveMesh> getDriveList() {
		return moDriveList;
	}
}



	
