/**
 * E19_DefenseMechanismsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.receive.I2_10_receive;
import pa.interfaces.receive.I2_9_receive;
import pa.interfaces.receive.I3_2_receive;
import pa.interfaces.receive.I4_2_receive;
import pa.interfaces.receive.I5_2_receive;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 * 
 */
public class E19_DefenseMechanismsForPerception extends clsModuleBase implements I2_9_receive, I3_2_receive {

	public ArrayList<clsPrimaryInformation> moSubjectivePerception_Input;
	public ArrayList<clsPrimaryInformation> moFilteredPerception_Output;
	
	ArrayList<clsThingPresentation> moDeniedThingPresentations; 
	ArrayList<clsAffectTension> moDeniedAffects;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:36:00
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E19_DefenseMechanismsForPerception(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
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
		mnPsychicInstances = ePsychicInstances.EGO;
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		moFilteredPerception_Output = moSubjectivePerception_Input;		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I4_2_receive)moEnclosingContainer).receive_I4_2(moFilteredPerception_Output, moDeniedThingPresentations, moDeniedAffects);
		((I2_10_receive)moEnclosingContainer).receive_I2_10(moFilteredPerception_Output);
		((I5_2_receive)moEnclosingContainer).receive_I5_2(moDeniedAffects);	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:28:09
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 24.10.2009, 20:01:39
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {

		moSubjectivePerception_Input = (ArrayList<clsPrimaryInformation>)this.deepCopy(poMergedPrimaryInformation);

	}

}
