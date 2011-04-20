/**
 * E5_GenerationOfAffectsForDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 */
package pa.modules._v19;

import java.util.ArrayList;

import pa._v19.clsInterfaceHandler;
import pa.interfaces._v19.itfTimeChartInformationContainer;
import pa.interfaces.receive._v19.I1_4_receive;
import pa.interfaces.receive._v19.I1_5_receive;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 * 
 */
@Deprecated
public class E05_GenerationOfAffectsForDrives extends clsModuleBase implements I1_4_receive, itfTimeChartInformationContainer {

	public ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> moDriveCandidate;
	public ArrayList<clsDriveMesh> moDriveList; 
		
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 13:59:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E05_GenerationOfAffectsForDrives(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
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
		
		((I1_5_receive)moEnclosingContainer).receive_I1_5(moDriveList);
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

}
