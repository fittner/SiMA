///**
// * E42_AccumulationOfAffectsForSexualDrives.java: DecisionUnits - pa.modules._v38
// * 
// * @author deutsch
// * 03.03.2011, 15:20:18
// */
//package pa._v38.modules.legacycode;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.SortedMap;
//
//import pa._v38.tools.clsDriveValueSplitter;
//import pa._v38.tools.clsPair;
//import pa._v38.tools.clsTriple;
//import pa._v38.tools.eDriveValueSplitter;
//import pa._v38.tools.toText;
//import pa._v38.interfaces.itfInspectorGenericTimeChart;
//import pa._v38.interfaces.modules.I3_3_receive;
//import pa._v38.interfaces.modules.eInterfaces;
////import pa._v38.interfaces.modules.I4_1_receive;
//import pa._v38.interfaces.modules.I4_1_send;
//import pa._v38.memorymgmt.datatypes.clsDriveDemand;
//import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
//import pa._v38.modules.clsModuleBase;
//import pa._v38.modules.eImplementationStage;
//import pa._v38.modules.eProcessType;
//import pa._v38.modules.ePsychicInstances;
//
//import config.clsProperties;
//
///**
// * (deutsch) - insert description 
// * 
// * @author deutsch
// * 03.03.2011, 15:20:18
// * 
// */
////HZ 4.05.2011: Module is only required to transfer its functionality to v38
//@Deprecated
//public class _E42_AccumulationOfAffectsForSexualDrives extends clsModuleBase implements 
//							I3_3_receive, I4_1_send, itfInspectorGenericTimeChart {
//	public static final String P_MODULENUMBER = "42";
//	
//	public static final String P_SPLITFACTORLABEL = "label";
//	public static final String P_SPLITFACTORVALUE = "value";
//	public static final String P_NUM_SPLIFACTOR = "num";
//	
//	private ArrayList< clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > > moDriveCandidates;
//	private ArrayList<clsDriveMeshOLD> moDriveList; 
//	private HashMap<String, Double> moSplitterFactor;
//	
//	/**
//	 * (deutsch) - insert description 
//	 * 
//	 * @author deutsch
//	 * 03.03.2011, 17:55:15
//	 *
//	 * @param poPrefix
//	 * @param poProp
//	 * @param poModuleList
//	 * @throws Exception
//	 */
//	public _E42_AccumulationOfAffectsForSexualDrives(String poPrefix,
//			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
//			throws Exception {
//		super(poPrefix, poProp, poModuleList, poInterfaceData);
//		applyProperties(poPrefix, poProp);
//	}
//	
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 14.04.2011, 17:36:19
//	 * 
//	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
//	 */
//	@Override
//	public String stateToTEXT() {
//		String text ="";
//		
//		text += toText.listToTEXT("moDriveCandidates", moDriveCandidates);
//		text += toText.listToTEXT("moDriveList", moDriveList);
//		text += toText.mapToTEXT("moSplitterFactor", moSplitterFactor);
//		
//		return text;
//	}		
//	
//	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
//		
//		clsProperties oProp = new clsProperties();
//		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
//				
//		int i=0;
//		
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "ORAL");
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
//		i++;
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "ANAL");
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
//		i++;
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "GENITAL");
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
//		i++;
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "PHALLIC");
//		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
//		i++;
//
//		
//		oProp.setProperty(pre+P_NUM_SPLIFACTOR, i);
//		
//		return oProp;
//	}
//	
//	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);
//	
//		moSplitterFactor = new HashMap<String, Double>();
//		
//		int num = poProp.getPropertyInt(pre+P_NUM_SPLIFACTOR);
//		for (int i=0; i<num; i++) {
//			String oKey = poProp.getProperty(pre+i+"."+P_SPLITFACTORLABEL);
//			Double oValue = poProp.getPropertyDouble(pre+i+"."+P_SPLITFACTORVALUE);
//			moSplitterFactor.put(oKey, oValue);
//		}	
//	}
//	
//	@Override
//	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
//	@Override
//	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
//	@Override
//	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 03.03.2011, 15:20:18
//	 * 
//	 * @see pa.modules._v38.clsModuleBase#process_basic()
//	 */
//	@Override
//	protected void process_basic() {
//		moDriveList = new ArrayList<clsDriveMeshOLD>(); 
//		
//		for (clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > oEntry:moDriveCandidates) {
//			double rFactor = 0.5;
//			try {
//				for (Map.Entry<String, Double> oSF:moSplitterFactor.entrySet()) {
//					if (oEntry.a.a.toString().contains(oSF.getKey())) {
//						rFactor = oSF.getValue();
//					}
//				}
//			} catch (java.lang.Exception e) {
//				//do nothing
//			}
//			
//			clsPair<Double, Double> oSplitResult = clsDriveValueSplitter.calc(oEntry.a.b.getTension(), oEntry.b.b.getTension(), 
//					eDriveValueSplitter.ADVANCED, rFactor); 
//			
//			double oLifeAffect  = normalize( oSplitResult.a * oEntry.a.c );
//			double oDeathAffect = normalize( oSplitResult.b * oEntry.b.c );
//			
//			oEntry.a.a.setPleasure(oLifeAffect); 
//			oEntry.b.a.setPleasure(oDeathAffect); 
//			moDriveList.add(oEntry.a.a); 
//			moDriveList.add(oEntry.b.a); 			
//		}
//	}
//	
//	private double normalize(double r) {
//		if (r>1) {return 1;}
//		else if (r<-1) {return -1;}
//		else {return r;}
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 03.03.2011, 15:20:18
//	 * 
//	 * @see pa.modules._v38.clsModuleBase#process_draft()
//	 */
//	@Override
//	protected void process_draft() {
//		// 
//
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 03.03.2011, 15:20:18
//	 * 
//	 * @see pa.modules._v38.clsModuleBase#process_final()
//	 */
//	@Override
//	protected void process_final() {
//		// 
//
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 03.03.2011, 15:20:18
//	 * 
//	 * @see pa.modules._v38.clsModuleBase#send()
//	 */
//	@Override
//	protected void send() {
////		send_I2_18(moDriveList);
//
//	}
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 03.03.2011, 15:50:26
//	 * 
//	 * @see pa.interfaces.send._v38.I2_18_send#receive_I2_18(java.util.ArrayList)
//	 */
////	@Override
////	public void send_I2_18(ArrayList<clsDriveMesh> poDrives) {
////		((I4_1_receive)moModuleList.get(44)).receive_I2_18(poDrives);
////		
////		putInterfaceData(I4_1_send.class, poDrives);
////	}
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 03.03.2011, 15:50:26
//	 * 
//	 * @see pa.interfaces.receive._v38.I2_17_receive#receive_I2_17(java.util.ArrayList)
//	 */
////	@SuppressWarnings("unchecked")
////	@Override
////	public void receive_I2_17(ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > > poDriveCandidates) {
////		moDriveCandidates = (ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > >)deepCopy(poDriveCandidates);
////
////	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 15.04.2011, 13:52:57
//	 * 
//	 * @see pa.modules._v38.clsModuleBase#setDescription()
//	 */
//	@Override
//	public void setDescription() {
//		moDescription = "The amount of total stored libido which equals the tension of the sexual drives is attached to the memory traces. Now, thing presentations consisting of drive aim, drive source, drive object, and quota of affects exist and can be processed by the next modules.";
//	}
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 15.04.2011, 17:41:33
//	 * 
//	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
//	 */
//	@Override
//	public ArrayList<String> getTimeChartCaptions() {
//		ArrayList<String> oCaptions = new ArrayList<String>();
//		
//		for( clsDriveMeshOLD oDM : moDriveList) {
//			oCaptions.add(oDM.getMoContent());
//		}
//		
//		return oCaptions;
//	}	
//	
//
//	/* (non-Javadoc)
//	 *
//	 * @author zeilinger
//	 * 02.11.2010, 23:00:57
//	 * 
//	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
//	 */
//	@Override
//	public ArrayList<Double> getTimeChartData() {
//		ArrayList<Double> oTimingValues = new ArrayList<Double>();
//		
//		for( clsDriveMeshOLD oDM : moDriveList) {
//			oTimingValues.add(oDM.getPleasure());
//		}
//		return oTimingValues;
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 19.04.2011, 10:31:35
//	 * 
//	 * @see pa.interfaces._v38.itfInspectorTimeChart#getTimeChartTitle()
//	 */
//	@Override
//	public String getTimeChartTitle() {
//		return "Sexual Drives: Drive-Quota of Affect Chart";
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 19.04.2011, 10:31:35
//	 * 
//	 * @see pa.interfaces._v38.itfInspectorTimeChart#getTimeChartUpperLimit()
//	 */
//	@Override
//	public double getTimeChartUpperLimit() {
//		return 1.05;
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 19.04.2011, 10:31:35
//	 * 
//	 * @see pa.interfaces._v38.itfInspectorTimeChart#getTimeChartLowerLimit()
//	 */
//	@Override
//	public double getTimeChartLowerLimit() {
//		return -0.05;
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author deutsch
//	 * 19.04.2011, 10:34:35
//	 * 
//	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartAxis()
//	 */
//	@Override
//	public String getTimeChartAxis() {
//		return "Quota of Affects";
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author zeilinger
//	 * 04.05.2011, 08:34:27
//	 * 
//	 * @see pa._v38.interfaces.modules.I4_1_send#send_I4_1(java.util.ArrayList)
//	 */
//	@Override
//	public void send_I4_1(ArrayList<clsDriveMeshOLD> poDriveCandidates) {
//		// 
//		
//	}
//
//	/* (non-Javadoc)
//	 *
//	 * @author zeilinger
//	 * 04.05.2011, 08:34:27
//	 * 
//	 * @see pa._v38.interfaces.modules.I3_3_receive#receive_I3_3(java.util.ArrayList)
//	 */
//	@Override
//	public void receive_I3_3(
//			ArrayList<clsPair<clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>, clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>>> poDriveCandidates) {
//		// 
//		
//	}		
//}
