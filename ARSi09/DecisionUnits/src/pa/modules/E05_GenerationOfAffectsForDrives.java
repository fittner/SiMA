/**
 * E5_GenerationOfAffectsForDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 */
package pa.modules;

import java.util.ArrayList;

import pa.datatypes.clsAffectTension;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.I1_4;
import pa.interfaces.I1_5;
import pa.interfaces.itfTimeChartInformationContainer;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 * 
 */
public class E05_GenerationOfAffectsForDrives extends clsModuleBase implements I1_4, itfTimeChartInformationContainer {

	public ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
	clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> moDriveCandidate;
	
	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moDriveList;
	
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
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
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
	public void receive_I1_4(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
	  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate) {
		moDriveCandidate = (ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
		  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>>) deepCopy( poDriveCandidate);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:56
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {

		moDriveList = new ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>();
		
		for( clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
		  		     clsPair<clsPrimaryInformationMesh, clsAffectCandidate>> oDriveCandidate : moDriveCandidate ) {

			//for a constant increase of the affect values, the following function is implemented:
			//1.: life-instinct increases faster than death-instinct
			//2.: life-instinct reaches maximum (death-instinct at 50%) and decreases
			//3.: death-instinct reaches maximum (--> should result in deatch)
			double oLiveAffect  = Math.sin(Math.PI*oDriveCandidate.a.b.getTensionValue());
			double oDeathAffect = (2-(Math.cos(Math.PI*oDriveCandidate.b.b.getTensionValue())+1))/2;
			
			//finally create the affect in the primary-mesh using the affect candidate 
			oDriveCandidate.a.a.moAffect = new clsAffectTension(oLiveAffect);
			oDriveCandidate.b.a.moAffect = new clsAffectTension(oDeathAffect);
			
			moDriveList.add(new clsPair<clsPrimaryInformation, clsPrimaryInformation>(oDriveCandidate.a.a, oDriveCandidate.b.a));
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
		for( clsPair<clsPrimaryInformation, clsPrimaryInformation> oPair : moDriveList ) {
			oOutput.add(oPair.a);
			oOutput.add(oPair.b);
		}
		
		((I1_5)moEnclosingContainer).receive_I1_5(oOutput);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.12.2009, 10:50:36
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<clsPair<String, Double>> getTimeChartData() {

		//public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moDriveList;
		ArrayList<clsPair<String, Double>> oTimingValues = new ArrayList<clsPair<String,Double>>();
		for( clsPair<clsPrimaryInformation, clsPrimaryInformation> oPair : moDriveList ) {
			
			clsPair<String, Double> oLibi = new clsPair<String, Double>(oPair.a.moTP.moContent.toString(), oPair.a.moAffect.getValue());
			clsPair<String, Double> oDeath = new clsPair<String, Double>(oPair.b.moTP.moContent.toString(), oPair.b.moAffect.getValue());
			
			oTimingValues.add(oLibi);
			oTimingValues.add(oDeath);
		}
		return oTimingValues;
	}

}
