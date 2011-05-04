/**
 * E44_PrimalRepressionForSexualDrives.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:20:48
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfInspectorDrives;
import pa._v38.interfaces.modules.I4_1_receive;
//import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_1_send;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:20:48
 * 
 */
//HZ 4.05.2011: Module is only required to transfer its functionality to v38
@Deprecated
public class _E44_PrimalRepressionForSexualDrives extends clsModuleBase 
			implements itfInspectorDrives, I4_1_receive, I5_1_send {
	public static final String P_MODULENUMBER = "44";
	
	private ArrayList< clsTripple<String, String, ArrayList<Double> >> moPrimalRepressionMemory;
	private ArrayList<clsDriveMesh> moDrives;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:57:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public _E44_PrimalRepressionForSexualDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		fillPrimalRepressionMemory();
	}
	
	private void fillPrimalRepressionMemory() {
		moPrimalRepressionMemory = new ArrayList<clsTripple<String,String,ArrayList<Double>>>();
		
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ORAL", new ArrayList<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ANAL", new ArrayList<Double>(Arrays.asList(0.4, 0.3, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_PHALLIC", new ArrayList<Double>(Arrays.asList(0.1, 0.1, 0.1, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_GENITAL", new ArrayList<Double>(Arrays.asList(0.1, 0.5, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ORAL", new ArrayList<Double>(Arrays.asList(0.8, 0.01, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ANAL", new ArrayList<Double>(Arrays.asList(0.1, 0.4, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_PHALLIC", new ArrayList<Double>(Arrays.asList(0.01, 0.01, 0.01, 0.6)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_GENITAL", new ArrayList<Double>(Arrays.asList(0.7, 0.7, 0.1, 0.9)) ) );
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moPrimalRepressionMemory", moPrimalRepressionMemory);
		text += toText.listToTEXT("moDrives", moDrives);		
		
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
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:48
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		for (clsDriveMesh oDM:moDrives) {
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
	 * 03.03.2011, 15:20:48
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:48
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:48
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
//		send_I2_19(moDrives);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:51:07
	 * 
	 * @see pa.interfaces.send._v38.I2_19_send#send_I2_19(java.util.List)
	 */
//	@Override
//	public void send_I2_19(ArrayList<clsDriveMesh> poData) {
//		((I5_1_receive)moModuleList.get(6)).receive_I2_19(poData);
//		((I5_1_receive)moModuleList.get(7)).receive_I2_19(poData);
//		((I5_1_receive)moModuleList.get(9)).receive_I2_19(poData);
//		
//		putInterfaceData(I5_1_send.class, poData);
//	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:51:07
	 * 
	 * @see pa.interfaces.receive._v38.I2_18_receive#receive_I2_18(java.util.ArrayList)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public void receive_I2_18(ArrayList<clsDriveMesh> poDrives) {
//		moDrives = (ArrayList<clsDriveMesh>)deepCopy(poDrives);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This function categorizes the thing presentations according to the four primary drives. The result of it is that thing presentations have an additional value which can be used for further memory lookup to find similar entries.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:13:03
	 * 
	 * @see pa._v38.interfaces.itfInspectorDrives#getDriveList()
	 */
	@Override
	public ArrayList<clsDriveMesh> getDriveList() {
		return moDrives;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:34:57
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_send#send_I5_1(java.util.ArrayList)
	 */
	@Override
	public void send_I5_1(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:34:57
	 * 
	 * @see pa._v38.interfaces.modules.I4_1_receive#receive_I4_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I4_1(ArrayList<clsDriveMesh> poDriveCandidates) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}	
}
