/**
 * E24_RealityCheck.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:49:09
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import pa._v38.interfaces.itfMinimalModelMode;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_7_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:49:09
 * 
 */
public class F51_RealityCheckWishFulfillment extends clsModuleBase implements itfMinimalModelMode, I6_6_receive, I6_7_send {
	public static final String P_MODULENUMBER = "51";
	
	private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception_Input; 
	//AW 20110602 Added associated memories to the input 
	private ArrayList<clsSecondaryDataStructureContainer> moAssociatedMemoriesSecondary_IN;
	
	
	private ArrayList<clsSecondaryDataStructureContainer> moRealityPerception_Output; 
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  //removed by HZ - not required now
	private ArrayList<clsTripple<clsSecondaryDataStructureContainer, ArrayList<clsSecondaryDataStructureContainer>, clsSecondaryDataStructureContainer>> moExtractedPrediction_OUT;
	private boolean mnMinimalModel;

	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:50:46
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F51_RealityCheckWishFulfillment(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.listToTEXT("moFocusedPerception_Input", moFocusedPerception_Input);
		text += toText.listToTEXT("moRealityPerception_Output", moRealityPerception_Output);
		text += toText.listToTEXT("moDriveList", moDriveList);
		
		return text;
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
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
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_6(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception, ArrayList<clsSecondaryDataStructureContainer> poDriveList, 
			ArrayList<clsSecondaryDataStructureContainer> poAssociatedMemoriesSecondary) {
		moFocusedPerception_Input = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poFocusedPerception);
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>) deepCopy(poDriveList);
		moAssociatedMemoriesSecondary_IN = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poAssociatedMemoriesSecondary);
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
		if (!mnMinimalModel) {
			moRealityPerception_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
			
			//FIXME HZ 2010.08.24 Functionality of old code is taken; however I am rather sure that it has to be
			//adapted
			for(clsSecondaryDataStructureContainer oCon : moFocusedPerception_Input){
				moRealityPerception_Output.add(oCon); 
			}
			
			moExtractedPrediction_OUT = extractPredictions(moAssociatedMemoriesSecondary_IN);
		}
	}
	
	private ArrayList<clsTripple<clsSecondaryDataStructureContainer, ArrayList<clsSecondaryDataStructureContainer>, clsSecondaryDataStructureContainer>> extractPredictions(ArrayList<clsSecondaryDataStructureContainer> oInput) {
		ArrayList<clsTripple<clsSecondaryDataStructureContainer, ArrayList<clsSecondaryDataStructureContainer>, clsSecondaryDataStructureContainer>> oRetVal = new ArrayList<clsTripple<clsSecondaryDataStructureContainer, ArrayList<clsSecondaryDataStructureContainer>, clsSecondaryDataStructureContainer>>();
		
		return oRetVal;
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
		if (mnMinimalModel) {
			send_I6_7(moFocusedPerception_Input, new ArrayList<clsTripple<clsSecondaryDataStructureContainer, ArrayList<clsSecondaryDataStructureContainer>, clsSecondaryDataStructureContainer>>());
		} else {
			//HZ: null is a placeholder for the bjects of the type pa._v38.memorymgmt.datatypes
			send_I6_7(moRealityPerception_Output, moExtractedPrediction_OUT);
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:51:11
	 * 
	 * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
	 */
	@Override
	public void send_I6_7(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception,
			ArrayList<clsTripple<clsSecondaryDataStructureContainer, ArrayList<clsSecondaryDataStructureContainer>, clsSecondaryDataStructureContainer>> poExtractedPrediction) {
		((I6_7_receive)moModuleList.get(26)).receive_I6_7(poRealityPerception, poExtractedPrediction);
		
		putInterfaceData(I6_7_send.class, poRealityPerception, poExtractedPrediction);
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
		// TODO (KOHLHAUSER) - Auto-generated method stub
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
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:50:53
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
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
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}.";
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

