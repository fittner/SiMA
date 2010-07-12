/**
 * E20_InnerPerception_Affects.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.receive.I5_1_receive;
import pa.interfaces.receive.I5_2_receive;
import pa.interfaces.receive.I5_3_receive;
import pa.interfaces.receive.I5_4_receive;
import pa.interfaces.receive.I5_5_receive;
import pa.interfaces.send.I5_5_send;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 * 
 */
public class E20_InnerPerception_Affects extends clsModuleBase implements I5_1_receive, I5_2_receive, I5_3_receive, I5_4_receive, I5_5_send {

	private ArrayList<clsAffectTension> moAffectOnlyList;
	private ArrayList<clsAffectTension> moDeniedAffects_Input;
	private ArrayList<clsSecondaryInformation> moPerception;
	private ArrayList<clsSecondaryInformation> moDriveList_Input;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:41:04
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E20_InnerPerception_Affects(String poPrefix, clsBWProperties poProp,
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
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList) {
		moAffectOnlyList = (ArrayList<clsAffectTension>)this.deepCopy(poAffectOnlyList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects) {
		moDeniedAffects_Input = (ArrayList<clsAffectTension>)this.deepCopy(poDeniedAffects);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_3(ArrayList<clsSecondaryInformation> poDriveList) {
		moDriveList_Input = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_4#receive_I5_4(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_4(ArrayList<clsSecondaryInformation> poPerception) {
		moPerception = (ArrayList<clsSecondaryInformation>)this.deepCopy(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_5(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:46:11
	 * 
	 * @see pa.interfaces.send.I5_5_send#send_I5_5(int)
	 */
	@Override
	public void send_I5_5(int pnData) {
		((I5_5_receive)moEnclosingContainer).receive_I5_5(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:00
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
	 * 12.07.2010, 10:47:00
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

}
