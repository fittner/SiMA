/**
 * F55_SuperEgoProactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:48
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I5_12_receive;
import pa._v38.interfaces.modules.I5_12_send;
import pa._v38.interfaces.modules.I5_14_send;
import pa._v38.interfaces.modules.I5_21_receive;
import pa._v38.interfaces.modules.I5_4_receive;
import pa._v38.interfaces.modules.I5_5_receive;
import pa._v38.interfaces.modules.I5_5_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Super-Ego pro-active watches internalized rules like "Do a good deed every day" or "Be always friendly" 
 * 
 * @author gelbard
 * 14.09.2011, 15:47:48
 * 
 */
public class F55_SuperEgoProactive extends clsModuleBase
		implements I5_4_receive, I5_5_send, I5_12_send, I5_14_send, I5_21_receive{

	public static final String P_MODULENUMBER = "55";
	private ArrayList<clsDriveMeshOLD> moDrives_Input;
	private ArrayList<clsDriveMeshOLD> moDrives_Output;
	private ArrayList<clsEmotion> moEmotions_Input;
	public int ReducedPsychicEnergy;
	public int PsychicEnergy_IN;
	private int step_count = 0;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:50:18
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F55_SuperEgoProactive(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp); 
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		text += toText.valueToTEXT("moDrives_Input", moDrives_Input);	
		text += toText.valueToTEXT("moDrives_Output", moDrives_Output);		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_4_receive#receive_I5_4(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_4(
			ArrayList<clsDriveMeshOLD> poDrives) {

		moDrives_Input = (ArrayList<clsDriveMeshOLD>) deepCopy(poDrives); 
	}

	/* (non-Javadoc)
	 *
	 * @author hinterleitner
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_4_receive#receive_I5_4(java.util.ArrayList)
	 */
	
	public void receive_D3_2(int ReducedPsychicEnergy)
	{
		receive_D3_2(ReducedPsychicEnergy); 
		PsychicEnergy_IN = ReducedPsychicEnergy;  
		
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moDrives_Output = moDrives_Input;
		
		// check drives and apply pro-active internalizes rules
		//checkInternalizedRules();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 14:47:14
	 * 
	 * Super-Ego pro-active checks drives and applies pro-active internalizes rules
	 * like "Be always friendly!" 
	 */
	@SuppressWarnings("unused")
	private void checkInternalizedRules() {
		// count iteration steps of simulation
		step_count++;
		
		if (step_count >= 10)
		{
			step_count = 0;
			
			// internalized pro-active rule: Be always friendly!
			// shift quota of affect from negative to positive
			// (sum of quota of affect is constant)
			if (getQuotaOfAffectFromDM("NOURISH") < .9 &&
				getQuotaOfAffectFromDM("BITE") > .5	)
			{
				increaseQuotaOfAffectFromDM("NOURISH", .1);
				decreaseQuotaOfAffectFromDM("BITE", .1);
			}
		}	
	}
	

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 14:53:50
	 * 
	 * searches the drive oContent (e.g. "NOURISH") in a drive mesh
	 * and returns the quota of affect (moPleasure) of the drive
	 * 
	 */
	private double getQuotaOfAffectFromDM (String oContent) {		
		// search in drives
		for(clsDriveMeshOLD oDrives : moDrives_Input){
			// check DriveMesh
			// oDrives.b.getMoContent() = for example "NOURISH"
			// oDrives.b.getMoContentType() =  for example "LIFE"
			if (oDrives.getMoContent().equalsIgnoreCase(oContent)){
				return oDrives.getMrQuotaOfAffect();
			}
		}
		return -1;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 14:53:50
	 * 
	 * searches the drive oContent (e.g. "NOURISH") in a drive mesh
	 * and increases the quota of affect (moPleasure) by the amount of oVal
	 * 
	 */
	private void increaseQuotaOfAffectFromDM (String oContent, double oVal) {		
		// search in drives
		for(clsDriveMeshOLD oDrives : moDrives_Input){
			// check DriveMesh
			// oDrives.b.getMoContent() = for example "NOURISH"
			// oDrives.b.getMoContentType() =  for example "LIFE"
			if (oDrives.getMoContent().equalsIgnoreCase(oContent)){
				oDrives.setMrQuotaOfAffect(oDrives.getMrQuotaOfAffect() + oVal);
				return; // only increase the drive which is found first 
			}
		}
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 14:53:50
	 * 
	 * searches the drive oContent (e.g. "NOURISH") in a drive mesh
	 * and decreases the quota of affect (moPleasure) by the amount of oVal
	 * 
	 */
	private void decreaseQuotaOfAffectFromDM (String oContent, double oVal) {		
		// search in drives
		for(clsDriveMeshOLD oDrives : moDrives_Input){
			// check DriveMesh
			// oDrives.b.getMoContent() = for example "NOURISH"
			// oDrives.b.getMoContentType() =  for example "LIFE"
			if (oDrives.getMoContent().equalsIgnoreCase(oContent)){
				oDrives.setMrQuotaOfAffect(oDrives.getMrQuotaOfAffect() - oVal);
				return; // only decrease the drive which is found first 
			}
		}
	}
	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_5(new ArrayList<clsPair<clsPhysicalRepresentation,clsDriveMeshOLD>>());
		send_I5_12(moDrives_Output);
		send_I5_14(new ArrayList<clsPair<clsPhysicalRepresentation,clsDriveMeshOLD>>());
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:50:14
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		moDescription = "Checks periodically and proactiv internalized rules.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_12_send#send_I5_12(java.util.ArrayList)
	 */
	@Override
	public void send_I5_12(
			ArrayList<clsDriveMeshOLD> poDrives) {
		
		((I5_12_receive)moModuleList.get(7)).receive_I5_12(poDrives);
		
		putInterfaceData(I5_12_send.class, poDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:36:17
	 * 
	 * @see pa._v38.interfaces.modules.I5_5_send#send_I5_5(java.util.ArrayList)
	 */
	@Override
	public void send_I5_5(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> poData) {
		
		((I5_5_receive)moModuleList.get(6)).receive_I5_5(poData);
		
		putInterfaceData(I5_5_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:40:54
	 * 
	 * @see pa._v38.interfaces.modules.I5_14_send#send_I5_14(java.util.ArrayList)
	 */
	@Override
	public void send_I5_14(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> poData) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:46:08
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_receive#receive_I5_21(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_21(ArrayList<clsEmotion> poEmotions) {
		moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions); 
		
	}	
}
