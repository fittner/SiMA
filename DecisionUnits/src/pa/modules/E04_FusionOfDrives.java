/**
 * E4_FusionOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:40:06
 */
package pa.modules;

import java.util.ArrayList;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.receive.I1_3_receive;
import pa.interfaces.receive.I1_4_receive;
import pa.interfaces.send.I1_4_send;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:40:06
 * 
 */
public class E04_FusionOfDrives extends clsModuleBase implements I1_3_receive, I1_4_send {

	ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
	  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> moDriveCandidate;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 13:40:24
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E04_FusionOfDrives(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
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
	public void receive_I1_3(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
	  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate) {
		moDriveCandidate = (ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
		  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>>)deepCopy(poDriveCandidate);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:52
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:52
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_4(moDriveCandidate);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:45:32
	 * 
	 * @see pa.interfaces.send.I1_4_send#send_I1_4(java.util.ArrayList)
	 */
	@Override
	public void send_I1_4(
			ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate) {
		((I1_4_receive)moEnclosingContainer).receive_I1_4(moDriveCandidate);
	}

}
