/**
 * F48_AccumulationOfAffectsForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author muchitsch
 * 02.05.2011, 15:47:11
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.SortedMap;
import pa._v38.modules.eImplementationStage;
import pa._v38.storage.DT4_PleasureStorage;
import pa._v38.tools.clsDriveValueSplitter;
import pa._v38.tools.eDriveValueSplitter;

import pa._v38.interfaces.itfInspectorCombinedTimeChart;
import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.itfInspectorStackedBarChart;
import pa._v38.interfaces.modules.I3_3_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I4_1_receive;
import pa._v38.interfaces.modules.I4_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
 * on a splitter mechanism and outputs the result to a list of drive candidates.
 * 
 * @author muchitsch
 * 07.05.2012, 15:47:11
 */
public class F48_AccumulationOfQuotaOfAffectsForDrives extends clsModuleBase 
					implements I3_3_receive, I3_4_receive, I4_1_send, itfInspectorGenericDynamicTimeChart, itfInspectorStackedBarChart, itfInspectorCombinedTimeChart {

	public static final String P_MODULENUMBER = "48";
	public static final String P_SPLITFACTORLABEL = "label";
	public static final String P_SPLITFACTORVALUE = "value";
	public static final String P_NUM_SPLIFACTOR = "num";
	private HashMap<String, Double> moSplitterFactor;	

	private DT4_PleasureStorage moPleasureStorage;
	private double mnCurrentPleasure = 0.0;
	
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData;
	//holds the homoestatic drive pairs, A is agressive
	private ArrayList<clsPair<clsDriveMesh,clsDriveMesh>> moHomoestasisDriveComponents_IN;
	//holds the list of all sexual and homeoststic drives
	private ArrayList<clsDriveMesh> moAllDriveComponents_OUT;

	private ArrayList<clsDriveMesh> moSexualDriveRepresentations_IN;
	
	/**
	 *F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
	 * on a splitter mechanism and outputs the result to a list of drive candidates.
	 * 
	 * @author muchitsch
	 * 02.05.2011, 15:48:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F48_AccumulationOfQuotaOfAffectsForDrives(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		moPleasureStorage = poPleasureStorage;
		moDriveChartData =  new HashMap<String, Double>(); //initialize charts
		applyProperties(poPrefix, poProp);	
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		// see PhD Deutsch2011 p82 for what this is used for		
		int i=0;
		
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "NOURISH");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "BITE");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "RELAX");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "DEPOSIT");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "REPRESS");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "SLEEP");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;

		oProp.setProperty(pre+P_NUM_SPLIFACTOR, i);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moSplitterFactor = new HashMap<String, Double>();
		
		int num = poProp.getPropertyInt(pre+P_NUM_SPLIFACTOR);
		for (int i=0; i<num; i++) {
			String oKey = poProp.getProperty(pre+i+"."+P_SPLITFACTORLABEL);
			Double oValue = poProp.getPropertyDouble(pre+i+"."+P_SPLITFACTORVALUE);
			moSplitterFactor.put(oKey, oValue);
		}		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("HomIN", moHomoestasisDriveComponents_IN);
		text += toText.listToTEXT("SexIN", moSexualDriveRepresentations_IN);
		text += toText.listToTEXT("OUT", moAllDriveComponents_OUT);	
		
		text += toText.valueToTEXT("Pleasure", mnCurrentPleasure);	
		
				
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		moAllDriveComponents_OUT = new ArrayList<clsDriveMesh>();
		
	
		//first calculate the tensions for homoestatic drives
		ProcessHomeostaticDriveCandidates();
		
		//second calculate the tensions for sexual drives
		ProcessSexualDriveCandidates();
		
		//calculate the pleasure gain from reduced tensions for DT4
		ProcessPleasureCalculation();
		
		//add some meaningfull information to the debug info, comment this out for performance
		AddDebugInfoForUsProgrammers(this.moAllDriveComponents_OUT);
		
		
		//add chart data for all drives:
		for (clsDriveMesh oDriveMeshEntry : moAllDriveComponents_OUT )
		{
			//add some time chart data
			String oaKey = oDriveMeshEntry.getChartShortString();
			if ( !moDriveChartData.containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moDriveChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
			
		}
		
		//now add chart for pleasure
		String olKey = "pleasure";
		if ( !moDriveChartData.containsKey(olKey) ) {
			mnChartColumnsChanged = true;
		}
		moDriveChartData.put(olKey, mnCurrentPleasure);
		

	}
	
	/**
	 * Guarantees that the provided scalar value r is within the range -1<r<1.
	 *
	 * @since 14.07.2011 11:53:36
	 *
	 * @param r
	 * @return r
	 */
	private double normalize(double r) {
		if (r>1) {return 1;}
		else if (r<-1) {return -1;}
		else {return r;}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
	}

	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 18.07.2012 12:56:23
	 *
	 */
	private void ProcessPleasureCalculation() {
		
		//set the actual drive list to DT4, this automatically calculates the pleasure and this value can the be used everywhere
		moPleasureStorage.receive_D4_1(moAllDriveComponents_OUT);
		mnCurrentPleasure = moPleasureStorage.send_D4_1();
	}

	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 18.07.2012 12:26:24
	 *
	 */
	private void ProcessSexualDriveCandidates() {
		for( clsDriveMesh oSexualDMPairEntry : moSexualDriveRepresentations_IN){
			moAllDriveComponents_OUT.add(oSexualDMPairEntry);
		}
		
	}

	/**
	 * Calculate the new values of the homeostatic drive pairs according to the Deutsch-curves see p82
	 *
	 * @since 18.07.2012 12:26:19
	 *
	 */
	private void ProcessHomeostaticDriveCandidates() {

		for( clsPair<clsDriveMesh,clsDriveMesh> oHomeostaticDMPairEntry : moHomoestasisDriveComponents_IN){
			double rSlopeFactor = 0.5; //default value, the real values are taken from the personality config in the next loop
				try {
					
					//get the slope factor from the table/aka properties
					eOrgan oOrgan = ((clsDriveMesh)oHomeostaticDMPairEntry.a).getActualDriveSourceAsENUM();
					if(moSplitterFactor.containsKey( oOrgan ))
					{
						rSlopeFactor = moSplitterFactor.get(oOrgan);
					}
					else
					{
					 //no slope factor found, use default
					}
				} catch (java.lang.Exception e) {
					System.out.print(e);
			}			

			//Calculate the tension values of the pairs, see Deutsch2011 p82 for details
			
			double oOldAgressiveQoA  = ((clsDriveMesh)oHomeostaticDMPairEntry.a).getQuotaOfAffect();
			double oOldLibidoneusQoA = ((clsDriveMesh)oHomeostaticDMPairEntry.b).getQuotaOfAffect();
			
			//calculate the tensions according to Deutsch's curves
			clsPair<Double, Double> oSplitResult = 
				clsDriveValueSplitter.calc(	oOldAgressiveQoA, 
											oOldLibidoneusQoA, 
											eDriveValueSplitter.ADVANCED, 
											rSlopeFactor); 
			
			//set the calculated affects into the entry set of the loop, actually pleasure is not right here, this value is quota of affect not pleasure
			double oNewAgressiveQoA  = oSplitResult.a;
			double oNewLibidoneusQoA = oSplitResult.b;
			
			((clsDriveMesh)oHomeostaticDMPairEntry.a).setQuotaOfAffect(oNewAgressiveQoA); //set the new agr tension to pair A
			((clsDriveMesh)oHomeostaticDMPairEntry.b).setQuotaOfAffect(oNewLibidoneusQoA); //set the new lib tension to pair B
						
			//and add it to the outgoing list as two separate entries for lib/agr
			moAllDriveComponents_OUT.add((clsDriveMesh)oHomeostaticDMPairEntry.a);
			moAllDriveComponents_OUT.add((clsDriveMesh)oHomeostaticDMPairEntry.b);
		}
	}
	
	
	/**
	 * This add debug info only, no real model-info, deactivate this if performance is king
	 *
	 * @since 23.07.2012 14:41:43
	 *
	 * @param poDriveList
	 */
	private void AddDebugInfoForUsProgrammers(ArrayList<clsDriveMesh> poDriveList)
	{ 
		// see Deutsch p81 for the source for the names
		
		for( clsDriveMesh oDriveEntry : poDriveList){
			
			//the big IF:
			if(		oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STOMACH &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("bite");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STOMACH &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("nourish");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.RECTUM &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("expulsion");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.RECTUM &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("repress");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.BLADDER &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("squirt out");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.BLADDER &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("retain warm");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STAMINA &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("put to sleep");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STAMINA &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("relax");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.LIBIDO &&
					oDriveEntry.getPartialDrive()== ePartialDrive.GENITAL &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("male: amputate; female:to suffocate");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.LIBIDO &&
					oDriveEntry.getPartialDrive()== ePartialDrive.GENITAL &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("male: to errect; female:to absorb/to complete");
			
			//TODO names for the other sexual/homeo drives

		}
		
	}
	


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I4_1(moAllDriveComponents_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based on a splitter mechanism and outputs the result to a list of drive candidates.";
	}
	


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I4_1_send#send_I4_1(java.util.ArrayList)
	 */
	@Override
	public void send_I4_1(ArrayList<clsDriveMesh> poDriveComponents) {
		((I4_1_receive)moModuleList.get(57)).receive_I4_1(poDriveComponents);
		putInterfaceData(I4_1_send.class, poDriveComponents);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I3_4_receive#receive_I3_4(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_4(
			ArrayList<clsPair<clsDriveMesh,clsDriveMesh>> poDriveComponents) {
		moHomoestasisDriveComponents_IN = (ArrayList<clsPair<clsDriveMesh,clsDriveMesh>>) deepCopy(poDriveComponents);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I3_3_receive#receive_I3_3(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_3(
			ArrayList<clsDriveMesh> poSexualDriveRepresentations) {
		moSexualDriveRepresentations_IN = (ArrayList<clsDriveMesh>)poSexualDriveRepresentations;
	}

	
	/*************************************************************/
	/***                        CHART METHODS                  ***/
	/*************************************************************/
	
	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Pleasure and Drives";
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moDriveChartData.values());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moDriveChartData.keySet());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;	
		
	}


	@Override
	public String getStackedBarChartTitle(){
		
		return "Drives Chart";
	}
	

	@Override
	public ArrayList<ArrayList<Double>> getStackedBarChartData(){
//		ArrayList<clsPair<clsDriveMesh,clsDriveMesh>> imoHomoestasisDriveComponents_IN;
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> aggr = new ArrayList<Double>();
		ArrayList<Double> lib = new ArrayList<Double>();
		if(moHomoestasisDriveComponents_IN!=null){
			for(int i=0; i<moHomoestasisDriveComponents_IN.size();i++){
				aggr.add(moHomoestasisDriveComponents_IN.get(i).a.getQuotaOfAffect());
				lib.add(moHomoestasisDriveComponents_IN.get(i).b.getQuotaOfAffect());
			}
		}
		
		oResult.add(aggr);
		oResult.add(lib);
		return oResult;
	}
	

	@Override
	public ArrayList<String> getStackedBarChartCategoryCaptions(){
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("aggr");
		oResult.add("lib");
		return oResult;
	}
	@Override
	public ArrayList<String> getStackedBarChartColumnCaptions(){
		ArrayList<String> oResult = new ArrayList<String>();
		if(moHomoestasisDriveComponents_IN!=null){
			for(int i=0; i<moHomoestasisDriveComponents_IN.size();i++){
				oResult.add(moHomoestasisDriveComponents_IN.get(i).a.getChartShortString().substring(2));
			}
		}
		return oResult;
	}


	@Override
	public String getCombinedTimeChartAxis() {
		return "1 to 0";
	}



	@Override
	public ArrayList<ArrayList<Double>> getCombinedTimeChartData() {
		ArrayList<ArrayList<Double>> oResult =new ArrayList<ArrayList<Double>>();
		if(moHomoestasisDriveComponents_IN!=null){
			for(int i=0; i<moHomoestasisDriveComponents_IN.size();i++){
				ArrayList<Double> iSeries = new ArrayList<Double>();
				iSeries.add(moHomoestasisDriveComponents_IN.get(i).a.getQuotaOfAffect());
				iSeries.add(moHomoestasisDriveComponents_IN.get(i).b.getQuotaOfAffect());
				oResult.add(iSeries);
			}
		}
		ArrayList<Double> iSeries = new ArrayList<Double>();
		iSeries.add(moPleasureStorage.send_D4_1());
		oResult.add(iSeries);
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 27, 2012 2:57:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getChartTitles()
	 */
	@Override
	public ArrayList<String> getChartTitles() {
		ArrayList<String> oResult = new ArrayList<String>();
		for(int i=0; i<moHomoestasisDriveComponents_IN.size();i++){
			oResult.add(moHomoestasisDriveComponents_IN.get(i).a.getChartShortString().substring(2));
		}
		oResult.add("pleasure");
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 27, 2012 2:57:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getValueCaptions()
	 */
	@Override
	public ArrayList<ArrayList<String>> getValueCaptions() {
		ArrayList<ArrayList<String>> oResult =new ArrayList<ArrayList<String>>();
		for(int i=0; i<moHomoestasisDriveComponents_IN.size();i++){
			ArrayList<String> iSeries = new ArrayList<String>();
			iSeries.add("aggr");
			iSeries.add("lib");
			oResult.add(iSeries);
		}
		ArrayList<String> iSeries = new ArrayList<String>();
		iSeries.add("pleasure");
		oResult.add(iSeries);
		return oResult;
	}


}
