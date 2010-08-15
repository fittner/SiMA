/**
 * E5_GenerationOfAffectsForDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 */
package pa.modules;

import java.util.ArrayList;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.itfTimeChartInformationContainer;
import pa.interfaces.receive.I1_4_receive;
import pa.interfaces.receive.I1_5_receive;
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
public class E05_GenerationOfAffectsForDrives extends clsModuleBase implements I1_4_receive, itfTimeChartInformationContainer {

	public ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
	clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> moDriveCandidate_old;
	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moDriveList_old;
	
	public ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> moDriveCandidate;
	public ArrayList<clsDriveMesh> moDriveList; 
		
	/**
	 * DOCUMENT (deutsch) - insert description 
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
	public void receive_I1_4(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>,clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate_old,
							 ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		
		moDriveCandidate_old = (ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
		  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>>) deepCopy( poDriveCandidate_old);
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
		process_oldDT();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 * This method is used while adapting the model from the old datatypes (pa.datatypes) to the
	 * new ones (pa.memorymgmt.datatypes) The method has to be deleted afterwards.
	 * @author zeilinger
	 * 13.08.2010, 09:56:48
	 * @deprecated
	 */
	private void process_oldDT() {
		moDriveList_old = new ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>();
		
		for( clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
		  		     clsPair<clsPrimaryInformationMesh, clsAffectCandidate>> oDriveCandidate : moDriveCandidate_old ) {

			//for a constant increase of the affect values, the following function is implemented:
			//1.: life-instinct increases faster than death-instinct
			//2.: life-instinct reaches maximum (death-instinct at 50%) and decreases
			//3.: death-instinct reaches maximum (--> should result in deatch)
			double oLiveAffect  = Math.sin(Math.PI*oDriveCandidate.a.b.getTensionValue());
			double oDeathAffect = (2-(Math.cos(Math.PI*oDriveCandidate.b.b.getTensionValue())+1))/2;
			
			//finally create the affect in the primary-mesh using the affect candidate 
			oDriveCandidate.a.a.moAffect = new clsAffectTension(oLiveAffect);
			oDriveCandidate.b.a.moAffect = new clsAffectTension(oDeathAffect);
			
			moDriveList_old.add(new clsPair<clsPrimaryInformation, clsPrimaryInformation>(oDriveCandidate.a.a, oDriveCandidate.b.a));
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
		
		//now decoupling drives from list - information still in clsDrive
		ArrayList<clsPrimaryInformation> oOutput = new ArrayList<clsPrimaryInformation>();
		for( clsPair<clsPrimaryInformation, clsPrimaryInformation> oPair : moDriveList_old) {
			oOutput.add(oPair.a);
			oOutput.add(oPair.b);
		}
		
		((I1_5_receive)moEnclosingContainer).receive_I1_5(oOutput, moDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.12.2009, 10:50:36
	 * 
	 * FIXME (deutsch) what is the purpose of this function? can it be replaced by something which accesses the interface handler?
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<clsPair<String, Double>> getTimeChartData() {

		//public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moDriveList;
		ArrayList<clsPair<String, Double>> oTimingValues = new ArrayList<clsPair<String,Double>>();
		for( clsPair<clsPrimaryInformation, clsPrimaryInformation> oPair : moDriveList_old) {
			
			clsPair<String, Double> oLibi = new clsPair<String, Double>(oPair.a.moTP.moContent.toString(), oPair.a.moAffect.getValue());
			clsPair<String, Double> oDeath = new clsPair<String, Double>(oPair.b.moTP.moContent.toString(), oPair.b.moAffect.getValue());
			
			oTimingValues.add(oLibi);
			oTimingValues.add(oDeath);
		}
		return oTimingValues;
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

}
