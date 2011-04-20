/**
 * E24_RealityCheck.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:49:09
 */
package pa.modules._v19;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I2_12_receive;
import pa.interfaces.receive._v19.I2_13_receive;
import pa.interfaces.receive._v19.I6_1_receive;
import pa.interfaces.send._v19.I2_13_send;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:49:09
 * 
 */
@Deprecated
public class E24_RealityCheck extends clsModuleBase implements I2_12_receive, I6_1_receive, I2_13_send {

	private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception_Input; 
	private ArrayList<clsSecondaryDataStructureContainer> moRealityPerception_Output; 
	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  //removed by HZ - not required now

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:49:30
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E24_RealityCheck(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
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
		mnProcessType = eProcessType.SECONDARY;
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
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception, ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moFocusedPerception_Input = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poFocusedPerception);
		//moDriveList = (ArrayList<clsSecondaryDataStructureContainer>) deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I6_1#receive_I6_1(int)
	 */
	@Override
	public void receive_I6_1(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:25
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moRealityPerception_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		
		//FIXME HZ 2010.08.24 Functionality of old code is taken; however I am rather sure that it has to be
		//adapted
		for(clsSecondaryDataStructureContainer oCon : moFocusedPerception_Input){
			moRealityPerception_Output.add(oCon); 
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:25
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa.memorymgmt.datatypes
		send_I2_13(moRealityPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:51:11
	 * 
	 * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
	 */
	@Override
	public void send_I2_13(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception) {
		((I2_13_receive)moEnclosingContainer).receive_I2_13(moRealityPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:25
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
	 * 12.07.2010, 10:47:25
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}
}
