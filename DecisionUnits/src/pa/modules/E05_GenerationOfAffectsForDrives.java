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
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 * 
 */
public class E05_GenerationOfAffectsForDrives extends clsModuleBase implements I1_4 {

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

			//finally create the affect in the primary-mesh using the affect candidate 
			oDriveCandidate.a.a.moAffect = new clsAffectTension(oDriveCandidate.a.b);
			oDriveCandidate.b.a.moAffect = new clsAffectTension(oDriveCandidate.b.b);
			
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

}
