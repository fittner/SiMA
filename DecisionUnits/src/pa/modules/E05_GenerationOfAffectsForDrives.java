/**
 * E5_GenerationOfAffectsForDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:58:45
 */
package pa.modules;

import java.util.ArrayList;

import pa.datatypes.clsAffect;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentationMesh;
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

	ArrayList<clsPair<clsThingPresentationMesh, clsAffectCandidate>> moDriveCandidate;
	ArrayList<clsPrimaryInformation> moDriveList;
	
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
	@Override
	public void receive_I1_4(ArrayList<clsPair<clsThingPresentationMesh, clsAffectCandidate>> poDriveCandidate) {
		moDriveCandidate = poDriveCandidate;
		
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

		moDriveList = new ArrayList<clsPrimaryInformation>();
		
		for( clsPair<clsThingPresentationMesh, clsAffectCandidate> oDriveCandidate : moDriveCandidate ) {
			clsThingPresentationMesh oTPMesh = oDriveCandidate.left;
			clsAffectCandidate oAffectCandidate = oDriveCandidate.right;
			
			clsPrimaryInformation oPrimaryInformation = new clsPrimaryInformation();
			oPrimaryInformation.moTP = oTPMesh;
			oPrimaryInformation.moAffect = new clsAffect(oAffectCandidate);
			
			moDriveList.add(oPrimaryInformation);
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
		((I1_5)moEnclosingContainer).receive_I1_5(moDriveList);
		
	}

}
