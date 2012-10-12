/**
 * F49_PrimalRepressionForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:30
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.modules.ePsychicInstances;
import pa._v38.interfaces.itfInspectorBarChart;
import pa._v38.interfaces.itfInterfaceCompare;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_2_receive;
import pa._v38.interfaces.modules.I5_2_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Primal repressed content is associated to the remembered drive contents. If a assoc.
 * is possible we get remembered drive contents with assoc. primal repressions
 * 
 * @author muchitsch
 * 07.05.2012, 15:47:30
 * 
 */
public class F49_PrimalRepressionForDrives extends clsModuleBase 
			implements I5_1_receive, I5_2_send, itfInspectorBarChart, itfInterfaceCompare{

	public static final String P_MODULENUMBER = "49";

	private ArrayList<clsDriveMesh> moInput;
	private ArrayList<clsDriveMesh> moOutput;
	
	private HashMap<String,Double> moChartInputData;
	private HashMap<String,Double> moChartOutputData;
	
	
	/** DOCUMENT (muchitsch) - insert description; @since 19.07.2011 14:06:33 */
	private ArrayList< clsTriple<String, String, ArrayList<Double> >> moPrimalRepressionMemory;
	/**
	 * Constructor, and filles the primal repression memory
	 * 
	 * @author muchitsch
	 * 02.05.2011, 15:51:29
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F49_PrimalRepressionForDrives(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp); 
		fillPrimalRepressionMemory();
		
		moChartInputData = new HashMap<String,Double>();
		moChartOutputData = new HashMap<String,Double>();
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
	
	/**
	 * This precreates the values for primal repression. Tha values are chosen randomly and should be loaded out of a 
	 * personality config file later.
	 *
	 * @since 19.07.2011 14:09:01
	 */
	private void fillPrimalRepressionMemory() {
		moPrimalRepressionMemory = new ArrayList<clsTriple<String,String,ArrayList<Double>>>();
		
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ORAL", new ArrayList<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ANAL", new ArrayList<Double>(Arrays.asList(0.4, 0.3, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_PHALLIC", new ArrayList<Double>(Arrays.asList(0.1, 0.1, 0.1, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_GENITAL", new ArrayList<Double>(Arrays.asList(0.1, 0.5, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ORAL", new ArrayList<Double>(Arrays.asList(0.8, 0.01, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ANAL", new ArrayList<Double>(Arrays.asList(0.1, 0.4, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_PHALLIC", new ArrayList<Double>(Arrays.asList(0.01, 0.01, 0.01, 0.6)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_GENITAL", new ArrayList<Double>(Arrays.asList(0.7, 0.7, 0.1, 0.9)) ) );
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moInput", moInput);	
		text += toText.listToTEXT("moOutput", moOutput);		
				
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:12:55
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_1(
			ArrayList<clsDriveMesh> poData) {
		
		moInput = (ArrayList<clsDriveMesh>) deepCopy(poData); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() {
		moOutput =  deepCopy(moInput); 
		
		// TODO objekte einbeziehen, dafuer auf den clsPhysicalRepresentation die property isCandidateForRepression auf true setzen
		//go to every drive mesh in the list and calculate the partial things
		
		/*for (clsDriveMesh oDM:moOutput) {
			categorizeDriveMesh(oDM);
		}
		*/
	
		
		//chart data
		for( clsDriveMesh oDriveMeshEntry:moOutput){
			String oaKey = oDriveMeshEntry.getChartShortString();
			moChartOutputData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
		for( clsDriveMesh oDriveMeshEntry:moInput){
			String oaKey = oDriveMeshEntry.getChartShortString();
			moChartInputData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}

	}
	
//	private void categorizeDriveMesh(clsDriveMesh poMD) {
//		for (clsTriple<String,String,ArrayList<Double>> oPRM:moPrimalRepressionMemory) {
//			String oContentType = oPRM.a; 
//			String oContext = oPRM.b;
//			
//			if ( poMD.getMoContent().equals(oContext) && poMD.getMoContentType().equals(oContentType)) {
//				ArrayList<Double> oC = oPRM.c;
//				
//				poMD.setCategories(oC.get(0), oC.get(1), oC.get(2), oC.get(3));
//				break;
//			}
//		}
//	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_2(moOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
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
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
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
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		//FIXME CM english descriptiopn
		moDescription = "Anhaengen von urverdraengten Inhalte an die erinnerten Triebinhalte, wenn einen Assoziation moeglich ist => 'Erinnerte Triebinhalte mit assoziierten Urverdraengungen'";
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:12:55
	 * 
	 * @see pa._v38.interfaces.modules.I5_2_send#send_I5_2(java.util.ArrayList)
	 */
	@Override
	public void send_I5_2(ArrayList<clsDriveMesh> poData) {
		
		((I5_2_receive)moModuleList.get(54)).receive_I5_2(poData);
		
		putInterfaceData(I5_2_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartTitle()
	 */
	@Override
	public String getBarChartTitle() {

		return "";
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getBarChartData() {
		ArrayList<Double> oInput= new ArrayList<Double>();
		oInput.addAll(moChartInputData.values());
		
		ArrayList<Double> oOutput= new ArrayList<Double>();
		oOutput.addAll(moChartOutputData.values());
		
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		oResult.add(oInput);
		oResult.add(oOutput);
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartCategoryCaptions()
	 */
	@Override
	public ArrayList<String> getBarChartCategoryCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("input values");
		oResult.add("output values");
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartColumnCaptions()
	 */
	@Override
	public ArrayList<String> getBarChartColumnCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moChartOutputData.keySet());

		return oResult;

	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 3:22:37 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesRecv() {
		
		return getInterfacesRecv();
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 3:22:37 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesSend() {
		
		return getInterfacesSend();
	}
}
