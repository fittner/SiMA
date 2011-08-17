/**
 * E23_ExternalPerception_focused.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:46:53
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfMinimalModelMode;
import pa._v30.interfaces.modules.I1_7_receive;
import pa._v30.interfaces.modules.I2_11_receive;
import pa._v30.interfaces.modules.I2_12_receive;
import pa._v30.interfaces.modules.I2_12_send;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v30.tools.toText;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 14:46:53
 * 
 */
public class E23_ExternalPerception_focused extends clsModuleBase implements itfMinimalModelMode, I2_11_receive, I1_7_receive, I2_12_send {
	public static final String P_MODULENUMBER = "23";
	
	private ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList; 
	private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception_Output; 
	private boolean mnMinimalModel;
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:50:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E23_ExternalPerception_focused(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
		text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.listToTEXT("moPerception", moPerception);
		text += toText.listToTEXT("moDriveList", moDriveList);
		text += toText.listToTEXT("moFocusedPerception_Output", moFocusedPerception_Output);

		return text;
	}	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		mnMinimalModel = false;
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
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moPerception = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		if (!mnMinimalModel) {				
			// HZ 23.08.2010: Normally the perceived information has to be ordered by its priority
			//that depends on the evaluation of external and internal perception (moDriveList); 
			//
			//Actual state: no ordering! 
			moFocusedPerception_Output = moPerception;
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		if (mnMinimalModel) {		
			send_I2_12(moPerception, new ArrayList<clsSecondaryDataStructureContainer>());
		} else {
			send_I2_12(moFocusedPerception_Output, moDriveList);
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
			   				ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I2_12_receive)moModuleList.get(24)).receive_I2_12(poFocusedPerception, poDriveList);
		((I2_12_receive)moModuleList.get(25)).receive_I2_12(poFocusedPerception, poDriveList);
		
		putInterfaceData(I2_12_send.class, poFocusedPerception, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:20
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
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:50:13
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The task of this module is to focus the external perception on ``important'' things. Thus, the word presentations originating from perception are ordered according to their importance to existing drive wishes. This could mean for example that an object is qualified to satisfy a bodily need. The resulting listthe package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importanceis forwarded by the interface {I2.12} to {E24} and {E25}. These two modules are part of reality check.";
	}	
	
	@Override
	public void setMinimalModelMode(boolean pnMinial) {
		mnMinimalModel = pnMinial;
	}

	@Override
	public boolean getMinimalModelMode() {
		return mnMinimalModel;
	}	
	
}
