/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I1_5;
import pa.interfaces.I1_6;
import pa.interfaces.I3_1;
import pa.interfaces.I4_1;
import pa.interfaces.I4_3;
import pa.interfaces.I5_1;
import pa.interfaces.I6_3;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 * 
 */
public class E06_DefenseMechanismsForDriveContents extends clsModuleBase implements I1_5, I3_1, I4_3, I6_3 {
	ArrayList<clsPrimaryInformation> moDriveList_Input;
	ArrayList<clsPrimaryInformation> moDriveList_Output;
	
	ArrayList<clsPrimaryInformation> moRepressedRetry_Input;
	
	ArrayList<clsThingPresentation> moDeniedThingPresentations; 
	ArrayList<clsAffectTension> moDeniedAffects;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:01:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E06_DefenseMechanismsForDriveContents(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		
		moDriveList_Input = new ArrayList<clsPrimaryInformation>();
		moDriveList_Output = new ArrayList<clsPrimaryInformation>();
		moDeniedThingPresentations = new ArrayList<clsThingPresentation>();
		moDeniedAffects = new ArrayList<clsAffectTension>();
		
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
		mnPsychicInstances = ePsychicInstances.EGO;
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
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moDriveList_Input = (ArrayList<clsPrimaryInformation>)deepCopy( (ArrayList<clsPrimaryInformation>)poData);
		
	}

	/* Input from Super-Ego = E7
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		mnTest += pnData;
		
	}

	/* Input from Repressed Content E15
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		moRepressedRetry_Input = (ArrayList<clsPrimaryInformation>)deepCopy( (ArrayList<clsPrimaryInformation>)poPIs);
	}

	/* Input from Knowledge about Reality E9
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:36
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		moDriveList_Output = moDriveList_Input; //pass everything through (deepCopy is called in the next module)
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I1_6)moEnclosingContainer).receive_I1_6(moDriveList_Output);
		((I4_1)moEnclosingContainer).receive_I4_1(moDriveList_Input, moDeniedThingPresentations, moDeniedAffects);
		((I5_1)moEnclosingContainer).receive_I5_1(moDeniedAffects);	
		
		//FIXME (langr) - moPrimaryInformation.clear();
		moDeniedThingPresentations.clear();
		moDeniedAffects.clear();
	}
}
