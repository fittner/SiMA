/**
 * E38_PrimalRepressionForSelfPreservationDrives.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:21:18
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.List;

import pa._v30.tools.clsTripple;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorDrives;
import pa._v30.interfaces.modules.I1_5_receive;
import pa._v30.interfaces.modules.I1_5_send;
import pa._v30.interfaces.modules.I2_15_receive;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

import config.clsBWProperties;

/**
 *
 * 
 * @author deutsch
 * 03.03.2011, 15:21:18
 * 
 */
public class E38_PrimalRepressionForSelfPreservationDrives extends	clsModuleBase 
			implements itfInspectorDrives, I2_15_receive, I1_5_send {
	public static final String P_MODULENUMBER = "38";
	
	private ArrayList<clsDriveMesh> moDriveList_IN;
	private ArrayList< clsTripple<String, String, ArrayList<Double> >> moPrimalRepressionMemory;
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:44:20
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E38_PrimalRepressionForSelfPreservationDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);
		fillPrimalRepressionMemory();
	}
	
	private void fillPrimalRepressionMemory() {
		moPrimalRepressionMemory = new ArrayList<clsTripple<String,String,ArrayList<Double>>>();
		
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "NOURISH", new ArrayList<Double>(Arrays.asList(0.9, 0.0, 0.1, 0.0)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "BITE", new ArrayList<Double>(Arrays.asList(0.9, 0.0, 0.1, 0.0)) ) );
		
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "RELAX", new ArrayList<Double>(Arrays.asList(0.1, 0.3, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "SLEEP", new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, 0.8)) ) );
		
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "REPRESS", new ArrayList<Double>(Arrays.asList(0.2, 0.4, 0.2, 0.0)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "DEPOSIT", new ArrayList<Double>(Arrays.asList(0.0, 0.7, 0.0, 0.1)) ) );		
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
		String text ="";
		
		text += toText.listToTEXT("moPrimalRepressionMemory", moPrimalRepressionMemory);
		text += toText.listToTEXT("moDriveList_IN", moDriveList_IN);
		
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
	
		//nothing to do
	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		for (clsDriveMesh oDM:moDriveList_IN) {
			categorizeDriveMesh(oDM);
		}
	}

	private void categorizeDriveMesh(clsDriveMesh poMD) {
		for (clsTripple<String,String,ArrayList<Double>> oPRM:moPrimalRepressionMemory) {
			String oContentType = oPRM.a; 
			String oContext = oPRM.b;
			
			if ( poMD.getMoContent().equals(oContext) && poMD.getMoContentType().equals(oContentType)) {
				ArrayList<Double> oC = oPRM.c;
				
				poMD.setCategories(oC.get(0), oC.get(1), oC.get(2), oC.get(3));
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_5(moDriveList_IN);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:21:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:59:47
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	
	/**
	 * @author zeilinger
	 * 19.03.2011, 15:50:57
	 * 
	 * @return the moDriveCandidate
	 */
	@Override
	public ArrayList<clsDriveMesh> getDriveList() {
		return moDriveList_IN;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:00:47
	 * 
	 * @see pa.interfaces.send._v30.I1_5_send#send_I1_5(java.util.List, java.util.List)
	 */
	@Override
	public void send_I1_5(List<clsDriveMesh> poData) {
		
		((I1_5_receive)moModuleList.get(6)).receive_I1_5(poData);
		((I1_5_receive)moModuleList.get(7)).receive_I1_5(poData);
		((I1_5_receive)moModuleList.get(9)).receive_I1_5(poData);
		
		putInterfaceData(I1_5_send.class, poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:00:47
	 * 
	 * @see pa.interfaces.receive._v30.I2_15_receive#receive_I2_15(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_15(ArrayList<clsDriveMesh> poDriveList) {
		
		moDriveList_IN = (ArrayList<clsDriveMesh>) deepCopy(poDriveList); 
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
		moDescription = "This function categorizes the thing presentations according to the four primary drives. The result of it is that thing presentations have an additional value which can be used for further memory lookup to find similar entries.";
	}	
}
