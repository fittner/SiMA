/**
 * E15_2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:18:15
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.receive.I4_1_receive;
import pa.interfaces.receive.I4_2_receive;
import pa.interfaces.receive.I4_3_receive;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:18:15
 * 
 */
public class S_ManagementOfRepressedContents_2 extends clsModuleBase implements I4_1_receive, I4_2_receive {
	ArrayList<clsPrimaryInformation> moPrimaryInformation;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 07.10.2009, 11:20:29
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public S_ManagementOfRepressedContents_2(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		moPrimaryInformation = new ArrayList<clsPrimaryInformation>();
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
	 * 07.10.2009, 11:19:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffectTension> poAffects) {
		if (poTPs.size() > 0) {
			throw new java.lang.IllegalArgumentException("thing presentations without attached affect not supported, currently.");
		}
		if (poAffects.size() > 0) {
			throw new java.lang.IllegalArgumentException("affects not attached tothing presentations not supported, currently.");
		}
		
		moPrimaryInformation.addAll((ArrayList<clsPrimaryInformation>) deepCopy((ArrayList) poPIs) );
	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:19:54
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryInformation> poPIs, ArrayList<clsThingPresentation> poTPs, ArrayList<clsAffectTension> poAffects) {
		//mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:46
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
	 * 07.10.2009, 11:20:46
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I4_3_receive)moEnclosingContainer).receive_I4_3(new ArrayList<clsPrimaryInformation>());
		
		moPrimaryInformation.clear();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:46
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:46
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

}
